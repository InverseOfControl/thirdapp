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
		 
		<div class="easyui-panel" title="查询条件" style="padding: 0px; width:305px; height: 75px; margin: 0px;" data-options="region:'west'">			 
			<input type="hidden" id="hiddenName" name="hiddenName"/> 
		<table id="dg_ddTDictonary" class="easyui-datagrid"  toolbar="#tb"
			data-options="rownumbers:true,pageSize:20,singleSelect:true,collapsible:true,selectOnCheck: 'true',checkOnSelect:'true',sortName:'',onClickRow:function(rowIndex, rowData){showInfo(rowIndex, rowData);},sortOrder:'desc',pagination:'true',url:'${path}/bim/dictionary/dictionaryListData/0',method:'post',fit:true,onLoadSuccess:function(){defaultSelect()}">
			<thead>
				<tr>
						 
							<th data-options="field:'ID',width:137,sortable:'true',align:'center',hidden:'true'">ID</th>
							
							<th data-options="field:'DIC_NAME',width:175,sortable:'true',align:'center'">名称</th>	
							<th data-options="field:'DIC_CODE',width:97,sortable:'true',align:'center'">编码</th>					 
					</tr>
			</thead>
		</table>
		</div>
		<div data-options="region:'center'" title="">
			<!--  中间部分（各模块主区域） -->
			<div id="roleTabs" data-options="fit:true" class="easyui-tabs"  style="width:700px;height:250px">
				<div  id="roleTabs_userList" title="数据字典子级列表" data-options="fit:true" style="text-align:center;font-size:18px;">   
			    </div>   
		</div>
		<div id="tb" style=" padding:0px">    
			<div style="height:40px;">    
		    		<table>
		    		<tr>
		    		<td>
		    		
		    		 	名称：<input type="text" id="sedicName" name="sedicName" class="easyui-textbox" value="${bankName}" style="width:100px;"/>  	    		 	 
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
		        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addBut"	>新增</a>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="updateBut"  >修改</a>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="deleteBut();" >删除</a>   
		    </div>    
		    
		</div> 
	<script type="text/javascript">
	function showInfo(rowIndex, rowData) {
		var userTabs = $('#roleTabs_userList');
	 
		userTabs.panel({    
		    href:'${path}/bim/dictionary/toDictionaryList/'+rowData.ID,
		    onLoad:function(){    
		            
		    }    
		});  
		
	 
		
	}
	function defaultSelect() {
		$('#dg_ddTDictonary').datagrid('selectRow', 0); 
		var row = $('#dg_ddTDictonary').datagrid('getSelected');
		if (row != null) {
			showInfo(0, row);
		}
	}
	function onClickRow(rowNum,rowData){// 
		 
		var obj=eval(rowData);
	 
		var tabsPanel = $('#tabsPanel2');
		var current_tab = tabsPanel.tabs('getSelected');//bim/dictionary/dictionaryListData
		var url='${path}/bim/dictionary/toDictionaryList/'+obj.ID;
		if (tabsPanel.tabs('exists',obj.BANK_NAME)) {
			tabsPanel.tabs('select',obj.BANK_NAME);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'"  style="width:100%;height:100%;padding:0px;"></iframe>';
			if(current_tab==null){							 
				tabsPanel.tabs('add',{
					title : '数据字典子级列表',
					tab:current_tab,
					content : content ,  	
				});
			}else{							 
				tabsPanel.tabs('update',{
					title : '数据字典子级列表',
					tab:current_tab,
					 options : {  
				          content : content ,  
				      //或者 href : '';  
				     }  
					 
					
				});
			}
			
		}
	};
 
	function deleteBut() {
	 
		var rowInfo = $("#dg_ddTDictonary").datagrid('getSelections');
		 
		   /*   $.each(checkedItems, function(index, item){
		         names.push(item.ID);
		    }); */      
		    
		     
	    if(rowInfo.length>0){
		    	  $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {
				     
			            if (data) {
			            	  $.ajax({
			                      type: 'GET',
			                      url: '${path}/bim/dictionary/dictionaryDelete/'+rowInfo[0].ID,
			                      data: rowInfo[0].ID,
			                      dataType: "json",
			                      success:function(data){
			                    	  $('#dg_ddTDictonary').datagrid('load');  
			                      }
			                  });
			            }
			        });		   
		    }else{
		    	$.messager.alert('提示','请选中一行');
				return;
		    	
		    }
		    
	};

	var ddTBankInfo_list ={};
	jQuery(function($){
 	ddTBankInfo_list.buildQueryParams=function(){			
		    $('#dg_ddTDictonary').datagrid("options").queryParams={
				 
							 'id':$("input[name='search_id']").val(),
							 'bankCode':$("input[name='bankCode']").val(),
							 'sedicName':$("input[name='sedicName']").val(),
							 'beginTime':$("input[name='beginTime']").val(),
							 'endTime':$("input[name='endTime']").val(),
							 'note':$("input[name='search_note']").val(),
				};
			 
		};
		//重新按照条件刷新查询内容  
		$('#searchBut').click(function(){
			$("input[name='hiddenName']").val($("input[name='sedicName']").val()),
			ddTBankInfo_list.buildQueryParams();
			$('#dg_ddTDictonary').datagrid('load');
		});  
		//新增
		$('#addBut').click(function(){
		 	var add_form_id ='#dictionarySave';   
			$('<div id="dialog_holder"></div>').dialog({
			    title:'新增数据字典', 
			    width: 500,
			    height: 300,
			    href: '${path}/bim/dictionary/dictionaryEditUI/'+null+'/'+'0'+'/'+null,
			    modal: true,
			    method: "POST",
			   
				onClose: function(){
					$(this).dialog("destroy");
				},
			    buttons: [{
			    	text: "提  交",
			    	handler: function(e){
			    		$('#dictionarySave').form({   
							 url:'${path}/bim/dictionary/dictionarySave/0',    
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
												$('#dg_ddTDictonary').datagrid('load');
												$('#dialog_holder').dialog('close');
									       	}	
								        
								       
								     }
							});// 
						    //validate and sbumit
						     
							/*  $.messager.progress();  */

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
		});
		//更新
		 
		$('#updateBut').click(function(){
	  	   var edit_form_id ='#dictionarySave';
				var rowInfo = $("#dg_ddTDictonary").datagrid('getSelections');
				 
				if(rowInfo.length<1){
					$.messager.alert('提示','请选中一行');
				}else {

					if(rowInfo.length>1){
						$.messager.alert('提示','请选中一行');
						return;
					}
				var id=	rowInfo[0].ID;
				
	 
		$('<div id="dialog_holder"></div>').dialog({
		    title: '修改数据字典',
		    width: 500,
		    height: 300,
		    href: '${path}/bim/dictionary/dictionaryEditUI/'+id+'/'+'0'+'/'+null,
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
		   				$('#dictionarySave').form({   
		   				 url:'${path}/bim/dictionary/dictionarySave/0',    
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
										$('#dg_ddTDictonary').datagrid('load');
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
		$('#clearBtn').click(function(){
			//清空查询条件
				$("input[name='sedicName']").val("");
				ddTBankInfo_list.buildQueryParams();
				$('#dg_ddTDictonary').datagrid('load');
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