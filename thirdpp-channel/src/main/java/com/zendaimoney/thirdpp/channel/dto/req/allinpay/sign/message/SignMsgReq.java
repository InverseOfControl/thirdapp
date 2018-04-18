package com.zendaimoney.thirdpp.channel.dto.req.allinpay.sign.message;

import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.SignMsgReqVo;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;
import org.apache.commons.lang.StringUtils;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "AIPG")
public class SignMsgReq extends ReqDto {

    @XmlElement(name = "INFO")
    private ReqHeader header;

    @XmlElement(name = "FAGRA")
    private ReqBody body;

    public SignMsgReq() {
        super();
    }

    /**
     * 构造方法
     * @param vo
     */
    public SignMsgReq(BizReqVo vo) {
        SignMsgReqVo signReqVo = (SignMsgReqVo)vo;
        String merchantType = ThirdPPCacheContainer.sysInfoCategoryMap.get(vo.getInfoCategoryCode()).getMerchantType();

        // 报文头
        header = new ReqHeader();
        // 处理级别 0-9  0优先级最低，默认为5
        header.setLevel(ConfigUtil.getInstance().getAllinpayConfig().getLevel());
        // 商户代码
        header.setMerchantId(ThirdPPCacheContainer.sysThirdChannelInfoMap.get(vo.getThirdType().getCode() + merchantType).getMerchantNo());
        // 用户名需要根据不同的第三方支付通道编码和商户号类型进行判断
        header.setUserName(ThirdPPCacheContainer.sysThirdChannelInfoMap.get(vo.getThirdType().getCode() + merchantType).getUserName());
        // 密码需要根据不同的第三方支付通道编码和商户号类型进行判断
        header.setUserPass(ThirdPPCacheContainer.sysThirdChannelInfoMap.get(vo.getThirdType().getCode() + merchantType).getUserPwd());
        // 交易批次号
        header.setReqSn(signReqVo.getTradeFlow());
        // 版本号默认为04，数据格式默认为2-xml

        // 报文体
        body = new ReqBody();
        // 商户号需要根据不同的业务系统来判断
        body.setMerchantId(ThirdPPCacheContainer.sysThirdChannelInfoMap.get(vo.getThirdType().getCode() + merchantType).getMerchantNo());
        // 将TPP系统银行卡类型转化为相应第三方平台银行卡类型
        body.setBankCode(ThirdPPCacheContainer.tppBankCodeToThirdBankCodeMap.get(signReqVo.getThirdType().getCode() + signReqVo.getBankCode()));
        // 将TPP系统银行卡类型转化为相应第三方平台银行卡类型
        body.setAccountType(ThirdPPCacheContainer.tppBankCardTypeToThirdBankCardTypeMap.get(signReqVo.getThirdType().getCode() + signReqVo.getAccountType()));
        body.setAccountName(signReqVo.getAccountName());
        body.setAccountNo(signReqVo.getAccountNo());
        // 将TPP系统账户属性转化为相应第三方平台账户属性
        body.setAccountProp(ThirdPPCacheContainer.tppUserTypeTothirdUserTypeMap.get(vo.getThirdType().getCode() + signReqVo.getAccountProp()));
        if(StringUtils.isEmpty(body.getAccountProp())){
            body.setAccountProp("0");   // 如果没有第三方账户属性的配置，则默认为"0-私人"(0-私人,1-公司)
        }

        // 将TPP系统证件类型转化为相应第三方平台证件类型
        body.setIdType(ThirdPPCacheContainer.tppIdTypeToThirdIdTypeMap.get(vo.getThirdType().getCode() + signReqVo.getIdType()));
        // 证件号码
        body.setIdNum(signReqVo.getIdNum());
        // 电话号码
        body.setTel(signReqVo.getTel());
        // CVV2
        body.setCvv2(signReqVo.getCvv2());
        // 有效期
        body.setValidDate(signReqVo.getValidDate());
        // 商户保留信息
        body.setMerrem(signReqVo.getMerrem());
        // 备注
        body.setRemark(signReqVo.getRemark());
        // 其他
        this.setBizSys(vo.getBizSys());
        this.setBizType(vo.getBizType());
        this.setThirdType(vo.getThirdType());
        this.setChannelReqId(vo.getChannelReqId());
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
