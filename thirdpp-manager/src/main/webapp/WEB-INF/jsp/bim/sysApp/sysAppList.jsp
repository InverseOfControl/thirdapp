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
</head>
<body class="easyui-layout">
	 <%--  <div class="easyui-panel" title="查询条件"
		style="padding: 5px; height: 75px; margin: 0px;"
		data-options="region:'north'">		 
			<!--搜索栏开始-->
					<form id="searchForm" name="searchForm" method="post">
					 	
						  <table cellpadding="5">
								<tr>
									<td>业务系统名称： </td>
									<td><input type="text" id="appName" name="seappName" class="easyui-textbox"
										value="${appName}" style="width: 200px;"
										data-options="required:true" /></td>
									<td>状态： </td>
									<td><select style="width: 150px" name="seisFlag"
										class="form-control" id="isFlag">
											<option></option>
											<option value="0" <c:if test="${isFlag == 0}">selected</c:if>>不可用</option>
											<option value="1" <c:if test="${isFlag == 1}">selected</c:if>>可用</option>
									</select></td>
									 
									<td>创建开始时间 </td>
									<td>
										<input type="text" id="beginTime" name="beginTime"  value="${beginTime}" class="easyui-datebox"   style="width:200px;"   />
									</td>
									<td>创建结束时间</td>
									<td>
										<input type="text" id="endTime" name="endTime"  value="${endTime}"  class="easyui-datebox"   style="width:200px;"   />
									</td>

								</tr>
								 
							</table>  
					</form>
			 </div> -  --%>
			 <input type="hidden" id="id" name="id" class="easyui-textbox" value="${id}"/>
			<!--搜索栏结束-->
			<!--搜索结果开始-->
			 <!-- div class="easyui-panel" style="padding: 0px; margin: 0px;"
						data-options="region:'center'"> -->
				<table id="dg_tppSysAppSon" class="easyui-datagrid"
				 		style="height:50px"
					data-options="rownumbers:true,collapsible:true ,checkOnSelect:false,sortName:'',sortOrder:'desc',url:'${path }/bim/sysApp/sysAppListData/${id}',method:'post',fit:true
							,pageSize:20,onClickRow:function(rowIndex, rowData){ $('#dg_tppSysAppSon').datagrid('unselectRow',rowIndex);}
					">
					<thead>
						<tr>
						<!-- 	<th
								data-options="field:'MESSAGETYPE',width:100,align:'center'">查看消息类别</th> -->
								<th
								data-options="field:'MESSAGETYPE',width:120,sortable:'true',align:'center'">操作</th> 
								<th
								data-options="field:'IS_FLAG',width:100,sortable:'true',align:'right'">状态</th>
							<th
								data-options="field:'ID',width:120,sortable:'true',align:'right',hidden:'true'">ID</th>
							<th
								data-options="field:'APP_NAME',width:120,sortable:'true',align:'right'">业务系统名称</th>
							<th
								data-options="field:'APP_CODE',width:120,sortable:'true',align:'right'">业务系统编码</th>
							
							<th
								data-options="field:'CONTACT_NAME',width:120,sortable:'true',align:'right'">联系人姓名</th>
							<th
								data-options="field:'CONTACT_MOBILE',width:120,sortable:'true',align:'right'">联系人手机</th>
							<th
								data-options="field:'REMARK',width:120,sortable:'true',align:'right'">	备注</th>
							<th
								data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'right'">	创建时间</th>
							<th
								data-options="field:'CREATER',width:120,sortable:'true',align:'right'">创建人</th>
							<th
								data-options="field:'UPDATE_TIME',width:120,sortable:'true',align:'right'">修改时间</th>
							<th
								data-options="field:'UPDATER',width:100,sortable:'true',align:'right'">修改人</th>
							<th
								data-options="field:'COLLECT_NOTIFY_URL',width:120,sortable:'true',align:'right'">线下代扣通知URL</th>
						 
							<th
								data-options="field:'PAY_NOTIFY_URL',width:120,sortable:'true',align:'right'">线下代付通知URL</th>
								<th
								data-options="field:'CASH_BACK_NOTIFY_URL',width:120,sortable:'true',align:'right'">线上退款通知URL</th>
								<th
								data-options="field:'CASH_DRAW_NOTIFY_URL',width:120,sortable:'true',align:'right'">线上提现通知URL</th>
								<th
								data-options="field:'ORDER_PAY_NOTIFY_URL',width:120,sortable:'true',align:'right'">线上订单支付通知URL</th>	
						</tr>
					</thead>
				</table>
			<!-- </div> -->
			<!--搜索栏结果end-->
		 

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
		iconCls : 'icon-cancel',
		handler : function() {
			var checkedItems = $('#dg_tppSysAppSon').datagrid('getChecked');
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
				                      url: '${path}/bim/sysApp/sysAppDelete/'+names,
				                      data: names,
				                      dataType: "json",
				                      success:function(data){
				                    	  $('#dg_tppSysAppSon').datagrid('load');  
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
	},{
		id : 'clear_btn',
		text : '清除',
		iconCls : 'icon-remove'
		/* handler : function() {
			var searchMsg =   $('#searchForm').serialize();
			//对参数进行解码(显示中文)
			searchMsg = decodeURIComponent(searchMsg);	 
			var queryParam = $.serializeToJsonObject(searchMsg);  
			queryParam.r = new Date().getTime();
			$('#dg_ddTBankInfo').datagrid('reload',queryParam);
		} */
	}];
		 
		jQuery(function($) {
			//定义构造查询
			 
			//定义构造查询JSON
		 
			//重置查询条件并刷新内容
			 
			//新增
	 
			//更新
		 
		});
		function toMessageTypeUI(id){
	 
			$('<div id="dialog_holder"></div>').dialog({
			    title: '',
			    width: 1200,
			    height: 600,			    
			    href: '${path}/bim/sysApp/toInfoCategoryAppList/'+id,
			    modal: true,
			    method: "POST",
			    buttons: [{
			    	text: "关闭",
			    	handler: function(e){
			    		$(this).dialog("close");
			    	}
			    }]
			});
		} 
		function updateFlag(id,flag){
			$.messager.confirm('确认','您确定要改变状态吗？',function(r){
				if (r){    
					//window.location.href = contextPath+"/bim/sysApp/sysAppUpdateIsFlag/"+id+"/"+flag;
					
					$.ajax({
						url:'${path}/bim/sysApp/sysAppUpdateIsFlag',
						data:{"id":id,"flag":flag},
						dataType:"JSON",
						type:"POST",
						async:true,
						success:function(data){
							$('#dg_tppSysAppSon').datagrid('load');
						},
						error:function(data){
							alert("请求超时");
						}
					});	
					
					
				 }
			});
		}
		
	</script>
</body>
</html>
