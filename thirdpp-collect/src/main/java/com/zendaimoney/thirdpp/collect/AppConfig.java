package com.zendaimoney.thirdpp.collect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by YM20051 on 2017/7/4.
 */
@Component
public class AppConfig {

    @Value("${system.no}")
    private String sysNo;

    @Value("${system.info.category}")
    private String infoCategoryCode;

    @Value("${system.pay.sys.no}")
    private String paySysNo;

    @Value("${system.excel.src}")
    private String excelSrc;

    @Value("${system.excel.dest}")
    private String excelDest;

    @Value("${system.need.split}")
    private int needSplit;

    private Map<String, String> headerMap = new LinkedHashMap<>();
    private Map<String, String> bankMap = new HashMap<>();

    @PostConstruct
    public void init(){
        // 初始化 excel 表头
        headerMap.put("payerName", "借款人姓名");
        headerMap.put("payerId", "借款人身份证号码");
        headerMap.put("payerBankCode", "银行名称");
        headerMap.put("payerBankCardNo", "银行卡号");
        headerMap.put("amount", "报盘金额(元)");

        // 初始化 银行信息
        bankMap.put("中国工商银行", "00080003");
        bankMap.put("中国建设银行", "00080005");
        bankMap.put("招商银行", "00080002");
        bankMap.put("中国农业银行", "00080004");
        bankMap.put("中国银行", "00080001");
        bankMap.put("平安银行", "00080014");
        bankMap.put("中国光大银行", "00080013");
        bankMap.put("广东发展银行", "00080029");
        bankMap.put("杭州银行", "00080035");
        bankMap.put("华夏银行", "00080010");
        bankMap.put("交通银行", "00080006");
        bankMap.put("中国民生银行", "00080009");
        bankMap.put("上海浦东发展银行", "00080168");
        bankMap.put("深圳发展银行", "00080007");
        bankMap.put("兴业银行", "00080011");
        bankMap.put("中国邮政储蓄银行", "00080016");
        bankMap.put("中信银行", "00080012");

    }


    public String getSysNo() {
        return sysNo;
    }

    public void setSysNo(String sysNo) {
        this.sysNo = sysNo;
    }

    public String getInfoCategoryCode() {
        return infoCategoryCode;
    }

    public void setInfoCategoryCode(String infoCategoryCode) {
        this.infoCategoryCode = infoCategoryCode;
    }

    public String getPaySysNo() {
        return paySysNo;
    }

    public void setPaySysNo(String paySysNo) {
        this.paySysNo = paySysNo;
    }

    public String getExcelSrc() {
        return excelSrc;
    }

    public void setExcelSrc(String excelSrc) {
        this.excelSrc = excelSrc;
    }

    public String getExcelDest() {
        return excelDest;
    }

    public void setExcelDest(String excelDest) {
        this.excelDest = excelDest;
    }

    public int getNeedSplit() {
        return needSplit;
    }

    public void setNeedSplit(int needSplit) {
        this.needSplit = needSplit;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public Map<String, String> getBankMap() {
        return bankMap;
    }

    public void setBankMap(Map<String, String> bankMap) {
        this.bankMap = bankMap;
    }

    public static void main(String[] args) {
        System.out.println(String.format("%04d", 1));
    }
}
