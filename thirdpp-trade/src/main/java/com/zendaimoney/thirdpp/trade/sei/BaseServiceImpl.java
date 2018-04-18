package com.zendaimoney.thirdpp.trade.sei;

import com.alibaba.dubbo.rpc.RpcContext;
import com.zendaimoney.thirdpp.common.util.BeanUtils;
import com.zendaimoney.thirdpp.common.vo.AttachmentResponse;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.trade.entity.*;
import com.zendaimoney.thirdpp.trade.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.trade.pub.service.IBaseService;
import com.zendaimoney.thirdpp.trade.pub.vo.req.query.*;
import com.zendaimoney.thirdpp.trade.pub.vo.resp.query.*;
import com.zendaimoney.thirdpp.trade.service.*;
import com.zendaimoney.thirdpp.trade.util.ExceptionUtil;
import com.zendaimoney.thirdpp.trade.util.LogPrn;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseServiceImpl implements IBaseService {

	private static final LogPrn logger = new LogPrn(BaseServiceImpl.class);
	
	@Autowired
	private SysAppIPSService sysAppIPSService;
	@Autowired
	private AreaInfoService areaInfoService;
	@Autowired
	private BankInfoService bankInfoService;
	@Autowired
	private BankOrgInfoService bankOrgInfoService;
	@Autowired
	private SysAppService sysAppService;
	@Autowired
	private SysInfoCategoryAPPSService sysInfoCategoryAPPSService;
	@Autowired
	private SysInfoCategoryService sysInfoCategoryService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private ThirdFieldMapperService thirdFieldMapperService;
	@Override
	public AttachmentResponse<List<BankResponseVo>> getSupportedBanks(BaseQueryRequestVo vo) {

		AttachmentResponse<List<BankResponseVo>> response = new AttachmentResponse<List<BankResponseVo>>();
		response.setCode(Response.SUCCESS_RESPONSE_CODE);
		
		try {
			basicCheck(response, vo);
			if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
				return response;
			}
			
			List<BankInfo> bankInfoList = bankInfoService.getSupportedBanks();
			List<BankResponseVo> bankResponseVoList = new ArrayList<BankResponseVo>();
			if (bankInfoList != null && bankInfoList.size() > 0) {
				BankResponseVo bankResponseVo = null;
				for (BankInfo bankInfo : bankInfoList) {
					bankResponseVo = new BankResponseVo();
					BeanUtils.copyNotNullProperties(bankInfo, bankResponseVo);
					bankResponseVoList.add(bankResponseVo);
				}
			} 
			
			response.setAttachment(bankResponseVoList);
			return response;
			
		} catch (Exception e) {
			logger.error("BaseServiceImpl=getSupportedBanks", e);
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
	
	}

	@Override
	public AttachmentResponse<List<BankAreaResponseVo>> getBankAreaProvinces(BaseQueryRequestVo vo) {
		
		AttachmentResponse<List<BankAreaResponseVo>> response = new AttachmentResponse<List<BankAreaResponseVo>>();
		response.setCode(Response.SUCCESS_RESPONSE_CODE);
		
		try {
			basicCheck(response, vo);
			if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
				return response;
			}
			
			List<AreaInfo> areaInfoList = areaInfoService.getAreaInfos(null);
			List<BankAreaResponseVo> bankAreaResponseVoList = new ArrayList<BankAreaResponseVo>();
			if (areaInfoList != null && areaInfoList.size() > 0) {
				BankAreaResponseVo bankAreaResponseVo = null;
				for (AreaInfo areaInfo : areaInfoList) {
					bankAreaResponseVo = new BankAreaResponseVo();
					BeanUtils.copyNotNullProperties(areaInfo, bankAreaResponseVo);
					bankAreaResponseVoList.add(bankAreaResponseVo);
				}
			} 
			
			response.setAttachment(bankAreaResponseVoList);
			return response;
			
		} catch (Exception e) {
			logger.error("BaseServiceImpl=getBankAreaProvinces", e);
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
	}

	@Override
	public AttachmentResponse<List<BankAreaResponseVo>> getBankAreaCitysByProvince(BankAreaCityQueryReqVo vo) {
		
		AttachmentResponse<List<BankAreaResponseVo>> response = new AttachmentResponse<List<BankAreaResponseVo>>();
		response.setCode(Response.SUCCESS_RESPONSE_CODE);
		
		try {
			basicCheck(response, vo);
			if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
				return response;
			}
			
			String provinceAreaCode = vo.getBankOrganizationProvinceNo();
			if (StringUtils.isBlank(provinceAreaCode)) {
				response.setCode(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode());
				response.setMsg("省份区域编码[bankOrganizationProvinceNo]为空");
				return response;
			}
			
			List<BankAreaResponseVo> bankAreaResponseVoList = new ArrayList<BankAreaResponseVo>();
			AreaInfo tempAreaInfo = areaInfoService.getAreaInfoByAreaCode(provinceAreaCode);
			if (tempAreaInfo != null && tempAreaInfo.isParent()) {
				List<AreaInfo> areaInfoList = areaInfoService.getAreaInfos(provinceAreaCode);
				if (areaInfoList != null && areaInfoList.size() > 0) {
					BankAreaResponseVo bankAreaResponseVo = null;
					for (AreaInfo areaInfo : areaInfoList) {
						bankAreaResponseVo = new BankAreaResponseVo();
						BeanUtils.copyNotNullProperties(areaInfo, bankAreaResponseVo);
						bankAreaResponseVoList.add(bankAreaResponseVo);
					}
				} else {
					AreaInfo areaInfo = areaInfoService.getAreaInfoByAreaCode(provinceAreaCode);
					if (areaInfo != null) {
						BankAreaResponseVo bankAreaResponseVo = new BankAreaResponseVo();
						BeanUtils.copyNotNullProperties(areaInfo, bankAreaResponseVo);
						bankAreaResponseVoList.add(bankAreaResponseVo);
					}
				}
			}
			
			response.setAttachment(bankAreaResponseVoList);
			return response;
			
		} catch (Exception e) {
			logger.error("BaseServiceImpl=getBankAreaCitysByProvince", e);
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
	
	}

	@Override
	public AttachmentResponse<List<BankOrgResponseVo>> getBankOrganizationsByBankCodeAndBankArea(BankOrganizationQueryReqVo vo) {
		
		AttachmentResponse<List<BankOrgResponseVo>> response = new AttachmentResponse<List<BankOrgResponseVo>>();
		response.setCode(Response.SUCCESS_RESPONSE_CODE);
		
		try {
			basicCheck(response, vo);
			if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
				return response;
			}
			
			String bankCode = vo.getBankCode();
			String bankOrgProvinceNo = vo.getBankOrganizationProvinceNo();
			String bankOrgProvinceCityNo = vo.getBankOrganizationCityNo();
			
			if (StringUtils.isBlank(bankCode) || StringUtils.isBlank(bankOrgProvinceNo) || StringUtils.isBlank(bankOrgProvinceCityNo)) {
				response.setCode(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode());
				response.setMsg("总行编码[bankCode]为空或者省份区域编码[bankOrganizationProvinceNo]为空或者市县区域编码[bankOrganizationCityNo]为空");
				return response;
			}
			
			AreaInfo areaInfo = null;
			List<BankOrgResponseVo> bankOrgResponseVoList = new ArrayList<BankOrgResponseVo>();

			if (bankOrgProvinceCityNo.equals(bankOrgProvinceNo)) {
				areaInfo = areaInfoService.getAreaInfoByAreaCode(bankOrgProvinceCityNo);
			} else {
				areaInfo = areaInfoService.getAreaInfoByAreaCodeAndParentCode(bankOrgProvinceCityNo, bankOrgProvinceNo);
			}
			if (null == areaInfo) {
				response.setAttachment(bankOrgResponseVoList);
				return response;
			} else {
				List<BankOrgInfo> bankOrgInfoList = bankOrgInfoService.getBankOrgInfoByBankCodeAndBankArea(bankCode, bankOrgProvinceNo, bankOrgProvinceCityNo);
				if (bankOrgInfoList != null && bankOrgInfoList.size() > 0) {
					BankOrgResponseVo bankOrgResponseVo = null;
					for (BankOrgInfo bankOrgInfo : bankOrgInfoList) {
						bankOrgResponseVo = new BankOrgResponseVo();
						BeanUtils.copyNotNullProperties(bankOrgInfo, bankOrgResponseVo);
						bankOrgResponseVoList.add(bankOrgResponseVo);
					}
				}
			}
			
			response.setAttachment(bankOrgResponseVoList);
			return response;
			
		} catch (Exception e) {
			logger.error("BaseServiceImpl=getBankOrganizationsByBankCodeAndBankArea", e);
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
		
	}

	@Override
	public AttachmentResponse<List<ThirdPartyPayPlatformResponseVo>> getSupportThirdPartyPayPlatforms(BaseQueryRequestVo vo) {

		AttachmentResponse<List<ThirdPartyPayPlatformResponseVo>> response = new AttachmentResponse<List<ThirdPartyPayPlatformResponseVo>>();
		response.setCode(Response.SUCCESS_RESPONSE_CODE);
		
		try {
			basicCheck(response, vo);
			if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
				return response;
			}
			
			final String payPlatformDicType = Dictionary.DIC_TYPE_PAY_PLATFORM;
			List<Dictionary> dictionaryList = dictionaryService.getSupportedPayPlatforms(payPlatformDicType);
			List<ThirdPartyPayPlatformResponseVo> payPlatformResponseVoList = new ArrayList<ThirdPartyPayPlatformResponseVo>();
			if (dictionaryList != null && dictionaryList.size() > 0) {
				ThirdPartyPayPlatformResponseVo payPlatformResponseVo = null;
				for (Dictionary dictionary : dictionaryList) {
					payPlatformResponseVo = new ThirdPartyPayPlatformResponseVo();
					payPlatformResponseVo.setThirdPartyPayPlatformCode(dictionary.getDicCode());
					payPlatformResponseVo.setThirdPartyPayPlatformName(dictionary.getDicName());
					payPlatformResponseVoList.add(payPlatformResponseVo);
				}
			}
			
			response.setAttachment(payPlatformResponseVoList);
			
		} catch (Exception e) {
			logger.error("BaseServiceImpl=getSupportedPayPlatforms", e);
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
		}
		
		return response;
	}

	@Override
	public AttachmentResponse<List<ThirdPartyPayPlatformSupportBankRespVo>> getSupportBanksByThirdPartyPayPlatform(
			ThirdPartyPayPlatformSupportBankReqVo vo) {
		
		AttachmentResponse<List<ThirdPartyPayPlatformSupportBankRespVo>> response = new AttachmentResponse<List<ThirdPartyPayPlatformSupportBankRespVo>>();
		response.setCode(Response.SUCCESS_RESPONSE_CODE);
		
		try {
			basicCheck(response, vo);
			if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
				return response;
			}
			
			String thirdPartyPayPlatformCode = vo.getThirdPartyPayPlatformCode();
			if (StringUtils.isBlank(thirdPartyPayPlatformCode)) {
				response.setCode(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode());
				response.setMsg("第三方支付平台编码[thirdPartyPayPlatformCode]为空");
				return response;
			}
			
			List<ThirdPartyPayPlatformSupportBankRespVo> respList = new ArrayList<ThirdPartyPayPlatformSupportBankRespVo>();
			List<ThirdFieldMapper> tempList = thirdFieldMapperService.queryThirdPartyPayPlatformSupportBanks(thirdPartyPayPlatformCode);
			if (null != tempList && tempList.size() > 0) {
				ThirdPartyPayPlatformSupportBankRespVo respObj = null;
				for (ThirdFieldMapper thirdFieldMapper : tempList) {
					respObj = new ThirdPartyPayPlatformSupportBankRespVo();
					respObj.setBankCode(thirdFieldMapper.getTppFieldCode());
					respObj.setBankName(thirdFieldMapper.getFieldName());
					respList.add(respObj);
				}
			}
			
			response.setAttachment(respList);
			
		} catch (Exception e) {
			logger.error("BaseServiceImpl=getSupportBanksByThirdPartyPayPlatform", e);
			String msg = PlatformErrorCode.SYSTEM_BUSY.getDefaultMessage();
			String exceptionMsg = ExceptionUtil.getExceptionMessage(e);
			if (logger.isDebugEnable()) {
				msg = exceptionMsg;
			}
			response.setCode(PlatformErrorCode.SYSTEM_BUSY.getErrorCode());
			response.setMsg(msg);
		}
		
		return response;
	}
	
	
	@Override
	public AttachmentResponse<ThirdPartyPayPlatformSupportBankInfoRespVo> getSupportBankInfoByThirdPartyPayPlatformCodeAndBankCode(
			ThirdPartyPayPlatformSupportBankInfoReqVo vo) {
		
		AttachmentResponse<ThirdPartyPayPlatformSupportBankInfoRespVo> response = new AttachmentResponse<ThirdPartyPayPlatformSupportBankInfoRespVo>();
		response.setCode(Response.SUCCESS_RESPONSE_CODE);
		
		try {
			basicCheck(response, vo);
			if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
				return response;
			}
			
			String thirdPartyPayPlatformCode = vo.getThirdPartyPayPlatformCode();
			if (StringUtils.isBlank(thirdPartyPayPlatformCode)) {
				response.setCode(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode());
				response.setMsg("第三方支付平台编码[thirdPartyPayPlatformCode]为空");
				return response;
			}
			
			String thirdPartyPayPlatformSupportBankCode = vo.getBankCode();
			if (StringUtils.isBlank(thirdPartyPayPlatformSupportBankCode)) {
				response.setCode(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode());
				response.setMsg("银行编码[bankCode]为空");
				return response;
			}
			
			ThirdFieldMapper thirdFieldMapper = thirdFieldMapperService.queryThirdPartyPayPlatformSupportBankInfo(thirdPartyPayPlatformCode, thirdPartyPayPlatformSupportBankCode);
			if (null != thirdFieldMapper) {
				ThirdPartyPayPlatformSupportBankInfoRespVo respVo = new ThirdPartyPayPlatformSupportBankInfoRespVo();
				BeanUtils.copyNotNullProperties(thirdFieldMapper, respVo);
				response.setAttachment(respVo);
			}
			
		} catch (Exception e) {
			logger.error("BaseServiceImpl=getSupportBankInfoByThirdPartyPayPlatformCodeAndBankCode", e);
			String msg = PlatformErrorCode.SYSTEM_BUSY.getDefaultMessage();
			String exceptionMsg = ExceptionUtil.getExceptionMessage(e);
			if (logger.isDebugEnable()) {
				msg = exceptionMsg;
			}
			
			response.setCode(PlatformErrorCode.SYSTEM_BUSY.getErrorCode());
			response.setMsg(msg);
		}
		return response;
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

	public void setAreaInfoService(AreaInfoService areaInfoService) {
		this.areaInfoService = areaInfoService;
	}

	public void setBankInfoService(BankInfoService bankInfoService) {
		this.bankInfoService = bankInfoService;
	}

	public void setBankOrgInfoService(BankOrgInfoService bankOrgInfoService) {
		this.bankOrgInfoService = bankOrgInfoService;
	}

	public void setSysAppService(SysAppService sysAppService) {
		this.sysAppService = sysAppService;
	}

	public void setSysInfoCategoryAPPSService(
			SysInfoCategoryAPPSService sysInfoCategoryAPPSService) {
		this.sysInfoCategoryAPPSService = sysInfoCategoryAPPSService;
	}

	public void setSysInfoCategoryService(
			SysInfoCategoryService sysInfoCategoryService) {
		this.sysInfoCategoryService = sysInfoCategoryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public void setThirdFieldMapperService(
			ThirdFieldMapperService thirdFieldMapperService) {
		this.thirdFieldMapperService = thirdFieldMapperService;
	}

}
