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
import com.zdmoney.manager.models.TSysPermission;
import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.service.TAppIpsService;
import com.zdmoney.manager.service.TBankInfoService;
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
@RequestMapping(value = "/bim/appIps")
public class TAppIpsController {
	private final Logger log = Logger.getLogger(TSymPermissionController.class);
	 
	@Autowired
	private Page<TAppIps> page;
	 
	@Autowired
	private TAppIpsService ipsService;
	
	@Autowired
	private  TSysAppService appService;
	
	
	
	@RequestMapping(value = "/appIpsListData/{appCode}")
	@ResponseBody
	public String listData(@PathVariable("appCode") String appCode,HttpServletRequest request, ModelMap modelMap){		
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
			 
			params.put("appCode",appCode);
			params.put("ip", request.getParameter("ip"));
		 
			// 2.遍历增加及转换自定义内容
			List<Map> rows =ipsService.getappIpsList(params);
			int count = ipsService.getappIpsListCount(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处  
				d.put("checkbox","<input name='subCheckBox' type='checkbox' value='"+ d.get("ID")+ "' />");
				d.put("UPDATER", d.get("UPDATER") == null ? "" : d.get("UPDATER").toString()+"/"+  (d.get("UPDATER_NAME") == null ? "":d.get("UPDATER_NAME").toString()));
				d.put("CREATER", d.get("CREATER") == null ? "" : d.get("CREATER").toString()+"/"+ (d.get("CREATER_NAME") == null ? "":d.get("CREATER_NAME").toString()));
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
				d.put("update","<a href='javascript:return void(0);' onClick=\"updateAppIp('"+ d.get("ID")+ "');\"><i class='icon-edit'></i></a>");
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
	@RequestMapping(value = "/appIpsList/{appCode}")
	public String ipslist(@PathVariable("appCode") String appCode,HttpServletRequest request, ModelMap modelMap){
		try {
			HttpSession session = request.getSession();
			/*if(!PermissionUtil.isHavePermission(session, "/bim/appIps/appIpsList")){
				return "/errorpage/permissionError";
			}*/
			
			modelMap.put("appCode", appCode);
			return "/bim/appIps/appIpsList";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	/**
	 * 银行信息编辑页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/appIpsEditUI/{id}/{appCode}")
	public String editUI(@PathVariable("id") String id,@PathVariable("appCode") String appCode,HttpServletRequest request, ModelMap modelMap) {
		TAppIps tb = null;
		if(!StringUtil.isEmpty(id)){
			//权限验证
			HttpSession session = request.getSession();
			 
	 
			tb = ipsService.selectAppIpsByID(StringUtil.parseLong(id));
		}else{
			//权限验证
			HttpSession session = request.getSession();
		 
			 
			 tb = new TAppIps();
			 tb.setAppCode(appCode);
			 	Map<String, Object> params=new HashMap<String, Object>();
				params.put("appCode", appCode);
				tb.setAppName(appService.getSysAppName(params));
		}
		if(null != tb){
			
			modelMap.put("appIps", tb);
	 
		}

		return "bim/appIps/appIpsEdit";
	}
	
	/**
	 * 保存银行数据信息
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/appIpsSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String bankInfoSave(@ModelAttribute("appIpsSave")  TAppIps appIps,HttpServletRequest request, ModelMap modelMap) {
		 
		try{
			//权限验证
			JSONObject jb=new JSONObject();
			HttpSession session = request.getSession();
			 
			
			ConverNullString sr=new  ConverNullString();
			sr.nullConverNullString(appIps);
			String response =  Validate.getInstance().validate(appIps,
					com.zdmoney.manager.Validate.InsertCheck.class, Default.class);
	
			if(!StringUtils.isBlank(response)){			 	 				
				jb.put("valmsg", response);
				return    jb.toString();
			}
		 
			 
			
			if(StringUtil.isEmpty(appIps.getId())){
				//新增	
				log.info("新增+："+appIps.getId());
				TSysUser user=	(TSysUser) session.getAttribute("user");
				appIps.setCreater(user.getLoginUserName());
				ipsService.insert(appIps);
			}else{
				//修改		
				log.info("修改+："+appIps.getId());
				TSysUser user=	(TSysUser) session.getAttribute("user");
				appIps.setUpdater(user.getLoginUserName());
				ipsService.updateAppIps(appIps);
			} 
			log.info("保存成功");
			return jb.toString();
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	@RequestMapping(value = "/appIpsDelete/{ids}")
	@ResponseBody
	public String ipsDelete(@PathVariable("ids") String[] ids,HttpServletRequest request, ModelMap modelMap) {
	 
		if(null != ids && ids.length>0){
			List<Integer> list = new ArrayList<Integer>();
			for(String infoIdStr : ids){
				Integer infoId = Integer.valueOf(infoIdStr);
				list.add(infoId);
			}
			ipsService.batchDeleteIps(list);;  
		}
		return null;
	}
	
}
