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
import com.zendaimoney.trust.channel.pub.vo.req.ScckReq;
import com.zendaimoney.trust.channel.sei.Impl.TrustChannelServiceImpl;
import com.zendaimoney.trust.channel.service.RequestService;
import com.zendaimoney.trust.channel.util.CalendarUtils;
import com.zendaimoney.trust.channel.util.LogPrn;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/spring-channel.xml",
		"classpath:spring/applicationContext-task.xml"})
public class ScckActionTest {

	private static final LogPrn logger = new LogPrn(
			ScckActionTest.class);
	
	@Autowired
	private DispatchAction dispatchAction;

	@Autowired
	private RequestService requestService;

	@Autowired
	private ChannelRequestFilter channelRequestFilter;

	@Autowired
	private DispatchChannel dispatchChannel;
	
	@Test
	public void testScck() {
		TrustChannelServiceImpl cmbBatchCommandServiceImpl = new TrustChannelServiceImpl();
		init(cmbBatchCommandServiceImpl);
		
		ScckReq scckReq = new ScckReq();
		// 公共部分
		scckReq.setBatchNo(00011);
		scckReq.setBizSysNo("004");
		scckReq.setBizType(BizType.TRUST_GOPER);
		scckReq.setInfoCategoryCode("10026");
		scckReq.setThirdType(ThirdType.CMBPAY);
		scckReq.setTradeDate(CalendarUtils.getShortFormatNow());
		scckReq.setTradeTime(CalendarUtils.getFormatNow().substring(8));
		scckReq.setTradeFlow(createTradeFlow());
		scckReq.setTrustBizType(TrustBizType.SCCK);
		scckReq.setTrustCategory(TrustCategory.TRADE);
		
		// 文件首行
		scckReq.setAccountDate("20160405");
		scckReq.setSpare("4test");
		
		Response response = cmbBatchCommandServiceImpl.tradeCommand(scckReq);
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
	
	private String createTradeFlow(){
		return "022"+CalendarUtils.getFormatNow();
	}
	
	/*public static void main(String[] args) {
		String s = "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        ";
		System.out.print(s.length());
	}*/
}
