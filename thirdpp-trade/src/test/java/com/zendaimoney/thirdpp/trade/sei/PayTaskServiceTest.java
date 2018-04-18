package com.zendaimoney.thirdpp.trade.sei;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zendaimoney.thirdpp.common.enums.BizSys;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.trade.business.ChannelSendService;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.PayReqVo;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.RequestVo;
import com.zendaimoney.thirdpp.trade.service.PayTaskService;
import com.zendaimoney.thirdpp.trade.service.SysAppIPSService;
import com.zendaimoney.thirdpp.trade.service.SysAppService;
import com.zendaimoney.thirdpp.trade.service.SysInfoCategoryAPPSService;
import com.zendaimoney.thirdpp.trade.service.SysInfoCategoryService;
import com.zendaimoney.thirdpp.trade.service.TppWhiteListService;
import com.zendaimoney.thirdpp.trade.util.CalendarUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml","classpath:spring-dubbo.xml","classpath:applicationContext-task.xml","classpath:spring-rabbitmq-producter.xml" })
public class PayTaskServiceTest {

	@Autowired
	public PayTaskService payTaskService;
	
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
	public TppWhiteListService tppWhiteListService;


	@Test
	public void syncPay() {
		BrokerTradeServiceImpl impl = new BrokerTradeServiceImpl();
		impl.setPayTaskService(payTaskService);
		impl.setChannelSendService(channelSendService);
		impl.setSysAppIPSService(sysAppIPSService);
		impl.setSysInfoCategoryAPPSService(sysInfoCategoryAPPSService);
		impl.setSysInfoCategoryService(sysInfoCategoryService);
		impl.setSysAppService(sysAppService);
		impl.setTppWhiteListService(tppWhiteListService);
		
		RequestVo vo = new RequestVo();
		vo.setBizSysNo(BizSys.ZENDAI_2004_SYS.getCode());
		vo.setInfoCategoryCode("10025");
		
		PayReqVo payReqVo = null;
		for (int i = 1; i <= 1; i++) {
			payReqVo = new PayReqVo();
			
			payReqVo.setBizSysAccountNo("10000001");
			payReqVo.setZengdaiAccountNo("10000002");
			payReqVo.setPayerAccountNo("xintuo1"); // 付款人账户编号
			payReqVo.setPayerAccountName("mencius55");
			payReqVo.setReceiverName("test1"); 
			payReqVo.setReceiverBankCardNo("6221122012201220"); // 收款人银行卡号
			payReqVo.setReceiverBankCardType("1"); // 收款人银行卡类型
			payReqVo.setReceiverIdType("0"); // 收款方证件类型
			payReqVo.setReceiverId("1111111"); // 收款方证件号
			payReqVo.setReceiverBankCode("00080010"); // 收款方银行编码
			payReqVo.setReceiverSubBankCode("001"); // 收款方银行支行行号
			payReqVo.setReceiverAccountNo("10000010"); // 收款方账户编号  （代付失败账户）
			
//			payReqVo.setReceiverAccountNo("24533753"); // 收款方账户编号 （代付成功账户）
			payReqVo.setCurrency("0");// 币种(0人民币)
			payReqVo.setAmount(new BigDecimal("123"));// 金额
			payReqVo.setFee(new BigDecimal("1"));// 手续费
			payReqVo.setBizFlow(CalendarUtils.getFormatNow());// 业务流水号
			
//			payReqVo.setBizFlow("20151223172112");// 业务流水号
			payReqVo.setPaySysNo("4");
			payReqVo.setIsNeedPush(0); //是否需要推送
			payReqVo.setReceiverType("P"); // 收款人用户类型(P:个人，C:公司)
			
			vo.getList().add(payReqVo);
		}

		Response response = impl.syncPay(vo);
		System.out.println("response-------------:" + response.getCode()
				+ ",msg:" + response.getMsg() + "flowid:" + response.getFlowId());
	}

}
