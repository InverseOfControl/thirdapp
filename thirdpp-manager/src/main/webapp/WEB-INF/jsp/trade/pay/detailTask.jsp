<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/xml"  prefix = "x" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/sql"  prefix = "sql" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/functions"  prefix = "fn" %> 
<div class="content-body">
		<div class="search-panel-content">
			<form id="editTppTradeTPayTaskFrom" name="editTppTradeTPayTaskFrom" method="post" action="editTppTradeTPayTaskAction.action">
				<div class="search-panel-bd">
					<table class="search-table">
							 <tr>
								<th class="wd-20"><label>交易请求ID</label></th>
								<td>
									<input type="text" id="req_id" name="req_id"  value="${req_id}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>支付渠道</label></th>
								<td>
									<input type="text" id="pay_sys_name" name="pay_sys_name"  value="${pay_sys_name}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>业务系统客户编号</label></th>
								<td>
									<input type="text" id="biz_sys_account_no" name="biz_sys_account_no"  value="${biz_sys_account_no}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>证大客户编号</label></th>
								<td>
									<input type="text" id="zengdai_account_no" name="zengdai_account_no"  value="${zengdai_account_no}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>业务系统</label></th>
								<td>
									<input type="text" id="biz_sys_no" name="biz_sys_no"  value="${app_name}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>付款方账户编号</label></th>
								<td>
									<input type="text" id="payer_account_no" name="payer_account_no"  value="${payer_account_no}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>付款方姓名</label></th>
								<td>
									<input type="text" id="payer_account_name" name="payer_account_name"  value="${payer_account_name}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>收款方姓名</label></th>
								<td>
									<input type="text" id="receiver_name" name="receiver_name"  value="${receiver_name}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>收款方银行卡号</label></th>
								<td>
									<input type="text" id="receiver_bank_card_no" name="receiver_bank_card_no"  value="${receiver_bank_card_no}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>收款方银行卡类型 </label></th>
								<td>
									<input type="text" id="receiver_bank_card_type" name="receiver_bank_card_type"  value="${receiver_bank_card_type}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>收款方证件类型</label></th>
								<td>
									<input type="text" id="receiver_id_type" name="receiver_id_type"  value="${receiver_id_type}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>收款方证件号</label></th>
								<td>
									<input type="text" id="receiver_id" name="receiver_id"  value="${receiver_id}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>收款方银行</label></th>
								<td>
									<input type="text" id="receiver_bank_name" name="receiver_bank_name"  value="${receiver_bank_name}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>收款方银行支行行号</label></th>
								<td>
									<input type="text" id="receiver_sub_bank_code" name="receiver_sub_bank_code"  value="${receiver_sub_bank_code}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>币种</label></th>
								<td>
									<input type="text" id="currency" name="currency"  value="${currency}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>金额</label></th>
								<td>
									<input type="text" id="amount" name="amount"  value="${amount}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>手续费</label></th>
								<td>
									<input type="text" id="fee" name="fee"  value="${fee}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>业务备注</label></th>
								<td>
									<input type="text" id="biz_remark" name="biz_remark"  value="${biz_remark}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>业务流水号</label></th>
								<td>
									<input type="text" id="biz_flow" name="biz_flow"  value="${biz_flow}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>优先级</label></th>
								<td>
									<input type="text" id="priority" name="priority"  value="${priority}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>发送状态</label></th>
								<td>
									<input type="text" id="status" name="status"  value="${status}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>备注</label></th>
								<td>
									<input type="text" id="remark" name="remark"  value="${remark}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>创建人</label></th>
								<td>
									<input type="text" id="creater" name="creater"  value="${creater}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>创建时间</label></th>
								<td>
									<input type="text" id="create_time" name="create_time"  value="<fmt:formatDate value='${create_time}' type='date'  pattern='yyyy-MM-dd HH:mm:ss'/>" style="width:200px;" readonly="true" />
								</td>
							 </tr>
							<tr>
								<th class="wd-20"><label>更新时间</label></th>
								<td>
									<input type="text" id="update_time" name="update_time"  value="<fmt:formatDate value='${update_time}' type='date'  pattern='yyyy-MM-dd HH:mm:ss'/>" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>发送程序名称</label></th>
								<td>
									<input type="text" id="send_thread_name" name="send_thread_name"  value="${send_thread_name}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>是否拆单</label></th>
								<td>
									<input type="text" id="is_separate" name="is_separate"  value="${is_separate}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>拆单数</label></th>
								<td>
									<input type="text" id="separate_count" name="separate_count"  value="${separate_count}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>发送次数</label></th>
								<td>
									<input type="text" id="send_num" name="send_num"  value="${send_num}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>收款方账户编号</label></th>
								<td>
									<input type="text" id="receiver_account_no" name="receiver_account_no"  value="${receiver_account_no}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>业务类型</label></th>
								<td>
									<input type="text" id="biz_type" name="biz_type"  value="${biz_type}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>是否需要推送</label></th>
								<td>
									<input type="text" id="is_need_push" name="is_need_push"  value="${is_need_push}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>交易结果状态</label></th>
								<td>
									<input type="text" id="trade_status" name="trade_status"  value="${trade_status}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>交易结果描述</label></th>
								<td>
									<input type="text" id="trade_result_info" name="trade_result_info"  value="${trade_result_info}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>交易成功金额</label></th>
								<td>
									<input type="text" id="trade_success_amount" name="trade_success_amount"  value="${trade_success_amount}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>信息类别</label></th>
								<td>
									<input type="text" id="info_category_name" name="info_category_name"  value="${info_category_name}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							  <tr>
							  	<th class="wd-20"><label>收款人用户类型</label></th>
								<td>
									<input type="text" id="receiver_type" name="receiver_type"  value="${receiver_type}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
					</table>
				</div>
			</form>
		</div>
</div>