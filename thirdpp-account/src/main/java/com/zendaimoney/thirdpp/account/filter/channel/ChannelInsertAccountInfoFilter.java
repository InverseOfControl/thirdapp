package com.zendaimoney.thirdpp.account.filter.channel;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.account.action.parse.FileParseAction;
import com.zendaimoney.thirdpp.account.annotation.ChannelFilterAnnotation;
import com.zendaimoney.thirdpp.account.conf.ServerConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;
import com.zendaimoney.thirdpp.account.exception.PlatformException;
import com.zendaimoney.thirdpp.account.service.ChannelAccountRequestService;
import com.zendaimoney.thirdpp.account.util.ActionMapperUtil;
import com.zendaimoney.thirdpp.account.util.Constants;
import com.zendaimoney.thirdpp.account.util.FTPUtil;

@ChannelFilterAnnotation(filterStep = Constants.ChannelFilter.FILTER_ANNOTATION_INSERT_ACCOUNT_INFO)
@Component(value = "com.zendaimoney.thirdpp.account.filter.channel.ChannelInsertAccountInfoFilter")
public class ChannelInsertAccountInfoFilter implements ChannelFilter {

	private static final Log logger = LogFactory.getLog(ChannelInsertAccountInfoFilter.class);
	
	@Autowired
	private ChannelAccountRequestService channelAccountRequestService;
	
	@Override
	public void doFilter(ChannelAccountConfig config, ChannelAccountRequest request, ChannelFilterChain chain, boolean isHandle) {
		String currentThreadName = Thread.currentThread().getName();
		FTPUtil ftpUtil = new FTPUtil();
		File file = null;
		if (canDoInsertOperation(config, request)) {
			try {
				startInsertOperation(request);
			} catch (Exception e) {
				request.setInsertStartTime(null);
				request.setInsertFailedTimes(request.getInsertFailedTimes() + 1);
				if (isHandle) {
					request.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_FAILED);
				}
				request.setStatus(Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_INSERT_TABLE_FAILED);
				request.setFailedReason(StringUtils.substring(e.getMessage(),0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH));
				channelAccountRequestService.update(request);
				throw new PlatformException("通道对账-下载对账文件-更新入表开始时间异常", e);
			}
			
			try {
				if (isHandle) {
					logger.info("通道【手工】对账入对账流水表开始......");
				} else {
					logger.info(currentThreadName + "通道对账入对账流水表开始......");
				}
				long currentStartTime = System.currentTimeMillis();
				
			    connectRemoteFtpServer(ftpUtil, config, request);
				String fileName = request.getDownloadFileName();
				file = ftpUtil.downloadFile(fileName, fileName);
				insert(file, config, request);
				
				long currentEndTime = System.currentTimeMillis();
				if (isHandle) {
					logger.info("通道【手工】对账入对账流水表结束耗时(" + (currentEndTime - currentStartTime) + ")......");
				} else {
					logger.info(currentThreadName + "通道对账入对账流水表结束耗时(" + (currentEndTime - currentStartTime) + ")......");
				}
			} catch (Exception e) {
				initialFailedCommonAttributes(request, isHandle);
				request.setFailedReason(StringUtils.substring(e.getMessage(), 0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH));
				channelAccountRequestService.update(request);
				throw new PlatformException("通道对账入对账流水表失败", e);
			} finally {
				try {
					clearLocalFile(file);
					ftpUtil.closeServer();
				} catch (IOException e) {
					logger.error("关闭FTP连接失败", e);
				}
			}
		}
		chain.doFilter(config, request, Constants.ChannelFilter.FILTER_ANNOTATION_INSERT_ACCOUNT_INFO, isHandle);
	}

	private void startInsertOperation(ChannelAccountRequest request) {
		if (request.getInsertStartTime() == null) {
			request.setInsertStartTime(new Date());
			channelAccountRequestService.update(request);
		}
	}
	
	private void clearLocalFile(File file){
		if (file != null && file.exists()) {
			file.delete();
		}
	}
	
	private boolean connectRemoteFtpServer(FTPUtil ftpUtil, ChannelAccountConfig config, ChannelAccountRequest request) throws IOException{
		String ftpPath = request.getDownloadFilePath();
		boolean flag = ftpUtil.connectServer(ServerConfig.systemConfig.getFtpServer(), ServerConfig.systemConfig.getFtpPort(),
				ServerConfig.systemConfig.getFtpRootUsername(), ServerConfig.systemConfig.getFtpRootPwd(), ftpPath);
		if (!flag) {
			throw new PlatformException("通道对账连接FTP服务器失败【" + config.getServer() + ":" + config.getPort() + "@" + 
					config.getLoginUsername() + "," + config.getLoginPwd() + "," + config.getFilePath() + "】");
		}
		return flag;
	}
	
	private void initialFailedCommonAttributes(ChannelAccountRequest request, boolean isHandle){
		request.setStatus(Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_INSERT_TABLE_FAILED);
		if (isHandle) {
			request.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_FAILED);
		}
		request.setInsertFailedTimes(request.getInsertFailedTimes() + 1);
		request.setInsertEndTime(null);
	}
	
	private boolean canDoInsertOperation(ChannelAccountConfig config, ChannelAccountRequest request){
		boolean flag = true;
		int status = request.getStatus();
		if (Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_DOWNLOAD_FILE_SUCCESS != status
				&& Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_INSERT_TABLE_FAILED != status) {
			flag = false;
		}
		return flag;
	}
	
	private void insert(File file, ChannelAccountConfig config,ChannelAccountRequest newRequest) throws IOException, SQLException, ReflectiveOperationException{
		Class<? extends FileParseAction> aa = ActionMapperUtil.fileParseActionMap.get(config.getFileSuffix()).getActionClazz();
		FileParseAction accountAction = (FileParseAction) ServerConfig.getBean(aa.getName());
		accountAction.parse(file, config, newRequest);
	}

}
