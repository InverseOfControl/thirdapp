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
	<input type="hidden" id="dicCode_cost" name="dicCode_cost" class="easyui-textbox"
		value="${dicCode}" style="width: 150px;" data-options="required:true" />
	<input type="hidden" id="parentId_cost" name="parentId_cost"
		class="easyui-textbox" value="${parentId}" style="width: 150px;"
		data-options="required:true" />
	<table  id="dg_tppChannerInfo_cost" class="easyui-datagrid"
					data-options="rownumbers:true, collapsible:true,sortName:'',selectOnCheck: 'true',checkOnSelect:true,sortOrder:'desc',pagination:'true'
					,url:'${path }/bim/channelInfo/costInfoListData/${dicCode}',method:'post',fit:'true',toolbar:'#tb2_cost'
					,pageSize:20,onClickRow:function(rowIndex, rowData){ $('#dg_tppChannerInfo_cost').datagrid('unselectRow',rowIndex);}">	
					<thead>
						<tr>	
							<th data-options="field:'ck',checkbox:'true'"></th>			
							<!-- <th data-options="field:'THIRD_TYPE_NAME',width:150,sortable:'true',align:'left'">第三方支付平台</th> -->
							<th data-options="field:'COST_TYPE',width:150,sortable:'true',align:'left'">类型</th>
							<th data-options="field:'FIXED_AMOUNT',width:150,sortable:'true',align:'left'" formatter="formatCurrency">固定金额(元)</th>
							<th data-options="field:'PERCENT',width:150,sortable:'true',align:'left' ">百分比</th>
							<th data-options="field:'MIN_AMOUNT',width:150,sortable:'true',align:'left'" formatter="formatCurrency" >最低金额(元) 含</th>
							<th data-options="field:'MAX_AMOUNT',width:150,sortable:'true',align:'left'" formatter="formatCurrency">最高金额(元) 含</th>
							<th data-options="field:'HAS_LIMIT_AMOUNT',width:150,sortable:'true',align:'left'" >是否封顶</th>
							<th data-options="field:'LIMIT_AMOUNT',width:150,sortable:'true',align:'left'">封顶金额(元)</th>
							 
						</tr>
					</thead>
				</table>
		
			<div id="tb2_cost" style=" padding:0px">    
		    <div>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addBut_cost">新增</a>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="updateBut_cost">修改</a>    
		        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" id="delBut_cost">删除</a> 
		  
		    </div>    
		   
		</div> 
		
			<!--搜索栏结果end--> 
			
			
 <script>
 
	jQuery(function($) {
		
		//新增
		$('#addBut_cost').click(function(){
			var parentId=$('#parentId_cost').val();	
			var dicCode=$('#dicCode_cost').val();	
			$('<div id="dialog_holder"></div>').dialog({
				title:'新增通道费用信息',
				width : 500,
				height : 400,
				href: '${path}/bim/channelInfo/costInfoEditUI/'+null+'/'+dicCode+'/'+parentId,
				modal : true,
				method : "POST",
				onClose : function() {
					$(this).dialog("destroy");
				},
				buttons : [{
					text : "提  交",
					handler : function(e) {
						$('#costInfoSave').form({
							url:'${path}/bim/channelInfo/costInfoSave',      
							onSubmit : function() {},
							success : function(data) {
								var obj=eval( "(" + data + ")" );
								if(obj.status){
									$.messager.alert('提示',"操作成功");		
									$.messager.progress('close');
									$('#dg_tppChannerInfo_cost').datagrid('load');
									$('#dialog_holder').dialog('close');
								}else{
									
									$.messager.alert('提示',obj.valmsg);
								}
							}
						});// 
						if($('#costInfoSave').form("validate")==true){
							var msg = checkData();
							if(msg){
								$.messager.alert('提示',msg);
								return;
							}
							$('#costInfoSave').submit();
						}; 
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
			$('#updateBut_cost').click(function(){
				var rowInfo = $("#dg_tppChannerInfo_cost").datagrid('getChecked');
				if(rowInfo.length<1){
					$.messager.alert('提示','请选中一行');
				}else {
		
					if(rowInfo.length>1){
						$.messager.alert('提示','请选中一行');
						return;
					}
					var id=	rowInfo[0].ID;
					var parentId=$('#parentId_cost').val();	
					var dicCode=$('#dicCode_cost').val();	
					$('<div id="dialog_holder"></div>').dialog({
						title:'修改通道费用信息',
						width : 500,
						height : 400,
						href: '${path}/bim/channelInfo/costInfoEditUI/'+id+'/'+dicCode+'/'+parentId,
						modal : true,
						method : "POST",
						onClose : function() {
							$(this).dialog("destroy");
						},
						buttons : [{
							text : "保  存",
							handler : function(e) {
								$('#costInfoSave').form({
									url:'${path}/bim/channelInfo/costInfoUpdate',      
									onSubmit : function() {},
									success : function(data) {
										var obj=eval( "(" + data + ")" );
										if(obj.status){
											$.messager.alert('提示',"操作成功");	
											$.messager.progress('close');
											$('#dg_tppChannerInfo_cost').datagrid('load');
											$('#dialog_holder').dialog('close');
										}else{
											$.messager.alert('提示',obj.valmsg);
										}
									}
								});// 
																//validate and sbumit
								
								if($('#costInfoSave').form("validate")==true){
									var msg = checkData();
									if(msg){
										$.messager.alert('提示',msg);
										return;
									}
									$('#costInfoSave').submit();
								}; 
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
		
			//删除
			$('#delBut_cost').click(function(){
				var rowInfo = $("#dg_tppChannerInfo_cost").datagrid('getChecked');
				if(rowInfo.length<1){
					$.messager.alert('提示','请选中行');
				}else {
					var id=	rowInfo[0].ID;
					var parentId=$('#parentId_cost').val();	
					var dicCode=$('#dicCode_cost').val();	
					
					 $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {
						  if (data) {
							    var costs = [];
								//var rowInfo = $("#dg_tppChannerInfo_cost").datagrid('getChecked');
								for(var i=0;i<rowInfo.length;i++){
									//var costInfo = {}
									//costInfo.id = rowInfo[i].ID;
									costs.push(rowInfo[i].ID);
								}
								$.ajax({
									dataType:"JSON",
									type:"POST",
									async:true,
									data:{"costInfos":costs+""},
									url: '${path}/bim/channelInfo/costInfoDelete',
									success:function(data){
										if(data.status){
								       		$.messager.alert('提示',"操作成功");	
								       		$.messager.progress('close');
											$('#dg_tppChannerInfo_cost').datagrid('load');
								       	}else{
								       		$.messager.alert('提示',data.valmsg);
								       	}
									},
									error:function(data){
										alert("请求超时");
									}
								});
						  }
					  });
				}
			});
			
			
	});
	</script>
</body>
</html>
 