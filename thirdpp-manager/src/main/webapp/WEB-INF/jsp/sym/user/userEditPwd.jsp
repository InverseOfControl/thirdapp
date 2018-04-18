<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/xml"  prefix = "x" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql"  prefix = "sql" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions"  prefix = "fn" %> 
<div class="content-body">
		<div class="search-panel-content">
			<form id="editTSysUserPwdFrom" name="editTSysUserPwdFrom" method="post" >
				<div class="search-panel-bd">
					<input type="hidden" id="userId" name="userId" value="${userId}" />
					<table class="search-table">
						<tr>
							<th class="wd-20"><label>用户密码</label></th>
							<td>
								<input type="text" id="password" name="password"  class="easyui-validatebox" style="width:200px;" data-options="required:true,invalidMessage:'不能为空',missingMessage:'不能为空',novalidate: true"/><span style="color:red">*</span>
							</td>
						  </tr>
					</table>
				</div>
			</form>
		</div>
</div>