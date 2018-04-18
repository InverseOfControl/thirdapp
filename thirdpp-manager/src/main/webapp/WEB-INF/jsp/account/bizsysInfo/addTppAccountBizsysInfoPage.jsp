<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content-body">
	<div class="search-panel toggle-panel">
		<div class="search-panel-content">
			<form id="addTppAccountBizsysInfoFrom" name="addTppAccountBizsysInfoFrom" method="post" action="addTppAccountBizsysInfoAction.action">
				<div class="search-panel-bd">
				 <table class="search-table">
						 <tr>
							<th class="wd-20"><label>biz_sys_no</label></th>
							<td>
								<input type="text" id="tppAccountBizsysInfoDto.biz_sys_no" name="tppAccountBizsysInfoDto.biz_sys_no" class="easyui-validatebox" data-options="required:true,invalidMessage:'biz_sys_no不能为空',missingMessage:'biz_sys_no不能为空'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>biz_flow</label></th>
							<td>
								<input type="text" id="tppAccountBizsysInfoDto.biz_flow" name="tppAccountBizsysInfoDto.biz_flow" class="easyui-validatebox" data-options="required:true,invalidMessage:'biz_flow不能为空',missingMessage:'biz_flow不能为空'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>biz_type</label></th>
							<td>
								<input type="text" id="tppAccountBizsysInfoDto.biz_type" name="tppAccountBizsysInfoDto.biz_type" class="easyui-validatebox" data-options="required:true,invalidMessage:'biz_type不能为空',missingMessage:'biz_type不能为空'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>pay_sys_no</label></th>
							<td>
								<input type="text" id="tppAccountBizsysInfoDto.pay_sys_no" name="tppAccountBizsysInfoDto.pay_sys_no" class="easyui-validatebox" data-options="required:true,invalidMessage:'pay_sys_no不能为空',missingMessage:'pay_sys_no不能为空'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>total_amount</label></th>
							<td>
								<input type="text" id="tppAccountBizsysInfoDto.total_amount" name="tppAccountBizsysInfoDto.total_amount" class="easyui-validatebox" data-options="required:true,validType:'number[2,16]',invalidMessage:'total_amount必须为数字',missingMessage:'total_amount必须为数字'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>success_amount</label></th>
							<td>
								<input type="text" id="tppAccountBizsysInfoDto.success_amount" name="tppAccountBizsysInfoDto.success_amount" class="easyui-validatebox" data-options="required:true,validType:'number[2,16]',invalidMessage:'success_amount必须为数字',missingMessage:'success_amount必须为数字'" style="width:200px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>account_day</label></th>
							<td>
								<input type="text" id="tppAccountBizsysInfoDto.account_day" name="tppAccountBizsysInfoDto.account_day" class="easyui-validatebox" data-options="required:true,invalidMessage:'account_day不能为空',missingMessage:'account_day不能为空'" style="width:200px;"/>
							</td>
						  </tr>
						<tr>
							<th class="wd-20"><label>create_time</label></th>
							<td>
								<input type="text" id="tppAccountBizsysInfoDto.create_time" name="tppAccountBizsysInfoDto.create_time" class="easyui-datebox" data-options="required:true,invalidMessage:'create_time格式必须为yyyy-mm-dd',missingMessage:'create_time格式必须为yyyy-mm-dd'"  style="width:200px;"/>
							</td>
						 </tr>
				  </table>
				</div>
			</form>
		</div>
	</div>
</div>