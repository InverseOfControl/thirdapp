<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/xml"  prefix = "x" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/sql"  prefix = "sql" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/functions"  prefix = "fn" %> 
<div class="content-body">
	<div class="search-panel toggle-panel">
		<div class="search-panel-content">
			<form id="editTppAccountBizsysNotifyFrom" name="editTppAccountBizsysNotifyFrom" method="post" action="editTppAccountBizsysNotifyAction.action">
				<div class="search-panel-bd">
					<table class="search-table">
							 <tr>
								<th class="wd-20"><label>bizsys_account_day</label></th>
								<td>
									<input type="text" id="tppAccountBizsysNotifyDto.bizsys_account_day" name="tppAccountBizsysNotifyDto.bizsys_account_day"  value="${tppAccountBizsysNotifyDto.bizsys_account_day}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>merchant_no</label></th>
								<td>
									<input type="text" id="tppAccountBizsysNotifyDto.merchant_no" name="tppAccountBizsysNotifyDto.merchant_no"  value="${tppAccountBizsysNotifyDto.merchant_no}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>third_party_account_req_id</label></th>
								<td>
									<input type="text" id="tppAccountBizsysNotifyDto.third_party_account_req_id" name="tppAccountBizsysNotifyDto.third_party_account_req_id"  value="${tppAccountBizsysNotifyDto.third_party_account_req_id}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>status</label></th>
								<td>
									<input type="text" id="tppAccountBizsysNotifyDto.status" name="tppAccountBizsysNotifyDto.status"  value="${tppAccountBizsysNotifyDto.status}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							<tr>
								<th class="wd-20"><label>create_time</label></th>
								<td>
									<input type="text" id="tppAccountBizsysNotifyDto.create_time" name="tppAccountBizsysNotifyDto.create_time"  value="<fmt:formatDate value='${tppAccountBizsysNotifyDto.create_time}' type='date'  pattern='yyyy-MM-dd'/>" style="width:200px;" readonly="true" />
								</td>
							 </tr>
					</table>
				</div>
			</form>
		</div>
	</div>
</div>