package com.zendaimoney.thirdpp;

import com.alibaba.dubbo.container.Main;

public class DubboProvider {
	
	public static void main(String[] args) {
		System.setProperty("dubbo.spring.config", "classpath*:spring/spring-context.xml");
		Main.main(null);
	}
}
