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
					<table class="search-table">
							<tr>
								<th class="wd-20"><label>TPP通道请求流水号</label></th>
								<td>
									<input type="text" id="CHANNEL_REQUEST.REQ_ID" name="CHANNEL_REQUEST.REQ_ID"  value="${CHANNEL_REQUEST.REQ_ID}" style="width:100px;" readonly="true" />
								</td>
								<th class="wd-20"><label>交易流水号</label></th>
								<td>
									<input type="text" id="CHANNEL_REQUEST.TRANSFER_FLOW" name="CHANNEL_REQUEST.TRANSFER_FLOW"  value="${CHANNEL_REQUEST.TRANSFER_FLOW}" style="width:100px;" readonly="true" />
								</td>
								<th class="wd-20"><label>业务系统</label></th>
								<td>
									<input type="text" id="CHANNEL_REQUEST.BIZ_SYS_NAME" name="CHANNEL_REQUEST.BIZ_SYS_NAME"  value="${CHANNEL_REQUEST.BIZ_SYS_NAME}" style="width:100px;" readonly="true" />
								</td>
							</tr>
							<tr>
								<th class="wd-20"><label>第三方支付平台</label></th>
								<td>
									<input type="text" id="CHANNEL_REQUEST.PAY_SYS_NAME" name="CHANNEL_REQUEST.PAY_SYS_NAME"  value="${CHANNEL_REQUEST.PAY_SYS_NAME}" style="width:100px;" readonly="true" />
								</td>
								<th class="wd-20"><label>请求状态</label></th>
								<td>
									<input type="text" id="CHANNEL_REQUEST.STATUS" name="CHANNEL_REQUEST.STATUS"  value="${CHANNEL_REQUEST.STATUS}" style="width:100px;" readonly="true" />
								</td>
								<th class="wd-20"><label>业务类型</label></th>
								<td>
									<input type="text" id="CHANNEL_REQUEST.BIZ_TYPE" name="CHANNEL_REQUEST.BIZ_TYPE"  value="${CHANNEL_REQUEST.BIZ_TYPE}" style="width:100px;" readonly="true" />
								</td> 
							</tr>
							<tr>
								<th class="wd-20"><label>第三方支付平台流水号</label></th>
								<td>
									<input type="text" id="CHANNEL_REQUEST.PAY_TRANS_FLOW" name="CHANNEL_REQUEST.PAY_TRANS_FLOW"  value="${CHANNEL_REQUEST.PAY_TRANS_FLOW}" style="width:100px;" readonly="true" />
								</td>
								<th class="wd-20"><label>信息类别</label></th>
								<td>
									<input type="text" id="CHANNEL_REQUEST.INFO_CATEGORY_NAME" name="CHANNEL_REQUEST.INFO_CATEGORY_NAME"  value="${CHANNEL_REQUEST.INFO_CATEGORY_NAME}" style="width:100px;" readonly="true" />
								</td>
								<th class="wd-20"><label>失败原因</label></th>
								<td>
									<textarea   id="CHANNEL_REQUEST.FAIL_REASON" name="CHANNEL_REQUEST.FAIL_REASON" style="width:100px;" readonly="true" >${CHANNEL_REQUEST.FAIL_REASON}</textarea>
								</td>
						  	</tr>
						  
				  </table>
				 <div id="tt1" class="easyui-tabs" style="width:750px;height:100px;">   
				  	<div title="交易结果" data-options="fit:true" style="text-align:center;">   
					        <table class="search-table">
					        	<tr>
					        		<th class="wd-20"><label>渠道返回状态</label></th>
					        		<td>
										<input type="text" id="TRADE_RESULT.TRANS_REP_CODE" name="TRADE_RESULT.TRANS_REP_CODE"  value="${TRADE_RESULT.TRANS_REP_CODE}" style="width:100px;" readonly="true" />
									</td>
					        		<th class="wd-20"><label>渠道返回信息</label></th>
					        		<td>
										<input type="text" id="TRADE_RESULT.TRANS_REP_INFO" name="TRADE_RESULT.TRANS_REP_INFO"  value="${TRADE_RESULT.TRANS_REP_INFO}" style="width:100px;" readonly="true" />
									</td>
									
					        	</tr>
					        	<tr>
					        		<th class="wd-20"><label>交易结果</label></th>
					        		<td>
										<input type="text" id="TRADE_RESULT.STATUS" name="TRADE_RESULT.STATUS"  value="${TRADE_RESULT.STATUS}" style="width:100px;" readonly="true" />
									</td>
					        		<th class="wd-20"><label>创建时间</label></th>
					        		<td>
										<input type="text" id="TRADE_RESULT.CREATE_TIME" name="TRADE_RESULT.CREATE_TIME"  value="${TRADE_RESULT.CREATE_TIME}" style="width:100px;" readonly="true" />
									</td>
					        	</tr>
					        	<tr>
					        	</tr>
					        </table>
					    </div> 
				  </div>
				  <div id="tt" class="easyui-tabs" style="width:750px;height:480px;">   
					    <div title="报文信息" data-options="fit:true" style="text-align:center;"> 
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
			</form>
		</div>
</div>