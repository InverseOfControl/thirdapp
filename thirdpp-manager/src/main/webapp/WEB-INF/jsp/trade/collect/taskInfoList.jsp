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
	<body>
		<div id="div_tppTradeTCollectInfo_list">
			<div class="content-body">
					<!--搜索栏开始-->
					<div class="search-panel toggle-panel">
						<!-- <div class="panel-header" data-role="toggle-handle">
							<h2 class="panel-title">查询条件</h2>
						</div> -->
						<div class="search-panel-content">
							<form id="searchForm" name="searchForm" method="post" >
								<%-- <div class="search-panel-bd">
									<table class="search-table">
										 <tr>
											<th class="wd-20"><label>交易流水号</label></th>
											<td>
												<input type="text" id="search_trade_flow" name="search_trade_flow"  value="${search_trade_flow}" style="width:200px;" data-options="required:true" />
												
											</td>
											
										  </tr>
									</table>
								</div> --%>
								<input type="hidden" id="taskId" name="taskId" value="${taskId }"/>
							</form>
						</div>
					</div>
					<!--搜索栏结束-->
					  	
					  	<!--搜索结果开始-->
						<div class="result-content">
							<table  id="dg_tppTradeTCollectInfo" class="easyui-datagrid" style="height:300px"
								data-options="rownumbers:true,pageSize:20,singleSelect:true,collapsible:true,sortName:'',sortOrder:'desc',pagination:'true',url:'${path }/trade/collect/infoListData/${taskId }',method:'post',onClickRow: function (rowIndex, rowData) {$(this).datagrid('unselectRow', rowIndex);}">
								<thead>
									<tr>
									 	<!-- <th data-options="field:'MERGEORDER',width:50,align:'center'">合单</th> -->
									 	<th data-options="field:'DETAIL',width:70,align:'center'">详细信息</th>
										<!-- <th data-options="field:'EDIT',width:50,align:'center'">编辑</th> -->
										<!-- <th data-options="field:'ID',width:120,sortable:'true',align:'left'">ID</th> -->
										<th data-options="field:'TASK_ID',width:120,sortable:'true',align:'left'">任务ID</th>
										<th data-options="field:'REQ_ID',width:120,sortable:'true',align:'left'">请求ID</th>
										<!-- <th data-options="field:'PAY_SYS_NO',width:120,sortable:'true',align:'left'">第三方支付平台编码</th> -->
										<th data-options="field:'PAY_SYS_NAME',width:120,sortable:'true',align:'left'">第三方支付平台</th>
										<th data-options="field:'BIZ_SYS_ACCOUNT_NO',width:120,sortable:'true',align:'left'">业务系统客户编号</th>
										<th data-options="field:'ZENGDAI_ACCOUNT_NO',width:120,sortable:'true',align:'left'">证大客户编号</th>
										<!-- <th data-options="field:'BIZ_SYS_NO',width:120,sortable:'true',align:'left'">业务系统号</th> -->
										<th data-options="field:'BIZ_SYS_NAME',width:120,sortable:'true',align:'left'">业务系统名称</th>
										<th data-options="field:'RECEIVER_ACCOUNT_NO',width:120,sortable:'true',align:'left'">收款方账户编号</th>
										<th data-options="field:'REVEIVER_ACCOUNT_NAME',width:120,sortable:'true',align:'left'">收款方姓名</th>
										<th data-options="field:'PAYER_NAME',width:120,sortable:'true',align:'left'">付款方姓名</th>
										<th data-options="field:'PAYER_MOBILE',width:120,sortable:'true',align:'left'">付款方手机号</th>
										<th data-options="field:'PAYER_BANK_CARD_NO',width:120,sortable:'true',align:'left'">付款方银行卡号</th>
										<th data-options="field:'PAYER_BANK_CARD_TYPE',width:120,sortable:'true',align:'left'">付款方银行卡类型 </th>
										<th data-options="field:'PAYER_ID_TYPE',width:120,sortable:'true',align:'left'">付款方证件类型</th>
										<th data-options="field:'PAYER_ID',width:120,sortable:'true',align:'left'">付款方证件号</th>
										<!-- <th data-options="field:'PAYER_BANK_CODE',width:120,sortable:'true',align:'left'">付款方银行编码</th> -->
										<th data-options="field:'PAYER_BANK_NAME',width:120,sortable:'true',align:'left'">付款方银行编码</th>
										<!-- <th data-options="field:'PAYER_SUB_BANK_CODE',width:120,sortable:'true',align:'left'">付款方银行支行行号</th> -->
										<th data-options="field:'CURRENCY',width:120,sortable:'true',align:'left'">币种</th>
										<th data-options="field:'AMOUNT',width:120,sortable:'true',align:'left'">金额</th>
										<th data-options="field:'FEE',width:120,sortable:'true',align:'left'">手续费</th>
										<th data-options="field:'BIZ_REMARK',width:120,sortable:'true',align:'left'">业务备注</th>
										<th data-options="field:'BIZ_FLOW',width:120,sortable:'true',align:'left'">业务流水号</th>
										<th data-options="field:'PRIORITY',width:120,sortable:'true',align:'left'">优先级</th>
										<th data-options="field:'STATUS',width:120,sortable:'true',align:'left'">交易状态</th>
										<th data-options="field:'REMARK',width:120,sortable:'true',align:'left'">备注</th>
										<th data-options="field:'CREATER',width:120,sortable:'true',align:'left'">创建人</th>
										<th data-options="field:'CREATE_TIME',width:120,sortable:'true',align:'left'">创建时间</th>
										<th data-options="field:'UPDATE_TIME',width:120,sortable:'true',align:'left'">更新时间</th>
										<th data-options="field:'SPARE1',width:120,sortable:'true',align:'left'">备用1</th>
										<th data-options="field:'SPARE2',width:120,sortable:'true',align:'left'">备用2</th>
										<th data-options="field:'TRADE_FLOW',width:120,sortable:'true',align:'left'">交易流水号</th>
										<th data-options="field:'PAY_TRANS_FLOW',width:120,sortable:'true',align:'left'">第三方支付平台流水号EQ</th>
										<th data-options="field:'FAIL_REASON',width:120,sortable:'true',align:'left'">失败原因</th>
										<th data-options="field:'PAYER_ACCOUNT_NO',width:120,sortable:'true',align:'left'">付款方账户编号</th>
										<th data-options="field:'BIZ_TYPE',width:120,sortable:'true',align:'left'">业务类型</th>
										<th data-options="field:'IS_NEED_PUSH',width:120,sortable:'true',align:'left'">是否需要推送 </th>
										<!-- <th data-options="field:'INFO_CATEGORY_CODE',width:120,sortable:'true',align:'left'">信息类别编码</th> -->
										<th data-options="field:'INFO_CATEGORY_NAME',width:120,sortable:'true',align:'left'">信息类别</th>
										<th data-options="field:'TRANS_REP_CODE',width:120,sortable:'true',align:'left'">渠道返回状态</th>
										<th data-options="field:'IS_NEED_SPILT',width:120,sortable:'true',align:'left'">报盘是否需要拆单</th>
										<th data-options="field:'THIRD_RETURN_TIME',width:120,sortable:'true',align:'left'">第三方回盘时间</th>
										<th data-options="field:'NOTIFY_QUERY_STATUS',width:120,sortable:'true',align:'left'">通知查询状态</th>
										<th data-options="field:'NOTIFY_MERGE_STATUS',width:120,sortable:'true',align:'left'">通知合单状态</th>
										<th data-options="field:'SETTLE_DATE',width:120,sortable:'true',align:'left'">清算日期</th>
									</tr>
								</thead>
							</table>
						</div>
						<!--搜索栏结果end-->
						<form id="mergeOrderForm" name="mergeOrderForm" method="post">
							<input type="hidden" id="tradeFlow" name="tradeFlow"/>
							<input type="hidden" id="bizType" name="bizType"/>
						</form>
					  </div>
				</div>
		<script type="text/javascript">
		var tppTradeTCollectInfo_list ={};
		jQuery(function($){
			/* //定义构造查询
			tppTradeTCollectInfo_list.buildQueryParams=function(){
			    $('#div#div_tppTradeTCollectInfo_list #dg_tppTradeTCollectInfo').datagrid("options").queryParams={				
					 'search_trade_flow':$("input[name='search_trade_flow']").val(),
				}
			}

			//重新按照条件刷新查询内容
			$('#search_btn1').click(function(){
				alert(111111);
				tppTradeTCollectInfo_list.buildQueryParams();
				$('div#div_tppTradeTCollectInfo_list #dg_tppTradeTCollectInfo').datagrid('load');
			});
			
		    //重置查询条件并刷新内容
			$('#clear_btn1').click(function(){
				//清空查询条件
					$("input[name='search_trade_flow']").val("");
				tppTradeTCollectInfo_list.buildQueryParams();
				$('div#div_tppTradeTCollectInfo_list #dg_tppTradeTCollectInfo').datagrid('load');
			}); */
			
		});
		
		function mergeOrder(tradeFlow,bizType) {
			$('#tradeFlow').val(tradeFlow);
			$('#bizType').val(bizType);
			$('#mergeOrderForm').form({  
		        url:'${path }/trade/collect/mergeOrder',  
		        onSubmit:function(){  
		        },  
		        success:function(data){  
		        	alert("合单通知成功");
		        }  
		    }); 
    		$('#mergeOrderForm').submit();
		}
		function viewInfoDetail(infoId){
			$('<div id="dialog_holder"></div>').dialog({
			    title: '详细信息',
			    width: 800,
			    height: 400,
			    href:  '${path}/trade/collect/detailInfo',
			    modal: true,
			    method: "POST",
			    params:{infoId:infoId},
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
