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
		<link rel="stylesheet" href="${sessionScope.path}/css/base.css" />
	    <script type="text/javascript" src="${path}/js/sym/role/role.js"></script>	
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body class="easyui-layout">
		<div class="easyui-panel" title="角色信息" data-options="region:'west'"  style="width:350px;">
				<table  id="dg_tSysRole" class="easyui-datagrid" 
					data-options="rownumbers:true,singleSelect:true,pageSize:20,selectOnCheck: 'true',checkOnSelect:'true',collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/sym/role/roleListData',method:'post',fit:'true',onClickRow:function(rowIndex, rowData){showInfo(rowIndex, rowData);},onLoadSuccess:function(){defaultSelect()}"
					toolbar="#tb"
					>
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:'true'"></th>
							<th data-options="field:'ID',width:120,sortable:'true',align:'left',hidden:true">ID</th>
							<th data-options="field:'ROLE_NAME',width:290,sortable:'true',align:'left'">角色名称</th>
							<!-- <th data-options="field:'IS_ACTIVE',width:60,sortable:'true',align:'left'">是否有效</th> -->
						</tr>
					</thead>
				</table>
		</div>
		<div data-options="region:'center'" title="" style="width:100%;">
			<!--  中间部分（各模块主区域） -->
			<div id="roleTabs" data-options="fit:true" class="easyui-tabs"  style="width:700px;height:250px">
				<div  id="roleTabs_userList" title="用户列表" data-options="fit:true" style="text-align:center;font-size:18px;">   
			    </div>   
			    <div id="roleTabs_appList" title="系统列表" data-options="fit:true" style="text-align:center;font-size:18px;">   
			    </div>   
			    <div id="roleTabs_permList" title="权限列表" data-options="fit:true" style="text-align:center;font-size:18px;">  
			    </div>   
			</div>
		</div>
		<div id="tb" style="padding:5px;height:auto">    
			<div>    
		    	<form id="searchRoleForm" name="searchRoleForm" method="post"></form>
		        	角色名称： <input type="text" id="search_role_name" name="search_role_name" class="easyui-textbox" value="${search_role_name}" style="width:100px;"/>  
		        </form>  
		        <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="search_btn">查询</a> 
		        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="clear_btn">清除</a>   
		        <form id="deleteRoleForm" name="deleteRoleForm">
					<input id="delete_roleId" name="delete_roleId" type="hidden"/>
				</form>
		    </div> 
		    <div style="margin-bottom:5px">    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRole()">新增</a>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRole()">修改</a>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="deleteRole()">删除</a>    
		    </div>    
		    
		</div>    
		
				
	<script>
	function showInfo(rowIndex, rowData) {
		var userTabs = $('#roleTabs_userList');
		var appTabs = $('#roleTabs_appList');
		var permTabs = $('#roleTabs_permList');
		userTabs.panel({    
		    href:'${path}/sym/role/roleUserListPage/' + rowData.ID,    
		    onLoad:function(){    
		            
		    }    
		});  
		
		appTabs.panel({    
		    href:'${path}/sym/role/roleAppListPage/' + rowData.ID,    
		    onLoad:function(){    
		            
		    }    
		});  
		
		permTabs.panel({    
		    href:'${path}/sym/role/rolePermListPage/' + rowData.ID,    
		    onLoad:function(){    
		            
		    }    
		}); 
		
	}
	
	function addRole() {
		var add_form_id ='#addTSysRoleFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: '新增角色',
		    width: 600,
		    height: 300,
		    href: '${path}/sym/role/addRolePage',
		    modal: true,
		    method: "POST",
			onClose: function(){
				$(this).dialog("destroy");
			},
		    buttons: [{
		    	text: "保  存",
		    	handler: function(e){
		    		$('#addTSysRoleFrom').form({   
						 url:'${path}/sym/role/addRole',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							    	 var obj = eval('(' + data + ')');
								       //do some
							    	 if(obj.flag==false){
							    		   $.messager.progress('close');
								    	   $.messager.alert('提示',obj.msg);   
								    	   return;
							       	 }
							       //do some
							        $.messager.progress('close');
									tSysRole_list.buildQueryParams();
									$('#dg_tSysRole').datagrid('load');
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
	}
	
	function editRole() {
		var row = $('#dg_tSysRole').datagrid('getSelected');
		if (row == null) {
			$.messager.alert('提示','请先选择要修改的角色！');
			return;
		}
		var roleId = row.ID;
		var edit_form_id ='#editTSysRoleFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: '编辑角色',
		    width: 600,
		    height: 300,
		    href: '${path}/sym/role/editRolePage',
		    modal: true,
		    method: "POST",
		    params:{roleId:roleId},
			onClose: function(){
				$(this).dialog("destroy");
			},
		    buttons: [{
		    	text: "保  存",
		    	handler: function(e){
		   				$('#editTSysRoleFrom').form({   
						 url:'${path}/sym/role/editRole',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							    	 var obj = eval('(' + data + ')');
							    	 if(obj.flag==false){
							    		   $.messager.progress('close');
								    	   $.messager.alert('提示',obj.msg);   
								    	   return;
							       	 }
							       //do some
							        $.messager.progress('close');
									tSysRole_list.buildQueryParams();
									$('#dg_tSysRole').datagrid('load');
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
		    }
		    ,{
		    	text: "取 消",
		    	handler: function(e){
		    		$(this).dialog("close");
		    	}
		    }]
		});
	}
	
	function deleteRole() {
		var row = $('#dg_tSysRole').datagrid('getSelected');
		if (row == null) {
			$.messager.alert('提示','请先选择需要删除的角色！');
			return;
		}
		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){    
				$('#delete_roleId').val(row.ID);
				$('#deleteRoleForm').form({  
			        url:'${path }/sym/role/deleteRole',  
			        onSubmit:function(){  
			        },  
			        success:function(data){  
			        	$('#dg_tSysRole').datagrid('load');
			        }  
			    }); 
				$('#deleteRoleForm').submit();
		    }    
		}); 
	}
	function defaultSelect() {
		$('#dg_tSysRole').datagrid('selectRow', 0); 
		var row = $('#dg_tSysRole').datagrid('getSelected');
		if (row != null) {
			showInfo(0, row);
		}
		
	}
	var tSysRole_list ={};
	jQuery(function($){
		//定义构造查询
		tSysRole_list.buildQueryParams=function(){
			$('#dg_tSysRole').datagrid("options").queryParams={
					 'search_role_name':$("input[name='search_role_name']").val(),
			}
		}

		//重新按照条件刷新查询内容
		$('#search_btn').click(function(){
			tSysRole_list.buildQueryParams();
			$('#dg_tSysRole').datagrid('load');
		});
		
		//重置查询条件并刷新内容
		$('#clear_btn').click(function(){
			//清空查询条件
				$("input[name='search_role_name']").val("");
				tSysRole_list.buildQueryParams();
			$('#dg_tSysRole').datagrid('load');
		});
	})
	</script>
	</body>
</html>
