package com.zdmoney.manager.controller;

import java.util.ArrayList;
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
import com.zdmoney.manager.models.TDictionary;
import com.zdmoney.manager.service.TDictionaryService;
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
@RequestMapping(value = "/bim/dictionary")
public class TDictionaryController {
	private final Logger log = Logger.getLogger(TSymPermissionController.class);
	@Autowired
	private TDictionaryService dictionaryService; 
	@Autowired
	private Page<TDictionary> page;
	@RequestMapping(value = "/dictionaryListData/{parentId}")
	@ResponseBody
	public String listData(@PathVariable("parentId") String parentId,HttpServletRequest request, ModelMap modelMap){
		
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
			params.put("dicCode", request.getParameter("dicCode"));
			params.put("dicName", request.getParameter("sedicName"));
			/*if(dicType.equals("noParent")){
				dicType=request.getParameter("sedicType");
			}
			params.put("dicType",dicType);*/
			params.put("parentId", parentId);
		 
			// 2.遍历增加及转换自定义内容
			List<Map> rows =dictionaryService.getDictionaryList(params);
			int count = dictionaryService.getDictionaryListCount(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				if(!StringUtil.isEmpty(d.get("DIC_TYPE"))){
				if(d.get("DIC_TYPE").equals("0")){d.put("DIC_TYPE","币种");}
				else if(d.get("DIC_TYPE").equals("1")){d.put("DIC_TYPE","银行卡类型");}
				else if(d.get("DIC_TYPE").equals("2")){d.put("DIC_TYPE","交易状态");}
				else if(d.get("DIC_TYPE").equals("3")){d.put("DIC_TYPE","接入支付平台");}
				else if(d.get("DIC_TYPE").equals("4")){d.put("DIC_TYPE","业务类型");}
				else if(d.get("DIC_TYPE").equals("5")){d.put("DIC_TYPE","证件类型");}
				else if(d.get("DIC_TYPE").equals("6")){d.put("DIC_TYPE","商户号类型");};
				}
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
				 
				d.put("update","<a href='javascript:return void(0);' onClick=\"updateDictionary('"+ d.get("ID")+ "');\"><i class='icon-edit'></i></a>");
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
	@RequestMapping(value = "/toDictionaryList/{id}")
	public String toListData(@PathVariable("id") String id,HttpServletRequest request, ModelMap modelMap){		 
		modelMap.put("id", id);		
		return "/bim/dictionary/dictionaryList";
	}
	@RequestMapping(value = "/dictionaryDicType/{dicType}")
	@ResponseBody
	public String dicTypelList(@PathVariable("dicType") String dicType,HttpServletRequest request, ModelMap modelMap){
		try {
			 
			List<Map> rows =dictionaryService.getDicTypeList(dicType);
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
	@RequestMapping(value = "/dictionaryList")
	public String list(HttpServletRequest request, ModelMap modelMap){
		try {
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/bim/dictionary/dictionaryList")){
				return "/errorpage/permissionError";
			}
		/*	Map<String,Object> params = new HashMap<String,Object>();
			//分页
			int pageNo = StringUtil.parseInteger(request.getParameter("pageNo"), 1);
			int pageSize = StringUtil.parseInteger(request.getParameter("pageSize"), 15);
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			//查询条件
			//封装查询条件
			String dicCode = request.getParameter("dicCode"); 
			String dicName = request.getParameter("dicName"); 
			String dicType = request.getParameter("dicType"); 
			String parentId = request.getParameter("parentId"); 
			String beginTime = request.getParameter("beginTime");
			String endTime = request.getParameter("endTime");
			if(!StringUtil.isEmpty(dicCode)){
				params.put("dicCode", dicCode);
				modelMap.put("dicCode", dicCode);
			}
			if(!StringUtil.isEmpty(dicName)){
				params.put("dicName", dicName);
				modelMap.put("dicName", dicName);
			}
			if(!StringUtil.isEmpty(dicType)){
				params.put("dicType", dicType);
				modelMap.put("dicType", dicType);
			}
			if(!StringUtil.isEmpty(parentId)){
				params.put("parentId", parentId);
				modelMap.put("parentId", parentId);
			}
			if(!StringUtil.isEmpty(beginTime)){
				modelMap.put("beginTime", beginTime);
				beginTime=beginTime.replaceAll("-", "");
				params.put("beginTime", beginTime);
			
			}
			if(!StringUtil.isEmpty(endTime)){
				modelMap.put("endTime", endTime);
				endTime=endTime.replaceAll("-", "");
				params.put("endTime", endTime);
			
			}
			params.put("page", page);			
			//查询银行信息列表
			List<TDictionary> dictionaryList=dictionaryService.getDictionaryList(params);
			Map<String,Object> param = new HashMap<String,Object>();
				page.setResults(dictionaryList);	
			param.put("parentId", "0");*/
			/*List<TDictionary> dList=dictionaryService.getDictionaryParentList();
			modelMap.put("dictionaryList", dList);*/
			return "/bim/dictionary/toDictionaryMeanu";
			/*return "/bim/dictionary/dictionaryList";*/
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
	@RequestMapping(value = "/dictionaryEditUI/{id}/{isParent}/{parentId}")
	public String editUI(@PathVariable("id") String id,@PathVariable("isParent") String isParent,@PathVariable("parentId") String parentId,HttpServletRequest request, ModelMap modelMap){
		TDictionary td = null;
		if(!StringUtil.isEmpty(id)){
			//权限验证
			 
		 
			td = dictionaryService.selectDictionaryByID(StringUtil.parseLong(id));
			td.setNoteNo(td.getDicCode());
			if(null != td){
				List<TDictionary> dList=dictionaryService.getDictionaryParentList();
				modelMap.put("dictionary", td);
				modelMap.put("dictionaryList", dList);
			}
			return "bim/dictionary/dictionaryEdit";
		}else{
		 
		  
			td = new TDictionary();
			if(!StringUtil.isEmpty(parentId)){
				td.setParentId(parentId);
				 
			}
			if(null != td){
				List<TDictionary> dList=dictionaryService.getDictionaryParentList();
				modelMap.put("dictionary", td);
				modelMap.put("dictionaryList", dList);
			}
			return "bim/dictionary/dictionaryAdd";
			
		}
		
	
	/*	if(isParent.equals("0")){
			return "bim/dictionary/dictionaryParentEdit";
		}else{
			return "bim/dictionary/dictionaryEdit";
		}*/
		
	}
	
	/**
	 * 保存银行数据信息
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/dictionarySave/{isParent}",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String dictionarySave(@ModelAttribute("dictionarySave")  TDictionary dictionary,@PathVariable("isParent") String isParent,HttpServletRequest request, ModelMap modelMap) {
		 
		try{
			//权限验证
			HttpSession session = request.getSession();
			JSONObject jb=new JSONObject();
		 
			JSONObject jb1=new JSONObject();
			ConverNullString sr=new  ConverNullString();
			sr.nullConverNullString(dictionary);
			String response =  Validate.getInstance().validate(dictionary,
					com.zdmoney.manager.Validate.InsertCheck.class, Default.class);
			if(StringUtil.isEmpty(dictionary.getParentId())){
				dictionary.setParentId("0");
			}
			int count=dictionaryService.getdicCodeCount(dictionary);
		
			if(!StringUtils.isBlank(response)){
				System.out.println("返回信息："+response);				 
				jb1.put("valmsg", response);
				return jb1.toString();
			}
			if(count>0){
				if(StringUtil.isEmpty(dictionary.getId())){
					response="编码已存在";
					jb1.put("valmsg",response);
					return jb1.toString();
				}else{
					if(!dictionary.getDicCode().equals(dictionary.getNoteNo())){
						response="编码已存在";
						jb1.put("valmsg",response);
						return jb1.toString();
					}
				}	
					
				}	 
			if(StringUtil.isEmpty(dictionary.getId())){
				//新增	
				log.info("新增+："+dictionary.getId());
				if(isParent.equals("0")){
					dictionary.setDicType(dictionary.getDicCode());
				}else{
					TDictionary t= 	dictionaryService.selectDictionaryByID(StringUtil.parseLong(dictionary.getParentId()));
					dictionary.setDicType(t.getDicType());
				}
				dictionaryService.insert(dictionary);
			}else{
				//修改		
				log.info("修改+："+dictionary.getId());
				dictionaryService.updateDictionary(dictionary);
			} 
			log.info("保存成功");
			return jb1.toString();
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	@RequestMapping(value = "/dictionaryDelete/{ids}")
	@ResponseBody
	public String delete(@PathVariable("ids") String[] ids,HttpServletRequest request, ModelMap modelMap) {
		
		 
			if(null != ids && ids.length>0){
				List<Integer> list = new ArrayList<Integer>();
				for(String infoIdStr : ids){
					Integer infoId = Integer.valueOf(infoIdStr);
					list.add(infoId);
				}
				dictionaryService.batchDeleteInfo(list);
			}
		 
		return null;
		
	}
	
}
