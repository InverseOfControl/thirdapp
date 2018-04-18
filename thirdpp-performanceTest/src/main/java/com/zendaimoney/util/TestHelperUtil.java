/*     */ package com.zendaimoney.util;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ 
/*     */ public class TestHelperUtil extends BaseTestHelper
/*     */ {
/*     */   public static String randomOrderNumber3()
/*     */   {
/*  24 */     String orderNumber = "";
/*  25 */     int orderNumberLength = 10;
/*  26 */     for (int i = 0; i < orderNumberLength; i++) {
/*  27 */       orderNumber = orderNumber + (int)(9.0D * Math.random() + 1.0D);
/*     */     }
/*  29 */     return orderNumber;
/*     */   }
/*     */ 
   public static String randomOrderNumber2(int length)
   {
	   String orderNumber = "";
	   for (int i = 0; i < length; i++) {
		   orderNumber = orderNumber + (int)(9.0D * Math.random() + 1.0D);
	   }
	   return orderNumber;
   }
	public static String randomOrderNumber() {
		String orderNumber = "";
		int orderNumberLength = 10;
		for (int i = 0; i < orderNumberLength; i++) {
			orderNumber += (int) (9 * (Math.random()) + 1);
		}
		System.out.println(orderNumber);
		return orderNumber;
	}
/*     */ 
/*     */   public static boolean isDataTableContain(String tableName, String columnName, String condition, String assertString)
/*     */     throws Exception
/*     */   {
/* 104 */     boolean result = false;
/*     */ 
/* 106 */     Statement stmt = conn.createStatement();
/* 107 */     String queryString = 
/* 108 */       "SELECT * FROM " + tableName + " WHERE " + condition;
/* 109 */     ResultSet rs = stmt.executeQuery(queryString);
/* 110 */     rs.next();
/* 111 */     result = rs.getString(columnName).contains(assertString);
/*     */ 
/* 113 */     rs.close();
/* 114 */     return result;
/*     */   }
/*     */ 
/*     */   public static boolean isDataTableEquals(String tableName, String columnName, String condition, String assertString)
/*     */     throws Exception
/*     */   {
/* 130 */     boolean result = false;
/*     */ 
/* 132 */     Statement stmt = conn.createStatement();
/* 133 */     String queryString = 
/* 134 */       "SELECT * FROM " + tableName + " WHERE " + condition;
/* 135 */     ResultSet rs = stmt.executeQuery(queryString);
/* 136 */     rs.next();
/* 137 */     result = rs.getString(columnName).equals(assertString);
/*     */ 
/* 139 */     rs.close();
/* 140 */     return result;
/*     */   }
/*     */ 
/*     */   public static String getColumnFromTable(String tableName, String columnName)
/*     */     throws Exception
/*     */   {
/* 154 */     String result = "";
/*     */ 
/* 156 */     Statement stmt = conn.createStatement();
/* 157 */     ResultSet rs = stmt.executeQuery("SELECT " + columnName + " FROM " + 
/* 158 */       tableName);
/* 159 */     rs.next();
/* 160 */     result = rs.getString(columnName);
/*     */ 
/* 162 */     rs.close();
/* 163 */     return result;
/*     */   }
/*     */ }

/* Location:           E:\pressTest\sync_pressScript\thirdpp-performanceTest-0.0.1.jar
 * Qualified Name:     com.zendaimoney.util.TestHelperUtil
 * JD-Core Version:    0.6.2
 */