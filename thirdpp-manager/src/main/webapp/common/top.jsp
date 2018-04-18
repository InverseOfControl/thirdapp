<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
		<div id="user-nav" class="navbar navbar-inverse">
            <ul class="nav btn-group">
                <li class="btn btn-inverse"><a title="" href="#"><i class="icon icon-user"></i> <span class="text">${sessionScope.user.loginUserName}</span></a></li>
                <c:if test="${null != sessionScope.permMap['/sym/editPassword']}">
                　　　　　　　　　　　<li class="btn btn-inverse"><a title="" data-toggle="modal" href="#modify-password-event"><i class="icon icon-cog"></i> <span class="text">修改密码</span></a></li>
			    </c:if>
                <li class="btn btn-inverse"><a title="" class="dl-log-quit" data-toggle="modal" href="#modal-add-event"><i class="icon icon-share-alt"></i> <span class="text">注销</span></a></li>
            </ul>
        </div>
        <!-- 注销确认弹出框 -->
		<div class="modal hide" id="modal-add-event">
			 <div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>系统提示</h3>
			</div>
			<div class="modal-body">
				<span>你确定要注销当前用户吗？</span>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn" data-dismiss="modal">取消</a>
				<a href="#" id="add-event-submit" onclick="logout()" class="btn btn-primary">确认</a>
			</div>
		</div>
        

		
		<script>
			function logout(){
		 		window.location.href = contextPath + "/zdmoney/logout";
			}
			
			function removeErrorInfo(){
				$("#error-span").html("");
			}
			
		</script>