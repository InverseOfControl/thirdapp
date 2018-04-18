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
import com.zdmoney.manager.models.TAppIps;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TCategoryApps;
import com.zdmoney.manager.models.TSysApp;
import com.zdmoney.manager.models.TSysPermission;
import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.service.TAppIpsService;
import com.zdmoney.manager.service.TBankInfoService;
import com.zdmoney.manager.service.TCategoryAppService;
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
@RequestMapping(value = "/bim/sysApp")
public class TSysAppController {
	private final Logger log = Logger.getLogger(TSymPermissionController.class);
	@Autowired
	private TSysAppService sysAppService; 
	@Autowired
	private Page<TCategoryApps> pageInfo;
	@Autowired
	private TCategoryAppService infoCategoryservice;
	@Autowired
	private Page<TSysApp> page;
	@RequestMapping(value = "/sysAppListData/{id}")
	@ResponseBody
	public String listData(@PathVariable("id") String id,HttpServletRequest request, ModelMap modelMap){
		try {
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			String beginTime =request.getParameter("beginTime");
			String endTime =request.getParameter("endTime");

			this.datagridParam(request, params);
			if(!StringUtil.isEmpty(beginTime)){

				beginTime = beginTime + " 00:00:00";
				params.put("beginTime",beginTime);
			}
			if(!StringUtil.isEmpty(endTime)){

				endTime = endTime + " 23:59:59";
				params.put("endTime",endTime);
			}
			
			params.put("id", id);
			params.put("appName", request.getParameter("appName"));
			params.put("appCode", request.getParameter("appCode"));
			params.put("isFlag", request.getParameter("isFlag"));
			params.put("contactName", request.getParameter("contactName"));
			params.put("contactMobile", request.getParameter("contactMobile"));
		 
			// 2.遍历增加及转换自定义内容
			List<Map> rows =sysAppService.getSysAppList(params);
			int count = sysAppService.getSysAppListCount(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				
				if(!StringUtil.isEmpty(d.get("IS_FLAG"))){
					if(d.get("IS_FLAG").equals("0")){
					d.put("MESSAGETYPE","<a href='javascript:return void(0);' style='text-decoration:none' onClick=\"updateFlag('"+ d.get("ID")+ "','"+ d.get("IS_FLAG")+ "');\">开启</a>");
					d.put("IS_FLAG","<span style='color:red'>关闭</span>");
					}
					else if(d.get("IS_FLAG").equals("1")){
					d.put("MESSAGETYPE","<a href='javascript:return void(0);' style='text-decoration:none'  onClick=\"updateFlag('"+ d.get("ID")+ "','"+ d.get("IS_FLAG")+ "');\">关闭 </a>");
					d.put("IS_FLAG","开启");
					};
				}
		
				d.put("UPDATER", d.get("UPDATER") == null ? "" : d.get("UPDATER").toString()+"/"+  (d.get("UPDATER_NAME") == null ? "":d.get("UPDATER_NAME").toString()));
				d.put("CREATER", d.get("CREATER") == null ? "" : d.get("CREATER").toString()+"/"+ (d.get("CREATER_NAME") == null ? "":d.get("CREATER_NAME").toString()));
				d.put("update","<a href='javascript:return void(0);' onClick=\"updateThreadPool('"+ d.get("ID")+ "');\"><i class='icon-edit'></i></a>");
				 
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
	@RequestMapping(value = "/sysAppList")
	public String list(HttpServletRequest request, ModelMap modelMap){
		try {
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/bim/sysApp/sysAppList")){
				return "/errorpage/permissionError";
			}		
		  	return "/bim/sysApp/toSysAppMeanu"; 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	@RequestMapping(value = "/sysAppListObj/{id}")
	public String sysAppObject(@PathVariable("id") String id,HttpServletRequest request, ModelMap modelMap){
		try {
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/bim/sysApp/sysAppList")){
				return "/errorpage/permissionError";
			}	
			modelMap.put("id", id);
		  	return "bim/sysApp/sysAppList"; 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	
	@RequestMapping(value = "/getSysAppMeanu")
	@ResponseBody
	public String getSysAppMeanu(HttpServletRequest request, ModelMap modelMap){
		try {
			 
			List<Map> rows=sysAppService.getSysAppMeanu();  
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);

			JSONArray json_rows = JSONArray.fromObject(rows, config);
			String json = "{\"total\":\""
					+ 0
					+ "\",\"rows\":" + json_rows.toString() + "}";
			return json;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/**
	 * 银行信息编辑页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/sysAppEditUI/{id}")
	public String editUI(@PathVariable("id") String id,HttpServletRequest request, ModelMap modelMap) {
		TSysApp sp = null;
		if(!StringUtil.isEmpty(id)){
			 

			sp = sysAppService.selectSysAppByID(StringUtil.parseLong(id));
			sp.setNoteNo(sp.getAppCode());
			if(StringUtil.isEmpty(sp.getRemark())){sp.setRemark(null);};
		}else{
			 
		 

			sp = new TSysApp();
		}
		if(null != sp){
			modelMap.put("sysApp", sp);
		}

		return "/bim/sysApp/sysAppEdit";
	}
	/**
	 * 改变系统信息状态
	 */
	@RequestMapping(value = "/sysAppUpdateIsFlag")
	@ResponseBody
	public String updateIsFlag(HttpServletRequest request, ModelMap modelMap) {
		TSysApp sp = new TSysApp();
		try{
			//权限验证
			String id = request.getParameter("id");
			String flag = request.getParameter("flag");
			sp.setId(StringUtil.parseInteger(id));
			/*flag=java.net.URLDecoder.decode(flag,"UTF-8"); 
			System.out.println("flag="+flag);*/
			if(flag.equals("1")){
				flag="0";
			}else if(flag.equals("0")){
				flag="1";
			}
			sp.setIsFlag(flag);
			HttpSession session = request.getSession();
			TSysUser user=	(TSysUser) session.getAttribute("user");
			sp.setUpdater(user.getLoginUserName());
			sysAppService.updateSysAppFlag(sp);
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
		 JSONObject js=new JSONObject();
	  return js.toString();
	 
	}
	/**
	 * 保存银行数据信息
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/sysAppSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String bankInfoSave(@ModelAttribute("sysAppSave")  TSysApp sysApp,HttpServletRequest request, ModelMap modelMap) {

		try{
			//权限验证
			HttpSession session = request.getSession();
		 
			ConverNullString sr=new  ConverNullString();
			sr.nullConverNullString(sysApp);
			String response =  Validate.getInstance().validate(sysApp,
					com.zdmoney.manager.Validate.InsertCheck.class, Default.class);
			JSONObject jb=new JSONObject();
		
			if(!StringUtils.isBlank(response)){			 	 				
				jb.put("valmsg", response);
				return    jb.toString();
			}
			int count=sysAppService.getAppCodeCount(sysApp.getAppCode());
			if(count>0){
				if(StringUtil.isEmpty(sysApp.getId())){
					response="业务系统编号已存在";
					jb.put("valmsg",response);
					return    jb.toString();
				}else{
					if(!sysApp.getAppCode().equals(sysApp.getNoteNo())){
						response="业务系统编号已存在";
						jb.put("valmsg",response);
						return    jb.toString();
					}
				}
				
			}
			
			if(StringUtil.isEmpty(sysApp.getId())){
				//新增	
				log.info("新增+："+sysApp.getId());	
				TSysUser user=	(TSysUser) session.getAttribute("user");
				sysApp.setCreater(user.getLoginUserName());
				sysAppService.insert(sysApp);
			}else{
				//修改		
				log.info("修改+："+sysApp.getId());
				TSysUser user=	(TSysUser) session.getAttribute("user");
				sysApp.setUpdater(user.getLoginUserName());
				sysAppService.updateSysApp(sysApp);
			} 
			log.info("保存成功");
			return jb.toString();
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	@RequestMapping(value = "/sysAppDelete/{ids}")
	@ResponseBody
	public String delete(@PathVariable("ids") String[] ids,HttpServletRequest request, ModelMap modelMap) {
			if(null != ids && ids.length>0){
				List<Integer> list = new ArrayList<Integer>();
				for(String infoIdStr : ids){
					Integer infoId = Integer.valueOf(infoIdStr);
					list.add(infoId);
				}
				sysAppService.batchDeleteInfo(list);
			}
		 
		return null;
		
	}
	@RequestMapping(value = "/infoCategoryAppsList/{id}")
	@ResponseBody
	public String infoCategoryList(@PathVariable("id") String id,HttpServletRequest request, ModelMap modelMap){
		try {
			HttpSession session = request.getSession();
			 
			Map<String,Object> params = new HashMap<String,Object>();
			String beginTime =request.getParameter("beginTime");
			String endTime =request.getParameter("endTime");

			this.datagridParam(request, params);
			if(!StringUtil.isEmpty(beginTime)){

				beginTime=beginTime.replaceAll("-", "");
				params.put("beginTime", beginTime);

			}
			if(!StringUtil.isEmpty(endTime)){
				endTime=endTime.replaceAll("-", "");
				params.put("endTime", endTime);

			}
			//分页		 
			//查询条件
			//封装查询条件
			params.put("infoCateGoryName", request.getParameter("infoCateGoryName"));
			params.put("appCode", request.getParameter("appCode"));
			params.put("priority", request.getParameter("priority"));
			params.put("contactName", request.getParameter("contactName"));
			params.put("contactMobile", request.getParameter("contactMobile"));
			
			params.put("id", id);
			 
			 
		 
			// 2.遍历增加及转换自定义内容
						List<Map> rows =infoCategoryservice.getInfoCatefoList(params);
						int count = infoCategoryservice.getInfoCatefoListCount(params);
						for (Map d : rows) {
							// 2.自定义按钮设置在此处
							/*/d.put("PAYER_BANK_CARD_TYPE", d.get("PAYER_BANK_CARD_TYPE") == null ? "" : BankCardTypeEnum.getEnumDesc(d.get("PAYER_BANK_CARD_TYPE").toString()));
							d.put("PAYER_ID_TYPE", d.get("PAYER_ID_TYPE") == null ? "" : IdTypeEnum.getEnumDesc(d.get("PAYER_ID_TYPE").toString()));
							d.put("CURRENCY", d.get("CURRENCY") == null ? "" : CurrencyEnum.getEnumDesc(d.get("CURRENCY").toString()));
							d.put("PRIORITY", d.get("PRIORITY") == null ? "" : PriorityEnum.getEnumDesc(d.get("PRIORITY").toString()));
							d.put("STATUS", d.get("STATUS") == null ? "" : SendStatusEnum.getEnumDesc(d.get("STATUS").toString()));
							d.put("IS_SEPARATE", d.get("IS_SEPARATE") == null ? "" : IsSeparateEnum.getEnumDesc(d.get("IS_SEPARATE").toString()));
							d.put("BIZ_TYPE", d.get("BIZ_TYPE") == null ? "" : BizTypeEnum.getEnumDesc(d.get("BIZ_TYPE").toString()));
							d.put("IS_NEED_PUSH", d.get("IS_NEED_PUSH") == null ? "" : NeedPushEnum.getEnumDesc(d.get("IS_NEED_PUSH").toString()));
							d.put("TRADE_STATUS", d.get("TRADE_STATUS") == null ? "" : TradeStatusEnum.getEnumDesc(d.get("TRADE_STATUS").toString()));
							d.put("IS_NEED_SPILT", d.get("IS_NEED_SPILT") == null ? "" : IsNeedSpiltEnum.getEnumDesc(d.get("IS_NEED_SPILT").toString()));
							d.put("AMOUNT" , d.get( "AMOUNT" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get( "AMOUNT" )) );
							d.put("FEE" , d.get( "FEE" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get( "FEE" )) );
							d.put("TRADE_SUCCESS_AMOUNT" , d.get( "TRADE_SUCCESS_AMOUNT" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get( "TRADE_SUCCESS_AMOUNT" )) );*/
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
	  
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value = "/toInfoCategoryAppList/{id}")
	public String toCategoryApplist(@PathVariable("id") String id,HttpServletRequest request, ModelMap modelMap){
		try {
			HttpSession session = request.getSession();
		  
			modelMap.put("appId", id);
		 return "/bim/sysApp/infoCategoryAppsList";
		/*	return "/bim/sysApp/toSysAppMeanu";*/
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	/**
	 * 信息编辑页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	
	
	@RequestMapping(value = "/infoAppsDelete")
	public String infoAppsDelete(HttpServletRequest request, ModelMap modelMap) {
		try{
			//权限验证
			HttpSession session = request.getSession();
		 
			
			String[] infoAppsIds = request.getParameterValues("subCheckBox");
			if(null != infoAppsIds && infoAppsIds.length>0){
				List<Integer> list = new ArrayList<Integer>();
				for(String infoIdStr : infoAppsIds){
					Integer infoId = Integer.valueOf(infoIdStr);
					list.add(infoId);
				}
				
				List<TCategoryApps> listId=	infoCategoryservice.infoAppsIDList(list);
				List<Integer> listAppId = new ArrayList<Integer>();
				for(TCategoryApps infoIdStr : listId){
					Integer infoId = Integer.valueOf(infoIdStr.getId());
					listAppId.add(infoId);
				}
				infoCategoryservice.batchDeleteInfo(list);
				infoCategoryservice.batchDeleteInfo(listAppId);
				 				
			}
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
		return "redirect:/bim/sysApp/sysAppList";
	} 
	

}
