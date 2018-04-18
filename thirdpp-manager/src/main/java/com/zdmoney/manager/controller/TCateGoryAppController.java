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
import com.zdmoney.manager.enumset.ChannelRulesEnum;
import com.zdmoney.manager.enumset.PriorityEnum;
import com.zdmoney.manager.enumset.ThirdType;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TCategoryApps;
import com.zdmoney.manager.models.TCategoryBanks;
import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.service.TBankInfoService;
import com.zdmoney.manager.service.TCategoryAppService;
import com.zdmoney.manager.service.TDictionaryService;
import com.zdmoney.manager.service.TSysAppService;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.Page;
import com.zdmoney.manager.utils.PermissionUtil;
import com.zdmoney.manager.utils.StringUtil;
import com.zdmoney.manager.utils.WebUtils;

/**
 *
 * @author wyj
 * @version 2015年7月3日 下午1:59:18
 */
@Controller
@RequestMapping(value = "/bim/categoryApp")
public class TCateGoryAppController {
	private final Logger log = Logger.getLogger(TSymPermissionController.class);
	@Autowired
	private TSysAppService sysAppService;
	@Autowired
	private Page<TCategoryApps> pageInfo;
	@Autowired
	private TCategoryAppService infoCategoryservice;
	@Autowired
	private TDictionaryService dictionaryService;
	@Autowired
	private TBankInfoService bankInfoService;
	@Autowired
	private Page<TCategoryApps> page;

	@RequestMapping(value = "/categoryAppListData")
	@ResponseBody
	public String infoCategoryList(HttpServletRequest request, ModelMap modelMap) {
		try {
			HttpSession session = request.getSession();
			if (!PermissionUtil.isHavePermission(session,
					"/bim/categoryApp/categoryAppList")) {
				return "/errorpage/permissionError";
			}
			Map<String, Object> params = new HashMap<String, Object>();
			this.datagridParam(request, params);
			// 分页
			// 查询条件
			// 封装查询条件
			params.put("infoCateGoryName", request.getParameter("cateGoryName"));
			/* params.put("paymentChannel", paymentChannel); */
			params.put("priority", request.getParameter("priority"));
			params.put("search_biz_sys_no",
					request.getParameter("search_biz_sys_no"));
			// 2.遍历增加及转换自定义内容
			List<Map> rows = infoCategoryservice.getInfoCatefoList(params);
			int count = infoCategoryservice.getInfoCatefoListCount(params);
			List<Map> merTypeM = infoCategoryservice.getMerchantTypeList();
			for (Map d : rows) {
				d.put("PRIORITY",
						d.get("PRIORITY") == null ? "" : PriorityEnum
								.getEnumDesc(d.get("PRIORITY").toString()));
				d.put("CHANNEL_RULES",
						d.get("CHANNEL_RULES") == null ? "" : ChannelRulesEnum
								.getEnumDesc(d.get("CHANNEL_RULES").toString()));
				d.put("UPDATER",
						d.get("UPDATER") == null ? "" : d.get("UPDATER")
								.toString()
								+ "/"
								+ (d.get("UPDATER_NAME") == null ? "" : d.get(
										"UPDATER_NAME").toString()));
				d.put("CREATER",
						d.get("CREATER") == null ? "" : d.get("CREATER")
								.toString()
								+ "/"
								+ (d.get("CREATER_NAME") == null ? "" : d.get(
										"CREATER_NAME").toString()));
				for (Map mm : merTypeM) {
					if (d.get("MERCHANT_TYPE") != null) {
						if (mm.get("DIC_CODE").equals(d.get("MERCHANT_TYPE"))) {
							d.put("MERCHANT_TYPE", mm.get("DIC_NAME"));
						}
						;
					}
				}
				// 2.自定义按钮设置在此处
				/*
				 * d.put("PAYMENT_CHANNEL", d.get("PAYMENT_CHANNEL") == null ?
				 * "" :
				 * ThirdType.getEnumDesc(d.get("PAYMENT_CHANNEL").toString()));
				 */
				/*
				 * /d.put("PAYER_BANK_CARD_TYPE", d.get("PAYER_BANK_CARD_TYPE")
				 * == null ? "" :
				 * BankCardTypeEnum.getEnumDesc(d.get("PAYER_BANK_CARD_TYPE"
				 * ).toString())); d.put("PAYER_ID_TYPE", d.get("PAYER_ID_TYPE")
				 * == null ? "" :
				 * IdTypeEnum.getEnumDesc(d.get("PAYER_ID_TYPE").toString()));
				 * d.put("CURRENCY", d.get("CURRENCY") == null ? "" :
				 * CurrencyEnum.getEnumDesc(d.get("CURRENCY").toString()));
				 * d.put("STATUS", d.get("STATUS") == null ? "" :
				 * SendStatusEnum.getEnumDesc(d.get("STATUS").toString()));
				 * d.put("IS_SEPARATE", d.get("IS_SEPARATE") == null ? "" :
				 * IsSeparateEnum.getEnumDesc(d.get("IS_SEPARATE").toString()));
				 * 
				 * d.put("IS_NEED_PUSH", d.get("IS_NEED_PUSH") == null ? "" :
				 * NeedPushEnum.getEnumDesc(d.get("IS_NEED_PUSH").toString()));
				 * d.put("TRADE_STATUS", d.get("TRADE_STATUS") == null ? "" :
				 * TradeStatusEnum
				 * .getEnumDesc(d.get("TRADE_STATUS").toString()));
				 * d.put("IS_NEED_SPILT", d.get("IS_NEED_SPILT") == null ? "" :
				 * IsNeedSpiltEnum
				 * .getEnumDesc(d.get("IS_NEED_SPILT").toString()));
				 * d.put("AMOUNT" , d.get( "AMOUNT" )==null ? "0.00" :
				 * BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get(
				 * "AMOUNT" )) ); d.put("FEE" , d.get( "FEE" )==null ? "0.00" :
				 * BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get(
				 * "FEE" )) ); d.put("TRADE_SUCCESS_AMOUNT" , d.get(
				 * "TRADE_SUCCESS_AMOUNT" )==null ? "0.00" :
				 * BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get(
				 * "TRADE_SUCCESS_AMOUNT" )) );
				 */
				d.put("APPNAME",
						"<a href='javascript:return void(0);' style='text-decoration:none'  onClick=\"toAppNameUI('"
								+ d.get("INFO_CATEGORY_CODE")
								+ "');return false;\">查看</a>");
				d.put("CHANNEL_CONFIGURE",
						"<a href='javascript:return void(0);' style='text-decoration:none'  onClick=\"channelConfigure('"
								+ d.get("INFO_CATEGORY_CODE")
								+ "');return false;\">按银行通道配置</a>");
			}

			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);

			JSONArray json_rows = JSONArray.fromObject(rows, config);
			String json = "{\"total\":\"" + count + "\",\"rows\":"
					+ json_rows.toString() + "}";
			return json;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void datagridParam(HttpServletRequest request,
			Map<String, Object> params) throws Exception {
		int pageNumber = StringUtils.isBlank(request.getParameter("page")) ? 1
				: Integer.parseInt(request.getParameter("page"));
		int pageSize = StringUtils.isBlank(request.getParameter("rows")) ? 10
				: Integer.parseInt(request.getParameter("rows"));

		int rowBegin = (pageNumber - 1) * pageSize + 1;
		int rowEnd = (pageNumber - 1) * pageSize + pageSize;
		String sortName = request.getParameter("sort");
		String sortOrder = StringUtils.isBlank(request.getParameter("order")) ? "asc"
				: request.getParameter("order");
		if (StringUtils.isNotBlank(sortName)
				&& StringUtils.isNotBlank(sortOrder)) {
			params.put("orderStr", " order by " + sortName + " " + sortOrder
					+ " ");
		}
		params.put("rowBegin", rowBegin);
		params.put("rowEnd", rowEnd);
	}

	// localhost:8085/thirdpp-manager/bim/categoryApp/categoryAppList
	@RequestMapping(value = "/categoryAppList")
	public String toCategoryApplist(HttpServletRequest request,
			ModelMap modelMap) {
		try {
			HttpSession session = request.getSession();
			System.out.println(session.getAttribute("permMap"));
			if (!PermissionUtil.isHavePermission(session,
					"/bim/categoryApp/categoryAppList")) {
				return "/errorpage/permissionError";
			}
			return "/bim/categoryApps/categoryAppsList";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}

	@RequestMapping(value = "/toCategoryAppsList/{dicCode}/{id}")
	public String toListData(@PathVariable("dicCode") String dicCode,
			@PathVariable("id") String parentId, HttpServletRequest request,
			ModelMap modelMap) {
		modelMap.put("dicCode", dicCode);
		modelMap.put("parentId", parentId);

		return "/bim/categoryApps/categoryAppsList";
	}

	/**
	 * 系统详情页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/appsNameEditUI/{code}")
	public String appsNameEditUI(@PathVariable("code") String code,
			HttpServletRequest request, ModelMap modelMap) {
		/*
		 * List<TCategoryApps>
		 * liststr=infoCategoryservice.selectAppNameStr(code);
		 * modelMap.put("liststr", liststr);
		 */
		modelMap.put("code", code);
		/*
		 * String str=""; for(TCategoryApps Apps:liststr){ if(Apps!=null){
		 * str+=","+Apps.getAppName(); } }
		 * 
		 * if(str!=""){ String[] mm=str.split(",", 2); modelMap.put("appName",
		 * mm[1]); }
		 */
		return "bim/categoryApps/categoryAppsAppName";
	}

	@RequestMapping(value = "/appTree")
	@ResponseBody
	public String appTree(HttpServletRequest request, ModelMap modelMap) {
		String code = request.getParameter("code");
		try {
			List<TCategoryApps> liststr = infoCategoryservice
					.selectAppNameStr(code);
			JSONArray array = new JSONArray();
			for (TCategoryApps app : liststr) {
				JSONObject obj = new JSONObject();
				obj.put("id", app.getId());
				obj.put("text", app.getAppName());
				obj.put("checked", false);
				array.add(obj);
			}
			return array.toString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 信息编辑页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/categoryAppsEditUI/{id}")
	public String infoCateGoryAppsUI(@PathVariable("id") String id,
			HttpServletRequest request, ModelMap modelMap) {
		TCategoryApps info = null;
		Map<String, Object> pam = new HashMap<String, Object>();

		// 权限验证
		if (!StringUtil.isEmpty(id)) {
			// 权限验证
			HttpSession session = request.getSession();
			System.out.println(session.getAttribute("permMap"));

			info = infoCategoryservice.selectCategoryByID(StringUtil
					.parseLong(id));
			info.setNoteNo(info.getInfoCateGoryCode());
			List<TCategoryApps> liststr = infoCategoryservice
					.selectCategoryCodeStr(info.getInfoCateGoryCode());
			String str = "";
			for (TCategoryApps Apps : liststr) {
				str += "," + Apps.getAppCode();

			}
			if (str != "") {
				String[] mm = str.split(",", 2);
				modelMap.put("appCodealue", mm[1]);
			}

			if (null != info) {

				modelMap.put("categoryApps", info);
			}
			return "bim/categoryApps/categoryAppsEdit";

		} else {
			// 权限验证
			if (null != info) {
				modelMap.put("categoryApps", info);
			}
			return "bim/categoryApps/categoryAppsAdd";
		}

	}

	@RequestMapping(value = "/categoryAppsSave", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String infoAppSave(
			@ModelAttribute("categoryAppsSave") TCategoryApps info,
			HttpServletRequest request, ModelMap modelMap) {
		try {
			// 权限验证
			JSONObject jb = new JSONObject();
			HttpSession session = request.getSession();

			String response = Validate.getInstance().validate(info,
					com.zdmoney.manager.Validate.InsertCheck.class,
					Default.class);
			int count = infoCategoryservice.getCategoryCodeCount(info
					.getInfoCateGoryCode());

			if (!StringUtils.isBlank(response)) {
				System.out.println("返回信息：" + response);
				jb.put("valmsg", response);
				return jb.toString();
			}
			if (count > 0) {
				if (StringUtil.isEmpty(info.getId())) {
					response = "信息类别编码已存在";
					jb.put("valmsg", response);
					return jb.toString();
				} else {

					if (!info.getInfoCateGoryCode().equals(info.getNoteNo())) {
						response = "信息类别编码已存在";
						jb.put("valmsg", response);
						return jb.toString();
					}
				}

			}

			if (StringUtil.isEmpty(info.getId())) {
				// 新增
				log.info("新增+：" + info.getId());
				// 新增
				String appCodeStr = info.getAppCode();
				if (!StringUtil.isEmpty(appCodeStr)) {
					String[] appCodeAyy = appCodeStr.split(",");
					List<TCategoryApps> cateList = new ArrayList<TCategoryApps>();
					for (String s : appCodeAyy) {

						info.setAppCode(s);

						infoCategoryservice.insertInfoCategoryApps(info);
					}
				}

				TSysUser user = (TSysUser) session.getAttribute("user");
				info.setCreater(user.getLoginUserName());
				infoCategoryservice.insertInfoCategory(info);
				log.info("保存成功");
			} else {
				// 修改
				log.info("修改+：" + info.getId());
				Integer id = info.getId();
				/**
				 * 获取原来的消息数据
				 */
				TCategoryApps cate = infoCategoryservice
						.selectCategoryByID(info.getId());
				/**
				 * 删除掉与此数据有关系的关联数据
				 */
				infoCategoryservice.batchDeleteInfoApps(cate
						.getInfoCateGoryCode());
				/**
				 * 添加与修改数据有关系的关联系统数据
				 */
				String appCodeStr = info.getAppCode();
				if (!StringUtil.isEmpty(appCodeStr)) {
					String[] appCodeAyy = appCodeStr.split(",");
					for (String s : appCodeAyy) {

						info.setAppCode(s);

						infoCategoryservice.insertInfoCategoryApps(info);
					}
				}
				info.setId(id);
				TSysUser user = (TSysUser) session.getAttribute("user");
				info.setUpdater(user.getLoginUserName());
				infoCategoryservice.updateCategory(info);
			}

			return jb.toString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}

	@RequestMapping(value = "/infoAppsDelete/{ids}/{codes}")
	@ResponseBody
	public String infoAppsDelete(@PathVariable("ids") String[] ids,
			@PathVariable("codes") String[] codes, HttpServletRequest request,
			ModelMap modelMap) {
		if (null != ids && ids.length > 0) {
			List<Integer> list = new ArrayList<Integer>();
			List<String> listcode = new ArrayList<String>();
			for (String infoIdStr : ids) {
				Integer infoId = Integer.valueOf(infoIdStr);
				list.add(infoId);
			}
			infoCategoryservice.batchDeleteInfo(list);
			if (null != codes && codes.length > 0) {
				for (String code : codes) {
					listcode.add(code);
				}
				infoCategoryservice.batchDeleteCode(listcode);
			}
		}

		return null;
	}
	
	@RequestMapping(value = "/rows")
	@ResponseBody
	public List<Map<String,String>> rows(){
		List<Map<String,String>>  list = new ArrayList<Map<String,String>>();
		List<Map> channels = dictionaryService.getDicTypeList(null);
		for(Map channel:channels){
			Map<String,String> map = new HashMap<String,String>();
			map.put("value", channel.get("DIC_CODE").toString());
			map.put("name", channel.get("DIC_NAME").toString());
			list.add(map);
		}
		return list;
	}
	
	@RequestMapping(value = "/datagrid/{categoryCode}")
	@ResponseBody
	public List<Map<String,String>> datagrid(@PathVariable("categoryCode")String categoryCode){
		List<Map<String,String>>  list = new ArrayList<Map<String,String>>();
		
		List<TBankInfo> banks = bankInfoService.selectAllBnkInfo();
		
		List<Map> channels = dictionaryService.getDicTypeList(null);
		
		List<TCategoryBanks> categoryBanks= infoCategoryservice.selectCategoryBanksByCategoryCode(categoryCode);
		
		for(TBankInfo bank:banks){
			Map<String,String> map = new HashMap<String,String>();
			map.put("BANK", bank.getBankName());
			for(Map channel:channels){
				if(categoryBanks.size()==0){
					map.put(channel.get("DIC_CODE").toString(), 
							"<input type=\"radio\" name=\"" + 
									bank.getBankCode() +
									"\" value=\""+
									bank.getBankCode().concat("|").concat(channel.get("DIC_CODE").toString())
									+"\">");
				}else{
					for(TCategoryBanks mapping:categoryBanks){
						if(bank.getBankCode().equals(mapping.getBankCode())&&
								channel.get("DIC_CODE").toString().equals(mapping.getPaySysNo())){
							map.put(channel.get("DIC_CODE").toString(), 
									"<input type=\"radio\" name=\"" + 
											bank.getBankCode() +
											"\" value=\""+
											bank.getBankCode().concat("|").concat(channel.get("DIC_CODE").toString())
											+"\" checked>");
							break;
						}else{
							map.put(channel.get("DIC_CODE").toString(), 
									"<input type=\"radio\" name=\"" + 
											bank.getBankCode() +
											"\" value=\""+
											bank.getBankCode().concat("|").concat(channel.get("DIC_CODE").toString())
											+"\">");
						}
					}
				}
				
			}
			map.put("CONFIGURE", "<a href='javascript:return void(0);' style='text-decoration:none'  onClick=\"rest('"
					+ bank.getBankCode()
					+ "');return false;\">清空</a>"
					+"&nbsp<a href='javascript:return void(0);' style='text-decoration:none'  onClick=\"singleConfigure('"
					+ categoryCode + "','"
					+ bank.getBankCode()+ "','"
					+ bank.getBankName()+ "','"
					+ "');return false;\">配置</a>"					
					);
			list.add(map);
		}
		
		return list;
	}
	
	@RequestMapping(value = "/configure")
	@ResponseBody
	public String configure(String categoryCode,HttpServletRequest request){
		try{
			Map<String,String> map = WebUtils.getParamMap();
			HttpSession session = request.getSession();
			
			for(String key:map.keySet()){
				TCategoryBanks mapping = new TCategoryBanks();
				if(StringUtils.isNotEmpty(map.get(key))){
					String[] arrs = map.get(key).split("[|]");
					if(arrs.length==1){
						continue;
					}
					mapping.setBankCode(arrs[0]);
					mapping.setPaySysNo(arrs[1]);
				}else{
					mapping.setBankCode(key);
				}
				TSysUser user=	(TSysUser) session.getAttribute("user");
				mapping.setInfoCategoryCode(categoryCode);
				mapping.setCreater(user.getLoginUserName());
				mapping.setUpdater(user.getLoginUserName());
				infoCategoryservice.commonConfigure(mapping);
			}
		}catch(Exception e){
			log.error("configure error:"+e);
			return "{\"success\":false}";
		}
		return "{\"success\":true}";
	}

}
