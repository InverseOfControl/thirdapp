package com.zdmoney.manager.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.zdmoney.manager.enumset.AccountBizTypeEnum;
import com.zdmoney.manager.enumset.AccountBizsysRequestStatusEnum;
import com.zdmoney.manager.service.TTppAccountBizsysRequestService;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.PermissionUtil;
import com.zendaimoney.thirdpp.account.pub.service.IHandleAccountService;
import com.zendaimoney.thirdpp.account.pub.vo.AccountResponseVo;

@Controller
@RequestMapping("/account/bizsysRequest")
public class TTppAccountBizsysRequestController {
	
	private final Logger log = Logger.getLogger(TTppAccountBizsysRequestController.class);
	
	@Autowired
	private TTppAccountBizsysRequestService bizsysRequestService;
	@Autowired
	private IHandleAccountService handleAccountService;
	
	/**
	 * 页面跳转
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/bizsysRequestList")
	public String list(HttpServletRequest request, ModelMap modelMap){
		try{
			//权限验证
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/account/bizsysRequest/bizsysRequestList")){
				return "/errorpage/permissionError";
			}
			return "/account/bizsysRequest/bizsysRequestList";
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
		String bizSysRequestId = request.getParameter("pkid");
		try {
			if (StringUtils.isBlank(bizSysRequestId)) {
				result.put("flag", false);
				result.put("msg", "业务系统手工对账请求ID不能为空");
				return result.toString();
			}
			AccountResponseVo arv = handleAccountService.bizsysAccount(bizSysRequestId, "", "");
			if (AccountResponseVo.SUCCESS_RESPONSE_CODE.equals(arv.getCode())) {
				result.put("flag", true);
			} else {
				result.put("flag", false);
				result.put("msg", arv.getMsg());
			}
		} catch (Exception e){
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
			List<Map> rows = bizsysRequestService.select_tppAccountBizsysRequestList(params);
			int count = bizsysRequestService.select_tppAccountBizsysRequestList_count(params);
			
			// 2.遍历增加自定义内容
			for (Map d : rows) {
				BigDecimal status = (BigDecimal)d.get("STATUS");
				if (!status.toString().equals(AccountBizsysRequestStatusEnum.PUSH_SUCCESS.getValue())) {
					d.put("EDIT","<input type = 'button' class='icon-assigned' id =  '" + d.get("REQ_ID") + "' href='javascript:return void(0);' onClick=\"tppAccountBizsysRequest_list.handleAccount('"+ d.get("REQ_ID")+ "');return false;\"></input>");
				}
				// 2.自定义按钮设置在此处
				/*d.put("EDIT","<a href='javascript:return void(0);' onClick=\"tppAccountChannelConfig_list.updateFormSubmit('"+ d.get("ID")+ "');return false;\"><i class='icon-edit'></i></a>");
				d.put("DETAIL","<a href='javascript:return void(0);'onClick=\"tppAccountChannelConfig_list.detailFormSubmit('"+ d.get("ID")+ "');return false;\"><i class='icon-search'></i></a>");*/
				d.put("STATUS", d.get("STATUS") == null ? "" : AccountBizsysRequestStatusEnum.getEnumDesc(d.get("STATUS").toString()));
				
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
			params.put("orderStr", " order by " + " REQ_ID " + " " + " DESC " + " ");
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
		String beginDateStr = request.getParameter("search_account_begin_date");
		String endDateStr = request.getParameter("search_account_end_date");
		if (StringUtils.isNotBlank(beginDateStr)) {
			beginDateStr = beginDateStr.replace("-", "");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			endDateStr = endDateStr.replace("-", "");
		}
		params.put("SEARCH_ACCOUNT_BEGIN_DATE", beginDateStr);
		params.put("SEARCH_ACCOUNT_END_DATE", endDateStr);
		
		params.put("SEARCH_REQ_ID", request.getParameter("search_req_id"));
		params.put("SEARCH_BIZ_SYS_NO", request.getParameter("search_biz_sys_no"));
		params.put("SEARCH_STATUS", request.getParameter("search_status"));
		String bizTypes = request.getParameter("search_biz_type");
		if (StringUtils.isNotBlank(bizTypes)) {
			List bizTypeList = Arrays.asList(bizTypes.split(","));
			params.put("SEARCH_BIZ_TYPE", bizTypeList);
		}
	}
}
