package com.zendaimoney.thirdpp.trade.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**   
  *   由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精   
  *   确的浮点数运算，包括加减乘除和四舍五入。   
  */

public class Arith {

    //默认除法运算精度   
    private static final int    DEF_DIV_SCALE     = 10;
    private static final String DECIMAL_DIV       = "##,###.00"; //格式
    private static final String DECIMAL_DIV_SCALE = "0.00";     //格式

    //这个类不能实例化   
    private Arith() {
    }

    /**   
      *   提供精确的加法运算。   
      *   @param   v1   被加数   
      *   @param   v2   加数   
      *   @return   两个参数的和   
      */

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**   
      *   提供精确的减法运算。   
      *   @param   v1   被减数   
      *   @param   v2   减数   
      *   @return   两个参数的差   
      */

    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**   
      *   提供精确的乘法运算。   
      *   @param   v1   被乘数   
      *   @param   v2   乘数   
      *   @return   两个参数的积   
      */

    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 保留最后2位小数，不进行四舍五入
     * @param finalMoney
     * @return
     */
    public static String moneyTo2(double finalMoney) {
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(2);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(finalMoney);
    }

    /**   
      *   提供（相对）精确的除法运算，当发生除不尽的情况时，精确到   
      *   小数点以后10位，以后的数字四舍五入。   
      *   @param   v1   被除数   
      *   @param   v2   除数   
      *   @return   两个参数的商   
      */

    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**   
      *   提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指   
      *   定精度，以后的数字四舍五入。   
      *   @param   v1   被除数   
      *   @param   v2   除数   
      *   @param   scale   表示表示需要精确到小数点以后几位。   
      *   @return   两个参数的商   
      */

    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                "The   scale   must   be   a   positive   integer   or   zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**   
      *   提供精确的小数位四舍五入处理。   
      *   @param   v   需要四舍五入的数字   
      *   @param   scale   小数点后保留几位   
      *   @return   四舍五入后的结果   
      */

    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                "The   scale   must   be   a   positive   integer   or   zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 精确到小数点0.00
     * 
     * @param number
     * @return
     */
    public static String diverssion(Double number) {
        DecimalFormat decfmt = new DecimalFormat("##0.00");
        return decfmt.format(number);
    }

    public static String roundNumber(double dir) {

        //BigDecimal d1=new BigDecimal(dir);
        String str = null;
        if (dir >= 10000) {
            str = String.valueOf(dir + "");
            if (str.indexOf(".") != -1) {
                str = str.substring(0, str.indexOf("."));
            }
            String ss = str.substring(0, str.length() - 4);
            String dd = dir + "";

            return "<span class=\"red\">" + ss + "</span>" + dd.substring(ss.length(), dd.length());
        } else {
            return dir + "";
        }
    }

    /**
     * 两数相除*100 保留一位, 不进行四舍五入
     */
    public static double getClientMargin_test(Double clientPrice, Double fprice) {
        if (clientPrice != null && fprice != null && fprice > 0) {
            Double re = clientPrice / fprice * 100;
            DecimalFormat formater = new DecimalFormat();
            formater.setMaximumFractionDigits(1);
            formater.setGroupingSize(0);
            formater.setRoundingMode(RoundingMode.FLOOR);
            return Double.parseDouble(formater.format(re));
        }
        return 0;
    }

    /**
     * 把Long类型  金额 分格式化元 按指定格式化
     * @param str
     * @return
     */
    public static String longToDecimalFormat(Long str) {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern(DECIMAL_DIV);
        if (str.toString().length() <= 2) //金额长度为2位，使用DECIMAL_DIV_SCALE格式化
            myformat.applyPattern(DECIMAL_DIV_SCALE);
        //double dd = (double) str;
        return myformat.format(Double.valueOf(str) / 100);
    }

    /**
     * 两数相除*100 保留一位, 不进行四舍五入
     */
    public static double getClientMargin(Double clientPrice, Double fprice) {
        if (clientPrice != null && fprice != null && fprice > 0) {
            Double re = clientPrice / fprice * 1000;
            int rep = re.intValue();
            return rep / 10.0;
        }
        return 0;
    }

    public static double getBigDecimal(double v) {

        BigDecimal bd = new BigDecimal(v + "").setScale(0, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

};
