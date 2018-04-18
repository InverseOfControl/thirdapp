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
import com.zendaimoney.trust.channel.pub.vo.req.DrslDetailReq;
import com.zendaimoney.trust.channel.pub.vo.req.DrslReq;
import com.zendaimoney.trust.channel.pub.vo.req.FileReq;
import com.zendaimoney.trust.channel.sei.Impl.CmbBatchCommandServiceImpl;
import com.zendaimoney.trust.channel.sei.Impl.TrustChannelServiceImpl;
import com.zendaimoney.trust.channel.service.RequestService;
import com.zendaimoney.trust.channel.util.CalendarUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/spring-channel.xml",
		"classpath:spring/applicationContext-task.xml"})
public class DrslActionTest {

	@Autowired
	private DispatchAction dispatchAction;

	@Autowired
	private RequestService requestService;

	@Autowired
	private ChannelRequestFilter channelRequestFilter;

	@Autowired
	private DispatchChannel dispatchChannel;
	
	@Test
	public void testDrsl() {
		
		CmbBatchCommandServiceImpl cmbBatchCommandService = new CmbBatchCommandServiceImpl();
		init(cmbBatchCommandService);
		
		DrslReq drslReq = new DrslReq();
		// 公共部分
		drslReq.setBizSysNo("004");
		drslReq.setBizType(BizType.TRUST_CLEARING);
		drslReq.setInfoCategoryCode("10026");
		drslReq.setThirdType(ThirdType.CMBPAY);
		drslReq.setTradeDate(CalendarUtils.getShortFormatNow());
		drslReq.setTradeTime(CalendarUtils.getFormatNow().substring(8));
		drslReq.setTradeFlow("02266710480426077");
		drslReq.setTrustBizType(TrustBizType.DRSL);
		drslReq.setTrustCategory(TrustCategory.BATCH_TRADE);
		
		// 文件首行
		drslReq.setSettleDate("20160401");
		drslReq.setTotalCount(1);
		drslReq.setTotalAmount(new BigDecimal(1.00));
		drslReq.setActualInAmount(new BigDecimal(1.00));
		drslReq.setAdvancePlatformUserNo("112032");
		drslReq.setAdvancePlatformVirtualSubNo("1219001002134010000000191");
		drslReq.setSpare("4test");
		
		// 文件明细
		List<DrslDetailReq> attachment = new ArrayList<DrslDetailReq>();
		
		DrslDetailReq detailReq = new DrslDetailReq();
		detailReq.setUserNo("112032");
		detailReq.setVirtualSubNo("1219001002134010000000190");
		detailReq.setAmount(new BigDecimal(1.00));
		detailReq.setSpare("4test");
		attachment.add(detailReq);
		
		drslReq.setAttachment(attachment);
		
		Response response = cmbBatchCommandService.build(drslReq);
		System.out.println("response code = " + response.getCode());
		System.out.println("response msg = " + response.getMsg());
		System.out.println("response flow id  = " + response.getFlowId());
		if (response instanceof AttachmentResponse) {
			AttachmentResponse attachmentResponse = (AttachmentResponse)response;
			System.out.println("response attachment = " + attachmentResponse.getAttachment());
		}
	}
	
	@Test
	public void testPull(){
		CmbBatchCommandServiceImpl cmbBatchCommandService = new CmbBatchCommandServiceImpl();
		init(cmbBatchCommandService);
		
		FileReq fileReq = new FileReq();
		// 公共部分
		fileReq.setBizSysNo("004");
		fileReq.setBizType(BizType.TRUST_CLEARING);
		fileReq.setInfoCategoryCode("10026");
		fileReq.setThirdType(ThirdType.CMBPAY);
		fileReq.setTradeDate(CalendarUtils.getShortFormatNow());
		fileReq.setTradeTime(CalendarUtils.getFormatNow().substring(8));
		fileReq.setTradeFlow("02266710480426081");
		fileReq.setTrustBizType(TrustBizType.DRSL);
		fileReq.setTrustCategory(TrustCategory.BATCH_TRADE);
		fileReq.setSpare("CMBADRSL88880216542016041400000SR.TXT");
		
		Response response = cmbBatchCommandService.pull(fileReq);
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
		CmbBatchCommandServiceImpl cmbBatchCommandService = new CmbBatchCommandServiceImpl();
		init(cmbBatchCommandService);
		
		FileReq fileReq = new FileReq();
		// 公共部分
		fileReq.setBizSysNo("004");
		fileReq.setBizType(BizType.TRUST_CLEARING);
		fileReq.setInfoCategoryCode("10026");
		fileReq.setThirdType(ThirdType.CMBPAY);
		fileReq.setTradeDate(CalendarUtils.getShortFormatNow());
		fileReq.setTradeTime(CalendarUtils.getFormatNow().substring(8));
		fileReq.setTradeFlow("02266710480426083");
		fileReq.setTrustBizType(TrustBizType.DRSL);
		fileReq.setTrustCategory(TrustCategory.BATCH_TRADE);
		fileReq.setSpare("CMBADRSL88880216542016041400000SR.TXT");
		
		Response response = cmbBatchCommandService.parse(fileReq);
		System.out.println("response code = " + response.getCode());
		System.out.println("response msg = " + response.getMsg());
		System.out.println("response flow id  = " + response.getFlowId());
		if (response instanceof AttachmentResponse) {
			AttachmentResponse attachmentResponse = (AttachmentResponse)response;
			System.out.println("response attachment = " + attachmentResponse.getAttachment());
		}
	}
	
	private void init(CmbBatchCommandServiceImpl cmbBatchCommandService){
		cmbBatchCommandService.setChannelRequestFilter(channelRequestFilter);
		cmbBatchCommandService.setDispatchAction(dispatchAction);
		cmbBatchCommandService.setRequestService(requestService);
	}
	
	private void init(TrustChannelServiceImpl trustChannelServiceImpl){
		trustChannelServiceImpl.setDispatchChannel(dispatchChannel);
	}
	
	@Test
	public void testFile(){
		TrustChannelServiceImpl trustChannelServiceImpl = new TrustChannelServiceImpl();
		init(trustChannelServiceImpl);
		
		FileReq fileReq = new FileReq();
		// 公共部分
		fileReq.setBizSysNo("004");
		fileReq.setBizType(BizType.TRUST_CLEARING);
		fileReq.setInfoCategoryCode("10026");
		fileReq.setThirdType(ThirdType.CMBPAY);
		fileReq.setTradeDate(CalendarUtils.getShortFormatNow());
		fileReq.setTradeTime(CalendarUtils.getFormatNow().substring(8));
		fileReq.setTradeFlow(createTradeFlow());
		fileReq.setTrustBizType(TrustBizType.FILE);
		fileReq.setTrustCategory(TrustCategory.TRADE);
		fileReq.setReqFileName("CMBADRSL88880216542016041400000SC.TXT");
		fileReq.setSpare("4test");
		
		Response response = trustChannelServiceImpl.tradeCommand(fileReq);
		
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
		String s = "CMBMB99DAIYUANCHENG                                                                    ";
		System.out.print(s.length());
	}	
}
