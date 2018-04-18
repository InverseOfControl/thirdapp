<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/jsCssInclude.jsp"%>
<link rel="stylesheet" href="${sessionScope.path}/css/base.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<div class="content-body">
	<div class="search-panel toggle-panel">		
		<div class="search-panel-content">
			<form id="bankOrgInfoSave" method="post"  
				action="${path}/bim/bankOrgInfo/bankOrgInfoSave">
				<input id="id" name="id" type="hidden" value="${bankOrgInfo.id}" />
					<table cellpadding="5">
						<tr>
							<td> 银行机构名称：</td>
							<td><input type="text" id="bankOrgName" name="bankOrgName"  class="easyui-validatebox"
								value="${bankOrgInfo.bankOrgName}" style="width: 200px;"
								data-options="required:true" /></td>
						</tr>
						<tr>
							<td>银行机构行号：</td>
							<td><input type="text" id="bankOrgNo" name="bankOrgNo" class="easyui-validatebox"
								value="${bankOrgInfo.bankOrgNo}" style="width: 200px;"
								data-options="required:true" /></td>
								<input type="hidden" id="noteOrgNo" name="noteOrgNo" class="easyui-textbox"
								value="${bankOrgInfo.noteOrgNo}"  data-options="required:true" />
						</tr>
						<tr>
							<td > 银行编码： </td>
							<td><input type="text" id=bankCode name="bankCode" class="easyui-validatebox"
								value="${bankOrgInfo.bankCode}" style="width: 200px;"
								data-options="required:true" /></td>
						</tr>
						<tr>
							<td>机构所在省份</td>
							<td><input id="bankOrgProvinceNo2" name="bankOrgProvinceNo"
										class="easyui-combobox" data-options="reqired:'true',
											editable:false,valueField: 'value',
											valueField: 'value',
										   textField: 'name',
										   url:'${path}/enumset/areaInfo/0',
										   onSelect: function(rec){
										            var url = '${path}/enumset/areaInfo/'+rec.value; 
										           $('#bankOrgProvinceCityNo2').combobox('reload', url);
										             
												},
										   onLoadSuccess: function () { 
                        									$('#bankOrgProvinceNo2').combobox('select', '${bankOrgInfo.bankOrgProvinceNo}');
                        									$('#bankOrgProvinceCityNo2').combobox('select', '${bankOrgInfo.bankOrgProvinceCityNo}');
                        									} "           											 
											  /></td>			
						</tr>
						<tr>					
							<td>机构所在城市</td>
							<td><input id="bankOrgProvinceCityNo2"
									name="bankOrgProvinceCityNo" class="easyui-combobox"
											data-options="
											editable:false,valueField: 'value',
											textField: 'name' "
											 
											
										
											style="width: 200px;" /></td>
						</tr>
					 
						<%-- <tr>
							<td></td>
							<td><c:if
									test="${null != sessionScope.permMap['/bim/bankOrgInfo/saveBankOrgInfo']}">
									<input id="search_btn" type="submit" class="input-btn-small"
										value="保存" />
								</c:if> <input id="cancelButton" type="button" onclick="cancel()"
								class="input-btn-small" value="取消" /></td>

						</tr>

 --%>
					</table>
			</form>
		</div>
	</div>
<script type="text/javascript">
/* jQuery(function($) {
	$('#bankOrgProvinceNo2').combobox(
			{
				onSelect : function(record) {
					alert("s");
							$.ajax({
								type : "POST",
								url : '${path}/enumset/areaInfo/'
										+ record.value,
								cache : false,
								dataType : "json",
								success : function(
										data) {
									$(
											"#bankOrgProvinceCityNo2").combobox("loadData",	data);
								}
							});
				}
			});
}); */
	</script>
</div>
 

