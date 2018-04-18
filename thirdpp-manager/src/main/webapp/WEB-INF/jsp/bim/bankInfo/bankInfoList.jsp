<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>证大财富管理系统</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/jsCssInclude.jsp"%>
<link rel="stylesheet" href="${sessionScope.path}/css/base.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body class="easyui-layout">
	<div class="easyui-panel" title="查询条件"
		style="padding: 5px; height: 75px; margin: 0px;"
		data-options="region:'north'">
		<form id="searchForm" name="searchForm" method="post">
			<table cellpadding="5">
				<tr>
					<td>银行名称:</td>
					<td><input class="easyui-textbox" type="text"
						class="easyui-textbox" id="sebankName" name="sebankName"
						value="${bankName}"></input></td>
				</tr>
			</table>
		</form>
	</div>


	<!--搜索结果开始-->
	<div class="easyui-panel" style="padding: 0px; margin: 0px;"
		data-options="region:'center'">
		<table id="dg_ddTBankInfo" class="easyui-datagrid"
			data-options="rownumbers:true,collapsible:true,selectOnCheck: 'true',checkOnSelect:'true',sortName:'',sortOrder:'desc',pagination:'true',url:'${path}/bim/bankInfo/bankInfoListData',method:'post',toolbar:toolbar,fit:'true'">
			<thead>
				<tr>

					<th data-options="field:'ck',checkbox:'true'"></th>
					<th
						data-options="field:'ID',width:120,sortable:'true',align:'right'">ID</th>
					<th
						data-options="field:'BANK_CODE',width:120,sortable:'true',align:'right'">银行编码</th>
					<th
						data-options="field:'BANK_NAME',width:120,sortable:'true',align:'right'">银行名称</th>
					<th
						data-options="field:'DT_TIME',width:120,sortable:'true',align:'right'">日期</th>
					<th
						data-options="field:'NOTE',width:120,sortable:'true',align:'right'">备注</th>

				</tr>
			</thead>
		</table>
	</div>

	<!--搜索栏结果end-->


	<!-- foot -->


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
		iconCls : 'icon-remove',
		handler : function() {
			var checkedItems = $('#dg_ddTBankInfo').datagrid('getChecked');
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
				                      url: '${path}/bim/bankInfo/bankInfoDelete/'+names,
				                      data: names,
				                      dataType: "json",
				                      success:function(data){
				                    	  $('#dg_ddTBankInfo').datagrid('load');  
				                      }
				                  });
				            }
				        });		   
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
	}];
	
	function viewInfo(id){
		$('<div id="dialog_holder"></div>').dialog({
		    title: '修改',
		    width: 400,
		    height: 300,
		    href:  '${path}/bim/bankInfo/bankInfoEditUI/'+id,
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
	 
	var ddTBankInfo_list ={};
jQuery(function($){
	//定义构造查询
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
	
    //重置查询条件并刷新内容
	$('#clear_btn').click(function(){
		//清空查询条件
			$("input[name='search_id']").val("");
			$("input[name='bankCode']").val("");
			$("input[name='bankName']").val("");
			$("input[name='beginTime']").val("");
			$("input[name='endTime']").val("");
		ddTBankInfo_list.buildQueryParams();
		$('#dg_ddTBankInfo').datagrid('load');
	}); 
	//新增
	$('#addBut').click(function(){
	/* 	var add_form_id ='#addDdTBankInfoFrom'; */
		$('<div id="dialog_holder"></div>').dialog({
		    title: '新增银行信息',
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
		    title: '修改银行信息',
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
	
	//详细
	ddTBankInfo_list.detailFormSubmit = function(pkid){
		$('<div id="dialog_holder"></div>').dialog({
		    title: '详细DdTBankInfo',
		    width: 800,
		    height: 500,
		    href: 'detailDdTBankInfoPage.action',
		    modal: true,
		    method: "POST",
		    params:{pkid:pkid},
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
		    		var ThisForm = $(this).find("form");
		    	}
		    },{
		    	text: "关闭",
		    	handler: function(e){
		    		$(this).dialog("close");
		    	}
		    }]
		});
	
	};
	
	
});

</script>

</body>
</html>
