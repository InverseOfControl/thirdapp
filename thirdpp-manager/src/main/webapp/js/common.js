$(function() {
	
	//随机数Key值
	var randomKeyName = 'r';
	
	$.isString = function(str) {
		return typeof(str) == 'string';
	}
	
	/**
	 * 判断变量是否为空
	 */
	$.isEmpty = function(value) {
		if (typeof(value) =='boolean') {
			return false;
		}
		if ($.isString(value)) {
			value = $.trim(value);
		}
		return (value == null || value == '' || typeof(value) == 'undefined');
	}
	
	/**
	 * 将字母转换成大写
	 */
	$.toUpperCase = function (string) {
		if ($.isString(string)) {
			return string.toUpperCase();
		}
		return string;
	}
	
	/**
	 * 将字母转换成小写
	 */
	$.toLowerCase = function (string) {
		if ($.isString(string)) {
			return string.toLowerCase();
		}
		return string;
	}
	
	/**
	 * 在地址后面追加随机数参数
	 */
	$.urlAppendRandom = function(url) {
		if ($.isEmpty(url) || !$.isString(url)) {
			return url;
		}
		if (url.indexOf(randomKeyName + '=') < 0) {
			if (url.indexOf('?') < 0) {
				url += '?';
			} else {
				url += '&';
			}
			url += randomKeyName + '=' + new Date().getTime();
		}
		return url;
	}
	
	/**
	 * 将字符串转换成JSON对象 如 a=b&c=d&e=f 结果{'a':'b','c':'d','e':'f'}
	 */
	$.serializeToJsonObject = function (data) {
		if ($.isEmpty(data) || !$.isString(data)) {
			return data;
		}
		data=data.replace(/&/g,"\",\"");
		data=data.replace(/=/g,"\":\"");
		data="{\""+data+"\"}";
		return eval('(' + data + ')');
	}
	
	//Ajax配置项默认值
	$.ajaxVariable = {};
	//默认数据包
	$.ajaxVariable.data = {};
	//默认异步、同步标识
	$.ajaxVariable.async = true;
	//默认请求方式
	$.ajaxVariable.type = 'get';
	//默认返回的数据类型
	$.ajaxVariable.dataType = 'json';
	//默认回调函数
	$.ajaxVariable.emptyFun = function() {};
	
	/**
	 * 针对JQuery.Ajax进行封装(便于内部控制)
	 * 
	 * url 请求远程地址
	 * data 推送远程数据包 如{'date':'20150505','time':'141414'} 或 date=20150505&time=141414
	 * async 默认值: true。默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。
     *       	 注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
	 * type  请求方式("POST" 或 "GET")， 默认为 "GET"
	 * dataType  预期服务器返回的数据类型，常用的如：xml、html、json、text
	 * success  成功回调函数
	 * error 失败回调函数
	 * complete 完成回调函数(成功和失败均回调)
	 * 
	 */
	$.ajaxPackage = function(options) {
		var url = options.url;
		var data = options.data;
		var async = options.async;
		var type = options.type;
		var dataType = options.dataType;
		var success = options.success;
		var error = options.error;
		var complete = options.complete;
		
		if ($.isEmpty(url)) {
			/** 缺少远程地址参数 **/
			$.myConsole.writeWarnLog('缺少远程地址参数');
			return;
		}
		
		data = $.isEmpty(data) ? $.ajaxVariable.data : data;
		async = $.isEmpty(async) ? $.ajaxVariable.async : async;
		type = $.isEmpty(type) ? $.ajaxVariable.type : type;
		type = $.toLowerCase(type);
		if (type != 'get' && type != 'post') {
			/** 请求方式参数有误 **/
			$.myConsole.writeWarnLog('请求方式参数有误');
			return;
		}
		
		dataType = $.isEmpty(dataType) ? $.ajaxVariable.dataType : dataType;
		dataType = $.toLowerCase(dataType);
		if (dataType != 'xml' && dataType != 'html' && dataType != 'script' && dataType != 'json' 
			&& dataType != 'jsonp' && dataType != 'text') {
			/** 请求方式参数有误 **/
			$.myConsole.writeWarnLog('请求方式参数有误');
			return;
		}
		//成功回调函数
		success = $.isFunction(success) ? success : $.ajaxVariable.emptyFun;
		//失败回调函数
		error = $.isFunction(error) ? error : $.ajaxVariable.emptyFun;
		//完成回调函数
		complete = $.isFunction(complete) ? complete : $.ajaxVariable.emptyFun;
		//追加随机数
		url = $.urlAppendRandom(url);
		$.ajax({
			url : url,
			type : type,
			data : data,
			async : async,
			dataType : dataType,
			success : function (data,textStatus,xmlHttpRequest) { 
				success.call(xmlHttpRequest,data,textStatus,xmlHttpRequest);
			},
			error : function (xmlHttpRequest, textStatus, errorThrown) {
				error.call(xmlHttpRequest,xmlHttpRequest,textStatus,errorThrown);
			},
			complete : function(xmlHttpRequest,textStatus) {
				complete.call(xmlHttpRequest,xmlHttpRequest,textStatus);
			}
		});
	}
})