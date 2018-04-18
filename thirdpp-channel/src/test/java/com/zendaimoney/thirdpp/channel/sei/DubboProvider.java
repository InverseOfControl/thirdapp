package com.zendaimoney.thirdpp.channel.sei;

import com.alibaba.dubbo.container.Main;

public class DubboProvider {
	
	public static void main(String[] args) {
		System.setProperty("dubbo.spring.config", "classpath*:spring/applicationContext.xml");
		Main.main(null);
	}
}
