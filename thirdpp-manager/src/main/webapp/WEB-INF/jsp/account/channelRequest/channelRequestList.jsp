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
								<td style="text-align:right">请求ID：</td>
								<td>
									<input type="text" id="search_req_id" name="search_req_id" class="easyui-textbox" value="${search_req_id}" style="width:100px;"/>
								</td>
								<td style="text-align:right">配置ID：</td>
								<td>
									<input type="text" id="search_config_id" name="search_config_id" class="easyui-textbox" value="${search_config_id}" style="width:100px;"/>
								</td>
								<td style="text-align:right">对账日期：</td>
								<td>
									<input type="text" id="search_account_begin_date" name="search_account_begin_date"  value="${search_account_begin_date}" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
									－
									<input type="text" id="search_account_end_date" name="search_account_end_date"  value="${search_account_end_date}" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
								</td>
						</tr>
						<tr>
								<td style="text-align:right">商户号：</td>
								<td>
									<input type="text" id="search_merchant_no" name="search_merchant_no" class="easyui-textbox" value="${search_merchant_no}" style="width:100px;"/>
								</td>
								<%-- <td style="text-align:right">对账日期：</td>
								<td>
									<input type="text" id="search_account_day" name="search_account_day" class="easyui-textbox" value="${search_account_day}" style="width:100px;"/>
								</td> --%>
								<td style="text-align:right">第三方支付平台：</td>
								<td>
									<input id="search_third_type_no" class="easyui-combobox" name="search_third_type_no" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/dictionary/3' " style="width:100px;"/>
								</td>
								<%-- <td style="text-align:right">业务类型：</td>
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
								</td> --%>
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
						<tr>
								<td style="text-align:right">处理进程：</td>
								<td>
									<input id="search_app_name" class="easyui-combobox" name="search_app_name" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/dictionary/15' " style="width:100px;"/>
								</td>
								<td style="text-align:right">状态：</td>
								<td>
									<input id="search_status" class="easyui-combobox" name="search_status" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/getAccountChannelRequestStatusList' " style="width:100px;"/> 
								</td>
						</tr>
					</table>
				</form>
				<form id="channelHandleAccountForm" name="channelHandleAccountForm" method="post">
				</form>
			</div>
			<!--搜索栏结束-->
			
			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding:0px;margin: 0px;" data-options="region:'center'">
				<table  id="dg_tppAccountChannelRequest" class="easyui-datagrid" 
					data-options="rownumbers:true,pageSize:20,singleSelect:false,selectOnCheck: 'true',checkOnSelect:'true',collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/account/channelRequest/listData',method:'post',toolbar:toolbar,fit:'true',onClickRow: function (rowIndex, rowData) {$(this).datagrid('unselectRow', rowIndex);},onBeforeLoad:function(param){loadBefore(param)}">
					<thead>
						<tr>
						 	<!-- <th data-options="field:'DETAIL',width:50,align:'center'">detail</th>
							<th data-options="field:'EDIT',width:50,align:'center'">edit</th> -->
							<th data-options="field:'EDIT',width:60,sortable:'false',align:'center'">手工对账</th>
							<th data-options="field:'REQ_ID',width:150,sortable:'true',align:'left'">请求ID</th>
							<th data-options="field:'THIRD_PAY_NAME',width:120,sortable:'true',align:'left'">第三方支付平台</th>
							<th data-options="field:'MERCHANT_NO',width:150,sortable:'true',align:'left'">商户号</th>
							<th data-options="field:'ACCOUNT_DAY',width:120,sortable:'true',align:'left'">对账日期</th>
							<th data-options="field:'CONFIG_ID',width:120,sortable:'true',align:'left'">配置ID</th>
							<th data-options="field:'STATUS',width:120,sortable:'true',align:'left'">状态</th>
							<th data-options="field:'FAILED_REASON',width:600,sortable:'true',align:'left'">失败原因</th>
							<th data-options="field:'HANDLE_ACCOUNT_STATUS',width:120,sortable:'true',align:'left'">手工对账状态</th>
							<th data-options="field:'BIZ_TYPE_STR',width:120,sortable:'true',align:'left'">业务类型</th>
							<th data-options="field:'BIZ_TYPE',width:120,sortable:'true',align:'left'">业务类型编码</th>
							<th data-options="field:'APP_NAME',width:250,sortable:'true',align:'left'">处理进程</th>
							<th data-options="field:'DOWNLOAD_START_TIME',width:120,sortable:'true',align:'left'">下载文件开始时间</th>
							<th data-options="field:'DOWNLOAD_FAILED_TIMES',width:120,sortable:'true',align:'left'">下载文件失败次数</th>
							<th data-options="field:'DOWNLOAD_FILE_NAME',width:120,sortable:'true',align:'left'">下载文件的名称</th>
							<th data-options="field:'DOWNLOAD_FILE_SIZE',width:120,sortable:'true',align:'left'">下载文件大小(KB)</th>
							<th data-options="field:'DOWNLOAD_FILE_PATH',width:120,sortable:'true',align:'left'">下载文件的存放路径</th>
							<th data-options="field:'DOWNLOAD_END_TIME',width:120,sortable:'true',align:'left'">下载文件结束时间</th>
							<th data-options="field:'TOTAL_COUNT_AMOUNT_DESC',width:120,sortable:'true',align:'left'">汇总信息</th>
							<th data-options="field:'INSERT_START_TIME',width:120,sortable:'true',align:'left'">入明细表开始时间</th>
							<th data-options="field:'INSERT_FAILED_TIMES',width:120,sortable:'true',align:'left'">入明细表失败次数</th>
							<th data-options="field:'INSERT_END_TIME',width:120,sortable:'true',align:'left'">入明细表结束时间</th>
							<th data-options="field:'ACCOUNT_FAILED_TIMES',width:120,sortable:'true',align:'left'">对账失败次数</th>
							<th data-options="field:'BACKUP_START_TIME',width:120,sortable:'true',align:'left'">备份开始时间</th>
							<th data-options="field:'BACKUP_FAILED_TIMES',width:120,sortable:'true',align:'left'">备份失败次数</th>
							<th data-options="field:'BACKUP_END_TIME',width:120,sortable:'true',align:'left'">备份结束时间</th>
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'left'">创建时间</th>
							<th data-options="field:'UPDATE_TIME',width:120,sortable:'true',align:'left'">更新时间</th>
						</tr>
					</thead>
				</table>
			</div>
			<!--搜索栏结果end-->

<script>
var tppAccountChannelRequest_list ={};
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
	$("#search_account_begin_date").datebox('setValue', CurentTime());
	$("#search_account_end_date").datebox('setValue', CurentTime());
	//定义构造查询
	tppAccountChannelRequest_list.buildQueryParams=function(){
		/* $("#search_biz_types").val($("#search_biz_type").combotree("getValues")); */
		var searchAppName = '';
		if ($('#search_app_name').combobox("getText") == '请选择'){
			searchAppName = '';
		} else {
			searchAppName = $('#search_app_name').combobox("getText");
		}
		$('#dg_tppAccountChannelRequest').datagrid("options").queryParams={
				 'search_req_id':$("input[name='search_req_id']").val(),
				 'search_config_id':$("input[name='search_config_id']").val(),
				 'search_third_type_no':$('#search_third_type_no').combobox("getValue"),
				 'search_merchant_no':$("input[name='search_merchant_no']").val(),
				 /* 'search_biz_type':$("input[name='search_biz_types']").val(), */
				 'search_biz_type':$('#search_biz_type').combobox("getValue"),
				 'search_account_begin_date':$("#search_account_begin_date").datebox("getValue"),
				 'search_account_end_date':$("#search_account_end_date").datebox("getValue"),
				 'search_status':$('#search_status').combobox("getValue"),
				 'search_app_name':searchAppName,
		}
	}
	tppAccountChannelRequest_list.buildQueryParams();
	//重新按照条件刷新查询内容
	$('#search_btn').click(function(){
		tppAccountChannelRequest_list.buildQueryParams();
		$('#dg_tppAccountChannelRequest').datagrid('load');
	});
	
    //重置查询条件并刷新内容
	$('#clear_btn').click(function(){
		//清空查询条件
			$("input[name='search_req_id']").val("");
			$('#search_third_type_no').combobox('setValue','');
			$("input[name='search_merchant_no']").val("");
			/* $('#search_biz_type').combotree('setValues', []);	
			$('#search_biz_type').combotree('setText', '请选择');	 */
			$('#search_biz_type').combobox('setValue','');
			$('#search_biz_type').combobox('setText', '请选择');	
			$("#search_account_begin_date").datebox('setValue', CurentTime());
			$("#search_account_end_date").datebox('setValue', CurentTime());
			$("input[name='search_config_id']").val("");
			$('#search_status').combobox('setValue','');
			$('#search_app_name').combobox('setValue','');
		tppAccountChannelRequest_list.buildQueryParams();
		$('#dg_tppAccountChannelRequest').datagrid('load');
	});
	
	
	//新增
	$('#add_btn').click(function(){
		var add_form_id ='#addTppAccountChannelRequestFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: 'TPP_ACCOUNT_CHANNEL_REQUEST',
		    width: 800,
		    height: 500,
		    href: 'addTppAccountChannelRequestPage.action',
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
		    		$('#addTppAccountChannelRequestFrom').form({   
						 url:'addTppAccountChannelRequestAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppAccountChannelRequest_list.buildQueryParams();
									$('#dg_tppAccountChannelRequest').datagrid('load');
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
	tppAccountChannelRequest_list.updateFormSubmit = function(pkid){
	    var edit_form_id ='#editTppAccountChannelRequestFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: 'TppAccountChannelRequest',
		    width: 800,
		    height: 500,
		    href: 'editTppAccountChannelRequestPage.action',
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
		   				$('#editTppAccountChannelRequestFrom').form({   
						 url:'editTppAccountChannelRequestAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppAccountChannelRequest_list.buildQueryParams();
									$('#dg_tppAccountChannelRequest').datagrid('load');
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
		     			$('#delTppAccountChannelRequestFrom').form({   
						 url:'delTppAccountChannelRequestAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppAccountChannelRequest_list.buildQueryParams();
									$('#dg_tppAccountChannelRequest').datagrid('load');
									$('#dialog_holder').dialog('close');
							     }
						});// 
					    $('#delTppAccountChannelRequestFrom').submit();
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
	tppAccountChannelRequest_list.detailFormSubmit = function(pkid){
		$('<div id="dialog_holder"></div>').dialog({
		    title: '详细TppAccountChannelRequest',
		    width: 800,
		    height: 500,
		    href: 'detailTppAccountChannelRequestPage.action',
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
	
	tppAccountChannelRequest_list.handleAccount = function(pkid){
		$.messager.confirm('确认','您确认想要进行手工对账操作吗？',function(r){    
		    if (r){
		    	var result = true;
		    	$("#" + pkid).attr("disabled", true);
		    	$.ajax({
                      url:'${path}/account/channelRequest/handleAccount',
                      data:{"pkid":pkid},
                  	  dataType:"JSON",
					  type:"POST",
                  	  success:function(data){
                  		 if (data.flag == false){
 					    	 $.messager.alert('提示',data.msg);
 					    	 result = false;
 				       	 } else {
 				       		 $.messager.alert('提示','通道手工对账成功');
 				       	 }
 				    	 $('#dg_tppAccountChannelRequest').datagrid('load');
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