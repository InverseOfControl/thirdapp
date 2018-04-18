package com.zdmoney.manager.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdmoney.manager.enumset.BankCardTypeEnum;
import com.zdmoney.manager.enumset.BizTypeEnum;
import com.zdmoney.manager.enumset.GenderEnum;
import com.zdmoney.manager.enumset.IdTypeEnum;
import com.zdmoney.manager.enumset.IsSyncEnum;
import com.zdmoney.manager.enumset.OperationRequestStatusEnum;
import com.zdmoney.manager.models.TPPOperationRequest;
import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.service.TppOperationRequestService;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.Page;
import com.zdmoney.manager.utils.StringUtil;

/** 
*
* @author wyj
* @version 2015年7月3日 下午1:59:18 
*/
@Controller
@RequestMapping(value = "/trade/operation")
public class TppOperationRequestController {
	private final Logger log = Logger.getLogger(TSymPermissionController.class);
	@Autowired
	private TppOperationRequestService requestService; 
	@Autowired
	private Page<TPPOperationRequest> page;
	@RequestMapping(value = "/requestList")
	public String list(HttpServletRequest request, ModelMap modelMap){
		try { 
			return "/trade/operation/requestList";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	@RequestMapping(value = "/requestListData")
	@ResponseBody
	public  String  listData(HttpServletRequest request, ModelMap modelMap){		
		try {
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			
			this.datagridParam(request, params);
			this.queryParam(request, params);
			
			// 2.遍历增加及转换自定义内容
			List<Map> rows =requestService.selectOperationRequestList(params); 
			int count = requestService.selectOperationRequestListCount(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				d.put("STATUS", d.get("STATUS") == null ? "" : OperationRequestStatusEnum.getEnumDesc(d.get("STATUS").toString()));
				d.put("IS_SYNC", d.get("IS_SYNC") == null ? "" : IsSyncEnum.getEnumDesc(d.get("IS_SYNC").toString()));
				d.put("BIZ_TYPE_NO", d.get("BIZ_TYPE_NO") == null ? "" : BizTypeEnum.getEnumDesc(d.get("BIZ_TYPE_NO").toString()));
				d.put("GENDER", d.get("GENDER") == null ? "" : GenderEnum.getEnumDesc(d.get("GENDER").toString()));
				d.put("BANK_CARD_TYPE", d.get("BANK_CARD_TYPE") == null ? "" : BankCardTypeEnum.getEnumDesc(d.get("BANK_CARD_TYPE").toString()));
				d.put("ID_TYPE", d.get("ID_TYPE") == null ? "" : IdTypeEnum.getEnumDesc(d.get("ID_TYPE").toString()));
				d.put("APP_REQUEST","<a href='javascript:return void(0);' onClick=\"appRequest('" + d.get("TRANSFER_FLOW") + "');return false;\"><i class='icon-search'></i></a>");
				d.put("DETAIL","<a href='javascript:return void(0);' onClick=\"viewInfoDetail('" + d.get("REQ_ID") + "');return false;\"><i class='icon-search'></i></a>");
				d.put("MOBILE", d.get("MOBILE") == null ? "" : StringUtil.hiddenPhoneNumber(d.get("MOBILE").toString()));
				d.put("RESERVE_MOBILE", d.get("RESERVE_MOBILE") == null ? "" : StringUtil.hiddenPhoneNumber(d.get("RESERVE_MOBILE").toString()));
				d.put("BANK_CARD_NO", d.get("BANK_CARD_NO") == null ? "" : StringUtil.hiddenPrivateInfomation(d.get("BANK_CARD_NO").toString()));
				d.put("ID_NO", d.get("ID_NO") == null ? "" : StringUtil.hiddenPrivateInfomation(d.get("ID_NO").toString()));
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
		} else {
			params.put("orderStr", " order by " + " REQ_ID " + " " + " DESC " + " ");
		}
		params.put("rowBegin", rowBegin);
		params.put("rowEnd", rowEnd);
	}
	
	/**
	 * 查询参数
	 * @param request
	 * @param params
	 */
	private void queryParam(HttpServletRequest request, Map<String,Object> params) {
		String beginTime =request.getParameter("beginDate");
		String endTime =request.getParameter("endDate");
		if(StringUtils.isNotBlank(beginTime)){
			beginTime=beginTime + " 00:00:00";
		}
		if(StringUtils.isNotBlank(endTime)){
			endTime=endTime  +" 23:59:59";
		}
		params.put("beginTime",  beginTime);
		params.put("endTime",  endTime);
		params.put("customerInfo", request.getParameter("customer_info"));
		params.put("bizSysNo", request.getParameter("bizSysNo"));
		params.put("bizType", request.getParameter("bizType"));
		params.put("status", request.getParameter("status"));
		params.put("transferFlow", request.getParameter("transferFlow"));
		params.put("bizFlow", request.getParameter("bizFlow"));
		params.put("infoCategory", request.getParameter("infoCategory"));
		
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
	@RequestMapping(value = "/requestInfo")
	public String detail(HttpServletRequest request, ModelMap modelMap){
		String id =request.getParameter("id");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		List<Map> rows = requestService.selectOperationRequestListById(params);
		Map<String, Object> resultMap = null;
		if (rows != null && rows.size() > 0) {
			resultMap = rows.get(0);
		}
		for (String key : resultMap.keySet()) {
			modelMap.put(key, resultMap.get(key));
		}
		modelMap.put("STATUS", modelMap.get("STATUS") == null ? "" : OperationRequestStatusEnum.getEnumDesc(modelMap.get("STATUS").toString()));
		modelMap.put("IS_SYNC", modelMap.get("IS_SYNC") == null ? "" : IsSyncEnum.getEnumDesc(modelMap.get("IS_SYNC").toString()));
		modelMap.put("BIZ_TYPE_NO", modelMap.get("BIZ_TYPE_NO") == null ? "" : BizTypeEnum.getEnumDesc(modelMap.get("BIZ_TYPE_NO").toString()));
		modelMap.put("GENDER", modelMap.get("GENDER") == null ? "" : GenderEnum.getEnumDesc(modelMap.get("GENDER").toString()));
		modelMap.put("BANK_CARD_TYPE", modelMap.get("BANK_CARD_TYPE") == null ? "" : BankCardTypeEnum.getEnumDesc(modelMap.get("BANK_CARD_TYPE").toString()));
		modelMap.put("ID_TYPE", modelMap.get("ID_TYPE") == null ? "" : IdTypeEnum.getEnumDesc(modelMap.get("ID_TYPE").toString()));
		modelMap.put("RESP_INFO", modelMap.get("RESP_INFO") == null ? "" : StringUtil.htmlEscape(modelMap.get("RESP_INFO").toString()));
		modelMap.put("MOBILE", modelMap.get("MOBILE") == null ? "" : StringUtil.hiddenPhoneNumber(modelMap.get("MOBILE").toString()));
		modelMap.put("RESERVE_MOBILE", modelMap.get("RESERVE_MOBILE") == null ? "" : StringUtil.hiddenPhoneNumber(modelMap.get("RESERVE_MOBILE").toString()));
		modelMap.put("BANK_CARD_NO", modelMap.get("BANK_CARD_NO") == null ? "" : StringUtil.hiddenPrivateInfomation(modelMap.get("BANK_CARD_NO").toString()));
		modelMap.put("ID_NO", modelMap.get("ID_NO") == null ? "" : StringUtil.hiddenPrivateInfomation(modelMap.get("ID_NO").toString()));
		return "/trade/operation/requestInfo";
	}
	 
	 
}
