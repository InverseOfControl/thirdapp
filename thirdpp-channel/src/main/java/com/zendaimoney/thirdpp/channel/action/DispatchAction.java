package com.zendaimoney.thirdpp.channel.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.service.ThirdFieldMapperService;
import com.zendaimoney.thirdpp.channel.util.LogPrn;
import com.zendaimoney.thirdpp.channel.util.ReqActionUtil;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.Response;


public class DispatchAction implements ApplicationContextAware{
	
	private static final LogPrn logger = new LogPrn(DispatchAction.class);
	
	private ApplicationContext applicationContext;

	private static Map<String, ReqActionTarget> reqActionTargetMap;
	
	@Autowired
	ThirdFieldMapperService thirdFieldMapperService = null;

	@SuppressWarnings("unused")
	private void initMap() {
		reqActionTargetMap = ReqActionUtil.getReqActionTarget();
	}

	
	
	/**
	 * 分发到指定业务处理action.
	 * @param bizReqVo
	 */
	public Response executeReqAction(BizReqVo bizReqVo,ChannelCategory channelCategory) {
		
		Response response = null;
		String errorMsg = "";
		
		BizType bizType = bizReqVo.getBizType();
		ThirdType thirdType = bizReqVo.getThirdType();
		ReqActionTarget target = reqActionTargetMap.get(ReqActionUtil.generateKey(bizType, thirdType,channelCategory));
		if (target == null) {
			errorMsg = "request match action error. action method is not found. bizType : " + bizType.getCode()+". thirdType" + thirdType.getCode() + ". channelCategory" + channelCategory.getCode();
			logger.error(errorMsg);
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),errorMsg);
			return response;
		}
		Class<? extends Action> clazz = target.getActionClazz();
		Action action = null;
		try {
			action = (Action)applicationContext.getBean(clazz.getName());
		} catch (Exception e) {
			errorMsg = "instance action error : " + clazz.getName();
			logger.error(errorMsg,e);
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),errorMsg);
			return response;
		}
		
		response = action.execute(bizReqVo);
		return response;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
