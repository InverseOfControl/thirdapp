<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>证大财富随指贷管理系统</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/jsCssInclude.jsp"%>
<link rel="stylesheet" href="${sessionScope.path}/css/base.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body class="easyui-layout">
	<%-- <div class="easyui-panel" title="查询条件"
		style="padding: 5px; height: 75px; margin: 0px;"
		data-options="region:'north'">
		<!--搜索栏开始-->
		<form id="searchForm" name="searchForm" method="post">
			<table cellpadding="5">
				<tr>
					<input class="easyui-textbox" type="hidden" id="bankCode"
						name="bankCode" value="${bankCode}" />
					<td>银行机构名称</td>
					<td><input class="easyui-textbox" type="text" id="bankOrgName"
						name="bankOrgName" value="${bankOrgName}" /></td>
					<td>机构所在省份</td>
					<td><input id="bankOrgProvinceNo" name="bankOrgProvinceNo"
						class="easyui-combobox"
						data-options="
											editable:false,valueField: 'value',
							textField: 'name', url:'${path }/enumset/areaInfo/0' "
						style="width: 200px;" /></td>
					<td>机构所在城市</td>
					<td><input id="bankOrgProvinceCityNo"
						name="bankOrgProvinceCityNo" class="easyui-combobox"
						data-options="
											editable:false,valueField: 'value',
											textField: 'name' "
						style="width: 200px;" /></td>
				</tr>
			</table>
		</form>
	</div> --%>
	<!--搜索栏结束-->
	<!--搜索结果开始-->
	<!-- <div class="easyui-panel" style="padding: 0px; margin: 0px;"
		data-options="region:'center'"> -->
		<table id="dg_ddTBankOrgInfo" class="easyui-datagrid"
			style="width: auto; height: 300px"
			data-options="rownumbers:true,collapsible:true,selectOnCheck: 'true',checkOnSelect:true, sortName:'',sortOrder:'desc',pagination:'true',url:'<%=path%>/bim/bankOrgInfo/bankOrgInfoListData/${bankCode}',method:'post',toolbar:'#tb2',fit:'true'
				,pageSize:20,onClickRow:function(rowIndex, rowData){ $('#dg_ddTBankOrgInfo').datagrid('unselectRow',rowIndex);},
				onLoadSuccess:function(data){ $('#dg_ddTBankOrgInfo').datagrid('doCellTip',{cls:{'background-color':'white'},delay:1000,tdname:['NOTE']});  } 
			">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:'true'"></th>
					<th
						data-options="field:'BANK_ORG_NO',width:140,sortable:'true',align:'left'">银行机构行号</th>
					<th
						data-options="field:'BANK_ORG_NAME',width:320,sortable:'true',align:'left'">银行机构名称</th>
					<th
						data-options="field:'BANK_CODE',width:140,sortable:'true',align:'left'">银行编码</th>
					<th
						data-options="field:'BANK_NAME',width:140,sortable:'true',align:'left'">银行名称</th>
					<th
						data-options="field:'BANK_LINE_NO',width:140,sortable:'true',align:'left'">支付联行号</th>
					<th
						data-options="field:'BANK_ORG_PROVINCE_NAME',width:140,sortable:'true',align:'left'">所在省份</th>
					<th
						data-options="field:'BANK_ORG_PROVINCE_CITY_NAME',width:144,sortable:'true',align:'left'">所在城市</th>
					<th
						data-options="field:'DT_TIME',width:142,sortable:'true',align:'left'">创建日期</th>
					<th
						data-options="field:'NOTE',width:142,sortable:'true',align:'left'">备注</th>
				</tr>
			</thead>
		</table>
	 
	<!--搜索栏结果end-->


	<!-- foot -->

		<div id="tb2" style=" padding:0px">    
		   <div style="height:40px;"> 
		  <form id="searchForm" name="searchForm" method="post">
			<table cellpadding="5">
				<tr>
					<input class="easyui-textbox" type="hidden" id="bankCode"
						name="bankCode" value="${bankCode}" />
					<td style="text-align:right">银行机构名称:</td>
					<td><input class="easyui-textbox" type="text" id="bankOrgName"
						name="bankOrgName" value="${bankOrgName}" style="width: 100px;"/></td>
					<td style="text-align:right">省份:</td>
					<td><input id="bankOrgProvinceNo" name="bankOrgProvinceNo"
						class="easyui-combobox"
						data-options="
											editable:false,valueField: 'value',
							textField: 'name', url:'${path }/enumset/areaInfo/0' "
						style="width: 80px;" /></td>
					<td style="text-align:right">城市:</td>
					<td><input id="bankOrgProvinceCityNo"
						name="bankOrgProvinceCityNo" class="easyui-combobox"
						data-options="
											editable:false,valueField: 'value',
											textField: 'name' "
						style="width: 80px;" /></td>
				</tr>
			</table>
		</form>
		    	 
		       
		    </div> 
		    <div>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addBut2">新增</a>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="updateBut2">修改</a>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" id="delBut2">删除</a> 
		         <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="searchBut2">查询</a> 
		        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="clear_btn2">清除</a>     
		      	<a href="#" class="easyui-linkbutton" iconCls="icon-assigned" plain="true" id="imp_btn">导入excel</a>   
		   
		  
		    </div>    
		   
		</div>   
	<script>
		var toolbar = [
				{
					id : 'addBut',
					text : '新增',
					iconCls : 'icon-add',
				/* 	handler : function() {
						 viewInfo(null);
					} */
				},
				{
					id : 'updateBut',
					text : '修改',
					iconCls : 'icon-edit',
				/* handler : function() {
					var rowInfo = $("#dg_ddTBankInfo").datagrid('getSelections');
					 
					if(rowInfo.length<1){
						$.messager.alert('提示','请选中一行');
					}else {

						if(rowInfo.length>1){
							$.messager.alert('提示','请选中一行');
							return;
						}
						viewInfo(rowInfo[0].ID);
					}
				} */
				},
				{
					id : 'delBut',
					text : '删除',
					iconCls : 'icon-cancel',
					handler : function() {
						var checkedItems = $('#dg_ddTBankOrgInfo').datagrid(
								'getChecked');
						var names = [];
						$.each(checkedItems, function(index, item) {
							names.push(item.ID);
						});
						if (names.length > 0) {
							$.messager
									.confirm(
											"操作提示",
											"您确定要执行操作吗？",
											function(data) {
												var m = names.toString()
												if (data) {
													$
															.ajax({
																type : 'GET',
																url : '${path}/bim/bankOrgInfo/bankInfoOrgDelete/'
																		+ names,
																data : names,
																dataType : "json",
																success : function(
																		data) {
																	$(
																			'#dg_ddTBankOrgInfo')
																			.datagrid(
																					'load');
																}
															});
												}
											});
						}else{
							$.messager.alert("提示","请选中一行");
							
							return;
						}

					}
				}, '-', {
					id : 'searchBut',
					text : '查询',
					iconCls : 'icon-search'
				/* handler : function() {
					var searchMsg =   $('#searchForm').serialize();
					//对参数进行解码(显示中文)
					searchMsg = decodeURIComponent(searchMsg);	 
					var queryParam = $.serializeToJsonObject(searchMsg);  
					queryParam.r = new Date().getTime();
					$('#dg_ddTBankInfo').datagrid('reload',queryParam);
				} */
				},{
					id : 'clear_btn',
					text : '清除',
					iconCls : 'icon-remove'
				/* handler : function() {
					var searchMsg =   $('#searchForm').serialize();
					//对参数进行解码(显示中文)
					searchMsg = decodeURIComponent(searchMsg);	 
					var queryParam = $.serializeToJsonObject(searchMsg);  
					queryParam.r = new Date().getTime();
					$('#dg_ddTBankInfo').datagrid('reload',queryParam);
				} */
				} ,'-',{
					id : 'imp_btn',
					text : '导入excel',
					iconCls : 'icon-assigned'
				/* handler : function() {
					var searchMsg =   $('#searchForm').serialize();
					//对参数进行解码(显示中文)
					searchMsg = decodeURIComponent(searchMsg);	 
					var queryParam = $.serializeToJsonObject(searchMsg);  
					queryParam.r = new Date().getTime();
					$('#dg_ddTBankInfo').datagrid('reload',queryParam);
				} */
				} ];

		function viewInfo(id) {
			$('<div id="dialog_holder"></div>').dialog({
				title : '修改',
				width : 400,
				height : 300,
				href : '${path}/bim/bankInfo/bankInfoEditUI/' + id,
				modal : true,
				method : "POST",

				onClose : function() {
					$(this).dialog("destroy");
				},
				buttons : [ {
					text : "关闭",
					handler : function(e) {
						$(this).dialog("close");
					}
				} ]
			});
		}
		
		var ddTBankOrgInfo_list = {};
		jQuery(function($) {
			$('#bankOrgProvinceNo').combobox(
					{
						onSelect : function(record) {
							if (record.value == '' || record.value == null) {
								$("#bankOrgProvinceCityNo").combobox(
								"clear");
								$("#bankOrgProvinceCityNo").combobox(
										"loadData", "");
							} else {
								$.ajax({
									type : "POST",
									url : '${path }/enumset/areaInfo/'
											+ record.value,
									cache : false,
									dataType : "json",
									success : function(data) {
										$("#bankOrgProvinceCityNo").combobox(
												"clear");
										$("#bankOrgProvinceCityNo").combobox(
												"loadData", data);
									}
								});
							}
							
						}
					});
	 
			//定义构造查询
			ddTBankOrgInfo_list.buildQueryParams = function() {
				$('#dg_ddTBankOrgInfo').datagrid("options").queryParams={
					 
								'bankCode' : $("input[name='bankCode']").val(),
								'bankOrgName' : $("input[name='bankOrgName']")
										.val(),
								'bankCode' : $("input[name='bankCode']").val(),
								'bankName' : $("input[name='bankName']").val(),
								'bankOrgNo' : $("input[name='bankOrgNo']")
										.val(),
								'bankOrgProvinceNo' : $(
										"input[name='bankOrgProvinceNo']")
										.val(),
								'bankOrgProvinceCityNo' : $(
										"input[name='bankOrgProvinceCityNo']")
										.val(),
								'beginTime' : $("input[name='beginTime']")
										.val(),
								'endTime' : $("input[name='endTime']").val(),
							};
					
			};
			 

			//定义构造查询JSON
			ddTBankOrgInfo_list.buildJsonQueryParams = function() {
				var searchContent = {
					//标准查询部分
					pageNumber : $(
							'div#div_ddTBankOrgInfo_list #dg_ddTBankOrgInfo')
							.datagrid('options').pageNumber,
					//页面查询框部分
					id : $("input[name='search_id']").val(),
					bank_org_no : $("input[name='search_bank_org_no']").val(),
					bank_org_name : $("input[name='search_bank_org_name']")
							.val(),
					bank_code : $("input[name='search_bank_code']").val(),
					bank_org_province_no : $(
							"input[name='search_bank_org_province_no']").val(),
					dt_time : $("input[name='search_dt_time']").val(),
					note : $("input[name='search_note']").val(),
				};
				var searchContentStr = JSON.stringify(searchContent);
				//alert(searchContentStr);
				//传到到后台的URL 必须先编码化
				return encodeURI(searchContentStr);
			}
			//删除
			$('#delBut2').click(
				function() {
				var checkedItems = $('#dg_ddTBankOrgInfo').datagrid('getChecked');
			     var names = [];
			     $.each(checkedItems, function(index, item){
			         names.push(item.ID);
			    });                
		    if(names.length>0){
			    	  $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {
					    	var m= names.toString();
				            if (data) {
				            	  $.ajax({
				                      type: 'GET',
				                  		url : '${path}/bim/bankOrgInfo/bankInfoOrgDelete/'+names,
				                      data: names,
				                      dataType: "json",
				                      success:function(data){
				                    	  $('#dg_ddTBankOrgInfo').datagrid('load');  
				                      }
				                  });
				            }
				        });		   
			    }else{
			    	  $.messager.alert("提示", "请选中一行");}
			    });
			//重新按照条件刷新查询内容
			$('#searchBut2').click(function() {
				ddTBankOrgInfo_list.buildQueryParams();
				$('#dg_ddTBankOrgInfo').datagrid('load');
			});

			//重置查询条件并刷新内容
			$('#clear_btn2').click(
					function() {
						//清空查询条件
						$('#bankOrgProvinceNo').combobox('setValue', '');	
						$("#bankOrgProvinceCityNo").combobox("clear");
						$("#bankOrgProvinceCityNo").combobox("loadData", "");
						$("input[name='bankOrgName']").val("");
						ddTBankOrgInfo_list.buildQueryParams();
						$('#dg_ddTBankOrgInfo').datagrid('load');
					});

			//新增
				$('#imp_btn')
					.click(
							function() {
								var add_form_id ='#excelImp';  
								$('<div id="dialog_holder"></div>')
										.dialog(
												{
													title:'导入excel',
													width : 550,
													height : 400,
													href : '${path}/bim/bankOrgInfo/ExcelImportUI/',
													modal : true,
													method : "POST",
													extractor : function(data) {
														var bodyPattern = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
														var contentPattern = /<article\s+id="content"[^>]*>((.|[\n\r])*)<\/article>/im;
														var scriptPattern = /<script[^>]*>(.|[\n\r\t])*?<\/script>/gim;
														var buttonPattern = /<div\s+class="button-field[\s\w]*"[^>]*>(.|[\n\r\t])*?<\/div>/im;
														var matches = bodyPattern
																.exec(data);
														if (matches) {
															var content = matches[1]
																	.match(contentPattern);
															var scripts = matches[1]
																	.match(scriptPattern);
															return (content[1] || "")
																	.replace(
																			scriptPattern,
																			"")
																	.replace(
																			buttonPattern,
																			"")
																	+ (scripts || [])
																			.join("");
														} else {
															return data;
														}
													},
													onClose : function() {
														$(this).dialog(
																"destroy");
													},
													buttons : [
													   		{
																text : "查看模板",
																handler : function(
																		e) {
															 
																	$('<div id="dialog_holder"></div>').dialog({
																	    title: 'excel导入模板',
																	    width: 600,
																	    height: 400,
																	    href:  '${path}/bim/bankOrgInfo/ExcelTemplate',
																	    modal: true,
																	    method: "POST",
																	    
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
															},
															{
																text : "导  入",
																handler : function(
																		e) {
																	
																	$('#excelImp').form(																		
																					{
																					 
																						onSubmit : function() {
																					 	 		$.messager.progress({
																					 	 			text:'加载中...'
																					 	 		 
																					 	 			});
																							
																						/* 	 $.ajax({
																								type : "POST",
																								url : '${path}/bim/bankOrgInfo/bankCodeValide/'+  $('#bankOrgNo').val(),
																								cache : false,
																								dataType : "json",
																								success : function(data) {
																									 
																									var flag= true;
																									var obj = eval(data);
																									
																									 
																								 
																									if (obj.valmsg == 'false') {
																										$.messager.alert('提示','银行机构行号已存在');
																										flag= false;
																										 
																									} 
																									
																								}
																								
																							}); */
																						
																							
																							// do some check       
																							// return false to prevent submit;  
																						 
																						
																							 
																						},
																						success : function(data) {
																								
																							//do some
																							var obj = eval("("+ data+ ")");
																									
																									
																							/* 	$.messager
																										.progress('close'); */
																						 
																							if (obj.valmsg != null) {
																								
																								if(obj.valmsg=="err"){
																									$.messager.progress('close');
																									$.messager.alert('提示',"文件格式不正确或无上传文件！");
																								}else{
																								if(obj.msgFalg=="true"){
																									$.messager.progress('close');
																									ddTBankOrgInfo_list.buildQueryParams();
																									$('#dg_ddTBankOrgInfo').datagrid('load');
																									$('#dialog_holder').dialog('close');
																								 
																									 $('<div id="dialog_holder"></div>').dialog({
																									    title: '导入详情',
																									    width: 450,
																									    height: 350,
																									    href:  '${path}/bim/bankOrgInfo/excelMsg/' ,
																									    modal: true,
																									    params:{msg:obj.valmsg},
																									    method: "POST",
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
																								  /* 	$.messager.show({
																										title:'提示',
																										width:'300',
																										height:'100%',  
																										msg:obj.valmsg ,
																										timeout:0,
																										showType:'show',
																										style:{
																											right:'',
																											top:document.body.scrollTop+document.documentElement.scrollTop,
																											bottom:''
																										}

																									}); */
 
																									
																								}else{
																								$.messager.alert('',obj.valmsg,"",function () {
																									$.messager.progress('close');
																									ddTBankOrgInfo_list.buildQueryParams();
																									$('#dg_ddTBankOrgInfo').datagrid('load');
																									$('#dialog_holder').dialog('close');
																						        });
																								}
																								
																							}
																							} else {
																							
																								$.messager.progress('close');
																								ddTBankOrgInfo_list.buildQueryParams();
																								$('#dg_ddTBankOrgInfo').datagrid('load');
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
																    if($(add_form_id).form("validate")==true){
																    	$.messager.progress({
																    		text:'加载中...'
																    		});  	
																		$(add_form_id).submit();
																	};  	
																 
																	 
																}
															},
															{
																text : "取 消",
																handler : function(
																		e) {
																	$(this)
																			.dialog(
																					"close");
																}
															} ]
												});
							});

			$('#addBut2')
					.click(
							function() {
								var add_form_id ='#bankOrgInfoSave';  
								$('<div id="dialog_holder"></div>')
										.dialog(
												{
													title:'新增银行机构',
													width : 500,
													height : 400,
													href : '${path}/bim/bankOrgInfo/bankOrgInfoEditUI/'
															+ null
															+ '/'
															+ $('#bankCode')
																	.val(),
													modal : true,
													method : "POST",
													extractor : function(data) {
														var bodyPattern = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
														var contentPattern = /<article\s+id="content"[^>]*>((.|[\n\r])*)<\/article>/im;
														var scriptPattern = /<script[^>]*>(.|[\n\r\t])*?<\/script>/gim;
														var buttonPattern = /<div\s+class="button-field[\s\w]*"[^>]*>(.|[\n\r\t])*?<\/div>/im;
														var matches = bodyPattern
																.exec(data);
														if (matches) {
															var content = matches[1]
																	.match(contentPattern);
															var scripts = matches[1]
																	.match(scriptPattern);
															return (content[1] || "")
																	.replace(
																			scriptPattern,
																			"")
																	.replace(
																			buttonPattern,
																			"")
																	+ (scripts || [])
																			.join("");
														} else {
															return data;
														}
													},
													onClose : function() {
														$(this).dialog(
																"destroy");
													},
													buttons : [
															{
																text : "提  交",
																handler : function(
																		e) {
																	
																	$('#bankOrgInfoSave').form(																		
																					{
																						url : '${path}/bim/bankOrgInfo/bankOrgInfoSave',
																						onSubmit : function() {
																						/* 	$.messager.progress(); */
																							
																						/* 	 $.ajax({
																								type : "POST",
																								url : '${path}/bim/bankOrgInfo/bankCodeValide/'+  $('#bankOrgNo').val(),
																								cache : false,
																								dataType : "json",
																								success : function(data) {
																									 
																									var flag= true;
																									var obj = eval(data);
																									
																									 
																								 
																									if (obj.valmsg == 'false') {
																										$.messager.alert('提示','银行机构行号已存在');
																										flag= false;
																										 
																									} 
																									
																								}
																								
																							}); */
																						
																							
																							// do some check       
																							// return false to prevent submit;  
																						 
																						
																							 
																						},
																						success : function(data) {
																								
																							//do some
																							var obj = eval("("+ data+ ")");
																									
																									
																							/* 	$.messager
																										.progress('close'); */
																						 
																							if (obj.valmsg != null) {
																								$.messager.alert('提示',obj.valmsg);
																							} else {
																								$.messager.progress('close');
																								ddTBankOrgInfo_list.buildQueryParams();
																								$('#dg_ddTBankOrgInfo').datagrid('load');
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
																    if($(add_form_id).form("validate")==true){
																		$(add_form_id).submit();
																	};  	
																 
																	 
																}
															},
															{
																text : "取 消",
																handler : function(
																		e) {
																	$(this)
																			.dialog(
																					"close");
																}
															} ]
												});
							});

			//更新  updateBut
			$('#updateBut2').click(function(){
				     var edit_form_id ='#bankOrgInfoSave';
							var rowInfo = $("#dg_ddTBankOrgInfo").datagrid('getChecked');
						 
							 
							if(rowInfo.length<1){
								$.messager.alert('提示','请选中一行');
							}else {

								if(rowInfo.length>1){
									$.messager.alert('提示','请选中一行');
									return;
								}
							var id=	rowInfo[0].ID;
							
				 
					$('<div id="dialog_holder"></div>').dialog({
					    title: '修改银行机构',
					    width: 500,
					    height: 400,
					    href: '${path}/bim/bankOrgInfo/bankOrgInfoEditUI/'+id+ '/'	+ $('#bankCode').val(),
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
					    	text: "保  存",
					    	handler: function(e){
					   				$('#bankOrgInfoSave').form({   
									 url:'${path}/bim/bankOrgInfo/bankOrgInfoSave',    
										     onSubmit: function(){
										     //  $.messager.progress(); 
										        // do some check       
										        // return false to prevent submit;    
										     },    
										        success:function(data){
										         
										        //do some
										        var obj=eval( "(" + data + ")" );
										        
										       	if(obj.valmsg!=null){
										       		$.messager.alert('提示',obj.valmsg);	
										       	}else{
										       		 
										       		$.messager.progress('close');
										       		ddTBankOrgInfo_list.buildQueryParams();
													$('#dg_ddTBankOrgInfo').datagrid('load');
													$('#dialog_holder').dialog('close');
										       	}	
										     }
									});// 
									 
									/*  $.messager.progress();  */

									 $(".easyui-validatebox").each(function(){
							        	 $(this).validatebox({    
							        		    novalidate: false   
							        		}); 
							        	 
							        });
								    if($(edit_form_id).form("validate")==true){
									   
										$(edit_form_id).submit();
									};  
					    		
					    	}
					    }		    
					    ,{
					    	text: "取 消",
					    	handler: function(e){
					    		$(this).dialog("close");
					    	}
					    }]
					}); }
				});
				
	 
				
				
			});

			</script>

			</body>
			</html>
