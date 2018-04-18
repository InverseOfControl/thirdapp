package com.zendaimoney.cmb.channel.batch;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.AttachmentResponse;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.trust.channel.action.DispatchAction;
import com.zendaimoney.trust.channel.action.DispatchChannel;
import com.zendaimoney.trust.channel.filter.ChannelRequestFilter;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.vo.req.FileReq;
import com.zendaimoney.trust.channel.pub.vo.req.MrslDetailReq;
import com.zendaimoney.trust.channel.pub.vo.req.MrslReq;
import com.zendaimoney.trust.channel.sei.Impl.CmbBatchCommandServiceImpl;
import com.zendaimoney.trust.channel.service.RequestService;
import com.zendaimoney.trust.channel.util.CalendarUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/spring-channel.xml",
		"classpath:spring/applicationContext-task.xml"})
public class MrslBatchTest {

	@Autowired
	private DispatchAction dispatchAction;

	@Autowired
	private RequestService requestService;

	@Autowired
	private ChannelRequestFilter channelRequestFilter;

	@Autowired
	private DispatchChannel dispatchChannel;
	
	@Test
	public void testMrsl() {
		CmbBatchCommandServiceImpl cmbBatchCommandServiceImpl = new CmbBatchCommandServiceImpl();
		init(cmbBatchCommandServiceImpl);
		
		MrslReq mrslReq = new MrslReq();
		// 公共部分
		mrslReq.setBatchNo(00011);
		mrslReq.setBizSysNo("004");
		mrslReq.setBizType(BizType.TRUST_SETTLE);
		mrslReq.setInfoCategoryCode("10026");
		mrslReq.setThirdType(ThirdType.CMBPAY);
		mrslReq.setTradeFlow("0226671048042670");
		mrslReq.setTrustBizType(TrustBizType.MRSL);
		mrslReq.setTrustCategory(TrustCategory.BATCH_TRADE);
		
		// 文件首行
		mrslReq.setSettleDate("20160331");
		mrslReq.setThirdpartyIdentify("fuyou");
		mrslReq.setTotalCount(1);
		mrslReq.setTotalAmount(new BigDecimal(100));
		mrslReq.setActualInAmount(new BigDecimal(100));
		mrslReq.setAdvancePlatformUserNo("zdmoneyuserno0000");
		mrslReq.setAdvancePlatformVirtualSubNo("zdmoneyvirtualsubno1111");
		
		// 文件体
		MrslDetailReq mrslDetailReq = new MrslDetailReq();
		mrslDetailReq.setThirdpartyTradeflow("111100003333");
		mrslDetailReq.setUserNo("112031");
		mrslDetailReq.setVirtualSubNo("1219001002134010000000190");
		mrslDetailReq.setAmount(new BigDecimal(100));
		mrslDetailReq.setSpare("4test");
		
		List<MrslDetailReq> mrslList = new ArrayList<MrslDetailReq>();
		mrslList.add(mrslDetailReq);
		mrslReq.setAttachment(mrslList);
		
		cmbBatchCommandServiceImpl.build(mrslReq);
	}
	
	@Test
	public void testPull(){
		String fileName = "CMBAMRSL88880216542016041300009SR.TXT";
		CmbBatchCommandServiceImpl cmbBatchCommandServiceImpl = new CmbBatchCommandServiceImpl();
		init(cmbBatchCommandServiceImpl);
		FileReq fileReq = new FileReq();
		fileReq.setSpare(fileName);
		
		// 公共部分
		fileReq.setBatchNo(00011);
		fileReq.setBizSysNo("004");
		fileReq.setBizType(BizType.TRUST_SETTLE);
		fileReq.setInfoCategoryCode("10026");
		fileReq.setThirdType(ThirdType.CMBPAY);
		fileReq.setTradeFlow(createTradeFlow());
		fileReq.setTrustBizType(TrustBizType.MRSL);
		fileReq.setTrustCategory(TrustCategory.BATCH_TRADE);
		
		Response response = cmbBatchCommandServiceImpl.pull(fileReq);
		
		System.out.println("response code = " + response.getCode());
		System.out.println("response msg = " + response.getMsg());
		System.out.println("response flow id  = " + response.getFlowId());
	}
	
	@Test
	public void testParse(){
		String fileName = "CMBAMRSL88880216542016041300009SR.TXT";
		CmbBatchCommandServiceImpl cmbBatchCommandServiceImpl = new CmbBatchCommandServiceImpl();
		init(cmbBatchCommandServiceImpl);
		FileReq fileReq = new FileReq();
		
		// 公共部分
		fileReq.setSpare(fileName);
		fileReq.setBatchNo(00011);
		fileReq.setBizSysNo("004");
		fileReq.setBizType(BizType.TRUST_SETTLE);
		fileReq.setInfoCategoryCode("10026");
		fileReq.setThirdType(ThirdType.CMBPAY);
		fileReq.setTradeFlow( createTradeFlow());
		fileReq.setTrustBizType(TrustBizType.MRSL);
		fileReq.setTrustCategory(TrustCategory.BATCH_TRADE);
		
		Response response = cmbBatchCommandServiceImpl.parse(fileReq);
		
		System.out.println("response code = " + response.getCode());
		System.out.println("response msg = " + response.getMsg());
		System.out.println("response flow id  = " + response.getFlowId());
	}
	
	private void init(CmbBatchCommandServiceImpl cmbBatchCommandServiceImpl){
		cmbBatchCommandServiceImpl.setChannelRequestFilter(channelRequestFilter);
		cmbBatchCommandServiceImpl.setDispatchAction(dispatchAction);
		cmbBatchCommandServiceImpl.setRequestService(requestService);
	}
	
	private String createTradeFlow(){
		return "022"+CalendarUtils.getFormatNow();
	}
	
}
