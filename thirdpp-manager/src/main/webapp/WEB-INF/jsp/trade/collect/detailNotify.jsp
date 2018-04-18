<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/xml"  prefix = "x" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/sql"  prefix = "sql" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/functions"  prefix = "fn" %> 
<div class="content-body">
		<div class="search-panel-content">
			<form id="editTppTradeTCollectInfoFrom" name="editTppTradeTCollectInfoFrom" method="post" >
				<div class="search-panel-bd">
					<table class="search-table">
							 <%-- <tr>
								<th class="wd-20"><label>请求ID</label></th>
								<td>
									<input type="text" id="req_id" name="req_id"  value="${req_id}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
 --%>							 
 							 <tr>
								<%-- <th class="wd-20"><label>请求ID</label></th>
								<td>
									<input type="text" id="id" name="id"  value="${ID}" style="width:200px;" readonly="true" />
								</td> --%>
								<th class="wd-20"><label>业务流水号</label></th>
								<td>
									<input type="text" id="biz_flow" name="biz_flow"  value="${NOTIFY.BIZ_FLOW}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>业务系统</label></th>
								<td>
									<input type="text" id="biz_sys_name" name="biz_sys_name"  value="${NOTIFY.BIZ_SYS_NAME}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							  
							 <tr>
								<th class="wd-20"><label>业务类型</label></th>
								<td>
									<input type="text" id="biz_type" name="biz_type"  value="${NOTIFY.BIZ_TYPE}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>创建时间</label></th>
								<td>
									<input type="text" id="create_time" name="create_time"  value="<fmt:formatDate value='${NOTIFY.CREATE_TIME}' type='date'  pattern='yyyy-MM-dd HH:mm:ss'/>" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							  
							  <tr>
								<th class="wd-20"><label>更新时间</label></th>
								<td>
									<input type="text" id="create_time" name="create_time"  value="<fmt:formatDate value='${NOTIFY.UPDATE_TIME}' type='date'  pattern='yyyy-MM-dd HH:mm:ss'/>" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>通知状态</label></th>
								<td>
									<input type="text" id="notify_status" name="notify_status"  value="${NOTIFY.NOTIFY_STATUS}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							  
							  <tr>
								<th class="wd-20"><label>交易结果</label></th>
								<td>
									<input type="text" id="trade_status" name="trade_status"  value="${NOTIFY.TRADE_STATUS}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>交易结果描述</label></th>
								<td>
									<input type="text" id="trade_result_info" name="trade_result_info"  value="${NOTIFY.TRADE_RESULT_INFO}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							  
							  <tr>
								<th class="wd-20"><label>交易流水号</label></th>
								<td>
									<input type="text" id="trade_flow" name="trade_flow"  value="${NOTIFY.TRADE_FLOW}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>交易成功金额</label></th>
								<td>
									<input type="text" id="trade_success_amount" name="trade_success_amount"  value="${NOTIFY.TRADE_SUCCESS_AMOUNT}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							  
							  <tr>
								<th class="wd-20"><label>应用名称</label></th>
								<td>
									<input type="text" id="app_name" name="app_name"  value="${NOTIFY.APP_NAME}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>通知次数</label></th>
								<td>
									<input type="text" id="notify_count" name="notify_count"  value="${NOTIFY.NOTIFY_COUNT}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							  
							  <tr>
								<th class="wd-20"><label>失败原因</label></th>
								<td>
									<input type="text" id="fail_reason" name="fail_reason"  value="${NOTIFY.FAIL_REASON}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>优先级</label></th>
								<td>
									<input type="text" id="priority" name="priority"  value="${NOTIFY.PRIORITY}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							  
							  <tr>
								<th class="wd-20"><label>运营方式</label></th>
								<td>
									<input type="text" id="op_mode" name="op_mode"  value="${NOTIFY.OP_MODE}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>通知URL</label></th>
								<td>
									<input type="text" id="notify_url" name="notify_url"  value="${NOTIFY.NOTIFY_URL}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
					</table>
				</div>
			</form>
	</div>
</div>