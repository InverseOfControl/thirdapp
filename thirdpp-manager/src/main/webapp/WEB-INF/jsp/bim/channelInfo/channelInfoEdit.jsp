<%@ page language="java" pageEncoding="utf-8"%>
<div class="content-body">
		<div class="search-panel-content">		 
								<form id="channelInfoSave" method="post"  action="${path}/bim/channelInfo/channelInfoSave">
							 	 	<input id="id" name="id" type="hidden" value="${channelInfo.id}" />
								<table  class="search-table">
								<tr>
									<th class="wd-20"><label>第三方支付平台：</label></th>
									<td>
									
									<input   type="text" style="width: 200px" maxlength="50" class="easyui-validatebox" readonly="readonly"	value="${channelInfo.noText}"/>
									<input id="thirdTypeNo" name="thirdTypeNo"  type="hidden"  value="${channelInfo.thirdTypeNo}" />  
									</td>
							    </tr>
								<tr>
								 <th class="wd-20"><label>通道名称：</label></th> 
									<td><input name="channelName" type="text" style="width: 200px"
													value="${channelInfo.channelName}" maxlength="100" class="easyui-validatebox" 
													data-options="required:true"
													/>
										   <span style="color:red">*</span>     
									</td>
							    </tr>
				   
							    <tr>
								 
								<th class="wd-20"><label>商户类型：</label></th>
								<td><input id="merchantType" name="merchantType" style="width: 200px" 
										class="easyui-combobox" data-options="required:true,
											editable:false,valueField: 'value',
											valueField: 'value',
										   textField: 'name',
										   url:'${path}/enumset/merType',
										   value:'${channelInfo.merchantType}'
										     "           											 
							 	 	/>
							 	 	  <span style="color:red">*</span>     
							 	 	<input name="noteNo" type="hidden" style="width: 200px"
													value="${channelInfo.noteNo}" maxlength="50" class="easyui-validatebox" />
							 	 	</td>			
									</tr>
									<tr>
									<th class="wd-20"><label>状态：</label></th>
									<td> <input style="width: 200px" name="status"  
															class="easyui-combobox" id="priority"
															data-options="editable:false,data:[{'type':'','text':'请选择'},{'type':'0','text':'关闭'},{'type':'1','text':'开启'} 
															 ],	valueField:'type',textField:'text',value:'${channelInfo.status}'" />									 
									  <span style="color:red">*</span>     
									</td>
							    </tr>
								<tr>
								 	<th class="wd-20"><label>用户名：</label></th>
									<td><input name="userName" type="text" style="width: 200px"  data-options=" invalidMessage:'最大长度不能超过100',validType:'maxLength[100]'"
													value="${channelInfo.userName}" maxlength="100" class="easyui-validatebox" />
									</td>
							    </tr>
							    <tr>
									<th class="wd-20"><label>用户密码：</label></th>
									<td><input name="userPwd" type="text" style="width: 200px" data-options=" invalidMessage:'最大长度不能超过100',validType:'maxLength[100]'"
													value="${channelInfo.userPwd}" maxlength="100" class="easyui-validatebox" />
									</td>
							    </tr>
								 <tr>
									<th class="wd-20"><label>证书名称：</label></th>
									<td><input name="certificateName" type="text" style="width: 200px" data-options=" invalidMessage:'最大长度不能超过100',validType:'maxLength[100]'"
													value="${channelInfo.certificateName}" maxlength="100" class="easyui-validatebox" />
									</td>
							    </tr>
							    <tr>
									<th class="wd-20"><label>证书密码：</label></th>
									<td><input name="certificatePwd" type="text" style="width: 200px" data-options=" invalidMessage:'最大长度不能超过100',validType:'maxLength[100]'"
													value="${channelInfo.certificatePwd}" maxlength="100" class="easyui-validatebox" />
									</td>
							    </tr>	
							       <tr>
									<th class="wd-20"><label>商户号：</label></th>
									<td><input name="certificateNo" type="text" style="width: 200px" data-options=" invalidMessage:'最大长度不能超过100',validType:'maxLength[100]'"
													value="${channelInfo.certificateNo}" maxlength="100" class="easyui-validatebox" />
									</td>
							   		 </tr>	
									</table> 								
								</form>
							</div>							
						</div>						