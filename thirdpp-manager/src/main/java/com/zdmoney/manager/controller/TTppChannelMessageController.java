package com.zdmoney.manager.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.zdmoney.manager.enumset.MessageTypeEnum;
import com.zdmoney.manager.enumset.TradeStatusEnum;
import com.zdmoney.manager.models.TppTradeTCollectTask;
import com.zdmoney.manager.service.TTppChannelMessageService;
import com.zdmoney.manager.service.TTppChannelRequestService;
import com.zdmoney.manager.service.TTppChannelTradeResultService;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.Page;

/** 
*
* @author 
* @version 
*/
@Controller
@RequestMapping(value = "/channel/message")
public class TTppChannelMessageController {
	
	private final Logger log = Logger.getLogger(TTppChannelMessageController.class);
	
	@Autowired
	private TTppChannelMessageService channelMessageService;
	
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
	@RequestMapping(value = "/channelMessageList/{tppRequestFlow}")
	public String list(@PathVariable("tppRequestFlow") String tppRequestFlow, HttpServletRequest request, ModelMap modelMap){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			//2、查询报文信息
			HashMap params = new HashMap();
			params.put("SEARCH_REQ_ID", tppRequestFlow);
			List<Map> messageRows = channelMessageService.select_tppChannelTMessageByReqId(params);
			for (Map d : messageRows) {
				if (MessageTypeEnum.REQUEST.getValue().equals(d.get("MSG_TYPE"))) {
					modelMap.put("requestMessage", d.get("MESSAGE"));
					modelMap.put("requestMessageDate", sdf.format(d.get("CREATE_TIME")));
				} else if (MessageTypeEnum.RESPONSE.getValue().equals(d.get("MSG_TYPE"))) {
					modelMap.put("responseMessage", d.get("MESSAGE"));
					modelMap.put("responseMessageDate", sdf.format(d.get("CREATE_TIME")));
				}
			}
			
			//3、查询交易结果
			params.clear();
			params.put("SEARCH_REQ_ID", tppRequestFlow);
			List<Map> tradeResultRows = channelTradeResultService.select_tppChannelTradeResultByTransferFolw(params);
			Map tradeResult = new HashMap();
			if (tradeResultRows != null && tradeResultRows.size() > 0) {
				tradeResult = tradeResultRows.get(0);
				tradeResult.put("STATUS", tradeResult.get("STATUS") == null ? "" : TradeStatusEnum.getEnumDesc(tradeResult.get("STATUS").toString()));
				tradeResult.put("CREATE_TIME", tradeResult.get("CREATE_TIME") == null ? "" : sdf.format(tradeResult.get("CREATE_TIME")));
			}
			modelMap.put("TRADE_RESULT", tradeResult);
			
			return "/channel/channelMessageList";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
}
