<%@ page language="java" pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<div class="content-body">
 
		<div class="search-panel-content">
							<form id="areaInfoSave" method="post"  
								action="${path}/bim/areaInfo/areaInfoSave">
								<input id="id" name="id" type="hidden" value="${areaInfo.id}" />								 
										<table  class="search-table">
										<tr>
										 
											<th class="wd-20"><label>所属省份：</label></th>
											<td>
												<input type="text"  class="easyui-validatebox"  readonly="readonly" value="${areaInfo.noteText}" style="width: 200px;"  />
												<input id="parentId" name="parentId"   type="hidden" value="${areaInfo.parentId}" />
											 </td>
										</tr>	
										<tr>
											<th class="wd-20"><label>区域编码：</label></th>
											<td><input type="text" id="areaCode" name="areaCode" class="easyui-validatebox"
												value="${areaInfo.areaCode}" style="width: 200px;" maxlength="10" data-options="required:true,novalidate:true,validType:['maxLength[10]','NumberOrLetter']"
											  />
												<input type="hidden" id="noteNo" name="noteNo" class="easyui-textbox"
													value="${areaInfo.noteNo}"  data-options="required:true" />
												<span style="color:red">*</span>
												</td>
										</tr>
										<tr>
										 
												<th class="wd-20"><label>区域名称：</label></th>
											<td><input type="text" id="areaName" name="areaName" class="easyui-validatebox" maxlength="50"
												value="${areaInfo.areaName}" style="width: 200px;" data-options="required:true,novalidate:true,invalidMessage:'最大长度不能超过50',validType:'maxLength[50]'"
												data-options="required:true,novalidate:true" />
												<span style="color:red">*</span>
												</td>
										</tr>	
																		
									</table>
							</form>
						</div>

					</div>

		 
 