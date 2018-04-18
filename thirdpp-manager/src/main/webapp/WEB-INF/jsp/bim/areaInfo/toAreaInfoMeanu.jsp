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
		<!-- <div  data-options="region:'west',split:true" 
			style="width: 400px;"> -->
			<!-- 权限 -->
	<div class="easyui-panel"   title="查询条件" style="padding: 0px; width:320px;   margin: 0px;" data-options="region:'west'">
	 
		<table id="dg_tppSysArea" class="easyui-datagrid"  toolbar="#tbp"
		 	data-options="rownumbers:true,singleSelect:true,pageSize:20,  selectOnCheck: 'true',checkOnSelect:'true',sortName:'',onClickRow:function(rowIndex, rowData){showInfo(rowIndex, rowData);},sortOrder:'desc',pagination:'true',url:'${path}/bim/areaInfo/areaInfoListData/0',method:'post',fit:true,onLoadSuccess:function(){defaultSelect()}">
			<thead>
				<tr>				 
				  <th 
						data-options="field:'ID',width:120,sortable:'true',hidden:true,align:'right'">ID</th>   
					
						<th
							data-options="field:'AREA_NAME',width:185,sortable:'true',align:'center'">省份名称</th>	
					 <th
							data-options="field:'AREA_CODE',width:82,sortable:'true',align:'center'">省份编码</th>			 
							 
				</tr>
			</thead>
		</table>
		</div>	  
	 
		<div data-options="region:'center'" title="">
			<!--  中间部分（各模块主区域） -->
		 	 
				
				<div id="roleTabs" data-options="fit:true" class="easyui-tabs"  style="width:700px;height:250px">
				<div  id="roleTabs_userList" title="省份区域信息" data-options="fit:true" style="text-align:center;font-size:18px;">   
			    </div>   
			     
			</div>
			 
 			 
		</div>
		<div id="tbp" style=" padding:0px">    
			<div style="height:40px;">    
		    		<table>
		    		<tr>
		    		<td>
		    	 
		    		 	省份名称:<input type="text" id="provinceName" name="provinceName" class="easyui-textbox" value="${provinceName}" style="width:100px;"/>  	    		 	 
		    		</td>
		    		<td>
		    			 <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="searchBut">查询</a> 
		    			
		    		</td>
		    		
		    		<td>
		    		  <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="clearBtn">清除</a>   
		    		</td>
		      			
		    		
		    		</tr>
		    			
		    		</table>
		      
		       
		    </div> 
		    <div>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addBut"	 >新增</a>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"	id="updateBut" >修改</a>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="deleteBut();"  id="delBut"  >删除</a>   
		    </div>    
		    
		</div>   
	<script type="text/javascript">
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
		iconCls : 'icon-save',
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
		iconCls : 'icon-remove',
	
	},'-',{ 
       
    },
    '-',{
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
	} ];
	function showInfo(rowIndex, rowData) {
		var userTabs = $('#roleTabs_userList');
	 
		userTabs.panel({    
		    href:'${path}/bim/areaInfo/toAreaInfoList/' + rowData.AREA_CODE,    
		    onLoad:function(){    
		            
		    }    
		});  
		
	 
		
	}
	
	function defaultSelect() {
		$('#dg_tppSysArea').datagrid('selectRow', 0); 
		var row = $('#dg_tppSysArea').datagrid('getSelected');
		if (row != null) {
			showInfo(0, row);
		}
	}
	 function deleteBut() {
			var checkedItems = $('#dg_tppSysArea').datagrid('getChecked');
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
				                    	  $('#dg_tppSysArea').datagrid('load');  
				                      }
				                  });
				            }
				        });		   
			    }
			    
		};
	function onClickRow(rowNum,rowData){ 
	 
		var obj=eval(rowData);
		var tabsPanelIps = $('#tabsPanel2');
		var current_tabIps = tabsPanelIps.tabs('getSelected');
		var urlIps='${path}/bim/areaInfo/toAreaInfoList/'+obj.AREA_CODE;
		if (tabsPanelIps.tabs('exists',obj.AREA_NAME)) {
			tabsPanelIps.tabs('select',obj.AREA_NAME);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+urlIps+'"  style="width:100%;height:100%;padding:0px;"></iframe>';
			if(current_tabIps==null){							 
				tabsPanelIps.tabs('add',{
					title : '省份区域信息',
					tab:current_tabIps,
					content : content ,  
					 
					
				});
			}else{							 
				tabsPanelIps.tabs('update',{
					title : '省份区域信息',
					tab:current_tabIps,
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
			
		    $('#dg_tppSysArea').datagrid("options").queryParams={
				 
							 'id':$("input[name='search_id']").val(),
							 'bankCode':$("input[name='bankCode']").val(),
							 'areaName':$("input[name='provinceName']").val(),
							 'beginTime':$("input[name='beginTime']").val(),
							 'endTime':$("input[name='endTime']").val(),
							 'note':$("input[name='search_note']").val(),
				};
			 
		};
		//重新按照条件刷新查询内容
		$('#searchBut').click(function(){
			ddTBankInfo_list.buildQueryParams();
			$('#dg_tppSysArea').datagrid('load');
		});  
		$('#clearBtn').click(function(){
			//清空查询条件
				$("input[name='provinceName']").val("");
				ddTBankInfo_list.buildQueryParams();
				 $('#dg_tppSysArea').datagrid('load');  
		});
		//新增
					$('#addBut')
					.click(
							function() {
								  var add_form_id ='#areaInfoSave';
							  var parentId=$('#parentId').val();
								$('<div id="dialog_holder"></div>')
										.dialog(
												{
											 		title:'新增区域',
													width : 500,
													height : 300,
													href: '${path}/bim/areaInfo/areaInfoEditUI/'+null+'/'+parentId+'/0',
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
																									'#dg_tppSysArea')
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
					 var edit_form_id ='#areaInfoSave';
			var rowInfo = $("#dg_tppSysArea").datagrid('getSelections');
		 
			 
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
									href: '${path}/bim/areaInfo/areaInfoEditUI/'+id+'/'+parentId+'/0',
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
																					'#dg_tppSysArea')
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