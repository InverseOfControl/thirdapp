<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page isELIgnored="false" %>
<%
	//父节点ID
	String pId = request.getParameter("pId")==null?"0":request.getParameter("pId").toString();
	//子节点ID
	String nId = request.getParameter("nId")==null?"0":(pId+"-"+request.getParameter("nId").toString());
%>
<%!
//高亮方法
public String active(String type,String id,String pramaId){
	if("parent".equals(type) && id.equals(pramaId)){
		if("0".equals(pramaId)){
			return "class=\"active\"";
		}else{
			return " active open";
		}
	}else if("node".equals(type) && id.equals(pramaId)){
		return "class=\"active\"";
	}else{
		return "";
	}
} 
%>
     <div id="sidebar">
			<a href="#" class="visible-phone"><i class="icon icon-th-list"></i> Tables</a>
			<ul>
				<!-- 1 -->
				<li <%=active("parent", "0", pId) %>><a href="${path}/zdmoney/main"><i class="icon icon-home"></i> <span>首页</span></a></li>
				
				<c:forEach items="${sessionScope.permList}" var="permission">
				   <c:if test="${null != sessionScope.permMap[permission.permUrl] && permission.permType==1}">
				      <c:set var="parentPosition" value="${permission.position}" scope="page"></c:set>
				      <li class="submenu <%=active("parent", (String)pageContext.getAttribute("parentPosition"), pId) %>">
				        <c:remove var="parentPosition" scope="page"/>
						<a href="#"><i class="icon icon-th-list"></i> <span>${permission.permName}</span> </a>
						<ul>
						    <c:if test="${null != permission.children}">
						      <c:forEach items="${permission.children}" var="children">
						        <c:if test="${null != sessionScope.permMap[children.permUrl] && children.permType==1}">
						            <c:set var="childPosition" value="${children.position}" scope="page"></c:set>
									<li <%=active("node", (String)pageContext.getAttribute("childPosition"), nId) %>><a href="${path}${children.permUrl}">${children.permName}</a></li>
									<c:remove var="childPosition" scope="page"/>
						        </c:if>
						      </c:forEach>
						    </c:if>
						</ul>
					</li>
				   </c:if>
				</c:forEach>
			</ul>
		
		</div>
