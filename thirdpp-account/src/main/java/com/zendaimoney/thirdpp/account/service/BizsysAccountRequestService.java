package com.zendaimoney.thirdpp.account.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.account.dao.BizsysAccountRequestDao;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountRequest;
import com.zendaimoney.thirdpp.account.exception.PlatformException;
import com.zendaimoney.thirdpp.account.util.Constants;
import com.zendaimoney.thirdpp.account.util.FTPUtil;

@Service
public class BizsysAccountRequestService {

	@Resource(name = "bizsysAccountRequestDao")
	private BizsysAccountRequestDao bizsysAccountRequestDao;
	
	private static final Log logger = LogFactory.getLog(BizsysAccountRequestService.class);
	
	public BizsysAccountRequest getBizsysAccountRequestByConfigId(String configId) throws SQLException {
		return bizsysAccountRequestDao.getBizsysAccountRequestByConfigId(configId);
	}
	
	public BizsysAccountRequest getBizsysAccountRequestByRequestId(String requestId) throws SQLException {
		return bizsysAccountRequestDao.getBizsysAccountRequestByRequestId(requestId);
	}
	
	public BizsysAccountRequest getBizsysAccountRequestByConfigId(String configId, String accountDay) throws SQLException {
		return bizsysAccountRequestDao.getBizsysAccountRequestByConfigIdAndAccountDay(configId, accountDay);
	}
	
	public void insert(BizsysAccountRequest request) {
		bizsysAccountRequestDao.insert(request);
	}
	
	public int update(BizsysAccountRequest request) {
		return bizsysAccountRequestDao.update(request);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class, RuntimeException.class})
	public void uploadFile(BizsysAccountRequest request, FTPUtil ftpUtil, boolean isHandle) throws IOException {
		String fileName = request.getLocalizeFileName();
		String filePath = request.getLocalizePath();
	
		request.setStatus(Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_PUSH_SUCCESS);
		if (isHandle) {
			request.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_SUCCESS);
		}
		request.setPushEndTime(new Date());
		request.setPushFileName(fileName);
		request.setPushFilePath(ftpUtil.currentPath());
		request.setPushFileSize(fileSize(filePath.concat(Constants.STRING_SLASH).concat(fileName)));
		request.setFailedReason(StringUtils.EMPTY);
		bizsysAccountRequestDao.update(request);
		
		// 向指定目录上传文件
		ftpUtil.uploadFile(filePath.concat(Constants.STRING_SLASH).concat(fileName), fileName);
		
		// 删除本地的临时文件
		deleteTempFile(request, ftpUtil);
	}
	
	private void deleteTempFile(BizsysAccountRequest request, FTPUtil ftpUtil) {
		logger.info("开始删除本地文件【" + request.getLocalizeFileName() + "】");
		String filePath = request.getLocalizePath();
		File tempFile = new File(filePath.concat(Constants.STRING_SLASH).concat(request.getLocalizeFileName()));
		if (tempFile.exists()) {
			boolean a = tempFile.delete();
			if (!a) {
				throw new PlatformException("业务系统对账向业务系统推送文件失败，删除本地临时文件，删除文件【" + request.getLocalizeFileName() + "】失败");
			}
		}
		
		String bizTypelevelPath = filePath.substring(0, filePath.lastIndexOf(Constants.STRING_SLASH));
		File bizTypelevelFile = new File(bizTypelevelPath);
		String needDeletePath =  filePath.substring(filePath.lastIndexOf(Constants.STRING_SLASH) + 1, filePath.length());
		if (bizTypelevelFile != null && bizTypelevelFile.exists() && bizTypelevelFile.isDirectory()) {
			File[] accountDayFiles = bizTypelevelFile.listFiles();
			if (accountDayFiles != null) {
				for (File file : accountDayFiles) {
					if (file.getName().equals(needDeletePath)) {
						boolean b = file.delete();
						if (!b) {
							throw new PlatformException("业务系统对账向业务系统推送文件失败，删除本地临时文件，删除文件目录【" + needDeletePath + "】失败");
						}
					}
				}
			}
		} else {
			logger.error("业务系统对账向业务系统推送文件失败，删除本地临时文件，切换文件存放目录【+ " + bizTypelevelPath + "】失败");
			throw new PlatformException("业务系统对账向业务系统推送文件失败，删除本地临时文件，切换文件存放目录【+ " + bizTypelevelPath + "】失败");
		}
	}
	
	private long fileSize(String fileName) {
		long size = 0l;
		File file = new File(fileName);
		if (file != null) {
			size = file.length() / 1024;
			if (size == 0) {
				size = 1;
			}
		}
		return size;
	}
	
	public int updateHandleAccountStatus(BizsysAccountRequest request) throws SQLException {
		return bizsysAccountRequestDao.updateHandleAccountStatus(request);
	}
	
	public static void main(String[] args){
		String str = "/home/tpp/bizsys_account_temp/001_002/20160114";
		String accountDayDic = str.substring(0, str.lastIndexOf(Constants.STRING_SLASH));
		String filaPath = str.substring(str.lastIndexOf(Constants.STRING_SLASH) + 1, str.length());
		System.out.println(accountDayDic);
		System.out.println(filaPath);
	}
}
