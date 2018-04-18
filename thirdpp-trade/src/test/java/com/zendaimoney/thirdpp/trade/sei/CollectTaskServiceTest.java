package com.zendaimoney.thirdpp.trade.sei;

import java.math.BigDecimal;

import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.PayReqVo;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.QueryReqVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zendaimoney.thirdpp.common.enums.BizSys;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.trade.business.ChannelSendService;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.CollectReqVo;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.RequestVo;
import com.zendaimoney.thirdpp.trade.service.CollectTaskService;
import com.zendaimoney.thirdpp.trade.service.SysAppIPSService;
import com.zendaimoney.thirdpp.trade.service.SysAppService;
import com.zendaimoney.thirdpp.trade.service.SysInfoCategoryAPPSService;
import com.zendaimoney.thirdpp.trade.service.SysInfoCategoryBanksService;
import com.zendaimoney.thirdpp.trade.service.SysInfoCategoryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml","classpath:spring-dubbo.xml","classpath:applicationContext-task.xml","classpath:spring-rabbitmq-producter.xml" })
public class CollectTaskServiceTest {

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


	@Test
	public void asyncCollect() {
		BrokerTradeServiceImpl impl = new BrokerTradeServiceImpl();
		impl.setCollectTaskService(collectTaskService);
		impl.setSysAppIPSService(sysAppIPSService);
		impl.setSysInfoCategoryAPPSService(sysInfoCategoryAPPSService);
		impl.setSysInfoCategoryService(sysInfoCategoryService);
		impl.setSysAppService(sysAppService);
		impl.setSysInfoCategoryBanksService(sysInfoCategoryBanksService);
		RequestVo vo = new RequestVo();
		vo.setBizSysNo(BizSys.ZENDAI_2004_SYS.getCode());
		vo.setBizTypeNo(BizType.BROKER_COLLECT.getCode());
		vo.setInfoCategoryCode("10009");
		CollectReqVo collectReqVo = null;
		for (int i = 1; i <= 1; i++) {
			collectReqVo = new CollectReqVo();
			collectReqVo.setPaySysNo(ThirdType.ALLINPAY.getCode());
			collectReqVo.setBizSysAccountNo("");
			collectReqVo.setZengdaiAccountNo("00080001");
			collectReqVo.setReceiverAccountNo("10000010"); // 收款方账户编号
			collectReqVo.setReveiverAccountName("证大财富");// 收款方姓名
			collectReqVo.setPayerName("张三");// 付款方姓名
			collectReqVo.setPayerBankCardNo("9558801001114990713");// 付款方银行卡号
			collectReqVo.setPayerBankCardType("1");// 付款方银行卡类型 1.借记卡，2信用卡
			collectReqVo.setPayerIdType("1");// 付款方证件类型
			collectReqVo.setPayerId("421023198609091827");// 付款方证件号
			collectReqVo.setPayerBankCode("00080001");// 付款方银行编码
			collectReqVo.setPayerSubBankCode("10023942");// 付款方银行支行行号
			collectReqVo.setCurrency("0");// 币种(0人民币)
			collectReqVo.setAmount(new BigDecimal("123"));// 金额
			collectReqVo.setBizFlow("201405300003" + i);// 业务流水号
			collectReqVo.setIsNeedPush(0); //是否需要推送
			collectReqVo.setSpare1("1");
			vo.getList().add(collectReqVo);
		}

		Response response = impl.asynCollect(vo);
		System.out.println("response-------------:" + response.getCode()
				+ ",msg:" + response.getMsg() + "flowid:" + response.getFlowId());
	}
	
	@Test
	public void syncCollect() {
		BrokerTradeServiceImpl impl = new BrokerTradeServiceImpl();
		impl.setCollectTaskService(collectTaskService);
		impl.setChannelSendService(channelSendService);
		impl.setSysAppIPSService(sysAppIPSService);
		impl.setSysAppService(sysAppService);
		impl.setSysInfoCategoryAPPSService(sysInfoCategoryAPPSService);
		impl.setSysInfoCategoryService(sysInfoCategoryService);
		RequestVo vo = new RequestVo();
		vo.setBizSysNo(BizSys.ZENDAI_2004_SYS.getCode());
		vo.setBizTypeNo(BizType.BROKER_COLLECT.getCode());
		vo.setInfoCategoryCode("10025");
		CollectReqVo collectReqVo = null;
		for (int i = 1; i <= 1; i++) {
			collectReqVo = new CollectReqVo();
			collectReqVo.setPaySysNo(ThirdType.ALLINPAY.getCode());
			collectReqVo.setBizSysAccountNo("");						// 业务系统客户号
			collectReqVo.setZengdaiAccountNo("10000001");				// 证大客户号
			collectReqVo.setReceiverAccountNo("10000010"); 				// 收款方账户编号
			collectReqVo.setReveiverAccountName("证大财富");				// 收款方姓名
			collectReqVo.setPayerName("张三");							// 付款方姓名
			collectReqVo.setPayerBankCardNo("9558801001114990713");		// 付款方银行卡号
			collectReqVo.setPayerBankCardType("1");						// 付款方银行卡类型 1.借记卡，2信用卡
			collectReqVo.setPayerIdType("1");							// 付款方证件类型
			collectReqVo.setPayerId("421023198609091827");				// 付款方证件号
			collectReqVo.setPayerBankCode("10000");						// 付款方银行编码
			collectReqVo.setPayerSubBankCode("10023942");				// 付款方银行支行行号
			collectReqVo.setCurrency("0");								// 币种(0人民币)
			collectReqVo.setAmount(new BigDecimal("11"));			// 金额
			collectReqVo.setBizFlow("201507130009" + i);				// 业务流水号
			collectReqVo.setIsNeedPush(1); 								// 是否需要推送
			vo.getList().add(collectReqVo);
		}
		QueryReqVo reqVo = new QueryReqVo();
		impl.queryPay(reqVo);

		Response response = impl.syncCollect(vo);
		System.out.println("response:" + response.getCode() + ",msg:" + response.getMsg() + "flowid:" + response.getFlowId());
	}

	@Test
	public void syncPay() {
		BrokerTradeServiceImpl impl = new BrokerTradeServiceImpl();
		impl.setCollectTaskService(collectTaskService);
		impl.setChannelSendService(channelSendService);
		impl.setSysAppIPSService(sysAppIPSService);
		impl.setSysInfoCategoryAPPSService(sysInfoCategoryAPPSService);
		impl.setSysInfoCategoryService(sysInfoCategoryService);
		RequestVo vo = new RequestVo();
		vo.setBizSysNo(BizSys.ZENDAI_2004_SYS.getCode());
		vo.setBizTypeNo(BizType.BROKER_PAY.getCode());
		vo.setInfoCategoryCode("10000");
		PayReqVo payReqVo = null;
		for (int i = 1; i <= 1; i++) {
			payReqVo = new PayReqVo();
			payReqVo.setPaySysNo(ThirdType.ALLINPAY.getCode());
			payReqVo.setBizSysAccountNo("");
			payReqVo.setZengdaiAccountNo("10000001");
			payReqVo.setReceiverAccountNo("10000010"); // 收款方账户编号

			payReqVo.setPayerAccountName("证大财富");// 收款方姓名
			payReqVo.setReceiverName("张三");// 付款方姓名
			payReqVo.setReceiverBankCardNo("9558801001114990713");// 付款方银行卡号
			payReqVo.setReceiverBankCardType("1");// 付款方银行卡类型 1.借记卡，2信用卡
			payReqVo.setReceiverIdType("1");// 付款方证件类型
			payReqVo.setReceiverId("421023198609091827");// 付款方证件号
			payReqVo.setReceiverBankCode("10000");// 付款方银行编码
			payReqVo.setReceiverSubBankCode("10023942");// 付款方银行支行行号
			payReqVo.setCurrency("0");// 币种(0人民币)
			payReqVo.setAmount(new BigDecimal("11"));// 金额
			payReqVo.setBizFlow("201507130009" + i);// 业务流水号

			payReqVo.setIsNeedPush(1); //是否需要推送
			vo.getList().add(payReqVo);
		}
		QueryReqVo reqVo = new QueryReqVo();
		impl.queryPay(reqVo);

		Response response = impl.syncCollect(vo);
		System.out.println("tradeFlow------------:"+payReqVo.getBizFlow());
		System.out.println("response-------------:" + response.getCode()
				+ ",msg:" + response.getMsg() + "flowid:" + response.getFlowId());
	}

}
