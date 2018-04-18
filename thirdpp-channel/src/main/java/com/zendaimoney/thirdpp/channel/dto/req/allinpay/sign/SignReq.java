package com.zendaimoney.thirdpp.channel.dto.req.allinpay.sign;

import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.SignReqVo;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;

import javax.xml.bind.annotation.*;

/**
 * 协议支付签约报文
 * 
 * @author wulj
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "AIPG")
public class SignReq extends ReqDto {

    @XmlElement(name = "INFO")
    private ReqHeader header;

    @XmlElement(name = "FAGRC")
    private ReqBody body;

    public SignReq() {
        super();
    }
    
    public SignReq(BizReqVo bizReqVo){
        SignReqVo signReqVo = (SignReqVo)bizReqVo;
        String merchantType = ThirdPPCacheContainer.sysInfoCategoryMap.get(bizReqVo.getInfoCategoryCode()).getMerchantType();

        // 报文头
        header = new ReqHeader();
        // 处理级别 0-9  0优先级最低，默认为5
        header.setLevel(ConfigUtil.getInstance().getAllinpayConfig().getLevel());
        // 商户代码
        header.setMerchantId(ThirdPPCacheContainer.sysThirdChannelInfoMap.get(bizReqVo.getThirdType().getCode() + merchantType).getMerchantNo());
        // 用户名需要根据不同的第三方支付通道编码和商户号类型进行判断
        header.setUserName(ThirdPPCacheContainer.sysThirdChannelInfoMap.get(bizReqVo.getThirdType().getCode() + merchantType).getUserName());
        // 密码需要根据不同的第三方支付通道编码和商户号类型进行判断
        header.setUserPass(ThirdPPCacheContainer.sysThirdChannelInfoMap.get(bizReqVo.getThirdType().getCode() + merchantType).getUserPwd());
        // 交易批次号
        header.setReqSn(signReqVo.getTradeFlow());
        // 版本号默认为04，数据格式默认为2-xml

        // 报文体
        body = new ReqBody();
        // 商户号需要根据不同的业务系统来判断
        body.setMerchantId(ThirdPPCacheContainer.sysThirdChannelInfoMap.get(bizReqVo.getThirdType().getCode() + merchantType).getMerchantNo());
        // 验证码
        body.setVerCode(signReqVo.getVerCode());
        // 原流水号
        body.setSrcReqSn(signReqVo.getSrcReqSn());

        // 其他
        this.setBizSys(bizReqVo.getBizSys());
        this.setBizType(bizReqVo.getBizType());
        this.setThirdType(bizReqVo.getThirdType());
        this.setChannelReqId(bizReqVo.getChannelReqId());
    }

    public ReqHeader getHeader() {
        return header;
    }

    public void setHeader(ReqHeader header) {
        this.header = header;
    }

    public ReqBody getBody() {
        return body;
    }

    public void setBody(ReqBody body) {
        this.body = body;
    }

    @Override
    public String encode() {
        JaxbBinder binder = new JaxbBinder(this.getClass());
        String xml = binder.toXml(this, "GBK");

        return xml.replaceFirst(JaxbBinder.REPLACE_STANDALONE, "");
    }
}
