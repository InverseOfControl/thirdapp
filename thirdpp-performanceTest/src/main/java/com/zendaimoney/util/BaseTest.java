/*    */ package com.zendaimoney.util;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import org.testng.Assert;
/*    */ import org.testng.annotations.AfterTest;
/*    */ import org.testng.annotations.BeforeMethod;
/*    */ import org.testng.annotations.BeforeTest;
/*    */ 
/*    */ public class BaseTest extends Assert
/*    */ {
/* 17 */   protected BaseTestHelper baseTestHelper = new BaseTestHelper();
/*    */ 
/*    */   @BeforeTest
/*    */   public void startTest() throws Exception {
/* 21 */     this.baseTestHelper.connectDB();
/*    */   }
/*    */ 
/*    */   @AfterTest
/*    */   public void endTest() throws SQLException {
/* 26 */     this.baseTestHelper.disconnectDB();
/*    */   }
/*    */ 
/*    */   @BeforeMethod(groups={"BusinessCheck"})
/*    */   public void initDB()
/*    */     throws Exception
/*    */   {
/*    */   }
/*    */ }

/* Location:           E:\pressTest\sync_pressScript\thirdpp-performanceTest-0.0.1.jar
 * Qualified Name:     com.zendaimoney.util.BaseTest
 * JD-Core Version:    0.6.2
 */