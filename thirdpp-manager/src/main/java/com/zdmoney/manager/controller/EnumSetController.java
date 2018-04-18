package com.zdmoney.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdmoney.manager.enumset.AccountBizTypeEnum;
import com.zdmoney.manager.enumset.AccountBizsysConfigStatusEnum;
import com.zdmoney.manager.enumset.AccountBizsysModeEnum;
import com.zdmoney.manager.enumset.AccountBizsysNotifyStatusEnum;
import com.zdmoney.manager.enumset.AccountBizsysRequestStatusEnum;
import com.zdmoney.manager.enumset.AccountBizsysStatusEnum;
import com.zdmoney.manager.enumset.AccountChannelRequestFailedReasonEnum;
import com.zdmoney.manager.enumset.AccountChannelRequestStatusEnum;
import com.zdmoney.manager.enumset.AccountChannelStatusEnum;
import com.zdmoney.manager.enumset.AccountCurrencyUnitEnum;
import com.zdmoney.manager.enumset.AccountFetchMethodEnum;
import com.zdmoney.manager.enumset.AccountFetchTypeEnum;
import com.zdmoney.manager.enumset.AccountThirdStatusEnum;
import com.zdmoney.manager.enumset.BizTypeEnum;
import com.zdmoney.manager.enumset.ChannelRequestStatusEnum;
import com.zdmoney.manager.enumset.IsActiveEnum;
import com.zdmoney.manager.enumset.NotifyMergeStatusEnum;
import com.zdmoney.manager.enumset.NotifyQueryStatusEnum;
import com.zdmoney.manager.enumset.NotifyStatusEnum;
import com.zdmoney.manager.enumset.OPModeEnum;
import com.zdmoney.manager.enumset.PayReceiverTypeEnum;
import com.zdmoney.manager.enumset.PayWhiteListStatusEnum;
import com.zdmoney.manager.enumset.PermissionTypeEnum;
import com.zdmoney.manager.enumset.SendStatusEnum;
import com.zdmoney.manager.enumset.TradeStatusEnum;
import com.zdmoney.manager.enumset.TradeWaitingStatusEnum;
import com.zdmoney.manager.service.EnumSetService;
import com.zdmoney.manager.service.TBankInfoService;
import com.zdmoney.manager.service.TDictionaryService;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
/** 
*
* @author 
* @version 
*/

@Controller
@RequestMapping(value = "/enumset")
public class EnumSetController {
	
	@Autowired
	private EnumSetService enumSetService;
	
	@Autowired
	private TDictionaryService dictionaryService; 
	@Autowired
	private TBankInfoService tBankInfoService;
	/**
	 * 业务系统名称-用于下拉列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/bizSysNoList")
	@ResponseBody
	public String getBizSysNoList(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List<Map> rows = enumSetService.select_sysAppList(null);
			List<Map> list=new ArrayList<Map>();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(Map row : rows){
				Map map = new HashMap();
				map.put("name", row.get("APP_NAME"));
				map.put("value", row.get("APP_CODE"));
				list.add(map);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/bizSysNoList/combobox")
	@ResponseBody
	public String getBizSysNoListCom(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List<Map> rows = enumSetService.select_sysAppList(null);
			List<Map> list=new ArrayList<Map>();
			Map nullMap = new HashMap();
			 
		 
			for(Map row : rows){
				Map map = new HashMap();
				map.put("text", row.get("APP_NAME"));
				map.put("id", row.get("APP_CODE"));
				list.add(map);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value = "/bizSysNoList/allcombobox")
	@ResponseBody
	public String getAllBizSysNoListCom(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List<Map> rows = enumSetService.select_allSysAppList();
			List<Map> list=new ArrayList<Map>();
			Map nullMap = new HashMap();
			 
		 
			for(Map row : rows){
				Map map = new HashMap();
				map.put("text", row.get("APP_NAME"));
				map.put("id", row.get("APP_CODE"));
				list.add(map);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/**
	 * 发送状态列表-用于下拉列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/sendStatusList")
	@ResponseBody
	public String getSendStatusList(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(SendStatusEnum acctType:SendStatusEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", SendStatusEnum.getEnumDesc(acctType.getValue()));
				nameMap.put("value", acctType.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/**
	 * 查询字典表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/dictionary/{dicType}")
	@ResponseBody
	public String getBizSysNoList(@PathVariable("dicType") String dicType,HttpServletRequest request, ModelMap modelMap) {
		if (StringUtils.isBlank(dicType)) {
			return null;
		}
		try {
			List<Map> rows = enumSetService.select_dictionaryByType(dicType);
			List<Map> list=new ArrayList<Map>();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(Map row : rows){
				Map map = new HashMap();
				map.put("name", row.get("DIC_NAME"));
				map.put("value", row.get("DIC_CODE"));
				list.add(map);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 查询字典表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/dictionary2/{dicType}")
	@ResponseBody
	public String getBizSysNoList2(@PathVariable("dicType") String dicType,HttpServletRequest request, ModelMap modelMap) {
		if (StringUtils.isBlank(dicType)) {
			return null;
		}
		try {
			List<Map> rows = enumSetService.select_dictionaryByType(dicType);
			List<Map> list=new ArrayList<Map>();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(Map row : rows){
				Map map = new HashMap();
				map.put("name", row.get("DIC_NAME"));
				map.put("value", row.get("DIC_CODE"));
				list.add(map);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 查询字典表_处理进程
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/appName")
	@ResponseBody
	public String getAppName(HttpServletRequest request, ModelMap modelMap) {
		try {
			List<Map> rows = enumSetService.select_dictionaryByType("15");
			List<Map> list=new ArrayList<Map>();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(Map row : rows){
				Map map = new HashMap();
				map.put("name", row.get("DIC_NAME"));
				map.put("value", row.get("DIC_NAME"));
				list.add(map);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 渠道请求发送状态列表-用于下拉列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/channelRequestStatusList")
	@ResponseBody
	public String getChannelRequestStatusList(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(ChannelRequestStatusEnum acctType:ChannelRequestStatusEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", ChannelRequestStatusEnum.getEnumDesc(acctType.getValue()));
				nameMap.put("value", acctType.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/**
	 * 通知查询状态
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/notifyQueryStatusList")
	@ResponseBody
	public String getNotifyQueryStatusList(HttpServletRequest request, ModelMap modelMap) {
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(NotifyQueryStatusEnum acctType:NotifyQueryStatusEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", NotifyQueryStatusEnum.getEnumDesc(acctType.getValue()));
				nameMap.put("value", acctType.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 通知合并状态
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/notifyMergeStatusList")
	@ResponseBody
	public String getNotifyMergeStatusList(HttpServletRequest request, ModelMap modelMap) {
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(NotifyMergeStatusEnum acctType:NotifyMergeStatusEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", NotifyMergeStatusEnum.getEnumDesc(acctType.getValue()));
				nameMap.put("value", acctType.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 通知状态
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/notifyStatusList")
	@ResponseBody
	public String getNotifyStatusList(HttpServletRequest request, ModelMap modelMap) {
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(NotifyStatusEnum acctType:NotifyStatusEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", NotifyStatusEnum.getEnumDesc(acctType.getValue()));
				nameMap.put("value", acctType.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 是否有效
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/isActiveList")
	@ResponseBody
	public String getIsActiveList(HttpServletRequest request, ModelMap modelMap) {
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(IsActiveEnum acctType:IsActiveEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", IsActiveEnum.getEnumDesc(acctType.getValue()));
				nameMap.put("value", acctType.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 菜单类型
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/permissionTypeList")
	@ResponseBody
	public String getPermissionTypeList(HttpServletRequest request, ModelMap modelMap) {
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(PermissionTypeEnum acctType:PermissionTypeEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", PermissionTypeEnum.getEnumDesc(acctType.getValue()));
				nameMap.put("value", acctType.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/**
	 * 查询地区
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/areaInfo/{parentId}")
	@ResponseBody
	public String getAreaInfoList(@PathVariable("parentId") String parentId,HttpServletRequest request, ModelMap modelMap) {
		try {
			 
			List<Map> rows = enumSetService.select_areaInfoParent(parentId);
			List<Map> list=new ArrayList<Map>();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(Map row : rows){
				Map map = new HashMap();
				map.put("name", row.get("AREA_NAME"));
				map.put("value", row.get("AREA_CODE"));
			 
				list.add(map);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	 
	}
	/**
	 * 查询商户类型
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/merType")
	@ResponseBody
	public String getMerchantTypeList(HttpServletRequest request, ModelMap modelMap) {
		try {
			 
			List<Map> rows = enumSetService.select_merType();
			List<Map> list=new ArrayList<Map>();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(Map row : rows){
				Map map = new HashMap();
				map.put("name", row.get("DIC_NAME"));
				map.put("value", row.get("DIC_CODE"));
			 
				list.add(map);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	 
	}
/*	@RequestMapping(value = "/dicType")
	@ResponseBody
	public String getdicTypeList(@PathVariable("dicType") String dicType,HttpServletRequest request, ModelMap modelMap) {
		try {
			 
			List<Map> rows =dictionaryService.getDicTypeList(dicType);
			List<Map> list=new ArrayList<Map>();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(Map row : rows){
				Map map = new HashMap();
				map.put("name", row.get("DIC_NAME"));
				map.put("value", row.get("DIC_CODE"));
				list.add(map);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	 
	}*/
 
	
	/**
	 * 运营类型
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/OPModeList")
	@ResponseBody
	public String getOPModeList(HttpServletRequest request, ModelMap modelMap) {
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(OPModeEnum type:OPModeEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", OPModeEnum.getEnumDesc(type.getValue()));
				nameMap.put("value", type.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 业务类型——代扣
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/bizTypeForCollectList")
	@ResponseBody
	public String getBizTypeForCollectList(HttpServletRequest request, ModelMap modelMap) {
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			Map map1 = new HashMap();
			map1.put("name", "代收");
			map1.put("value", BizTypeEnum.MEDIATOR_COLLECT.getValue());
			Map map2 = new HashMap();
			map2.put("name", "还款");
			map2.put("value", BizTypeEnum.CAPITAL_REPAYMENT.getValue());
			list.add(nullMap);
			list.add(map1);
			list.add(map2);
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 业务类型——代付
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/bizTypeForPayList")
	@ResponseBody
	public String getBizTypeForPayList(HttpServletRequest request, ModelMap modelMap) {
		try {
			List list=new ArrayList();
			
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			
			Map map1 = new HashMap();
			map1.put("name", "代付");
			map1.put("value", BizTypeEnum.MEDIATOR_PAY.getValue());
			list.add(map1);
			
			Map map2 = new HashMap();
			map2.put("name", "融资");
			map2.put("value", BizTypeEnum.CAPITAL_FINANCING.getValue());
			list.add(map2);
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 业务类型——代付
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/receiverTypeForPayList")
	@ResponseBody
	public String getReceiverTypeForPayList(HttpServletRequest request, ModelMap modelMap) {
		try {
			List list=new ArrayList();
			
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			
			Map map1 = new HashMap();
			map1.put("name", "对公");
			map1.put("value", PayReceiverTypeEnum.to_public.getCode());
			list.add(map1);
			
			Map map2 = new HashMap();
			map2.put("name", "对私");
			map2.put("value", PayReceiverTypeEnum.to_private.getCode());
			list.add(map2);
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 业务类型——代付
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/payWhiteListStatus")
	@ResponseBody
	public String getPayWhiteListStatus(HttpServletRequest request, ModelMap modelMap) {
		try {
			List list=new ArrayList();
			Map map1 = new HashMap();
			map1.put("name", "无效");
			map1.put("value", PayWhiteListStatusEnum.invalid.getCode());
			list.add(map1);
			
			Map map2 = new HashMap();
			map2.put("name", "有效");
			map2.put("value", PayWhiteListStatusEnum.valid.getCode());
			list.add(map2);
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/**
	 * 业务类型——代收代付
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/collectAndPayList")
	@ResponseBody
	public String getCollectAndPayList(HttpServletRequest request, ModelMap modelMap) {
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			Map map1 = new HashMap();
			map1.put("name", BizTypeEnum.getEnumDesc(BizTypeEnum.MEDIATOR_COLLECT.getValue()));
			map1.put("value", BizTypeEnum.MEDIATOR_COLLECT.getValue());
			Map map2 = new HashMap();
			map2.put("name", BizTypeEnum.getEnumDesc(BizTypeEnum.MEDIATOR_PAY.getValue()));
			map2.put("value", BizTypeEnum.MEDIATOR_PAY.getValue());
			Map map3 = new HashMap();
			map3.put("name", BizTypeEnum.getEnumDesc(BizTypeEnum.CAPITAL_FINANCING.getValue()));
			map3.put("value", BizTypeEnum.CAPITAL_FINANCING.getValue());
			Map map4 = new HashMap();
			map4.put("name", BizTypeEnum.getEnumDesc(BizTypeEnum.CAPITAL_REPAYMENT.getValue()));
			map4.put("value", BizTypeEnum.CAPITAL_REPAYMENT.getValue());
			list.add(nullMap);
			list.add(map1);
			list.add(map2);
			list.add(map3);
			list.add(map4);
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/**
	 * 业务类型——操作请求
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getBizTypeForRequest")
	@ResponseBody
	public String getBizTypeForRequest(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List<Map> rows = enumSetService.select_dictionaryByType("4");
			List<Map> list=new ArrayList<Map>();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(Map row : rows){
				Map map = new HashMap();
				map.put("name", row.get("DIC_NAME"));
				map.put("value", row.get("DIC_CODE"));
				if (BizTypeEnum.MEDIATOR_COLLECT.getValue().equals(row.get("DIC_CODE")) ||
						BizTypeEnum.MEDIATOR_PAY.getValue().equals(row.get("DIC_CODE")) || 
						BizTypeEnum.CAPITAL_FINANCING.getValue().equals(row.get("DIC_CODE")) || 
						BizTypeEnum.CAPITAL_REPAYMENT.getValue().equals(row.get("DIC_CODE"))) {
					continue;
				}
				list.add(map);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/**
	 * 信息类别
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getInfoCategoryList")
	@ResponseBody
	public String getInfoCategoryList(HttpServletRequest request, ModelMap modelMap) {
		try {
			List<Map> rows = enumSetService.select_infoCategory();
			List<Map> list=new ArrayList<Map>();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(Map row : rows){
				Map map = new HashMap();
				map.put("name", row.get("INFO_CATEGORY_NAME"));
				map.put("value", row.get("INFO_CATEGORY_CODE"));
				list.add(map);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 信息类别
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getInfoCategoryListByBizSysNo/{bizSysNo}")
	@ResponseBody
	public String getInfoCategoryListByBizSysNo(@PathVariable("bizSysNo") String bizSysNo, HttpServletRequest request, ModelMap modelMap) {
		try {
			List<Map> rows = enumSetService.select_infoCategoryByApp(bizSysNo);
			List<Map> list=new ArrayList<Map>();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(Map row : rows){
				Map map = new HashMap();
				map.put("name", row.get("INFO_CATEGORY_NAME"));
				map.put("value", row.get("INFO_CATEGORY_CODE"));
				list.add(map);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 业务类型——代收代付
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getTradeStatusForCollectTask")
	@ResponseBody
	public String getTradeStatusForCollectTask(HttpServletRequest request, ModelMap modelMap) {
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			Map map1 = new HashMap();
			map1.put("name", TradeStatusEnum.getEnumDesc(TradeStatusEnum.SUCCESS.getValue()));
			map1.put("value", TradeStatusEnum.SUCCESS.getValue());
			Map map2 = new HashMap();
			map2.put("name", TradeStatusEnum.getEnumDesc(TradeStatusEnum.FAIL.getValue()));
			map2.put("value", TradeStatusEnum.FAIL.getValue());
			Map map3 = new HashMap();
			map3.put("name", TradeStatusEnum.getEnumDesc(TradeStatusEnum.PROCESSING.getValue()));
			map3.put("value", TradeStatusEnum.PROCESSING.getValue());
			Map map4 = new HashMap();
			map4.put("name", TradeStatusEnum.getEnumDesc(TradeStatusEnum.PART_SUCCESS.getValue()));
			map4.put("value", TradeStatusEnum.PART_SUCCESS.getValue());
			list.add(nullMap);
			list.add(map1);
			list.add(map2);
			list.add(map3);
			list.add(map4);
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 业务类型——代收代付
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getTradeStatusForCollectInfo")
	@ResponseBody
	public String getTradeStatusForCollectInfo(HttpServletRequest request, ModelMap modelMap) {
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			Map map1 = new HashMap();
			map1.put("name", TradeStatusEnum.getEnumDesc(TradeStatusEnum.SUCCESS.getValue()));
			map1.put("value", TradeStatusEnum.SUCCESS.getValue());
			Map map2 = new HashMap();
			map2.put("name", TradeStatusEnum.getEnumDesc(TradeStatusEnum.FAIL.getValue()));
			map2.put("value", TradeStatusEnum.FAIL.getValue());
			Map map3 = new HashMap();
			map3.put("name", TradeStatusEnum.getEnumDesc(TradeStatusEnum.PROCESSING.getValue()));
			map3.put("value", TradeStatusEnum.PROCESSING.getValue());
			Map map4 = new HashMap();
			map4.put("name", TradeStatusEnum.getEnumDesc(TradeStatusEnum.EXCEPTION.getValue()));
			map4.put("value", TradeStatusEnum.EXCEPTION.getValue());
			list.add(nullMap);
			list.add(map1);
			list.add(map2);
			list.add(map3);
			list.add(map4);
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/**
	 * 查询字典表业务类型
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/bizTypeList")
	@ResponseBody
	public String getBizTypeList(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List<Map> rows = enumSetService.select_bizType();
			List<Map> list=new ArrayList<Map>();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(Map row : rows){
				Map map = new HashMap();
				map.put("name", row.get("DIC_NAME"));
				map.put("value", row.get("DIC_CODE"));
				list.add(map);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 交易待查询状态
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getTradeWaitingStatusList")
	@ResponseBody
	public String getTradeWaitingStatusList(HttpServletRequest request, ModelMap modelMap) {
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(TradeWaitingStatusEnum tradeWaitingStatusEnum:TradeWaitingStatusEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", TradeWaitingStatusEnum.getEnumDesc(tradeWaitingStatusEnum.getValue()));
				nameMap.put("value", tradeWaitingStatusEnum.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/**
	 * 获得查询模块列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getQueryModuleList")
	@ResponseBody
	public String getQueryModuleList(HttpServletRequest request, ModelMap modelMap) {
		try {
			String dicType = "7";
			List<Map> rows = enumSetService.select_dictionaryByType(dicType);
			List<Map> list=new ArrayList<Map>();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(Map row : rows){
				Map map = new HashMap();
				map.put("name", row.get("DIC_NAME"));
				map.put("value", row.get("DIC_NAME"));
				list.add(map);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
		/**
	 * 查询对账渠道状态
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getAccountChannelStatusList")
	@ResponseBody
	public String getAccountChannelStatusList(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(AccountChannelStatusEnum accountChannelStatus:AccountChannelStatusEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", AccountChannelStatusEnum.getEnumDesc(accountChannelStatus.getValue()));
				nameMap.put("value", accountChannelStatus.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/**
	 * 对账文件下载方式列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getAccountFetchMethodList")
	@ResponseBody
	public String getAccountFetchMethodList(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(AccountFetchMethodEnum accountFetchMethod:AccountFetchMethodEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", AccountFetchMethodEnum.getEnumDesc(accountFetchMethod.getValue()));
				nameMap.put("value", accountFetchMethod.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取对账文件方式列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getAccountFetchTypeList")
	@ResponseBody
	public String getAccountFetchTypeList(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(AccountFetchTypeEnum accountFetchType:AccountFetchTypeEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", AccountFetchTypeEnum.getEnumDesc(accountFetchType.getValue()));
				nameMap.put("value", accountFetchType.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取对账货币单位列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getAccountCurrencyUnitList")
	@ResponseBody
	public String getAccountCurrencyUnitList(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(AccountCurrencyUnitEnum accountCurrencyUnit:AccountCurrencyUnitEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", AccountCurrencyUnitEnum.getEnumDesc(accountCurrencyUnit.getValue()));
				nameMap.put("value", accountCurrencyUnit.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取操作请求状态列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getAccountChannelRequestStatusList")
	@ResponseBody
	public String getAccountChannelRequestStatusList(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(AccountChannelRequestStatusEnum accountChannelRequestStatus:AccountChannelRequestStatusEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", AccountChannelRequestStatusEnum.getEnumDesc(accountChannelRequestStatus.getValue()));
				nameMap.put("value", accountChannelRequestStatus.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取操作请求状态列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getAccountChannelRequestFailedReasonList")
	@ResponseBody
	public String getAccountChannelRequestFailedReasonList(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(AccountChannelRequestFailedReasonEnum accountChannelRequestFailedReason:AccountChannelRequestFailedReasonEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", AccountChannelRequestFailedReasonEnum.getEnumDesc(accountChannelRequestFailedReason.getValue()));
				nameMap.put("value", accountChannelRequestFailedReason.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取与第三方对账状态列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getAccountThirdStatusList")
	@ResponseBody
	public String getAccountThirdStatusList(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(AccountThirdStatusEnum accountThirdStatusEnum:AccountThirdStatusEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", AccountThirdStatusEnum.getEnumDesc(accountThirdStatusEnum.getValue()));
				nameMap.put("value", accountThirdStatusEnum.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取与业务系统对账状态列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getAccountBizsysStatusList")
	@ResponseBody
	public String getAccountBizsysStatusList(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(AccountBizsysStatusEnum accountBizsysStatusEnum:AccountBizsysStatusEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", AccountBizsysStatusEnum.getEnumDesc(accountBizsysStatusEnum.getValue()));
				nameMap.put("value", accountBizsysStatusEnum.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取与业务系统对账状态列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getAccountBizsysNotifyStatusList")
	@ResponseBody
	public String getAccountBizsysNotifyStatusList(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(AccountBizsysNotifyStatusEnum accountBizsysNotifyStatusEnum:AccountBizsysNotifyStatusEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", AccountBizsysNotifyStatusEnum.getEnumDesc(accountBizsysNotifyStatusEnum.getValue()));
				nameMap.put("value", accountBizsysNotifyStatusEnum.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取与业务系统配置状态列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getAccountBizsysConfigStatusList")
	@ResponseBody
	public String getAccountBizsysConfigStatusList(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(AccountBizsysConfigStatusEnum accountBizsysConfigStatusEnum:AccountBizsysConfigStatusEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", AccountBizsysConfigStatusEnum.getEnumDesc(accountBizsysConfigStatusEnum.getValue()));
				nameMap.put("value", accountBizsysConfigStatusEnum.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取对账模式列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getAccountBizsysModeList")
	@ResponseBody
	public String getAccountBizsysModeList(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(AccountBizsysModeEnum accountBizsysModeEnum:AccountBizsysModeEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", AccountBizsysModeEnum.getEnumDesc(accountBizsysModeEnum.getValue()));
				nameMap.put("value", accountBizsysModeEnum.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取对账请求状态列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getAccountBizsysRequestStatusModeList")
	@ResponseBody
	public String getAccountBizsysRequestStatusList(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			list.add(nullMap);
			for(AccountBizsysRequestStatusEnum accountBizsysRequestStatusEnum:AccountBizsysRequestStatusEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("name", AccountBizsysRequestStatusEnum.getEnumDesc(accountBizsysRequestStatusEnum.getValue()));
				nameMap.put("value", accountBizsysRequestStatusEnum.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value = "/accountBizTypeList/combobox")
	@ResponseBody
	public String getAccountBizTypeListCom(HttpServletRequest request, ModelMap modelMap) {
		
		try {
			List list=new ArrayList();
			for(AccountBizTypeEnum accountBizTypeEnum:AccountBizTypeEnum.values()){
				Map nameMap=new HashMap();
				nameMap.put("text", AccountBizTypeEnum.getEnumDesc(accountBizTypeEnum.getValue()));
				nameMap.put("id", accountBizTypeEnum.getValue());
				list.add(nameMap);
			}
			// 3.组合输出列表查询所需数据
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 业务类型——代扣
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/bizTypeForChannelAccount")
	@ResponseBody
	public String getBizTypeForChannelAccount(HttpServletRequest request, ModelMap modelMap) {
		try {
			List list=new ArrayList();
			Map nullMap = new HashMap();
			nullMap.put("name", "请选择");
			nullMap.put("value", "");
			Map map1 = new HashMap();
			map1.put("name", "代收");
			map1.put("value", BizTypeEnum.MEDIATOR_COLLECT.getValue());
			Map map2 = new HashMap();
			map2.put("name", "代付");
			map2.put("value", BizTypeEnum.MEDIATOR_PAY.getValue());
			list.add(nullMap);
			list.add(map1);
			list.add(map2);
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
     * 银行
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/getBankName")
    @ResponseBody
    public String getBankName(HttpServletRequest request, ModelMap modelMap) {
        try {
            List<Map> rows = tBankInfoService.getBankInfoList(null);
            List<Map> list=new ArrayList<Map>();
            Map nullMap = new HashMap();
            nullMap.put("name", "请选择");
            nullMap.put("value", "");
            list.add(nullMap);
            for(Map row : rows){
                Map map = new HashMap();
                map.put("name", row.get("BANK_NAME"));
                map.put("value", row.get("BANK_CODE"));
                list.add(map);
            }
            // 3.组合输出列表查询所需数据
            // JsonConfig 用于解析转换的设置
            JsonConfig config = new JsonConfig();
            JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
            JSONArray json_rows = JSONArray.fromObject(list, config);
            return json_rows.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
	
	
}
