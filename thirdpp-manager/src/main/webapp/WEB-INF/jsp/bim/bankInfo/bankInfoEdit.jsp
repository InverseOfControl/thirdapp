<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<div class="content-body">
		<div class="search-panel-content">
			<form id="searchForm2" name="searchForm" method="post"
				action="${path}/bim/bankInfo/bankInfoSave">
				<input id="id" name="id" type="hidden" value="${bankInfo.id}" />
			    <table  class="search-table">
				<tr>
					<th class="wd-20"  style="font-size: 12px" ><label>银行名称:</label></th>
					 
					<td><input type="text" id="bankName2" name="bankName"
						value="${bankInfo.bankName}" style="width: 200px;"  maxlength="100"
						data-options="required:true,novalidate:true,invalidMessage:'最大长度不能超过100',validType:'maxLength[100]'" class="easyui-validatebox" />
						<span style="color:red">*</span>
						</td>
				</tr>
				<tr>
					<th class="wd-20"  style="font-size: 12px"><label>银行编码:</label></th>
					<td><c:if test="${readOnly==true}"><input type="text" id="bankCode2" name="bankCode" maxlength="10"
						value="${bankInfo.bankCode}" style="width: 200px;"
						data-options="required:true,novalidate:true,validType:['maxLength[10]','NumberOrLetter']"  
							readonly="true"
						 class="easyui-validatebox"/></c:if>
						 <c:if test="${readOnly==false}"><input type="text" id="bankCode2" name="bankCode" maxlength="10"
						value="${bankInfo.bankCode}" style="width: 200px;"
						data-options="required:true,novalidate:true,validType:['maxLength[10]','NumberOrLetter']"  
						 class="easyui-validatebox"/></c:if>
						<span style="color:red">*</span>
						</td>
						<input type="hidden" id="noteNo" name="noteNo" class="easyui-textbox"
								value="${bankInfo.noteNo}"  data-options="required:true" />
				</tr>
				<tr>
				 
					<th class="wd-20"  style="font-size: 12px"><label>备注:</label></th>
					<td>
					 
						<textarea   class="easyui-validatebox"   id="note2" name="note"   style="width: 200px;height:100px"  data-options="invalidMessage:'最大长度不能超过200',validType:'maxLength[200]'" >${bankInfo.note}</textarea>
				    </td>
						
				</tr>
			</table>
		</form>

		</div>
	</div>
 

	 
		
