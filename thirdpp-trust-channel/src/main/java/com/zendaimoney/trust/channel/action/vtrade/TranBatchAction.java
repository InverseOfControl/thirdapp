package com.zendaimoney.trust.channel.action.vtrade;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.trust.channel.action.BatchActionAbstract;
import com.zendaimoney.trust.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.trust.channel.entity.batch.TranTop;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.vo.req.TranBatchReq;
import com.zendaimoney.trust.channel.pub.vo.req.TranDetailReq;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.util.BeanUtils;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.ConvertUtils;
import com.zendaimoney.trust.channel.util.FileUtils;
import com.zendaimoney.trust.channel.util.LogPrn;

/**
 * 批量转账操作- 虚拟户交易
 * @author 00233197
 *
 */
@SuppressWarnings("rawtypes")
@ReqActionAnnotation(thirdType = ThirdType.CMBPAY, cmbBizType = {TrustBizType.INVS,TrustBizType.REPY,TrustBizType.CASM,TrustBizType.MFEE,TrustBizType.MPAY,TrustBizType.MADV,
		TrustBizType.GINN,TrustBizType.GOUT,TrustBizType.OADV}, cmbCategory = TrustCategory.BATCH_TRADE)
@Component("com.zendaimoney.trust.channel.action.vtrade.TranBatchAction")
public class TranBatchAction extends BatchActionAbstract {

	private static final LogPrn logger = new LogPrn(TranBatchAction.class);
	
	/**
	 * 报文内容数据转换为第三方银行标准
	 */
	@Override
	protected List preProcess(TrustBizReqVo trustBizReqVo) throws PlatformException {
		
		List<String> messages = new ArrayList<String>();
		
		TranBatchReq tranBatchReq = (TranBatchReq)trustBizReqVo;
		
		// 报文首行对象
		TranTop tranTop = new TranTop();
		
		//映射交易类别
		//tranBatchReq.setTradeType(ThirdPPCacheContainer.tppTradeTypeTothirdTradeTypeMap.get(trustBizReqVo.getThirdType().getCode()+trustBizReqVo.getTrustBizType().getCode()));
		
		BeanUtils.copyNotNullProperties(tranBatchReq, tranTop);
		// 添加报文首行内容
		messages.add(ConvertUtils.objToMessage(tranTop));
		
		
		TranDetailReq tranDetailReq = null;
		List<TranDetailReq> requestData = tranBatchReq.getAttachment();
		// 遍历数据集
		for (int i = 0; i < requestData.size(); i++) {
			// 转换为明细对象
			tranDetailReq = (TranDetailReq)requestData.get(i);
			
			//映射交易类别
			//tranDetailReq.setTradeType(ThirdPPCacheContainer.tppTradeTypeTothirdTradeTypeMap.get(trustBizReqVo.getThirdType().getCode()+trustBizReqVo.getTrustBizType().getCode()));
			// 将单条记录转换为每行报文
			messages.add(ConvertUtils.objToMessage(tranDetailReq));
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
			TranTop tranTop = new TranTop();
			tranTop.setSum(list.size()-1);
			// 由报文转换位首行对象
			ConvertUtils.messageToObj(list.get(0).toString(), tranTop);
			// 明细对象载体
			TranDetailReq record = new TranDetailReq();
			StringBuffer buffer = new StringBuffer();
			for (int i = 1; i < list.size(); i++) {
				
				// 由招商银行资金托管的明细报文转换为TPP用户开户明细对象
				ConvertUtils.messageToObj(list.get(i).toString(), record);
				
				// 商户交易流水号 + "|" + 银行流水号+ "|" + 用户号+ "|" + 虚拟子账号+ "|" + 交易金额+ "|" + 交易类别+ "|" + 交易摘要+ "|" 
				// + 涉及本金+ "|" + 关联信息 + "|" + 交易状态码 + "|" + 银行账务日期+ "|"  + 交易描述  +"|"+ 备用1+ "|"+ 备用2+ "|"+ 备用3 
				buffer.append(record.getTradeFlow()).append("|")
					.append(record.getBankTradeFlow()).append("|")
					.append(record.getUserNo()).append("|")
					.append(record.getVirtualSubNo()).append("|")
					.append(record.getAmount()).append("|")
					.append(record.getTradeType()).append("|")
					.append(record.getSummary()).append("|")
					.append(record.getPrincipal()).append("|")
					.append(record.getAssociatedInfo()).append("|")
					.append(convertTradeCode(record.getRetCode())).append("|")
					.append(record.getBankAccountDate()).append("|")
					.append(record.getMsg()).append("|")
					.append(record.getSpare1()).append("|")
					.append(record.getSpare2()).append("|")
					.append(record.getSpare3());
				
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
		if (Constants.CmbStatus.CMBMB99.getCode().equals(tradeCode)) {
			return Constants.CmbConstants.TRADE_STATE_SUCCESS.getCode();
		} else if (StringUtils.isBlank(tradeCode) || Constants.CmbStatus.CMBMBXX.getCode().equals(tradeCode)||Constants.CmbStatus.CMBMB05.getCode().equals(tradeCode)) {
			return Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode();
		} else {
			return Constants.CmbConstants.TRADE_STATE_FAILER.getCode();
		}
	}
	

}
