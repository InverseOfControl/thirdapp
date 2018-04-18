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


 
	<div id="tb_rce" style="padding: 0px">
			<div  style="padding: 10px">
			<span style="color:red"><label>拖拽进行优先级调整</label></span>
			<span style="color:red"><label>↑ 优先级下降</label></span>
			<span style="color:red"><label>↓ 优先级上升</label></span>
			</div>
	</div> 
	<table id="dg_tppChannerInfo_rce" class="easyui-datagrid"
			style="width: auto; height: auto"
			data-options="rownumbers:true,fitColumns:true,scrollbarSize:0,collapsible:true,sortName:'',singleSelect:'true',selectOnCheck: 'true',checkOnSelect:'true',moveRow:'true',sortOrder:'desc',pagination:false
			,url:'${path }/bim/routeInfo/routePriorityListData/${dicCode}'
			,method:'post',fit:'true'
			,toolbar:'#tb_rce'
			,onLoadSuccess:function(){
				$(this).datagrid('enableDnd');
			}">
			<thead>
				<tr>
				<th data-options="field:'ROUTE_NAME',width:200,sortable:'true',align:'center'">路由逻辑</th>
					
				</tr>
			</thead>
		</table>
	</form>



	</div>
	<!--搜索栏结果end-->


	<script>
	
/* 	var moveRow = function(target,type){
        var options = $(target).datagrid('options');
        if(options.moveRow){
            var dmr = new DatagridMoveRow(target);
            if(type == 38){ //up
                dmr.moveUp();
            }else if(type == 40) {// down
                dmr.moveDown();
            }
        }
    }
	
	function isChecked(gridTarget){    //参数为你要判断的行
		el = gridTarget;
        $el = $(el);
	    var selectrow= $el.datagrid('getSelected');  
        var selectRowIndex = $el.datagrid('getRowIndex', selectrow); 
        if(selectRowIndex == -1){
            return false;
        }else{
            return true;
        }
	}
	
	jQuery(function($) {
		
		$('#updateBut_rce_up').click(function(){
			if(isChecked($('#dg_tppChannerInfo_rce'))){
				moveRow($('#dg_tppChannerInfo_rce'),38)
			}else{
				$.messager.alert('提示','请选择一行');
			}
			
		});
		
		$('#updateBut_rce_down').click(function(){
			if(isChecked($('#dg_tppChannerInfo_rce'))){
				moveRow($('#dg_tppChannerInfo_rce'),40)
			}else{
				$.messager.alert('提示','请选择一行');
			}
		});
	}); */
	
	
	
		
	</script>
</body>
</html>
