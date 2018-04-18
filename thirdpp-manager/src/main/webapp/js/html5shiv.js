(function(){
    var aHtml5 = ['article','aside','audio','canvas','command','datagrid','datalist','datatemplate','details','dialog','eventsource','figure','figcaption','footer','header','mark','meter','nav','nest','output','progress','rule','section','source','time','vedio','hgroup'],
    i = aHtml5.length;
	while(i--){
       document.createElement(aHtml5[i]);
    }
	var text = aHtml5.join(",").concat("{display:block;}"),
	style = document.createElement("style");
	style.type = "text/css";
	if(style.styleSheet){
		style.styleSheet.cssText = text;
	}else{
		style.appendChild(document.createTextNode(text));
	}
	document.getElementsByTagName("head")[0].appendChild(style);
})();