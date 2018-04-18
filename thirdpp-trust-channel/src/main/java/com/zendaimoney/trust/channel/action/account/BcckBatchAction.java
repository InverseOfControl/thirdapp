package com.zendaimoney.trust.channel.action.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.trust.channel.action.BatchActionAbstract;
import com.zendaimoney.trust.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.trust.channel.entity.batch.BcckDetail;
import com.zendaimoney.trust.channel.entity.batch.BcckTop;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.vo.req.BcckReq;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.util.BeanUtils;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.ConvertUtils;
import com.zendaimoney.trust.channel.util.FileUtils;
import com.zendaimoney.trust.channel.util.LogPrn;

@ReqActionAnnotation(thirdType = ThirdType.CMBPAY, cmbCategory = TrustCategory.BATCH_TRADE, cmbBizType = TrustBizType.BCCK)
@Component(value = "com.zendaimoney.trust.channel.action.account.BcckBatchAction")
public class BcckBatchAction extends BatchActionAbstract {

	private static final LogPrn logger = new LogPrn(BcckBatchAction.class);
	
	/**
	 * 由于Dcck 请求不需要向招商银行发送文件，所以preProcess writeFile 无需做任何实现
	 * 但是需要 提前调用 pull 方法
	 */
	@Override
	protected List<String> preProcess(TrustBizReqVo trustBizReqVo) throws PlatformException {
		List<String> messages = new ArrayList<String>();
		
		BcckReq bcckReq = (BcckReq)trustBizReqVo;
		BcckTop bcckTop = new BcckTop();
		BeanUtils.copyNotNullProperties(bcckReq, bcckTop);
		messages.add(ConvertUtils.objToMessage(bcckTop));
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
		BcckTop dcckTop = new BcckTop();
		try {
			ConvertUtils.messageToObj(firstLine, dcckTop);
			BcckDetail bcckDetail = new BcckDetail();
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i < list.size(); i++) {
				String content = list.get(i).toString();
				ConvertUtils.messageToObj(content, bcckDetail);
				
				sb.append(bcckDetail.getUserNo()).append("|")
				  .append(bcckDetail.getVirtualSubNo()).append("|")
				  .append(bcckDetail.getAvaliableAmount()).append("|")
				  .append(bcckDetail.getFreezeAmount()).append("|")
				  .append(bcckDetail.getInAmount()).append("|")
				  .append(bcckDetail.getNeedSettleAmount()).append("|")
				  .append(bcckDetail.getAdvanceAmount()).append("|")
				  .append(bcckDetail.getSpare());
				
				messages.add(sb.toString());
				
				sb.delete(0, sb.length());
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new PlatformException(PlatformErrorCode.CHANNEL_FILE_PARSE_ERROR,  PlatformErrorCode.CHANNEL_FILE_PARSE_ERROR.getDefaultMessage());
		}
		return messages;
	}
}
