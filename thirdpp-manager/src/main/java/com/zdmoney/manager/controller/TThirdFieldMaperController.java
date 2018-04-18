package com.zdmoney.manager.controller;

import java.math.BigDecimal;
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
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
 
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
 
import org.springframework.web.bind.annotation.ResponseBody;

 
 
 







import com.zdmoney.manager.Validate.Validate;
import com.zdmoney.manager.enumset.ChannelStatusEnum;
import com.zdmoney.manager.models.TChannelInfo;
import com.zdmoney.manager.models.TThirdFieldMapper;
 
import com.zdmoney.manager.service.TDictionaryService;
import com.zdmoney.manager.service.TThirdFieldMapperService;
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
@RequestMapping(value = "/bim/thirdField")
public class TThirdFieldMaperController {
	private final Logger log = Logger.getLogger(TSymPermissionController.class);
	@Autowired
	private TThirdFieldMapperService tThirdService; 
	@Autowired
	private TDictionaryService dictionaryService; 
	@Autowired
	private Page<TThirdFieldMapper> page;
	@RequestMapping(value = "/thirdFieldListData/{dicCode}")
	@ResponseBody
	public String listData(@PathVariable("dicCode") String dicCode,HttpServletRequest request, ModelMap modelMap){
		
		try {
			// 1、设置查询参数
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
			params.put("thirdPartyType", dicCode);
			params.put("thirdFieldCode", request.getParameter("thirdFieldCode"));
			params.put("fieldType", request.getParameter("fieldType"));
			params.put("fieldName", request.getParameter("fieldName"));
			params.put("bankOrgProvinceNo", request.getParameter("bankOrgProvinceNo"));
			// 2.遍历增加及转换自定义内容
			List<Map> rows =tThirdService.getThirdFieldList(params);
			int count = tThirdService.getThirdFieldListCount(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				if(!StringUtil.isEmpty(d.get("FIELD_TYPE"))) {
				
				
	 
				if(!StringUtil.isEmpty(d.get("COLLECT_MAX_MONEY"))){
					d.put("COLLECT_MAX_MONEY", d.get("COLLECT_MAX_MONEY").toString()) ;
				}
				if(!StringUtil.isEmpty(d.get("PAY_MAX_MONEY"))){
					d.put("PAY_MAX_MONEY", d.get("PAY_MAX_MONEY").toString()) ;
				}
				if(!StringUtil.isEmpty(d.get("QUICK_PAY_MAX_MONEY"))){
					d.put("QUICK_PAY_MAX_MONEY", d.get("QUICK_PAY_MAX_MONEY").toString()) ;
				}
				if(!StringUtil.isEmpty(d.get("FIELD_TYPE")) && d.get("FIELD_TYPE").equals("0")){
					
					if(d.get("STATUS").toString().equals("0")){
						d.put("AC","<a href='javascript:return void(0);' style='text-decoration:none' onClick=\"updateFlag('"+ d.get("ID")+ "','"+ d.get("STATUS")+ "');return false;\">开启 </a>");
					}
					else if(d.get("STATUS").toString().equals("1")){
						d.put("AC","<a href='javascript:return void(0);' style='text-decoration:none'  onClick=\"updateFlag('"+ d.get("ID")+ "','"+ d.get("STATUS")+ "');return false;\">关闭 </a>");
					}
					d.put("STATUS", d.get("STATUS") == null ? "" : ChannelStatusEnum.getEnumDesc(d.get("STATUS").toString()));
				}else{
					d.put("STATUS", d.get("STATUS") == null ? "" : "");
				}
			
				 if(!StringUtil.isEmpty(d.get("STATUS"))){
					if( d.get("STATUS").toString().equals("关闭")){
						d.put("STATUS","<span style='color:red'>关闭</span>");
					}
				 }
				if(d.get("FIELD_TYPE").equals("0")){
					d.put("FIELD_TYPE","银行编码");
				} else if(d.get("FIELD_TYPE").equals("1")){
					d.put("FIELD_TYPE","币种");
				} else if(d.get("FIELD_TYPE").equals("2")){
					d.put("FIELD_TYPE","银行卡类型");
				} else if(d.get("FIELD_TYPE").equals("3")){
					d.put("FIELD_TYPE","证件类型");
				} else if(d.get("FIELD_TYPE").equals("4")){
					d.put("FIELD_TYPE","对公对私标志");
				}
			}
				 
			}

			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type2(config);

			JSONArray json_rows = JSONArray.fromObject(rows, config);
			System.out.println(json_rows.toString());
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
	
	@RequestMapping(value = "/toThirdFieldList/{dicCode}/{id}")
	public String toListData(@PathVariable("dicCode") String dicCode,@PathVariable("id") String parentId,HttpServletRequest request, ModelMap modelMap){		 
		modelMap.put("dicCode", dicCode);
		modelMap.put("parentId", parentId);
		return "/bim/thirdField/thirdFieldList";
	}
	@RequestMapping(value = "/thirdFieldList")
	public String list(HttpServletRequest request, ModelMap modelMap){
		try {
			HttpSession session = request.getSession();
			System.out.println(session.getAttribute("permMap"));
			if(!PermissionUtil.isHavePermission(session, "/bim/thirdField/thirdFieldList")){
				return "/errorpage/permissionError";
			}		
		 
			return "/bim/thirdField/toDictionaryMeanu";
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
	@RequestMapping(value = "/thirdFieldEditUI/{id}/{dicCode}/{parentId}")
	public String editUI(@PathVariable("id") String id,@PathVariable("dicCode")  String dicCode,@PathVariable("parentId")  String parentId,HttpServletRequest request, ModelMap modelMap) {
		TThirdFieldMapper th = null;
		if(!StringUtil.isEmpty(id)){
			 
			th = tThirdService.selectThirdFieldByID(StringUtil.parseLong(id));
		}else{
			//权限验证
			 
			th = new TThirdFieldMapper();
		}
		if(null != th){
			Map<String, Object> parmas=new HashMap<String, Object>();
			parmas.put("dicCode", dicCode);
			parmas.put("parentId", parentId);
			th.setThirdPartyType(dicCode);
			th.setDicName(dictionaryService.getDicName(parmas));
			modelMap.put("thirdField", th);
		}
		return "bim/thirdField/thirdFieldEdit";
	}
	@RequestMapping(value = "/thirdFieldDelete/{ids}")
	@ResponseBody
	public String delete(@PathVariable("ids") String[] ids,HttpServletRequest request, ModelMap modelMap) {
			if(null != ids && ids.length>0){
				List<Integer> list = new ArrayList<Integer>();
				for(String infoIdStr : ids){
					Integer infoId = Integer.valueOf(infoIdStr);
					list.add(infoId);
				}
				tThirdService.batchDeleteInfo(list);
			}
		 
		return null;
		
	}
	
	/**
	 * 改变系统状态
	 */
	@RequestMapping(value = "/updateStatus/{id}/{status}")
	@ResponseBody
	public String updateIsActive(@PathVariable("id") String id,@PathVariable("status") String status,HttpServletRequest request, ModelMap modelMap) {
		TThirdFieldMapper ci = new TThirdFieldMapper();
		try{
			ci.setId(StringUtil.parseInteger(id));
			if(status.toString().equals("0")){
				ci.setStatus("1");
			}else if(status.toString().equals("1")){
				ci.setStatus("0");
			}
		
			tThirdService.updateStatus(ci);
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			 
		}
		return null;
		 
	}
	
	/**
	 * 保存银行数据信息
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/thirdFieldSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String thirdFieldSave(@ModelAttribute("thirdField")  TThirdFieldMapper thirdField,HttpServletRequest request, ModelMap modelMap) {	 
		try{
			 
			ConverNullString sr=new  ConverNullString();
			sr.nullConverNullString(thirdField);
			if(!StringUtil.isEmpty(thirdField.getCollectMaxMoney())){
				BigDecimal bd=new BigDecimal(thirdField.getCollectMaxMoney()); 
				thirdField.setCollectMaxMoney1(bd);
				 
			}else{
				BigDecimal bd=new BigDecimal("0"); 
				thirdField.setCollectMaxMoney1(bd);
			}
			if(!StringUtil.isEmpty(thirdField.getPayMaxMoney())){
				BigDecimal bd=new BigDecimal(thirdField.getPayMaxMoney()); 
				thirdField.setPayMaxMoney1(bd);
				 
			}else{
				BigDecimal bd=new BigDecimal("0"); 
				thirdField.setPayMaxMoney1(bd);
			}
			if(!StringUtil.isEmpty(thirdField.getQuickPayMaxMoney())){
				BigDecimal bd=new BigDecimal(thirdField.getQuickPayMaxMoney()); 
				thirdField.setQuickPayMaxMoney1(bd);
				 
			}else{
				BigDecimal bd=new BigDecimal("0"); 
				thirdField.setQuickPayMaxMoney1(bd);
			}
			System.out.println(thirdField.getThirdPartyType().length());
			 String response =  Validate.getInstance().validate(thirdField,
					com.zdmoney.manager.Validate.InsertCheck.class, Default.class);
			JSONObject jb=new JSONObject();
			if(!StringUtils.isBlank(response)){
				System.out.println("返回信息："+response);				 
				jb.put("valmsg", response);
				return jb.toString();
			}
			if(StringUtil.isEmpty(thirdField.getId())){
				
				TThirdFieldMapper params = new TThirdFieldMapper();
				params.setThirdFieldCode(thirdField.getThirdFieldCode());
				params.setThirdPartyType(thirdField.getThirdPartyType());
				params.setTppFieldCode(thirdField.getTppFieldCode());
				params.setFieldType(thirdField.getFieldType());
				List<TThirdFieldMapper> thirdFieldList = tThirdService.getThirdField(params);
				if (thirdFieldList != null && thirdFieldList.size() > 0) {
					params = thirdFieldList.get(0);
				} else {
					params = null;
				}
				if (params != null) {
					if (params.getId().equals(thirdField.getId())) {
						tThirdService.updateThirdField(thirdField);
					} else {
						jb.put("valmsg", "不能新增！该映射关系已存在！");
						return jb.toString();
					}
				} else {
					//新增
					thirdField.setStatus("1");
					tThirdService.insert(thirdField);
				}
			}else{
				//修改		
				log.info("修改+："+thirdField.getId());
				
				TThirdFieldMapper params = new TThirdFieldMapper();
				params.setThirdFieldCode(thirdField.getThirdFieldCode());
				params.setThirdPartyType(thirdField.getThirdPartyType());
				params.setTppFieldCode(thirdField.getTppFieldCode());
				params.setFieldType(thirdField.getFieldType());
				List<TThirdFieldMapper> thirdFieldList = tThirdService.getThirdField(params);
				if (thirdFieldList != null && thirdFieldList.size() > 0) {
					params = thirdFieldList.get(0);
				} else {
					params = null;
				}
				if (params != null) {
					if (params.getId().equals(thirdField.getId())) {
						tThirdService.updateThirdField(thirdField);
					} else {
						jb.put("valmsg", "不能修改！该映射关系已存在！");
						return jb.toString();
					}
				} else {
					tThirdService.updateThirdField(thirdField);
				}
				
				
				
			} 
			log.info("保存成功"); 
			return jb.toString();
		 
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
}
