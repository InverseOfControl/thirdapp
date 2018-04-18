/**
 * 省市级联代码
 * json/basedata.json为数据源
 */

/**
 * 初始化省级和市级的元素
 * @param privObj 省级的jquery元素
 * @param cityObj 市级的jquery元素
 * 
 * 调用：
 * 		jQuery(function($){privInit( $("#OpenPriv") , $("#OpenCity") );});
 */
function privInit( privObj , cityObj ){
	privObj.combobox(
			{
				valueField: 'name',    
			    textField: 'name' ,
				data: getProvinceJson()
			}
	);
	
	privObj.combobox(
			{
				onSelect : function(record)
				{
					var json = getCityJsonBy( record.name );
					cityObj.combobox(
							{
								valueField:'name' ,    
							    textField:'name' ,
								data: json ,
								onLoadSuccess:function(){
									cityObj.combobox( 'setValue' , json[0].name ) ;
								}
							}		
					);
				}
			}
	);
	
	var provinceName = privObj.val();
	if( provinceName != undefined  || provinceName != "" ){
		cityObj.combobox(
				{
					valueField:'name' ,    
				    textField:'name' ,
					data: getCityJsonBy( provinceName )
				}		
		);
	}
}


/**
 * 获取省份的JSON
 */
function getProvinceJson( )
{
	return arrCity;
}

function getCityJsonBy( provinceName ){
	var json ;
	$.each( arrCity , function( i , tem )
			{
				if( tem.name == provinceName )
				{
					if( tem.sub )
					{
						console.log( tem.sub );
						json = tem.sub;
						return false;
					}
				}
				
			}
	);
	return json;
}
