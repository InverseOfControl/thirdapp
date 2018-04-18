package com.zdmoney.manager.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.groups.Default;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdmoney.manager.Validate.Validate;
import com.zdmoney.manager.enumset.BankCardTypeEnum;
import com.zdmoney.manager.enumset.GenderEnum;
import com.zdmoney.manager.enumset.IdTypeEnum;
import com.zdmoney.manager.enumset.OpenAccountCardsStatusEnum;
import com.zdmoney.manager.enumset.OpenAccountStatusEnum;
import com.zdmoney.manager.enumset.PermissionTypeEnum;
import com.zdmoney.manager.enumset.ThirdType;
import com.zdmoney.manager.models.TAppIps;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TPPOpenAccount;
import com.zdmoney.manager.models.TPPOpenAccountCards;
import com.zdmoney.manager.models.TSysPermission;
import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.service.TAppIpsService;
import com.zdmoney.manager.service.TBankInfoService;
import com.zdmoney.manager.service.TPPOpenAccountCardsService;
import com.zdmoney.manager.service.TPPOpenAccountService;
import com.zdmoney.manager.service.TSysAppService;
import com.zdmoney.manager.utils.ConverNullString;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.Page;
import com.zdmoney.manager.utils.PermissionUtil;
import com.zdmoney.manager.utils.StringUtil;


/** 
*
* @author wyj
* @version 2015年7月3日 下午1:59:18 
*/
@Controller
@RequestMapping(value = "/trade/openAccountCards")
public class TPPOpenAcountCardsController {
	private final Logger log = Logger.getLogger(TSymPermissionController.class);	 
	@Autowired
	private Page<TPPOpenAccountCards> page;
	@Autowired
	private TPPOpenAccountCardsService accountService;
	@Autowired
	private TPPOpenAccountService openAccountService;
	@RequestMapping(value = "/openAccountCardsListData")
	@ResponseBody
	public String listData(HttpServletRequest request, ModelMap modelMap){		
		try {
			Map<String,Object> params = new HashMap<String,Object>();
						 
			this.datagridParam(request, params);
			this.queryParam(request, params);
			
			// 2.遍历增加及转换自定义内容
			List<Map> rows =accountService.getOpenAccountCardsList(params);
			int count = accountService.getOpenAccountCardsListCount(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处  
				d.put("AC","<a href='javascript:return void(0);' style='text-decoration:none' onClick=\"searchAccount('"+ d.get("OPEN_ACCOUNT_ID")+ "' );\"><i class='icon-search'></i></a>");
				d.put("BANK_CARD_TYPE", d.get("BANK_CARD_TYPE") == null ? "" : BankCardTypeEnum.getEnumDesc(d.get("BANK_CARD_TYPE").toString()));
			/*	d.put("PAY_SYS_NO", d.get("PAY_SYS_NO") == null ? "" : ThirdType.getEnumDesc(d.get("PAY_SYS_NO").toString()));*/
				d.put("STATUS", d.get("STATUS") == null ? "" : OpenAccountCardsStatusEnum.getEnumDesc(d.get("STATUS").toString()));
				d.put("BANK_CARD_NO", d.get("BANK_CARD_NO") == null ? "" : StringUtil.hiddenPrivateInfomation(d.get("BANK_CARD_NO").toString()));
				d.put("RESERVE_MOBILE", d.get("RESERVE_MOBILE") == null ? "" : StringUtil.hiddenPhoneNumber(d.get("RESERVE_MOBILE").toString()));
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
		params.put("thirdAccountNo", request.getParameter("thirdAccountNo"));
		params.put("bankCardName", request.getParameter("bankCardName"));
		params.put("bankCardType", request.getParameter("bankCardType"));
		params.put("paySysNo", request.getParameter("paySysNo"));
		params.put("status", request.getParameter("status"));
		params.put("bankCardNo", request.getParameter("bankCardNo"));
		params.put("reserveMobile", request.getParameter("reserveMobile"));
		
		params.put("zengdaiAccountNo", request.getParameter("zengdaiAccountNo"));
		params.put("bizsysAccountNo", request.getParameter("bizsysAccountNo"));
		params.put("thirdAccountNo", request.getParameter("thirdAccountNo"));
			
		//设置系统查看权限
		TSysUser user = (TSysUser) request.getSession().getAttribute("user");
		List<String> appIdsList = user.getAppIds();
		if (appIdsList == null || appIdsList.size() < 1) {
			appIdsList = new ArrayList<String>();
			appIdsList.add("NO BIZ SYS");
		}
		params.put("APPIDS", appIdsList);
	}
	
	@RequestMapping(value = "/openAccountCardsList")
	public String openAccountCardslist(HttpServletRequest request, ModelMap modelMap){
		try {
			return "/trade/accountCards/openAccountCardsList";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	@RequestMapping(value = "/getOpenAccountUI/{id}")
	public String getOpenAccount(@PathVariable("id") String id,HttpServletRequest request, ModelMap modelMap){
		try {			
			TPPOpenAccount oa=	openAccountService.getOpenAccountById(id);
			 String rtn_str = "";
				 
			if(oa!=null){
				oa.setIdType(oa.getIdType() == null ? "" : IdTypeEnum.getEnumDesc(oa.getIdType().toString()));
				oa.setGender(oa.getGender() == null ? "" : GenderEnum.getEnumDesc(oa.getGender() .toString()));
				oa.setPaySysNo(oa.getPaySysNo() == null ? "" : ThirdType.getEnumDesc(oa.getPaySysNo().toString()));
				oa.setStatus(oa.getStatus() == null ? "" : OpenAccountStatusEnum.getEnumDesc(oa.getStatus() .toString()));
				if(oa.getOpenTime()!=null){
					oa.setOpenTime1(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)oa.getOpenTime()));
				}
				if(oa.getUpdateTime()!=null){
					oa.setUpdateTime1(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)oa.getUpdateTime()));
				}
				if(oa.getCreateTime()!=null){
					oa.setCreateTime1(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)oa.getCreateTime()));
				}
				
			}
			modelMap.put("account",oa);
			return "/trade/accountCards/openAccountInfo";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	
}
