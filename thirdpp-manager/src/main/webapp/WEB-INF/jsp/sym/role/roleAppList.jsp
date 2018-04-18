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
<body class="easyui-layout">
		<form id="removeAppForm" name="removeAppForm" method="post">
			
			<input id="removeAppId" name="removeAppId" type="hidden"/>
			<input id="removeRoleId123" name="removeRoleId123" type="hidden"/>
		</form>
		<form id="roleAppSaveFrom" name="roleAppSaveFrom" method="post">
        	<input id="appIds" name="appIds" type="hidden"/>
        	<input id="roleId3" name="roleId3" type="hidden" value="${roleId }"/>
        </form>
			<!--搜索结果开始-->
				<input id="roleId123" name="roleId123" type="hidden" value="${roleId }"/>
				<table  id="dg_tppSysApp" class="easyui-datagrid" 
					data-options="rownumbers:true,pageSize:20,singleSelect:true,selectOnCheck: 'true',checkOnSelect:'true',collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/sym/role/roleAppListData/${roleId }',method:'post',toolbar:toolbar,fit:'true',onClickRow: function (rowIndex, rowData) {$(this).datagrid('unselectRow', rowIndex);}">
					<thead>
						<tr>
						 	<!-- <th data-options="field:'REMOVE',width:50,align:'center'">移除</th> -->
						 	<th data-options="field:'ck',checkbox:'true'"></th>
							<th data-options="field:'ID',width:120,sortable:'true',align:'left',hidden:true">ID</th>
							<th data-options="field:'APP_NAME',width:120,sortable:'true',align:'left'">业务系统名称</th>
							<th data-options="field:'APP_CODE',width:120,sortable:'true',align:'left'">业务系统编号</th>
							<th data-options="field:'CONTACT_NAME',width:120,sortable:'true',align:'left'">联系人姓名</th>
							<th data-options="field:'CONTACT_MOBILE',width:120,sortable:'true',align:'left'">联系人手机</th>
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'left'">创建时间</th>
							<th data-options="field:'CREATER',width:120,sortable:'true',align:'left'">创建人</th>
							<th data-options="field:'UPDATE_TIME',width:120,sortable:'true',align:'left'">修改时间</th>
							<th data-options="field:'UPDATER',width:120,sortable:'true',align:'left'">修改人</th>
						</tr>
					</thead>
				</table>
			<!--搜索栏结果end-->
		
<script>
var tppSysApp_list ={};
var toolbar = [{
				id : 'app_add_btn',
				text : '添加系统',
				iconCls : 'icon-add',
				handler:function(){
					var roleId = $('#roleId123').val();
					$('<div id="dialog_holder"></div>').dialog({
					    title: '添加系统',
					    width: 600,
					    height: 300,
					    href: '${path}/sym/role/roleAppAddPage',
					    modal: true,
					    method: "POST",
					    params:{roleId:roleId},
						onClose: function(){
							$(this).dialog("destroy");
						},
					    buttons: [{
					    	text: "全选/反选",
					    	handler: function(e){
					    		oncheckAll();
					    	}
					    },{
					    	text: "提  交",
					    	handler: function(e){
					    		saveApp();
					    		$('#dialog_holder').dialog('close');
					    	}
					    },{
					    	text: "关闭",
					    	handler: function(e){
					    		$(this).dialog("close");
					    	}
					    }]
					});
				}
			},{
				id : 'app_remove_btn',
				text : '移除系统',
				iconCls : 'icon-cancel',
				handler:function(){
					var row = $('#dg_tppSysApp').datagrid('getSelected');
					if (row == null) {
						$.messager.alert('提示','请先选择要移除的系统！');
						return;
					}
					var appId = row.ID;
					var roleId = $('#roleId123').val();
					removeApp(appId,roleId)
				}
			}];
			
function removeApp(appId,roleId) {
	$.messager.confirm('确认','您确认想要移除该记录吗？',function(r){    
	    if (r){    
			$('#removeRoleId123').val(roleId);
			$('#removeAppId').val(appId);
			
			$('#removeAppForm').form({   
			 	url:'${path}/sym/role/removeApp',    
				     onSubmit: function(){
				       $.messager.progress(); 
				        // do some check       
				        // return false to prevent submit;    
				     },    
				     success:function(data){
				       //do some
				        $.messager.progress('close');
						$('#dg_tppSysApp').datagrid('reload');
				     }
			}); 
			$('#removeAppForm').submit();
	    }    
	}); 
}
/**
 * 保存app
 */
function saveApp(){
	var nodes = $('#appTree').tree('getChecked');
	var appIds = '';
	for (var i = 0;i < nodes.length; i++) {
		appIds += nodes[i].id + ",";
	}
	$('#appIds').val(appIds);
	$('#roleAppSaveFrom').form({   
	 	url:'${path}/sym/role/saveRoleApp',    
		     onSubmit: function(){
		       $.messager.progress(); 
		        // do some check       
		        // return false to prevent submit;    
		     },    
		     success:function(data){
		       //do some
		        $.messager.progress('close');
				$('#dg_tppSysApp').datagrid('reload');
		     }
	}); 
	$('#roleAppSaveFrom').submit();
}
jQuery(function($){
	//重新按照条件刷新查询内容

});

</script>
</body>
</html>