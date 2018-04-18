package com.zendaimoney.performance;

import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.trade.pub.service.IBrokerTradeService;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.CollectReqVo;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.RequestVo;

import java.util.ArrayList;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.springframework.test.context.ContextConfiguration;
/**
 * 同步接口并发测试脚本
 * @author 00222809
 *
 */
@ContextConfiguration(locations={"classpath:spring-dubbo.xml"})
public class SyncCollecting extends AbstractJavaSamplerClient
{
  BizTransferRequestHelper bizReqeustHelper = new BizTransferRequestHelper();
  public IBrokerTradeService brokerTradeConsumer;
  private static long start = 0L;
  private static long end = 0L;

  public void setupTest(JavaSamplerContext arg0)
  {
    start = System.currentTimeMillis();
  }

  public void prepareTest(JavaSamplerContext arg0)
  {
  }

  public SampleResult runTest(JavaSamplerContext arg0)
  {
    prepareTest(arg0);
    SampleResult results = new SampleResult();
    results.sampleStart();

//    CollectReqVo collectReqVo = this.bizReqeustHelper.collectVoforBiz();

    ArrayList<CollectReqVo> collectReqVoList = new ArrayList<CollectReqVo>();
//    collectReqVoList.add(collectReqVo);

//    RequestVo requestVo = this.bizReqeustHelper.requestVoforBiz(collectReqVoList);

//    Response response = BizTransferRequestHelper.brokerTradeConsumer.syncCollect(requestVo);
//    Response response = brokerTradeConsumer.syncCollect(requestVo);
//    System.out.println(response.getCode() + " Msg = " + response.getMsg());

//    if (response.getCode().equals("000000"))
//    {
//      results.setSuccessful(true);
//    }
//    else {
//      results.setSuccessful(false);
//    }
    results.sampleEnd();
    return results;
  }

  public void teardownTest(JavaSamplerContext arg0) {
    end = System.currentTimeMillis();
    System.out.println("The cost is " + (end - start) / 1000L + "s");
  }
  
	 public static void main(String[] args) {
	 JavaSamplerContext arg0 = new JavaSamplerContext(new Arguments());
	 SyncCollecting test = new SyncCollecting();
	 test.setupTest(arg0);
	 test.runTest(arg0);
	 test.teardownTest(arg0);
	 }
}