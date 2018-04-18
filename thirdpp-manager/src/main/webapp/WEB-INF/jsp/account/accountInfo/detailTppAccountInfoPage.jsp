<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/xml"  prefix = "x" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/sql"  prefix = "sql" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/functions"  prefix = "fn" %> 
<div class="content-body">
	<div class="search-panel toggle-panel">
		<div class="search-panel-content">
			<form id="editTppAccountInfoFrom" name="editTppAccountInfoFrom" method="post" action="editTppAccountInfoAction.action">
				<div class="search-panel-bd">
					<table class="search-table">
							 <tr>
								<th class="wd-20"><label>third_type_no</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.third_type_no" name="tppAccountInfoDto.third_type_no"  value="${tppAccountInfoDto.third_type_no}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>merchant_no</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.merchant_no" name="tppAccountInfoDto.merchant_no"  value="${tppAccountInfoDto.merchant_no}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>biz_type</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.biz_type" name="tppAccountInfoDto.biz_type"  value="${tppAccountInfoDto.biz_type}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>third_party_account_req_id</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.third_party_account_req_id" name="tppAccountInfoDto.third_party_account_req_id"  value="${tppAccountInfoDto.third_party_account_req_id}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>bizsys_account_req_id</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.bizsys_account_req_id" name="tppAccountInfoDto.bizsys_account_req_id"  value="${tppAccountInfoDto.bizsys_account_req_id}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>task_id</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.task_id" name="tppAccountInfoDto.task_id"  value="${tppAccountInfoDto.task_id}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>is_separate</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.is_separate" name="tppAccountInfoDto.is_separate"  value="${tppAccountInfoDto.is_separate}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>separate_count</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.separate_count" name="tppAccountInfoDto.separate_count"  value="${tppAccountInfoDto.separate_count}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>biz_sys_no</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.biz_sys_no" name="tppAccountInfoDto.biz_sys_no"  value="${tppAccountInfoDto.biz_sys_no}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>trade_flow</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.trade_flow" name="tppAccountInfoDto.trade_flow"  value="${tppAccountInfoDto.trade_flow}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>amount</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.amount" name="tppAccountInfoDto.amount"  value="${tppAccountInfoDto.amount}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>original_amount</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.original_amount" name="tppAccountInfoDto.original_amount"  value="${tppAccountInfoDto.original_amount}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>third_party_trade_flow</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.third_party_trade_flow" name="tppAccountInfoDto.third_party_trade_flow"  value="${tppAccountInfoDto.third_party_trade_flow}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>trade_time</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.trade_time" name="tppAccountInfoDto.trade_time"  value="${tppAccountInfoDto.trade_time}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>third_party_update_time</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.third_party_update_time" name="tppAccountInfoDto.third_party_update_time"  value="${tppAccountInfoDto.third_party_update_time}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>current_index</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.current_index" name="tppAccountInfoDto.current_index"  value="${tppAccountInfoDto.current_index}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>account_status</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.account_status" name="tppAccountInfoDto.account_status"  value="${tppAccountInfoDto.account_status}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>bizsys_account_status</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.bizsys_account_status" name="tppAccountInfoDto.bizsys_account_status"  value="${tppAccountInfoDto.bizsys_account_status}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>failed_reason</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.failed_reason" name="tppAccountInfoDto.failed_reason"  value="${tppAccountInfoDto.failed_reason}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							<tr>
								<th class="wd-20"><label>create_time</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.create_time" name="tppAccountInfoDto.create_time"  value="<fmt:formatDate value='${tppAccountInfoDto.create_time}' type='date'  pattern='yyyy-MM-dd'/>" style="width:200px;" readonly="true" />
								</td>
							 </tr>
							 <tr>
								<th class="wd-20"><label>biz_flow</label></th>
								<td>
									<input type="text" id="tppAccountInfoDto.biz_flow" name="tppAccountInfoDto.biz_flow"  value="${tppAccountInfoDto.biz_flow}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
					</table>
				</div>
			</form>
		</div>
	</div>
</div>