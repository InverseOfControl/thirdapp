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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body class="easyui-layout">
	<div class="easyui-panel" title="查询条件"
		style="padding: 5px; height: 144px; margin: 0px;"
		data-options="region:'north'">
	 
			<!--搜索栏开始-->
					<form id="searchForm" name="searchForm" method="post">
						<input type="hidden" id="appCode" name="appCode" class="easyui-textbox" value="${appCode}"/>
						<input id="id" name="id" type="hidden" value="${threadPool.id}" />
							<table  cellpadding="5">
								<tr>
									<td style="text-align:right">证大客户编号：</td> 
									<td><input type="text" id="ip" name="zengDaiAccountNo" value="${zengDaiAccountNo}" class="easyui-textbox"
										style="width: 100px;" data-options="required:true" /></td>
									<td style="text-align:right">客户姓名：</td> 
									<td><input type="text" id="realName" name="realName" value="${realName}" class="easyui-textbox"
										style="width: 100px;" data-options="required:true" /></td>
									<td style="text-align:right">客户手机号：</td> 
									<td><input type="text" id="mobile" name="mobile" value="${mobile}" class="easyui-textbox"
										style="width: 100px;" data-options="required:true" /></td>	
								
								
								
								</tr>	
								<tr>
								<td style="text-align:right">第三方支付平台：</td>
								<td>
										<input id="paySysNo" class="easyui-combobox" name="paySysNo" data-options="
												editable:false,valueField: 'value',
												textField: 'name',
												//url是下拉框请求显示的数据
												url:'${path }/enumset/dictionary/3' " style="width:100px;"/>  
								</td>	
									<td style="text-align:right">状态：</td>
										<td> 
										 <input style="width: 100px" name="status"  id="status" 
															class="easyui-combobox" id="priority"
															data-options="editable:false,data:[{'type':'','text':'请选择'},{'type':'1','text':'正常'},{'type':'2','text':'冻结'},{'type':'3','text':'销户'}  
															 ],	valueField:'type',textField:'text',value:'${status}'" />
										</td>
									<td style="text-align:right">开户时间： </td>
									<td><input type="text" id="beginTime" name="beginTime"
										value="${beginTime}"  class="easyui-datebox"
										style="width: 100px;"   data-options="required:false,editable:false"/> 
								 	-
									 <input type="text" id="endTime" name="endTime"
										value="${endTime}" class="easyui-datebox"
										style="width: 100px;"   data-options="required:false,editable:false"/></td>
								 </tr>
								 <tr>
								 	<td style="text-align:right">业务系统：</td>
									<td>
											<input id="bizSysNo" class="easyui-combobox" name="bizSysNo" data-options="
													editable:false,valueField: 'value',
													textField: 'name',
														//url是下拉框请求显示的数据
													url:'${path }/enumset/bizSysNoList' " style="width:100px;"/>  
									</td>
								 </tr>
							</table>
					</form>
				</div>
			 
			<!--搜索栏结束-->

			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding: 0px; margin: 0px;"
				data-options="region:'center'">
				<table id="dg_tppOpenAccount" class="easyui-datagrid"
				 	 style="width: auto; height: 300px"
					data-options="rownumbers:true,selectOnCheck: 'true',checkOnSelect:true,collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/trade/openAccount/openAccountListData',method:'post',toolbar:toolbar,fit:'true'
							,pageSize:20,onLoadSuccess:function(){loadQueryParam();$(this).datagrid('fixRownumber');},onBeforeLoad:function(param){loadBefore(param)},onClickRow:function(rowIndex, rowData){ $('#dg_tppOpenAccount').datagrid('unselectRow',rowIndex);}
					">
					<thead>
						<tr>
							<th
								data-options="field:'VIEW_CARDS',width:100,sortable:'true',align:'center'">查看绑卡信息</th>
							<th
								data-options="field:'ID',width:150,sortable:'true',align:'left',hidden:'true'">ID</th>
							<th
								data-options="field:'ZENGDAI_ACCOUNT_NO',width:190,sortable:'true',align:'left'">证大客户编号</th>
							<th
								data-options="field:'BIZ_SYS_NAME',width:190,sortable:'true',align:'left'">业务系统</th>
							<th
								data-options="field:'BIZ_SYS_ACCOUNT_NO',width:180,sortable:'true',align:'left'">业务系统客户编号</th>
							<th
								data-options="field:'THIRD_ACCOUNT_NO',width:180,sortable:'true',align:'left'">客户第三方账户编号</th>
							<th
								data-options="field:'USER_NAME',width:180,sortable:'true',align:'left'">登录名</th>
							
							<th
								data-options="field:'REAL_NAME',width:180,sortable:'true',align:'left'">客户姓名</th>
							<th
								data-options="field:'GENDER',width:180,sortable:'true',align:'left'">性别</th>
							<th
								data-options="field:'NATION',width:180,sortable:'true',align:'left'">民族</th>
							<th
								data-options="field:'MOBILE',width:180,sortable:'true',align:'left'">客户手机号码</th>
							<th
								data-options="field:'PAY_SYS_NAME',width:180,sortable:'true',align:'left'">第三方支付平台</th>
							<th
								data-options="field:'STATUS',width:180,sortable:'true',align:'left'">状态</th>
							<th
								data-options="field:'ID_TYPE',width:180,sortable:'true',align:'left'">证件类型</th>
							<th
								data-options="field:'ID_NO',width:180,sortable:'true',align:'left'">证件号</th>
							<th
								data-options="field:'REQ_ID',width:180,sortable:'true',align:'left'">请求Id</th>
							<th
								data-options="field:'OPEN_TIME',width:180,sortable:'true',align:'left'">开户时间</th>
							<th
								data-options="field:'CREATE_TIME',width:180,sortable:'true',align:'left'">创建时间</th>
							<th
								data-options="field:'UPDATE_TIME',width:180,sortable:'true',align:'left'">修改时间</th>
						</tr>
					</thead>
				</table>
			</div>
	<script>
	var toolbar = [{
		id : 'searchBut',
		text : '查询',
		iconCls : 'icon-search'
		 
	},'-',{
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
	function loadBefore(param) {
		var search_begin_date = $("#beginTime").datebox("getValue");
		var search_end_date = $("#endTime").datebox("getValue");
		if (search_begin_date == '' && search_end_date == '') {
			param.beginTime=CurentTime("begin");
			param.endTime=CurentTime("end");
		 
	  	}
		  
		 
	}
	function CurentTime(type)
	{ 
	    if(type=="begin"){
	    	
	    	var d=new Date(); 
		    d.setDate(d.getDate()+(-30)); 
		    
		    var year = d.getFullYear();  //年
		    var month =d.getMonth() + 1;
		        //月
		    var day = d.getDate();            //日
		    
		    var m=d.getMonth()+1; 
		    
		    var clock = year + "-";
			   
		    if(month < 10)
		        clock += "0";
		   
		    clock += month + "-";
		   
		    if(day < 10)
		        clock += "0";
		    clock += day ;
	    	return(clock); 
	    }else{
	    	var now = new Date();
	 	   
		    var year = now.getFullYear();  //年
		    var month =now.getMonth() + 1;
		        //月
		    var day = now.getDate();            //日
		   
		    var hh = now.getHours();            //时
		    var mm = now.getMinutes();          //分
		   
		    var clock = year + "-";
		   
		    if(month < 10)
		        clock += "0";
		   
		    clock += month + "-";
		   
		    if(day < 10)
		        clock += "0";
		    clock += day ;
	    	return(clock); 
	    }
	    
	   
	} 
		var tppSysAppIps_list = {};
		jQuery(function($) {			 
			$("#beginTime").datebox('setValue', CurentTime("begin"));
			$("#endTime").datebox('setValue', CurentTime("end"));
			//定义构造查询
			tppSysAppIps_list.buildQueryParams = function() {
				$('#dg_tppOpenAccount').datagrid("options").queryParams={
						'zengDaiAccountNo' : $("input[name='zengDaiAccountNo']").val(),
						'realName' : $("input[name='realName']").val(),
						'mobile' : $("input[name='mobile']").val(),
						'bizSysNo' : $("input[name='bizSysNo']").val(),
						'paySysNo' : $("input[name='paySysNo']").val(),
						'status' : $("input[name='status']").val(),
						'beginTime' : $("input[name='beginTime']").val(),
						'endTime' : $("input[name='endTime']").val(),
				};
			};
			//重新按照条件刷新查询内容
			$('#searchBut').click(
					function() {
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
						tppSysAppIps_list.buildQueryParams();
						$('#dg_tppOpenAccount')
								.datagrid('load');
					});

			//重置查询条件并刷新内容
			$('#clear_btn').click(
					function() {
						 
						//清空查询条件
						$('#status').combobox('setValue', '');	
						$('#bizSysNo').combobox('setValue', '');
						$('#paySysNo').combobox('setValue', '');
						$("input[name='zengDaiAccountNo']").val("");
						$("input[name='realName']").val("");
						$("input[name='mobile']").val("");
						$("#beginTime").datebox('setValue', CurentTime("begin"));
						$("#endTime").datebox('setValue', CurentTime("end")); 
						$("input[name='beginTime']").val(""),
						$("input[name='endTime']").val(""),
						tppSysAppIps_list.buildQueryParams();
						$('#dg_tppOpenAccount')
								.datagrid('load');
					});

			

		});
		function updateAppIp(id) {

			window.location.href = contextPath
					+ "/bim/appIps/appIpsEditUI/" + id;

		}
		function updateFlag(id,flag){
			
			window.location.href = contextPath+"/bim/sysApp/sysAppUpdateIsFlag/"+id+"/"+flag;
		}
		function loadQueryParam(){
		 
			$('#dg_tppOpenAccount').datagrid("options").queryParams={
				'zengDaiAccountNo' : $("input[name='zengDaiAccountNo']").val(),
				'realName' : $("input[name='realName']").val(),
				'mobile' : $("input[name='mobile']").val(),
				'bizSysNo' : $("input[name='bizSysNo']").val(),
				'paySysNo' : $("input[name='paySysNo']").val(),
				'status' : $("input[name='status']").val(),
				'beginTime' : $("input[name='beginTime']").val(),
				'endTime' : $("input[name='endTime']").val(),
		};
			 
		}
		function viewCards(zengdaiAccountNo,bizsysAccountNo,thirdAccountNo){
			$('<div id="dialog_holder"></div>')
			.dialog(
					{
						title : '绑卡信息列表',
						width : 800,
						height : 400,
						href : '${path}/trade/openAccount/openAccountCardsList',
						modal : true,
						method : "POST",
						params : {
							zengdaiAccountNo : zengdaiAccountNo,bizsysAccountNo:bizsysAccountNo,thirdAccountNo:thirdAccountNo
						},
						onClose : function() {
							$(this).dialog("destroy");
						},
						buttons : [
								{
									text : "关闭",
									handler : function(e) {
										$(this).dialog("close");
									}
								} ]
					});
		}
	</script>
</body>
</html>
