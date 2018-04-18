package com.zendaimoney.thirdpp.trade.service;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zendaimoney.thirdpp.trade.entity.SysApp;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class SysAppServiceTest {
	
	
	@Autowired
	public SysAppService sysAppService;
	
	@Test
	public void querySysApp() throws SQLException {
		
		SysApp sysApp = sysAppService.querySysApp("004");
		
		System.out.println("appName=" + sysApp.getAppName());
		System.out.println("orderPayNotifyUrl=" + sysApp.getOrderPayNotifyUrl());
		
	}

}
