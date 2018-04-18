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
			data-options="collapsible:true,pageSize:20,singleSelect:true,selectOnCheck: 'true',checkOnSelect:'true',sortName:'',onClickRow:function(rowIndex, rowData){showInfo(rowIndex, rowData);},sortOrder:'desc',url:'${path}/bim/dictionary/dictionaryDicType/3',method:'post',fit:true,onLoadSuccess:function(){defaultSelect()}">
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
		 
		<div data-options="region:'center'" title="">
			<!--  中间部分（各模块主区域） -->
			<div id="roleTabs" data-options="fit:true" class="easyui-tabs"  style="width:700px;height:250px">
				<div  id="roleTabs_userList" title="系统字段映射信息列表" data-options="fit:true" style="text-align:center;font-size:18px;">   
			    </div> 
		</div>
	<script type="text/javascript">
	function defaultSelect() {
		$('#dg_ddTBankInfo').datagrid('selectRow', 0); 
		var row = $('#dg_ddTBankInfo').datagrid('getSelected');
		if (row != null) {
			showInfo(0, row);
		}
	}
	function showInfo(rowIndex, rowData) {
		var userTabs = $('#roleTabs_userList');
	 
		userTabs.panel({    
		    href:'${path}/bim/thirdField/toThirdFieldList/'+rowData.DIC_CODE+"/"+rowData.ID,
		    onLoad:function(){    
		            
		    }    
		});  
		
	 
		
	}
	function onClickRow(rowNum,rowData){ 
		var obj=eval(rowData);
	 
		var tabsPanel = $('#tabsPanel2');
		var current_tab = tabsPanel.tabs('getSelected');
		var url='${path}/bim/thirdField/toThirdFieldList/'+obj.DIC_CODE+"/"+obj.ID;
		if (tabsPanel.tabs('exists',obj.DIC_TYPE)) {
			tabsPanel.tabs('select',obj.DIC_TYPE);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'"  style="width:100%;height:100%;padding:0px;"></iframe>';
			if(current_tab==null){							 
				tabsPanel.tabs('add',{
					title : '系统字段映射信息列表',
					tab:current_tab,
					content : content ,  
					 
					
				});
			}else{							 
				tabsPanel.tabs('update',{
					title : '系统字段映射信息列表',
					tab:current_tab,
					 options : {  
				          content : content ,  
				      //或者 href : '';  
				     }  
					 
					
				});
			}
			
		}
	};


 
	</script>
</body>
	 
</html>