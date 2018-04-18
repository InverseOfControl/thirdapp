package com.zdmoney.manager.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zdmoney.manager.enumset.BankCardTypeEnum;
import com.zdmoney.manager.enumset.BizTypeEnum;
import com.zdmoney.manager.enumset.ChannelRequestStatusEnum;
import com.zdmoney.manager.enumset.CurrencyEnum;
import com.zdmoney.manager.enumset.IdTypeEnum;
import com.zdmoney.manager.enumset.IsSeparateEnum;
import com.zdmoney.manager.enumset.MessageTypeEnum;
import com.zdmoney.manager.enumset.NeedPushEnum;
import com.zdmoney.manager.enumset.NotifyMergeStatusEnum;
import com.zdmoney.manager.enumset.NotifyQueryStatusEnum;
import com.zdmoney.manager.enumset.PayReceiverTypeEnum;
import com.zdmoney.manager.enumset.PriorityEnum;
import com.zdmoney.manager.enumset.SendStatusEnum;
import com.zdmoney.manager.enumset.TradeStatusEnum;
import com.zdmoney.manager.enumset.TradeWaitingStatusEnum;
import com.zdmoney.manager.exception.TppManagerException;
import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.models.TppTradeTPayInfo;
import com.zdmoney.manager.models.TppTradeTWaitingMerge;
import com.zdmoney.manager.models.TppTradeTWaitingQuery;
import com.zdmoney.manager.service.TTppChannelMessageService;
import com.zdmoney.manager.service.TTppChannelRequestService;
import com.zdmoney.manager.service.TTppChannelTradeResultService;
import com.zdmoney.manager.service.TTppTradePayInfoService;
import com.zdmoney.manager.service.TTppTradeWaitingMergeService;
import com.zdmoney.manager.service.TTppTradeWaitingQueryService;
import com.zdmoney.manager.utils.BigDecimalFormatUtil;
import com.zdmoney.manager.utils.ExportExcel;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.Page;
import com.zdmoney.manager.utils.PermissionUtil;
import com.zdmoney.manager.utils.StringUtil;

/** 
*
* @author 
* @version 
*/
@Controller
@RequestMapping(value = "/trade/pay")
public class TTppTradePayInfoController {
private final Logger log = Logger.getLogger(TTppTradePayInfoController.class);
	
	@Autowired
	private TTppTradePayInfoService infoService;
	@Autowired
	private TTppChannelRequestService channelRequestService;
	
	@Autowired
	private TTppChannelMessageService channelMessageService;
	
	@Autowired
	private TTppChannelTradeResultService channelTradeResultService;
	
	@Autowired
	private TTppTradeWaitingQueryService tradeWaitingQueryService;
	
	@Autowired
	private TTppTradeWaitingMergeService tradeWaitingMergeService;
	
	@Autowired
	private Page<TppTradeTPayInfo> page;
	
	@RequestMapping(value = "/infoList")
	public String list(HttpServletRequest request, ModelMap modelMap){
		try{
			//权限验证
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/trade/collect/infoList")){
				return "/errorpage/permissionError";
			}
			return "/trade/pay/infoList";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	@RequestMapping(value = "/infoListData/{taskId}")
	@ResponseBody
	public String listData(@PathVariable("taskId") String taskId, HttpServletRequest request, ModelMap modelMap){
		
		try {
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			if (StringUtils.isBlank(taskId) || "null".equals(taskId)) {
				taskId = null;
			}
			params.put("taskId", taskId);
			this.datagridParam(request, params);
			this.queryParam(request, params);
			// 2.遍历增加及转换自定义内容
			List<Map> rows = infoService.select_tppTradeTPayInfoList(params);
			int count = infoService.select_tppTradeTPayInfoList_count(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				d.put("DETAIL","<a href='javascript:return void(0);' onClick=\"viewInfoDetail('" + d.get("ID") + "');return false;\"><i class='icon-search'></i></a>");
				d.put("NOTIFY_QUERY","<a href='javascript:return void(0);' onClick=\"notifyQuery('" + d.get("TRADE_FLOW") + "');return false;\"><i class='icon-assigned'></i></a>&nbsp;<a href='javascript:return void(0);' onClick=\"notifyQueryList('" + d.get("TRADE_FLOW") + "');return false;\"><i class='icon-search'></i></a>");
				d.put("NOTIFY_MERGE","<a href='javascript:return void(0);' onClick=\"notifyMerge('" + d.get("TRADE_FLOW") + "');return false;\"><i class='icon-assigned'></i></a>&nbsp;<a href='javascript:return void(0);' onClick=\"notifyMergeList('" + d.get("TRADE_FLOW") + "');return false;\"><i class='icon-search'></i></a>");
				d.put("APP_REQUEST","<a href='javascript:return void(0);' onClick=\"appRequest('" + d.get("TRADE_FLOW") + "');return false;\"><i class='icon-search'></i></a>");
				
				d.put("RECEIVER_BANK_CARD_TYPE", d.get("RECEIVER_BANK_CARD_TYPE") == null ? "" : BankCardTypeEnum.getEnumDesc(d.get("RECEIVER_BANK_CARD_TYPE").toString()));
				d.put("RECEIVER_ID_TYPE", d.get("RECEIVER_ID_TYPE") == null ? "" : IdTypeEnum.getEnumDesc(d.get("RECEIVER_ID_TYPE").toString()));
				d.put("CURRENCY", d.get("CURRENCY") == null ? "" : CurrencyEnum.getEnumDesc(d.get("CURRENCY").toString()));
				d.put("PRIORITY", d.get("PRIORITY") == null ? "" : PriorityEnum.getEnumDesc(d.get("PRIORITY").toString()));
				d.put("IS_SEPARATE", d.get("IS_SEPARATE") == null ? "" : IsSeparateEnum.getEnumDesc(d.get("IS_SEPARATE").toString()));
				d.put("BIZ_TYPE", d.get("BIZ_TYPE") == null ? "" : BizTypeEnum.getEnumDesc(d.get("BIZ_TYPE").toString()));
				d.put("IS_NEED_PUSH", d.get("IS_NEED_PUSH") == null ? "" : NeedPushEnum.getEnumDesc(d.get("IS_NEED_PUSH").toString()));
				d.put("STATUS", d.get("STATUS") == null ? "" : TradeStatusEnum.getEnumDesc(d.get("STATUS").toString()));
				d.put("AMOUNT" , d.get( "AMOUNT" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get( "AMOUNT" )) );
				d.put("FEE" , d.get( "FEE" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get( "FEE" )) );
				d.put("TRADE_SUCCESS_AMOUNT" , d.get( "TRADE_SUCCESS_AMOUNT" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get( "TRADE_SUCCESS_AMOUNT" )) );
				d.put("NOTIFY_QUERY_STATUS", d.get("NOTIFY_QUERY_STATUS") == null ? "" : NotifyQueryStatusEnum.getEnumDesc(d.get("NOTIFY_QUERY_STATUS").toString()));
				d.put("NOTIFY_MERGE_STATUS", d.get("NOTIFY_MERGE_STATUS") == null ? "" : NotifyMergeStatusEnum.getEnumDesc(d.get("NOTIFY_MERGE_STATUS").toString()));
				d.put("RECEIVER_TYPE", d.get("RECEIVER_TYPE") == null ? "" : PayReceiverTypeEnum.getEnumDesc(d.get("RECEIVER_TYPE").toString()));
				d.put("RECEIVER_BANK_CARD_NO", d.get("RECEIVER_BANK_CARD_NO") == null ? "" : StringUtil.hiddenPrivateInfomation(d.get("RECEIVER_BANK_CARD_NO").toString()));
				d.put("RECEIVER_ID",  d.get("RECEIVER_ID") == null ? "" : StringUtil.hiddenPrivateInfomation(d.get("RECEIVER_ID").toString()));
			}

			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);

			JSONArray json_rows = JSONArray.fromObject(rows, config);
			String json = "{\"total\":\""
					+ count
					+ "\",\"rows\":" + json_rows.toString() + "}";
			modelMap.put("data", json);
			return json;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 翻页 排序参数
	 * @param request
	 * @param params
	 */
	private void datagridParam(HttpServletRequest request, Map<String,Object> params) throws Exception {
		int pageNumber = StringUtils.isBlank(request.getParameter("page")) ? 1: Integer.parseInt(request.getParameter("page"));
		int pageSize = StringUtils.isBlank(request.getParameter("rows")) ? 10: Integer.parseInt(request.getParameter("rows"));
		
		int rowBegin = (pageNumber-1) * pageSize + 1;
		int rowEnd = (pageNumber-1) * pageSize + pageSize;
		String sortName = request.getParameter("sort");
		String sortOrder = StringUtils.isBlank(request.getParameter("order")) ? "asc" : request.getParameter("order");
		if (StringUtils.isNotBlank(sortName) && StringUtils.isNotBlank(sortOrder)) {
			params.put("orderStr", " order by " + sortName + " " + sortOrder + " ");
		}
		params.put("rowBegin", rowBegin);
		params.put("rowEnd", rowEnd);
	}
	
	/**
	 * 查询参数
	 * @param request
	 * @param params
	 */
	private void queryParam(HttpServletRequest request, Map<String,Object> params) throws Exception {
		//查询数据只能是7天内
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String searchDateType = request.getParameter("search_date_type");
		String beginDateStr = request.getParameter("search_begin_date");
		String endDateStr = request.getParameter("search_end_date");
		if (StringUtils.isNotBlank(beginDateStr)) {
			beginDateStr = beginDateStr + " 00:00:00";
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			endDateStr = endDateStr + " 23:59:59";
		}
		if (StringUtils.isBlank(searchDateType)) {
			searchDateType = "CREATE_TIME";
		}
		params.put("SEARCH_BEGIN_DATE", beginDateStr);
		params.put("SEARCH_END_DATE", endDateStr);
		
		params.put("SEARCH_PAY_SYS_NO", request.getParameter("search_pay_sys_no"));
		params.put("SEARCH_BIZ_SYS_NO", request.getParameter("search_biz_sys_no"));
		params.put("SEARCH_FLOW", request.getParameter("search_flow"));
		params.put("SEARCH_TRADE_STATUS", request.getParameter("search_trade_status"));
		params.put("SEARCH_BIZ_TYPE", request.getParameter("search_biz_type"));
		params.put("SEARCH_RECEIVER_INFO", request.getParameter("search_receiver_info"));
		params.put("SEARCH_NOTIFY_QUERY_STATUS", request.getParameter("search_notify_query_status"));
		params.put("SEARCH_NOTIFY_MERGE_STATUS", request.getParameter("search_notify_merge_status"));
		params.put("SEARCH_DATE_TYPE", searchDateType);
		params.put("SEARCH_SETTLE_DATE", request.getParameter("search_settle_date"));
		params.put("SEARCH_RECEIVER_TYPE", request.getParameter("search_receiver_type"));
		params.put("SEARCH_BANK_CODE", request.getParameter("search_bank_code"));
		
		
		//设置系统查看权限
		TSysUser user = (TSysUser) request.getSession().getAttribute("user");
		List<String> appIdsList = user.getAppIds();
		if (appIdsList == null || appIdsList.size() < 1) {
			appIdsList = new ArrayList<String>();
			appIdsList.add("NO BIZ SYS");
		}
		params.put("APPIDS", appIdsList);
	}
	
	/**
	 * 页面跳转
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/detailInfo")
	public String detailInfo(HttpServletRequest request, ModelMap modelMap){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			String infoId =request.getParameter("infoId");
			if (StringUtils.isBlank(infoId)) {
				return null;
			}
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("SEARCH_ID", infoId);
			this.datagridParam(request, params);
			// 2、设置返回结果
			List<Map> rows = infoService.select_tppTradeTPayInfoList(params);
			Map<String, Object> resultMap = null;
			if (rows != null && rows.size() > 0) {
				resultMap = rows.get(0);
			}
			for (String key : resultMap.keySet()) {
				modelMap.put(key.toLowerCase(), resultMap.get(key));
			}
			// 3、枚举转换
			modelMap.put("receiver_bank_card_type", modelMap.get("receiver_bank_card_type") == null ? "" : BankCardTypeEnum.getEnumDesc(modelMap.get("receiver_bank_card_type").toString()));
			modelMap.put("receiver_id_type", modelMap.get("receiver_id_type") == null ? "" : IdTypeEnum.getEnumDesc(modelMap.get("receiver_id_type").toString()));
			modelMap.put("currency", modelMap.get("currency") == null ? "" : CurrencyEnum.getEnumDesc(modelMap.get("currency").toString()));
			modelMap.put("priority", modelMap.get("priority") == null ? "" : PriorityEnum.getEnumDesc(modelMap.get("priority").toString()));
			modelMap.put("status", modelMap.get("status") == null ? "" : TradeStatusEnum.getEnumDesc(modelMap.get("status").toString()));
			modelMap.put("is_separate", modelMap.get("is_separate") == null ? "" : IsSeparateEnum.getEnumDesc(modelMap.get("is_separate").toString()));
			modelMap.put("biz_type", modelMap.get("biz_type") == null ? "" : BizTypeEnum.getEnumDesc(modelMap.get("biz_type").toString()));
			modelMap.put("is_need_push", modelMap.get("is_need_push") == null ? "" : NeedPushEnum.getEnumDesc(modelMap.get("is_need_push").toString()));
			modelMap.put("trade_status", modelMap.get("trade_status") == null ? "" : TradeStatusEnum.getEnumDesc(modelMap.get("trade_status").toString()));
			modelMap.put("amount" , modelMap.get( "amount" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)modelMap.get( "amount" )) );
			modelMap.put("fee" , modelMap.get( "fee" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)modelMap.get( "fee" )) );
			modelMap.put("trade_success_amount" , modelMap.get( "trade_success_amount" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)modelMap.get( "trade_success_amount" )) );
			modelMap.put("notify_query_status", modelMap.get("notify_query_status") == null ? "" : NotifyQueryStatusEnum.getEnumDesc(modelMap.get("notify_query_status").toString()));
			modelMap.put("notify_merge_status", modelMap.get("notify_merge_status") == null ? "" : NotifyMergeStatusEnum.getEnumDesc(modelMap.get("notify_merge_status").toString()));
			modelMap.put("receiver_bank_card_no" , modelMap.get( "receiver_bank_card_no" )==null ? "" : StringUtil.hiddenPrivateInfomation(modelMap.get( "receiver_bank_card_no" ).toString()));
			modelMap.put("receiver_id" , modelMap.get( "receiver_id" )==null ? "" : StringUtil.hiddenPrivateInfomation(modelMap.get( "receiver_id" ).toString()));
			modelMap.put("receiver_type" , modelMap.get( "receiver_type" )==null ? "" : PayReceiverTypeEnum.getEnumDesc(modelMap.get( "receiver_type" ).toString()));
			
			return "/trade/pay/detailInfo";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	@RequestMapping(value = "/infoSummaryData")
	@ResponseBody
	public String summaryData(HttpServletRequest request, ModelMap modelMap){
		try {
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			this.queryParam(request, params);
			List<Map> rows = infoService.select_summary(params);
			//查询成功笔数及金额
			params.put("SEARCH_STATUS", TradeStatusEnum.SUCCESS.getValue());
			List<Map> successRows = infoService.select_summary(params);
			JSONObject jsob = new JSONObject();
			if (rows != null && rows.size() > 0) {
				Map resultMap = rows.get(0);
				jsob.put("totalSum", resultMap.get("TOTALSUM") == null ? "0" : String.valueOf(resultMap.get("TOTALSUM")));
				jsob.put("totalAmount", resultMap.get("TOTALAMOUNT") == null ? "0.00" : String.valueOf(resultMap.get("TOTALAMOUNT")));
			} else {
				return null;
			}
			if (successRows != null && successRows.size() > 0) {
				Map successResultMap = successRows.get(0);
				jsob.put("successAmount", successResultMap.get("TOTALAMOUNT") == null ? "0.00" : String.valueOf(successResultMap.get("TOTALAMOUNT")));
				jsob.put("successCount", successResultMap.get("TOTALSUM") == null ? "0" : String.valueOf(successResultMap.get("TOTALSUM")));
				
			} else {
				return null;
			}
			return jsob.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage(), ex);
			return null;
		}
	}
	
	
	@RequestMapping(value = "/exportPayInfoExcel")
	public ModelAndView exportPayInfoExcel(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws IOException{
		String[] headers = new String[]{"任务ID","请求ID","支付渠道","业务系统客户编号","证大客户编号","业务系统名称","付款方账户编号","付款方姓名","收款方姓名","收款方银行卡号","收款方银行卡类型","收款方证件类型","收款方证件号","收款方银行名称","收款方银行支行行号","币种","金额","手续费","业务备注","业务流水号","支付渠道交易返回流水号","优先级","交易状态","备注","创建人","创建时间","更新时间","备用1","备用2","交易流水号","失败原因","收款方账户编号","业务类型","是否需要推送","信息类别","渠道返回状态","第三方回盘时间","通知查询状态","通知合并状态","清算日期"};
		String[] fieldNames = new String[]{"TASK_ID","REQ_ID","PAY_SYS_NAME","BIZ_SYS_ACCOUNT_NO","ZENGDAI_ACCOUNT_NO","BIZ_SYS_NAME","PAYER_ACCOUNT_NO","PAYER_ACCOUNT_NAME","RECEIVER_NAME","RECEIVER_BANK_CARD_NO","RECEIVER_BANK_CARD_TYPE","RECEIVER_ID_TYPE","RECEIVER_ID","RECEIVER_BANK_NAME","RECEIVER_SUB_BANK_CODE","CURRENCY","AMOUNT","FEE","BIZ_REMARK","BIZ_FLOW","PAY_TRANS_FLOW","PRIORITY","STATUS","REMARK","CREATER","CREATE_TIME","UPDATE_TIME","SPARE1","SPARE2","TRADE_FLOW","FAIL_REASON","RECEIVER_ACCOUNT_NO","BIZ_TYPE","IS_NEED_PUSH","INFO_CATEGORY_NAME","TRANS_REP_CODE","THIRD_RETURN_TIME","NOTIFY_QUERY_STATUS","NOTIFY_MERGE_STATUS","SETTLE_DATE"};
		SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,Object> params = new HashMap<String,Object>();
		try {
			this.queryParamForExport(request, params);
			List<Map> rows = infoService.select_tppTradeTPayInfoList(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				d.put("RECEIVER_BANK_CARD_TYPE", d.get("RECEIVER_BANK_CARD_TYPE") == null ? "" : BankCardTypeEnum.getEnumDesc(d.get("RECEIVER_BANK_CARD_TYPE").toString()));
				d.put("RECEIVER_ID_TYPE", d.get("RECEIVER_ID_TYPE") == null ? "" : IdTypeEnum.getEnumDesc(d.get("RECEIVER_ID_TYPE").toString()));
				d.put("CURRENCY", d.get("CURRENCY") == null ? "" : CurrencyEnum.getEnumDesc(d.get("CURRENCY").toString()));
				d.put("PRIORITY", d.get("PRIORITY") == null ? "" : PriorityEnum.getEnumDesc(d.get("PRIORITY").toString()));
				d.put("STATUS", d.get("STATUS") == null ? "" : SendStatusEnum.getEnumDesc(d.get("STATUS").toString()));
				d.put("IS_SEPARATE", d.get("IS_SEPARATE") == null ? "" : IsSeparateEnum.getEnumDesc(d.get("IS_SEPARATE").toString()));
				d.put("BIZ_TYPE", d.get("BIZ_TYPE") == null ? "" : BizTypeEnum.getEnumDesc(d.get("BIZ_TYPE").toString()));
				d.put("IS_NEED_PUSH", d.get("IS_NEED_PUSH") == null ? "" : NeedPushEnum.getEnumDesc(d.get("IS_NEED_PUSH").toString()));
				d.put("TRADE_STATUS", d.get("TRADE_STATUS") == null ? "" : TradeStatusEnum.getEnumDesc(d.get("TRADE_STATUS").toString()));
				d.put("AMOUNT" , d.get( "AMOUNT" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get( "AMOUNT" )) );
				d.put("FEE" , d.get( "FEE" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get( "FEE" )) );
				d.put("TRADE_SUCCESS_AMOUNT" , d.get( "TRADE_SUCCESS_AMOUNT" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get( "TRADE_SUCCESS_AMOUNT" )) );
				d.put("NOTIFY_QUERY_STATUS", d.get("NOTIFY_QUERY_STATUS") == null ? "" : NotifyQueryStatusEnum.getEnumDesc(d.get("NOTIFY_QUERY_STATUS").toString()));
				d.put("NOTIFY_MERGE_STATUS", d.get("NOTIFY_MERGE_STATUS") == null ? "" : NotifyMergeStatusEnum.getEnumDesc(d.get("NOTIFY_MERGE_STATUS").toString()));
				d.put("CREATE_TIME", d.get("CREATE_TIME") == null ? "" : sdf.format(d.get("CREATE_TIME")));
				d.put("UPDATE_TIME", d.get("UPDATE_TIME") == null ? "" : sdf.format(d.get("UPDATE_TIME")));
			}
			String title = "sheet1";
			String fileName="代付任务明细" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + ".xls";
			ModelMap model = new ModelMap();
			model.put("title", title);
			model.put("headers", headers);
			model.put("fieldNames", fieldNames);
			model.put("dataset", rows);
			model.put("fileName", fileName);
			ModelAndView mv = new ModelAndView(new ExportExcel(), model);
			JSONObject result = new JSONObject();
			result.put("msg", "导出成功");
			mv.addObject("result", result);
			return mv;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导出查询参数
	 * @param request
	 * @param params
	 */
	private void queryParamForExport(HttpServletRequest request, Map<String,Object> params) {
		int pageNumber = 1;
		int pageSize = 20000;
		int rowBegin = (pageNumber-1) * pageSize + 1;
		int rowEnd = (pageNumber-1) * pageSize + pageSize;
		params.put("orderStr", " order by " + " CREATE_TIME " + " " + " DESC " + " ");
		params.put("rowBegin", rowBegin);
		params.put("rowEnd", rowEnd);
		
		//查询数据只能是7天内
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String searchDateType = request.getParameter("export_date_type");
		String beginDateStr = request.getParameter("export_begin_date");
		String endDateStr = request.getParameter("export_end_date");
		if (StringUtils.isNotBlank(beginDateStr)) {
			beginDateStr = beginDateStr + " 00:00:00";
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			endDateStr = endDateStr + " 23:59:59";
		}
		if (StringUtils.isBlank(searchDateType)) {
			searchDateType = "CREATE_TIME";
		}
		params.put("SEARCH_BEGIN_DATE", beginDateStr);
		params.put("SEARCH_END_DATE", endDateStr);
		
		params.put("SEARCH_PAY_SYS_NO", request.getParameter("export_pay_sys_no"));
		params.put("SEARCH_BIZ_SYS_NO", request.getParameter("export_biz_sys_no"));
		params.put("SEARCH_FLOW", request.getParameter("export_flow"));
		params.put("SEARCH_TRADE_STATUS", request.getParameter("export_trade_status"));
		params.put("SEARCH_BIZ_TYPE", request.getParameter("export_biz_type"));
		params.put("SEARCH_RECEIVER_INFO", request.getParameter("export_receiver_info"));
		params.put("SEARCH_NOTIFY_QUERY_STATUS", request.getParameter("export_notify_query_status"));
		params.put("SEARCH_NOTIFY_MERGE_STATUS", request.getParameter("export_notify_merge_status"));
		params.put("SEARCH_DATE_TYPE", searchDateType);
		params.put("SEARCH_SETTLE_DATE", request.getParameter("export_settle_date"));
		//设置系统查看权限
		TSysUser user = (TSysUser) request.getSession().getAttribute("user");
		List<String> appIdsList = user.getAppIds();
		if (appIdsList == null || appIdsList.size() < 1) {
			appIdsList = new ArrayList<String>();
			appIdsList.add("NO BIZ SYS");
		}
		params.put("APPIDS", appIdsList);
	}
	
	@RequestMapping(value = "/appRequest")
	public String appRequest(HttpServletRequest request, ModelMap modelMap){
		String tradeFlow = request.getParameter("tradeFlow");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			//1、查询渠道请求信息
			HashMap params = new HashMap();
			params.put("SEARCH_TRANSFER_FLOW", tradeFlow);
			List<Map> rows = channelRequestService.select_tppChannelTRequestByTransferFlow(params);
			Map channelRequest = new HashMap();
			if (rows != null && rows.size() > 0) {
				channelRequest = rows.get(0);
				channelRequest.put("STATUS", channelRequest.get("STATUS") == null ? "" : ChannelRequestStatusEnum.getEnumDesc(channelRequest.get("STATUS").toString()));
				channelRequest.put("BIZ_TYPE", channelRequest.get("BIZ_TYPE") == null ? "" : BizTypeEnum.getEnumDesc(channelRequest.get("BIZ_TYPE").toString()));
			
			}
			//TPP通道请求流水号
			String tppRequestFlow = (String) channelRequest.get("REQ_ID");
			modelMap.put("CHANNEL_REQUEST", channelRequest);
			
			//2、查询报文信息
			params.clear();
			params.put("SEARCH_REQ_ID", tppRequestFlow);
			if (StringUtils.isNotBlank(tppRequestFlow)) {
				List<Map> messageRows = channelMessageService.select_tppChannelTMessageByReqId(params);
				for (Map d : messageRows) {
					if (MessageTypeEnum.REQUEST.getValue().equals(d.get("MSG_TYPE"))) {
						modelMap.put("requestMessage", d.get("MESSAGE"));
						modelMap.put("requestMessageDate", sdf.format(d.get("CREATE_TIME")));
					} else if (MessageTypeEnum.RESPONSE.getValue().equals(d.get("MSG_TYPE"))) {
						modelMap.put("responseMessage", d.get("MESSAGE"));
						modelMap.put("responseMessageDate", sdf.format(d.get("CREATE_TIME")));
					}
				}
			}
			
			//3、查询交易结果
			params.clear();
			params.put("SEARCH_TRADE_FLOW", tradeFlow);
			List<Map> tradeResultRows = channelTradeResultService.select_tppChannelTradeResultByTransferFolw(params);
			Map tradeResult = new HashMap();
			if (tradeResultRows != null && tradeResultRows.size() > 0) {
				tradeResult = tradeResultRows.get(0);
				tradeResult.put("STATUS", tradeResult.get("STATUS") == null ? "" : TradeStatusEnum.getEnumDesc(tradeResult.get("STATUS").toString()));
				tradeResult.put("CREATE_TIME", tradeResult.get("CREATE_TIME") == null ? "" : sdf.format(tradeResult.get("CREATE_TIME")));
				tradeResult.put("TRANS_REP_INFO", tradeResult.get("TRANS_REP_INFO") == null ? "" : StringUtil.htmlEscape((String) tradeResult.get("TRANS_REP_INFO")));
			}
			modelMap.put("TRADE_RESULT", tradeResult);
			
			return "/trade/pay/appRequest"; 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
		
	}
	
	/**
	 * 通知查询页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/tradeWaitingQueryListPage")
	public String tradeWaitingQueryListPage(HttpServletRequest request, ModelMap modelMap){
		String tradeFlow = request.getParameter("tradeFlow");
		modelMap.put("tradeFlow", tradeFlow);
		return "/trade/collect/tradeWaitingQueryList";
	}
	
	//通知查询页面
	@RequestMapping(value="/tradeWaitingQueryPage")
	public String tradeWaitingQueryPage(HttpServletRequest request, ModelMap modelMap) {
		String tradeFlow = request.getParameter("tradeFlow");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeFlow", tradeFlow);
		params.put("rowBegin", 0);
		params.put("rowEnd", 1);
		List<Map> rows = infoService.select_tppTradeTPayInfoList(params);
		Map payInfo = null;
		if (rows != null && rows.size() > 0) {
			payInfo = rows.get(0);
			payInfo.put("BIZ_TYPE", payInfo.get("BIZ_TYPE") == null ? "" : BizTypeEnum.getEnumDesc(payInfo.get("BIZ_TYPE").toString()));
		}
		modelMap.put("payInfo", payInfo);
		return "/trade/pay/tradeWaitingQuery";
	}
	//通知查询插入
	@RequestMapping(value = "/tradeWaitingQuery")
	@ResponseBody
	public String tradeWaitingQuery(HttpServletRequest request, ModelMap modelMap) {
		JSONObject result = new JSONObject();
		try {
			String tradeFlow = request.getParameter("notify_query_trade_flow");
			String appName = request.getParameter("query_module_name");
			String opMode = request.getParameter("op_mode");
			if (StringUtils.isBlank(tradeFlow)) {
				throw new TppManagerException("交易流水号为空，通知失败！");
			}
			if (StringUtils.isBlank(appName) || "请选择".equals(appName)) {
				throw new TppManagerException("查询模块名称为空，通知失败！");
			}
			if (StringUtils.isBlank(opMode)) {
				throw new TppManagerException("运营类型为空，通知失败！");
			}
			TSysUser user = (TSysUser) request.getSession().getAttribute("user");
			
			List<TppTradeTPayInfo> rows = infoService.getPayInfoByTradeFlow(tradeFlow);
			TppTradeTPayInfo payInfo = null;
			if (rows != null && rows.size() > 0) {
				payInfo = rows.get(0);
			}
			TppTradeTWaitingQuery waitingQuery = new TppTradeTWaitingQuery();
			waitingQuery.setTradeFlow(payInfo.getTradeFlow());
			waitingQuery.setBizTypeNo(payInfo.getBizType());
			waitingQuery.setBizSysNo(payInfo.getBizSysNo());
			waitingQuery.setPaySysNo(payInfo.getPaySysNo());
			waitingQuery.setInfoCategoryCode(payInfo.getInfoCategoryCode());
			waitingQuery.setQueryModuleName(appName);
			waitingQuery.setStatus(TradeWaitingStatusEnum.WAIT_PROCESS.getValue());
			waitingQuery.setCreater(user.getLoginUserName());
			waitingQuery.setPayerAccountNo(payInfo.getPayerAccountNo());
			waitingQuery.setOpMode(opMode);
			tradeWaitingQueryService.insert(waitingQuery);
		} catch (TppManagerException e) {
			log.error(e.getMessage(), e);
			result.put("flag", false);
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("flag", false);
			result.put("msg","通知查询失败");
		}
		return result.toString();
	}
	
	//通知合并页面
	@RequestMapping(value="/tradeWaitingMergePage")
	public String tradeWaitingMergePage(HttpServletRequest request, ModelMap modelMap) {
		String tradeFlow = request.getParameter("tradeFlow");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeFlow", tradeFlow);
		params.put("rowBegin", 0);
		params.put("rowEnd", 1);
		List<Map> rows = infoService.select_tppTradeTPayInfoList(params);
		Map collectInfo = null;
		if (rows != null && rows.size() > 0) {
			collectInfo = rows.get(0);
			collectInfo.put("BIZ_TYPE", collectInfo.get("BIZ_TYPE") == null ? "" : BizTypeEnum.getEnumDesc(collectInfo.get("BIZ_TYPE").toString()));
		}
		modelMap.put("collectInfo", collectInfo);
		return "/trade/collect/tradeWaitingMerge";
	}
	//通知合并插入
	@RequestMapping(value = "/tradeWaitingMerge")
	@ResponseBody
	public String tradeWaitingMerge(HttpServletRequest request, ModelMap modelMap) {
		JSONObject result = new JSONObject();
		try {
			String tradeFlow = request.getParameter("notify_merge_trade_flow");
			String appName = request.getParameter("merge_module_name");
			if (StringUtils.isBlank(tradeFlow)) {
				throw new TppManagerException("交易流水号为空，通知失败！");
			}
			if (StringUtils.isBlank(appName)  || "请选择".equals(appName)) {
				throw new TppManagerException("合单模块名称为空，通知失败！");
			}
			TSysUser user = (TSysUser) request.getSession().getAttribute("user");
			
			List<TppTradeTPayInfo> rows = infoService.getPayInfoByTradeFlow(tradeFlow);
			TppTradeTPayInfo payInfo = null;
			if (rows != null && rows.size() > 0) {
				payInfo = rows.get(0);
			}
			TppTradeTWaitingMerge waitingMerge = new TppTradeTWaitingMerge();
			waitingMerge.setTradeFlow(tradeFlow);
			waitingMerge.setBizTypeNo(payInfo.getBizType());
			waitingMerge.setStatus(TradeWaitingStatusEnum.WAIT_PROCESS.getValue());
			waitingMerge.setMergeModuleName(appName);
			waitingMerge.setCreater(user.getLoginUserName());
			tradeWaitingMergeService.insert(waitingMerge);
		} catch (TppManagerException e) {
			log.error(e.getMessage(), e);
			result.put("flag", false);
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("flag", false);
			result.put("msg","通知查询失败");
		}
		return result.toString();
	}
}
