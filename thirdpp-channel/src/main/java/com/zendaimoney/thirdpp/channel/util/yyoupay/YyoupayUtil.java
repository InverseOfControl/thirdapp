package com.zendaimoney.thirdpp.channel.util.yyoupay;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zendaimoney.thirdpp.channel.dto.req.yyoupay.collect.query.RepayQueryReq;
import com.zendaimoney.thirdpp.channel.dto.req.yyoupay.collect.repay.RepayReq;
import com.zendaimoney.thirdpp.channel.dto.req.yyoupay.pay.trade.PayReq;
import com.zendaimoney.thirdpp.channel.dto.resp.yyoupay.collect.query.RepayQueryResp;
import com.zendaimoney.thirdpp.channel.dto.resp.yyoupay.collect.repay.RepayResp;
import com.zendaimoney.thirdpp.channel.dto.resp.yyoupay.pay.trade.PayResp;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.util.Constants;
import com.zendaimoney.thirdpp.channel.util.EscapeSpecialCharacteUtil;
import com.zendaimoney.thirdpp.channel.util.MD5DigestUtils;

/**
 * 用友通道处理类
 * @author mencius
 *
 */
public class YyoupayUtil {

	/**
	 * 还款请求MD5加密
	 * @param req
	 * @return
	 */
	public static String repaySign(RepayReq req, Map<String,String> Messagemap) {
		
		//组装报文
		String message = new String();
		message = toString(message, req.getDealType());
		message = toString(message, req.getSubDealType());
		message = toString(message, req.getTpCode()); //证大商户号
		message = toString(message, req.getTpUserCode()); //信贷端用户ID
		message = toString(message, req.getTpUserCode2()); //信托ID
		message = toString(message, req.getAmount());
		message = toString(message, "002");
		message = toString(message, req.getDate()); //日期
		message = toString(message, req.getFlow()); //发送第三方流水号
		message = toString(message, req.getNote1()); //备注一
		message = toString(message, req.getNote2()); //备注二
		
		//对报文进行加密
		String md5 = message.concat("|" + Constants.MD5_ENCRYPT);
		md5 = MD5DigestUtils.md5(md5);
		String md5Message = message.concat("|").concat(md5);
		Messagemap.put("requestinfo", md5Message);
		return md5Message;
	}
	
	/**
	 * 用友还款验证签名
	 * @param responseInfo
	 * @return
	 */
	public static void checkRepayRespSign(String responseInfo, RepayResp repayResp) {
		
//		交易类型|交易子类型|tpCode|P2P系统ID |CJZF系统id|返回码|附加信息|note1|note2|MD5密钥
		try {
			String message = new String(responseInfo.substring(0, responseInfo.length()-33).getBytes("UTF-8"));
			String md5 = responseInfo.substring(responseInfo.length()-32, responseInfo.length());
			
			String md5message = MD5DigestUtils.md5(message.concat("|" + Constants.MD5_ENCRYPT));		
			//解析报文数据
			String[] messagelist = responseInfo.split("\\|");
			//验证前后密文是否一致
			if(md5.equals(md5message) && messagelist.length == 10){
				
				repayResp.setSubDealType(EscapeSpecialCharacteUtil.convert(messagelist[1], EscapeSpecialCharacteUtil.escapeCharMap));
				repayResp.setTppCode(EscapeSpecialCharacteUtil.convert(messagelist[2], EscapeSpecialCharacteUtil.escapeCharMap));
				repayResp.setTpUserCode(EscapeSpecialCharacteUtil.convert(messagelist[3], EscapeSpecialCharacteUtil.escapeCharMap));
				repayResp.setCjzfId(EscapeSpecialCharacteUtil.convert(messagelist[4], EscapeSpecialCharacteUtil.escapeCharMap));
				repayResp.setRetCode(EscapeSpecialCharacteUtil.convert(messagelist[5], EscapeSpecialCharacteUtil.escapeCharMap));
				repayResp.setRetMessage(EscapeSpecialCharacteUtil.convert(messagelist[6], EscapeSpecialCharacteUtil.escapeCharMap));
				repayResp.setNote1(EscapeSpecialCharacteUtil.convert(messagelist[7], EscapeSpecialCharacteUtil.escapeCharMap));
				repayResp.setNote2(EscapeSpecialCharacteUtil.convert(messagelist[8], EscapeSpecialCharacteUtil.escapeCharMap));
			} else {
				repayResp.setRetMessage(Constants.YyoupayConstants.YYOU_REPAY_SIGN_CHECK_ERROR_CODE.getDesc());
			}
		} catch (Exception e) {
			
			throw new PlatformException(PlatformErrorCode.CHANNEL_AUTH_THIRD_ERROR, PlatformErrorCode.CHANNEL_AUTH_THIRD_ERROR.getDefaultMessage());
		}
	}
	
	/**
	 * 还款交易查询请求MD5加密
	 * @param req
	 * @return
	 */
	public static String repayQuerySign(RepayQueryReq req, Map<String,String> Messagemap) {
		
//		QUERY_BASE|002|3695221|4000101|0000001|000||
		//组装报文
		String message = new String();
		message = toString(message, req.getDealType());
		message = toString(message, req.getSubDealType());
		message = toString(message, req.getTpUserCode()); //信贷端用户ID
		message = toString(message, req.getTpCode()); //证大商户号
		message = toString(message, req.getCjzfUserCode()); //CJZF系统id（可以不传）	
		message = toString(message, "002");
		message = toString(message, req.getFlow()); //发送第三方流水号
		message = toString(message, req.getNote1()); //备注一
		message = toString(message, req.getNote2()); //备注二
		
		//对报文进行加密
		String md5 = message.concat("|" + Constants.MD5_ENCRYPT);
		md5 = MD5DigestUtils.md5(md5);
		String md5Message = message.concat("|").concat(md5);
		Messagemap.put("requestinfo", md5Message);
		return md5Message;
	}
	
	/**
	 * 用友还款验证签名
	 * @param responseInfo
	 * @return
	 */
	public static void checkQueryRespSign(String responseInfo, RepayQueryResp repayResp) {
		
//		交易类型|交易子类型|返回码|P2P系统用户ID|CJZF系统用户ID|P2P流水ID|CJZF流水日期|状态(该笔交易)|交易类型|交易金额|发生方|接受方|失败返回信息|MD5密钥
		try {
			String message = new String(responseInfo.substring(0, responseInfo.length()-33).getBytes("UTF-8"));
			String md5 = responseInfo.substring(responseInfo.length()-32, responseInfo.length());
			
			String md5message = MD5DigestUtils.md5(message.concat("|" + Constants.MD5_ENCRYPT));		
			//解析报文数据
			String[] messagelist = responseInfo.split("\\|");
			
			
			//验证前后密文是否一致
			if(md5.equals(md5message) && messagelist.length == 15){
				
				repayResp.setSubDealType(EscapeSpecialCharacteUtil.convert(messagelist[1], EscapeSpecialCharacteUtil.escapeCharMap));
				repayResp.setRetCode(EscapeSpecialCharacteUtil.convert(messagelist[2], EscapeSpecialCharacteUtil.escapeCharMap));
				repayResp.setTppUserCode(EscapeSpecialCharacteUtil.convert(messagelist[3], EscapeSpecialCharacteUtil.escapeCharMap));
				repayResp.setCjzfId(EscapeSpecialCharacteUtil.convert(messagelist[4], EscapeSpecialCharacteUtil.escapeCharMap));
				repayResp.setFlow(EscapeSpecialCharacteUtil.convert(messagelist[5], EscapeSpecialCharacteUtil.escapeCharMap));
				repayResp.setTradeDate(EscapeSpecialCharacteUtil.convert(messagelist[6], EscapeSpecialCharacteUtil.escapeCharMap));
				repayResp.setState(EscapeSpecialCharacteUtil.convert(messagelist[7], EscapeSpecialCharacteUtil.escapeCharMap));
				repayResp.setAmount(EscapeSpecialCharacteUtil.convert(messagelist[9], EscapeSpecialCharacteUtil.escapeCharMap));
				repayResp.setPayer(EscapeSpecialCharacteUtil.convert(messagelist[10], EscapeSpecialCharacteUtil.escapeCharMap));
				repayResp.setReceiver(EscapeSpecialCharacteUtil.convert(messagelist[11], EscapeSpecialCharacteUtil.escapeCharMap));
				repayResp.setRetMessage(EscapeSpecialCharacteUtil.convert(messagelist[12], EscapeSpecialCharacteUtil.escapeCharMap));
			} else {
				repayResp.setRetMessage(Constants.YyoupayConstants.YYOU_REPAY_SIGN_CHECK_ERROR_CODE.getDesc());
			}
		} catch (Exception e) {
			
			throw new PlatformException(PlatformErrorCode.CHANNEL_AUTH_THIRD_ERROR, PlatformErrorCode.CHANNEL_AUTH_THIRD_ERROR.getDefaultMessage());
		}
	}
	
	/**
	 * 融资请求MD5加密
	 * @param req
	 * @return
	 */
	public static String paySign(PayReq req, Map<String,String> Messagemap) {
		
		// eg:BID_PAYORDER|000|4000101|tpuser2|tpuser1|500000|000|20141230203400|2014102720231000|000|5||
		
		// 组装报文
		String message = new String();
		message = toString(message, req.getDealType());
		message = toString(message, req.getSubDealType());
		message = toString(message, req.getTpCode()); //证大商户号
		message = toString(message, req.getTpUserCode()); //出资方用户ID
		message = toString(message, req.getTpUserCode2()); //融资ID
		message = toString(message, req.getAmount());
		message = toString(message, "000");
		message = toString(message, req.getDate()); //日期
		message = toString(message, req.getFlow()); //发送第三方流水号
		message = toString(message, req.getAccountType());//账户类型
		message = toString(message, req.getIncome());//手续费
		message = toString(message, req.getNote1()); //备注一
		message = toString(message, req.getNote2()); //备注二
		
		//对报文进行加密
		String md5 = message.concat("|" + Constants.MD5_ENCRYPT);
		md5 = MD5DigestUtils.md5(md5);
		String md5Message = message.concat("|").concat(md5);
		Messagemap.put("requestinfo", md5Message);
		return md5Message;
	}
	
	/**
	 * 用友融资验证签名
	 * @param responseInfo
	 * @return
	 */
	public static void checkPayRespSign(String responseInfo, PayResp payResp) {
//		交易类型|交易子类型|tp流水|我方订单号|tp放款人ID|tp收款人id|金额|日期|tpId|返回码|附加信息|note1|note2
		try {
			String message = new String(responseInfo.substring(0, responseInfo.length()-33).getBytes("UTF-8"));
			String md5 = responseInfo.substring(responseInfo.length()-32, responseInfo.length());
			
			String md5message = MD5DigestUtils.md5(message.concat("|" + Constants.MD5_ENCRYPT));		
			//解析报文数据
			String[] messagelist = responseInfo.split("\\|");
			//验证前后密文是否一致
			if(md5.equals(md5message) && messagelist.length == 14){
				payResp.setSubDealType(messagelist[1]);
				payResp.setTransferFlow(messagelist[2]);
				payResp.setFlow(messagelist[3]);
				payResp.setTpUserCode(messagelist[4]);
				payResp.setTpUserCode2(messagelist[5]);
				payResp.setAmount(messagelist[6]);
				payResp.setDate(messagelist[7]);
				payResp.setTpId(messagelist[8]);
				payResp.setRetCode(messagelist[9]);
				payResp.setRetMessage(messagelist[10]);
				payResp.setNote1(messagelist[11]);
				payResp.setNote2(messagelist[12]);
			} else {
				payResp.setRetCode(Constants.YyoupayConstants.YYOU_PAY_SIGN_CHECK_ERROR_CODE.getCode());
				payResp.setRetMessage(Constants.YyoupayConstants.YYOU_PAY_SIGN_CHECK_ERROR_CODE.getDesc());
			}
		} catch (Exception e) {
		}
		
	}
	
	
	/**
	 * 组装待加密字符串
	 * @param str
	 * @param str1
	 * @return
	 */
	public static String toString(String str,String str1){
		if(0==str.length()){
			return str1;
		}if(str1==null){
			str1 = "";
		}
		return str+"|"+str1;
	}
}
