<%@ page language="java" pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<div class="content-body">
	 
		<div class="search-panel-content">
							<form id="appIpsSave" method="post"  
								action="${path}/bim/appIps/appIpsSave">
								<input id="id" name="id" type="hidden" value="${appIps.id}" />								 
										<table  class="search-table">
										<tr>
												<th class="wd-20"><label>业务系统名称：</label></th>
											<td>
											<input type="text"  class="easyui-textbox" readonly="readonly"
												value="${appIps.appName}" style="width: 200px;"
												data-options="required:true 
												"  />
											<input type="hidden" id="appCode" name="appCode" class="easyui-validatebox"
												value="${appIps.appCode}" style="width: 200px;"
												data-options="required:true" /></td>
										</tr>
										<tr>
											 
											<th class="wd-20"><label>IP：</label></th>
											<td><input type="text" id="ip" name="ip" class="easyui-validatebox"
												value="${appIps.ip}" style="width: 200px;" maxlength="64"
												data-options="required:true,novalidate:true,validType:'maxLength[64]' ,invalidMessage:'最大长度不能超过64'" />
													<span style="color:red">*</span>
												</td>
										</tr>									
									</table>
							</form>
						</div>

					</div>

			 
 