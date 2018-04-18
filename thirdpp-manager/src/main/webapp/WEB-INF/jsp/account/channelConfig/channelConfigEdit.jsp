<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/xml"  prefix = "x" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql"  prefix = "sql" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions"  prefix = "fn" %> 
<%@ include file="/common/base.jsp"%>
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
								<%-- <input type="text" id="channelName" name="channelName" value="${channelConfig.channelName }" class="easyui-validatebox" data-options="required:true,missingMessage:'通道名称不能为空',novalidate: true,validType:'maxLength[100]'" style="width:150px;"/><span style="color:red">*</span> --%>
								<input id="thirdTypeNo" class="easyui-combobox" name="thirdTypeNo" data-options="
									editable:false,valueField: 'value',
									textField: 'name',
									//url是下拉框请求显示的数据
									url:'${path }/enumset/dictionary/3',
									value:'${ channelConfig.thirdTypeNo}' " style="width:150px;"/><span style="color:red">*</span>
							</td>
							<%-- <th class="wd-20"><label>渠道编码</label></th>
							<td>
								<input type="text" id="thirdTypeNo" name="thirdTypeNo" value="${channelConfig.thirdTypeNo }" class="easyui-validatebox" data-options="required:true,missingMessage:'渠道编码不能为空',novalidate: true,validType:'maxLength[10]'" style="width:150px;"/><span style="color:red">*</span>
							</td> --%>
							<th class="wd-20"><label>业务类型</label></th>
								<td><input id="bizType" name="bizType"    style="width: 150px" 
										class="easyui-combotree" data-options="
											editable:false, 
										   multiple: 'true',
										  url:'${path}/enumset/accountBizTypeList/combobox',
									 	  onLoadSuccess: function () {
									 	  		var strAry, ary=$('#bizTypes').val();
						 	  					if(ary==''){
						 	  						$('#bizType').combotree('setText', '请选择');	
						 	  					}else{
						 	  						strAry=ary.split('_');	
						 	  						$('#bizType').combotree('setValues',strAry );
						 	  					}
									 	  }
										     "/><span style="color:red">*</span></td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>商户号</label></th>
							<td>
								<input type="text" id="merchantNo" name="merchantNo" value="${channelConfig.merchantNo }" class="easyui-validatebox" data-options="required:true,missingMessage:'商户号不能为空',novalidate: true,validType:'maxLength[100]'" style="width:150px;"/><span style="color:red">*</span>
							</td>
							<th class="wd-20"><label>文件下载方式</label></th>
							<td>
								<!-- <input type="text" id="fetchMethod" name="fetchMethod" class="easyui-validatebox" data-options="required:false,missingMessage:'文件下载方式不能为空',novalidate: true,validType:'maxLength[100]'" style="width:150px;"/> -->
								<input id="fetchMethod" class="easyui-combobox" name="fetchMethod" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/getAccountFetchMethodList',
										value:'${channelConfig.fetchMethod }'" style="width:150px;"/><span style="color:red">*</span>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>获取文件方式</label></th>
							<td>
								<!-- <input type="text" id="fetchType" name="fetchType" class="easyui-validatebox" data-options="" style="width:150px;"/> -->
								<input id="fetchType" class="easyui-combobox" name="fetchType" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/getAccountFetchTypeList',
										value:'${channelConfig.fetchType }'" style="width:150px;"/><span style="color:red">*</span>
							</td>
							<th class="wd-20"><label>HTTP请求下载地址</label></th>
							<td>
								<input type="text" id="downloadUrl" name="downloadUrl" value="${channelConfig.downloadUrl }" class="easyui-validatebox" data-options="" style="width:150px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>FTP请求登录用户名</label></th>
							<td>
								<input type="text" id="loginUsername" name="loginUsername" value="${channelConfig.loginUsername }" class="easyui-validatebox" data-options="" style="width:150px;"/>
							</td>
							<th class="wd-20"><label>FTP请求登录密码</label></th>
							<td>
								<input type="text" id="loginPwd" name="loginPwd" value="${channelConfig.loginPwd }" class="easyui-validatebox" data-options="" style="width:150px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>FTP请求文件存放目录</label></th>
							<td>
								<input type="text" id="filePath" name="filePath" value="${channelConfig.filePath }" class="easyui-validatebox" data-options="" style="width:150px;"/>
							</td>
							<%-- <th class="wd-20"><label>FTP请求文件备份目录</label></th>
							<td>
								<input type="text" id="fileBackupPath" name="fileBackupPath" value="${channelConfig.fileBackupPath }" class="easyui-validatebox" data-options="" style="width:150px;"/>
							</td> --%>
							<%-- <th class="wd-20"><label>文件编码</label></th>
							<td>
								<input type="text" id="fileEncoding" name="fileEncoding" value="${channelConfig.fileEncoding }" class="easyui-validatebox" data-options="required:true,missingMessage:'文件编码不能为空',novalidate: true,validType:'maxLength[20]'" style="width:150px;"/><span style="color:red">*</span>
							</td> --%>
							<th class="wd-20"><label>文件编码</label></th>
							<td>
								<!-- <input type="text" id="fileEncoding" name="fileEncoding" class="easyui-validatebox" data-options="novalidate: true,validType:'maxLength[15]'" style="width:150px;"/> -->
								<input style="width: 150px" name="fileEncoding"  
															class="easyui-combobox" id="fileEncoding"
															data-options="editable:false,data:[{'type':'','text':'请选择'},{'type':'UTF-8','text':'UTF-8'},{'type':'GBK','text':'GBK'}],	valueField:'type',textField:'text',value:'${channelConfig.fileEncoding }'" />	
								<span style="color:red">*</span>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>文件名格式</label></th>
							<td>
								<input type="text" id="fileNameFormat" name="fileNameFormat" value="${channelConfig.fileNameFormat }" class="easyui-validatebox" data-options="" style="width:150px;"/>
							</td>
							<th class="wd-20"><label>文件后缀名</label></th>
							<td>
								<input type="text" id="fileSuffix" name="fileSuffix" value="${channelConfig.fileSuffix }" class="easyui-validatebox" data-options="" style="width:150px;"/>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>从第几行开始读取记录</label></th>
							<td>
								<input type="text" id="fileStartIndex" name="fileStartIndex" value="${channelConfig.fileStartIndex }" class="easyui-validatebox" data-options="required:true,missingMessage:'第几行开始读取记录不能为空',novalidate: true,validType:['maxLength[2]','Number']" style="width:150px;"/><span style="color:red">*</span>
							</td>
							<th class="wd-20"><label>对账时间</label></th>
							<td>
								<!-- <input type="text" id="accountTime" name="accountTime" class="easyui-validatebox" data-options="required:false,missingMessage:'不能为空',novalidate: true,validType:'maxLength[10]'" style="width:150px;"/> -->
								<input id="accountTime" name="accountTime" class="easyui-timespinner"  style="width:150px;" required="required" data-options="min:'00:00',showSeconds:true,value:'${channelConfig.accountTime }'" /><span style="color:red">*</span>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>文件内容分隔符</label></th>
							<td>
								<input type="text" id="attrSplit" name="attrSplit" value="${channelConfig.attrSplit }" class="easyui-validatebox" data-options="" style="width:150px;"/>
							</td>
							<th class="wd-20"><label>通道状态</label></th>
							<td>
								<input id="status" class="easyui-combobox" name="status" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/getAccountChannelStatusList',
										value:'${channelConfig.status }' " style="width:150px;"/><span style="color:red">*</span>
							</td>
						  </tr>
						  <tr>
							<th class="wd-20"><label>币种</label></th>
							<td>
								<input id="currency" class="easyui-combobox" name="currency" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/dictionary/0',
										value:'${channelConfig.currency }'" style="width:150px;"/><span style="color:red">*</span>
							</td>
							<th class="wd-20"><label>货币单位</label></th>
							<td>
								<!-- <input type="text" id="currencyUnit" name="currencyUnit" class="easyui-validatebox" data-options="" style="width:150px;"/> -->
								<input id="currencyUnit" class="easyui-combobox" name="currencyUnit" data-options="
										editable:false,valueField: 'value',
										textField: 'name',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/getAccountCurrencyUnitList',
										value:'${channelConfig.currencyUnit }'" style="width:150px;"/><span style="color:red">*</span>
							</td>
						  </tr>
						 <tr>
						 	<th class="wd-20"><label>FTP主机</label></th>
							<td>
								<input type="text" id="server" name="server" value="${channelConfig.server }" class="easyui-validatebox" data-options="novalidate: true,validType:'maxLength[100]'" style="width:150px;"/>
							</td>
							<th class="wd-20"><label>FTP端口号</label></th>
							<td>
								<input type="text" id="port" name="port" value="${channelConfig.port }" class="easyui-validatebox" data-options="required:true,missingMessage:'不能为空',novalidate: true,validType:'Number'" style="width:150px;"/><span style="color:red">*</span>
							</td>
						 </tr>
						  <tr>
					  		<th class="wd-20"><label>FTP请求文件存放目录子目录 </label></th>
							<td>
								<input type="text" id="filePathSub" name="filePathSub" value="${channelConfig.filePathSub }" class="easyui-validatebox" data-options="novalidate: true,validType:'maxLength[200]'" style="width:150px;"/>
							</td>
							<%-- <th class="wd-20"><label>处理进程</label></th>
							<td>
								<input type="text" id="appName" name="appName" value="${channelConfig.appName }" class="easyui-validatebox" data-options="required:false,novalidate: true,validType:'maxLength[20]'" style="width:150px;"/>
							</td>	 --%>
							<th class="wd-20"><label>处理进程</label></th>
							<td>
								<!-- <input type="text" id="appName" name="appName" class="easyui-validatebox" data-options="required:false,novalidate: true,validType:'maxLength[20]'" style="width:150px;"/> -->
								<input id="appName" class="easyui-combobox" name="appName" data-options="
									editable:false,valueField: 'value',
									textField: 'name',
									//url是下拉框请求显示的数据
									url:'${path }/enumset/appName',
									value:'${channelConfig.appName }' " style="width:150px;"/><span style="color:red">*</span>
						 </tr>
						 <tr>
							<th class="wd-20"><label>下载操作最多失败次数</label></th>
							<td>
								<input type="text" id="maxDownloadFailedTimes" name="maxDownloadFailedTimes" value="${channelConfig.maxDownloadFailedTimes }" class="easyui-validatebox" data-options="required:true,missingMessage:'不能为空',novalidate: true,validType:'Number'" value="0" style="width:150px;"/><span style="color:red">*</span>
							</td>
							<th class="wd-20"><label>入库操作最多失败次数</label></th>
							<td>
								<input type="text" id="maxInsertFailedTimes" name="maxInsertFailedTimes" value="${channelConfig.maxInsertFailedTimes }" class="easyui-validatebox" data-options="required:true,missingMessage:'不能为空',novalidate: true,validType:'Number'" value="0" style="width:150px;"/><span style="color:red">*</span>
							</td>
						  </tr>
						 <tr>
							<th class="wd-20"><label>备份操作最多失败次数</label></th>
							<td>
								<input type="text" id="maxBackupFailedTimes" name="maxBackupFailedTimes" value="${channelConfig.maxBackupFailedTimes }" class="easyui-validatebox" data-options="required:true,missingMessage:'不能为空',novalidate: true,validType:'maxLength[3]'" style="width:150px;"/><span style="color:red">*</span>
							</td>
							<th class="wd-20"><label>最大对账失败次数</label></th>
							<td>
								<input type="text" id="maxAccountFailedTimes" name="maxAccountFailedTimes" value="${channelConfig.maxAccountFailedTimes }" class="easyui-validatebox" data-options="required:true,missingMessage:'不能为空',novalidate: true,validType:'maxLength[3]'" style="width:150px;"/><span style="color:red">*</span>
							</td>
						 </tr>
						 <tr>
						 	<th class="wd-20"><label>汇总信息所在行</label></th>
							<td>
								<input type="text" id="fileHeaderAttrsIndex" name="fileHeaderAttrsIndex" value="${channelConfig.fileHeaderAttrsIndex }" class="easyui-validatebox" data-options="novalidate: true,validType:'maxLength[10]'" style="width:150px;"/>
								<br/><span style="color:red">跨行以_分隔</span>
							</td>
						 </tr>
						 <tr>
						 	<th class="wd-20"><label>文件属性定义</label></th>
						 	<td>
						 		<select id="select1" style="width:150px;">
					 				<%
					 					List<String> attrsDefinitionLeftList = (List<String>)request.getAttribute("attrsDefinitionLeftList");
					 					for (String attrsDefinition : attrsDefinitionLeftList) {
					 				%>
						            <option value="<%=attrsDefinition%>"><%=attrsDefinition%></option>
						            <%
					 					}
						            %>
						        </select>
						        <br/>
						        <br/>
						        <select id="select2" style="width:150px;">
					 				<%
					 					for (int i = 0; i <= 30; i++) {
					 				%>
						            <option value="<%=i%>"><%=i%></option>
						            <%
					 					}
						            %>
						        </select>
						        <br/>
						        <br/>
						        <input type="button" id="add" value="添加到选择列表"/>
						        <input type="button" id="remove" value="从选择列表移除"/>
						        <br/>
						        <br/>
						        <select multiple="multiple" id="attrsDefinition" name="attrsDefinition" style="width: 150px;height:160px;">
						        	<%
					 					List<String> attrsDefinitionRightList = (List<String>)request.getAttribute("attrsDefinitionRightList");
					 					for (String attrsDefinition : attrsDefinitionRightList) {
					 				%>
						            <option value="<%=attrsDefinition%>"><%=attrsDefinition%></option>
						            <%
					 					}
						            %>
						        </select>
						   </td>
						   <%-- <th class="wd-20"><label>文件头属性定义</label></th>
						 	<td>
						 		<select id="select3" style="width:150px;">
					 				<%
					 					List<String> headerAttrsDefinitionLeftList = (List<String>)request.getAttribute("headerAttrsDefinitionLeftList");
					 					for (String headerAttrsDefinition : headerAttrsDefinitionLeftList) {
					 				%>
						            <option value="<%=headerAttrsDefinition%>"><%=headerAttrsDefinition%></option>
						            <%
					 					}
						            %>
						        </select>
						        <br/>
						        <br/>
						        <select id="select4" style="width:150px;">
					 				<%
					 					for (int i = 0; i <= 30; i++) {
					 				%>
						            <option value="<%=i%>"><%=i%></option>
						            <%
					 					}
						            %>
						        </select>
						        <br/>
						        <br/>
						        <input type="button" id="add2" value="添加到选择列表"/>
						        <input type="button" id="remove2" value="从选择列表移除"/>
						        <br/>
						        <br/>
						        <select multiple="multiple" id=headerAttrsDefinition name="headerAttrsDefinition" style="width: 150px;height:160px;">
						        	<%
					 					List<String> headerAttrsDefinitionRightList = (List<String>)request.getAttribute("headerAttrsDefinitionRightList");
					 					for (String headerAttrsDefinition : headerAttrsDefinitionRightList) {
					 				%>
						            <option value="<%=headerAttrsDefinition%>"><%=headerAttrsDefinition%></option>
						            <%
					 					}
						            %>
						        </select>
						   </td> --%>
						 </tr>
				  </table>
				</div>
			</form>
		</div>
</div>
<script>
	//文件属性定义
	//移到右边
	$('#add').click(function() {
		var str1 = $('#select1 option:selected').val();
		var str2 = $('#select2 option:selected').val();
		if (str1 != null && str1 != "" && str2 != null && str2 != "") {
			var value = str2 + "/" + str1;
			$('#select1 option:selected').remove();
			$('#select2 option:selected').remove();
			//获取选中的选项，删除并追加给对方
			$("#attrsDefinition").append("<option value='"+ value +"'>"+ value +"</option>");
		}
	});
	//移到左边
	$('#remove').click(function() {
		var sltSrc=$('#attrsDefinition option:selected');
	    for (var i=0;i<sltSrc.length;i++){
	        var value = sltSrc[i].value;
			var str = value.split("/");
			var str2 = str[0];
			var str1 = str[1];
			$('#select1').append("<option value='"+ str1 +"'>"+ str1 +"</option>");
			$('#select2').append("<option value='"+ str2 +"'>"+ str2 +"</option>");
		    $('#attrsDefinition option:selected').remove();
	    }
	});
	//文件头属性定义
	//移到右边
	$('#add2').click(function() {
		var str1 = $('#select3 option:selected').val();
		var str2 = $('#select4 option:selected').val();
		if (str1 != null && str1 != "" && str2 != null && str2 != "") {
			var value = str2 + "/" + str1;
			$('#select3 option:selected').remove();
			$('#select4 option:selected').remove();
			//获取选中的选项，删除并追加给对方
			$("#headerAttrsDefinition").append("<option value='"+ value +"'>"+ value +"</option>");
		}
	});
	//移到左边
	$('#remove2').click(function() {
		var sltSrc=$('#headerAttrsDefinition option:selected');
	    for (var i=0;i<sltSrc.length;i++){
	        var value = sltSrc[i].value;
			var str = value.split("/");
			var str2 = str[0];
			var str1 = str[1];
			$('#select3').append("<option value='"+ str1 +"'>"+ str1 +"</option>");
			$('#select4').append("<option value='"+ str2 +"'>"+ str2 +"</option>");
		    $('#headerAttrsDefinition option:selected').remove();
	    }
	});
	/* //全部移到右边
	$('#add_all').click(function() {
	    //获取全部的选项,删除并追加给对方
	    $('#select1 option').appendTo('#attrsDefinition');
	});
	//全部移到左边
	$('#remove_all').click(function() {
	    $('#attrsDefinition option').appendTo('#select1');
	});
	//双击选项
	$('#select1').dblclick(function(){ //绑定双击事件
	    //获取全部的选项,删除并追加给对方
	    $("option:selected",this).appendTo('#attrsDefinition'); //追加给对方
	});
	//双击选项
	$('#attrsDefinition').dblclick(function(){
	   $("option:selected",this).appendTo('#select1');
	}); */

</script>