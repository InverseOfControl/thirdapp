<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content-body">
		<div class="search-panel-content">
			<form id="editPwdForm" name="editPwdForm" method="post" >
				<div class="search-panel-bd">
				 <table class="search-table">
						 <tr>
							<th class="wd-20"><label>原密码：</label></th>
							<td>
								<input type="password" id="old_password" name="old_password" class="easyui-validatebox" data-options="required:true,invalidMessage:'不能为空',missingMessage:'不能为空',novalidate: true" style="width:200px;"/><span style="color:red">*</span>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>新密码：</label></th>
							<td>
								<input type="password" id="new_password" name="new_password" class="easyui-validatebox" data-options="required:true,invalidMessage:'不能为空',missingMessage:'不能为空',novalidate: true" style="width:200px;"/><span style="color:red">*</span>
							</td>
						  </tr>
						 
						 <tr>
							<th class="wd-20"><label>确认新密码：</label></th>
							<td>
								<input type="password" id="new_password_repeat" name="new_password_repeat" class="easyui-validatebox" data-options="required:true,invalidMessage:'不能为空',missingMessage:'不能为空',novalidate: true" style="width:200px;"/><span style="color:red">*</span>
							</td>
						  </tr>
						
				  </table>
				</div>
			</form>
		</div>
</div>