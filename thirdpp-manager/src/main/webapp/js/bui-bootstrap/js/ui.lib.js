(function($){
	if($.fn.validatebox){
		var validataDefOpt = $.fn.validatebox.defaults;
		validataDefOpt.missingMessage = "此项不能为空";
		validataDefOpt.rules.email.message = "邮件地址不正确";
		validataDefOpt.rules.url.message = "url地址非法";
		validataDefOpt.rules.length.message = "该值介于{0}和{1}之间";
		validataDefOpt.rules.remote.message = "请填写此项";
		validataDefOpt.rules.select.message = "该值不能为空";
	}
	if($.fn.pagination){
		$.fn.pagination.defaults.displayMsg = "显示{from}到{to}条，共{total}条记录";
		$.fn.pagination.defaults.beforePageText = "第";
		$.fn.pagination.defaults.afterPageText = "页, 共{pages}页";
	}
	if($.fn.panel){
		$.fn.panel.defaults.loadingMessage = "加载中，请稍候...";
	}
	if($.fn.datagrid){
		$.fn.datagrid.defaults.loadMsg = "处理中，请稍候...";
		$.fn.datagrid.defaults.noDataMsg = "";
	}
	if($.fn.datebox){
		$.fn.datebox.defaults.currentText = "今天";
		$.fn.datebox.defaults.closeText = "关闭";
		$.fn.datebox.defaults.okText = "确定";
	}
	if ($.messager){
		$.messager.defaults.ok = "确定";
		$.messager.defaults.cancel = "取消";
	}
	if($.fn.calendar){
		$.fn.calendar.defaults.weeks = ["日","一","二","三","四","五","六"];
		$.fn.calendar.defaults.months = ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"];
	}
    $.fn.toggleBar = function(fn1, fn2){
    	var _this = this;
    	var header = this.find("[data-role='toggle-handle']");
    	header.find(">:header").addClass("ui-panel-title").prepend("<i class='icon-angle'></i>");
    	header.attr("title","点击收起");
    	header.css("cursor","pointer").click(function(){
    		var $this = $(this);
	    	if(header.hasClass("panel-active")){
	    		$this.removeClass("panel-active")
	    		.next().slideDown(function(){
	    			header.attr("title","点击收起");
	    			fn2&&fn2.apply(_this,arguments);
	    		});
	    	}
	    	else{
	    		$this.next().slideUp(function(){
	    			$this.addClass("panel-active");
	    			header.attr("title","点击展开");
	    			fn1&&fn1.apply(_this,arguments);
	    		});
	    	}
    	});
    	return this;
    };
    
    $.directForm = function(param){
    	if(typeof param === 'object' && param.action){
    		var ipt;
    		var form = document.createElement("form");
    		form.setAttribute("action", param.action);
    		form.setAttribute("method", "post");
    		delete param.action;
    		var frag = document.createDocumentFragment();
    		for(var i in param){
    			ipt = document.createElement("input");
    			with(ipt){
	    			type = "hidden";
	    			name = i;
	    			value = param[i];
    			}
    			frag.appendChild(ipt);
    		}
    		form.appendChild(frag);
    		form.style.cssText = "position:absolute; visibility:hidden;";
    		document.body.appendChild(form);
    		form.submit();
    	}
    };
    
    $.fn.getFormParam = function(){
    	var jsonData = $(this[0]).serializeArray();
    	var object = {};
    	for(var i = 0; i < jsonData.length; i++){
    		object[jsonData[i].name] = jsonData[i].value;
    	}
    	return object;
    }
    
    $.fn.menuDropDown = function(){
    	this.delegate("li","mouseenter",function(){
    		if($(this).is(".noDropdown")){
    			return;
    		}
    		$(this).addClass("ui-hover")
    		.find(">.dropdown-menu").show();
    	});
    	this.delegate("li","mouseleave",function(){
    		if($(this).is(".noDropdown")){
    			return;
    		}
    		$(this).removeClass("ui-hover")
    		.find(">.dropdown-menu").hide();
    	});
    	
    	return this;
    }
    
    $.fixUI = function( contain ){
	    	if(/msie\s+[67]/i.test(navigator.userAgent)){
	    		var $doc = $(contain || document);
	    		var input = $doc.find("input[type='text'],input[type='password'],textarea, select");
	    		var btn = $doc.find("input[type='button'], input[type='submit'], input[type='reset']");
	    		input.addClass("input-text");
	    		btn.addClass("input-btn");
	    		
	    		if(!this.isUIBinded){
	    			this.isUIBinded = true;
		    		$doc.on("mouseenter", ":input", function(){
		    			if(!this.type || /text|password|textarea/.test(this.type)){
		    				return
		    			}
		    			$(this).addClass("hover");
		    		});
		    		$doc.on("mouseleave", ":input", function(){
		    			if(!this.type || /text|password|textarea/.test(this.type)){
		    				return
		    			}
		    			$(this).removeClass("hover");
		    		});
		    		$doc.on("mousedown",":input", function(){
		    			if(!this.type || /submit|reset|button/.test(this.type) || this.tagName=="BUTTON"){
		    				$(this).addClass("active");
		    			}
		    		});
		    		$doc.on("mouseup",":input", function(){
		    			if(!this.type || /submit|reset|button/.test(this.type) || this.tagName=="BUTTON"){
		    				$(this).removeClass("active");
		    			}
		    		});
		    		$doc.on("focus",":input", function(){
		    			if(!this.type || /text|password|textarea/.test(this.type)){
		    				$(this).addClass("focus");
		    			}
		    		});
		    		$doc.on("blur",":input", function(){
		    			if(!this.type || /text|password|textarea/.test(this.type)){
		    				$(this).removeClass("focus");
		    			}
		    		});
	    		}
	    	}
    	};
    	
    	$.fixUI.isUIBinded = false;
    
    $(function(){
    	$(".toggle-panel").toggleBar();
    	$("#header .ui-menu").menuDropDown();
    	$.fixUI();
    	$.fn.panel.global.onLoad = function(){
			$.fixUI(this);
		};
    });
    
})(jQuery);