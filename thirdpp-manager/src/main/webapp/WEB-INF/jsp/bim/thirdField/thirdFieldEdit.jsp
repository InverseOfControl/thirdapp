<%@ page language="java" pageEncoding="utf-8"%>
		<div class="content-body">
				<div class="search-panel-content">
							<form id="thirdFieldSave" method="post"  
								action="${path}/bim/thirdField/thirdFieldSave">
								<input id="id" name="id" type="hidden" value="${thirdField.id}" />								 
									<table class="search-table">
										<tr>
											<th class="wd-20"><label>第三方支付平台:</label></th>
											<td>
											<input type="text"  readonly="readonly" class="easyui-textbox"
												  value="${thirdField.dicName}"
												style="width: 150px;"   />
											<input type="hidden" id="thirdPartyType" class="easyui-validatebox"
												name="thirdPartyType" value="${thirdField.thirdPartyType}"
												style="width: 150px;" data-options="required:true" /></td>
										</tr>
										<tr>
										 
											<th class="wd-20"><label>tpp系统字段编码:</label></th>
											<td><input type="text" id="tppFieldCode" class="easyui-validatebox"
												name="tppFieldCode" value="${thirdField.tppFieldCode}" maxlength="10"
												style="width: 150px;" data-options="required:true,novalidate:true,validType:['maxLength[10]','NumberOrLetter']" class="easyui-validatebox" />
													<span style="color:red">*</span></td>
										</tr>
										<tr>
											<th class="wd-20"><label>第三方平台字段编码:</label></th>
											<td><input type="text" id="thirdFieldCode" class="easyui-validatebox"
												name="thirdFieldCode" value="${thirdField.thirdFieldCode}" maxlength="10"
												style="width: 150px;" data-options="required:true,novalidate:true,validType:['maxLength[10]','NumberOrLetter']" class="easyui-validatebox" />
												
													<span style="color:red">*</span></td>
										</tr>

										<tr>
											<th class="wd-20"><label>字段名称：</label></th>
											<td><input type="text" id="fieldName" name="fieldName" class="easyui-validatebox"
												value="${thirdField.fieldName}" style="width: 150px;" 
												 maxlength="100"   data-options="required:true,novalidate:true,invalidMessage:'最大长度不能超过100',validType:'maxLength[100]'" class="easyui-validatebox" />
													<span style="color:red">*</span></td>
										</tr>
										<tr>
											<th class="wd-20"><label>字段类型：</label></th>
											<td>
											<input type="hidden"  id="selectTypeVal" value="${thirdField.fieldType}" 	  />
											<input style="width: 150px" name="fieldType" class="easyui-combobox" style="width: 300px;"
												  id="fieldTypeSelect" data-options="editable:false,data:[{'type':'','text':'请选择'},{'type':'0','text':'银行编码'},{'type':'1','text':'币种'},{'type':'2','text':'银行卡类型'},{'type':'3','text':'证件类型'},{'type':'4','text':'对公对私标志'}],
													valueField:'type',textField:'text',value:'${thirdField.fieldType}',
									 	  			 onLoadSuccess: function () { 									 	  					 
									 	  					var  ary=	$('#selectTypeVal').val();	
									 	  					if(ary!='0'){
                              								    $('#collectMaxMoney').attr('disabled','disabled');
																$('#payMaxMoney').attr('disabled','disabled');
																$('#quickPayMaxMoney').attr('disabled','disabled');						
									 	  					} 
                        								},
									 	  			onSelect:function(param){
														if(param.type != -1){
															if(param.type =='0'){	
																		$('#collectMaxMoney').removeAttr('disabled');
																		$('#payMaxMoney').removeAttr('disabled')
																		$('#quickPayMaxMoney').removeAttr('disabled')
															}else{				
																$('#quickPayMaxMoney').val('');
																$('#collectMaxMoney').val('');
																$('#payMaxMoney').val('');
																$('#collectMaxMoney').attr('disabled','true');
																$('#payMaxMoney').attr('disabled','true');
																$('#quickPayMaxMoney').attr('disabled','true');		
															}
																}else{																		 
																		$('#quickPayMaxMoney').val('');
																		$('#collectMaxMoney').val('');
																		$('#payMaxMoney').val('');
																		$('#collectMaxMoney').attr('disabled','true');
																		$('#payMaxMoney').attr('disabled','true');
																		$('#quickPayMaxMoney').attr('disabled','true');
															}
			 
												}		" />		 
                        								 
		 
														<span style="color:red">*</span></td>
										</tr>
										<tr>
											<th class="wd-20"><label>代收业务银行最高限额：</label></th>
											<td><input type="text" id="collectMaxMoney" class="easyui-validatebox" maxlength="18"
												name="collectMaxMoney" value="${thirdField.collectMaxMoney1}"
												style="width: 150px;" data-options="validType:'Money'" /></td>
										</tr>
										<tr>
										 
											<th class="wd-20"><label>代付业务银行最高限额：</label></th>
											<td><input type="text" id="payMaxMoney" class="easyui-validatebox"
												name="payMaxMoney" value="${thirdField.payMaxMoney1}" maxlength="18"
												style="width: 150px;"  data-options="validType:'Money'" /></td>
										</tr>
										<tr>
										 
											<th class="wd-20"><label>快捷业务银行最高限额：</label></th>
											<td><input type="text" id="quickPayMaxMoney"
												name="quickPayMaxMoney" class="easyui-validatebox" maxlength="18"
												value="${thirdField.quickPayMaxMoney1}" style="width: 150px;"
												data-options="validType:'Money'" /></td>
										</tr>
									</table>
								 
							</form>
						</div>

					</div>

