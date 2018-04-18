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
	        <div align="left">
		        <div id="tb" style="padding:5px;height:auto">    
				    <div style="margin-bottom:5px">    
				        <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="savePersessions()">保存权限</a>
				        <input title="全选/反选" type="checkbox" onclick="oncheckAll()" id="checkAll"/><a href="#" class="easyui-linkbutton" plain="true" onclick="oper()">全选/反选 </a>
				    </div>
				</div>
				<form id="treeForm" name="treeForm">
					<ul id="roleTree" ></ul>  
				</form>
				<form id="rolePermSaveFrom" name="rolePermSaveFrom" method="post">
		        	<input id="permIds" name="permIds" type="hidden"/>
		        	<input id="roleId4" name="roleId4" type="hidden" value="${roleId }"/>
		        </form>
	        </div>
<script>
/**
 * 保存分配的权限
 */
function savePersessions(){
	$.messager.confirm('确认','您确认想要修改权限吗？',function(r){    
	    if (r){    
			var nodes = $('#roleTree').tree('getChecked',['checked','indeterminate']);
			var permIds = '';
			for (var i = 0;i < nodes.length; i++) {
				permIds += nodes[i].id + ",";
			}
			$('#permIds').val(permIds);
			$('#rolePermSaveFrom').form({   
			 	url:'${path}/sym/role/savePersessions',    
				     onSubmit: function(){
				       $.messager.progress(); 
				        // do some check       
				        // return false to prevent submit;    
				     },    
				     success:function(data){
				       //do some
				        $.messager.progress('close');
						$('#roleTree').tree('reload');
				     }
			}); 
			$('#rolePermSaveFrom').submit();
	    }    
	});  
}

jQuery(function($){
	$('#roleTree').tree({    
	    url:'${path}/sym/role/getPermissionTreeDataByRoleId' ,
	    onBeforeLoad:function(node, param){param.roleId=$('#roleId4').val()},
	    cascadeCheck:true,
	    checkbox: true
	    
	}); 
});
function oper(){
	if ($('#checkAll').attr("checked")) {
		$('#checkAll').attr("checked",false);
	} else {
		$('#checkAll').attr("checked",true);
	}
	oncheckAll();
}
function oncheckAll(){
   if ($('#checkAll').attr("checked")) {
	   var nodes = $('#roleTree').tree('getChecked', 'unchecked');	// 获取未选择节点
	   for (var i=0;i<nodes.length;i++) {
		   $('#roleTree').tree('check', nodes[i].target);//将得到的节点选中
	   }
   } else {
	   var nodes = $('#roleTree').tree('getChecked');	// get checked nodes
	   for (var i=0;i<nodes.length;i++) {
		   $('#roleTree').tree('uncheck', nodes[i].target);//将得到的节点选中
	   }
   }
}
</script>
</body>
</html>