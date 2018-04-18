<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/xml"  prefix = "x" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql"  prefix = "sql" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions"  prefix = "fn" %> 
<div class="content-body">
		<div class="search-panel-content">
			<form id="editTWhiteListForm" name="editTWhiteListForm" method="post" action="editTWhiteListForm.action">
				<div class="search-panel-bd">
							<input type="hidden" id="id" name="id" value="${id}" />
							<table class="search-table">
							 <tr>
								<th class="wd-20"><label>业务系统</label></th>
								<td>
									<input id="biz_sys_name" class="easyui-combobox" name="biz_sys_name"  data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										value:'${biz_sys_no}',
										url:'${path }/enumset/bizSysNoList',
										onSelect: function(rec){
								            var url = '${path }/enumset/getInfoCategoryListByBizSysNo/'+rec.value; 
								            $('#info_category_name').combobox('clear');
								            $('#info_category_name').combobox('reload', url);
										},
										onLoadSuccess: function () { 
                        					$('#biz_sys_name').combobox('select', '${biz_sys_no}');
                        					$('#info_category_name').combobox('select', '${info_category_code}');
                        				}" style="width:200px;"/>  
								</td>
								<th class="wd-20"><label>信息类别</label></th>
								<td>
									<input id="info_category_name" class="easyui-combobox" name="info_category_name" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										value:'${info_category_code}',
										url:'${path }/enumset/getInfoCategoryListByBizSysNo/${biz_sys_no}' " style="width:200px;"/>  
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>账号</label></th>
								<td>
									<input type="text" id="account_no" name="account_no"  value="${account_no}" style="width:200px;"/>
								</td>
								<th class="wd-20"><label>银行卡号 </label></th>
								<td>
									<input type="text" id="bank_card_no" name="bank_card_no"  value="${bank_card_no}" style="width:200px;"  />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>创建人</label></th>
								<td>
									<input type="text" id="creater" name="creater"  value="${creater}" style="width:200px;"  readonly="true" />
								</td>
								<th class="wd-20"><label>创建时间</label></th>
								<td>
									<input type="text" id="create_time" name="create_time"  value="<fmt:formatDate value='${create_time}' type='date'  pattern='yyyy-MM-dd HH:mm:ss'/>" style="width:200px;"  readonly="true"/>
								</td>
							 </tr>
							<tr>
								<th class="wd-20"><label>备注</label></th>
								<td>
									<input type="text" id="note" name="note"  value="${note}" style="width:200px;" />
								</td>
								<th class="wd-20"><label>更新时间</label></th>
								<td>
									<input type="text" id="update_time" name="update_time"  value="<fmt:formatDate value='${update_time}' type='date'  pattern='yyyy-MM-dd HH:mm:ss'/>" style="width:200px;" readonly="true"/>
								</td>
							  </tr>
							   <tr>
								<th class="wd-20"><label>状态</label></th>
								<td>
									<input id="status" class="easyui-combobox" name="status" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										value:'${status}',
										url:'${path}/enumset/payWhiteListStatus' " style="width:200px;"/>  
								</td>
							  </tr>
					</table>
				</div>
			</form>
		</div>
</div>