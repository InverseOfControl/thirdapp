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
								<td style="text-align:right">交易流水号：</td>
								<td>
									<input type="text" id="search_trade_flow" name="search_trade_flow" class="easyui-textbox" value="${search_trade_flow}"  style="width:100px;"/>
								</td>
								<td style="text-align:right">业务类型：</td>
								<td>
									<%-- <input type="text" id="search_biz_type_no" name="search_biz_type_no" class="easyui-textbox" value="${search_biz_type_no}"/> --%>
									<input id="search_biz_type_no" class="easyui-combobox" name="search_biz_type_no" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/collectAndPayList' " style="width:100px;"/>  
								</td>
								
								<td style="text-align:right">创建日期：</td>
								<td>
									<input type="text" id="beginDate" name="beginDate"  value="${beginDate}" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
									－
									<input type="text" id="endDate" name="endDate"  value="${endDate}" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
								</td>
						</tr>
						<tr>
								<td style="text-align:right">业务系统：</td>
								<td>
									<%-- <input type="text" id="search_biz_sys_no" name="search_biz_sys_no" class="easyui-textbox" value="${search_biz_sys_no}"/> --%>
									<input id="search_biz_sys_no" class="easyui-combobox" name="search_biz_sys_no" data-options="
									editable:false,valueField: 'value',
									textField: 'name',
									//url是下拉框请求显示的数据
									url:'${path }/enumset/bizSysNoList' " style="width:100px;"/>  
								</td>
								
								<td style="text-align:right">信息类别：</td>
								<td>
									<%-- <input type="text" id="search_info_category_code" name="search_info_category_code" class="easyui-textbox" value="${search_info_category_code}"/> --%>
									<input id="search_info_category_code" class="easyui-combobox" name="search_info_category_code" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/getInfoCategoryList'" style="width:100px;"/>
								</td>
								<td style="text-align:right">第三方支付平台：</td>
								<td>
									<%-- <input type="text" id="search_pay_sys_no" name="search_pay_sys_no" class="easyui-textbox" value="${search_pay_sys_no}"/> --%>
									<input id="search_pay_sys_no" class="easyui-combobox" name="search_pay_sys_no" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/dictionary/3' " style="width:100px;"/>  
								</td>
						</tr>
						<tr>	
								<td style="text-align:right">处理状态：</td>
								<td>
									<%-- <input type="text" id="search_status" name="search_status" class="easyui-textbox" value="${search_status}"/> --%>
									<input id="search_status" class="easyui-combobox" name="search_status" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/getTradeWaitingStatusList'" style="width:100px;"/>
								</td>
								<td style="text-align:right">运营类型：</td>
								<td>
									<input id="search_op_mode" class="easyui-combobox" name="search_op_mode" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/OPModeList'" style="width:100px;"/>
								</td>
						</tr>
					</table>
				</form>
			</div>
			<!--搜索栏结束-->
			
			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding:0px;margin: 0px;" data-options="region:'center'">
				<%-- <table  id="dg_tppTradeTWaitingQuery" class="easyui-datagrid" 
					data-options="rownumbers:true,singleSelect:true,collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/trade/waitingQuery/tradeWaitingQueryListData/null',method:'post',toolbar:toolbar,fit:'true'"> --%>
				<table  id="dg_tppTradeTWaitingQuery" class="easyui-datagrid" 
					data-options="rownumbers:true,pageSize:20,singleSelect:true,collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/trade/waitingQuery/tradeWaitingQueryListData/null',method:'post',toolbar:toolbar,fit:'true',
					onLoadSuccess:function(){loadQueryParam();$(this).datagrid('fixRownumber');},
					onBeforeLoad:function(param){loadBefore(param)},onClickRow: function (rowIndex, rowData) {$(this).datagrid('unselectRow', rowIndex);}">
					<thead>
						<tr>
						 	<!-- <th data-options="field:'ID',width:120,sortable:'true',align:'left'">ID</th> -->
						 	<th data-options="field:'CHECKED',width:60,sortable:'true',align:'center'">审核操作</th>
							<th data-options="field:'TRADE_FLOW',width:120,sortable:'true',align:'left'">交易流水号</th>
							<th data-options="field:'BIZ_TYPE_NO',width:120,sortable:'true',align:'left'">业务类型</th>
							<!-- <th data-options="field:'BIZ_SYS_NO',width:120,sortable:'true',align:'left'">业务系统号</th> -->
							<th data-options="field:'BIZ_SYS_NAME',width:120,sortable:'true',align:'left'">业务系统</th>
							<!-- <th data-options="field:'PAY_SYS_NO',width:120,sortable:'true',align:'left'">第三方支付平台编码</th> -->
							<th data-options="field:'PAY_SYS_NAME',width:120,sortable:'true',align:'left'">第三方支付平台</th>
							<th data-options="field:'PAYER_ACCOUNT_NO',width:120,sortable:'true',align:'left'">付款方账户编号</th>
							<!-- <th data-options="field:'INFO_CATEGORY_CODE',width:120,sortable:'true',align:'left'">信息类别编码</th> -->
							<th data-options="field:'INFO_CATEGORY_NAME',width:120,sortable:'true',align:'left'">信息类别</th>
							<th data-options="field:'QUERY_MODULE_NAME',width:120,sortable:'true',align:'left'">查询模块名称</th>
							<th data-options="field:'OP_MODE',width:120,sortable:'true',align:'left'">运营类型</th>
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'left'">创建时间</th>
							<th data-options="field:'UPDATE_TIME',width:120,sortable:'true',align:'left'">修改时间</th>
							<th data-options="field:'STATUS',width:120,sortable:'true',align:'left'">处理状态</th>
							<th data-options="field:'CREATER',width:120,sortable:'true',align:'left'">创建人</th>
							<th data-options="field:'AUDITOR',width:120,sortable:'true',align:'left'">审核人</th>
							<th data-options="field:'AUDIT_TIME',width:120,sortable:'true',align:'left'">审核时间</th>
						</tr>
					</thead>
				</table>
			</div>
			<!--搜索栏结果end-->

<script>
var tppTradeTWaitingQuery_list ={};
var toolbar = [{
				id : 'search_btn',
				text : '查询',
				iconCls : 'icon-search'
			},'-',{
				id : 'clear_btn',
				text : '清除',
				iconCls : 'icon-remove'
			},'-',{
				id : 'checked_btn',
				text : '批量审核',
				iconCls : 'icon-mini-edit'
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
function loadBefore(param) {
	var search_begin_date = $("#beginDate").datebox("getValue");
	var search_end_date = $("#endDate").datebox("getValue");
	if (search_begin_date == '' && search_end_date == '') {
		param.beginDate=CurentTime();
		param.endDate=CurentTime();
  	}
}
function loadQueryParam(){
	 
	
	$('#dg_tppTradeTWaitingQuery').datagrid("options").queryParams={
		 'search_trade_flow':$("input[name='search_trade_flow']").val(),
		 'search_biz_type_no':$('#search_biz_type_no').combobox("getValue"),
		 'search_biz_sys_no':$('#search_biz_sys_no').combobox("getValue"),
		 'search_pay_sys_no':$('#search_pay_sys_no').combobox("getValue"),
		 'search_info_category_code':$('#search_info_category_code').combobox("getValue"),
		 'search_status':$('#search_status').combobox("getValue"),
		 'search_op_mode':$('#search_op_mode').combobox("getValue"),
		 'beginDate':$("#beginDate").datebox("getValue"),
		 'endDate':$("#endDate").datebox("getValue"),
	}
	 
}
function checkDate(){
	var beginDate = $("#beginDate").datebox("getValue");
	var endDate = $("#endDate").datebox("getValue");
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
		if (day < 0){
			$.messager.alert('提示','查询开始时间不能大于结束时间！');
			return false;
		}
	}
	return true;
}
Date.prototype.diff = function(date){
	  return (this.getTime() - date.getTime())/(24 * 60 * 60 * 1000);
	};
jQuery(function($){
	$("#beginDate").datebox('setValue', CurentTime());
	$("#endDate").datebox('setValue', CurentTime());
	//定义构造查询
	tppTradeTWaitingQuery_list.buildQueryParams=function(){
		$('#dg_tppTradeTWaitingQuery').datagrid("options").queryParams={
				 'search_trade_flow':$("input[name='search_trade_flow']").val(),
				 'search_biz_type_no':$('#search_biz_type_no').combobox("getValue"),
				 'search_biz_sys_no':$('#search_biz_sys_no').combobox("getValue"),
				 'search_pay_sys_no':$('#search_pay_sys_no').combobox("getValue"),
				 'search_info_category_code':$('#search_info_category_code').combobox("getValue"),
				 'search_status':$('#search_status').combobox("getValue"),
				 'search_op_mode':$('#search_op_mode').combobox("getValue"),
				 'beginDate':$("#beginDate").datebox("getValue"),
				 'endDate':$("#endDate").datebox("getValue"),
		}
	}

	//重新按照条件刷新查询内容
	$('#search_btn').click(function(){
		var flag = checkDate();
		if (!flag) {
			return ;
		}
		tppTradeTWaitingQuery_list.buildQueryParams();
		$('#dg_tppTradeTWaitingQuery').datagrid('load');
	});
	
    //重置查询条件并刷新内容
	$('#clear_btn').click(function(){
		//清空查询条件
			$("input[name='search_trade_flow']").val("");
			$('#search_biz_type_no').combobox('setValue','');
			$('#search_biz_sys_no').combobox('setValue','');
			$('#search_pay_sys_no').combobox('setValue','');
			$('#search_info_category_code').combobox('setValue','');
			$('#search_status').combobox('setValue','');
			$('#search_op_mode').combobox('setValue','');
			$("#beginDate").datebox('setValue', CurentTime());
			$("#endDate").datebox('setValue', CurentTime());
		tppTradeTWaitingQuery_list.buildQueryParams();
		$('#dg_tppTradeTWaitingQuery').datagrid('load');
	});
	
	
	//新增
	$('#add_btn').click(function(){
		var add_form_id ='#addTppTradeTWaitingQueryFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: 'TPP_TRADE_T_WAITING_QUERY',
		    width: 800,
		    height: 500,
		    href: 'addTppTradeTWaitingQueryPage.action',
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
		    		$('#addTppTradeTWaitingQueryFrom').form({   
						 url:'addTppTradeTWaitingQueryAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppTradeTWaitingQuery_list.buildQueryParams();
									$('#dg_tppTradeTWaitingQuery').datagrid('load');
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
	tppTradeTWaitingQuery_list.updateFormSubmit = function(pkid){
	    var edit_form_id ='#editTppTradeTWaitingQueryFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: 'TppTradeTWaitingQuery',
		    width: 800,
		    height: 500,
		    href: 'editTppTradeTWaitingQueryPage.action',
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
		   				$('#editTppTradeTWaitingQueryFrom').form({   
						 url:'editTppTradeTWaitingQueryAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppTradeTWaitingQuery_list.buildQueryParams();
									$('#dg_tppTradeTWaitingQuery').datagrid('load');
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
		     			$('#delTppTradeTWaitingQueryFrom').form({   
						 url:'delTppTradeTWaitingQueryAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppTradeTWaitingQuery_list.buildQueryParams();
									$('#dg_tppTradeTWaitingQuery').datagrid('load');
									$('#dialog_holder').dialog('close');
							     }
						});// 
					    $('#delTppTradeTWaitingQueryFrom').submit();
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
	tppTradeTWaitingQuery_list.detailFormSubmit = function(pkid){
		$('<div id="dialog_holder"></div>').dialog({
		    title: '详细TppTradeTWaitingQuery',
		    width: 800,
		    height: 500,
		    href: 'detailTppTradeTWaitingQueryPage.action',
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
	
	$('#checked_btn').click(function(){
		var add_form_id ='#checkedForm';
		
		$('<div id="dialog_holder"></div>').dialog({
		    title: '批量审核',
		    width: 800,
		    height: 400,
		    href : '${path}/trade/waitingQuery/batchWaitingQueryCheckedPage',
		    modal: true,
		    method: "POST",
		 
			onClose: function(){
				$(this).dialog("destroy");
			},
		    buttons: [{
		    	text: "确  定",
		    	handler: function(e){
		    		$('#checkedForm').form({   
						 url:'${path}/trade/waitingQuery/batchWaitingQueryChecked',    
							     onSubmit: function(){
							    	 $.messager.progress({
							 	 			text:'加载中...'							 	 		 
							 	 			});    
							     },    
							     success:function(data){
							       //do some
							       	var obj = eval("("+ data+ ")");
							    	if(obj.msg!=null){
							       		$.messager.alert('提示',obj.msg);	
							       	 	$.messager.progress('close');
							       		tppTradeTWaitingQuery_list.buildQueryParams();
										$('#dg_tppTradeTWaitingQuery').datagrid('load');
										$('#dialog_holder').dialog('close');
							       	}else{
							       		 $.messager.progress('close');
							       		tppTradeTWaitingQuery_list.buildQueryParams();
										$('#dg_tppTradeTWaitingQuery').datagrid('load');
										$('#dialog_holder').dialog('close');
							       	}	
							     }
						});// 
					    //validate and sbumit
					     $(".easyui-validatebox").each(function(){
			        	 $(this).validatebox({    
			        		    novalidate: false   
			        		}); 
			        	 
			        });
				     $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {
						  if (data) {
							var beginDate = $("#checked_begin_date").datebox("getValue");
							var endDate = $("#checked_end_date").datebox("getValue");
							if (beginDate != '' && beginDate != null && endDate != '' && endDate != null) {
								beginDate = new Date(beginDate);
								endDate = new Date(endDate);
								var day = endDate.diff(beginDate);
								if (day < 0){
									$.messager.alert('提示','请输入正确的日期！');
									return false;
							}
							}
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
	});
});
function tradeWaitingChecked(id){
	 $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {
		  if (data) {
			  $.ajax({
		        type: 'GET',
		        url: '${path}/trade/waitingQuery/tradeWaitingQueryChecked/'+id,
		        dataType: "json",
		        success:function(data){
		        	$('#dg_tppTradeTWaitingQuery').datagrid("options").queryParams={
						 'search_trade_flow':$("input[name='search_trade_flow']").val(),
						 'search_biz_type_no':$('#search_biz_type_no').combobox("getValue"),
						 'search_biz_sys_no':$('#search_biz_sys_no').combobox("getValue"),
						 'search_pay_sys_no':$('#search_pay_sys_no').combobox("getValue"),
						 'search_info_category_code':$('#search_info_category_code').combobox("getValue"),
						 'search_status':$('#search_status').combobox("getValue"),
						 'search_op_mode':$('#search_op_mode').combobox("getValue"),
						 'beginDate':$("#beginDate").datebox("getValue"),
						 'endDate':$("#endDate").datebox("getValue"),
				}
		      	  $('#dg_tppTradeTWaitingQuery').datagrid('load');  
		        }
		    });
		  }
	  });	
	
	
}
Date.prototype.diff = function(date){
  return (this.getTime() - date.getTime())/(24 * 60 * 60 * 1000);
}
</script>
</body>
</html>