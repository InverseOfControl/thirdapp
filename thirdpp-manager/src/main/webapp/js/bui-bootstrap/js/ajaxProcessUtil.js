/**
 * 定时器对象
 */
var timer;
/**
 * 定时器定时间隔
 */
var time=1000;

/**
 * 流程请求并获取流程指向的状态消息
 * @param action 开启流程的action
 * @param doProcessNo 流程编号 用于获取消息使用
 * @param $context 展示流程信息的区域(jQuery对象)
 * @param $btn 触发事件的对象(jQuery对象)
 */
function ajaxProcess(action,doProcessNo,$context,$btn,workDay){
	$.ajax({
		url:action,
		data:{"workDay":workDay},
		dataType:"JSON",
		type:"POST",
		async:true,
		success:function(data){
			timer = setInterval(function(){
				ajaxMessage(doProcessNo,$context,$btn);
			},time);
			
			jQuery.messager.show({ 
				title:'温馨提示:', 
				msg:data.message, 
				timeout:3000, 
				showType:'slide'
				}); 
		},
		error:function(data){
			alert("请求超时");
		}
	});
};
/**
 * 流程请求并获取流程指向的状态消息
 * @param action 开启流程的action
 * @param doProcessNo 流程编号 用于获取消息使用
 * @param $context 展示流程信息的区域(jQuery对象)
 * @param $btn 触发事件的对象(jQuery对象)
 */
function ajaxProcessForPgSuite(action,doProcessNo,$context,$btn,$dataGrid){
	$.ajax({
		url:action,
		data:{},
		dataType:"JSON",
		type:"POST",
		async:true,
		success:function(data){
			timer = setInterval(function(){
				ajaxMessageOfSuite(doProcessNo,"",$context,$btn,$dataGrid);
			},time);
			
			jQuery.messager.show({ 
				title:'温馨提示:', 
				msg:data.message, 
				timeout:3000, 
				showType:'slide'
				}); 
		},
		error:function(data){
			alert("请求超时");
		}
	});
};
	
/**
 * ajax请求执行状态消息
 * @param doProcessNo 流程编号 用于获取消息使用
 * @param $context 展示流程信息的区域(jQuery对象)
 * @param $btn 触发事件的对象(jQuery对象)
 */
function ajaxMessage(doProcessNo,$context,$btn){
	$.ajax({
		url:"getMessageAction.action",
		data:{"doProcessNo":doProcessNo},
		dataType:"JSON",
		type:"POST",
		async:true,
		success:function(data){
			var res=eval(data);
			//关闭定时器
			if(res.status=="2"){
				clearInterval(timer);
				jQuery.messager.show({ 
					title:'温馨提示:', 
					msg:'当前流程执行结束', 
					timeout:3000, 
					showType:'slide'
					}); 
				
			}else if(res.status=="0"){
				$btn.removeAttr("disabled");
				$btn.removeAttr("group");
			}
			//把返回 数据放到初始化信息中
			var context="";
			$.each(res.message,function(index,itme){
				var tem = res.message[index];
				if(tem != undefined ){
				//0：正常；1：警示； 2：错误
					
					if(tem.messageType=="1"){
						context+="<p><font color='#660000'>[warning]:"+tem.messageContext+"</font></p>";
					}else if(tem.messageType=="2"){
						context+="<p><font color='red'>[error]:"+tem.messageContext+"</font></p>";
						clearInterval(timer);
					}else {
						context+="<p><font color='green'>[info]:"+tem.messageContext+"</font></p>";
					}
				}
			});
			$context.text("").append(context);
			closeProgress();
			//刷新页面
			location.reload(true);
		},
		error:function(data){
			clearInterval(timer);
			$btn.removeAttr("disabled");
			$btn.removeAttr("group");
			closeProgress();
		}
	});
};


/**
 * ajax请求执行状态消息
 * @param doProcessNo 流程编号 用于获取消息使用
 * @param pgSuiteType 指令类型 在获取message的时候用于确定是否关闭页面的定时器
 * @param $context 展示流程信息的区域(jQuery对象)
 * @param $btn 触发事件的对象(jQuery对象)
 * @param $dataGrid 定时器关闭的时候需要刷新
 */
function ajaxMessageOfSuite(doProcessNo,pgSuiteType,$context,$btn,$dataGrid){
	$.ajax({
		url:"getMessageAction.action",
		data:{"doProcessNo":doProcessNo,"pgSuiteType":pgSuiteType},
		dataType:"JSON",
		type:"POST",
		async:true,
		success:function(data){
			var res=eval(data);
			//关闭定时器
			if(res.status=="2" || res.status=="1"){
				clearInterval(timer);
				$dataGrid.datagrid("reload");
				jQuery.messager.show({ 
					title:'温馨提示:', 
					msg:'当前流程执行结束', 
					timeout:3000, 
					showType:'slide'
					}); 
			}else if(res.status=="0"){
				$btn.removeAttr("disabled");
				$btn.removeAttr("group");
			}
			//把返回 数据放到初始化信息中
			var context="";
			$.each(res.message,function(index,itme){
				//0：正常；1：警示； 2：错误
				var tem = res.message[index];
				if(tem != undefined){
					if(tem.messageType){
						if(tem.messageType=="1"){
							context+="<p><font color='#660000'>[warning]:"+tem.messageContext+"</font></p>";
						}else if(tem.messageType=="2"){
							context+="<p><font color='red'>[error]:"+tem.messageContext+"</font></p>";
							clearInterval(timer);
						}else {
							context+="<p><font color='green'>[info]:"+tem.messageContext+"</font></p>";
						}
					}
				}
			});
			$context.text("").append(context);
			closeProgress();
			//刷新页面
			location.reload(true);
//			var win = $("div.messager-progress").closest("div.messager-body");
//			if (win.length) {
//				win.window("close");
//			}
		},
		error:function(data){
			clearInterval(timer);
			$btn.removeAttr("disabled");
			$btn.removeAttr("group");
			closeProgress();
		}
	});
};


/**
 * 获取消息
 * @param processNo 流程号
 */
function getMessage(processNo,$context){
	$.ajax({
		url:"getMessageAction.action",
		data:{"doProcessNo":processNo},
		dataType:"JSON",
		type:"POST",
		async:true,
		success:function(data){
			var res=eval(data);
			
			//把返回 数据放到初始化信息中
			var context="";
			$.each(res.message,function(index,itme){
				//0：正常；1：警示； 2：错误
				var tem = res.message[index];
				if(tem != undefined){
					if(tem.messageType){
						if(tem.messageType=="1"){
							context+="<p><font color='#660000'>[warning]:"+tem.messageContext+"</font></p>";
						}else if(tem.messageType=="2"){
							context+="<p><font color='red'>[error]:"+tem.messageContext+"</font></p>";
							clearInterval(timer);
						}else {
							context+="<p><font color='green'>[info]:"+tem.messageContext+"</font></p>";
						}
					}
				}
			});
			$context.text("").append(context);
			closeProgress();
		},
		error:function(data){
			clearInterval(timer);
			$btn.removeAttr("disabled");
			$btn.removeAttr("group");
			closeProgress();
		}
	});
	
}
/**
 * 获取数据消息
 * @param processNo 流程号
 */
function getDataMessage(dataflag,$context){
	
	$.ajax({
		url:"getDataMessageAction.action",
		data:{"dataflag":dataflag},
		dataType:"JSON",
		type:"POST",
		async:true,
		success:function(data){
			var res=eval(data);
			
			//把返回 数据放到初始化信息中
			var context="";
			$.each(res.message,function(index,itme){
				//0：正常；1：警示； 2：错误
				var tem = res.message[index];
				if(tem != undefined){
					if(tem.messageType){
						if(tem.messageType=="1"){
							context+="<p><font color='#660000'>[warning]:"+tem.messageContext+"</font></p>";
						}else if(tem.messageType=="2"){
							context+="<p><font color='red'>[error]:"+tem.messageContext+"</font></p>";
							clearInterval(timer);
						}else {
							context+="<p><font color='green'>[info]:"+tem.messageContext+"</font></p>";
						}
					}
				}
			});
			$context.text("").append(context);
			closeProgress();
		},
		error:function(data){
			clearInterval(timer);
			
			closeProgress();
		}
	});
	
}

/**
 * 数据操作请求并获取流程指向的状态消息
 * @param action 开启流程的action
 * @param doProcessNo 流程编号 用于获取消息使用
 * @param $context 展示流程信息的区域(jQuery对象)
 * @param $btn 触发事件的对象(jQuery对象)
 */
function ajaxDataOperate(action,dataflag,$context,$btn){
	$.ajax({
		url:action,
		data:{"dataflag":dataflag},
		dataType:"JSON",
		type:"POST",
		async:true,
		success:function(data){
			clearInterval(timer);
			ajaxDataMessage(dataflag,$context,$btn)
			$btn.removeAttr("disabled");
			$btn.removeAttr("group");
			jQuery.messager.show({ 
				title:'温馨提示:', 
				msg:data.message, 
				timeout:3000, 
				showType:'slide'
				}); 
		},
		error:function(data){
			
			clearInterval(timer);
			ajaxDataMessage(dataflag,$context,$btn)
			$btn.removeAttr("disabled");
			$btn.removeAttr("group");
		}
	});
	timer = setInterval(function(){
		ajaxDataMessage(dataflag,$context,$btn);
	},time);
	
};
/**
 * 根据工作日数据操作请求并获取流程指向的状态消息
 * @param action 开启流程的action
 * @param doProcessNo 流程编号 用于获取消息使用
 * @param $context 展示流程信息的区域(jQuery对象)
 * @param $btn 触发事件的对象(jQuery对象)
 * @param workDay 工作日
 */
function ajaxDataOperateByWorkDay(action,dataflag,$context,$btn,workDay){
	$.ajax({
		url:action,
		data:{"dataflag":dataflag,"workDay":workDay},
		dataType:"JSON",
		type:"POST",
		async:true,
		success:function(data){
			clearInterval(timer);
			ajaxDataMessage(dataflag,$context,$btn)
			$btn.removeAttr("disabled");
			$btn.removeAttr("group");
			jQuery.messager.show({ 
				title:'温馨提示:', 
				msg:data.message, 
				timeout:3000, 
				showType:'slide'
				}); 
		},
		error:function(data){
			clearInterval(timer);
			ajaxDataMessage(dataflag,$context,$btn)
			$btn.removeAttr("disabled");
			$btn.removeAttr("group");
		}
	});
	timer = setInterval(function(){
		ajaxDataMessage(dataflag,$context,$btn);
	},time);
	
};
/**
 * ajax请求执行状态消息
 * @param doProcessNo 流程编号 用于获取消息使用
 * @param $context 展示流程信息的区域(jQuery对象)
 * @param $btn 触发事件的对象(jQuery对象)
 */
function ajaxDataMessage(dataflag,$context,$btn){
	$.ajax({
		url:"getDataMessageAction.action",
		data:{"dataflag":dataflag},
		dataType:"JSON",
		type:"POST",
		async:true,
		success:function(data){
			var res=eval(data);
			
			//把返回 数据放到初始化信息中
			var context="";
			$.each(res.message,function(index,itme){
				var tem = res.message[index];
				if(tem != undefined ){
				//0：正常；1：警示； 2：错误
					
					if(tem.messageType=="1"){
						context+="<p><font color='#660000'>[warning]:"+tem.messageContext+"</font></p>";
					}else if(tem.messageType=="2"){
						context+="<p><font color='red'>[error]:"+tem.messageContext+"</font></p>";
						clearInterval(timer);
					}else {
						context+="<p><font color='green'>[info]:"+tem.messageContext+"</font></p>";
					}
				}
			});
			$context.text("").append(context);
			closeProgress();
			
		},
		error:function(data){
			clearInterval(timer);
			$btn.removeAttr("disabled");
			$btn.removeAttr("group");
			closeProgress();
		}
	});
};

function beforeJump(processNo,callBack){
	jQuery.messager.confirm('提示:','确认要执行该流程吗 ?',function(event){ 
		if(event){ 
			callBack();
		}
	}); 
}