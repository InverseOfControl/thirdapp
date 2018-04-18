package com.zdmoney.manager.common.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.service.TSymPermissionService;

/**
 * 
 * @author 渡心
 * @version 2014年11月26日 上午10:37:12
 */
public class CheckPrivilegeIntercepter extends HandlerInterceptorAdapter
		implements InitializingBean {
	private static final Logger logger = LoggerFactory
			.getLogger(Interceptor.class);
	
	@Autowired
	private TSymPermissionService permService;

	public String[] allowUrls;// 还没发现可以直接配置不拦截的资源，所以在代码里面来排除

	public void setAllowUrls(String[] allowUrls) {
		this.allowUrls = allowUrls;
	}

	/**
	 * 在系统启动时执行
	 */
	public void afterPropertiesSet() throws Exception {
		logger.debug("=======初始化权限检查拦截器=========");
	}

	/**
	 * 在Controller方法前进行拦截 如果返回false 从当前拦截器往回执行所有拦截器的afterCompletion方法,再退出拦截器链.
	 * 如果返回true 执行下一个拦截器,直到所有拦截器都执行完毕. 再运行被拦截的Controller.
	 * 然后进入拦截器链,从最后一个拦截器往回运行所有拦截器的postHandle方法.
	 * 接着依旧是从最后一个拦截器往回执行所有拦截器的afterCompletion方法.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 在preHandle中，可以进行编码、安全控制等处理
		logger.debug("=====preHandle====");
		// 业务逻辑
		// 获取当前登陆用户
		TSysUser user = (TSysUser) request.getSession().getAttribute("user");
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), ""); // 访问的URL
		if (null != allowUrls && allowUrls.length >= 1)
			for (String url : allowUrls) {
				if (requestUrl.contains(url)) {
					return true;
				}
			}
		// 1、用户未登录

		if (user == null) {
			// >>使用的是登陆功能，就放行
			// >>使用的不是登陆功能，就转到登陆页面
			response.sendRedirect(request.getContextPath() + "/zdmoney/forward");
//			request.getRequestDispatcher(request.getContextPath() + "/zdmoney/loginUI").forward(request, response);
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.debug("==========postHandle=========");
		// 在postHandle中，有机会修改ModelAndView；
		if (modelAndView != null) {
			String viewName = modelAndView.getViewName();
			logger.debug("view name : " + viewName);
		} else {
			logger.debug("view is null");
		}
	}

	/**
	 * 在Controller方法后进行拦截 当有拦截器抛出异常时,会从当前拦截器往回执行所有拦截器的afterCompletion方法
	 */
	@Override
	public void afterCompletion(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse, Object obj,
			Exception exception) throws Exception {
		// 在afterCompletion中，可以根据ex是否为null判断是否发生了异常，进行日志记录。

		logger.debug("=====afterCompletion====");
	}

}
