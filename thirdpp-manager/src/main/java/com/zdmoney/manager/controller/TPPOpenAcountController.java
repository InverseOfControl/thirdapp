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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdmoney.manager.Validate.Validate;
import com.zdmoney.manager.enumset.GenderEnum;
import com.zdmoney.manager.enumset.IdTypeEnum;
import com.zdmoney.manager.enumset.OpenAccountStatusEnum;
import com.zdmoney.manager.enumset.PermissionTypeEnum;
import com.zdmoney.manager.enumset.ThirdType;
import com.zdmoney.manager.models.TAppIps;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TPPOpenAccount;
import com.zdmoney.manager.models.TSysPermission;
import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.service.TAppIpsService;
import com.zdmoney.manager.service.TBankInfoService;
import com.zdmoney.manager.service.TPPOpenAccountService;
import com.zdmoney.manager.service.TSysAppService;
import com.zdmoney.manager.utils.ConverNullString;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.Page;
import com.zdmoney.manager.utils.PermissionUtil;
import com.zdmoney.manager.utils.StringUtil;


/** 
* 开户
* @author wyj
* @version 2015年7月3日 下午1:59:18 
*/
@Controller
@RequestMapping(value = "/trade/openAccount")
public class TPPOpenAcountController {
	private final Logger log = Logger.getLogger(TSymPermissionController.class);	 
	@Autowired
	private Page<TPPOpenAccount> page;
	@Autowired
	private TPPOpenAccountService accountService;
	@RequestMapping(value = "/openAccountListData")
	@ResponseBody
	public String listData(HttpServletRequest request, ModelMap modelMap){		
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			this.datagridParam(request, params);
			this.queryParam(request, params);
		 
			// 2.遍历增加及转换自定义内容
			List<Map> rows =accountService.getOpenAccountList(params);
			int count = accountService.getOpenAccountListCount(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处  
				d.put("VIEW_CARDS","<a href='javascript:return void(0);' style='text-decoration:none' onClick=\"viewCards('"+ d.get("ZENGDAI_ACCOUNT_NO")+ "','" + d.get("BIZ_SYS_ACCOUNT_NO") + "','" +d.get("THIRD_ACCOUNT_NO")+ "' );\"><i class='icon-search'></i></a>");
				d.put("ID_TYPE", d.get("ID_TYPE") == null ? "" : IdTypeEnum.getEnumDesc(d.get("ID_TYPE").toString()));
				d.put("GENDER", d.get("GENDER") == null ? "" : GenderEnum.getEnumDesc(d.get("GENDER").toString()));
				/*d.put("PAY_SYS_NO", d.get("PAY_SYS_NO") == null ? "" : ThirdType.getEnumDesc(d.get("PAY_SYS_NO").toString()));*/
				d.put("STATUS", d.get("STATUS") == null ? "" : OpenAccountStatusEnum.getEnumDesc(d.get("STATUS").toString()));
				d.put("MOBILE", d.get("MOBILE") == null ? "" : StringUtil.hiddenPhoneNumber(d.get("MOBILE").toString()));
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
			params.put("orderStr", " order by " + " ID " + " " + " DESC " + " ");
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
		String beginTime =request.getParameter("beginTime");
		String endTime =request.getParameter("endTime");	
		if(StringUtils.isNotBlank(beginTime)){	
			beginTime = beginTime + " 00:00:00";
		}
		if(StringUtils.isNotBlank(endTime)){
			endTime = endTime + " 23:59:59";
		}
		params.put("beginTime", beginTime);		
		params.put("endTime", endTime);
		params.put("zengDaiAccountNo", request.getParameter("zengDaiAccountNo"));
		params.put("realName", request.getParameter("realName"));
		params.put("bizSysNo", request.getParameter("bizSysNo"));
		params.put("paySysNo", request.getParameter("paySysNo"));
		params.put("status", request.getParameter("status"));
		params.put("mobile", request.getParameter("mobile"));
		
		
		//设置系统查看权限
		TSysUser user = (TSysUser) request.getSession().getAttribute("user");
		List<String> appIdsList = user.getAppIds();
		if (appIdsList == null || appIdsList.size() < 1) {
			appIdsList = new ArrayList<String>();
			appIdsList.add("NO BIZ SYS");
		}
		params.put("APPIDS", appIdsList);
	}
	
	@RequestMapping(value = "/openAccountList")
	public String openAccountlist(HttpServletRequest request, ModelMap modelMap){
		try {
			
			return "/trade/openAccount/openAccountList";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	/**
	 * 跳转到银行卡查询页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/openAccountCardsList")
	public String openAccountCardslist(HttpServletRequest request, ModelMap modelMap){
		try {
			String zengdaiAccountNo = request.getParameter("zengdaiAccountNo");
			String bizsysAccountNo = request.getParameter("bizsysAccountNo");
			String thirdAccountNo = request.getParameter("thirdAccountNo");
			modelMap.put("zengdaiAccountNo", zengdaiAccountNo);
			modelMap.put("bizsysAccountNo", bizsysAccountNo);
			modelMap.put("thirdAccountNo", thirdAccountNo);
			return "/trade/openAccount/openAccountCardsList";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
}
