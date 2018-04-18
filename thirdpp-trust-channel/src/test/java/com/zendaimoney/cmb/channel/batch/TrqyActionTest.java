package com.zendaimoney.cmb.channel.batch;

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
import com.zendaimoney.trust.channel.pub.vo.req.BcckReq;
import com.zendaimoney.trust.channel.pub.vo.req.FileReq;
import com.zendaimoney.trust.channel.pub.vo.req.TrqyReq;
import com.zendaimoney.trust.channel.sei.Impl.CmbBatchCommandServiceImpl;
import com.zendaimoney.trust.channel.sei.Impl.TrustChannelServiceImpl;
import com.zendaimoney.trust.channel.service.RequestService;
import com.zendaimoney.trust.channel.util.CalendarUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/spring-channel.xml",
		"classpath:spring/applicationContext-task.xml"})
public class TrqyActionTest {

	@Autowired
	private DispatchAction dispatchAction;

	@Autowired
	private RequestService requestService;

	@Autowired
	private ChannelRequestFilter channelRequestFilter;

	@Autowired
	private DispatchChannel dispatchChannel;
	
	@Test
	public void testTrqy() {
		TrustChannelServiceImpl cmbBatchCommandServiceImpl = new TrustChannelServiceImpl();
		init(cmbBatchCommandServiceImpl);
		
		TrqyReq trqyReq = new TrqyReq();
		// 公共部分
		trqyReq.setBizSysNo("004");
		trqyReq.setBizType(BizType.TRUST_GOPER);
		trqyReq.setInfoCategoryCode("10026");
		trqyReq.setThirdType(ThirdType.CMBPAY);
		trqyReq.setTradeDate(CalendarUtils.getShortFormatNow());
		trqyReq.setTradeTime(CalendarUtils.getFormatNow().substring(8));
		trqyReq.setTradeFlow(createTradeFlow());
		trqyReq.setTrustBizType(TrustBizType.TRQY);
		trqyReq.setTrustCategory(TrustCategory.TRADE);
		
		// 文件首行
		trqyReq.setUserNo("112031");
		trqyReq.setVirtualSubNo("1219001002134010000000190");
		trqyReq.setStartDate("20160401");
		trqyReq.setEndDate("20160402");
		trqyReq.setSpare("4test");
		
		Response response = cmbBatchCommandServiceImpl.tradeCommand(trqyReq);
		
		System.out.println("response code = " + response.getCode());
		System.out.println("response msg = " + response.getMsg());
		System.out.println("response flow id  = " + response.getFlowId());
		if (response instanceof AttachmentResponse) {
			AttachmentResponse attachmentResponse = (AttachmentResponse)response;
			System.out.println("response attachment = " + attachmentResponse.getAttachment());
		}
	}
	
	private void init(TrustChannelServiceImpl channelServiceImpl){
		channelServiceImpl.setDispatchChannel(dispatchChannel);
	}
	
	private void init(CmbBatchCommandServiceImpl trustChannelServiceImpl ){
		trustChannelServiceImpl.setChannelRequestFilter(channelRequestFilter);
		trustChannelServiceImpl.setDispatchAction(dispatchAction);
		trustChannelServiceImpl.setRequestService(requestService);
	}
	
	@Test
	public void testFile(){
		
		CmbBatchCommandServiceImpl trustChannelServiceImpl = new CmbBatchCommandServiceImpl();
		init(trustChannelServiceImpl);
		
		FileReq fileReq = new FileReq();
		// 公共部分
		fileReq.setBatchNo(00011);
		fileReq.setBizSysNo("004");
		fileReq.setBizType(BizType.TRUST_GOPER);
		fileReq.setInfoCategoryCode("10026");
		fileReq.setThirdType(ThirdType.CMBPAY);
		fileReq.setTradeDate(CalendarUtils.getShortFormatNow());
		fileReq.setTradeTime(CalendarUtils.getFormatNow().substring(8));
		fileReq.setTradeFlow(createTradeFlow());
		fileReq.setTrustBizType(TrustBizType.BCCK);
		fileReq.setTrustCategory(TrustCategory.BATCH_TRADE);
		fileReq.setBatchNo(15192);
		fileReq.setSpare("CMBABCCK88880216502016041215192SC.TXT");
		
		Response response = trustChannelServiceImpl.pull(fileReq);
		
		System.out.println("response code = " + response.getCode());
		System.out.println("response msg = " + response.getMsg());
		System.out.println("response flow id  = " + response.getFlowId());
		if (response instanceof AttachmentResponse) {
			AttachmentResponse attachmentResponse = (AttachmentResponse)response;
			System.out.println("response attachment = " + attachmentResponse.getAttachment());
		}
	}
	
	@Test
	public void testParse(){
		
		CmbBatchCommandServiceImpl trustChannelServiceImpl = new CmbBatchCommandServiceImpl();
		init(trustChannelServiceImpl);
		
		FileReq fileReq = new FileReq();
		// 公共部分
		fileReq.setBatchNo(00011);
		fileReq.setBizSysNo("004");
		fileReq.setBizType(BizType.TRUST_GOPER);
		fileReq.setInfoCategoryCode("10026");
		fileReq.setThirdType(ThirdType.CMBPAY);
		fileReq.setTradeDate(CalendarUtils.getShortFormatNow());
		fileReq.setTradeTime(CalendarUtils.getFormatNow().substring(8));
		fileReq.setTradeFlow(createTradeFlow());
		fileReq.setTrustBizType(TrustBizType.BCCK);
		fileReq.setTrustCategory(TrustCategory.BATCH_TRADE);
		fileReq.setBatchNo(15192);
		fileReq.setSpare("CMBABCCK88880216502016041215192SC.TXT");
		
		Response response = trustChannelServiceImpl.parse(fileReq);
		
		System.out.println("response code = " + response.getCode());
		System.out.println("response msg = " + response.getMsg());
		System.out.println("response flow id  = " + response.getFlowId());
		if (response instanceof AttachmentResponse) {
			AttachmentResponse attachmentResponse = (AttachmentResponse)response;
			System.out.println("response attachment = " + attachmentResponse.getAttachment());
		}
	}
	
	private String createTradeFlow(){
		return "022"+CalendarUtils.getFormatNow();
	}
	
	public static void main(String[] args) {
		String s = "DAIYUANCHENG                                                                    ";
		System.out.print(s.length());
	}
}
