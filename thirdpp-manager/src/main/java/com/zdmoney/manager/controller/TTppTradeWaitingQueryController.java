package com.zdmoney.manager.controller;

import java.text.SimpleDateFormat;
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
import com.zdmoney.manager.enumset.OPModeEnum;
import com.zdmoney.manager.enumset.TradeWaitingStatusEnum;
import com.zdmoney.manager.exception.TppManagerException;
import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.models.TppTradeTCollectInfo;
import com.zdmoney.manager.models.TppTradeTNotify;
import com.zdmoney.manager.models.TppTradeTWaitingQuery;
import com.zdmoney.manager.service.TTppTradeWaitingQueryService;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.PermissionUtil;
import com.zdmoney.manager.utils.StringUtil;

/**
 * 
 * @author srainsk
 *
 */
@Controller
@RequestMapping(value = "/trade/waitingQuery")
public class TTppTradeWaitingQueryController {
	private final Logger log = Logger.getLogger(TTppTradeWaitingQueryController.class);
	
	@Autowired
	private TTppTradeWaitingQueryService tradeWaitingQueryService;
	
	
	/**
	 * 通知查询页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/tradeWaitingQueryList")
	public String tradeWaitingQueryList(HttpServletRequest request, ModelMap modelMap){
		try{
			//权限验证
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/trade/waitingQuery/tradeWaitingQueryList")){
				return "/errorpage/permissionError";
			}
			return "/trade/waitingQuery/tradeWaitingQueryList";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	/**
	 * 通知查询数据
	 * @param tradeFlow
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/tradeWaitingQueryListData/{tradeFlow}")
	@ResponseBody
	public String tradeWaitingQueryListData(@PathVariable("tradeFlow") String tradeFlow, HttpServletRequest request, ModelMap modelMap){
		
		try {
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			if (StringUtils.isBlank(tradeFlow) || "null".equals(tradeFlow)) {
				tradeFlow = null;
			}
			params.put("tradeFlow", tradeFlow);
			this.datagridParam(request, params);
			this.queryParam(request, params);
			// 2.遍历增加及转换自定义内容
			List<Map> rows = tradeWaitingQueryService.select_tppTradeTWaitingQueryList(params);
			int count = tradeWaitingQueryService.select_tppTradeTWaitingQueryList_count(params);
			for (Map d : rows) {
				if(TradeWaitingStatusEnum.WAIT_CHECK.getValue().equals(d.get("STATUS").toString())){
					d.put("CHECKED","<a href='javascript:return void(0);' onClick=\"tradeWaitingChecked('" + d.get("ID") + "');return false;\"><i class='icon-mini-edit'></i></a>");
				}
				d.put("STATUS", d.get("STATUS") == null ? "" : TradeWaitingStatusEnum.getEnumDesc(d.get("STATUS").toString()));
				d.put("BIZ_TYPE_NO", d.get("BIZ_TYPE_NO") == null ? "" : BizTypeEnum.getEnumDesc(d.get("BIZ_TYPE_NO").toString()));
				d.put("OP_MODE", d.get("OP_MODE") == null ? "" : OPModeEnum.getEnumDesc(d.get("OP_MODE").toString()));
				d.put("AUDITOR", d.get("AUDITOR") == null ? "" : d.get("AUDITOR") + "/" + d.get("AUDITOR_NAME"));
			}

			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);

			JSONArray json_rows = JSONArray.fromObject(rows, config);
			String json = "{\"total\":\""
					+ count
					+ "\",\"rows\":" + json_rows.toString() + "}";
			modelMap.put("data", json);
			return json;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage(), ex);
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
		params.put("SEARCH_TRADE_FLOW", request.getParameter("search_trade_flow"));
		params.put("SEARCH_BIZ_TYPE_NO", request.getParameter("search_biz_type_no"));
		params.put("SEARCH_BIZ_SYS_NO", request.getParameter("search_biz_sys_no"));
		params.put("SEARCH_PAY_SYS_NO", request.getParameter("search_pay_sys_no"));
		params.put("SEARCH_INFO_CATEGORY_CODE", request.getParameter("search_info_category_code"));
		params.put("SEARCH_STATUS", request.getParameter("search_status"));
		params.put("SEARCH_OP_MODE", request.getParameter("search_op_mode"));
		
		String beginDateStr = request.getParameter("beginDate");
		String endDateStr = request.getParameter("endDate");
		if (StringUtils.isNotBlank(beginDateStr)) {
			beginDateStr = beginDateStr + " 00:00:00";
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			endDateStr = endDateStr + " 23:59:59";
		}
		params.put("SEARCH_BEGIN_DATE", beginDateStr);
		params.put("SEARCH_END_DATE", endDateStr);
	}
	
	
	//通知查询插入
	@RequestMapping(value = "/tradeWaitingQueryChecked/{id}")
	@ResponseBody
	public String tradeWaitingQueryChecked(@PathVariable("id") String id, HttpServletRequest request, ModelMap modelMap) {
		try{ 
			TppTradeTWaitingQuery waitingQuery = new TppTradeTWaitingQuery();
			waitingQuery.setId(StringUtil.parseLong(id));
			waitingQuery.setStatus(TradeWaitingStatusEnum.WAIT_PROCESS.getValue());
			TSysUser sessionUser = (TSysUser) request.getSession().getAttribute("user");
			waitingQuery.setAuditor(sessionUser.getLoginUserName());
			waitingQuery.setAuditTime(new Date());
			tradeWaitingQueryService.updateByPrimaryKeySelective(waitingQuery);
			tradeWaitingQueryService.updateByPrimaryKeySelectiveHis(waitingQuery);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage(), ex);
			return null;
		}
		return null;
	}
	/**
	 * 批量审核页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/batchWaitingQueryCheckedPage")
	public String batchWaitingQueryCheckedPage(HttpServletRequest request, ModelMap modelMap){	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		df.format(new Date());
		modelMap.put("now", df.format(new Date()));
		return "/trade/waitingQuery/batchWaitingQueryChecked";
	}
	
	/**
	 * 批量通知
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/batchWaitingQueryChecked")
	@ResponseBody
	public String batchWaitingQueryChecked(HttpServletRequest request, ModelMap modelMap){
		JSONObject result = new JSONObject();
		try{
			Map<String, Object> map=new HashMap<String, Object>();
			String beginTime = request.getParameter("beginTime");
			String endTime = request.getParameter("endTime");
			
			String paySysNo = request.getParameter("paySysNo");
			String bizSysNo = request.getParameter("bizSysNo");
			String infoCategoryCode = request.getParameter("infoCategoryCode");
			String opMode = request.getParameter("opMode");
			
			if (StringUtils.isBlank(beginTime) || StringUtils.isBlank(endTime) ) {
				throw new TppManagerException("时间不能为空！");
			}
			map.put("checked_begin_date", beginTime);
			map.put("checked_end_date", endTime);
			
			map.put("paySysNo", paySysNo);
			map.put("bizSysNo", bizSysNo);
			map.put("infoCategoryCode", infoCategoryCode);
			map.put("opMode", opMode);
			TSysUser sessionUser = (TSysUser) request.getSession().getAttribute("user");
			map.put("AUDITOR", sessionUser.getLoginUserName());
			int count = tradeWaitingQueryService.batchCheckedWaitingQuery(map);
			count += tradeWaitingQueryService.batchCheckedWaitingQueryHis(map);
			JSONObject jb=new JSONObject();
			jb.put("msg","成功审核"+(count)+"条");
			return    jb.toString();
		} catch (TppManagerException e) {
			log.error(e.getMessage(), e);
			result.put("flag", false);
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("flag", false);
			result.put("msg", e.getMessage());
		}
		return result.toString();
	}
}
