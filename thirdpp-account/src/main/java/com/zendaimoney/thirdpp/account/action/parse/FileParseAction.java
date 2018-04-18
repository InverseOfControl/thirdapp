package com.zendaimoney.thirdpp.account.action.parse;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

import com.zendaimoney.thirdpp.account.conf.ServerConfig;
import com.zendaimoney.thirdpp.account.entity.AccountInfo;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;
import com.zendaimoney.thirdpp.account.service.ChannelAccountInfoService;
import com.zendaimoney.thirdpp.account.service.ChannelAccountRequestService;
import com.zendaimoney.thirdpp.account.util.Constants;

public abstract class FileParseAction {

	@Autowired
	protected ChannelAccountRequestService channelAccountRequestService;
	@Autowired
	protected ChannelAccountInfoService channelAccountInfoService;

	private static final Log logger = LogFactory.getLog(FileParseAction.class);
	
	protected abstract void parseFileAndRecord(File file, ChannelAccountConfig config, ChannelAccountRequest request) throws IOException, SQLException, ReflectiveOperationException;
	
	public void parse(File file, ChannelAccountConfig config, ChannelAccountRequest request) throws IOException, SQLException, ReflectiveOperationException {
		parseFileAndRecord(file, config, request);
		updateChannelAccountRequest(request);
	}
	
	protected void updateChannelAccountRequest(ChannelAccountRequest request) {
		request.setFailedReason(StringUtils.EMPTY);
		request.setStatus(Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_INSERT_TABLE_SUCCESS);
		request.setInsertEndTime(new Date());
		channelAccountRequestService.update(request);
	}
	
	protected void batchInsertAccountInfo(List<AccountInfo> aiList) throws SQLException {
		// accountInfoList.size() 应该是等于  collectInfoList.size()
		// 那么我们只需要控制500 条进行一次的提交
		int size = aiList.size();
		int batchDefinitionSize = ServerConfig.systemConfig.getBatchSize();
		if (batchDefinitionSize <= 0) {
			batchDefinitionSize = 500;
		}
		int batchSize = size / batchDefinitionSize;
		for (int i = 0; i < batchSize; i++) {
			List<AccountInfo> subBatchAccountInfo = aiList.subList(batchDefinitionSize * i, batchDefinitionSize * (i + 1));
			channelAccountInfoService.batchInsert(subBatchAccountInfo);
			subBatchAccountInfo = null;
		}
		List<AccountInfo> remainAccountInfo = aiList.subList(batchDefinitionSize * batchSize,size);
		channelAccountInfoService.batchInsert(remainAccountInfo);
		remainAccountInfo = null;
	}
	
	protected void initialPublicAttributes(AccountInfo info, ChannelAccountConfig config, ChannelAccountRequest request) {
		info.setThirdTypeNo(request.getThirdTypeNo());
		info.setMerchantNo(request.getMerchantNo());
		
		info.setBizType(StringUtils.isNotBlank(info.getTradeFlow())
				&& info.getTradeFlow().length() > 3 ? info.getTradeFlow()
				.substring(0, 3) : AccountInfo.UNCONTROLLED_FIELD_BLANK_VALUE);
		
		info.setThirdPartyAccountReqId(request.getReqId());
		info.setCreateDate(new Date());
		String currencyUnit = config.getCurrencyUnit();
		if (Constants.CURRENCY_UNIT_FEN.equals(currencyUnit)) {
			info.setAmount(new BigDecimal(info.getOriginalAmount())
					.divide(new BigDecimal(100)).setScale(2).toString());
		}
		if (Constants.CURRENCY_UNIT_YUAN.equals(currencyUnit)) {
			info.setAmount(info.getOriginalAmount());
		}
		info.setAccountDay(request.getAccountDay());
		info.setAccountStatus(Constants.AccountInfo.ACCOUNT_INFO_ACCOUNT_STATUS_INITIAL);
		info.setBizsysAccountStatus(Constants.AccountInfo.BIZSYS_ACCOUNT_INFO_ACCOUNT_STATUS_INITIAL);
	}
	
	protected boolean checkUncontrolledFields(AccountInfo accountInfo, ChannelAccountConfig config) throws NoSuchFieldException {
		Class<AccountInfo> cla = AccountInfo.class;
		int tradeFlowMax = cla.getDeclaredField(AccountInfo.UNCONTROLLED_FIELD_TRADE_FLOW).getAnnotation(Length.class).max();
		String tradeFlow = accountInfo.getTradeFlow();
		// 要是交易流水号字段为blank或者长度超长 
		if (StringUtils.isBlank(tradeFlow) || tradeFlow.length() > tradeFlowMax) {
			logger.info("交易流水号【" + tradeFlow + "】为空或者超长");
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]{1," + tradeFlowMax + "}$");
	    Matcher match = pattern.matcher(tradeFlow);
		if (!match.matches()) {
			logger.info("交易流水号【" + tradeFlow + "】不符合正则规则");
			return false;
		}
		
		String originalAmount = accountInfo.getOriginalAmount();
		int originalAmountMax = cla.getDeclaredField(AccountInfo.UNCONTROLLED_FIELD_ORIGINAL_AMOUNT).getAnnotation(Length.class).max();
		if (StringUtils.isBlank(originalAmount) || originalAmount.length() > originalAmountMax) {
			logger.info("交易流水号【" + tradeFlow + "】，交易原始金额【" + originalAmount + "】为空或者超长");
			return false;
		}
		
		String currencyUnit = config.getCurrencyUnit(); // 货币单位 0 = 分 1 = 元
		if (Constants.CURRENCY_UNIT_FEN.equals(currencyUnit)) {
			pattern = Pattern.compile("^\\+{0,1}[1-9]\\d*");
		    match = pattern.matcher(originalAmount);
			if (!match.matches()) {
				logger.info("交易流水号【" + tradeFlow + "】，交易原始金额（分）【" + originalAmount + "】不符合正则规则");
				return false;
			}
		} else if (Constants.CURRENCY_UNIT_YUAN.equals(currencyUnit)) {
			pattern = Pattern.compile("^[+]?(([1-9]\\d*[.]?)|(0.))(\\d{0,2})?$");
			match = pattern.matcher(originalAmount);
			if (!match.matches()) {
				logger.info("交易流水号【" + tradeFlow + "】，交易原始金额（元）【" + originalAmount + "】不符合正则规则");
				return false;
			}
		}
		
		String thirdPartyTradeFlow = accountInfo.getThirdPartyTradeFlow();
		int thirdPartyTradeFlowMax = cla.getDeclaredField(AccountInfo.UNCONTROLLED_FIELD_THIRD_PARTY_TRADE_FLOW).getAnnotation(Length.class).max();
		if (StringUtils.isNotBlank(thirdPartyTradeFlow) && thirdPartyTradeFlow.length() > thirdPartyTradeFlowMax) {
			accountInfo.setThirdPartyTradeFlow(thirdPartyTradeFlow.substring(0, thirdPartyTradeFlowMax));
		}
		
		String thirdPartyTradeTime = accountInfo.getTradeTime();
		int thirdPartyTradeTimeMax = cla.getDeclaredField(AccountInfo.UNCONTROLLED_FIELD_THIRD_PARTY_TRADE_TIME).getAnnotation(Length.class).max();
		if (StringUtils.isNotBlank(thirdPartyTradeTime) && thirdPartyTradeTime.length() > thirdPartyTradeTimeMax) {
			accountInfo.setTradeTime(thirdPartyTradeTime.substring(0, thirdPartyTradeTimeMax));
		}
		
		return true;
	}
	
	protected int getStartRowIndex(ChannelAccountRequest request, ChannelAccountConfig config, int firstStartRowNumber) throws SQLException {
		int fileStartIndex = 0;
		if (firstStartRowNumber > 0) {
			fileStartIndex = config.getFileStartIndex() - firstStartRowNumber;
			if (fileStartIndex <= 0) {
				fileStartIndex = 0;
			}
		} else {
			fileStartIndex = config.getFileStartIndex() - 1;
			if (fileStartIndex <= 0) {
				fileStartIndex = 0;
			}
		}
		
		AccountInfo accountInfo = channelAccountInfoService.getLastByReqId(request.getReqId());
		if (accountInfo != null) {
			return accountInfo.getCurrentIndex();
		}
		return fileStartIndex;
	}
	
	protected String[] totalCountAmountLines(ChannelAccountConfig config) {
		String headAttrIdex = config.getFileHeaderAttrsIndex();
		Pattern pattern = Pattern.compile("^-?\\d+$");
		Matcher match = null;
		if (StringUtils.isBlank(headAttrIdex)) {
			return null;
		} else {
			String[] lines = headAttrIdex.split(Constants.STRING_DOWN_LINE);
			if (lines == null || lines.length <= 0 || lines.length > 2) {
				return null;
			} 
		
			if (lines.length == 1) {
				match = pattern.matcher(lines[0]);
				int line = Integer.parseInt(lines[0]);
				if (line == 0 || !match.matches()) {
					return null;
				}
			}
			
			if (lines.length == 2) {
				int startLine = Integer.parseInt(lines[0]);
				match = pattern.matcher(lines[0]);
				if (startLine == 0 || !match.matches()) {
					return null;
				}
				
				int endLine = Integer.parseInt(lines[1]);
				match = pattern.matcher(lines[1]);
				if (endLine == 0 || endLine <= startLine || !match.matches()) {
					return null;
				}
			}
			return lines;
		}
	}
	
	protected Map<Integer, String> getDefinitionAttributes(ChannelAccountConfig config) {
		String attrsDefinition = config.getAttrsDefinition();
		Map<Integer, String> m = new HashMap<Integer, String>();
		if (StringUtils.isNotBlank(attrsDefinition)) {
			String[] attributes = attrsDefinition.split(Constants.STRING_DOWN_LINE);
			for (String str : attributes) {
				String[] indexAndAd = str.split(Constants.STRING_SLASH);
				int index = Integer.parseInt(indexAndAd[0]);
				m.put(index, indexAndAd[1]);
			}
		}
		return m;
	}
	
	public static void main(String[] args) {
		// \\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*
		Pattern pattern = Pattern.compile("^[+]?(([1-9]\\d*[.]?)|(0.))(\\d{0,2})?$");
		String str = "89777005";
	    Matcher match = pattern.matcher(str);
	    if (match.matches()) {
	    	System.out.println("true");
	    } else {
	    	System.out.println("false");
	    }
	}
}
