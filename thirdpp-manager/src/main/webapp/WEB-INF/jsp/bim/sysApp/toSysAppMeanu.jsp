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
	 <div class="easyui-panel"  style="padding: 0px; width:180px;  margin: 0px;" data-options="region:'west'">
		<table id="dg_tppSysApp" class="easyui-datagrid"  
		 	data-options="collapsible:true,singleSelect:true,pageSize:20,checkOnSelect:'true',sortName:'',onClickRow:function(rowIndex, rowData){showInfo(rowIndex, rowData);},sortOrder:'desc',url:'${path}/bim/sysApp/getSysAppMeanu',toolbar:toolbar,method:'post',fit:true,onLoadSuccess:function(){defaultSelect()}">
			<thead>
				<tr>				 
				  <th 
						data-options="field:'ID',width:120,sortable:'true',hidden:true,align:'right'">ID</th>   
					 
							<th
								data-options="field:'APP_NAME',width:175,sortable:'true',align:'center'">业务系统名称</th>			 
				</tr>
			</thead>
		</table>
		</div>	  
	 
		<div data-options="region:'center'" title="">
			<!--  中间部分（各模块主区域） -->
		 
		<!-- 	<div data-options="region:'North',split:true,title:'South Title'"  >
		 	   <div id="tabsPanel2" class="easyui-tabs"
				data-options=" fit:true"
				style="width: 700px; height: 650px"></div> 								  
			</div> -->
			<div data-options="region:'North',split:true,title:'South Title'"  >
			<div id="roleTabs" data-options="fit:true" class="easyui-tabs"  style="width:700px;height:650px">
				<div  id="roleTabs_userList" title="业务系统IP白名单" data-options="fit:true" style="text-align:center;font-size:18px;">   
			    </div>   
			     
			</div>
			</div>
 			<div data-options="region:'South',split:true,title:'South Title' "  >	
			 <div id="tabsPanel3" class="easyui-tabs" 
				data-options="tools:'#tab-tools',fit:true"
				style="width: 700px; height: 150px">
					<div  id="ipsTabs_userList" title="业务系统详细信息" data-options="fit:true" style="text-align:center;font-size:18px;">   
			    	</div>
				</div>  
			</div> 
		</div>
		
	<script type="text/javascript">
	function showInfo(rowIndex, rowData) {
		var userTabs = $('#roleTabs_userList');
		var ipsTabs = $('#ipsTabs_userList');
		
		userTabs.panel({    
		    href:'${path}/bim/appIps/appIpsList/'+rowData.APP_CODE,
		    onLoad:function(){    
		            
		    }    
		});  
		ipsTabs.panel({    
		    href:'${path}/bim/sysApp/sysAppListObj/'+rowData.ID,
		    onLoad:function(){    
		            
		    }    
		});  
		
	 
		
	}
	function defaultSelect() {
		$('#dg_tppSysApp').datagrid('selectRow', 0); 
		var row = $('#dg_tppSysApp').datagrid('getSelected');
		if (row != null) {
			showInfo(0, row);
		}
	}
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
			var checkedItems = $('#dg_tppSysApp').datagrid('getChecked');
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
				                      url: '${path}/bim/sysApp/sysAppDelete/'+names,
				                      data: names,
				                      dataType: "json",
				                      success:function(data){
				                    	  $('#dg_tppSysApp').datagrid('load');  
				                      }
				                  });
				            }
				        });		   
			    }
			    
		}
	} ];
	function onClickRow(rowNum,rowData){ 
	 
		var obj=eval(rowData);
		var tabsPanelIps = $('#tabsPanel2');
		var current_tabIps = tabsPanelIps.tabs('getSelected');
		var urlIps='${path}/bim/appIps/appIpsList/'+obj.APP_CODE;
		if (tabsPanelIps.tabs('exists',obj.APP_NAME)) {
			tabsPanelIps.tabs('select',obj.APP_NAME);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+urlIps+'"  style="width:100%;height:100%;padding:0px;"></iframe>';
			if(current_tabIps==null){							 
				tabsPanelIps.tabs('add',{
					title : '业务系统IP白名单',
					tab:current_tabIps,
					content : content ,  
					 
					
				});
			}else{							 
				tabsPanelIps.tabs('update',{
					title : '业务系统IP白名单',
					tab:current_tabIps,
					 options : {  
				          content : content ,  
				      //或者 href : '';  
				     }  
					 
					
				});
			}
			
		}
		
		var tabsPanel = $('#tabsPanel3');
		var current_tab = tabsPanel.tabs('getSelected');
		var urlApp='${path}/bim/sysApp/sysAppListObj/'+obj.ID;
		if (tabsPanel.tabs('exists',obj.APP_NAME)) {
			tabsPanel.tabs('select',obj.APP_NAME);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+urlApp+'"  style="width:100%;height:100%;padding:0px;"></iframe>';
			if(current_tab==null){							 
				tabsPanel.tabs('add',{
					title : '业务系统详细信息',
					tab:current_tab,
					content : content ,  
					 
					
				});
			}else{							 
				tabsPanel.tabs('update',{
					title : '业务系统详细信息',
					tab:current_tab,
					 options : {  
				          content : content ,  
				      //或者 href : '';  
				     }  
					 
					
				});
			}
			
		}
	};


	var ddTBankInfo_list ={};
	jQuery(function($){
 	ddTBankInfo_list.buildQueryParams=function(){
			
		    $('#dg_ddTBankInfo').datagrid("options").queryParams={
			 
							 'id':$("input[name='search_id']").val(),
							 'bankCode':$("input[name='bankCode']").val(),
							 'sebankName':$("input[name='sebankName']").val(),
							 'beginTime':$("input[name='beginTime']").val(),
							 'endTime':$("input[name='endTime']").val(),
							 'note':$("input[name='search_note']").val(),
				};
			 
		};
		//重新按照条件刷新查询内容
		$('#searchBut').click(function(){
			ddTBankInfo_list.buildQueryParams();
			$('#dg_ddTBankInfo').datagrid('load');
		});  
		//新增
					$('#addBut')
					.click(
							function() {
								 var add_form_id ='#sysAppSave';
								$('<div id="dialog_holder"></div>')
										.dialog(
												{
											 		title:'新增',
													width : 500,
													height : 400,
													href: '${path}/bim/sysApp/sysAppEditUI/'+null,
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
																			'#sysAppSave')
																			.form(
																					{
																						 url:'${path}/bim/sysApp/sysAppSave',    
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
																									'#dg_tppSysApp')
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
		 
				$('#updateBut').click(function(){
					  var edit_form_id ='#sysAppSave';
			var rowInfo = $("#dg_tppSysApp").datagrid('getSelections');
 
			 
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
									title:'修改',
									width : 500,
									height : 400,
								    href: '${path}/bim/sysApp/sysAppEditUI/'+id,
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
													$('#sysAppSave')
															.form(
																	{
																		 url:'${path}/bim/sysApp/sysAppSave',    
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
																					'#dg_tppSysApp')
																					.datagrid(
																							'load');
																			$(
																					'#dialog_holder')
																					.dialog(
																							'close');
																		}}
																	});// 
													//validate and sbumit
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
										    }]
										}); }
		});
		var treeContainer = $('#treeContainer');
		//Tab选项卡对象
		var tabsPanel = $('#tabsPanel2');
		$(".easyui-tree").tree({
	        onBeforeExpand:function(node,param){
	          //$("#tree1").tree('options').url = "/@info/Home/GetTreeByEasyui?id=1";
	        },   
	        onClick : function(node){  
	        	if (treeContainer.tree('isLeaf',node.target)) {
					//点击子菜单打开对应的地址
					var attributes = node.attributes;
					if (attributes) {
						var menuId = attributes.id;
						var menuText = node.text;
						var current_tab = tabsPanel.tabs('getSelected');
					 
						var isUpdate='';
					 
						var menuUrl = attributes.url;
						if (tabsPanel.tabs('exists',menuText)) {
							tabsPanel.tabs('select',menuText);
						} else {
							var content = '<iframe scrolling="auto" frameborder="0"  src="'+menuUrl+'"  style="width:100%;height:100%;padding:0px;"></iframe>';
							if(current_tab==null){							 
								tabsPanel.tabs('add',{
									title : '银行机构列表',
									tab:current_tab,
									content : content ,  
									 
									
								});
							}else{							 
								tabsPanel.tabs('update',{
									title : '银行机构列表',
									tab:current_tab,
									 options : {  
								          content : content ,  
								      //或者 href : '';  
								     }  
									 
									
								});
							}
							
						}
					}
				} else {
					//点击父菜单无事件
				}
	        	
	          /*if (node.state == 'closed'){  
	            $(this).tree('expand', node.target);  
	          }else if (node.state == 'open'){  
	            $(this).tree('collapse', node.target);  
	          }else{
	            var tabTitle = node.text;
	            var url = "../../" + node.attributes;
	            var icon = node.iconCls;
	            alert(1);
	            //addTab(tabTitle, url, icon);
	          }*/
	        }
	      });
		
	});
	</script>
</body>
	 
</html>