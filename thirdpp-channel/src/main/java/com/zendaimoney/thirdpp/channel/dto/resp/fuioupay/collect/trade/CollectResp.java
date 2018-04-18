package com.zendaimoney.thirdpp.channel.dto.resp.fuioupay.collect.trade;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;

/**
 * 代收业务请求数据传输对象
 * 
 * @author 00231257
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "incomeforrsp")
public class CollectResp extends RespDto {
	
	  /**
     * 返回码
     */
    @XmlElement(name = "ret")
    private String ret;
    
    /**
     * 响应码描述
     */
    @XmlElement(name = "memo")
    private String memo;

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
    

	/**
	 * 返回XML报文转成CollectResp。
	 */
	@Override
	public CollectResp decode(String respMsg) throws PlatformException {
		JaxbBinder binder = new JaxbBinder(this.getClass());

		return binder.fromXml(respMsg);
	}

}
