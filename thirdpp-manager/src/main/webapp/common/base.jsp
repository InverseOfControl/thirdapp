<%@page import="com.zdmoney.manager.utils.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%
	//设置页面全局变量
	String path = request.getContextPath();
	session.setAttribute("path", path);
	
	String defaultDate = StringUtil.formatDate(new Date(), "yyyy-MM-dd");
	request.setAttribute("defaultDate", defaultDate);
%>
