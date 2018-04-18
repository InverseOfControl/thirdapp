<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/xml"  prefix = "x" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/sql"  prefix = "sql" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/functions"  prefix = "fn" %> 
<div class="content-body">
		<div class="search-panel-content">
			<form id="detailTppTradeTPayWhiteListForm" name="detailTppTradeTPayWhiteListForm" method="post">
				<div class="search-panel-bd">
					<table class="search-table">
							 <tr>
								<th class="wd-20"><label>业务系统</label></th>
								<td>
									<input type="text" id="biz_sys_name" name="biz_sys_name"  value="${biz_sys_name}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>信息类别</label></th>
								<td>
									<input type="text" id="info_category_name" name="info_category_name"  value="${info_category_name}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							 <tr>
								<th class="wd-20"><label>账号</label></th>
								<td>
									<input type="text" id="account_no" name="account_no"  value="${account_no}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>银行卡号 </label></th>
								<td>
									<input type="text" id="bank_card_no" name="bank_card_no"  value="${bank_card_no}" style="width:200px;" readonly="true" />
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
								<th class="wd-20"><label>备注</label></th>
								<td>
									<input type="text" id="note" name="note"  value="${note}" style="width:200px;" readonly="true" />
								</td>
								<th class="wd-20"><label>更新时间</label></th>
								<td>
									<input type="text" id="update_time" name="update_time"  value="<fmt:formatDate value='${update_time}' type='date'  pattern='yyyy-MM-dd HH:mm:ss'/>" style="width:200px;" readonly="true" />
								</td>
							  </tr>
							   <tr>
								<th class="wd-20"><label>状态</label></th>
								<td>
									<input type="text" id="status" name="status"  value="${status}" style="width:200px;" readonly="true" />
								</td>
							  </tr>
					</table>
				</div>
			</form>
		</div>
</div>