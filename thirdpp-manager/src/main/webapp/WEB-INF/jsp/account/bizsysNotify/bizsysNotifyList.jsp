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
			<div class="easyui-panel" title="查询条件" style="padding:5px;height:80px;margin: 0px;" data-options="region:'north'">
				<form id="searchForm" name="searchForm" method="post" >
					<table cellpadding="5">
								<td style="text-align:right">对账日期：</td>
								<td>
									<input type="text" id="search_bizsys_account_day" name="search_bizsys_account_day"  value="${search_bizsys_account_day}" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
								</td>
								<td style="text-align:right">商户号：</td>
								<td>
									<input type="text" id="search_merchant_no" name="search_merchant_no" class="easyui-textbox" value="${search_merchant_no}" style="width:100px;"/>
								</td>
								<td style="text-align:right">对账状态： </td>
								<td>
									<input id="search_status" class="easyui-combobox" name="search_status" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/getAccountBizsysNotifyStatusList' " style="width:100px;"/> 
								</td>
								
					</table>
				</form>
			</div>
			<!--搜索栏结束-->
			
			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding:0px;margin: 0px;" data-options="region:'center'">
				<table  id="dg_tppAccountBizsysNotify" class="easyui-datagrid" 
					data-options="rownumbers:true,singleSelect:true,collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/account/bizsysNotify/listData',method:'post',toolbar:toolbar,fit:'true'">
					<thead>
						<tr>
						 	<!-- <th data-options="field:'DETAIL',width:50,align:'center'">detail</th>
							<th data-options="field:'EDIT',width:50,align:'center'">edit</th>
							<th data-options="field:'ID',width:120,sortable:'true',align:'left'">主键</th> -->
							<th data-options="field:'BIZSYS_ACCOUNT_DAY',width:120,sortable:'true',align:'left'">对账日期</th>
							<th data-options="field:'MERCHANT_NO',width:120,sortable:'true',align:'left'">商户号</th>
							<th data-options="field:'THIRD_PARTY_ACCOUNT_REQ_ID',width:120,sortable:'true',align:'left'">第三方对账请求ID</th>
							<th data-options="field:'STATUS',width:120,sortable:'true',align:'left'">对账状态 </th>
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'left'">创建时间</th>
						</tr>
					</thead>
				</table>
			</div>
			<!--搜索栏结果end-->

<script>
var tppAccountBizsysNotify_list ={};
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
	//定义构造查询
	tppAccountBizsysNotify_list.buildQueryParams=function(){
		$('#dg_tppAccountBizsysNotify').datagrid("options").queryParams={
				 'search_bizsys_account_day':$("#search_bizsys_account_day").datebox("getValue"),
				 'search_merchant_no':$("input[name='search_merchant_no']").val(),
				 'search_status':$('#search_status').combobox("getValue"),
		}
	}

	//重新按照条件刷新查询内容
	$('#search_btn').click(function(){
		tppAccountBizsysNotify_list.buildQueryParams();
		$('#dg_tppAccountBizsysNotify').datagrid('load');
	});
	
    //重置查询条件并刷新内容
	$('#clear_btn').click(function(){
		//清空查询条件
			$("#search_bizsys_account_day").datebox('setValue', '');
			$("input[name='search_merchant_no']").val("");
			$('#search_status').combobox('setValue','');
		tppAccountBizsysNotify_list.buildQueryParams();
		$('#dg_tppAccountBizsysNotify').datagrid('load');
	});
	
	
	//新增
	$('#add_btn').click(function(){
		var add_form_id ='#addTppAccountBizsysNotifyFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: 'TPP_ACCOUNT_BIZSYS_NOTIFY',
		    width: 800,
		    height: 500,
		    href: 'addTppAccountBizsysNotifyPage.action',
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
		    		$('#addTppAccountBizsysNotifyFrom').form({   
						 url:'addTppAccountBizsysNotifyAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppAccountBizsysNotify_list.buildQueryParams();
									$('#dg_tppAccountBizsysNotify').datagrid('load');
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
	tppAccountBizsysNotify_list.updateFormSubmit = function(pkid){
	    var edit_form_id ='#editTppAccountBizsysNotifyFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: 'TppAccountBizsysNotify',
		    width: 800,
		    height: 500,
		    href: 'editTppAccountBizsysNotifyPage.action',
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
		   				$('#editTppAccountBizsysNotifyFrom').form({   
						 url:'editTppAccountBizsysNotifyAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppAccountBizsysNotify_list.buildQueryParams();
									$('#dg_tppAccountBizsysNotify').datagrid('load');
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
		     			$('#delTppAccountBizsysNotifyFrom').form({   
						 url:'delTppAccountBizsysNotifyAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppAccountBizsysNotify_list.buildQueryParams();
									$('#dg_tppAccountBizsysNotify').datagrid('load');
									$('#dialog_holder').dialog('close');
							     }
						});// 
					    $('#delTppAccountBizsysNotifyFrom').submit();
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
	tppAccountBizsysNotify_list.detailFormSubmit = function(pkid){
		$('<div id="dialog_holder"></div>').dialog({
		    title: '详细TppAccountBizsysNotify',
		    width: 800,
		    height: 500,
		    href: 'detailTppAccountBizsysNotifyPage.action',
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

</script>
</body>
</html>