package com.zendaimoney.trust.channel.action;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.util.LogPrn;
import com.zendaimoney.trust.channel.util.ReqActionUtil;

/**
 * 资金托管通道处理Action分发器
 * @author mencius
 *
 */
public class DispatchAction implements ApplicationContextAware{
	
	private static final LogPrn logger = new LogPrn(DispatchAction.class);
	
	/**
	 * spring容器
	 */
	private ApplicationContext applicationContext;

	// 单笔交易
	private static Map<String, TradeActionTarget> reqTradeActionTargetMap;
	
	// 单笔交易
	private static Map<String, QueryActionTarget> reqQueryActionTargetMap;
	
	// 批量处理
	private static Map<String, BatchActionTarget> reqBatchActionTargetMap;
	

	@SuppressWarnings("unused")
	private void initMap() {
		reqTradeActionTargetMap = ReqActionUtil.getTradeActionTarget();
		
		reqQueryActionTargetMap = ReqActionUtil.getQueryActionTarget();
		
		reqBatchActionTargetMap = ReqActionUtil.getBatchActionTarget();
		
	}

	
	/**
	 * 单笔交易分发到指定业务处理action.
	 * @param bizReqVo
	 */
	public Response executeTradeReqAction(TrustBizReqVo bizReqVo, TrustCategory cmbCategory) {
		
		Response response = null;
		String errorMsg = "";
		
		TrustBizType cmbBizType = bizReqVo.getTrustBizType();
		TradeActionTarget target = reqTradeActionTargetMap.get(ReqActionUtil.generateKey(cmbBizType, cmbCategory));
		if (target == null) {
			errorMsg = "request match action error. action method is not found. cmbBizType : " + cmbBizType.getCode() + ". cmbCategory" + cmbCategory.getCode();
			logger.error(errorMsg);
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),errorMsg);
			return response;
		}
		Class<? extends TradeActionAbstract> clazz = target.getActionClazz();
		TradeActionAbstract action = null;
		try {
			action = (TradeActionAbstract)applicationContext.getBean(clazz.getName());
		} catch (Exception e) {
			errorMsg = "instance action error : " + clazz.getName();
			logger.error(errorMsg,e);
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),errorMsg);
			return response;
		}
		
		response = action.execute(bizReqVo);
		return response;
	}
	
	
	/**
	 * 单笔查询分发到指定业务处理action.
	 * @param bizReqVo
	 */
	public Response executeQueryReqAction(TrustBizReqVo bizReqVo, TrustCategory cmbCategory) {
		
		Response response = null;
		String errorMsg = "";
		
		TrustBizType cmbBizType = bizReqVo.getTrustBizType();
		QueryActionTarget target = reqQueryActionTargetMap.get(ReqActionUtil.generateKey(cmbBizType, cmbCategory));
		if (target == null) {
			errorMsg = "request match action error. action method is not found. cmbBizType : " + cmbBizType.getCode() + ". cmbCategory" + cmbCategory.getCode();
			logger.error(errorMsg);
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),errorMsg);
			return response;
		}
		Class<? extends QueryActionAbstract> clazz = target.getActionClazz();
		QueryActionAbstract action = null;
		try {
			action = (QueryActionAbstract)applicationContext.getBean(clazz.getName());
		} catch (Exception e) {
			errorMsg = "instance action error : " + clazz.getName();
			logger.error(errorMsg,e);
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),errorMsg);
			return response;
		}
		
		response = action.execute(bizReqVo);
		return response;
	}
	
	/**
	 * 生产报文文件请求分发到指定业务处理action.
	 * @param bizReqVo
	 */
	public Response executeBuildReqAction(TrustBizReqVo cmbBizReqVo, TrustCategory cmbCategory) {
		
		Response response = null;
		String errorMsg = "";
		
		BatchActionTarget target = reqBatchActionTargetMap.get(ReqActionUtil.generateKey(cmbBizReqVo.getTrustBizType(), cmbCategory));
		if (target == null) {
			errorMsg = "request match action error. action method is not found. cmbBizType : " + cmbBizReqVo.getTrustBizType().getCode() + ". cmbCategory" + cmbCategory.getCode();
			logger.error(errorMsg);
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),errorMsg);
			return response;
		}
		Class<? extends BatchActionAbstract> clazz = target.getActionClazz();
		BatchActionAbstract action = null;
		try {
			action = (BatchActionAbstract)applicationContext.getBean(clazz.getName());
		} catch (Exception e) {
			errorMsg = "instance action error : " + clazz.getName();
			logger.error(errorMsg,e);
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),errorMsg);
			return response;
		}
		
		// 生产批量报文文件
		response = action.buildBatch(cmbBizReqVo);
		
		return response;
	}
	
	
	/**
	 * 解析报文文件请求分发到指定业务处理action.
	 * @param bizReqVo
	 */
	public Response executeParseReqAction(TrustBizReqVo cmbBizReqVo, TrustCategory cmbCategory) {
		
		Response response = null;
		String errorMsg = "";
		
		BatchActionTarget target = reqBatchActionTargetMap.get(ReqActionUtil.generateKey(cmbBizReqVo.getTrustBizType(), cmbCategory));
		if (target == null) {
			errorMsg = "request match action error. action method is not found. cmbBizType : " + cmbBizReqVo.getTrustBizType().getCode() + ". cmbCategory" + cmbCategory.getCode();
			logger.error(errorMsg);
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),errorMsg);
			return response;
		}
		Class<? extends BatchActionAbstract> clazz = target.getActionClazz();
		BatchActionAbstract action = null;
		try {
			action = (BatchActionAbstract)applicationContext.getBean(clazz.getName());
		} catch (Exception e) {
			errorMsg = "instance action error : " + clazz.getName();
			logger.error(errorMsg,e);
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),errorMsg);
			return response;
		}
		
		// 解析批量报文文件
		response = action.parseBatch(cmbBizReqVo);
		return response;
	}
	
	
	/**
	 * 下载报文文件请求分发到指定业务处理action.
	 * @param bizReqVo
	 */
	public Response executePullReqAction(TrustBizReqVo cmbBizReqVo, TrustCategory cmbCategory) {
		
		Response response = null;
		String errorMsg = "";
		
		BatchActionTarget target = reqBatchActionTargetMap.get(ReqActionUtil.generateKey(cmbBizReqVo.getTrustBizType(), cmbCategory));
		if (target == null) {
			errorMsg = "request match action error. action method is not found. cmbBizType : " + cmbBizReqVo.getTrustBizType().getCode() + ". cmbCategory" + cmbCategory.getCode();
			logger.error(errorMsg);
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),errorMsg);
			return response;
		}
		Class<? extends BatchActionAbstract> clazz = target.getActionClazz();
		BatchActionAbstract action = null;
		try {
			action = (BatchActionAbstract)applicationContext.getBean(clazz.getName());
		} catch (Exception e) {
			errorMsg = "instance action error : " + clazz.getName();
			logger.error(errorMsg,e);
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),errorMsg);
			return response;
		}
		
		// 解析批量报文文件
		response = action.pullBatch(cmbBizReqVo);
		return response;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
