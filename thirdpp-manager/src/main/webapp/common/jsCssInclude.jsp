<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <link href="${path}/js/bootstrap2/img/ico57.png" rel="icon" type="image/x-icon" />
<link rel="stylesheet" href="${sessionScope.path}/js/bootstrap2/css/bootstrap.min.css" />
<link rel="stylesheet" href="${sessionScope.path}/js/bootstrap2/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${sessionScope.path}/js/bootstrap2/css/uniform.css" />
<link rel="stylesheet" href="${sessionScope.path}/js/bootstrap2/css/select2.css" />		
<link rel="stylesheet" href="${sessionScope.path}/js/bootstrap2/css/unicorn.main.css" />
<link rel="stylesheet" href="${sessionScope.path}/js/bootstrap2/css/bootstrap-table.min.css" />
<link rel="stylesheet" href="${sessionScope.path}/js/bootstrap2/css/bootstrap-tree.css" />

<script type="text/javascript" src="${sessionScope.path}/js/bootstrap2/js/jquery.min.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/bootstrap2/js/jquery.ui.custom.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/bootstrap2/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/bootstrap2/js/jquery.uniform.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/bootstrap2/js/select2.min.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/bootstrap2/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/bootstrap2/js/unicorn.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/bootstrap2/js/unicorn.login.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/bootstrap2/js/jquery.validate.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/bootstrap2/js/jquery.bootstrap.teninedialog.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/bootstrap2/js/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/My97DatePicker/WdatePicker.js"></script> --%>
<script type="text/javascript" src="${sessionScope.path}/js/Clock.js"></script>

<link rel="stylesheet" href="${sessionScope.path}/css/base.css" />
<link rel="stylesheet" type="text/css" href="${sessionScope.path}/css/batch.css">
<link rel="stylesheet" type="text/css" href="${sessionScope.path}/css/baseUI.css">
<link rel="stylesheet" type="text/css" href="${sessionScope.path}/css/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="${sessionScope.path}/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${sessionScope.path}/css/progress.css">
<script type="text/javascript" src="${sessionScope.path}/js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/easyui.validator.ext.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/easyui-validate.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/ui.lib.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/json/json2.min.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/ajaxProcessUtil.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/progress.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/privCity.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/json/basedata.json"></script>
<script type="text/javascript" src="${sessionScope.path}/js/datagrid-detailview.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/easyui-datagrid.ext.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/md5.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/easyui.ext.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/easyui-datagrid-moverow.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/datagrid-dnd.js"></script>
<script type="text/javascript" src="${sessionScope.path}/js/zdFormatter.js"></script>
<script>
    contextPath = '${path}';

    /* jQuery.extend(jQuery.validator.messages, {
		required: "请填写本字段",
		remote: "请指定一个不重复的值",
		email: "请输入正确格式的电子邮件",
		url: "请输入合法的网址",
		date: "请输入合法的日期",
		dateISO: "请输入合法的日期 (ISO).",
		number: "请输入合法的数字",
		digits: "只能输入整数",
		creditcard: "请输入合法的信用卡号",
		equalTo: "请再次输入相同的值",
		accept: "请输入拥有合法后缀名的字符串",
		maxlength: jQuery.validator.format("允许的最大长度为 {0} 个字符"),
		minlength: jQuery.validator.format("允许的最小长度为 {0} 个字符"),
		rangelength: jQuery.validator.format("允许的长度为{0}和{1}之间"),
		range: jQuery.validator.format("请输入介于 {0} 和 {1} 之间的值"),
		max: jQuery.validator.format("请输入一个最大为 {0} 的值"),
		min: jQuery.validator.format("请输入一个最小为 {0} 的值")
	});
     */
	function getAjaxCfg(){
	    return {
	            type : "POST",
	            dataType : "json",
	            cache : false,
	            async : true,
	            contentType: "application/json; charset=utf-8"
	           };
	}
	/* jQuery(function($){
		$.extend($.fn.validatebox.defaults.rules, {     
	        maxLength: {     
	            validator: function(value, param){     
	                return param[0] >= value.replace(/[^\x00-\xff]/g, '___').length;     
	            },     
	            message: '请输入最大{0}位字符.'    
	        }     
	    });
	}); */
	$(function(){
		
		//控制复选框全选/全不选
		$("input[name='title-checkbox']").click(function() {
            $('input[name="subCheckBox"]').attr("checked",this.checked); 
        });
		
		$("#pageSizeSelect").change(function(){
			var pageSize = $(this).children('option:selected').val();
			if(document.forms[0]){ 
	            var url = document.forms[0].getAttribute("action") + "?pageSize=";  
	            document.forms[0].action = url+pageSize;  
	            document.forms[0].submit();  
	        }else{
	            var url = document.location + "?pageSize="; 
	            document.location = url + pageSize; 
	        }
		});
		
	});
	
	//打开提示对话框
	function openTeninedialog(data){
		$.teninedialog({
	        title:'提示信息',
	        content:data,
	        showCloseButton:true,// 是否显示关闭按钮
	        otherButtonStyles:['btn-primary'],
	        clickButton:function(sender,modal,index){
	            $(this).closeDialog(modal);
	        }
	    });
	}
	
	/** 
	* 判断是否null
	* 修改了data == ""，解决 0 == "" 为true的bug
	* @param data 
	*/
	function isNull(data){ 
	   return (data === "" || data == undefined || data == null) ? "" : data; 
	}
</script>
<script type="text/javascript" src="${sessionScope.path}/js/jgxLoader.js"></script>