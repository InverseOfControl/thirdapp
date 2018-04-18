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
								
								<td style="text-align:right">业务系统：</td>
								<td>
									<input id="search_biz_sys_no1" class="easyui-combobox" name="search_biz_sys_no1" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/bizSysNoList' " style="width:100px;"/>  
								</td>
								<td style="text-align:right">第三方支付平台：</td>
								<td>
									<input id="search_pay_sys_no1" class="easyui-combobox" name="search_pay_sys_no1" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/dictionary/3' " style="width:100px;"/>
								</td>
								<td style="text-align:right">查询日期：</td>
								<td>
									<input type="text" id="search_begin_date1" name="search_begin_date1"  value="${search_begin_date}" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
									－
									<input type="text" id="search_end_date1" name="search_end_date1"  value="${search_end_date}" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
								</td>
						</tr>
						<tr>
								<td style="text-align:right">发送状态：</td>
								<td>
									<%-- <input type="text" id="search_status" name="search_status" class="easyui-textbox" value="${search_status}"/> --%>
									<input id="search_send_status1" class="easyui-combobox" name="search_send_status1" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/sendStatusList' " style="width:100px;"/>  
								</td>
								<td style="text-align:right">交易结果状态：</td>
								<td>
									<%-- <input type="text" id="search_trade_status" name="search_trade_status" class="easyui-textbox" value="${search_trade_status}"/> --%>
									<input id="search_trade_status1" class="easyui-combobox" name="search_trade_status1" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/getTradeStatusForCollectTask' " style="width:100px;"/>  
								</td>
								<td style="text-align:right">业务流水号：</td>
								<td>
									<input type="text" id="search_biz_flow1" name="search_biz_flow1" class="easyui-textbox" value="${search_biz_flow}"/>
								</td>
						</tr>
						<tr>
								<td style="text-align:right">信息类别：</td>
								<td>
									<%-- <input type="text" id="search_info_category_code" name="search_info_category_code" class="easyui-textbox" value="${search_info_category_code}"/> --%>
									<input id="search_info_category1" class="easyui-combobox" name="search_info_category1" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/getInfoCategoryList' " style="width:100px;"/>  
								</td>
								<td style="text-align:right">业务类型：</td>
								<td>
									<%-- <input type="text" id="search_biz_type" name="search_biz_type" class="easyui-textbox" value="${search_biz_type}"/> --%>
									<input id="search_biz_type1" class="easyui-combobox" name="search_biz_type1" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/bizTypeForPayList' " style="width:100px;"/>  
								</td>
								<td style="text-align:right">收款方信息：</td>
								<td>
									<input type="text" id="search_receiver_info1" name="search_receiver_info1" class="grayTips"   value="姓名/银行卡号/证件号" style="width:200px;"/>
								</td>
						</tr>
					</table>
				</form>
			</div>
			<!--搜索栏结束-->
			
			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding:0px;margin: 0px;" data-options="region:'center'">
				<table  id="dg_tppTradeTPayTask" class="easyui-datagrid" style="height:400px"
					data-options="rownumbers:true,pageSize:20,singleSelect:true,collapsible:true,sortName:'',pagination:'true',sortOrder:'desc',url:'${path}/trade/pay/listData/${reqId}',method:'post',toolbar:toolbar1,onLoadSuccess:function(){loadSummary();$(this).datagrid('fixRownumber');},onClickRow: function (rowIndex, rowData) {$(this).datagrid('unselectRow', rowIndex);}">
					<thead>
						<tr>
						 	<th data-options="field:'DETAIL',width:70,align:'center'">详细信息</th>
						 	<th data-options="field:'DETAIL_TASK',width:70,align:'center'">任务明细</th>
						 	<th data-options="field:'DETAIL_NOTIFY',width:70,align:'center'">推送明细</th>
							<!-- <th data-options="field:'EDIT',width:50,align:'center'">edit</th> -->
							<!-- <th data-options="field:'ID',width:120,sortable:'true',align:'right'">id</th> -->
							<th data-options="field:'REQ_ID',width:120,sortable:'true',align:'right'">交易请求ID</th>
							<th data-options="field:'PAY_SYS_NAME',width:120,sortable:'true',align:'right'">第三方支付平台</th>
							<th data-options="field:'BIZ_SYS_ACCOUNT_NO',width:120,sortable:'true',align:'right'">业务系统客户编号</th>
							<th data-options="field:'ZENGDAI_ACCOUNT_NO',width:120,sortable:'true',align:'right'">证大客户编号</th>
							<th data-options="field:'APP_NAME',width:120,sortable:'true',align:'right'">业务系统</th>
							<th data-options="field:'PAYER_ACCOUNT_NO',width:120,sortable:'true',align:'right'">付款方账户编号</th>
							<th data-options="field:'PAYER_ACCOUNT_NAME',width:120,sortable:'true',align:'right'">付款方姓名</th>
							<th data-options="field:'RECEIVER_NAME',width:120,sortable:'true',align:'right'">收款方姓名</th>
							<th data-options="field:'RECEIVER_BANK_CARD_NO',width:120,sortable:'true',align:'right'">收款方银行卡号</th>
							<th data-options="field:'RECEIVER_BANK_CARD_TYPE',width:120,sortable:'true',align:'right'">收款方银行卡类型</th>
							<th data-options="field:'RECEIVER_ID_TYPE',width:120,sortable:'true',align:'right'">收款方证件类型</th>
							<th data-options="field:'RECEIVER_ID',width:120,sortable:'true',align:'right'">收款方证件号</th>
							<th data-options="field:'RECEIVER_BANK_CODE',width:120,sortable:'true',align:'right'">收款方银行编码</th>
							<th data-options="field:'RECEIVER_BANK_NAME',width:120,sortable:'true',align:'right'">收款方银行</th>
							<th data-options="field:'RECEIVER_SUB_BANK_CODE',width:120,sortable:'true',align:'right'">收款方银行支行行号</th>
							<th data-options="field:'CURRENCY',width:120,sortable:'true',align:'right'">币种</th>
							<th data-options="field:'AMOUNT',width:120,sortable:'true',align:'right'">金额</th>
							<th data-options="field:'FEE',width:120,sortable:'true',align:'right'">手续费</th>
							<th data-options="field:'BIZ_REMARK',width:120,sortable:'true',align:'right'">业务备注</th>
							<th data-options="field:'BIZ_FLOW',width:120,sortable:'true',align:'right'">业务流水号</th>
							<th data-options="field:'PRIORITY',width:120,sortable:'true',align:'right'">优先级</th>
							<th data-options="field:'STATUS',width:120,sortable:'true',align:'right'">发送状态</th>
							<th data-options="field:'REMARK',width:120,sortable:'true',align:'right'">备注</th>
							<th data-options="field:'CREATER',width:120,sortable:'true',align:'right'">创建人</th>
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'right'">创建时间</th>
							<th data-options="field:'UPDATE_TIME',width:120,sortable:'true',align:'right'">更新时间</th>
							<th data-options="field:'SEND_THREAD_NAME',width:120,sortable:'true',align:'right'">发送程序名称</th>
							<th data-options="field:'IS_SEPARATE',width:120,sortable:'true',align:'right'">是否拆单</th>
							<th data-options="field:'SEPARATE_COUNT',width:120,sortable:'true',align:'right'">拆单数</th>
							<th data-options="field:'SPARE1',width:120,sortable:'true',align:'right'">备用1</th>
							<th data-options="field:'SPARE2',width:120,sortable:'true',align:'right'">备用2</th>
							<th data-options="field:'SEND_NUM',width:120,sortable:'true',align:'right'">发送次数</th>
							<th data-options="field:'RECEIVER_ACCOUNT_NO',width:120,sortable:'true',align:'right'">收款方账户编号</th>
							<th data-options="field:'BIZ_TYPE',width:120,sortable:'true',align:'right'">业务类型</th>
							<th data-options="field:'IS_NEED_PUSH',width:120,sortable:'true',align:'right'">是否需要推送 </th>
							<th data-options="field:'TRADE_STATUS',width:120,sortable:'true',align:'right'">交易结果状态</th>
							<th data-options="field:'TRADE_RESULT_INFO',width:120,sortable:'true',align:'right'">交易结果描述</th>
							<th data-options="field:'TRADE_SUCCESS_AMOUNT',width:120,sortable:'true',align:'right'">交易成功金额</th>
							<th data-options="field:'INFO_CATEGORY_NAME',width:120,sortable:'true',align:'right'">信息类别</th>
						</tr>
					</thead>
				</table>
			</div>
			<!--搜索栏结果end-->
			<div data-options="region:'south',split:true" style="height:100px;">
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
var tppTradeTPayTask_list ={};
var toolbar1 = [{
				id : 'search_btn1',
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {
					var flag = checkDate1();
					if (!flag) {
						return ;
					}
					tppTradeTPayTask_list.buildQueryParams();
					$('#dg_tppTradeTPayTask').datagrid('load');
					loadSummary();
				}
			},'-',{
				id : 'clear_btn1',
				text : '清除',
				iconCls : 'icon-remove',
				handler : function(){
					//清空查询条件
					$("#search_begin_date1").datebox('setValue', '');
					$("#search_end_date1").datebox('setValue', '');
					$('#search_biz_sys_no1').combobox('setValue','');
					$('#search_pay_sys_no1').combobox('setValue','');
					$('#search_send_status1').combobox('setValue','');
					$('#search_trade_status1').combobox('setValue','');
					$('#search_biz_type1').combobox('setValue','');
					$('#search_info_category1').combobox('setValue','');
					$("input[name='search_biz_flow1']").val("");
					$("input[name='search_receiver_info1']").val("姓名/银行卡号/证件号");
					
					tppTradeTPayTask_list.buildQueryParams();
					$('#dg_tppTradeTPayTask').datagrid('load');
					inputTipText(); 
				}
			}];

jQuery(function($){
	//定义构造查询
	tppTradeTPayTask_list.buildQueryParams=function(){
		var search_receiver_info = $("input[name='search_receiver_info1']").val();
		if (search_receiver_info=='姓名/银行卡号/证件号') {
			search_receiver_info='';
		}
		$('#dg_tppTradeTPayTask').datagrid("options").queryParams={
			 'search_begin_date':$("#search_begin_date1").datebox("getValue"),
			 'search_end_date':$("#search_end_date1").datebox("getValue"),
			 'search_biz_sys_no':$('#search_biz_sys_no1').combobox("getValue"),
			 'search_pay_sys_no':$('#search_pay_sys_no1').combobox("getValue"),
			 'search_send_status':$('#search_send_status1').combobox("getValue"),
			 'search_trade_status':$('#search_trade_status1').combobox("getValue"),
			 'search_biz_flow':$("input[name='search_biz_flow1']").val(),
			 'search_receiver_info':search_receiver_info,
			 'search_biz_type':$('#search_biz_type1').combobox("getValue"),
			 'search_info_category':$('#search_info_category1').combobox("getValue"),
		}
	}

	
	
	//新增
	$('#add_btn').click(function(){
		var add_form_id ='#addTppTradeTPayTaskFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: 'TPP_TRADE_T_PAY_TASK',
		    width: 800,
		    height: 500,
		    href: 'addTppTradeTPayTaskPage.action',
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
		    		$('#addTppTradeTPayTaskFrom').form({   
						 url:'addTppTradeTPayTaskAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppTradeTPayTask_list.buildQueryParams();
									$('#dg_tppTradeTPayTask').datagrid('load');
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
	tppTradeTPayTask_list.updateFormSubmit = function(pkid){
	    var edit_form_id ='#editTppTradeTPayTaskFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: 'TppTradeTPayTask',
		    width: 800,
		    height: 500,
		    href: 'editTppTradeTPayTaskPage.action',
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
		   				$('#editTppTradeTPayTaskFrom').form({   
						 url:'editTppTradeTPayTaskAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppTradeTPayTask_list.buildQueryParams();
									$('#dg_tppTradeTPayTask').datagrid('load');
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
		     			$('#delTppTradeTPayTaskFrom').form({   
						 url:'delTppTradeTPayTaskAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppTradeTPayTask_list.buildQueryParams();
									$('#dg_tppTradeTPayTask').datagrid('load');
									$('#dialog_holder').dialog('close');
							     }
						});// 
					    $('#delTppTradeTPayTaskFrom').submit();
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
	tppTradeTPayTask_list.detailFormSubmit = function(pkid){
		$('<div id="dialog_holder"></div>').dialog({
		    title: '详细TppTradeTPayTask',
		    width: 800,
		    height: 500,
		    href: 'detailTppTradeTPayTaskPage.action',
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
	inputTipText(); 
});

function checkDate1(){
	var beginDate = $("#search_begin_date1").datebox("getValue");
	var endDate = $("#search_end_date1").datebox("getValue");
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

function viewTaskDetail(taskId){
	$('<div id="dialog_holder"></div>').dialog({
	    title: '详细信息',
	    width: 800,
	    height: 400,
	    href:  '${path}/trade/pay/detailTask',
	    modal: true,
	    method: "POST",
	    params:{taskId:taskId},
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

function viewTaskInfo(taskId){
	$('<div id="dialog_holder"></div>').dialog({
	    title: '代付交易明细',
	    width: 800,
	    height: 400,
	    href:  '${path}/trade/pay/taskInfoList',
	    modal: true,
	    method: "POST",
	    params:{taskId:taskId},
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

function viewNotify(taskId) {
	$('<div id="dialog_holder"></div>').dialog({
	    title: '推送信息',
	    width: 800,
	    height: 400,
	    href:  '${path}/trade/pay/notifyList',
	    modal: true,
	    method: "POST",
	    params:{taskId:taskId},
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
	var search_begin_date = $("#search_begin_date1").datebox("getValue");
	var search_end_date = $("#search_end_date1").datebox("getValue");
	
	var search_biz_sys_no = $('#search_biz_sys_no1').combobox("getValue");
	var search_pay_sys_no = $('#search_pay_sys_no1').combobox("getValue");
	var search_send_status = $('#search_send_status1').combobox("getValue");
	var search_trade_status = $('#search_trade_status1').combobox("getValue");
	var search_biz_type = $('#search_biz_type1').combobox("getValue");
	var search_info_category = $('#search_info_category1').combobox("getValue");
	var search_biz_flow = $("input[name='search_biz_flow1']").val();
	
	var search_receiver_info = $("input[name='search_receiver_info1']").val();
	
	if (search_receiver_info=='姓名/银行卡号/证件号') {
		search_receiver_info='';
	}
	
  	var table_collect_summary=document.getElementById("table_pay_summary");
  	$('#dg_tppTradeTPayTask').datagrid("options").queryParams={
  		 'search_begin_date':$("#search_begin_date1").datebox("getValue"),
		 'search_end_date':$("#search_end_date1").datebox("getValue"),
		 'search_biz_sys_no':$('#search_biz_sys_no1').combobox("getValue"),
		 'search_pay_sys_no':$('#search_pay_sys_no1').combobox("getValue"),
		 'search_send_status':$('#search_send_status1').combobox("getValue"),
		 'search_trade_status':$('#search_trade_status1').combobox("getValue"),
		 'search_biz_flow':$("input[name='search_biz_flow1']").val(),
		 'search_receiver_info':search_receiver_info,
		 'search_biz_type':$('#search_biz_type1').combobox("getValue"),
		 'search_info_category':$('#search_info_category1').combobox("getValue"),
		 
	};
  	$.ajax({
		url:'${path}/trade/pay/summaryData/${reqId}',
		data:{"search_begin_date":search_begin_date,"search_end_date":search_end_date,"search_biz_sys_no":search_biz_sys_no,"search_pay_sys_no":search_pay_sys_no,"search_send_status":search_send_status,"search_trade_status":search_trade_status,"search_biz_flow":search_biz_flow,"search_biz_type":search_biz_type,"search_info_category":search_info_category,"search_receiver_info":search_receiver_info},
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
</script>
</body>
</html>