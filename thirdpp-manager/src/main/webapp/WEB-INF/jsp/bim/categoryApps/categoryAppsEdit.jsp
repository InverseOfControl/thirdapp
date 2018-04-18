<%@ page language="java" pageEncoding="utf-8"%>
<div class="content-body">
	<div class="search-panel-content">
		<form id="infoCategoryAppsSave" method="post"
			action="${path}/bim/categoryApp/categoryAppsSave">
			<input id="id" name="id" type="hidden" value="${categoryApps.id}" />

			<table class="search-table">


				<tr>

					<th class="wd-20"><label>信息类别名称：</label></th>
					<td><input name="infoCateGoryName" type="text"
						style="width: 200px"
						data-options="required:true,novalidate:true,invalidMessage:'最大长度不能超过50',validType:'maxLength[50]'"
						value="${categoryApps.infoCateGoryName}" maxlength="50"
						class="easyui-validatebox" /> <span style="color: red">*</span> <input
						type="hidden" id="appCodeValue" value="${appCodealue}" /> <input
						type="hidden" id="noteNo" name="noteNo" class="easyui-textbox"
						value="${categoryApps.noteNo}"
						data-options="required:true,novalidate:true" /> <input
						name="infoCateGoryCode" type="hidden" style="width: 200px"
						data-options="required:true,novalidate:true,validType:['maxLength[10]','NumberOrLetter']"
						value="${categoryApps.infoCateGoryCode}" maxlength="10"
						class="easyui-validatebox" /></td>
				</tr>

				<tr>

					<th class="wd-20"><label>第三方支付平台：</label></th>


					<td><input id="paymentChannel" name="paymentChannel"
						style="width: 200px" class="easyui-combobox"
						data-options="
											editable:false, 
											valueField: 'value',
										   textField: 'name',
										   value:'${categoryApps.paymentChannel}',
										   url:'${path}/enumset/dictionary/3'
										  
										     " />
					</td>
				</tr>
				<tr>

					<th class="wd-20"><label>优先级：</label></th>
					<td><input style="width: 200px" name="priority"
						class="easyui-combobox" id="priority"
						data-options="editable:false,data:[{'type':'','text':'请选择'},{'type':'0','text':'普通'},{'type':'1','text':'中'},{'type':'2','text':'高'},
															{'type':'3','text':'最高'}],	valueField:'type',textField:'text',value:'${categoryApps.priority}'" />
					</td>
				</tr>
				<tr>

					<th class="wd-20"><label>商户类型：</label></th>
					<td><input id="merchantType" name="merchantType"
						style="width: 200px" class="easyui-combobox"
						data-options="
											editable:false,valueField: 'value',
											valueField: 'value',
										   textField: 'name',
										   url:'${path}/enumset/merType',
										   value:'${categoryApps.merchantType}'
										     " /></td>
				</tr>
				<tr>

					<th class="wd-20"><label>系统：</label></th>
					<td><input id="appsCode" name="appCode" style="width: 200px"
						class="easyui-combotree"
						data-options="
											editable:false, 
										   multiple: 'true',
										  url:'${path}/enumset/bizSysNoList/combobox',
									 	  onLoadSuccess: function () { 									 	  					 
									 	  					var strAry, ary=$('#appCodeValue').val();
									 	  					if(ary==''){
									 	  						$('#appsCode').combotree('setText', '请选择');	
									 	  					}else{
									 	  						strAry=ary.split(',');	
									 	  						$('#appsCode').combotree('setValues',strAry );
									 	  					}
									 	  				 									 	  					 
                        								}
										     " /></td>

				</tr>
				<tr>

					<th class="wd-20"><label>通道规则：</label></th>
					<td><input style="width: 200px" name="channelRule"
						class="easyui-combobox" id="channelRule"
						data-options="editable:false,data:[{'type':'1','text':'按业务系统指定通道'},{'type':'2','text':'按银行指定通道'},{'type':'3','text':'按路由规则指定通道'}],	valueField:'type',textField:'text',value:'${categoryApps.channelRule}'" />
					</td>
				</tr>
				<tr>
					<th class="wd-20"><label>备注：</label></th>
					<td><textarea name="note" type="text" name="note"
							style="width: 200px; height: 100px" class="easyui-validatebox"
							maxlength="512"
							data-options="required:true,novalidate:true,invalidMessage:'最大长度不能超过512',validType:'maxLength[512]'">${categoryApps.note}</textarea>
						<span style="color: red">*</span></td>
				</tr>
			</table>
		</form>
	</div>
</div>
