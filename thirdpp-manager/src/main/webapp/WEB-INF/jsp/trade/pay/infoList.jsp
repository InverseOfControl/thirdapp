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
			<div class="easyui-panel" title="查询条件" style="padding:5px;height:143px;margin: 0px;" data-options="region:'north'">
				<form id="searchForm" name="searchForm" method="post" >
					<table cellpadding="5">
						<tr>
								<td style="text-align:right">第三方支付平台：</td>
								<td>
									<input id="search_pay_sys_no" class="easyui-combobox" name="search_pay_sys_no" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/dictionary/3' " style="width:200px;"/>  
								</td>
								<td style="text-align:right">业务系统：</td>
								<td>
									<input id="search_biz_sys_no" class="easyui-combobox" name="search_biz_sys_no" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/bizSysNoList' " style="width:200px;"/>  
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
								<td style="text-align:right">交易结果状态：</td>
								<td>
									<input id="search_trade_status" class="easyui-combobox" name="search_trade_status" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/getTradeStatusForCollectInfo' " style="width:200px;"/>  
								</td>
								
								<td style="text-align:right">业务类型：</td>
								<td>
									<input id="search_biz_type" class="easyui-combobox" name="search_biz_type" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/bizTypeForPayList' " style="width:200px;"/>  
								</td>
								<td style="text-align:right">收款人用户类型：</td>
								<td>
									<input id="search_receiver_type" class="easyui-combobox" name="search_receiver_type" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/receiverTypeForPayList' " style="width:200px;"/>  
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
								<td style="text-align:right">流水号：</td>
								<td>
									<input type="text" id="search_flow" name="search_flow" class="grayTips" value="业务流水号/交易流水号" style="width:200px;"/>
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
				<form id="exportPayInfoExcelForm" name="exportPayInfoExcelForm" method="post">
					<input type="hidden" id="export_begin_date" name="export_begin_date"/>
					<input type="hidden" id="export_end_date" name="export_end_date"/>
					<input type="hidden" id="export_date_type" name="export_date_type"/>
					<input type="hidden" id="export_pay_sys_no" name="export_pay_sys_no"/>
					<input type="hidden" id="export_biz_sys_no" name="export_biz_sys_no"/>
					<input type="hidden" id="export_trade_status" name="export_trade_status"/>
					<input type="hidden" id="export_biz_type" name="export_biz_type"/>
					<input type="hidden" id="export_notify_query_status" name="export_notify_query_status"/>
					<input type="hidden" id="export_notify_merge_status" name="export_notify_merge_status"/>
					<input type="hidden" id="export_receiver_info" name="export_receiver_info"/>
					<input type="hidden" id="export_flow" name="export_flow"/>
				</form>
			</div>
			<!--搜索栏结束-->
			
			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding:0px;margin: 0px;" data-options="region:'center'">
				<table  id="dg_tppTradeTPayInfo" class="easyui-datagrid" 
					data-options="rownumbers:true,pageSize:20,singleSelect:true,collapsible:true,sortName:'',pagination:'true',sortOrder:'desc',url:'${path }/trade/pay/infoListData/null',method:'post',toolbar:toolbar,fit:'true',onLoadSuccess:function(){loadSummary();$(this).datagrid('fixRownumber');},onBeforeLoad:function(param){loadBefore(param)},onClickRow: function (rowIndex, rowData) {$(this).datagrid('unselectRow', rowIndex);}">
					
					<thead>
						<tr>
							<th data-options="field:'NOTIFY_QUERY',width:70,align:'center'">通知查询</th>
						 	<th data-options="field:'NOTIFY_MERGE',width:70,align:'center'">通知合单</th>
						 	<th data-options="field:'DETAIL',width:70,align:'center'">详细信息</th>
						 	<th data-options="field:'APP_REQUEST',width:70,align:'center'">渠道请求</th>
							<!-- <th data-options="field:'EDIT',width:50,align:'center'">edit</th> -->
							<th data-options="field:'ID',width:120,sortable:'true',align:'right',hidden:true">ID</th>
							<th data-options="field:'TASK_ID',width:120,sortable:'true',align:'right'">任务ID</th>
							<th data-options="field:'REQ_ID',width:120,sortable:'true',align:'right'">请求ID</th>
							<th data-options="field:'PAY_SYS_NAME',width:120,sortable:false,align:'right'">第三方支付平台</th>
							<th data-options="field:'BIZ_SYS_ACCOUNT_NO',width:120,sortable:'true',align:'right'">业务系统客户编号</th>
							<th data-options="field:'ZENGDAI_ACCOUNT_NO',width:120,sortable:'true',align:'right'">证大客户编号</th>
							<th data-options="field:'BIZ_SYS_NAME',width:120,sortable:false,align:'right'">业务系统</th>
							<th data-options="field:'PAYER_ACCOUNT_NO',width:120,sortable:'true',align:'right'">付款方账户编号</th>
							<th data-options="field:'PAYER_ACCOUNT_NAME',width:120,sortable:'true',align:'right'">付款方姓名</th>
							<th data-options="field:'RECEIVER_NAME',width:120,sortable:'true',align:'right'">收款方姓名</th>
							<th data-options="field:'RECEIVER_TYPE',width:120,sortable:'true',align:'right'">收款人用户类型</th>
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
							<th data-options="field:'INFO_CATEGORY_NAME',width:120,sortable:false,align:'right'">信息类别</th>
							<th data-options="field:'TRANS_REP_CODE',width:120,sortable:'true',align:'right'">渠道返回状态</th>
							<th data-options="field:'THIRD_RETURN_TIME',width:120,sortable:'true',align:'right'">第三方回盘时间</th>
							<th data-options="field:'SETTLE_DATE',width:120,sortable:'true',align:'right'">对账日期</th>
							
						</tr>
					</thead>
				</table>
			</div>
			<!--搜索栏结果end-->
			<div data-options="region:'south',split:true" style="height:80px;">
				汇总信息：<br/>
				<table id="table_pay_summary" cellpadding="5"  class="search-table">
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
var tppTradeTPayInfo_list ={};
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
jQuery(function($){
	$("#search_begin_date").datebox('setValue', CurentTime());
	$("#search_end_date").datebox('setValue', CurentTime());
	//定义构造查询
	tppTradeTPayInfo_list.buildQueryParams=function(){
		var search_receiver_info = $("input[name='search_receiver_info']").val();
		if (search_receiver_info=='姓名/银行卡号/证件号') {
			search_receiver_info='';
		}
		var search_flow = $("input[name='search_flow']").val();
		if (search_flow=='业务流水号/交易流水号') {
			search_flow='';
		}
		$('#dg_tppTradeTPayInfo').datagrid("options").queryParams={
			'search_pay_sys_no':$('#search_pay_sys_no').combobox("getValue"),
	    	'search_biz_sys_no':$('#search_biz_sys_no').combobox("getValue"),
	    	'search_flow':search_flow,
	    	'search_trade_status':$('#search_trade_status').combobox("getValue"),
	    	'search_biz_type':$('#search_biz_type').combobox("getValue"),
	    	'search_receiver_info':search_receiver_info,
	    	'search_notify_query_status':$('#search_notify_query_status').combobox("getValue"),
	    	'search_notify_merge_status':$('#search_notify_merge_status').combobox("getValue"),
	    	'search_begin_date':$("#search_begin_date").datebox("getValue"),
			'search_end_date':$("#search_end_date").datebox("getValue"),
			'search_date_type':$("#search_date_type").val(),
			'search_receiver_type':$('#search_receiver_type').combobox("getValue"),
			'search_bank_code':$('#search_bank_code').combobox("getValue")
		}
	}

	//重新按照条件刷新查询内容
	$('#search_btn').click(function(){
		var flag = checkDate();
		if (!flag) {
			return ;
		}
		tppTradeTPayInfo_list.buildQueryParams();
		$('#dg_tppTradeTPayInfo').datagrid('load');
	});
	
    //重置查询条件并刷新内容
	$('#clear_btn').click(function(){
		//清空查询条件
			$('#search_pay_sys_no').combobox('setValue','');
			$('#search_biz_sys_no').combobox('setValue','');
			$('#search_trade_status').combobox('setValue','');
			$('#search_biz_type').combobox('setValue','');
			$("#search_begin_date").datebox('setValue', CurentTime());
			$("#search_end_date").datebox('setValue', CurentTime());
			$('#search_notify_query_status').combobox('setValue','');
			$('#search_notify_merge_status').combobox('setValue','');
			$('#search_receiver_type').combobox('setValue','');
			$('#search_bank_code').combobox('setValue','');
			$("input[name='search_biz_flow']").val("");
			$("input[name='search_receiver_info']").val("姓名/银行卡号/证件号");
			$("input[name='search_flow']").val("业务流水号/交易流水号");
			$("#search_date_type").val("创建日期");
		tppTradeTPayInfo_list.buildQueryParams();
		$('#dg_tppTradeTPayInfo').datagrid('load');
		
		inputTipText();
	});
	
	
	//新增
	$('#add_btn').click(function(){
		var add_form_id ='#addTppTradeTPayInfoFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: 'TPP_TRADE_T_PAY_INFO',
		    width: 800,
		    height: 500,
		    href: 'addTppTradeTPayInfoPage.action',
		    modal: true,
		    method: "POST",
		    extractor: function(data){
		    	var bodyPattern = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
				var contentPattern = /<article\s+id="content"[^>]*>((.|[\n\r])*)<\/article>/im;
				var scriptPattern = /<script[^>]*>(.|[\n\r\t])*?<\/script>/gim;
				var buttonPattern = /<div\s+class="button-field[\s\w]*"[^>]*>(.|[\n\r\t])*?<\/div>/im;
				var matches = bodyPattern.exec(data);
				if (matches){
					var content = matches[1].match(contentPattern);
				    var scripts = matches[1].match(scriptPattern);
					return (content[1]||"").replace(scriptPattern,"").replace(buttonPattern,"") + (scripts||[]).join("");
				} else {
					return data;
				}
			},
			onClose: function(){
				$(this).dialog("destroy");
			},
		    buttons: [{
		    	text: "提  交",
		    	handler: function(e){
		    		$('#addTppTradeTPayInfoFrom').form({   
						 url:'addTppTradeTPayInfoAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppTradeTPayInfo_list.buildQueryParams();
									$('#dg_tppTradeTPayInfo').datagrid('load');
									$('#dialog_holder').dialog('close');
							     }
						});// 
					    //validate and sbumit
					    if($(add_form_id).form("validate")==true){
						  	$.messager.progress();
							$(add_form_id).submit();
						};   
		    	}
		    },{
		    	text: "取 消",
		    	handler: function(e){
		    		$(this).dialog("close");
		    	}
		    }]
		});
	});
	
	//更新
	tppTradeTPayInfo_list.updateFormSubmit = function(pkid){
	    var edit_form_id ='#editTppTradeTPayInfoFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: 'TppTradeTPayInfo',
		    width: 800,
		    height: 500,
		    href: 'editTppTradeTPayInfoPage.action',
		    modal: true,
		    method: "POST",
		    params:{pkid:pkid},
		    extractor: function(data){
		    	var bodyPattern = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
				var contentPattern = /<article\s+id="content"[^>]*>((.|[\n\r])*)<\/article>/im;
				var scriptPattern = /<script[^>]*>(.|[\n\r\t])*?<\/script>/gim;
				var buttonPattern = /<div\s+class="button-field[\s\w]*"[^>]*>(.|[\n\r\t])*?<\/div>/im;
				var matches = bodyPattern.exec(data);
				if (matches){
					var content = matches[1].match(contentPattern);
				    var scripts = matches[1].match(scriptPattern);
					return (content[1]||"").replace(scriptPattern,"").replace(buttonPattern,"") + (scripts||[]).join("");
				} else {
					return data;
				}
			},
			onClose: function(){
				$(this).dialog("destroy");
			},
		    buttons: [{
		    	text: "保  存",
		    	handler: function(e){
		   				$('#editTppTradeTPayInfoFrom').form({   
						 url:'editTppTradeTPayInfoAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppTradeTPayInfo_list.buildQueryParams();
									$('#dg_tppTradeTPayInfo').datagrid('load');
									$('#dialog_holder').dialog('close');
							     }
						});// 
					    //validate and sbumit
					    if($(edit_form_id).form("validate")==true){
						  	$.messager.progress();
							$(edit_form_id).submit();
						};   
		    		
		    	}
		    }
		    ,{
		    	text: "删  除",
		     	handler: function(e){
		     			$('#delTppTradeTPayInfoFrom').form({   
						 url:'delTppTradeTPayInfoAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppTradeTPayInfo_list.buildQueryParams();
									$('#dg_tppTradeTPayInfo').datagrid('load');
									$('#dialog_holder').dialog('close');
							     }
						});// 
					    $('#delTppTradeTPayInfoFrom').submit();
		    	}
		     }
		    ,{
		    	text: "取 消",
		    	handler: function(e){
		    		$(this).dialog("close");
		    	}
		    }]
		});
	}
	
	//详细
	tppTradeTPayInfo_list.detailFormSubmit = function(pkid){
		$('<div id="dialog_holder"></div>').dialog({
		    title: '详细TppTradeTPayInfo',
		    width: 800,
		    height: 500,
		    href: 'detailTppTradeTPayInfoPage.action',
		    modal: true,
		    method: "POST",
		    params:{pkid:pkid},
		    extractor: function(data){
		    	var bodyPattern = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
				var contentPattern = /<article\s+id="content"[^>]*>((.|[\n\r])*)<\/article>/im;
				var scriptPattern = /<script[^>]*>(.|[\n\r\t])*?<\/script>/gim;
				var buttonPattern = /<div\s+class="button-field[\s\w]*"[^>]*>(.|[\n\r\t])*?<\/div>/im;
				var matches = bodyPattern.exec(data);
				if (matches){
					var content = matches[1].match(contentPattern);
				    var scripts = matches[1].match(scriptPattern);
					return (content[1]||"").replace(scriptPattern,"").replace(buttonPattern,"") + (scripts||[]).join("");
				} else {
					return data;
				}
			},
			onClose: function(){
				$(this).dialog("destroy");
			},
		    buttons: [{
		    	text: "提  交",
		    	handler: function(e){
		    		var ThisForm = $(this).find("form");
		    	}
		    },{
		    	text: "关闭",
		    	handler: function(e){
		    		$(this).dialog("close");
		    	}
		    }]
		});
	}
	$('#export_btn').click(function(){
		var search_begin_date = $("#search_begin_date").datebox("getValue");
		var search_end_date = $("#search_end_date").datebox("getValue");
		var search_date_type = $("#search_date_type").val();
		var search_pay_sys_no = $('#search_pay_sys_no').combobox("getValue");
		var search_biz_sys_no = $('#search_biz_sys_no').combobox("getValue");
		var search_trade_status = $('#search_trade_status').combobox("getValue");
		var search_biz_type = $('#search_biz_type').combobox("getValue");
		
		var search_notify_query_status = $('#search_notify_query_status').combobox("getValue");
		var search_notify_merge_status = $('#search_notify_merge_status').combobox("getValue"); 
		
		
		var search_receiver_info = $("input[name='search_receiver_info']").val();
		if (search_receiver_info=='姓名/银行卡号/证件号') {
			search_receiver_info='';
		}
		var search_flow = $("input[name='search_flow']").val();
		if (search_flow=='业务流水号/交易流水号') {
			search_flow='';
		}
		
		$('#export_begin_date').val(search_begin_date);
		$('#export_end_date').val(search_end_date);
		$('#export_date_type').val(search_date_type);
		$('#export_pay_sys_no').val(search_pay_sys_no);
		$('#export_biz_sys_no').val(search_biz_sys_no);
		$('#export_trade_status').val(search_trade_status);
		$('#export_biz_type').val(search_biz_type);
		$('#export_notify_query_status').val(search_notify_query_status);
		$('#export_notify_merge_status').val(search_notify_merge_status);
		$('#export_receiver_info').val(search_receiver_info);
		$('#export_flow').val(search_flow);
		
		
		var exporForm="#exportPayInfoExcelForm";
		$(exporForm).form({   
			 url:'${path}/trade/pay/exportPayInfoExcel',    
		     onSubmit: function(){
		    	 
		     }
		});// 
		$(exporForm).submit();
	});	
	inputTipText(); 
});
function loadBefore(param) {
	var search_begin_date = $("#search_begin_date").datebox("getValue");
	var search_end_date = $("#search_end_date").datebox("getValue");
	if (search_begin_date == '' && search_end_date == '') {
		param.search_begin_date=CurentTime();
		param.search_end_date=CurentTime();
  	}
}

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
	var search_pay_sys_no = $('#search_pay_sys_no').combobox("getValue");
	var search_biz_sys_no = $('#search_biz_sys_no').combobox("getValue");
	var search_trade_status = $('#search_trade_status').combobox("getValue");
	var search_biz_type = $('#search_biz_type').combobox("getValue");
	
	var search_notify_query_status = $('#search_notify_query_status').combobox("getValue");
	var search_notify_merge_status = $('#search_notify_merge_status').combobox("getValue"); 
	var search_receiver_type = $('#search_receiver_type').combobox("getValue");
	var search_bank_code = $('#search_bank_code').combobox("getValue");
	var search_receiver_info = $("input[name='search_receiver_info']").val();
	if (search_receiver_info=='姓名/银行卡号/证件号') {
		search_receiver_info='';
	}
	var search_flow = $("input[name='search_flow']").val();
	if (search_flow=='业务流水号/交易流水号') {
		search_flow='';
	}
	
  	var table_collect_summary=document.getElementById("table_pay_summary");
  	
  	$('#dg_tppTradeTPayInfo').datagrid("options").queryParams={
  		'search_pay_sys_no':$('#search_pay_sys_no').combobox("getValue"),
    	'search_biz_sys_no':$('#search_biz_sys_no').combobox("getValue"),
    	'search_flow':search_flow,
    	'search_trade_status':$('#search_trade_status').combobox("getValue"),
    	'search_biz_type':$('#search_biz_type').combobox("getValue"),
    	'search_receiver_info':search_receiver_info,
    	'search_notify_query_status':$('#search_notify_query_status').combobox("getValue"),
    	'search_notify_merge_status':$('#search_notify_merge_status').combobox("getValue"),
    	'search_begin_date':$("#search_begin_date").datebox("getValue"),
		'search_end_date':$("#search_end_date").datebox("getValue"),
		'search_date_type':$("#search_date_type").val(),
		'search_receiver_type':$('#search_receiver_type').combobox("getValue"),
		'search_bank_code':$('#search_bank_code').combobox("getValue")
		};
  	
  	$.ajax({
		url:'${path}/trade/pay/infoSummaryData',
		data:{"search_begin_date":search_begin_date,"search_end_date":search_end_date,'search_date_type':search_date_type,'search_biz_sys_no':search_biz_sys_no,'search_notify_query_status':search_notify_query_status,'search_notify_merge_status':search_notify_merge_status,'search_flow':search_flow,"search_trade_status":search_trade_status,"search_receiver_info":search_receiver_info,"search_pay_sys_no":search_pay_sys_no,"search_biz_type":search_biz_type, "search_receiver_type":search_receiver_type, "search_bank_code":search_bank_code},
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

function appRequest(tradeFlow){
	$('<div id="dialog_holder"></div>').dialog({
	    title: '渠道请求信息',
	    width: 800,
	    height: 400,
	    href:  '${path}/trade/pay/appRequest',
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
function notifyQuery(tradeFlow) {
	var add_form_id ='#addTppTradeTWaitingQueryFrom';
	$('<div id="dialog_holder"></div>').dialog({
	    title: '通知查询',
	    width: 800,
	    height: 400,
	    href: '${path}/trade/pay/tradeWaitingQueryPage',
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
					 url:'${path}/trade/pay/tradeWaitingQuery',    
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
    href: '${path}/trade/pay/tradeWaitingMergePage',
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
				 url:'${path}/trade/pay/tradeWaitingMerge',    
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
    href:  '${path }/trade/pay/tradeWaitingQueryListPage',
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
</script>
</body>
</html>