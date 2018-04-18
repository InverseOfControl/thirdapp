package com.zdmoney.manager.controller;
import java.util.ArrayList;
import java.util.Dictionary;
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
import com.zdmoney.manager.enumset.ChannelStatusEnum;
import com.zdmoney.manager.enumset.PriorityEnum;
import com.zdmoney.manager.enumset.ThirdType;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TCategoryApps;
import com.zdmoney.manager.models.TChannelInfo;
import com.zdmoney.manager.models.TDictionary;
import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.models.TThreadPool;
import com.zdmoney.manager.service.TCategoryAppService;
import com.zdmoney.manager.service.TChannelInfoService;
import com.zdmoney.manager.service.TDictionaryService;
import com.zdmoney.manager.service.TSysAppService;
import com.zdmoney.manager.utils.BeanUtils;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.Page;
import com.zdmoney.manager.utils.PermissionUtil;
import com.zdmoney.manager.utils.StringUtil;
/** 
 *
 * @author wyj
 * @version 2015年7月3日 下午1:59:18 
 */
@Controller
@RequestMapping(value = "/bim/channelInfo")
public class TChannelInfoController {
	private final Logger log = Logger.getLogger(TSymPermissionController.class);
	@Autowired
	private TChannelInfoService channelService; 
	@Autowired
	private TDictionaryService dictionaryService; 
	@Autowired
	private Page<TChannelInfo> pageInfo;
	@Autowired
	private TCategoryAppService infoCategoryservice;
	@Autowired
	private Page<TChannelInfo> page;	 	 
	@RequestMapping(value = "/channelInfoListData/{dicCode}")
	@ResponseBody
	public String infoCategoryList(@PathVariable("dicCode") String dicCode,HttpServletRequest request, ModelMap modelMap){
		try {
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/bim/channelInfo/channelInfoList")){
				return "/errorpage/permissionError";
			}
			Map<String,Object> params = new HashMap<String,Object>();			 
			this.datagridParam(request, params);			 
			//分页		 
			//查询条件
			//封装查询条件
			params.put("channelName", request.getParameter("channelName"));
			params.put("status", request.getParameter("status"));
			params.put("thirdTypeNo",  dicCode);
			params.put("merchantType", request.getParameter("merchantType"));
			// 2.遍历增加及转换自定义内容
						List<Map> rows =channelService.getChannelInfoList(params);
						int count = channelService.getChannelInfoListCount(params);
						List<Map>	merTypeM=infoCategoryservice.getMerchantTypeList();
						for (Map d : rows) {							
							for(Map mm:merTypeM){
								if(d.get("MERCHANT_TYPE")!=null){
								if( mm.get("DIC_CODE").equals(d.get("MERCHANT_TYPE"))){d.put("MERCHANT_TYPE", mm.get("DIC_NAME"));};	}	
							}
							if(!StringUtil.isEmpty(d.get("STATUS"))){
								if(d.get("STATUS").toString().equals("0")){
									d.put("AC","<a href='javascript:return void(0);' style='text-decoration:none' onClick=\"updateFlag('"+ d.get("ID")+ "','"+ d.get("STATUS")+ "');\">开启 </a>");
								}
								else if(d.get("STATUS").toString().equals("1")){
									d.put("AC","<a href='javascript:return void(0);' style='text-decoration:none'  onClick=\"updateFlag('"+ d.get("ID")+ "','"+ d.get("STATUS")+ "');\">关闭 </a>");
								}
							};
							d.put("STATUS", d.get("STATUS") == null ? "" : ChannelStatusEnum.getEnumDesc(d.get("STATUS").toString()));
						/*	d.put("THIRD_TYPE_NO", d.get("THIRD_TYPE_NO") == null ? "" : ThirdType.getEnumDesc(d.get("THIRD_TYPE_NO").toString()));*/
							d.put("UPDATER", d.get("UPDATER") == null ? "" : d.get("UPDATER").toString()+"/"+  (d.get("UPDATER_NAME") == null ? "":d.get("UPDATER_NAME").toString()));
							d.put("CREATER", d.get("CREATER") == null ? "" : d.get("CREATER").toString()+"/"+ (d.get("CREATER_NAME") == null ? "":d.get("CREATER_NAME").toString()));
							 
							 if(!StringUtil.isEmpty(d.get("STATUS"))){
								if( d.get("STATUS").toString().equals("关闭")){
									d.put("STATUS","<span style='color:red'>关闭</span>");
								}
							 }
							// 2.自定义按钮设置在此处
							/*/ 
							d.put("TRADE_SUCCESS_AMOUNT" , d.get( "TRADE_SUCCESS_AMOUNT" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get( "TRADE_SUCCESS_AMOUNT" )) );*/
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
	  
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/toChannelInfoList/{dicCode}/{id}/{type}")
	public String toListData(@PathVariable("dicCode") String dicCode,@PathVariable("id") String parentId,@PathVariable("type") String type,HttpServletRequest request, ModelMap modelMap){		 
		String view = "/bim/channelInfo/channelInfoList";
	    modelMap.put("dicCode", dicCode);
		modelMap.put("parentId", parentId);
		modelMap.put("type", type);
		System.out.println(type);
		if("0001".equals(type)){
		    view = "/bim/channelInfo/channelInfoList";
		}else if("0002".equals(type)){
		    view = "/bim/channelInfo/precipitationInfoList";
        }else if("0003".equals(type)){
            view = "/bim/channelInfo/businessPriorityList";
        }else if("0004".equals(type)){
            view = "/bim/channelInfo/controlInfoList";
        }else if("0005".equals(type)){
            view = "/bim/channelInfo/costInfoList";
        }
		
		return view;
	}
	
	@RequestMapping(value = "/channelInfoList")
	public String list(HttpServletRequest request, ModelMap modelMap){	
		try {
			HttpSession session = request.getSession();
			System.out.println(session.getAttribute("permMap"));
			if(!PermissionUtil.isHavePermission(session, "/bim/channelInfo/channelInfoList")){
				return "/errorpage/permissionError";
			}	
		return "/bim/channelInfo/toChannelInfoMeanu";
	} catch (Exception e) {
		log.error(e.getMessage(), e);
		modelMap.put("errorMsg", e.getMessage());
		return "/errorpage/error";
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
		}
		params.put("rowBegin", rowBegin);
		params.put("rowEnd", rowEnd);
	}
	 
	 
	/**
	 * 信息编辑页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/channelInfoEditUI/{id}/{dicCode}/{parentId}")
	public String infoCateGoryAppsUI(@PathVariable("id") String id,@PathVariable("dicCode") String dicCode,@PathVariable("parentId") String parentId,HttpServletRequest request, ModelMap modelMap) {
		TChannelInfo info = null;
		//权限验证
		Map<String, Object> pam=new HashMap<String, Object>();
		pam.put("dicCode", dicCode);
		pam.put("parentId", parentId);
		if(!StringUtil.isEmpty(id)){
		 
			
			info = channelService.selectChannelInfoByID(StringUtil.parseLong(id));	
		    info.setNoText(dictionaryService.getDicName(pam));	
		}else{
		 
		 		
			info = new TChannelInfo();
			
			
			info.setNoText(dictionaryService.getDicName(pam));	
		}
		 
		if(null != info){
			 
			info.setNoteNo(info.getMerchantType());
			info.setThirdTypeNo(dicCode);
			modelMap.put("channelInfo", info);
		}

		return "bim/channelInfo/channelInfoEdit";
	}
	@RequestMapping(value = "/channelInfoSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String infoAppSave(@ModelAttribute("channelInfoSave") TChannelInfo info,HttpServletRequest request, ModelMap modelMap) {		 
		try{
			//权限验证
			JSONObject jb=new JSONObject();
			HttpSession session = request.getSession();
			 	 
			String response =  Validate.getInstance().validate(info,
					com.zdmoney.manager.Validate.InsertCheck.class, Default.class);
			int count=channelService.selectMerchantTypeCount(info);
			if(!StringUtils.isBlank(response)){
				jb.put("valmsg", response);
				return jb.toString();
			}
			if(count>0){
				if(StringUtil.isEmpty(info.getId())){
					response="商户类型已存在";
					jb.put("valmsg",response);
					return jb.toString();
				}else{
					if(!info.getMerchantType().equals(info.getNoteNo())){
						response="商户类型已存在";
						jb.put("valmsg",response);
						return jb.toString();
					}
				}	
					}	
	
			if(StringUtil.isEmpty(info.getId())){
					TSysUser user=	(TSysUser) session.getAttribute("user");
					info.setCreater(user.getLoginUserName());
					//新增	
					log.info("新增+："+info.getId());	
					//新增				 										 
					channelService.insert(info);				 
					log.info("保存成功");
				}else{
					//修改		
					log.info("修改+："+info.getId());
					TSysUser user=	(TSysUser) session.getAttribute("user");
					info.setUpdater(user.getLoginUserName());
					//新增	
					channelService.updateChannelInfo(info);
				} 
							
			return jb.toString();
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	/**
	 * 改变系统状态
	 */
	@RequestMapping(value = "/channelInfoUpdateStatus/{id}/{status}")
	@ResponseBody
	public String updateIsActive(@PathVariable("id") String id,@PathVariable("status") String status,HttpServletRequest request, ModelMap modelMap) {
		TChannelInfo ci = new TChannelInfo();
		try{
			ci.setId(StringUtil.parseInteger(id));
			if(status.toString().equals("0")){
				ci.setStatus("1");
			}else if(status.toString().equals("1")){
				ci.setStatus("0");
			}
		
			channelService.updateChannelStatus(ci);
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			 
		}
		return null;
		 
	}
	@RequestMapping(value = "/channelInfoDelete/{ids}")
	@ResponseBody
	public String infoAppsDelete(@PathVariable("ids") String[] ids,HttpServletRequest request, ModelMap modelMap) {
		if(null != ids && ids.length>0){
			List<Integer> list = new ArrayList<Integer>();
			for(String infoIdStr : ids){
				Integer infoId = Integer.valueOf(infoIdStr);
				list.add(infoId);
			}
			channelService.batchDeleteChannelInfo(list);
		}
		return null;
	} 
	

}
