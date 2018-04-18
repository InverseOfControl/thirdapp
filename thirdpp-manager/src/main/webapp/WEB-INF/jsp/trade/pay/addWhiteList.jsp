<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/xml"  prefix = "x" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql"  prefix = "sql" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions"  prefix = "fn" %> 
<div class="content-body">
		<div class="search-panel-content">
			<form id="addTWhiteListForm" name="addTWhiteListForm" method="post" action="addTWhiteListForm.action">
				<div class="search-panel-bd">
							<table class="search-table">
							 <tr>
								<th class="wd-20"><label>业务系统</label></th>
								<td>
									<input id="biz_sys_name" class="easyui-combobox" name="biz_sys_name"  data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/bizSysNoList', 
										onSelect: function(rec){
								            var url = '${path }/enumset/getInfoCategoryListByBizSysNo/'+rec.value; 
								            $('#info_category_name').combobox('clear');
								            $('#info_category_name').combobox('reload', url);
										}" style="width:200px;"/>
										<span style="color:red">*</span>
								</td>
								<th class="wd-20"><label>信息类别</label></th>
								<td>
									<input id="info_category_name" class="easyui-combobox" name="info_category_name" data-options="
										editable:false,valueField: 'value',
										textField: 'name'" style="width:200px;"/>  
										<span style="color:red">*</span>
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>账号</label></th>
								<td>
									<input type="text" id="account_no" name="account_no"  maxlength = "64" style="width:200px;"/>
								</td>
								<th class="wd-20"><label>银行卡号 </label></th>
								<td>
									<input type="text" id="bank_card_no" name="bank_card_no" maxlength = "64"  style="width:200px;"  />
								</td>
							  </tr>
							<tr>
								<th class="wd-20"><label>备注</label></th>
								<td>
									<input type="text" id="note" name="note"  value="${note}" style="width:200px;" />
								</td>
								<th class="wd-20"><label>状态</label></th>
								<td>
									<input id="status" class="easyui-combobox" name="status" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										value:'1',
										url:'${path}/enumset/payWhiteListStatus'"  style="width:200px;"/>  
								</td>
							  </tr>
					</table>
				</div>
			</form>
		</div>
</div>