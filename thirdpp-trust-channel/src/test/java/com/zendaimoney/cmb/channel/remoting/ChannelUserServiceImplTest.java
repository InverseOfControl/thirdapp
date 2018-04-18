package com.zendaimoney.cmb.channel.remoting;

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
import com.zendaimoney.trust.channel.pub.vo.req.FileReq;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.pub.vo.req.UsraDetailReq;
import com.zendaimoney.trust.channel.pub.vo.req.UsraReq;
import com.zendaimoney.trust.channel.pub.vo.req.UsrbReq;
import com.zendaimoney.trust.channel.pub.vo.req.UsrdReq;
import com.zendaimoney.trust.channel.pub.vo.req.UsreReq;
import com.zendaimoney.trust.channel.pub.vo.req.UsrjReq;
import com.zendaimoney.trust.channel.sei.Impl.TrustChannelServiceImpl;
import com.zendaimoney.trust.channel.service.RequestService;
import com.zendaimoney.trust.channel.util.CalendarUtils;
import com.zendaimoney.trust.channel.util.LogPrn;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml", "classpath:spring/spring-channel.xml",
		"classpath:spring/applicationContext-task.xml" })
public class ChannelUserServiceImplTest {
	
	private static final LogPrn logger = new LogPrn(ChannelUserServiceImplTest.class);
	
	@Autowired
	private DispatchAction dispatchAction;

	@Autowired
	private RequestService requestService;
	
	@Autowired
	private ChannelRequestFilter channelRequestFilter;
	
	@Autowired
	private DispatchChannel dispatchChannel;

	

	@Test
	public void usraCommond() throws SQLException,
			InterruptedException {
		
		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);
		
		UsraReq vo = new UsraReq();
		vo.setTrustBizType(TrustBizType.USRA);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));
		
		vo.setUserNo("1000007"); // 用户号1000005(已开)
		vo.setUserName("张"); // 用户姓名
		vo.setUserType("2"); // 用户类型
		vo.setIdNo("411421197506123370"); // 证件号码
		vo.setIdType("Y"); // 证件类型
		vo.setMobile("18221374887"); // 手机号码
		
//		1219001002134010000000201 对公 1000006
//		1219001002134010000000200 对私 1000005
		
		AttachmentResponse<String> response = (AttachmentResponse<String>)channelServiceImpl.tradeCommand(vo);
		logger.info("code:" + response.getCode() 
				+ ",msg:" + response.getMsg() 
				+ ",BankRepCode:" + response.getBankRepCode() 
				+ ",flow:" + response.getFlowId()
				+ ",tradeFlow:" + vo.getTradeFlow()
				+ ",virtualSubNo:" + response.getAttachment());
	}
	
	@Test
	public void usreCommond() throws SQLException,
			InterruptedException {
		
		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);
		
		UsreReq vo = new UsreReq();
		vo.setBizType(BizType.TRUST_OPEN_ACCOUNT);
		vo.setTrustBizType(TrustBizType.USRE);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow("005000001700002");
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));
		
		vo.setUserNo("102030"); // 用户号1000005(已开)
		vo.setVirtualSubNo("1219001002134010000000202");
		vo.setIdNo("370983198706191354");
		
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
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void usraBatchBuildCommond() throws SQLException,
			InterruptedException {
		
		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);
		AttachmentReqVo<List<UsraDetailReq>> vo = new AttachmentReqVo<List<UsraDetailReq>>();
		vo.setTrustBizType(TrustBizType.USRA);
//		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setBatchNo(1);
		
		UsraDetailReq req = new UsraDetailReq();
		req.setUserNo("1000003"); // 用户号
		req.setUserName("mencius"); // 用户姓名
		req.setUserType("1"); // 用户类型
		req.setIdNo("411421198506123370"); // 证件号码
		req.setIdType("0"); // 证件类型
		req.setMobile("18221374887"); // 手机号码
		
		UsraDetailReq req1 = new UsraDetailReq();
		req1.setUserNo("1000004"); // 用户号
		req1.setUserName("张蒙"); // 用户姓名
		req1.setUserType("1"); // 用户类型
		req1.setIdNo("411421198706123370"); // 证件号码
		req1.setIdType("0"); // 证件类型
		req1.setMobile("18221374856"); // 手机号码
		
		List<UsraDetailReq> detailReqs = new ArrayList<UsraDetailReq>();
		detailReqs.add(req);
		detailReqs.add(req1);
		vo.setAttachment(detailReqs);
		
		AttachmentResponse<String> response = (AttachmentResponse<String>) channelServiceImpl.batchCommand(vo);
		
		logger.info("code:" + response.getCode() 
				+ ",msg:" + response.getMsg() 
				+ ",BankRepCode:" + response.getBankRepCode() 
				+ ",flow:" + response.getFlowId()
				+ ",tradeFlow:" + vo.getTradeFlow()
				+ ",filename:" + response.getAttachment());
	}

	@Test
	public void usraBatchParseCommond() throws SQLException,
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
		
		vo.setSpare("CMBAUSRA88880216542016011500005SR.TXT");
		
		
		Response response = channelServiceImpl.batchCommand(vo);
		
		logger.info("code:" + response.getCode() 
				+ ",msg:" + response.getMsg() 
				+ ",BankRepCode:" + response.getBankRepCode() 
				+ ",flow:" + response.getFlowId()
				+ ",tradeFlow:" + vo.getTradeFlow());
	}
	
	@Test
	public void fileCommand() {
		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);
		
		FileReq vo = new FileReq();
		
		vo.setTrustBizType(TrustBizType.FILE);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));
		
		vo.setReqFileName("CMBAUSRA88880216542016011800001SC.TXT");
		
		Response response = channelServiceImpl.tradeCommand(vo);
		
		logger.info("code:" + response.getCode() 
				+ ",msg:" + response.getMsg() 
				+ ",BankRepCode:" + response.getBankRepCode() 
				+ ",flow:" + response.getFlowId()
				+ ",tradeFlow:" + vo.getTradeFlow());
	}
	
	@Test
	public void queryCommond() throws SQLException,
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
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));
		
		
		Response response = channelServiceImpl.queryCommand(vo);
		logger.info("code:" + response.getCode() 
				+ ",msg:" + response.getMsg() 
				+ ",BankRepCode:" + response.getBankRepCode() 
				+ ",flow:" + response.getFlowId()
				+ ",tradeFlow:" + vo.getTradeFlow());
	}
	
	
	/**
	 * 用户解绑银行卡(USRJ)
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void usrjCommond() throws SQLException,
			InterruptedException {
		
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
		vo.setTradeTime(CalendarUtils.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));
		
		vo.setUserNo("1000005"); // 用户号
		vo.setVirtualSubNo("1219001002134010000000200"); // 用户虚拟子账户号
//		vo.setBankCardNo("6226660604487294"); // 银行账户号
		vo.setBankName("");
		vo.setBankSubName("");
		
		Response response =channelServiceImpl.tradeCommand(vo);
		logger.info("code:" + response.getCode() 
				+ ",msg:" + response.getMsg() 
				+ ",BankRepCode:" + response.getBankRepCode() 
				+ ",flow:" + response.getFlowId()
				+ ",tradeFlow:" + vo.getTradeFlow());
	}
	
	/**
	 * 用户关户(USRD)
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void usrdCommond() throws SQLException,
			InterruptedException {
		
		TrustChannelServiceImpl channelServiceImpl = new TrustChannelServiceImpl();
		channelServiceImpl.setDispatchChannel(dispatchChannel);
		
		UsrdReq vo = new UsrdReq();
		vo.setTrustBizType(TrustBizType.USRD);
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));
		
		vo.setUserNo("1000005"); // 用户号
		vo.setVirtualSubNo("1219001002134010000000201"); // 用户虚拟子账户号(1219001002134010000000201,msg:用户不存在,BankRepCode:CMBUS02)
		
		Response response =channelServiceImpl.tradeCommand(vo);
		logger.info("code:" + response.getCode() 
				+ ",msg:" + response.getMsg() 
				+ ",BankRepCode:" + response.getBankRepCode() 
				+ ",flow:" + response.getFlowId()
				+ ",tradeFlow:" + vo.getTradeFlow());
	}
}
