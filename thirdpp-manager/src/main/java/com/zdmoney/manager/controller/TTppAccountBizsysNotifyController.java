package com.zdmoney.manager.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdmoney.manager.enumset.AccountBizsysNotifyStatusEnum;
import com.zdmoney.manager.service.TTppAccountBizsysNotifyService;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.PermissionUtil;

@Controller
@RequestMapping("/account/bizsysNotify")
public class TTppAccountBizsysNotifyController {
	
	private final Logger log = Logger.getLogger(TTppAccountBizsysNotifyController.class);
	
	@Autowired
	private TTppAccountBizsysNotifyService bizsysNotifyService;
	
	/**
	 * 页面跳转
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/bizsysNotifyList")
	public String list(HttpServletRequest request, ModelMap modelMap){
		try{
			//权限验证
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/account/bizsysNotify/bizsysNotifyList")){
				return "/errorpage/permissionError";
			}
			return "/account/bizsysNotify/bizsysNotifyList";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	/**
	 * 获取数据
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/listData")
	@ResponseBody
	public String getListData(HttpServletRequest request, ModelMap modelMap) {

		try {
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			this.datagridParam(request, params);
			this.queryParam(request, params);
			List<Map> rows = bizsysNotifyService.select_tppAccountBizsysNotifyList(params);
			int count = bizsysNotifyService.select_tppAccountBizsysNotifyList_count(params);

			// 2.遍历增加自定义内容
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				/*d.put("EDIT","<a href='javascript:return void(0);' onClick=\"tppAccountChannelConfig_list.updateFormSubmit('"+ d.get("ID")+ "');return false;\"><i class='icon-edit'></i></a>");
				d.put("DETAIL","<a href='javascript:return void(0);'onClick=\"tppAccountChannelConfig_list.detailFormSubmit('"+ d.get("ID")+ "');return false;\"><i class='icon-search'></i></a>");*/
				/*d.put("IS_SEPARATE", d.get("IS_SEPARATE") == null ? "" : IsSeparateEnum.getEnumDesc(d.get("IS_SEPARATE").toString()));
				d.put("ACCOUNT_STATUS", d.get("ACCOUNT_STATUS") == null ? "" : AccountThirdStatusEnum.getEnumDesc(d.get("ACCOUNT_STATUS").toString()));
				d.put("BIZSYS_ACCOUNT_STATUS", d.get("BIZSYS_ACCOUNT_STATUS") == null ? "" : AccountBizsysStatusEnum.getEnumDesc(d.get("BIZSYS_ACCOUNT_STATUS").toString()));*/
				d.put("STATUS", d.get("STATUS") == null ? "" : AccountBizsysNotifyStatusEnum.getEnumDesc(d.get("STATUS").toString()));
			}

			// 3.组合输出列表查询所需数据
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			
			JSONArray json_rows = JSONArray.fromObject(rows, config);
			String json = "{\"total\":\""
					+ count
					+ "\",\"rows\":" + json_rows.toString() + "}";
			modelMap.put("total", count);
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
		} else {
			params.put("orderStr", " order by " + " ID " + " " + " DESC " + " ");
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
	private void queryParam(HttpServletRequest request, Map<String,Object> params) throws Exception {
		params.put("SEARCH_BIZSYS_ACCOUNT_DAY", request.getParameter("search_bizsys_account_day"));
		params.put("SEARCH_MERCHANT_NO", request.getParameter("search_merchant_no"));
		params.put("SEARCH_STATUS", request.getParameter("search_status"));
	}
}
