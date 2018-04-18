package com.zendaimoney.thirdpp.account.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.account.conf.ServerConfig;
import com.zendaimoney.thirdpp.account.dao.ChannelAccountRequestDao;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;
import com.zendaimoney.thirdpp.account.exception.PlatformException;
import com.zendaimoney.thirdpp.account.util.Constants;
import com.zendaimoney.thirdpp.account.util.FTPUtil;

@Service
public class ChannelAccountRequestService {
	
	@Resource(name = "channelAccountRequestDao")
	public ChannelAccountRequestDao channelAccountRequestDao;
	
	public ChannelAccountRequest getChannelAccountRequestByConfig(ChannelAccountConfig config) throws SQLException {
		return channelAccountRequestDao.getChannelAccountRequestByConfig(config);
	}
	
	public ChannelAccountRequest getChannelAccountRequestByConfig(ChannelAccountConfig config, String accountDay) throws SQLException {
		return channelAccountRequestDao.getChannelAccountRequestByConfig(config, accountDay);
	}
	
	public int update(ChannelAccountRequest request) {
		return channelAccountRequestDao.update(request);
	}
	
	public int updateHandleAccountStatus(ChannelAccountRequest request) throws SQLException {
		return channelAccountRequestDao.updateHandleAccountStatus(request);
	}
	
	public void insert(ChannelAccountRequest request) {
		channelAccountRequestDao.insert(request);
	}
	
	public ChannelAccountRequest getChannelAccountRequestByRequestId(String requestId) throws SQLException {
		return channelAccountRequestDao.getChannelAccountRequestByRequestId(requestId);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class, RuntimeException.class})
	public void backup(ChannelAccountRequest request, ChannelAccountConfig config, FTPUtil ftpUtil, boolean isHandle) throws IOException {
		request.setFailedReason(StringUtils.EMPTY);
		request.setBackupEndTime(new Date());
		request.setStatus(Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_BACKUP_SUCCESS);
		if (isHandle) {
			request.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_SUCCESS);
		}
		channelAccountRequestDao.update(request);
		
		ftpUtil.connectServer(ServerConfig.systemConfig.getFtpServer(), 
				ServerConfig.systemConfig.getFtpPort(), ServerConfig.systemConfig.getFtpRootUsername(), ServerConfig.systemConfig.getFtpRootPwd(), 
				ServerConfig.channelAccountPropertyConfig.getFtpChannelAccountFileBackupPath());
		
		if (ftpUtil.createDirectory(config, request)) {
			ftpUtil.changeDirectory(request.getDownloadFilePath());
			String fileName = request.getDownloadFileName();
			if (!ftpUtil.existFile(fileName)) {
				throw new PlatformException("通道对账备份操作失败目录【" + ftpUtil.currentPath() + "】下不存在文件【" + fileName + "】");
			}
			String sourceDir = ServerConfig.channelAccountPropertyConfig
					.getFtpChannelAccountFileBackupPath()
					.concat(Constants.STRING_SLASH)
					.concat(config.getThirdTypeNo())
					.concat(Constants.STRING_SLASH)
					.concat(config.getMerchantNo())
					.concat(Constants.STRING_SLASH)
					.concat(config.getBizType())
					.concat(Constants.STRING_SLASH)
					.concat(request.getAccountDay());
			ftpUtil.copyFile(fileName, null, sourceDir);
			ftpUtil.changeDirectory(request.getDownloadFilePath());
			ftpUtil.deleteFile(new String(fileName.getBytes(Constants.ENCODE_CHARACTER_GBK),Constants.ENCODE_CHARACTER_ISO));
		}
		ftpUtil.closeServer();
	}
}
