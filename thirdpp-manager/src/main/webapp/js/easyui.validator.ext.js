(function($){
	$.extend($.fn.validatebox.defaults.rules, {
		equals: {
			validator: function(value,param){
				return value == $(param[0]).val();
			},
			message: '该值必须与{1}的值相同'
		},
	 	maxDate: {
            validator: function(value, param){
				var parser = $.fn.datebox.defaults.parser;
                var d1 = parser(param[0]);
                var d2 = parser(value);
                return d2 <= d1;
            },
            message: '选择的日期不能大于{0}'
        },
        minDate: {
            validator: function(value, param){
				return !$.fn.validatebox.defaults.rules.maxDate.validator(value, param);
            },
            message: '选择的日期不能小于{0}'
        },
		mobile: {
			validator: function(value){
				return /^1[3458]\d{9}$/.test(value);
			},
			message: '手机格式不正确'
		},
		tel: {
			validator: function(value){
				return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/.test(value);
			},
			message: '电话格式不正确'
		},
		idcard: {
			validator: function(value){
				return /^[1-9]([0-9]{13}|[0-9]{16})(\d|x)$/.test(value);
			},
			message: '身份证号不正确'
		},
		currency: {
			validator: function(value){
				return /^\d+(\.\d+)?$/.test(value);
			},
			message: '金额格式不正确'
		},
		number: {
			validator: function(value, param){
				var pattern = /^[-\+]?\d+(\.\d+)?$/.exec(value);
				if( !pattern ){
					return false;
				}
				
				if( param[0] != null && pattern[1] && pattern[1].slice(1).length > param[0] ){
					return false;
				}
				
				if( param[1] && value.replace(/[-\+\.]/g,"").length > param[1] ){
					return false;
				}
				return true;
			},
			message: '该项必须为数字'
		},
		double: {
			validator: function(value){
				return $.fn.validatebox.defaults.rules.number.validator(value,[2]);
			},
			message: '此项只能是正负的数字'
		},
		pureNumber: {
			validator: function(value){
				return /^[-]?\d+$/.test(value);
			},
			message: '该项必须为数字'
		},
		zip: {
			validator: function(value){
				return /^[1-9]\d{5}$/.test(value);
			},
			message: '邮政编码格式不正确'
		},
		qq: {
			validator: function(value){
				return /^[1-9]\d{3,9}$/.test(value);
			},
			message: '非法的QQ号码'
		},
		english: {
			validator: function(value){
				return /^[A-Za-z]+$/.test(value);
			},
			message: '此项只能为英文字母'
		},
		chinese: {
			validator: function(value){
				return /^[\u0391-\uFFE5]+$/.test(value);
			},
			message: '此项只能填中文'
		},
		username: {
			validator: function(value){
				return /^([A-Za-z\d]\w){4}$/.test(value);
			},
			message: '不合法的用户名'
		},
		onlychar: {
			validator: function(value){
				return /^[A-Za-z\d]+$/.test(value);
			},
			message: '只能输入字母或数字'
		},
		password: {
			validator: function(value){
				return /^.{6,20}$/.test(value);
			},
			message: '密码必须在6~20位之间'
		},
		unsafe: {
			validator: function(value){
				return !/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value);
			},
			message: '此项填写不安全'
		},
		english: {
			validator: function(value){
				return /^[A-Za-z]+$/.test(value);
			},
			message: '此项只能为英文字母'
		},
		percent: {
			validator: function(value){
				return /^[-\+]?\d+(\.\d+)?%$/.test(value);
			},
			message: '必须写成百分比的形式'
		},
		picture: {
			validator: function(value){
				return /(.*)\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$/.test(value);
			},
			message: '非法图片格式'
		},
		ip: {
			validator: function(value){
				return /^((?:(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))\.){3}(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d))))$/.test(value);
			},
			message: '非法IP地址格式'
		},
		date: {
			validator: function(value){
				return /^\d{4}(\-|\/)(0?[1-9]|1[012])(\-|\/)(0?[1-9]|[12][0-9]|3[01])$/.test(value);
			},
			message: '日期格式错误'
		},
		color: {
			validator: function(value){
				return /^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$/.test(value);
			},
			message: '颜色格式不正确'
		}
	});
})(jQuery);