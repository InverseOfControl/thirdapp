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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body class="easyui-layout"> 
			<!--搜索结果开始-->
			<%-- <div class="easyui-panel" title="查询条件" style="padding:5px;height:75px;margin: 0px;" data-options="region:'north'">
				<form id="searchFormApp" name="searchFormApp" method="post">
				<table cellpadding="5" style="height:15px">	
					<tr>					 
						 <td>通道名称： </td>
									<td><input type="text" id="channelName" name="channelName" class="easyui-textbox"
										value="${channelName}" style="width: 150px;"
										data-options="required:true" /></td>
										<input type="hidden" id="dicCode" name="dicCode" class="easyui-textbox"
										value="${dicCode}" style="width: 150px;"
										data-options="required:true" />
										<input type="hidden" id="parentId" name="parentId" class="easyui-textbox"
										value="${parentId}" style="width: 150px;"
										data-options="required:true" />
									<td>状态： </td>
									<td> 
										 <input style="width: 200px" name="status"  id="status" 
															class="easyui-combobox" id="priority"
															data-options="editable:false,data:[{'type':'','text':'请选择'},{'type':'0','text':'关闭'},{'type':'1','text':'开启'} 
															 ],	valueField:'type',textField:'text',value:'${status}'" />
										</td>
										<td>商户类型： </td>
										<td><input id="merchantType" name="merchantType" style="width: 200px" 
										class="easyui-combobox" data-options="
											editable:false,valueField: 'value',
											valueField: 'value',
										   textField: 'name',
										   url:'${path}/enumset/merType',
										   value:'${merchantType}'
										     "           											 
							 	 	/></td>		
										
					</tr>
				</table>
				</form>
			</div> --%>
		<!-- 	<div class="easyui-panel" style="padding: 0px; margin: 0px;"
				data-options="region:'center'"> -->
				<table  id="dg_tppChannerInfo" class="easyui-datagrid"
					 style="width: auto; height: 300px"
					data-options="rownumbers:true, collapsible:true,sortName:'',selectOnCheck: 'true',checkOnSelect:true,sortOrder:'desc',pagination:'true',url:'${path }/bim/channelInfo/channelInfoListData/${dicCode}',method:'post',fit:'true',toolbar:'#tb2'
					,pageSize:20,onClickRow:function(rowIndex, rowData){ $('#dg_tppChannerInfo').datagrid('unselectRow',rowIndex);}">
					<thead>
						<tr>	
							<th data-options="field:'ck',checkbox:'true'"></th>			
								<th
								data-options="field:'AC',width:150,sortable:'true',align:'center'">操作</th>	
									<th data-options="field:'STATUS',width:120,sortable:'true',align:'left'">状态</th> 	 
							<th data-options="field:'CHANNEL_NAME',width:200,sortable:'true',align:'left'">通道名称</th>
							<th data-options="field:'THIRD_TYPE_NAME',width:120,sortable:'true',align:'left'">第三方支付平台</th>
							<th data-options="field:'MERCHANT_TYPE',width:120,sortable:'true',align:'left'">商户类型</th>
							<th data-options="field:'USER_NAME',width:120,sortable:'true',align:'left'">用户名</th>
							<th data-options="field:'USER_PWD',width:120,sortable:'true',align:'left'">用户密码</th>
							<th data-options="field:'CERTIFICATE_NAME',width:150,sortable:'true',align:'left'">证书名称</th>
							<th data-options="field:'CERTIFICATE_PWD',width:140,sortable:'true',align:'left'">证书密码</th>
							<th data-options="field:'CERTIFICATE_NO',width:140,sortable:'true',align:'left'">商户号</th>
						
							<th data-options="field:'CREATER',width:120,sortable:'true',align:'center'">创建人</th>
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'left'">创建时间</th>
							<th data-options="field:'UPDATE_TIME',width:120,sortable:'true',align:'left'">修改时间</th>
							<th data-options="field:'UPDATER',width:110,sortable:'true',align:'left' ">修改人</th>
							 
						</tr>
					</thead>
				</table>
		 
			<div id="tb2" style=" padding:0px">    
		   <div style="height:40px;"> 
		 <form id="searchFormApp" name="searchFormApp" method="post">
				<table cellpadding="5" style="height:15px">	
					<tr>					 
						 <td style="text-align:right">通道名称： </td>
									<td><input type="text" id="channelName" name="channelName" class="easyui-textbox"
										value="${channelName}" style="width: 100px;"
										data-options="required:true" /></td>
										<input type="hidden" id="dicCode" name="dicCode" class="easyui-textbox"
										value="${dicCode}" style="width: 150px;"
										data-options="required:true" />
										<input type="hidden" id="parentId" name="parentId" class="easyui-textbox"
										value="${parentId}" style="width: 150px;"
										data-options="required:true" />
									<td style="text-align:right">状态： </td>
									<td> 
										 <input style="width: 100px" name="status"  id="status" 
															class="easyui-combobox" id="priority"
															data-options="editable:false,data:[{'type':'','text':'请选择'},{'type':'0','text':'关闭'},{'type':'1','text':'开启'} 
															 ],	valueField:'type',textField:'text',value:'${status}'" />
										</td>
										<%-- <td>商户类型： </td>
										<td><input id="merchantType" name="merchantType" style="width: 200px" 
										class="easyui-combobox" data-options="
											editable:false,valueField: 'value',
											valueField: 'value',
										   textField: 'name',
										   url:'${path}/enumset/merType',
										   value:'${merchantType}'
										     "           											 
							 	 	/></td>	 --%>	
										
					</tr>
				</table>
				</form>
		    	 
		    </div> 
		    <div>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addBut2">新增</a>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="updateBut2">修改</a>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="searchBut2">查询</a> 
		        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="clear_btn2">清除</a>     
		      <!--   <a href="#" class="easyui-linkbutton" iconCls="icon-assigned" plain="true" onclick="importInfo()">导入excel</a>   --> 
		   
		  
		    </div>    
		   
		</div>  
			<!--搜索栏结果end--> 
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
	}/* ,{
		id : 'delBut',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : function() {
			var checkedItems = $('#dg_tppChannerInfo').datagrid('getChecked');
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
				                      url: '${path}/bim/channelInfo/channelInfoDelete/'+names,
				                      data: names,
				                      dataType: "json",
				                      success:function(data){
				                    	  $('#dg_tppChannerInfo').datagrid('load');  
				                      }
				                  });
				            	  
				            }
				        });		   
			    }else {
			    	
			    	  $.messager.alert("提示","请选中一行！");
			    	  return;
			    }
			    
		}
	} */,'-',{
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
				$('#dg_tppChannerInfo').datagrid("options").queryParams={
				 
						'channelName' : $("input[name='channelName']").val(),
						'status' : $("input[name='status']").val(),
						'merchantType' : $("input[name='merchantType']").val()
						 
					};
				
			};

			//定义构造查询JSON
		 
			//重新按照条件刷新查询内容
			$('#searchBut2').click(
					function() {
						tppSysAppIps_list.buildQueryParams();
						$('#dg_tppChannerInfo')
								.datagrid('load');
					});

			//重置查询条件并刷新内容
			$(' #clear_btn2').click(
					function() {
						//清空查询条件

						 $("input[name='channelName']").val("");
						$('#status').combobox('setValue', '');	
						$('#merchantType').combobox('setValue', '');
						tppSysAppIps_list.buildQueryParams();
						$(' #dg_tppChannerInfo')
								.datagrid('load');
					});

			//新增
					$('#addBut2')
					.click(
							function() {
								var add_form_id ='#channelInfoSave';
								var dicCode=$('#dicCode').val();	
								var parentId=$('#parentId').val();	
								
								$('<div id="dialog_holder"></div>')
										.dialog(
												{
											 		title:'新增通道信息',
													width : 500,
													height : 400,
													href: '${path}/bim/channelInfo/channelInfoEditUI/'+null+'/'+dicCode+'/'+parentId,
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
																handler : function(e) {
																	$('#channelInfoSave').form({
																					    url:'${path}/bim/channelInfo/channelInfoSave',    
																						onSubmit : function() {
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
																									'#dg_tppChannerInfo')
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
																        		 novalidate:false,  
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
			var rowInfo = $("#dg_tppChannerInfo").datagrid('getChecked');
			if(rowInfo.length<1){
				$.messager.alert('提示','请选中一行');
			}else {

				if(rowInfo.length>1){
					$.messager.alert('提示','请选中一行');
					return;
				}
			var id=	rowInfo[0].ID;
			var parentId=$('#parentId').val();	
			var dicCode=$('#dicCode').val();	
				$('<div id="dialog_holder"></div>')
						.dialog(
								{
									title:'修改通道信息',
									width : 500,
									height : 400,
									href: '${path}/bim/channelInfo/channelInfoEditUI/'+id+'/'+dicCode+'/'+parentId,
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
													$('#channelInfoSave')
															.form(
																	{
																		 url:'${path}/bim/channelInfo/channelInfoSave',      
																		onSubmit : function() {
																			 
																			// do some check       
																			// return false to prevent submit;    
																		},
																		success : function(data) {
																				
																			     var obj=eval( "(" + data + ")" );
																			  	if(obj.valmsg!=null){
																		       		$.messager.alert('提示',obj.valmsg);	
																		       	}else{
																			$.messager
																					.progress('close');
																			/* tppSysApp_list
																					.buildQueryParams(); */
																			$(
																					'#dg_tppChannerInfo')
																					.datagrid(
																							'load');
																			$(
																					'#dialog_holder')
																					.dialog(
																							'close');
																		}}
																	});// 
													//validate and sbumit
													/* if ($(edit_form_id).form(
															"validate") == true) {
														$.messager.progress();
														$(edit_form_id)
																.submit();
													}
													; */
													$('#channelInfoSave').submit();
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
									width : 500,
									height : 300,
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
		function updateFlag(id,status){
			  $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {
				  if (data) {
			  $.ajax({
                  type: 'GET',
                  url: '${path}/bim/channelInfo/channelInfoUpdateStatus/'+id+'/'+status,
            
                  dataType: "json",
                  success:function(data){
                	  $('#dg_tppChannerInfo').datagrid('load');  
                  }
              });
				  }
			  });
		} 
		function toAppNameUI(code){
		 
			$('<div id="dialog_holder"></div>').dialog({
			    title: '',
			    width: 200,
			    height: 200,			    
			    href: '${path}/bim/categoryApp/appsNameEditUI/'+code,
			    modal: true,
			    method: "POST",
			    buttons: [{
			    	text: "关闭",
			    	handler: function(e){
			    		$(this).dialog("close");
			    	}
			    }]
			});
		} 
		function updateAppIp(id) {

			window.location.href = contextPath
					+ "/bim/appIps/appIpsEditUI/" + id;

		}
		
	</script>
</body>
</html>
 