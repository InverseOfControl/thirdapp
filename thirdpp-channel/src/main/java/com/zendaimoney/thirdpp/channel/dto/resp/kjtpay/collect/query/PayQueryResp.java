package com.zendaimoney.thirdpp.channel.dto.resp.kjtpay.collect.query;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.util.JSONHelper;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.kjtpay.KjtpayUtil;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;

/**
 * 代收业务请求数据传输对象
 * 
 * @author 00231257
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PayQueryResp extends RespDto {

	/**
	 * 成功标识
	 */
	@XmlElement(name = "is_success")
	private String is_success;
	
	/**
	 * 参数编码字符集
	 */
	@XmlElement(name = "_input_charset ")
	private String _input_charset;
	
	/**
	 * 是否跳转收银台
	 */
	@XmlElement(name = "isForwardCashier")
	private String isForwardCashier;
	
	/**
	 * 受理状态
	 */
	@XmlElement(name = "result")
	private String result;
	
	/**
	 * 备注
	 */
	@XmlElement(name = "memo")
	private String memo;
	
	/**
	 * 商户网站唯一订单号
	 */
	@XmlElement(name = "outer_trade_no")
	private String outer_trade_no;
	
	/**
	 * 返回错误码
	 */
	@XmlElement(name = "error_code")
	private String error_code;
	
	/**
	 * 返回错误原因
	 */
	@XmlElement(name = "error_message")
	private String error_message;
	
	/**
	 * 快捷通交易号
	 */
	@XmlElement(name = "inner_trade_no")
	private String inner_trade_no;
	
	/**
	 * 交易列表
	 */
	@XmlElement(name = "tradeList")
	private String tradeList;
	
	
	/**
	 * 返回XML报文转成CollectResp。
	 */
	@Override
	public PayQueryResp decode(String respMsg) {

		PayQueryResp dto = new PayQueryResp();
		Map<String, String> valueMap = KjtpayUtil.getResponseMap(respMsg, ChannelCategory.QUERY);
		
		dto = (PayQueryResp) JSONHelper.json2Object(JSONHelper.map2json(valueMap), dto.getClass());
		
		return dto;
	}


	public String getIs_success() {
		return is_success;
	}


	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}


	public String get_input_charset() {
		return _input_charset;
	}


	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
	}


	public String getIsForwardCashier() {
		return isForwardCashier;
	}


	public void setIsForwardCashier(String isForwardCashier) {
		this.isForwardCashier = isForwardCashier;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getMemo() {
		return memo;
	}


	public void setMemo(String memo) {
		this.memo = memo;
	}


	public String getOuter_trade_no() {
		return outer_trade_no;
	}


	public void setOuter_trade_no(String outer_trade_no) {
		this.outer_trade_no = outer_trade_no;
	}


	public String getInner_trade_no() {
		return inner_trade_no;
	}


	public void setInner_trade_no(String inner_trade_no) {
		this.inner_trade_no = inner_trade_no;
	}


	public String getTradeList() {
		return tradeList;
	}


	public void setTradeList(String tradeList) {
		this.tradeList = tradeList;
	}


	public String getError_code() {
		return error_code;
	}


	public void setError_code(String error_code) {
		this.error_code = error_code;
	}


	public String getError_message() {
		return error_message;
	}


	public void setError_message(String error_message) {
		this.error_message = error_message;
	}


	
}
