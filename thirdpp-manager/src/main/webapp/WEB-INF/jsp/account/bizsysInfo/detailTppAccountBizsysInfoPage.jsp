<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/xml"  prefix = "x" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/sql"  prefix = "sql" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/functions"  prefix = "fn" %> 
<div class="content-body">
	<div class="search-panel toggle-panel">
		<div class="search-panel-content">
			<form id="editTppAccountBizsysInfoFrom" name="editTppAccountBizsysInfoFrom" method="post" action="editTppAccountBizsysInfoAction.action">
				<div class="search-panel-bd">
					<table class="search-table">
							 <tr>
								<th class="wd-20"><label>biz_sys_no</label></th>
								<td>
									<input type="text" id="tppAccountBizsysInfoDto.biz_sys_no" name="tppAccountBizsysInfoDto.biz_sys_no"  value="${tppAccountBizsysInfoDto.biz_sys_no}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>biz_flow</label></th>
								<td>
									<input type="text" id="tppAccountBizsysInfoDto.biz_flow" name="tppAccountBizsysInfoDto.biz_flow"  value="${tppAccountBizsysInfoDto.biz_flow}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>biz_type</label></th>
								<td>
									<input type="text" id="tppAccountBizsysInfoDto.biz_type" name="tppAccountBizsysInfoDto.biz_type"  value="${tppAccountBizsysInfoDto.biz_type}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>pay_sys_no</label></th>
								<td>
									<input type="text" id="tppAccountBizsysInfoDto.pay_sys_no" name="tppAccountBizsysInfoDto.pay_sys_no"  value="${tppAccountBizsysInfoDto.pay_sys_no}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>total_amount</label></th>
								<td>
									<input type="text" id="tppAccountBizsysInfoDto.total_amount" name="tppAccountBizsysInfoDto.total_amount"  value="${tppAccountBizsysInfoDto.total_amount}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>success_amount</label></th>
								<td>
									<input type="text" id="tppAccountBizsysInfoDto.success_amount" name="tppAccountBizsysInfoDto.success_amount"  value="${tppAccountBizsysInfoDto.success_amount}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>account_day</label></th>
								<td>
									<input type="text" id="tppAccountBizsysInfoDto.account_day" name="tppAccountBizsysInfoDto.account_day"  value="${tppAccountBizsysInfoDto.account_day}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							<tr>
								<th class="wd-20"><label>create_time</label></th>
								<td>
									<input type="text" id="tppAccountBizsysInfoDto.create_time" name="tppAccountBizsysInfoDto.create_time"  value="<fmt:formatDate value='${tppAccountBizsysInfoDto.create_time}' type='date'  pattern='yyyy-MM-dd'/>" style="width:200px;" readonly="true" />
								</td>
							 </tr>
					</table>
				</div>
			</form>
		</div>
	</div>
</div>