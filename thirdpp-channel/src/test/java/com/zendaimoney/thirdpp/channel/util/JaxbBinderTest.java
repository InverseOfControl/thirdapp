package com.zendaimoney.thirdpp.channel.util;

import junit.framework.Assert;

import org.junit.Test;

import com.zendaimoney.thirdpp.channel.dto.req.allinpay.collect.trade.CollectReq;
import com.zendaimoney.thirdpp.channel.dto.req.allinpay.collect.trade.ReqBody;
import com.zendaimoney.thirdpp.channel.dto.req.allinpay.collect.trade.ReqHeader;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;

public class JaxbBinderTest {

	@Test
	public void testObj2Xml() {
		try {
			JaxbBinder jb = new JaxbBinder(CollectReq.class);
			CollectReq req = new CollectReq();
			ReqHeader header = new ReqHeader();
			header.setDataType("2");
			header.setLevel("5");
			header.setReqSn("1377051780610");
			header.setSignedMsg("4b560e777597045d7273e73865671f3af7a34db15a3000eb7f5247090137f8e1feec91977364b6716f7407a7e540a8664048029832c18fdfcb37086bd2462310644a8ba3f81ce2001959219e7564dbb83a15aeae579ac814853be5e6bac7047dd108dcd37790b4ea0956118924c3a08e9e72e0baacfc74ba0298ac627769c07f");
			header.setTrxCode("100011");
			header.setUserName("20058100000051902");
			header.setUserPass("111111");
			header.setVersion("03");
			ReqBody body = new ReqBody();
			body.setAccountName("测试试");
			body.setAccountNo("622588121251757643");
			body.setAccountProp("0");
			body.setMerchantId("200581000000519");
			body.setSubmitTime("20130821102300");
			body.setBusinessCode("10600");
			body.setBankCode("0105");
			body.setTel("13434245846");
			body.setCustUserId("252523524253xx");
			req.setHeader(header);
			req.setBody(body);
			req.setBizType(BizType.TRUST_FINANCE);
			req.setThirdType(ThirdType.SHUNIONPAY);
			String collectReqStr = jb.toXml(req, "GBK");
			System.out.println(collectReqStr);


		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

}
