<%@ page language="java" pageEncoding="utf-8"%>
	<div class="content-body"> 
				<div class="search-panel-content">
					<form id="dictionarySave" method="post"  
						action="${path}/bim/dictionary/dictionarySave">
						<input id="id" name="id" type="hidden" value="${dictionary.id}" />			
						<input id="id" name="parentId" type="hidden" value="${dictionary.parentId}" />						 
						<div class="search-panel-bd">
						<table class="search-table">
							 
								
										
							 
								<tr>
								 
									<th class="wd-20"><label>名称：</label></th>
									<td><input type="text" id="dicName" name="dicName" class="easyui-validatebox"
										value="${dictionary.dicName}" style="width: 200px;" maxlength="50"
										data-options="required:true,novalidate:true,invalidMessage:'最大长度不能超过50',validType:'maxLength[50]'"   /><span style="color:red">*</span></td>
									<td><input type="hidden" id="noteNo" name="noteNo" class="easyui-textbox"
											value="${dictionary.noteNo}"  data-options="required:true" /></td>
								</tr>

								<%-- <tr>
									<td>父级字典： </td>
									<td><select style="width: 200px" name="parentId"
										class="form-control" id="parentIdSelect">
											<option></option>
											<c:forEach items="${dictionaryList}" var="diction">
												<option value="${diction.id}"
													<c:if test="${dictionary.parentId == diction.id}">selected</c:if>>${diction.dicName}</option>
											</c:forEach>
									</select></td>
								</tr> --%>
								<%-- <tr>
								 
									<th class="wd-20"><label>类型：</label></th>
									<td>
									<input id="dicTypeSelect" name="dicType" style="width: 200px;" readonly="readonly"
										class="easyui-combobox" data-options=" 
											editable:false,data:[{'type':'','text':'请选择'},{'type':0,'text':'币种'},{'type':1,'text':'银行卡类型'},{'type':2,'text':'交易状态'},
															{'type':3,'text':'接入支付平台'},{'type':4,'text':'业务类型'},{'type':5,'text':'证件类型'},{'type':6,'text':'商户类型'}],	valueField:'type',textField:'text',value:'${dictionary.dicType}'" />								
									
								 	<span style="color:red">*</span>
								 </td>
								</tr> --%>
								<tr>
										<th class="wd-20"><label>描述：</label></th>
									<td> 
										<textarea   id="dicDesc" name="dicDesc"   maxlength="200"  class="easyui-validatebox"
										  style="width: 200px;height: 100px" data-options="invalidMessage:'最大长度不能超过200',validType:'maxLength[200]'"
										  >${dictionary.dicDesc}</textarea>
										
										</td>
								</tr>
 
							</table>
						 </div>
					</form>
				</div>

			</div>
 

 

 
