package com.zendaimoney.thirdpp.account.action.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.account.annotation.DownloadActionAnnotation;
import com.zendaimoney.thirdpp.account.conf.ServerConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;
import com.zendaimoney.thirdpp.account.exception.PlatformException;
import com.zendaimoney.thirdpp.account.service.ChannelAccountRequestService;
import com.zendaimoney.thirdpp.account.util.Constants;
import com.zendaimoney.thirdpp.account.util.FTPUtil;

/**
 * FTP 方式下载文件处理类
 * 包含连接 FTP 服务器。进入执行目录，下载文件操作
 * @author 00237071
 *
 */
@DownloadActionAnnotation(fetchMethod = Constants.FETCH_METHOD_FTP)
@Component("com.zendaimoney.thirdpp.account.action.download.FTPAction")
public class FTPAction extends DownloadAction {

	private static final Log logger = LogFactory.getLog(FTPAction.class);
	@Autowired
	private ChannelAccountRequestService channelAccountRequestService;
	
	@Override
	public void download(ChannelAccountConfig config, ChannelAccountRequest request, boolean isHandle) {
		FTPUtil ftpUtil = new FTPUtil();
		File file = null;
		String remoteFileName = null;
		String localFileName = null;
		long fileSize = 0l;
		boolean uploadResult = false;
		try {
			connectRemoteFtpServer(ftpUtil, config, request);
			enterPath(ftpUtil, config, request);
			
			remoteFileName = remoteFileName(config,request);
			localFileName = String.valueOf(config.getId()).concat(Constants.STRING_DOWN_LINE).concat(remoteFileName);
			file = ftpUtil.downloadFile(remoteFileName, localFileName);
			ftpUtil.closeServer();
			
			if (file != null) {
				fileSize = file.length() / 1024;
				connectLocalFtpServer(ftpUtil);
				if (ftpUtil.createDirectory(config, request)) {
					FileInputStream fis = new FileInputStream(file);
					uploadResult = ftpUtil.uploadFile(fis, localFileName);
				}
			}
		} catch (Exception e) {
			logger.error("通道对账下载FTP文件过程中异常", e);
			initialFailedCommonAttributes(request, isHandle);
			request.setFailedReason(StringUtils.substring(e.getMessage(), 0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH));
			channelAccountRequestService.update(request);
			throw new PlatformException("通道对账下载FTP文件过程中异常", e);
		} finally {
			try {
				clearLocalFile(file);
				ftpUtil.closeServer();
			} catch (IOException e) {
				logger.error("关闭FTP连接失败", e);
			}
		}
		
		try {
			if (uploadResult) {
				String accountDay = request.getAccountDay();
				String filePath = ServerConfig.channelAccountPropertyConfig.getFtpChannelAccountFileTempPath().concat(Constants.STRING_SLASH).concat(config.getThirdTypeNo()).
						concat(Constants.STRING_SLASH).concat(config.getMerchantNo()).concat(Constants.STRING_SLASH).concat(config.getBizType()).concat(Constants.STRING_SLASH).concat(accountDay);
				initialSuccessdCommonAttributes(request,localFileName,fileSize,filePath);
			} else {
				initialFailedCommonAttributes(request, isHandle);
				String errorMsg = "通道对账FTP对账文件【" + localFileName + "】不存在";
				logger.info(errorMsg);
				request.setFailedReason(errorMsg);
			}
			channelAccountRequestService.update(request);
		} catch(Exception e) {
			logger.error("通道对账下载FTP文件，更新本地库失败", e);
			initialFailedCommonAttributes(request, isHandle);
			request.setFailedReason(StringUtils.substring(e.getMessage(), 0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH));
			channelAccountRequestService.update(request);
			throw new PlatformException("通道对账下载FTP文件，更新本地库失败", e);
		}
	}

	private boolean connectRemoteFtpServer(FTPUtil ftpUtil, ChannelAccountConfig config, ChannelAccountRequest request) throws IOException{
		String ftpPath = config.getFilePath();
		boolean flag = ftpUtil.connectServer(config.getServer(), config.getPort(),
					config.getLoginUsername(), config.getLoginPwd(), ftpPath);
		if (!flag) {
			throw new PlatformException("通道对账连接FTP服务器失败【" + config.getServer() + ":" + config.getPort() + "@" + 
					config.getLoginUsername() + "," + config.getLoginPwd() + "," + config.getFilePath() + "】");
		}
		logger.info("通道对账连接FTP服务器成功 【server = " + config.getServer() + "】," + "【port = "  + config.getPort() + "】");
		return flag;
	}
	
	private boolean connectLocalFtpServer(FTPUtil ftpUtil) throws IOException {
		boolean f = ftpUtil.connectServer(ServerConfig.systemConfig.getFtpServer(), 
				ServerConfig.systemConfig.getFtpPort(), ServerConfig.systemConfig.getFtpRootUsername(), ServerConfig.systemConfig.getFtpRootPwd(), 
				ServerConfig.channelAccountPropertyConfig.getFtpChannelAccountFileTempPath());
		if (!f) {
			throw new PlatformException("通道对账连接FTP服务器失败【" + ServerConfig.systemConfig.getFtpServer() + ":" + ServerConfig.systemConfig.getFtpPort() + "@" + 
					ServerConfig.systemConfig.getFtpRootUsername() + "," + ServerConfig.systemConfig.getFtpRootPwd() + "," + ServerConfig.channelAccountPropertyConfig.getFtpChannelAccountFileTempPath() + "】");
		}
		logger.info("通道对账连接FTP服务器成功 【server = " + ServerConfig.systemConfig.getFtpServer() + "】," + "【port = "  + ServerConfig.systemConfig.getFtpPort() + "】");
		return f;
	}
	
	private void clearLocalFile(File file){
		if (file != null && file.exists()) {
			file.delete();
		}
	}
	
	private boolean enterPath(FTPUtil ftpUtil, ChannelAccountConfig config, ChannelAccountRequest request) throws ReflectiveOperationException, IOException {
		boolean flag = false;
		String filePathSub = config.getFilePathSub();
		if (StringUtils.isBlank(filePathSub)) {
			flag = true;
		} else {
			if (!filePathSub.contains(Constants.STRING_SLASH)) {
				flag = matchAndSetepIntoDirectory(ftpUtil, filePathSub, request);
			} else {
				String[] subPaths = filePathSub.split(Constants.STRING_SLASH);
				for (String subPath : subPaths) {
					flag = matchAndSetepIntoDirectory(ftpUtil, subPath, request);
					if (!flag) break;
				}
			}
		}
		if (!flag) {
			throw new PlatformException("通道对账请求对象中找不到指定的域【filePathSub = " + config.getFilePathSub() + "】，请检查该配置项");
		}
		return flag;
	}
	
	/**
	 * 进入FTP目录
	 * @param ftpUtil
	 * @param filePathSub
	 * @param request
	 * @return
	 * @throws ReflectiveOperationException 
	 * @throws IOException 
	 */
	private boolean matchAndSetepIntoDirectory(FTPUtil ftpUtil, String filePathSub, ChannelAccountRequest request) throws ReflectiveOperationException, IOException{
		boolean flag = true;
		Class<ChannelAccountRequest> cla = ChannelAccountRequest.class;
		Field[] classFields = cla.getDeclaredFields();
		String subPath = filePathSub;
		for (Field field : classFields) {
			String name = field.getName();
			if (name.equals(filePathSub)) {
				field.setAccessible(true);
				subPath = (String)field.get(request);
				break;
			}
		}
		boolean temp = ftpUtil.changeDirectory(subPath);
		if (!temp) {
			flag = false;
			throw new PlatformException("通道对账FTP服务器目录不存在【" + subPath + "】");
		}
		logger.info("程序进入FTP目录【" + subPath + "】");
		return flag;
	}
	
	/** 
	 * 根据通道对账配置的文件名称规则，生成对账文件名称
	 * 组装通道对账文件名称
	 * @param config
	 * @return 完成的对账文件名称 TODO 这边文件名称怎么规范和扩展
	 */
	private String remoteFileName(ChannelAccountConfig config, ChannelAccountRequest request){
		String formatName = config.getFileNameFormat();
		if (StringUtils.isNotBlank(formatName)) {
			if (formatName.contains("merchantNo")) {
				formatName = formatName.replaceAll("merchantNo", config.getMerchantNo());
			}
			if (formatName.contains("accountDay")) {
				formatName = formatName.replaceAll("accountDay", request.getAccountDay());
			}
			formatName = formatName.concat(Constants.STRING_POINT).concat(config.getFileSuffix());
			return formatName;
		}
		return StringUtils.EMPTY;
	}

	private void initialFailedCommonAttributes(ChannelAccountRequest request, boolean isHandle){
		request.setDownloadEndTime(null);
		request.setStatus(Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_DOWNLOAD_FILE_FAILED);
		if (isHandle) {
			request.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_FAILED);
		}
		request.setDownloadFailedTimes(request.getDownloadFailedTimes() + 1);
	}
	
	private void initialSuccessdCommonAttributes(ChannelAccountRequest request, String fileName, long fileSize, String ftpPath){
		request.setFailedReason(StringUtils.EMPTY);
		request.setStatus(Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_DOWNLOAD_FILE_SUCCESS);
		request.setDownloadEndTime(new Date());
		request.setDownloadFileName(fileName);
		request.setDownloadFilePath(ftpPath);
		request.setDownloadFileSize(fileSize);
	}
	
}
