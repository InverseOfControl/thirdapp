package com.zdmoney.manager.controller;

import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdmoney.manager.Validate.InsertCheck;
import com.zdmoney.manager.Validate.Validate;
import com.zdmoney.manager.enumset.PermissionTypeEnum;
import com.zdmoney.manager.exception.TppManagerException;
import com.zdmoney.manager.models.TSysPermission;
import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.service.TSymPermissionService;
import com.zdmoney.manager.service.TSymRolePermissionService;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.Page;
import com.zdmoney.manager.utils.PermissionUtil;
import com.zdmoney.manager.utils.StringUtil;


/** 
 *
 * @author 渡心
 * @version 2014年12月18日 下午1:59:18 
 */
@Controller
@RequestMapping(value = "/sym/permission")
public class TSymPermissionController {
	private final Logger log = Logger.getLogger(TSymPermissionController.class);
	
	@Autowired
	private TSymPermissionService permService;
	
	@Autowired
	private TSymRolePermissionService rolePermService;
	
	@Autowired
	private Page<TSysPermission> page;
	
	@RequestMapping(value = "/permList")
	public String listPage(HttpServletRequest request, ModelMap modelMap){
		try {
			//权限验证
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/sym/permission/permList")){
				return "/errorpage/permissionError";
			}
			return "/sym/permission/permList";
		} catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	@RequestMapping(value = "/permListData")
	@ResponseBody
	public String permListData(HttpServletRequest request, ModelMap modelMap){
		try {
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			this.datagridParam(request, params);
			this.queryParam(request, params);
			// 2.遍历增加及转换自定义内容
			List<Map> rows = permService.select_tSysPermissionList(params);
			int count = permService.select_tSysPermissionList_count(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				d.put("EDIT","<a href='javascript:return void(0);' onClick=\"editPerm('"+ d.get("ID")+ "');return false;\"><i class='icon-edit'></i></a>");
				d.put("DELETE","<a href='javascript:return void(0);' onClick=\"deletePerm('"+ d.get("ID")+ "');return false;\"><i class='icon-cancel'></i></a>");
				d.put("PERM_TYPE", d.get("PERM_TYPE") == null ? "" : PermissionTypeEnum.getEnumDesc(d.get("PERM_TYPE").toString()));
				d.put("CREATOR", d.get("CREATOR") == null ? "" : d.get("CREATOR") + "/" + d.get("CREATOR_NAME"));
				d.put("UPDATOR", d.get("UPDATOR") == null ? "" : d.get("UPDATOR") + "/" + d.get("UPDATOR_NAME"));
				
				/*d.put("IS_ACTIVE", d.get("IS_ACTIVE") == null ? "" : IsActiveEnum.getEnumDesc(d.get("IS_ACTIVE").toString()));*/
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
			params.put("orderStr", " order by " + "POSITION" + " " + "asc" + " ");
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
		params.put("SEARCH_PERM_NAME", request.getParameter("search_perm_name"));
		params.put("SEARCH_PERM_URL", request.getParameter("search_perm_url"));
		params.put("SEARCH_PERM_TYPE", request.getParameter("search_perm_type"));
	}
	
	@RequestMapping(value = "/deletePerm",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deletePerm(HttpServletRequest request, ModelMap modelMap){
		String permIds = request.getParameter("permIds");
		JSONObject result = new JSONObject();
		try {
			if (StringUtils.isBlank(permIds)) {
				throw new TppManagerException("ID为空");
			}
			List<Long> list = new ArrayList<Long>();
			String[] ids = permIds.split(",");
			for (String id : ids) {
				list.add(StringUtil.parseLong(id));
			}
			int count = permService.getPermUsedCount(list);
			//如果都未分配给角色，查询子权限数量
		    if(0 == count){
		    	count = permService.getSonPermsCount(list);
		    }
			if(count>0){
				throw new TppManagerException("选择的权限已分配给角色或存在子权限,请先解除关系或删除子权限！");
			}else{
				//批量删除权限
				permService.batchDeletePerm(list);  
			}
			result.put("flag", true);
		} catch (TppManagerException e) {
			result.put("flag", false);
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("flag", false);
			result.put("msg", "删除失败");
		}
		return result.toString();
	}
	
	/**
	 * 页面跳转——权限编辑页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/editPermPage")
	public String editPermPage(HttpServletRequest request, ModelMap modelMap) {
		try {
			String permId = request.getParameter("permId");
			TSysPermission permission = permService.getPermissionById(StringUtil.parseLong(permId));
			permission.setPermUrl(StringUtil.htmlEscape(permission.getPermUrl()));
			modelMap.put("permission", permission);
			return "sym/permission/permEdit";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	/**
	 * 查询权限列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/getPermissionList")
	@ResponseBody
	public String getPermissionList(HttpServletRequest request, ModelMap modelMap) {
		try {
			List<TSysPermission> permList = permService.getOtherPermissionList(null);
			List<Map> list=new ArrayList<Map>();
			Map nullMap = new HashMap();
			nullMap.put("name", "顶级菜单");
			nullMap.put("value", "");
			list.add(nullMap);
			for(TSysPermission perm : permList){
				Map map = new HashMap();
				map.put("name", perm.getPermName());
				map.put("value", perm.getId());
				list.add(map);
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			JSONArray json_rows = JSONArray.fromObject(list, config);
			return json_rows.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage(), ex);
			return null;
		}
	}
	
	/**
	 * 修改用户
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/editPermission",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String editUser(TSysPermission permission,HttpServletRequest request, ModelMap modelMap) {
		JSONObject result = new JSONObject();
		try{
			String msg = Validate.getInstance().validate(permission,
					InsertCheck.class, Default.class);
			if (StringUtils.isNotBlank(msg)) {
				throw new TppManagerException(msg);
			}
			TSysPermission editPerm = new TSysPermission();
			editPerm.setId(permission.getId());
			editPerm.setPermName(permission.getPermName());
			editPerm.setPermUrl(permission.getPermUrl());
			editPerm.setPosition(permission.getPosition());
			editPerm.setParentId(permission.getParentId());
			editPerm.setPermType(permission.getPermType());
			TSysUser sessionUser = (TSysUser) request.getSession().getAttribute("user");
			editPerm.setUpdator(sessionUser.getLoginUserName());
			permService.update(editPerm);
		} catch (TppManagerException e) {
			result.put("flag", false);
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("flag", false);
			result.put("msg", "编辑失败");
		}
		return result.toString();
	}
	
	/**
	 * 页面跳转——权限增加
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/addPermissionPage")
	public String addPermissionPage(HttpServletRequest request, ModelMap modelMap){
		try {
			return "sym/permission/permAdd";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	/**
	 * 新增权限
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/addPermission",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addPermission(HttpServletRequest request, ModelMap modelMap){
		JSONObject result = new JSONObject();
		String permName = request.getParameter("perm_add_perm_name");
		String permUrl = request.getParameter("perm_add_perm_url");
		String permType = request.getParameter("perm_add_perm_type");
		String permPosition = request.getParameter("perm_add_position");
		String permParentId= request.getParameter("perm_add_parent_id");
		try {
			TSysPermission perm = new TSysPermission();
			perm.setPermName(permName);
			perm.setPermUrl(permUrl);
			perm.setPermType(permType);
			perm.setPosition(permPosition);
			perm.setParentId(StringUtil.parseLong(permParentId));
			TSysUser user = (TSysUser) request.getSession().getAttribute("user");
			perm.setCreator(user.getLoginUserName());
			perm.setUpdator(user.getLoginUserName());
			String msg = Validate.getInstance().validate(perm,
					InsertCheck.class, Default.class);
			if (StringUtils.isNotBlank(msg)) {
				throw new TppManagerException(msg);
			}
			permService.insert(perm);
		} catch (TppManagerException e) {
			result.put("flag", false);
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("flag", false);
			result.put("msg", "新增失败");
		}
		return result.toString();
	}
}
