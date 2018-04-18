<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/xml"  prefix = "x" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql"  prefix = "sql" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions"  prefix = "fn" %> 
<div class="content-body">
		<div class="search-panel-content">
			<form id="editTppAccountBizsysConfigFrom" name="editTppAccountBizsysConfigFrom" method="post" action="editTppAccountBizsysConfigAction.action">
				<div class="search-panel-bd">
					<input type="hidden" id="id" name="id" value="${bizsysConfig.id}" />
						<table class="search-table">
								 <tr>
									<th class="wd-20"><label>业务系统号</label></th>
									<td>
										<input id="bizSysNo" class="easyui-combobox" name="bizSysNo" data-options="
											editable:false,valueField: 'value',
											textField: 'name',
											//url是下拉框请求显示的数据
											url:'${path }/enumset/bizSysNoList',
											value:'${bizsysConfig.bizSysNo}' " style="width:150px;"/>  <span style="color:red">*</span>
									</td>
									<th class="wd-20"><label>FTP文件存放目录</label></th>
									<td>
										<input type="text" id="ftpPath" name="ftpPath" value="${bizsysConfig.ftpPath }" class="easyui-validatebox" data-options="required:true,missingMessage:'FTP文件存放目录不能为空',novalidate: true,validType:'maxLength[200]'" style="width:150px;"/><span style="color:red">*</span>
									</td>
								</tr>
								<tr>
									<th class="wd-20"><label>FTP服务</label></th>
									<td>
										<input type="text" id="ftpServer" name="ftpServer" value="${bizsysConfig.ftpServer }" class="easyui-validatebox" data-options="required:true,missingMessage:'FTP服务不能为空',novalidate: true,validType:'maxLength[100]'" style="width:150px;"/><span style="color:red">*</span> 
									</td>
									<th class="wd-20"><label>FTP端口</label></th>
									<td>
										<input type="text" id="ftpPort" name="ftpPort" value="${bizsysConfig.ftpPort }" class="easyui-validatebox" data-options="required:true,missingMessage:'不能为空',novalidate: true,validType:'Number'" style="width:150px;"/><span style="color:red">*</span> 
									</td>
							  	</tr>
								<tr>
									<th class="wd-20"><label>FTP登录帐号</label></th>
									<td>
										<input type="text" id="ftpUsername" name="ftpUsername" value="${bizsysConfig.ftpUsername }" class="easyui-validatebox" data-options="required:true,missingMessage:'FTP登录帐号不能为空',novalidate: true,validType:'maxLength[100]'" style="width:150px;"/><span style="color:red">*</span> 
									</td>
									<th class="wd-20"><label>FTP登录密码</label></th>
									<td>
										<input type="text" id="ftpPwd" name="ftpPwd" value="${bizsysConfig.ftpPwd }" class="easyui-validatebox" data-options="required:true,missingMessage:'FTP登录密码不能为空',novalidate: true,validType:'maxLength[100]'" style="width:150px;"/><span style="color:red">*</span> 
									</td>
								  </tr>
								  
								<tr>
								  	<th class="wd-20"><label>处理进程</label></th>
									<td>
										<input id="appName" class="easyui-combobox" name="appName" data-options="
											editable:false,valueField: 'value',
											textField: 'name',
											//url是下拉框请求显示的数据
											url:'${path }/enumset/appName',
											value:'${bizsysConfig.appName }' " style="width:150px;"/><span style="color:red">*</span>
									</td>
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
											     "/><span style="color:red">*</span>
											     <input  type="hidden" id="bizTypes" name="bizTypes" value="${bizTypes}"/></td>	
								</tr>
								<tr>
									<th class="wd-20"><label>文件内容分隔符</label></th>
									<td>
										<input style="width: 150px" name="attrsSplit"  
															class="easyui-combobox" id="attrsSplit"
															data-options="editable:false,data:[{'type':'','text':'请选择'},{'type':',','text':','},{'type':'|','text':'|'}],	valueField:'type',textField:'text',
															value:'${bizsysConfig.attrsSplit }'" />		
									<span style="color:red">*</span>
									</td>
									<th class="wd-20"><label>对账文件编码</label></th>
									<td>
										<input style="width: 150px" name="fileEncoding"  
															class="easyui-combobox" id="fileEncoding"
															data-options="editable:false,data:[{'type':'','text':'请选择'},{'type':'UTF-8','text':'UTF-8'},{'type':'GBK','text':'GBK'}],	valueField:'type',textField:'text',
															value:'${bizsysConfig.fileEncoding }'" />	
										<span style="color:red">*</span>
									</td>
								</tr>
								<tr>
								 	<th class="wd-20"><label>状态</label></th>
									<td>
										<input id="status" class="easyui-combobox" name="status" data-options="
												editable:false,valueField: 'value',
												textField: 'name',
												//url是下拉框请求显示的数据
												url:'${path }/enumset/getAccountBizsysConfigStatusList',
												value:${bizsysConfig.status } " style="width:150px;"/><span style="color:red">*</span> 
									</td>
								 	<th class="wd-20"><label>获取对账文件时间</label></th>
									<td>
										<!-- <input type="text" id="bizsysAccountTime" name="bizsysAccountTime" class="easyui-validatebox"  style="width:150px;"/> -->
										<input id="accountTime" name="accountTime"  class="easyui-timespinner"  style="width:150px;" required="required" data-options="min:'00:00',showSeconds:true,value:'${bizsysConfig.accountTime }'" /><span style="color:red">*</span>
									</td>
								</tr>
								<tr>
									<th class="wd-20"><label>最大推送失败次数</label></th>
									<td>
										<input type="text" id="maxPushFailedTimes" name="maxPushFailedTimes" value="${bizsysConfig.maxPushFailedTimes }" class="easyui-validatebox"  data-options="required:true,missingMessage:'不能为空',novalidate: true,validType:'Number'"  style="width:150px;"/><span style="color:red">*</span>
									</td>
									<th class="wd-20"><label>最大本地存储失败次数</label></th>
									<td>
										<input type="text" id="maxLocalizeFailedTimes" name="maxLocalizeFailedTimes" value="${bizsysConfig.maxLocalizeFailedTimes }" class="easyui-validatebox" data-options="required:true,missingMessage:'不能为空',novalidate: true,validType:'Number'" style="width:150px;"/><span style="color:red">*</span>
									</td>
								</tr>
									
								 <tr>
									
									
								  </tr>
								<%--  <tr>
									<th class="wd-20"><label>使用的货币单位</label></th>
									<td>
										<!-- <input type="text" id="currencyUnit" name="currencyUnit" class="easyui-validatebox"  style="width:150px;"/> -->
										<input id="currencyUnit" class="easyui-combobox" name="currencyUnit" data-options="
												editable:false,valueField: 'value',
												textField: 'name',
												//url是下拉框请求显示的数据
												url:'${path }/enumset/getAccountCurrencyUnitList', 
												value:${bizsysConfig.currencyUnit }" style="width:150px;"/><span style="color:red">*</span>
									</td>
									
								  </tr> --%>
							 	<tr>
							 		<th class="wd-20"><label>文件属性定义</label></th>
							 		<td>
							 			<select multiple="multiple" id="select1" style="width:100px;height:160px;">
							 				<%
							 					List<String> attrsDefinitionLeftList = (List<String>)request.getAttribute("attrsDefinitionLeftList");
							 					for (String attrsDefinition : attrsDefinitionLeftList) {
							 						
							 					
							 				%>
								            <option value="<%=attrsDefinition%>"><%=attrsDefinition%></option>
								            <%
							 					}
								            %>
								        </select>
								        <select multiple="multiple" id="attrsDefinition" name="attrsDefinition" style="width: 100px;height:160px;">
								            <%
							 					List<String> attrsDefinitionRightList = (List<String>)request.getAttribute("attrsDefinitionRightList");
							 					for (String attrsDefinition : attrsDefinitionRightList) {
							 						
							 					
							 				%>
								            <option value="<%=attrsDefinition%>"><%=attrsDefinition%></option>
								            <%
							 					}
								            %>
								        </select>
								        <br/>
							        	<input type="button" id="add" value="选中移到右边&gt;&gt;"/>
							        	<input type="button" id="remove" value="&lt;&lt;选中移至左边"/>
							        	<br/>
							        	<input type="button" id="add_all" value="全部移到右边&gt;&gt;"/>
							        	<input type="button" id="remove_all" value="&lt;&lt;全部移至左边"/>
							 		</td>
							 		<th class="wd-20"><label>文件名称格式</label></th>
									<td>
										<select multiple="multiple" id="select3" style="width:100px;height:160px;">
							 				<%
							 					List<String> fileNameFormatLeftList = (List<String>)request.getAttribute("fileNameFormatLeftList");
							 					for (String fileNameFormat : fileNameFormatLeftList) {
							 				%>
								            <option value="<%=fileNameFormat%>"><%=fileNameFormat%></option>
								            <%
							 					}
								            %>
								        </select>
								        <select multiple="multiple" id="fileNameFormat" name="fileNameFormat" style="width: 100px;height:160px;">
								        	<%
							 					List<String> fileNameFormatRightList = (List<String>)request.getAttribute("fileNameFormatRightList");
							 					for (String fileNameFormat : fileNameFormatRightList) {
							 				%>
								            <option value="<%=fileNameFormat%>"><%=fileNameFormat%></option>
								            <%
							 					}
								            %>
								        </select>
								        <br/>
							        	<input type="button" id="add2" value="选中移到右边&gt;&gt;"/>
							        	<input type="button" id="remove2" value="&lt;&lt;选中移至左边"/>
							        	<br/>
							        	<input type="button" id="add_all2" value="全部移到右边&gt;&gt;"/>
							        	<input type="button" id="remove_all2" value="&lt;&lt;全部移至左边"/>
									
									</td>
							 		<%-- <th class="wd-20"><label>文件头属性定义</label></th>
									<td>
										<select multiple="multiple" id="select2" style="width:100px;height:160px;">
							 				<%
							 					List<String> headerAttrsDefinitionLeftList = (List<String>)request.getAttribute("headerAttrsDefinitionLeftList");
							 					for (String headerAttrsDefinition : headerAttrsDefinitionLeftList) {
							 				%>
								            <option value="<%=headerAttrsDefinition%>"><%=headerAttrsDefinition%></option>
								            <%
							 					}
								            %>
								        </select>
								        <select multiple="multiple" id="headerAttrsDefinition" name="headerAttrsDefinition" style="width: 100px;height:160px;">
								        	<%
							 					List<String> headerAttrsDefinitionRightList = (List<String>)request.getAttribute("headerAttrsDefinitionRightList");
							 					for (String headerAttrsDefinition : headerAttrsDefinitionRightList) {
							 				%>
								            <option value="<%=headerAttrsDefinition%>"><%=headerAttrsDefinition%></option>
								            <%
							 					}
								            %>
								        </select>
								        <br/>
							        	<input type="button" id="add1" value="选中移到右边&gt;&gt;"/>
							        	<input type="button" id="remove1" value="&lt;&lt;选中移至左边"/>
							        	<br/>
							        	<input type="button" id="add_all1" value="全部移到右边&gt;&gt;"/>
							        	<input type="button" id="remove_all1" value="&lt;&lt;全部移至左边"/>
									</td> --%>
									
							 	</tr>
					</table>
				</div>
			</form>
		</div>
</div>
<script type="text/javascript">
$(function(){
	//文件属性定义
    //移到右边
    $('#add').click(function() {
    //获取选中的选项，删除并追加给对方
        $('#select1 option:selected').appendTo('#attrsDefinition');
    });
    //移到左边
    $('#remove').click(function() {
        $('#attrsDefinition option:selected').appendTo('#select1');
    });
    //全部移到右边
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
    });
    
  	//文件头属性定义
  	//移到右边
    $('#add1').click(function() {
    //获取选中的选项，删除并追加给对方
        $('#select2 option:selected').appendTo('#headerAttrsDefinition');
    });
    //移到左边
    $('#remove1').click(function() {
        $('#headerAttrsDefinition option:selected').appendTo('#select2');
    });
    //全部移到右边
    $('#add_all1').click(function() {
        //获取全部的选项,删除并追加给对方
        $('#select2 option').appendTo('#headerAttrsDefinition');
    });
    //全部移到左边
    $('#remove_all1').click(function() {
        $('#headerAttrsDefinition option').appendTo('#select2');
    });
    //双击选项
    $('#select2').dblclick(function(){ //绑定双击事件
        //获取全部的选项,删除并追加给对方
        $("option:selected",this).appendTo('#headerAttrsDefinition'); //追加给对方
    });
    //双击选项
    $('#headerAttrsDefinition').dblclick(function(){
       $("option:selected",this).appendTo('#select2');
    });
    
    
  	//文件名称格式
  	//移到右边
    $('#add2').click(function() {
    //获取选中的选项，删除并追加给对方
        $('#select3 option:selected').appendTo('#fileNameFormat');
    });
    //移到左边
    $('#remove2').click(function() {
        $('#fileNameFormat option:selected').appendTo('#select3');
    });
    //全部移到右边
    $('#add_all2').click(function() {
        //获取全部的选项,删除并追加给对方
        $('#select3 option').appendTo('#fileNameFormat');
    });
    //全部移到左边
    $('#remove_all2').click(function() {
        $('#fileNameFormat option').appendTo('#select3');
    });
    //双击选项
    $('#select3').dblclick(function(){ //绑定双击事件
        //获取全部的选项,删除并追加给对方
        $("option:selected",this).appendTo('#fileNameFormat'); //追加给对方
    });
    //双击选项
    $('#fileNameFormat').dblclick(function(){
       $("option:selected",this).appendTo('#select3');
    });
});
</script>
