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
		<link rel="stylesheet" href="${sessionScope.path}/css/base.css" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body class="easyui-layout">
		<!--搜索栏开始-->
			<div class="easyui-panel" title="查询条件" style="padding:5px;height:145px;margin: 0px;" data-options="region:'north'">
				<form id="searchForm" name="searchForm" method="post" >
					<table cellpadding="5">
						<tr>
							<td style="text-align:right">业务系统：</td>
							<td>
								<input id="search_biz_sys_no" class="easyui-combobox" name="search_biz_sys_no" data-options="
									editable:false,valueField: 'value',
									textField: 'name',
									//url是下拉框请求显示的数据
									url:'${path }/enumset/bizSysNoList' " style="width:200px;"/>  
							</td>
							
							<td style="text-align:right">交易结果状态：</td>
							<td>
								<input id="search_trade_status" class="easyui-combobox" name="search_trade_status" data-options="
									editable:false,valueField: 'value',
									textField: 'name',
									//url是下拉框请求显示的数据
									url:'${path }/enumset/getTradeStatusForCollectInfo' " style="width:200px;"/>  
							</td>
							<td style="text-align:right">交易流水号：</td>
							<td>
								<input type="text" id="search_trade_flow" name="search_trade_flow"  value="${search_trade_flow}" style="width:200px;"/>
							</td>
						</tr>
						<tr>
							<td style="text-align:right">通知查询状态：</td>
							<td>
								<input id="search_notify_query_status" class="easyui-combobox" name="search_notify_query_status" data-options="
									editable:false,valueField: 'value',
									textField: 'name',
									//url是下拉框请求显示的数据
									url:'${path }/enumset/notifyQueryStatusList' " style="width:200px;"/>  
							</td>
							<td style="text-align:right">通知合单状态：</td>
							<td>
								<input id="search_notify_merge_status" class="easyui-combobox" name="search_notify_merge_status" data-options="
									editable:false,valueField: 'value',
									textField: 'name',
									//url是下拉框请求显示的数据
									url:'${path }/enumset/notifyMergeStatusList' " style="width:200px;"/>  
							</td>
							<td style="text-align:right">业务流水号：</td>
							<td>
								<input type="text" id="search_biz_flow" name="search_biz_flow"  value="${search_biz_flow}" style="width:200px;"/>
							</td>
							<td style="text-align:right">银行：</td>
							<td>
								<input id="search_bank_code" class="easyui-combobox" name="search_bank_code" data-options="
									editable:false,valueField: 'value',
									textField: 'name',
									//url是下拉框请求显示的数据
									url:'${path }/enumset/getBankName' " style="width:200px;"/>  
							</td>
						</tr>
						<tr>
						<td style="text-align:right">第三方支付平台：</td>
							<td>
								<input id="search_pay_sys_no" class="easyui-combobox" name="search_pay_sys_no" data-options="
									editable:false,valueField: 'value',
									textField: 'name',
									//url是下拉框请求显示的数据
									url:'${path }/enumset/dictionary/3' " style="width:200px;"/>  
							</td>
							<td style="text-align:right">信息类别：</td>
							<td>
								<input id="search_info_category" class="easyui-combobox" name="search_info_category" data-options="
									editable:false,valueField: 'value',
									textField: 'name',
									//url是下拉框请求显示的数据
									url:'${path }/enumset/getInfoCategoryList' " style="width:204px;"/>  
							</td>
							<td style="text-align:right">付款方信息：</td>
							<td>
								<input type="text" id="search_payer_info" name="search_payer_info" class="grayTips"   value="姓名/银行卡号/手机号/证件号" style="width:200px;"/>
							</td>
							
							<td style="text-align:right"><select id="search_date_type" name="search_date_type">
								  <option value ="CREATE_TIME">创建日期</option>
								  <option value ="THIRD_RETURN_TIME">回盘日期</option>
								</select>
							</td>
							<td>
								<input type="text" id="search_begin_date" name="search_begin_date"  value="${search_begin_date}" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
								－
								<input type="text" id="search_end_date" name="search_end_date"  value="${search_end_date}" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
							</td>
						</tr>
						
					</table>
				</form>
				<form id="exportCollectInfoExcelForm" name="exportCollectInfoExcelForm" method="post">
					<input type="hidden" id="export_trade_flow" name="export_trade_flow"/>
					<input type="hidden" id="export_biz_sys_no" name="export_biz_sys_no"/>
					<input type="hidden" id="export_notify_query_status" name="export_notify_query_status"/>
					<input type="hidden" id="export_notify_merge_status" name="export_notify_merge_status"/>
					<input type="hidden" id="export_begin_date" name="export_begin_date"/>
					<input type="hidden" id="export_end_date" name="export_end_date"/>
					<input type="hidden" id="export_biz_flow" name="export_biz_flow"/>
					<input type="hidden" id="export_date_type" name="export_date_type"/>
					<input type="hidden" id="export_biz_type" name="export_biz_type"/>
					<input type="hidden" id="export_info_category" name="export_info_category"/>
					<input type="hidden" id="export_trade_status" name="export_trade_status"/>
					<input type="hidden" id="export_payer_info" name="export_payer_info"/>
				</form>
				<!-- <form id="notifyQueryForm" name="notifyQueryForm" method="post">
					<input type="hidden" id="notify_query_trade_flow" name="notify_query_trade_flow"/>
				</form> -->
				<!-- <form id="notifyMergeForm" name="notifyMergeForm" method="post">
					<input type="hidden" id="notify_merge_trade_flow" name="notify_merge_trade_flow"/>
				</form> -->
			</div>
			<!--搜索栏结束-->
			
			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding:0px;margin: 0px;" data-options="region:'center'">
				<table  id="dg_tppTradeTCollectInfo" class="easyui-datagrid" 
					data-options="rownumbers:true,pageSize:20,singleSelect:true,collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/trade/collect/infoListData/null',method:'post',toolbar:toolbar,fit:'true',onLoadSuccess:function(){loadSummary();$(this).datagrid('fixRownumber');},onBeforeLoad:function(param){loadBefore(param)},onClickRow: function (rowIndex, rowData) {$(this).datagrid('unselectRow', rowIndex);}">
					<thead>
						<tr>
							<th data-options="field:'NOTIFY_QUERY',width:70,align:'center'">通知查询</th>
						 	<th data-options="field:'NOTIFY_MERGE',width:70,align:'center'">通知合单</th>
							<!-- <th data-options="field:'EDIT',width:50,align:'center'">编辑</th> -->
							<!-- <th data-options="field:'ID',width:120,sortable:'true',align:'left'">ID</th> -->
							<th data-options="field:'DETAIL',width:70,align:'center'">详细信息</th>
							<th data-options="field:'APP_REQUEST',width:70,align:'center'">渠道请求</th>
							<th data-options="field:'TASK_ID',width:120,sortable:'true',align:'left'">任务ID</th>
							<th data-options="field:'REQ_ID',width:130,sortable:'true',align:'left'">请求ID</th>
							<!-- <th data-options="field:'PAY_SYS_NO',width:120,sortable:'true',align:'left'">第三方支付平台编码</th> -->
							<th data-options="field:'PAY_SYS_NAME',width:120,sortable:'true',align:'left'">第三方支付平台</th>
							<th data-options="field:'BIZ_SYS_ACCOUNT_NO',width:120,sortable:'true',align:'left'">业务系统客户编号</th>
							<th data-options="field:'ZENGDAI_ACCOUNT_NO',width:120,sortable:'true',align:'left'">证大客户编号</th>
							<!-- <th data-options="field:'BIZ_SYS_NO',width:120,sortable:'true',align:'left'">业务系统号</th> -->
							<th data-options="field:'BIZ_SYS_NAME',width:120,sortable:'true',align:'left'">业务系统名称</th>
							<th data-options="field:'RECEIVER_ACCOUNT_NO',width:120,sortable:'true',align:'left'">收款方账户编号</th>
							<th data-options="field:'REVEIVER_ACCOUNT_NAME',width:120,sortable:'true',align:'left'">收款方姓名</th>
							<th data-options="field:'PAYER_NAME',width:120,sortable:'true',align:'left'">付款方姓名</th>
							<th data-options="field:'PAYER_MOBILE',width:120,sortable:'true',align:'left'">付款方手机号</th>
							<th data-options="field:'PAYER_BANK_CARD_NO',width:120,sortable:'true',align:'left'">付款方银行卡号</th>
							<th data-options="field:'PAYER_BANK_CARD_TYPE',width:120,sortable:'true',align:'left'">付款方银行卡类型 </th>
							<th data-options="field:'PAYER_ID_TYPE',width:120,sortable:'true',align:'left'">付款方证件类型</th>
							<th data-options="field:'PAYER_ID',width:120,sortable:'true',align:'left'">付款方证件号</th>
							<!-- <th data-options="field:'PAYER_BANK_CODE',width:120,sortable:'true',align:'left'">付款方银行编码</th> -->
							<th data-options="field:'PAYER_BANK_NAME',width:120,sortable:'true',align:'left'">付款方银行名称</th>
							<!-- <th data-options="field:'PAYER_SUB_BANK_CODE',width:120,sortable:'true',align:'left'">付款方银行支行行号</th> -->
							<th data-options="field:'CURRENCY',width:120,sortable:'true',align:'left'">币种</th>
							<th data-options="field:'AMOUNT',width:120,sortable:'true',align:'left'">金额</th>
							<th data-options="field:'FEE',width:120,sortable:'true',align:'left'">手续费</th>
							<th data-options="field:'BIZ_REMARK',width:120,sortable:'true',align:'left'">业务备注</th>
							<th data-options="field:'BIZ_FLOW',width:120,sortable:'true',align:'left'">业务流水号</th>
							<th data-options="field:'PRIORITY',width:120,sortable:'true',align:'left'">优先级</th>
							<th data-options="field:'STATUS',width:120,sortable:'true',align:'left'">交易状态</th>
							<th data-options="field:'REMARK',width:120,sortable:'true',align:'left'">备注</th>
							<th data-options="field:'CREATER',width:120,sortable:'true',align:'left'">创建人</th>
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'left'">创建时间</th>
							<th data-options="field:'UPDATE_TIME',width:120,sortable:'true',align:'left'">更新时间</th>
							<th data-options="field:'SPARE1',width:120,sortable:'true',align:'left'">备用1</th>
							<th data-options="field:'SPARE2',width:120,sortable:'true',align:'left'">备用2</th>
							<th data-options="field:'TRADE_FLOW',width:120,sortable:'true',align:'left'">交易流水号</th>
							<th data-options="field:'PAY_TRANS_FLOW',width:120,sortable:'true',align:'left'">第三方支付平台流水号EQ</th>
							<th data-options="field:'FAIL_REASON',width:120,sortable:'true',align:'left'">失败原因</th>
							<th data-options="field:'PAYER_ACCOUNT_NO',width:120,sortable:'true',align:'left'">付款方账户编号</th>
							<th data-options="field:'BIZ_TYPE',width:120,sortable:'true',align:'left'">业务类型</th>
							<th data-options="field:'IS_NEED_PUSH',width:120,sortable:'true',align:'left'">是否需要推送 </th>
							<!-- <th data-options="field:'INFO_CATEGORY_CODE',width:120,sortable:'true',align:'left'">信息类别编码</th> -->
							<th data-options="field:'INFO_CATEGORY_NAME',width:120,sortable:'true',align:'left'">信息类别</th>
							<th data-options="field:'TRANS_REP_CODE',width:120,sortable:'true',align:'left'">渠道返回状态</th>
							<th data-options="field:'IS_NEED_SPILT',width:120,sortable:'true',align:'left'">报盘是否需要拆单</th>
							<th data-options="field:'THIRD_RETURN_TIME',width:120,sortable:'true',align:'left'">第三方回盘时间</th>
							<th data-options="field:'NOTIFY_QUERY_STATUS',width:120,sortable:'true',align:'left'">通知查询状态</th>
							<th data-options="field:'NOTIFY_MERGE_STATUS',width:120,sortable:'true',align:'left'">通知合单状态</th>
							<th data-options="field:'SETTLE_DATE',width:120,sortable:'true',align:'left'">清算日期</th>
						</tr>
					</thead>
				</table>
			</div>
			<!--搜索栏结果end-->
			<div data-options="region:'south',split:true" style="height:80px;">
					<!-- 尾部 -->
				汇总信息：<br/>
				<table id="table_collect_summary" cellpadding="5"  class="search-table">
					<tr style="background:#DDDDDD">
						<td>总笔数</td>
						<td>总金额</td>
						<td>成功笔数</td>
						<td>成功金额</td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>
			</div>
<script>
var tppTradeTCollectInfo_list ={};
var toolbar = [{
	id : 'search_btn',
	text : '查询',
	iconCls : 'icon-search'
},'-',{
	id : 'clear_btn',
	text : '清除',
	iconCls : 'icon-remove'
}];
	
function CurentTime()
   { 
       var now = new Date();
      
       var year = now.getFullYear();       //年
       var month = now.getMonth() + 1;     //月
       var day = now.getDate();            //日
      
       var hh = now.getHours();            //时
       var mm = now.getMinutes();          //分
      
       var clock = year + "-";
      
       if(month < 10)
           clock += "0";
      
       clock += month + "-";
      
       if(day < 10)
           clock += "0";
       clock += day ;
       return(clock); 
   } 
function viewInfoDetail(infoId){
	$('<div id="dialog_holder"></div>').dialog({
	    title: '详细信息',
	    width: 800,
	    height: 400,
	    href:  '${path}/trade/collect/detailInfo',
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
function loadBefore(param) {
	var search_begin_date = $("#search_begin_date").datebox("getValue");
	var search_end_date = $("#search_end_date").datebox("getValue");
	if (search_begin_date == '' && search_end_date == '') {
		param.search_begin_date=CurentTime();
		param.search_end_date=CurentTime();
  	}
}
function appRequest(tradeFlow){
	$('<div id="dialog_holder"></div>').dialog({
	    title: '渠道请求信息',
	    width: 800,
	    height: 400,
	    href:  '${path}/trade/collect/appRequest',
	    modal: true,
	    method: "POST",
	    params:{tradeFlow:tradeFlow},
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
	$("#search_begin_date").datebox('setValue', CurentTime());
	$("#search_end_date").datebox('setValue', CurentTime());
	//定义构造查询
	tppTradeTCollectInfo_list.buildQueryParams=function(){
		var search_payer_info = $("input[name='search_payer_info']").val();
		if (search_payer_info=='姓名/银行卡号/手机号/证件号') {
			search_payer_info='';
		}
	    $('#dg_tppTradeTCollectInfo').datagrid("options").queryParams={
	    	'search_trade_flow':$("input[name='search_trade_flow']").val(),
	    	'search_biz_sys_no':$('#search_biz_sys_no').combobox("getValue"),
	    	'search_notify_query_status':$('#search_notify_query_status').combobox("getValue"),
	    	'search_notify_merge_status':$('#search_notify_merge_status').combobox("getValue"),
	    	'search_begin_date':$("#search_begin_date").datebox("getValue"),
			'search_end_date':$("#search_end_date").datebox("getValue"),
			'search_biz_flow':$("input[name='search_biz_flow']").val(),
			'search_date_type':$("#search_date_type").val(),
			'search_info_category':$('#search_info_category').combobox("getValue"),
			'search_trade_status':$('#search_trade_status').combobox("getValue"),
			'search_payer_info':search_payer_info,
			'search_pay_sys_no':$('#search_pay_sys_no').combobox("getValue"),
			'search_bank_code':$('#search_bank_code').combobox("getValue")
			};
	};

	//重新按照条件刷新查询内容
	$('#search_btn').click(function(){
		var flag = checkDate();
		if (!flag) {
			return ;
		}
		tppTradeTCollectInfo_list.buildQueryParams();
		$('#dg_tppTradeTCollectInfo').datagrid('load');
	});
	
    //重置查询条件并刷新内容
	$('#clear_btn').click(function(){
		//清空查询条件
		$("input[name='search_trade_flow']").val("");
		$('#search_biz_sys_no').combobox('setValue','');
		$("#search_begin_date").datebox('setValue', CurentTime());
		$("#search_end_date").datebox('setValue', CurentTime());
		$('#search_notify_query_status').combobox('setValue','');
		$('#search_notify_merge_status').combobox('setValue','');
		$("input[name='search_biz_flow']").val("");
		$('#search_info_category').combobox('setValue','');
		$('#search_trade_status').combobox('setValue','');
		$('#search_pay_sys_no').combobox('setValue','');
		$('#search_bank_code').combobox('setValue','');
		$("input[name='search_payer_info']").val("姓名/银行卡号/手机号/证件号");
		$("#search_date_type").val("创建日期");
		tppTradeTCollectInfo_list.buildQueryParams();
		$('#dg_tppTradeTCollectInfo').datagrid('load');
		inputTipText(); 
	});
    
	$('#export_btn').click(function(){
		var search_trade_flow = $("#search_trade_flow").val();
    	var search_biz_sys_no = $('#search_biz_sys_no').combobox("getValue");
    	var search_notify_query_status = $('#search_notify_query_status').combobox("getValue");
    	var search_notify_merge_status = $('#search_notify_merge_status').combobox("getValue");
    	var search_begin_date = $("#search_begin_date").datebox("getValue");
    	var search_end_date = $("#search_end_date").datebox("getValue");
		var search_biz_flow = $("#search_biz_flow").val();
		var search_date_type = $("#search_date_type").val();
		var search_info_category = $('#search_info_category').combobox("getValue");
		var search_trade_status = $('#search_trade_status').combobox("getValue");
		var search_payer_info = $('#search_payer_info').val();
		var search_bank_code = $('#search_bank_code').combobox("getValue");
		
		if (search_payer_info=='姓名/银行卡号/手机号/证件号') {
			search_payer_info='';
		}
		
		$('#export_trade_flow').val(search_trade_flow);
		$('#export_biz_sys_no').val(search_biz_sys_no);
		$('#export_notify_query_status').val(search_notify_query_status);
		$('#export_notify_merge_status').val(search_notify_merge_status);
		$('#export_begin_date').val(search_begin_date);
		$('#export_end_date').val(search_end_date);
		$('#export_biz_flow').val(search_biz_flow);
		$('#export_date_type').val(search_date_type);
		$('#export_info_category').val(search_info_category);
		$('#export_trade_status').val(search_trade_status);
		$('#export_payer_info').val(search_payer_info);
		
		
		var exporForm="#exportCollectInfoExcelForm";
		$(exporForm).form({   
			 url:'${path}/trade/collect/exportCollectInfoExcel',    
		     onSubmit: function(){
		    	 
		     }
		});// 
		$(exporForm).submit();
	});	
	inputTipText(); 
});
function checkDate(){
	var beginDate = $("#search_begin_date").datebox("getValue");
	var endDate = $("#search_end_date").datebox("getValue");
	if (beginDate != '' && beginDate != null) {
		if (endDate == '' || endDate == null) {
			$.messager.alert('提示','请选择结束日期'); 
			return false;
		}
	}
	if (endDate != '' && endDate != null) {
		if (beginDate == '' || beginDate == null) {
			$.messager.alert('提示','请选择开始日期');
			return false;
		}
	}
	if (beginDate != '' && beginDate != null && endDate != '' && endDate != null) {
		beginDate = new Date(beginDate);
		endDate = new Date(endDate);
		var day = endDate.diff(beginDate);
		if (day < 0) {
			$.messager.alert("提示","查询开始时间不能大于结束时间！");
			return false;
		}
		if (day > 7){
			$.messager.alert('提示','请查询七日内数据');
			return false;
		}
	}
	return true;
}
Date.prototype.diff = function(date){
  return (this.getTime() - date.getTime())/(24 * 60 * 60 * 1000);
}
function loadSummary(){
	var search_begin_date = $("#search_begin_date").datebox("getValue");
	var search_end_date = $("#search_end_date").datebox("getValue");
	if (search_begin_date == '' && search_end_date == '') {
  		$("#search_begin_date").datebox('setValue', CurentTime());
		$("#search_end_date").datebox('setValue', CurentTime());
		search_begin_date = CurentTime();
		search_end_date = CurentTime();
		
  	}
	
	var search_date_type = $("#search_date_type").val();
	var search_biz_sys_no = $('#search_biz_sys_no').combobox("getValue");
	var search_notify_query_status = $('#search_notify_query_status').combobox("getValue");
	var search_notify_merge_status = $('#search_notify_merge_status').combobox("getValue"); 
	var search_info_category = $('#search_info_category').combobox("getValue");
	var search_trade_status = $('#search_trade_status').combobox("getValue");
	var search_trade_flow = $("input[name='search_trade_flow']").val();
	var search_biz_flow = $("input[name='search_biz_flow']").val();
	var search_payer_info = $('#search_payer_info').val();
	var search_pay_sys_no = $('#search_pay_sys_no').combobox("getValue");
	var search_bank_code = $('#search_bank_code').combobox("getValue");
	if (search_payer_info=='姓名/银行卡号/手机号/证件号') {
		search_payer_info='';
	}
	
  	var table_collect_summary=document.getElementById("table_collect_summary");
  	
  	$('#dg_tppTradeTCollectInfo').datagrid("options").queryParams={
    	'search_trade_flow':$("input[name='search_trade_flow']").val(),
    	'search_biz_sys_no':$('#search_biz_sys_no').combobox("getValue"),
    	'search_notify_query_status':$('#search_notify_query_status').combobox("getValue"),
    	'search_notify_merge_status':$('#search_notify_merge_status').combobox("getValue"),
    	'search_begin_date':$("#search_begin_date").datebox("getValue"),
		'search_end_date':$("#search_end_date").datebox("getValue"),
		'search_biz_flow':$("input[name='search_biz_flow']").val(),
		'search_date_type':$("#search_date_type").val(),
		'search_info_category':$('#search_info_category').combobox("getValue"),
		'search_trade_status':$('#search_trade_status').combobox("getValue"),
		'search_pay_sys_no':$('#search_pay_sys_no').combobox("getValue"),
		'search_payer_info':search_payer_info,
		'search_bank_code':$('#search_bank_code').combobox("getValue")
		};
  	
  	$.ajax({
		url:'${path}/trade/collect/infoSummaryData',
		data:{"search_begin_date":search_begin_date,"search_end_date":search_end_date,'search_date_type':search_date_type,'search_biz_sys_no':search_biz_sys_no,'search_notify_query_status':search_notify_query_status,'search_notify_merge_status':search_notify_merge_status,'search_trade_flow':search_trade_flow,'search_biz_flow':search_biz_flow,"search_info_category":search_info_category,"search_trade_status":search_trade_status,"search_payer_info":search_payer_info, "search_pay_sys_no":search_pay_sys_no, "search_bank_code":search_bank_code},
		dataType:"JSON",
		type:"POST",
		async:true,
		success:function(data){
			table_collect_summary.rows[1].cells[0].innerHTML=data.totalSum+'笔';
			table_collect_summary.rows[1].cells[1].innerHTML=data.totalAmount+'元';
			table_collect_summary.rows[1].cells[2].innerHTML=data.successCount+'笔';
			table_collect_summary.rows[1].cells[3].innerHTML=data.successAmount+'元';
		},
		error:function(data){
			alert("请求超时");
		}
	});	
}



function notifyQuery(tradeFlow) {
		var add_form_id ='#addTppTradeTWaitingQueryFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: '通知查询',
		    width: 800,
		    height: 400,
		    href: '${path}/trade/collect/tradeWaitingQueryPage',
		    modal: true,
		    method: "POST",
		    params:{tradeFlow:tradeFlow},
			onClose: function(){
				$(this).dialog("destroy");
			},
		    buttons: [{
		    	text: "提  交",
		    	handler: function(e){
		    		$('#addTppTradeTWaitingQueryFrom').form({   
						 url:'${path}/trade/collect/tradeWaitingQuery',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							    	 var obj = eval('(' + data + ')');
								       //do some
							    	 if(obj.flag==false){
							    		   $.messager.progress('close');
								    	   $.messager.alert('提示',obj.msg);   
								    	   return;
							       	 }
							       //do some
							        $.messager.progress('close');
									/* tppTradeTWaitingQuery_list.buildQueryParams();
									$('#dg_tppTradeTWaitingQuery').datagrid('load'); */
									$('#dialog_holder').dialog('close');
							     }
						});// 
						$.messager.confirm('确认','您确认想要通知查询吗？',function(r){
							if (r){    
								$('#notify_query_trade_flow').val(tradeFlow);
								/* $('#notifyQueryForm').form({  
							        url:'${path }/trade/collect/notifyQuery',  
							        onSubmit:function(){  
							        },  
							        success:function(data){  
							        	var obj = eval('(' + data + ')');
									       //do some
								    	 if(obj.flag==false){
								    		   $.messager.progress('close');
									    	   $.messager.alert('提示',obj.msg);   
									    	   return;
								       	 }
							        	//$('#dg_tSysRole').datagrid('load');
							        }  
							    }); 
								$('#notifyQueryForm').submit(); */
								$(".easyui-validatebox").each(function(){
						        	$(this).validatebox({    
						        		novalidate: false   
						        	}); 
						        });
								//validate and sbumit
							    if($(add_form_id).form("validate")==true){
								  	$.messager.progress();
									$(add_form_id).submit();
								};   
						    }
						});
					    
		    	}
		    },{
		    	text: "取 消",
		    	handler: function(e){
		    		$(this).dialog("close");
		    	}
		    }]
		});
}

function notifyMerge(tradeFlow){
	var add_form_id ='#addTppTradeTWaitingMergeFrom';
	$('<div id="dialog_holder"></div>').dialog({
	    title: '通知合单',
	    width: 800,
	    height: 400,
	    href: '${path}/trade/collect/tradeWaitingMergePage',
	    modal: true,
	    method: "POST",
	    params:{tradeFlow:tradeFlow},
		onClose: function(){
			$(this).dialog("destroy");
		},
	    buttons: [{
	    	text: "提  交",
	    	handler: function(e){
	    		$('#addTppTradeTWaitingMergeFrom').form({   
					 url:'${path}/trade/collect/tradeWaitingMerge',    
						     onSubmit: function(){
						       $.messager.progress(); 
						        // do some check       
						        // return false to prevent submit;    
						     },    
						     success:function(data){
						    	 var obj = eval('(' + data + ')');
							       //do some
						    	 if(obj.flag==false){
						    		   $.messager.progress('close');
							    	   $.messager.alert('提示',obj.msg);   
							    	   return;
						       	 }
						       //do some
						        $.messager.progress('close');
								/* tppTradeTWaitingQuery_list.buildQueryParams();
								$('#dg_tppTradeTWaitingQuery').datagrid('load'); */
								$('#dialog_holder').dialog('close');
						     }
					});// 
					$.messager.confirm('确认','您确认想要通知合单吗？',function(r){
						if (r){    
							$('#notify_merge_trade_flow').val(tradeFlow);
							/* $('#notifyQueryForm').form({  
						        url:'${path }/trade/collect/notifyQuery',  
						        onSubmit:function(){  
						        },  
						        success:function(data){  
						        	var obj = eval('(' + data + ')');
								       //do some
							    	 if(obj.flag==false){
							    		   $.messager.progress('close');
								    	   $.messager.alert('提示',obj.msg);   
								    	   return;
							       	 }
						        	//$('#dg_tSysRole').datagrid('load');
						        }  
						    }); 
							$('#notifyQueryForm').submit(); */
							$(".easyui-validatebox").each(function(){
					        	$(this).validatebox({    
					        		novalidate: false   
					        	}); 
					        });
							//validate and sbumit
						    if($(add_form_id).form("validate")==true){
							  	$.messager.progress();
								$(add_form_id).submit();
							};   
					    }
					});
				    
	    	}
	    },{
	    	text: "取 消",
	    	handler: function(e){
	    		$(this).dialog("close");
	    	}
	    }]
	});
}
function notifyMergeList(tradeFlow){
	$('<div id="dialog_holder"></div>').dialog({
	    title: '通知合单列表',
	    width: 800,
	    height: 400,
	    href:  '${path }/trade/waitingMerge/tradeWaitingMergePage',
	    modal: true,
	    method: "POST",
	    params:{tradeFlow:tradeFlow},
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

function notifyQueryList(tradeFlow){
	$('<div id="dialog_holder"></div>').dialog({
	    title: '通知查询列表',
	    width: 800,
	    height: 400,
	    href:  '${path }/trade/collect/tradeWaitingQueryListPage',
	    modal: true,
	    method: "POST",
	    params:{tradeFlow:tradeFlow},
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
function inputTipText(){
	$("input[class*=grayTips]") //所有样式名中含有grayTips的input
	.each(function(){
	   var oldVal=$(this).val();     //默认的提示性文本
	   $(this)
	   .css({"color":"#888"})     //灰色
	   .focus(function(){
	    if($(this).val()!=oldVal){$(this).css({"color":"#000"})}else{$(this).val("").css({"color":"#888"})}
	   })
	   .blur(function(){
	    if($(this).val()==""){$(this).val(oldVal).css({"color":"#888"})}
	   })
	   .keydown(function(){$(this).css({"color":"#000"})})
	  
	})
}
</script>
	</body>
</html>
