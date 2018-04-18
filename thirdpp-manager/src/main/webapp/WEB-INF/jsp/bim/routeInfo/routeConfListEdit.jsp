<%@ page language="java" pageEncoding="utf-8"%>
<div class="content-body">
		<div class="search-panel-content">		 
								<form id="routeConfInfoSave" method="post"  action="${path}/bim/channelInfo/routeConfInfoSave">
							 	 	<input id="id" name="id" type="hidden" value="${routeConf.id}" />
								<table  class="search-table">
								<tr>
									<th class="wd-20"><label>路由逻辑名称：</label></th>
									<td>
									
									<input id="routeName" name="routeName"   type="text" style="width: 200px" maxlength="50" 
									data-options="required:true"
									class="easyui-validatebox"	value="${routeConf.routeName}"/>
									<span style="color:red">*</span> 
									</td>
							    </tr>
								<tr>
							   
									<tr>
									<th class="wd-20"><label>是否可用：</label></th>
									<td> <input style="width: 200px" name="isAvailable"  
															class="easyui-combobox" id="priority"
															data-options="editable:false,data:[{'type':'','text':'请选择'},{'type':'1','text':'是'},{'type':'2','text':'否'} 
															 ],	valueField:'type',textField:'text',value:'${routeConf.isAvailable}'" />									 
									  <span style="color:red">*</span>     
									</td>
							    </tr>
							    
							    <th class="wd-20"><label>路由逻辑类：</label></th> 
									<td><input name="routeClass" type="text" style="width: 200px"
													value="${routeConf.routeClass}" maxlength="100" class="easyui-validatebox" 
													data-options="required:true,invalidMessage:'路由逻辑类不能为空'"
													/>
										   <span style="color:red">*</span>     
									</td>
							    </tr>
								
									</table> 								
								</form>
							</div>							
						</div>						