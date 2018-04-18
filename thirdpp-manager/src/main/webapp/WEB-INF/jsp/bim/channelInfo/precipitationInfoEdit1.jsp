<%@ page language="java" pageEncoding="utf-8"%>
<div class="content-body">
		<div class="search-panel-content">		 
								<form id="totalPrecipitationInfoSave" method="post"  action="${path}/bim/channelInfo/totalPrecipitationInfoSave">
							 	 	<input id="id" name="id" type="hidden" value="${pChannelInfo.id}" />
								<table  class="search-table">
								<tr>
									<th class="wd-20"><label>第三方支付平台：</label></th>
									<td>
									
									<input   type="text" style="width: 200px" maxlength="50" class="easyui-validatebox" disabled="disabled"	value="${pChannelInfo.thirdTypeName}"/>
									<input id="thirdTypeNo" name="thirdTypeNo"  type="hidden"  value="${pChannelInfo.thirdTypeNo}" />  
									</td>
							    </tr>
								
							       <tr>
									<th class="wd-20"><label>每月资金沉淀量金额：</label></th>
									<td><input name="precipitation" type="text" style="width: 200px" data-options=" required:true,validType:'length[1,12]'"
													value="${pChannelInfo.precipitation}" maxlength="100" class="easyui-numberbox" /> 元
									</td>
							   		 </tr>	
									</table> 								
								</form>
							</div>							
						</div>						