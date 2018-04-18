<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>证大财富统一支付管理系统</title>
		<meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@ include file="/common/jsCssInclude.jsp"%>
	 	 
	 	<link rel="stylesheet" href="${sessionScope.path}/style/cssRset.css" />
	 	<link rel="stylesheet" href="${sessionScope.path}/style/index.css" />
		<script type="text/javascript" src="${path}/js/jquery-1.11.0.min.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript">
			$(function() {
				if (window.parent != window) {
					window.parent.location.href="${sessionScope.path}/zdmoney/login";
				}
				  document.forms[0].userName.focus();  
			});
			
			function getEncryptionSubmit() {
				var userName = $('#userName').val();
				var password = $('#userPwd').val();
				password = hex_md5(password);
				$('#userPwd').val(password);
				//validate and sbumit
				$('#loginform').submit();
		    }
		</script>
	</head>
<body>
	<div id="top">
	<div id="header">
		<h1 class="logo">证大财富</h1>
	</div>
	<div id="con_wrap">
		<div class="con">
			<div class="login_padding">
				<div class="login">
					<h2>登录</h2>
					   <form id="loginform" class="login_form" action="${path}/zdmoney/loginPage" method="post">
						<input id="userName" name="userName" type="text" placeholder="用户名" class="text" />
						<input id="userPwd" name="userPwd" type="password" placeholder="密码"  class="password"/>
						<input type="button" value="" class="submit" onclick="getEncryptionSubmit()">	
						<!-- <a href="#" class="submit" plain="true" id="addBut2">新增</a>     -->
						<p style="color:red;font-size: 12px;">${errorMsg}&nbsp;</p>				 
					</form>
			 	</div>
			</div>
		</div>
	</div>
	<div class="footer">建议使用IE8.0以上版本，1024×768以上分辨率浏览本站</div>
	</div>
</body>
</html>
