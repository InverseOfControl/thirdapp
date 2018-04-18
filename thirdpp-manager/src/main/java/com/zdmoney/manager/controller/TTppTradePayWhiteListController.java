package com.zdmoney.manager.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.zdmoney.manager.enumset.PayWhiteListStatusEnum;
import com.zdmoney.manager.exception.TppManagerException;
import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.models.TppTradeTPayWhiteList;
import com.zdmoney.manager.service.TTppTradePayWhiteListService;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.PermissionUtil;

/** 
*
* @author 00237071
* @version 
*/
@Controller
@RequestMapping(value = "/trade/pay")
public class TTppTradePayWhiteListController {
	
	private final Logger log = Logger.getLogger(TTppTradePayWhiteListController.class);
	
	@Autowired
	private TTppTradePayWhiteListService whiteListService;
	
	@RequestMapping(value = "/whitelist")
	public String whitelist(HttpServletRequest request, ModelMap modelMap){
		try {
			//权限验证
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/trade/pay/taskList")) {
				return "/errorpage/permissionError";
			}
			return "/trade/pay/whiteList";
		} catch (Exception e) {
			log.error("加载白名单列表页面异常", e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/whitelistData")
	@ResponseBody
	public String listData(HttpServletRequest request, ModelMap modelMap){
		try {
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			this.datagridParam(request, params);
			this.queryParam(request, params);
			// 2.遍历增加及转换自定义内容
			List<Map> rows = whiteListService.getPayWhiteList(params);
			int count = whiteListService.getPayWhiteListCount(params);
			for (Map d : rows) {
				// 2.自定义按钮
				d.put("DETAIL","<a href='javascript:return void(0);' onClick=\"viewWhiteListDetail('"+ d.get("ID")+ "');return false;\"><i class='icon-search'></i></a>");
				d.put("STATUS", d.get("STATUS") == null ? "" : PayWhiteListStatusEnum.getEnumDesc(d.get("STATUS").toString()));
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
			log.error("加载白名单列表数据异常", ex);
			return null;
		}
	}
	
	private void datagridParam(HttpServletRequest request, Map<String,Object> params) {
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
		params.put("SEARCH_STATUS", request.getParameter("search_status"));
		params.put("SEARCH_ACCOUNT_NO", request.getParameter("search_account_no"));
		params.put("SEARCH_BANK_CARD_NO", request.getParameter("search_bank_card_no"));
		params.put("SEARCH_INFO_CATEGORY", request.getParameter("search_info_category"));
		
		//设置系统查看权限
		TSysUser user = (TSysUser) request.getSession().getAttribute("user");
		List<String> appIdsList = user.getAppIds();
		if (appIdsList == null || appIdsList.size() < 1) {
			appIdsList = new ArrayList<String>();
			appIdsList.add("");
		}
		params.put("APPIDS", appIdsList);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/detailWhiteList")
	public String taskDetail(HttpServletRequest request, ModelMap modelMap){
		try {
			String whiteListId = request.getParameter("Id");
			if (StringUtils.isBlank(whiteListId)) {
				return null;
			}
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("SEARCH_ID", whiteListId);
			this.datagridParam(request, params);
			// 2、设置返回结果
			List<Map> rows = whiteListService.detailWhiteList(params);
			Map<String, Object> resultMap = null;
			if (rows != null && rows.size() > 0) {
				resultMap = rows.get(0);
			}
			for (String key : resultMap.keySet()) {
				modelMap.put(key.toLowerCase(), resultMap.get(key));
			}
			modelMap.put("status", modelMap.get("status") == null ? "" : PayWhiteListStatusEnum.getEnumDesc(modelMap.get("status").toString()));
			return "/trade/pay/detailWhiteList";
		} catch (Exception e){
			log.error("加载白名单详细信息异常", e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editWhiteList")
	public String editWhiteList(HttpServletRequest request, ModelMap modelMap) {
		try{
			String id = request.getParameter("Id");
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("SEARCH_ID", id);
			this.datagridParam(request, params);
			// 2、设置返回结果
			List<Map> rows = whiteListService.detailWhiteList(params);
			Map<String, Object> resultMap = null;
			if (rows != null && rows.size() > 0) {
				resultMap = rows.get(0);
			}
			for (String key : resultMap.keySet()) {
				modelMap.put(key.toLowerCase(), resultMap.get(key));
			}
			return "/trade/pay/editWhiteList";
		} catch (Exception e){
			log.error("加载白名单编辑页面异常", e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	@RequestMapping(value = "/editAndSaveWhiteList",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String update(HttpServletRequest request, ModelMap modelMap) {
		JSONObject result = new JSONObject();
		try {
			String bizSysNo = request.getParameter("biz_sys_name");
			if (StringUtils.isBlank(bizSysNo)) {
				result.put("msg", "业务系统不能为空");
				result.put("flag", false);
				return result.toString();
			}
			
			String info_category_name = request.getParameter("info_category_name");
			if (StringUtils.isBlank(info_category_name)) {
				result.put("msg", "信息类别不能为空");
				result.put("flag", false);
				return result.toString();
			}
			
			String account_no = request.getParameter("account_no");
			if (account_no != null && account_no.length() > 64) {
				result.put("msg", "账号超长");
				result.put("flag", false);
				return result.toString();
			}
			
			String bank_card_no = request.getParameter("bank_card_no");
			if (bank_card_no != null && bank_card_no.length() > 64) {
				result.put("msg", "银行卡号超长");
				result.put("flag", false);
				return result.toString();
			}
			
			String id = request.getParameter("id");
			String note = request.getParameter("note");
			String status =  request.getParameter("status");
			
			TppTradeTPayWhiteList whiteList = new TppTradeTPayWhiteList();
			whiteList.setId(Long.parseLong(id));
			whiteList.setAccountNo(account_no);
			whiteList.setBankCardNo(bank_card_no);
			whiteList.setBizSysNo(bizSysNo);
			whiteList.setInfoCategoryCode(info_category_name);
			whiteList.setNote(note);
			whiteList.setStatus(Integer.parseInt(status));
			whiteListService.update(whiteList);
			
		} catch (TppManagerException e){
			result.put("msg", e.getMessage());
			result.put("flag", false);
		} catch(Exception e){
			result.put("msg", "编辑白名单失败");
			result.put("flag", false);
		}
		return result.toString();
	}
	
	
	@RequestMapping(value = "/addWhiteList")
	public String addWhiteList(HttpServletRequest request, ModelMap modelMap){
		try {
			return "/trade/pay/addWhiteList";
		} catch (Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	@RequestMapping(value = "/delWhiteList/{ids}")
	@ResponseBody
	public String delete(@PathVariable("ids") String[] ids,HttpServletRequest request, ModelMap modelMap) {
		if (null != ids && ids.length > 0) {
			List<Integer> list = new ArrayList<Integer>();
			for (String infoIdStr : ids) {
				Integer infoId = Integer.valueOf(infoIdStr);
				list.add(infoId);
			}
			whiteListService.delete(list);
		}
		return null;
		
	}
	
	@RequestMapping(value = "/addAndSaveWhiteList",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String insert(HttpServletRequest request, ModelMap modelMap) {
		JSONObject result = new JSONObject();
		try {
			String bizSysNo = request.getParameter("biz_sys_name");
			if (StringUtils.isBlank(bizSysNo)) {
				result.put("msg", "业务系统不能为空");
				result.put("flag", false);
				return result.toString();
			}
			
			String info_category_name = request.getParameter("info_category_name");
			if (StringUtils.isBlank(info_category_name)) {
				result.put("msg", "信息类别不能为空");
				result.put("flag", false);
				return result.toString();
			}
			
			String account_no = request.getParameter("account_no");
			if (account_no != null && account_no.length() > 64) {
				result.put("msg", "账号超长");
				result.put("flag", false);
				return result.toString();
			}
			
			String bank_card_no = request.getParameter("bank_card_no");
			if (bank_card_no != null && bank_card_no.length() > 64) {
				result.put("msg", "银行卡号超长");
				result.put("flag", false);
				return result.toString();
			}
			
			String note = request.getParameter("note");
			String status =  request.getParameter("status");
			
			TppTradeTPayWhiteList whiteList = new TppTradeTPayWhiteList();
			whiteList.setAccountNo(account_no);
			whiteList.setBankCardNo(bank_card_no);
			whiteList.setBizSysNo(bizSysNo);
			whiteList.setInfoCategoryCode(info_category_name);
			whiteList.setNote(note);
			whiteList.setStatus(Integer.parseInt(status));
			HttpSession session = request.getSession();
			TSysUser user = (TSysUser) session.getAttribute("user");
			whiteList.setCreater(user.getUserName());
			whiteListService.insert(whiteList);
		} catch (TppManagerException e){
			result.put("msg", e.getMessage());
			result.put("flag", false);
		} catch (Exception e){
			System.out.println(e.getMessage());
			result.put("msg", "添加白名单失败");
			result.put("flag", false);
		}
		return result.toString();
	}
}
