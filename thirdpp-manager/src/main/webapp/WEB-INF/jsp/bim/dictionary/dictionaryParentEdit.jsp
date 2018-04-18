<%@ page language="java" pageEncoding="utf-8"%>
 
	<div class="content-body">
				<div class="search-panel-content">
					 
					<form id="dictionarySave" method="post"  
						action="${path}/bim/dictionary/dictionarySave">
						<input id="id" name="id" type="hidden" value="${dictionary.id}" />
					 
						<table class="search-table">
								<tr>
								 
									<th class="wd-20" style="font-size: 12px"><label>编码：</label></th>
									<td><input type="text" id="dicCode" name="dicCode" maxlength="10"
										value="${dictionary.dicCode}" style="width: 200px;" class="easyui-validatebox"
										data-options="required:true,novalidate:true,validType:['maxLength[10]','NumberOrLetter']" /><span style="color:red">*</span></td>
										
											 
										<input type="hidden" id="noteNo" name="noteNo" class="easyui-textbox"
											value="${dictionary.noteNo}"  data-options="required:true" />
											<input type="hidden" id="noteNo2" name="noteNo2" class="easyui-textbox"
											value="${dictionary.noteNo2}"  data-options="required:true" />
								</tr>
								<tr>
								 
									<th class="wd-20" style="font-size: 12px"><label>名称：</label></th>
									<td><input type="text" id="dicName" name="dicName" class="easyui-validatebox"
										value="${dictionary.dicName}" style="width: 200px;" maxlength="50"
											data-options="required:true,novalidate:true,invalidMessage:'最大长度不能超过50',validType:'maxLength[50]'"   /><span style="color:red">*</span></td>
											 
								</tr>
							
								<tr>
								 
									<th class="wd-20" style="font-size: 12px"><label>描述：</label></th>
									<td><textarea   id="dicDesc" name="dicDesc"   maxlength="200"  class="easyui-validatebox"
										  style="width: 200px;height: 100px" data-options="invalidMessage:'最大长度不能超过200',validType:'maxLength[200]'"
										  >${dictionary.dicDesc}</textarea></td>
								</tr>
							</table>
							 
					</form>
				</div>

		 

</div>