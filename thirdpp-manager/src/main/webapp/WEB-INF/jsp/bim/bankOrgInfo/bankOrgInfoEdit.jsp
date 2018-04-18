<%@ page language="java" pageEncoding="utf-8"%>
<div class="content-body">
 	<div class="search-panel-content">
			<form id="bankOrgInfoSave" method="post"  
				action="${path}/bim/bankOrgInfo/bankOrgInfoSave">
				<input id="id" name="id" type="hidden" value="${bankOrgInfo.id}" />
					<table class="search-table">
						<tr>
							<th class="wd-20" style="font-size: 12px"><label>银行名称:</label></th>
							<td><input type="text"    class="easyui-textbox" 
								value="${bankOrgInfo.bankName}" style="width: 200px;" 
								data-options="required:true" readonly="readonly"/>
			
								</td>
						</tr>
						<tr>
							<th class="wd-20" style="font-size: 12px"><label>银行机构名称：</label></th>
							<td><input type="text" id="bankOrgName" name="bankOrgName"  class="easyui-validatebox"
								value="${bankOrgInfo.bankOrgName}" style="width: 200px;" maxlength="100"
								data-options="required:true,novalidate:true,validType:'maxLength[100]'" />
									 <input type="hidden" id=bankCode name="bankCode" class="easyui-validatebox" 
								value="${bankOrgInfo.bankCode}" style="width: 200px;"
							  />
									<span style="color:red">*</span>
								</td>
						</tr>
				  	<tr>
						 
							<th class="wd-20" style="font-size: 12px"><label>银行机构行号：</label></th>
							<td><input type="text" id="bankOrgNo" name="bankOrgNo" class="easyui-validatebox"
								value="${bankOrgInfo.bankOrgNo}" style="width: 200px;" maxlength="15"
								data-options="required:true,novalidate:true,validType:['maxLength[15]','NumberOrLetter']" />
									<span style="color:red">*</span>
								</td>
								<input type="hidden" id="noteOrgNo" name="noteOrgNo" class="easyui-textbox"
										value="${bankOrgInfo.noteOrgNo}"   />
					</tr> 
						   <tr>
							<th class="wd-20" style="font-size: 12px"><label>支付联行号：</label></th>
							<td><input type="text" id="bankLineNo" name="bankLineNo"  class="easyui-validatebox"
								value="${bankOrgInfo.bankLineNo}" style="width: 200px;" maxlength="20"
								data-options="required:true,novalidate:true,validType:['maxLength[20]','Number']" /> 
							 <input type="hidden" id="noteOrgNo2" name="noteOrgNo2" class="easyui-textbox"
								 value="${bankOrgInfo.noteOrgNo2}" /> 
									<span style="color:red">*</span>
								</td>
						</tr>
							  
						
					 
					 	<tr>
						 
							<th class="wd-20" style="font-size: 12px"><label>机构所在省份：</label></th>
							<td><input id="bankOrgProvinceNo2" name="bankOrgProvinceNo" style="width: 200px;"
										class="easyui-combobox" data-options="reqired:'true',
											editable:false,valueField: 'value',
											valueField: 'value',
										   textField: 'name',
										   url:'${path}/enumset/areaInfo/0',
										   onSelect: function(rec){
										            var url = '${path}/enumset/areaInfo/'+rec.value; 
										            $('#bankOrgProvinceCityNo2').combobox('clear');
										
										           $('#bankOrgProvinceCityNo2').combobox('reload', url);
										             
												},
										   onLoadSuccess: function () { 
                        									$('#bankOrgProvinceNo2').combobox('select', '${bankOrgInfo.bankOrgProvinceNo}');
                        									$('#bankOrgProvinceCityNo2').combobox('select', '${bankOrgInfo.bankOrgProvinceCityNo}');
                        									} "           											 
											  />
											  	<span style="color:red">*</span>
											  </td>			
						</tr>  
				 	<tr>					
						 
							<th class="wd-20" style="font-size: 12px"><label>机构所在城市：</label></th>
							<td><input id="bankOrgProvinceCityNo2" style="width: 200px;"
									name="bankOrgProvinceCityNo" class="easyui-combobox"
											data-options="
											editable:false,valueField: 'value',
											textField: 'name' "
							
											  /></td>
						</tr>  
						  <tr>
							 
							<th class="wd-20" style="font-size: 12px"><label>备注：</label></th>
							<td>
							   
							   <textarea   class="easyui-validatebox"   id="note" name="note"   style="width: 200px;height:100px"  maxlength="100" data-options="invalidMessage:'最大长度不能超过100',validType:'maxLength[100]'" 
											  >${bankOrgInfo.note}</textarea>
							   </td>
							  
						</tr> 
					</table>
			</form>
		</div>
	</div>
 

