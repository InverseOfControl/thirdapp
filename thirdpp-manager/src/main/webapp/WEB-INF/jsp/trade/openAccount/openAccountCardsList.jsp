<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>证大财富随指贷管理系统</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/jsCssInclude.jsp"%>
<link rel="stylesheet" href="${sessionScope.path}/css/base.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body class="easyui-layout">
	<%-- <div class="easyui-panel" title="查询条件"
		style="padding: 5px; height: 144px; margin: 0px;"
		data-options="region:'north'">
	 
			<!--搜索栏开始-->
					<form id="searchForm" name="searchForm" method="post">
						<input type="hidden" id="appCode" name="appCode" class="easyui-textbox" value="${appCode}"/>
						<input id="id" name="id" type="hidden" value="${threadPool.id}" />
							<table  cellpadding="5">
								<tr>
									<td style="text-align:right">第三方账户编号：</td> 
									<td>
										<input type="text" id="ip" name="thirdAccountNo" value="${thirdAccountNo}" class="easyui-textbox"
										style="width: 100px;" data-options="required:true" />
									</td>
									<td style="text-align:right">卡持有人姓名：</td> 
									<td>
										<input type="text" id="bankCardName" name="bankCardName" value="${bankCardName}" class="easyui-textbox"
										style="width: 100px;" data-options="required:true" />
									</td>
										
									<td style="text-align:right">银行卡号：</td> 
									<td>
										<input type="text" id="bankCardNo" name="bankCardNo" value="${bankCardNo}" class="easyui-textbox"
										style="width: 100px;" data-options="required:true" />
									</td>
								</tr>	
								<tr>	
									<td style="text-align:right">预留手机号：</td> 
									<td>
										<input type="text" id="reserveMobile" name="reserveMobile" value="${reserveMobile}" class="easyui-textbox"
										style="width: 100px;" data-options="required:true" />
									</td>
									<td style="text-align:right">状态：</td>
									<td> 
										 <input style="width: 100px" name="status"  id="status" 
															class="easyui-combobox" id="priority"
															data-options="editable:false,data:[{'type':'','text':'请选择'},{'type':'1','text':'正常'},{'type':'2','text':'解绑'}   
															 ],	valueField:'type',textField:'text',value:'${status}'" />
									</td>
									<td style="text-align:right">创建时间： </td>
									<td>
										<input type="text" id="beginTime" name="beginTime"
										value="${beginTime}"  class="easyui-datebox"
										style="width: 100px;"   data-options="required:false,editable:false"/> 
								 	-
									 	<input type="text" id="endTime" name="endTime"
										value="${endTime}" class="easyui-datebox"
										style="width: 100px;"   data-options="required:false,editable:false"/>
									</td>
								 </tr>
								 <tr>
								 	<td style="text-align:right">银行卡类型：</td>
									<td> 
									 	<input style="width: 100px" name="bankCardType"  id="bankCardType" 
														class="easyui-combobox" id="priority"
														data-options="editable:false,data:[{'type':'','text':'请选择'},{'type':'1','text':'借记卡'},{'type':'2','text':'信用卡'}   
														 ],	valueField:'type',textField:'text',value:'${bankCardType}'" />
									</td>
									<td style="text-align:right">第三方支付平台：</td>
									<td>
										<input id="paySysNo" class="easyui-combobox" name="paySysNo" data-options="
											editable:false,valueField: 'value',
											textField: 'name',
											//url是下拉框请求显示的数据
											url:'${path }/enumset/dictionary/3' " style="width:100px;"/>  
									</td>
								 </tr>
							</table>
					</form>
				</div> --%>
			 
			<!--搜索栏结束-->
			<form id="searchForm" name="searchForm" method="post">
			<input type="hidden" id="zengdaiAccountNo1" name="zengdaiAccountNo1" value="${zengdaiAccountNo }"/>
			<input type="hidden" id="thirdAccountNo1" name="thirdAccountNo1" value="${thirdAccountNo }"/>
			<input type="hidden" id="bizsysAccountNo1" name="bizsysAccountNo1" value="${bizsysAccountNo }"/>
			</form>
			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding: 0px; margin: 0px;"
				data-options="region:'center'">
				<table id="dg_tppOpenAccountCards" class="easyui-datagrid"
				 	 style="width: auto; height: 300px"
					data-options="rownumbers:true,selectOnCheck: 'true',checkOnSelect:true,collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/trade/openAccountCards/openAccountCardsListData',method:'post'
					,pageSize:20,onLoadSuccess:function(){loadQueryParam1();$(this).datagrid('fixRownumber');},onBeforeLoad:function(param){loadBefore1(param)},onClickRow:function(rowIndex, rowData){ $('#dg_tppOpenAccountCards').datagrid('unselectRow',rowIndex);}
					">
					<thead>
						<tr>
							<th
								data-options="field:'ID',width:150,sortable:'true',align:'right',hidden:'true'">ID</th>
							<!-- <th
								data-options="field:'AC',width:100,sortable:'true',align:'center'">查看开户信息</th> -->
						
							<th
								data-options="field:'THIRD_ACCOUNT_NO',width:190,sortable:'true',align:'left'">第三方账户编号</th>
							<th
								data-options="field:'BANK_CARD_NO',width:190,sortable:'true',align:'left'">银行卡号</th>
							<th
								data-options="field:'BANK_CARD_NAME',width:180,sortable:'true',align:'left'">银行卡持有人姓名</th>
							<th
								data-options="field:'BANK_CARD_TYPE',width:180,sortable:'true',align:'left'">银行卡类型</th>
							<th
								data-options="field:'BANK_CODE_NAME',width:180,sortable:'true',align:'left'">银行名称</th>
							<th
								data-options="field:'STATUS',width:180,sortable:'true',align:'left'">状态</th>
							<th
								data-options="field:'RESERVE_MOBILE',width:180,sortable:'true',align:'left'">银行预留手机号</th>
							<th
								data-options="field:'PAY_SYS_NAME',width:180,sortable:'true',align:'left'">第三方支付平台</th>
							<th
								data-options="field:'OPEN_BANK_NAME',width:180,sortable:'true',align:'left'">开户行银行名称</th>
							 
							<th
								data-options="field:'REQ_ID',width:180,sortable:'true',align:'left'">请求Id</th>
							 
							<th
								data-options="field:'CREATE_TIME',width:180,sortable:'true',align:'left'">创建时间</th>
							<th
								data-options="field:'UPDATE_TIME',width:180,sortable:'true',align:'left'">修改时间</th>
						</tr>
					</thead>
				</table>
			</div>
	<script>
		function loadBefore1(param) {
			param.zengdaiAccountNo=$("#zengdaiAccountNo1").val();
			//查询条件不明，暂时只用证大客户号查询
			/* param.bizsysAccountNo=$("#bizsysAccountNo1").val();
			param.thirdAccountNo=$("#thirdAccountNo1").val(); */
		}
		
		function loadQueryParam1(){
			$('#dg_tppOpenAccountCards').datagrid("options").queryParams={
				'zengdaiAccountNo' : $("#zengdaiAccountNo1").val(),
				//查询条件不明，暂时只用证大客户号查询
				/* 'bizsysAccountNo' : $("#bizsysAccountNo1").val(),
				'thirdAccountNo' : $("#thirdAccountNo1").val(), */
			};
			 
		}
	</script>
</body>
</html>
