package com.zendaimoney.trust.channel.action.settlement;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.trust.channel.action.BatchActionAbstract;
import com.zendaimoney.trust.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.trust.channel.entity.batch.MrslTop;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.vo.req.MrslDetailReq;
import com.zendaimoney.trust.channel.pub.vo.req.MrslReq;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.util.BeanUtils;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.ConvertUtils;
import com.zendaimoney.trust.channel.util.FileUtils;
import com.zendaimoney.trust.channel.util.LogPrn;

/**
 * MrslReq 对象为入参对象，该对象继承 AttachmentReqVo，其中 attachment 属性 则为 List<MrslDetailReq>
 * 
 * @author 00237071
 *
 */
@ReqActionAnnotation(thirdType = ThirdType.CMBPAY, cmbBizType = TrustBizType.MRSL, cmbCategory = TrustCategory.BATCH_TRADE)
@Component(value = "com.zendaimoney.trust.channel.action.settlement.MrslBatchAction")
public class MrslBatchAction extends BatchActionAbstract {

	private static final LogPrn logger = new LogPrn(MrslBatchAction.class);
	/**
	 * 根据结果集生成文件首行以及文件内容
	 * 
	 */
	@Override
	protected List<String> preProcess(TrustBizReqVo trustBizReqVo) throws PlatformException {
		List<String> message = new ArrayList<String>();

		MrslReq mrslReq = (MrslReq) trustBizReqVo;
		MrslTop mrslTop = new MrslTop();
		// 需要计算的属性由调用方进行业务计算，通道尽量不涉及业务
		BeanUtils.copyNotNullProperties(mrslReq, mrslTop);
		message.add(ConvertUtils.objToMessage(mrslTop));

		List<MrslDetailReq> data = mrslReq.getAttachment();
		for (MrslDetailReq mdr : data) {
			message.add(ConvertUtils.objToMessage(mdr));
		}
		return message;
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void writeFile(List data, String forloadPath, String fileName)
			throws Exception {
		// 创建文件工具类
		FileUtils txtFileUtils = new FileUtils();
		// 将文件写入本地磁盘内
		txtFileUtils.writerTxt(forloadPath, fileName, data,Constants.ENCODE_GBK);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected List<String> parseHandle(TrustBizReqVo trustBizReqVo, String filePath) {
		List<String> newMessages = new ArrayList<String>();
		try {
			FileUtils fileUtils = new FileUtils();
			List lineContents = fileUtils.readTxt(filePath, Constants.ENCODE_GBK);

			MrslTop mrslTop = new MrslTop();
			mrslTop.setTotalCount(lineContents.size() - 1);
			ConvertUtils.messageToObj(lineContents.get(0).toString(), mrslTop);
			
			MrslDetailReq detail = new MrslDetailReq();
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i < lineContents.size(); i++) {
				String eachLine = (String)lineContents.get(i);
				ConvertUtils.messageToObj(eachLine, detail);
				sb.append(detail.getThirdpartyTradeflow()).append("|")
							.append(detail.getUserNo()).append("|")
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
		} else if (StringUtils.isBlank(tradeCode) || Constants.CmbStatus.CMBMBXX.getCode().equals(tradeCode)||Constants.CmbStatus.CMBMB05.getCode().equals(tradeCode)) {
			return Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode();
		} else {
			return Constants.CmbConstants.TRADE_STATE_FAILER.getCode();
		}
	}
}
