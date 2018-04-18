package com.zendaimoney.thirdpp.channel.sei;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zendaimoney.thirdpp.channel.action.DispatchAction;
import com.zendaimoney.thirdpp.channel.filter.BankRequestFilter;
import com.zendaimoney.thirdpp.channel.filter.ChannelRequestFilter;
import com.zendaimoney.thirdpp.channel.pub.vo.BankCardAuthReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.BankCardBinQueryReqVO;
import com.zendaimoney.thirdpp.channel.pub.vo.BankCardBinQueryRespVO;
import com.zendaimoney.thirdpp.channel.pub.vo.CollectReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.PayReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.QueryReqVo;
import com.zendaimoney.thirdpp.channel.service.RequestService;
import com.zendaimoney.thirdpp.channel.util.CalendarUtils;
import com.zendaimoney.thirdpp.channel.util.LogPrn;
import com.zendaimoney.thirdpp.common.enums.BizSys;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.AttachmentResponse;
import com.zendaimoney.thirdpp.common.vo.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/applicationContext-task.xml",
		"classpath:spring/applicationContext-rabbitmq-producter.xml" })
public class ChannelServiceImplTest {
	
	private static final LogPrn logger = new LogPrn(ChannelServiceImplTest.class);
	@Autowired
	private DispatchAction dispatchAction;

	@Autowired
	private RequestService requestService;

	@Autowired
	private ChannelRequestFilter channelRequestFilter;
	
	@Autowired
	private BankRequestFilter bankRequestFilter;

	@Test
	public void allinpayCollectCommond() throws SQLException,
			InterruptedException {
		ChannelServiceImpl impl = new ChannelServiceImpl();
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);
		impl.setBankRequestFilter(bankRequestFilter);

		CollectReqVo vo = new CollectReqVo();
		vo.setBizType(BizType.BROKER_COLLECT);
		vo.setThirdType(ThirdType.ALLINPAY);
		vo.setBizSys(BizSys.ZENDAI_2018_SYS);
		vo.setAmount(new BigDecimal("1111"));
		vo.setTradeFlow(CalendarUtils.getFormatNow());
//		vo.setPayerBankCode("10010");
		vo.setPayerBankCardNo("9558801001114990713");
		vo.setPayerName("代付");
		vo.setInfoCategoryCode("10022");

		Response response = impl.collectCommond(vo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + ",flow:" + response.getFlowId()
				+ "tradeFlow:" + vo.getTradeFlow());
	}
	
	/**
	 * 通联310011代扣接口测试
	 * 
	 * autor:ym10159
	 * date:2018年4月9日 上午10:38:47
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void allinpayCollectCommond_310011() throws SQLException, InterruptedException {
		ChannelServiceImpl impl = new ChannelServiceImpl();
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);
		impl.setBankRequestFilter(bankRequestFilter);

		CollectReqVo vo = new CollectReqVo();
		vo.setBizType(BizType.BROKER_COLLECT);
		vo.setThirdType(ThirdType.ALLINPAY2);
		vo.setBizSys(BizSys.ZENDAI_2018_SYS);
		vo.setAmount(new BigDecimal("5"));
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setPayerBankCode("0105");
		vo.setPayerBankCardNo("9558801001114990713");
		vo.setPayerName("张三");
		vo.setInfoCategoryCode("10082");
		vo.setPayerMobile("13916158084");
		vo.setPayerId("320800198504287434");

		Response response = impl.collectCommond(vo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + ",flow:" + response.getFlowId()
				+ "tradeFlow:" + vo.getTradeFlow());
	}
	
	@Test
	public void allinpayPayCommond() throws SQLException,
			InterruptedException {
		ChannelServiceImpl impl = new ChannelServiceImpl();
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);
		impl.setBankRequestFilter(bankRequestFilter);

		PayReqVo vo = new PayReqVo();
		vo.setBizType(BizType.BROKER_PAY);
		vo.setThirdType(ThirdType.ALLINPAY);
		vo.setBizSys(BizSys.ZENDAI_2003_SYS);
		vo.setAmount(new BigDecimal("1111"));
		vo.setTradeFlow(CalendarUtils.getFormatNow());
//		vo.setPayerBankCode("10010");
		vo.setReceiverName("张三");
		vo.setReceiverBankCardNo("9558801001114990713");
		vo.setReceiverBankCardType("1");
		vo.setReceiverId("370983198706191356");
		vo.setReceiverIdType("0");
		vo.setReceiverType("C");
		vo.setInfoCategoryCode("10022");

		Response response = impl.payCommond(vo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + ",flow:" + response.getFlowId()
				+ "tradeFlow:" + vo.getTradeFlow());
	}

	@Test
	public void allinpayQueryCommond() throws SQLException,
			InterruptedException {
		ChannelServiceImpl impl = new ChannelServiceImpl();
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);

		QueryReqVo vo = new QueryReqVo();
//		vo.setBizType(BizType.BROKER_COLLECT);
		vo.setBizType(BizType.BROKER_PAY);
		vo.setThirdType(ThirdType.ALLINPAY);
		vo.setBizSys(BizSys.ZENDAI_2003_SYS);
		vo.setTradeFlow("20150723111921");
		vo.setInfoCategoryCode("10000");

		Response response = impl.queryCommond(vo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + ",flow:" + response.getFlowId());
	}

	@Test
	public void shunionpayCommond() throws SQLException, InterruptedException {
		ChannelServiceImpl impl = new ChannelServiceImpl();
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);
		impl.setBankRequestFilter(bankRequestFilter);
		CollectReqVo vo = new CollectReqVo();

		vo.setBizType(BizType.BROKER_COLLECT);
		vo.setThirdType(ThirdType.SHUNIONPAY);
		vo.setBizSys(BizSys.ZENDAI_2004_SYS);
		vo.setInfoCategoryCode("10000");
		;
		vo.setAmount(new BigDecimal("15.93"));
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setPayerBankCode("10000");// 付款方银行支行行号
		vo.setPayerBankCardNo("6214850213301903");
		vo.setPayerName("待发");
		vo.setPayerIdType("1");// 付款方证件类型
		vo.setPayerId("12313fdsaf");// 付款方证件号
		vo.setCurrency("0");
		vo.setPayerBankCardType("1");

		Response response = impl.collectCommond(vo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + ",flow:" + response.getFlowId()
				+ "tradeFlow:" + vo.getTradeFlow());
	}
	
	@Test
	public void zendaipayCommond() throws SQLException, InterruptedException {
		ChannelServiceImpl impl = new ChannelServiceImpl();
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);
		impl.setBankRequestFilter(bankRequestFilter);
		CollectReqVo vo = new CollectReqVo();

		vo.setBizType(BizType.BROKER_COLLECT);
		vo.setThirdType(ThirdType.ZENDAIPAY);
		vo.setBizSys(BizSys.ZENDAI_2004_SYS);
		vo.setInfoCategoryCode("10000");
		;
		vo.setAmount(new BigDecimal("16.93"));
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setPayerBankCode("10000");// 付款方银行支行行号
		vo.setPayerBankCardNo("6214850213301903");
		vo.setPayerName("待发");
		vo.setPayerIdType("1");// 付款方证件类型
		vo.setPayerId("12313fdsaf");// 付款方证件号
		vo.setCurrency("0");
		vo.setPayerBankCardType("1");
		vo.setPayerAccountNo("88992222");

		Response response = impl.collectCommond(vo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + ",flow:" + response.getFlowId()
				+ "tradeFlow:" + vo.getTradeFlow());
	}
	
	@Test
	public void zendaiQueryCommond() throws SQLException, InterruptedException {
		ChannelServiceImpl impl = new ChannelServiceImpl();
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);

		QueryReqVo vo = new QueryReqVo();
		vo.setBizType(BizType.BROKER_COLLECT);
		vo.setThirdType(ThirdType.ZENDAIPAY);
		vo.setBizSys(BizSys.ZENDAI_2004_SYS);
		vo.setTradeFlow("20161017173841");
		vo.setInfoCategoryCode("10000");

		Response response = impl.queryCommond(vo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + ",flow:" + response.getFlowId());
	}
	
	@Test
	public void kjtpayCommond() throws SQLException, InterruptedException {
		ChannelServiceImpl impl = new ChannelServiceImpl();
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);
		impl.setBankRequestFilter(bankRequestFilter);
		CollectReqVo vo = new CollectReqVo();

		vo.setBizType(BizType.BROKER_COLLECT);
		vo.setThirdType(ThirdType.KJTPAY);
		vo.setBizSys(BizSys.ZENDAI_2004_SYS);
		vo.setInfoCategoryCode("10017");
		;
		vo.setAmount(new BigDecimal("0.01"));
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setPayerBankCode("00080003");// 付款方银行支行行号
		vo.setPayerBankCardNo("6222021001072465757");
		vo.setPayerName("郝文");
		vo.setPayerIdType("1");// 付款方证件类型
		vo.setPayerId("370983198706191356");// 付款方证件号
		vo.setCurrency("0");
		vo.setPayerBankCardType("1");
		vo.setPayerAccountNo("88992222");
		vo.setPayerIdType("0");
		
		Response response = impl.collectCommond(vo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + ",flow:" + response.getFlowId()
				+ "tradeFlow:" + vo.getTradeFlow());
	}
	
	@Test
	public void kjtQueryCommond() throws SQLException, InterruptedException {
		ChannelServiceImpl impl = new ChannelServiceImpl();
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);

		QueryReqVo vo = new QueryReqVo();
		vo.setBizType(BizType.BROKER_COLLECT);
		vo.setThirdType(ThirdType.KJTPAY);
		vo.setBizSys(BizSys.ZENDAI_2004_SYS);
		vo.setTradeFlow("20161230105327");
		vo.setInfoCategoryCode("20011");

		Response response = impl.queryCommond(vo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + ",flow:" + response.getFlowId());
	}


	@Test
	public void unspayCommond() throws SQLException, InterruptedException {
		ChannelServiceImpl impl = new ChannelServiceImpl();
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);
		impl.setBankRequestFilter(bankRequestFilter);
		CollectReqVo vo = new CollectReqVo();

		vo.setBizType(BizType.BROKER_COLLECT);
		vo.setThirdType(ThirdType.UNSPAY);
		vo.setBizSys(BizSys.ZENDAI_2004_SYS);
		vo.setInfoCategoryCode("20011");
		;
		vo.setAmount(new BigDecimal("16.93"));
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setPayerBankCode("00080003");// 付款方银行支行行号
		vo.setPayerBankCardNo("6222021001173465757");
		vo.setPayerName("待发");
		vo.setPayerIdType("1");// 付款方证件类型
		vo.setPayerId("370983198706191356");// 付款方证件号
		vo.setCurrency("0");
		vo.setPayerBankCardType("1");
		vo.setPayerAccountNo("88992222");
		vo.setPayerIdType("0");
		//测试逻辑:  62开头的卡 :  备注  purpose   00  交易会成功  。10  是处理中  。99 是余额不足
		Response response = impl.collectCommond(vo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + ",flow:" + response.getFlowId()
				+ "tradeFlow:" + vo.getTradeFlow());
	}
	@Test
	public void unspayQueryCommond() throws SQLException, InterruptedException {
		ChannelServiceImpl impl = new ChannelServiceImpl();
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);
		// 20170614170554  成功   20170620175142 处理中   20170620175449  失败
		QueryReqVo vo = new QueryReqVo();
		vo.setBizType(BizType.BROKER_COLLECT);
		vo.setThirdType(ThirdType.UNSPAY);
		vo.setBizSys(BizSys.ZENDAI_2004_SYS);
		vo.setTradeFlow("20170620175449");
		vo.setInfoCategoryCode("20011");
		vo.setOrderDate("2017-05-16 15:24:38");

		Response response = impl.queryCommond(vo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + ",flow:" + response.getFlowId());
	}

	@Test
	public void shunionQueryCommond() throws SQLException, InterruptedException {
		ChannelServiceImpl impl = new ChannelServiceImpl();
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);

		QueryReqVo vo = new QueryReqVo();
		vo.setBizType(BizType.BROKER_COLLECT);
		vo.setThirdType(ThirdType.SHUNIONPAY);
		vo.setBizSys(BizSys.ZENDAI_2004_SYS);
		vo.setTradeFlow("20150723113ew1");
		vo.setInfoCategoryCode("10000");

		Response response = impl.queryCommond(vo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + ",flow:" + response.getFlowId());
	}
	

	@Test
	public void fuioucollectCommond() throws SQLException, InterruptedException {
		ChannelServiceImpl impl = new ChannelServiceImpl();
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);
		impl.setBankRequestFilter(bankRequestFilter);

		CollectReqVo vo = new CollectReqVo();
		vo.setBizType(BizType.BROKER_COLLECT);
		vo.setThirdType(ThirdType.FUIOUPAY);
		vo.setBizSys(BizSys.ZENDAI_2003_SYS);
		vo.setAmount(new BigDecimal("1000"));
		vo.setPayerBankCode("006");
		vo.setPayerBankCardNo("6217857500007166082");
		vo.setPayerName("欧福");
		vo.setPayerId("45236819901223170X");
		vo.setPayerIdType("0");
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setPayerMobile("15987654321");
		vo.setInfoCategoryCode("10000");

		Response response = impl.collectCommond(vo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + "flow:" + response.getFlowId() + "tradeFlow:" + vo.getTradeFlow());
	}

	@Test
	public void fuiouQueryCommond() throws SQLException, InterruptedException {
		ChannelServiceImpl impl = new ChannelServiceImpl();
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);

		QueryReqVo vo = new QueryReqVo();
		vo.setBizType(BizType.BROKER_COLLECT);
		vo.setThirdType(ThirdType.FUIOUPAY);
		vo.setBizSys(BizSys.ZENDAI_2003_SYS);
		vo.setTradeFlow("0017074446280225");
		vo.setInfoCategoryCode("10000");
		vo.setOrderDate("2015/9/1 9:11:35");

		Response response = impl.queryCommond(vo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + ",flow:" + response.getFlowId());
	}
	
	@Test
	public void shunionAuthCommond() throws SQLException, InterruptedException {
		BaseChannelServiceImpl impl = new BaseChannelServiceImpl(); 
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);

		BankCardAuthReqVo bankCardAuthReqVo = new BankCardAuthReqVo();
		bankCardAuthReqVo.setBizType(BizType.CERTIFICATION);
		bankCardAuthReqVo.setThirdType(ThirdType.SHUNIONPAY_ACCOUNT_AUTH);
		bankCardAuthReqVo.setBizSys(BizSys.ZENDAI_2003_SYS);
		
		bankCardAuthReqVo.setUserName("王五");
		bankCardAuthReqVo.setCardNo("6227001217450018751");
		bankCardAuthReqVo.setCardType("1");
		bankCardAuthReqVo.setCertNo("340102198212062039");
//		bankCardAuthReqVo.setCertNo("231002198903302055");
		bankCardAuthReqVo.setCertType("1");
		bankCardAuthReqVo.setMobile("18221374856");
		bankCardAuthReqVo.setInfoCategoryCode("10001");
		bankCardAuthReqVo.setAuthFlow(CalendarUtils.getFormatNow());
		Response response = impl.bankCardAuthCommond(bankCardAuthReqVo);
		System.out.println("code:" + response.getCode() + ", msg:"
				+ response.getMsg() + ", flow:" + response.getFlowId() + ", tradeFlow:" + bankCardAuthReqVo.getAuthFlow() + ", bankretCode:"+ response.getBankRepCode());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shunionCardBinQueryCommond() throws SQLException, InterruptedException {
		BaseChannelServiceImpl impl = new BaseChannelServiceImpl(); 
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);

		BankCardBinQueryReqVO binQueryReqVO = new BankCardBinQueryReqVO();
		binQueryReqVO.setBizType(BizType.BIN_QUERY);
		binQueryReqVO.setThirdType(ThirdType.SHUNIONPAY_ACCOUNT_AUTH);
		binQueryReqVO.setBizSys(BizSys.ZENDAI_2003_SYS);
		
		binQueryReqVO.setCardNo("6226620607792207");
		binQueryReqVO.setInfoCategoryCode("10024");
		binQueryReqVO.setQueryFlow(CalendarUtils.getFormatNow());
		AttachmentResponse<BankCardBinQueryRespVO> response = (AttachmentResponse<BankCardBinQueryRespVO>)impl.bankCardBinQueryCommond(binQueryReqVO);
		
		logger.info(response);
		System.out.println("code:" + response.getCode() + ", msg:"
				+ response.getMsg() + ", flow:" + response.getFlowId() + ", bankretCode:"+ response.getBankRepCode());
		
	}
	
	
	@Test
	public void yyouRepayCommond() throws SQLException, InterruptedException {
		ChannelServiceImpl impl = new ChannelServiceImpl();
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);
		impl.setBankRequestFilter(bankRequestFilter);

		CollectReqVo vo = new CollectReqVo();
		vo.setBizType(BizType.BROKER_COLLECT);
		vo.setThirdType(ThirdType.YONGYOUUNIONPAY);
		vo.setBizSys(BizSys.ZENDAI_2003_SYS);
		vo.setAmount(new BigDecimal("1000"));
		vo.setPayerBankCode("006");
		vo.setPayerBankCardNo("6222622262226222");
		vo.setPayerId("45236819901223170X");
		vo.setPayerIdType("0");
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setInfoCategoryCode("10000");
		vo.setPayerName("mencius");
		
		vo.setPayerAccountNo("1000100");
		vo.setReceiverAccountNo("xintuo1");

		Response response = impl.collectCommond(vo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + "flow:" + response.getFlowId() + "tradeFlow:" + vo.getTradeFlow());
	}
	
	@Test
	public void yyouRepayQueryCommond() throws SQLException, InterruptedException {
		ChannelServiceImpl impl = new ChannelServiceImpl();
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);

		QueryReqVo vo = new QueryReqVo();
		vo.setBizType(BizType.BROKER_COLLECT);
		vo.setThirdType(ThirdType.YONGYOUUNIONPAY);
		vo.setBizSys(BizSys.ZENDAI_2003_SYS);
		vo.setTradeFlow("20150906155734");
		vo.setInfoCategoryCode("10000");
		
		Response response = impl.queryCommond(vo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + ",flow:" + response.getFlowId());
	}
		@Test
	public void yyouPayCommond() throws SQLException, InterruptedException {
		ChannelServiceImpl impl = new ChannelServiceImpl();
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);
		impl.setBankRequestFilter(bankRequestFilter);

		PayReqVo payReqVo = new PayReqVo();
		payReqVo.setBizType(BizType.BROKER_PAY);
		payReqVo.setThirdType(ThirdType.YONGYOUUNIONPAY);
		payReqVo.setBizSys(BizSys.ZENDAI_2004_SYS);
		payReqVo.setAmount(new BigDecimal("1000"));
		payReqVo.setCurrency("0");
		payReqVo.setFee(new BigDecimal("10"));
		payReqVo.setInfoCategoryCode("10004");
		payReqVo.setPayerAccountName("mencius");
		payReqVo.setPayerAccountNo("xintuo1");
		payReqVo.setReceiverAccountNo("24533753");
		payReqVo.setTradeFlow(CalendarUtils.getFormatNow());

		Response response = impl.payCommond(payReqVo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + "flow:" + response.getFlowId() + "tradeFlow:" + payReqVo.getTradeFlow());
	}
	
	
	@Test
	public void yyouBidPayQueryCommond() throws SQLException, InterruptedException {
		ChannelServiceImpl impl = new ChannelServiceImpl();
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);

		QueryReqVo vo = new QueryReqVo();
		vo.setBizType(BizType.BROKER_PAY);
		vo.setThirdType(ThirdType.YONGYOUUNIONPAY);
		vo.setBizSys(BizSys.ZENDAI_2004_SYS);
		vo.setTradeFlow("20151116141320");
		vo.setPayerAccountNo("24533753");
		vo.setOrderDate(CalendarUtils.getShortFormatNow());
		vo.setInfoCategoryCode("10004");
		
		Response response = impl.queryCommond(vo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + ",flow:" + response.getFlowId());
	}
	
	/**
	 * 银联代付测试Case
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void shunionPayCommond() throws SQLException, InterruptedException {
		ChannelServiceImpl impl = new ChannelServiceImpl();
		impl.setDispatchAction(dispatchAction);
		impl.setRequestService(requestService);
		impl.setChannelRequestFilter(channelRequestFilter);
		impl.setBankRequestFilter(bankRequestFilter);
		
		// 代付传输对象
		PayReqVo payReqVo = new PayReqVo();
		payReqVo.setBizType(BizType.BROKER_PAY);
		payReqVo.setThirdType(ThirdType.SHUNIONPAY);
		payReqVo.setBizSys(BizSys.ZENDAI_2004_SYS);
		
		payReqVo.setReceiverBankCardNo("6222600232143526");
		payReqVo.setReceiverName("mencius"); //
		payReqVo.setReceiverBankCode("00080002");
		payReqVo.setReceiverType("C"); // C：对公，P：对私
		
		payReqVo.setAmount(new BigDecimal("1000"));
		payReqVo.setInfoCategoryCode("10025");
		payReqVo.setTradeFlow(CalendarUtils.getFormatNow());

		Response response = impl.payCommond(payReqVo);
		System.out.println("code:" + response.getCode() + ",msg:"
				+ response.getMsg() + ",flow:" + response.getFlowId() + ",tradeFlow:" + payReqVo.getTradeFlow());
	}

}

