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
	<!--搜索结果开始-->
	<div class="easyui-panel" title="查询条件"
		style="padding: 5px; height: 75px; margin: 0px;"
		data-options="region:'north'">
		<table cellpadding="5" style="height: 15px">
			<tr>
				<td style="text-align: right">信息类别名称：</td>
				<td><input type="text" id="cateGoryName" name="cateGoryName"
					class="easyui-textbox" value="${cateGoryName}"
					style="width: 100px;" data-options="required:true" /> <input
					type="hidden" id="dicCode" name="dicCode" class="easyui-textbox"
					value="${dicCode}" style="width: 100px;"
					data-options="required:true" /> <input type="hidden" id="parentId"
					name="parentId" class="easyui-textbox" value="${parentId}"
					style="width: 100px;" data-options="required:true" /></td>
				<td style="text-align: right">优先级：</td>
				<td><select style="width: 100px" name="priority"
					class="form-control" id="priority">
						<option value="">请选择</option>
						<option value="0" <c:if test="${priority == 0}">selected</c:if>>普通</option>
						<option value="1" <c:if test="${priority == 1}">selected</c:if>>中</option>
						<option value="2" <c:if test="${priority == 2}">selected</c:if>>高</option>
						<option value="3" <c:if test="${priority == 3}">selected</c:if>>最高</option>
				</select></td>
				<td style="text-align: right">业务系统：</td>
				<td><input id="search_biz_sys_no" class="easyui-combobox"
					name="search_biz_sys_no"
					data-options="
											editable:false,valueField: 'value',
											textField: 'name',
											//url是下拉框请求显示的数据
											url:'${path }/enumset/bizSysNoList' "
					style="width: 100px;" /></td>
			</tr>
		</table>
	</div>
	<div class="easyui-panel" style="padding: 0px; margin: 0px;"
		data-options="region:'center'">
		<table id="dg_tppSysTInfoCategory" class="easyui-datagrid"
			style="width: auto; height: 300px"
			data-options="rownumbers:true,selectOnCheck: 'true',checkOnSelect:true, collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/bim/categoryApp/categoryAppListData/${dicCode}',method:'post',fit:'true',toolbar:toolbar
									,pageSize:20,onClickRow:function(rowIndex, rowData){ $('#dg_tppSysTInfoCategory').datagrid('unselectRow',rowIndex);},
									onLoadSuccess:function(data){ 
																  $('#dg_tppSysTInfoCategory').datagrid('doCellTip',{cls:{'background-color':'white'},delay:1000,tdname:['INFO_CATEGORY_NAME','NOTE']});
																 
									  } 
					">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:'true'"></th>
					<th
						data-options="field:'APPNAME',width:150,sortable:'true',align:'center'">系统详情</th>
					<th
						data-options="field:'CHANNEL_CONFIGURE',width:150,sortable:'true',align:'center'">通道配置</th>
					<th
						data-options="field:'ID',width:120,sortable:'true',align:'left',hidden:'true'">ID</th>
					<th
						data-options="field:'INFO_CATEGORY_CODE',width:140,sortable:'true',align:'left'">信息类别编码</th>
					<th
						data-options="field:'INFO_CATEGORY_NAME',width:150,sortable:'true',align:'left'">信息类别名称</th>
					<th
						data-options="field:'PRIORITY',width:150,sortable:'true',align:'left'">优先级</th>
					<th
						data-options="field:'CHANNEL_RULES',width:150,sortable:'true',align:'left'">通道规则</th>
					<th
						data-options="field:'PAYMENT_CHANNEL_NAME',width:150,sortable:'true',align:'left'">第三方支付平台</th>
					<th
						data-options="field:'CREATER',width:150,sortable:'true',align:'left'">创建人</th>
					<th
						data-options="field:'CREATE_TIME',width:150,sortable:'true',align:'left'">创建时间</th>
					<th
						data-options="field:'UPDATE_TIME',width:150,sortable:'true',align:'left'">修改时间</th>
					<th
						data-options="field:'UPDATER',width:150,sortable:'true',align:'left'">修改人</th>
					<th
						data-options="field:'MERCHANT_TYPE',width:150,sortable:'true',align:'left'">商户类型</th>
					<th
						data-options="field:'NOTE',width:170,sortable:'true',align:'left'">备注</th>
				</tr>
			</thead>
		</table>
	</div>
	<!--搜索栏结果end-->
	<script>
		function formatA(value, row, index) {
			return '<span data-p1='+index+' class="easyui-tooltip">' + value
					+ '</span>';
		}
		function createTooltip() {

			$('#dg_tppSysTInfoCategory').datagrid('getPanel').find(
					'.easyui-tooltip').each(
					function() {
						var index = parseInt($(this).attr('data-p1'));
						$(this).tooltip(
								{
									content : $('<div></div>'),
									onUpdate : function(cc) {
										var row = $('#dg_tppSysTInfoCategory')
												.datagrid('getRows')[index];
										var content = '<div>content</div><ul>';
										content += '<li>name: '
												+ row.INFO_CATEGORY_NAME
												+ '</li>';
										content += '</ul>';
										cc.panel({
											width : 200,
											content : content
										});
									},
									position : 'right'
								});
					});
		}
		var toolbar = [
				{
					id : 'addBut',
					text : '新增',
					iconCls : 'icon-add',
				/* 	handler : function() {
						 viewInfo(null);
					} */
				},
				{
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
				},
				{
					id : 'delBut',
					text : '删除',
					iconCls : 'icon-cancel',
					handler : function() {
						var checkedItems = $('#dg_tppSysTInfoCategory')
								.datagrid('getChecked');
						var names = [];
						var codes = [];
						$.each(checkedItems, function(index, item) {
							names.push(item.ID);
							codes.push(item.INFO_CATEGORY_CODE);
						});
						if (names.length > 0) {
							$.messager
									.confirm(
											"操作提示",
											"您确定要执行操作吗？",
											function(data) {

												if (data) {
													$
															.ajax({
																type : 'GET',
																url : '${path}/bim/categoryApp/infoAppsDelete/'
																		+ names
																		+ '/'
																		+ codes,
																data : names,
																dataType : "json",
																success : function(
																		data) {
																	$(
																			'#dg_tppSysTInfoCategory')
																			.datagrid(
																					'load');
																}
															});
												}
											});
						} else {
							$.messager.alert("提示", "请选中一行")
						}

					}
				}, '-', {
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
				}, {
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
				} ];

		var tppSysAppIps_list = {};
		jQuery(function($) {
			//定义构造查询
			tppSysAppIps_list.buildQueryParams = function() {
				$('#dg_tppSysTInfoCategory').datagrid("options").queryParams = {

					'cateGoryName' : $("input[name='cateGoryName']").val(),
					'priority' : $("select[name='priority']").val(),
					'search_biz_sys_no' : $('#search_biz_sys_no').combobox(
							"getValue"),

				};

			};

			//定义构造查询JSON

			//重新按照条件刷新查询内容
			$('#searchBut').click(function() {
				tppSysAppIps_list.buildQueryParams();
				$('#dg_tppSysTInfoCategory').datagrid('load');
			});

			//重置查询条件并刷新内容
			$(' #clear_btn').click(function() {
				//清空查询条件

				$("input[name='cateGoryName']").val("");
				$("select[name='priority']").val("");
				$('#search_biz_sys_no').combobox('setValue', '');

				tppSysAppIps_list.buildQueryParams();
				$(' #dg_tppSysTInfoCategory').datagrid('load');
			});

			//新增
			$('#addBut')
					.click(
							function() {
								var add_form_id = '#infoCategoryAppsSave';

								$('<div id="dialog_holder"></div>')
										.dialog(
												{
													title : '新增信息类别',
													width : 500,
													height : 400,
													href : '${path}/bim/categoryApp/categoryAppsEditUI/'
															+ null,
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
														$(this).dialog(
																"destroy");
													},
													buttons : [
															{
																text : "提  交",
																handler : function(
																		e) {
																	$(
																			'#infoCategoryAppsSave')
																			.form(
																					{
																						url : '${path}/bim/categoryApp/categoryAppsSave',
																						onSubmit : function() {

																							// do some check       
																							// return false to prevent submit;    
																						},
																						success : function(
																								data) {

																							var obj = eval("("
																									+ data
																									+ ")");
																							if (obj.valmsg != null) {
																								$.messager
																										.alert(
																												'提示',
																												obj.valmsg);
																							} else {
																								$.messager
																										.progress('close');
																								$(
																										'#dg_tppSysTInfoCategory')
																										.datagrid(
																												'load');
																								$(
																										'#dialog_holder')
																										.dialog(
																												'close');
																							}
																						},
																						error : function(
																								XMLHttpRequest,
																								textStatus,
																								errorThrown) {

																							$.messager
																									.progress('close');
																							$(
																									'#dg_tppSysTInfoCategory')
																									.datagrid(
																											'load');
																							$(
																									'#dialog_holder')
																									.dialog(
																											'close');

																						},
																						complete : function(
																								XMLHttpRequest,
																								textStatus) {

																							$.messager
																									.progress('close');
																							$(
																									'#dg_tppSysTInfoCategory')
																									.datagrid(
																											'load');
																							$(
																									'#dialog_holder')
																									.dialog(
																											'close');
																						}
																					});// 
																	$(
																			".easyui-validatebox")
																			.each(
																					function() {
																						$(
																								this)
																								.validatebox(
																										{
																											novalidate : false
																										});

																					});
																	if ($(
																			add_form_id)
																			.form(
																					"validate") == true) {

																		$(
																				add_form_id)
																				.submit();
																	}
																	;

																}
															},
															{
																text : "取 消",
																handler : function(
																		e) {
																	$(this)
																			.dialog(
																					"close");
																}
															} ]
												});
							});

			//更新
			$('#updateBut')
					.click(
							function() {
								/* var rowInfo = $("#dg_tppSysTInfoCategory").datagrid('getSelections'); */
								var rowInfo = $('#dg_tppSysTInfoCategory')
										.datagrid('getChecked');
								/*  var names = [];
								 $.each(checkedItems, function(index, item){
								     names.push(item.ID);
								});  */

								var edit_form_id = '#infoCategoryAppsSave';

								if (rowInfo.length < 1) {
									$.messager.alert('提示', '请选中一行');
								} else {

									if (rowInfo.length > 1) {
										$.messager.alert('提示', '请选中一行');
										return;
									}
									var id = rowInfo[0].ID;

									$('<div id="dialog_holder"></div>')
											.dialog(
													{
														title : '修改信息类别',
														width : 500,
														height : 400,
														href : '${path}/bim/categoryApp/categoryAppsEditUI/'
																+ id,
														modal : true,
														method : "POST",

														extractor : function(
																data) {
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
															$(this).dialog(
																	"destroy");
														},
														buttons : [
																{
																	text : "保  存",
																	handler : function(
																			e) {
																		$(
																				'#infoCategoryAppsSave')
																				.form(
																						{
																							url : '${path}/bim/categoryApp/categoryAppsSave',
																							onSubmit : function() {

																								// do some check       
																								// return false to prevent submit;    
																							},
																							success : function(
																									data) {
																								//do some
																								var obj = eval("("
																										+ data
																										+ ")");
																								if (obj.valmsg != null) {
																									$.messager
																											.alert(
																													'提示',
																													obj.valmsg);
																								} else {
																									$.messager
																											.progress('close');
																									/* tppSysApp_list
																											.buildQueryParams(); */
																									$(
																											'#dg_tppSysTInfoCategory')
																											.datagrid(
																													'load');
																									$(
																											'#dialog_holder')
																											.dialog(
																													'close');
																								}
																							}
																						});// 
																		$(
																				".easyui-validatebox")
																				.each(
																						function() {
																							$(
																									this)
																									.validatebox(
																											{
																												novalidate : false
																											});

																						});
																		if ($(
																				edit_form_id)
																				.form(
																						"validate") == true) {

																			$(
																					edit_form_id)
																					.submit();
																		}
																		;
																	}
																},
																{
																	text : "取 消",
																	handler : function(
																			e) {
																		$(this)
																				.dialog(
																						"close");
																	}
																} ]
													});
								}
							});
			//详细
			tppSysAppIps_list.detailFormSubmit = function(pkid) {
				$('<div id="dialog_holder"></div>')
						.dialog(
								{
									title : '详细TppSysAppIps',
									width : 800,
									height : 500,
									href : 'detailTppSysAppIpsPage.action',
									modal : true,
									method : "POST",
									params : {
										pkid : pkid
									},
									extractor : function(data) {
										var bodyPattern = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
										var contentPattern = /<article\s+id="content"[^>]*>((.|[\n\r])*)<\/article>/im;
										var scriptPattern = /<script[^>]*>(.|[\n\r\t])*?<\/script>/gim;
										var buttonPattern = /<div\s+class="button-field[\s\w]*"[^>]*>(.|[\n\r\t])*?<\/div>/im;
										var matches = bodyPattern.exec(data);
										if (matches) {
											var content = matches[1]
													.match(contentPattern);
											var scripts = matches[1]
													.match(scriptPattern);
											return (content[1] || "").replace(
													scriptPattern, "").replace(
													buttonPattern, "")
													+ (scripts || []).join("");
										} else {
											return data;
										}
									},
									onClose : function() {
										$(this).dialog("destroy");
									},
									buttons : [
											{
												text : "提  交",
												handler : function(e) {
													var ThisForm = $(this)
															.find("form");
												}
											}, {
												text : "关闭",
												handler : function(e) {
													$(this).dialog("close");
												}
											} ]
								});
			};

		});
		function toAppNameUI(code) {
			$('<div id="dialog_holder"></div>').dialog({
				title : '查看系统信息',
				width : 400,
				height : 300,
				href : '${path}/bim/categoryApp/appsNameEditUI/' + code,
				modal : true,
				method : "POST",
				onClose : function() {
					$(this).dialog("destroy");
				},
				buttons : [ {
					text : "关闭",
					handler : function(e) {
						$(this).dialog("close");
					}
				} ]
			});
		};
		
		 
		function checkAll(filed){
			$(":checkbox[name="+filed+"]").click(function(){
				var checked = this.checked;
				$("td[field="+filed+"]").each(function(){
					$(this).find(":radio").prop("checked",checked);
				});
				$(":checkbox[name!="+filed+"]").removeAttr("checked");
				$("tr").removeClass("datagrid-row-selected").removeClass("datagrid-row-checked");
			});
		}
		function singleConfigure(categoryCode,bankcode,bankenum){
			var param = "{\"categoryCode\":\""+categoryCode+"\",\"" +bankcode + "\":\"\"}";
			
			$(":radio[value^="+bankcode+"]").each(function(){
				if($(this).is(":checked")){
					param = "{\"categoryCode\":\""+categoryCode+"\",\"" +bankcode + "\":\""+ $(this).val() + "\"}";
					return false;
				}
			});
			console.log(JSON.parse(param));
			$.ajax({
	            type: "POST",
	            url:  '${path}/bim/categoryApp/configure',
	            dataType: "json",
	            data: JSON.parse(param),
	            success: function(data){
	            	if(data!=null && data.success==true){
			        	$.messager.alert('提示','配置成功');
						$('#dialog_holder').dialog('close');
			        }else{
			        	$.messager.alert('提示','配置失败,请联系管理员');
			        }
	            }
	        }); 
		}
		function rest(bankcode){
			$(":radio[value^="+bankcode+"]").prop('checked',false);
		}
		
		function channelConfigure(categoryCode) {
			var rows = "";
			$.ajax({
	            type: "POST",
	            url:  '${path}/bim/categoryApp/rows?radom='+ new Date().getTime(),
	            dataType: "json",
	            async:false,
	            cache: false,
	            success: function(data){
	                 $.each(data,function(i,v){
	                	 rows = rows + "<th align='center' data-options=\"field:'"+v.value+"'\">"+
	                	 "<input type=\"checkbox\" name=\"" + v.value + "\" onClick=\"checkAll('"+v.value+"')\">" +
	                	 v.name+
	                	 "</th>";
	                 });
	                 console.log(rows);
	            }
	        });
			$('<div id="dialog_holder"></div>').dialog({
				title : '银行通道配置',
				width : 1000,
				/* height : 500, */
				fitColumns:false,
				content : "<form id=\"configureForm\"><table id=\"configureTable\" class=\"easyui-datagrid\" data-options=\"url:'${path}/bim/categoryApp/datagrid/"+categoryCode+"',width:'1200px',height : 500,singleSelect:false,onClickRow:function(rowIndex, rowData){$('#configureTable').datagrid('unselectRow',rowIndex);}\">"+ 
							"<thead frozen=\"true\">" +
		        				"<tr>" +  "<th  width=\"120\" data-options=\"field:'BANK'\">银行/通道</th>" + 
		        				"</tr>" +
		        			"</thead>" +
							"<thead>" +
						        "<tr>" + rows + 
						            "<th width=\"80\" data-options=\"field:'CONFIGURE'\">操作</th>" + 
						        "</tr>" +
						    "</thead>" +
						  "</table></form>",
				modal : true,
				method : "POST",
				onClose : function() {
					$(this).dialog("destroy");
				},
				buttons : [{
					text : "统一配置",
					handler : function(e) {
						$('#configureForm').form('submit', {   
						    url:'${path}/bim/categoryApp/configure',   
						    onSubmit: function(param){   
						        param.categoryCode = categoryCode;   
						    },
						    success:function(data){
						    	var result = JSON.parse(data);
						        if(data!=null && result.success==true){
						        	$.messager.alert('提示','配置成功');
									$('#dialog_holder').dialog('close');
						        }else{
						        	$.messager.alert('提示','配置失败,请联系管理员');
						        }
						    } 
						});  
						$(this).dialog("close");
					}
				},{
					text : "关闭",
					handler : function(e) {
						$(this).dialog("close");
					}
				}]
			});
		};
	</script>
</body>
</html>
