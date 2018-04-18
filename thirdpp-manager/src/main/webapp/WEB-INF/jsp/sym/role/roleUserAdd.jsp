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
				<form id="searchUserForm" name="searchUserForm" method="post" >
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
				<table  id="dg_tSysUserAdd" class="easyui-datagrid" style="height:500px"
					data-options="rownumbers:true,singleSelect:false,selectOnCheck: 'true',checkOnSelect:'true',collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/sym/role/addRoleUserListData/${roleId }',method:'post',toolbar:toolbar,onClickRow: function (rowIndex, rowData) {$(this).datagrid('unselectRow', rowIndex);},">
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
						</tr>
					</thead>
				</table>
			</div>
			<!--搜索栏结果end-->
			
<script>
var tSysUser_list ={};
var toolbar = [{
				id : 'search_user_btn',
				text : '查询',
				iconCls : 'icon-search',
				handler:function(){
					tSysUser_list.buildQueryParams();
					$('#dg_tSysUserAdd').datagrid('load');
				}
			},{
				id : 'clear_user_btn',
				text : '清除',
				iconCls : 'icon-remove',
				handler:function(){
					//清空查询条件
					$("input[name='search_user_name']").val("");
					$("input[name='search_login_user_name']").val("");
					$('#search_is_active').combobox('setValue','');
					tSysUser_list.buildQueryParams();
					$('#dg_tSysUserAdd').datagrid('load');
				}
				
			}];
jQuery(function($){
	//定义构造查询
	tSysUser_list.buildQueryParams=function(){
		$('#dg_tSysUserAdd').datagrid("options").queryParams={
				 'search_user_name':$("input[name='search_user_name']").val(),
				 'search_login_user_name':$("input[name='search_login_user_name']").val(),
				 'search_is_active':$('#search_is_active').combobox("getValue"),
		}
	}
});
</script>
</body>
</html>