package com.zdmoney.manager.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdmoney.manager.enumset.BizTypeEnum;
import com.zdmoney.manager.enumset.NotifyStatusEnum;
import com.zdmoney.manager.enumset.OPModeEnum;
import com.zdmoney.manager.enumset.PriorityEnum;
import com.zdmoney.manager.enumset.TradeStatusEnum;
import com.zdmoney.manager.models.TAppIps;
import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.models.TppTradeTNotify;
import com.zdmoney.manager.service.TTppTradeNotifyService;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.Page;
import com.zdmoney.manager.utils.PermissionUtil;
import com.zdmoney.manager.utils.StringUtil;

/** 
*
* @author 
* @version 
*/
@Controller
@RequestMapping(value = "/trade/notify")
public class TTppTradeNotifyController {
private final Logger log = Logger.getLogger(TTppTradeNotifyController.class);
	
	@Autowired
	private TTppTradeNotifyService notifyService;
	
	@Autowired
	private Page<TppTradeTNotify> page;
	
	@RequestMapping(value = "/notifyList")
	public String list(HttpServletRequest request, ModelMap modelMap){
		try{
			//权限验证
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/trade/notify/notifyList")){
				return "/errorpage/permissionError";
			}
			return "/trade/notify/notifyList";
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
	@RequestMapping(value = "/listData/{taskId}")
	@ResponseBody
	public String listData(@PathVariable("taskId") String taskId, HttpServletRequest request, ModelMap modelMap){
		try {
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			if (StringUtils.isBlank(taskId) || "null".equals(taskId)) {
				taskId = null;
			}
			params.put("taskId", taskId);
			this.datagridParam(request, params);
			this.queryParam(request, params);
			// 2.遍历增加及转换自定义内容
			List<Map> rows = notifyService.select_tppTradeTNotifyList(params);
			int count = notifyService.select_tppTradeTNotifyList_count(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				d.put("DETAIL","<a href='javascript:return void(0);' onClick=\"viewNotifyDetail('" + d.get("ID") + "');return false;\"><i class='icon-search'></i></a>");
				
				d.put("BIZ_TYPE", d.get("BIZ_TYPE") == null ? "" : BizTypeEnum.getEnumDesc(d.get("BIZ_TYPE").toString()));
				if(!d.get("NOTIFY_STATUS").toString().equals("3")){
					d.put("CLEAR","<a href='javascript:return void(0);' onClick=\"clearNotify('" + d.get("ID") + "');return false;\"><i class='icon-mini-edit'></i></a>");
				}
				d.put("NOTIFY_STATUS", d.get("NOTIFY_STATUS") == null ? "" : NotifyStatusEnum.getEnumDesc(d.get("NOTIFY_STATUS").toString()));
				d.put("TRADE_STATUS", d.get("TRADE_STATUS") == null ? "" : TradeStatusEnum.getEnumDesc(d.get("TRADE_STATUS").toString()));
				d.put("PRIORITY", d.get("PRIORITY") == null ? "" : PriorityEnum.getEnumDesc(d.get("PRIORITY").toString()));
				d.put("OP_MODE", d.get("OP_MODE") == null ? "" : OPModeEnum.getEnumDesc(d.get("OP_MODE").toString()));
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
			params.put("orderStr", " order by " + " ID " + " " + " DESC " + " ");
		}
		params.put("rowBegin", rowBegin);
		params.put("rowEnd", rowEnd);
	}
	
	/**
	 * 查询参数
	 * @param request
	 * @param params
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
		params.put("SEARCH_BIZ_FLOW", request.getParameter("search_biz_flow"));
		params.put("SEARCH_TRADE_FLOW", request.getParameter("search_trade_flow"));
		params.put("SEARCH_BIZ_TYPE", request.getParameter("search_biz_type"));
		params.put("SEARCH_BIZ_SYS_NO", request.getParameter("search_biz_sys_no"));
		params.put("SEARCH_NOTIFY_STATUS", request.getParameter("search_notify_status"));
		params.put("SEARCH_OP_MODE", request.getParameter("search_op_mode"));
		
		//设置系统查看权限
		TSysUser user = (TSysUser) request.getSession().getAttribute("user");
		List<String> appIdsList = user.getAppIds();
		if (appIdsList == null || appIdsList.size() < 1) {
			appIdsList = new ArrayList<String>();
			appIdsList.add("NO BIZ SYS");
		}
		params.put("APPIDS", appIdsList);
	}
	/**
	 * 通知清零
	 */
	
	
	@RequestMapping(value = "/clearNotify/{id}")
	@ResponseBody
	public String clear(@PathVariable("id") String id, HttpServletRequest request, ModelMap modelMap){
		notifyService.updateNotifyCount(id);
		notifyService.updateNotifyHisCount(id);
		return null;
	}
	/**
	 * 批量通知清零UI
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/clearNotifyAllUI")
	public String templateUI(HttpServletRequest request, ModelMap modelMap){	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		df.format(new Date());
		modelMap.put("now", df.format(new Date()));
		return "/trade/notify/clearNotifyUI";
	}
	/**
	 * 批量通知
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/clearNotifyAll")
	@ResponseBody
	public String clear(@ModelAttribute("clearNotifyForm") TppTradeTNotify notify, HttpServletRequest request, ModelMap modelMap){
		Map<String, Object> map=new HashMap<String, Object>();
		if (StringUtils.isNotBlank(notify.getBeginTime())) {
			notify.setBeginTime( notify.getBeginTime());
		}
		if (StringUtils.isNotBlank(notify.getEndTime())) {
			notify.setEndTime(  notify.getEndTime());
		}
		map.put("clear_begin_date", notify.getBeginTime());
		map.put("clear_end_date", notify.getEndTime());
		map.put("notifyCount", notify.getNotifyCount());
		if(!StringUtil.isEmpty(notify.getBizSysNo())){
			map.put("bizSysNo", notify.getBizSysNo());
		};
		int count = notifyService.updateNotifyAllCount(map);
		int num=	notifyService.updateNotifyHisAllCount(map);
		JSONObject jb=new JSONObject();
		jb.put("valmsg","成功清零"+(count+num)+"条");
		return    jb.toString();
	}
	 
	 
}
