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
			<div class="easyui-panel" title="查询条件" style="padding:5px;height:144px;margin: 0px;" data-options="region:'north'">
				<form id="searchForm" name="searchForm" method="post" >
					<table cellpadding="5">
						<tr>
						<td style="text-align:right">业务流水号：</td>
						<td>
							<input type="text" id="search_biz_flow" name="search_biz_flow" class="easyui-textbox" value="${search_biz_flow}" style="width:100px;"/>
						</td>
						<td style="text-align:right">业务系统：</td>
						<td>
							<input id="search_biz_sys_no" class="easyui-combobox" name="search_biz_sys_no" data-options="
								editable:false,valueField: 'value',
								textField: 'name',
								//url是下拉框请求显示的数据
								url:'${path }/enumset/bizSysNoList' " style="width:100px;"/>  
						</td>
						<td style="text-align:right">查询日期：</td>
						<td>
							<input type="text" id="search_begin_date" name="search_begin_date"  value="${search_begin_date}" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
							－
							<input type="text" id="search_end_date" name="search_end_date"  value="${search_end_date}" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
						</td>
						</tr>
						<tr>
						<td style="text-align:right">交易流水号：</td>
						<td>
							<input type="text" id="search_trade_flow" name="search_trade_flow" class="easyui-textbox" value="${search_trade_flow}" style="width:100px;"/>
						</td>
						<td style="text-align:right">业务类型：</td>
						<td>
							<input id="search_biz_type" class="easyui-combobox" name="search_biz_type" data-options="
								editable:false,valueField: 'value',
								textField: 'name',
								//url是下拉框请求显示的数据
								url:'${path }/enumset/dictionary/4' " style="width:100px;"/>  
						</td>
						
						
						
						<td style="text-align:right">通知状态：</td>
						<td>
							<input id="search_notify_status" class="easyui-combobox" name="search_notify_status" data-options="
								editable:false,valueField: 'value',
								textField: 'name',
								//url是下拉框请求显示的数据
								url:'${path }/enumset/notifyStatusList' " style="width:100px;"/>  
						</td>
						</tr>
						<tr>
						<td style="text-align:right">运营类型：</td>
						<td>
							<input id="search_op_mode" class="easyui-combobox" name="search_op_mode" data-options="
								editable:false,valueField: 'value',
								textField: 'name',
								//url是下拉框请求显示的数据
								url:'${path }/enumset/OPModeList' " style="width:100px;"/>  
						</td>
						</tr>
					</table>
				</form>
			</div>
			<!--搜索栏结束-->
			
			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding:0px;margin: 0px;" data-options="region:'center'">
				<table  id="dg_tppTradeTNotify" class="easyui-datagrid" 
					data-options="rownumbers:true,singleSelect:true,pageSize:20,collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/trade/notify/listData/null',method:'post',toolbar:toolbar,fit:'true',onLoadSuccess:function(){loadQueryParam();$(this).datagrid('fixRownumber');},onBeforeLoad:function(param){loadBefore(param)},onClickRow: function (rowIndex, rowData) {$(this).datagrid('unselectRow', rowIndex);}">
					<thead>
						<tr>
						 	<!-- <th data-options="field:'DETAIL',width:50,align:'center'">detail</th> -->
							 <th data-options="field:'CLEAR',width:120,align:'center'">通知次数清零</th>  
						<!--   <th data-options="field:'ID',width:120,sortable:'true',align:'left'">ID</th> -->  
							<th data-options="field:'NOTIFY_COUNT',width:120,sortable:'true',align:'left'">通知次数</th>
							<th data-options="field:'BIZ_FLOW',width:120,sortable:'true',align:'left'">业务流水号</th>
							<!-- <th data-options="field:'BIZ_SYS_NO',width:120,sortable:'true',align:'left'">业务系统编码</th> -->
							<th data-options="field:'BIZ_SYS_NAME',width:120,sortable:'true',align:'left'">业务系统名称</th>
							<th data-options="field:'BIZ_TYPE',width:120,sortable:'true',align:'left'">业务类型</th>
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'left'">创建时间</th>
							<th data-options="field:'UPDATE_TIME',width:120,sortable:'true',align:'left'">更新时间</th>
							<th data-options="field:'NOTIFY_STATUS',width:120,sortable:'true',align:'left'">通知状态</th>
							<th data-options="field:'TRADE_STATUS',width:120,sortable:'true',align:'left'">交易结果状态</th>
							<th data-options="field:'TRADE_RESULT_INFO',width:120,sortable:'true',align:'left'">交易结果描述</th>
							<th data-options="field:'PAY_SYS_NAME',width:120,sortable:'true',align:'left'">支付通道</th>
							<th data-options="field:'TASK_ID',width:120,sortable:'true',align:'left'">任务ID</th>
							<th data-options="field:'TRADE_FLOW',width:120,sortable:'true',align:'left'">交易流水号</th>
							<th data-options="field:'TRADE_SUCCESS_AMOUNT',width:120,sortable:'true',align:'left'">交易成功金额</th>
							<th data-options="field:'APP_NAME',width:120,sortable:'true',align:'left'">应用名称</th>
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
var toolbar = [{
				id : 'search_btn',
				text : '查询',
				iconCls : 'icon-search'
			},'-',{
				id : 'clear_btn',
				text : '清除',
				iconCls : 'icon-remove'
			},{
				id : 'clear_notify_btn',
				text : '通知次数条件清零',
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
	var search_begin_date = $("#search_begin_date").datebox("getValue");
	var search_end_date = $("#search_end_date").datebox("getValue");
	if (search_begin_date == '' && search_end_date == '') {
		param.search_begin_date=CurentTime();
		param.search_end_date=CurentTime();
  	}
}
jQuery(function($){
	$("#search_begin_date").datebox('setValue', CurentTime());
	$("#search_end_date").datebox('setValue', CurentTime());
	//定义构造查询
	tppTradeTNotify_list.buildQueryParams=function(){
		$('#dg_tppTradeTNotify').datagrid("options").queryParams={
			 'search_begin_date':$("#search_begin_date").datebox("getValue"),
			 'search_end_date':$("#search_end_date").datebox("getValue"),
			 'search_biz_sys_no':$('#search_biz_sys_no').combobox("getValue"),
			 'search_biz_type':$('#search_biz_type').combobox("getValue"),
			 'search_notify_status':$('#search_notify_status').combobox("getValue"),
			 'search_biz_flow':$("input[name='search_biz_flow']").val(),
			 'search_trade_flow':$("input[name='search_trade_flow']").val(),
			 'search_op_mode':$('#search_op_mode').combobox("getValue"),
		}
	}

	//重新按照条件刷新查询内容
	$('#search_btn').click(function(){
		
		var startTime= $("input[name='search_begin_date']").val();
		 var endTime= $("input[name='search_end_date']").val();
		 
		 if(startTime!=null && startTime!='' && endTime!=null && endTime!=''){
				var start=new Number(startTime.replace("-", "").replace("-", "")); 	
				var end=new Number(endTime.replace("-", "").replace("-", ""));
				 if(end<start){
						$.messager.alert("提示","查询开始时间不能大于结束时间！");
				 	return false;
				  }
		} 
		 
		tppTradeTNotify_list.buildQueryParams();
		$('#dg_tppTradeTNotify').datagrid('load');
	});
	
    //重置查询条件并刷新内容
	$('#clear_btn').click(function(){
		//清空查询条件
			$("#search_begin_date").datebox('setValue', CurentTime());
			$("#search_end_date").datebox('setValue', CurentTime());
			$('#search_biz_sys_no').combobox('setValue','');
			$('#search_biz_type').combobox('setValue','');
			$('#search_notify_status').combobox('setValue','');
			$("input[name='search_biz_flow']").val("");
			$("input[name='search_trade_flow']").val("");
			$('#search_op_mode').combobox('setValue','');
			
		tppTradeTNotify_list.buildQueryParams();
		$('#dg_tppTradeTNotify').datagrid('load');
	});
	
	
	//新增
	$('#clear_notify_btn').click(function(){
		var add_form_id ='#clearNotifyForm';
		
		$('<div id="dialog_holder"></div>').dialog({
		    title: '通知次数清零',
		    width: 800,
		    height: 400,
		    href : '${path}/trade/notify/clearNotifyAllUI',
		    modal: true,
		    method: "POST",
		 
			onClose: function(){
				$(this).dialog("destroy");
			},
		    buttons: [{
		    	text: "确  定",
		    	handler: function(e){
		    		$('#clearNotifyForm').form({   
						 url:'${path}/trade/notify/clearNotifyAll',    
							     onSubmit: function(){
							    	 $.messager.progress({
							 	 			text:'加载中...'							 	 		 
							 	 			});    
							     },    
							     success:function(data){
							       //do some
							       	var obj = eval("("+ data+ ")");
							    	if(obj.valmsg!=null){
							       		$.messager.alert('提示',obj.valmsg);	
							       	 $.messager.progress('close');
										tppTradeTNotify_list.buildQueryParams();
										$('#dg_tppTradeTNotify').datagrid('load');
										$('#dialog_holder').dialog('close');
							       	}else{
							       		 $.messager.progress('close');
										tppTradeTNotify_list.buildQueryParams();
										$('#dg_tppTradeTNotify').datagrid('load');
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
							var beginDate = $("#clear_begin_date").datebox("getValue");
							var endDate = $("#clear_end_date").datebox("getValue");
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
	
	//更新
	tppTradeTNotify_list.updateFormSubmit = function(pkid){
	    var edit_form_id ='#editTppTradeTNotifyFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: 'TppTradeTNotify',
		    width: 800,
		    height: 400,
		    href: 'editTppTradeTNotifyPage.action',
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
		   				$('#editTppTradeTNotifyFrom').form({   
						 url:'editTppTradeTNotifyAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppTradeTNotify_list.buildQueryParams();
									$('#dg_tppTradeTNotify').datagrid('load');
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
		     			$('#delTppTradeTNotifyFrom').form({   
						 url:'delTppTradeTNotifyAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppTradeTNotify_list.buildQueryParams();
									$('#dg_tppTradeTNotify').datagrid('load');
									$('#dialog_holder').dialog('close');
							     }
						});// 
					    $('#delTppTradeTNotifyFrom').submit();
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
	tppTradeTNotify_list.detailFormSubmit = function(pkid){
		$('<div id="dialog_holder"></div>').dialog({
		    title: '详细TppTradeTNotify',
		    width: 800,
		    height: 400,
		    href: 'detailTppTradeTNotifyPage.action',
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
function loadQueryParam(){
	$('#dg_tppTradeTNotify').datagrid("options").queryParams={
		 'search_begin_date':$("#search_begin_date").datebox("getValue"),
		 'search_end_date':$("#search_end_date").datebox("getValue"),
		 'search_biz_sys_no':$('#search_biz_sys_no').combobox("getValue"),
		 'search_biz_type':$('#search_biz_type').combobox("getValue"),
		 'search_notify_status':$('#search_notify_status').combobox("getValue"),
		 'search_biz_flow':$("input[name='search_biz_flow']").val(),
		 'search_trade_flow':$("input[name='search_trade_flow']").val(),
		 'search_op_mode':$('#search_op_mode').combobox("getValue"),
	}
  	
}
function clearNotify(id){
	 $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {
		  if (data) {
	  $.ajax({
         type: 'GET',
         url: '${path}/trade/notify/clearNotify/'+id,
         dataType: "json",
         success:function(data){
        	 $('#dg_tppTradeTNotify').datagrid("options").queryParams={
        		 'search_begin_date':$("#search_begin_date").datebox("getValue"),
        		 'search_end_date':$("#search_end_date").datebox("getValue"),
        		 'search_biz_sys_no':$('#search_biz_sys_no').combobox("getValue"),
        		 'search_biz_type':$('#search_biz_type').combobox("getValue"),
        		 'search_notify_status':$('#search_notify_status').combobox("getValue"),
        		 'search_biz_flow':$("input[name='search_biz_flow']").val(),
        		 'search_trade_flow':$("input[name='search_trade_flow']").val(),
        		 'search_op_mode':$('#search_op_mode').combobox("getValue"),
        	};	 
       	  $('#dg_tppTradeTNotify').datagrid('load');  
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