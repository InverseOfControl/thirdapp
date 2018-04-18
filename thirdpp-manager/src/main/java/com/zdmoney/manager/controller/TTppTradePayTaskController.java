package com.zdmoney.manager.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
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
import com.zdmoney.manager.enumset.CurrencyEnum;
import com.zdmoney.manager.enumset.IdTypeEnum;
import com.zdmoney.manager.enumset.IsSeparateEnum;
import com.zdmoney.manager.enumset.NeedPushEnum;
import com.zdmoney.manager.enumset.NotifyStatusEnum;
import com.zdmoney.manager.enumset.OPModeEnum;
import com.zdmoney.manager.enumset.PayReceiverTypeEnum;
import com.zdmoney.manager.enumset.PriorityEnum;
import com.zdmoney.manager.enumset.SendStatusEnum;
import com.zdmoney.manager.enumset.TradeStatusEnum;
import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.models.TppTradeTPayTask;
import com.zdmoney.manager.service.TTppTradeNotifyService;
import com.zdmoney.manager.service.TTppTradePayTaskService;
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
public class TTppTradePayTaskController {
private final Logger log = Logger.getLogger(TTppTradePayTaskController.class);
	
	@Autowired
	private TTppTradePayTaskService taskService;
	
	@Autowired
	private TTppTradeNotifyService notifyService;
	
	@Autowired
	private Page<TppTradeTPayTask> page;
	
	@RequestMapping(value = "/taskList")
	public String list(HttpServletRequest request, ModelMap modelMap){
		try{
			//权限验证
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/trade/pay/taskList")){
				return "/errorpage/permissionError";
			}
			return "/trade/pay/taskList";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	
	
	@RequestMapping(value = "/listData/{reqId}")
	@ResponseBody
	public String listData(@PathVariable("reqId") String reqId, HttpServletRequest request, ModelMap modelMap){
		try {
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			if (StringUtils.isBlank(reqId) || "null".equals(reqId)) {
				reqId = null;
			}
			params.put("SEARCH_REQID", reqId);
			this.datagridParam(request, params);
			this.queryParam(request, params);
			// 2.遍历增加及转换自定义内容
			List<Map> rows = taskService.select_tppTradeTPayTaskList(params);
			int count = taskService.select_tppTradeTPayTaskList_count(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				d.put("DETAIL","<a href='javascript:return void(0);' onClick=\"viewTaskDetail('"+ d.get("ID")+ "');return false;\"><i class='icon-search'></i></a>");
				d.put("DETAIL_TASK","<a href='javascript:return void(0);' onClick=\"viewTaskInfo('"+ d.get("ID")+ "');return false;\"><i class='icon-search'></i></a>");
				d.put("DETAIL_NOTIFY","<a href='javascript:return void(0);' onClick=\"viewNotify('"+ d.get("ID")+ "');return false;\"><i class='icon-search'></i></a>");
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
			return json;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
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
	 * @throws ParseException 
	 */
	private void queryParam(HttpServletRequest request, Map<String,Object> params) throws ParseException {
		//查询数据只能是7天内
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String beginDateStr = request.getParameter("search_begin_date");
		String endDateStr = request.getParameter("search_end_date");
		if (StringUtils.isNotBlank(beginDateStr)) {
			beginDateStr = beginDateStr + " 00:00:00";
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			endDateStr = endDateStr + " 23:59:59";
		}
		params.put("SEARCH_BEGIN_DATE", beginDateStr);
		params.put("SEARCH_END_DATE", endDateStr);
		params.put("SEARCH_BIZ_SYS_NO", request.getParameter("search_biz_sys_no"));
		params.put("SEARCH_PAY_SYS_NO", request.getParameter("search_pay_sys_no"));
		params.put("SEARCH_SEND_STATUS", request.getParameter("search_send_status"));
		params.put("SEARCH_TRADE_STATUS", request.getParameter("search_trade_status"));
		params.put("SEARCH_BIZ_FLOW", request.getParameter("search_biz_flow"));
		params.put("SEARCH_BIZ_TYPE", request.getParameter("search_biz_type"));
		params.put("SEARCH_INFO_CATEGORY", request.getParameter("search_info_category"));
		params.put("SEARCH_RECEIVER_INFO", request.getParameter("search_receiver_info"));
		params.put("SEARCH_RECEIVER_TYPE", request.getParameter("search_receiver_type"));
		params.put("SEARCH_BANK_CODE", request.getParameter("search_bank_code"));
		//设置系统查看权限
		TSysUser user = (TSysUser) request.getSession().getAttribute("user");
		List<String> appIdsList = user.getAppIds();
		if (appIdsList == null || appIdsList.size() < 1) {
			appIdsList = new ArrayList<String>();
			appIdsList.add("");
			//appIdsList.add("NO BIZ SYS");
		}
		params.put("APPIDS", appIdsList);
	}
	
	/**
	 * 页面跳转
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/detailTask")
	public String taskDetail(HttpServletRequest request, ModelMap modelMap){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			String taskId =request.getParameter("taskId");
			if (StringUtils.isBlank(taskId)) {
				return null;
			}
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("SEARCH_ID", taskId);
			this.datagridParam(request, params);
			// 2、设置返回结果
			List<Map> rows = taskService.select_tppTradeTPayTaskList(params);
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
			modelMap.put("status", modelMap.get("status") == null ? "" : SendStatusEnum.getEnumDesc(modelMap.get("status").toString()));
			modelMap.put("is_separate", modelMap.get("is_separate") == null ? "" : IsSeparateEnum.getEnumDesc(modelMap.get("is_separate").toString()));
			modelMap.put("biz_type", modelMap.get("biz_type") == null ? "" : BizTypeEnum.getEnumDesc(modelMap.get("biz_type").toString()));
			modelMap.put("is_need_push", modelMap.get("is_need_push") == null ? "" : NeedPushEnum.getEnumDesc(modelMap.get("is_need_push").toString()));
			modelMap.put("trade_status", modelMap.get("trade_status") == null ? "" : TradeStatusEnum.getEnumDesc(modelMap.get("trade_status").toString()));
			modelMap.put("amount" , modelMap.get( "amount" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)modelMap.get( "amount" )) );
			modelMap.put("fee" , modelMap.get( "fee" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)modelMap.get( "FEE" )) );
			modelMap.put("trade_success_amount" , modelMap.get( "trade_success_amount" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)modelMap.get( "trade_success_amount" )) );
			modelMap.put("receiver_bank_card_no" , modelMap.get( "receiver_bank_card_no" )==null ? "" : StringUtil.hiddenPrivateInfomation(modelMap.get( "receiver_bank_card_no" ).toString()));
			modelMap.put("receiver_id" , modelMap.get( "receiver_id" )==null ? "" : StringUtil.hiddenPrivateInfomation(modelMap.get( "receiver_id" ).toString()));
			modelMap.put("receiver_type" , modelMap.get( "receiver_type" )==null ? "" : PayReceiverTypeEnum.getEnumDesc(modelMap.get( "receiver_type" ).toString()));

			return "/trade/pay/detailTask";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	@RequestMapping(value = "/taskInfoList")
	public String taskInfoList(HttpServletRequest request, ModelMap modelMap) {
		String taskId = request.getParameter("taskId");
		modelMap.put("taskId", taskId);
		return "/trade/pay/taskInfoList";
	}
	
	/**
	 * 页面跳转
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/notifyList")
	public String notifyList(HttpServletRequest request, ModelMap modelMap){
		try{
			modelMap.put("taskId", request.getParameter("taskId"));
			return "/trade/pay/notifyList";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	@RequestMapping(value = "/notifyDetailPage")
	public String notifyDetailPage(HttpServletRequest request, ModelMap modelMap){
		String notifyId= request.getParameter("notifyId");
		
		List<Map> rows = notifyService.select_tppTradeTNotifyById(notifyId);
		if (rows != null && rows.size() > 0) {
			Map d = rows.get(0);
			d.put("BIZ_TYPE", d.get("BIZ_TYPE") == null ? "" : BizTypeEnum.getEnumDesc(d.get("BIZ_TYPE").toString()));
			d.put("NOTIFY_STATUS", d.get("NOTIFY_STATUS") == null ? "" : NotifyStatusEnum.getEnumDesc(d.get("NOTIFY_STATUS").toString()));
			d.put("TRADE_STATUS", d.get("TRADE_STATUS") == null ? "" : TradeStatusEnum.getEnumDesc(d.get("TRADE_STATUS").toString()));
			d.put("PRIORITY", d.get("PRIORITY") == null ? "" : PriorityEnum.getEnumDesc(d.get("PRIORITY").toString()));
			d.put("OP_MODE", d.get("OP_MODE") == null ? "" : OPModeEnum.getEnumDesc(d.get("OP_MODE").toString()));
			modelMap.put("NOTIFY", d);
		}
		return "/trade/pay/detailNotify";
	}
	
	@RequestMapping(value = "/summaryData/{reqId}")
	@ResponseBody
	public String summaryData(@PathVariable("reqId") String reqId, HttpServletRequest request, ModelMap modelMap){
		try {
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			if (StringUtils.isBlank(reqId) || "null".equals(reqId)) {
				reqId = null;
			}
			params.put("SEARCH_REQID", reqId);
			this.queryParam(request, params);
			List<Map> rows = taskService.select_summary(params);
			Integer successCount = taskService.select_success_count(params);
			Map resultMap = null;
			if (rows != null && rows.size() > 0) {
				resultMap = rows.get(0);
				JSONObject jsob = new JSONObject();
				jsob.put("totalSum", resultMap.get("TOTALSUM") == null ? "0" : String.valueOf(resultMap.get("TOTALSUM")));
				jsob.put("totalAmount", resultMap.get("TOTALAMOUNT") == null ? "0.00" : String.valueOf(resultMap.get("TOTALAMOUNT")));
				jsob.put("successAmount", resultMap.get("SUCCESSAMOUNT") == null ? "0.00" : String.valueOf(resultMap.get("SUCCESSAMOUNT")));
				jsob.put("successCount", successCount == null ? "0" : String.valueOf(successCount));
				return jsob.toString();
			} else {
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/exportPayTaskExcel")
	public ModelAndView exportPayTaskExcel(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws IOException{
		String[] headers = new String[]{"请求ID","支付渠道","业务系统客户编号","证大客户编号","业务系统","付款方账户编号","付款方姓名","收款方姓名","收款方银行卡号","收款方银行卡类型","收款方证件类型","收款方证件号","收款方银行名称","币种","金额","手续费","业务流水号","优先级","发送状态","创建人","创建时间","更新时间","是否拆单","拆单数","发送次数","收款方账户编号","业务类型","是否需要推送","交易结果状态","交易结果描述","交易成功金额","信息类别","是否拆单"};
		String[] fieldNames = new String[]{"REQ_ID","PAY_SYS_NAME","BIZ_SYS_ACCOUNT_NO","ZENGDAI_ACCOUNT_NO","APP_NAME","PAY_ACCOUNT_NO","PAYER_ACCOUNT_NAME","RECEIVER_NAME","RECEIVER_BANK_CARD_NO","RECEIVER_BANK_CARD_TYPE","RECEIVER_ID_TYPE","RECEIVER_ID","RECEIVER_BANK_NAME","CURRENCY","AMOUNT","FEE","BIZ_FLOW","PRIORITY","STATUS","CREATER","CREATE_TIME","UPDATE_TIME","IS_SEPARATE","SEPARATE_COUNT","SEND_NUM","RECEIVER_ACCOUNT_NO","BIZ_TYPE","IS_NEED_PUSH","TRADE_STATUS","TRADE_RESULT_INFO","TRADE_SUCCESS_AMOUNT","INFO_CATEGORY_NAME","IS_SEPARATE"};
		SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,Object> params = new HashMap<String,Object>();
		try {
			this.queryParamForExport(request, params);
			List<Map> rows = taskService.select_tppTradeTPayTaskList(params);
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
				d.put("CREATE_TIME", d.get("CREATE_TIME") == null ? "" : sdf.format(d.get("CREATE_TIME")));
				d.put("UPDATE_TIME", d.get("UPDATE_TIME") == null ? "" : sdf.format(d.get("UPDATE_TIME")));
			}
			String title = "sheet1";
			String fileName="代扣任务" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls";
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
	 * 查询参数
	 * @param request
	 * @param params
	 * @throws ParseException 
	 */
	private void queryParamForExport(HttpServletRequest request, Map<String,Object> params) throws ParseException {
		int pageNumber = 1;
		int pageSize = 20000;
		int rowBegin = (pageNumber-1) * pageSize + 1;
		int rowEnd = (pageNumber-1) * pageSize + pageSize;
		params.put("orderStr", " order by " + " CREATE_TIME " + " " + " DESC " + " ");
		params.put("rowBegin", rowBegin);
		params.put("rowEnd", rowEnd);
		//查询数据只能是7天内
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String beginDateStr = request.getParameter("export_begin_date");
		String endDateStr = request.getParameter("export_end_date");
		if (StringUtils.isNotBlank(beginDateStr)) {
			beginDateStr = beginDateStr + " 00:00:00";
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			endDateStr = endDateStr + " 23:59:59";
		}
		params.put("SEARCH_BEGIN_DATE", beginDateStr);
		params.put("SEARCH_END_DATE", endDateStr);
		params.put("SEARCH_BIZ_SYS_NO", request.getParameter("export_biz_sys_no"));
		params.put("SEARCH_PAY_SYS_NO", request.getParameter("export_pay_sys_no"));
		params.put("SEARCH_SEND_STATUS", request.getParameter("export_send_status"));
		params.put("SEARCH_TRADE_STATUS", request.getParameter("export_trade_status"));
		params.put("SEARCH_BIZ_FLOW", request.getParameter("export_biz_flow"));
		params.put("SEARCH_BIZ_TYPE", request.getParameter("export_biz_type"));
		params.put("SEARCH_INFO_CATEGORY", request.getParameter("export_info_category"));
		params.put("SEARCH_RECEIVER_INFO", request.getParameter("SEARCH_RECEIVER_INFO"));
		//设置系统查看权限
		TSysUser user = (TSysUser) request.getSession().getAttribute("user");
		List<String> appIdsList = user.getAppIds();
		if (appIdsList == null || appIdsList.size() < 1) {
			appIdsList = new ArrayList<String>();
			appIdsList.add("NO BIZ SYS");
		}
		params.put("APPIDS", appIdsList);
	}
}
