<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content-body">
		<div class="search-panel-content">
			<form id="addTSysUserFrom" name="addTSysUserFrom" method="post">
				<div class="search-panel-bd">
				 <table class="search-table">
						 <tr>
							<th class="wd-20"><label>用户名</label></th>
							<td>
								<input type="text" id="user_add_user_name" name="user_add_user_name" class="easyui-validatebox" data-options="required:false,missingMessage:'用户名不能为空',novalidate: true,validType:'maxLength[50]'"  style="width:200px;"/><span style="color:red">*</span>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>登录名</label></th>
							<td>
								<input type="text" id="user_add_login_user_name" name="user_add_login_user_name" class="easyui-validatebox" data-options="required:true,missingMessage:'登录名不能为空',novalidate: true,validType:'maxLength[12]'" style="width:200px;"/><span style="color:red">*</span>
							</td>
						  </tr>
						<!--  <tr>
							<th class="wd-20"><label>密码</label></th>
							<td>
								<input type="text" id="user_add_password" name="user_add_password" class="easyui-validatebox" style="width:200px;"/><span style="color:red">*不设置密码将使用默认密码！</span>
							</td>
						  </tr> -->
						 <tr>
							<th class="wd-20"><label>电子邮箱</label></th>
							<td>
								<input type="text" id="user_add_email" name="user_add_email" class="easyui-validatebox" missingMessage="邮件必须填写"  required="true" style="width:200px;" data-options="novalidate: true,validType:['email','maxLength[50]']"/><span style="color:red">*</span>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>电话号码</label></th>
							<td>
								<input type="text" id="user_add_phone_no" name="user_add_phone_no" class="easyui-validatebox"  style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>是否生效</label></th>
							<td>
								<input id="user_add_is_active" class="easyui-combobox" name="user_add_is_active" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/isActiveList',
										value:'1' " style="width:200px;"/>  <span style="color:red">*</span>
							</td>
						  </tr>
				  </table>
				</div>
			</form>
		</div>
</div>
<script>
jQuery(function($){
});
</script>