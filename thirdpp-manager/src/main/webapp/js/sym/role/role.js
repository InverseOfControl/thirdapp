/*$(function(){
	$("#isActiveSelect").select2(); 
	$("#isActive").select2({  
		placeholder: "----请选择----",
		allowClear: true
	}); 
	
	//------------------新增页面-----------------//
	
	*//**
	 * 表单添加校验功能
	 *//*
	$("#roleSave").validate({
		rules:{
			roleName:{
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
	
});*/
//------------------列表页面-----------------//

/**
 * 条件查询所有记录
 */
function search(){
	var form = $("#roleForm");
	form.submit();
}

/**
 * 查询条件重置
 */
function formReset(){
	$("#roleForm input").val('');
	$("#roleForm select").select2('val','');
}

/**
 * 跳转到修改页面
 */
function editUI(id){
	window.location.href = contextPath+"/sym/role/roleEditUI/"+id;
}

/**
 * 分配权限
 * @param id
 */
function setPermission(roleId){
	$("#tree-form input[name=roleId]").val(roleId);
	//查询当前角色的所有权限
	$.ajax({
        type: "POST",
        url: contextPath+"/sym/role/getPermissionByRole",
        data: {"roleId":roleId},
        dataType: "json",
        async: false,
        success: function(data){
        	if(data && data.permList && data.permList.length>0){
        		var html = '';
        		//拼接父节点
        		jQuery.each(data.permList, function(i,item){
        			html += "<li class='parent_li'>";
        			if(item.children.length>0){
        				html += "<span "+(item.permType=='2'?'class=func-node':'')+"><input value='"+item.id+"' type='checkbox' "+(item.isOwn=='1'?'checked':'')+"/><span>"+item.permName+"<i class='icon-minus-sign'></i></span></span>";
        				html += "<ul>";
        				//拼装子节点
        				jQuery.each(item.children, function(j,child){
        					html += "<li class='parent_li'>";
        					if(child.children.length>0){
        						html += "<span "+(child.permType=='2'?'class=func-node':'')+"><input value='"+child.id+"' type='checkbox' "+(child.isOwn=='1'?'checked':'')+"/><span>"+child.permName+"<i class='icon-minus-sign'></i></span></span>";
        						html += "<ul>";
        						//拼装叶子节点
        						jQuery.each(child.children, function(i,leaf){
        							html += "<li>";
                					html += "<span "+(leaf.permType=='2'?'class=func-node':'')+"><input value='"+leaf.id+"' type='checkbox' "+(leaf.isOwn=='1'?'checked':'')+"/><span>"+leaf.permName+"<i class='icon-leaf'></i></span></span>";
                					html += "</li>";
        						});
        						html += "</ul>";
        					}else{
        						html += "<span "+(child.permType=='2'?'class=func-node':'')+"><input value='"+child.id+"' type='checkbox' "+(child.isOwn=='1'?'checked':'')+"/><span>"+child.permName+"<i class='icon-leaf'></i></span></span>";
        					}
        					html += "</li>";
        				});
        				html += "</ul>";
        			}else{
        				html += "<span "+(item.permType=='2'?'class=func-node':'')+"><input value='"+item.id+"' type='checkbox' "+(item.isOwn=='1'?'checked':'')+"/><span>"+item.permName+"<i class='icon-leaf'></span></span>";
        			}
        			html += "</li>";
        		}); 
        		$(".ul-root").html(html);
        		addNodeClickEvent();
        		addCheckBoxSelectEvent();
        		$("#set-permission-event").modal().show();
        	}
        }
    });
}

/**
 * 给树节点添加展开、收缩事件
 */
function addNodeClickEvent(){
	$('.tree li.parent_li > span span').on('click', function (e) {
        var children = $(this).parent('span').next("ul").find(' > li');
        if (children.is(":visible")) {
            children.hide('fast');
            $(this).find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
        } else {
            children.show('fast');
            $(this).find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
        }
        e.stopPropagation();
    });
}

/**
 * 权限树复选框全选、全不选
 */
function addCheckBoxSelectEvent(){
	$('.tree li > span input').on('click', function (e) {
		$(this).parent("span").next("ul").find(' > li').find('input').attr("checked",this.checked); 
		e.stopPropagation();
	});
}

/**
 * 保存分配的权限
 */
function savePersessions(){
	var permIds='';
	var checked=$("#tree-form input[type='checkbox']:checked").val([]);
	for(var i=0;i<checked.length;i++){
		permIds += checked[i].value+",";
	}
	$("#tree-form input[name='permIds']").val(permIds.substring(0,permIds.length-1));
	$("#tree-form").submit();
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
                	var count = getRoleUsedCount();
                	if(count>0){
                		openTeninedialog("选择的角色已分配给用户，请重新选择");
                	}else{
                		var form = $("#resultForm");
                		form.attr("action", contextPath+"/sym/role/rolesDelete"); 
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
function getRoleUsedCount(){
	var roleIds = '';
	$("input[name='subCheckBox']:checked").each(function(){
	     roleIds += $(this).attr('value') + ",";
	});
	roleIds = roleIds.substring(0, roleIds.length-1);
	var count = 0;
	$.ajax({
        type: "post",
        url: contextPath+"/sym/role/getRoleUsedCount",
        data: {"roleIds":roleIds},
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
   window.location.href = contextPath+"/sym/role/roleList";
}
