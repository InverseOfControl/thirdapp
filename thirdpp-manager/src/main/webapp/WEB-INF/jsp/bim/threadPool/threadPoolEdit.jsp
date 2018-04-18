<%@ page language="java" pageEncoding="utf-8"%>
<div class="content-body">
	<div class="search-panel-content">
		<form id="threadPoolSave" method="post"
			action="${path}/bim/threadPool/threadPoolSave">
			<input id="id" name="id" type="hidden" value="${threadPool.id}" />
			<table  class="search-table">
				<tr>
				 
						<th class="wd-20"><label>业务类型：</label></th>
					<td> 
							<input id="bizTypeSelect" name="bizType" style="width: 200px" 
										class="easyui-combobox" data-options="
											editable:false,valueField: 'value',
											valueField: 'value',
										   textField: 'name',
										   url:'${path}/enumset/bizTypeList',
										  value:'${threadPool.bizType}'
										     "    /> 
									<span style="color:red">*</span>
					
					</td>
				</tr>
				<tr>
					 
						<th class="wd-20"><label>第三方支付平台：</label></th>
					<td> 
						 
						<input id="paySysNo" name="paySysNo" style="width: 200px" 
										class="easyui-combobox" data-options="
											editable:false,valueField: 'value',
											valueField: 'value',
										   textField: 'name',
										   url:'${path}/enumset/dictionary/3',
										  value:'${threadPool.paySysNo}'
										     "    /> 
										    <span style="color:red">*</span>      
						
						</td>
				</tr>
				 
				 
				<tr>
					 
						<th class="wd-20"><label>应用程序名称：</label></th>
					<td><input type="text" id="appName" name="appName" maxlength="100"
						class="easyui-validatebox" value="${threadPool.appName}"
						style="width: 200px;"data-options="required:true,novalidate:true,invalidMessage:'最大长度不能超过100',validType:'maxLength[100]'" class="easyui-validatebox" />
						<span style="color:red">*</span>
						</td>
						
				</tr>
				<tr>
						 
					 <th class="wd-20"><label>睡眠时间：</label></th>
					<td><input type="text" id="sleepTime" name="sleepTime"
						class="easyui-validatebox" value="${threadPool.sleepTime}" maxlength="10"
						style="width: 200px;" data-options="required:true,novalidate:true,validType:['maxLength[10]','Number']" />(/ms)
						 <span style="color:red">*</span>    
						</td>
				</tr>
				<tr>
				 
						<th class="wd-20"><label>状态：</label></th>
					<td> 
					<input id="isActiveSelect" name="isActive" style="width: 200px;"
										class="easyui-combobox" data-options="
											editable:false,data:[{'type':'','text':'请选择'},{'type':'0','text':'关闭'},{'type':'1','text':'开启'} 
															],	valueField:'type',textField:'text',value:'${threadPool.isActive}'" />
				    
					<span style="color:red">*</span></td>
				</tr>	
			</table>

		</form>
	</div>
</div>



