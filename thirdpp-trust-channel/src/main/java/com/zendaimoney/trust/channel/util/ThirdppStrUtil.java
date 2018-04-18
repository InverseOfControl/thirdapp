package com.zendaimoney.trust.channel.util;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * TPP2.0字符串工具类
 * @author mencius
 *
 */
public class ThirdppStrUtil {

    /**
     * 根据Unicode编码完美的判断中文汉字和符号
     * @param c
     * @return
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
 
    /**
     * 完整的判断中文汉字和符号
     * @param strName
     * @return
     */
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }
 
    /**
     * 只能判断部分CJK字符（CJK统一汉字）
     * @param str
     * @return
     */
    public static boolean isChineseByREG(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str.trim()).find();
    }
 
    /**
     * 只能判断部分CJK字符（CJK统一汉字）
     * @param str
     * @return
     */
    public static boolean isChineseByName(String str) {
        if (str == null) {
            return false;
        }
        // 大小写不同：\\p 表示包含，\\P 表示不包含
        // \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
        String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
        Pattern pattern = Pattern.compile(reg);
        return pattern.matcher(str.trim()).find();
    }
    
    /**
     * 获取字符串内中文个数
     * @param str
     * @return count
     */
    public static int lengthOfChinese (String str) {
    	
    	// 字符串是否为空
    	if (StringUtils.isBlank(str)) {
    		return 0;
    	} else {
    		
    		// 字符串内是否包含中文
    		if (isChinese(str)) {
    			
    			// 中文个数统计
    			int count = 0;
    			// 将字符串转换为字符数组
    			char[] ch = str.toCharArray();
    			// 遍历
    			for (int i = 0; i < ch.length; i++) {
    				char c = ch[i];
    				
    				// 判断是否为中文
    				if (isChinese(c))
    					// 是中文，统计加一
    					count ++;
    			} 
    			return count;
    		} else {
    			return 0;
    		}
    	}
    }
    
    public static void main(String[] args) {
//      String[] strArr = new String[] { "www.micmiu.com", "!@#$%^&*()_+{}[]|\"'?/:;<>,.", "！￥……（）——：；“”‘’《》，。？、", "不要啊", "やめて", "韩佳人", "???" };
//      for (String str : strArr) {
//          System.out.println("===========> 测试字符串：" + str);
//          System.out.println("正则判断结果：" + isChineseByREG(str) + " -- " + isChineseByName(str));
//          System.out.println("Unicode判断结果 ：" + isChinese(str));
//          System.out.println("详细判断列表：");
//          char[] ch = str.toCharArray();
//          for (int i = 0; i < ch.length; i++) {
//              char c = ch[i];
//              System.out.println(c + " --> " + (isChinese(c) ? "是" : "否"));
//          }
//      }
		
		
		String str = "0";
//		String str = "abc状defg";
		System.out.println(isChinese(str));
		
		System.out.println(lengthOfChinese(str));
    }
}
