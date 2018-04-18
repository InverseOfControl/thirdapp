package com.zdmoney.manager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.zdmoney.manager.enumset.ExecStatusEnum;
import com.zdmoney.manager.enumset.TradeWaitingStatusEnum;
import com.zdmoney.manager.service.TTppTradeWaitingMergeService;
import com.zdmoney.manager.utils.JsonDateFormatUtil;

/**
 * 
 * @author srainsk
 *
 */
@Controller
@RequestMapping(value = "/trade/waitingMerge")
public class TTppTradeWaitingMergeController {
	private final Logger log = Logger.getLogger(TTppTradeWaitingMergeController.class);
	
	@Autowired
	private TTppTradeWaitingMergeService tradeWaitingMergeService;
	
	/**
	 * 通知合并页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/tradeWaitingMergePage")
	public String tradeWaitingMergePage(HttpServletRequest request, ModelMap modelMap){
		String tradeFlow = request.getParameter("tradeFlow");
		modelMap.put("tradeFlow", tradeFlow);
		return "/trade/collect/tradeWaitingMergeList";
	}
	
	/**
	 * 通知合并数据
	 * @param tradeFlow
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/tradeWaitingMergeListData/{tradeFlow}")
	@ResponseBody
	public String tradeWaitingMergeListData(@PathVariable("tradeFlow") String tradeFlow, HttpServletRequest request, ModelMap modelMap){
		
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
			List<Map> rows = tradeWaitingMergeService.select_tppTradeTWaitingMergeList(params);
			int count = tradeWaitingMergeService.select_tppTradeTWaitingMergeList_count(params);
			for (Map d : rows) {
				d.put("STATUS", d.get("STATUS") == null ? "" : TradeWaitingStatusEnum.getEnumDesc(d.get("STATUS").toString()));
				d.put("BIZ_TYPE_NO", d.get("BIZ_TYPE_NO") == null ? "" : BizTypeEnum.getEnumDesc(d.get("BIZ_TYPE_NO").toString()));
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
		
	}
}
