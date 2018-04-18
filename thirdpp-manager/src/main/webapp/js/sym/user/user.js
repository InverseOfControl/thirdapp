$(function(){
	$("#isActiveSelect").select2(); 
	$("#isActive").select2({  
		placeholder: "----请选择----",
		allowClear: true
	}); 
	$("#merchantCodeSelect").select2({  
		placeholder: "----请选择----",
		allowClear: true
	}); 
	
	
	//------------------新增页面-----------------//
	jQuery.validator.addMethod("email", function(value, element) { 
	    var type =  /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;  
	    return this.optional(element) || (type.test(value));  
	}, "邮箱格式不合法"); 
	jQuery.validator.addMethod("phoneNum", function(value, element) { 
		var type =  /(^(\d{3,4}-)?\d{6,8}$)|(^(\d{3,4}-)?\d{6,8}(-\d{1,5})?$)|(\d{11})/;  
		return this.optional(element) || (type.test(value));  
	}, "请输入合法的手机号码"); 
	/**
	 * 表单添加校验功能
	 */
	$("#userSave").validate({
		rules:{
			roleName:{
				required:true
			},
			email:{
				email:true
			},
			phoneNo:{
				phoneNum:true
			},
			selectedRoles:{
				required:true
			}
		},
		errorClass: "help-inline",
		errorElement: "span",
		highlight:function(element, errorClass, validClass) {
			$(element).parents('.control-group').removeClass('success');
			$(element).parents('.control-group').addClass('error');
		},
		unhighlight: function(element, errorClass, validClass) {
			$(element).parents('.control-group').removeClass('error');
			$(element).parents('.control-group').addClass('success');
		}
	});
	
	
});
//------------------列表页面-----------------//

/**
 * 条件查询所有记录
 */
function search(){
	var form = $("#userForm");
	form.submit();
}

/**
 * 查询条件重置
 */
function formReset(){
	$("#userForm input").val('');
	$("#userForm select").select2('val','');
}


/**
 * 跳转到修改页面
 */
function editUI(id){
	window.location.href = contextPath+"/sym/user/userEditUI/"+id;
}

//------------------新增页面-----------------//
/**
 * 取消按钮事件
 */
function cancel() {
   window.location.href = contextPath+"/sym/user/userList";
}

/**
 * 所属角色选择下拉框元素选择事件
 */
function moveToRight(){
	$("#forSelect option:selected").prependTo("#selected");
}
function moveToLeft(){
	$("#selected option:selected").prependTo("#forSelect");
}
function moveAllToRight(){
	$("#forSelect option").prependTo("#selected");
}
function moveAllToLeft(){
	$("#selected option").prependTo("#forSelect");
}

function setSelectedRoles(){
	$("#selected option").attr("selected","selected");
}
