package com.zendaimoney.thirdpp.account.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;

public class FTPUtil {

	private static final Log logger = LogFactory.getLog(FTPUtil.class);

	private FTPClient ftpClient;
	public static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE;
	public static final int ASCII_FILE_TYPE = FTP.ASCII_FILE_TYPE;

	public boolean connectServer(String host, String port, String user,
			String password, String remotePath) throws SocketException,
			IOException {
		return connectServer(host, Integer.parseInt(port), user, password, remotePath);
	}

	public boolean connectServer(String server, int port, String user,
			String password, String path) throws IOException {
		ftpClient = new FTPClient();
		ftpClient.connect(server, port);
		ftpClient.setControlEncoding(Constants.ENCODE_CHARACTER_UTF8);// 这里设置编码
		logger.info("Connected to " + server + "." + ftpClient.getReplyCode());
		boolean flag = ftpClient.login(user, password);
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		if (flag && path != null && path.length() != 0) {
			flag = changeDirectory(path);
		}
		return flag;
	}

	public void setFileType(int fileType) throws IOException {
		ftpClient.setFileType(fileType);
	}

	public void closeServer() throws IOException {
		if (ftpClient != null && ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}

	public boolean changeDirectory(String path) throws IOException {
		boolean b = ftpClient.changeWorkingDirectory(path);
		if (!b) {
			throw new IOException("FTP 服务器切换目录【" + path + "】失败， 错误原因 = " + ftpClient.getReplyCode());
		}
		return b;
	}

	public String currentPath() throws IOException{
		return ftpClient.printWorkingDirectory();
	}
	
	public boolean createDirectory(String pathName) throws IOException {
		boolean b = ftpClient.makeDirectory(pathName);
		if (b) {
			logger.debug("创建目录【" + pathName + "】");
		}
		return b;
	}

	public boolean removeDirectory(String path) throws IOException {
		return ftpClient.removeDirectory(path);
	}

	public boolean removeDirectory(String path, boolean isAll)
			throws IOException {

		if (!isAll) {
			return removeDirectory(path);
		}

		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		if (ftpFileArr == null || ftpFileArr.length == 0) {
			return removeDirectory(path);
		}
		for (FTPFile ftpFile : ftpFileArr) {
			String name = ftpFile.getName();
			if (ftpFile.isDirectory()) {
				logger.info("* [sD]Delete subPath [" + path + "/" + name + "]");
				removeDirectory(path + "/" + name, true);
			} else if (ftpFile.isFile()) {
				logger.info("* [sF]Delete file [" + path + "/" + name + "]");
				deleteFile(path + "/" + name);
			} else if (ftpFile.isSymbolicLink()) {

			} else if (ftpFile.isUnknown()) {

			}
		}
		return ftpClient.removeDirectory(path);
	}

	// Check the path is exist; exist return true, else false.
	public boolean existDirectory(String path) throws IOException {
		boolean flag = false;
		ftpClient.setControlEncoding(Constants.ENCODE_CHARACTER_GBK);
		FTPFile[] ftpFileArr = ftpClient.listFiles(ftpClient.printWorkingDirectory());
		for (FTPFile ftpFile : ftpFileArr) {
			if (ftpFile.isDirectory() && ftpFile.getName().equalsIgnoreCase(path)) {
				flag = true;
				break;
			}
		}
		
		if (flag) {
			return flag;
		} else {
			ftpClient.setControlEncoding(Constants.ENCODE_CHARACTER_UTF8);
			FTPFile[] ftpFileArr2 = ftpClient.listFiles(ftpClient.printWorkingDirectory());
			for (FTPFile ftpFile : ftpFileArr2) {
				if (ftpFile.isDirectory() && ftpFile.getName().equalsIgnoreCase(path)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	// Check the file is exist; exist return true, else false.
	public boolean existFile(String filaName) throws IOException {
		boolean flag = false;	
		ftpClient.setControlEncoding(Constants.ENCODE_CHARACTER_GBK);
		FTPFile[] ftpFileArr = ftpClient.listFiles(ftpClient.printWorkingDirectory());
		for (FTPFile ftpFile : ftpFileArr) {
			if (!ftpFile.isDirectory() && ftpFile.getName().equalsIgnoreCase(filaName)) {
				flag = true;
				break;
			}
		}
		if (flag) {
			return flag;
		} else {
			ftpClient.setControlEncoding(Constants.ENCODE_CHARACTER_UTF8);
			FTPFile[] ftpFileArr2 = ftpClient.listFiles(ftpClient.printWorkingDirectory());
			for (FTPFile ftpFile : ftpFileArr2) {
				if (!ftpFile.isDirectory() && ftpFile.getName().equalsIgnoreCase(filaName)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	public List<String> getFileList(String path) throws IOException {
		FTPFile[] ftpFiles = null;
		if (StringUtils.isBlank(path)) {
			ftpFiles = ftpClient.listFiles();
		} else {
			ftpFiles = ftpClient.listFiles(path);
		}
		List<String> retList = new ArrayList<String>();
		if (ftpFiles == null || ftpFiles.length == 0) {
			return retList;
		}
		for (FTPFile ftpFile : ftpFiles) {
			if (ftpFile.isFile()) {
				retList.add(ftpFile.getName());
			}
		}
		return retList;
	}

	public boolean deleteFile(String pathName) throws IOException {
		boolean flag = ftpClient.deleteFile(pathName);
		if (!flag) {
			throw new IOException("删除 FTP 文件失败， 错误原因 = " + ftpClient.getReplyCode());
		}
		return flag;
	}

	public void uploadFile(String fileName, String newName) throws IOException {
		InputStream iStream = null;
		try {
			iStream = new FileInputStream(fileName);
			boolean flag = ftpClient.storeFile(newName, iStream);
			if (!flag) {
				throw new IOException("向  FTP 服务器推送文件失败， 错误原因 = " + ftpClient.getReplyCode());
			}
		} finally {
			if (iStream != null) {
				iStream.close();
			}
		}
	}

	public void uploadFile(String fileName) throws IOException {
		uploadFile(fileName, fileName);
	}

	public boolean uploadFile(InputStream iStream, String newName) throws IOException {
		boolean flag = false;
		try {
			String encodeRemoteFileName = new String(
					newName.getBytes(Constants.ENCODE_CHARACTER_GBK),
					Constants.ENCODE_CHARACTER_ISO);
			flag = ftpClient.storeFile(encodeRemoteFileName, iStream);
			if (!flag) {
				throw new IOException("向  FTP 服务器推送文件失败， 错误原因 = " + ftpClient.getReplyCode());
			}
		} finally {
			if (iStream != null) {
				iStream.close();
			}
		}
		return flag;
	}

	public boolean download(String remoteFileName, String localFileName) throws IOException {
		boolean flag = false;
		File outfile = new File(localFileName);
		OutputStream oStream = null;
		try {
			oStream = new FileOutputStream(outfile);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			flag = ftpClient.retrieveFile(remoteFileName, oStream);
			if (!flag) {
				throw new IOException("从FTP 服务器下载文件【" + remoteFileName + "】失败， 错误原因 = " + ftpClient.getReplyCode());
			}
		} finally {
			oStream.close();
		}
		return flag;
	}

	/**
	 * 
	 * @param remoteFileName
	 * @param config
	 * @param localFileName
	 * @return
	 * @throws IOException
	 */
	public File downloadFile(String remoteFileName, String localFileName) throws IOException {
		boolean flag = false;
		File outFile = new File(localFileName);
		OutputStream oStream = null;
		try {
			oStream = new FileOutputStream(outFile);
			String encodeRemoteFileName = new String(
					remoteFileName.getBytes(Constants.ENCODE_CHARACTER_GBK),
					Constants.ENCODE_CHARACTER_ISO);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			flag = ftpClient.retrieveFile(encodeRemoteFileName, oStream);
			if (!flag) {
				throw new IOException("从FTP 服务器下载文件【" + encodeRemoteFileName + "】失败， 错误原因 = " + ftpClient.getReplyCode());
			}
		} finally {
			oStream.close();
			if (!flag) {
				if (outFile != null && outFile.exists()) {
					outFile.delete();
				}
			}
		}
		return flag ? outFile : null;
	}

	/**
	 * 创建的本地临时存放文件目录结构是 ：通道编码/商户号/对账日期
	 * 
	 * @param config
	 * @return
	 */
	public boolean createDirectory(ChannelAccountConfig config, ChannelAccountRequest request) throws IOException {
		boolean flag = false;
		if (!existDirectory(config.getThirdTypeNo())) {
			boolean a = createDirectory(config.getThirdTypeNo());
			if (!a) {
				throw new IOException("FTP 服务器创建目录【" + config.getThirdTypeNo() + "】失败， 错误原因 = " + ftpClient.getReplyCode());
			}
		}
		changeDirectory(config.getThirdTypeNo());
		if (!existDirectory(config.getMerchantNo())) {
			boolean b = createDirectory(config.getMerchantNo());
			if (!b) {
				throw new IOException("FTP 服务器创建目录【" + config.getMerchantNo() + "】失败， 错误原因 = " + ftpClient.getReplyCode());
			}
		}
		changeDirectory(config.getMerchantNo());

		if (!existDirectory(config.getBizType())) {
			boolean b = createDirectory(config.getBizType());
			if (!b) {
				throw new IOException("FTP 服务器创建目录【" + config.getBizType() + "】失败， 错误原因 = " + ftpClient.getReplyCode());
			}
		}
		changeDirectory(config.getBizType());
		
		String accountDay = request.getAccountDay();
		if (!existDirectory(accountDay)) {
			boolean c = createDirectory(accountDay);
			if (!c) {
				throw new IOException("FTP 服务器创建目录【" + accountDay + "】失败， 错误原因 = " + ftpClient.getReplyCode());
			}
		}
		flag = changeDirectory(accountDay);
		if (!flag) {
			throw new IOException("向  FTP 服务器推送文件失败， 错误原因 = " + ftpClient.getReplyCode());
		}
		
		return flag;
	}

	public InputStream downFile(String sourceFileName) throws IOException {
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		return ftpClient.retrieveFileStream(sourceFileName);
	}

	/**
	 * 复制文件.
	 * 
	 * @param sourceFileName
	 * @param targetFile
	 * @throws IOException
	 */
	public void copyFile(String sourceFileName, String sourceDir, String targetDir) throws IOException {
		ByteArrayInputStream in = null;
		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		try {
			if (!existDirectory(targetDir)) {
				createDirectory(targetDir);
			}
			ftpClient.setBufferSize(1024 * 2);
			if (StringUtils.isNotBlank(sourceDir)) {
				changeDirectory(sourceDir);
			}
			// 设置以二进制流的方式传输
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			// 将文件读到内存中
			boolean b = ftpClient.retrieveFile(new String(sourceFileName.getBytes(Constants.ENCODE_CHARACTER_GBK),
					Constants.ENCODE_CHARACTER_ISO), fos);
			if (!b) {
				throw new IOException("从FTP服务器下载文件【" + sourceFileName + "】失败， 错误原因 = " + ftpClient.getReplyCode());
			}
			in = new ByteArrayInputStream(fos.toByteArray());
			if (in != null) {
				changeDirectory(targetDir);
				boolean c = ftpClient.storeFile(new String(sourceFileName.getBytes(Constants.ENCODE_CHARACTER_GBK),
						Constants.ENCODE_CHARACTER_ISO), in);
				if (!c) {
					throw new IOException("向FTP服务器上传文件【" + targetDir + "】失败， 错误原因 = " + ftpClient.getReplyCode());
				}
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}

	/**
	 * 复制文件夹.
	 * 
	 * @param sourceDir
	 * @param targetDir
	 * @throws IOException
	 */
	public void copyDirectiory(String sourceDir, String targetDir) throws IOException {
		// 新建目标目录
		if (!existDirectory(targetDir)) {
			createDirectory(targetDir);
		}
		// 获取源文件夹当前下的文件或目录
		FTPFile[] ftpFiles = ftpClient.listFiles(sourceDir);
		for (int i = 0; i < ftpFiles.length; i++) {
			if (ftpFiles[i].isFile()) {
				copyFile(ftpFiles[i].getName(), sourceDir, targetDir);
			} else if (ftpFiles[i].isDirectory()) {
				copyDirectiory(sourceDir + Constants.STRING_SLASH + ftpFiles[i].getName(),
						targetDir + Constants.STRING_SLASH + ftpFiles[i].getName());
			}
		}
	}
}