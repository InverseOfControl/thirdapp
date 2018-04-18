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
<link rel="stylesheet" href="${sessionScope.path}/css/base.css" />
<script type="text/javascript"
	src="${path}/js/bim/thirdField/thirdField.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body class="easyui-layout">
	<!-- <div class="easyui-panel" title="查询条件"
		style="padding: 5px; height: 75px; margin: 0px;"
		data-options="region:'north'">
		搜索栏开始
		
	</div> -->

	<!--搜索栏结束-->

	<!--搜索结果开始-->
	<!-- <div class="easyui-panel" style="padding: 0px; margin: 0px;"
		data-options="region:'center'"> -->
		<table id="dg_ddTThirdFieldMapper" class="easyui-datagrid"
			style="width: auto; height: 300px"
			data-options="rownumbers:true,collapsible:true , selectOnCheck: 'true',checkOnSelect:true,sortName:'',sortOrder:'desc',pagination:'true',	url:'<%=path%>/bim/thirdField/thirdFieldListData/${dicCode}',method:'post',toolbar:'#tb2',fit:true
				,pageSize:20,onClickRow:function(rowIndex, rowData){ $('#dg_ddTThirdFieldMapper').datagrid('unselectRow',rowIndex);}
			">
			<thead>
				<tr>
						<th data-options="field:'ck',checkbox:'true'"></th>
					<th
						data-options="field:'ID',width:120,align:'right',hidden:'true'">ID</th>
					<!-- <th data-options="field:'THIRD_PARTY_TYPE',width:120,sortable:'true',align:'right'">第三方平台编码</th> -->
					<th
						data-options="field:'AC',width:140,sortable:'true',align:'right'">操作</th>
							<th
						data-options="field:'STATUS',width:180,sortable:'true',align:'right'">银行通道状态</th>
					<th
						data-options="field:'TPP_FIELD_CODE',width:140,sortable:'true',align:'right'">tpp系统字段编码</th>
					<th
						data-options="field:'THIRD_FIELD_CODE',width:148,sortable:'true',align:'right'">第三方平台字段编码</th>
					<th
						data-options="field:'FIELD_NAME',width:160,sortable:'true',align:'right'">字段名称</th>
					<th
						data-options="field:'DT_TIME',width:160,sortable:'true',align:'right'">创建日期</th>
					<th
						data-options="field:'FIELD_TYPE',width:180,sortable:'true',align:'right'">字段类型</th>
				
					<th
						data-options="field:'COLLECT_MAX_MONEY',width:180,sortable:'true',align:'right'">第三方支付平台代收业务最高限额(元)</th>
					<th
						data-options="field:'PAY_MAX_MONEY',width:180,sortable:'true',align:'right'">第三方支付平台代付业务最高限额(元)</th>
					<th
						data-options="field:'QUICK_PAY_MAX_MONEY',width:180,sortable:'true',align:'right'">第三方支付平台快捷业务最高限额(元)</th>
				</tr>
			</thead>
		</table>
 
	<!--搜索栏结果end-->

	<!-- foot -->

	<div id="tb2" style=" padding:0px">    
		   <div style="height:70px;"> 
		    <form id="searchForm" name="searchForm" method="post">
			<table cellpadding="5">
				<tr>
					<input class="easyui-textbox" type="hidden" id="dicCode"
						name="dicCode" value="${dicCode}" />
						<input class="easyui-textbox" type="hidden" id="parentId"
						name="parentId" value="${parentId}" />
					<%-- <td>第三方平台编码:</td>
								
									<td><input class="easyui-textbox" type="text"
											class="easyui-textbox" id="thirdPartyType" name="thirdPartyType"
												value="${thirdPartyType}"></input></td> --%>
					<td style="text-align:right">字段名称：</td>
					<td><input class="easyui-textbox" type="text" id="fieldName"
						name="fieldName" value="${fieldName}" style="width:100px;"
						data-options="required:true" /></td>
					
					
					
					<td style="text-align:right">创建时间：</td>
					<td><input type="text" id="beginTime" name="beginTime"
						value="${beginTime}" class="easyui-datebox" style="width: 90px;"
						data-options="required:false,editable:false" /> 
					 -
					 <input type="text" id="endTime" name="endTime"
						value="${endTime}" class="easyui-datebox" style="width: 90px;"
						data-options="required:false,editable:false" /></td>
				 	</tr>
				 	<tr>
				 	<td style="text-align:right">字段类型：</td>
					<td><select style="width: 100px" name="fieldType" 	 
						id="fieldType">
							<option  value="" >请选择</option>
							<option value="0" <c:if test="${fieldType == 0}">selected</c:if>>银行编码</option>
							<option value="1" <c:if test="${fieldType == 1}">selected</c:if>>币种</option>
							<option value="2" <c:if test="${fieldType == 2}">selected</c:if>>银行卡类型</option>
							<option value="3" <c:if test="${fieldType == 3}">selected</c:if>>证件类型</option>
							<option value="4" <c:if test="${fieldType == 4}">selected</c:if>>对公对私标志</option>
					</select></td>
				 	<td style="text-align:right">第三方平台字段编码：</td>
					<td><input class="easyui-textbox" type="text"
						id="thirdFieldCode" name="thirdFieldCode"
						value="${thirdFieldCode}" style="width: 100px;"
						data-options="required:true" /></td>

				</tr>
			</table>

			<%-- <div class="search-btn-area">
							<input id="search_btn" type="button" class="input-btn-small" value="查 询" />
							<input id="clear_btn" type="button" class="input-btn-small" value="清 除" />
								 <c:if test="${null != sessionScope.permMap['/bim/thirdField/addThirdField']}">
										  <a class="btn tip-bottom" href="${path}/bim/thirdField/thirdFieldEditUI/null"><i class="icon-add"></i>新增</a>	
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
	
	
	var ddTThirdFieldMapper_list ={};
	jQuery(function($){
	//定义构造查询
 
		ddTThirdFieldMapper_list.buildQueryParams=function(){
    $('#dg_ddTThirdFieldMapper').datagrid("options").queryParams={
					 'id':$("input[name='search_id']").val(),
					 'thirdPartyType':$("input[name='thirdPartyType']").val(),
					 'tppFieldCode':$("input[name='tppFieldCode']").val(),
					 'thirdFieldCode':$("input[name='thirdFieldCode']").val(),
					 'fieldType':$("select[name='fieldType']").val(),
					 'fieldName':$("input[name='fieldName']").val(),
					 'beginTime' : $("input[name='beginTime']").val(),	
					 'endTime' : $("input[name='endTime']").val(),
		};
 
};


//定义构造查询JSON
ddTThirdFieldMapper_list.buildJsonQueryParams = function(){
	  	var searchContent =	{
	  		//标准查询部分
		 	pageNumber:$('#dg_ddTThirdFieldMapper').datagrid('options').pageNumber,
	  		//页面查询框部分
				id :$("input[name='search_id']").val(), 
				third_party_type :$("input[name='search_third_party_type']").val(), 
				tpp_field_code :$("input[name='search_tpp_field_code']").val(), 
				third_field_code :$("input[name='search_third_field_code']").val(), 
				fieldName :$("input[name='fieldName']").val(), 
				fieldType :$("input[name='fieldType']").val(), 
				field_type :$("input[name='search_field_type']").val(), 
				collect_max_money :$("input[name='search_collect_max_money']").val(), 
				pay_max_money :$("input[name='search_pay_max_money']").val(), 
				quick_pay_max_money :$("input[name='search_quick_pay_max_money']").val(), 
	    };
		var searchContentStr  =JSON.stringify(searchContent);
		//alert(searchContentStr);
		//传到到后台的URL 必须先编码化
		return encodeURI(searchContentStr);
 }


//重新按照条件刷新查询内容
$('#searchBut2').click(function(){
	 var startTime= $("input[name='beginTime']").val();
	 var endTime= $("input[name='endTime']").val();
	 
	 if(startTime!=null && startTime!='' && endTime!=null && endTime!=''){
			var start=new Number(startTime.replace("-", "").replace("-", "")); 	
			var end=new Number(endTime.replace("-", "").replace("-", ""));
			 if(end<start){
					$.messager.alert("提示","查询开始时间不能大于结束时间！");
			 	return false;
			  }
	} 
	ddTThirdFieldMapper_list.buildQueryParams();
	$('#dg_ddTThirdFieldMapper').datagrid('load');
});
//删除
$('#delBut2').click(function(){
	var checkedItems = $('#dg_ddTThirdFieldMapper').datagrid(
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
									url : '${path}/bim/thirdField/thirdFieldDelete/'
											+ names,
									data : names,
									dataType : "json",
									success : function(
											data) {
										$(
												'#dg_ddTThirdFieldMapper')
												.datagrid(
														'load');
									}
								});
					}
				});
		}else{
			$.messager.alert("提示","请选中一行");
		}
});
//重置查询条件并刷新内容
$('#clear_btn2').click(function(){
	//清空查询条件
		$("input[name='search_id']").val("");
		$("input[name='thirdPartyType']").val("");
		$("input[name='tppFieldCode']").val("");
		$("input[name='thirdFieldCode']").val("");
		$("select[name='fieldType']").val("");
		$("input[name='fieldName']").val("");
		$('#beginTime').combo('setText','');  
		$('#endTime').combo('setText','');  
		 $("input[name='beginTime']").val(""),
		 $("input[name='endTime']").val(""),
		ddTThirdFieldMapper_list.buildQueryParams();
		$('#dg_ddTThirdFieldMapper').datagrid('load');
 
});


//新增
$('#addBut2').click(function(){
	var add_form_id ='#thirdFieldSave';
	var parentId=$('#parentId').val();	
	$('<div id="dialog_holder"></div>').dialog({
	   title:'新增系统字段映射',
	    width: 500,
	    height: 400,
	    href: '${path}/bim/thirdField/thirdFieldEditUI/'+ null + '/' + $('#dicCode').val()+'/'+parentId,
	    modal: true,
	    method: "POST",
		onClose: function(){
			$(this).dialog("destroy");
		},
	    buttons: [{
	    	text: "提  交",
	    	handler: function(e){
	    		$('#thirdFieldSave').form({   
					 url: '${path}/bim/thirdField/thirdFieldSave',
						      onSubmit: function(){
						 
						     },    
						     success:function(data){
						      
							    var obj = eval("("+ data+ ")");	
						      if (obj.valmsg != null) {
									$.messager.alert('提示',obj.valmsg);
								} else {
								$.messager.progress('close');
								ddTThirdFieldMapper_list.buildQueryParams();
								$('#dg_ddTThirdFieldMapper').datagrid('load');
								$('#dialog_holder').dialog('close');
								}
						     }  
					});// 
				

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
	var edit_form_id ='#thirdFieldSave';  
	var rowInfo = $("#dg_ddTThirdFieldMapper").datagrid('getChecked');
	if(rowInfo.length<1){
		$.messager.alert('提示','请选中一行');
	}
	else {

		if(rowInfo.length>1){
			$.messager.alert('提示','请选中一行');
			return;
		}
	var id=	rowInfo[0].ID;
	$('<div id="dialog_holder"></div>').dialog({
	    title:'修改系统字段映射', 
	    width: 500,
	    height: 400,
	    href:  '${path}/bim/thirdField/thirdFieldEditUI/'+ id
		+ '/'
		+ $('#dicCode')
				.val()+'/'+$('#parentId')
				.val(),
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
	   				$('#thirdFieldSave').form({   
	   				 url: '${path}/bim/thirdField/thirdFieldSave',
						     onSubmit: function(){
						   /*     $.messager.progress();  */
						        // do some check       
						        // return false to prevent submit;    
						     },    
						     success:function(data){
						    	 
						        var obj=eval( "(" + data + ")" );
							       	if(obj.valmsg!=null){
							       		$.messager.alert('提示',obj.valmsg);	
							       	}else{  
						        $.messager.progress('close');
								ddTThirdFieldMapper_list.buildQueryParams();
								$('#dg_ddTThirdFieldMapper').datagrid('load');
								$('#dialog_holder').dialog('close');
						     }
						     }     	 
					});// 
					
					 $(".easyui-validatebox").each(function(){
			        	 $(this).validatebox({    
			        		    novalidate: false   
			        		}); 
			        	 
			        });
					 
				    if($('#thirdFieldSave').form("validate")==true){
					   
				    	$('#thirdFieldSave').submit();
					};  
	    		
	    	}
	    }
	 
	    ,{
	    	text: "取 消",
	    	handler: function(e){
	    		$(this).dialog("close");
	    	}
	    }]
	});}
});


});
	function updateFlag(id,status){
		  $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {
			  if (data) {
		  $.ajax({
            type: 'GET',
            url: '${path}/bim/thirdField/updateStatus/'+id+'/'+status,
      
            dataType: "json",
            success:function(data){
            	ddTThirdFieldMapper_list.buildJsonQueryParams = function(){
            	  	var searchContent =	{
            	  		//标准查询部分
            		 	pageNumber:$('#dg_ddTThirdFieldMapper').datagrid('options').pageNumber,
            	  		//页面查询框部分
            				id :$("input[name='search_id']").val(), 
            				third_party_type :$("input[name='search_third_party_type']").val(), 
            				tpp_field_code :$("input[name='search_tpp_field_code']").val(), 
            				third_field_code :$("input[name='search_third_field_code']").val(), 
            				fieldName :$("input[name='fieldName']").val(), 
            				fieldType :$("input[name='fieldType']").val(), 
            				field_type :$("input[name='search_field_type']").val(), 
            				collect_max_money :$("input[name='search_collect_max_money']").val(), 
            				pay_max_money :$("input[name='search_pay_max_money']").val(), 
            				quick_pay_max_money :$("input[name='search_quick_pay_max_money']").val(), 
            	    };
            		var searchContentStr  =JSON.stringify(searchContent);
            		//alert(searchContentStr);
            		//传到到后台的URL 必须先编码化
            		return encodeURI(searchContentStr);
             };
	
            	ddTThirdFieldMapper_list.buildQueryParams();
        		$('#dg_ddTThirdFieldMapper').datagrid('load');
            }
        });
			  }
		  });
	} 
	
</script>

</body>
</html>
