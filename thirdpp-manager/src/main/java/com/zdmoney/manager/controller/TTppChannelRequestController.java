package com.zdmoney.manager.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdmoney.manager.enumset.BizTypeEnum;
import com.zdmoney.manager.enumset.ChannelRequestStatusEnum;
import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.models.TppTradeTCollectTask;
import com.zdmoney.manager.service.TTppChannelRequestService;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.Page;
import com.zdmoney.manager.utils.PermissionUtil;

/** 
*
* @author 
* @version 
*/
@Controller
@RequestMapping(value = "/channel")
public class TTppChannelRequestController {
	
	private final Logger log = Logger.getLogger(TTppChannelRequestController.class);
	
	@Autowired
	private TTppChannelRequestService channelRequestService;
	
	@Autowired
	private Page<TppTradeTCollectTask> page;
	
	/**
	 * 页面跳转
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/request/channelRequestList")
	public String list(HttpServletRequest request, ModelMap modelMap){
		try{
			//权限验证
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/channel/request/channelRequestList")){
				return "/errorpage/permissionError";
			}
			return "/channel/channelRequestList";
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
	@RequestMapping(value = "/channelRequestlistData/{tradeFlow}")
	@ResponseBody
	public String listData(@PathVariable("tradeFlow") String tradeFlow,HttpServletRequest request, ModelMap modelMap){
		try {
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			if (StringUtils.isBlank(tradeFlow) || "null".equals(tradeFlow)) {
				tradeFlow = null;
			}
			params.put("SEARCH_TRADE_FLOW", tradeFlow);
			this.datagridParam(request, params);
			this.queryParam(request, params);
			// 2.遍历增加及转换自定义内容
			List<Map> rows = channelRequestService.select_tppChannelTRequestList(params);
			int count = channelRequestService.select_tppChannelTRequestList_count(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				
				d.put("STATUS", d.get("STATUS") == null ? "" : ChannelRequestStatusEnum.getEnumDesc(d.get("STATUS").toString()));
				d.put("BIZ_TYPE", d.get("BIZ_TYPE") == null ? "" : BizTypeEnum.getEnumDesc(d.get("BIZ_TYPE").toString()));
				d.put("DETAIL_MESSAGE","<a href='javascript:return void(0);' onClick=\"viewMessage('"+ d.get("REQ_ID")+ "');return false;\"><i class='icon-search'></i></a>");
				d.put("DETAIL_RESULT","<a href='javascript:return void(0);' onClick=\"viewResult('"+ d.get("TRANSFER_FLOW")+ "');return false;\"><i class='icon-search'></i></a>");
			}

			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
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
			log.error(ex.getMessage(), ex);
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
		//查询数据只能是7天内
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String beginDateStr = request.getParameter("search_begin_date");
		String endDateStr = request.getParameter("search_end_date");
		if (StringUtils.isNotBlank(beginDateStr)) {
			beginDateStr = beginDateStr + " 00:00:00";
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			endDateStr = endDateStr + " 23:59:59";
		}
		params.put("SEARCH_BEGIN_DATE", beginDateStr);
		params.put("SEARCH_END_DATE", endDateStr);
		params.put("SEARCH_REQ_ID", request.getParameter("search_req_id"));
		params.put("SEARCH_TRANSFER_FLOW", request.getParameter("search_transfer_flow"));
		params.put("SEARCH_STATUS", request.getParameter("search_status"));
		params.put("SEARCH_PAY_SYS_NO", request.getParameter("search_pay_sys_no"));
		params.put("SEARCH_BIZ_SYS_NO", request.getParameter("search_biz_sys_no"));
		params.put("SEARCH_BIZ_TYPE", request.getParameter("search_biz_type"));
		
		//设置系统查看权限
		TSysUser user = (TSysUser) request.getSession().getAttribute("user");
		List<String> appIdsList = user.getAppIds();
		if (appIdsList == null || appIdsList.size() < 1) {
			appIdsList = new ArrayList<String>();
			appIdsList.add("NO BIZ SYS");
		}
		params.put("APPIDS", appIdsList);
	}
}
