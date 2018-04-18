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
		<div class="easyui-panel"  style="padding: 0px; width:180px; height: 75px; margin: 0px;" data-options="region:'west'">
			 
		<table id="dg_ddTBankInfo" class="easyui-datagrid"   
			data-options="collapsible:true,singleSelect:true,selectOnCheck: 'true',checkOnSelect:'true',sortName:'',onClickRow:function(rowIndex, rowData){onClickRow(rowIndex, rowData);},sortOrder:'desc',url:'${path}/bim/dictionary/dictionaryDicType/3',method:'post',fit:true,onLoadSuccess:function(){defaultSelect()}">
			<thead>
				<tr>				 
					 <th 	
						data-options="field:'ID',width:120,sortable:'true',hidden:true,align:'right'">ID</th>    
					 <th
						data-options="field:'DIC_CODE',width:20,sortable:'true',hidden:true,align:'right'">编码</th>
					<th
						data-options="field:'DIC_NAME',width:175,sortable:'true',align:'center'">第三方支付平台</th>
				<!-- 	<th
						data-options="field:'DT_TIME',width:120,sortable:'true',align:'right'">日期</th>
					<th
						data-options="field:'NOTE',width:120,sortable:'true',align:'right'">备注</th> -->
				</tr>
			</thead>
		</table>
		</div>	
 		<!-- </div> -->
		<div data-options="region:'center'" title="">
			<!--  中间部分（各模块主区域） -->
			<div id="tabsPanel2" class="easyui-tabs"
				data-options="fit:true"
				style="width: 700px; height: 250px"></div>
		</div>
	<script type="text/javascript">
	function defaultSelect() {
		$('#dg_ddTBankInfo').datagrid('selectRow', 0); 
		var row = $('#dg_ddTBankInfo').datagrid('getSelected');
		if (row != null) {
			onClickRow(0, row);
		}
	}
	function onClickRow(rowNum,rowData){ 
		 
		var obj=eval(rowData);
	 
		var tabsPanel = $('#tabsPanel2');
		var current_tab = tabsPanel.tabs('getSelected');
		var url='${path}/bim/categoryApp/toCategoryAppsList/'+obj.DIC_CODE+'/'+obj.ID;
		if (tabsPanel.tabs('exists',obj.DIC_TYPE)) {
			tabsPanel.tabs('select',obj.DIC_TYPE);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'"  style="width:100%;height:100%;padding:0px;"></iframe>';
			if(current_tab==null){							 
				tabsPanel.tabs('add',{
					title : '系统信息类别列表',
					tab:current_tab,
					content : content ,  
					 
					
				});
			}else{							 
				tabsPanel.tabs('update',{
					title : '系统信息类别列表',
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
		$('#addBut').click(function(){
		/* 	var add_form_id ='#addDdTBankInfoFrom'; */		 
			$('<div id="dialog_holder"></div>').dialog({
			    title: 'DD_T_BANK_INFO',
			    width: 800,
			    height: 500,
			    href: '${path}/bim/bankInfo/bankInfoEditUI/'+null,
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
												$('#dg_ddTBankInfo').datagrid('load');
												$('#dialog_holder').dialog('close');
									       	}	
								        
								       
								     }
							});// 
						    //validate and sbumit
						     
							/*  $.messager.progress();  */
							$('#searchForm2').submit();
						/*     if($(add_form_id).form("validate")==true){
							  	$.messager.progress();
								$(add_form_id).submit();
							};  */  
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
	 //   var edit_form_id ='#editDdTBankInfoFrom';
				var rowInfo = $("#dg_ddTBankInfo").datagrid('getSelections');
			 
				 
				if(rowInfo.length<1){
					$.messager.alert('提示','请选中一行');
				}else {

					if(rowInfo.length>1){
						$.messager.alert('提示','请选中一行');
						return;
					}
				var id=	rowInfo[0].ID;
				
	 
		$('<div id="dialog_holder"></div>').dialog({
		    title: 'DdTBankInfo',
		    width: 800,
		    height: 500,
		    href: '${path}/bim/bankInfo/bankInfoEditUI/'+id,
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
		   				$('#searchForm2').form({   
						 url:'${path}/bim/bankInfo/bankInfoSave',    
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
										$('#dg_ddTBankInfo').datagrid('load');
										$('#dialog_holder').dialog('close');
							       	}	
							     }
						});// 
					 
						/*  $.messager.progress();  */
						$('#searchForm2').submit();
					    //validate and sbumit
					   /*  if($(edit_form_id).form("validate")==true){
						  	$.messager.progress();
							
						};   */ 
		    		
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
				}
	        	
	          
	        }
	      });
		
	});
	</script>
</body>
	 
</html>