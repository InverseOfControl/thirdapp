/**
 * 显示蒙版层，并有进度圈
 */
/**
 * http://localhost:8083/proj
 */
function getRootPath(){
    //获取当前网址，如： http://localhost:8083/proj/meun.jsp
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： proj/meun.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPath = curWwwPath.substring(0, pos);
    //获取带"/"的项目名，如：/proj
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/')+1);
    return(localhostPath + projectName);
}


function showProgress(){
	var progress = "<div class='loading-mask' style='display: none;'></div><div class='loading' style='display: none;'><img src='"+getRootPath()+"/images/progress.gif' /></div>";
	$("body").prepend(progress);
	//遮罩层显示，弹出层显示，定位弹出层位置
   	var wi=$(document).width();
   	var he=$(document).height();
   	$(".loading-mask").css({'width':wi,'height':he});
   	$('.loading').css({'position':'absolute','left':wi/2-20,'top':he/2-150});

	$('.loading-mask').show();
	$('.loading').show();
}


function closeProgress(){
//关闭遮罩层，弹出层
	$('.loading-mask').hide();
	$('.loading').hide();
}