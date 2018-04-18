package com.zdmoney.manager.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdmoney.manager.Validate.InsertCheck;
import com.zdmoney.manager.Validate.Validate;
import com.zdmoney.manager.enumset.IsActiveEnum;
import com.zdmoney.manager.exception.TppManagerException;
import com.zdmoney.manager.models.TSysPermission;
import com.zdmoney.manager.models.TSysRole;
import com.zdmoney.manager.models.TSysRoleApp;
import com.zdmoney.manager.models.TSysRolePermission;
import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.models.TSysUserRole;
import com.zdmoney.manager.service.TSymPermissionService;
import com.zdmoney.manager.service.TSymRolePermissionService;
import com.zdmoney.manager.service.TSymRoleService;
import com.zdmoney.manager.service.TSymUserRoleService;
import com.zdmoney.manager.service.TSymUserService;
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
@RequestMapping(value = "/sym/role")
public class TSymRoleController {
	private final Logger log = Logger.getLogger(TSymRoleController.class);
	
	@Autowired
	private TSymRoleService roleService;
	
	@Autowired
	private TSymPermissionService permService;

	@Autowired
	private TSymRolePermissionService rolePermService;
	
	@Autowired
	private TSymUserService userService;
	
	@Autowired
	private TSymUserRoleService userRoleService;
	
	@Autowired
	private Page<TSysRole> page;
	
	@RequestMapping(value = "/roleList")
	public String roleListPage(HttpServletRequest request, ModelMap modelMap) {
		try{
			//权限验证
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/sym/role/roleList")){
				return "/errorpage/permissionError";
			}
			return "/sym/role/roleList";
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return "/errorpage/error";
		}
	}
	
	@RequestMapping(value = "/roleListData")
	@ResponseBody
	public String roleListData(HttpServletRequest request, ModelMap modelMap) {
		try {
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			this.datagridParam(request, params);
			this.queryParam(request, params);
			// 2.遍历增加及转换自定义内容
			List<Map> rows = roleService.select_tSysRoleList(params);
			int count = roleService.select_tSysRoleList_count(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				d.put("IS_ACTIVE", d.get("IS_ACTIVE") == null ? "" : IsActiveEnum.getEnumDesc(d.get("IS_ACTIVE").toString()));
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
			log.error(ex.getMessage(), ex);
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
			params.put("orderStr", " order by " + "CREATE_TIME" + " " + "asc" + " ");
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
		params.put("SEARCH_ROLE_NAME", request.getParameter("search_role_name"));
	}
	
	
	/**
	 * 角色——用户绑定页面
	 * @param roleId
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/roleUserListPage/{roleId}")
	public String roleUserListPage(@PathVariable("roleId") String roleId, HttpServletRequest request, ModelMap modelMap) {
		try{
			modelMap.put("roleId", roleId);
			return "/sym/role/roleUserList";
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return "/errorpage/error";
		}
	}
	@RequestMapping(value = "/roleUserListData/{roleId}")
	@ResponseBody
	public String roleUserListData(@PathVariable("roleId") String roleId, HttpServletRequest request, ModelMap modelMap) {
		if (StringUtils.isBlank(roleId)) {
			return null;
		}
		try {
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("roleId", roleId);
			this.datagridRoleUserParam(request, params);
			this.queryRoleUserParam(request, params);
			// 2.遍历增加及转换自定义内容
			List<Map> rows = roleService.select_tSysUserListByRole(params);
			int count = roleService.select_tSysUserListByRole_count(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				d.put("REMOVE","<a href='javascript:return void(0);' onClick=\"removeUser('" + d.get("ID") + "','" + roleId + "');return false;\"><i class='icon-remove'></i></a>");
				/*d.put("DELETE","<a href='javascript:return void(0);' onClick=\"deleteUser('"+ d.get("ID")+ "');return false;\"><i class='icon-cancel'></i></a>");*/
				d.put("IS_ACTIVE", d.get("IS_ACTIVE") == null ? "" : IsActiveEnum.getEnumDesc(d.get("IS_ACTIVE").toString()));
				d.put("CREATOR", d.get("CREATOR") == null ? "" : d.get("CREATOR") + "/" + d.get("CREATOR_NAME"));
				d.put("UPDATOR", d.get("UPDATOR") == null ? "" : d.get("UPDATOR") + "/" + d.get("UPDATOR_NAME"));
				d.put("VIEW_ROLE","<a href='javascript:return void(0);' onClick=\"viewRole('"+ d.get("ID")+ "');return false;\"><i class='icon-search'></i></a>");
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
			log.error(ex.getMessage(), ex);
			return null;
		}
	}
	
	/**
	 * 翻页 排序参数
	 * @param request
	 * @param params
	 */
	private void datagridRoleUserParam(HttpServletRequest request, Map<String,Object> params) throws Exception {
		int pageNumber = StringUtils.isBlank(request.getParameter("page")) ? 1: Integer.parseInt(request.getParameter("page"));
		int pageSize = StringUtils.isBlank(request.getParameter("rows")) ? 10: Integer.parseInt(request.getParameter("rows"));
		
		int rowBegin = (pageNumber-1) * pageSize + 1;
		int rowEnd = (pageNumber-1) * pageSize + pageSize;
		String sortName = request.getParameter("sort");
		String sortOrder = StringUtils.isBlank(request.getParameter("order")) ? "asc" : request.getParameter("order");
		if (StringUtils.isNotBlank(sortName) && StringUtils.isNotBlank(sortOrder)) {
			params.put("orderStr", " order by " + sortName + " " + sortOrder + " ");
		} else {
			params.put("orderStr", " order by " + "CREATE_TIME" + " " + "asc" + " ");
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
	private void queryRoleUserParam(HttpServletRequest request, Map<String,Object> params) throws ParseException {
	}
	
	//TODO
	/**
	 * 移除角色用户
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/removeRoleUser")
	@ResponseBody
	public String removeRoleUser(HttpServletRequest request, ModelMap modelMap){
		String roleId = request.getParameter("removeRoleId");
		String userId = request.getParameter("removeUserId");
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(roleId)) {
			return null;
		}
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("roleId", roleId);
			params.put("userId", userId);
			this.roleService.delete_roleUser(params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	@RequestMapping(value = "/addRoleUserPage")
	public String addRoleUserPage(HttpServletRequest request, ModelMap modelMap){
		String roleId = request.getParameter("roleId");
		modelMap.put("roleId", roleId);
		return "/sym/role/roleUserAdd";
	}
	
	@RequestMapping(value = "/addRoleUserListData/{roleId}")
	@ResponseBody
	public String addRoleUserListData(@PathVariable("roleId") String roleId, HttpServletRequest request, ModelMap modelMap){
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("roleId", roleId);
			this.datagridParamForAddUser(request, params);
			this.queryParamForAddUser(request, params);
			List<Map> userList = roleService.select_notRoleUserByRole(params);
			int count = roleService.select_notRoleUserByRole_count(params);
			for (Map d : userList) {
				// 2.自定义按钮设置在此处
				/*d.put("DELETE","<a href='javascript:return void(0);' onClick=\"deleteUser('"+ d.get("ID")+ "');return false;\"><i class='icon-cancel'></i></a>");*/
				d.put("IS_ACTIVE", d.get("IS_ACTIVE") == null ? "" : IsActiveEnum.getEnumDesc(d.get("IS_ACTIVE").toString()));
				d.put("VIEW_ROLE","<a href='javascript:return void(0);' onClick=\"viewRole('"+ d.get("ID")+ "');return false;\"><i class='icon-search'></i></a>");
				d.put("CREATOR", d.get("CREATOR") == null ? "" : d.get("CREATOR") + "/" + d.get("CREATOR_NAME"));
				d.put("UPDATOR", d.get("UPDATOR") == null ? "" : d.get("UPDATOR") + "/" + d.get("UPDATOR_NAME"));
			}
			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type1(config);
			
			JSONArray json_rows = JSONArray.fromObject(userList, config);
			String json = "{\"total\":\""
					+ count
					+ "\",\"rows\":" + json_rows.toString() + "}";
			return json;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 翻页 排序参数
	 * @param request
	 * @param params
	 */
	private void datagridParamForAddUser(HttpServletRequest request, Map<String,Object> params) throws Exception {
		int pageNumber = StringUtils.isBlank(request.getParameter("page")) ? 1: Integer.parseInt(request.getParameter("page"));
		int pageSize = StringUtils.isBlank(request.getParameter("rows")) ? 10: Integer.parseInt(request.getParameter("rows"));
		
		int rowBegin = (pageNumber-1) * pageSize + 1;
		int rowEnd = (pageNumber-1) * pageSize + pageSize;
		String sortName = request.getParameter("sort");
		String sortOrder = StringUtils.isBlank(request.getParameter("order")) ? "asc" : request.getParameter("order");
		if (StringUtils.isNotBlank(sortName) && StringUtils.isNotBlank(sortOrder)) {
			params.put("orderStr", " order by " + sortName + " " + sortOrder + " ");
		} else {
			params.put("orderStr", " order by " + " CREATE_TIME " + " " + "asc" + " ");
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
	private void queryParamForAddUser(HttpServletRequest request, Map<String,Object> params) throws Exception {
		params.put("SEARCH_USER_NAME", request.getParameter("search_user_name"));
		params.put("SEARCH_LOGIN_USER_NAME", request.getParameter("search_login_user_name"));
		params.put("SEARCH_IS_ACTIVE", request.getParameter("search_is_active"));
	}
	
	@RequestMapping(value = "/saveRoleUser",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveRoleUser(HttpServletRequest request, ModelMap modelMap){
		JSONObject result = new JSONObject();
		TSysUser user = (TSysUser) request.getSession().getAttribute("user");
		try{
			//1 获取参数：角色ID，用户ID
			String roleId = request.getParameter("roleId");
			String userIdsStr = request.getParameter("userIds");
			String[] userIds = userIdsStr.split(",");
			
			TSysUser sessionUser = (TSysUser) request.getSession().getAttribute("user");
			//3保存用户角色
			for(String userId : userIds){
				if (StringUtils.isBlank(userId) || StringUtils.isBlank(roleId)) {
					continue;
				}
				TSysUserRole ur = new TSysUserRole(StringUtil.parseLong(userId), StringUtil.parseLong(roleId), sessionUser.getLoginUserName(), sessionUser.getLoginUserName());
				List<Map> rows = userRoleService.select_userRole(userId, roleId);
				if (rows != null && rows.size() > 0) {
					continue;
				}
				userRoleService.insert(ur);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("flag", false);
			result.put("msg", "保存失败");
			return result.toString();
		}
		return null;
	}
	
	
	/**
	 * 角色——系统绑定页面
	 * @param roleId
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/roleAppListPage/{roleId}")
	public String roleAppListPage(@PathVariable("roleId") String roleId, HttpServletRequest request, ModelMap modelMap) {
		try{
			modelMap.put("roleId", roleId);
			return "/sym/role/roleAppList";
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return "/errorpage/error";
		}
	}
	@RequestMapping(value = "/roleAppListData/{roleId}")
	@ResponseBody
	public String roleAppListData(@PathVariable("roleId") String roleId, HttpServletRequest request, ModelMap modelMap) {
		if (StringUtils.isBlank(roleId)) {
			return null;
		}
		try {
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("roleId", roleId);
			this.datagridRoleAppParam(request, params);
			this.queryRoleAppParam(request, params);
			// 2.遍历增加及转换自定义内容
			List<Map> rows = roleService.select_tSysAppListByRole(params);
			int count = roleService.select_tSysAppListByRole_count(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				d.put("REMOVE","<a href='javascript:return void(0);' onClick=\"removeApp('"+ d.get("ID") + "','"+ roleId + "');return false;\"><i class='icon-remove'></i></a>");
				/*d.put("DELETE","<a href='javascript:return void(0);' onClick=\"deleteUser('"+ d.get("ID")+ "');return false;\"><i class='icon-cancel'></i></a>");*/
				d.put("CREATER", d.get("CREATER") == null ? "" : d.get("CREATER") + "/" + d.get("CREATER_NAME"));
				d.put("UPDATER", d.get("UPDATER") == null ? "" : d.get("UPDATER") + "/" + d.get("UPDATER_NAME"));
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
			log.error(ex.getMessage(), ex);
			return null;
		}
	}
	
	/**
	 * 翻页 排序参数
	 * @param request
	 * @param params
	 */
	private void datagridRoleAppParam(HttpServletRequest request, Map<String,Object> params) throws Exception {
		int pageNumber = StringUtils.isBlank(request.getParameter("page")) ? 1: Integer.parseInt(request.getParameter("page"));
		int pageSize = StringUtils.isBlank(request.getParameter("rows")) ? 10: Integer.parseInt(request.getParameter("rows"));
		
		int rowBegin = (pageNumber-1) * pageSize + 1;
		int rowEnd = (pageNumber-1) * pageSize + pageSize;
		String sortName = request.getParameter("sort");
		String sortOrder = StringUtils.isBlank(request.getParameter("order")) ? "asc" : request.getParameter("order");
		if (StringUtils.isNotBlank(sortName) && StringUtils.isNotBlank(sortOrder)) {
			params.put("orderStr", " order by " + sortName + " " + sortOrder + " ");
		} else {
			params.put("orderStr", " order by " + "CREATE_TIME" + " " + "asc" + " ");
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
	private void queryRoleAppParam(HttpServletRequest request, Map<String,Object> params) throws Exception {
	}
	
	/**
	 * 移除APP
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/removeApp")
	@ResponseBody
	public String removeApp(HttpServletRequest request, ModelMap modelMap){
		String roleId = request.getParameter("removeRoleId123");
		String appId = request.getParameter("removeAppId");
		if (StringUtils.isBlank(appId) || StringUtils.isBlank(roleId)) {
			return null;
		}
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("roleId", roleId);
			params.put("appId", appId);
			this.roleService.delete_role_app(params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 跳转到添加APP页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/roleAppAddPage")
	public String addAppPage(HttpServletRequest request, ModelMap modelMap) {
		String roleId = request.getParameter("roleId");
		modelMap.put("roleId", roleId);
		return "/sym/role/roleAppAdd";
	}
	@RequestMapping(value = "/roleAppAddTree")
	@ResponseBody
	public String addAppTree(HttpServletRequest request, ModelMap modelMap) {
		String roleId = request.getParameter("roleId");
		try {
			List<Map> appList = roleService.select_appList(null);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("roleId", roleId);
			List<Map> roleAppList = roleService.select_appListByRole(params);
			List<Long> roleAppIds = new ArrayList<Long>();
			for (Map roleApp : roleAppList) {
				roleAppIds.add(StringUtil.parseLong(roleApp.get("APP_ID")));
			}
			JSONArray array = new JSONArray();
			for (Map app : appList) {
				JSONObject obj = new JSONObject();
				obj.put("id", app.get("ID"));
				obj.put("text", app.get("APP_NAME"));
				if (roleAppIds.contains(StringUtil.parseLong(app.get("ID")))) {
					obj.put("checked", true);
				} else {
					obj.put("checked", false);
				}
				array.add(obj);
			}
			return array.toString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 保存角色系统
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/saveRoleApp")
	@ResponseBody
	public String saveRoleApp(HttpServletRequest request, ModelMap modelMap) {
		JSONObject result = new JSONObject();
		TSysUser user = (TSysUser) request.getSession().getAttribute("user");
		try{
			//1 获取参数：角色ID，系统ID
			String roleId = request.getParameter("roleId3");
			String appIdsStr = request.getParameter("appIds");
			String[] appIds = appIdsStr.split(",");
			
			//2删除该角色原有系统
			roleService.delete_roleAppByRole(StringUtil.parseLong(roleId));
			
			//3保存分配的权限
			for(String appId : appIds){
			   TSysRoleApp roleApp = new TSysRoleApp();
			   roleApp.setAppId(StringUtil.parseLong(appId));
			   roleApp.setRoleId(StringUtil.parseLong(roleId));
			   roleApp.setCreator(user.getLoginUserName());
			   roleService.insert_sysRoleApp(roleApp);
			}
			log.info("保存系统成功");
		}catch(Exception e){
			log.error(e.getMessage(), e);
			result.put("flag", false);
			result.put("msg", "保存失败");
			return result.toString();
		}
		return null;
	}
	
	/**
	 * 角色——权限绑定页面
	 * @param roleId
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/rolePermListPage/{roleId}")
	public String rolePermListPage(@PathVariable("roleId") String roleId, HttpServletRequest request, ModelMap modelMap) {
		try{
			modelMap.put("roleId", roleId);
			return "/sym/role/rolePermList";
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return "/errorpage/error";
		}
	}
	
	@RequestMapping(value = "/getPermissionTreeDataByRoleId")
	@ResponseBody
	public String getPermissionTreeData(HttpServletRequest request, HttpServletResponse response) {
		Long roleId = StringUtil.parseLong(request.getParameter("roleId"));
		try{
			JSONArray permDataArray = new JSONArray();
			//查询所有的顶级权限
			List<TSysPermission> permList = permService.getTopPermissionList(null); 
			//查询角色的权限
			List<TSysPermission> rolePermList = permService.getPermissionByRole(roleId); 
			//将角色权限的ID放入数组
			List<Long> rolePermIdList = new ArrayList<Long>();
			if(rolePermList.size()>0){
				for(TSysPermission perm : rolePermList){
					rolePermIdList.add(perm.getId());
				}
			}
			for (TSysPermission perm : permList) {
				JSONObject obj = new JSONObject();
				obj.put("id", perm.getId());
				obj.put("text", perm.getPermName());
				
				JSONArray attributes = new JSONArray();
				JSONObject checkName = new JSONObject();
				checkName.put("name", "items");
				attributes.add(checkName);
				obj.put("attributes", attributes);
				/*if (rolePermIdList.contains(perm.getId())) {
					obj.put("checked", true);
				} else {
					obj.put("checked", false);
				}*/
				if (perm.getChildren().size() > 0) {
					obj.put("children", this.parsePermission(perm.getChildren(), rolePermIdList));
				}
				permDataArray.add(obj);
			}
			return permDataArray.toString();
		} catch (Exception e) {
			log.error("【系统异常】" + e.getMessage());
			e.printStackTrace();
			return null;
		} 
	}
	
	public String parsePermission(List<TSysPermission> permChildList, List<Long> rolePermIdList) {
		JSONArray array = new JSONArray();
		for (TSysPermission perm : permChildList) {
			JSONObject obj = new JSONObject();
			obj.put("id", perm.getId());
			obj.put("text", perm.getPermName());
			JSONArray attributes = new JSONArray();
			JSONObject checkName = new JSONObject();
			checkName.put("name", "items");
			attributes.add(checkName);
			obj.put("attributes", attributes);
			if (rolePermIdList.contains(perm.getId())) {
				obj.put("checked", true);
			} else {
				obj.put("checked", false);
			}
			if (perm.getChildren().size() > 0) {
				obj.put("children", this.parsePermission(perm.getChildren(), rolePermIdList));
			}
			array.add(obj);
		}
		return array.toString();
	}
	
	/**
	 * 保存角色权限
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/savePersessions",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String savePersessions(HttpServletRequest request, ModelMap modelMap) {
		JSONObject result = new JSONObject();
		TSysUser user = (TSysUser) request.getSession().getAttribute("user");
		try{
			//1 获取参数：角色ID，分配的权限ID
			String roleId = request.getParameter("roleId4");
			String permIdsStr = request.getParameter("permIds");
			String[] permIds = permIdsStr.split(",");
			
			//2删除该角色原有权限
			rolePermService.deleteByRole(StringUtil.parseLong(roleId));
			
			//3保存分配的权限
			for(String permId : permIds){
			   TSysRolePermission rp = new TSysRolePermission();
			   rp.setRoleId(StringUtil.parseLong(roleId));
			   rp.setPermId(StringUtil.parseLong(permId));
			   rp.setCreator(user.getLoginUserName());
			   rolePermService.insert(rp);
			}
			log.info("保存权限成功");
		}catch(Exception e){
			log.error(e.getMessage(), e);
			result.put("flag", false);
			result.put("msg", "保存失败");
			return result.toString();
		}
		return null;
	}
	
	@RequestMapping(value = "/addRolePage")
	public String addRolePage(){
		return "/sym/role/roleAdd";
	}
	
	@RequestMapping(value = "/addRole",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addRole(HttpServletRequest request, ModelMap modelMap){
		JSONObject result = new JSONObject();
		try {
			String roleName = request.getParameter("role_add_role_name");
//			String isActive = request.getParameter("role_add_is_active");
			/*List<Map> roleList = roleService.select_tSysRoleByRoleName(roleName);
			if (roleList != null && roleList.size() > 0) {
				throw new TppManagerException("角色名称已存在！");
			}*/
			TSysRole role = new TSysRole();
			role.setIsActive(IsActiveEnum.ACTIVE.getValue());
			role.setRoleName(roleName);
			String msg = Validate.getInstance().validate(role,
					InsertCheck.class, Default.class);
			if (StringUtils.isNotBlank(msg)) {
				throw new TppManagerException(msg);
			} 
			TSysUser user = (TSysUser) request.getSession().getAttribute("user");
			role.setCreator(user.getLoginUserName());
			roleService.insert(role);
			return result.toString();
		} catch (TppManagerException e) {
			result.put("flag", false);
			result.put("msg", e.getMessage());
			return result.toString();
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("flag", false);
			result.put("msg", "保存失败");
			return result.toString();
		}
	}
	@RequestMapping(value = "/editRolePage")
	public String editRolePage(HttpServletRequest request, ModelMap modelMap){
		String roleId = request.getParameter("roleId");
		TSysRole role = roleService.getRoleById(StringUtil.parseLong(roleId));
		modelMap.put("role", role);
		return "/sym/role/roleEdit";
	}
	
	@RequestMapping(value = "/editRole",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String editRole(HttpServletRequest request, ModelMap modelMap){
		JSONObject result = new JSONObject();
		try {
			String roleId = request.getParameter("edit_role_id");
			String roleName = request.getParameter("edit_role_role_name");
			TSysRole role = new TSysRole();
			role.setId(StringUtil.parseLong(roleId));
			role.setRoleName(roleName);
			role.setIsActive(IsActiveEnum.ACTIVE.getValue());
			String msg = Validate.getInstance().validate(role,
					InsertCheck.class, Default.class);
			if (StringUtils.isNotBlank(msg)) {
				throw new TppManagerException(msg);
			} 
			TSysUser user = (TSysUser) request.getSession().getAttribute("user");
			role.setUpdator(user.getLoginUserName());
			roleService.update(role);
		} catch (TppManagerException e) {
			result.put("flag", false);
			result.put("msg", e.getMessage());
			return result.toString();
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("flag", false);
			result.put("msg", "保存失败");
			return result.toString();
		}
		return result.toString();
	}
	
	@RequestMapping(value = "/deleteRole")
	@ResponseBody
	public String deleteRole(HttpServletRequest request, ModelMap modelMap){
		try {
			String roleId = request.getParameter("delete_roleId");
			//1删除该角色原有权限
			rolePermService.deleteByRole(StringUtil.parseLong(roleId));
			//2删除该角色绑定的用户
			roleService.delete_roleUserByRole(StringUtil.parseLong(roleId));
			//3删除该角色原有系统
			roleService.delete_roleAppByRole(StringUtil.parseLong(roleId));
			
			List<Long> list = new ArrayList<Long>();
			list.add(StringUtil.parseLong(roleId));
			roleService.batchDeleteRole(list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
}
