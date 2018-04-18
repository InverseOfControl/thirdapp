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
		 
		 	<!--  <div id="tb_c" style=" padding:0px">   
			 
		        <a href="#" class="easyui-linkbutton"  iconCls="icon-add" plain="true" id="addBut_pt">新增</a>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="updateBut_pt">修改</a>    
		   </div>   -->  
		<input type="hidden" id="dicCode_ptl" name="dicCode_ptl" class="easyui-textbox" value="${dicCode}" style="width: 150px;" data-options="required:true" />
		<input type="hidden" id="parentId_ptl" name="parentId_ptl" class="easyui-textbox" value="${parentId}" style="width: 150px;" data-options="required:true" />
				<%-- 	<table  id="dg_tppChannerInfo_ptl" class="easyui-datagrid"
					style="width: auto; height: 300px"
					data-options="rownumbers:true, collapsible:true,sortName:'',selectOnCheck: 'true',checkOnSelect:true,sortOrder:'desc',pagination:'true',url:'${path }/bim/channelInfo/totalprecipitationListData/${dicCode}',method:'post',toolbar:'#tb_c'
					,pageSize:20,onClickRow:function(rowIndex, rowData){ $('#dg_tppChannerInfo_ptl').datagrid('unselectRow',rowIndex);}">	
					<thead>
						<tr>	
							<th data-options="field:'ck',checkbox:'true'"></th>			
								<th
							<th data-options="field:'THIRD_TYPE_NAME',width:120,sortable:'true',align:'left'">第三方支付平台</th>
							<th data-options="field:'PRECIPITATION',width:150,sortable:'true',align:'left'" formatter="formatCurrency">月总沉淀量(元)</th>
							 
						</tr>
					</thead>
				</table> --%>
		
				<table  id="dg_tppChannerInfo_pt" class="easyui-datagrid"
					 style="width: auto; height: 400px"
					data-options="rownumbers:true, collapsible:true,sortName:'',fit:'true',selectOnCheck: 'true',checkOnSelect:true,sortOrder:'desc',pagination:'true',url:'${path }/bim/channelInfo/precipitationListData/${dicCode}',method:'post',toolbar:'#tb2_c'
					,pageSize:20,onClickRow:function(rowIndex, rowData){ $('#dg_tppChannerInfo_pt').datagrid('unselectRow',rowIndex);}">
					<thead>
						<tr>	
							<th data-options="field:'ck',checkbox:'true'"></th>			
						    <th data-options="field:'ID',width:150,hidden:'true',sortable:'true',align:'left'">ID</th>
							<th data-options="field:'CERTIFICATE_NAME',width:150,sortable:'true',align:'left'">商户名称</th>
							<!-- <th data-options="field:'THIRD_TYPE_NAME',width:150,sortable:'true',align:'left'">第三方支付平台</th> -->
							<th data-options="field:'CERTIFICATE_NO',width:150,sortable:'true',align:'left'">商户号</th>
							<th data-options="field:'PRECIPITATION',width:150,sortable:'true',align:'left'" formatter="formatCurrency">商户月资金沉淀量(元)</th>
						</tr>
					</thead>
				</table>
		 
		 	<div id="tb2_c" style=" padding:0px">    
		   <div style="height:40px;"> 
		 <form id="searchFormApp" name="searchFormApp" method="post">
				<table cellpadding="5" style="height:15px">	
					<tr>					 
						 <td style="text-align:right">商户号： </td>
									<td><input type="text" id="certificateNo" name="certificateNo" class="easyui-textbox"
										value="${certificateNo}" style="width: 100px;"
										data-options="required:true" /></td>
					</tr>
				</table>
				</form>
		    	 
		    </div> 
		    <div>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addBut2_pt">新增</a>     
		        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="updateBut2_pt">修改</a>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="searchBut2_pt">查询</a> 
		        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="clear_btn2_pt">清除</a>     
		  
		    </div>    
		   
		</div>    
			<!--搜索栏结果end--> 
			
			
 <script>
 
 var tppSysAppIps_list = {};
	jQuery(function($) {
		//定义构造查询
		tppSysAppIps_list.buildQueryParams = function() {
			$('#dg_tppChannerInfo_pt').datagrid("options").queryParams={
					'certificateNo' : $("input[name='certificateNo']").val(),
					 
				};
			
		};
		
		$('#dg_tppChannerInfo_pt').datagrid({  
	        onLoadSuccess:function(data){  
	        	 $(this).parent().find("div .datagrid-header-check").children("input[type=\"checkbox\"]").eq(0).attr("style", "display:none;");
	        },  
	    });
	
	 $('#addBut2_pt').click(function(){
					var add_form_id ='#precipitationInfoSave';
					var dicCode=$('#dicCode_ptl').val();	
					var parentId=$('#parentId_ptl').val();	
					$('<div id="dialog_holder"></div>').dialog({
					 	title:'业务沉淀量金额配置',
						width : 500,
						height : 400,
						href: '${path}/bim/channelInfo/precipitationInfoEditUI/'+null+'/'+dicCode+'/'+parentId,
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
							$(this).dialog("destroy");
						},buttons : [
							{
								text : "提  交",
								handler : function(e) {
									$('#precipitationInfoSave').form({
									    url:'${path}/bim/channelInfo/precipitationInfoSave',    
										onSubmit : function() {
										},
										success : function(data) {
											var obj=eval( "(" + data + ")" );
										  	if(obj.status){
									       		$.messager.alert('提示',"操作成功");	
									       		$.messager.progress('close');
												$('#dg_tppChannerInfo_pt').datagrid('load');
												$('#dialog_holder').dialog('close');
									       	}else{
									       		$.messager.alert('提示',obj.valmsg);
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
							},{
								text : "取 消",
								handler : function(e) {
									$(this).dialog("close");
								}
							} ]
					});
				});
	 
	 
		//更新
		$('#updateBut2_pt').click(function(){
			var rowInfo = $("#dg_tppChannerInfo_pt").datagrid('getChecked');
			if(rowInfo.length<1){
				$.messager.alert('提示','请选中一行');
			}else {
	
				if(rowInfo.length>1){
					$.messager.alert('提示','请选中一行');
					return;
				}
			var id=	rowInfo[0].ID;
			var parentId=$('#parentId_ptl').val();	
			var dicCode=$('#dicCode_ptl').val();	
				$('<div id="dialog_holder"></div>')
						.dialog(
								{
									title:'修改沉淀量信息',
									width : 500,
									height : 400,
									href: '${path}/bim/channelInfo/precipitationInfoEditUI/'+id+'/'+dicCode+'/'+parentId,
									modal : true,
									method : "POST",
									onClose : function() {
										$(this).dialog("destroy");
									},
									buttons : [
												{
													text : "保  存",
													handler : function(e) {
														$('#precipitationInfoSave')
																.form(
																		{
																			 url:'${path}/bim/channelInfo/precipitationInfoUpdate',      
																			onSubmit : function() {
																				 
																				// do some check       
																				// return false to prevent submit;    
																			},
																			success : function(data) {
																				    var obj=eval( "(" + data + ")" );
																				  	if(obj.status){
																				  		$.messager.alert('提示',"操作成功");
																			       		$.messager.progress('close');
																						$('#dg_tppChannerInfo_pt').datagrid('load');
																						$('#dialog_holder').dialog('close');
																			       	}else{
																			       		$.messager.alert('提示',obj.valmsg);
																			       	}
																				
																			}
																		});// 
														//validate and sbumit
														/* if ($(edit_form_id).form(
																"validate") == true) {
															$.messager.progress();
															$(edit_form_id)
																	.submit();
														}
														; */
														$('#precipitationInfoSave').submit();
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
		
		
		//重新按照条件刷新查询内容
		$('#searchBut2_pt').click(
				function() {
					tppSysAppIps_list.buildQueryParams();
					$('#dg_tppChannerInfo_pt').datagrid('load');
		});
		
		//重置查询条件并刷新内容
		$(' #clear_btn2_pt').click(
				function() {
					//清空查询条件
					 $("input[name='certificateNo']").val("");
					tppSysAppIps_list.buildQueryParams();
					$(' #dg_tppChannerInfo_pt').datagrid('load');
		});
		
		
		 $('#addBut_pt').click(function(){
				var add_form_id ='#totalPrecipitationInfoSave';
				var dicCode=$('#dicCode_ptl').val();	
				var parentId=$('#parentId_ptl').val();	
				
				$('<div id="dialog_holder"></div>').dialog({
				 	title:'业务沉淀量金额配置',
					width : 500,
					height : 400,
					href: '${path}/bim/channelInfo/pChannelInfoEditUI/'+null+'/'+dicCode+'/'+parentId,
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
						$(this).dialog("destroy");
					},buttons : [
						{
							text : "提  交",
							handler : function(e) {
								$('#totalPrecipitationInfoSave').form({
								    url:'${path}/bim/channelInfo/totalPrecipitationInfoSave',    
									onSubmit : function() {
									},
									success : function(data) {
										var obj=eval( "(" + data + ")" );
									  	if(obj.status){
								       		$.messager.alert('提示',obj.valmsg);	
								       		$.messager.progress('close');
											$('#dg_tppChannerInfo_pt').datagrid('load');
											$('#dialog_holder').dialog('close');
								       	}else{
								       		$.messager.alert('提示',obj.valmsg);
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
						},{
							text : "取 消",
							handler : function(e) {
								$(this).dialog("close");
							}
						} ]
				});
			});


	//更新
	$('#updateBut_pt').click(function(){
		var rowInfo = $("#dg_tppChannerInfo_ptl").datagrid('getChecked');
		if(rowInfo.length<1){
			$.messager.alert('提示','请选中一行');
		}else {

			if(rowInfo.length>1){
				$.messager.alert('提示','请选中一行');
				return;
			}
		var id=	rowInfo[0].ID;
		console.info('id',id);
		var parentId=$('#parentId_ptl').val();	
		var dicCode=$('#dicCode_ptl').val();	
			$('<div id="dialog_holder"></div>')
					.dialog(
							{
								title:'修改沉淀量信息',
								width : 500,
								height : 400,
								href: '${path}/bim/channelInfo/pChannelInfoEditUI/'+id+'/'+dicCode+'/'+parentId,
								modal : true,
								method : "POST",
								onClose : function() {
									$(this).dialog("destroy");
								},
								buttons : [
											{
												text : "保  存",
												handler : function(e) {
													$('#totalPrecipitationInfoSave')
															.form(
																	{
																		 url:'${path}/bim/channelInfo/totalPrecipitationInfoSave',      
																		onSubmit : function() {
																			 
																			// do some check       
																			// return false to prevent submit;    
																		},
																		success : function(data) {
																			    var obj=eval( "(" + data + ")" );
																			  	if(obj.status){
																		       		$.messager.alert('提示',obj.valmsg);	
																		       		$.messager.progress('close');
																					$('#dg_tppChannerInfo_ptl').datagrid('load');
																					$('#dialog_holder').dialog('close');
																		       	}else{
																		       		$.messager.alert('提示',obj.valmsg);
																		       	}
																			
																		}
																	});// 
													//validate and sbumit
													/* if ($(edit_form_id).form(
															"validate") == true) {
														$.messager.progress();
														$(edit_form_id)
																.submit();
													}
													; */
													$('#totalPrecipitationInfoSave').submit();
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

 
	});
	</script>
</body>
</html>
 