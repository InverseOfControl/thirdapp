<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/xml"  prefix = "x" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql"  prefix = "sql" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions"  prefix = "fn" %> 
<%@ include file="/common/base.jsp"%>
<div class="content-body">
		<div class="search-panel-content">
			<form id="handleTppAccountBizsysConfigFrom" name="handleTppAccountBizsysConfigFrom" method="post" action="editTppAccountBizsysConfigAction.action">
				<div class="search-panel-bd">
					<table class="search-table">
						 <tr>
							<th class="wd-20"><label>业务系统对账配置ID</label></th>
							<td>
								<input type="text" id="configId" name="configId" value="${configId}" class="easyui-validatebox" readonly="true" style="width:80px;"/><span style="color:red">*</span>
							</td>
						  </tr>
						  
						  <tr>
						 	<th class="wd-20"><label>对账时间</label></th>
							<td>
								<input type = "hidden" id="accountDay" name = "accountDay">
								<input id="accountDay1" name="accountDay1"  class="easyui-datebox" style="width:100px;" 
								data-options="required:true,
								              editable:false, 
								              onSelect:function(date) {
												 var now = new Date();
		    			        				 var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
		    			        				 if (d1 < date) {
		    			        						 $.messager.alert('提示','业务系统手工对账只能选择当天或者当天之前的日期');
		    			        				 		 $(this).datebox('setValue','');
		    			        			     } else {
		    			        			     		var value = $(this).datebox('getValue');
								    			        $('#accountDay').val(value);	     
		    			        			     }
								              }" /><span style="color:red">*</span>
							</td>
						  </tr>
				  </table>
				</div>
			</form>
		</div>
</div>
