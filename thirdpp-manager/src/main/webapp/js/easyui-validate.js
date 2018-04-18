jQuery(function($){
		$.extend($.fn.validatebox.defaults.rules, {     
	        maxLength: {     
	            validator: function(value, param){     
	                return param[0] >= value.replace(/[^\x00-\xff]/g, '___').length;     
	            },     
	            message: '最大长度不能超过{0}.'    
	        }  
		 
	    });
		$.extend($.fn.validatebox.defaults.rules, {     
	        NumberOrLetter: {     
	            validator: function(value){     
	            	    return  /^[a-z0-9A-Z]+$/i.test($.trim(value));        
	            },     
	            message: '只能输入字母和数字'    
	        }  
		 
	    });
		$.extend($.fn.validatebox.defaults.rules, {     
	        Number: {     
	            validator: function(value){     
	            	    return  /^[0-9]+$/i.test($.trim(value));        
	            },     
	            message: '只能输入数字'    
	        }  
		 
	    });
		$.extend($.fn.validatebox.defaults.rules, {     
	        Money: {     
	            validator: function(value){     
	            	    return   /^([1-9][\d]{0,16}|0)(\.[\d]{1,2})?$/i.test($.trim(value));        
	            },     
	            message: '金额格式有误'    
	        }  
		 
	    });
	});