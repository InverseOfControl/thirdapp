package com.zendaimoney.thirdpp.account.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * SFTP工具类
 * 
 * @version v1.0 2014-01-13
 * 
 */
public class SFTPUtil {

	private static final Logger logger = Logger.getLogger(SFTPUtil.class);

	/**
	 * 连接sftp服务器
	 * 
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return sftp连接
	 */
	public ChannelSftp connect(String host, String port, String userName,
			String password) {
		ChannelSftp sftp = null;
		int ftpPort = Integer.parseInt(port);
		logger.info("连接SFTP服务器:" + host);
		try {
			JSch jsch = new JSch();
			jsch.getSession(userName, host, ftpPort);
			Session sshSession = jsch.getSession(userName, host, ftpPort);
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			logger.info("Connected to " + host + ".");
			logger.debug("链接到SFTP成功 " + host);
		} catch (Exception e) {
			logger.error("连接失败！！！！！！", e);
		}
		return sftp;
	}

	/**
	 * 关闭连接 server
	 */
	public void disconnect(ChannelSftp sftp) {
		if (sftp != null) {
			if (sftp.isConnected()) {
				sftp.disconnect();
			} else if (sftp.isClosed()) {
				logger.info("sftp 已经关闭");
			}
		}

	}

	/**
	 * 上传单个文件(上传后文件名需保持一致)
	 * 
	 * @param directory
	 *            远程上传目录
	 * @param uploadFilePath
	 *            本地待上传文件路径
	 * @param uploadFile
	 *            本地待上传文件名
	 * @param sftp
	 *            连接的sftp
	 */
	public void upload(String directory, String uploadFilePath,
			String uploadFile, ChannelSftp sftp) {
		try {
			if (directory != null && !"".equals(directory)) {
				sftp.cd(directory);
			}
			File file = new File(uploadFilePath + uploadFile);
			sftp.put(new FileInputStream(file), file.getName());
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param sftp
	 *            连接的sftp
	 * @param directory
	 *            远程下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param savePath
	 *            存在本地的路径
	 * @param verifyDate
	 *            日期
	 * 
	 * 
	 */
	public void download(ChannelSftp sftp, String directory,
			String downloadFile, String verifyDate, String savePath)
			throws Exception {

		try {
			if (null == verifyDate || "".equals(verifyDate)) {
				verifyDate = getPrevDate();
			}

			sftp.isConnected();
			// String sftpAddress = directory + File.separator;
			String sftpAddress = directory + "/";
			logger.info("sftp目录为:" + sftpAddress);
			if (sftpAddress != null && !"".equals(sftpAddress)) {
				sftp.cd(sftpAddress);
			}

			File dir = new File(savePath);
			if (!dir.exists()) {
				dir.mkdir();
			}
			File file = new File(savePath + File.separator + downloadFile);
			sftp.getExitStatus();
			System.out.println("get exitstatus before=" + sftp.getExitStatus());
			sftp.get(downloadFile, new FileOutputStream(file));
			logger.info("下载文件成功 " + sftp);
			System.out.println("get exitstatus after=" + sftp.getExitStatus());
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @param sftp
	 */
	public void delete(String directory, String deleteFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private String getPrevDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		logger.info(cal.getTime());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String prevDate = format.format(cal.getTime());
		return prevDate;
	}

}
