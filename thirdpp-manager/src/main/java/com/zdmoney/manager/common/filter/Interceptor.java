package com.zdmoney.manager.common.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/** 
 * Spring MVC拦截器 
 * @author gary 
 * 
 */ 
public class Interceptor extends HandlerInterceptorAdapter implements
		InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(Interceptor.class);

	 /** 
     * 在系统启动时执行 
     */
	public void afterPropertiesSet() throws Exception {
		logger.debug("=======初始化Interceptor拦截器=========");
	}
	
	/** 
     * 在Controller方法前进行拦截 
     * 如果返回false 
     *      从当前拦截器往回执行所有拦截器的afterCompletion方法,再退出拦截器链. 
     * 如果返回true 
     *      执行下一个拦截器,直到所有拦截器都执行完毕. 
     *      再运行被拦截的Controller. 
     *      然后进入拦截器链,从最后一个拦截器往回运行所有拦截器的postHandle方法. 
     *      接着依旧是从最后一个拦截器往回执行所有拦截器的afterCompletion方法. 
     */  
    @Override  
    public boolean preHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception {  
    	//在preHandle中，可以进行编码、安全控制等处理
    	logger.debug("=====preHandle====");  
        //业务逻辑  
        System.out.println(request.getParameterMap());
        System.out.println(request.getParameterValues("siteKey"));
        return true;  
    }  
  
    @Override  
    public void postHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler,  
            ModelAndView modelAndView) throws Exception {  
    	logger.debug("==========postHandle=========");  
        //在postHandle中，有机会修改ModelAndView； 
        if(modelAndView != null){  
            String viewName = modelAndView.getViewName();  
            logger.debug("view name : " + viewName);  
        }else{  
        	logger.debug("view is null");  
        }  
    }  
      
    /** 
     * 在Controller方法后进行拦截 
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有拦截器的afterCompletion方法 
     */  
    @Override  
    public void afterCompletion(HttpServletRequest httpservletrequest,  
            HttpServletResponse httpservletresponse, Object obj,  
            Exception exception) throws Exception {  
//    	在afterCompletion中，可以根据ex是否为null判断是否发生了异常，进行日志记录。 
    	
    	logger.debug("=====afterCompletion====");  
    }  

}
