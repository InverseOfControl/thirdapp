$(function(){
	$("#isFlagSelect").select2({  
		placeholder: "----请选择----",
		allowClear: true
	}); 
	
	$("#isFlag").select2({  
		placeholder: "----请选择----",
		allowClear: true
	}); 
	//------------------新增页面-----------------//
 
 
	



});
//------------------列表页面-----------------//

/**
 * 条件查询所有记录
 */
function search(){
	var form = $("#sysAppForm");
	form.submit();
}

/**
 * 查询条件重置
 */
function formReset(){
	$("#sysAppForm input").val('');
	$("#sysAppForm select").select2('val','');
 
}

/**
 * 跳转到修改页面
 */
function editUI(id){
 
	window.location.href = contextPath+"/bim/sysApp/sysAppEditUI/"+id;
}
/**
 * 跳转消息类别页面
 * @param id
 */
function infoCateGoryAppsUI(id,appCode){
	alert("id="+id);
	window.location.href = contextPath+"/bim/sysApp/infoCategoryAppsList/"+id+"/"+appCode;
}
function updateFlag(id,flag){
	
	window.location.href = contextPath+"/bim/sysApp/sysAppUpdateIsFlag/"+id+"/"+flag;
}
function addInfoAppUI(appCode){
	
	window.location.href = contextPath+"/bim/sysApp/infoCategoryAppsEditUI/"+appCode;
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
              
                	
                		var form = $("#resultForm");
                		form.attr("action", contextPath+"/bim/sysApp/infoAppsDelete"); 
                		form.submit();
                	
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
   window.location.href = contextPath+"/bim/sysApp/sysAppList";
}
function cancelInfo() {
	   window.location.href = contextPath+"/bim/sysApp/infoCategoryAppsLists";
	}

