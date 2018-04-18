package com.zendaimoney.thirdpp.transfer.service;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zendaimoney.thirdpp.common.enums.BizSys;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.transfer.entity.collect.CollectTask;
import com.zendaimoney.thirdpp.transfer.util.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/applicationContext.xml" })
public class CollectTaskServiceTest {

	@Autowired
	private CollectTaskService collectTaskService;

	@Test
	public void test() throws SQLException {
        CollectTask task = new CollectTask();
        task.setAmount(new BigDecimal(14));
        task.setBizFlow("bizFlow");
        task.setBizRemark("bizRemark");
        task.setBizSysAccountNo("bizSysAccountNo");
        task.setBizSysNo(BizSys.ZENDAI_2004_SYS.getCode());
        task.setCreater("creater");
        task.setCurrency(Constants.TppConstants.CURRENCY_RMB.getCode());
        task.setFee(new BigDecimal(8000));
       // task.setIsSeparate(Integer.valueOf(Constants.TppConstants.TASK_IS_SEPARATE_YES.getCode()));
       // task.setSeparateCount(4);
        task.setPayerBankCardNo("9558801001114990713");
        task.setPayerBankCardType(Constants.TppConstants.BANK_CARD_TYPE_CREDIT.getCode());
        task.setPayerBankCode("10000");
        task.setPayerId("payerId");
        task.setPayerIdType("2");
       // task.setPayerName("payerName");
        task.setPayerSubBankCode("1");
        task.setPaySysNo(ThirdType.ALLINPAY.getCode());
        task.setPriority(Integer.valueOf(Constants.TppConstants.PRIORITY_COMMON.getCode()));
        task.setReceiverAccountNo("receiverAccountNo");
        task.setReveiverAccountName("reveiverAccountName");
        task.setRemark("remark");
        task.setReqId("reqId");
        task.setSpare1("spare1");
        task.setSpare2("spare2");
        task.setZengdaiAccountNo("zengdaiAccountNo");
        task.setStatus(Integer.valueOf(Constants.TppConstants.SEND_STATUS_WAIT.getCode()));
        task.setBizTypeNo(BizType.BROKER_COLLECT.getCode());
        collectTaskService.insert(task);
	}

}
