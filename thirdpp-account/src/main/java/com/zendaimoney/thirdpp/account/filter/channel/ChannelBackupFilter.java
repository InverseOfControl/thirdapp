package com.zendaimoney.thirdpp.account.filter.channel;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.account.annotation.ChannelFilterAnnotation;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;
import com.zendaimoney.thirdpp.account.exception.PlatformException;
import com.zendaimoney.thirdpp.account.service.ChannelAccountRequestService;
import com.zendaimoney.thirdpp.account.util.Constants;
import com.zendaimoney.thirdpp.account.util.FTPUtil;

/**
 * 
 * @author 00237071
 *
 */
@ChannelFilterAnnotation(filterStep = Constants.ChannelFilter.FILTER_ANNOTATION_BACKUP)
@Component(value = "com.zendaimoney.thirdpp.account.filter.channel.ChannelBackupFilter")
public class ChannelBackupFilter implements ChannelFilter {

	private static final Log logger = LogFactory.getLog(ChannelBackupFilter.class);
	
	@Autowired
	public ChannelAccountRequestService channelAccountRequestService;
	
	@Override
	public void doFilter(ChannelAccountConfig config, ChannelAccountRequest request, ChannelFilterChain chain, boolean isHandle) {
		FTPUtil ftpUtil = new FTPUtil();
		if (canBackup(config, request)) {
			try {
				startBackupOperation(request);
			} catch (Exception e) {
				request.setBackupStartTime(null);
				request.setBackupFailedTimes(request.getBackupFailedTimes() + 1);
				if (isHandle) {
					request.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_FAILED);
				}
				request.setStatus(Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_BACKUP_FAILED);
				request.setFailedReason(StringUtils.substring(e.getMessage(),0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH));
				channelAccountRequestService.update(request);
				throw new PlatformException("通道对账-下载对账文件-更新下载开始时间异常", e);
			}
			
			try {
				String currentThreadName = Thread.currentThread().getName();
				if (isHandle) {
					logger.info("通道【手工】对账备份开始......");
				} else {
					logger.info(currentThreadName + "通道对账备份开始......");
				}
				long currentStartTime = System.currentTimeMillis();
				
				channelAccountRequestService.backup(request, config, ftpUtil, isHandle);
				
				long currentEndTime = System.currentTimeMillis();
				if (isHandle) {
					logger.info("通道【手工】对账备份结束耗时(" + (currentEndTime - currentStartTime) + ")......");					
				} else {
					logger.info(currentThreadName + "通道对账备份结束耗时(" + (currentEndTime - currentStartTime) + ")......");
				}
			} catch (Exception e) {
				request.setBackupEndTime(null);
				request.setStatus(Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_BACKUP_FAILED);
				if (isHandle) {
					request.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_FAILED);
				}
				request.setBackupFailedTimes(request.getBackupFailedTimes() + 1);
				request.setFailedReason(StringUtils.substring(e.getMessage(), 0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH));
				channelAccountRequestService.update(request);
				throw new PlatformException("通道对账备份操作异常", e);
			} finally {
				try {
					ftpUtil.closeServer();
				} catch (IOException e) {
					logger.error("关闭FTP连接失败", e);
				}
			}
		}
		chain.doFilter(config, request, Constants.ChannelFilter.FILTER_ANNOTATION_BACKUP, isHandle);
	}

	private void startBackupOperation(ChannelAccountRequest request) {
		if (request.getBackupStartTime() == null) {
			request.setBackupStartTime(new Date());
			channelAccountRequestService.update(request);
		}
	}
	
	private boolean canBackup(ChannelAccountConfig config, ChannelAccountRequest request) {
		boolean canBackup = true;
		int status = request.getStatus();
		if (Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_ACCOUNT_SUCCESS != status
				&& Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_BACKUP_FAILED != status) {
			canBackup = false;
		}
		return canBackup;
	}
}
