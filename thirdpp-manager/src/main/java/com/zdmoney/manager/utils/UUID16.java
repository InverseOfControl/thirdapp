package com.zdmoney.manager.utils;

import java.util.UUID;

/**
 * 16位随机UUID生成工具
 *
 */
public class UUID16 {

	
	public static void main(String[] args){
		
		
	
		/* 取消注释可输出详细计算信息
		 * //UUID
		UUID uuid = UUID.randomUUID();
        String previous = uuid.toString().replaceAll("-", "").substring(0,16);
        String next = uuid.toString().replaceAll("-", "").substring(16,32);
        System.out.println(previous);
        System.out.println(next);
        
        byte[] a = hexStringToBytes(previous);
        byte[] b = hexStringToBytes(next);
        
        StringBuilder ss  =new StringBuilder();
        for(int i=0;i<8;i++){
        	Integer ddfd = a[i]^b[i];
        	System.out.print(a[i]+"^");
        	System.out.print(b[i]+"=");
        	byte c1 = ddfd.byteValue();
        	
        	System.out.print(c1+"   ");
        	

        	System.out.print(Integer.toHexString(0xFF & a[i])+"^");
        	System.out.print(Integer.toHexString(0xFF & b[i])+"=");
        	System.out.print(Integer.toHexString(0xFF & c1));
        	System.out.println();
        	
        	String dd = Integer.toHexString(0xFF & c1);
        	ss.append(dd.length()<2?"0"+dd:dd);
        }
        System.out.println(ss.toString());*/
		
		System.out.println(createRandomUUID16());
        
        
	}
	
	public static String createRandomUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 生成随机uuid，将前16位和后16位做异或操作
	 * @return
	 */
	public static String createRandomUUID16(){
		String uuid = createRandomUUID();
        byte[] previous = hexStringToBytes(uuid.substring(0,16));
        byte[] next = hexStringToBytes(uuid.substring(16,32));
        StringBuilder uuid16  =new StringBuilder();
        for(int i=0;i<8;i++){
        	Integer byteValue = previous[i]^next[i];        	
        	String byteValueHex = Integer.toHexString(0xFF & byteValue);
        	uuid16.append(byteValueHex.length()<2?("0"+byteValueHex):byteValueHex);
        }
        return uuid16.toString();
        
	}
	
	
   /**
    * Convert hex string to byte[]
    * @param hexString the hex string
    * @return byte[]
    */
   public static byte[] hexStringToBytes(String hexString) {
       if (hexString == null || hexString.equals("")) {
           return null;
       }
       hexString = hexString.toUpperCase();
       int length = hexString.length() / 2;
       char[] hexChars = hexString.toCharArray();
       byte[] d = new byte[length];
       for (int i = 0; i < length; i++) {
           int pos = i * 2;
           d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
       }
       return d;
   }
   
   /**
    * Convert char to byte
    * @param c char
    * @return byte
    */
    private static byte charToByte(char c) {
       return (byte) "0123456789ABCDEF".indexOf(c);
   }
   
}
