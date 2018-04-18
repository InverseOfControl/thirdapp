package com.zdmoney.manager.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdmoney.manager.Validate.InsertCheck;
import com.zdmoney.manager.Validate.Validate;
import com.zdmoney.manager.enumset.IsActiveEnum;
import com.zdmoney.manager.exception.TppManagerException;
import com.zdmoney.manager.models.TSysRole;
import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.models.TSysUserRole;
import com.zdmoney.manager.service.TSymRoleService;
import com.zdmoney.manager.service.TSymUserRoleService;
import com.zdmoney.manager.service.TSymUserService;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.MD5;
import com.zdmoney.manager.utils.Page;
import com.zdmoney.manager.utils.PermissionUtil;
import com.zdmoney.manager.utils.StringUtil;


/** 
 *
 * @author 渡心
 * @version 2014年12月18日 下午1:59:18 
 */
@Controller
@RequestMapping(value = "/sym/user")
public class TSymUserController {
	private final Logger log = Logger.getLogger(TSymUserController.class);
	
	@Autowired
	private TSymUserService userService;

	@Autowired
	private TSymRoleService roleService;

	@Autowired
	private TSymUserRoleService userRoleService;
	
	@Autowired
	private Page<TSysUser> page;
	
	@RequestMapping(value = "/userList")
	public String list(HttpServletRequest request, ModelMap modelMap){
		try{
			//权限验证
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/sym/user/userList")){
				return "/errorpage/permissionError";
			}
			
			return "/sym/user/userList";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	@RequestMapping(value = "/userListData")
	@ResponseBody
	public String listData(HttpServletRequest request, ModelMap modelMap){
		
		try {
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			this.datagridParam(request, params);
			this.queryParam(request, params);
			// 2.遍历增加及转换自定义内容
			List<Map> rows = userService.select_tSysUserList(params);
			int count = userService.select_tSysUserList_count(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处
				d.put("EDIT","<a href='javascript:return void(0);' onClick=\"editUser('"+ d.get("ID")+ "');return false;\"><i class='icon-edit'></i></a>");
				/*d.put("DELETE","<a href='javascript:return void(0);' onClick=\"deleteUser('"+ d.get("ID")+ "');return false;\"><i class='icon-cancel'></i></a>");*/
				d.put("IS_ACTIVE", d.get("IS_ACTIVE") == null ? "" : IsActiveEnum.getEnumDesc(d.get("IS_ACTIVE").toString()));
				d.put("VIEW_ROLE","<a href='javascript:return void(0);' onClick=\"viewRole('"+ d.get("ID")+ "');return false;\"><i class='icon-search'></i></a>");
				d.put("CREATOR", d.get("CREATOR") == null ? "" : d.get("CREATOR") + "/" + d.get("CREATOR_NAME"));
				d.put("UPDATOR", d.get("UPDATOR") == null ? "" : d.get("UPDATOR") + "/" + d.get("UPDATOR_NAME"));
				//用户角色信息
				List<TSysRole> roles = roleService.getRolesByUser(Long.valueOf(String.valueOf(d.get("ID"))));
				String roleStr = "";
				if (roles != null && roles.size() > 0) {
					StringBuffer roleBuffer = new StringBuffer();
					for(TSysRole role : roles){
						roleBuffer.append(role.getRoleName()).append(",");
					}
					roleStr = StringUtil.parseString(roleBuffer, "");
					roleStr = roleStr.substring(0, roleStr.length()-1); 
				}
				d.put("USER_ROLE", roleStr);
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
	private void queryParam(HttpServletRequest request, Map<String,Object> params) throws ParseException {
		params.put("SEARCH_USER_NAME", request.getParameter("search_user_name"));
		params.put("SEARCH_LOGIN_USER_NAME", request.getParameter("search_login_user_name"));
		params.put("SEARCH_IS_ACTIVE", request.getParameter("search_is_active"));
	}
	
	/**
	 * 页面跳转-新增用户页面
	 * 
	 */
	@RequestMapping(value = "/addUserPage")
	public String addUserPage(HttpServletRequest request, ModelMap modelMap){
		try{
			return "/sym/user/userAdd";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	/**
	 * 新增用户
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/addUser",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addUser(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException{
		String loginName = request.getParameter("user_add_login_user_name").trim();
		String userName = request.getParameter("user_add_user_name").trim();
		String email = request.getParameter("user_add_email").trim();
		String phone = request.getParameter("user_add_phone_no").trim();
		String isActive = request.getParameter("user_add_is_active").trim();
		JSONObject result = new JSONObject();
		try {
			TSysUser isExistUser = userService.getByLoginName(loginName);
			if (isExistUser != null) {
				throw new TppManagerException("用户已存在！");
			}
			//默认密码
			String password = MD5.MD5Encode("888888");

			TSysUser sessionUser = (TSysUser) request.getSession().getAttribute("user");
			TSysUser user = new TSysUser();
			user.setLoginUserName(loginName);
			user.setUserName(userName);
			user.setPassword(password);
			user.setEmail(email);
			user.setPhoneNo(phone);
			user.setIsActive(isActive);
			user.setCreator(sessionUser.getLoginUserName());
			user.setUpdator(sessionUser.getLoginUserName());
			String msg = Validate.getInstance().validate(user,
					InsertCheck.class, Default.class);
			if (StringUtils.isNotBlank(msg)) {
				throw new TppManagerException(msg);
			}
			userService.insert(user);
		} catch (TppManagerException ex) {
			result.put("msg", ex.getMessage());
			result.put("result", false);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			result.put("msg", "新增用户失败！");
			result.put("result", false);
		}
		return result.toString();
	}
	
	/**
	 * 页面跳转——用户编辑页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/editUserPage")
	public String editUserPage(HttpServletRequest request, ModelMap modelMap) {
		try{
			String userId = request.getParameter("userId");
			TSysUser user = userService.getUserById(StringUtil.parseLong(userId));
			user.setPassword("");
			modelMap.put("user", user);
			return "sym/user/userEdit";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
		
	}
	/**
	 * 修改用户
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/editUser",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String editUser(TSysUser user,HttpServletRequest request, ModelMap modelMap) {
		JSONObject result = new JSONObject();
		try{
			if (StringUtils.isBlank(user.getIsActive())) {
				throw new TppManagerException("请选择是否有效！");
			}
			TSysUser saveUser = new TSysUser();
			saveUser.setId(user.getId());
			saveUser.setUserName(user.getUserName().trim());
			/*if (StringUtils.isNotBlank(user.getPassword().trim())) {
				saveUser.setPassword(MD5.MD5Encode(user.getPassword()));
			}*/
			saveUser.setEmail(user.getEmail().trim());
			saveUser.setPhoneNo(user.getPhoneNo().trim());
			saveUser.setIsActive(user.getIsActive().trim());
			TSysUser sessionUser = (TSysUser) request.getSession().getAttribute("user");
			saveUser.setUpdator(sessionUser.getLoginUserName());
			userService.update(saveUser);
		} catch(TppManagerException e){
			result.put("msg", e.getMessage());
			result.put("flag", false);
		} catch(Exception e){
			result.put("msg", "编辑用户失败");
			result.put("flag", false);
		}
		return result.toString();
	}
	/**
	 * 页面跳转——用户编辑页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/editUserPwdPage")
	public String editUserPwdPage(HttpServletRequest request, ModelMap modelMap) {
		try{
			String userId = request.getParameter("userId");
			modelMap.put("userId", userId);
			return "sym/user/userEditPwd";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
		
	}
	/**
	 * 修改用户密码
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/editUserPwd",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String editUserPwd(HttpServletRequest request, ModelMap modelMap) {
		JSONObject result = new JSONObject();
		try{
			String userId = request.getParameter("userId").trim();
			String pwd = request.getParameter("password").trim();
			if (StringUtils.isBlank(userId)) {
				throw new TppManagerException("用户密码修改错误！");
			}
			if (StringUtils.isBlank(pwd)) {
				throw new TppManagerException("密码不能为空！");
			}
			
			TSysUser saveUser = new TSysUser();
			saveUser.setId(StringUtil.parseLong(userId));
			saveUser.setPassword(MD5.MD5Encode(pwd));
			TSysUser sessionUser = (TSysUser) request.getSession().getAttribute("user");
			saveUser.setUpdator(sessionUser.getLoginUserName());
			userService.update(saveUser);
		} catch(TppManagerException e){
			result.put("msg", e.getMessage());
			result.put("flag", false);
		} catch(Exception e){
			log.error(e.getMessage(), e);
			result.put("msg", "编辑密码失败");
			result.put("flag", false);
		}
		return result.toString();
	}
	/**
	 * 系统用户编辑页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/userEditUI/{id}")
	public String editUI(@PathVariable("id") String id,HttpServletRequest request, ModelMap modelMap) {
		TSysUser user = null;
		if(!StringUtil.isEmpty(id)){
			//权限验证
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/sym/user/editUser")){
				return "/errorpage/permissionError";
			}
			
			user = userService.getUserById(StringUtil.parseLong(id));
			//查询当前用户的角色
			List<TSysRole> userRoleList = roleService.getRolesByUser(user.getId());
			modelMap.put("userRoleList", userRoleList);
		}else{
			//权限验证
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/sym/user/addUser")){
				return "/errorpage/permissionError";
			}
			
			user = new TSysUser();
		}
		if(null != user){
			modelMap.put("user", user);
		}
		
		//查询除分配给用户的其他角色
		List<TSysRole> roleList = roleService.getOtherRoles(user.getId());
		modelMap.put("roleList", roleList);
		
		return "sym/user/userEdit";
	}
	
	/**
	 * 保存用户信息
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/userSave",produces = "text/html;charset=UTF-8")
	public String userSave(@ModelAttribute("userSave") TSysUser user, @RequestParam("id") String id, @RequestParam(value="selectedRoles", required=false) List<Long> roles, HttpServletRequest request, ModelMap modelMap) {
		try{
			//权限验证
			HttpSession session = request.getSession();
			if(!PermissionUtil.isHavePermission(session, "/sym/user/saveUser")){
				return "/errorpage/permissionError";
			}
			
			TSysUser sessionUser = (TSysUser) request.getSession().getAttribute("user");
			Long userId = null;
			if(StringUtil.isEmpty(id)){
				//新增
				user.setPassword(MD5.MD5Encode("888888")); //设置初始密码为888888
				//封装WHO字段
				user.setCreator(sessionUser.getLoginUserName());
				user.setUpdator(sessionUser.getLoginUserName());
				userService.insert(user);
				userId = user.getId();
			}else{
				//修改
				//封装WHO字段
				userId = StringUtil.parseLong(id);
				user.setUpdator(sessionUser.getLoginUserName());
				userService.update(user);
				userRoleService.deleteByUserId(userId);
			} 
			for(Long roleId : roles){
				TSysUserRole ur = new TSysUserRole(userId, roleId, sessionUser.getLoginUserName(), sessionUser.getLoginUserName());
				userRoleService.insert(ur);
			}
			log.info("保存成功");
			return "redirect:/sym/user/userList";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	
	@RequestMapping(value = "/modifyPasswordPage")
	public String modifyPasswordPage(HttpServletRequest request, ModelMap modelMap){
		return "/sym/user/modifyPwd";
	}
	
	/**
	 * 用户密码修改
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/modifyPassword",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String modifyPassword(HttpServletRequest request, ModelMap modelMap) {
		JSONObject result = new JSONObject();
		try{
			TSysUser sessionUser = (TSysUser) request.getSession().getAttribute("user");
			String oldPassword = request.getParameter("old_password").trim();
			String newPassword = request.getParameter("new_password").trim();
			String confirmPassword = request.getParameter("new_password_repeat");
			
			if (!MD5.MD5Encode(oldPassword).equals(sessionUser.getPassword())) {
				throw new TppManagerException("原密码错误，不能修改密码！");
			}
			if (!newPassword.equals(confirmPassword)) {
				throw new TppManagerException("二次密码输入不一致，请重新输入！");
			}
			TSysUser user = new TSysUser();
			user.setId(sessionUser.getId());
			user.setPassword(MD5.MD5Encode(newPassword));
			user.setUpdator(sessionUser.getCreator());
			userService.update(user);
		} catch(TppManagerException e){
			result.put("msg", e.getMessage());
			result.put("flag", false);
			return result.toString();
		} catch(Exception e){
			log.error(e.getMessage(), e);
			result.put("msg", "编辑失败");
			result.put("flag", false);
			return result.toString();
		}
		return result.toString();
	}
	
	@RequestMapping(value = "/userRolePage")
	public String userRolePage(HttpServletRequest request, ModelMap modelMap){
		String userId = request.getParameter("userId");
		modelMap.put("userId", userId);
		return "/sym/user/userRole";
	}
	
	@RequestMapping(value = "/userRoleTree")
	@ResponseBody
	public String userRoleTree(HttpServletRequest request, ModelMap modelMap){
		String userId = request.getParameter("userId");
		try {
			List<TSysRole> userRoleList = roleService.getRolesByUser(StringUtil.parseLong(userId));
			JSONArray array = new JSONArray();
			for (TSysRole role : userRoleList) {
				JSONObject obj = new JSONObject();
				obj.put("id", role.getId());
				obj.put("text", role.getRoleName());
				obj.put("checked", false);
				array.add(obj);
			}
			return array.toString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
