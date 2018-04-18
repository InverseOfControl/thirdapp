$(function(){
	$("#permTypeSelect").select2(); 
	$("#permType").select2({  
		placeholder: "----请选择----",
		allowClear: true
	}); 
	$("#parentIdSelect").select2({  
		placeholder: "----请选择----",
		allowClear: true
	}); 
	
	//------------------新增页面-----------------//
	/**
	 * 如果权限类型选择的是菜单，权限位置必填
	 */
	$("#permTypeSelect").change(function(){
		var value = $(this).val();
		if(value==1){
			$("input[name=position]").rules("add",{required:true});
		}else{
			$("input[name=position]").rules("remove","required");
		}
	});
	
	/**
	 * 表单添加校验功能
	 */
	$("#permSave").validate({
		rules:{
			permName:{
				required:true
			},
			permUrl:{
				required:true
			},
			position:{
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
	
	var permType = $("#permTypeSelect").val();
	if(permType == 1){
		$("input[name=position]").rules("add",{required:true});
	}else{
		$("input[name=position]").rules("remove","required");
	}
});
//------------------列表页面-----------------//

/**
 * 条件查询所有记录
 */
function search(){
	var form = $("#permForm");
	form.submit();
}

/**
 * 查询条件重置
 */
function formReset(){
	$("#permForm input").val('');
	$("#permForm select").select2('val','');
}

/**
 * 跳转到修改页面
 */
function editUI(id){
	window.location.href = contextPath+"/sym/permission/permEditUI/"+id;
}

/**
 * 批量删除
 */
function deleteList(){
	var checkedSize = $('input[name="subCheckBox"]:checked').size();
	if(checkedSize == 0){
		 $.teninedialog({
             title:'系统提示',
             content:'未选中任何记录！'
         });
	}else{
		$.teninedialog({
            title:'系统提示',
            content:'确定要删除选中的所有记录？',
            otherButtons:["确定","取消"],
            showCloseButton:false,//是否显示关闭按钮
            otherButtonStyles:['btn-primary'],
            clickButton:function(sender,modal,index){
                if(index == 0){
                	var count = getPermUsedCount();
                	if(count>0){
                		openTeninedialog("选择的权限已分配给角色或存在子权限，请重新选择");
                	}else{
                		var form = $("#resultForm");
                		form.attr("action", contextPath+"/sym/permission/permsDelete"); 
                		form.submit();
                	}
                }
                $(this).closeDialog(modal);
            }
        });
	}
}

/**
 * 查询角色(列表)中已分配给用户的数量
 */
function getPermUsedCount(){
	var permIds = '';
	$("input[name='subCheckBox']:checked").each(function(){
		permIds += $(this).attr('value') + ",";
	});
	permIds = permIds.substring(0, permIds.length-1);
	var count = 0;
	$.ajax({
        type: "post",
        url: contextPath+"/sym/permission/getPermUsedCount",
        data: {"permIds":permIds},
        dataType: "json",
        async: false,
        success: function(data){
            if(data && data>0){
            	count = data;
            }
        }
    });
	return count;
};

//------------------新增页面-----------------//
/**
 * 取消按钮事件
 */
function cancel() {
   window.location.href = contextPath+"/sym/permission/permList";
}
