package com.zendaimoney.thirdpp.trade.sei;

import com.alibaba.dubbo.rpc.RpcContext;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.util.BeanUtils;
import com.zendaimoney.thirdpp.common.vo.AttachmentResponse;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.trade.business.ChannelSendService;
import com.zendaimoney.thirdpp.trade.entity.OperationRequest;
import com.zendaimoney.thirdpp.trade.entity.SysApp;
import com.zendaimoney.thirdpp.trade.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.trade.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.trade.pub.service.IAuthService;
import com.zendaimoney.thirdpp.trade.pub.vo.req.query.BankCardAuthReqVo;
import com.zendaimoney.thirdpp.trade.pub.vo.req.query.BankCardBinQueryReqVo;
import com.zendaimoney.thirdpp.trade.pub.vo.req.query.BaseQueryRequestVo;
import com.zendaimoney.thirdpp.trade.pub.vo.resp.query.CardBinResponseVo;
import com.zendaimoney.thirdpp.trade.service.*;
import com.zendaimoney.thirdpp.trade.util.ExceptionUtil;
import com.zendaimoney.thirdpp.trade.util.IDGenerateUtil;
import com.zendaimoney.thirdpp.trade.util.LogPrn;
import com.zendaimoney.thirdpp.trade.validate.AccountVerificationInsertCheck;
import com.zendaimoney.thirdpp.trade.validate.CardBinInsertCheck;
import com.zendaimoney.thirdpp.trade.validate.Validate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.groups.Default;
import java.sql.SQLException;

public class AuthServiceImpl implements IAuthService {

	private static final LogPrn logger = new LogPrn(AuthServiceImpl.class);
	
	@Autowired
	private SysAppIPSService sysAppIPSService;
	@Autowired
	private SysAppService sysAppService;
	@Autowired
	private ChannelSendService channelSendService;
	@Autowired
	private OperationRequestService operationRequestService;
	@Autowired
	private SysInfoCategoryAPPSService sysInfoCategoryAPPSService;
	@Autowired
	private SysInfoCategoryService sysInfoCategoryService;
	
	@SuppressWarnings("unchecked")
	@Override
	public Response bankCardAuth(BankCardAuthReqVo vo) {

		Response response = new Response();
		response.setCode(Response.SUCCESS_RESPONSE_CODE);
		OperationRequest oar = null;
		try {
			response = basicCheck(response, vo);
			if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
				return response;
			}
			
			if (StringUtils.isBlank(vo.getInfoCategoryCode())) {
				response.setCode(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode());
				response.setMsg("信息类别编码[infoCategoryCode]为空");
				return response;
			}
			
			// 查看此信息类别编码是否有访问此业务系统权限
			boolean isAccessPermission = sysInfoCategoryAPPSService.isAccessPermission(
					vo.getInfoCategoryCode(), vo.getBizSysNo());
			if (!isAccessPermission) {
				logger.info("此信息类别编码没有有访问此业务系统权限:" + vo.getInfoCategoryCode());
				response = new Response(
						PlatformErrorCode.INFO_CATEGORY_ACCESS_APP_PERMISSION_ERROR
								.getErrorCode(),
						PlatformErrorCode.INFO_CATEGORY_ACCESS_APP_PERMISSION_ERROR
								.getDefaultMessage());
				return response;
			}
			// 查询信息类别
			SysInfoCategory sysInfoCategory = sysInfoCategoryService
					.querySysInfoCategoryByCode(vo.getInfoCategoryCode());
			if (sysInfoCategory == null) {
				response = new Response(
						PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
						"信息类别编码[infoCategoryCode]不存在");
				return response;
			}
			
			oar = initAccountVerificationOperationRequest(vo, sysInfoCategory.getPaymentChannel());
			
			response = Validate.getInstance().validate(oar,
					AccountVerificationInsertCheck.class, Default.class);
			if (Response.SUCCESS_RESPONSE_CODE != response.getCode()) {
				return response;
			}
			
			operationRequestService.insert(oar);
			
			response = channelSendService.channelBankCardAuth(oar);
			
		} catch (Exception e) {
			logger.error("AuthServiceImpl=bankCardAuth", e);
			// 系统默认异常信息
			String msg = PlatformErrorCode.SYSTEM_BUSY.getDefaultMessage();
			// 真实异常信息
			String exceptionMsg = ExceptionUtil.getExceptionMessage(e);
			// 如果是debug模式
			if (logger.isDebugEnable()) {
				msg = exceptionMsg;
			}
			response.setCode(PlatformErrorCode.SYSTEM_BUSY.getErrorCode());
			response.setMsg(msg);
			return response;
		}
		response.setFlowId(oar.getReqId());
		return response;
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public AttachmentResponse<CardBinResponseVo> bankCardBinQuery(
			BankCardBinQueryReqVo vo) {

		AttachmentResponse<CardBinResponseVo> response = new AttachmentResponse<CardBinResponseVo>();
		response.setCode(Response.SUCCESS_RESPONSE_CODE);
		OperationRequest oar = null;
		try {
			basicCheck(response, vo);
			if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
				return response;
			}
			
			if (StringUtils.isBlank(vo.getInfoCategoryCode())) {
				response.setCode(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode());
				response.setMsg("信息类别编码[infoCategoryCode]为空");
				return response;
			}
			
			// 查看此信息类别编码是否有访问此业务系统权限
			boolean isAccessPermission = sysInfoCategoryAPPSService.isAccessPermission(
					vo.getInfoCategoryCode(), vo.getBizSysNo());
			if (!isAccessPermission) {
				logger.info("此信息类别编码没有有访问此业务系统权限:" + vo.getInfoCategoryCode());
				response.setCode(PlatformErrorCode.INFO_CATEGORY_ACCESS_APP_PERMISSION_ERROR
						.getErrorCode());
				response.setMsg(PlatformErrorCode.INFO_CATEGORY_ACCESS_APP_PERMISSION_ERROR
						.getDefaultMessage());
				return response;
			}
			// 查询信息类别
			SysInfoCategory sysInfoCategory = sysInfoCategoryService
					.querySysInfoCategoryByCode(vo.getInfoCategoryCode());
			if (sysInfoCategory == null) {
				response.setCode(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode());
				response.setMsg("信息类别编码[infoCategoryCode]不存在");
				return response;
			}
			
			oar = initCardBinOperationRequest(vo, sysInfoCategory.getPaymentChannel());
			
			Response respValidate = Validate.getInstance().validate(oar, CardBinInsertCheck.class, Default.class);
			if (!Response.SUCCESS_RESPONSE_CODE.equals(respValidate.getCode())) {
				response.setCode(respValidate.getCode());
				response.setMsg(respValidate.getMsg());
				return response;
			}
			
			operationRequestService.insert(oar);
			
			response = channelSendService.channelBankCardBinQuery(oar);
			
		} catch (Exception e) {
			logger.error("AuthServiceImpl-bankCardBinQuery", e);
			String msg = PlatformErrorCode.SYSTEM_BUSY.getDefaultMessage();
			String exceptionMsg = ExceptionUtil.getExceptionMessage(e);
			if (logger.isDebugEnable()) {
				msg = exceptionMsg;
			}
			response.setCode(PlatformErrorCode.SYSTEM_BUSY.getErrorCode());
			response.setMsg(msg);
			return response;
		}
		response.setFlowId(oar.getReqId());
		return response;
	}

	public OperationRequest initCardBinOperationRequest(BankCardBinQueryReqVo vo, String paymentChannel){
		
		OperationRequest oar = new OperationRequest();
		BeanUtils.copyNotNullProperties(vo, oar);
		
		oar.setReqId(IDGenerateUtil.createReqId());
		oar.setBizTypeNo(BizType.BIN_QUERY.getCode());
		oar.setStatus(OperationRequest.StatusEnum.processing.getStatusCode());
		oar.setIp(RpcContext.getContext().getRemoteHost());
		oar.setPaySysNo(paymentChannel);
		oar.setTransferFlow(IDGenerateUtil.createTradeFlow(BizType.BIN_QUERY.getCode()));
		return oar;
	}
	
	private OperationRequest initAccountVerificationOperationRequest(BankCardAuthReqVo vo, String paymentChannel){
		
		OperationRequest oar = new OperationRequest();
		BeanUtils.copyNotNullProperties(vo, oar);
		
		oar.setReqId(IDGenerateUtil.createReqId());
		oar.setBizTypeNo(BizType.CERTIFICATION.getCode());
		oar.setStatus(OperationRequest.StatusEnum.processing.getStatusCode());
		oar.setIp(RpcContext.getContext().getRemoteHost());
		oar.setPaySysNo(paymentChannel);
		oar.setTransferFlow(IDGenerateUtil.createTradeFlow(BizType.CERTIFICATION.getCode()));
		return oar;
	}
	

	private Response basicCheck(Response response, BaseQueryRequestVo vo) throws SQLException{
		
		if (null == vo || StringUtils.isBlank(vo.getBizSysNo())) {
			response.setCode(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode());
			response.setMsg("业务系统编码[bizSysNo]为空");
			return response;
		}
		
		// 查看请求业务系统是否存在
		SysApp sysApp = sysAppService.querySysApp(vo.getBizSysNo());
		// 如果请求业务系统不存在
		if (null == sysApp){
			logger.info("业务系统不存在或已关闭:" + vo.getBizSysNo());
			response.setCode(PlatformErrorCode.NOT_EXISTS_SYSTEM.getErrorCode());
			response.setMsg(PlatformErrorCode.NOT_EXISTS_SYSTEM.getDefaultMessage());
			return response;
		}
		
		String requestIp = RpcContext.getContext().getRemoteHost();
		// requestIp = "192.168.1.1"; //代码仅限单元测试，测试完成之后即可删除
		boolean hasAccessPermission = hasAccessPermission(vo.getBizSysNo(), requestIp);
		if (!hasAccessPermission) {
			logger.info("此IP没有访问此业务系统权限:" + requestIp);
			response.setCode(PlatformErrorCode.IP_ACCESS_APP_PERMISSION_ERROR.getErrorCode());
			response.setMsg(PlatformErrorCode.IP_ACCESS_APP_PERMISSION_ERROR.getDefaultMessage());
			return response;
		}
		
		return response;
	}
	
	private boolean hasAccessPermission(String bizSysNo, String requestIp) throws SQLException {
		if (StringUtils.isBlank(bizSysNo) || StringUtils.isBlank(requestIp)) {
			return false;
		}
		boolean hasAccessPermission = sysAppIPSService.isAccessPermission(bizSysNo, requestIp);
		return hasAccessPermission;
	}
	
	public void setSysAppIPSService(SysAppIPSService sysAppIPSService) {
		this.sysAppIPSService = sysAppIPSService;
	}

	public void setSysAppService(SysAppService sysAppService) {
		this.sysAppService = sysAppService;
	}

	public ChannelSendService getChannelSendService() {
		return channelSendService;
	}

	public void setChannelSendService(ChannelSendService channelSendService) {
		this.channelSendService = channelSendService;
	}

	public void setOperationRequestService(
			OperationRequestService operationRequestService) {
		this.operationRequestService = operationRequestService;
	}

	public void setSysInfoCategoryAPPSService(
			SysInfoCategoryAPPSService sysInfoCategoryAPPSService) {
		this.sysInfoCategoryAPPSService = sysInfoCategoryAPPSService;
	}

	public void setSysInfoCategoryService(
			SysInfoCategoryService sysInfoCategoryService) {
		this.sysInfoCategoryService = sysInfoCategoryService;
	}

}
