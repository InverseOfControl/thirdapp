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
						<input type="hidden" id="parentId" name="parentId" class="easyui-textbox" value="${parentId}"/>
					 
							<table  cellpadding="5">
									<td>区域名称：</td> 
									<td><input type="text" id="areaName" name="areaName" value="${areaName}" class="easyui-textbox"
										style="width: 200px;" data-options="required:true" /></td>
								</tr>
							</table>
					</form>
				</div> --%>
			<!--搜索栏结束-->
			<!--搜索结果开始-->
 
				<table id="dg_tppSysAreaInfo" class="easyui-datagrid"
					  style="width: auto; height: 300px"
					data-options="rownumbers:true, selectOnCheck: 'true',checkOnSelect:true, collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/bim/areaInfo/areaInfoListData/${parentId}',method:'post',toolbar:'#tb',fit:'true'
							,pageSize:20,onClickRow:function(rowIndex, rowData){ $('#dg_tppSysAreaInfo').datagrid('unselectRow',rowIndex);}
					">
					<thead>
						<tr>
						<th data-options="field:'ck',checkbox:'true'"></th>
							<th
								data-options="field:'ID',width:375,sortable:'true',align:'right',hidden:'true'">ID</th>
							<th
								data-options="field:'AREA_CODE',width:80,sortable:'true',align:'left'">地市编码</th>
							<th
								data-options="field:'AREA_NAME',width:300,sortable:'true',align:'left'">地市名称</th>
							
						</tr>
					</thead>
				</table>
		 
		 
		<div id="tb" style=" padding:0px">    
		   <div style="height:40px;"> 
						<input type="hidden" id="parentId" name="parentId" class="easyui-textbox" value="${parentId}"/>
					 
							<table  cellpadding="5">
									<td style="text-align:right">地市名称：</td> 
									<td><input type="text" id="cityName" name="cityName" value="${cityName}" class="easyui-textbox"
										style="width: 100px;" data-options="required:true" /></td>
								</tr>
							</table>
		    	 
		       
		    </div> 
		    <div>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addBut2">新增</a>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="updateBut2">修改</a>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" id="delBut2">删除</a> 
		         <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="searchBut2">查询</a> 
		           <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="clear_btn2">清除</a>     
		      <!--   <a href="#" class="easyui-linkbutton" iconCls="icon-assigned" plain="true" onclick="importInfo()">导入excel</a>   --> 
		   
		  
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
			var checkedItems = $('#dg_tppSysAreaInfo').datagrid('getChecked');
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
				                      url: '${path}/bim/areaInfo/areaInfoDelete/'+names,
				                      data: names,
				                      dataType: "json",
				                      success:function(data){
				                    	  $('#dg_tppSysAreaInfo').datagrid('load');  
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
	}];
	
		var tppSysAppIps_list = {};
		jQuery(function($) {
			//定义构造查询
			tppSysAppIps_list.buildQueryParams = function() {
				$('#dg_tppSysAreaInfo').datagrid("options").queryParams={
				 
						'appCode' : $("input[name='appCode']").val(),
						'areaName' : $("input[name='cityName']").val(),
						'beginTime' : $("input[name='beginTime']").val(),
						'endTime' : $("input[name='endTime']").val(),
					};
		 
			};

			//定义构造查询JSON
		 

			//重新按照条件刷新查询内容
			$('#searchBut2').click(
					function() {
						tppSysAppIps_list.buildQueryParams();
						$('#dg_tppSysAreaInfo')
								.datagrid('load');
					});
		//删除
		$('#delBut2').click(
				function() {
				var checkedItems = $('#dg_tppSysAreaInfo').datagrid('getChecked');
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
				                      url: '${path}/bim/areaInfo/areaInfoDelete/'+names,
				                      data: names,
				                      dataType: "json",
				                      success:function(data){
				                    	  $('#dg_tppSysAreaInfo').datagrid('load');  
				                      }
				                  });
				            }
				        });		   
			    }else{
			    	  $.messager.alert("提示", "请选中一行");}
			    });

			//重置查询条件并刷新内容
			$('#clear_btn2').click(
					function() {
						//清空查询条件

					 
						  $("input[name='cityName']").val("");
						 
					 
							tppSysAppIps_list.buildQueryParams();
						$('#dg_tppSysAreaInfo')
								.datagrid('load');
					});

			//新增
					$('#addBut2')
					.click(
							function() {
								  var add_form_id ='#areaInfoSave';
							  var parentId=$('#parentId').val();
								$('<div id="dialog_holder"></div>')
										.dialog(
												{
											 		title:'新增区域',
													width : 500,
													height :300,
													href: '${path}/bim/areaInfo/areaInfoEditUI/'+null+'/'+parentId+'/1',
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
																			'#areaInfoSave')
																			.form(
																					{
																						 url:'${path}/bim/areaInfo/areaInfoSave',    
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
																							/* tppSysApp_list
																									.buildQueryParams(); */
																							$(
																									'#dg_tppSysAreaInfo')
																									.datagrid(
																											'load');
																							$(
																									'#dialog_holder')
																									.dialog(
																											'close');
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

			//更新
				$('#updateBut2').click(function(){
					 var edit_form_id ='#areaInfoSave';
			 
			var rowInfo = $('#dg_tppSysAreaInfo').datagrid('getChecked');
		 
			 
			if(rowInfo.length<1){
				$.messager.alert('提示','请选中一行');
			}else {

				if(rowInfo.length>1){
					$.messager.alert('提示','请选中一行');
					return;
				}
			var id=	rowInfo[0].ID;
			var parentId=$('#parentId').val();
				$('<div id="dialog_holder"></div>')
						.dialog(
								{
									title:'修改区域',
									width : 500,
									height : 300,
									href: '${path}/bim/areaInfo/areaInfoEditUI/'+id+'/'+parentId+'/1',
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
													$('#areaInfoSave')
															.form(
																	{
																		 url:'${path}/bim/areaInfo/areaInfoSave',  
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
																			/* tppSysApp_list
																					.buildQueryParams(); */
																			$(
																					'#dg_tppSysAreaInfo')
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
										    }]
										}); }
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
			
			window.location.href = contextPath+"/bim/sysApp/sysAppUpdateIsFlag/"+id+"/"+flag;
		}
	</script>
</body>
</html>
