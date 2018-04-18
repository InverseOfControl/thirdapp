<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content-body">
		<div class="search-panel-content">
			<form id="addTppTradeTWaitingMergeFrom" name="addTppTradeTWaitingMergeFrom" method="post" >
				<div class="search-panel-bd">
				 <table class="search-table">
				 
					 <tr>
						<th class="wd-20"><label>交易流水号</label></th>
						<td>
							<input type="text" id="TRADE_FLOW" name="TRADE_FLOW" value="${payInfo.TRADE_FLOW}" class="easyui-validatebox" readonly="true" style="width:200px;"/>
						</td>
					</tr>
					<tr>
						<th class="wd-20"><label>业务类型</label></th>
						<td>
							<input type="text" id="BIZ_TYPE" name="BIZ_TYPE" value="${payInfo.BIZ_TYPE}" class="easyui-validatebox" readonly="true" style="width:200px;"/>
						</td>
					</tr>
					<%-- <tr>
						<th class="wd-20"><label>业务系统</label></th>
						<td>
							<input type="text" id="BIZ_SYS_NAME" name="BIZ_SYS_NAME" value="${payInfo.BIZ_SYS_NAME}" class="easyui-validatebox" readonly="true" style="width:200px;"/>
						</td>
					</tr>
					<tr>
						<th class="wd-20"><label>第三方支付平台</label></th>
						<td>
							<input type="text" id="PAY_SYS_NAME" name="PAY_SYS_NAME" value="${payInfo.PAY_SYS_NAME}" class="easyui-validatebox" readonly="true" style="width:200px;"/>
						</td>
					</tr>
					<tr>
						<th class="wd-20"><label>信息类别</label></th>
						<td>
							<input type="text" id="INFO_CATEGORY_NAME" name="INFO_CATEGORY_NAME" value="${payInfo.INFO_CATEGORY_NAME}" class="easyui-validatebox" readonly="true" style="width:200px;"/>
						</td>
					</tr> --%>
					<tr>
						<th class="wd-20"><label>处理状态</label></th>
						<td>
							<input type="text" id="STATUS" name="STATUS" value="待处理" class="easyui-validatebox" readonly="true" style="width:200px;"/>
						</td>
					</tr>
					<tr>
						<th class="wd-20"><label>合单模块名称</label></th>
						<td>
							<input type="hidden" id="notify_merge_trade_flow" name="notify_merge_trade_flow"/>
							<!-- <input type="text" id="query_module_name" name="query_module_name" class="easyui-validatebox" data-options="required:true,missingMessage:'不能为空',novalidate: true,validType:'maxLength[50]'" style="width:200px;"/> -->
							<input id="merge_module_name" class="easyui-combobox" name="merge_module_name" data-options="
								editable:false,valueField: 'name',
								textField: 'name',
								//url是下拉框请求显示的数据
								url:'${path }/enumset/dictionary2/8',
								value:'请选择'" style="width:200px;"/> 
							<span style="color:red">*</span>
						</td>
					</tr>
						 <!-- <tr>
							<th class="wd-20"><label>合单模块名称</label></th>
							<td>
								<input type="text" id="merge_module_name" name="merge_module_name" class="easyui-validatebox" data-options="required:true,missingMessage:'不能为空',novalidate: true,validType:'maxLength[50]'" style="width:200px;"/>
								<input type="hidden" id="notify_merge_trade_flow" name="notify_merge_trade_flow" />
							</td>
						  </tr> -->
				  </table>
				</div>
			</form>
		</div>
</div>