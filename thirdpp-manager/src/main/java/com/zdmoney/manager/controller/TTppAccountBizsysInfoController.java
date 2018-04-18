package com.zdmoney.manager.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
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

import com.zdmoney.manager.enumset.AccountBizTypeEnum;
import com.zdmoney.manager.enumset.AccountBizsysNotifyStatusEnum;
import com.zdmoney.manager.enumset.BizTypeEnum;
import com.zdmoney.manager.service.TTppAccountBizsysInfoService;
import com.zdmoney.manager.utils.BigDecimalFormatUtil;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.PermissionUtil;

@Controller
@RequestMapping("/account/bizsysInfo")
public class TTppAccountBizsysInfoController {
	@Autowired
	TTppAccountBizsysInfoService bizsysInfoService;
	
	private final Logger log = Logger.getLogger(TTppAccountBizsysInfoController.class);
	
	/**
	 * 页面跳转
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/bizsysInfoList")
	public String list(HttpServletRequest request, ModelMap modelMap){
		try{
			//权限验证
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/account/bizsysInfo/bizsysInfoList")){
				return "/errorpage/permissionError";
			}
			return "/account/bizsysInfo/bizsysInfoList";
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
			List<Map> rows = bizsysInfoService.select_tppAccountBizsysInfoList(params);
			int count = bizsysInfoService.select_tppAccountBizsysInfoList_count(params);

			// 2.遍历增加自定义内容
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				/*d.put("EDIT","<a href='javascript:return void(0);' onClick=\"tppAccountChannelConfig_list.updateFormSubmit('"+ d.get("ID")+ "');return false;\"><i class='icon-edit'></i></a>");
				d.put("DETAIL","<a href='javascript:return void(0);'onClick=\"tppAccountChannelConfig_list.detailFormSubmit('"+ d.get("ID")+ "');return false;\"><i class='icon-search'></i></a>");*/
				/*d.put("IS_SEPARATE", d.get("IS_SEPARATE") == null ? "" : IsSeparateEnum.getEnumDesc(d.get("IS_SEPARATE").toString()));
				d.put("ACCOUNT_STATUS", d.get("ACCOUNT_STATUS") == null ? "" : AccountThirdStatusEnum.getEnumDesc(d.get("ACCOUNT_STATUS").toString()));
				d.put("BIZSYS_ACCOUNT_STATUS", d.get("BIZSYS_ACCOUNT_STATUS") == null ? "" : AccountBizsysStatusEnum.getEnumDesc(d.get("BIZSYS_ACCOUNT_STATUS").toString()));*/
				/*d.put("STATUS", d.get("STATUS") == null ? "" : AccountBizsysNotifyStatusEnum.getEnumDesc(d.get("STATUS").toString()));*/
				d.put("TOTAL_AMOUNT" , d.get( "TOTAL_AMOUNT" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get( "TOTAL_AMOUNT" )) );
				d.put("SUCCESS_AMOUNT" , d.get( "SUCCESS_AMOUNT" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get( "SUCCESS_AMOUNT" )) );
				
				//业务类型转换
				String bizTypes = d.get("BIZ_TYPE") == null ? "" : d.get("BIZ_TYPE").toString();
				List<String> bizTypeList = Arrays.asList(bizTypes.split("_"));
				StringBuffer bizTypeStr = new StringBuffer();
				for (String bizType : bizTypeList) {
					if (StringUtils.isBlank(bizTypeStr.toString())) {
						bizTypeStr.append(AccountBizTypeEnum.getEnumDesc(bizType));
					} else {
						bizTypeStr.append(",");
						bizTypeStr.append(AccountBizTypeEnum.getEnumDesc(bizType));
					}
				}
				d.put("BIZ_TYPE", bizTypes);
				d.put("BIZ_TYPE_STR", bizTypeStr.toString());
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
			params.put("orderStr", " order by " + " TASK_ID " + " " + " DESC " + " ");
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
		String beginDateStr = request.getParameter("search_account_begin_date");
		String endDateStr = request.getParameter("search_account_end_date");
		if (StringUtils.isNotBlank(beginDateStr)) {
			beginDateStr = beginDateStr.replace("-", "");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			endDateStr = endDateStr.replace("-", "");
		}
		
		params.put("SEARCH_ACCOUNT_BEGIN_DATE", beginDateStr);
		params.put("SEARCH_ACCOUNT_END_DATE", endDateStr);
		params.put("SEARCH_BIZ_FLOW", request.getParameter("search_biz_flow"));
		params.put("SEARCH_BIZ_TYPE", request.getParameter("search_biz_type"));
		params.put("SEARCH_TASK_ID", request.getParameter("search_task_id"));
		params.put("SEARCH_BIZ_SYS_NO", request.getParameter("search_biz_sys_no"));
		params.put("SEARCH_PAY_SYS_NO", request.getParameter("search_third_type_no"));
	}
}
