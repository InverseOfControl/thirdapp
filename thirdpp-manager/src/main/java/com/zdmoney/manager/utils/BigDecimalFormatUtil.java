package com.zdmoney.manager.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BigDecimalFormatUtil {
	/**
	 * 保留两位精度
	 * @param money
	 * @return
	 */
	public static String bigDeclimalToString(BigDecimal money){
		DecimalFormat df1 = new DecimalFormat("#0.00");//两位精度
		if(money == null){
			BigDecimal zero = new BigDecimal( "0.00" ) ;
			return df1.format(zero);
		}
		return df1.format(money);
	}
	
	/**
	 * @param money ：123456.78
	 * @return +-123,,456.78
	 */
	public static String switchMoneyFormat( BigDecimal money ){
		if( money == null ){
			money = new BigDecimal("0.00");
		}
		money = new BigDecimal( bigDeclimalToString(money) );
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		String temStr = nf.format( money ) ;
		return  temStr.indexOf('.') < 0 ? temStr+".00"  : temStr ;
		
	}
}
