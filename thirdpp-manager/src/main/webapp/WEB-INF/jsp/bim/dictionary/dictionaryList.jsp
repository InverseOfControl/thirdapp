<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	 
			
			<!--搜索结果开始-->
			 
				<table  id="dg_ddTDictionary" class="easyui-datagrid"   style="width:auto;height:300px"
					data-options="rownumbers:true, selectOnCheck: 'true',checkOnSelect:true,collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'<%=path%>/bim/dictionary/dictionaryListData/${id}',method:'post',toolbar:'#tb2',fit:'true'
							,pageSize:20,onClickRow:function(rowIndex, rowData){ $('#dg_ddTDictionary').datagrid('unselectRow',rowIndex);},
							onLoadSuccess:function(data){ $('#dg_ddTDictionary').datagrid('doCellTip',{cls:{'background-color':'white'},delay:1000,tdname:['DIC_DESC']});  } 
					">
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:'true'"></th>
							<th data-options="field:'ID',width:150,sortable:'true',align:'right',hidden:'true'">ID</th>
							<th data-options="field:'DIC_CODE',width:160,sortable:'true',align:'right'">编码</th>
							<th data-options="field:'DIC_NAME',width:160,sortable:'true',align:'right'">名称</th>
							<th data-options="field:'PARENT_NAME',width:165,sortable:'true',align:'right'">父类字典</th>
							<th data-options="field:'DIC_DESC',width:350,sortable:'true',align:'center'">备注</th>
							
						</tr>
					</thead>
				</table>
		 
			<!--搜索栏结果end-->
				<div id="tb2" style=" padding:0px">    
		   <div style="height:40px;"> 
		 <form id="searchForm" name="searchForm" method="post" >
							<table  cellpadding="5">
								<tr>
									<td style="text-align:right">编码：</td>
									<td>
										<input class="easyui-textbox" type="hidden" id="id" name="id"  value="${id}" />
										<input class="easyui-textbox" type="text" id="dicCode" name="dicCode"  value="${dicCode}" style="width:100px;" data-options="required:true" />
									</td>
									<td style="text-align:right">名称：</td>
									<td>
										<input class="easyui-textbox" type="text" id="dicName" name="dicName"  value="${dicName}" style="width:100px;" data-options="required:true" />
									</td>
									<%-- <td>类型</td>
									<td>
										<select style="width: 150px" name="sedicType" class="form-control" id="dicType">
											  <option value="">请选择</option>
											  <option value="0" <c:if test="${dicType == 0}">selected</c:if>>币种</option>
											  <option value="1" <c:if test="${dicType == 1}">selected</c:if>>银行卡类型</option>
											  <option value="2" <c:if test="${dicType == 2}">selected</c:if>>交易状态</option>
											  <option value="3" <c:if test="${dicType == 3}">selected</c:if>>接入支付平台</option>
											  <option value="4" <c:if test="${dicType == 4}">selected</c:if>>业务类型</option>
									     </select>
									</td> --%>
									
									<%-- <td>父级字典 </td>
									<td>
										 <select style="width: 150px" name="parentId" class="form-control" id="parentIdSelect">
											  <option></option>
											  <c:forEach items="${dictionaryList}" var="diction">
											    <option value="${diction.id}" <c:if test="${parentId == diction.id}">selected</c:if>>${diction.dicName}</option>
											  </c:forEach>
									   </select>
									</td> --%>
								</tr>
							</table>
					
						<%-- <div class="search-btn-area">
							<input id="search_btn" type="button" class="input-btn-small" value="查 询" />
							<input id="clear_btn" type="button" class="input-btn-small" value="清 除" />
							 <c:if test="${null != sessionScope.permMap['/bim/dictionary/addDictionary']}">
										  <a class="btn tip-bottom" href="${path}/bim/dictionary/dictionaryEditUI/null"><i class="icon-add"></i>新增</a>	
							 </c:if> 
						 				</div> --%>
					</form>
		       
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
						var checkedItems = $('#dg_ddTDictionary').datagrid(
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
																   url: '${path}/bim/dictionary/dictionaryDelete/'+names,
																	 
																data : names,
																dataType : "json",
																success : function(
																		data) {
																	$(
																			'#dg_ddTDictionary')
																			.datagrid(
																					'load');
																}
															});
												}
											});
						}else{
							$.messager.alert('提示','请选中一行');
							return;
						}

					}
				}, '-', {
					id : 'searchBut',
					text : '查询',
					iconCls : 'icon-search'
				
				} , {
					id : 'clear_btn',
					text : '清除',
					iconCls : 'icon-remove'
				
				} ];

var ddTDictionary_list ={};
jQuery(function($){
	//定义构造查询
	ddTDictionary_list.buildQueryParams=function(){
	    $('#dg_ddTDictionary').datagrid("options").queryParams={
						 'id':$("input[name='search_id']").val(),
						 'dicCode':$("input[name='dicCode']").val(),
						 'sedicName':$("input[name='dicName']").val(),
		};
	};


   
	$('#delBut2').click(function(){		
		var checkedItems = $('#dg_ddTDictionary').datagrid(
		'getChecked');
		var names = [];
		$.each(checkedItems, function(index, item) {
			names.push(item.ID);
		});
			if (names.length > 0) {
				$.messager.confirm(
			
					"操作提示",
					"您确定要执行操作吗？",
					function(data) {
						var m = names.toString()
						if (data) {
							$
									.ajax({
										type : 'GET',
										   url: '${path}/bim/dictionary/dictionaryDelete/'+names,
											 
										data : names,
										dataType : "json",
										success : function(
												data) {
											$(
													'#dg_ddTDictionary')
													.datagrid(
															'load');
										}
									});
						}
					});
			}else{
				$.messager.alert('提示','请选中一行');
				return;
		}

 
	});

	//重新按照条件刷新查询内容
	$('#searchBut2').click(function(){		
		ddTDictionary_list.buildQueryParams();
		$('#dg_ddTDictionary').datagrid('load');
	});
	
    //重置查询条件并刷新内容
	$('#clear_btn2').click(function(){
		//清空查询条件
			$("input[name='search_id']").val("");
			$("input[name='dicCode']").val("");
			$("input[name='dicName']").val("");
		ddTDictionary_list.buildQueryParams();
		$('#dg_ddTDictionary').datagrid('load');
	});	
	//新增
	$('#addBut2').click(function(){	
		  var add_form_id ='#dictionarySave';
		var id=$('#id').val();
		$('<div id="dialog_holder"></div>').dialog({
		    title: '新增数据字典',
		    width: 500,
		    height: 300,
		    href: '${path}/bim/dictionary/dictionaryEditUI/'+null+'/'+'1'+'/'+id,
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
		    		$('#dictionarySave').form({   
		    			 url:'${path}/bim/dictionary/dictionarySave/'+null,    
							     onSubmit: function(){
							     
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							    	 var obj=eval( "(" + data + ")" );
								        
								     
								       	if(obj.valmsg!=null){
								       		$.messager.alert('提示',obj.valmsg);	
								       	}else{
							        $.messager.progress('close');
									ddTDictionary_list.buildQueryParams();
									$('#dg_ddTDictionary').datagrid('load');
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
	});
	
	//更新
		$('#updateBut2').click(function(){
			   var edit_form_id ='#dictionarySave';
			var parentId=$('#id').val();
			var rowInfo = $("#dg_ddTDictionary").datagrid('getChecked');
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
		    href: '${path}/bim/dictionary/dictionaryEditUI/'+id+'/'+'1'+'/'+null,
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
						 url:'${path}/bim/dictionary/dictionarySave/'+null,    
							     onSubmit: function(){
							       
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
									ddTDictionary_list.buildQueryParams();
									$('#dg_ddTDictionary').datagrid('load');
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
});

</script>
	</body>
</html>
