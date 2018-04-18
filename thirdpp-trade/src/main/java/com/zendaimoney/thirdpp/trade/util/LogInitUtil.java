package com.zendaimoney.thirdpp.trade.util;

import java.io.File;

import org.apache.log4j.PropertyConfigurator;

public class LogInitUtil {

	public static String confPath = null;

	/**
	 * log4j文件自动加载配置
	 * 
	 * @author liuyi
	 * @date 2014-8-1 上午10:28:30
	 * @param runPath
	 * @throws CsswebException
	 *              
	 * @since 0.1
	 * @see
	 */
	public static void log4jAutoConfig(String runPath) {
		try {
			//如果runPath路径包含bin目录，那去掉bin
			initConfPath(getUserDir(runPath));
			initLog4j();
		} catch (Exception e) {
			System.out.println("log4j配置文件初始化失败!");
			System.exit(1);
		}
	}

	public static void initConfPath(String runPath) throws Exception {

		if (isPath(runPath)) {
			confPath = runPath;
		} else {
			throw new Exception("log4j目录路径不正确!!");
		}

	}

	/**
	 * 初始化Log4j
	 */
	public static void initLog4j() throws Exception {
		File log4jXml = new File(confPath + File.separator + "conf"
				+ File.separator + "log4j.properties");
		if (log4jXml.exists()) {
			PropertyConfigurator.configure(confPath + File.separator + "conf"
					+ File.separator + "log4j.properties");
			/*FileAppender file = new FileAppender();
			file.setFile(confPath + File.separator + "logs" + File.separator
					+ "server.log");
			LogManager.getRootLogger().addAppender(file);*/
		} else {
			throw new Exception("加载log4j失败!!");
		}
	}

	/**
	 * 检测是否获得参数路径
	 * 
	 * @param runPath
	 *            目录路径
	 * @return
	 */
	public static boolean isPath(String runPath) {
		if (runPath == null) {
			return false;
		}

		File dirname = new File(runPath);
		if (!dirname.isDirectory()) {
			return false;
		}

		return true;
	}

	/**
	 * 根据文件名获取用户执行目录下的配置文件
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件路径
	 */
	public static String getFilePathInConf(String fileName) {

		if (fileName == null) {
			return null;
		}
		if (confPath == null) {
			return null;
		}

		File file = new File(confPath + "\\conf\\" + fileName);
		if (file.exists()) {
			return confPath + "\\conf\\" + fileName;
		}
		return null;

	}

	/**
	 * 如果user.dir目录中包含bin,那就应该截取bin以及文件分隔符
	 * 
	 * @author liuyi
	 * @date 2014-8-1 下午1:28:51
	 * @param runPath
	 * @return 
	 * @since 0.1
	 * @see
	 */
	public static String getUserDir(String runPath) {
		String userDir = "";
		if (runPath != null && runPath.contains("bin")) {
			userDir = runPath.replace("bin", "");
			userDir = userDir.substring(0, userDir.length() - 1);
		} else {
			userDir = runPath;
		}
		return userDir;
	}

}
