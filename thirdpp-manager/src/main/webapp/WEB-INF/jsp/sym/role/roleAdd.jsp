<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content-body">
		<div class="search-panel-content">
			<form id="addTSysRoleFrom" name="addTSysRoleFrom" method="post" action="addTSysRoleAction.action">
				<div class="search-panel-bd">
				 <table class="search-table">
						 <tr>
							<th class="wd-20"><label>角色名称</label></th>
							<td>
								<input type="text" id="role_add_role_name" name="role_add_role_name" class="easyui-validatebox" data-options="required:true,missingMessage:'角色名称不能为空',novalidate: true,validType:'maxLength[50]'" style="width:200px;"/><span style="color:red">*</span>
							</td>
						  </tr>
						 <%-- <tr>
							<th class="wd-20"><label>是否有效：</label></th>
							<td>
								<input id="role_add_is_active" class="easyui-combobox" name="role_add_is_active" data-options="
									editable:false,valueField: 'value',
									textField: 'name',
									//url是下拉框请求显示的数据
									url:'${path }/enumset/isActiveList',
									value:'1' " style="width:200px;"/>  
							</td>
						  </tr> --%>
				  </table>
				</div>
			</form>
		</div>
</div>