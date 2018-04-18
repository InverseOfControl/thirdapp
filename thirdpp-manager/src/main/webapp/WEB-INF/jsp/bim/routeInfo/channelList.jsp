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
	<table  id="dg_tppChannerInfo_rcl" class="easyui-datagrid"
					data-options="rownumbers:true, collapsible:true,sortName:'',selectOnCheck: 'true',singleSelect:'true',checkOnSelect:true,sortOrder:'desc',pagination:'true',url:'${path }/bim/routeInfo/channelListData',method:'post',fit:'true',toolbar:'#tb2_rcl'
					,pageSize:20,onClickRow:function(rowIndex, rowData){ $('#dg_tppChannerInfo_rcl').datagrid('unselectRow',rowIndex);}">	
					<thead>
						<tr>	
							<th data-options="field:'ck',checkbox:'true'"></th>	
							<th data-options="field:'AC',width:150,sortable:'true',align:'center'">操作</th>	
							<th data-options="field:'THIRD_TYPE_NAME',width:150,sortable:'true',align:'left'">第三方支付平台</th>
							<th data-options="field:'IS_AVAILABLE',width:150,sortable:'true',align:'left'">是否可用</th>
							<th data-options="field:'PRIORITY',width:150,sortable:'true',align:'left'">优先级</th>
							<th data-options="field:'TRANSFER_TIMES',width:150,sortable:'true',align:'left'">每日可划扣次数</th>		
							<th data-options="field:'FAIL_TIMES',width:150,sortable:'true',align:'left'">每日可失败次数</th>
							<th data-options="field:'PRECIPITATION',width:150,sortable:'true',align:'left'" formatter="formatCurrency">总沉淀量(元)</th>
							 
						</tr>
					</thead>
				</table>
		
			<div id="tb2_rcl" style=" padding:0px">    
		    <div>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addBut2_rcl">新增</a>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="updateBut_rcl">修改</a>    
		       <!--  <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="delBut2">删除</a>  -->
		        <a href="#" class="easyui-linkbutton" iconCls="icon-edit"  plain="true" id="updateBut2_rcl">调整通道顺序</a>
		    </div>    
		   
		</div> 
		
			<!--搜索栏结果end--> 
			
			
 <script>
	jQuery(function($) {
		
		$('#dg_tppChannerInfo_rcl').datagrid({  
	        onLoadSuccess:function(data){  
	        	 $(this).parent().find("div .datagrid-header-check").children("input[type=\"checkbox\"]").eq(0).attr("style", "display:none;");
	        },  
	    });  
		
		$('#addBut2_rcl').click(function(){
				var add_form_id ='#channelListSave';
				$('<div id="dialog_holder"></div>').dialog({
					title:'新增通道信息',
					width : 500,
					height : 400,
					href: '${path}/bim/routeInfo/channelListEditUI/'+null,
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
					},
					buttons : [{
						text : "提  交",
						handler : function(e) {
							$('#channelListSave').form({
								url:'${path}/bim/routeInfo/channelListSave',      
								onSubmit : function() {},
								success : function(data) {
									var obj=eval( "(" + data + ")" );
									if(obj.status){
										$.messager.alert('提示',"操作成功");	
										$.messager.progress('close');
										$('#dg_tppChannerInfo_rcl').datagrid('load');
										$('#dialog_holder').dialog('close');
									}else{
										$.messager.alert('提示',obj.valmsg);
									}
								}
							});// 
							
							$(add_form_id).submit();
							
															//validate and sbumit
							
						}
					},{
						text : "取 消",
						handler : function(e) {
						$(this).dialog("close");
						}
					}]
				});
		});
		
		
		//更新
			$('#updateBut_rcl').click(function(){
				var rowInfo = $("#dg_tppChannerInfo_rcl").datagrid('getChecked');
				if(rowInfo.length<1){
					$.messager.alert('提示','请选中一行');
				}else {
		
					if(rowInfo.length>1){
						$.messager.alert('提示','请选中一行');
						return;
					}
					var id=	rowInfo[0].ID;
					$('<div id="dialog_holder"></div>').dialog({
						title:'修改通道信息',
						width : 500,
						height : 400,
						href: '${path}/bim/routeInfo/channelListEditUI/'+id,
						modal : true,
						method : "POST",
						onClose : function() {
							$(this).dialog("destroy");
						},
						buttons : [{
							text : "保  存",
							handler : function(e) {
								$('#channelListSave').form({
									url:'${path}/bim/routeInfo/channelListUpdate',      
									onSubmit : function() {},
									success : function(data) {
										var obj=eval( "(" + data + ")" );
										if(obj.status){
											$.messager.alert('提示',"操作成功");	
											$.messager.progress('close');
											$('#dg_tppChannerInfo_rcl').datagrid('load');
											$('#dialog_holder').dialog('close');
										}else{
											$.messager.alert('提示',obj.valmsg);
										}
									}
								});// 
																//validate and sbumit
								$('#channelListSave').submit();
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
		
		
			$('#updateBut2_rcl').click(function(){
				var rowInfo_rcl = $("#dg_tppChannerInfo_rcl").datagrid('getRows');
				if(rowInfo_rcl.length<1){
					$.messager.alert('提示','当前无可用数据');
				}else {
					var temp = false;
					for(var i=0;i<rowInfo_rcl.length;i++){
						if('是' == rowInfo_rcl[i].IS_AVAILABLE){
							temp = true;
							$('<div id="dialog_holder"></div>').dialog({
								title:'业务优先配置信息',
								width : 500,
								height : 400,
								href: '${path}/bim/routeInfo/businessPriorityInfoEditUI',
								modal : true,
								method : "POST",
								onClose : function() {
									$(this).dialog("destroy");
								},
								buttons : [{
									text : "保  存",
									handler : function(e) {
											var channels = [];
											var rowInfo_bpe = $("#dg_tppChannerInfo_bpe").datagrid('getRows');
											for(var i=0;i<rowInfo_bpe.length;i++){
												var channelInfo = {}
												channelInfo.id = rowInfo_bpe[i].ID;
												channelInfo.priority = i+1
												channels.push(channelInfo);
											}
											$.ajax({
												dataType:"JSON",
												type:"POST",
												async:true,
												data:{"channelInfos":JSON.stringify(channels)},
												url: '${path}/bim/routeInfo/businessPriorityInfoSave',
												success:function(data){
													if(data.status){
											       		$.messager.alert('提示',"操作成功");	
											       		$.messager.progress('close');
														$('#dg_tppChannerInfo_rcl').datagrid('load');
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
			
	});
	
	
	function updateFlag(id,isAvailable){
		  $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {
			  if (data) {
				  $.ajax({
	                  type: 'POST',
	                  url: '${path}/bim/routeInfo/updateChannelStatus/'+id+'/'+isAvailable,
	                  dataType: "json",
	                  success:function(data){
	                	  $('#dg_tppChannerInfo_rcl').datagrid('load');  
	                  }
	              });
			  }
		  });
	} 
	</script>
</body>
</html>
 