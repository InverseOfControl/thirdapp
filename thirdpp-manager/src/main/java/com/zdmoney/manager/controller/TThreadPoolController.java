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
import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.models.TThreadPool;
import com.zdmoney.manager.service.TThreadPoolService;
import com.zdmoney.manager.utils.ConverNullString;
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
@RequestMapping(value = "/bim/threadPool")
public class TThreadPoolController {
	private final Logger log = Logger.getLogger(TSymPermissionController.class);

	@Autowired
	private Page<TThreadPool> page;
	@Autowired
	private TThreadPoolService threadService;
	@RequestMapping(value = "/threadPoolListData")
	@ResponseBody
	public String listData(HttpServletRequest request, ModelMap modelMap){

		try {
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			String beginTime =request.getParameter("beginTime");
			String endTime =request.getParameter("endTime");

			this.datagridParam(request, params);
			if(!StringUtil.isEmpty(beginTime)){
				beginTime= beginTime + " 00:00:00";
				params.put("beginTime", beginTime);

			}
			if(!StringUtil.isEmpty(endTime)){

				endTime=endTime +" 23:59:59";
				params.put("endTime", endTime);

			}

			params.put("bizType", request.getParameter("bizType"));
			params.put("infType", request.getParameter("infType"));
			params.put("paySysNo", request.getParameter("paySysNo"));
			params.put("minSize", request.getParameter("minSize"));
			params.put("maxSize", request.getParameter("maxSize"));
			params.put("queueSize", request.getParameter("queueSize"));
			params.put("isActive", request.getParameter("isActive"));
			// 2.遍历增加及转换自定义内容
			List<Map> rows =threadService.getThreadPoolList(params);
			int count = threadService.getThreadPoolListCount(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
			/*	d.put("BIZ_TYPE", d.get("BIZ_TYPE") == null ? "" : BizTypeEnum.getEnumDesc(d.get("BIZ_TYPE").toString()));*/
				if(!StringUtil.isEmpty(d.get("IS_ACTIVE"))){
					if(d.get("IS_ACTIVE").equals("0")){
					d.put("AC","<a href='javascript:return void(0);' style='text-decoration:none' onClick=\"updateFlag('"+ d.get("ID")+ "','"+ d.get("IS_ACTIVE")+ "');return false;\">开启</a>");
					d.put("IS_ACTIVE","<span style='color:red'>关闭</span>");
					}
					else if(d.get("IS_ACTIVE").equals("1")){
					d.put("AC","<a href='javascript:return void(0);' style='text-decoration:none'  onClick=\"updateFlag('"+ d.get("ID")+ "','"+ d.get("IS_ACTIVE")+ "');return false;\">关闭 </a>");
					d.put("IS_ACTIVE","开启");
					}
				}
				d.put("UPDATER", d.get("UPDATER") == null ? "" : d.get("UPDATER").toString()+"/"+  (d.get("UPDATER_NAME") == null ? "":d.get("UPDATER_NAME").toString()));
				d.put("CREATER", d.get("CREATER") == null ? "" : d.get("CREATER").toString()+"/"+ (d.get("CREATER_NAME") == null ? "":d.get("CREATER_NAME").toString()));

				d.put("update","<a href='javascript:return void(0);' onClick=\"updateThreadPool('"+ d.get("ID")+ "');\"><i class='icon-edit'></i></a>");
				 




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
		}
		params.put("rowBegin", rowBegin);
		params.put("rowEnd", rowEnd);
	}
	@RequestMapping(value = "/threadPoolList")
	public String threadlist(HttpServletRequest request, ModelMap modelMap){
		try {
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/bim/threadPool/threadPoolList")){
				return "/errorpage/permissionError";
			}
			/*Map<String,Object> params = new HashMap<String,Object>();
			//分页
			int pageNo = StringUtil.parseInteger(request.getParameter("pageNo"), 1);
			int pageSize = StringUtil.parseInteger(request.getParameter("pageSize"), 15);
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			//查询条件
			//封装查询条件

			String bizType = request.getParameter("bizType"); 
			String infType = request.getParameter("infType"); 
			String paySysNo = request.getParameter("paySysNo"); 
			String isActive = request.getParameter("isActive"); 
			String beginTime = request.getParameter("beginTime");
			String endTime = request.getParameter("endTime");
			if(!StringUtil.isEmpty(bizType)){
				params.put("bizType", bizType);
				modelMap.put("bizType",bizType);
			}
			if(!StringUtil.isEmpty(infType)){
				params.put("infType", infType);
				modelMap.put("infType", infType);
			}
			if(!StringUtil.isEmpty(paySysNo)){
				params.put("paySysNo", paySysNo);
				modelMap.put("paySysNo",paySysNo);
			}
			if(!StringUtil.isEmpty(isActive)){
				params.put("isActive", isActive);
				modelMap.put("isActive", isActive);
			}
			if(!StringUtil.isEmpty(beginTime)){
				modelMap.put("beginTime", beginTime);
				beginTime=beginTime.replaceAll("-", "");
				params.put("beginTime", beginTime);

			}
			if(!StringUtil.isEmpty(endTime)){
				modelMap.put("endTime", endTime);
				endTime=endTime.replaceAll("-", "");
				params.put("endTime", endTime);

			}
			params.put("page", page);

			//查询银行信息列表
			List<TThreadPool> threadList=threadService.getThreadPoolList(params);
			page.setResults(threadList);		
			modelMap.put("threadList", threadList);
			modelMap.put("page", page);*/
			return "/bim/threadPool/threadPoolList";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}

	/**
	 * 银行信息编辑页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/threadPoolEditUI/{id}")
	public String editUI(@PathVariable("id") String id,HttpServletRequest request, ModelMap modelMap) {
		TThreadPool th = null;
		if(!StringUtil.isEmpty(id)){
			 
			th = threadService.selectThreadPoolByID(StringUtil.parseLong(id));
		}else{
			//权限验证
		 

			th = new TThreadPool();
		}
		if(null != th){
			modelMap.put("threadPool", th);
		}

		return "bim/threadPool/threadPoolEdit";
	}

	/**
	 * 保存银行数据信息
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/threadPoolSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String bankInfoSave(@ModelAttribute("threadPoolSave")  TThreadPool thread,HttpServletRequest request, ModelMap modelMap) {
		try{
			//权限验证
			HttpSession session = request.getSession();
			 
			JSONObject jb=new JSONObject();
			ConverNullString sr=new  ConverNullString();
			sr.nullConverNullString(thread);			
			String response =  Validate.getInstance().validate(thread,
					com.zdmoney.manager.Validate.InsertCheck.class, Default.class);	
			String count=threadService.getThreadPoolCount(thread);
			if(!StringUtils.isBlank(response)){
				jb.put("valmsg", response);
				return jb.toString();
			}
			if(!StringUtil.isEmpty(count)){
				if(StringUtil.isEmpty(thread.getId())){
					response="相同业务类型,支付通道编码,应用程序名称已存在";
					jb.put("valmsg",response);
					return jb.toString();
				}else{
					 
					
					if(!thread.getId().equals(Integer.parseInt(count))){
						response="相同业务类型,支付通道编码,应用程序名称已存在";
						jb.put("valmsg",response);
						return jb.toString();
					 
				}	
					}

			}	
			if(StringUtil.isEmpty(thread.getId())){
				//新增	
				log.info("新增+："+thread.getId());
				TSysUser user=	(TSysUser) session.getAttribute("user");
				thread.setCreater(user.getLoginUserName());
				threadService.insert(thread);
			}else{
				//修改		
				log.info("修改+："+thread.getId());
				TSysUser user=	(TSysUser) session.getAttribute("user");
				thread.setUpdater(user.getLoginUserName());
				threadService.updateThreadPool(thread);
			} 
			log.info("保存成功");
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
	@RequestMapping(value = "/threadPoolUpdateIsActive")
	@ResponseBody
	public String updateIsActive(HttpServletRequest request, ModelMap modelMap) {
		TThreadPool sp = new TThreadPool();
		try{
			String id = request.getParameter("id");
			String isActive = request.getParameter("active");
			sp.setId(StringUtil.parseInteger(id));
			/*isActive=java.net.URLDecoder.decode(isActive,"UTF-8"); */
			if(isActive.equals("0")){
				isActive="1";
			}else if(isActive.equals("1")){
				isActive="0";
			}
			HttpSession session = request.getSession();
			TSysUser user=	(TSysUser) session.getAttribute("user");
			sp.setUpdater(user.getLoginUserName());
			sp.setIsActive(isActive);
			threadService.updateThreadPoolActive(sp);
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
		return  null;
	}
	@RequestMapping(value = "/threadPoolDelete/{ids}")
	@ResponseBody
	public String delete(@PathVariable("ids") String[] ids,HttpServletRequest request, ModelMap modelMap) {
		
		 
			if(null != ids && ids.length>0){
				List<Integer> list = new ArrayList<Integer>();
				for(String infoIdStr : ids){
					Integer infoId = Integer.valueOf(infoIdStr);
					list.add(infoId);
				}
				threadService.batchDeleteInfo(list);
			}
		 
		return null;
		
	}
	public static void main(String[] args) {
		/*TThreadPool t=new TThreadPool();
		 Response response =  Validate.getInstance().validate(t,
				com.zdmoney.manager.Validate.InsertCheck.class, Default.class);
		// 如果request检验不通过
		if (com.zdmoney.manager.vo.Response.SUCCESS_RESPONSE_CODE != response.getCode()) {

		}*/
	}

}
