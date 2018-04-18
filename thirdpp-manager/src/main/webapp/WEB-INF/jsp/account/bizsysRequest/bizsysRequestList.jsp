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
			<div class="easyui-panel" title="查询条件" style="padding:5px;height:110px;margin: 0px;" data-options="region:'north'">
				<form id="searchForm" name="searchForm" method="post" >
					<table cellpadding="5">
						<tr>
								<td style="text-align:right">请求ID：</td>
								<td>
									<input type="text" id="search_req_id" name="search_req_id" class="easyui-textbox" value="${search_req_id}"  style="width:100px;"/>
								</td>
								<td style="text-align:right">业务系统：</td>
								<td>
									<input id="search_biz_sys_no" class="easyui-combobox" name="search_biz_sys_no" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/bizSysNoList' " style="width:100px;"/>  
								</td>
								<td style="text-align:right">对账日期：</td>
								<td>
									<input type="text" id="search_account_begin_date" name="search_account_begin_date"  value="${search_account_begin_date}" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
									－
									<input type="text" id="search_account_end_date" name="search_account_end_date"  value="${search_account_end_date}" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
								</td>
								
						</tr>
						<tr>
								
								<td style="text-align:right">状态：</td>
								<td>
									<%-- <input type="text" id="search_status" name="search_status" class="easyui-textbox" value="${search_status}"  style="width:100px;"/> --%>
									<input id="search_status" class="easyui-combobox" name="search_status" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/getAccountBizsysRequestStatusModeList' " style="width:100px;"/> 
								</td>
								<td style="text-align:right">业务类型：</td>
								<td>
									<input id="search_biz_type" name="search_biz_type"    style="width: 150px" 
											class="easyui-combotree" data-options="
												editable:false, 
											   multiple: 'true',
											  url:'${path}/enumset/accountBizTypeList/combobox',
										 	  onLoadSuccess: function () { 									 	  					 
										 	  						$('#search_biz_type').combotree('setText', '请选择');	
										 	  					}
											     "/>
									<input type="hidden" id="search_biz_types" name="search_biz_types"/>
								</td>
						</tr>
					</table>
				</form>
			</div>
			<!--搜索栏结束-->
			
			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding:0px;margin: 0px;" data-options="region:'center'">
				<table  id="dg_tppAccountBizsysRequest" class="easyui-datagrid" 
					data-options="rownumbers:true,singleSelect:true,collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/account/bizsysRequest/listData',method:'post',toolbar:toolbar,fit:'true',onBeforeLoad:function(param){loadBefore(param)}">
					<thead>
						<tr>
						 	<!-- <th data-options="field:'DETAIL',width:50,align:'center'">detail</th>
							<th data-options="field:'EDIT',width:50,align:'center'">edit</th> -->
							<th data-options="field:'EDIT',width:60,sortable:'false',align:'center'">手工对账</th>
							<th data-options="field:'REQ_ID',width:150,sortable:'true',align:'left'">请求ID</th>
							<th data-options="field:'BIZ_SYS_NAME',width:120,sortable:'true',align:'left'">业务系统</th>
							<th data-options="field:'CONFIG_ID',width:120,sortable:'true',align:'left'">配置ID</th>
							<th data-options="field:'BIZ_TYPE_STR',width:250,sortable:'true',align:'left'">业务类型</th>
							<th data-options="field:'BIZ_TYPE',width:250,sortable:'true',align:'left'">业务类型编码</th>
							<th data-options="field:'ACCOUNT_DAY',width:120,sortable:'true',align:'left'">对账日期</th>
							<th data-options="field:'STATUS',width:120,sortable:'true',align:'left'">状态</th>
							<th data-options="field:'APP_NAME',width:250,sortable:'true',align:'left'">处理进程</th>
							<th data-options="field:'LOCALIZE_START_TIME',width:150,sortable:'true',align:'left'">本地存储开始时间</th>
							<th data-options="field:'LOCALIZE_PATH',width:200,sortable:'true',align:'left'">本地存储对账文件目录</th>
							<th data-options="field:'LOCALIZE_FILE_NAME',width:150,sortable:'true',align:'left'">本地存储对账文件名称</th>
							<th data-options="field:'LOCALIZE_END_TIME',width:150,sortable:'true',align:'left'">本地存储结束时间</th>
							<th data-options="field:'LOCALIZE_FAILED_TIMES',width:120,sortable:'true',align:'left'">本地存储失败次数</th>
							<th data-options="field:'PUSH_START_TIME',width:120,sortable:'true',align:'left'">推送开始时间</th>
							<th data-options="field:'PUSH_FILE_NAME',width:120,sortable:'true',align:'left'">推送的对账文件名称</th>
							<th data-options="field:'PUSH_FILE_SIZE',width:150,sortable:'true',align:'left'">推送的对账文件大小(KB)</th>
							<th data-options="field:'PUSH_FILE_PATH',width:200,sortable:'true',align:'left'">对账文件推送目录</th>
							<th data-options="field:'PUSH_END_TIME',width:120,sortable:'true',align:'left'">推送结束时间</th>
							<th data-options="field:'PUSH_FAILED_TIMES',width:120,sortable:'true',align:'left'">推送失败的次数</th>
							<th data-options="field:'FAILED_REASON',width:600,sortable:'true',align:'left'">失败原因</th>
							<!-- <th data-options="field:'SPARE1',width:120,sortable:'true',align:'left'">预留字段</th>
							<th data-options="field:'SPARE2',width:120,sortable:'true',align:'left'">预留字段</th> -->
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'left'">创建时间</th>
							<th data-options="field:'UPDATE_TIME',width:120,sortable:'true',align:'left'">更新时间</th>
						</tr>
					</thead>
				</table>
			</div>
			<!--搜索栏结果end-->

<script>
var tppAccountBizsysRequest_list ={};
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
	tppAccountBizsysRequest_list.buildQueryParams=function(){
		$("#search_biz_types").val($("#search_biz_type").combotree("getValues"));
		$('#dg_tppAccountBizsysRequest').datagrid("options").queryParams={
				 'search_req_id':$("input[name='search_req_id']").val(),
				 'search_biz_sys_no':$('#search_biz_sys_no').combobox("getValue"),
				 'search_status':$('#search_status').combobox("getValue"),
				 'search_biz_type':$("input[name='search_biz_types']").val(),
				 'search_account_begin_date':$("#search_account_begin_date").datebox("getValue"),
				 'search_account_end_date':$("#search_account_end_date").datebox("getValue"),
		}
	}
	tppAccountBizsysRequest_list.buildQueryParams();
	//重新按照条件刷新查询内容
	$('#search_btn').click(function(){
		var flag = checkDate();
		if (!flag) {
			return ;
		}
		tppAccountBizsysRequest_list.buildQueryParams();
		$('#dg_tppAccountBizsysRequest').datagrid('load');
	});
	
    //重置查询条件并刷新内容
	$('#clear_btn').click(function(){
		//清空查询条件
			$("input[name='search_req_id']").val("");
			$('#search_biz_sys_no').combobox('setValue','');
			$('#search_status').combobox('setValue','');
			$('#search_biz_type').combotree('setValues', []);	
			$('#search_biz_type').combotree('setText', '请选择');	
			$("#search_account_begin_date").datebox('setValue', CurentTime());
			$("#search_account_end_date").datebox('setValue', CurentTime());
		tppAccountBizsysRequest_list.buildQueryParams();
		$('#dg_tppAccountBizsysRequest').datagrid('load');
	});
	
	
	//新增
	$('#add_btn').click(function(){
		var add_form_id ='#addTppAccountBizsysRequestFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: 'TPP_ACCOUNT_BIZSYS_REQUEST',
		    width: 800,
		    height: 500,
		    href: 'addTppAccountBizsysRequestPage.action',
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
		    		$('#addTppAccountBizsysRequestFrom').form({   
						 url:'addTppAccountBizsysRequestAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppAccountBizsysRequest_list.buildQueryParams();
									$('#dg_tppAccountBizsysRequest').datagrid('load');
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
	tppAccountBizsysRequest_list.updateFormSubmit = function(pkid){
	    var edit_form_id ='#editTppAccountBizsysRequestFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: 'TppAccountBizsysRequest',
		    width: 800,
		    height: 500,
		    href: 'editTppAccountBizsysRequestPage.action',
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
		   				$('#editTppAccountBizsysRequestFrom').form({   
						 url:'editTppAccountBizsysRequestAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppAccountBizsysRequest_list.buildQueryParams();
									$('#dg_tppAccountBizsysRequest').datagrid('load');
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
		     			$('#delTppAccountBizsysRequestFrom').form({   
						 url:'delTppAccountBizsysRequestAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppAccountBizsysRequest_list.buildQueryParams();
									$('#dg_tppAccountBizsysRequest').datagrid('load');
									$('#dialog_holder').dialog('close');
							     }
						});// 
					    $('#delTppAccountBizsysRequestFrom').submit();
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
	tppAccountBizsysRequest_list.detailFormSubmit = function(pkid){
		$('<div id="dialog_holder"></div>').dialog({
		    title: '详细TppAccountBizsysRequest',
		    width: 800,
		    height: 500,
		    href: 'detailTppAccountBizsysRequestPage.action',
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
	
	tppAccountBizsysRequest_list.handleAccount = function(pkid){
		$.messager.confirm('确认','您确认想要进行手工对账操作吗？',function(r){    
		    if (r){ 
		    	var result = true;
		    	$("#" + pkid).attr("disabled", true);
		    	$.ajax({
                      url:'${path}/account/bizsysRequest/handleAccount',
                      data:{"pkid":pkid},
                  	  dataType:"JSON",
					  type:"POST",
                  	  success:function(data){
                  		 if (data.flag == false){
 					    	 $.messager.alert('提示',data.msg);
 					    	 result = false;
 				       	 } else {
 				       		 $.messager.alert('提示','业务系统手工对账成功');   
 				       	 }
 				    	 $('#dg_tppAccountBizsysRequest').datagrid('load');
					  },
					  error:function(data){
						  result = false;
						  $.messager.alert('提示', '请求超时');
					  }
                });
		    	if (!result) {
		    		$("#" + pkid).attr("disabled", false);
		    	}
		    }    
		}); 
	}
});
function checkDate(){
	var beginDate = $("#search_account_begin_date").datebox("getValue");
	var endDate = $("#search_account_end_date").datebox("getValue");
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
		/* if (day > 7){
			$.messager.alert('提示','请查询七日内数据');
			return false;
		} */
	}
	return true;
}
Date.prototype.diff = function(date){
  return (this.getTime() - date.getTime())/(24 * 60 * 60 * 1000);
}
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