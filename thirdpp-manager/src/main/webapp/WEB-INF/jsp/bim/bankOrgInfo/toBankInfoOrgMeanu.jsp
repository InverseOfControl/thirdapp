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
		<div class="easyui-panel" id='bankInfoPanel' title="查询条件" style="padding: 0px; width:305px; " data-options="region:'west'">
		<table id="dg_ddTBankInfoParent" class="easyui-datagrid" toolbar="#tb"
			data-options="rownumbers:true,collapsible:true,singleSelect:true,checkOnSelect:'true',sortName:'',pageSize:20,onClickRow:function(rowIndex, rowData){showInfo(rowIndex, rowData);},sortOrder:'desc',pagination:'true',url:'${path}/bim/bankInfo/bankInfoListData',method:'post',fit:true,onLoadSuccess:function(){defaultSelect()}">
			<thead>
				<tr>
				
					 <th 
						data-options="field:'ID',width:140,sortable:'true',align:'center',hidden:'true'">ID</th>  
					<th
						data-options="field:'BANK_NAME',width:250,sortable:'true',align:'center'">银行名称</th>
					 <th
						data-options="field:'BANK_CODE',width:120,sortable:'true',hidden:'true',align:'center'">银行编码</th>
			 
				</tr>
			</thead>
		</table>
		</div>
		<div data-options="region:'center'" style="width:100%;">
			<!--  中间部分（各模块主区域） -->
			 
			<!--  中间部分（各模块主区域） -->
		 	 
				
				<div id="roleTabs" data-options="fit:true" class="easyui-tabs"  style="width:700px;height:250px">
				<div  id="roleTabs_userList" title="银行机构列表" data-options="fit:true" style="text-align:center;font-size:18px;">   
			    </div>   
			     
			</div>
			 
 			 
		</div>
		<div id="tb" style=" padding:0px">    
			<div style="height:40px;">    
		    		<table>
		    		<tr>
		    		<td>
		    		  
		    		 		银行名称：<input type="text" id="sebankName" name="sebankName" class="easyui-textbox" value="${bankName}" style="width:100px;"/>  
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
		        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addBankInfo()">新增</a>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editBankInfo()">修改</a>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="deleteBankInfo()">删除</a>   
		      <!--   <a href="#" class="easyui-linkbutton" iconCls="icon-assigned" plain="true" onclick="importInfo()">导入excel</a>   --> 
		   
		  
		    </div>    
		    
		</div>   
	<script type="text/javascript">
	function defaultSelect() {
		$('#dg_ddTBankInfoParent').datagrid('selectRow', 0); 
		var row = $('#dg_ddTBankInfoParent').datagrid('getSelected');
		if (row != null) {
			showInfo(0, row);
		}
	}
	function showInfo(rowIndex, rowData) {
		var userTabs = $('#roleTabs_userList');
	 
		userTabs.panel({    
		    href:'${path}/bim/bankOrgInfo/tobankOrgInfoList/'+rowData.BANK_CODE,
		    onLoad:function(){    
		            
		    }    
		});  
		
	 
		
	}
	$('#clearBtn').click(function(){
		//清空查询条件
			$("input[name='sebankName']").val("");
			ddTBankInfo_list.buildQueryParams();
			$('#dg_ddTBankInfoParent').datagrid('load');
	});
	function onClickRow(rowNum,rowData){ 
		var obj=eval(rowData);
		var tabsPanel = $('#tabsPanel2');
		var current_tab = tabsPanel.tabs('getSelected');
		var url='${path}/bim/bankOrgInfo/tobankOrgInfoList/'+obj.BANK_CODE;
		if (tabsPanel.tabs('exists',obj.BANK_NAME)) {
			tabsPanel.tabs('select',obj.BANK_NAME);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'"  style="width:100%;height:100%;padding:0px;"></iframe>';
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
	};
	function importInfo(){
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
																			ddTBankInfo_list.buildQueryParams();
																			$('#dg_ddTBankInfoParent').datagrid('load');
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
																			ddTBankInfo_list.buildQueryParams();
																			$('#dg_ddTBankInfoParent').datagrid('load');
																			$('#dialog_holder').dialog('close');
																        });
																		}
																		
																	}
																	} else {
																	
																		$.messager.progress('close');
																		ddTBankInfo_list.buildQueryParams();
																		$('#dg_ddTBankInfoParent').datagrid('load');
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
	};
	function addBankInfo(){
		 	var add_form_id ='#searchForm2';  
			$('<div id="dialog_holder"></div>').dialog({
			    title: '新增银行信息',
			    width: 500,
			    height: 300,
			    href: '${path}/bim/bankInfo/bankInfoEditUI/',
			    params:{id:null},
			    modal: true,
			    method: "POST",
				onClose: function(){
					$(this).dialog("destroy");
				},
			    buttons: [{
			    	text: "提  交",
			    	handler: function(e){
			    		$('#searchForm2').form({   
							 url:'${path}/bim/bankInfo/bankInfoSave',    
								     onSubmit: function(){
									       
								   /*     $.messager.progress();  */
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
												ddTBankInfo_list.buildQueryParams();
												$('#dg_ddTBankInfoParent').datagrid('load');
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
			    },{
			    	text: "取 消",
			    	handler: function(e){
			    		$(this).dialog("close");
			    	}
			    }]
			});
		}
	
	function editBankInfo(){
		  	  var edit_form_id ='#searchForm2';
					var rowInfo = $("#dg_ddTBankInfoParent").datagrid('getSelections');
				 
					 
					if(rowInfo.length<1){
						$.messager.alert('提示','请选中一行');
					}else {

						if(rowInfo.length>1){
							$.messager.alert('提示','请选中一行');
							return;
						}
					var id=	rowInfo[0].ID;
			$('<div id="dialog_holder"></div>').dialog({
			    title: '修改银行信息',
			    width: 500,
			    height: 300,
			    href: '${path}/bim/bankInfo/bankInfoEditUI/',
			    modal: true,
			    method: "POST",		  
			    params:{id:id},
				onClose: function(){
					$(this).dialog("destroy");
				},
			    buttons: [{
			    	text: "保  存",
			    	handler: function(e){
			    	 
			   				$('#searchForm2').form({   
							 url:'${path}/bim/bankInfo/bankInfoSave/',    
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
											ddTBankInfo_list.buildQueryParams();
											$('#dg_ddTBankInfoParent').datagrid('load');
											$('#dialog_holder').dialog('close');
								       	}	
								     }
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
			    }		    
			    ,{
			    	text: "取 消",
			    	handler: function(e){
			    		$(this).dialog("close");
			    	}
			    }]
			}); }
		}
	
	 function deleteBankInfo() {
			var checkedItems = $('#dg_ddTBankInfoParent').datagrid('getChecked');
			                
		    if(checkedItems.length>0){
			    	  $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {
					     
				            if (data) {
				            	  $.ajax({
				                      type: 'GET',
				                      url: '${path}/bim/bankInfo/bankInfoDelete/'+   checkedItems[0].ID,
				                      data: checkedItems[0].ID,
				                      dataType: "json",
				                      success:function(data){
				                    	  $('#dg_ddTBankInfoParent').datagrid('load');  
				                      }
				                  });
				            }
				        });		   
			    }
			    
		}
 
	var ddTBankInfo_list ={};
	jQuery(function($){
 	ddTBankInfo_list.buildQueryParams=function(){
			
		    $('#dg_ddTBankInfoParent').datagrid("options").queryParams={
			 
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
			$('#dg_ddTBankInfoParent').datagrid('load');
		});  
		$('#clearBtn').click(function(){
			//清空查询条件
				$("input[name='sebankName']").val("");
				tSysRole_list.buildQueryParams();
				$('#dg_ddTBankInfoParent').datagrid('load');
		});
		//新增
 
 
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
	        }
	      });
		
	});
	</script>
</body>
	 
</html>