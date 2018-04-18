<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/xml"  prefix = "x" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql"  prefix = "sql" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions"  prefix = "fn" %> 
<div class="content-body">
		<div class="search-panel-content">
			<form id="editTSysPermissionFrom" name="editTSysPermissionFrom" method="post">
				<div class="search-panel-bd">
							<input type="hidden" id="id" name="id" value="${permission.id}" />
					<table class="search-table">
								 <tr>
									<th class="wd-20"><label>权限名称</label></th>
									<td>
										<input type="text" id="permName" name="permName" value="${permission.permName}" class="easyui-validatebox" data-options="required:true,missingMessage:'权限名称不能为空',novalidate: true,validType:'maxLength[100]'" style="width:200px;"/><span style="color:red">*</span>
									</td>
								  </tr>
								 <tr>
									<th class="wd-20"><label>权限URL</label></th>
									<td>
										<input type="text" id="permUrl" name="permUrl" value="${permission.permUrl}" class="easyui-validatebox" data-options="required:true,missingMessage:'权限URL不能为空',novalidate: true,validType:'maxLength[200]'" style="width:200px;"/><span style="color:red">*</span>
									</td>
								  </tr>
								 <tr>
									<th class="wd-20"><label>权限类型</label></th>
									<td>
										<input id="permType" class="easyui-combobox" name="permType" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/permissionTypeList',
										value:'${permission.permType }' " style="width:200px;"/> 
									</td>
								  </tr>
								 <tr>
									<th class="wd-20"><label>菜单位置</label></th>
									<td>
										<input type="text" id="position" name="position" value="${permission.position}" class="easyui-validatebox"  style="width:200px;" data-options="validType:'maxLength[10]'"/>
									</td>
								  </tr>
								 <tr>
									<th class="wd-20"><label>父级权限</label></th>
									<td>
										<input id="parentId" class="easyui-combobox" name="parentId" data-options="
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