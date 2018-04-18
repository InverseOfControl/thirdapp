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
		<ul id="appTree"></ul>
        <input type="hidden" id="checkAll" value="false"/>
<script>
jQuery(function($){
	$('#appTree').tree({    
	    url:'${path}/sym/role/roleAppAddTree',   
    	onBeforeLoad:function(node, param){param.roleId=$('#roleId3').val()},
	    checkbox: true
	});  
});

function oncheckAll(){
   if ($('#checkAll').val()!="true") {
	   var nodes = $('#appTree').tree('getChecked', 'unchecked');	// 获取未选择节点
	   for (var i=0;i<nodes.length;i++) {
		   $('#appTree').tree('check', nodes[i].target);//将得到的节点选中
	   }
	   $('#checkAll').val("true");
   } else {
	   var nodes = $('#appTree').tree('getChecked');	// get checked nodes
	   for (var i=0;i<nodes.length;i++) {
		   $('#appTree').tree('uncheck', nodes[i].target);//将得到的节点选中
	   }
	   $('#checkAll').val("false");
   }
}

</script>
</body>
</html>