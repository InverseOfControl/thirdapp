<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/xml"  prefix = "x" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql"  prefix = "sql" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions"  prefix = "fn" %> 
<div class="content-body">
	<div class="search-panel-content">
		<div class="search-panel-bd">
			<div id="tt" class="easyui-tabs" style="width:750px;height:500px;">   
				<table class="search-table">
		        	<tr>
		        		<th class="wd-20"><label>渠道返回状态</label></th>
		        		<td>
							<input type="text" id="TRADE_RESULT.TRANS_REP_CODE" name="TRADE_RESULT.TRANS_REP_CODE"  value="${TRADE_RESULT.TRANS_REP_CODE}" style="width:200px;" readonly="true" />
						</td>
		        		<th class="wd-20"><label>渠道返回信息</label></th>
		        		<td>
							<input type="text" id="TRADE_RESULT.TRANS_REP_INFO" name="TRADE_RESULT.TRANS_REP_INFO"  value="${TRADE_RESULT.TRANS_REP_INFO}" style="width:200px;" readonly="true" />
						</td>
						
		        	</tr>
		        	<tr>
		        		<th class="wd-20"><label>交易结果</label></th>
		        		<td>
							<input type="text" id="TRADE_RESULT.STATUS" name="TRADE_RESULT.STATUS"  value="${TRADE_RESULT.STATUS}" style="width:200px;" readonly="true" />
						</td>
		        		<th class="wd-20"><label>创建时间</label></th>
		        		<td>
							<input type="text" id="TRADE_RESULT.CREATE_TIME" name="TRADE_RESULT.CREATE_TIME"  value="${TRADE_RESULT.CREATE_TIME}" style="width:200px;" readonly="true" />
						</td>
		        	</tr>
		        	<tr>
		        	</tr>
		        </table>
		        <table class="search-table">
		        	<tr>
		        		<th class="wd-20" style="width:50%"><label>请求报文</label></th>
		        		<th class="wd-20" style="width:50%"><label>响应报文</label></th>
		        	</tr>
		        	<tr>
		        		<th class="wd-20" style="width:50%"><label>请求报文时间：${requestMessageDate }</label></th>
		        		<th class="wd-20" style="width:50%"><label>响应报文时间：${responseMessageDate }</label></th>
		        	</tr>	
		        	<tr>
		        		<th class="wd-20" style="width:50%">
							<textarea  id="requestMessage" name="requestMessage" cols="45" rows="25" readonly="true">${requestMessage}</textarea>
								
						</th>
		        		<th class="wd-20" style="width:50%">
							<textarea  id="responseMessage" name="responseMessage" cols="45" rows="25" readonly="true">${responseMessage}</textarea>
						</th>
		        	</tr>
		        </table>
		        
		  	</div>
		</div>
	</div>

</div>