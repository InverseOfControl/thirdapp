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
import com.zendaimoney.trust.channel.sei.Impl.CmbBatchCommandServiceImpl;
import com.zendaimoney.trust.channel.sei.Impl.TrustChannelServiceImpl;
import com.zendaimoney.trust.channel.service.RequestService;
import com.zendaimoney.trust.channel.util.CalendarUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/spring-channel.xml",
		"classpath:spring/applicationContext-task.xml"})
public class BcckActionTest {

	@Autowired
	private DispatchAction dispatchAction;

	@Autowired
	private RequestService requestService;

	@Autowired
	private ChannelRequestFilter channelRequestFilter;

	@Autowired
	private DispatchChannel dispatchChannel;
	
	@Test
	public void testBcck() {
		TrustChannelServiceImpl cmbBatchCommandServiceImpl = new TrustChannelServiceImpl();
		init(cmbBatchCommandServiceImpl);
		
		BcckReq bcckReq = new BcckReq();
		// 公共部分
		bcckReq.setBizSysNo("004");
		bcckReq.setBizType(BizType.TRUST_GOPER);
		bcckReq.setInfoCategoryCode("10026");
		bcckReq.setThirdType(ThirdType.CMBPAY);
		bcckReq.setTradeDate(CalendarUtils.getShortFormatNow());
		bcckReq.setTradeTime(CalendarUtils.getFormatNow().substring(8));
		bcckReq.setTradeFlow(createTradeFlow());
		bcckReq.setTrustBizType(TrustBizType.BCCK);
		bcckReq.setTrustCategory(TrustCategory.TRADE);
		
		// 文件首行
		bcckReq.setBalanceDate("20160402");
		bcckReq.setSpare("4test");
		
		Response response = cmbBatchCommandServiceImpl.tradeCommand(bcckReq);
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
		String s = "BDBBD2D7B3C9B9A6                                                                                                                                                                                                                                                                                                                              ";
		System.out.print(s.length());
	}
}
