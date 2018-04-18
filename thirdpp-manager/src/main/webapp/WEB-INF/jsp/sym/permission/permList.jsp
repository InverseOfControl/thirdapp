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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
<body class="easyui-layout">
			<!--搜索栏开始-->
			<div class="easyui-panel" title="查询条件" style="padding:5px;height:75px;margin: 0px;" data-options="region:'north'">
				<form id="searchForm" name="searchForm" method="post" >
					<table cellpadding="5">
								<td style="text-align:right">权限名称：</td>
								<td>
									<input type="text" id="search_perm_name" name="search_perm_name" class="easyui-textbox" value="${search_perm_name}" style="width:100px;"/>
								</td>
								<td style="text-align:right">权限URL：</td>
								<td>
									<input type="text" id="search_perm_url" name="search_perm_url" class="easyui-textbox" value="${search_perm_url}" style="width:100px;"/>
								</td>
								<td style="text-align:right">权限类型：</td>
								<td>
									<input id="search_perm_type" class="easyui-combobox" name="search_perm_type" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/permissionTypeList' " style="width:100px;"/> 
								</td>
					</table>
				</form>
				<form id="deleteForm" name="deleteForm method="post">
					<input type="hidden" id="permIds" name="permIds"/>
				</form>
			</div>
			<!--搜索栏结束-->
			
			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding:0px;margin: 0px;" data-options="region:'center'">
				<table  id="dg_tSysPermission" class="easyui-datagrid" 
					data-options="rownumbers:true,pageSize:20,singleSelect:false,selectOnCheck: 'true',checkOnSelect:'true',collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/sym/permission/permListData',method:'post',toolbar:toolbar,fit:'true',onClickRow: function (rowIndex, rowData) {$(this).datagrid('unselectRow', rowIndex);}">
					<thead>
						<tr>
							<!-- <th data-options="field:'EDIT',width:50,align:'center'">编辑</th>
							<th data-options="field:'DELETE',width:50,align:'center'">删除</th> -->
							<th data-options="field:'ck',checkbox:'true'"></th>
							<th data-options="field:'ID',width:120,sortable:'true',align:'left',hidden:true">ID</th>
							<th data-options="field:'PERM_NAME',width:120,sortable:'true',align:'left'">权限名称</th>
							<th data-options="field:'PERM_URL',width:200,sortable:'true',align:'left'">权限URL</th>
							<th data-options="field:'PERM_TYPE',width:120,sortable:'true',align:'left'">权限类型</th>
							<th data-options="field:'POSITION',width:120,sortable:'true',align:'left'">菜单位置</th>
							<!-- <th data-options="field:'PARENT_ID',width:120,sortable:'true',align:'left'">父级权限ID</th> -->
							<th data-options="field:'CREATOR',width:120,sortable:'true',align:'left'">创建者</th>
							<th data-options="field:'UPDATOR',width:120,sortable:'true',align:'left'">更新者</th>
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'left'">创建时间</th>
							<th data-options="field:'UPDATE_TIME',width:120,sortable:'true',align:'left'">更新时间</th>
							<!-- <th data-options="field:'RESV_FLD1',width:120,sortable:'true',align:'left'">备用域1</th>
							<th data-options="field:'RESV_FLD2',width:120,sortable:'true',align:'left'">备用域2</th>
							<th data-options="field:'RESV_FLD3',width:120,sortable:'true',align:'left'">备用域3</th> -->
						</tr>
					</thead>
				</table>
			</div>
			<!--搜索栏结果end-->

<script>
var tSysPermission_list ={};
var toolbar = [{
				id : 'add_btn',
				text : '新增',
				iconCls : 'icon-add'
			},{
				id : 'edit_btn',
				text : '修改',
				iconCls : 'icon-edit'
			},{
				id : 'delete_btn',
				text : '删除',
				iconCls : 'icon-cancel'
			},'-',{
				id : 'search_btn',
				text : '查询',
				iconCls : 'icon-search'
			},{
				id : 'clear_btn',
				text : '清除',
				iconCls : 'icon-remove'
			}];
			function deletePerm(permId){
				$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
				    if (r){    
				        $('#deleteForm').form({  
					        url:'${path }/sym/permission/deletePerm/' + permId,  
					        onSubmit:function(){  
					        },  
					        success:function(data){  
					        	 var obj = eval('(' + data + ')');
							       //do some
						    	 if(obj.flag==false){
							    	   $.messager.alert('提示',obj.msg);   
							    	   return;
						       	 }
					        	$('#dg_tSysPermission').datagrid('load');
					        }  
					    }); 
			    		$('#deleteForm').submit();
				    }    
				}); 
			}
			
jQuery(function($){
	//定义构造查询
	tSysPermission_list.buildQueryParams=function(){
		$('#dg_tSysPermission').datagrid("options").queryParams={
				 'search_perm_name':$("input[name='search_perm_name']").val(),
				 'search_perm_url':$("input[name='search_perm_url']").val(),
				 'search_perm_type':$('#search_perm_type').combobox("getValue"),
		}
	}

	//重新按照条件刷新查询内容
	$('#search_btn').click(function(){
		tSysPermission_list.buildQueryParams();
		$('#dg_tSysPermission').datagrid('load');
	});
	
    //重置查询条件并刷新内容
	$('#clear_btn').click(function(){
		//清空查询条件
			$("input[name='search_perm_name']").val("");
			$("input[name='search_perm_url']").val("");
			$('#search_perm_type').combobox('setValue','');
		tSysPermission_list.buildQueryParams();
		$('#dg_tSysPermission').datagrid('load');
	});
	$('#delete_btn').click(function(){
		var row = $('#dg_tSysPermission').datagrid('getSelected');
		if (row == null) {
			$.messager.alert('提示','请先选择要删除的权限！');
			return;
		}
		
		var checkedItems = $('#dg_tSysPermission').datagrid('getChecked');
	     var ids = [];
	     $.each(checkedItems, function(index, item){
	         ids.push(item.ID);
	    });                
		$("#permIds").val(ids);
		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){    
		        $('#deleteForm').form({  
			        url:'${path }/sym/permission/deletePerm',  
			        onSubmit:function(){  
			        	$.messager.progress(); 
			        },  
			        success:function(data){  
			        	$.messager.progress('close');
			        	 var obj = eval('(' + data + ')');
					       //do some
				    	 if(obj.flag==false){
					    	   $.messager.alert('提示',obj.msg);   
					    	   return;
				       	 }
			        	$('#dg_tSysPermission').datagrid('load');
			        }  
			    }); 
	    		$('#deleteForm').submit();
		    }    
		}); 
	});
	$('#edit_btn').click(function(){
		var row = $('#dg_tSysPermission').datagrid('getSelected');
		if (row == null) {
			$.messager.alert('提示','请先选择要修改的权限！');
			return;
		}
		
		var rows = $("#dg_tSysPermission").datagrid('getSelections');
		if(rows.length<1 || rows.length>1){
			$.messager.alert('提示','请选中一行');
			return;
		}
		var permId = row.ID;
		var edit_form_id ='#editTSysPermissionFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: '修改信息',
		    width: 600,
		    height: 300,
		    href: '${path}/sym/permission/editPermPage',
		    modal: true,
		    method: "POST",
		    params:{permId:permId},
			onClose: function(){
				$(this).dialog("destroy");
			},
		    buttons: [{
		    	text: "保  存",
		    	handler: function(e){
		   				$('#editTSysPermissionFrom').form({   
						 url:'${path}/sym/permission/editPermission',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							    	 var obj = eval('(' + data + ')');
								       //do some
							    	 if(obj.flag==false){
								    	   $.messager.alert('提示',obj.msg);   
								    	   $.messager.progress('close');   
								    	   return;
							       	 }
							       //do some
							        $.messager.progress('close');
									tSysPermission_list.buildQueryParams();
									$('#dg_tSysPermission').datagrid('load');
									$('#dialog_holder').dialog('close');
							     }
						});// 
						$(".easyui-validatebox").each(function(){
				        	$(this).validatebox({    
				        		novalidate: false   
				        	}); 
				        });
					    //validate and sbumit
					    if($(edit_form_id).form("validate")==true){
						  	$.messager.progress();
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
		});
		
	});
	//新增
	$('#add_btn').click(function(){
		var add_form_id ='#addTSysPermissionFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: '新增权限',
		    width: 600,
		    height: 300,
		    href: '${path}/sym/permission/addPermissionPage',
		    modal: true,
		    method: "POST",
			onClose: function(){
				$(this).dialog("destroy");
			},
		    buttons: [{
		    	text: "提  交",
		    	handler: function(e){
		    		$('#addTSysPermissionFrom').form({   
						 url:'${path}/sym/permission/addPermission',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							    	 var obj = eval('(' + data + ')');
								       //do some
								       if(obj.flag==false){
								    	   $.messager.alert('提示',obj.msg);   
								    	   $.messager.progress('close');   
								    	   return;
								       }
							       //do some
							        $.messager.progress('close');
									tSysPermission_list.buildQueryParams();
									$('#dg_tSysPermission').datagrid('load');
									$('#dialog_holder').dialog('close');
							     }
						});// 
						$(".easyui-validatebox").each(function(){
				        	$(this).validatebox({    
				        		novalidate: false   
				        	}); 
				        });
					    //validate and sbumit
					    if($(add_form_id).form("validate")==true){
						  	$.messager.progress();
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
		
	
	
	//详细
	tSysPermission_list.detailFormSubmit = function(pkid){
		$('<div id="dialog_holder"></div>').dialog({
		    title: '详细TSysPermission',
		    width: 600,
		    height: 300,
		    href: 'detailTSysPermissionPage.action',
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
	}
	
	
});
//更新
function editPerm(permId){
    var edit_form_id ='#editTSysPermissionFrom';
	$('<div id="dialog_holder"></div>').dialog({
	    title: '修改信息',
	    width: 600,
	    height: 300,
	    href: '${path}/sym/permission/editPermPage',
	    modal: true,
	    method: "POST",
	    params:{permId:permId},
		onClose: function(){
			$(this).dialog("destroy");
		},
	    buttons: [{
	    	text: "保  存",
	    	handler: function(e){
	   				$('#editTSysPermissionFrom').form({   
					 url:'${path}/sym/permission/editPermission',    
						     onSubmit: function(){
						       $.messager.progress(); 
						        // do some check       
						        // return false to prevent submit;    
						     },    
						     success:function(data){
						    	 var obj = eval('(' + data + ')');
							       //do some
						    	 if(obj.flag==false){
							    	   $.messager.alert('提示',obj.msg);   
							    	   $.messager.progress('close');   
							    	   return;
						       	 }
						       //do some
						        $.messager.progress('close');
								tSysPermission_list.buildQueryParams();
								$('#dg_tSysPermission').datagrid('load');
								$('#dialog_holder').dialog('close');
						     }
					});// 
					$(".easyui-validatebox").each(function(){
			        	$(this).validatebox({    
			        		novalidate: false   
			        	}); 
			        });
				    //validate and sbumit
				    if($(edit_form_id).form("validate")==true){
					  	$.messager.progress();
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
	});
}
</script>
</body>
</html>