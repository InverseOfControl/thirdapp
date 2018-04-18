package com.zendaimoney.performance;

import com.zendaimoney.thirdpp.common.enums.BizSys;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.trade.pub.service.IBrokerTradeService;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.CollectReqVo;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.RequestVo;
import com.zendaimoney.util.TestHelperUtil;


//import com.zendaimoney.thirdpp.trade.pub.service.IBrokerTradeService;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BizTransferRequestHelper
{


	  //获取dubbo service实例
	  public static ApplicationContext context = new ClassPathXmlApplicationContext(
	    "spring-dubbo.xml");

	  public static IBrokerTradeService brokerTradeConsumer = (IBrokerTradeService)context
	    .getBean(IBrokerTradeService.class);
	  
  /**
   * 交易明细对象赋值
   * @return CollectReqVo
   */
	public CollectReqVo collectVoforBiz(String[] value) {
		CollectReqVo collectReqVo = new CollectReqVo();
//		collectReqVo.setPaySysNo(ThirdType.BAOFOOPAY.getCode());// 第三方系统编码
//		collectReqVo.setPayerName("张宝");// 付款方姓名
//		collectReqVo.setPayerBankCardNo("6222020111122220000");// 付款方银行卡号
//		collectReqVo.setPayerBankCardType("1");// 付款方银行卡类型 1.借记卡，2信用卡
//		collectReqVo.setPayerIdType("0");// 付款方证件类型
//		collectReqVo.setPayerId("320301198502169142");// 付款方证件号
//		collectReqVo.setPayerBankCode("00080003");// 付款方银行编码
//		collectReqVo.setCurrency("0");// 币种(0人民币)
//		collectReqVo.setAmount(new BigDecimal("5001.01"));// 金额
//		collectReqVo.setIsNeedPush(1);//1需要推送 0不需要推送		
//		collectReqVo.setIsNeedSpilt(1);//1需要拆单0不需要拆单
//		collectReqVo.setPayerMobile("15151611");
//		collectReqVo.setBizFlow(randomOrderNumber());// 业务流水号
		collectReqVo.setBizSysAccountNo(value[0]);      //业务系统客户号
		collectReqVo.setZengdaiAccountNo(value[1]);     //证大客户号
		collectReqVo.setReceiverAccountNo(value[2]);    //收款人账户号
		collectReqVo.setReveiverAccountName(value[3]);  //收款人账户姓名
		collectReqVo.setPayerName(value[4]);            //付款人姓名
		collectReqVo.setPayerBankCardNo(value[5]);      //付款人银行卡号
		collectReqVo.setPayerBankCardType(value[6]);    //付款人银行卡类型
		collectReqVo.setPayerIdType(value[7]);          //付款方证件类型
		collectReqVo.setPayerId(value[8]);              //付款方证件号
		collectReqVo.setPayerBankCode(value[9]);        //付款方银行编码
		collectReqVo.setPayerSubBankCode(value[10]);     //付款方银行支行行号
		collectReqVo.setPayerAccountNo(value[11]);       //付款方账户编号
		collectReqVo.setPayerMobile(value[12]);          //付款方手机号
		collectReqVo.setCurrency(value[13]);             //币种
		collectReqVo.setAmount(new BigDecimal(value[14]));               //金额
		collectReqVo.setFee(new BigDecimal(value[15]));                  //手续费
		collectReqVo.setBizRemark(value[16]);            //业务备注
		collectReqVo.setBizFlow(randomOrderNumber());              //业务流水号
		collectReqVo.setCreater(value[18]);              //创建人
		collectReqVo.setIsNeedPush(Integer.parseInt(value[19]));           //交易状态是否需要通知
		collectReqVo.setIsNeedSpilt(Integer.parseInt(value[20]));          //报盘是否需要拆单(限额超过后)
		collectReqVo.setPaySysNo(value[21]);             //第三方通道编码
		collectReqVo.setSpare1(value[22]);               //扩展字段1
		collectReqVo.setSpare2(value[23]);                 //扩展字段2
		return collectReqVo;
	}
  
	public static String randomOrderNumber() {
		String orderNumber = "";
		int orderNumberLength = 10;
		for (int i = 0; i < orderNumberLength; i++) {
			orderNumber += (int) (9 * (Math.random()) + 1);
		}
//		System.out.println(orderNumber);
		return orderNumber;
	}
  /**
   * 将明细对象列表放入请求对象BizReqVoList，返回请求对象
   * @param collectReqVoList
   * @return requestVo
   */
  public RequestVo requestVoforBiz(ArrayList<CollectReqVo> collectReqVoList,String[] value)
  {
		RequestVo requestVo = new RequestVo();
		requestVo.setBizSysNo(BizSys.ZENDAI_2004_SYS.getCode()); // 业务系统编码		
		requestVo.setInfoCategoryCode(value[1]); // 信息类别代码
		requestVo.setRemark(value[2]);
		requestVo.setSpare1(value[3]);
		requestVo.setSpare2(value[4]);
//		requestVo.setInfoCategoryCode("20011"); // 信息类别代码
//		requestVo.setRemark("宝付测试第二轮");
//		requestVo.setSpare1("BaofooTest2");
//		requestVo.setBizTypeNo("01");
//		requestVo.setBizSysNo(BizSys.ZENDAI_2004_SYS.getCode()); // 业务系统编码
//		requestVo.setBizSysNo("555"); // 业务系统编码

		for (int i = 0; i < collectReqVoList.size(); i++) {
			requestVo.getList().add(collectReqVoList.get(i));
		}
		return requestVo;
  }
}