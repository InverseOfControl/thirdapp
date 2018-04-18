package com.zendaimoney.performance;


import java.util.ArrayList;
import java.util.List;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.zendaimoney.performance.BizTransferRequestHelper;
import com.zendaimoney.util.ReadExcleHelper;

import com.zendaimoney.thirdpp.common.vo.Response;

import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.CollectReqVo;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.RequestVo;


/**
 * 异步接口并发测试脚本
 * @author 00222809
 *
 */
public class AsyncCollectingTest extends AbstractJavaSamplerClient {
	
	private BizTransferRequestHelper bizReqeustHelper = new BizTransferRequestHelper();
	
	
	ReadExcleHelper readExcleHelper = new ReadExcleHelper();
	private static long start = 0;
    private static long end = 0;
	
	//初始化方法，实际运行时每个线程仅执行一次，在测试方法运行前执行，类似于LoadRunner中的init方法
    public void setupTest(JavaSamplerContext arg0) {
    	
    	start = System.currentTimeMillis();//获取当前系统时间
    	System.out.println("The System time is:"+ start );
    }
    
    public void prepareTest(JavaSamplerContext arg0){
       
    }
    
    //测试执行的循环体，根据线程数和循环次数的不同可执行多次，类似于LoadRunner中的Action方法
//    @Test(groups="asyscTest")
    
    

    public SampleResult runTest(JavaSamplerContext arg0) {
    	
    	 prepareTest(arg0);
         SampleResult	results = new SampleResult();
         results.sampleStart(); 
 		int requestNum = 1;
 		//交易明细对象
 		CollectReqVo collectReqVo = null;
 		CollectReqVo butai = null;
 		//交易明细list
 		ArrayList<CollectReqVo> collectReqVoList = new ArrayList<CollectReqVo>();
 		List<String[]> sheet_collect;
		sheet_collect = readExcleHelper.getDataList(
				ReadExcleHelper.COLLECT_CASE_DATA,
				ReadExcleHelper.ArrayList_sheet_ALL);
		String[] line ;
 		for (int i = 0; i < sheet_collect.size(); i++) {
 			line = readExcleHelper.getCaseData(sheet_collect,i);
 			collectReqVo = bizReqeustHelper.collectVoforBiz(line);
 			collectReqVoList.add(collectReqVo);
 		}
 		
 		//获得requestVo
		String[] line2;
		List<String[]> sheet_request;
		
		sheet_request = readExcleHelper.getDataList(
				ReadExcleHelper.COLLECT_CASE_DATA,
				ReadExcleHelper.RequestVo_Data_sheet);
		line2 = readExcleHelper.getCaseData(sheet_request,0);
 		RequestVo requestVo = bizReqeustHelper.requestVoforBiz(collectReqVoList,line2);
 		
 		long s1 = System.currentTimeMillis();
 		//通过dubbo服务调用异步代收接口
 		System.out.println("begin get response");
 		Response response = BizTransferRequestHelper.brokerTradeConsumer.asynCollect(requestVo);
 		System.out.println("end get response");
 		long s2 = System.currentTimeMillis();
 		System.out.println(s2 - s1); //计算异步请求时间
 		System.out.println(response.getCode() + ": " + response.getMsg() + "   "+response.getFlowId());
 		//设置检查点
//         //报盘数量
//         int requestNum = 1;
// 		//交易明细对象
// 		CollectReqVo collectReqVo = null;
// 		//交易明细list
//
// 		ArrayList<CollectReqVo> collectReqVoList = new ArrayList<CollectReqVo>();
//
// 		for (int i = 1; i <= requestNum; i++) {
// 			collectReqVo = bizReqeustHelper.collectVoforBiz();
// 			System.out.println(collectReqVo.getBizFlow());
// 			collectReqVoList.add(collectReqVo);
// 		}
//
// 		//获得requestVo
// 		RequestVo requestVo = new RequestVo();
// 		requestVo =	bizReqeustHelper.requestVoforBiz(collectReqVoList);
//
// 		long s1 = System.currentTimeMillis();
// 		//通过dubbo服务调用异步代收接口
// 		System.out.println("Begin to get response");
//// 		System.out.println(requestVo.getBizSysNo()+" " +requestVo.getBizTypeNo() + " " +requestVo.getList().get(0));
//// 		Response response = bizReqeustHelper.brokerTradeConsumer.asynCollect(requestVo);
//// 		try{
//// 			Response response = new Response();
//
// 			Response response = brokerTradeConsumer.asynCollect(requestVo);
//// 			response = brokerTradeConsumer.asynCollect(requestVo);
// 			
// 			System.out.println("The response is :" + response);

 		
// 		long s2 = System.currentTimeMillis();
 		
// 		System.out.println("the request cost = " + (s2 - s1) / 1000L + "s"); //计算异步请求消耗时间
// 		
// 		
// 		System.out.println(response.getCode() + " Msg = " + response.getMsg());
 		
 		if(response.getCode().equals("000000"))
 		{
 			results.setSuccessful(true);
 		}
 		else{
 			results.setSuccessful(false);
 		}
         results.sampleEnd();
//         System.out.println(results.gets);
         return results;
    }	
    //结束方法，实际运行时每个线程仅执行一次，在测试方法运行结束后执行，类似于LoadRunner中的end方法
    public void teardownTest(JavaSamplerContext arg0) {
    	end = System.currentTimeMillis();
    	System.out.println("per transaction cost time is " + (end - start) / 1000L + "s");
	}
    
	 public static void main(String[] args) {
	 JavaSamplerContext arg0 = new JavaSamplerContext(new Arguments());
	 System.out.println("Now begin to start AsyncCollectingTest");
	 AsyncCollectingTest test = new AsyncCollectingTest();
	 System.out.println("Progress Test start");
	 test.setupTest(arg0);
	 test.runTest(arg0);
	 test.teardownTest(arg0);
	 }
}
