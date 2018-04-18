<%@ page language="java" pageEncoding="utf-8"%>
	<div class="content-body"> 
				<div class="search-panel-content">
					<form id="clearNotifyForm" method="post"  
						action="${path}/bim/dictionary/dictionarySave">
						<div class="search-panel-bd">
						<table class="search-table">
								<tr>
										<th class="wd-20"><label>创建时间：</label></th>										
										<td>
											<input type="text" id="clear_begin_date" name="beginTime"  value= "${now}"   class="easyui-datetimebox" style="width:150px;" 
																data-options="required:true,novalidate:true,editable:false" />
											－
											<input type="text" id="clear_end_date" name="endTime"  value= CurentTime2() class="easyui-datetimebox" style="width:150px;" 
																data-options="required:true,novalidate:true,editable:false" /><span style="color:red">*</span>
										</td>
								</tr>
								<tr>
										<th class="wd-20"><label>通知次数>=：</label></th>	
										<td><input type="text" id="notifyCount" name="notifyCount" class="easyui-validatebox"
										value="${notifyCount}" style="width: 220px;" maxlength="1"
										data-options="required:true,novalidate:true,validType:'Number'"   /><span style="color:red">*</span>
										</td>
										 
								</tr>
									<tr>
										<th class="wd-20"><label>业务系统</label></th>	
										
										<td><input id="bizSysNo" class="easyui-combobox" name="bizSysNo" data-options="
										editable:false,valueField: 'id',
										textField: 'text',
										//url是下拉框请求显示的数据
										url:'${path }/enumset/bizSysNoList/allcombobox' " style="width:200px;"/> </td> 
								</tr>
							</table>
						 </div>
					</form>
				</div>
			</div>
<script type="text/javascript">
function CurentTime2()
{ 
    var now = new Date();  
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日 
    var hh = now.getHours();            //时
    var mm = now.getMinutes();          //分
    var ss = now.getSeconds();
    var clock = year + "-";
   
    if(month < 10)
        clock += "0";
   
    clock += month + "-";
   
    if(day < 10)
        clock += "0";
    clock += day ;
    
    clock +=" ";
    
    if(hh<10)
    	clock +="0";
    clock += hh + ":";
    
    if(mm<10)
    	clock +="0";
    clock += mm + ":";
    if(ss<10)
    	clock +="0";
    clock += ss;
    return(clock); 
}

</script>

 

 
