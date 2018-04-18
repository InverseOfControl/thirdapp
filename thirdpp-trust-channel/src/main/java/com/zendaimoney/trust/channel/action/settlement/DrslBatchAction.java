package com.zendaimoney.trust.channel.action.settlement;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.trust.channel.action.BatchActionAbstract;
import com.zendaimoney.trust.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.trust.channel.entity.batch.DrslTop;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.vo.req.DrslDetailReq;
import com.zendaimoney.trust.channel.pub.vo.req.DrslReq;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.util.BeanUtils;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.ConvertUtils;
import com.zendaimoney.trust.channel.util.FileUtils;
import com.zendaimoney.trust.channel.util.LogPrn;

@ReqActionAnnotation(thirdType = ThirdType.CMBPAY, cmbCategory = TrustCategory.BATCH_TRADE, cmbBizType = TrustBizType.DRSL)
@Component(value = "com.zendaimoney.trust.channel.action.settlement.DrslBatchAction")
public class DrslBatchAction extends BatchActionAbstract {

	private static final LogPrn logger = new LogPrn(DrslBatchAction.class);
	
	@Override
	protected List<String> preProcess(TrustBizReqVo trustBizReqVo) throws PlatformException {

		List<String> messages = new ArrayList<String>();
		
		DrslReq drslReq = (DrslReq)trustBizReqVo;
		DrslTop drslTop = new DrslTop();
		BeanUtils.copyNotNullProperties(drslReq, drslTop);
		messages.add(ConvertUtils.objToMessage(drslTop));
		
		for (DrslDetailReq drslDetailReq : drslReq.getAttachment()) {
			messages.add(ConvertUtils.objToMessage(drslDetailReq));
		}
		return messages;
	}

	@Override
	protected void writeFile(List data, String forloadPath, String fileName) throws Exception {
		// 创建文件工具类
		FileUtils txtFileUtils = new FileUtils();
		// 将文件写入本地磁盘内
		txtFileUtils.writerTxt(forloadPath, fileName, data, Constants.ENCODE_GBK);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected List<String> parseHandle(TrustBizReqVo trustBizReqVo, String filePath) {
		List<String> newMessages = new ArrayList<String>();
		try {
			FileUtils fileUtils = new FileUtils();
			List lineContents = fileUtils.readTxt(filePath, Constants.ENCODE_GBK);

			DrslTop drslTop = new DrslTop();
			drslTop.setTotalCount(lineContents.size() - 1);
			ConvertUtils.messageToObj(lineContents.get(0).toString(), drslTop);
			
			DrslDetailReq detail = new DrslDetailReq();
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i < lineContents.size(); i++) {
				String eachLine = (String)lineContents.get(i);
				ConvertUtils.messageToObj(eachLine, detail);
				sb.append(detail.getUserNo()).append("|")
						.append(detail.getVirtualSubNo()).append("|")
						.append(detail.getAmount()).append("|")
						.append(convertTradeCode(detail.getRetCode())).append("|")
						.append(detail.getRetMsg()).append("|")
						.append(detail.getSpare());
				
				newMessages.add(sb.toString());
				sb.delete(0, sb.length());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new PlatformException(PlatformErrorCode.CHANNEL_FILE_PARSE_ERROR, PlatformErrorCode.CHANNEL_FILE_PARSE_ERROR.getDefaultMessage());
		}
		return newMessages;
	}
	
	/**
	 * 由招商银行交易状态转换为TPP2.0交易状态规则
	 * @param tradeCode of CMB
	 * @return tradeCode of TPP2.0
	 */
	public String convertTradeCode(String tradeCode) {
		// TRADE_STATE_SUCCESS(成功)，TRADE_STATE_FAILER(失败),TRADE_STATE_MIDDLE(处理中)
		if (Constants.CmbStatus.CMBMB99.getCode().equals(tradeCode)) {
			return Constants.CmbConstants.TRADE_STATE_SUCCESS.getCode();
		/*} else if (StringUtils.isBlank(tradeCode) || Constants.CmbStatus.CMBMBXX.getCode().equals(tradeCode)||Constants.CmbStatus.CMBMB05.getCode().equals(tradeCode)) {
			return Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode();*/
		} else {
			return Constants.CmbConstants.TRADE_STATE_FAILER.getCode();
		}
	}

}
