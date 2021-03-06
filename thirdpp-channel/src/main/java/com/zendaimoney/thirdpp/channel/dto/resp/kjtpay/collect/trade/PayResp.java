package com.zendaimoney.thirdpp.channel.dto.resp.kjtpay.collect.trade;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.util.ExceptionUtil;
import com.zendaimoney.thirdpp.channel.util.JSONHelper;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.kjtpay.KjtpayUtil;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;

/**
 * 代付业务响应数据传输对象
 * 
 * @author 00233197
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PayResp extends RespDto {

	/**
	 * 成功标识  表示接口调用是否成功，并不表明业务处理结果。T表示成功，X表示处理中F表示失败。
	 */
	@XmlElement(name = "is_success")
	private String is_success;
	
	
	/**
	 * 参数编码字符集
	 */
	@XmlElement(name = "_input_charset")
	private String _input_charset;
	
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
	
	
	@Override
	public PayResp decode(String respMsg) throws PlatformException {
		
		PayResp dto = new PayResp();
		Map<String, String> valueMap = KjtpayUtil.getResponseMap(respMsg, ChannelCategory.TRADE);
		
		dto = (PayResp) JSONHelper.json2Object(JSONHelper.map2json(valueMap), dto.getClass());
		
		return dto;
	}
	
	/**
	 * 对象转XML报文
	 * 
	 * @return
	 */
	public String encode(String respMsg) throws PlatformException {
		JaxbBinder binder = null;
		String xml = null;
		try {
			binder = new JaxbBinder(this.getClass());
			xml = binder.toXml(this, "UTF-8");
			
		} catch (Exception e) {
			throw new PlatformException(PlatformErrorCode.DTO_ENCODE_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}
		return xml.substring(xml.indexOf("\n") + 1);
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


}
