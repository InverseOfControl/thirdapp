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
				<input id="roleId" name="roleId" type="hidden" value="${roleId }"/>
			<!--搜索结果开始-->
				<table  id="dg_tSysUser" class="easyui-datagrid" 
					data-options="rownumbers:true,pageSize:20,singleSelect:true,selectOnCheck: 'true',checkOnSelect:'true',collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/sym/role/roleUserListData/${roleId }',method:'post',toolbar:toolbar,fit:'true',onClickRow: function (rowIndex, rowData) {$(this).datagrid('unselectRow', rowIndex);}">
					<thead>
						<tr>
							<!-- <th data-options="field:'REMOVE',width:50,align:'center'">移除</th> -->
							<!-- <th data-options="field:'DELETE',width:50,align:'center'">删除</th> -->
							<th data-options="field:'ck',checkbox:'true'"></th>
							<th data-options="field:'ID',width:120,sortable:'true',align:'left',hidden:true">ID</th>
							<th data-options="field:'USER_NAME',width:120,sortable:'true',align:'left'">用户名</th>
							<th data-options="field:'LOGIN_USER_NAME',width:120,sortable:'true',align:'left'">登录名</th>
							<th data-options="field:'EMAIL',width:120,sortable:'true',align:'left'">电子邮箱</th>
							<th data-options="field:'PHONE_NO',width:120,sortable:'true',align:'left'">电话号码</th>
							<!-- <th data-options="field:'USER_ROLE',width:120,sortable:'true',align:'left'">角色</th> -->
							<th data-options="field:'CREATOR',width:120,sortable:'true',align:'left'">创建者</th>
							<th data-options="field:'UPDATOR',width:120,sortable:'true',align:'left'">更新者</th>
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'left'">创建时间</th>
							<th data-options="field:'UPDATE_TIME',width:120,sortable:'true',align:'left'">更新时间</th>
							<th data-options="field:'IS_ACTIVE',width:120,sortable:'true',align:'left'">是否有效</th>
							<th data-options="field:'VIEW_ROLE',width:120,sortable:'true',align:'left'">查看角色</th>
						</tr>
					</thead>
				</table>
			<!--搜索栏结果end-->
		<form id="removeUserForm" name="removeUserForm" method="post">
			<input id="removeRoleId" name="removeRoleId" type="hidden"/>
			<input id="removeUserId" name="removeUserId" type="hidden"/>
		</form>
		<form id="roleUserSaveFrom" name="roleUserSaveFrom" method="post" action="${path}/sym/role/saveRoleUser">
			<input type="hidden" id="roleId" name="roleId" value="${roleId }"/>
			<input type="hidden" id="userIds" name="userIds"/>
		</form>
<script>
var tSysUser_list ={};
var toolbar = [{
				id : 'user_add',
				text : '添加用户',
				iconCls : 'icon-add',
				handler:function(){
					var roleId = $('#roleId').val();
					$('<div id="dialog_holder"></div>').dialog({
					    title: '添加用户',
					    width: 600,
					    height: 300,
					    href: '${path}/sym/role/addRoleUserPage',
					    modal: true,
					    method: "POST",
					    params:{roleId:roleId},
						onClose: function(){
							$(this).dialog("destroy");
						},
					    buttons: [{
					    	text: "提  交",
					    	handler: function(e){
					    		saveUser();
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
				id : 'user_remove_btn',
				text : '移除用户',
				iconCls : 'icon-cancel',
				handler:function(){
					
					var row = $('#dg_tSysUser').datagrid('getSelected');
					if (row == null) {
						$.messager.alert('提示','请先选择要移除的用户！');
						return;
					}
					var userId = row.ID;
					var roleId = $('#roleId').val();
					removeUser(userId,roleId);
				}
			}];
/**
 * 保存user
 */
function saveUser(){
	var rows = $('#dg_tSysUserAdd').datagrid('getSelections');
	var userIds = '';
	for (var i = 0;i < rows.length; i++) {
		userIds += rows[i].ID + ",";
	}
	$('#userIds').val(userIds);
	$('#roleUserSaveFrom').form({   
	 	url:'${path}/sym/role/saveRoleUser',    
		     onSubmit: function(){
		       $.messager.progress(); 
		        // do some check       
		        // return false to prevent submit;    
		     },    
		     success:function(data){
		       //do some
		        $.messager.progress('close');
				$('#dg_tSysUser').datagrid('reload');
		     }
	}); 
	$('#roleUserSaveFrom').submit();
}
function removeUser(userId,roleId) {
	$.messager.confirm('确认','您确认想要移除该记录吗？',function(r){    
	    if (r){    
			$('#removeRoleId').val(roleId);
			$('#removeUserId').val(userId);
			
			$('#removeUserForm').form({   
			 	url:'${path}/sym/role/removeRoleUser',    
				     onSubmit: function(){
				       $.messager.progress(); 
				        // do some check       
				        // return false to prevent submit;    
				     },    
				     success:function(data){
				       //do some
				        $.messager.progress('close');
						$('#dg_tSysUser').datagrid('reload');
				     }
			}); 
			$('#removeUserForm').submit();
	    }    
	}); 
}


jQuery(function($){
	
	
	
	//新增
	$('#add_btn').click(function(){
		var add_form_id ='#addTSysUserFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: '新增用户',
		    width: 800,
		    height: 500,
		    href: '${path}/sym/user/addUserPage',
		    modal: true,
		    method: "POST",
			onClose: function(){
				$(this).dialog("destroy");
			},
		    buttons: [{
		    	text: "提  交",
		    	handler: function(e){
		    		$('#addTSysUserFrom').form({   
						 url:'${path}/sym/user/addUser',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							    	 var obj = eval('(' + data + ')');
							       //do some
							       if(obj.result==false){
							    	   $.messager.alert('提示',obj.msg);   
							    	   $.messager.progress('close');   
							    	   return;
							       }
							        $.messager.progress('close');
									tSysUser_list.buildQueryParams();
									$('#dg_tSysUser').datagrid('load');
									$('#dialog_holder').dialog('close');
							       
							     }
						});// 
					    //validate and sbumit
					    if($(add_form_id).form("validate")==true){
						  	$.messager.progress();
							$(add_form_id).submit();
						};   
		    	}
		    },{
		    	text: "取 消",
		    	handler: function(e){
		    		$(this).dialog("close");
		    	}
		    }]
		});
	});
	
});
//更新
function editUser(userId){
    var edit_form_id ='#editTSysUserFrom';
	$('<div id="dialog_holder"></div>').dialog({
	    title: '修改信息',
	    width: 800,
	    height: 500,
	    href: '${path}/sym/user/editUserPage',
	    modal: true,
	    method: "POST",
	    params:{userId:userId},
		onClose: function(){
			$(this).dialog("destroy");
		},
	    buttons: [{
	    	text: "保  存",
	    	handler: function(e){
	   				$('#editTSysUserFrom').form({   
					 url:'${path}/sym/user/editUser',    
						     onSubmit: function(){
						       $.messager.progress(); 
						        // do some check       
						        // return false to prevent submit;    
						     },    
						     success:function(data){
						    	 var obj = eval('(' + data + ')');
							       //do some
						    	 if(obj.flag==false){
							    	   $.messager.alert('提示',obj.msg);   
							    	   $.messager.progress('close');   
							    	   return;
						       	 }
						       //do some
						        $.messager.progress('close');
								tSysUser_list.buildQueryParams();
								$('#dg_tSysUser').datagrid('load');
								$('#dialog_holder').dialog('close');
						     }
					});// 
				    //validate and sbumit
				    if($(edit_form_id).form("validate")==true){
					  	$.messager.progress();
						$(edit_form_id).submit();
					};   
	    		
	    	}
	    },
	    {
	    	text: "取 消",
	    	handler: function(e){
	    		$(this).dialog("close");
	    	}
	    }]
	});
}

function viewRole(userId) {
	$('<div id="dialog_holder"></div>').dialog({
	    title: '查看角色',
	    width: 800,
	    height: 500,
	    href: '${path}/sym/user/userRolePage',
	    params:{userId:userId},
	    modal: true,
	    method: "POST",
		onClose: function(){
			$(this).dialog("destroy");
		},
	    buttons: [{
	    	text: "取 消",
	    	handler: function(e){
	    		$(this).dialog("close");
	    	}
	    }]
	});
}

</script>
</body>
</html>