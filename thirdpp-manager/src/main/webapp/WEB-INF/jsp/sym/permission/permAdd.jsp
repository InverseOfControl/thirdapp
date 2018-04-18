<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content-body">
		<div class="search-panel-content">
			<form id="addTSysPermissionFrom" name="addTSysPermissionFrom" method="post" >
				<div class="search-panel-bd">
				 <table class="search-table">
						 <tr>
							<th class="wd-20"><label>权限名称</label></th>
							<td>
								<input type="text" id="perm_add_perm_name" name="perm_add_perm_name" class="easyui-validatebox" data-options="required:true,missingMessage:'权限名称不能为空',novalidate: true,validType:'maxLength[100]'" style="width:200px;"/><span style="color:red">*</span>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>权限URL</label></th>
							<td>
								<input type="text" id="perm_add_perm_url" name="perm_add_perm_url" class="easyui-validatebox" data-options="required:true,missingMessage:'权限URL不能为空',novalidate: true,validType:'maxLength[200]'" style="width:200px;"/><span style="color:red">*</span>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>权限类型</label></th>
							<td>
								<input id="perm_add_perm_type" class="easyui-combobox" name="perm_add_perm_type" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/permissionTypeList',
										value: 1" style="width:200px;"/> 
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>菜单位置</label></th>
							<td>
								<input type="text" id="perm_add_position" name="perm_add_position" class="easyui-validatebox" style="width:200px;" data-options="validType:'maxLength[10]'"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>父级权限</label></th>
							<td>
								<input id="perm_add_parent_id" class="easyui-combobox" name="perm_add_parent_id" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/sym/permission/getPermissionList',
										value:'${permission.parentId}' " style="width:200px;"/> 
							</td>
						  </tr>
				  </table>
				</div>
			</form>
		</div>
</div>