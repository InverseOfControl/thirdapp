package com.zendaimoney.thirdpp.account.filter.bizsys;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.account.annotation.BizsysFilterAnnotation;
import com.zendaimoney.thirdpp.account.conf.ServerConfig;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountConfig;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountRequest;
import com.zendaimoney.thirdpp.account.exception.PlatformException;
import com.zendaimoney.thirdpp.account.service.BizsysAccountRequestService;
import com.zendaimoney.thirdpp.account.util.Constants;
import com.zendaimoney.thirdpp.account.util.FTPUtil;

/**
 * 向业务系统推送对账文件
 * @author user
 *
 */
@BizsysFilterAnnotation(filterStep = Constants.BizsysFilter.FILTER_ANNOTATION_PUSH_FILE)
@Component(value = "com.zendaimoney.thirdpp.account.filter.bizsys.BizsysPushAccountFileFilter")
public class BizsysPushAccountFileFilter implements BizsysFilter {

	private static final Log logger = LogFactory.getLog(BizsysPushAccountFileFilter.class);
	
	@Autowired
	private BizsysAccountRequestService bizsysAccountRequestService;
	
	@Override
	public void doFilter(BizsysAccountRequest request, BizsysAccountConfig config,  BizsysSimpleFilterChain chain, boolean isHandle) {
			FTPUtil ftpUtil = new FTPUtil();
			String currentThreadName = Thread.currentThread().getName();
			try {
				if (canPush(request, config)) {
					if (isHandle) {
						logger.debug("业务系统【手工】对账推送对账文件开始......");
					} else {
						logger.debug(currentThreadName + "业务系统对账推送对账文件开始......");
					}
					long currentStartTime = System.currentTimeMillis();
					
					try {
						startPushOperation(request);
					} catch (Exception e) {
						request.setPushStartTime(null);
						request.setPushFailedTimes(request.getPushFailedTimes()+ 1);
						if (isHandle) {
							request.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_FAILED);
						}
						request.setStatus(Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_PUSH_FAILED);
						request.setFailedReason(StringUtils.substring(e.getMessage(),0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH));
						bizsysAccountRequestService.update(request);
						throw new PlatformException("业务系统对账-推送对账文件-更新推送开始时间异常", e);
					}
					
					connectRemoteFtpServer(ftpUtil, config);
					bizsysAccountRequestService.uploadFile(request, ftpUtil, isHandle);
					
					long currentEndTime = System.currentTimeMillis();
					if (isHandle) {
						logger.info("业务系统【手工】对账推送对账文件结束耗时(" + (currentEndTime - currentStartTime) + ")......");
					} else {
						logger.info(currentThreadName + "业务系统对账推送对账文件结束耗时(" + (currentEndTime - currentStartTime) + ")......");
					}
				}
			} catch (Exception e) {
				request.setPushFileName(StringUtils.EMPTY);
				request.setPushFilePath(StringUtils.EMPTY);
				request.setPushFileSize(0l);
				request.setPushEndTime(null);
				request.setPushFailedTimes(request.getPushFailedTimes() + 1);
				if (isHandle) {
					request.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_FAILED);
				}
				request.setStatus(Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_PUSH_FAILED);
				request.setFailedReason(StringUtils.substring(e.getMessage(), 0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH));
				bizsysAccountRequestService.update(request);
				throw new PlatformException("业务系统对账向FTP服务器推送对账文件失败", e);
			} finally {
				try {
					ftpUtil.closeServer();
				} catch (IOException e) {
					logger.error("业务系统对账关闭FTP连接失败", e);
				}
			}
		
	}

	private boolean canPush(BizsysAccountRequest request, BizsysAccountConfig config) {
		boolean flag = true;
		int requestStatus = request.getStatus();
		if (Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_LOCALIZE_SUCCESS != requestStatus 
				&& Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_PUSH_FAILED != requestStatus ) {
			flag = false;
		}
		
		return flag;
	}
	
	private void startPushOperation(BizsysAccountRequest request){
		if (request.getPushStartTime() == null) {
			request.setPushStartTime(new Date());
			bizsysAccountRequestService.update(request);
		}
	}
	
	private boolean connectRemoteFtpServer(FTPUtil ftpUtil, BizsysAccountConfig config) throws IOException {
		String ftpServer = config.getFtpServer();
		String ftpPort = config.getFtpPort();
		String ftpUsername = ServerConfig.systemConfig.getFtpRootUsername();
		String ftpPwd = ServerConfig.systemConfig.getFtpRootPwd();
		String ftpPath = config.getFtpPath();
		boolean flag = ftpUtil.connectServer(ftpServer, ftpPort, ftpUsername, ftpPwd, ftpPath);
		if (!flag) {
			throw new PlatformException("业务系统对账连接FTP服务器失败【" + ftpServer + ":" +ftpPort + "@" + 
					ftpUsername + "," + ftpPwd + "," + ftpPath + "】");
		}
		return flag;
	}
}
