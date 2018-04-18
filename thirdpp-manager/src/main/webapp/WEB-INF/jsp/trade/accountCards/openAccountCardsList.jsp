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
	<div class="easyui-panel" title="查询条件"
		style="padding: 5px; height: 144px; margin: 0px;"
		data-options="region:'north'">
	 
			<!--搜索栏开始-->
					<form id="searchForm" name="searchForm" method="post">
						<input type="hidden" id="appCode" name="appCode" class="easyui-textbox" value="${appCode}"/>
						<input id="id" name="id" type="hidden" value="${threadPool.id}" />
							<table  cellpadding="5">
								<tr>
									<td style="text-align:right">第三方账户编号：</td> 
									<td>
										<input type="text" id="ip" name="thirdAccountNo" value="${thirdAccountNo}" class="easyui-textbox"
										style="width: 100px;" data-options="required:true" />
									</td>
									<td style="text-align:right">卡持有人姓名：</td> 
									<td>
										<input type="text" id="bankCardName" name="bankCardName" value="${bankCardName}" class="easyui-textbox"
										style="width: 100px;" data-options="required:true" />
									</td>
										
									<td style="text-align:right">银行卡号：</td> 
									<td>
										<input type="text" id="bankCardNo" name="bankCardNo" value="${bankCardNo}" class="easyui-textbox"
										style="width: 100px;" data-options="required:true" />
									</td>
								</tr>	
								<tr>	
									<td style="text-align:right">预留手机号：</td> 
									<td>
										<input type="text" id="reserveMobile" name="reserveMobile" value="${reserveMobile}" class="easyui-textbox"
										style="width: 100px;" data-options="required:true" />
									</td>
									<td style="text-align:right">状态：</td>
									<td> 
										 <input style="width: 100px" name="status"  id="status" 
															class="easyui-combobox" id="priority"
															data-options="editable:false,data:[{'type':'','text':'请选择'},{'type':'1','text':'正常'},{'type':'2','text':'解绑'}   
															 ],	valueField:'type',textField:'text',value:'${status}'" />
									</td>
									<td style="text-align:right">创建时间： </td>
									<td>
										<input type="text" id="beginTime" name="beginTime"
										value="${beginTime}"  class="easyui-datebox"
										style="width: 100px;"   data-options="required:false,editable:false"/> 
								 	-
									 	<input type="text" id="endTime" name="endTime"
										value="${endTime}" class="easyui-datebox"
										style="width: 100px;"   data-options="required:false,editable:false"/>
									</td>
								 </tr>
								 <tr>
								 	<td style="text-align:right">银行卡类型：</td>
									<td> 
									 	<input style="width: 100px" name="bankCardType"  id="bankCardType" 
														class="easyui-combobox" id="priority"
														data-options="editable:false,data:[{'type':'','text':'请选择'},{'type':'1','text':'借记卡'},{'type':'2','text':'信用卡'}   
														 ],	valueField:'type',textField:'text',value:'${bankCardType}'" />
									</td>
									<td style="text-align:right">第三方支付平台：</td>
									<td>
										<input id="paySysNo" class="easyui-combobox" name="paySysNo" data-options="
											editable:false,valueField: 'value',
											textField: 'name',
											//url是下拉框请求显示的数据
											url:'${path }/enumset/dictionary/3' " style="width:100px;"/>  
									</td>
								 </tr>
							</table>
					</form>
				</div>
			 
			<!--搜索栏结束-->

			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding: 0px; margin: 0px;"
				data-options="region:'center'">
				<table id="dg_tppOpenAccount" class="easyui-datagrid"
				 	 style="width: auto; height: 300px"
					data-options="rownumbers:true,selectOnCheck: 'true',checkOnSelect:true,collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/trade/openAccountCards/openAccountCardsListData',method:'post',toolbar:toolbar,fit:'true'
							,pageSize:20,onLoadSuccess:function(){loadQueryParam();$(this).datagrid('fixRownumber');},onBeforeLoad:function(param){loadBefore(param)},onClickRow:function(rowIndex, rowData){ $('#dg_tppOpenAccount').datagrid('unselectRow',rowIndex);}
					">
					<thead>
						<tr>
							<th
								data-options="field:'ID',width:150,sortable:'true',align:'right',hidden:'true'">ID</th>
							<!-- <th
								data-options="field:'AC',width:100,sortable:'true',align:'center'">查看开户信息</th> -->
						
							<th
								data-options="field:'THIRD_ACCOUNT_NO',width:190,sortable:'true',align:'left'">第三方账户编号</th>
							<th
								data-options="field:'BANK_CARD_NO',width:190,sortable:'true',align:'left'">银行卡号</th>
							<th
								data-options="field:'BANK_CARD_NAME',width:180,sortable:'true',align:'left'">银行卡持有人姓名</th>
							<th
								data-options="field:'BANK_CARD_TYPE',width:180,sortable:'true',align:'left'">银行卡类型</th>
							<th
								data-options="field:'BANK_CODE_NAME',width:180,sortable:'true',align:'left'">银行名称</th>
							<th
								data-options="field:'STATUS',width:180,sortable:'true',align:'left'">状态</th>
							<th
								data-options="field:'RESERVE_MOBILE',width:180,sortable:'true',align:'left'">银行预留手机号</th>
							<th
								data-options="field:'PAY_SYS_NAME',width:180,sortable:'true',align:'left'">第三方支付平台</th>
							<th
								data-options="field:'OPEN_BANK_NAME',width:180,sortable:'true',align:'left'">开户行银行名称</th>
							 
							<th
								data-options="field:'REQ_ID',width:180,sortable:'true',align:'left'">请求Id</th>
							 
							<th
								data-options="field:'CREATE_TIME',width:180,sortable:'true',align:'left'">创建时间</th>
							<th
								data-options="field:'UPDATE_TIME',width:180,sortable:'true',align:'left'">修改时间</th>
						</tr>
					</thead>
				</table>
			</div>
	<script>
	var toolbar = [{
		id : 'searchBut',
		text : '查询',
		iconCls : 'icon-search'
		 
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
	}];
	function loadBefore(param) {
		var search_begin_date = $("#beginTime").datebox("getValue");
		var search_end_date = $("#endTime").datebox("getValue");
		if (search_begin_date == '' && search_end_date == '') {
			param.beginTime=CurentTime("begin");
			param.endTime=CurentTime("end");
		 
	  	}
		  
		 
	}
	function CurentTime(type)
	{ 
	    if(type=="begin"){
	    	
	    	var d=new Date(); 
		    d.setDate(d.getDate()+(-30)); 
		    
		    var year = d.getFullYear();  //年
		    var month =d.getMonth() + 1;
		        //月
		    var day = d.getDate();            //日
		    
		    var m=d.getMonth()+1; 
		    
		    var clock = year + "-";
			   
		    if(month < 10)
		        clock += "0";
		   
		    clock += month + "-";
		   
		    if(day < 10)
		        clock += "0";
		    clock += day ;
	    	return(clock); 
	    }else{
	    	var now = new Date();
	 	   
		    var year = now.getFullYear();  //年
		    var month =now.getMonth() + 1;
		        //月
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
	    
	   
	} 
		var tppSysAppIps_list = {};
		jQuery(function($) {
			$("#beginTime").datebox('setValue', CurentTime("begin"));
			$("#endTime").datebox('setValue', CurentTime("end"));

			//定义构造查询
			tppSysAppIps_list.buildQueryParams = function() {
				$('#dg_tppOpenAccount').datagrid("options").queryParams={
						'thirdAccountNo' : $("input[name='thirdAccountNo']").val(),
						'reserveMobile' : $("input[name='reserveMobile']").val(),
						'bankCardNo' : $("input[name='bankCardNo']").val(),
						'bankCardName' : $("input[name='bankCardName']").val(),
						'bankCardType' : $("input[name='bankCardType']").val(),
						'paySysNo' : $("input[name='paySysNo']").val(),
						'status' : $("input[name='status']").val(),
						'beginTime' : $("input[name='beginTime']").val(),
						'endTime' : $("input[name='endTime']").val(),
						
					 
				};
			};

		 

			//重新按照条件刷新查询内容
			$('#searchBut').click(
					function() {
						 var startTime= $("input[name='beginTime']").val();
						 var endTime= $("input[name='endTime']").val();
						 
						 if(startTime!=null && startTime!='' && endTime!=null && endTime!=''){
								var start=new Number(startTime.replace("-", "").replace("-", "")); 	
								var end=new Number(endTime.replace("-", "").replace("-", ""));
								 if(end<start){
										$.messager.alert("提示","查询开始时间不能大于结束时间！");
								 	return false;
								  }
						} 
						tppSysAppIps_list.buildQueryParams();
						$('#dg_tppOpenAccount')
								.datagrid('load');
					});

			//重置查询条件并刷新内容
			$('#clear_btn').click(
					function() {
					 
					 
					 
					 
						//清空查询条件
						$('#status').combobox('setValue', '');	
						$('#bankCardType').combobox('setValue', '');
						$('#paySysNo').combobox('setValue', '');
						$("input[name='thirdAccountNo']").val("");
						$("input[name='bankCardName']").val("");
						$("input[name='reserveMobile']").val("");
						$("input[name='bankCardNo']").val("");
						$('#beginTime').combo('setText','');  
						$('#endTime').combo('setText','');  
						$("#beginTime").datebox('setValue', CurentTime("begin"));
						$("#endTime").datebox('setValue', CurentTime("end")); 
						tppSysAppIps_list.buildQueryParams();
						$('#dg_tppOpenAccount')
								.datagrid('load');
					});

			//新增
			$('#addBut')
					.click(
							function() {
								  var add_form_id ='#appIpsSave';
								var appCode=$('#appCode').val();
								$('<div id="dialog_holder"></div>')
										.dialog(
												{
													title:'新增IP信息', 
													width : 400,
													height : 200,
													href : '${path}/bim/appIps/appIpsEditUI/'+null+'/'+appCode,
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
																	$(
																			'#appIpsSave')
																			.form(
																					{
																						url : '${path}/bim/appIps/appIpsSave',
																						onSubmit : function() {
																							 
																							// do some check       
																							// return false to prevent submit;    
																						},
																						success : function(
																								data) {
																							//do some
																									  var obj=eval( "(" + data + ")" );
																								    	if(obj.valmsg!=null){
																								       		$.messager.alert('提示',obj.valmsg);	
																								       	}else{
																							$.messager
																									.progress('close');
																							tppSysAppIps_list
																									.buildQueryParams();
																							$(
																									'#dg_tppSysAppIps')
																									.datagrid(
																											'load');
																							$(
																									'#dialog_holder')
																									.dialog(
																											'close');
																						}}
																					});// 
																					 
																					 $(".easyui-validatebox").each(function(){
																			        	 $(this).validatebox({    
																			        		    novalidate: false   
																			        		}); 
																			        	 
																			        });
																				    if($(add_form_id).form("validate")==true){
																					   
																						$(add_form_id).submit();
																					};  
																	;
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

			//更新
		$('#updateBut').click(function(){
			   var edit_form_id ='#appIpsSave';
		/* 	var rowInfo = $("#dg_tppSysAppIps").datagrid('getSelections'); */
			var rowInfo = $('#dg_tppSysAppIps').datagrid('getChecked');

			if(rowInfo.length<1){
				$.messager.alert('提示','请选中一行');
			}else {

				if(rowInfo.length>1){
					$.messager.alert('提示','请选中一行');
					return;
				}
			var id=	rowInfo[0].ID;
				$('<div id="dialog_holder"></div>')
						.dialog(
								{
									title:'修改IP信息', 
									width : 400,
									height : 200,
									href : '${path}/bim/appIps/appIpsEditUI/'+id+'/'+null,
									modal : true,
									method : "POST",
									extractor : function(data) {
										var bodyPattern = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
										var contentPattern = /<article\s+id="content"[^>]*>((.|[\n\r])*)<\/article>/im;
										var scriptPattern = /<script[^>]*>(.|[\n\r\t])*?<\/script>/gim;
										var buttonPattern = /<div\s+class="button-field[\s\w]*"[^>]*>(.|[\n\r\t])*?<\/div>/im;
										var matches = bodyPattern.exec(data);
										if (matches) {
											var content = matches[1]
													.match(contentPattern);
											var scripts = matches[1]
													.match(scriptPattern);
											return (content[1] || "").replace(
													scriptPattern, "").replace(
													buttonPattern, "")
													+ (scripts || []).join("");
										} else {
											return data;
										}
									},
									onClose : function() {
										$(this).dialog("destroy");
									},
									buttons : [
											{
												text : "保  存",
												handler : function(e) {
													$('#appIpsSave')
															.form(
																	{
																		url : '${path}/bim/appIps/appIpsSave',
																		onSubmit : function() {
																			 
																			// do some check       
																			// return false to prevent submit;    
																		},
																		success : function(
																				data) {
																			//do somevar
																				var obj=eval( "(" + data + ")" );
																				  	if(obj.valmsg!=null){
																			       		$.messager.alert('提示',obj.valmsg);	
																			       	}else{
																			$.messager
																					.progress('close');
																			tppSysAppIps_list
																					.buildQueryParams();
																			$(
																					'#dg_tppSysAppIps')
																					.datagrid(
																							'load');
																			$(
																					'#dialog_holder')
																					.dialog(
																							'close');
																		}}
																	});// 
													//validate and sbumit
													  $(".easyui-validatebox").each(function(){
												        	 $(this).validatebox({    
												        		    novalidate: false   
												        		}); 
												        	 
												        });
													    if($(edit_form_id).form("validate")==true){
														   
															$(edit_form_id).submit();
														}; 

												}
											},
											 {
												text : "取 消",
												handler : function(e) {
													$(this).dialog("close");
												}
											} ]
								});
			}
		});
			//详细
			tppSysAppIps_list.detailFormSubmit = function(pkid) {
				$('<div id="dialog_holder"></div>')
						.dialog(
								{
									title : '详细TppSysAppIps',
									width : 800,
									height : 400,
									href : 'detailTppSysAppIpsPage.action',
									modal : true,
									method : "POST",
									params : {
										pkid : pkid
									},
									extractor : function(data) {
										var bodyPattern = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
										var contentPattern = /<article\s+id="content"[^>]*>((.|[\n\r])*)<\/article>/im;
										var scriptPattern = /<script[^>]*>(.|[\n\r\t])*?<\/script>/gim;
										var buttonPattern = /<div\s+class="button-field[\s\w]*"[^>]*>(.|[\n\r\t])*?<\/div>/im;
										var matches = bodyPattern.exec(data);
										if (matches) {
											var content = matches[1]
													.match(contentPattern);
											var scripts = matches[1]
													.match(scriptPattern);
											return (content[1] || "").replace(
													scriptPattern, "").replace(
													buttonPattern, "")
													+ (scripts || []).join("");
										} else {
											return data;
										}
									},
									onClose : function() {
										$(this).dialog("destroy");
									},
									buttons : [
											{
												text : "提  交",
												handler : function(e) {
													var ThisForm = $(this)
															.find("form");
												}
											}, {
												text : "关闭",
												handler : function(e) {
													$(this).dialog("close");
												}
											} ]
								});
			}

		});
		function updateAppIp(id) {

			window.location.href = contextPath
					+ "/bim/appIps/appIpsEditUI/" + id;

		}
		function searchAccount(id){
			 $('<div id="dialog_holder"></div>').dialog({
				    title: '开户信息',
				    width: 600,
				    height: 400,
				    href:  '${path}/trade/openAccountCards/getOpenAccountUI/'+id ,
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
		function loadQueryParam(){
			 
			$('#dg_tppOpenAccount').datagrid("options").queryParams={
				'thirdAccountNo' : $("input[name='thirdAccountNo']").val(),
				'reserveMobile' : $("input[name='reserveMobile']").val(),
				'bankCardNo' : $("input[name='bankCardNo']").val(),
				'bankCardName' : $("input[name='bankCardName']").val(),
				'bankCardType' : $("input[name='bankCardType']").val(),
				'paySysNo' : $("input[name='paySysNo']").val(),
				'status' : $("input[name='status']").val(),
				'beginTime' : $("input[name='beginTime']").val(),
				'endTime' : $("input[name='endTime']").val(),
				
			 
		};
			 
		}
	</script>
</body>
</html>
