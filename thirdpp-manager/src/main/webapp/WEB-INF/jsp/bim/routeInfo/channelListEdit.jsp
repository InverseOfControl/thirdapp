<%@ page language="java" pageEncoding="utf-8"%>
<div class="content-body">
		<div class="search-panel-content">		 
								<form id="channelListSave" method="post"  action="${path}/bim/routeInfo/channelListSave">
							 	 	<input id="id" name="id" type="hidden" value="${pChannelInfo.id}" />
							 	 	<input id="thirdTypeNoHidden" name="thirdTypeNoHidden" type="hidden" value="${pChannelInfo.thirdTypeNo}" />
								<table  class="search-table">
								<tr>
									<th class="wd-20"><label>第三方支付平台：</label></th>
									<td><input id="thirdTypeNo" name="thirdTypeNo" style="width: 200px" 
										class="easyui-combobox" data-options="
											required:true,
											editable:false,
											valueField: 'value',
										   textField: 'name',
										   url:'${path}/enumset/dictionary/3',
										   value:'${pChannelInfo.thirdTypeNo}',
										   onLoadSuccess:function(data){ 
											   var temp = $('#thirdTypeNoHidden').val();
											   if(temp){
											   		$(this).combobox('select',temp);
											   	}
										   }
										     "           											 
							 	 	/><span style="color:red"> *</span> </td>
							    </tr>
							    <tr>
							    
							    </tr>
							    	<tr>
							   
									<tr>
									<th class="wd-20"><label>是否可用：</label></th>
									<td> <input style="width: 200px" name="isAvailable"  
															class="easyui-combobox" id="priority"
															data-options="editable:false,data:[{'type':'','text':'请选择'},{'type':'1','text':'是'},{'type':'2','text':'否'} 
															 ],	valueField:'type',textField:'text',value:'${pChannelInfo.isAvailable}'" />									 
									  <span style="color:red">*</span>     
									</td>
							    </tr>
							    
								
							       <tr>
									<th class="wd-20"><label>每日可失败次数</label></th>
									<td><input name="failTimes" type="text" style="width: 200px" 
													value="${pChannelInfo.failTimes}" maxlength="100" class="easyui-numberbox" />
									</td>
							   		 </tr>
							   		 <tr>
									<th class="wd-20"><label>每日可划次数</label></th>
									<td><input name="transferTimes" type="text" style="width: 200px"
													value="${pChannelInfo.transferTimes}" maxlength="100" class="easyui-numberbox" />
									</td>
							   		 </tr>
							   		  <tr>
										<th class="wd-20"><label>每月沉淀量金额：</label></th>
										<td><input name="precipitation" type="text" style="width: 200px"
														value="${pChannelInfo.precipitation}" maxlength="100" class="easyui-numberbox" /> 元
										</td>
							   		 </tr>		
									</table> 								
								</form>
							</div>							
						</div>						