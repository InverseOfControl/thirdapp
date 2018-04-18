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
import com.zendaimoney.trust.channel.pub.vo.req.UsraReq;
import com.zendaimoney.trust.channel.sei.Impl.TrustChannelServiceImpl;
import com.zendaimoney.trust.channel.service.RequestService;
import com.zendaimoney.trust.channel.util.CalendarUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/spring-channel.xml",
		"classpath:spring/applicationContext-task.xml"})
public class UsraActionTest {

	@Autowired
	private DispatchAction dispatchAction;

	@Autowired
	private RequestService requestService;

	@Autowired
	private ChannelRequestFilter channelRequestFilter;

	@Autowired
	private DispatchChannel dispatchChannel;
	
	@Test
	public void testUsra() {
		
		TrustChannelServiceImpl cmbBatchCommandServiceImpl = new TrustChannelServiceImpl();
		init(cmbBatchCommandServiceImpl);
		
		UsraReq vo = new UsraReq();
		vo.setTrustBizType(TrustBizType.USRA);//业务类型不存在
		vo.setTrustCategory(TrustCategory.TRADE);
		vo.setThirdType(ThirdType.CMBPAY);
		vo.setBizType(BizType.TRUST_OPEN_ACCOUNT);
		vo.setInfoCategoryCode("10026");
		vo.setBizSysNo("9999");
		vo.setTradeFlow(createTradeFlow());
		vo.setTradeDate(CalendarUtils.getShortFormatNow());
		vo.setTradeTime(CalendarUtils.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME));
	 
		vo.setUserNo("162006"); // 用户号1000001(已开)
		vo.setUserName("张蒙"); // 用户姓名
		vo.setUserType("P"); // 用户类型
		vo.setIdType("5"); // 证件类型
		vo.setIdNo("411421198706123272");
		vo.setMobile("18221374888");
	
		Response response = cmbBatchCommandServiceImpl.tradeCommand(vo);
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
		return "005"+CalendarUtils.getFormatNow();
	}
}
