package com.zendaimoney.thirdpp.collect.service;

import com.zendaimoney.thirdpp.collect.AppConfig;
import com.zendaimoney.thirdpp.collect.common.Constant;
import com.zendaimoney.thirdpp.collect.utils.Excel;
import com.zendaimoney.thirdpp.collect.utils.FileUtil;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.trade.pub.service.IBrokerTradeService;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.CollectReqVo;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.RequestVo;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by YM20051 on 2017/7/4.
 */

@Service
public class DataServiceImpl implements DataService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private IBrokerTradeService brokerTradeService;

    @Override
    public void parse() {

        // 获取excel 文件
        File srcFile = FileUtil.getFirstFile(appConfig.getExcelSrc());
        if(srcFile == null ){
            return;
        }
        // 获取报盘数据
        List<CollectReqVo> collectReqVos = this.getDataFromExcel(srcFile);

        // 移动文件
        String destFileName = FileUtil.move(srcFile, appConfig.getExcelDest());
        if(CollectionUtils.isEmpty(collectReqVos)){
            // 重命名文件
            FileUtil.rename(destFileName, "parse_fail");
            return;
        }

        // 构造请求参数
        RequestVo requestVo = this.getRequestVo(collectReqVos);

        // 调用 tpp 报盘接口
        Response response = brokerTradeService.asynCollect(requestVo);
        logger.info("处理结果:{}", com.alibaba.fastjson.JSON.toJSONString(response));
        // 重命名 文件
        if(response.getCode().equals(Response.SUCCESS_RESPONSE_CODE)){
            logger.info("处理成功");
            FileUtil.rename(destFileName, response.getFlowId());
        }else{
            logger.info("提交接口失败");
            FileUtil.rename(destFileName, "submit_fail");
        }

    }

    // 从excel中获取数据
    private List<CollectReqVo> getDataFromExcel(File file){

        FileInputStream fis = null;
        List<CollectReqVo> collectReqVos = null;
        try {
            Excel excel;
            fis = new FileInputStream(file);
            if (file.getAbsolutePath().matches("^.+\\.(?i)(xls)$")) {
                excel = new Excel(fis, Excel.ExcelType.XLS);
            } else {
                excel = new Excel(fis, Excel.ExcelType.XLSX);
            }
            collectReqVos = excel.excelToBean(appConfig.getHeaderMap(), CollectReqVo.class);
            fis.close();

        } catch (Exception e) {
            logger.error("读取Excel 失败:", e);

        } finally {

            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error("关闭Excel文件流 失败:", e);
                }
            }
        }
        return collectReqVos;
    }

    // 构造请求对象
    private RequestVo getRequestVo(List<CollectReqVo> collectReqVos){

        RequestVo requestVo = new RequestVo();
        requestVo.setBizSysNo(appConfig.getSysNo());
        requestVo.setBizTypeNo(BizType.BROKER_COLLECT.getCode());
        requestVo.setInfoCategoryCode(appConfig.getInfoCategoryCode());

        String currDateFormat = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");

        int i=0;
        for (CollectReqVo collectReqVo : collectReqVos) {
            // 生成流水号
            String bizFlow = currDateFormat+(String.format("%04d", i++));

            collectReqVo.setPayerIdType(Constant.PAYER_ID_TYPE_IDENTITY);// 付款方证件类型
            collectReqVo.setCurrency(Constant.CURRENCY_CNY);// 币种(0人民币)
            collectReqVo.setBizFlow(bizFlow);// 业务流水号
            collectReqVo.setIsNeedPush(0); //是否需要推送
            collectReqVo.setIsNeedSpilt(appConfig.getNeedSplit()); //是否需要拆单
            collectReqVo.setPaySysNo(appConfig.getPaySysNo());  //通道编码

            // 银行编码
            String bankCode = appConfig.getBankMap().get(collectReqVo.getPayerBankCode());
            if(StringUtils.isEmpty(bankCode)){
                bankCode = Constant.DEFAULT_BANK_CODE;
            }
            collectReqVo.setPayerBankCode(bankCode); // 设置银行编码

            requestVo.getList().add(collectReqVo);
        }

        return requestVo;
    }
}
