<%@ page language="java" pageEncoding="utf-8"%>
<div class="content-body">
	<div class="search-panel-content">
		<form id="sysAppSave" method="post" name="sysAppSave"
			action="${path}/bim/sysApp/sysAppSave">
			<input id="id" name="id" type="hidden" value="${sysApp.id}" />
			<table  class="search-table">
				<tr>
				 
					<th class="wd-20" style="font-size: 12px"><label>业务系统名称：</label></th>
					<td><input name="appName" type="text"  style="width: 200px" data-options="required:true,novalidate:true,invalidMessage:'最大长度不能超过64',validType:'maxLength[64]'" class="easyui-validatebox" 
						value="${sysApp.appName}" maxlength="64"   />
					<span style="color:red">*</span>
					</td>
				</tr>
				<tr>
				 
					<th class="wd-20" style="font-size: 12px"><label>业务系统编号：</label></th>
					<td><input name="appCode" type="text"  style="width: 200px" data-options="required:true,novalidate:true,validType:['maxLength[10]','NumberOrLetter']" class="easyui-validatebox" 
						value="${sysApp.appCode}" maxlength="4" class="easyui-validatebox" />
							<input type="hidden" id="noteNo" name="noteNo"  
											value="${sysApp.noteNo}"  data-options="required:true" />
					<span style="color:red">*</span>
					</td>
				</tr>
				<tr>
				 
					<th class="wd-20" style="font-size: 12px"><label>状态</label></th>
					<td><input style="width: 200px" name="isFlag" 
						class="easyui-combobox" id="isFlagSelect"
						data-options="editable:false,data:[{'type':'','text':'请选择'},{'type':'0','text':'关闭'},{'type':'1','text':'开启'}],
													valueField:'type',textField:'text',value:'${sysApp.isFlag}'" />
					<span style="color:red">*</span>
					</td>
				</tr>


				<tr>
				 
					<th class="wd-20" style="font-size: 12px"><label>联系人姓名：</label></th>
					<td><input name="contactName" type="text"  style="width: 200px" 
						value="${sysApp.contactName}" maxlength="64" data-options=" invalidMessage:'最大长度不能超过64',validType:'maxLength[64]'" class="easyui-validatebox" 
					 class="easyui-validatebox" /></td>
				</tr>
			
				<tr>
				 
					<th class="wd-20" style="font-size: 12px"><label>联系人手机：</label></th>
					<td><input name="contactMobile" type="text"  style="width: 200px" 
						value="${sysApp.contactMobile}" maxlength="24"  data-options=" invalidMessage:'最大长度不能超过24',validType:'maxLength[24]'" class="easyui-validatebox" 
						 class="easyui-validatebox"  /></td>
				</tr>
					 
					<tr>
						<th class="wd-20" style="font-size: 12px"><label>线下代扣/还款通知URL：</label></th>
					<td><input name="collectNotifyUrl" type="text"  style="width: 200px"
						value="${sysApp.collectNotifyUrl}" maxlength="218"  data-options=" invalidMessage:'最大长度不能超过218',validType:'maxLength[218]'" class="easyui-validatebox" 
						class="easyui-validatebox" /></td>
				</tr>
					<tr>
			
						<th class="wd-20" style="font-size: 12px"><label>线下代付/融资通知URL：</label></th>
					<td><input name="payNotifyUrl" type="text"  style="width: 200px"
						value="${sysApp.payNotifyUrl}" maxlength="218"  data-options=" invalidMessage:'最大长度不能超过218',validType:'maxLength[218]'" class="easyui-validatebox" 
						class="easyui-validatebox" /></td>
				</tr>
					<tr>
			
						<th class="wd-20" style="font-size: 12px"><label>线上退款通知URL：</label></th>
					<td><input name="cashBackNotifyUrl" type="text"  style="width: 200px"
						value="${sysApp.cashBackNotifyUrl}" maxlength="218"  data-options=" invalidMessage:'最大长度不能超过218',validType:'maxLength[218]'" class="easyui-validatebox" 
						class="easyui-validatebox" /></td>
				</tr>
					<tr>
			
						<th class="wd-20" style="font-size: 12px"><label>线上提现通知URL：</label></th>
					<td><input name="cashDrawNotifyUrl" type="text"  style="width: 200px"
						value="${sysApp.cashDrawNotifyUrl}" maxlength="218"  data-options=" invalidMessage:'最大长度不能超过218',validType:'maxLength[218]'" class="easyui-validatebox" 
						class="easyui-validatebox" /></td>
				</tr>
					<tr>
			
						<th class="wd-20" style="font-size: 12px"><label>线上订单支付通知URL：</label></th>
					<td><input name="orderPayNotifyUrl" type="text"  style="width: 200px"
						value="${sysApp.orderPayNotifyUrl}" maxlength="218"  data-options=" invalidMessage:'最大长度不能超过218',validType:'maxLength[218]'" class="easyui-validatebox" 
						class="easyui-validatebox" /></td>
				</tr>
				<tr>

				 
					<th class="wd-20" style="font-size: 12px"><label>备注：</label></th>
					<td> 
						<textarea   id="remark" name="remark"   class="easyui-validatebox"
										  style="width: 200px;height: 100px" maxlength="200"  data-options=" invalidMessage:'最大长度不能超过200',validType:'maxLength[200]'" class="easyui-validatebox" >${sysApp.remark}</textarea> 
										 
						</td>

				</tr>
			</table>
		</form>
	</div>
</div>




