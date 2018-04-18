<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/xml"  prefix = "x" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/sql"  prefix = "sql" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/functions"  prefix = "fn" %> 
<div class="content-body">
	<div class="search-panel toggle-panel">
		<div class="search-panel-content">
			<form id="editTppAccountBizsysConfigFrom" name="editTppAccountBizsysConfigFrom" method="post" action="editTppAccountBizsysConfigAction.action">
				<div class="search-panel-bd">
					<table class="search-table">
							 <tr>
								<th class="wd-20"><label>biz_sys_no</label></th>
								<td>
									<input type="text" id="tppAccountBizsysConfigDto.biz_sys_no" name="tppAccountBizsysConfigDto.biz_sys_no"  value="${tppAccountBizsysConfigDto.biz_sys_no}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>bizsys_account_mode</label></th>
								<td>
									<input type="text" id="tppAccountBizsysConfigDto.bizsys_account_mode" name="tppAccountBizsysConfigDto.bizsys_account_mode"  value="${tppAccountBizsysConfigDto.bizsys_account_mode}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>ftp_username</label></th>
								<td>
									<input type="text" id="tppAccountBizsysConfigDto.ftp_username" name="tppAccountBizsysConfigDto.ftp_username"  value="${tppAccountBizsysConfigDto.ftp_username}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>ftp_pwd</label></th>
								<td>
									<input type="text" id="tppAccountBizsysConfigDto.ftp_pwd" name="tppAccountBizsysConfigDto.ftp_pwd"  value="${tppAccountBizsysConfigDto.ftp_pwd}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>ftp_path</label></th>
								<td>
									<input type="text" id="tppAccountBizsysConfigDto.ftp_path" name="tppAccountBizsysConfigDto.ftp_path"  value="${tppAccountBizsysConfigDto.ftp_path}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>local_account_root_path</label></th>
								<td>
									<input type="text" id="tppAccountBizsysConfigDto.local_account_root_path" name="tppAccountBizsysConfigDto.local_account_root_path"  value="${tppAccountBizsysConfigDto.local_account_root_path}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>bizsys_account_time</label></th>
								<td>
									<input type="text" id="tppAccountBizsysConfigDto.bizsys_account_time" name="tppAccountBizsysConfigDto.bizsys_account_time"  value="${tppAccountBizsysConfigDto.bizsys_account_time}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>file_name_format</label></th>
								<td>
									<input type="text" id="tppAccountBizsysConfigDto.file_name_format" name="tppAccountBizsysConfigDto.file_name_format"  value="${tppAccountBizsysConfigDto.file_name_format}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>file_suffix</label></th>
								<td>
									<input type="text" id="tppAccountBizsysConfigDto.file_suffix" name="tppAccountBizsysConfigDto.file_suffix"  value="${tppAccountBizsysConfigDto.file_suffix}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>file_type</label></th>
								<td>
									<input type="text" id="tppAccountBizsysConfigDto.file_type" name="tppAccountBizsysConfigDto.file_type"  value="${tppAccountBizsysConfigDto.file_type}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>attrs_definition</label></th>
								<td>
									<input type="text" id="tppAccountBizsysConfigDto.attrs_definition" name="tppAccountBizsysConfigDto.attrs_definition"  value="${tppAccountBizsysConfigDto.attrs_definition}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>header_attrs_definition</label></th>
								<td>
									<input type="text" id="tppAccountBizsysConfigDto.header_attrs_definition" name="tppAccountBizsysConfigDto.header_attrs_definition"  value="${tppAccountBizsysConfigDto.header_attrs_definition}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>attrs_split</label></th>
								<td>
									<input type="text" id="tppAccountBizsysConfigDto.attrs_split" name="tppAccountBizsysConfigDto.attrs_split"  value="${tppAccountBizsysConfigDto.attrs_split}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>max_push_failed_time</label></th>
								<td>
									<input type="text" id="tppAccountBizsysConfigDto.max_push_failed_time" name="tppAccountBizsysConfigDto.max_push_failed_time"  value="${tppAccountBizsysConfigDto.max_push_failed_time}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>currency_unit</label></th>
								<td>
									<input type="text" id="tppAccountBizsysConfigDto.currency_unit" name="tppAccountBizsysConfigDto.currency_unit"  value="${tppAccountBizsysConfigDto.currency_unit}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>status</label></th>
								<td>
									<input type="text" id="tppAccountBizsysConfigDto.status" name="tppAccountBizsysConfigDto.status"  value="${tppAccountBizsysConfigDto.status}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							<tr>
								<th class="wd-20"><label>create_time</label></th>
								<td>
									<input type="text" id="tppAccountBizsysConfigDto.create_time" name="tppAccountBizsysConfigDto.create_time"  value="<fmt:formatDate value='${tppAccountBizsysConfigDto.create_time}' type='date'  pattern='yyyy-MM-dd'/>" style="width:200px;" readonly="true" />
								</td>
							 </tr>
					</table>
				</div>
			</form>
		</div>
	</div>
</div>