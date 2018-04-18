package com.zendaimoney.cmb.channel.remoting;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zendaimoney.thirdpp.common.enums.BizSys;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.AttachmentResponse;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.trust.channel.action.DispatchAction;
import com.zendaimoney.trust.channel.action.DispatchChannel;
import com.zendaimoney.trust.channel.filter.ChannelRequestFilter;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.vo.req.AttachmentReqVo;
import com.zendaimoney.trust.channel.pub.vo.req.ChrgDetailReq;
import com.zendaimoney.trust.channel.pub.vo.req.ChrgReq;
import com.zendaimoney.trust.channel.pub.vo.req.FileReq;
import com.zendaimoney.trust.channel.pub.vo.req.GroaReq;
import com.zendaimoney.trust.channel.pub.vo.req.GrocReq;
import com.zendaimoney.trust.channel.pub.vo.req.MchgReq;
import com.zendaimoney.trust.channel.pub.vo.req.ProaReq;
import com.zendaimoney.trust.channel.pub.vo.req.ProcDetailReq;
import com.zendaimoney.trust.channel.pub.vo.req.ProcReq;
import com.zendaimoney.trust.channel.pub.vo.req.SchgReq;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.pub.vo.req.UbqyReq;
import com.zendaimoney.trust.channel.pub.vo.req.UsraDetailReq;
import com.zendaimoney.trust.channel.pub.vo.req.UsraReq;
import com.zendaimoney.trust.channel.pub.vo.req.UsrbReq;
import com.zendaimoney.trust.channel.pub.vo.req.UsrdReq;
import com.zendaimoney.trust.channel.pub.vo.req.UsrjReq;
import com.zendaimoney.trust.channel.pub.vo.req.WdqyReq;
import com.zendaimoney.trust.channel.pub.vo.req.WtdrReq;
import com.zendaimoney.trust.channel.pub.vo.resp.ChrgResp;
import com.zendaimoney.trust.channel.pub.vo.resp.SchgResp;
import com.zendaimoney.trust.channel.pub.vo.resp.WtdrResp;
import com.zendaimoney.trust.channel.sei.Impl.CmbBatchCommandServiceImpl;
import com.zendaimoney.trust.channel.sei.Impl.TrustChannelServiceImpl;
import com.zendaimoney.trust.channel.service.RequestService;
import com.zendaimoney.trust.channel.util.CalendarUtils;
import com.zendaimoney.trust.channel.util.LogPrn;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/spring-channel.xml",
		"classpath:spring/applicationContext-task.xml" })
public class BatchServiceImplTest {

	private static final LogPrn logger = new LogPrn(
			BatchServiceImplTest.class);

	@Autowired
	private DispatchAction dispatchAction;

	@Autowired
	private RequestService requestService;

	@Autowired
	private ChannelRequestFilter channelRequestFilter;

	@Autowired
	private DispatchChannel dispatchChannel;

	@Test
	public void usraCommand() throws SQLException, InterruptedException {

		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);

		UsraReq vo = new UsraReq();
		vo.setTrustBizType(TrustBizType.USRA);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setBizType(BizType.TRUST_OPEN_ACCOUNT);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils
				.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));

		vo.setUserNo("3000002"); // 用户号1000001(已开)
		vo.setUserName("张"); // 用户姓名
		vo.setUserType("C"); // 用户类型
//		vo.setIdNo("411421197506123372"); // 证件号码
//		vo.setIdType("0"); // 证件类型
//		vo.setMobile("18221374888"); // 手机号码

		// 1219001002134010000000101 1000001 (个人)
		// 1219001002134010000000102 2000001 (团账户)
		vo.setSpare("111111111");
		AttachmentResponse<String> response = (AttachmentResponse<String>) channelServiceImpl
				.tradeCommand(vo);
		logger.info("code:" + response.getCode() + ",msg:" + response.getMsg()
				+ ",BankRepCode:" + response.getBankRepCode() + ",flow:"
				+ response.getFlowId() + ",tradeFlow:" + vo.getTradeFlow()
				+ ",virtualSubNo:" + response.getAttachment());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void usraBatchBuildCommand() throws SQLException,
			InterruptedException {

		CmbBatchCommandServiceImpl batchServiceImpl = new CmbBatchCommandServiceImpl();
			
		batchServiceImpl.setDispatchAction(dispatchAction);
		batchServiceImpl.setRequestService(requestService);
		AttachmentReqVo<List<UsraDetailReq>> vo = new AttachmentReqVo<List<UsraDetailReq>>();
		vo.setTrustBizType(TrustBizType.USRA);
		//vo.setTrustCategory(TrustCategory.BATCH_TRADE);
		vo.setBizType(BizType.TRUST_OPEN_ACCOUNT);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setBatchNo(2222);

		UsraDetailReq req = new UsraDetailReq();
		req.setUserNo("1000003"); // 用户号
		req.setUserName("mencius"); // 用户姓名
		req.setUserType("P"); // 用户类型
		req.setIdNo("411421198506123370"); // 证件号码
		req.setIdType("0"); // 证件类型
		req.setMobile("18221374887"); // 手机号码

		UsraDetailReq req1 = new UsraDetailReq();
		req1.setUserNo("1000004"); // 用户号
		req1.setUserName("张蒙"); // 用户姓名
		req1.setUserType("P"); // 用户类型
		req1.setIdNo("411421198706123370"); // 证件号码
		req1.setIdType("0"); // 证件类型
		req1.setMobile("18221374856"); // 手机号码

		List<UsraDetailReq> detailReqs = new ArrayList<UsraDetailReq>();
		detailReqs.add(req);
		detailReqs.add(req1);
		vo.setAttachment(detailReqs);

		AttachmentResponse<String> response = (AttachmentResponse<String>)batchServiceImpl.build(vo);

		logger.info("code:" + response.getCode() + ",msg:" + response.getMsg()
				+ ",BankRepCode:" + response.getBankRepCode() + ",flow:"
				+ response.getFlowId() + ",tradeFlow:" + vo.getTradeFlow()
				+ ",filename:" + response.getAttachment());
	}
	
	

	@Test
	public void usraBatchPullCommand() throws SQLException,
			InterruptedException {

		CmbBatchCommandServiceImpl batchServiceImpl = new CmbBatchCommandServiceImpl();
		batchServiceImpl.setDispatchAction(dispatchAction);
		batchServiceImpl.setRequestService(requestService);
		TrustBizReqVo vo = new TrustBizReqVo();
		vo.setTrustBizType(TrustBizType.USRA);
		vo.setTrustCategory(TrustCategory.BATCH_TRADE);
		vo.setBizType(BizType.TRUST_OPEN_ACCOUNT);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());

		vo.setSpare("CMBAUSRB88880216542016041210222SR.TXT");

		Response response = batchServiceImpl.pull(vo);

		logger.info("code:" + response.getCode() + ",msg:" + response.getMsg()
				+ ",BankRepCode:" + response.getBankRepCode() + ",flow:"
				+ response.getFlowId() + ",tradeFlow:" + vo.getTradeFlow());
	}
	
	@Test
	public void usraBatchParseCommand() throws SQLException,
			InterruptedException {

		CmbBatchCommandServiceImpl batchServiceImpl = new CmbBatchCommandServiceImpl();
		batchServiceImpl.setDispatchAction(dispatchAction);
		batchServiceImpl.setRequestService(requestService);
		TrustBizReqVo vo = new TrustBizReqVo();
		vo.setTrustBizType(TrustBizType.USRB);
		vo.setTrustCategory(TrustCategory.BATCH_TRADE);
		vo.setBizType(BizType.TRUST_OPEN_ACCOUNT);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());

		vo.setSpare("CMBAUSRB88880216542016041510001SR.TXT");

		Response response = batchServiceImpl.parse(vo);

		logger.info("code:" + response.getCode() + ",msg:" + response.getMsg()
				+ ",BankRepCode:" + response.getBankRepCode() + ",flow:"
				+ response.getFlowId() + ",tradeFlow:" + vo.getTradeFlow());
	}

	@Test
	public void fileCommand() {
		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);

		FileReq vo = new FileReq();

		vo.setTrustBizType(TrustBizType.FILE);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setBizType(BizType.TRUST_OPEN_ACCOUNT);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils
				.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));

		// vo.setReqFileName("CMBAUSRA88880216542016011800001SC.TXT");

		//
//		vo.setReqFileName("CMBACHRG88880216542016022200001SC.TXT"); //该业务[FILE_CODE][CHRG]尚未开通
		
//		CMBAPROC88880216542016022200000SC.TXT
//		vo.setReqFileName("CMBAPROC88880216542016022200000SC.TXT"); //该业务[FILE_CODE][PROC]尚未开通
		
//		CMBAUSRA88880216542016022400008SC.TXT
		vo.setReqFileName("CMBAUSRA88880216542016041202222SC.TXT");

		Response response = channelServiceImpl.tradeCommand(vo);

		logger.info("code:" + response.getCode() + ",msg:" + response.getMsg()
				+ ",BankRepCode:" + response.getBankRepCode() + ",flow:"
				+ response.getFlowId() + ",tradeFlow:" + vo.getTradeFlow());
	}
	//CMBAUSRA88880216542016033100008SR.TXT
	
	

	@Test
	public void queryCommand() throws SQLException, InterruptedException {

		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);

		TrustBizReqVo vo = new TrustBizReqVo();
		vo.setTrustBizType(TrustBizType.USRA);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);

		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils
				.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));

		Response response = channelServiceImpl.queryCommand(vo);
		logger.info("code:" + response.getCode() + ",msg:" + response.getMsg()
				+ ",BankRepCode:" + response.getBankRepCode() + ",flow:"
				+ response.getFlowId() + ",tradeFlow:" + vo.getTradeFlow());
	}

	/**
	 * 用户解绑银行卡(USRJ)
	 * 
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void usrjCommand() throws SQLException, InterruptedException {

		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);

		UsrjReq vo = new UsrjReq();
		vo.setTrustBizType(TrustBizType.USRJ);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);

		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils
				.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));

		vo.setUserNo("1000005"); // 用户号
		vo.setVirtualSubNo("1219001002134010000000200"); // 用户虚拟子账户号
		// vo.setBankCardNo("6226660604487294"); // 银行账户号
		vo.setBankName("");
		vo.setBankSubName("");

		Response response = channelServiceImpl.tradeCommand(vo);
		logger.info("code:" + response.getCode() + ",msg:" + response.getMsg()
				+ ",BankRepCode:" + response.getBankRepCode() + ",flow:"
				+ response.getFlowId() + ",tradeFlow:" + vo.getTradeFlow());
	}

	/**
	 * 用户关户(USRD)
	 * 
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void usrdCommand() throws SQLException, InterruptedException {

		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);

		UsrdReq vo = new UsrdReq();
		vo.setTrustBizType(TrustBizType.USRD);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setBizType(BizType.TRUST_OPEN_ACCOUNT);

		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils
				.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));

		vo.setUserNo("1000005"); // 用户号
		vo.setVirtualSubNo("1219001002134010000000201"); // 用户虚拟子账户号(1219001002134010000000201,msg:用户不存在,BankRepCode:CMBUS02)

		Response response = channelServiceImpl.tradeCommand(vo);
		logger.info("code:" + response.getCode() + ",msg:" + response.getMsg()
				+ ",BankRepCode:" + response.getBankRepCode() + ",flow:"
				+ response.getFlowId() + ",tradeFlow:" + vo.getTradeFlow());
	}

	@Test
	public void chrgCommand() {

		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);

		ChrgReq vo = new ChrgReq();
		vo.setBizType(BizType.TRUST_RECHARGE);
		vo.setTrustBizType(TrustBizType.CHRG);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils
				.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));

		vo.setUserNo("1000001"); // 用户号
		vo.setVirtualSubNo("1219001002134010000000101"); // 用户虚拟子账户号
		vo.setThirdFlag("10001"); // 三方支付公司标识
		vo.setThirdOrderNo("111111"); // 三方支付公司订单号
		vo.setAmount(new BigDecimal("12.34")); // 充值金额

		AttachmentResponse<ChrgResp> attachmentResponse = (AttachmentResponse<ChrgResp>) channelServiceImpl
				.tradeCommand(vo);

		logger.info("code:" + attachmentResponse.getCode() + ",msg:"
				+ attachmentResponse.getMsg() + ",BankRepCode:"
				+ attachmentResponse.getBankRepCode() + ",flow:"
				+ attachmentResponse.getFlowId() + ",tradeFlow:"
				+ vo.getTradeFlow());
	}
	
	@Test
	public void mchgCommand() {

		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);

		MchgReq vo = new MchgReq();
		vo.setBizType(BizType.TRUST_RECHARGE);
		vo.setTrustBizType(TrustBizType.MCHG);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils
				.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));

		vo.setUserNo("1000001"); // 用户号
		vo.setVirtualSubNo("1219001002134010000000101"); // 用户虚拟子账户号
		vo.setForCorporateNo("53312333");
		vo.setAmount(new BigDecimal("12.34")); // 充值金额

		Response response  =  channelServiceImpl
				.tradeCommand(vo);

		logger.info("code:" + response.getCode() + ",msg:"
				+ response.getMsg() + ",BankRepCode:"
				+ response.getBankRepCode() + ",flow:"
				+ response.getFlowId() + ",tradeFlow:"
				+ vo.getTradeFlow());
	}

	/**
	 * 暂未开通
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void chrgBatchBuildCommand() {

		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);
		AttachmentReqVo<List<ChrgDetailReq>> vo = new AttachmentReqVo<List<ChrgDetailReq>>();
		vo.setTrustBizType(TrustBizType.CHRG);
		vo.setThirdType(ThirdType.CMBPAY);

		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setBizType(BizType.TRUST_RECHARGE);
		vo.setBatchNo(1);

		ChrgDetailReq req = new ChrgDetailReq();
		req.setMerchantFlow(CalendarUtils.getFormatNow());
		req.setUserNo("1000001"); // 用户号
		req.setVirtualSubNo("1219001002134010000000101"); // 用户虚拟子账户号
		req.setThirdFlag("10001"); // 三方支付公司标识
		req.setThirdOrderNo("111111"); // 三方支付公司订单号
		req.setAmount(new BigDecimal("12.34")); // 充值金额

		ChrgDetailReq req1 = new ChrgDetailReq();
		req1.setMerchantFlow(CalendarUtils.getFormatNow());
		req1.setUserNo("1000001"); // 用户号
		req1.setVirtualSubNo("1219001002134010000000101"); // 用户虚拟子账户号
		req1.setThirdFlag("10002"); // 三方支付公司标识
		req1.setThirdOrderNo("111112"); // 三方支付公司订单号
		req1.setAmount(new BigDecimal("22.34")); // 充值金额

		List<ChrgDetailReq> detailReqs = new ArrayList<ChrgDetailReq>();
		detailReqs.add(req);
		detailReqs.add(req1);
		vo.setAttachment(detailReqs);

		AttachmentResponse<String> response = (AttachmentResponse<String>) channelServiceImpl
				.batchCommand(vo);

		logger.info("code:" + response.getCode() + ",msg:" + response.getMsg()
				+ ",BankRepCode:" + response.getBankRepCode() + ",flow:"
				+ response.getFlowId() + ",tradeFlow:" + vo.getTradeFlow()
				+ ",filename:" + response.getAttachment());
	}
	
	/**
	 * 暂未开通
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void chrgBatchParseCommand() throws SQLException,
			InterruptedException {

		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);
		TrustBizReqVo vo = new TrustBizReqVo();
		vo.setTrustBizType(TrustBizType.USRA);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());

		vo.setSpare("CMBACHRG88880216542016022200001SR.TXT");

		Response response = channelServiceImpl.batchCommand(vo);

		logger.info("code:" + response.getCode() + ",msg:" + response.getMsg()
				+ ",BankRepCode:" + response.getBankRepCode() + ",flow:"
				+ response.getFlowId() + ",tradeFlow:" + vo.getTradeFlow());
	}

	@Test
	public void schgCommand() {

		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);

		SchgReq vo = new SchgReq();
		vo.setBizType(BizType.TRUST_RECHARGE);
		vo.setTrustBizType(TrustBizType.SCHG);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils
				.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));

		vo.setUserNo("1000001"); // 用户号
		vo.setVirtualSubNo("1219001002134010000000101"); // 用户虚拟子账户号
		vo.setAmount(new BigDecimal("12.34")); // 充值金额
		vo.setTransferFlow(CalendarUtils.getFormatNow()); // 自助转账交易流水

		AttachmentResponse<SchgResp> attachmentResponse = (AttachmentResponse<SchgResp>) channelServiceImpl
				.tradeCommand(vo);

		logger.info("code:" + attachmentResponse.getCode() + ",msg:"
				+ attachmentResponse.getMsg() + ",BankRepCode:"
				+ attachmentResponse.getBankRepCode() + ",flow:"
				+ attachmentResponse.getFlowId() + ",tradeFlow:"
				+ vo.getTradeFlow());
	}

	@Test
	public void wtdrCommand() {

		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);

		WtdrReq vo = new WtdrReq();
		vo.setBizType(BizType.TRUST_WITHDRWA);
		vo.setTrustBizType(TrustBizType.WTDR);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils
				.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));

		vo.setUserNo("1000001"); // 用户号
		vo.setVirtualSubNo("1219001002134010000000101"); // 用户虚拟子账户号
		vo.setBankCardNo("6226660604487294");
		// vo.setUserName("mencius");
		// vo.setBankName("招商银行");
		// vo.setBankSubName("");
		vo.setAmount(new BigDecimal("12.34")); // 充值金额
		// vo.setNote("");
		// vo.setAdvanceSubNo("");
		// vo.setAdvanceUserNo("");

		AttachmentResponse<WtdrResp> attachmentResponse = (AttachmentResponse<WtdrResp>) channelServiceImpl
				.tradeCommand(vo);

		logger.info("code:" + attachmentResponse.getCode() + ",msg:"
				+ attachmentResponse.getMsg() + ",BankRepCode:"
				+ attachmentResponse.getBankRepCode() + ",flow:"
				+ attachmentResponse.getFlowId() + ",tradeFlow:"
				+ vo.getTradeFlow());
	}
	
	@Test
	public void wdqyCommand() {

		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);

		WdqyReq vo = new WdqyReq();
		vo.setBizType(BizType.TRUST_WITHDRWA);
		vo.setTrustBizType(TrustBizType.WDQY);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils
				.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));

		vo.setUserNo("1000001"); // 用户号
		vo.setVirtualSubNo("1219001002134010000000101"); // 用户虚拟子账户号
		//vo.setTradeFlow("6226660604487294");
		// vo.setUserName("mencius");
		// vo.setBankName("招商银行");
		// vo.setBankSubName("");
		vo.setAmount(new BigDecimal("12.34")); // 充值金额
		// vo.setNote("");
		// vo.setAdvanceSubNo("");
		// vo.setAdvanceUserNo("");

		Response  r= (Response) channelServiceImpl
				.tradeCommand(vo);

		logger.info("code:" + r.getCode() + ",msg:"
				+ r.getMsg() + ",BankRepCode:"
				+ r.getBankRepCode() + ",flow:"
				+ r.getFlowId() + ",tradeFlow:"
				+ vo.getTradeFlow());
	}
	@Test
	public void usrbCommond() throws SQLException,
			InterruptedException {
		
		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);
		
		UsrbReq vo = new UsrbReq();
		vo.setTrustBizType(TrustBizType.USRB);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setBizType(BizType.TRUST_BIND_CARD);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));
		
		vo.setUserNo("1000007"); // 用户号1000005(已开)
		vo.setVirtualSubNo("1219001002134010000000202");
		vo.setBankCardType("P");
		vo.setBankCardNo("6222021001072465252");
		vo.setUserName("张");
		vo.setBankName("工商银行");
		vo.setBankName("工商银行峨山路支行");
		
		//vo.setIdNo("411421197506123370"); // 证件号码
		//vo.setIdType("Y"); // 证件类型
		//vo.setMobile("18221374887"); // 手机号码
		
//		1219001002134010000000201 对公 1000006
//		1219001002134010000000200 对私 1000005
		
		Response response = channelServiceImpl.tradeCommand(vo);
		logger.info("code:" + response.getCode() 
				+ ",msg:" + response.getMsg() 
				+ ",BankRepCode:" + response.getBankRepCode() 
				+ ",flow:" + response.getFlowId()
				+ ",tradeFlow:" + vo.getTradeFlow());
	}
	
	@Test
	public void ubqyCommond() throws SQLException,
			InterruptedException {
		
		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);
		
		UbqyReq vo = new UbqyReq();
		vo.setTrustBizType(TrustBizType.UBQY);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setBizType(BizType.TRUST_BIND_CARD);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));
		
		vo.setUserNo("1000007"); // 用户号1000005(已开)
		vo.setVirtualSubNo("1219001002134010000000202");
		//vo.setBankCardType("P");
		vo.setBankCardNo("6222021001072465252");
		//vo.setUserName("张");
		//vo.setBankName("工商银行");
		//vo.setBankName("工商银行峨山路支行");
		
		//vo.setIdNo("411421197506123370"); // 证件号码
		//vo.setIdType("Y"); // 证件类型
		//vo.setMobile("18221374887"); // 手机号码
		
//		1219001002134010000000201 对公 1000006
//		1219001002134010000000200 对私 1000005
		
		Response response = channelServiceImpl.tradeCommand(vo);
		logger.info("code:" + response.getCode() 
				+ ",msg:" + response.getMsg() 
				+ ",BankRepCode:" + response.getBankRepCode() 
				+ ",flow:" + response.getFlowId()
				+ ",tradeFlow:" + vo.getTradeFlow());
	}

	/**
	 * 登记散标(PROA)
	 * 
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void proaCommand() throws SQLException, InterruptedException {

		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);

		ProaReq vo = new ProaReq();
		vo.setTrustBizType(TrustBizType.PROA);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setBizType(BizType.TRUST_GOPER);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils
				.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));

		vo.setDisperseSubjectNo("00001");// 散标编号
		vo.setDisperseSubjectName("证大散标1");//
		vo.setBorrowerType("个人贷借款人");
		vo.setBorrowerNo("1000001");
		vo.setbVirtualSubNo("1219001002134010000000101");
		vo.setTerm(12);
		vo.setUnit("M");
		vo.setTotalAmount(new BigDecimal("12.34"));
		vo.setYield(new BigDecimal("5.1"));
		vo.setRepayment("受托支付");

		Response response = channelServiceImpl.tradeCommand(vo);
		logger.info("code:" + response.getCode() + ",msg:" + response.getMsg()
				+ ",BankRepCode:" + response.getBankRepCode() + ",flow:"
				+ response.getFlowId() + ",tradeFlow:" + vo.getTradeFlow());
	}

	/**
	 * 关闭散标(PROC)
	 * 
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void procCommand() throws SQLException, InterruptedException {

		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);

		ProcReq vo = new ProcReq();
		vo.setTrustBizType(TrustBizType.PROC);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setBizType(BizType.TRUST_GOPER);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils
				.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));

		vo.setDisperseSubjectNo("00001");// 散标编号
		vo.setBorrowerNo("1000001");
		vo.setbVirtualSubNo("1219001002134010000000101");

		Response response = channelServiceImpl.tradeCommand(vo);
		logger.info("code:" + response.getCode() + ",msg:" + response.getMsg()
				+ ",BankRepCode:" + response.getBankRepCode() + ",flow:"
				+ response.getFlowId() + ",tradeFlow:" + vo.getTradeFlow());
	}
	
	/**
	 * 关闭散标(PROC)
	 * 
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void procBatchBuildCommand() throws SQLException, InterruptedException {

		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);

		AttachmentReqVo<List<ProcDetailReq>> vo = new AttachmentReqVo<List<ProcDetailReq>>();
		vo.setTrustBizType(TrustBizType.PROC);
		vo.setTrustCategory(TrustCategory.BATCH_TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setBizType(BizType.TRUST_GOPER);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils
				.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));

		ProcDetailReq req0 = new ProcDetailReq();
		req0.setDisperseSubjectNo("00001");// 散标编号
		req0.setBorrowerNo("1000001");
		req0.setbVirtualSubNo("1219001002134010000000101");
		
		ProcDetailReq req1 = new ProcDetailReq();
		req1.setDisperseSubjectNo("00002");// 散标编号
		req1.setBorrowerNo("1000001");
		req1.setbVirtualSubNo("1219001002134010000000101");
		
		List<ProcDetailReq> detailReqs = new ArrayList<ProcDetailReq>();
		detailReqs.add(req0);
		detailReqs.add(req1);
		vo.setAttachment(detailReqs);

		AttachmentResponse<String> response = (AttachmentResponse<String>) channelServiceImpl
				.batchCommand(vo);
		logger.info("code:" + response.getCode() + ",msg:" + response.getMsg()
				+ ",BankRepCode:" + response.getBankRepCode() + ",flow:"
				+ response.getFlowId() + ",tradeFlow:" + vo.getTradeFlow()
				+ ",filename:" + response.getAttachment());
	}

	/**
	 * 登记团信息(GROA)
	 * 
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void groaCommand() throws SQLException, InterruptedException {

		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);

		GroaReq vo = new GroaReq();
		vo.setTrustBizType(TrustBizType.GROA);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setBizType(BizType.TRUST_GOPER);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils
				.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));

		vo.setGroupNo("20001"); // 团编号
		vo.setGroupName("登记团1"); // 团名称
		vo.setGroupUserNo("2000001"); // 团用户号
		vo.setgVirtualSubNo("1219001002134010000000102"); // 团虚拟子账户号

		vo.setTerm(12);
		vo.setUnit("M");
		vo.setTotalAmount(new BigDecimal("12.34"));
		vo.setYield(new BigDecimal("5.1"));
		vo.setRepayment("受托支付");

		Response response = channelServiceImpl.tradeCommand(vo);
		logger.info("code:" + response.getCode() + ",msg:" + response.getMsg()
				+ ",BankRepCode:" + response.getBankRepCode() + ",flow:"
				+ response.getFlowId() + ",tradeFlow:" + vo.getTradeFlow());
	}

	/**
	 * 关闭团(GROC)
	 * 
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void grocCommand() throws SQLException, InterruptedException {

		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);

		GrocReq vo = new GrocReq();
		vo.setTrustBizType(TrustBizType.GROC);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setBizType(BizType.TRUST_GOPER);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils
				.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));

		vo.setGroupNo("20001");
		vo.setGroupUserNo("2000001");
		vo.setgVirtualSubNo("1219001002134010000000102");

		Response response = channelServiceImpl.tradeCommand(vo);
		logger.info("code:" + response.getCode() + ",msg:" + response.getMsg()
				+ ",BankRepCode:" + response.getBankRepCode() + ",flow:"
				+ response.getFlowId() + ",tradeFlow:" + vo.getTradeFlow());
	}
	public static void main(String[] args){
		try {
			System.out.println(checkExist("/home/tpp/bak/download/cmb/12/"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(File.separator);
	}
	public static File checkExist(String filepath) throws Exception{
	      File file=new File(filepath);
	      
	      if (file.exists()) {//判断文件目录的存在
	          System.out.println("文件夹存在！");
	          if(file.isDirectory()){//判断文件的存在性      
	                System.out.println("文件存在！");      
	            }else{
	             file.createNewFile();//创建文件
	              System.out.println("文件不存在，创建文件成功！"   );      
	            }
	      }else {
	          System.out.println("文件夹不存在！");
	          File file2=new File(file.getParent());
	          file.mkdirs();
	          System.out.println("创建文件夹成功！");
	          if(file.isDirectory()){      
	                System.out.println("文件存在！");      
	            }else{      
	             file.createNewFile();//创建文件
	              System.out.println("文件不存在，创建文件成功！"   );      
	            }
	      }
	      return file;
	   }
}
