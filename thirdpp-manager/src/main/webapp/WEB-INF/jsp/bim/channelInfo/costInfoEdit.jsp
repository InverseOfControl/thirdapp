<%@ page language="java" pageEncoding="utf-8"%>
<div class="content-body">
		<div class="search-panel-content">		 
		<form id="costInfoSave" method="post"  action="${path}/bim/channelInfo/costInfoSave">
	 	 	<input id="id" name="id" type="hidden" value="${costInfo.id}" />
		<table  class="search-table">
		<tr>
			<th class="wd-20"><label>第三方支付平台：</label></th>
			<td>
			
			<input   type="text" style="width: 200px" maxlength="50" class="easyui-validatebox" disabled="disabled"	value="${costInfo.thirdTypeName}"/>
			<input id="thirdTypeNo" name="thirdTypeNo"  type="hidden"  value="${costInfo.thirdTypeNo}" />  
			</td>
	    </tr>
	    
	    <tr>
			<th class="wd-20"><label>类型：</label></th>
			<td> <input style="width: 200px" name="costType"  
									class="easyui-combobox" id="costType"
									data-options="editable:false,data:[{'type':'1','text':'固定'},{'type':'2','text':'百分比'} 
									 ],	valueField:'type',textField:'text',value:'${costInfo.costType}'" />									 
			  <span style="color:red">*</span>     
			</td>
	    </tr>
	    
		 <tr name="fixedAmountTR">
			<th class="wd-20"><label>固定金额：</label></th>
			<td><input id="fixedAmount" name="fixedAmount" type="text" style="width: 200px" 
							value="${costInfo.fixedAmount}" maxlength="100"  precision="2"  class="easyui-numberbox"/>
							<span style="color:red">*</span>(元)
			</td>
	   		 </tr>
	   		 <tr name="percentTR">
			<th class="wd-20" ><label>百分比率：</label></th>
			<td><input id="percent" name="percent" type="text" style="width: 200px" 
							value="${costInfo.percent}" maxlength="100" precision="6" class="easyui-numberbox" />
							<span style="color:red;width:100px">* 输入百分比转换的小数</span>
			</td>
	   		 </tr>
	   		 <tr>
			<th class="wd-20"><label>范围：</label></th>
			<td><input name="minAmount" type="text" style="width: 100px" 
							value="${costInfo.minAmount}" maxlength="100" precision="2" class="easyui-numberbox"/>
							<span >(含)</span>
							<span >—</span>
							<input name="maxAmount" type="text" style="width: 100px" 
							value="${costInfo.maxAmount}" maxlength="100" precision="2" class="easyui-numberbox"/>
							<span >(含)</span>
							
			</td>
	   		 </tr>
	   		  <tr name="percentTR">
			<th class="wd-20"><label>是否封顶：</label></th>
			<td> <input style="width: 200px" name="hasLimitAmount"  
									class="easyui-combobox" id="hasLimitAmount"
									data-options="editable:false,data:[{'type':'1','text':'是'},{'type':'2','text':'否'} 
									 ],	valueField:'type',textField:'text',value:'${costInfo.hasLimitAmount}'
									 " />									 
			  <span style="color:red">*</span>     
			</td>
	    </tr>
	   		  <tr name="percentTR">
			<th class="wd-20"><label>封顶金额：</label></th>
			<td><input id="limitAmount" name="limitAmount" type="text" style="width: 200px" 
							value="${costInfo.limitAmount}" maxlength="100" class="easyui-numberbox" />
							<span style="color:red">*</span>
			</td>
	   		 </tr>
	   		 
			</table> 								
		</form>
	</div>							
</div>	
						
<script>

	
	function clearFixData(){//清除固定类型的数据
		$('#fixedAmount').val("");
		$("input[name='fixedAmount']").val("");
	}
	
	function clearPercentData(){//清除百分比类型的数据
		$("input[name='percent']").val("");
		$("input[name='hasLimitAmount']").val("");
		$("input[name='limitAmount']").val("");
		
		$('#percent').val("");
		$('#hasLimitAmount').val("");
		$('#limitAmount').val("");
	}
	
	function checkData(){//清除百分比类型的数据
		var costType = $("input[name='costType']").val();
		var fixedAmount =  $("input[name='fixedAmount']").val();
		var minAmount = $("input[name='minAmount']").val();
		var maxAmount = $("input[name='maxAmount']").val();
		var hasLimitAmount = $("input[name='hasLimitAmount']").val();
		var limitAmount = $("input[name='limitAmount']").val();
		var percent = $("input[name='percent']").val();
		if(!costType){
			return "请选择类型";
		}else if(costType == '1'){
			if(!fixedAmount){
				return "请输入固定金额";
			}
			if(parseFloat(fixedAmount) == 0){
				return "固定金额必须大于0";
			}
			if(!minAmount){
				return "请输入的最低金额";
			}
		}else if(costType == '2'){
			if(!percent){
				return "请输入百分比率";
			}
			if(parseFloat(percent)==0){
				return "百分比必须大于0";
			}
			
			if(!hasLimitAmount){
				return "请选择是否封顶";
			}else if(hasLimitAmount == '1'){
				if(!limitAmount){
					return "请输入封顶金额";
				}else if(parseFloat(limitAmount) == 0){
					return "封顶金额必须大于0";
				}
			}
		}

		if(maxAmount){
			if(parseFloat(maxAmount) == 0){
				return "输入的最高金额有误(金额需大于0)";
			}
			
			/* if(parseInt(minAmount) >= parseInt(maxAmount)) {
				return "输入的金额区间非法";
			} */
			
			if(parseFloat(minAmount) >= parseFloat(maxAmount)) {
				return "输入的金额区间非法";
			}
		}
		if(minAmount){
			if(parseFloat(minAmount) < 0){
				return "输入的最低金额有误(金额需大于0)";
			}
		}
		
	}

	$('#hasLimitAmount').combobox({//封顶
		onLoadSuccess:function(data){
			if(!$('#id').val()){
				$(this).combobox('select',"1");
			}
			var peo = $(this).combobox('getValue');
	         if(peo == '2'){//否
	           $('#limitAmount').attr('disabled',true);
	         }else if(peo == '1'){//是
	           $('#limitAmount').attr('disabled',false);
	         }
		},
        onSelect:function(record){
         var peo = $(this).combobox('getValue');
         if(peo == '2'){//否
           $('#limitAmount').attr('disabled',true);
           $('#limitAmount').val("");
         }else if(peo == '1'){//是
           $('#limitAmount').attr('disabled',false);
         }
        }
     });
	

	$('#costType').combobox({//类型
		onLoadSuccess:function(data){
			if(!$('#id').val()){
				$(this).combobox('select',"1");
			}
			var peo = $(this).combobox('getValue');
	         if(peo == '1'){//固定
	        	 $("tr[name='percentTR']").attr("hidden","hidden"); 
	        	 $("tr[name='fixedAmountTR']").removeAttr("hidden");  
	        	 clearPercentData();
	         }else if(peo == '2'){
	        	 $("tr[name='percentTR']").removeAttr("hidden");  
	        	 $("tr[name='fixedAmountTR']").attr("hidden","hidden");
	        	 clearFixData();
	         }
		},
        onSelect:function(record){
         var peo = $(this).combobox('getValue');
         if(peo == '1'){//固定
        	 $("tr[name='percentTR']").attr("hidden","hidden"); 
        	 $("tr[name='fixedAmountTR']").removeAttr("hidden");  
        	 clearPercentData();
         }else if(peo == '2'){
        	 $("tr[name='percentTR']").removeAttr("hidden");  
        	 $("tr[name='fixedAmountTR']").attr("hidden","hidden");
        	 clearFixData();
         }
        }
});
</script>					