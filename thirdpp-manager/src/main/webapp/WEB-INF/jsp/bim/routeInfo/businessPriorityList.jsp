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
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="updateBut_bp">调整通道顺序</a>
	</div>
	<table id="dg_tppChannerInfo_bp" class="easyui-datagrid"
			style="width: auto; height: auto"
			data-options="rownumbers:true, collapsible:true,sortName:'',selectOnCheck: 'true',checkOnSelect:true,sortOrder:'desc',pagination:true,url:'${path }/bim/channelInfo/businessPriorityListData/${dicCode}',method:'post',fit:'true',toolbar:'#tb_b'
					,onClickRow:function(rowIndex, rowData){ $('#dg_tppChannerInfo_bp').datagrid('unselectRow',rowIndex);}">
			<thead>
				<tr>
					<th data-options="field:'PRIORITY',width:120,sortable:'true',align:'left'">优先级</th>
					<th data-options="field:'THIRD_TYPE_NAME',width:120,sortable:'true',align:'left'">第三方支付平台</th>
					
				</tr>
			</thead>
		</table>
	</form>



	</div>
	<!--搜索栏结果end-->


	<script>
	
	
	jQuery(function($) {
		$('#updateBut_bp').click(function(){
			$('<div id="dialog_holder"></div>').dialog({
				title:'业务优先配置信息',
				width : 500,
				height : 400,
				href: '${path}/bim/channelInfo/businessPriorityInfoEditUI',
				modal : true,
				method : "POST",
				onClose : function() {
					$(this).dialog("destroy");
				},
				buttons : [{
					text : "保  存",
					handler : function(e) {
							var channels = [];
							var rowInfo = $("#dg_tppChannerInfo_bpe").datagrid('getRows');
							for(var i=0;i<rowInfo.length;i++){
								var channelInfo = {}
								channelInfo.id = rowInfo[i].ID;
								channelInfo.priority = i+1
								channels.push(channelInfo);
							}
							$.ajax({
								dataType:"JSON",
								type:"POST",
								async:true,
								data:{"channelInfos":JSON.stringify(channels)},
								url: '${path}/bim/channelInfo/businessPriorityInfoSave',
								success:function(data){
									if(data.status){
							       		$.messager.alert('提示',data.valmsg);	
							       		$.messager.progress('close');
										$('#dg_tppChannerInfo_bp').datagrid('load');
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
		});
	});
	
	
		
	</script>
</body>
</html>
