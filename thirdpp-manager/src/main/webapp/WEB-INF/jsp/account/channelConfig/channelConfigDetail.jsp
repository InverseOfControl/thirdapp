<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/xml"  prefix = "x" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/sql"  prefix = "sql" %> 
<%@ taglib  uri = "http://java.sun.com/jsp/jstl/functions"  prefix = "fn" %> 
<div class="content-body">
		<div class="search-panel-content">
			<form id="editTppAccountChannelConfigFrom" name="editTppAccountChannelConfigFrom" method="post" action="editTppAccountChannelConfigAction.action">
				<div class="search-panel-bd">
					<table class="search-table">
						 <tr>
						 	<input type="hidden" id="id" name="id" value="${channelConfig.id }"/>
						 	<input  type="hidden" id="bizTypes" name="bizTypes" value="${bizTypes}"/>
							<th class="wd-20"><label>第三方支付平台</label></th>
							<td>
								<input type="text" id="channelName" name="channelName" value="${channelConfig.channelName }" readonly="true" style="width:150px;"/>
							</td>
							<th class="wd-20"><label>业务类型</label></th>
							<td>
								<input type="text" id="bizTypes" name="bizTypes" value="${bizTypes}" readonly="true" style="width:150px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>商户号</label></th>
							<td>
								<input type="text" id="merchantNo" name="merchantNo" value="${channelConfig.merchantNo }" readonly="true" style="width:150px;"/>
							</td>
							<th class="wd-20"><label>文件下载方式</label></th>
							<td>
								<input type="text" id="fetchMethod" name="fetchMethod" value="${channelConfig.fetchMethod }" readonly="true" style="width:150px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>获取文件方式</label></th>
							<td>
								<input type="text" id="fetchType" name="fetchType" value="${fetchType}" readonly="true" style="width:150px;"/>
							</td>
							<th class="wd-20"><label>HTTP请求下载地址</label></th>
							<td>
								<input type="text" id="downloadUrl" name="downloadUrl" value="${channelConfig.downloadUrl }" readonly="true" style="width:150px;"/>
							</td>
						  </tr>
						 
						 <tr>
							<th class="wd-20"><label>FTP请求登录用户名</label></th>
							<td>
								<input type="text" id="loginUsername" name="loginUsername" value="${channelConfig.loginUsername }" readonly="true" style="width:150px;"/>
							</td>
							<th class="wd-20"><label>FTP请求登录密码</label></th>
							<td>
								<input type="text" id="loginPwd" name="loginPwd" value="${channelConfig.loginPwd }" readonly="true" style="width:150px;"/>
							</td>
						  </tr>
						   
						 <tr>
							<th class="wd-20"><label>FTP请求文件存放目录</label></th>
							<td>
								<input type="text" id="filePath" name="filePath" value="${channelConfig.filePath }" readonly="true" style="width:150px;"/>
							</td>
							<th class="wd-20"><label>文件编码</label></th>
							<td>
								<input type="text" id="fileEncoding" name="fileEncoding" value="${channelConfig.fileEncoding }" readonly="true" style="width:150px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>文件名格式</label></th>
							<td>
								<input type="text" id="fileNameFormat" name="fileNameFormat" value="${channelConfig.fileNameFormat }" readonly="true" style="width:150px;"/>
							</td>
							<th class="wd-20"><label>文件后缀名</label></th>
							<td>
								<input type="text" id="fileSuffix" name="fileSuffix" value="${channelConfig.fileSuffix }" readonly="true" style="width:150px;"/>
							</td>
						  </tr>
						  <tr>
							<th class="wd-20"><label>从第几行开始读取记录</label></th>
							<td>
								<input type="text" id="fileStartIndex" name="fileStartIndex" value="${channelConfig.fileStartIndex }" readonly="true" style="width:150px;"/>
							</td>
							<th class="wd-20"><label>对账时间</label></th>
							<td>
								<input type="text" id="accountTime" name="accountTime" value="${channelConfig.accountTime }" readonly="true" style="width:150px;"/>
							</td>
						  </tr>
						  <tr>
							<th class="wd-20"><label>文件内容分隔符</label></th>
							<td>
								<input type="text" id="attrSplit" name="attrSplit" value="${channelConfig.attrSplit }" readonly="true" style="width:150px;"/>
							</td>
							<th class="wd-20"><label>通道状态</label></th>
							<td>
								<input type="text" id="status" name="status" value="${status}" readonly="true" style="width:150px;"/>
							</td>
						  </tr>
						  <tr>
						  <th class="wd-20"><label>币种</label></th>
							<td>
								<input type="text" id="currency" name="currency" value="${currency}" readonly="true" style="width:150px;"/>
							</td>
							<th class="wd-20"><label>货币单位</label></th>
							<td>
								<input type="text" id="currencyUnit" name="currencyUnit" value="${currencyUnit}" readonly="true" style="width:150px;"/>
							</td>
						  </tr>
						 <tr>
						 	<th class="wd-20"><label>FTP主机</label></th>
							<td>
								<input type="text" id="server" name="server" value="${channelConfig.server }" readonly="true" style="width:150px;"/>
							</td>
							<th class="wd-20"><label>FTP端口号</label></th>
							<td>
								<input type="text" id="port" name="port" value="${channelConfig.port }" readonly="true" style="width:150px;"/>
							</td>
						 </tr>
						 <tr>
						 	<th class="wd-20"><label>FTP请求文件存放目录子目录 </label></th>
							<td>
								<input type="text" id="filePathSub" name="filePathSub" value="${channelConfig.filePathSub }" readonly="true" style="width:150px;"/>
							</td>
						 	<th class="wd-20"><label>处理进程</label></th>
							<td>
								<input type="text" id="appName" name="appName" value="${channelConfig.appName }" readonly="true" style="width:150px;"/>
							</td>
						 </tr>
						 <tr>
							 <th class="wd-20"><label>下载操作最多失败次数</label></th>
							<td>
								<input type="text" id="maxDownloadFailedTimes" name="maxDownloadFailedTimes" value="${channelConfig.maxDownloadFailedTimes }" readonly="true" value="0" style="width:150px;"/>
							</td>
							<th class="wd-20"><label>入库操作最多失败次数</label></th>
							<td>
								<input type="text" id="maxInsertFailedTimes" name="maxInsertFailedTimes" value="${channelConfig.maxInsertFailedTimes }" readonly="true" value="0" style="width:150px;"/>
							</td>
						  </tr>
						 <tr>
						 	<th class="wd-20"><label>对账操作最多失败次数</label></th>
							<td>
								<input type="text" id="maxAccountFailedTimes" name="maxAccountFailedTimes" value="${channelConfig.maxAccountFailedTimes }" readonly="true" style="width:150px;"/>
							</td>
							<th class="wd-20"><label>备份操作最多失败次数</label></th>
							<td>
								<input type="text" id="maxBackupFailedTimes" name="maxBackupFailedTimes" value="${channelConfig.maxBackupFailedTimes }" readonly="true" style="width:150px;"/>
							</td>
						 </tr>
						 <tr>
						 	<th class="wd-20"><label>汇总信息所在行</label></th>
							<td>
								<input type="text" id="headerAttrsDefinition" name="headerAttrsDefinition" value="${channelConfig.fileHeaderAttrsIndex }" readonly="true" style="width:150px;"/>
							</td>
							<th class="wd-20"><label>文件属性定义</label></th>
							<td>
								<input type="text" id="attrsDefinition" name="attrsDefinition" value="${channelConfig.attrsDefinition }" readonly="true" style="width:150px;"/>
							</td>
						  </tr>
				  </table>
				</div>
			</form>
		</div>
</div>