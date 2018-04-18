<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/xml"  prefix = "x" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/sql"  prefix = "sql" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/functions"  prefix = "fn" %> 
<div class="content-body">
		<div class="search-panel-content">
			<form id="editTppTradeTCollectInfoFrom" name="editTppTradeTCollectInfoFrom" method="post" action="editTppTradeTCollectInfoAction.action">
				<div class="search-panel-bd">
					<table class="search-table">
							 
							 <tr>
								<th class="wd-20"><label>请求ID</label></th>
								<td>
									<input type="text" id="REQ_ID" name="REQ_ID"  value="${REQ_ID}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>业务类型</label></th>
								<td>
									<input type="text" id="BIZ_TYPE_NO" name="BIZ_TYPE_NO"  value="${BIZ_TYPE_NO}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>业务系统</label></th>
								<td>
									<input type="text" id="BIZ_SYS_NAME" name="BIZ_SYS_NAME"  value="${BIZ_SYS_NAME}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>业务系统客户编号</label></th>
								<td>
									<input type="text" id="BIZ_SYS_ACCOUNT_NO" name="BIZ_SYS_ACCOUNT_NO"  value="${BIZ_SYS_ACCOUNT_NO}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>证大客户编号</label></th>
								<td>
									<input type="text" id="ZENGDAI_ACCOUNT_NO" name="ZENGDAI_ACCOUNT_NO"  value="${ZENGDAI_ACCOUNT_NO}" style="width:200px;" readonly="true" />
								</td>
							 
								<th class="wd-20"><label>客户姓名</label></th>
								<td>
									<input type="text" id="REAL_NAME" name="REAL_NAME"  value="${REAL_NAME}" style="width:200px;" readonly="true" />
								</td>
							 </tr>
							 <tr>
								<th class="wd-20"><label>性别</label></th>
								<td>
									<input type="text" id="GENDER" name="GENDER"  value="${GENDER}" style="width:200px;" readonly="true" />
								</td>
							 
								<th class="wd-20"><label>民族</label></th>
								<td>
									<input type="text" id="NATION" name="NATION"  value="${NATION}" style="width:200px;" readonly="true" />
								</td>
								 </tr>
							 <tr>
								<th class="wd-20"><label>客户手机号码</label></th>
								<td>
									<input type="text" id="MOBILE" name="MOBILE"  value="${MOBILE}" style="width:200px;" readonly="true" />
								</td>
							 
								<th class="wd-20"><label>客户预留手机号码</label></th>
								<td>
									<input type="text" id="RESERVE_MOBILE" name="RESERVE_MOBILE"  value="${RESERVE_MOBILE}" style="width:200px;" readonly="true" />
								</td>
								 </tr>
							 <tr>
								<th class="wd-20"><label>绑定银行卡号</label></th>
								<td>
									<input type="text" id="BANK_CARD_NO" name="BANK_CARD_NO"  value="${BANK_CARD_NO}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>银行编码</label></th>
								<td>
									<input type="text" id="BANK_CODE" name="BANK_CODE"  value="${BANK_CODE}" style="width:200px;" readonly="true" />
								</td>
								 
							 <tr>
							 </tr>
								<th class="wd-20"><label>银行名称</label></th>
								<td>
									<input type="text" id="BANK_NAME" name="BANK_NAME"  value="${BANK_NAME}" style="width:200px;" readonly="true" />
								</td>
								 
								<th class="wd-20"><label>银行卡类型</label></th>
								<td>
									<input type="text" id="BANK_CARD_TYPE" name="BANK_CARD_TYPE"  value="${BANK_CARD_TYPE}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
								
							 <tr>
							 	<th class="wd-20"><label>第三方支付平台</label></th>
								<td>
									<input type="text" id="PAY_SYS_NAME" name="PAY_SYS_NAME"  value="${PAY_SYS_NAME}" style="width:200px;" readonly="true" />
								</td>
								
								<th class="wd-20"><label>状态</label></th>
								<td>
									<input type="text" id="STATUS" name="STATUS"  value="${STATUS}" style="width:200px;" readonly="true" />
								</td>
							 </tr>
								
							 <tr>
								 <th class="wd-20"><label>证件类型</label></th>
								<td>
									<input type="text" id="ID_TYPE" name="ID_TYPE"  value="${ID_TYPE}" style="width:200px;" readonly="true" />
								</td>
								
								<th class="wd-20"><label>证件号</label></th>
								<td>
									<input type="text" id="ID_NO" name="ID_NO"  value="${ID_NO}" style="width:200px;" readonly="true" />
								</td>
							</tr>
							 <tr>
							 	<th class="wd-20"><label>请求IP</label></th>
								<td>
									<input type="text" id="IP" name="IP"  value="${IP}" style="width:200px;" readonly="true" />
								</td>
								
								<th class="wd-20"><label>业务流水号</label></th>
								<td>
									<input type="text" id="BIZ_FLOW" name="BIZ_FLOW"  value="${BIZ_FLOW}" style="width:200px;" readonly="true" />
								</td>
								</tr>
							 <tr>
								<th class="wd-20"><label>发送状态</label></th>
								<td>
									<input type="text" id="STATUS" name="STATUS"  value="${STATUS}" style="width:200px;" readonly="true" />
								</td>
								 
								<th class="wd-20"><label>是否同步</label></th>
								<td>
									<input type="text" id="IS_SYNC" name="IS_SYNC"  value="${IS_SYNC}" style="width:200px;" readonly="true" />
								</td>
								 </tr>
							 <tr>
								
								
							 	<th class="wd-20"><label>开户行编码</label></th>
								<td>
									<input type="text" id="OPEN_BANK_CODE" name="OPEN_BANK_CODE"  value="${OPEN_BANK_CODE}" style="width:200px;" readonly="true" />
								</td>
								
								<th class="wd-20"><label>开户行名称</label></th>
								<td>
									<input type="text" id="OPEN_BANK_NAME" name="OPEN_BANK_NAME"  value="${OPEN_BANK_NAME}" style="width:200px;" readonly="true" />
								</td>
								 </tr>
							 <tr>
								<th class="wd-20"><label>第三方支付平台流水号</label></th>
								<td>
									<input type="text" id="PAY_TRANS_FLOW" name="PAY_TRANS_FLOW"  value="${PAY_TRANS_FLOW}" style="width:200px;" readonly="true" />
								</td>
							
								<th class="wd-20"><label>交易流水号</label></th>
								<td>
									<input type="text" id="TRANSFER_FLOW" name="TRANSFER_FLOW"   value='${TRANSFER_FLOW}'  style="width:200px;" readonly="true" />
								</td>
								 </tr>
							<tr>
								<th class="wd-20"><label>客户第三方账户编号</label></th>
								<td>
									<input type="text" id="THIRD_ACCOUNT_NO" name="THIRD_ACCOUNT_NO"  value="${THIRD_ACCOUNT_NO}" style="width:200px;" readonly="true" />
								</td>
							 
								<th class="wd-20"><label>移动设备信息</label></th>
								<td>
									<input type="text" id="MAC" name="MAC"  value="${MAC}" style="width:200px;" readonly="true" />
								</td>
								 </tr>
							 <tr>
								<th class="wd-20"><label>信息类别</label></th>
								<td>
									<input type="text" id="INFO_CATEGORY_NAME" name="INFO_CATEGORY_NAME"  value="${INFO_CATEGORY_NAME}" style="width:200px;" readonly="true" />
								</td>
							 
								<th class="wd-20"><label>更新时间</label></th>
								<td>
									<input type="text" id="UPDATE_TIME" name="UPDATE_TIME"  value="<fmt:formatDate value='${UPDATE_TIME}' type='date'  pattern='yyyy-MM-dd HH:mm:ss'/>" style="width:200px;" readonly="true" />
								</td>
								 </tr>
							 <tr>
								<th class="wd-20"><label>创建人</label></th>
								<td>
									<input type="text" id="CREATER" name="CREATER"  value="${CREATER}" style="width:200px;" readonly="true" />
								</td>
							 
								<th class="wd-20"><label>创建时间</label></th>
								<td>
									<input type="text" id="CREATE_TIME" name="CREATE_TIME"  value="<fmt:formatDate value='${CREATE_TIME}' type='date'  pattern='yyyy-MM-dd HH:mm:ss'/>" style="width:200px;" readonly="true" />
								</td>
								 </tr>
							 <tr>
								<th class="wd-20"><label>返回时间</label></th>
								<td>
									<input type="text" id="RESP_TIME" name="RESP_TIME"  value="<fmt:formatDate value='${RESP_TIME}' type='date'  pattern='yyyy-MM-dd HH:mm:ss'/>" style="width:200px;" readonly="true" />
								</td>
							 
								<th class="wd-20"><label>返回信息</label></th>
								<td>
									<input type="text" id="RESP_INFO" name="RESP_INFO"  value="${RESP_INFO}" style="width:200px;" readonly="true" />
								</td>
								 </tr>
							 <tr>
								<th class="wd-20"><label>返回码</label></th>
								<td>
									<input type="text" id="RESP_CODE" name="RESP_CODE"  value="${RESP_CODE}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 
					</table>
				</div>
			</form>
		</div>
</div>