package com.zdmoney.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zdmoney.manager.common.Constant;
import com.zdmoney.manager.models.TSysPermission;
import com.zdmoney.manager.models.TSysRole;
import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.service.TSymPermissionService;
import com.zdmoney.manager.service.TSymRoleService;
import com.zdmoney.manager.service.TSymUserService;
import com.zdmoney.manager.utils.JackJsonUtil;
import com.zdmoney.manager.utils.StringUtil;

/** 
 * 用户登录管理
 * @author LeiJun
 * @version 2014年11月12日 下午1:50:32 
 */
@Controller
@RequestMapping(value = "/zdmoney")
public class TZdpayLoginController {
private final Logger log = Logger.getLogger(TZdpayLoginController.class);
	
	@Autowired
	private TSymUserService userService;
	
	@Autowired
	private TSymRoleService roleService;
	
	@Autowired
	private TSymPermissionService permService;
	
	@RequestMapping(value = "/main")
	public String main(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
		return "/main";
	}
	
	@RequestMapping(value = "/login")
	public String loginUI(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
		try{
			//判断是否已登录
			HttpSession session = request.getSession();
			TSysUser user = (TSysUser) session.getAttribute("user");
			if(user != null){
				user = userService.getByLoginNameAndPassword(user.getLoginUserName(), user.getPassword());
				if (user == null) {
					return "/login";
				}
			} else {
				return "/login";
			}
			return "/main";
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
		
	}
	@RequestMapping(value = "/forward")
	public String forward(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
		return "/forward";
	}
	@RequestMapping(value = "/loginPage")
	public String login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
		try{
			//判断是否已登录
			HttpSession session = request.getSession();
			TSysUser user = (TSysUser) session.getAttribute("user");
			if (user != null) {
				user = userService.getByLoginNameAndPassword(user.getLoginUserName(), user.getPassword());
				if(user != null){
					return "/main";
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
		String loginUserName = request.getParameter("userName");
		String password = request.getParameter("userPwd");
		
		if (loginUserName == null && password == null) {
			return "/login";
		}
		
		TSysUser user = userService.getByLoginNameAndPassword(loginUserName, password);
		
		modelMap.put("loginUserName", loginUserName);
		if(null != user){
			if(Constant.ACTIVE_TYPE_NO.equals(user.getIsActive())){
				modelMap.put("errorMsg", "登录用户已失效");
				return "/login";
			}
			
			request.getSession().invalidate();//清空session
			Cookie cookie = request.getCookies()[0];//获取cookie
			cookie.setMaxAge(0);//让cookie过期
			
			List<TSysRole> roles = roleService.getRolesByUser(user.getId());
			user.setRoles(roles);
			//存放角色ID集合
			List<Long> roleIds = new ArrayList<Long>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			for(TSysRole role : roles){
				List<TSysPermission> permList = permService.getPermissionByRole(role.getId());
				for(TSysPermission perm : permList){
					map.put(perm.getId().toString(), perm.getPermUrl());
				}
				roleIds.add(role.getId());
			}
			
			List<String> appIds = new ArrayList<String>();
			if (roleIds != null && roleIds.size() > 0) {
				appIds = roleService.select_appIdsByRoles(roleIds);
			}
			user.setAppIds(appIds);
			request.getSession().setAttribute("permMap", map);
			request.getSession().setAttribute("user", user);
			log.info("【用户"+user.getLoginUserName()+"的权限】:"+map);
			
			//查询所有的顶级菜单
			List<TSysPermission> permList = permService.getTopPermissionList(Constant.PERM_TYPE_MENU); 
			//遍历顶级菜单，去除没有权限的子菜单，拼装前台菜单功能所需的json对象
			for(TSysPermission perm : permList){
				perm.setPermUrl(StringUtil.htmlEscape(perm.getPermUrl()));
				List<TSysPermission> children = perm.getChildren();
				if(!StringUtil.isEmpty(children) && children.size()>0){
					//必须用迭代器删除，否则会保存，list不可以边循环边删除元素
					Iterator<TSysPermission> iter = children.iterator();
					while(iter.hasNext()){
						TSysPermission child = iter.next();
						child.setPermUrl(StringUtil.htmlEscape(child.getPermUrl()));
						//如果子菜单对当前用户没有权限，移出该菜单
						if(StringUtil.isEmpty(map.get(child.getId().toString())) || Constant.PERM_TYPE_FUNCTION.equals(child.getPermType())){
							iter.remove();
						}
					}
				}
			}
			Iterator<TSysPermission> parentIter = permList.iterator();
			//如果没有子权限，移除父权限
			while(parentIter.hasNext()){  
				TSysPermission temp = parentIter.next();  
				if(StringUtil.isEmpty(temp.getChildren()) || temp.getChildren().size()==0){
					parentIter.remove();  
				}  
			}
			try {
				request.getSession().setAttribute("perms", JackJsonUtil.objToStr(permList));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
			}
			
			request.getSession().setAttribute("permList", permList);
			return "/main";
		}else{
			modelMap.put("errorMsg", "用户名或密码错误，请重新输入");
			return "/login";
		}
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
		request.getSession().removeAttribute("user");
		return "/login";
	}
}
