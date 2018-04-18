package com.zendaimoney.thirdpp.alarm.sms;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.zendaimoney.thirdpp.alarm.common.Monitor;
import com.zendaimoney.thirdpp.alarm.common.MonitorService;
import com.zendaimoney.thirdpp.alarm.util.Constants;
import com.zendaimoney.thirdpp.alarm.util.CsswebConfig;
import com.zendaimoney.thirdpp.alarm.util.Sender;

public class SMSQueryThread implements Runnable {

	public static Log logger = LogFactory.getLog(SMSQueryThread.class);
	public SMSSend smsSend;
	public static Integer errorNum = 0;
	private Integer sleep = 0;
	private MonitorService monitorService = null;
	
	private static java.lang.reflect.Type JsonType = new com.google.gson.reflect.TypeToken<Map<String, String>>(){}.getType();

	public SMSQueryThread() {
		this.smsSend = new SMSSend();
		this.monitorService = new MonitorService();
	}

	@Override
	public void run() {
		while (true) {
			try {
				// 读取数据库的待发告警记录
				List<Monitor> list = monitorService.queryMonitor();
				Sender sender = null;
				if (list == null || list.isEmpty()) {
					sleep = CsswebConfig.errorSleepTime;
				} else {
					for (Monitor m : list) {
						//mobile格式为(13248317292,13248317292),需要拆成多条记录
						sender = smsSend.newSenders(m);
						if(sender!=null){
							String result = smsSend.send(sender);
							
							// 发送告警结果不为空
							if (StringUtils.isNotBlank(result)) {
								
								Gson gson = new Gson();
								Map<String, String> map = gson.fromJson(result.toString(), JsonType);
								
								// 发送告警结果响应码：ucCode = 900000，成功后更新状态为1 （发送成功）
								if(map!=null && map.get("ucCode")!=null && Constants.ALARM_SUCCESS_CODE.equals(map.get("ucCode"))){
									
									monitorService.updateMonitor(m);
								}
							}
						}
					}
					sleep = CsswebConfig.sleepTime;
				}
				errorNum = 0;
			} catch (Exception e) {
				logger.error("发送告警短信出现异常", e);
			} finally {
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					logger.error("发送告警短信线程中止异常", e);
				}
			}
		}
	}
}
