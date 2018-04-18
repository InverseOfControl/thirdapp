package com.zendaimoney.thirdpp.trade.sei;

import com.alibaba.fastjson.JSONObject;
import com.zendaimoney.thirdpp.common.enums.BizSys;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.trade.business.ChannelSendService;
import com.zendaimoney.thirdpp.trade.dao.RequestDao;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.RequestVo;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.SignMsgReqVo;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.SignReqVo;
import com.zendaimoney.thirdpp.trade.service.*;
import com.zendaimoney.thirdpp.trade.util.LogPrn;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml","classpath:spring-dubbo.xml","classpath:applicationContext-task.xml","classpath:spring-rabbitmq-producter.xml" })
public class AgreementTradeServiceTest {

    // 日志工具类
    private static final LogPrn logger = new LogPrn(AgreementTradeServiceTest.class);

    @Autowired
    public CollectTaskService collectTaskService;

    @Autowired
    public ChannelSendService channelSendService;

    @Autowired
    public SysAppIPSService sysAppIPSService;

    @Autowired
    public SysInfoCategoryAPPSService sysInfoCategoryAPPSService;

    @Autowired
    public SysInfoCategoryService sysInfoCategoryService;

    @Autowired
    public SysAppService sysAppService;

    @Autowired
    public SysInfoCategoryBanksService sysInfoCategoryBanksService;

    @Autowired
    private SignMessageInfoService signMessageInfoService;

    @Autowired
    private SignInfoService signInfoService;

    @Resource(name = "requestDao")
    private RequestDao requestDao;


    @Test
    public void signMessageTest(){
        BrokerTradeServiceImpl impl = new BrokerTradeServiceImpl();
        impl.setCollectTaskService(collectTaskService);
        impl.setSysAppIPSService(sysAppIPSService);
        impl.setSysInfoCategoryAPPSService(sysInfoCategoryAPPSService);
        impl.setSysInfoCategoryService(sysInfoCategoryService);
        impl.setSysAppService(sysAppService);
        impl.setSysInfoCategoryBanksService(sysInfoCategoryBanksService);
        impl.setSignMessageInfoService(signMessageInfoService);
        impl.setSignInfoService(signInfoService);
        impl.setChannelSendService(channelSendService);
        impl.setRequestDao(requestDao);
        // IBrokerTradeService brokerTradeService =  (IBrokerTradeService)SpringContextHelper.getBean("brokerTradeServiceImplProvider");

        RequestVo vo = new RequestVo();
        vo.setBizSysNo(BizSys.ZENDAI_2004_SYS.getCode());   // todo 签约系统功能号
        vo.setBizTypeNo(BizType.AGREEMENT_SIGN.getCode());  // 业务类型：签约
        vo.setInfoCategoryCode("10082");                    // todo 信息类别编码

        SignMsgReqVo signMsgReqVo = new SignMsgReqVo();
        for (int i = 1; i <= 1; i++) {
            signMsgReqVo.setPaySysNo(ThirdType.ALLINPAY2.getCode());    // 通联支付2
            signMsgReqVo.setBankCode("00080005");       // 银行编码
            signMsgReqVo.setBankName("中国建设银行");    // 银行名称
            signMsgReqVo.setAccountType("1");           // 银行卡类型 1.借记卡，2信用卡
            signMsgReqVo.setAccountNo("9558801001114990713");
            signMsgReqVo.setAccountName("张三");
            signMsgReqVo.setAccountProp("C");
            signMsgReqVo.setIdType("0");
            signMsgReqVo.setIdNum("320800198504287434");
            signMsgReqVo.setTel("13916158084");
            signMsgReqVo.setMerrem("这是一个保留信息");
            signMsgReqVo.setRemark("备注");
            vo.getList().add(signMsgReqVo);
        }

        Response response = impl.syncSignMessage(vo);

        logger.info("协议支付短信触发返回结果:" + JSONObject.toJSONString(response));
    }

    @Test
    public void signTest(){
        BrokerTradeServiceImpl impl = new BrokerTradeServiceImpl();
        impl.setCollectTaskService(collectTaskService);
        impl.setSysAppIPSService(sysAppIPSService);
        impl.setSysInfoCategoryAPPSService(sysInfoCategoryAPPSService);
        impl.setSysInfoCategoryService(sysInfoCategoryService);
        impl.setSysAppService(sysAppService);
        impl.setSysInfoCategoryBanksService(sysInfoCategoryBanksService);
        impl.setSignMessageInfoService(signMessageInfoService);
        impl.setSignInfoService(signInfoService);
        impl.setChannelSendService(channelSendService);
        impl.setRequestDao(requestDao);
        // IBrokerTradeService brokerTradeService =  (IBrokerTradeService)SpringContextHelper.getBean("brokerTradeServiceImplProvider");

        RequestVo vo = new RequestVo();
        vo.setBizSysNo(BizSys.ZENDAI_2004_SYS.getCode());   // todo 签约系统功能号
        vo.setBizTypeNo(BizType.AGREEMENT_SIGN.getCode());  // 业务类型：签约
        vo.setInfoCategoryCode("10082");                    // todo 信息类别编码

        SignReqVo signReqVo = new SignReqVo();
        for (int i = 1; i <= 1; i++) {
            signReqVo.setPaySysNo(ThirdType.ALLINPAY2.getCode());    // 通联支付2
            signReqVo.setSrcFlowId("12193034645086121557");
            signReqVo.setVerCode("111111");
            vo.getList().add(signReqVo);
        }

        Response response = impl.syncSign(vo);

        logger.info("协议支付签约返回结果:" + JSONObject.toJSONString(response));
    }
}
