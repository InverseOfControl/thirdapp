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
<body >
			
			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding:0px;margin: 0px;" >
				<table  id="dg_tppTradeTNotify" class="easyui-datagrid" style="height:300px"
					data-options="rownumbers:true,pageSize:20,singleSelect:true,collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/trade/notify/listData/${taskId }',method:'post',onClickRow: function (rowIndex, rowData) {$(this).datagrid('unselectRow', rowIndex);}">
					<thead>
						<tr>
						 	<th data-options="field:'DETAIL',width:50,align:'center'">详细信息</th>
							<!-- <th data-options="field:'EDIT',width:50,align:'center'">edit</th> -->
							<th data-options="field:'ID',width:120,sortable:'true',align:'left'">ID</th>
							<th data-options="field:'BIZ_FLOW',width:120,sortable:'true',align:'left'">业务流水号</th>
							<th data-options="field:'BIZ_SYS_NO',width:120,sortable:'true',align:'left'">业务系统编码</th>
							<th data-options="field:'BIZ_SYS_NAME',width:120,sortable:'true',align:'left'">业务系统名称</th>
							<th data-options="field:'BIZ_TYPE',width:120,sortable:'true',align:'left'">业务类型</th>
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'left'">创建时间</th>
							<th data-options="field:'UPDATE_TIME',width:120,sortable:'true',align:'left'">更新时间</th>
							<th data-options="field:'NOTIFY_STATUS',width:120,sortable:'true',align:'left'">通知状态</th>
							<th data-options="field:'TRADE_STATUS',width:120,sortable:'true',align:'left'">交易结果状态</th>
							<th data-options="field:'TRADE_RESULT_INFO',width:120,sortable:'true',align:'left'">交易结果描述</th>
							<th data-options="field:'TASK_ID',width:120,sortable:'true',align:'left'">任务ID</th>
							<th data-options="field:'TRADE_FLOW',width:120,sortable:'true',align:'left'">交易流水号</th>
							<th data-options="field:'TRADE_SUCCESS_AMOUNT',width:120,sortable:'true',align:'left'">交易成功金额</th>
							<th data-options="field:'APP_NAME',width:120,sortable:'true',align:'left'">应用名称</th>
							<th data-options="field:'NOTIFY_COUNT',width:120,sortable:'true',align:'left'">通知次数</th>
							<th data-options="field:'FAIL_REASON',width:120,sortable:'true',align:'left'">失败原因</th>
							<th data-options="field:'PRIORITY',width:120,sortable:'true',align:'left'">优先级</th>
							<th data-options="field:'OP_MODE',width:120,sortable:'true',align:'left'">运营方式</th>
						</tr>
					</thead>
				</table>
			</div>
			<!--搜索栏结果end-->
<script>
var tppTradeTNotify_list ={};

function viewNotifyDetail (notifyId){
	$('<div id="dialog_holder"></div>').dialog({
	    title: '详细信息',
	    width: 800,
	    height: 400,
	    href: '${path}/trade/collect/notifyDetailPage',
	    modal: true,
	    method: "POST",
	    params:{notifyId:notifyId},
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
jQuery(function($){
	//详细
	tppTradeTNotify_list.detailFormSubmit = function(notifyId){
		$('<div id="dialog_holder"></div>').dialog({
		    title: '详细信息',
		    width: 800,
		    height: 400,
		    href: '${path}/trade/collect/notifyDetailPage',
		    modal: true,
		    method: "POST",
		    params:{notifyId:notifyId},
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
	
	
});

</script>
</body>
</html>