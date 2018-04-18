package com.zendaimoney.performance;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.zendaimoney.thirdpp.vo.RequestDetailInfoVo;
import com.zendaimoney.thirdpp.vo.RequestDetailVo;
import com.zendaimoney.thirdpp.vo.RequestVo;
import com.zendaimoney.thirdpp.vo.biz.BizTransferRequestVo;
import com.zendaimoney.thirdpp.vo.enums.IsAsyn;
import com.zendaimoney.thirdpp.vo.enums.RequestType;
import com.zendaimoney.thirdpp.vo.enums.ThirdPartyType;
import com.zendaimoney.thirdpp.vo.enums.ZendaiSys;
import com.zendaimoney.thirdpp.vo.expand.Allinpay;
import com.zendaimoney.thirdpp.vo.expand.Fuiou;
import com.zendaimoney.thirdpp.vo.expand.SZUnionpay;
import com.zendaimoney.thirdpp.vo.expand.TPPEnum;
import com.zendaimoney.util.BaseTestHelper;
import com.zendaimoney.util.TestHelperUtil;

public class BizTransferRequestTestHelperImpl extends BaseTestHelper {

	public BizTransferRequestVo requestWithAllObjectsForTransferVo() {
		BizTransferRequestVo btr = new BizTransferRequestVo();
		Map<TPPEnum, String> expandMap1 = new LinkedHashMap<TPPEnum, String>();
		btr.setExpandProperties(expandMap1);
		btr.setOrdNo(TestHelperUtil.randomOrderNumber());
		btr.setPayBank("00080168");
		btr.setPayBankId("6217920105506872");
		btr.setPayName("AAA");
		btr.setAmount(new BigDecimal("0.1"));
		btr.setPayCardType(0);
		btr.setPayCardId("222401198510211818");
		return btr;
	}
	
	public BizTransferRequestVo requestWithAllTransferVo(ThirdPartyType thirdPartyType) {
		BizTransferRequestVo btr = new BizTransferRequestVo();
		Map<TPPEnum, String> expandMap1 = new LinkedHashMap<TPPEnum, String>();
		if(null !=thirdPartyType && thirdPartyType.equals(ThirdPartyType.Fuiou)){
			SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMdd");
			expandMap1.put(Fuiou.BizTransferReq.merdt, sdf.format(new Date()));
			expandMap1.put(Fuiou.BizTransferReq.entseq, "1234567890");
			expandMap1.put(Fuiou.BizTransferReq.mobile, "15052051357");
			expandMap1.put(Fuiou.BizTransferReq.branchnm, "福建省厦门市莲前西路招商银行支行");
			expandMap1.put(Fuiou.BizTransferReq.cityno, "0592");
		}
		else{
			expandMap1.put(SZUnionpay.BizTransferReq.AccountType, "11");// 账户类型： 11=个人账户,12=企业账户
		}
		btr.setExpandProperties(expandMap1);
		btr.setOrdNo(TestHelperUtil.randomOrderNumber());
		btr.setPayBank("00080168");
		btr.setPayBankId("6217920105506872");
		btr.setPayName("AAA");
		btr.setAmount(new BigDecimal("0.1"));
		btr.setPayCardType(0);
		btr.setPayCardId("222401198510211818");
		return btr;
	}
	
	public BizTransferRequestVo requestWithAllTransferVoForYongyou() {
		Map<TPPEnum, String> expandMap1 = new LinkedHashMap<TPPEnum, String>();
		BizTransferRequestVo btr = new BizTransferRequestVo();

		//浦东发展银行--成功
		//用友无扩展字段
		btr.setExpandProperties(expandMap1);
		//还款对应的信贷端流水号
		btr.setOrdNo(TestHelperUtil.randomOrderNumber());
		//还款用户ID
		btr.setPayBankId("12345688");
		//信托账号
		btr.setRecBankId("xintuo");
		btr.setPayName("沈月月");
		btr.setAmount(new BigDecimal("100.02"));
		//银行卡号
		btr.setPayBank("6217850800001220399");
		//证件号
		btr.setPayCardId("342501199004106828");
//		btr.setRecName("信托");//备用
//		btr.setRecBank("中行-金额划扣0.01元");//备用
		return btr;
	}
	
	public RequestVo requestWithAllNessassaryObjects(
			BizTransferRequestVo bizTransferRequestVo, ThirdPartyType thirdPartyType,ZendaiSys zdsys) {
		List<RequestDetailVo> requestDetails = new ArrayList<RequestDetailVo>();
			requestDetails.add(bizTransferRequestVo);
		RequestDetailInfoVo requestDetailInfoVo = new RequestDetailInfoVo(
				requestDetails);

		RequestVo request = new RequestVo();
		request.setRequestOperator("同步接口");
		request.setRequestType(RequestType.COLLECTING);
		request.setThirdPartyType(thirdPartyType);
		request.setRequestSystem(zdsys);
		request.setAsynTag(IsAsyn.IS_ASYN_TAG_0);
		request.setRequestDetailInfo(requestDetailInfoVo);
		long s1 = System.currentTimeMillis();
		RequestVo response = thirdPaymentService.innerTransfer(request);
		long s2 = System.currentTimeMillis();
		System.out.println("实时同步接口耗时" + (s2 - s1));
		return response;
	}

	// 返回可用RequestVo类型（即Request所有必填项都存在）
	public RequestVo requestWithAllNessassaryObjectsAsync(
			ArrayList<BizTransferRequestVo> bizTransferRequestVoList,ThirdPartyType thirdPartyType,ZendaiSys zdsys) {
		List<RequestDetailVo> requestDetails = new ArrayList<RequestDetailVo>();
		for (int i = 0; i < bizTransferRequestVoList.size(); i++) {
			requestDetails.add(bizTransferRequestVoList.get(i));
		}
		RequestDetailInfoVo requestDetailInfoVo = new RequestDetailInfoVo(
				requestDetails);

		RequestVo request = new RequestVo();
		request.setRequestOperator("异步接口");
		request.setRequestType(RequestType.COLLECTING);
		request.setThirdPartyType(thirdPartyType);
		request.setRequestSystem(zdsys);
		request.setAsynTag(IsAsyn.IS_ASYN_TAG_1);
		request.setRequestDetailInfo(requestDetailInfoVo);
		long s1 = System.currentTimeMillis();
		RequestVo response = thirdPaymentService.innerTransferAsync(request);
		long s2 = System.currentTimeMillis();
		System.out.println("异步接口耗时" + (s2 - s1));
		return response;
	}
}
