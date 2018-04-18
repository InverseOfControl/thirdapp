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
						<input type="hidden" id="appCode" name="appCode" class="easyui-textbox" value="${appCode}"/>
						<input id="id" name="id" type="hidden" value="${threadPool.id}" />
							<table  cellpadding="5">
								<tr>
									<td>ip：</td> 
									<td><input type="text" id="ip" name="ip" value="${ip}" class="easyui-textbox"
										style="width: 200px;" data-options="required:true" /></td>
									<td>创建时间 </td>
									<td><input type="text" id="beginTime" name="beginTime"
										value="${beginTime}"  class="easyui-datebox"
										style="width: 90px;"   /> 
								 	-
									 <input type="text" id="endTime" name="endTime"
										value="${endTime}" class="easyui-datebox"
										style="width: 90px;"   /></td>
								</tr>
							</table>
					</form>
				</div> --%>
			 
			<!--搜索栏结束-->

			<!--搜索结果开始-->
	<!-- 	<div class="easyui-panel" style="padding: 0px; margin: 0px;"
				data-options="region:'center'"> -->
				<table id="dg_tppSysAppIps" class="easyui-datagrid"
				 	 style="width: auto; height: 300px"
					data-options="rownumbers:true,selectOnCheck: 'true',checkOnSelect:true,collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/bim/appIps/appIpsListData/${appCode}',method:'post',toolbar:'#tb2',fit:'true'
							,pageSize:20,onClickRow:function(rowIndex, rowData){ $('#dg_tppSysAppIps').datagrid('unselectRow',rowIndex);}
					">
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:'true'"></th>
							<th
								data-options="field:'ID',width:150,sortable:'true',align:'right',hidden:'true'">ID</th>
							<th
								data-options="field:'APP_CODE',width:190,sortable:'true',align:'right'">业务系统编号</th>
							<th
								data-options="field:'APP_NAME',width:190,sortable:'true',align:'right'">业务系统名称</th>
							<th
								data-options="field:'IP',width:180,sortable:'true',align:'right'">ip</th>
							<th
								data-options="field:'CREATE_TIME',width:180,sortable:'true',align:'right'">创建时间</th>
							<th
								data-options="field:'CREATER',width:180,sortable:'true',align:'right'">创建人</th>
							<th
								data-options="field:'UPDATE_TIME',width:180,sortable:'true',align:'right'">修改时间</th>
							<th
								data-options="field:'UPDATER',width:180,sortable:'true',align:'right'">修改人</th>
							 
						</tr>
					</thead>
				</table>
		 	<div id="tb2" style=" padding:0px">    
		   <div style="height:40px;"> 
		    <form id="searchForm" name="searchForm" method="post">
						<input type="hidden" id="appCode" name="appCode" class="easyui-textbox" value="${appCode}"/>
						<input id="id" name="id" type="hidden" value="${threadPool.id}" />
							<table  cellpadding="5">
								<tr>
									<td style="text-align:right">ip：</td> 
									<td><input type="text" id="ip" name="ip" value="${ip}" class="easyui-textbox"
										style="width: 100px;" data-options="required:true" /></td>
									<td style="text-align:right">创建时间： </td>
									<td><input type="text" id="beginTime" name="beginTime"
										value="${beginTime}"  class="easyui-datebox"
										style="width: 90px;"  data-options="required:false,editable:false" /> 
								 	-
									 <input type="text" id="endTime" name="endTime"
										value="${endTime}" class="easyui-datebox"
										style="width: 90px;" data-options="required:false,editable:false"  /></td>
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
		    </div>    
		</div>   
	<script>
	var toolbar = [{
		id : 'addBut',
		text : '新增',
		iconCls : 'icon-add',
	/* 	handler : function() {
			 viewInfo(null);
		} */
	},{
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
	},{
		id : 'delBut',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : function() {
			var checkedItems = $('#dg_tppSysAppIps').datagrid('getChecked');
			     var names = [];
			     $.each(checkedItems, function(index, item){
			         names.push(item.ID);
			    });                
		    if(names.length>0){
			    	  $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {
					    	var m= names.toString()
				            if (data) {
				            	  $.ajax({
				                      type: 'GET',
				                      url: '${path}/bim/appIps/appIpsDelete/'+names,
				                      data: names,
				                      dataType: "json",
				                      success:function(data){
				                    	  $('#dg_tppSysAppIps').datagrid('load');  
				                      }
				                  });
				            }
				        });		   
			    }else{
			    	  $.messager.alert("提示", "请选中一行")
			    }
			    
		}
	},'-',{
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
	
		var tppSysAppIps_list = {};
		jQuery(function($) {
			 

			//定义构造查询
			tppSysAppIps_list.buildQueryParams = function() {
				$('#dg_tppSysAppIps').datagrid("options").queryParams={
				 
						'appCode' : $("input[name='appCode']").val(),
						'ip' : $("input[name='ip']").val(),
						'beginTime' : $("input[name='beginTime']").val(),
						'endTime' : $("input[name='endTime']").val(),
				};
			};

			$('#delBut2').click(		function() {
				var checkedItems = $('#dg_tppSysAppIps').datagrid('getChecked');
			     var names = [];
			     $.each(checkedItems, function(index, item){
			         names.push(item.ID);
			    });                
		    if(names.length>0){
			    	  $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {
					    	var m= names.toString()
				            if (data) {
				            	  $.ajax({
				                      type: 'GET',
				                      url: '${path}/bim/appIps/appIpsDelete/'+names,
				                      data: names,
				                      dataType: "json",
				                      success:function(data){
				                    	  $('#dg_tppSysAppIps').datagrid('load');  
				                      }
				                  });
				            }
				        });		   
			    }else{
			    	  $.messager.alert("提示", "请选中一行");
			    }
			  
				
				
			});

			//重新按照条件刷新查询内容
			$('#searchBut2').click(
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
						$('#dg_tppSysAppIps')
								.datagrid('load');
					});

			//重置查询条件并刷新内容
			$('#clear_btn2').click(
					function() {
						//清空查询条件
						$("input[name='appCode']").val("");
						$("input[name='ip']").val("");
						$('#beginTime').combo('setText','');  
						$('#endTime').combo('setText','');  
						$("input[name='beginTime']").val(""),
						$("input[name='endTime']").val(""),
						tppSysAppIps_list.buildQueryParams();
						$('#dg_tppSysAppIps')
								.datagrid('load');
					});

			//新增
			$('#addBut2')
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
		$('#updateBut2').click(function(){
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
									height : 500,
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
		function updateFlag(id,flag){
			$.messager.confirm('确认','您确定要改变状态吗？',function(r){
				if (r){    
					//window.location.href = contextPath+"/bim/sysApp/sysAppUpdateIsFlag/"+id+"/"+flag;
					
					$.ajax({
						url:'${path}/bim/sysApp/sysAppUpdateIsFlag',
						data:{"id":id,"flag":flag},
						dataType:"JSON",
						type:"POST",
						async:true,
						success:function(data){
							$('#dg_tppSysAppSon').datagrid('load');
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
