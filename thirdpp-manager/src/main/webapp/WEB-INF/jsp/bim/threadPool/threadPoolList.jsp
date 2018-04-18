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
		style="height: 105px; margin: 0px;"
		data-options="region:'north'">
					<form id="searchForm" name="searchForm" method="post">					 
								<table  cellpadding="5">
								<tr>
									<td style="text-align:right">业务类型： </td>
									<td> 
										<input id="bizType" name="bizType" style="width: 100px" 
										class="easyui-combobox" data-options="
											editable:false,valueField: 'value',
											valueField: 'value',
										   textField: 'name',
										   url:'${path}/enumset/bizTypeList',
										  value:'${bizType}'
										     "    /> 
									
									</td>
									<td style="text-align:right">第三方支付平台：</td>
									<td> 
										
										<input id="paySysNo" name="paySysNo" style="width: 100px" 
										class="easyui-combobox" data-options="
											editable:false,valueField: 'value',
											valueField: 'value',
										   textField: 'name',
										   url:'${path}/enumset/dictionary/3'
										  
										     "    />       											 
							 	 	 
										
										</td>
									
									 <td style="text-align:right">创建时间：</td>
										<td><input type="text" id="beginTime" name="beginTime"
											value="${beginTime}" class="easyui-datebox"
											style="width: 100px;"   data-options="required:false,editable:false"/> 
									 -
									 <input type="text" id="endTime" name="endTime"
											value="${endTime}" class="easyui-datebox"
											style="width: 100px;"  data-options="required:false,editable:false" /></td>
							 
								</tr>
								<tr>
									<td style="text-align:right">状态 ：</td>
									<td><select style="width: 100px" name="isActive"
										class="form-control" id="isActive">
											<option value="">请选择</option>
											<option value="0"
												<c:if test="${isActive == 0}">selected</c:if>>关闭</option>
											<option value="1"
												<c:if test="${isActive == 1}">selected</c:if>>开启</option>
									</select></td>
									

								</tr>
							</table>
					 
						<%-- <div class="search-btn-area">
							<input id="search_btn" type="button" class="input-btn-small"
								value="查 询" /> <input id="clear_btn" type="button"
								class="input-btn-small" value="清 除" />
								<c:if
								test="${null != sessionScope.permMap['/bim/threadPool/addThreadPool']}">
								<a class="btn tip-bottom"
									href="${path}/bim/threadPool/threadPoolEditUI/null"><i
									class="icon-add"></i>新增</a>	</c:if>
						</div> --%>
					</form>
				</div>
		 
			<!--搜索栏结束-->

			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding: 0px; margin: 0px;" 	data-options="region:'center'">
				<table id="dg_tppSysTThreadPool" class="easyui-datagrid"
				 style="width: auto; height: 300px"
					data-options="rownumbers:true,collapsible:true,sortName:'',selectOnCheck: 'true',checkOnSelect:'true',sortOrder:'desc',pagination:'true',url:'<%=path%>/bim/threadPool/threadPoolListData',method:'post',toolbar:toolbar,fit:true
						,pageSize:20,onClickRow:function(rowIndex, rowData){ $('#dg_tppSysTThreadPool').datagrid('unselectRow',rowIndex);}
						">
					<thead>
						<tr>
							 <th data-options="field:'ck',checkbox:'true'"></th>
							<th
								data-options="field:'ID',width:120,sortable:'true',align:'right',hidden:'true'">ID</th>
									<th
								data-options="field:'AC',width:150,sortable:'true',align:'center'">操作</th>
									<th data-options="field:'IS_ACTIVE',width:150,sortable:'true',align:'right'">状态</th>
							<th
								data-options="field:'BIZ_TYPE_NAME',width:150,sortable:'true',align:'right'">业务类型</th>							 
							<th
								data-options="field:'PAY_SYS_NAME',width:150,sortable:'true',align:'right'">第三方支付平台</th>							 
							
								<th
								data-options="field:'SLEEP_TIME',width:150,sortable:'true',align:'right'">睡眠时间</th>
								<th
								data-options="field:'APP_NAME',width:160,sortable:'true',align:'right'">应用程序名称</th>
							<th
								data-options="field:'CREAT_TIME',width:150,sortable:'true',align:'right'">创建时间</th>
							<th
								data-options="field:'CREATOR',width:150,sortable:'true',align:'right'">创建人</th>
							<th
								data-options="field:'UPDATE_TIME',width:150,sortable:'true',align:'right'">修改时间</th>
							<th
								data-options="field:'UPDATOR',width:150,sortable:'true',align:'right'">修改人</th>
							
						</tr>
					</thead>
				</table>
			</div>
			<!--搜索栏结果end-->
	 

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
							var checkedItems = $('#dg_tppSysTThreadPool').datagrid(
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
																	   url: '${path}/bim/threadPool/threadPoolDelete/'+names,
																		 
																	data : names,
																	dataType : "json",
																	success : function(
																			data) {
																		$(
																				'#dg_tppSysTThreadPool')
																				.datagrid(
																						'load');
																	}
																});
													}
												});
							}else{
								$.messager.alert("提示","请选择一行");
								 
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
					},
					{
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

		var tppSysTThreadPool_list = {};
		jQuery(function($) {
			//定义构造查询
			tppSysTThreadPool_list.buildQueryParams = function() {
				$('#dg_tppSysTThreadPool').datagrid("options").queryParams={
						 
										'id' : $(
												"input[name='search_id']").val(),
												
										'bizType' : $(
												"input[name='bizType']").val(),
												
										'infType' : $(
												"select[name='infType']").val(),
												
										'paySysNo' : $(
												"input[name='paySysNo']").val(),
												
										'minSize' : $(
											 "input[name='minSize']").val(),
											
										'maxSize' : $(
												"input[name='maxSize']").val(),
												
										'queueSize' : $(
												"input[name='queueSize']").val(),
												
										'beginTime' : $(
												"input[name='beginTime']")	.val(),
											
									 
										'endTime' : $(
												"input[name='endTime']").val(),
												 
												
										'isActive' : $(
												"select[name='isActive']").val(),
											
									};
								 
			};

			//定义构造查询JSON
			tppSysTThreadPool_list.buildJsonQueryParams = function() {
				var searchContent = {
					//标准查询部分
					pageNumber : $(
							'div#div_tppSysTThreadPool_list #dg_tppSysTThreadPool')
							.datagrid('options').pageNumber,
					bizType : $("input[name='bizType']").val(),
					infType : $("input[name='infType']").val(),
					queueSize : $("input[name='paySysNo']").val(),
					queueSize : $("input[name='minSize']").val(),
					queueSize : $("input[name='maxSize']").val(),
					queueSize : $("input[name='queueSize']").val(),
					beginTime : $("input[name='beginTime']").val(),
					endTime : $("input[name='endTime']").val(),
					isActive : $("input[name='isActive']").val(),
				 
					 
				};
				var searchContentStr = JSON.stringify(searchContent);
				//alert(searchContentStr);
				//传到到后台的URL 必须先编码化
				return encodeURI(searchContentStr);
			}

			//重新按照条件刷新查询内容
			$('#searchBut')
					.click(
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
								tppSysTThreadPool_list.buildQueryParams();
								$(
										'#dg_tppSysTThreadPool')
										.datagrid('load');
							});

			//重置查询条件并刷新内容
			$('#clear_btn')
					.click(
							function() {
								//清空查询条件
							 
								 $('#bizType').combobox('setValue', '');	
							 
								$("select[name='infType']").val("");
								$("input[name='paySysNo']").val("");
								$("input[name='maxSize']").val("");
								$("input[name='queueSize']").val("");
								$('#beginTime').combo('setText','');  
								$('#endTime').combo('setText','');  
								$("input[name='beginTime']").val("");
								$("input[name='endTime']").val("");
								$("select[name='isActive']").val("");
								$('#paySysNo').combobox('setValue', '');	
								tppSysTThreadPool_list.buildQueryParams();
								$(
										'#dg_tppSysTThreadPool')
										.datagrid('load');
							});

			//新增
			$('#addBut')
					.click(
							function() {
							 
								var add_form_id ='#threadPoolSave';
								$('<div id="dialog_holder"></div>')
										.dialog(
												{
													title:'新增线程池信息', 
													width : 500,
													height : 300,
													href : '${path}/bim/threadPool/threadPoolEditUI/'+null,
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
																			'#threadPoolSave')
																			.form(
																					{
																						url : '${path}/bim/threadPool/threadPoolSave',
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
																							tppSysTThreadPool_list
																									.buildQueryParams();
																							$(
																									'#dg_tppSysTThreadPool')
																									.datagrid(
																											'load');
																							$(
																									'#dialog_holder')
																									.dialog(
																											'close');
																						}
																							 	}
																					});// 
																					
																					
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

			//更新
				$('#updateBut').click(function(){
					var add_form_id ='#threadPoolSave';
					var rowInfo = $("#dg_tppSysTThreadPool").datagrid('getChecked');
				 
					 
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
									title : '修改线程池信息',
									width : 500,
									height :300,
									href : '${path}/bim/threadPool/threadPoolEditUI/'+id,
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
													$(
															'#threadPoolSave')
															.form(
																	{
																		url : '${path}/bim/threadPool/threadPoolSave',
																		onSubmit : function() {
																			 
																			// do some check       
																			// return false to prevent submit;    
																		},
																		success : function(
																				data) {
																		    var obj=eval( "(" + data + ")" );
																			if(obj.valmsg!=null){
																	       		$.messager.alert('提示',obj.valmsg);	
																	       	}else{
																		    $.messager
																					.progress('close');
																			tppSysTThreadPool_list
																					.buildQueryParams();
																			$(
																					'#dg_tppSysTThreadPool')
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
												}
											},
										 {
												text : "取 消",
												handler : function(e) {
													$(this).dialog("close");
												}
											} ]
								}); }
				});

			//详细
			tppSysTThreadPool_list.detailFormSubmit = function(pkid) {
				$('<div id="dialog_holder"></div>')
						.dialog(
								{
									title : '详细TppSysTThreadPool',
									width : 800,
									height : 500,
									href : 'detailTppSysTThreadPoolPage.action',
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
		function updateThreadPool(id) {

			window.location.href = contextPath
					+ "/bim/threadPool/threadPoolEditUI/" + id;

		}
		function updateFlag(id,active){
			  $.messager.confirm('确认','您确定要改变状态吗？',function(r){
					if (r){    
						//window.location.href = contextPath+"/bim/sysApp/sysAppUpdateIsFlag/"+id+"/"+flag;
						
						$.ajax({
							url:'${path}/bim/threadPool/threadPoolUpdateIsActive',
							data:{"id":id,"active":active},
							dataType:"JSON",
							type:"POST",
							async:true,
							success:function(data){
								$('#dg_tppSysTThreadPool').datagrid('load');
							},
							error:function(data){
								alert("请求超时");
							}
						});	
						
						
					 }
				});
		}
	</script>
</body>
</html>
