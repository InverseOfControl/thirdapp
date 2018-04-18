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
import com.zdmoney.manager.enumset.AccountChannelStatusEnum;
import com.zdmoney.manager.enumset.AccountCurrencyUnitEnum;
import com.zdmoney.manager.enumset.AccountFetchMethodEnum;
import com.zdmoney.manager.enumset.AccountFetchTypeEnum;
import com.zdmoney.manager.enumset.CurrencyEnum;
import com.zdmoney.manager.enumset.ThirdType;
import com.zdmoney.manager.exception.TppManagerException;
import com.zdmoney.manager.models.TDictionary;
import com.zdmoney.manager.models.TppAccountChannelConfig;
import com.zdmoney.manager.service.TDictionaryService;
import com.zdmoney.manager.service.TTppAccountChannelConfigService;
import com.zdmoney.manager.utils.DateUtil;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.PermissionUtil;
import com.zdmoney.manager.utils.StringUtil;
import com.zendaimoney.thirdpp.account.pub.service.IHandleAccountService;
import com.zendaimoney.thirdpp.account.pub.vo.AccountResponseVo;

 /**
 * 类功能:自动代码生成模板查询   action 模板
 * <p>创建者:</p>
 * <p>创建时间:</p>
 * <p>修改者:</p>
 * <p>修改时间:</p>
 * <p>修改原因：</p>
 * <p>审核者:</p>
 * <p>审核时间:</p>
 * <p>审核意见：</p>
 */
 
@Controller
@RequestMapping("/account/channelConfig")
public class TTppAccountChannelConfigController {
	
	private final Logger log = Logger.getLogger(TTppAccountChannelConfigController.class);

	@Autowired
	private TTppAccountChannelConfigService channelConfigService;
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
	@RequestMapping(value = "/channelConfigList")
	public String list(HttpServletRequest request, ModelMap modelMap){
		try{
			//权限验证
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/account/channelConfig/channelConfigList")){
				return "/errorpage/permissionError";
			}
			return "/account/channelConfig/channelConfigList";
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
			List<Map> rows = channelConfigService.select_tppAccountChannelConfigList(params);
			int count = channelConfigService.select_tppAccountChannelConfigList_count(params);
			

			// 2.遍历增加自定义内容
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				/*d.put("EDIT","<a href='javascript:return void(0);' onClick=\"tppAccountChannelConfig_list.updateFormSubmit('"+ d.get("ID")+ "');return false;\"><i class='icon-edit'></i></a>");*/
				d.put("VIEW_DETAIL","<a href='javascript:return void(0);'onClick=\"viewDetail('"+ d.get("ID")+ "');return false;\"><i class='icon-search'></i></a>");
				d.put("FETCH_METHOD", d.get("FETCH_METHOD") == null ? "" : AccountFetchMethodEnum.getEnumDesc(d.get("FETCH_METHOD").toString()));
				d.put("FETCH_TYPE", d.get("FETCH_TYPE") == null ? "" : AccountFetchTypeEnum.getEnumDesc(d.get("FETCH_TYPE").toString()));
				d.put("CURRENCY", d.get("CURRENCY") == null ? "" : CurrencyEnum.getEnumDesc(d.get("CURRENCY").toString()));
				d.put("CURRENCY_UNIT", d.get("CURRENCY_UNIT") == null ? "" : AccountCurrencyUnitEnum.getEnumDesc(d.get("CURRENCY_UNIT").toString()));
				d.put("STATUS", d.get("STATUS") == null ? "" : AccountChannelStatusEnum.getEnumDesc(d.get("STATUS").toString()));
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
		params.put("SEARCH_CHANNEL_NAME", request.getParameter("search_channel_name"));
		params.put("SEARCH_THIRD_TYPE_NO", request.getParameter("search_third_type_no"));
		params.put("SEARCH_MERCHANT_NO", request.getParameter("search_merchant_no"));
		params.put("SEARCH_STATUS", request.getParameter("search_status"));
		params.put("SEARCH_APP_NAME", request.getParameter("search_app_name"));
		String bizTypes = request.getParameter("search_biz_type");
		if (StringUtils.isNotBlank(bizTypes)) {
			List bizTypeList = Arrays.asList(bizTypes.split(","));
			params.put("SEARCH_BIZ_TYPE", bizTypeList);
		}
		
	}
	
	
	/**
	 * 新增对账通道配置页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/addChannelConfigPage")
	public String addChannelConfigPage(HttpServletRequest request, ModelMap modelMap){
		try{
			TDictionary dicParams = new TDictionary();
			dicParams.setDicType("13");//字典表类型13=第三方对账文件属性定义
			List<TDictionary> dictionaryList = dictionaryService.select_DictionarySelective(dicParams);
			List<String> attrsDefinitionLeftList = new ArrayList<String>();
			for (TDictionary dictionary : dictionaryList) {
				attrsDefinitionLeftList.add(dictionary.getDicName());
			}
			modelMap.put("attrsDefinitionLeftList", attrsDefinitionLeftList);
			
			/*dicParams = new TDictionary();
			dicParams.setDicType("14");//字典表类型14=第三方对账文件头属性定义
			dictionaryList = dictionaryService.select_DictionarySelective(dicParams);
			List<String> headerAttrsDefinitionLeftList = new ArrayList<String>();
			for (TDictionary dictionary : dictionaryList) {
				headerAttrsDefinitionLeftList.add(dictionary.getDicName());
			}
			modelMap.put("headerAttrsDefinitionLeftList", headerAttrsDefinitionLeftList);*/
			
			return "/account/channelConfig/channelConfigAdd";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	@RequestMapping(value = "/addChannelConfig")
	@ResponseBody
	public String addChannelConfig(TppAccountChannelConfig channelConfig, HttpServletRequest request, ModelMap modelMap){
		JSONObject result = new JSONObject();
		try{
			//检查分隔符，默认为下划线
			if (channelConfig.getAttrSplit().equals("1_1")) {
				channelConfig.setAttrSplit("_");
			} else if (channelConfig.getAttrSplit().equals("1%1")){
				channelConfig.setAttrSplit("%");
			} else {
				if (StringUtil.checkNumber(channelConfig.getAttrSplit()) || StringUtil.existsStr(channelConfig.getAttrSplit(), "/") || StringUtil.existsChar(channelConfig.getAttrSplit())
						 || StringUtil.existsXie(channelConfig.getAttrSplit())) {
					throw new TppManagerException("不能使用数字或斜杠作为文件内容分隔符！");
				}
			}
			//汇总信息所在行格式检查
			if (StringUtils.isNotBlank(channelConfig.getFileHeaderAttrsIndex()) && !StringUtil.checkNumber(channelConfig.getFileHeaderAttrsIndex())) {
				if (StringUtil.existsStr(channelConfig.getFileHeaderAttrsIndex(), "_")) {
					String[] fileHeaderAttrsIndexs = channelConfig.getFileHeaderAttrsIndex().split("_");
					if (fileHeaderAttrsIndexs.length != 2) {
						throw new TppManagerException("请输入正确的汇总信息所在行格式！");
					}
					if (!StringUtil.checkNumber(fileHeaderAttrsIndexs[0]) || !StringUtil.checkNumber(fileHeaderAttrsIndexs[1])) {
						throw new TppManagerException("请输入正确的汇总信息所在行格式！");
					}
				} else {
					throw new TppManagerException("请输入正确的汇总信息所在行格式！");
				}
			}
			//文件属性
			String[] attrsDefinitions = request.getParameterValues("attrsDefinition");
			String attrsDefinition = "";
			if (attrsDefinitions != null && attrsDefinitions.length > 0) {
				attrsDefinition = StringUtils.join(attrsDefinitions, ",");
				attrsDefinition = attrsDefinition.replaceAll(",", "_");
			}
			channelConfig.setAttrsDefinition(attrsDefinition);
			
			/*//文件头属性
			String headerAttrsDefinitions = request.getParameter("headerAttrsDefinition");
			channelConfig.setFileHeaderAttrsIndex(headerAttrsDefinitions);*/
			
			//设置第三方支付平台名称
			channelConfig.setChannelName(ThirdType.getEnumDesc(request.getParameter("thirdTypeNo")));
			//常规项检查
			String msg = Validate.getInstance().validate(channelConfig,
					InsertCheck.class, Default.class);
			if (StringUtils.isNotBlank(msg)) {
				throw new TppManagerException(msg);
			}	
			//查询是否已经存在该配置
			String thirdTypeNo = channelConfig.getThirdTypeNo();
			String merchantNo = channelConfig.getMerchantNo();
			String[] bizTypes = channelConfig.getBizType().split(",");
			List<String> bizTypeList = Arrays.asList(bizTypes);
			for (String bizType : bizTypeList) {
				HashMap params = new HashMap();
				params.put("SEARCH_THIRD_TYPE_NO", thirdTypeNo);
				params.put("SEARCH_MERCHANT_NO", merchantNo);
				params.put("SEARCH_BIZ_TYPE", bizType);
				List<Map> rows = channelConfigService.select_tppAccountChannelConfigSelective(params);
				if (rows != null && rows.size() > 0 ) {
					throw new TppManagerException("该通道对账配置已存在！");
				}
			}
			
			
			channelConfig.setBizType(channelConfig.getBizType().replaceAll(",", "_"));
			channelConfig.setAccountTime(channelConfig.getAccountTime().replaceAll(":", ""));
			channelConfigService.insert(channelConfig);
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
	 * 修改对账通道配置页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/editChannelConfigPage")
	public String editChannelConfigPage(HttpServletRequest request, ModelMap modelMap){
		try{
			String configId = request.getParameter("configId");
			TppAccountChannelConfig channelConfig = channelConfigService.selectByPrimaryKey(StringUtil.parseLong(configId));
			channelConfig.setAccountTime(DateUtil.stringToTime(channelConfig.getAccountTime()));
			String bizTypes = channelConfig.getBizType();
			if (StringUtils.isNotBlank(bizTypes)) {
				modelMap.put("bizTypes", bizTypes);
			}
			/*String split = "";
			if (StringUtils.isBlank(channelConfig.getAttrSplit())) {
				split = "_";
			} else {
				split = channelConfig.getAttrSplit();
				if (split.equals("|")) {
					split = "\\|";
				}
			}*/
			//文件属性定义
			List<String> attrsDefinitionList = new ArrayList<String>();
			if (StringUtils.isNotBlank(channelConfig.getAttrsDefinition())) {
				
				attrsDefinitionList = Arrays.asList(channelConfig.getAttrsDefinition().split("_"));
			}
			TDictionary dicParams = new TDictionary();
			dicParams.setDicType("13");//字典表类型13=第三方对账文件属性定义
			List<TDictionary> dictionaryList = dictionaryService.select_DictionarySelective(dicParams);
			List<String> attrsDefinitionLeftList = new ArrayList<String>();
			for (TDictionary dictionary : dictionaryList) {
				Boolean flag = false;
				for (String attrsDefinition : attrsDefinitionList) {
					String value = attrsDefinition.split("/")[1];
					if (value.equals(dictionary.getDicName())) {
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
			
			//文件头属性定义
			/*List<String> headerAttrsDefinitionList = new ArrayList<String>();
			if (StringUtils.isNotBlank(channelConfig.getHeaderAttrsDefinition())) {
				headerAttrsDefinitionList = Arrays.asList(channelConfig.getHeaderAttrsDefinition().split(split));
			}
			dicParams = new TDictionary();
			dicParams.setDicType("14");//字典表类型12=对账文件头属性定义
			dictionaryList = dictionaryService.select_DictionarySelective(dicParams);
			List<String> headerAttrsDefinitionLeftList = new ArrayList<String>();
			for (TDictionary dictionary : dictionaryList) {
				Boolean flag = false;
				for (String headerAttrsDefinition : headerAttrsDefinitionList) {
					String value = headerAttrsDefinition.split("/")[1];
					if (value.equals(dictionary.getDicName())) {
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
			channelConfig.setAttrSplit(StringUtil.htmlEscape(channelConfig.getAttrSplit()));//特殊字符转义
			modelMap.put("channelConfig", channelConfig);
			return "/account/channelConfig/channelConfigEdit";
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
	@RequestMapping(value = "/handleAccountChannelConfigPage")
	public String handleAccountChannelConfigPage(HttpServletRequest request, ModelMap modelMap){
		try{
			String configId = request.getParameter("configId");
			modelMap.put("configId", configId);
			return "/account/channelConfig/channelConfigHandleAccount";
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
				result.put("msg", "通道手工对账请求参数不能为空");
				return result.toString();
			}
			accountDay = accountDay.replace("-", "");
			AccountResponseVo arv = handleAccountService.channelAccount("", configId, accountDay);
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
	
	@RequestMapping(value = "/editChannelConfig")
	@ResponseBody
	public String editChannelConfig(TppAccountChannelConfig channelConfig, HttpServletRequest request, ModelMap modelMap){
		JSONObject result = new JSONObject();
		try{
			//设置第三方支付平台名称
			channelConfig.setChannelName(ThirdType.getEnumDesc(request.getParameter("thirdTypeNo")));
			//汇总信息所在行格式检查
			if (StringUtils.isNotBlank(channelConfig.getFileHeaderAttrsIndex()) && !StringUtil.checkNumber(channelConfig.getFileHeaderAttrsIndex())) {
				if (StringUtil.existsStr(channelConfig.getFileHeaderAttrsIndex(), "_")) {
					String[] fileHeaderAttrsIndexs = channelConfig.getFileHeaderAttrsIndex().split("_");
					if (fileHeaderAttrsIndexs.length != 2) {
						throw new TppManagerException("请输入正确的汇总信息所在行格式！");
					}
					if (!StringUtil.checkNumber(fileHeaderAttrsIndexs[0]) || !StringUtil.checkNumber(fileHeaderAttrsIndexs[1])) {
						throw new TppManagerException("请输入正确的汇总信息所在行格式！");
					}
				} else {
					throw new TppManagerException("请输入正确的汇总信息所在行格式！");
				}
			}
			if (channelConfig.getAttrSplit().equals("1_1")) {
				channelConfig.setAttrSplit("_");
			} else if (channelConfig.getAttrSplit().equals("1%1")){
				channelConfig.setAttrSplit("%");
			} else {
				if (StringUtil.checkNumber(channelConfig.getAttrSplit()) || StringUtil.existsStr(channelConfig.getAttrSplit(), "/") || StringUtil.existsChar(channelConfig.getAttrSplit())
						 || StringUtil.existsXie(channelConfig.getAttrSplit())) {
					throw new TppManagerException("不能使用数字或斜杠作为文件内容分隔符！");
				}
			}
			String msg = Validate.getInstance().validate(channelConfig,
					InsertCheck.class, Default.class);
			if (StringUtils.isNotBlank(msg)) {
				throw new TppManagerException(msg);
			}
			//查询是否已经存在该配置
			String thirdTypeNo = channelConfig.getThirdTypeNo();
			String merchantNo = channelConfig.getMerchantNo();
			Long id = channelConfig.getId();
			String[] bizTypes = channelConfig.getBizType().split(",");
			List<String> bizTypeList = Arrays.asList(bizTypes);
			for (String bizType : bizTypeList) {
				HashMap params = new HashMap();
				params.put("SEARCH_THIRD_TYPE_NO", thirdTypeNo);
				params.put("SEARCH_MERCHANT_NO", merchantNo);
				params.put("SEARCH_BIZ_TYPE", bizType);
				params.put("SEARCH_ID", id);
				List<Map> rows = channelConfigService.select_tppAccountChannelConfigForUpdateSelective(params);
				if (rows != null && rows.size() > 0 ) {
					throw new TppManagerException("该通道对账配置已存在！");
				}
			}
			//文件属性
			String[] attrsDefinitions=request.getParameterValues("attrsDefinition");
			String attrsDefinition = "";
			
			if (attrsDefinitions!= null && attrsDefinitions.length > 0) {
				attrsDefinition = StringUtils.join(attrsDefinitions, ",");
				attrsDefinition = attrsDefinition.replaceAll(",", "_");
			}
			channelConfig.setAttrsDefinition(attrsDefinition);
			//文件头属性
			/*String[] headerAttrsDefinitions=request.getParameterValues("headerAttrsDefinition");
			String headerAttrsDefinition = "";
			if (headerAttrsDefinitions != null && headerAttrsDefinitions.length > 0) {
				headerAttrsDefinition = StringUtils.join(headerAttrsDefinitions, ",");
				headerAttrsDefinition = headerAttrsDefinition.replaceAll(",", attrSplit);
			}
			channelConfig.setHeaderAttrsDefinition(headerAttrsDefinition);*/
			
			channelConfig.setBizType(channelConfig.getBizType().replaceAll(",", "_"));
			channelConfig.setAccountTime(channelConfig.getAccountTime().replaceAll(":", ""));
			channelConfigService.updateByPrimaryKey(channelConfig);
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
	
	@RequestMapping(value = "/deleteChannelConfig",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deleteChannelConfig(HttpServletRequest request, ModelMap modelMap){
		String channelConfigIds = request.getParameter("channelConfigIds");
		JSONObject result = new JSONObject();
		try {
			if (StringUtils.isBlank(channelConfigIds)) {
				throw new TppManagerException("ID为空");
			}
			List<Long> list = new ArrayList<Long>();
			String[] ids = channelConfigIds.split(",");
			for (String id : ids) {
				list.add(StringUtil.parseLong(id));
			}
			//批量删除权限
			channelConfigService.batchDelete(list);  
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
	
	/**
	 * 对账通道配置详情页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/detailChannelConfigPage")
	public String detailChannelConfigPage(HttpServletRequest request, ModelMap modelMap){
		try{
			String configId = request.getParameter("id");
			TppAccountChannelConfig channelConfig = channelConfigService.selectByPrimaryKey(StringUtil.parseLong(configId));
			channelConfig.setAccountTime(DateUtil.stringToTime(channelConfig.getAccountTime()));
			
			//业务类型转换
			String bizTypes = channelConfig.getBizType();
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
			modelMap.put("bizTypes", bizTypeStr.toString());
			
			modelMap.put("channelConfig", channelConfig);
			modelMap.put("fetchType", AccountFetchTypeEnum.getEnumDesc(channelConfig.getFetchType()));
			modelMap.put("status", AccountChannelStatusEnum.getEnumDesc(channelConfig.getStatus()));
			modelMap.put("currencyUnit", AccountCurrencyUnitEnum.getEnumDesc(channelConfig.getCurrencyUnit()));
			modelMap.put("currency", CurrencyEnum.getEnumDesc(channelConfig.getCurrency()));
			return "/account/channelConfig/channelConfigDetail";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
}
