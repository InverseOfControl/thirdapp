<%@ page language="java" pageEncoding="utf-8"%>
<div class="content-body">
		<div class="search-panel-content">		 
								<form id="precipitationInfoSave" method="post"  action="${path}/bim/channelInfo/precipitationInfoSave">
							 	 	<input id="id" name="id" type="hidden" value="${precipitationInfo.id}" />
								<table  class="search-table">
								<tr>
									<th class="wd-20"><label>第三方支付平台：</label></th>
									<td>
									
									<input   type="text" style="width: 200px" maxlength="50" class="easyui-validatebox" disabled="disabled"	value="${precipitationInfo.thirdTypeName}"/>
									<input id="thirdTypeNo" name="thirdTypeNo"  type="hidden"  value="${precipitationInfo.thirdTypeNo}" />  
									</td>
							    </tr>
							     <tr>
								<th class="wd-20"><label>商户名称</label></th>
								<td><input id="certificateName" name="certificateName"   type="text" style="width: 200px" maxlength="50" data-options=" required:true" class="easyui-validatebox" value="${precipitationInfo.certificateName}"/>
							 	 	  <span style="color:red">*</span>     
							 	 	</td>			
								</tr>
								
								<th class="wd-20"><label>商户号</label></th>
								<td><input id="certificateNo" name="certificateNo"   type="text" style="width: 200px" maxlength="50" data-options=" required:true" class="easyui-validatebox" value="${precipitationInfo.certificateNo}"/>
							 	 	  <span style="color:red">*</span>     
							 	 	</td>			
								</tr>
								
								
								
							       <tr>
									<th class="wd-20"><label>每月资金沉淀量金额：</label></th>
									<td>
									<input name="precipitation" type="text" style="width: 200px" 
													value="${precipitationInfo.precipitation}" maxlength="100" class="easyui-numberbox" /> 元
									</td>
							   		 </tr>	
									</table> 								
								</form>
							</div>							
						</div>						