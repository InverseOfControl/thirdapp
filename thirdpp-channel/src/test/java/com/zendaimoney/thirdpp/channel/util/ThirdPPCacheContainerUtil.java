package com.zendaimoney.thirdpp.channel.util;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zendaimoney.thirdpp.channel.service.ThirdFieldMapperService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class ThirdPPCacheContainerUtil {
	
	@Autowired
	ThirdFieldMapperService bankCodeMapperService = null;
	
	@Test
	public void test(){
		
		
		Map<String, String> map = ThirdPPCacheContainer.tppBankCodeToThirdBankCodeMap;
		for(Map.Entry<String, String> entry: map.entrySet()) {

			 System.out.print(entry.getKey() + ":" + entry.getValue() + "\t");

			}
		
	}

}
