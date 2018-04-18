package com.zendaimoney.thirdpp.alarm.sms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.zendaimoney.ice.uc.client.CustInfo;
import com.zendaimoney.ice.uc.client.MsgBody;
import com.zendaimoney.ice.uc.client.UInfo;
import com.zendaimoney.ice.uc.client.service.IDataSendService;
import com.zendaimoney.ice.uc.client.service.impl.LongSendService;
import com.zendaimoney.thirdpp.alarm.IDataSend;
import com.zendaimoney.thirdpp.alarm.common.Monitor;
import com.zendaimoney.thirdpp.alarm.common.exception.CsswebException;
import com.zendaimoney.thirdpp.alarm.util.CsswebConfig;
import com.zendaimoney.thirdpp.alarm.util.Sender;

public class SMSSend implements IDataSend {

	public static Log logger = LogFactory.getLog(SMSSend.class);
	
	private static IDataSendService dataSendService = LongSendService.getInstance();

	public synchronized String send(Sender sender) throws CsswebException {
		
		String result = null;
		try {
			result = dataSendService.sendMsg(CsswebConfig.tytxServerConfig.getSysId(), CsswebConfig.tytxServerConfig.getEmpId(), sender.getMobile(),"0","sms", "", "", CsswebConfig.tytxServerConfig.getmType(),
					sender.getBody(), "", "2099-01-01 18", null, CsswebConfig.tytxServerConfig.getEncoding());
		} catch (Exception e) {
			logger.error("调用统一通信平台失败,需重新尝试 " + sender.toJson());
			//throw new CsswebException("调用无线天利平台失败", e);
			return result;
		}
		
		return result;
	}

/*	public Sender newSender(Monitor monitor) {
		Sender sender = new Sender();
		try {
			sender.setSequenceId(Standard_SeqNum.computeSeqNoErr(2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		sender.setMobile(monitor.getMobiles());
		sender.setBody(monitor.getNote() + CsswebConfig.wxtlServerConfig.getSuffix());
		sender.setType("SMS");
		sender.setPriority("3");
		sender.setProductId(CsswebConfig.wxtlServerConfig.getProductId());
		sender.setTaskId(String.valueOf(monitor.getId()));
		return sender;
	}*/

	public Sender newSenders(Monitor monitor) {
		String[] mobileArray = null;
		Sender sender = null;
		UInfo info = null;
		Gson gson = new Gson();
		if (monitor.getMobiles() != null
				&& monitor.getMobiles().startsWith("(")
				&& monitor.getMobiles().endsWith(")")) {
			mobileArray = monitor.getMobiles()
					.substring(1, monitor.getMobiles().length() - 1).split(",");
		} else {
			mobileArray = new String[1];
			mobileArray[0] = monitor.getMobiles();
		}
		if (mobileArray != null && mobileArray.length > 0) {
			
			sender = new Sender();
			info = new UInfo();
			
			CustInfo[] custInfos = new CustInfo[mobileArray.length];
			for (int i = 0; i < mobileArray.length; i++) {
				CustInfo cinfo = new CustInfo();
				cinfo.setcMarkType("1");
				cinfo.setcType("-1");
				cinfo.setMarkInfo(mobileArray[i]);
				custInfos[i] = cinfo;
			}
			
			MsgBody msgBody = new MsgBody();
			msgBody.setSummary("");
			msgBody.setBody(monitor.getNote());
			msgBody.setPublishTime("");
			msgBody.setMailFrom("");
			msgBody.setSubject("");
			msgBody.setThirdSysId("");
			msgBody.setUrl("");
			msgBody.setBodyFormat("text");
			info.setCustInfo(custInfos);
			sender.setMobile(gson.toJson(info));
			sender.setBody(gson.toJson(msgBody));
		}
		return sender;
	}

	@Override
	public Object receive() throws CsswebException {
		// TODO Auto-generated method stub
		return null;
	}
}
