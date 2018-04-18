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
import com.zdmoney.manager.enumset.AccountBizsysStatusEnum;
import com.zdmoney.manager.enumset.AccountThirdStatusEnum;
import com.zdmoney.manager.enumset.IsSeparateEnum;
import com.zdmoney.manager.service.TTppAccountInfoService;
import com.zdmoney.manager.utils.BigDecimalFormatUtil;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.PermissionUtil;

@Controller
@RequestMapping("/account/accountInfo")
public class TTppAccountInfoController {
	
	private final Logger log = Logger.getLogger(TTppAccountInfoController.class);
	
	@Autowired
	private TTppAccountInfoService accountInfoService;
	
	/**
	 * 页面跳转
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/accountInfoList")
	public String list(HttpServletRequest request, ModelMap modelMap){
		try{
			//权限验证
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/account/accountInfo/accountInfoList")){
				return "/errorpage/permissionError";
			}
			return "/account/accountInfo/accountInfoList";
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
			List<Map> rows = accountInfoService.select_tppAccountInfoList(params);
			int count = accountInfoService.select_tppAccountInfoList_count(params);

			// 2.遍历增加自定义内容
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				/*d.put("EDIT","<a href='javascript:return void(0);' onClick=\"tppAccountChannelConfig_list.updateFormSubmit('"+ d.get("ID")+ "');return false;\"><i class='icon-edit'></i></a>");
				d.put("DETAIL","<a href='javascript:return void(0);'onClick=\"tppAccountChannelConfig_list.detailFormSubmit('"+ d.get("ID")+ "');return false;\"><i class='icon-search'></i></a>");*/
				d.put("IS_SEPARATE", d.get("IS_SEPARATE") == null ? "" : IsSeparateEnum.getEnumDesc(d.get("IS_SEPARATE").toString()));
				d.put("ACCOUNT_STATUS", d.get("ACCOUNT_STATUS") == null ? "" : AccountThirdStatusEnum.getEnumDesc(d.get("ACCOUNT_STATUS").toString()));
				d.put("BIZSYS_ACCOUNT_STATUS", d.get("BIZSYS_ACCOUNT_STATUS") == null ? "" : AccountBizsysStatusEnum.getEnumDesc(d.get("BIZSYS_ACCOUNT_STATUS").toString()));
				d.put("AMOUNT" , d.get( "AMOUNT" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get( "AMOUNT" )) );
				d.put("TASK_AMOUNT" , d.get( "TASK_AMOUNT" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get( "TASK_AMOUNT" )) );
				d.put("ORIGINAL_AMOUNT" , d.get( "ORIGINAL_AMOUNT" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat(new BigDecimal((String)d.get( "ORIGINAL_AMOUNT" ))) );
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
		
		params.put("SEARCH_THIRD_TYPE_NO", request.getParameter("search_third_type_no"));
		params.put("SEARCH_MERCHANT_NO", request.getParameter("search_merchant_no"));
		params.put("SEARCH_BIZ_TYPE", request.getParameter("search_biz_type"));
		params.put("SEARCH_BIZ_SYS_NO", request.getParameter("search_biz_sys_no"));
		params.put("SEARCH_TRADE_FLOW", request.getParameter("search_trade_flow"));
		params.put("SEARCH_THIRD_PARTY_TRADE_FLOW", request.getParameter("search_third_party_trade_flow"));
		params.put("SEARCH_ACCOUNT_STATUS", request.getParameter("search_account_status"));
		params.put("SEARCH_BIZSYS_ACCOUNT_STATUS", request.getParameter("search_bizsys_account_status"));
		params.put("SEARCH_BIZ_FLOW", request.getParameter("search_biz_flow"));
	}
}
