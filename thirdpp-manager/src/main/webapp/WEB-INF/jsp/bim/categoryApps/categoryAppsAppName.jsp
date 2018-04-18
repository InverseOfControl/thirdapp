<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>证大财富统一支付管理系统</title>
		<meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@ include file="/common/jsCssInclude.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
<body >
		<ul id="roleTree"></ul>
		<input id="code" name="code" type="hidden" value="${code }"/>
<script>
jQuery(function($){
	$('#roleTree').tree({    
	    url:'${path}/bim/categoryApp/appTree',   
    	onBeforeLoad:function(node, param){param.code=$('#code').val();},
	    checkbox: false
	});  
});

</script>
</body>
</html>	 