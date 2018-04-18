package com.zendaimoney.trust.channel.action.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.trust.channel.action.BatchActionAbstract;
import com.zendaimoney.trust.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.trust.channel.entity.batch.DcckDetail;
import com.zendaimoney.trust.channel.entity.batch.DcckTop;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.vo.req.DcckReq;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.util.BeanUtils;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.ConvertUtils;
import com.zendaimoney.trust.channel.util.FileUtils;
import com.zendaimoney.trust.channel.util.LogPrn;

@ReqActionAnnotation(thirdType = ThirdType.CMBPAY, cmbCategory = TrustCategory.BATCH_TRADE, cmbBizType = TrustBizType.DCCK)
@Component(value = "com.zendaimoney.trust.channel.action.account.DcckBatchAction")
public class DcckBatchAction extends BatchActionAbstract {

	private static final LogPrn logger = new LogPrn(DcckBatchAction.class);
	
	/**
	 * 由于Dcck 请求不需要向招商银行发送文件，所以preProcess writeFile 无需做任何实现
	 */
	@Override
	protected List<String> preProcess(TrustBizReqVo trustBizReqVo) throws PlatformException {

		List<String> messages = new ArrayList<String>();
		
		DcckReq dcckReq = (DcckReq)trustBizReqVo;
		DcckTop dcckTop = new DcckTop();
		BeanUtils.copyNotNullProperties(dcckReq, dcckTop);
		messages.add(ConvertUtils.objToMessage(dcckTop));
		return messages;
	}
	@Override
	@SuppressWarnings("rawtypes")
	protected void writeFile(List data, String forloadPath, String fileName) throws Exception {
		// 创建文件工具类
		FileUtils txtFileUtils = new FileUtils();
		// 将文件写入本地磁盘内
		txtFileUtils.writerTxt(forloadPath, fileName, data, Constants.ENCODE_GBK);
	}

	@Override
	protected List<String> parseHandle(TrustBizReqVo trustBizReqVo, String filePath) {
		List<String> messages = new ArrayList<String>();
		FileUtils fileUtils = new FileUtils();
		List list = fileUtils.readTxt(filePath, Constants.ENCODE_GBK);
		String firstLine = list.get(0).toString();
		DcckTop dcckTop = new DcckTop();
		try {
			ConvertUtils.messageToObj(firstLine, dcckTop);
			DcckDetail dcckDetail = new DcckDetail();
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i < list.size(); i++) {
				String content = list.get(i).toString();
				ConvertUtils.messageToObj(content, dcckDetail);
				
				sb.append(dcckDetail.getmTradeFlow()).append("|")
				  .append(dcckDetail.getbTradeFlow()).append("|")
				  .append(dcckDetail.getUserNo()).append("|")
				  .append(dcckDetail.getVirtualSubNo()).append("|")
				  .append(dcckDetail.getTradeCode()).append("|")
				  .append(dcckDetail.getTradeDirection()).append("|")
				  .append(dcckDetail.getAmount()).append("|")
				  .append(dcckDetail.getSpare());
				
				messages.add(sb.toString());
				
				sb.delete(0, sb.length());
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new PlatformException(PlatformErrorCode.CHANNEL_FILE_PARSE_ERROR,  PlatformErrorCode.CHANNEL_FILE_PARSE_ERROR.getDefaultMessage());
		}
		return messages;
	}
	
	/**
	 * 由招商银行交易状态转换为TPP2.0交易状态规则
	 * @param tradeCode of CMB
	 * @return tradeCode of TPP2.0
	 */
	public String convertTradeCode(String tradeCode) {
		
		// 如果第三方交易成功，则返回 TRADE_STATE_SUCCESS(成功)，否则返回TRADE_STATE_FAILER(失败)
		if (Constants.CmbStatus.CMBMB99.getCode().equals(tradeCode) || Constants.CmbStatus.CMBUS01.getCode().equals(tradeCode)) {
			return Constants.CmbConstants.TRADE_STATE_SUCCESS.getCode();
		} else {
			return Constants.CmbConstants.TRADE_STATE_FAILER.getCode();
		}
	}
	
}
