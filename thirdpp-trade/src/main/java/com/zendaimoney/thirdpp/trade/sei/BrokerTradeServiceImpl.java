package com.zendaimoney.thirdpp.trade.sei;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSONObject;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.util.BeanUtils;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.trade.business.ChannelSendService;
import com.zendaimoney.thirdpp.trade.dao.RequestDao;
import com.zendaimoney.thirdpp.trade.entity.*;
import com.zendaimoney.thirdpp.trade.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.trade.pub.service.IBrokerTradeService;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.*;
import com.zendaimoney.thirdpp.trade.service.*;
import com.zendaimoney.thirdpp.trade.util.*;
import com.zendaimoney.thirdpp.trade.util.Constants.ChannelRulesConstants;
import com.zendaimoney.thirdpp.trade.validate.InsertCheck;
import com.zendaimoney.thirdpp.trade.validate.Validate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrokerTradeServiceImpl implements IBrokerTradeService {

	// 日志工具类
	private static final LogPrn logger = new LogPrn(BrokerTradeServiceImpl.class);

	@Autowired
	public CollectTaskService collectTaskService;

	@Autowired
	private SignMessageInfoService signMessageInfoService;

	@Autowired
	private SignInfoService signInfoService;

	@Autowired
	public ChannelSendService channelSendService;

	@Autowired
	public SysAppIPSService sysAppIPSService;

	@Autowired
	public SysInfoCategoryAPPSService sysInfoCategoryAPPSService;
	
	@Autowired
	public SysInfoCategoryBanksService sysInfoCategoryBanksService;

	@Autowired
	public SysInfoCategoryService sysInfoCategoryService;

	@Autowired
	public SysAppService sysAppService;
	
	@Autowired
	public PayTaskService payTaskService;
	
	@Autowired
	public TppWhiteListService tppWhiteListService;

	@Resource(name = "requestDao")
	private RequestDao requestDao;

	/**
	 * 同步单笔接口，vo里面list明细为一条
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Response syncCollect(RequestVo vo) {
		Response response = null;
		// 交易请求
		Request request = new Request();
		// 实体对象-任务
		CollectTask task = new CollectTask();
		// 实体对象-交易对象
		CollectInfo info = new CollectInfo();

		// 业务对象列表
		List<BizReqVo> bizReqVos = null;
		// 是否有访问权限
		boolean isAccessPermission = false;

		// 信息类别(此对象中包含业务级别等字段信息)
		SysInfoCategory sysInfoCategory = null;
		// 请求方IP.
		String requestIp = null;
		// 请求系统
		SysApp sysApp = null;
		
		try {
			// 获取请求IP
			requestIp = RpcContext.getContext().getRemoteHost();

			if (vo == null) {
				response = new Response(
						PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
						"RequestVo 为空");
				return response;
			}
			// 设置业务类型
			vo.setBizTypeNo(BizType.BROKER_COLLECT.getCode());
			// 将vo对象转化成实体对象
			BeanUtils.copyNotNullProperties(vo, request);
			// 设置request相关属性
			// 设置reqId
			request.setReqId(IDGenerateUtil.createReqId());
			// 设置非补单操作
			request.setIsHandAdd(Request.ISHANDADD_NO);
			// 设置为同步
			request.setIsSync(Request.ISSYNC_YES);
			// 设置IP
			request.setIp(requestIp);

			// 检验request实体对象
			response = Validate.getInstance().validate(request,
					InsertCheck.class, Default.class);
			// 如果request检验不通过
			if (Response.SUCCESS_RESPONSE_CODE != response.getCode()) {
				return response;
			}
			// 查看请求业务系统是否存在
			sysApp = sysAppService.querySysApp(vo.getBizSysNo());
			// 如果请求业务系统不存在
			if (sysApp == null) {
				logger.info("业务系统不存在或已关闭:" + vo.getBizSysNo());
				response = new Response(
						PlatformErrorCode.NOT_EXISTS_SYSTEM.getErrorCode(),
						PlatformErrorCode.NOT_EXISTS_SYSTEM.getDefaultMessage());
				return response;
			}

			// 查看此IP是否有访问此业务系统权限
			isAccessPermission = sysAppIPSService.isAccessPermission(
					vo.getBizSysNo(), requestIp);
			if (!isAccessPermission) {
				logger.info("此IP没有访问此业务系统权限:" + requestIp);
				response = new Response(
						PlatformErrorCode.IP_ACCESS_APP_PERMISSION_ERROR
								.getErrorCode(),
						PlatformErrorCode.IP_ACCESS_APP_PERMISSION_ERROR
								.getDefaultMessage());
				return response;
			}
			// 查看此信息类别编码是否有访问此业务系统权限
			isAccessPermission = sysInfoCategoryAPPSService.isAccessPermission(
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
			sysInfoCategory = sysInfoCategoryService
					.querySysInfoCategoryByCode(vo.getInfoCategoryCode());
			if (sysInfoCategory == null) {
				response = new Response(
						PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
						"此信息类别编码不存在");
				return response;
			}

			// 获取业务对象列表
			bizReqVos = vo.getList();
			if (bizReqVos == null || bizReqVos.isEmpty()) {
				response = new Response(
						PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
						"BizReqVo为空");
				return response;
			}

			// 如果批量提交数超过1,同步操作只支持每次一条
			if (bizReqVos.size() > 1) {
				response = new Response(
						PlatformErrorCode.EXCEED_BATCHLIMIT_ERROR
								.getErrorCode(),
						PlatformErrorCode.EXCEED_BATCHLIMIT_ERROR
								.getDefaultMessage());
				return response;
			}
			//
			if (bizReqVos.get(0) == null) {
				response = new Response(
						PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
						"BizReqVo为空");
				return response;
			}

			// 将bizReqVo对象转化成task实体对象
			BeanUtils.copyNotNullProperties((CollectReqVo) bizReqVos.get(0),
					task);
			// 设置task属性
			task.setReqId(request.getReqId());
			// 状态为已发送状态
			task.setStatus(Constants.TppConstants.SEND_STATUS_SENDED.getCode());
			// 设置任务不拆分
			task.setIsSeparate(Integer
					.valueOf(Constants.TppConstants.TASK_IS_SEPARATE_NO
							.getCode()));
			// 设置拆分数-1
			task.setSeparateCount(1);
			// 设置交易状态
			task.setTradeStatus(Constants.TppConstants.TRADE_STATE_MIDDLE
					.getCode());

			// 业务系统
			task.setBizSysNo(vo.getBizSysNo());

			// 信息类别编码
			task.setInfoCategoryCode(vo.getInfoCategoryCode());

			// 级别
			task.setPriority(sysInfoCategory.getPriority());

			// 业务类型
			task.setBizTypeNo(vo.getBizTypeNo());

			// 检验task实体对象
			response = Validate.getInstance().validate(task, InsertCheck.class,
					Default.class);
			// 如果task检验不通过
			if (Response.SUCCESS_RESPONSE_CODE != response.getCode()) {
				return response;
			}

			// 业务检验
			response = checkSyncCollect(task);
			// 如果业务检验不通过
			if (Response.SUCCESS_RESPONSE_CODE != response.getCode()) {
				return response;
			}
			// 将bizReqVo对象转化成info实体对象(task做了validate检验，info就不用做validate检验)
			BeanUtils.copyNotNullProperties((CollectReqVo) bizReqVos.get(0),
					info);
			// 设置info属性
			info.setReqId(request.getReqId());
			// 状态为交易处理中
			info.setStatus(Constants.TppConstants.TRADE_STATE_MIDDLE.getCode());
			// 设置交易流水号
			info.setTradeFlow(IDGenerateUtil.createTradeFlow(request
					.getBizTypeNo()));

			// 业务系统
			info.setBizSysNo(vo.getBizSysNo());

			// 信息类别编码
			info.setInfoCategoryCode(vo.getInfoCategoryCode());

			// 级别
			info.setPriority(sysInfoCategory.getPriority());

			// 业务类型
			info.setBizTypeNo(vo.getBizTypeNo());

			// 设置通知查询状态-待通知查询
			info.setNotifyQueryStatus(CollectInfo.NOTIFY_QUERY_STATUS_WAITING);
			
			// 设置推送表
			// 写入数据库
			collectTaskService.syncCollect(request, task, info);

			// 调用通道
			response = channelSendService.callChannel(info);

		} catch (Exception e) {
			logger.error("BrokerTradeImpl=syncCollect", e);
			// 系统默认异常信息
			String msg = PlatformErrorCode.SYSTEM_BUSY.getDefaultMessage();
			// 异常信息
			String exceptionMsg = ExceptionUtil.getExceptionMessage(e);
			// 如果是数据库抛出唯一约束错误，需要进行有好提醒，
			if (exceptionMsg != null
					&& exceptionMsg.contains(ConfigUtil.getInstance()
							.getSystemConfig().getOracleUniqeErrorCode())) {
				msg = PlatformErrorCode.REPEAT_TRADE_ERROR.getDefaultMessage();
			}
			// 如果是debug模式
			if (logger.isDebugEnable()) {
				msg = exceptionMsg;
			}

			response = new Response(
					PlatformErrorCode.SYSTEM_BUSY.getErrorCode(), msg);
			return response;
		}
		// 设置流水号
		response.setFlowId(request.getReqId());
		return response;
	}

	/**
	 * 异步接口 提交单笔批量功能
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Response asynCollect(RequestVo vo) {

		Response response = null;
		// 交易请求
		Request request = new Request();

		// 实体对象列表
		List<CollectTask> tasks = new ArrayList<CollectTask>();

		// 业务对象列表
		List<BizReqVo> bizReqVos = null;

		// 实体对象
		CollectTask task = null;

		// 批量提交数
		long batchLimit = 0l;

		// 是否有访问权限
		boolean isAccessPermission = false;

		// 信息类别(此对象中包含业务级别等字段信息)
		SysInfoCategory sysInfoCategory = null;

		// 请求方IP.
		String requestIp = null;

		// 请求系统
		SysApp sysApp = null;

		// 检验Request
		try {
			// 获取请求IP
			requestIp = RpcContext.getContext().getRemoteHost();

			batchLimit = ConfigUtil.getInstance().getSystemConfig()
					.getBatchLimit();

			if (vo == null) {
				response = new Response(
						PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
						"RequestVo为空");
				return response;
			}

			// 设置业务类型
			vo.setBizTypeNo(BizType.BROKER_COLLECT.getCode());
			// 将vo对象转化成实体对象
			BeanUtils.copyNotNullProperties(vo, request);

			// 设置request相关属性
			// 设置reqId
			request.setReqId(IDGenerateUtil.createReqId());
			// 设置非补单操作
			request.setIsHandAdd(Request.ISHANDADD_NO);
			// 设置为异步
			request.setIsSync(Request.ISSYNC_NO);

			// 设置IP
			request.setIp(requestIp);

			// 检验request实体对象
			response = Validate.getInstance().validate(request,
					InsertCheck.class, Default.class);
			// 如果request检验不通过
			if (Response.SUCCESS_RESPONSE_CODE != response.getCode()) {
				return response;
			}
			// 查看请求业务系统是否存在
			sysApp = sysAppService.querySysApp(vo.getBizSysNo());
			// 如果请求业务系统不存在
			if (sysApp == null) {
				logger.info("业务系统不存在或已关闭:" + vo.getBizSysNo());
				response = new Response(
						PlatformErrorCode.NOT_EXISTS_SYSTEM.getErrorCode(),
						PlatformErrorCode.NOT_EXISTS_SYSTEM.getDefaultMessage());
				return response;
			}
			// 查看此IP是否有访问此业务系统权限
			isAccessPermission = sysAppIPSService.isAccessPermission(
					vo.getBizSysNo(), requestIp);
			if (!isAccessPermission) {
				logger.info("此IP没有访问此业务系统权限:" + requestIp);
				response = new Response(
						PlatformErrorCode.IP_ACCESS_APP_PERMISSION_ERROR
								.getErrorCode(),
						PlatformErrorCode.IP_ACCESS_APP_PERMISSION_ERROR
								.getDefaultMessage());
				return response;
			}
			// 查看此信息类别编码是否有访问此业务系统权限
			isAccessPermission = sysInfoCategoryAPPSService.isAccessPermission(
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
			sysInfoCategory = sysInfoCategoryService
					.querySysInfoCategoryByCode(vo.getInfoCategoryCode());
			if (sysInfoCategory == null) {
				response = new Response(
						PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
						"此信息类别编码不存在");
				return response;
			}

			// 获取业务对象列表
			bizReqVos = vo.getList();
			if (bizReqVos == null || bizReqVos.isEmpty()) {
				response = new Response(
						PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
						"BizReqVo为空");
				return response;
			}

			// 如果批量提交数超过系统定义最大值
			if (bizReqVos.size() > batchLimit) {
				response = new Response(
						PlatformErrorCode.EXCEED_BATCHLIMIT_ERROR
								.getErrorCode(),
						PlatformErrorCode.EXCEED_BATCHLIMIT_ERROR
								.getDefaultMessage());
				return response;
			}
			//根据信息类别加载银行通道配置(TPP设置为按照银行指定通道策略时加载 银行和通道配置关系)
			Map<String,String> channelMap = new HashMap<String,String>();
			if(ChannelRulesConstants.CHANNEL_RULES_BANKS.getCode().equals(sysInfoCategory.getChannelRules().trim())){
				channelMap = sysInfoCategoryBanksService.queryInfoCategoryBanks(vo.getInfoCategoryCode());
			}
			
			
			// 批量业务对象转化成实体对象
			for (BizReqVo bizReqVo : bizReqVos) {
				if (bizReqVo != null) {
					task = new CollectTask();
					// 将bizReqVo对象转化成实体对象
					BeanUtils.copyNotNullProperties((CollectReqVo) bizReqVo,
							task);
					// 设置task属性
					task.setReqId(request.getReqId());
					// 状态为待发送状态
					task.setStatus(Constants.TppConstants.SEND_STATUS_WAIT
							.getCode());

					// 设置交易状态
					task.setTradeStatus(Constants.TppConstants.TRADE_STATE_MIDDLE
							.getCode());

					// 业务系统
					task.setBizSysNo(vo.getBizSysNo());

					// 信息类别
					task.setInfoCategoryCode(vo.getInfoCategoryCode());

					// 级别
					task.setPriority(sysInfoCategory.getPriority());

					// 业务类型
					task.setBizTypeNo(vo.getBizTypeNo());
					
					task.setSpare1(bizReqVo.getSpare1());
					
					task.setPaySysNo(ChannelRulesUtils.getPaymentChannel(sysInfoCategory, channelMap, (CollectReqVo) bizReqVo));
					// 如果是走路由规则，默认进行拆单处理
					if(ThirdType.ROUTEPAY.getCode().equals(task.getPaySysNo())){
						task.setIsNeedSpilt(1);
					}
					// 检验task实体对象
					response = Validate.getInstance().validate(task,
							InsertCheck.class, Default.class);
					// 如果task检验不通过
					if (Response.SUCCESS_RESPONSE_CODE != response.getCode()) {
						return response;
					}

					tasks.add(task);
				}
			}
			// 如果批量对象都为空
			if (tasks != null && tasks.isEmpty()) {
				response = new Response(
						PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
						"BizReqVo为空");
				return response;
			}
			// 写入数据库
			collectTaskService.asynCollect(request, tasks);

		} catch (Exception e) {
			logger.error("BrokerTradeImpl=asynCollect", e);
			// 系统默认异常信息
			String msg = PlatformErrorCode.SYSTEM_BUSY.getDefaultMessage();

			// 异常信息
			String exceptionMsg = ExceptionUtil.getExceptionMessage(e);
			// 如果是数据库抛出唯一约束错误，需要进行有好提醒，
			if (exceptionMsg != null
					&& exceptionMsg.contains(ConfigUtil.getInstance()
							.getSystemConfig().getOracleUniqeErrorCode())) {
				msg = PlatformErrorCode.REPEAT_TRADE_ERROR.getDefaultMessage();
			}

			// 如果是debug模式
			if (logger.isDebugEnable()) {
				msg = ExceptionUtil.getExceptionMessage(e);
			}
			response = new Response(
					PlatformErrorCode.SYSTEM_BUSY.getErrorCode(), msg);
			return response;
		}
		// 设置成功返回
		response = new Response(Response.SUCCESS_RESPONSE_CODE);
		response.setFlowId(request.getReqId());
		return response;
	}

	/**
	 * 检验同步代扣业务
	 * 
	 * @param task
	 * @return
	 */
	private Response checkSyncCollect(CollectTask task) {
		Response response = new Response(Response.SUCCESS_RESPONSE_CODE);
		// 如果银行编码为空
		if (StringUtils.isEmpty(task.getPayerBankCode())) {
			response = new Response(
					PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
					"payerBankCode为空");
			return response;
		}
		// 如果银行卡号为空
		if (StringUtils.isEmpty(task.getPayerBankCardNo())) {
			response = new Response(
					PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
					"payerBankCardNo为空");
			return response;
		}

		// 如果银行卡类型为空
		if (StringUtils.isEmpty(task.getPayerBankCardType())) {
			response = new Response(
					PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
					"payerBankCardType为空");
			return response;
		}
		// 支付通道不支持该银行
		if (ThirdPPCacheContainer.tppBankCodeToThirdBankCodeMap.get(task
				.getPaySysNo() + task.getPayerBankCode()) == null) {
			response = new Response(
					PlatformErrorCode.NOT_SUPPORT_BANK_ERROR.getErrorCode(),
					PlatformErrorCode.NOT_SUPPORT_BANK_ERROR
							.getDefaultMessage());
			return response;
		}
		// 支付通道银行额度检验
		BigDecimal maxMoney = ThirdPPCacheContainer.tppBankCodeToThirdBankCollectMaxMoneyMap
				.get(task.getPaySysNo() + task.getPayerBankCode());
		// 如果代扣额度超过支付通道银行所规定最大额度(maxMoney <= 0时认为没有上限限制)
		if (maxMoney != null && BigDecimal.ZERO.compareTo(maxMoney) < 0) {
			if (maxMoney.compareTo(task.getAmount()) < 0) {
				response = new Response(
						PlatformErrorCode.MAX_MONEY_BANK_ERROR.getErrorCode(),
						PlatformErrorCode.MAX_MONEY_BANK_ERROR
								.getDefaultMessage());
				return response;
			}
		}
		return response;
	}

	public Response checkSyncSignMessage(SignMsgReqVo vo){
		Response response = new Response(Response.SUCCESS_RESPONSE_CODE);

		// 如果第三方支付平台
		if (StringUtils.isEmpty(vo.getPaySysNo())) {
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "paySysNo为空");
			return response;
		}

		// 如果银行编码为空
		if (StringUtils.isEmpty(vo.getBankCode())) {
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "bankCode为空");
			return response;
		}

		// 如果银行名称为空
		if (StringUtils.isEmpty(vo.getBankName())) {
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "bankName为空");
			return response;
		}

		// 如果账号为空
		if (StringUtils.isEmpty(vo.getAccountNo())) {
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "accountNo为空");
			return response;
		}

		// 如果账号类型为空
		if (StringUtils.isEmpty(vo.getAccountType())) {
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "accountType为空");
			return response;
		}

		// 如果账号名为空
		if (StringUtils.isEmpty(vo.getAccountName())) {
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "accountName为空");
			return response;
		}

		// 如果账号属性为空
		if (StringUtils.isEmpty(vo.getAccountProp())) {
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "accountProp为空");
			return response;
		}

		// 如果证件类型为空
		if (StringUtils.isEmpty(vo.getIdType())) {
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "idType为空");
			return response;
		}

		// 如果证件号为空
		if (StringUtils.isEmpty(vo.getIdNum())) {
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "idNum为空");
			return response;
		}

		// 如果手机号为空
		if (StringUtils.isEmpty(vo.getTel())) {
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "tel为空");
			return response;
		}

		return response;
	}


	public Response checkSyncSign(SignReqVo vo){
		Response response = new Response(Response.SUCCESS_RESPONSE_CODE);

		if (StringUtils.isEmpty(vo.getSrcFlowId())) {
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "srcFlowId为空");
			return response;
		}

		if (StringUtils.isEmpty(vo.getVerCode())) {
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "verCode为空");
			return response;
		}

		return response;

	}

	/**
	 * 同步代付单笔接口，vo里面list明细为一条
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Response syncPay(RequestVo vo) {
		Response response = null;
		// 交易请求
		Request request = new Request();
		// 实体对象-任务
		PayTask task = new PayTask();
		// 实体对象-交易对象
		PayInfo info = new PayInfo();

		// 业务对象列表
		List<BizReqVo> bizReqVos = null;
		// 是否有访问权限
		boolean isAccessPermission = false;

		// 信息类别(此对象中包含业务级别等字段信息)
		SysInfoCategory sysInfoCategory = null;
		// 请求方IP.
		String requestIp = null;
		// 请求系统
		SysApp sysApp = null;
		
		//银行卡查询返回实体对象
		List<PayTask> taskInfo =  new ArrayList<PayTask>();
		
		try {
			// 获取请求IP
			requestIp = RpcContext.getContext().getRemoteHost();
			
			if (vo == null) {
				response = new Response(
						PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
						"RequestVo 为空");
				return response;
			}
			// 设置业务类型
			vo.setBizTypeNo(BizType.BROKER_PAY.getCode());
			// 将vo对象转化成实体对象
			BeanUtils.copyNotNullProperties(vo, request);
			// 设置request相关属性
			// 设置reqId
			request.setReqId(IDGenerateUtil.createReqId());
			// 设置非补单操作
			request.setIsHandAdd(Request.ISHANDADD_NO);
			// 设置为同步
			request.setIsSync(Request.ISSYNC_YES);
			// 设置IP
			request.setIp(requestIp);

			// 检验request实体对象
			response = Validate.getInstance().validate(request,
					InsertCheck.class, Default.class);
			// 如果request检验不通过
			if (Response.SUCCESS_RESPONSE_CODE != response.getCode()) {
				return response;
			}
			// 查看请求业务系统是否存在
			sysApp = sysAppService.querySysApp(vo.getBizSysNo());
			// 如果请求业务系统不存在
			if (sysApp == null) {
				logger.info("业务系统不存在或已关闭:" + vo.getBizSysNo());
				response = new Response(
						PlatformErrorCode.NOT_EXISTS_SYSTEM.getErrorCode(),
						PlatformErrorCode.NOT_EXISTS_SYSTEM.getDefaultMessage());
				return response;
			}

			// 查看此IP是否有访问此业务系统权限
			isAccessPermission = sysAppIPSService.isAccessPermission(
					vo.getBizSysNo(), requestIp);
			if (!isAccessPermission) {
				logger.info("此IP没有访问此业务系统权限:" + requestIp);
				response = new Response(
						PlatformErrorCode.IP_ACCESS_APP_PERMISSION_ERROR
								.getErrorCode(),
						PlatformErrorCode.IP_ACCESS_APP_PERMISSION_ERROR
								.getDefaultMessage());
				return response;
			}
			// 查看此信息类别编码是否有访问此业务系统权限
			isAccessPermission = sysInfoCategoryAPPSService.isAccessPermission(
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
			sysInfoCategory = sysInfoCategoryService
					.querySysInfoCategoryByCode(vo.getInfoCategoryCode());
			if (sysInfoCategory == null) {
				response = new Response(
						PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
						"此信息类别编码不存在");
				return response;
			}

			// 获取业务对象列表
			bizReqVos = vo.getList();
			if (bizReqVos == null || bizReqVos.isEmpty()) {
				response = new Response(
						PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
						"BizReqVo为空");
				return response;
			}

			// 如果批量提交数超过1,同步操作只支持每次一条
			if (bizReqVos.size() > 1) {
				response = new Response(
						PlatformErrorCode.EXCEED_BATCHLIMIT_ERROR
								.getErrorCode(),
						PlatformErrorCode.EXCEED_BATCHLIMIT_ERROR
								.getDefaultMessage());
				return response;
			}
			//
			if (bizReqVos.get(0) == null) {
				response = new Response(
						PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
						"BizReqVo为空");
				return response;
			}

			// 将bizReqVo对象转化成task实体对象
			BeanUtils.copyNotNullProperties((PayReqVo) bizReqVos.get(0),
					task);
			// 设置task属性
			task.setReqId(request.getReqId());
			// 状态为已发送状态
			task.setStatus(Constants.TppConstants.SEND_STATUS_SENDED.getCode());
			// 设置任务不拆分
			task.setIsSeparate(Integer
					.valueOf(Constants.TppConstants.TASK_IS_SEPARATE_NO
							.getCode()));
			// 设置拆分数-1
			task.setSeparateCount(1);
			// 设置交易状态
			task.setTradeStatus(Constants.TppConstants.TRADE_STATE_MIDDLE
					.getCode());

			// 业务系统
			task.setBizSysNo(vo.getBizSysNo());

			// 信息类别编码
			task.setInfoCategoryCode(vo.getInfoCategoryCode());

			// 级别
			task.setPriority(sysInfoCategory.getPriority());

			// 业务类型
			task.setBizType(vo.getBizTypeNo());

			// 检验task实体对象
			response = Validate.getInstance().validate(task, InsertCheck.class,
					Default.class);
			// 如果task检验不通过
			if (Response.SUCCESS_RESPONSE_CODE != response.getCode()) {
				return response;
			}

			// 业务检验
			response = checkSyncPay(task);
			// 如果业务检验不通过
			if (Response.SUCCESS_RESPONSE_CODE != response.getCode()) {
				return response;
			}
			// 将bizReqVo对象转化成info实体对象(task做了validate检验，info就不用做validate检验)
			BeanUtils.copyNotNullProperties((PayReqVo) bizReqVos.get(0),
					info);
			// 设置info属性
			info.setReqId(request.getReqId());
			// 状态为交易处理中
			info.setStatus(Constants.TppConstants.TRADE_STATE_MIDDLE.getCode());
			// 设置交易流水号
			info.setTradeFlow(IDGenerateUtil.createTradeFlow(request
					.getBizTypeNo()));

			// 业务系统
			info.setBizSysNo(vo.getBizSysNo());

			// 信息类别编码
			info.setInfoCategoryCode(vo.getInfoCategoryCode());

			// 级别
			info.setPriority(sysInfoCategory.getPriority());

			// 业务类型
			info.setBizType(vo.getBizTypeNo());

			// 设置通知查询状态-待通知查询
			info.setNotifyQueryStatus(PayInfo.NOTIFY_QUERY_STATUS_NO);
			
			// 设置通知合并状态-待通知合并
			info.setNotifyMergeStatus(PayInfo.NOTIFY_MERGE_STATUS_NO);

			// 过滤请求白名单
//			response = checkRequestWhiteList(vo, task);
			
			// 如果request检验不通过
			/*if (Response.SUCCESS_RESPONSE_CODE != response.getCode()) {
				return response;
			}*/

			//由银行卡号获取当天记录
			taskInfo = payTaskService.queryPayTaskByReceiverBankCardNo(task.getReceiverBankCardNo());
			if(null != taskInfo && taskInfo.size() > 0){
				for(PayTask pt : taskInfo){
					if(Constants.TppConstants.TRADE_STATE_SUCCESS.getCode().equals(pt.getTradeStatus()) || Constants.TppConstants.TRADE_STATE_MIDDLE.getCode().equals(pt.getTradeStatus())){
						//根据卡号获取任务表为 交易成功 OR 交易处理中
						response = new Response(
								PlatformErrorCode.REPEAT_TRADE_ERROR.getErrorCode(),
								"该卡号今日代付请求已达上限");
						return response;
					}
				}
				// 	存在当天记录且状态为 非交易成功 和处理中  写入数据库
				payTaskService.syncPay(request, task, info);
			} else {
				// 不存在当天记录  写入数据库
				payTaskService.syncPay(request, task, info);
			}

			// 调用通道
			response = channelSendService.callChannel(info);

			logger.info("trade resp[code:" + response.getCode() + " msg:" + response.getMsg() + ",tradeFlow:" + info.getTradeFlow() + "]");
		} catch (Exception e) {
			logger.error("BrokerTradeImpl=syncPay", e);
			// 系统默认异常信息
			String msg = PlatformErrorCode.SYSTEM_BUSY.getDefaultMessage();
			// 异常信息
			String exceptionMsg = ExceptionUtil.getExceptionMessage(e);
			// 如果是数据库抛出唯一约束错误，需要进行有好提醒，
			if (exceptionMsg != null
					&& exceptionMsg.contains(ConfigUtil.getInstance()
							.getSystemConfig().getOracleUniqeErrorCode())) {
				msg = PlatformErrorCode.REPEAT_TRADE_ERROR.getDefaultMessage();
			}
			// 如果是debug模式
			if (logger.isDebugEnable()) {
				msg = exceptionMsg;
			}

			response = new Response(
					PlatformErrorCode.SYSTEM_BUSY.getErrorCode(), msg);
			return response;
		}
		// 设置流水号
		response.setFlowId(request.getReqId());
		return response;
	}

	/**
	 * 检验同步代付业务
	 * 
	 * @param task
	 * @return response
	 */
	private Response checkSyncPay(PayTask task) {
		Response response = new Response(Response.SUCCESS_RESPONSE_CODE);
		// 如果银行编码为空
		if (StringUtils.isEmpty(task.getReceiverBankCode())) {
			response = new Response(
					PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
					"receiverBankCode为空");
			return response;
		}
		return response;
	}
	
	private Response checkRequestWhiteList(RequestVo vo, PayTask task) throws SQLException {
		Response response = new Response(Response.SUCCESS_RESPONSE_CODE);
		// 请求不在白名单内，则过滤掉请求
		if (!tppWhiteListService.isAccessPermission(vo.getInfoCategoryCode(), task.getReceiverBankCardNo(), task.getReceiverAccountNo(), vo.getBizSysNo())) {
			response = new Response(
					PlatformErrorCode.ACCESS_WHITELIST_PERMISSION_ERROR.getErrorCode(),
					PlatformErrorCode.ACCESS_WHITELIST_PERMISSION_ERROR.getDefaultMessage());
			return response;
		}
		return response;
	}

	/**
	 * 实时代付(融资)查询
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Response queryPay(QueryReqVo vo) {
		Response response = null;

		QueryPayTask queryPayTask = new QueryPayTask();
		// 请求方IP.
		String requestIp = null;
		// 请求系统
		SysApp sysApp = null;
		// 是否有访问权限
		boolean isAccessPermission = false;

		// 信息类别(此对象中包含业务级别等字段信息)
		SysInfoCategory sysInfoCategory = null;

		List<PayTask> payTasks = null;

		List<PayInfo> payInfos = null;
		try{

			// 获取请求IP
			requestIp = RpcContext.getContext().getRemoteHost();
			if (vo == null) {
				response = new Response(
						PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
						"QueryReqVo 为空");
				return response;
			}


			// 将bizReqVo对象转化成task实体对象
			BeanUtils.copyNotNullProperties(vo,
					queryPayTask);
			//合法性校验
			response = Validate.getInstance().validate(queryPayTask, InsertCheck.class,Default.class);
			// 如果vo 检验不通过
			if (Response.SUCCESS_RESPONSE_CODE != response.getCode()) {
				return response;
			}
			// 查看请求业务系统是否存在
			sysApp = sysAppService.querySysApp(vo.getBizSysNo());
			// 如果请求业务系统不存在
			if (sysApp == null) {
				logger.info("业务系统不存在或已关闭:" + vo.getBizSysNo());
				response = new Response(
						PlatformErrorCode.NOT_EXISTS_SYSTEM.getErrorCode(),
						PlatformErrorCode.NOT_EXISTS_SYSTEM.getDefaultMessage());
				return response;
			}

			// 查看此IP是否有访问此业务系统权限
			isAccessPermission = sysAppIPSService.isAccessPermission(
					vo.getBizSysNo(), requestIp);
			if (!isAccessPermission) {
				logger.info("此IP没有访问此业务系统权限:" + requestIp);
				response = new Response(
						PlatformErrorCode.IP_ACCESS_APP_PERMISSION_ERROR
								.getErrorCode(),
						PlatformErrorCode.IP_ACCESS_APP_PERMISSION_ERROR
								.getDefaultMessage());
				return response;
			}
			// 查看此信息类别编码是否有访问此业务系统权限
			isAccessPermission = sysInfoCategoryAPPSService.isAccessPermission(
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
			sysInfoCategory = sysInfoCategoryService
					.querySysInfoCategoryByCode(vo.getInfoCategoryCode());
			if (sysInfoCategory == null) {
				response = new Response(
						PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
						"此信息类别编码不存在");
				return response;
			}
		
			HashMap<String, Object> param = new HashMap<>();
			param.put("bizFlow",vo.getBizFlow());
			param.put("bizSysNo",vo.getBizSysNo());
			//payTasks = payTaskService.queryPayTaskBybizFlow(param);
			payInfos = payTaskService.queryPayInfo(param);
			if (payInfos == null || payInfos.size()==0) {
				// 不存在两周内的任务
				response = new Response(PlatformErrorCode.NOT_EXISTS_TRADE_INFO.getErrorCode(),"查无此交易记录!");
				return response;
			}
			for(PayInfo pt:payInfos){
				if (!Constants.TppConstants.TRADE_STATE_MIDDLE.getCode().equals(pt.getStatus())) {
					// 状态为非处理中
					response = new Response(pt.getStatus(),pt.getFailReason());
					return response;
				}
			}

			// 设置代付通道
			queryPayTask.setPaySysNo(payInfos.get(0).getPaySysNo());
			queryPayTask.setTradeFlow(payInfos.get(0).getTradeFlow());
		    response = channelSendService.queryPayChannel(payInfos.get(0),queryPayTask);
		    
		} catch (Exception e) {

			logger.error("BrokerTradeImpl=queryPay", e);
			// 系统默认异常信息
			String msg = PlatformErrorCode.SYSTEM_BUSY.getDefaultMessage();
			// 异常信息
			String exceptionMsg = ExceptionUtil.getExceptionMessage(e);
			// 如果是数据库抛出唯一约束错误，需要进行有好提醒，
			if (exceptionMsg != null
					&& exceptionMsg.contains(ConfigUtil.getInstance()
					.getSystemConfig().getOracleUniqeErrorCode())) {
				msg = PlatformErrorCode.REPEAT_TRADE_ERROR.getDefaultMessage();
			}
			// 如果是debug模式
			if (logger.isDebugEnable()) {
				msg = exceptionMsg;
			}
			response = new Response(PlatformErrorCode.SYSTEM_BUSY.getErrorCode(), msg);
			return response;
		}
		// 设置流水号
//		response.setFlowId(request.getReqId());
		return response;
	}

	/**
	 * 协议支付签约短信触发
	 * @param requestVo
	 * @author wulj
	 * @return
	 */
	@Override
	public Response syncSignMessage(RequestVo requestVo) {
		logger.info("签约短信触发，requestVo" + JSONObject.toJSONString(requestVo));
		Response response = null;
		// 交易请求
		Request request = new Request();
		// 实体对象-交易对象
		SignMessageInfo signMessageInfo = new SignMessageInfo();
		// 业务对象列表
		List<BizReqVo> bizReqVos = null;
		// 是否有访问权限
		boolean isAccessPermission = false;
		// 信息类别(此对象中包含业务级别等字段信息)
		SysInfoCategory sysInfoCategory = null;
		// 请求方IP.
		String requestIp = null;
		// 请求系统
		SysApp sysApp = null;
		try{
			requestIp = RpcContext.getContext().getRemoteHost();

			logger.info("调用方IP" + requestIp);

			if(requestVo == null){
				return new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "RequestVo 为空");
			}

			// 将vo对象转化成实体对象
			BeanUtils.copyNotNullProperties(requestVo, request);
			request.setReqId(IDGenerateUtil.createReqId());
			request.setIsHandAdd(Request.ISHANDADD_NO);
			request.setIsSync(Request.ISSYNC_YES);
			request.setIp(requestIp);

			// 检验request实体对象
			response = Validate.getInstance().validate(request, InsertCheck.class, Default.class);
			// 如果request检验不通过
			if (Response.SUCCESS_RESPONSE_CODE != response.getCode()) {
				return response;
			}

            // 查看请求业务系统是否存在
            sysApp = sysAppService.querySysApp(requestVo.getBizSysNo());
            if (sysApp == null) {
                logger.info("业务系统不存在或已关闭:" + requestVo.getBizSysNo());
                return new Response(PlatformErrorCode.NOT_EXISTS_SYSTEM.getErrorCode(),
                        PlatformErrorCode.NOT_EXISTS_SYSTEM.getDefaultMessage());
            }

            // 查看此IP是否有访问此业务系统权限
            isAccessPermission = sysAppIPSService.isAccessPermission(
                    requestVo.getBizSysNo(), requestIp);
            if (!isAccessPermission) {
                logger.info("此IP没有访问此业务系统权限:" + requestIp);
                return new Response(PlatformErrorCode.IP_ACCESS_APP_PERMISSION_ERROR.getErrorCode(),
                        PlatformErrorCode.IP_ACCESS_APP_PERMISSION_ERROR.getDefaultMessage());
            }
            // 查看此信息类别编码是否有访问此业务系统权限
            isAccessPermission = sysInfoCategoryAPPSService.isAccessPermission(
                    requestVo.getInfoCategoryCode(), requestVo.getBizSysNo());
            if (!isAccessPermission) {
                logger.info("此信息类别编码没有有访问此业务系统权限:" + requestVo.getInfoCategoryCode());
                return new Response(PlatformErrorCode.INFO_CATEGORY_ACCESS_APP_PERMISSION_ERROR.getErrorCode(),
                        PlatformErrorCode.INFO_CATEGORY_ACCESS_APP_PERMISSION_ERROR.getDefaultMessage());
            }

			// 查询信息类别
			sysInfoCategory = sysInfoCategoryService.querySysInfoCategoryByCode(requestVo.getInfoCategoryCode());
			if (sysInfoCategory == null) {
				return new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "此信息类别编码不存在");
			}

			// 获取业务对象列表
			bizReqVos = requestVo.getList();
			if (bizReqVos == null || bizReqVos.isEmpty()) {
				response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "BizReqVo为空");
				return response;
			}

			// 如果批量提交数超过1,同步操作只支持每次一条
			if (bizReqVos.size() > 1) {
				return new Response(PlatformErrorCode.EXCEED_BATCHLIMIT_ERROR.getErrorCode(),
						PlatformErrorCode.EXCEED_BATCHLIMIT_ERROR.getDefaultMessage());
			}

			// 业务VO不能为null
			if (bizReqVos.get(0) == null) {
				return new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "BizReqVo为空");
			}

			SignMsgReqVo signMsgReqVo = (SignMsgReqVo)requestVo.getList().get(0);

			// 业务检验
			response = checkSyncSignMessage(signMsgReqVo);
			// 如果业务检验不通过
			if (Response.SUCCESS_RESPONSE_CODE != response.getCode()) {
				return response;
			}

			BeanUtils.copyNotNullProperties(signMsgReqVo, signMessageInfo);

			// 请求流水号
			signMessageInfo.setReqId(request.getReqId());
			// 状态为交易处理中
			signMessageInfo.setStatus(Constants.TppConstants.TRADE_STATE_MIDDLE.getCode());
			// 交易流水号
			signMessageInfo.setTradeFlow(IDGenerateUtil.createTradeFlow(request.getBizTypeNo()));
			// 业务系统号
			signMessageInfo.setBizSysNo(requestVo.getBizSysNo());
			// 业务类型
			signMessageInfo.setBizTypeNo(requestVo.getBizTypeNo());
			// 信息类别编码
			signMessageInfo.setInfoCategoryCode(requestVo.getInfoCategoryCode());
			// todo 应该是按照信息类别去判断 第三方通道
			signMessageInfo.setPaySysNo(signMsgReqVo.getPaySysNo());
			// 将"协议支付签约短信触发记录"写入数据库


			signMessageInfoService.saveOrUpdateSignInfo(signMessageInfo);

			request.setPaySysNo(signMessageInfo.getPaySysNo());
			requestDao.insert(request);

			// 调用通道
			response = channelSendService.callChannel(signMessageInfo);
		}catch (Exception e){
			logger.error("协议支付签约短信触发调用异常", e);
			// 系统默认异常信息
			String msg = PlatformErrorCode.SYSTEM_BUSY.getDefaultMessage();
			// 异常信息
			String exceptionMsg = ExceptionUtil.getExceptionMessage(e);
			// 如果是数据库抛出唯一约束错误，需要进行有好提醒，
			if (exceptionMsg != null && exceptionMsg.contains(ConfigUtil.getInstance().getSystemConfig().getOracleUniqeErrorCode())) {
				msg = PlatformErrorCode.REPEAT_TRADE_ERROR.getDefaultMessage();
			}
			// 如果是debug模式
			if (logger.isDebugEnable()) {
				msg = exceptionMsg;
			}
			response = new Response(PlatformErrorCode.SYSTEM_BUSY.getErrorCode(), msg);

			return response;
		}

		return response;
	}

	/**
	 * 协议支付签约
	 * @author wulj
	 * @param requestVo
	 * @return
	 */
	@Override
	public Response syncSign(RequestVo requestVo) {
        logger.info("签约短信触发，requestVo" + JSONObject.toJSONString(requestVo));
		Response response = null;
		// 交易请求
		Request request = new Request();
		// 实体对象-交易对象
		SignInfo signInfo = new SignInfo();
		// 业务对象列表
		List<BizReqVo> bizReqVos = null;
		// 是否有访问权限
		boolean isAccessPermission = false;
		// 信息类别(此对象中包含业务级别等字段信息)
		SysInfoCategory sysInfoCategory = null;
		// 请求方IP.
		String requestIp = null;
		// 请求系统
		SysApp sysApp = null;
		try{
			requestIp = RpcContext.getContext().getRemoteHost();
            logger.info("调用方IP" + requestIp);
			if(requestVo == null){
				return new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "RequestVo 为空");
			}

			// 将vo对象转化成实体对象
			BeanUtils.copyNotNullProperties(requestVo, request);
			request.setReqId(IDGenerateUtil.createReqId());
			request.setIsHandAdd(Request.ISHANDADD_NO);
			request.setIsSync(Request.ISSYNC_YES);
			request.setIp(requestIp);

			// 检验request实体对象
			response = Validate.getInstance().validate(request, InsertCheck.class, Default.class);
			// 如果request检验不通过
			if (Response.SUCCESS_RESPONSE_CODE != response.getCode()) {
				return response;
			}

            // 查看请求业务系统是否存在
            sysApp = sysAppService.querySysApp(requestVo.getBizSysNo());
            if (sysApp == null) {
                logger.info("业务系统不存在或已关闭:" + requestVo.getBizSysNo());
                return new Response(PlatformErrorCode.NOT_EXISTS_SYSTEM.getErrorCode(),
                        PlatformErrorCode.NOT_EXISTS_SYSTEM.getDefaultMessage());
            }

            // 查看此IP是否有访问此业务系统权限
            isAccessPermission = sysAppIPSService.isAccessPermission(
                    requestVo.getBizSysNo(), requestIp);
            if (!isAccessPermission) {
                logger.info("此IP没有访问此业务系统权限:" + requestIp);
                return new Response(PlatformErrorCode.IP_ACCESS_APP_PERMISSION_ERROR.getErrorCode(),
                        PlatformErrorCode.IP_ACCESS_APP_PERMISSION_ERROR.getDefaultMessage());
            }
            // 查看此信息类别编码是否有访问此业务系统权限
            isAccessPermission = sysInfoCategoryAPPSService.isAccessPermission(
                    requestVo.getInfoCategoryCode(), requestVo.getBizSysNo());
            if (!isAccessPermission) {
                logger.info("此信息类别编码没有有访问此业务系统权限:" + requestVo.getInfoCategoryCode());
                return new Response(PlatformErrorCode.INFO_CATEGORY_ACCESS_APP_PERMISSION_ERROR.getErrorCode(),
                        PlatformErrorCode.INFO_CATEGORY_ACCESS_APP_PERMISSION_ERROR.getDefaultMessage());
            }

			// 查询信息类别
			sysInfoCategory = sysInfoCategoryService.querySysInfoCategoryByCode(requestVo.getInfoCategoryCode());
			if (sysInfoCategory == null) {
				return new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "此信息类别编码不存在");
			}

			// 获取业务对象列表
			bizReqVos = requestVo.getList();
			if (bizReqVos == null || bizReqVos.isEmpty()) {
				response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "BizReqVo为空");
				return response;
			}

			// 如果批量提交数超过1,同步操作只支持每次一条
			if (bizReqVos.size() > 1) {
				return new Response(PlatformErrorCode.EXCEED_BATCHLIMIT_ERROR.getErrorCode(),
						PlatformErrorCode.EXCEED_BATCHLIMIT_ERROR.getDefaultMessage());
			}

			// 业务VO不能为null
			if (bizReqVos.get(0) == null) {
				return new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), "BizReqVo为空");
			}

			SignReqVo signReqVo = (SignReqVo)requestVo.getList().get(0);

			// 业务检验
			response = checkSyncSign(signReqVo);
			// 如果业务检验不通过
			if (Response.SUCCESS_RESPONSE_CODE != response.getCode()) {
				return response;
			}

			// 判断原交易流水号是否存在
			SignMessageInfo signMessageInfo = signMessageInfoService.getByReqId(signReqVo.getSrcFlowId());
			if(signMessageInfo == null){
				return new Response(PlatformErrorCode.NOT_EXISTS_SIGN_MESSAGE_INFO.getErrorCode(),
						PlatformErrorCode.NOT_EXISTS_SIGN_MESSAGE_INFO.getDefaultMessage());
			}

			BeanUtils.copyNotNullProperties(signReqVo, signInfo);

			// 请求流水号
			signInfo.setReqId(request.getReqId());
			// 状态为交易处理中
			signInfo.setStatus(Constants.TppConstants.TRADE_STATE_MIDDLE.getCode());
			// 交易流水号
			signInfo.setTradeFlow(IDGenerateUtil.createTradeFlow(request.getBizTypeNo()));
			// 业务系统号
			signInfo.setBizSysNo(requestVo.getBizSysNo());
			// 业务类型
			signInfo.setBizTypeNo(requestVo.getBizTypeNo());
			// 信息类别编码
			signInfo.setInfoCategoryCode(requestVo.getInfoCategoryCode());
			// todo 应该是按照信息类别去判断 第三方通道
			signInfo.setPaySysNo(signReqVo.getPaySysNo());
			// 原交易流水号
			signInfo.setSrcReqSn(signMessageInfo.getTradeFlow());
			// 保存
			signInfoService.saveOrUpdateSignInfo(signInfo);

			request.setPaySysNo(signInfo.getPaySysNo());
			requestDao.insert(request);

			// 调用通道
			response = channelSendService.callChannel(signInfo);
		}catch (Exception e){
			logger.error("协议支付签约调用异常", e);
			// 系统默认异常信息
			String msg = PlatformErrorCode.SYSTEM_BUSY.getDefaultMessage();
			// 异常信息
			String exceptionMsg = ExceptionUtil.getExceptionMessage(e);
			// 如果是数据库抛出唯一约束错误，需要进行有好提醒，
			if (exceptionMsg != null && exceptionMsg.contains(ConfigUtil.getInstance().getSystemConfig().getOracleUniqeErrorCode())) {
				msg = PlatformErrorCode.REPEAT_TRADE_ERROR.getDefaultMessage();
			}
			// 如果是debug模式
			if (logger.isDebugEnable()) {
				msg = exceptionMsg;
			}
			response = new Response(PlatformErrorCode.SYSTEM_BUSY.getErrorCode(), msg);

			return response;
		}

		return response;
	}

	public CollectTaskService getCollectTaskService() {
		return collectTaskService;
	}

	public void setCollectTaskService(CollectTaskService collectTaskService) {
		this.collectTaskService = collectTaskService;
	}

	public ChannelSendService getChannelSendService() {
		return channelSendService;
	}

	public void setChannelSendService(ChannelSendService channelSendService) {
		this.channelSendService = channelSendService;
	}

	public void setSysAppIPSService(SysAppIPSService sysAppIPSService) {
		this.sysAppIPSService = sysAppIPSService;
	}

	public void setSysInfoCategoryAPPSService(
			SysInfoCategoryAPPSService sysInfoCategoryAPPSService) {
		this.sysInfoCategoryAPPSService = sysInfoCategoryAPPSService;
	}

	public SysInfoCategoryBanksService getSysInfoCategoryBanksService() {
		return sysInfoCategoryBanksService;
	}

	public void setSysInfoCategoryBanksService(
			SysInfoCategoryBanksService sysInfoCategoryBanksService) {
		this.sysInfoCategoryBanksService = sysInfoCategoryBanksService;
	}

	public void setSysInfoCategoryService(
			SysInfoCategoryService sysInfoCategoryService) {
		this.sysInfoCategoryService = sysInfoCategoryService;
	}

	public PayTaskService getPayTaskService() {
		return payTaskService;
	}

	public void setPayTaskService(PayTaskService payTaskService) {
		this.payTaskService = payTaskService;
	}

	public SysAppService getSysAppService() {
		return sysAppService;
	}

	public void setSysAppService(SysAppService sysAppService) {
		this.sysAppService = sysAppService;
	}

	public SysAppIPSService getSysAppIPSService() {
		return sysAppIPSService;
	}

	public SysInfoCategoryAPPSService getSysInfoCategoryAPPSService() {
		return sysInfoCategoryAPPSService;
	}

	public SysInfoCategoryService getSysInfoCategoryService() {
		return sysInfoCategoryService;
	}
	
	public TppWhiteListService getTppWhiteListService() {
		return tppWhiteListService;
	}
	
	public void setTppWhiteListService(TppWhiteListService tppWhiteListService) {
		this.tppWhiteListService = tppWhiteListService;
	}

	public SignMessageInfoService getSignMessageInfoService() {
		return signMessageInfoService;
	}

	public void setSignMessageInfoService(SignMessageInfoService signMessageInfoService) {
		this.signMessageInfoService = signMessageInfoService;
	}

	public SignInfoService getSignInfoService() {
		return signInfoService;
	}

	public void setSignInfoService(SignInfoService signInfoService) {
		this.signInfoService = signInfoService;
	}

	public RequestDao getRequestDao() {
		return requestDao;
	}

	public void setRequestDao(RequestDao requestDao) {
		this.requestDao = requestDao;
	}
}
