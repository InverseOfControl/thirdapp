package com.zendaimoney.trust.channel.action.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.trust.channel.action.BatchActionAbstract;
import com.zendaimoney.trust.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.trust.channel.entity.batch.UsrbTop;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.vo.req.AttachmentReqVo;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.pub.vo.req.UsrbDetailReq;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.ConvertUtils;
import com.zendaimoney.trust.channel.util.LogPrn;
import com.zendaimoney.trust.channel.util.ThirdPPCacheContainer;
import com.zendaimoney.trust.channel.util.FileUtils;

/**
 * 用户开户处理Action-批量
 * @author mencius
 *
 */
@SuppressWarnings("rawtypes")
@ReqActionAnnotation(thirdType = ThirdType.CMBPAY, cmbBizType = TrustBizType.USRB, cmbCategory = TrustCategory.BATCH_TRADE)
@Component("com.zendaimoney.trust.channel.action.user.UsrbBatchAction")
public class UsrbBatchAction extends BatchActionAbstract {

	private static final LogPrn logger = new LogPrn(UsrbBatchAction.class);
	
	/**
	 * 报文内容数据转换为第三方银行标准
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List preProcess(TrustBizReqVo trustBizReqVo) throws PlatformException {
		
		List<String> messages = new ArrayList<String>();
		
		List requestData = ((AttachmentReqVo<List>)trustBizReqVo).getAttachment();
		
		// 报文首行对象
		UsrbTop usrbTop = new UsrbTop(requestData.size());
		// 添加报文首行内容
		messages.add(ConvertUtils.objToMessage(usrbTop));
		
		UsrbDetailReq usrbDetailReq = null;
		// 遍历数据集
		for (int i = 0; i < requestData.size(); i++) {
			// 转换为用户绑卡对象
			usrbDetailReq = (UsrbDetailReq)requestData.get(i);
			
			// TPP银行账户类型映射到第三方银行账户类型
			usrbDetailReq.setBankCardType(ThirdPPCacheContainer.tppBankCardTypeToThirdBankCardTypeMap
					.get(trustBizReqVo.getThirdType().getCode() + usrbDetailReq.getBankCardType()));
			
			// 将单条记录转换为每行报文
			messages.add(ConvertUtils.objToMessage(usrbDetailReq));
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
			UsrbTop usrbTop = new UsrbTop(list.size() - 1);
			// 由报文转换位首行对象
			ConvertUtils.messageToObj(list.get(0).toString(), usrbTop);
			// 明细对象载体
			UsrbDetailReq record = new UsrbDetailReq();
			StringBuffer buffer = new StringBuffer();
			for (int i = 1; i < list.size(); i++) {
				
				// 由招商银行资金托管的明细报文转换为TPP用户绑卡明细对象
				ConvertUtils.messageToObj(list.get(i).toString(), record);
				
				// 用户号 + "|" + 开户账户(第三方托管虚拟账户) + "|" + 交易状态码 + "|" + 交易描述
				buffer.append(record.getUserNo()).append("|")
					.append(record.getVirtualSubNo()).append("|")
					.append(record.getBankCardType()).append("|")
					.append(record.getBankCardNo()).append("|")
					.append(record.getUserName()).append("|")
					.append(record.getBankName()).append("|")
					.append(record.getBankSubName()).append("|")
					.append(record.getMobile()).append("|")
					.append(convertTradeCode(record.getRetCode())).append("|")
					.append(record.getMsg()).append("|")
					.append(record.getSpare());
				
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
		
		// 如果第三方交易成功，则返回 TRADE_STATE_SUCCESS(成功)，否则返回TRADE_STATE_FAILER(失败)
		if (Constants.CmbStatus.CMBMB99.getCode().equals(tradeCode) || Constants.CmbStatus.CMBUS01.getCode().equals(tradeCode)) {
			return Constants.CmbConstants.TRADE_STATE_SUCCESS.getCode();
		} else {
			return Constants.CmbConstants.TRADE_STATE_FAILER.getCode();
		}
	}
	

}
