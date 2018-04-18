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
			
			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding:0px;margin: 0px;" >
				<table  id="dg_tppTradeTWaitingMerge" class="easyui-datagrid" style="height:300px"
					data-options="rownumbers:true,pageSize:20,singleSelect:true,collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/trade/waitingMerge/tradeWaitingMergeListData/${tradeFlow }',method:'post'">
					<thead>
						<tr>
						 	<!-- <th data-options="field:'DETAIL',width:50,align:'center'">detail</th>
							<th data-options="field:'EDIT',width:50,align:'center'">edit</th>
							<th data-options="field:'ID',width:120,sortable:'true',align:'left'">ID(对应序列SEQ_TRADE_T_WAITING_MERGE)</th> -->
							<th data-options="field:'TRADE_FLOW',width:120,sortable:'true',align:'left'">交易流水号</th>
							<th data-options="field:'BIZ_TYPE_NO',width:120,sortable:'true',align:'left'">业务类型</th>
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'left'">创建时间</th>
							<th data-options="field:'UPDATE_TIME',width:120,sortable:'true',align:'left'">修改时间</th>
							<th data-options="field:'STATUS',width:120,sortable:'true',align:'left'">处理状态</th>
							<th data-options="field:'MERGE_MODULE_NAME',width:120,sortable:'true',align:'left'">合单模块名称</th>
							<th data-options="field:'CREATER',width:120,sortable:'true',align:'left'">创建人</th>
						</tr>
					</thead>
				</table>
			</div>
			<!--搜索栏结果end-->
</body>
</html>