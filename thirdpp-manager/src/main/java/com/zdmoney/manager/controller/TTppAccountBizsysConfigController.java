package com.zdmoney.manager.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdmoney.manager.Validate.InsertCheck;
import com.zdmoney.manager.Validate.Validate;
import com.zdmoney.manager.enumset.AccountBizTypeEnum;
import com.zdmoney.manager.enumset.AccountBizsysConfigStatusEnum;
import com.zdmoney.manager.enumset.AccountBizsysModeEnum;
import com.zdmoney.manager.enumset.AccountCurrencyUnitEnum;
import com.zdmoney.manager.enumset.CurrencyEnum;
import com.zdmoney.manager.exception.TppManagerException;
import com.zdmoney.manager.models.TDictionary;
import com.zdmoney.manager.models.TppAccountBizsysConfig;
import com.zdmoney.manager.service.TDictionaryService;
import com.zdmoney.manager.service.TTppAccountBizsysConfigService;
import com.zdmoney.manager.utils.DateUtil;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.PermissionUtil;
import com.zdmoney.manager.utils.StringUtil;
import com.zendaimoney.thirdpp.account.pub.service.IHandleAccountService;
import com.zendaimoney.thirdpp.account.pub.vo.AccountResponseVo;

@Controller
@RequestMapping("/account/bizsysConfig")
public class TTppAccountBizsysConfigController {
	
	private final Logger log = Logger.getLogger(TTppAccountBizsysConfigController.class);
	
	@Autowired
	private TTppAccountBizsysConfigService bizsysConfigService;
	
	@Autowired
	private TDictionaryService dictionaryService; 
	@Autowired
	private IHandleAccountService handleAccountService;
	/**
	 * 页面跳转
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/bizsysConfigList")
	public String list(HttpServletRequest request, ModelMap modelMap){
		try{
			//权限验证
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/account/bizsysConfig/bizsysConfigList")){
				return "/errorpage/permissionError";
			}
			return "/account/bizsysConfig/bizsysConfigList";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	/**
	 * 修改对账通道配置页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/handleAccountBizsysConfigPage")
	public String handleAccountChannelConfigPage(HttpServletRequest request, ModelMap modelMap){
		try{
			String configId = request.getParameter("configId");
			modelMap.put("configId", configId);
			return "/account/bizsysConfig/bizsysConfigHandleAccount";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	@RequestMapping(value = "/handleAccount")
	@ResponseBody
	public String handleAccount(HttpServletRequest request, ModelMap modelMap){
		JSONObject result = new JSONObject();
		String configId = request.getParameter("configId");
		String accountDay = request.getParameter("accountDay");
		try {
			if (StringUtils.isBlank(configId) || StringUtils.isBlank(accountDay)) {
				result.put("flag", false);
				result.put("msg", "业务系统手工对账请求参数不能为空");
				return result.toString();
			}
			accountDay = accountDay.replace("-", "");
			AccountResponseVo arv = handleAccountService.bizsysAccount("", configId, accountDay);
			if (AccountResponseVo.SUCCESS_RESPONSE_CODE.equals(arv.getCode())) {
				result.put("flag", true);
			} else {
				result.put("flag", false);
				result.put("msg", arv.getMsg());
			}
		} catch (Exception e){
			System.out.println(e.getMessage());
			log.error(e.getMessage(), e);
			result.put("flag", false);
			result.put("msg", "连接后台对账系统超时，请重试");
			return result.toString();
		}
		return result.toString();
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
			List<Map> rows = bizsysConfigService.select_tppAccountBizsysConfigList(params);
			int count = bizsysConfigService.select_tppAccountBizsysConfigList_count(params);

			// 2.遍历增加自定义内容
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				/*d.put("EDIT","<a href='javascript:return void(0);' onClick=\"tppAccountChannelConfig_list.updateFormSubmit('"+ d.get("ID")+ "');return false;\"><i class='icon-edit'></i></a>");
				d.put("DETAIL","<a href='javascript:return void(0);'onClick=\"tppAccountChannelConfig_list.detailFormSubmit('"+ d.get("ID")+ "');return false;\"><i class='icon-search'></i></a>");*/
				d.put("BIZSYS_ACCOUNT_MODE", d.get("BIZSYS_ACCOUNT_MODE") == null ? "" : AccountBizsysModeEnum.getEnumDesc(d.get("BIZSYS_ACCOUNT_MODE").toString()));
				d.put("STATUS", d.get("STATUS") == null ? "" : AccountBizsysConfigStatusEnum.getEnumDesc(d.get("STATUS").toString()));
				d.put("CURRENCY", d.get("CURRENCY") == null ? "" : CurrencyEnum.getEnumDesc(d.get("CURRENCY").toString()));
				d.put("CURRENCY_UNIT", d.get("CURRENCY_UNIT") == null ? "" : AccountCurrencyUnitEnum.getEnumDesc(d.get("CURRENCY_UNIT").toString()));
				d.put("ACCOUNT_TIME", d.get("ACCOUNT_TIME") == null ? "00:00:00" : DateUtil.stringToTime(d.get("ACCOUNT_TIME").toString()));
				
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
		params.put("SEARCH_BIZ_SYS_NO", request.getParameter("search_biz_sys_no"));
		params.put("SEARCH_BIZSYS_ACCOUNT_MODE", request.getParameter("search_bizsys_account_mode"));
		params.put("SEARCH_STATUS", request.getParameter("search_status"));
		String bizTypes = request.getParameter("search_biz_type");
		if (StringUtils.isNotBlank(bizTypes)) {
			List bizTypeList = Arrays.asList(bizTypes.split(","));
			params.put("SEARCH_BIZ_TYPE", bizTypeList);
		}
		
	}
	
	
	
	/**
	 * 新增业务系统对账配置页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/addBizsysConfigPage")
	public String addBizsysConfigPage(HttpServletRequest request, ModelMap modelMap){
		try{
			
			TDictionary dicParams = new TDictionary();
			dicParams.setDicType("12");//字典表类型12=对账文件属性定义
			List<TDictionary> dictionaryList = dictionaryService.select_DictionarySelective(dicParams);
			List<String> attrsDefinitionLeftList = new ArrayList<String>();
			for (TDictionary dictionary : dictionaryList) {
				attrsDefinitionLeftList.add(dictionary.getDicName());
			}
			modelMap.put("attrsDefinitionLeftList", attrsDefinitionLeftList);
			
			/*dicParams = new TDictionary();
			dicParams.setDicType("12");//字典表类型12=对账文件头属性定义
			dictionaryList = dictionaryService.select_DictionarySelective(dicParams);
			List<String> headerAttrsDefinitionLeftList = new ArrayList<String>();
			for (TDictionary dictionary : dictionaryList) {
				headerAttrsDefinitionLeftList.add(dictionary.getDicName());
			}
			modelMap.put("headerAttrsDefinitionLeftList", headerAttrsDefinitionLeftList);*/
			dicParams = new TDictionary();
			dicParams.setDicType("16");//字典表类型16=对账文件名称格式定义
			dictionaryList = dictionaryService.select_DictionarySelective(dicParams);
			List<String> fileNameFormatList = new ArrayList<String>();
			for (TDictionary dictionary : dictionaryList) {
				fileNameFormatList.add(dictionary.getDicName());
			}
			modelMap.put("fileNameFormatList", fileNameFormatList);
			
			return "/account/bizsysConfig/bizsysConfigAdd";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	@RequestMapping(value = "/addBizsysConfig")
	@ResponseBody
	public String addBizsysConfig(TppAccountBizsysConfig bizsysConfig, HttpServletRequest request, ModelMap modelMap){
		JSONObject result = new JSONObject();
		try{
			//默认参数设置
			bizsysConfig.setLocalAccountRootPath("/home/tpp/bizsys_account_temp");
			bizsysConfig.setFileSuffix("txt");
			bizsysConfig.setCurrency("0");
			bizsysConfig.setCurrencyUnit("0");
			//默认为下划线
			if (StringUtils.isBlank(bizsysConfig.getAttrsSplit())) {
				throw new TppManagerException("请选择文件内容分隔符！");
			} else {
				if (StringUtil.checkNumber(bizsysConfig.getAttrsSplit()) || StringUtil.existsStr(bizsysConfig.getAttrsSplit(), "/") || StringUtil.existsChar(bizsysConfig.getAttrsSplit())
						 || StringUtil.existsXie(bizsysConfig.getAttrsSplit())) {
					throw new TppManagerException("不能使用数字、字母或斜杠作为分隔符！");
				}
			}
			
			//文件属性
			String[] attrsDefinitions = request.getParameterValues("attrsDefinition");
			String attrsDefinition = "";
			if (attrsDefinitions != null && attrsDefinitions.length > 0) {
				attrsDefinition = StringUtils.join(attrsDefinitions, ",");
				attrsDefinition = attrsDefinition.replaceAll(",", bizsysConfig.getAttrsSplit());
			}
			/*//文件头属性
			String[] headerAttrsDefinitions = request.getParameterValues("headerAttrsDefinition");
			String headerAttrsDefinition = "";
			if (headerAttrsDefinitions != null && headerAttrsDefinitions.length > 0) {
				headerAttrsDefinition = StringUtils.join(headerAttrsDefinitions, ",");
				headerAttrsDefinition = headerAttrsDefinition.replaceAll(",", bizsysConfig.getAttrsSplit());
			}*/
			//对账文件名称格式
			String[] fileNameFormats = request.getParameterValues("fileNameFormat");
			String fileNameFormat = "";
			if (fileNameFormats != null && fileNameFormats.length > 0) {
				fileNameFormat = StringUtils.join(fileNameFormats, ",");
				fileNameFormat = fileNameFormat.replaceAll(",", "_");
			}
			
			bizsysConfig.setFileNameFormat(fileNameFormat);
			/*bizsysConfig.setHeaderAttrsDefinition(headerAttrsDefinition);*/
			bizsysConfig.setAttrsDefinition(attrsDefinition);
			
			String msg = Validate.getInstance().validate(bizsysConfig,
					InsertCheck.class, Default.class);
			if (StringUtils.isNotBlank(msg)) {
				throw new TppManagerException(msg);
			}
			
			//校验业务系统配置是否存在
			String bizsysNo = bizsysConfig.getBizSysNo();
			String[] bizTypes = bizsysConfig.getBizType().split(",");
			List<String> bizTypeList = Arrays.asList(bizTypes);
			for (String bizType : bizTypeList) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("SEARCH_BIZ_SYS_NO", bizsysNo);
				params.put("SEARCH_BIZ_TYPE", bizType);
				List<Map> rows = bizsysConfigService.select_tppAccountBizsysConfigSelective(params);
				if (rows != null && rows.size() > 0) {
					throw new TppManagerException("该业务系统配置已存在!");
				}
			}
			
			bizsysConfig.setBizType(bizsysConfig.getBizType().replaceAll(",", "_"));
			bizsysConfig.setAccountTime(bizsysConfig.getAccountTime().replaceAll(":", ""));
			bizsysConfigService.insert(bizsysConfig);
			result.put("flag", true);
		} catch(TppManagerException e){
			result.put("msg", e.getMessage());
			result.put("flag", false);
		} catch(Exception e){
			result.put("msg", "新增配置失败");
			result.put("flag", false);
		}
		return result.toString();
	}
	
	/**
	 * 修改业务系统对账配置页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/editBizsysConfigPage")
	public String editBizsysConfigPage(HttpServletRequest request, ModelMap modelMap){
		try{
			String configId = request.getParameter("configId");
			TppAccountBizsysConfig bizsysConfig = bizsysConfigService.selectByPrimaryKey(StringUtil.parseLong(configId));
			
			String bizTypes = bizsysConfig.getBizType();
			if (StringUtils.isNotBlank(bizTypes)) {
				modelMap.put("bizTypes", bizTypes);
			}
			bizsysConfig.setAccountTime(DateUtil.stringToTime(bizsysConfig.getAccountTime()));

			//默认分隔符
			String split = "";
			if (StringUtils.isBlank(bizsysConfig.getAttrsSplit())) {
				split = "_";
			} else {
				split = bizsysConfig.getAttrsSplit();
				if (split.equals("|")) {
					split = "\\|";
				}
			}
			//文件属性定义
			List<String> attrsDefinitionList = new ArrayList<String>();
			if (StringUtils.isNotBlank(bizsysConfig.getAttrsDefinition())) {
				attrsDefinitionList = Arrays.asList(bizsysConfig.getAttrsDefinition().split(split));
			}
			TDictionary dicParams = new TDictionary();
			dicParams.setDicType("12");//字典表类型12=对账文件属性定义
			List<TDictionary> dictionaryList = dictionaryService.select_DictionarySelective(dicParams);
			List<String> attrsDefinitionLeftList = new ArrayList<String>();
			for (TDictionary dictionary : dictionaryList) {
				Boolean flag = false;
				for (String attrsDefinition : attrsDefinitionList) {
					if (attrsDefinition.equals(dictionary.getDicName())) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					attrsDefinitionLeftList.add(dictionary.getDicName());
				}
			}
			modelMap.put("attrsDefinitionRightList", attrsDefinitionList);
			modelMap.put("attrsDefinitionLeftList", attrsDefinitionLeftList);
			
			/*//文件头属性定义
			List<String> headerAttrsDefinitionList = new ArrayList<String>();
			if (StringUtils.isNotBlank(bizsysConfig.getHeaderAttrsDefinition())) {
				headerAttrsDefinitionList = Arrays.asList(bizsysConfig.getHeaderAttrsDefinition().split(split));
			}
			dicParams = new TDictionary();
			dicParams.setDicType("12");//字典表类型12=对账文件头属性定义
			dictionaryList = dictionaryService.select_DictionarySelective(dicParams);
			List<String> headerAttrsDefinitionLeftList = new ArrayList<String>();
			for (TDictionary dictionary : dictionaryList) {
				Boolean flag = false;
				for (String headerAttrsDefinition : headerAttrsDefinitionList) {
					if (headerAttrsDefinition.equals(dictionary.getDicName())) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					headerAttrsDefinitionLeftList.add(dictionary.getDicName());
				}
			}
			modelMap.put("headerAttrsDefinitionRightList", headerAttrsDefinitionList);
			modelMap.put("headerAttrsDefinitionLeftList", headerAttrsDefinitionLeftList);*/
			
			//对账文件名称格式
			List<String> fileNameFormatList = new ArrayList<String>();
			if (StringUtils.isNotBlank(bizsysConfig.getFileNameFormat())) {
				fileNameFormatList = Arrays.asList(bizsysConfig.getFileNameFormat().split("_"));
			}
			dicParams = new TDictionary();
			dicParams.setDicType("16");//字典表类型16=对账文件名称格式定义
			dictionaryList = dictionaryService.select_DictionarySelective(dicParams);
			List<String> fileNameFormatLeftList = new ArrayList<String>();
			for (TDictionary dictionary : dictionaryList) {
				Boolean flag = false;
				for (String fileNameFormat : fileNameFormatList) {
					if (fileNameFormat.equals(dictionary.getDicName())) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					fileNameFormatLeftList.add(dictionary.getDicName());
				}
			}
			modelMap.put("fileNameFormatRightList", fileNameFormatList);
			modelMap.put("fileNameFormatLeftList", fileNameFormatLeftList);
			bizsysConfig.setAttrsSplit(StringUtil.htmlEscape(bizsysConfig.getAttrsSplit()));//特殊字符转义
			modelMap.put("bizsysConfig", bizsysConfig);
			return "/account/bizsysConfig/bizsysConfigEdit";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	/**
	 * 修改业务系统对账配置页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/editBizsysConfig")
	@ResponseBody
	public String editBizsysConfig(TppAccountBizsysConfig bizsysConfig, HttpServletRequest request, ModelMap modelMap){
		JSONObject result = new JSONObject();
		try{
			//默认参数设置
			bizsysConfig.setLocalAccountRootPath("/home/tpp/bizsys_account_temp");
			bizsysConfig.setFileSuffix("txt");
			bizsysConfig.setCurrency("0");
			bizsysConfig.setCurrencyUnit("0");
			String msg = Validate.getInstance().validate(bizsysConfig,
					InsertCheck.class, Default.class);
			if (StringUtils.isNotBlank(msg)) {
				throw new TppManagerException(msg);
			}
			//查询是否已经存在该配置
			String bizSysNo = bizsysConfig.getBizSysNo();
			Long id = bizsysConfig.getId();
			String[] bizTypes = bizsysConfig.getBizType().split(",");
			List<String> bizTypeList = Arrays.asList(bizTypes);
			for (String bizType : bizTypeList) {
				HashMap params = new HashMap();
				params.put("SEARCH_BIZ_SYS_NO", bizSysNo);
				params.put("SEARCH_ID", id);
				params.put("SEARCH_BIZ_TYPE", bizType);
				List<Map> rows = bizsysConfigService.select_tppAccountBizsysConfigForUpdateSelective(params);
				if (rows != null && rows.size() > 0 ) {
					throw new TppManagerException("该业务对账配置已存在！");
				}
			}
			
			String attrSplit = "";
			if (StringUtils.isBlank(bizsysConfig.getAttrsSplit())) {
				throw new TppManagerException("请选择文件内容分隔符！");
			} else {
				if (StringUtil.checkNumber(bizsysConfig.getAttrsSplit()) || StringUtil.existsStr(bizsysConfig.getAttrsSplit(), "/") || StringUtil.existsChar(bizsysConfig.getAttrsSplit())
						 || StringUtil.existsXie(bizsysConfig.getAttrsSplit())) {
					throw new TppManagerException("不能使用数字、字母或斜杠作为分隔符！");
				}
				attrSplit = bizsysConfig.getAttrsSplit();
			}
			//文件属性
			String[] attrsDefinitions=request.getParameterValues("attrsDefinition");
			String attrsDefinition = "";
			if (attrsDefinitions!= null && attrsDefinitions.length > 0) {
				attrsDefinition = StringUtils.join(attrsDefinitions, ",");
				attrsDefinition = attrsDefinition.replaceAll(",", attrSplit);
			}
			bizsysConfig.setAttrsDefinition(attrsDefinition);
			/*//文件头属性
			String[] headerAttrsDefinitions=request.getParameterValues("headerAttrsDefinition");
			String headerAttrsDefinition = "";
			if (headerAttrsDefinitions != null && headerAttrsDefinitions.length > 0) {
				headerAttrsDefinition = StringUtils.join(headerAttrsDefinitions, ",");
				headerAttrsDefinition = headerAttrsDefinition.replaceAll(",", attrSplit);
			}
			bizsysConfig.setHeaderAttrsDefinition(headerAttrsDefinition);*/
			
			//对账文件名称格式
			String[] fileNameFormats=request.getParameterValues("fileNameFormat");
			String fileNameFormat = "";
			if (fileNameFormats != null && fileNameFormats.length > 0) {
				fileNameFormat = StringUtils.join(fileNameFormats, ",");
				fileNameFormat = fileNameFormat.replaceAll(",", "_");
			}
			bizsysConfig.setFileNameFormat(fileNameFormat);
			
			bizsysConfig.setBizType(bizsysConfig.getBizType().replaceAll(",", "_"));
			bizsysConfig.setAccountTime(bizsysConfig.getAccountTime().replaceAll(":", ""));
			bizsysConfigService.updateByPrimaryKey(bizsysConfig);
			result.put("flag", true);
		} catch(TppManagerException e){
			result.put("msg", e.getMessage());
			result.put("flag", false);
		} catch(Exception e){
			result.put("msg", "修改配置失败");
			result.put("flag", false);
		}
		return result.toString();
	}
	
	@RequestMapping(value = "/deleteBizsysConfig",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deleteBizsysConfig(HttpServletRequest request, ModelMap modelMap){
		String bizsysConfigIds = request.getParameter("bizsysConfigIds");
		JSONObject result = new JSONObject();
		try {
			if (StringUtils.isBlank(bizsysConfigIds)) {
				throw new TppManagerException("ID为空");
			}
			List<Long> list = new ArrayList<Long>();
			String[] ids = bizsysConfigIds.split(",");
			for (String id : ids) {
				list.add(StringUtil.parseLong(id));
			}
			//批量删除权限
			bizsysConfigService.batchDelete(list);  
			result.put("flag", true);
		} catch (TppManagerException e) {
			result.put("flag", false);
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("flag", false);
			result.put("msg", "删除失败");
		}
		return result.toString();
	}
	
}
