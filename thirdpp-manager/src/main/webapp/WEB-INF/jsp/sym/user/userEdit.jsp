<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/xml"  prefix = "x" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql"  prefix = "sql" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions"  prefix = "fn" %> 
<div class="content-body">
		<div class="search-panel-content">
			<form id="editTSysUserFrom" name="editTSysUserFrom" method="post" action="editTSysUserAction.action">
				<div class="search-panel-bd">
							<input type="hidden" id="id" name="id" value="${user.id}" />
					<table class="search-table">
								 <tr>
									<th class="wd-20"><label>用户名</label></th>
									<td>
										<input type="text" id="userName" name="userName" value="${user.userName}" class="easyui-validatebox" data-options="required:true,missingMessage:'用户名不能为空',novalidate: true,validType:'maxLength[50]'" style="width:200px;"/><span style="color:red">*</span>
									</td>
								  </tr>
								 <tr>
									<th class="wd-20"><label>登录名</label></th>
									<td>
										<input type="text" id="loginUserName" name="loginUserName" value="${user.loginUserName}" class="easyui-validatebox" readonly="true" style="width:200px;"/>
									</td>
								  </tr>
								 <%-- <tr>
									<th class="wd-20"><label>用户密码</label></th>
									<td>
										<input type="password" id="password" name="password" value="${user.password}" class="easyui-validatebox" disable style="width:200px;"/>
									</td>
								  </tr> --%>
								 <tr>
									<th class="wd-20"><label>电子邮箱</label></th>
									<td>
										<input type="text" id="email" name="email" value="${user.email}" class="easyui-validatebox" missingMessage="邮件必须填写"  required="true" style="width:200px;" data-options="novalidate: true,validType:['email','maxLength[50]']"/><span style="color:red">*</span>
									</td>
								  </tr>
								 <tr>
									<th class="wd-20"><label>电话号码</label></th>
									<td>
										<input type="text" id="phoneNo" name="phoneNo" value="${user.phoneNo}" class="easyui-validatebox" style="width:200px;"/>
									</td>
								  </tr>
								 <tr>
									<th class="wd-20"><label>是否有效</label></th>
									<td>
										<input id="isActive" class="easyui-combobox" name="isActive" data-options="
												editable:false,valueField: 'value',
												textField: 'name',
												//url是下拉框请求显示的数据
												url:'${path }/enumset/isActiveList',
												value:'${user.isActive }' " style="width:200px;"/>  <span style="color:red">*</span>
									</td>
								  </tr>
					</table>
				</div>
			</form>
		</div>
</div>