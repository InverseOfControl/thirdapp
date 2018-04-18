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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
<body class="easyui-layout">
			<!--搜索栏开始-->
			<div class="easyui-panel" title="查询条件" style="padding:5px;height:135px;margin: 0px;" data-options="region:'north'">
				<form id="searchForm" name="searchForm" method="post" >
					<table cellpadding="5">
						<tr>
							<%-- <td style="text-align:right">通道名称：</td>
							<td>
								<input type="text" id="search_channel_name" name="search_channel_name" class="easyui-textbox" value="${search_channel_name}" style="width:100px;"/>
							</td> --%>
							<td style="text-align:right">通道状态：</td>
							<td>
								<input id="search_status" class="easyui-combobox" name="search_status" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/getAccountChannelStatusList' " style="width:100px;"/> 
							</td>
							<%-- <td style="text-align:right">业务类型：</td>
							<td>
								<input id="search_biz_type" class="easyui-combobox" name="search_biz_type" data-options="
									editable:false,valueField: 'value',
									textField: 'name',
									//url是下拉框请求显示的数据
									url:'${path }/enumset/bizTypeForChannelAccount' " style="width:100px;"/>  
							</td> --%>
							<td style="text-align:right">业务类型：</td>
								<td>
									<input id="search_biz_type" name="search_biz_type"    style="width: 150px" 
											class="easyui-combotree" data-options="
												editable:false, 
											   multiple: 'true',
											  url:'${path}/enumset/accountBizTypeList/combobox',
										 	  onLoadSuccess: function () { 									 	  					 
										 	  						$('#search_biz_type').combotree('setText', '请选择');	
										 	  					}
											     "/>
									<input type="hidden" id="search_biz_types" name="search_biz_types"/>
								</td>
							<td style="text-align:right">第三方支付平台：</td>
							<td>
								<input id="search_third_type_no" class="easyui-combobox" name="search_third_type_no" data-options="
									editable:false,valueField: 'value',
									textField: 'name',
									//url是下拉框请求显示的数据
									url:'${path }/enumset/dictionary/3' " style="width:150px;"/>
							</td>
						</tr>
						<tr>
							<%-- <td style="text-align:right">渠道编码：</td>
							<td>
								<input type="text" id="search_third_type_no" name="search_third_type_no" class="easyui-textbox" value="${search_third_type_no}" style="width:100px;"/>
							</td> --%>
							<td style="text-align:right">商户号：</td>
							<td>
								<input type="text" id="search_merchant_no" name="search_merchant_no" class="easyui-textbox" value="${search_merchant_no}" style="width:100px;"/>
							</td>
							<td style="text-align:right">处理进程：</td>
							<td>
								<input id="search_app_name" class="easyui-combobox" name="search_app_name" data-options="
									editable:false,valueField: 'value',
									textField: 'name',
									//url是下拉框请求显示的数据
									url:'${path }/enumset/dictionary/15' " style="width:150px;"/>
							</td>
						</tr>
						<tr>
							<td colspan="6"><span style="color:red">新增、修改、删除配置信息，需重启服务器后配置信息生效。</span></td>
						</tr>
					</table>
				</form>
				<form id="deleteForm" name="deleteForm" method="post">
					<input type="hidden" id="channelConfigIds" name="channelConfigIds"/>
				</form>
			</div>
			<!--搜索栏结束-->
			
			<!--搜索结果开始-->
			<div class="easyui-panel" style="padding:0px;margin: 0px;" data-options="region:'center'">
				<table  id="dg_tppAccountChannelConfig" class="easyui-datagrid" 
					data-options="rownumbers:true,pageSize:20,singleSelect:false,selectOnCheck: 'true',checkOnSelect:'true',collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/account/channelConfig/listData',method:'post',toolbar:toolbar,fit:'true',onClickRow: function (rowIndex, rowData) {$(this).datagrid('unselectRow', rowIndex);}">
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:'true'"></th>
						 	<!-- <th data-options="field:'DETAIL',width:50,align:'center'">detail</th> -->
							<!-- <th data-options="field:'EDIT',width:50,align:'center'">编辑</th> -->
							
							<th data-options="field:'ID',width:120,sortable:'true',align:'left',hidden:'true'">主键</th>
							<th data-options="field:'VIEW_DETAIL',width:60,sortable:'true',align:'center'">详细信息</th>
							<th data-options="field:'CHANNEL_NAME',width:120,sortable:'true',align:'left'">第三方支付平台</th>
							<!-- <th data-options="field:'THIRD_TYPE_NO',width:60,sortable:'true',align:'left'">通道编码</th> -->
							<th data-options="field:'MERCHANT_NO',width:150,sortable:'true',align:'left'">商户号</th>
							<th data-options="field:'ACCOUNT_TIME',width:120,sortable:'true',align:'left'">对账时间</th>
							<th data-options="field:'BIZ_TYPE_STR',width:250,sortable:'true',align:'left'">业务类型</th>
							<th data-options="field:'BIZ_TYPE',width:250,sortable:'true',align:'left'">业务类型编码</th>
							<th data-options="field:'APP_NAME',width:150,sortable:'true',align:'left'">处理进程</th>
							<th data-options="field:'FETCH_METHOD',width:100,sortable:'true',align:'left'">文件下载方式 </th>
							<th data-options="field:'FETCH_TYPE',width:100,sortable:'true',align:'left'">获取文件方式</th>
							<th data-options="field:'DOWNLOAD_URL',width:120,sortable:'true',align:'left'">HTTP请求下载地址</th>
							<th data-options="field:'SERVER',width:120,sortable:'true',align:'left'">FTP主机</th>
							<th data-options="field:'PORT',width:120,sortable:'true',align:'left'">FTP端口号</th>
							<th data-options="field:'LOGIN_USERNAME',width:120,sortable:'true',align:'left'">FTP请求登录用户名 </th>
							<th data-options="field:'LOGIN_PWD',width:120,sortable:'true',align:'left'">FTP请求登录密码</th>
							<th data-options="field:'FILE_PATH',width:120,sortable:'true',align:'left'">FTP请求文件存放目录</th>
							<th data-options="field:'FILE_PATH_SUB',width:120,sortable:'true',align:'left'">FTP请求文件存放目录子目录</th>
							<th data-options="field:'FILE_NAME_FORMAT',width:120,sortable:'true',align:'left'">文件名格式</th>
							<th data-options="field:'FILE_SUFFIX',width:120,sortable:'true',align:'left'">文件后缀名</th>
							<th data-options="field:'FILE_ENCODING',width:120,sortable:'true',align:'left'">文件编码</th>
							<th data-options="field:'FILE_START_INDEX',width:120,sortable:'true',align:'left'">从第几行开始读取记录</th>
							<th data-options="field:'ATTR_SPLIT',width:120,sortable:'true',align:'left'">文件内容分隔符</th>
							<th data-options="field:'ATTRS_DEFINITION',width:200,sortable:'true',align:'left'">文件属性定义</th>
							<th data-options="field:'FILE_HEADER_ATTRS_INDEX',width:200,sortable:'true',align:'left'">汇总信息所在行</th>
							<th data-options="field:'CURRENCY',width:120,sortable:'true',align:'left'">币种</th>
							<th data-options="field:'CURRENCY_UNIT',width:120,sortable:'true',align:'left'">货币单位</th>
							<th data-options="field:'MAX_DOWNLOAD_FAILED_TIMES',width:120,sortable:'true',align:'left'">下载操作最多失败次数</th>
							<th data-options="field:'MAX_INSERT_FAILED_TIMES',width:120,sortable:'true',align:'left'">入库操作最多失败次数</th>
							<th data-options="field:'MAX_BACKUP_FAILED_TIMES',width:120,sortable:'true',align:'left'">备份操作最多失败次数</th>
							<th data-options="field:'MAX_ACCOUNT_FAILED_TIMES',width:120,sortable:'true',align:'left'">最大对账失败次数</th>
							<th data-options="field:'STATUS',width:120,sortable:'true',align:'left'">通道状态</th>
							<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'left'">创建时间</th>
						</tr>
					</thead>
				</table>
			</div>
			<!--搜索栏结果end-->

<script>
var tppAccountChannelConfig_list ={};
var toolbar = [{
			id : 'add_btn',
			text : '新增',
			iconCls : 'icon-add'
		},{
			id : 'edit_btn',
			text : '修改',
			iconCls : 'icon-edit'
		},{
			id : 'delete_btn',
			text : '删除',
			iconCls : 'icon-cancel'
		},'-',{
			id : 'search_btn',
			text : '查询',
			iconCls : 'icon-search'
		},{
			id : 'clear_btn',
			text : '清除',
			iconCls : 'icon-remove'
		},{
			id : 'handleAccount_btn',
			text : '手工对账',
			iconCls : 'icon-assigned'
		}];
jQuery(function($){
	//定义构造查询
	tppAccountChannelConfig_list.buildQueryParams=function(){
		$("#search_biz_types").val($("#search_biz_type").combotree("getValues"));
		var searchAppName = '';
		if ($('#search_app_name').combobox("getText") == '请选择'){
			searchAppName = '';
		} else {
			searchAppName = $('#search_app_name').combobox("getText");
		}
		$('#dg_tppAccountChannelConfig').datagrid("options").queryParams={
				 //'search_channel_name':$("input[name='search_channel_name']").val(),
				 'search_third_type_no':$('#search_third_type_no').combobox("getValue"),
				 'search_merchant_no':$("input[name='search_merchant_no']").val(),
				 'search_status':$('#search_status').combobox("getValue"),
				 'search_biz_type':$("input[name='search_biz_types']").val(),
				 'search_app_name':searchAppName,
				 
		}
	}

	//重新按照条件刷新查询内容
	$('#search_btn').click(function(){
		tppAccountChannelConfig_list.buildQueryParams();
		$('#dg_tppAccountChannelConfig').datagrid('load');
	});
	
    //重置查询条件并刷新内容
	$('#clear_btn').click(function(){
		//清空查询条件
			//$("input[name='search_channel_name']").val("");
			//$("input[name='search_third_type_no']").val("");
			$("input[name='search_merchant_no']").val("");
			$('#search_third_type_no').combobox('setValue','');
			$('#search_status').combobox('setValue','');
			$('#search_biz_type').combotree('setValues', []);	
			$('#search_biz_type').combotree('setText', '请选择');	
			$('#search_app_name').combobox('setValue','');
		tppAccountChannelConfig_list.buildQueryParams();
		$('#dg_tppAccountChannelConfig').datagrid('load');
	});
	
	
	//新增
	$('#add_btn').click(function(){
		var add_form_id ='#addTppAccountChannelConfigFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: '新增通道配置',
		    width: 800,
		    height: 400,
		    href: '${path}/account/channelConfig/addChannelConfigPage',
		    modal: true,
		    method: "POST",
			onClose: function(){
				$(this).dialog("destroy");
			},
		    buttons: [{
		    	text: "提  交",
		    	handler: function(e){
		    		//提交前全选
		    		var sltSrc=document.getElementById('attrsDefinition');
		    	    for (var i=0;i<sltSrc.options.length;i++){
		    	        var temOption=sltSrc.options[i];
		    	        temOption.selected=true;      
		    	    }
		    	  	/* //提交前全选
		    		var sltSrc2=document.getElementById('headerAttrsDefinition');
		    	    for (var i=0;i<sltSrc2.options.length;i++){
		    	        var temOption=sltSrc2.options[i];
		    	        temOption.selected=true;      
		    	    } */
		    		$('#addTppAccountChannelConfigFrom').form({   
						 url:'${path}/account/channelConfig/addChannelConfig',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							    	//提交后还原
									if (split == '_') {
							    		$("input[name='attrSplit']").val('_');
							    	} else if (split == '%') {
							    		$("input[name='attrSplit']").val('%');
							    	}
						    	 	var obj = eval('(' + data + ')');
							       	//do some
							       	if(obj.flag==false){
							    	   	$.messager.alert('提示',obj.msg);   
							    	   	$.messager.progress('close');   
							    	   	return;
							       	}
							        $.messager.progress('close');
									tppAccountChannelConfig_list.buildQueryParams();
									$('#dg_tppAccountChannelConfig').datagrid('load');
									$('#dialog_holder').dialog('close');
							     }
						});// 
						$(".easyui-validatebox").each(function(){
				        	$(this).validatebox({    
				        		novalidate: false   
				        	}); 
				        });
					    //validate and sbumit
					    if($(add_form_id).form("validate")==true){
					    	//防过滤器过滤
					    	var split = $("input[name='attrSplit']").val();
					    	if (split == '_') {
					    		$("input[name='attrSplit']").val('1_1');
					    	} else if (split == '%') {
					    		$("input[name='attrSplit']").val('1%1');
					    	}
						  	$.messager.progress();
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
	
	//手工对账
	$('#handleAccount_btn').click(function(){
		var row = $('#dg_tppAccountChannelConfig').datagrid('getSelected');
		if (row == null) {
			$.messager.alert('提示','请先选择要手工对账的配置！');
			return;
		}
		
		var rows = $("#dg_tppAccountChannelConfig").datagrid('getSelections');
		if(rows.length<1 || rows.length>1){
			$.messager.alert('提示','请选中一行');
			return;
		}
		var configId = row.ID;
		
	    var handle_form_id ='#handleTppAccountChannelConfigFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: '通道手工对账',
		    width: 500,
		    height: 400,
		    href: '${path}/account/channelConfig/handleAccountChannelConfigPage',
		    modal: true,
		    method: "POST",
		    params:{configId:configId},
			onClose: function(){
				$(this).dialog("destroy");
			},
		    buttons: [{
		    	text: "开始对账",
		    	handler: function(e){
		   				$('#handleTppAccountChannelConfigFrom').form({   
						         url:'${path}/account/channelConfig/handleAccount',    
							     onSubmit: function(){
							       $.messager.progress(); 
							     },    
							     success:function(data){
							        var obj = eval('(' + data + ')');
							    	if (obj.flag == false){
			 					    	 $.messager.alert('提示',obj.msg);
			 				       	} else {
			 				       		 $.messager.alert('提示','通道手工对账成功');
			 				       	}
							        $.messager.progress('close');

							        $('#dialog_holder').dialog("close");
									tppAccountChannelConfig_list.buildQueryParams();
									$('#dg_tppAccountChannelConfig').datagrid('load');
							     },
							     error:function(data){
							    	  $.messager.progress('close');
							    	  $('#dialog_holder').dialog("close");
									  result = false;
									  $.messager.alert('提示', '请求超时');
								  }
						});
						$(".easyui-validatebox").each(function(){
				        	$(this).validatebox({    
				        		novalidate: false   
				        	}); 
				        });
					    if($(handle_form_id).form("validate")==true){
						  	$.messager.progress();
							$(handle_form_id).submit();
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
	$('#edit_btn').click(function(){
		var row = $('#dg_tppAccountChannelConfig').datagrid('getSelected');
		if (row == null) {
			$.messager.alert('提示','请先选择要修改的配置！');
			return;
		}
		
		var rows = $("#dg_tppAccountChannelConfig").datagrid('getSelections');
		if(rows.length<1 || rows.length>1){
			$.messager.alert('提示','请选中一行');
			return;
		}
		var configId = row.ID;
		
	    var edit_form_id ='#editTppAccountChannelConfigFrom';
		$('<div id="dialog_holder"></div>').dialog({
		    title: '修改通道配置',
		    width: 800,
		    height: 400,
		    href: '${path}/account/channelConfig/editChannelConfigPage',
		    modal: true,
		    method: "POST",
		    params:{configId:configId},
			onClose: function(){
				$(this).dialog("destroy");
			},
		    buttons: [{
		    	text: "保  存",
		    	handler: function(e){
			    		//提交前全选
			    		var sltSrc=document.getElementById('attrsDefinition');
			    	    for (var i=0;i<sltSrc.options.length;i++){
			    	        var temOption=sltSrc.options[i];
			    	        temOption.selected=true;      
			    	    }
			    	  	/* //提交前全选
			    		var sltSrc2=document.getElementById('headerAttrsDefinition');
			    	    for (var i=0;i<sltSrc2.options.length;i++){
			    	        var temOption=sltSrc2.options[i];
			    	        temOption.selected=true;      
			    	    } */
		   				$('#editTppAccountChannelConfigFrom').form({   
						 url:'${path}/account/channelConfig/editChannelConfig',    
							     onSubmit: function(){
							       $.messager.progress(); 
							        // do some check       
							        // return false to prevent submit;    
							     },    
							     success:function(data){
							    	//提交后还原
									if (split == '_') {
							    		$("input[name='attrSplit']").val('_');
							    	} else if (split == '%') {
							    		$("input[name='attrSplit']").val('%');
							    	}
							    	var obj = eval('(' + data + ')');
							       	//do some
							       	if(obj.flag==false){
							    	   	$.messager.alert('提示',obj.msg);   
							    	   	$.messager.progress('close');   
							    	   	return;
							       	}
							        $.messager.progress('close');
									tppAccountChannelConfig_list.buildQueryParams();
									$('#dg_tppAccountChannelConfig').datagrid('load');
									$('#dialog_holder').dialog('close');
							     }
						});// 
						$(".easyui-validatebox").each(function(){
				        	$(this).validatebox({    
				        		novalidate: false   
				        	}); 
				        });
					    //validate and sbumit
					    if($(edit_form_id).form("validate")==true){
					    	//防过滤器过滤
					    	var split = $("input[name='attrSplit']").val();
					    	if (split == '_') {
					    		$("input[name='attrSplit']").val('1_1');
					    	} else if (split == '%') {
					    		$("input[name='attrSplit']").val('1%1');
					    	}
						  	$.messager.progress();
							$(edit_form_id).submit();
						};   
		    		
		    	}
		    }
		    ,{
		    	text: "取 消",
		    	handler: function(e){
		    		$(this).dialog("close");
		    	}
		    }]
		});
	});
	$('#delete_btn').click(function(){
		var row = $('#dg_tppAccountChannelConfig').datagrid('getSelected');
		if (row == null) {
			$.messager.alert('提示','请先选择要删除的配置！');
			return;
		}
		
		var checkedItems = $('#dg_tppAccountChannelConfig').datagrid('getChecked');
	     var ids = [];
	     $.each(checkedItems, function(index, item){
	         ids.push(item.ID);
	    });                
		$("#channelConfigIds").val(ids);
		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){    
		        $('#deleteForm').form({  
			        url:'${path }/account/channelConfig/deleteChannelConfig',  
			        onSubmit:function(){  
			        	$.messager.progress(); 
			        },  
			        success:function(data){  
			        	$.messager.progress('close');
			        	 var obj = eval('(' + data + ')');
					       //do some
				    	 if(obj.flag==false){
					    	   $.messager.alert('提示',obj.msg);   
					    	   return;
				       	 }
			        	$('#dg_tppAccountChannelConfig').datagrid('load');
			        }  
			    }); 
	    		$('#deleteForm').submit();
		    }    
		}); 
	});
	//详细
	tppAccountChannelConfig_list.detailFormSubmit = function(pkid){
		$('<div id="dialog_holder"></div>').dialog({
		    title: '详细TppAccountChannelConfig',
		    width: 800,
		    height: 500,
		    href: 'detailTppAccountChannelConfigPage.action',
		    modal: true,
		    method: "POST",
		    params:{pkid:pkid},
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
		    	text: "提  交",
		    	handler: function(e){
		    		var ThisForm = $(this).find("form");
		    	}
		    },{
		    	text: "关闭",
		    	handler: function(e){
		    		$(this).dialog("close");
		    	}
		    }]
		});
	}
	
	
});

function viewDetail(id){
	$('<div id="dialog_holder"></div>').dialog({
	    title: '详细信息',
	    width: 800,
	    height: 400,
	    href: '${path}/account/channelConfig/detailChannelConfigPage',
	    modal: true,
	    method: "POST",
	    params:{id:id},
		onClose: function(){
			$(this).dialog("destroy");
		},
	    buttons: [{
	    	text: "关闭",
	    	handler: function(e){
	    		$(this).dialog("close");
	    	}
	    }]
	});
}

</script>
</body>
</html>