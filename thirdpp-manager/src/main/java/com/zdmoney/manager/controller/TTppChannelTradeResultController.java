package com.zdmoney.manager.controller;

import java.text.ParseException;
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
import com.zdmoney.manager.enumset.TradeStatusEnum;
import com.zdmoney.manager.models.TppTradeTCollectTask;
import com.zdmoney.manager.service.TTppChannelTradeResultService;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.Page;


/** 
*
* @author 
* @version 
*/
@Controller
@RequestMapping(value = "/channel/tradeResult")
public class TTppChannelTradeResultController {
private final Logger log = Logger.getLogger(TTppChannelTradeResultController.class);
	
	@Autowired
	private TTppChannelTradeResultService channelTradeResultService;
	
	@Autowired
	private Page<TppTradeTCollectTask> page;
	
	/**
	 * 页面跳转
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/tradeResultList/{tradeFlow}")
	public String list(@PathVariable("tradeFlow") String tradeFlow,HttpServletRequest request, ModelMap modelMap){
		try{
			modelMap.put("tradeFlow", tradeFlow);
			return "/channel/channelTradeResultList";
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
	@RequestMapping(value = "/tradeResultListData/{tradeFlow}")
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
			List<Map> rows = channelTradeResultService.select_tppChannelTradeResultList(params);
			int count = channelTradeResultService.select_tppChannelTradeResultList_count(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				d.put("STATUS", d.get("STATUS") == null ? "" : TradeStatusEnum.getEnumDesc(d.get("STATUS").toString()));
				d.put("BIZ_TYPE", d.get("BIZ_TYPE") == null ? "" : BizTypeEnum.getEnumDesc(d.get("BIZ_TYPE").toString()));
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
			params.put("orderStr", " order by " + " CREATE_TIME " + " " + " DESC " + " ");
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
	}
}