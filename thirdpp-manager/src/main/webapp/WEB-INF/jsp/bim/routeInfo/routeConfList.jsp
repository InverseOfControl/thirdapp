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



	<div id="tb_b" style="padding: 0px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addBut2_rc">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="updateBut2_rc">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="updateBut_rc">调整路由顺序</a>
	</div>
	<table id="dg_tppChannerInfo_rc" class="easyui-datagrid"
			style="width: auto; height: auto"
			data-options="rownumbers:true, collapsible:true,sortName:'',singleSelect:'true',selectOnCheck: 'true',checkOnSelect:true,sortOrder:'desc',pagination:true
			,url:'${path }/bim/routeInfo/routeConfListData/${dicCode}',method:'post',fit:'true',toolbar:'#tb_b'
			,pageSize:20,onClickRow:function(rowIndex, rowData){ $('#dg_tppChannerInfo_rc').datagrid('unselectRow',rowIndex);}">
			<thead>
				<tr>
				<th data-options="field:'ck',checkbox:'true'"></th>		
				<th data-options="field:'AC',width:150,sortable:'true',align:'center'">操作</th>	
				<th data-options="field:'ROUTE_NAME',width:150,sortable:'true',align:'left'">路由逻辑</th>
				<th data-options="field:'PRIORITY',width:150,sortable:'true',align:'left'">优先级</th>
				<th data-options="field:'IS_AVAILABLE',width:150,sortable:'true',align:'left'">是否可用</th>
				<th data-options="field:'ROUTE_CLASS',width:500,sortable:'true',align:'left'">路由逻辑类</th>
				</tr>
			</thead>
		</table>
	</form>



	</div>
	<!--搜索栏结果end-->


	<script>
	
	
	jQuery(function($) {
		//新增
		
		$('#dg_tppChannerInfo_rc').datagrid({  
	        onLoadSuccess:function(data){  
	        	 $(this).parent().find("div .datagrid-header-check").children("input[type=\"checkbox\"]").eq(0).attr("style", "display:none;");
	        },  
	    }); 
		
		$('#addBut2_rc').click(function(){
				$('<div id="dialog_holder"></div>').dialog({
					title:'新增路由逻辑信息',
					width : 500,
					height : 400,
					href: '${path}/bim/routeInfo/routeConfListEdit/'+null,
					modal : true,
					method : "POST",
					onClose : function() {
						$(this).dialog("destroy");
					},
					buttons : [{
						text : "保  存",
						handler : function(e) {
							$('#routeConfInfoSave').form({
								url:'${path}/bim/routeInfo/routeConfInfoSave',      
								onSubmit : function() {},
								success : function(data) {
									var obj=eval( "(" + data + ")" );
									if(obj.status){
										$.messager.alert('提示',"操作成功");	
										$.messager.progress('close');
										$('#dg_tppChannerInfo_rc').datagrid('load');
										$('#dialog_holder').dialog('close');
									}else{
										$.messager.alert('提示',obj.valmsg);
									}
								}
							});// 
							if($('#routeConfInfoSave').form("validate")==true){								//validate and sbumit
								$('#routeConfInfoSave').submit();
							}
						}
					},{
						text : "取 消",
						handler : function(e) {
						$(this).dialog("close");
						}
					}]
				});
		});
		
		//优先级
		$('#updateBut_rc').click(function(){
			var rowInfo_rc = $("#dg_tppChannerInfo_rc").datagrid('getRows');
			if(rowInfo_rc.length<1){
				$.messager.alert('提示','当前无可用数据');
			}else {
				var temp = false;
				for(var i=0;i<rowInfo_rc.length;i++){
					if('是' == rowInfo_rc[i].IS_AVAILABLE){
						temp = true;
						$('<div id="dialog_holder"></div>').dialog({
							title:'路由配置信息',
							width : 500,
							height : 400,
							href: '${path}/bim/routeInfo/routeConfPriorityList',
							modal : true,
							method : "POST",
							onClose : function() {
								$(this).dialog("destroy");
							},
							buttons : [{
								text : "保  存",
								handler : function(e) {
										var routes = [];
										var rowInfo_rce = $("#dg_tppChannerInfo_rce").datagrid('getRows');
										for(var i=0;i<rowInfo_rce.length;i++){
											var routeInfo = {}
											routeInfo.id = rowInfo_rce[i].ID;
											routeInfo.priority = i+1
											routes.push(routeInfo);
										}
										$.ajax({
											dataType:"JSON",
											type:"POST",
											async:true,
											data:{"routeConfInfos":JSON.stringify(routes)},
											url: '${path}/bim/routeInfo/priorityInfoUpdate',
											success:function(data){
												if(data.status){
										       		$.messager.alert('提示',"操作成功");	
										       		$.messager.progress('close');
													$('#dg_tppChannerInfo_rc').datagrid('load');
													$('#dialog_holder').dialog('close');
										       	}else{
										       		$.messager.alert('提示',data.valmsg);
										       	}
											},
											error:function(data){
												alert("请求超时");
											}
										});
								}
							},{
								text : "取 消",
								handler : function(e) {
								$(this).dialog("close");
								}
							}]
						});
						break;
					}
				}
				if(!temp){
					$.messager.alert('提示','当前无可用数据');
				}
			}
		});
		
		//更新基础信息
		$('#updateBut2_rc').click(function(){
			var rowInfo = $("#dg_tppChannerInfo_rc").datagrid('getChecked');
			if(rowInfo.length<1){
				$.messager.alert('提示','请选中一行');
			}else {
	
				if(rowInfo.length>1){
					$.messager.alert('提示','请选中一行');
					return;
				}
				var id=	rowInfo[0].ID;
				$('<div id="dialog_holder"></div>').dialog({
					title:'修改路由逻辑信息',
					width : 500,
					height : 400,
					href: '${path}/bim/routeInfo/routeConfListEdit/'+id,
					modal : true,
					method : "POST",
					onClose : function() {
						$(this).dialog("destroy");
					},
					buttons : [{
						text : "保  存",
						handler : function(e) {
							$('#routeConfInfoSave').form({
								url:'${path}/bim/routeInfo/routeConfInfoUpdate',      
								onSubmit : function() {},
								success : function(data) {
									var obj=eval( "(" + data + ")" );
									if(obj.status){
										$.messager.alert('提示',"操作成功");	
										$.messager.progress('close');
										$('#dg_tppChannerInfo_rc').datagrid('load');
										$('#dialog_holder').dialog('close');
									}else{
										$.messager.alert('提示',obj.valmsg);
									}
								}
							});// 
							if($('#routeConfInfoSave').form("validate")==true){								//validate and sbumit
								$('#routeConfInfoSave').submit();
							}
						}
					},{
						text : "取 消",
						handler : function(e) {
						$(this).dialog("close");
						}
					}]
				});
			}
		});
	});
	
	function updateFlag(id,isAvailable){
		  $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {
			  if (data) {
				  $.ajax({
	                  type: 'POST',
	                  url: '${path}/bim/routeInfo/updateRouteStatus/'+id+'/'+isAvailable,
	                  dataType: "json",
	                  success:function(data){
	                	  $('#dg_tppChannerInfo_rc').datagrid('load');  
	                  }
	              });
			  }
		  });
	} 
		
	</script>
</body>
</html>
