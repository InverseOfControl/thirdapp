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
									<input id="search_third_type_no" class="easyui-combobox" name="search_third_type_no" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/dictionary/3' " style="width:100px;"/>
								</td>
								<td style="text-align:right">与业务系统对账状态：</td>
								<td>
									<input id="search_bizsys_account_status" class="easyui-combobox" name="search_bizsys_account_status" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/getAccountBizsysStatusList' " style="width:100px;"/> 
								</td>
								<td style="text-align:right">对账日期：</td>
								<td>
									<input type="text" id="search_account_begin_date" name="search_account_begin_date"  value="${search_account_begin_date}" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
									－
									<input type="text" id="search_account_end_date" name="search_account_end_date"  value="${search_account_end_date}" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
								</td>
								
						</tr>
						<tr>
								<td style="text-align:right">与第三方对账状态：</td>
								<td>
									<input id="search_account_status" class="easyui-combobox" name="search_account_status" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/getAccountThirdStatusList' " style="width:100px;"/> 
								</td>
								<td style="text-align:right">商户号：</td>
								<td>
									<input type="text" id="search_merchant_no" name="search_merchant_no" class="easyui-textbox" value="${search_merchant_no}" style="width:100px;"/>
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
								<%-- <td style="text-align:right">第三方交易流水号：</td>
								<td>
									<input type="text" id="search_third_party_trade_flow" name="search_third_party_trade_flow" class="easyui-textbox" value="${search_third_party_trade_flow}" style="width:100px;"/>
								</td> --%>
								
						</tr>
						<tr>
								<td style="text-align:right">业务系统：</td>
								<td>
									<input id="search_biz_sys_no" class="easyui-combobox" name="search_biz_sys_no" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/bizSysNoList' " style="width:100px;"/>  
								</td>
								<td style="text-align:right">交易流水号：</td>
								<td>
									<input type="text" id="search_trade_flow" name="search_trade_flow" class="easyui-textbox" value="${search_trade_flow}" style="width:100px;"/>
								</td>
								<td style="text-align:right">业务流水号：</td>
								<td>
									<input type="text" id="search_biz_flow" name="search_biz_flow" class="easyui-textbox" value="${search_biz_flow}" style="width:100px;"/>
								</td>
						</tr>
					</table>
				</form>
			</div>
			<!--搜索栏结束-->
			
			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding:0px;margin: 0px;" data-options="region:'center'">
				<table  id="dg_tppAccountInfo" class="easyui-datagrid" 
					data-options="rownumbers:true,pageSize:20,singleSelect:true,collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/account/accountInfo/listData',method:'post',toolbar:toolbar,fit:'true',onBeforeLoad:function(param){loadBefore(param)}">
					<thead>
						<tr>
						 	<!-- <th data-options="field:'DETAIL',width:50,align:'center'">detail</th>
							<th data-options="field:'EDIT',width:50,align:'center'">edit</th>
							<th data-options="field:'ID',width:120,sortable:'true',align:'left'">主键</th> -->
							<!-- <th data-options="field:'THIRD_TYPE_NO',width:120,sortable:'true',align:'left'">对账渠道编码</th> -->
							<th data-options="field:'THIRD_PAY_NAME',width:120,sortable:'true',align:'left'">第三方支付平台</th>
							<th data-options="field:'MERCHANT_NO',width:120,sortable:'true',align:'left'">商户号</th>
							<th data-options="field:'BIZ_TYPE_STR',width:120,sortable:'true',align:'left'">业务类型</th>
							<th data-options="field:'BIZ_TYPE',width:120,sortable:'true',align:'left'">业务类型编码</th>
							<th data-options="field:'ACCOUNT_DAY',width:120,sortable:'true',align:'left'">对账日期</th>
							<th data-options="field:'ACCOUNT_STATUS',width:120,sortable:'true',align:'left'">与第三方对账状态</th>
							<th data-options="field:'FAILED_REASON',width:600,sortable:'true',align:'left'">对账失败原因</th>
							<th data-options="field:'BIZ_SYS_NAME',width:120,sortable:'true',align:'left'">业务系统</th>
							<th data-options="field:'BIZSYS_ACCOUNT_STATUS',width:120,sortable:'true',align:'left'">与业务系统对账状态</th>
							<th data-options="field:'TASK_ID',width:150,sortable:'true',align:'left'">任务ID</th>
							<th data-options="field:'ORIGINAL_AMOUNT',width:120,sortable:'true',align:'left'">原始金额</th>
							<th data-options="field:'TASK_AMOUNT',width:120,sortable:'true',align:'left'">任务金额</th>
							<th data-options="field:'AMOUNT',width:120,sortable:'true',align:'left'">金额</th>
							<th data-options="field:'TRADE_FLOW',width:150,sortable:'true',align:'left'">交易流水号</th>
							<th data-options="field:'BIZ_FLOW',width:150,sortable:'true',align:'left'">业务流水号</th>
							<th data-options="field:'THIRD_PARTY_TRADE_FLOW',width:150,sortable:'true',align:'left'">第三方交易流水号</th>
							<th data-options="field:'THIRD_PARTY_ACCOUNT_REQ_ID',width:150,sortable:'true',align:'left'">第三方对账请求ID</th>
							<th data-options="field:'BIZSYS_ACCOUNT_REQ_ID',width:150,sortable:'true',align:'left'">业务系统对账请求ID</th>
							<th data-options="field:'IS_SEPARATE',width:120,sortable:'true',align:'left'">是否拆单 </th>
							<th data-options="field:'SEPARATE_COUNT',width:120,sortable:'true',align:'left'">拆单数</th>
							<th data-options="field:'TRADE_TIME',width:120,sortable:'true',align:'left'">商户清算时间</th>
							<th data-options="field:'CURRENT_INDEX',width:120,sortable:'true',align:'left'">当前索引值</th>
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'left'">创建时间</th>
						</tr>
					</thead>
				</table>
			</div>
			<!--搜索栏结果end-->

<script>
var tppAccountInfo_list ={};
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
	tppAccountInfo_list.buildQueryParams=function(){
		/* $("#search_biz_types").val($("#search_biz_type").combotree("getValues")); */
		$('#dg_tppAccountInfo').datagrid("options").queryParams={
				 'search_third_type_no':$("#search_third_type_no").combobox("getValue"),
				 'search_merchant_no':$("input[name='search_merchant_no']").val(),
				/*  'search_biz_type':$("input[name='search_biz_types']").val(), */
				 'search_biz_type':$('#search_biz_type').combobox("getValue"),
				 'search_bizsys_account_status':$('#search_bizsys_account_status').combobox("getValue"),
				 'search_account_begin_date':$("#search_account_begin_date").datebox("getValue"),
				 'search_account_end_date':$("#search_account_end_date").datebox("getValue"),
				 'search_account_status':$('#search_account_status').combobox("getValue"),
				 'search_biz_sys_no':$('#search_biz_sys_no').combobox("getValue"),
				 'search_trade_flow':$("input[name='search_trade_flow']").val(),
				 'search_biz_flow':$("input[name='search_biz_flow']").val(),
		}
	}
	tppAccountInfo_list.buildQueryParams();
	//重新按照条件刷新查询内容
	$('#search_btn').click(function(){
		var flag = checkDate();
		if (!flag) {
			return ;
		}
		tppAccountInfo_list.buildQueryParams();
		$('#dg_tppAccountInfo').datagrid('load');
	});
	
    //重置查询条件并刷新内容
	$('#clear_btn').click(function(){
		//清空查询条件
			$("input[name='search_merchant_no']").val("");
			$("input[name='search_trade_flow']").val("");
			$("input[name='search_biz_flow']").val("");
			$('#search_account_status').combobox('setValue','');
			$('#search_bizsys_account_status').combobox('setValue','');
			$('#search_biz_sys_no').combobox('setValue','');
			$('#search_third_type_no').combobox('setValue','');
			/* $('#search_biz_type').combotree('setValues', []);	
			$('#search_biz_type').combotree('setText', '请选择');	 */
			$('#search_biz_type').combobox('setValue','');
			$('#search_biz_type').combobox('setText', '请选择');	
			$("#search_account_begin_date").datebox('setValue', CurentTime());
			$("#search_account_end_date").datebox('setValue', CurentTime());
		tppAccountInfo_list.buildQueryParams();
		$('#dg_tppAccountInfo').datagrid('load');
	});
	
	
	//新增
	$('#add_btn').click(function(){
		var add_form_id ='#addTppAccountInfoFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: 'TPP_ACCOUNT_INFO',
		    width: 800,
		    height: 500,
		    href: 'addTppAccountInfoPage.action',
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
		    		$('#addTppAccountInfoFrom').form({   
						 url:'addTppAccountInfoAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppAccountInfo_list.buildQueryParams();
									$('#dg_tppAccountInfo').datagrid('load');
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
	tppAccountInfo_list.updateFormSubmit = function(pkid){
	    var edit_form_id ='#editTppAccountInfoFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: 'TppAccountInfo',
		    width: 800,
		    height: 500,
		    href: 'editTppAccountInfoPage.action',
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
		   				$('#editTppAccountInfoFrom').form({   
						 url:'editTppAccountInfoAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppAccountInfo_list.buildQueryParams();
									$('#dg_tppAccountInfo').datagrid('load');
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
		     			$('#delTppAccountInfoFrom').form({   
						 url:'delTppAccountInfoAction.action',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							       //do some
							        $.messager.progress('close');
									tppAccountInfo_list.buildQueryParams();
									$('#dg_tppAccountInfo').datagrid('load');
									$('#dialog_holder').dialog('close');
							     }
						});// 
					    $('#delTppAccountInfoFrom').submit();
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
	tppAccountInfo_list.detailFormSubmit = function(pkid){
		$('<div id="dialog_holder"></div>').dialog({
		    title: '详细TppAccountInfo',
		    width: 800,
		    height: 500,
		    href: 'detailTppAccountInfoPage.action',
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