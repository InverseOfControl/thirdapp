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
								<td style="text-align:right">信息类别：</td>
								<td>
									<input id="search_info_category" class="easyui-combobox" name="search_info_category" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/getInfoCategoryList' " style="width:200px;"/>  
								</td>
								
								<td style="text-align:right">银行卡号：</td> 
								<td>
									<input type="text" id="search_bank_card_no" name="search_bank_card_no"  class="easyui-textbox"
									style="width: 150px;" data-options="required:true" />
								</td>
								
								<td style="text-align:right">账号：</td> 
								<td>
									<input type="text" id="search_account_no" name="search_account_no"  class="easyui-textbox"
									style="width: 150px;" data-options="required:true" />
								</td>
								
						</tr>
						
						<tr>
							<td style="text-align:right">业务系统：</td>
								<td>
									<input id="search_biz_sys_no" class="easyui-combobox" name="search_biz_sys_no" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/bizSysNoList' " style="width:200px;"/>  
								</td>
						
								<td style="text-align:right">状态：</td>
								<td>
									<input id="search_status" class="easyui-combobox" name="search_status" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/payWhiteListStatus' " style="width:100px;"/>  
								</td>
								
								<td style="text-align:right">查询日期：</td>
								<td>
									<input type="text" id="search_begin_date" name="search_begin_date"   class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
									－
									<input type="text" id="search_end_date" name="search_end_date" class="easyui-datebox" style="width:100px;" data-options="required:false,editable:false" />
								</td>
						</tr>
					</table>
				</form>
			</div>
			<!--搜索栏结束-->
			
			<!--搜索结果开始-->
			
				<!--搜索结果开始-->
			<div class="easyui-panel" style="padding:0px;margin: 0px;" data-options="region:'center'">
				<table  id="dg_tppTradeTPayWhiteList" class="easyui-datagrid" 
					data-options="rownumbers:true,pageSize:20,singleSelect:false,selectOnCheck: 'true',checkOnSelect:'true',collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/trade/pay/whitelistData',method:'post',toolbar:toolbar,fit:'true',onClickRow: function (rowIndex, rowData) {$(this).datagrid('unselectRow', rowIndex);}">
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:'true'"></th>
						 	<th data-options="field:'ID',width:120,sortable:'true',align:'left',hidden:true">ID</th>
							<th data-options="field:'INFO_CATEGORY_NAME',width:180,sortable:false,align:'right'">信息类别</th>
							<th data-options="field:'ACCOUNT_NO',width:120,sortable:'true',align:'right'">账号</th>
							<th data-options="field:'BIZ_SYS_NAME',width:180,sortable:false,align:'right'">业务系统</th>
							<th data-options="field:'BANK_CARD_NO',width:120,sortable:'true',align:'right'">银行卡号</th>
							<th data-options="field:'STATUS',width:120,sortable:'true',align:'right'">状态</th>
							<th data-options="field:'NOTE',width:120,sortable:'true',align:'right'">备注</th>
							<th data-options="field:'CREATER',width:120,sortable:'true',align:'right'">创建人</th>
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'right'">创建时间</th>
							<th data-options="field:'UPDATE_TIME',width:120,sortable:'true',align:'right'">更新时间</th>
						</tr>
					</thead>
				</table>
			</div>
<script>
var tppTradeTPayWhiteList ={};
var toolbar = [{
				id : 'add_btn',
				text : '新增',
				iconCls : 'icon-add'
			},{
				id : 'edit_btn',
				text : '修改',
				iconCls : 'icon-edit'
			},{
				id : 'del_btn',
				text : '删除',
				iconCls : 'icon-cancel',
				handler : function() {
					var checkedItems = $('#dg_tppTradeTPayWhiteList').datagrid('getChecked');
				    var names = [];
				    $.each(checkedItems, function(index, item){
				         names.push(item.ID);
				    });                
				    if(names.length>0){
					    	  $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {
						            if (data) {
						            	  $.ajax({
						                      type: 'GET',
						                      url: '${path}/trade/pay/delWhiteList/'+ names,
						                      data: names,
						                      dataType: "json",
						                      success:function(data){
						                    	  tppTradeTPayWhiteList.buildQueryParams();
						                    	  $('#dg_tppTradeTPayWhiteList').datagrid('load');  
						                      }
						                  });
						            }
						        });		   
					    }
					    
				}
			},'-',{
				id : 'search_btn',
				text : '查询',
				iconCls : 'icon-search'
			},{
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
	//定义构造查询
	tppTradeTPayWhiteList.buildQueryParams=function(){
		$('#dg_tppTradeTPayWhiteList').datagrid("options").queryParams={
			 'search_begin_date':$("#search_begin_date").datebox("getValue"),
			 'search_end_date':$("#search_end_date").datebox("getValue"),
			 'search_info_category':$('#search_info_category').combobox("getValue"),
			 'search_biz_sys_no':$('#search_biz_sys_no').combobox("getValue"),
			 'search_bank_card_no':$("input[name='search_bank_card_no']").val(),
			 'search_account_no':$("input[name='search_account_no']").val(),
			 'search_status':$('#search_status').combobox("getValue"),
		}
	}

	//新增
	$('#add_btn').click(function(){
		var add_form_id ='#addTWhiteListForm';
		$('<div id="dialog_holder"></div>').dialog({
		    title: '新增',
		    width: 800,
		    height: 300,
		    href: '${path}/trade/pay/addWhiteList',
		    modal: true,
		    method: "POST",
			onClose: function(){
				$(this).dialog("destroy");
			},
		    buttons: [{
		    	text: "提  交",
		    	handler: function(e){
		    		$('#addTWhiteListForm').form({   
						 url:'${path}/trade/pay/addAndSaveWhiteList',    
							     onSubmit: function(){
							       $.messager.progress(); 
							     },    
							     success:function(data){
							    	 var obj = eval('(' + data + ')');
							       if(obj.flag==false){
							    	   $.messager.alert('提示',obj.msg);   
							    	   $.messager.progress('close');   
							    	   return;
							       }
							        $.messager.progress('close');
							        tppTradeTPayWhiteList.buildQueryParams();
									$('#dg_tppTradeTPayWhiteList').datagrid('load');
									$('#dialog_holder').dialog('close');
							     }
						});// 
						$(".easyui-validatebox").each(function(){
				        	$(this).validatebox({    
				        		novalidate: false   
				        	}); 
				        });
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

	$('#edit_btn').click(function(){
		var row = $('#dg_tppTradeTPayWhiteList').datagrid('getChecked');
		if (row == null) {
			$.messager.alert('提示','请先选择要修改的记录！');
			return;
		}
		
		var names = [];
	    $.each(row, function(index, item){
	         names.push(item.ID);
	    }); 
	    
		if (names.length == 0 || names.length > 1) {
			$.messager.alert('提示','请先选择一条需要修改的记录！');
			return;
		}
		var Id = names[0];
		var edit_form_id ='#editTWhiteListForm';
		$('<div id="dialog_holder"></div>').dialog({
		    title: '修改',
		    width: 800,
		    height: 300,
		    href: '${path}/trade/pay/editWhiteList',
		    modal: true,
		    method: "POST",
		    params:{Id:Id},
			onClose: function(){
				$(this).dialog("destroy");
			},
		    buttons: [{
		    	text: "保  存",
		    	handler: function(e){
		   				 $('#editTWhiteListForm').form({   
						 url:'${path}/trade/pay/editAndSaveWhiteList',    
							     onSubmit: function(){
							       $.messager.progress(); 
							     },    
							     success:function(data){
							    	 var obj = eval('(' + data + ')');
							    	 if(obj.flag==false){
								    	   $.messager.alert('提示',obj.msg);   
								    	   $.messager.progress('close');   
								    	   return;
							       	 }
							        $.messager.progress('close');
							        tppTradeTPayWhiteList.buildQueryParams();
									$('#dg_tppTradeTPayWhiteList').datagrid('load');
									$('#dialog_holder').dialog('close');
							     }
						});// 
						$(".easyui-validatebox").each(function(){
				        	$(this).validatebox({    
				        		novalidate: false   
				        	}); 
				        });
					    if($(edit_form_id).form("validate")==true){
						  	$.messager.progress();
							$(edit_form_id).submit();
						};
		    	}
		    },
		    {
		    	text: "取 消",
		    	handler: function(e){
		    		$(this).dialog("close");
		    	}
		    }]
		});
	});
	
	//重新按照条件刷新查询内容
	$('#search_btn').click(function(){
		var flag = checkDate();
		if (!flag) {
			return ;
		}
		tppTradeTPayWhiteList.buildQueryParams();
		$('#dg_tppTradeTPayWhiteList').datagrid('load');
	});
    //重置查询条件并刷新内容
	$('#clear_btn').click(function(){
		//清空查询条件
		$("#search_begin_date").datebox('setValue', '');
		$("#search_end_date").datebox('setValue', '');
		$('#search_biz_sys_no').combobox('setValue','');
		$('#search_status').combobox('setValue','');
		$('#search_info_category').combobox('setValue','');
		
		$("input[name='search_bank_card_no']").val("");
		$("input[name='search_account_no']").val("");
		
		tppTradeTPayWhiteList.buildQueryParams();
		$('#dg_tppTradeTPayWhiteList').datagrid('load');
		inputTipText();
	});
	inputTipText(); 
});

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

function viewWhiteListDetail(Id){
	$('<div id="dialog_holder"></div>').dialog({
	    title: '详细信息',
	    width: 800,
	    height: 400,
	    href:  '${path}/trade/pay/detailWhiteList',
	    modal: true,
	    method: "POST",
	    params:{Id:Id},
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