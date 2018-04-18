<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>证大财富统一支付管理系统</title>
		<meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@ include file="/common/jsCssInclude.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
<body class="easyui-layout">
			<!--搜索栏开始-->
				<form id="searchForm" name="searchForm" method="post" >
					<input type="hidden" id="taskId" name="taskId" value="${taskId }"/>
				</form>
			<!--搜索栏结束-->
			
			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding:0px;margin: 0px;" data-options="region:'center'">
				<table  id="dg_tppTradeTPayInfo" class="easyui-datagrid"  style="height:300px"
					data-options="rownumbers:true,pageSize:20,singleSelect:true,collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/trade/pay/infoListData/${taskId }',method:'post',onClickRow: function (rowIndex, rowData) {$(this).datagrid('unselectRow', rowIndex);}">
					
					<thead>
						<tr>
						 	<th data-options="field:'DETAIL',width:70,align:'center'">详细信息</th>
							<!-- <th data-options="field:'EDIT',width:50,align:'center'">edit</th> -->
							<th data-options="field:'ID',width:120,sortable:'true',align:'right',hidden:true">ID</th>
							<th data-options="field:'TASK_ID',width:120,sortable:'true',align:'right'">任务ID</th>
							<th data-options="field:'REQ_ID',width:120,sortable:'true',align:'right'">请求ID</th>
							<th data-options="field:'PAY_SYS_NAME',width:120,sortable:'true',align:'right'">第三方支付平台</th>
							<th data-options="field:'BIZ_SYS_ACCOUNT_NO',width:120,sortable:'true',align:'right'">业务系统客户编号</th>
							<th data-options="field:'ZENGDAI_ACCOUNT_NO',width:120,sortable:'true',align:'right'">证大客户编号</th>
							<th data-options="field:'BIZ_SYS_NAME',width:120,sortable:'true',align:'right'">业务系统</th>
							<th data-options="field:'PAYER_ACCOUNT_NO',width:120,sortable:'true',align:'right'">付款方账户编号</th>
							<th data-options="field:'PAYER_ACCOUNT_NAME',width:120,sortable:'true',align:'right'">付款方姓名</th>
							<th data-options="field:'RECEIVER_NAME',width:120,sortable:'true',align:'right'">收款方姓名</th>
							<th data-options="field:'RECEIVER_BANK_CARD_NO',width:120,sortable:'true',align:'right'">收款方银行卡号</th>
							<th data-options="field:'RECEIVER_BANK_CARD_TYPE',width:120,sortable:'true',align:'right'">收款方银行卡类型</th>
							<th data-options="field:'RECEIVER_ID_TYPE',width:120,sortable:'true',align:'right'">收款方证件类型</th>
							<th data-options="field:'RECEIVER_ID',width:120,sortable:'true',align:'right'">收款方证件号</th>
							<th data-options="field:'RECEIVER_BANK_CODE',width:120,sortable:'true',align:'right'">收款方银行编码</th>
							<th data-options="field:'RECEIVER_BANK_NAME',width:120,sortable:'true',align:'right'">收款方银行名称</th>
							<th data-options="field:'RECEIVER_SUB_BANK_CODE',width:120,sortable:'true',align:'right'">收款方银行支行行号</th>
							<th data-options="field:'CURRENCY',width:120,sortable:'true',align:'right'">币种</th>
							<th data-options="field:'AMOUNT',width:120,sortable:'true',align:'right'">金额</th>
							<th data-options="field:'FEE',width:120,sortable:'true',align:'right'">手续费</th>
							<th data-options="field:'BIZ_REMARK',width:120,sortable:'true',align:'right'">业务备注</th>
							<th data-options="field:'BIZ_FLOW',width:120,sortable:'true',align:'right'">业务流水号</th>
							<th data-options="field:'PAY_TRANS_FLOW',width:120,sortable:'true',align:'right'">第三方支付平台交易返回流水号</th>
							<th data-options="field:'PRIORITY',width:120,sortable:'true',align:'right'">优先级</th>
							<th data-options="field:'STATUS',width:120,sortable:'true',align:'right'">交易结果</th>
							<th data-options="field:'REMARK',width:120,sortable:'true',align:'right'">备注</th>
							<th data-options="field:'CREATER',width:120,sortable:'true',align:'right'">创建人</th>
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'right'">创建时间</th>
							<th data-options="field:'UPDATE_TIME',width:120,sortable:'true',align:'right'">更新时间</th>
							<th data-options="field:'SPARE1',width:120,sortable:'true',align:'right'">备用1</th>
							<th data-options="field:'SPARE2',width:120,sortable:'true',align:'right'">备用2</th>
							<th data-options="field:'TRADE_FLOW',width:120,sortable:'true',align:'right'">交易流水号</th>
							<th data-options="field:'FAIL_REASON',width:120,sortable:'true',align:'right'">失败原因</th>
							<th data-options="field:'RECEIVER_ACCOUNT_NO',width:120,sortable:'true',align:'right'">收款方账户编号</th>
							<th data-options="field:'BIZ_TYPE',width:120,sortable:'true',align:'right'">业务类型</th>
							<th data-options="field:'IS_NEED_PUSH',width:120,sortable:'true',align:'right'">是否需要推送</th>
							<th data-options="field:'INFO_CATEGORY_NAME',width:120,sortable:'true',align:'right'">信息类别</th>
							<th data-options="field:'TRANS_REP_CODE',width:120,sortable:'true',align:'right'">渠道返回状态</th>
							<th data-options="field:'THIRD_RETURN_TIME',width:120,sortable:'true',align:'right'">第三方回盘时间</th>
						</tr>
					</thead>
				</table>
			</div>
			<!--搜索栏结果end-->

<script>
var tppTradeTPayInfo_list ={};


function viewInfoDetail(infoId){
	$('<div id="dialog_holder"></div>').dialog({
	    title: '详细信息',
	    width: 800,
	    height: 400,
	    href:  '${path}/trade/pay/detailInfo',
	    modal: true,
	    method: "POST",
	    params:{infoId:infoId},
		onClose: function(){
			$(this).dialog("destroy");
		},
	    buttons: [{
	    	text: "关闭",
	    	handler: function(e){
	    		$(this).dialog("close");
	    	}
	    }]
	});
}
</script>
</body>
</html>