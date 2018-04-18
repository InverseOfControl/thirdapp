/*    */ package com.zendaimoney.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.sql.Connection;
/*    */ import java.sql.DriverManager;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Statement;
/*    */ import java.util.Properties;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class BaseTestHelper
/*    */ {
/* 26 */   private static String JDBC_URL = null;
/* 27 */   private static String DRIVER = null;
/* 28 */   private static String DB_USER = null;
/* 29 */   private static String DB_PWD = null;
/* 30 */   protected static Connection conn = null;
/*    */ 
/* 37 */   private static Logger logger = Logger.getLogger(BaseTestHelper.class);
/*    */ 
/*    */   static {
/* 40 */     Properties properties = new Properties();
/* 41 */     InputStream in = BaseTestHelper.class
/* 42 */       .getResourceAsStream("/application.properties");
/*    */     try {
/* 44 */       properties.load(in);
/* 45 */       JDBC_URL = properties.getProperty("jdbc.url");
/* 46 */       DRIVER = properties.getProperty("jdbc.driver");
/* 47 */       DB_USER = properties.getProperty("jdbc.username");
/* 48 */       DB_PWD = properties.getProperty("jdbc.password");
/* 49 */       logger.info("JDBC URL:" + JDBC_URL);
/*    */     } catch (IOException e) {
/* 51 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void connectDB() throws ClassNotFoundException, SQLException {
/* 56 */     if (conn == null) {
/* 57 */       Class.forName(DRIVER);
/* 58 */       conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PWD);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void disconnectDB() throws SQLException {
/* 63 */     if (conn != null)
/* 64 */       conn.close();
/*    */   }
/*    */ 
/*    */   public void prepareDB() throws Exception
/*    */   {
/* 69 */     Statement stmt = conn.createStatement();
/* 70 */     stmt.execute("TRUNCATE TABLE tpp_trade_t_request");
/* 71 */     stmt.execute("TRUNCATE TABLE tpp_trade_t_collect_task");
/* 72 */     stmt.execute("TRUNCATE TABLE tpp_trade_t_collect_info");
/* 73 */     stmt.execute("TRUNCATE TABLE tpp_channel_t_request");
/* 74 */     stmt.execute("TRUNCATE TABLE tpp_channel_t_message");
/* 75 */     stmt.execute("TRUNCATE TABLE tpp_channel_trade_result");
/* 76 */     stmt.execute("TRUNCATE TABLE tpp_trade_t_notify");
/*    */ 
/* 78 */     stmt.close();
/*    */   }
/*    */ }

/* Location:           E:\pressTest\sync_pressScript\thirdpp-performanceTest-0.0.1.jar
 * Qualified Name:     com.zendaimoney.util.BaseTestHelper
 * JD-Core Version:    0.6.2
 */