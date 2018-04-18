package com.zendaimoney.trust.channel.action.trade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.trust.channel.action.BatchActionAbstract;
import com.zendaimoney.trust.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.trust.channel.entity.batch.ChrgTop;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.vo.req.AttachmentReqVo;
import com.zendaimoney.trust.channel.pub.vo.req.ChrgDetailReq;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.ConvertUtils;
import com.zendaimoney.trust.channel.util.LogPrn;
import com.zendaimoney.trust.channel.util.FileUtils;

/**
 * 三方充值请求(CHRG)Action-批量
 * @author mencius
 *
 */
@SuppressWarnings("rawtypes")
@ReqActionAnnotation(thirdType = ThirdType.CMBPAY, cmbBizType = TrustBizType.CHRG, cmbCategory = TrustCategory.BATCH_TRADE)
@Component("com.zendaimoney.trust.channel.action.trade.ChrgBatchAction")
public class ChrgBatchAction extends BatchActionAbstract {

	private static final LogPrn logger = new LogPrn(ChrgBatchAction.class);
	
	/**
	 * 报文内容数据转换为第三方银行标准
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List preProcess(TrustBizReqVo trustBizReqVo) throws PlatformException {
		
		List<String> messages = new ArrayList<String>();
		
		List requestData = ((AttachmentReqVo<List>)trustBizReqVo).getAttachment();
		
		// 报文首行对象
		ChrgTop chrgTop = new ChrgTop(requestData.size(), calculateRechargeAmountSum(requestData));
		// 添加报文首行内容
		messages.add(ConvertUtils.objToMessage(chrgTop));
		
		ChrgDetailReq chrgDetailReq = null;
		// 遍历数据集
		for (int i = 0; i < requestData.size(); i++) {
			// 转换为明细对象
			chrgDetailReq = (ChrgDetailReq)requestData.get(i);
			
			// 将单条记录转换为每行报文
			messages.add(ConvertUtils.objToMessage(chrgDetailReq));
		}
		
		return messages;
	}

	/**
	 * 写文件
	 */
	@Override
	protected void writeFile(List messages,
			String forloadPath, String fileName) throws Exception {
		
		// 创建文件工具类
		FileUtils txtFileUtils = new FileUtils();
		// 将文件写入本地磁盘内
		txtFileUtils.writerTxt(forloadPath, fileName, messages, Constants.ENCODE_GBK);
		
	}

	/**
	 * 解析处理
	 * @param trustBizReqVo
	 * @param filePath
	 */
	@Override
	protected List<String> parseHandle(TrustBizReqVo trustBizReqVo, String filePath) {
		// 整理转换后 的报文集合  返回对象 newMessages
		List<String> newMessages = new ArrayList<String>();
		
		try {
			
			// 文件操作工具类
			FileUtils txtFileUtils = new FileUtils();
			// 读取文件
			List list = txtFileUtils.readTxt(filePath, Constants.ENCODE_GBK);
			
			// 报文文件首行
//			ChrgTop usraTop = new ChrgTop(list.size());
			// 由报文转换位首行对象
//			ConvertUtils.messageToObj(list.get(0).toString(), usraTop);
			// 明细对象载体
			ChrgDetailReq record = new ChrgDetailReq();
			StringBuffer buffer = new StringBuffer();
			for (int i = 1; i < list.size(); i++) {
				
				// 由招商银行资金托管的明细报文转换为TPP用户开户明细对象
				ConvertUtils.messageToObj(list.get(i).toString(), record);
				
				// 商户交易流水+ "|" + 银行交易流水 + "|" + 用户号 + "|" + 用户虚拟子账户号 + "|" + 三方支付公司标识 + "|" 
				// + 三方支付公司订单号 + "|" + 金额 + "|" + 交易返回码 + "|" + 银行账务日期  + "|" + 交易返回描述信息 + "|" + 备用
				buffer.append(record.getMerchantFlow())
					.append("|").append(record.getBankFlow())
					.append("|").append(record.getUserNo())
					.append("|").append(record.getVirtualSubNo())
					.append("|").append(record.getThirdFlag())
					.append("|").append(record.getThirdOrderNo())
					.append("|").append(record.getAmount())
					.append("|").append(convertTradeCode(record.getRetCode()))
					.append("|").append(record.getBankAccountDate())
					.append("|").append(record.getRetMsg())
					.append("|").append(record.getSpare());
				
				// 添加转换TPP内部规则后的报文明细体
				newMessages.add(buffer.toString());
				
				// 清空buffer缓存
				buffer.delete(0, buffer.length());
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new PlatformException(PlatformErrorCode.CHANNEL_FILE_PARSE_ERROR, PlatformErrorCode.CHANNEL_FILE_PARSE_ERROR.getDefaultMessage());
		}
		
		// 返回报文集合对象
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
		} else if (StringUtils.isBlank(tradeCode) || Constants.CmbStatus.CMBMBXX.getCode().equals(tradeCode)||Constants.CmbStatus.CMBMB05.getCode().equals(tradeCode)) {
			return Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode();
		} else {
			return Constants.CmbConstants.TRADE_STATE_FAILER.getCode();
		}
	}
	
	public ChrgDetailReq convertTradeMsg(ChrgDetailReq record,String tradeCode,String retMsg){
		if (Constants.CmbStatus.CMBMB99.getCode().equals(tradeCode)) {
			record.setRetMsg(StringUtils.isBlank(retMsg)?Constants.CmbConstants.TRADE_STATE_SUCCESS.getDesc():retMsg);
			record.setRetCode(Constants.CmbConstants.TRADE_STATE_SUCCESS.getCode());
		} else if (StringUtils.isBlank(tradeCode) || Constants.CmbStatus.CMBMBXX.getCode().equals(tradeCode)||Constants.CmbStatus.CMBMB05.getCode().equals(tradeCode)) {
			record.setRetMsg(StringUtils.isBlank(retMsg)?Constants.CmbConstants.TRADE_STATE_MIDDLE.getDesc():retMsg);
			record.setRetCode(Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode());
		} else {
			record.setRetMsg(StringUtils.isBlank(retMsg)?Constants.CmbConstants.TRADE_STATE_FAILER.getDesc():retMsg);
			record.setRetCode(Constants.CmbConstants.TRADE_STATE_FAILER.getCode());
		}
		return record;
	}
	
	/**
	 * 统计充值总额
	 * @param requestData
	 * @return
	 */
	private BigDecimal calculateRechargeAmountSum(List requestData) {
		
		// 充值总额
		BigDecimal amountSum = BigDecimal.ZERO;
		
		ChrgDetailReq chrgDetailReq = null;
		// 遍历数据集
		for (int i = 0; i < requestData.size(); i++) {
			// 转换为用户开户对象
			chrgDetailReq = (ChrgDetailReq)requestData.get(i);
			
			// 统计充值总额
			amountSum = amountSum.add(chrgDetailReq.getAmount());
		}
		return amountSum;
	}
}
