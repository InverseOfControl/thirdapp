package com.zendaimoney.thirdpp.channel.dto.resp.allinpay.sign;

import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.dto.resp.allinpay.sign.message.RespBody;
import com.zendaimoney.thirdpp.channel.dto.resp.allinpay.sign.message.RespHeader;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "AIPG")
public class SignResp extends RespDto {
    /**
     * 返回头
     */
    @XmlElement(name = "INFO")
    private RespHeader header;

    /**
     * 返回体
     */
    @XmlElement(name = "FAGRCRET")
    private RespBody body;

    public RespHeader getHeader() {
        return header;
    }

    public void setHeader(RespHeader header) {
        this.header = header;
    }

    public RespBody getBody() {
        return body;
    }

    public void setBody(RespBody body) {
        this.body = body;
    }

    @Override
    public SignResp decode(String respMsg) throws PlatformException {
        JaxbBinder binder = new JaxbBinder(this.getClass());

        return binder.fromXml(respMsg);
    }
}
