package com.zendaimoney.thirdpp.channel.util.unspay;

import com.zendaimoney.thirdpp.channel.dto.req.unspay.collect.query.CollectQueryReq;
import com.zendaimoney.thirdpp.channel.dto.req.unspay.collect.trade.CollectReq;
import com.zendaimoney.thirdpp.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 00233197 on 2017/6/13.
 */
public class UnspayUtil {


    public static CollectReq signMsg(CollectReq req,BizReqVo bizReqVo,SysThirdChannelInfo channelInfo)
            throws Exception {

        Map<String, String> reqParam = new HashMap<>();
        reqParam.put("orderId",req.getBatchNo());
        reqParam.put("accName", req.getDataContent().getAccName());
        reqParam.put("accNo", req.getDataContent().getAccNo());
        reqParam.put("idCardNo", req.getDataContent().getIdCardNo());
        reqParam.put("phoneNo", req.getDataContent().getPhoneNo());
        reqParam.put("amount", req.getDataContent().getAmount());
        reqParam.put("purpose", req.getDataContent().getPurpose());

        String data = FileEncAndDec.buildFileContent(reqParam, channelInfo.getCertPwd());

        String text = "accountId=" + req.getAccountId() + "&batchNo=" + req.getBatchNo()
                + "&totalNum=" + req.getTotalNum() + "&totalAmount=" + req.getTotalAmount()
                + "&reqTime=" + req.getReqTime() + "&data=" + data + "&version="
                + req.getVersion() + "&key=" + channelInfo.getCertPwd();

        String mac = Md5Encrypt.md5(text);
        req.setData(data);
        req.setMac(mac);

        return req;
    }

    public static CollectQueryReq signQueryMsg(CollectQueryReq req, BizReqVo bizReqVo, SysThirdChannelInfo channelInfo)
            throws Exception {

        String text = "accountId=" + req.getAccountId() + "&batchNo=" + req.getBatchNo() + "&orderId=" + req.getOrderId()
                + "&reqTime=" + req.getReqTime() + "&version=" + req.getVersion() + "&key="
                + channelInfo.getCertPwd();

        String mac = Md5Encrypt.md5(text);
        req.setMac(mac);
        return req;
    }


    @SuppressWarnings("unchecked")
    public static Map<String, String> xmlToMap(String xmlValue) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new InputSource(new StringReader(xmlValue)));
        Element root = document.getRootElement();
        // 获取所有子元素
        List<Element> childList = root.elements();
        Map<String, String> messageMap = new HashMap<String, String>();
        for (Element element : childList) {
            messageMap.put(element.getName(), element.getTextTrim());
        }

        return messageMap;
    }
}
