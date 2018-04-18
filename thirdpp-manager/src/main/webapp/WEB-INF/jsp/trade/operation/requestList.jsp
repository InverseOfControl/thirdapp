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
								<input id="bizSysNo" class="easyui-combobox" name="bizSysNo" data-options="
									editable:false,valueField: 'value',
									textField: 'name',
									//url是下拉框请求显示的数据
									url:'${path }/enumset/bizSysNoList' " style="width:100px;"/>  
							</td>
							<td style="text-align:right">业务类型：</td>
							<td>
								<input id="bizType" class="easyui-combobox" name="bizType" data-options="
								editable:false,valueField: 'value',
								textField: 'name',
								//url是下拉框请求显示的数据
								url:'${path }/enumset/getBizTypeForRequest' " style="width:100px;"/>  
							</td>
							
							 <td style="text-align:right">创建日期：</td>
							<td>
								<input type="text" id="beginDate" name="beginDate"  value="${beginDate}" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
								－
								<input type="text" id="endDate" name="endDate"  value="${endDate}" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
							</td>
							
							 
							
						</tr>
						<tr>
							<td style="text-align:right">状态：</td>
							<td>
								<input id="status" class="easyui-combobox" name="status" data-options="
									editable:false,
									valueField: 'type',
									textField: 'text',
									data:[{'type':'','text':'请选择'},{'type':'0','text':'处理中'},{'type':'1','text':'操作成功'},{'type':'2','text':'操作失败'}]
									  " style="width:100px;"/>  
							</td>
							<td style="text-align:right">信息类别：</td>
							<td>
								<input id="infoCategory" class="easyui-combobox" name="infoCategory" data-options="
									editable:false,valueField: 'value',
									textField: 'name',
									//url是下拉框请求显示的数据
									url:'${path }/enumset/getInfoCategoryList' " style="width:100px;"/>  
							</td>
							
							<td style="text-align:right">客户信息：</td>
							<td>
								<input type="text" id="customer_info" name="customer_info" class="grayTips" value="姓名/绑定银行卡号/手机号" style="width:200px;"/>
							</td>
						</tr>
						<tr>	
							<td style="text-align:right">业务流水号：</td>
							<td>
								<input type="text" id="bizFlow" name="bizFlow"  value="${bizFlow}" style="width:100px;"/>
							</td>
							<td style="text-align:right">交易流水号：</td>
							<td>
								<input type="text" id="transferFlow" name="transferFlow"  value="${transferFlow}" style="width:100px;"/>
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
					<input type="hidden" id="export_payer_name" name="export_payer_name"/>
					<input type="hidden" id="export_payer_bank_card_no" name="export_payer_bank_card_no"/>
					<input type="hidden" id="export_payer_no" name="export_payer_no"/>
					<input type="hidden" id="export_date_type" name="export_date_type"/>
					<input type="hidden" id="export_biz_type" name="export_biz_type"/>
					<input type="hidden" id="export_info_category" name="export_info_category"/>
					<input type="hidden" id="export_trade_status" name="export_trade_status"/>
					<input type="hidden" id="export_payer_mobile" name="export_payer_mobile"/>
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
					data-options="rownumbers:true,pageSize:20,singleSelect:true,collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/trade/operation/requestListData',method:'post',toolbar:toolbar,fit:'true',
					onLoadSuccess:function(){loadQueryParam();$(this).datagrid('fixRownumber');},
					onBeforeLoad:function(param){loadBefore(param)},onClickRow: function (rowIndex, rowData) {$(this).datagrid('unselectRow', rowIndex);}">
					<thead>
						<tr>
							<th data-options="field:'DETAIL',width:70,align:'center'">详细信息</th>
							<th data-options="field:'APP_REQUEST',width:70,align:'center'">渠道请求</th>
							<th data-options="field:'REQ_ID',width:130,sortable:'true',align:'left'">请求ID</th>
							<th data-options="field:'PAY_SYS_NAME',width:120,sortable:'true',align:'left'">第三方支付平台</th>
							<th data-options="field:'BIZ_SYS_ACCOUNT_NO',width:120,sortable:'true',align:'left'">业务系统客户编号</th>
							<th data-options="field:'ZENGDAI_ACCOUNT_NO',width:120,sortable:'true',align:'left'">证大客户编号</th>
							<th data-options="field:'BIZ_SYS_NAME',width:120,sortable:'true',align:'left'">业务系统名称</th>
							<th data-options="field:'REAL_NAME',width:120,sortable:'true',align:'left'">客户姓名</th>
							<th data-options="field:'GENDER',width:120,sortable:'true',align:'left'">性别</th>
							<th data-options="field:'NATION',width:120,sortable:'true',align:'left'">民族</th>
							<th data-options="field:'MOBILE',width:120,sortable:'true',align:'left'">客户手机号码</th>
							<th data-options="field:'RESERVE_MOBILE',width:120,sortable:'true',align:'left'">客户预留手机号</th>
							<th data-options="field:'BANK_CARD_NO',width:120,sortable:'true',align:'left'">绑定银行卡号</th>
							<th data-options="field:'BANK_CARD_TYPE',width:120,sortable:'true',align:'left'">银行卡类型 </th>
							<th data-options="field:'BANK_CODE',width:120,sortable:'true',align:'left'">银行编码</th>
							<th data-options="field:'BANK_NAME',width:120,sortable:'true',align:'left'">银行名称</th>
							<th data-options="field:'BIZ_FLOW',width:120,sortable:'true',align:'left'">业务流水号</th>
							<th data-options="field:'STATUS',width:120,sortable:'true',align:'left'">状态</th>
							<th data-options="field:'ID_TYPE',width:120,sortable:'true',align:'left'">证件类型</th>
							<th data-options="field:'ID_NO',width:120,sortable:'true',align:'left'">证件号</th>
							<th data-options="field:'IP',width:120,sortable:'true',align:'left'">请求IP</th>
							<th data-options="field:'CREATER',width:120,sortable:'true',align:'left'">创建人</th>
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'left'">创建时间</th>
							<th data-options="field:'UPDATE_TIME',width:120,sortable:'true',align:'left'">更新时间</th>
							<th data-options="field:'IS_SYNC',width:120,sortable:'true',align:'left'">是否同步</th>
							<th data-options="field:'OPEN_BANK_CODE',width:120,sortable:'true',align:'left'">开户行编码</th>
							<th data-options="field:'OPEN_BANK_NAME',width:120,sortable:'true',align:'left'">开户行名称</th>
							<th data-options="field:'TRANSFER_FLOW',width:120,sortable:'true',align:'left'">交易流水号</th>
							<th data-options="field:'PAY_TRANS_FLOW',width:120,sortable:'true',align:'left'">第三方支付平台流水号</th>
							<th data-options="field:'RESP_TIME',width:120,sortable:'true',align:'left'">返回时间</th>
							<th data-options="field:'RESP_INFO',width:120,sortable:'true',align:'left'">返回信息</th>
							<th data-options="field:'BIZ_TYPE_NO',width:120,sortable:'true',align:'left'">业务类型</th>
							<th data-options="field:'RESP_CODE',width:120,sortable:'true',align:'left'">返回码</th>
							<th data-options="field:'THIRD_ACCOUNT_NO',width:120,sortable:'true',align:'left'">客户第三方账户编号 </th>
							<th data-options="field:'INFO_CATEGORY_NAME',width:120,sortable:'true',align:'left'">信息类别</th>
							<th data-options="field:'MAC',width:120,sortable:'true',align:'left'">移动设备信息</th>
				 
						</tr>
					</thead>
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
} ];
	
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
function viewInfoDetail(id){
	$('<div id="dialog_holder"></div>').dialog({
	    title: '详细信息',
	    width: 800,
	    height: 400,
	    href:  '${path}/trade/operation/requestInfo',
	    modal: true,
	    method: "POST",
	    params:{id:id},
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
	var search_begin_date = $("#beginDate").datebox("getValue");
	var search_end_date = $("#endDate").datebox("getValue");
	if (search_begin_date == '' && search_end_date == '') {
		param.beginDate=CurentTime();
		param.endDate=CurentTime();
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
	$("#beginDate").datebox('setValue', CurentTime());
	$("#endDate").datebox('setValue', CurentTime());
	//定义构造查询
	tppTradeTCollectInfo_list.buildQueryParams=function(){
		var customer_info = $("input[name='customer_info']").val();
		if (customer_info=='姓名/绑定银行卡号/手机号') {
			customer_info='';
		}
	    $('#dg_tppTradeTCollectInfo').datagrid("options").queryParams={
	    	'bizSysNo':$("input[name='bizSysNo']").val(),
	    	'bizType':$('#bizType').combobox("getValue"),
	    	'status':$('#status').combobox("getValue"),
	    	'beginDate':$("#beginDate").datebox("getValue"),
			'endDate':$("#endDate").datebox("getValue"),
			'transferFlow':$("input[name='transferFlow']").val(),
			'bizFlow':$("input[name='bizFlow']").val(),
			'infoCategory':$("input[name='infoCategory']").val(),
			'customer_info':customer_info,
			
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
		$('#bizSysNo').combobox('setValue','');
		$('#bizType').combobox('setValue','');
		$("#beginDate").datebox('setValue', CurentTime());
		$("#endDate").datebox('setValue', CurentTime());
		$('#status').combobox('setValue','');
		$("input[name='transferFlow']").val("");
		$("input[name='bizFlow']").val("");
		$("input[name='customer_info']").val("姓名/绑定银行卡号/手机号");
		$('#infoCategory').combobox('setValue','');
		tppTradeTCollectInfo_list.buildQueryParams();
		$('#dg_tppTradeTCollectInfo').datagrid('load');
		inputTipText(); 
	});
    
	inputTipText(); 
});
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
	    title: '通知查询列表',
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
function loadQueryParam(){
	 
	var customer_info = $("input[name='customer_info']").val();
	if (customer_info=='姓名/绑定银行卡号/手机号') {
		customer_info='';
	}
  	$('#dg_tppTradeTCollectInfo').datagrid("options").queryParams={
    	'bizSysNo':$("input[name='bizSysNo']").val(),
    	'bizType':$('#bizType').combobox("getValue"),
    	'status':$('#status').combobox("getValue"),
    	'beginDate':$("#beginDate").datebox("getValue"),
		'endDate':$("#endDate").datebox("getValue"),
		'transferFlow':$("input[name='transferFlow']").val(),
		'bizFlow':$("input[name='bizFlow']").val(),
		'infoCategory':$("input[name='infoCategory']").val(),
		'customer_info':customer_info,
	};
	 
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
