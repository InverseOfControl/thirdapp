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
			<div class="easyui-panel" style="padding:0px;margin: 0px;">
				<table  id="dg_tppChannelTRequest" class="easyui-datagrid"  style="height:400px"
					data-options="rownumbers:true,pageSize:20,singleSelect:true,collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/channel/tradeResult/tradeResultListData/${tradeFlow }',method:'post'">
					<thead>
						<tr>
							<!-- <th data-options="field:'ID',width:120,sortable:'true',align:'left'">主键(对应SEQ_TPP_CHANNEL_TRADE_RESULT序列)</th> -->
							<!-- <th data-options="field:'PAY_SYS_NO',width:120,sortable:'true',align:'left'">支付渠道编码</th> -->
							<th data-options="field:'REQ_ID',width:120,sortable:'true',align:'left'">TPP通道请求流水号</th>
							<th data-options="field:'TRANSFER_FLOW',width:120,sortable:'true',align:'left'">交易流水号</th>
							<th data-options="field:'PAY_SYS_NAME',width:120,sortable:'true',align:'left'">第三方支付平台</th>
							<th data-options="field:'STATUS',width:120,sortable:'true',align:'left'">交易状态</th>
							<th data-options="field:'TRANS_REP_CODE',width:120,sortable:'true',align:'left'">渠道返回状态</th>
							<th data-options="field:'TRANS_REP_INFO',width:120,sortable:'true',align:'left'">渠道返回信息</th>
							<th data-options="field:'BIZ_TYPE',width:120,sortable:'true',align:'left'">业务类型</th>
							<th data-options="field:'PAY_TRANS_FLOW',width:120,sortable:'true',align:'left'">第三方支付平台流水号</th>
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'left'">创建时间</th>
							<th data-options="field:'UPDATE_TIME',width:120,sortable:'true',align:'left'">更新时间</th>
						</tr>
					</thead>
				</table>
			</div>
			<!--搜索栏结果end-->

</body>
</html>