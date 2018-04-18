package com.zendaimoney.trust.channel.action.query;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.trust.channel.action.BatchActionAbstract;
import com.zendaimoney.trust.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.trust.channel.entity.batch.TrqyDetail;
import com.zendaimoney.trust.channel.entity.batch.TrqyTop;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.ConvertUtils;
import com.zendaimoney.trust.channel.util.FileUtils;
import com.zendaimoney.trust.channel.util.LogPrn;
/**
 * 虚拟户交易查询TRQY - 批量文件
 * @author 00233197
 *
 */
@ReqActionAnnotation(thirdType = ThirdType.CMBPAY, cmbCategory = TrustCategory.BATCH_TRADE, cmbBizType = TrustBizType.TRQY)
@Component(value = "com.zendaimoney.trust.channel.action.query.TrqyBatchAction")
public class TrqyBatchAction extends BatchActionAbstract {

	private static final LogPrn logger = new LogPrn(TrqyBatchAction.class);
	
	@Override
	protected List preProcess(TrustBizReqVo trustBizReqVo) throws PlatformException {
		return null;
	}

	@Override
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
		TrqyTop trqyTop = new TrqyTop();
		try {
			ConvertUtils.messageToObj(firstLine, trqyTop);
			TrqyDetail trqyDetail = new TrqyDetail();
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i < list.size(); i++) {
				String content = list.get(i).toString();
				ConvertUtils.messageToObj(content, trqyDetail);
				
				sb.append(trqyDetail.getmTradeFlow()).append("|")
				  .append(trqyDetail.getbTradeFlow()).append("|")
				  .append(trqyDetail.getBankAccountDate()).append("|")
				  .append(trqyDetail.getAmount()).append("|")
				  .append(trqyDetail.getTradeDirection()).append("|")
				  .append(trqyDetail.getTradeCode()).append("|")
				  .append(trqyDetail.getTradeType()).append("|")
				  .append(trqyDetail.getNote()).append("|")
				  .append(trqyDetail.getRelate()).append("|")
				  .append(trqyDetail.getSpare());
				
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
