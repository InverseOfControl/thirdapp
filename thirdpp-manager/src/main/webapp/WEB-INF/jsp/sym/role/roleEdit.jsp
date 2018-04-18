<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/xml"  prefix = "x" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql"  prefix = "sql" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions"  prefix = "fn" %> 
<div class="content-body">
		<div class="search-panel-content">
			<form id="editTSysRoleFrom" name="editTSysRoleFrom" method="post">
				<div class="search-panel-bd">
							<input type="hidden" id="edit_role_id" name="edit_role_id" value="${role.id}" />
					<table class="search-table">
						 <tr>
							<th class="wd-20"><label>角色名称</label></th>
							<td>
								<input type="text" id="edit_role_role_name" name="edit_role_role_name" value="${role.roleName}" class="easyui-validatebox" data-options="required:true,missingMessage:'角色名称不能为空',novalidate: true,validType:'maxLength[50]'" style="width:200px;"/><span style="color:red">*</span>
							</td>
						  </tr>
						 <%-- <tr>
							<th class="wd-20"><label>是否有效</label></th>
							<td>
								<input id="edit_role_is_active" class="easyui-combobox" name="edit_role_is_active" data-options="
									editable:false,valueField: 'value',
									textField: 'name',
									//url是下拉框请求显示的数据
									url:'${path }/enumset/isActiveList',
									value:'${role.isActive }' " style="width:200px;"/>  
							</td>
						  </tr> --%>
					</table>
				</div>
			</form>
		</div>
</div>