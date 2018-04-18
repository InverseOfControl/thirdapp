<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content-body">
	<div class="search-panel toggle-panel">
		<div class="search-panel-content">
			<form id="addTppAccountInfoFrom" name="addTppAccountInfoFrom" method="post" action="addTppAccountInfoAction.action">
				<div class="search-panel-bd">
				 <table class="search-table">
						 <tr>
							<th class="wd-20"><label>third_type_no</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.third_type_no" name="tppAccountInfoDto.third_type_no" class="easyui-validatebox" data-options="required:true,invalidMessage:'third_type_no不能为空',missingMessage:'third_type_no不能为空'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>merchant_no</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.merchant_no" name="tppAccountInfoDto.merchant_no" class="easyui-validatebox" data-options="required:true,invalidMessage:'merchant_no不能为空',missingMessage:'merchant_no不能为空'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>biz_type</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.biz_type" name="tppAccountInfoDto.biz_type" class="easyui-validatebox" data-options="required:true,invalidMessage:'biz_type不能为空',missingMessage:'biz_type不能为空'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>third_party_account_req_id</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.third_party_account_req_id" name="tppAccountInfoDto.third_party_account_req_id" class="easyui-validatebox" data-options="required:true,invalidMessage:'third_party_account_req_id不能为空',missingMessage:'third_party_account_req_id不能为空'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>bizsys_account_req_id</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.bizsys_account_req_id" name="tppAccountInfoDto.bizsys_account_req_id" class="easyui-validatebox" data-options="required:true,invalidMessage:'bizsys_account_req_id不能为空',missingMessage:'bizsys_account_req_id不能为空'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>task_id</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.task_id" name="tppAccountInfoDto.task_id" class="easyui-validatebox" data-options="required:true,validType:'number[2,16]',invalidMessage:'task_id必须为数字',missingMessage:'task_id必须为数字'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>is_separate</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.is_separate" name="tppAccountInfoDto.is_separate" class="easyui-validatebox" data-options="required:true,validType:'number[2,16]',invalidMessage:'is_separate必须为数字',missingMessage:'is_separate必须为数字'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>separate_count</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.separate_count" name="tppAccountInfoDto.separate_count" class="easyui-validatebox" data-options="required:true,validType:'number[2,16]',invalidMessage:'separate_count必须为数字',missingMessage:'separate_count必须为数字'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>biz_sys_no</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.biz_sys_no" name="tppAccountInfoDto.biz_sys_no" class="easyui-validatebox" data-options="required:true,invalidMessage:'biz_sys_no不能为空',missingMessage:'biz_sys_no不能为空'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>trade_flow</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.trade_flow" name="tppAccountInfoDto.trade_flow" class="easyui-validatebox" data-options="required:true,invalidMessage:'trade_flow不能为空',missingMessage:'trade_flow不能为空'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>amount</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.amount" name="tppAccountInfoDto.amount" class="easyui-validatebox" data-options="required:true,validType:'number[2,16]',invalidMessage:'amount必须为数字',missingMessage:'amount必须为数字'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>original_amount</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.original_amount" name="tppAccountInfoDto.original_amount" class="easyui-validatebox" data-options="required:true,validType:'number[2,16]',invalidMessage:'original_amount必须为数字',missingMessage:'original_amount必须为数字'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>third_party_trade_flow</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.third_party_trade_flow" name="tppAccountInfoDto.third_party_trade_flow" class="easyui-validatebox" data-options="required:true,invalidMessage:'third_party_trade_flow不能为空',missingMessage:'third_party_trade_flow不能为空'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>trade_time</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.trade_time" name="tppAccountInfoDto.trade_time" class="easyui-validatebox" data-options="required:true,invalidMessage:'trade_time不能为空',missingMessage:'trade_time不能为空'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>third_party_update_time</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.third_party_update_time" name="tppAccountInfoDto.third_party_update_time" class="easyui-validatebox" data-options="required:true,invalidMessage:'third_party_update_time不能为空',missingMessage:'third_party_update_time不能为空'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>current_index</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.current_index" name="tppAccountInfoDto.current_index" class="easyui-validatebox" data-options="required:true,validType:'number[2,16]',invalidMessage:'current_index必须为数字',missingMessage:'current_index必须为数字'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>account_status</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.account_status" name="tppAccountInfoDto.account_status" class="easyui-validatebox" data-options="required:true,validType:'number[2,16]',invalidMessage:'account_status必须为数字',missingMessage:'account_status必须为数字'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>bizsys_account_status</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.bizsys_account_status" name="tppAccountInfoDto.bizsys_account_status" class="easyui-validatebox" data-options="required:true,validType:'number[2,16]',invalidMessage:'bizsys_account_status必须为数字',missingMessage:'bizsys_account_status必须为数字'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>failed_reason</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.failed_reason" name="tppAccountInfoDto.failed_reason" class="easyui-validatebox" data-options="required:true,invalidMessage:'failed_reason不能为空',missingMessage:'failed_reason不能为空'" style="width:200px;"/>
							</td>
						  </tr>
						<tr>
							<th class="wd-20"><label>create_time</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.create_time" name="tppAccountInfoDto.create_time" class="easyui-datebox" data-options="required:true,invalidMessage:'create_time格式必须为yyyy-mm-dd',missingMessage:'create_time格式必须为yyyy-mm-dd'"  style="width:200px;"/>
							</td>
						 </tr>
						 <tr>
							<th class="wd-20"><label>biz_flow</label></th>
							<td>
								<input type="text" id="tppAccountInfoDto.biz_flow" name="tppAccountInfoDto.biz_flow" class="easyui-validatebox" data-options="required:true,invalidMessage:'biz_flow不能为空',missingMessage:'biz_flow不能为空'" style="width:200px;"/>
							</td>
						  </tr>
				  </table>
				</div>
			</form>
		</div>
	</div>
</div>