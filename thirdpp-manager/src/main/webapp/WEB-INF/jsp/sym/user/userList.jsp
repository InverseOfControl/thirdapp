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
			<!--搜索栏开始-->
			<div class="easyui-panel" title="查询条件" style="padding:5px;height:75px;margin: 0px;" data-options="region:'north'">
				<form id="searchForm" name="searchForm" method="post" >
					<table cellpadding="5">
								<td style="text-align:right">用户名：</td>
								<td>
									<input type="text" id="search_user_name" name="search_user_name" class="easyui-textbox" value="${search_user_name}" style="width:100px;"/>
								</td>
								<td style="text-align:right">登录名：</td>
								<td>
									<input type="text" id="search_login_user_name" name="search_login_user_name" class="easyui-textbox" value="${search_login_user_name}" style="width:100px;"/>
								</td >
								<td style="text-align:right">是否有效：</td>
								<td>
									<input id="search_is_active" class="easyui-combobox" name="search_is_active" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/isActiveList' " style="width:100px;"/>  
								</td>
								
					</table>
				</form>
			</div>
			<!--搜索栏结束-->
			
			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding:0px;margin: 0px;" data-options="region:'center'">
				<table  id="dg_tSysUser" class="easyui-datagrid" 
					data-options="rownumbers:true,pageSize:20,singleSelect:true,selectOnCheck: 'true',checkOnSelect:'true',collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/sym/user/userListData',method:'post',toolbar:toolbar,fit:'true',onClickRow: function (rowIndex, rowData) {$(this).datagrid('unselectRow', rowIndex);}">
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:'true'"></th>
							<!-- <th data-options="field:'EDIT',width:50,align:'center'">编辑</th> -->
							<!-- <th data-options="field:'DELETE',width:50,align:'center'">删除</th> -->
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
			</div>
			<!--搜索栏结果end-->
			
<script>
var tSysUser_list ={};
var toolbar = [{
				id : 'add_btn',
				text : '新增',
				iconCls : 'icon-add'
			},{
				id : 'edit_btn',
				text : '修改',
				iconCls : 'icon-edit'
			},{
				id : 'edit_pwd_btn',
				text : '重置密码',
				iconCls : 'icon-edit'
			},'-',{
				id : 'search_btn',
				text : '查询',
				iconCls : 'icon-search'
			},{
				id : 'clear_btn',
				text : '清除',
				iconCls : 'icon-remove'
			}];
jQuery(function($){
	//定义构造查询
	tSysUser_list.buildQueryParams=function(){
		$('#dg_tSysUser').datagrid("options").queryParams={
				 'search_user_name':$("input[name='search_user_name']").val(),
				 'search_login_user_name':$("input[name='search_login_user_name']").val(),
				 'search_is_active':$('#search_is_active').combobox("getValue"),
		}
	}

	//重新按照条件刷新查询内容
	$('#search_btn').click(function(){
		tSysUser_list.buildQueryParams();
		$('#dg_tSysUser').datagrid('load');
	});
	
    //重置查询条件并刷新内容
	$('#clear_btn').click(function(){
		//清空查询条件
			$("input[name='search_user_name']").val("");
			$("input[name='search_login_user_name']").val("");
			$('#search_is_active').combobox('setValue','');
		tSysUser_list.buildQueryParams();
		$('#dg_tSysUser').datagrid('load');
	});
    
	$('#edit_btn').click(function(){
		var row = $('#dg_tSysUser').datagrid('getSelected');
		if (row == null) {
			$.messager.alert('提示','请先选择要修改的用户！');
			return;
		}
		var userId = row.ID;
		var edit_form_id ='#editTSysUserFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: '修改信息',
		    width: 600,
		    height: 300,
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
						$(".easyui-validatebox").each(function(){
				        	$(this).validatebox({    
				        		novalidate: false   
				        	}); 
				        });
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
	});
	
	$('#edit_pwd_btn').click(function(){
		var row = $('#dg_tSysUser').datagrid('getSelected');
		if (row == null) {
			$.messager.alert('提示','请先选择要修改的用户！');
			return;
		}
		var userId = row.ID;
		var edit_pwd_form_id ='#editTSysUserPwdFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: '修改信息',
		    width: 600,
		    height: 300,
		    href: '${path}/sym/user/editUserPwdPage',
		    modal: true,
		    method: "POST",
		    params:{userId:userId},
			onClose: function(){
				$(this).dialog("destroy");
			},
		    buttons: [{
		    	text: "保  存",
		    	handler: function(e){
		   				$('#editTSysUserPwdFrom').form({   
						 url:'${path}/sym/user/editUserPwd',    
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
						$(".easyui-validatebox").each(function(){
				        	$(this).validatebox({    
				        		novalidate: false   
				        	}); 
				        });
					    //validate and sbumit
					    if($(edit_pwd_form_id).form("validate")==true){
						  	$.messager.progress();
							$(edit_pwd_form_id).submit();
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
	});
	
	//新增
	$('#add_btn').click(function(){
		var add_form_id ='#addTSysUserFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: '新增用户',
		    width: 600,
		    height: 300,
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
						$(".easyui-validatebox").each(function(){
				        	$(this).validatebox({    
				        		novalidate: false   
				        	}); 
				        });
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
	    width: 600,
	    height: 300,
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
					$(".easyui-validatebox").each(function(){
			        	$(this).validatebox({    
			        		novalidate: false   
			        	}); 
			        });
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
	    width: 600,
	    height: 300,
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


/* function deleteUser(userId) {
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){    
	        alert('确认删除' + userId);    
	        $('#deleteForm').form({  
		        url:'${path }/sym/user/mergeOrder',  
		        onSubmit:function(){  
		        },  
		        success:function(data){  
		        	$('#dg_tSysUser').datagrid('load');
		        }  
		    }); 
    		$('#deleteForm').submit();
	    }    
	}); 
} */
</script>
</body>
</html>