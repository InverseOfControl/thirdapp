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
			<div class="easyui-panel" title="查询条件" style="padding:5px;height:120px;margin: 0px;" data-options="region:'north'">
				<form id="searchForm" name="searchForm" method="post" >
					<table cellpadding="5">
						<tr>
								<td style="text-align:right">任务ID：</td>
								<td>
									<input type="text" id="search_task_id" name="search_task_id" class="easyui-textbox" value="${search_task_id}" style="width:100px;"/>
								</td>
								<td style="text-align:right">业务流水号：</td>
								<td>
									<input type="text" id="search_biz_flow" name="search_biz_flow" class="easyui-textbox" value="${search_biz_flow}" style="width:100px;"/>
								</td>
								
								<td style="text-align:right">对账日期：</td>
								<td>
									<input type="text" id="search_account_begin_date" name="search_account_begin_date"  value="${search_account_begin_date}" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
									－
									<input type="text" id="search_account_end_date" name="search_account_end_date"  value="${search_account_end_date}" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
								</td>
						</tr>
						<tr>	
								<td style="text-align:right">第三方支付平台：</td>
								<td>
									<input id="search_third_type_no" class="easyui-combobox" name="search_third_type_no" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/dictionary/3' " style="width:100px;"/>
								</td>
								<td style="text-align:right">业务系统：</td>
								<td>
									<input id="search_biz_sys_no" class="easyui-combobox" name="search_biz_sys_no" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/bizSysNoList' " style="width:100px;"/>  
								</td>
								<td style="text-align:right">业务类型：</td>
								<td>
									<input id="search_biz_type" class="easyui-combobox" name="search_biz_type" data-options="
										editable:false,valueField: 'id',
										textField: 'text',
										//url是下拉框请求显示的数据
										url:'${path}/enumset/accountBizTypeList/combobox',
										onLoadSuccess: function () { 									 	  					 
										 	  						$('#search_biz_type').combotree('setText', '请选择');	
										 	  					} " style="width:100px;"/> 
								</td>
						</tr>
					</table>
				</form>
			</div>
			<!--搜索栏结束-->
			
			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding:0px;margin: 0px;" data-options="region:'center'">
				<table  id="dg_tppAccountBizsysInfo" class="easyui-datagrid" 
					data-options="rownumbers:true,pageSize:20,singleSelect:false,selectOnCheck: 'true',checkOnSelect:'true',collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/account/bizsysInfo/listData',method:'post',toolbar:toolbar,fit:'true',onClickRow: function (rowIndex, rowData) {$(this).datagrid('unselectRow', rowIndex);},onBeforeLoad:function(param){loadBefore(param)}">
					<thead>
						<tr>
						 	<!-- <th data-options="field:'DETAIL',width:50,align:'center'">detail</th>
							<th data-options="field:'EDIT',width:50,align:'center'">edit</th> -->
							<th data-options="field:'TASK_ID',width:120,sortable:'true',align:'left'">任务ID</th>
							<!-- <th data-options="field:'BIZ_SYS_NO',width:120,sortable:'true',align:'left'">业务系统号</th> -->
							<th data-options="field:'BIZ_SYS_NAME',width:150,sortable:'true',align:'left'">业务系统</th>
							<th data-options="field:'BIZ_FLOW',width:120,sortable:'true',align:'left'">业务流水号</th>
							<th data-options="field:'BIZ_TYPE_STR',width:120,sortable:'true',align:'left'">业务类型</th>
							<th data-options="field:'BIZ_TYPE',width:120,sortable:'true',align:'left'">业务类型编码</th>
							<!-- <th data-options="field:'PAY_SYS_NO',width:120,sortable:'true',align:'left'">通道编码</th> -->
							<th data-options="field:'PAY_SYS_NAME',width:120,sortable:'true',align:'left'">第三方支付平台</th>
							<th data-options="field:'TOTAL_AMOUNT',width:120,sortable:'true',align:'left'">任务总金额</th>
							<th data-options="field:'SUCCESS_AMOUNT',width:120,sortable:'true',align:'left'">对账成功金额</th>
							<th data-options="field:'ACCOUNT_DAY',width:120,sortable:'true',align:'left'">对账日期</th>
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'left'">创建时间</th>
						</tr>
					</thead>
				</table>
			</div>
			<!--搜索栏结果end-->
<script>
var tppAccountBizsysInfo_list ={};
var toolbar = [{
				id : 'search_btn',
				text : '查询',
				iconCls : 'icon-search'
			},'-',{
				id : 'clear_btn',
				text : '清除',
				iconCls : 'icon-remove'
			}];
jQuery(function($){
	$("#search_account_begin_date").datebox('setValue', CurentTime());
	$("#search_account_end_date").datebox('setValue', CurentTime());
	//定义构造查询
	tppAccountBizsysInfo_list.buildQueryParams=function(){
		$('#dg_tppAccountBizsysInfo').datagrid("options").queryParams={
				 'search_biz_flow':$("input[name='search_biz_flow']").val(),
				 'search_biz_type':$('#search_biz_type').combobox("getValue"),
				 'search_task_id':$("input[name='search_task_id']").val(),
				 'search_third_type_no':$('#search_third_type_no').combobox("getValue"),
				 'search_biz_sys_no':$('#search_biz_sys_no').combobox("getValue"),
				 'search_account_begin_date':$("#search_account_begin_date").datebox("getValue"),
				 'search_account_end_date':$("#search_account_end_date").datebox("getValue"),
		}
	}
	tppAccountBizsysInfo_list.buildQueryParams();
	//重新按照条件刷新查询内容
	$('#search_btn').click(function(){
		tppAccountBizsysInfo_list.buildQueryParams();
		$('#dg_tppAccountBizsysInfo').datagrid('load');
	});
	
    //重置查询条件并刷新内容
	$('#clear_btn').click(function(){
		//清空查询条件
			$("input[name='search_biz_flow']").val("");
			$("input[name='search_task_id']").val("");
			$('#search_third_type_no').combobox('setValue','');
			$('#search_biz_sys_no').combobox('setValue','');
			$('#search_biz_type').combobox('setValue','');
			$('#search_biz_type').combobox('setText', '请选择');	
			$("#search_account_begin_date").datebox('setValue', CurentTime());
			$("#search_account_end_date").datebox('setValue', CurentTime());
		tppAccountBizsysInfo_list.buildQueryParams();
		$('#dg_tppAccountBizsysInfo').datagrid('load');
	});
	
	
	//新增
	$('#add_btn').click(function(){
		var add_form_id ='#addTppAccountBizsysInfoFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: 'TPP_ACCOUNT_BIZSYS_INFO',
		    width: 800,
		    height: 500,
		    href: 'addTppAccountBizsysInfoPage.action',
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
		    		$('#addTppAccountBizsysInfoFrom').form({   
						 url:'addTppAccountBizsysInfoAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppAccountBizsysInfo_list.buildQueryParams();
									$('#dg_tppAccountBizsysInfo').datagrid('load');
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
	tppAccountBizsysInfo_list.updateFormSubmit = function(pkid){
	    var edit_form_id ='#editTppAccountBizsysInfoFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: 'TppAccountBizsysInfo',
		    width: 800,
		    height: 500,
		    href: 'editTppAccountBizsysInfoPage.action',
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
		   				$('#editTppAccountBizsysInfoFrom').form({   
						 url:'editTppAccountBizsysInfoAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppAccountBizsysInfo_list.buildQueryParams();
									$('#dg_tppAccountBizsysInfo').datagrid('load');
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
		     			$('#delTppAccountBizsysInfoFrom').form({   
						 url:'delTppAccountBizsysInfoAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppAccountBizsysInfo_list.buildQueryParams();
									$('#dg_tppAccountBizsysInfo').datagrid('load');
									$('#dialog_holder').dialog('close');
							     }
						});// 
					    $('#delTppAccountBizsysInfoFrom').submit();
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
	tppAccountBizsysInfo_list.detailFormSubmit = function(pkid){
		$('<div id="dialog_holder"></div>').dialog({
		    title: '详细TppAccountBizsysInfo',
		    width: 800,
		    height: 500,
		    href: 'detailTppAccountBizsysInfoPage.action',
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
	
	
});
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
	var search_begin_date = $("#search_account_begin_date").datebox("getValue");
	var search_end_date = $("#search_account_end_date").datebox("getValue");
	if (search_begin_date == '' && search_end_date == '') {
		param.search_account_begin_date=CurentTime();
		param.search_account_end_date=CurentTime();
  	}
	
}
</script>
</body>
</html>