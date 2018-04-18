<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@page import="com.zdmoney.manager.models.TSysPermission"%>
<!DOCTYPE HTML>
<html>
 <head>
  <title>统一支付管理系统</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <%@ include file="/common/jsCssInclude.jsp"%>
   <%-- <link href="${path}/js/bui-bootstrap/assets/css/dpl-min.css" rel="stylesheet" type="text/css" /> --%>
   <link href="${path}/js/bui-bootstrap/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
   <link href="${path}/js/bui-bootstrap/assets/css/main-min.css" rel="stylesheet" type="text/css" />
 </head>
 <body>
<!--   <div class="header">
      
  </div> -->
   <%-- <div class="content">
    <div class="dl-main-nav">
      <ul id="J_Nav"  class="nav-list ks-clear">
	    <li class="nav-item"><div class="nav-item-inner nav-home">首页</div></li>
        <c:forEach items="${sessionScope.permList}" var="permission">
           <c:if test="${null != sessionScope.permMap[permission.permUrl] && permission.permType==1}">
	         <li class="nav-item"><div class="nav-item-inner">${permission.permName}</div></li>
           </c:if>
        </c:forEach>
      </ul>
    </div>
    <ul id="J_NavContent" class="dl-tab-conten">
       
    </ul>
   </div> --%>
   <div class="easyui-layout" style="" data-options="fit:true">
   			<div data-options="region:'north'" style="height:110px">
				<!-- 头部 -->
				<div data-options="fit:true" style="text-align:center;padding-top:0px;font-size:18px;">
					<div class="header">
						<div class="headercent"> 
						<h1 class="headerlogo"></h1>
							<span class="headerSpan"></span>
							<h2 class="headerlogo_text"></h2>
						</div>
					    <div class="dl-log" style="font-size:14px;" >欢迎您，<span class="dl-log-user">${sessionScope.user.loginUserName}</span>
					       <a href="#" class="dl-log-quit" data-toggle="modal" id="modifyPwd_btn" onclick="modifyPwd()">修改密码</a>
					       <a href="#" class="dl-log-quit" data-toggle="modal" id="clear_btn" onclick="logout()">注    销</a>&nbsp;&nbsp;&nbsp;&nbsp;
					       <span id="clock"></span>
					    </div>
				    </div>
				    
				</div>
			</div>
			<div data-options="region:'south',split:true" style="height:50px;">
				<!-- 尾部 -->
				<div data-options="fit:true" style="text-align:center;padding-top:15px;font-size:14px;">&copy;RIGHT©2013 ZENDAI WEALTHMONEY,ALL RIGHTS RESERVD &nbsp;&nbsp;版权所有：<a href="http://www.zendaimoney.com./">上海证大投资咨询有限公司</a></div>
			</div>
			<!-- 
			右侧部分
			<div data-options="region:'east',split:true" title="ss" style="width:150px;">
				
			</div>
			 -->
			<div data-options="region:'west',split:true" title="我的菜单" style="width:210px;">
			 <!-- 权限 -->
				   <div class="easyui-accordion" data-options="fit:true,animate:true">
				   <%
				   		List<TSysPermission> permList = (List<TSysPermission>)request.getSession().getAttribute("permList");
				   		for (TSysPermission perm : permList) {
				   		 %>
				   			
				   			<div title="<%=perm.getPermName()%>" data-options="iconCls:'pic_1'" style="overflow:auto;padding:3px;">
				   				<ul class="easyui-tree" >
				   				<%
				   					for(TSysPermission childern : perm.getChildren()) {
				   						%>
							   				<li data-options="iconCls:'pic_17',attributes:{'id':'111','url':'${path }<%=childern.getPermUrl()%>'}">
												<span><%=childern.getPermName() %></span>
											</li>
				   						<%
				   					}
				   				%>
				   				</ul>
				   			</div>
				   		<%}%>
				  </div>
				<!-- 左侧部分（菜单） -->
				   <%--  <div class="easyui-accordion" data-options="fit:true,animate:true">
				        <div title="借款管理" data-options="iconCls:'pic_1'" style="overflow:auto;padding:3px;">
				            <ul class="easyui-tree" >
				            	<li data-options="iconCls:'pic_17',attributes:{'id':'111','url':'/credit-web/views/test/myTest.jsp'}">
									<span>EasyUI 示例</span>
								</li>
								<li data-options="iconCls:'pic_2',attributes:{'id':'1','url':'${path }/trade/collect/taskList'}">
									<span>借款申请</span>
								</li>
								<li data-options="iconCls:'pic_3',attributes:{'id':'2','url':'address_b'}">
									<span>合同生成</span>
								</li>
								<li data-options="iconCls:'pic_4',attributes:{'id':'3','url':'address_c'}">
									<span>合同确认</span>
								</li>
							</ul>
				        </div>
				        <div title="系统管理" data-options="iconCls:'pic_5'" style="padding:3px;">
							<ul class="easyui-tree" >
								<li data-options="iconCls:'pic_6',attributes:{'id':'1','url':'address_a'}">
									<span>修改密码</span>
								</li>
								<li data-options="iconCls:'pic_7',attributes:{'id':'2','url':'address_b'}">
									<span>系统日志</span>
								</li>
							</ul>
				        </div>
				        <div title="规则配置" data-options="iconCls:'pic_10'" style="padding:3px;">
							<ul class="easyui-tree" >
								<li data-options="iconCls:'pic_9',attributes:{'id':'1','url':'address_a'}">
									<span>特殊签单设置</span>
								</li>
								<li data-options="iconCls:'pic_10',attributes:{'id':'2','url':'address_b'}">
									<span>还款日设置</span>
								</li>
							</ul>
				        </div>
				        <div title="财务管理" data-options="iconCls:'pic_11'" style="padding:3px;">
							<ul class="easyui-tree" >
								<li data-options="iconCls:'pic_12',attributes:{'id':'1','url':'address_a'}">
									<span>财务审核</span>
								</li>
								<li data-options="iconCls:'pic_13',attributes:{'id':'2','url':'address_b'}">
									<span>财务放款</span>
								</li>
							</ul>
				        </div>
				        <div title="还款管理" data-options="iconCls:'pic_14'" style="padding:3px;">
							<ul class="easyui-tree" >
								<li data-options="iconCls:'pic_15',attributes:{'id':'1','url':'address_a'}">
									<span>还款试算</span>
								</li>
								<li data-options="iconCls:'pic_16',attributes:{'id':'2','url':'address_b'}">
									<span>划扣管理</span>
								</li>
								<li data-options="iconCls:'pic_17',attributes:{'id':'2','url':'address_b'}">
									<span>对应还款</span>
								</li>
							</ul>
				        </div>
				   </div> --%>
				   
				  
			</div>
			<div data-options="region:'center'" title="">
				<!--  中间部分（各模块主区域） -->
				<div id="tabsPanel" class="easyui-tabs" data-options="tools:'#tab-tools',fit:true" style="width:700px;height:220px"></div>
			</div>
		</div>
   
	
  <script>
    //时钟显示
    var clock = new Clock();
	clock.display(document.getElementById("clock"));
	
	/**
	 ** 菜单显示
	**/
    //将权限列表字符串转为json对象
    var permList = eval('('+'${sessionScope.perms}'+')');
    //最终生成的菜单列表json对象
    var config = [{
        id:'menu', 
        homePage : 'home',
        menu:[{
            text:'首页',
            items:[
              {id:'home',text:'首页',href:'${path}/homePage.html'}
            ]
          }]
    }];
    if(permList.length > 0){
       //轮询顶级菜单（如果有三级或三级以上菜单，只需轮询二级菜单）
       for(var i=0;i<permList.length;i++){
    	   var parent = {};
    	   parent['id'] = permList[i].id;
    	   //构建menu
    	   var menus = [];
    	   if(permList[i].children && permList[i].children.length>0){
	    	   var menu = {};
	    	   menu['text'] = permList[i].permName;
	    	   //构建items
	    	   var items = [];
	    	   var son = permList[i].children;
    		   for(var j=0;j<son.length;j++){
    			   var item = {};
    			   item['id'] = son[j].id;
    			   item['text'] = son[j].permName;
    			   item['href'] = '${path}'+son[j].permUrl;
    			   items.push(item);
    		   }
    		   menu['items'] = items;
    	       menus.push(menu);
    	       parent['homePage'] = son[0].id;
    	   }
    	   parent['menu'] = menus;
    	   config.push(parent);
       }
    }
   

    
			function logout() {
				$.messager.confirm('确认','您确认想要注销吗？',function(r){    
				    if (r){    
				    	window.location.href = contextPath + "/zdmoney/logout";
				    }    
				});  
				
			}

			//修改密碼
			function modifyPassword() {
				var form = $("#password_modify_form");
				var newPassword = $("input[name=newPassword]").val();
				var confirmPassword = $("input[name=confirmPassword]").val();
				if (!newPassword || !confirmPassword
						|| newPassword != confirmPassword) {
					$("#error-span").html("输入的密码为空或者两次输入的密码不相等");
				} else {
					form.submit();
				}
			}
			function modifyPwd(){
				var edit_form_id ='#editPwdForm';
				$('<div id="dialog_holder"></div>').dialog({
				    title: '修改密码',
				    width: 800,
				    height: 500,
				    href: '${path}/sym/user/modifyPasswordPage',
				    modal: true,
				    method: "POST",
					onClose: function(){
						$(this).dialog("destroy");
					},
				    buttons: [{
				    	text: "修  改",
				    	handler: function(e){
				    		$('#editPwdForm').form({   
								 url:'${path}/sym/user/modifyPassword',    
									     onSubmit: function(){
									       $.messager.progress(); 
									        // do some check       
									        // return false to prevent submit;    
									     },    
									     success:function(data){
									    	 var obj = eval('(' + data + ')');
									       //do some
									       if(obj.flag==false){
									    	   $.messager.alert('提示',obj.msg);   
									    	   $.messager.progress('close');   
									    	   return;
									       }
									        $.messager.progress('close');
											$('#dialog_holder').dialog('close');
									     }
								});//
								$(".easyui-validatebox").each(function(){
						        	$(this).validatebox({    
						        		novalidate: false   
						        	}); 
						        });
							    //validate and sbumit
							    if($(edit_form_id).form("validate")==true){
								  	$.messager.progress();
									$(edit_form_id).submit();
								};   
				    	}
				    },{
				    	text: "取 消",
				    	handler: function(e){
				    		$(this).dialog("close");
				    	}
				    }]
				});
			}
			
			jQuery(function($){
				
				//菜单树对象
				var treeContainer = $('#treeContainer');
				//Tab选项卡对象
				var tabsPanel = $('#tabsPanel');
				
				//初始首页面板
				tabsPanel.tabs('add',{
					title : '首页',
					content: '<div data-options="fit:true" style="text-align:center;padding-top:30px;font-size:18px;">欢迎使用统一支付管理系统！</div>',
					closable: false
				});
				
				/*$('#RightAccordion').accordion({ //初始化accordion
			        fillSpace:true,
			        fit:true,
			        border:false,
			        animate:false  
			      });
				
				$('#RightAccordion').accordion('add', {
			        title: 'aaaa',
			        content: "<ul id='tree"+1+"' >1</ul>",
			        selected: true,
			        iconCls:'1'
			      });
				
				$('#RightAccordion').accordion('add', {
			        title: 'aaaab',
			        content: "<ul id='tree"+2+"' >2</ul>",
			        selected: true,
			        iconCls:'1'
			      });
				
			    var treeData = [{
			        "id":1,
			        "text":"Folder1",
			        "iconCls":"icon-save",
			        "children":[{
			    		"text":"File1",
			    		"checked":true
			        },{
			    		"text":"Books",
			    		"state":"open",
			    		"attributes":{
			    			"url":"/demo/book/abc",
			    			"price":100
			    		},
			    		"children":[{
			    			"text":"PhotoShop",
			    			"checked":true
			    		},{
			    			"id": 8,
			    			"text":"Sub Bookds",
			    			"state":"closed"
			    		}]
			        }]
			    },{
			        "text":"Languages",
			        "state":"closed",
			        "children":[{
			    		"text":"Java"
			        },{
			    		"text":"C#"
			        }]
			    }];
			    alert(treeData);*/
				$(".easyui-tree").tree({
			        onBeforeExpand:function(node,param){
			          //$("#tree1").tree('options').url = "/@info/Home/GetTreeByEasyui?id=1";
			        },   
			        onClick : function(node){  
			        	if (treeContainer.tree('isLeaf',node.target)) {
							//点击子菜单打开对应的地址
							var attributes = node.attributes;
							if (attributes) {
								var menuId = attributes.id;
								var menuText = node.text;
								var menuUrl = attributes.url;
								if (tabsPanel.tabs('exists',menuText)) {
									tabsPanel.tabs('select',menuText);
								} else {
									var content = '<iframe scrolling="auto" frameborder="0"  src="'+menuUrl+'"  style="width:100%;height:100%;padding:0px;"></iframe>';
									tabsPanel.tabs('add',{
										title : menuText,
										content: content,
										closable: true
									});
								}
							}
						} else {
							//点击父菜单无事件
						}
			        	
			          /*if (node.state == 'closed'){  
			            $(this).tree('expand', node.target);  
			          }else if (node.state == 'open'){  
			            $(this).tree('collapse', node.target);  
			          }else{
			            var tabTitle = node.text;
			            var url = "../../" + node.attributes;
			            var icon = node.iconCls;
			            alert(1);
			            //addTab(tabTitle, url, icon);
			          }*/
			        }
			      });
			})
		</script>
 </body>
</html>

