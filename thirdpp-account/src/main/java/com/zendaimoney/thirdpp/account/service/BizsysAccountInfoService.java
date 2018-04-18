package com.zendaimoney.thirdpp.account.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.account.annotation.TranslateAnnotation;
import com.zendaimoney.thirdpp.account.dao.BizsysAccountInfoDao;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountConfig;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountInfo;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountRequest;
import com.zendaimoney.thirdpp.account.exception.PlatformException;
import com.zendaimoney.thirdpp.account.util.Constants;
import com.zendaimoney.thirdpp.account.util.TxtUtil;

@Service
public class BizsysAccountInfoService {
	
	private static final Log logger = LogFactory.getLog(BizsysAccountInfoService.class);
	
	@Resource(name = "bizsysAccountInfoDao")
	private BizsysAccountInfoDao bizsysAccountInfoDao;
	@Autowired
	private BizsysAccountRequestService bizsysAccountRequestService;
	
	public List<BizsysAccountInfo> getBizsysAccountStatistic(String bizSysNo, String bizType, String accountDay) throws SQLException{
		return bizsysAccountInfoDao.getBizsysAccountStatistic(bizSysNo, bizType, accountDay);
	}
	public void batchInsert(List<BizsysAccountInfo> accountInfos) throws SQLException {
		bizsysAccountInfoDao.batchInsert(accountInfos);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class, RuntimeException.class})
	public void generateFile(BizsysAccountRequest request, BizsysAccountConfig config) throws IOException, NoSuchFieldException {
		String fileName = remoteFileName(request, config);
		String completeFilePath = createDirectory(config, request);
		logger.info("业务系统对账-开始创建空白文件，文件路径=" + completeFilePath + " 文件名=" + fileName);
		
		String temp = completeFilePath.concat(Constants.STRING_SLASH).concat(fileName);
		File file = new File(temp);
		boolean isFileExist = file.exists();
		if (isFileExist) {
			logger.info("文件【" + temp +"】已经存在");
		} else {
			logger.info("文件【" + temp +"】不存在");
		}
		
		logger.info("业务系统对账请求表【LocalizePath=" + request.getLocalizePath() + "】【LocalizeFileName=" + request.getLocalizeFileName() + "】");
		
		if (StringUtils.isBlank(request.getLocalizePath()) || StringUtils.isBlank(request.getLocalizeFileName())) {
			if (file.exists()) {
				logger.info("开始删除文件【" + temp + "】");
				boolean f = file.delete();
				if (!f) {
					throw new PlatformException("删除文件【" + temp + "】失败");
				}
				logger.info("完成删除文件【" + temp + "】");
			}
		}
		
		if (!file.exists()) {
			updateBizsysAccountRequest(request, fileName,completeFilePath);
			List<String> emptyContent = attrsDefinition(config, request);
			TxtUtil.writerTxt(file, emptyContent, config.getFileEncoding());
		}
		
		logger.info("业务系统对账-成功创建空白文件【文件路径=" + completeFilePath + " 文件名=" + fileName + "】");
	}
	
	private void updateBizsysAccountRequest(BizsysAccountRequest request, String fileName, String filePath) {
		request.setLocalizeFileName(fileName);
		request.setLocalizePath(filePath);
		bizsysAccountRequestService.update(request);
	}
	
	private List<String> attrsDefinition(BizsysAccountConfig config, BizsysAccountRequest request) {
		String attrsDefinition = config.getAttrsDefinition();
		String attrSplit = transfered(config.getAttrsSplit());
		List<String> strList = new ArrayList<String>();
		if (StringUtils.isBlank(attrsDefinition) || StringUtils.isBlank(attrSplit)) {
			return strList;
		}
		Class<BizsysAccountInfo> cla = BizsysAccountInfo.class;
		String[] tStr = attrsDefinition.split(attrSplit);
		StringBuffer sbf = new StringBuffer();
		
		for (int i = 0 ; i < tStr.length; i++) {
			Field field = null;
			try {
				field = cla.getDeclaredField(tStr[i]);
			} catch (Exception e) {
				logger.error("不存在属性" + tStr[i] + ", 请检查业务系统对账配置表【ATTRS_DEFINATION】属性名称是否正确【ATTRS_SPLIT】分隔符是否匹配");
				throw new PlatformException("不存在属性" + tStr[i] + ", 请检查业务系统对账配置表【ATTRS_DEFINATION】属性名称是否正确【ATTRS_SPLIT】分隔符是否匹配");
			}
			TranslateAnnotation annotation = field.getAnnotation(TranslateAnnotation.class);
			String value = annotation.value();
			sbf.append(value);
			if (i !=  tStr.length - 1) {
				sbf.append(config.getAttrsSplit());
			}
		}
		if (StringUtils.isNotBlank(sbf.toString())) {
			strList.add(sbf.toString());
		}
		
		return strList;
	}
	
	private String transfered(String split){
		if (split.equals(Constants.STRING_CA)) {
			return Constants.STRING_CA_TR;
		}
		return split;
	}
	
	private String remoteFileName(BizsysAccountRequest request, BizsysAccountConfig config) {
		String formatName = config.getFileNameFormat();
		if (StringUtils.isNotBlank(formatName)) {
			if (formatName.contains("bizSysNo")) {
				formatName = formatName.replaceAll("bizSysNo", request.getBizSysNo());
			}
			if (formatName.contains("accountDay")) {
				formatName = formatName.replaceAll("accountDay", request.getAccountDay());
			}
			if (formatName.contains("bizType")) {
				formatName = formatName.replaceAll("bizType", request.getBizType());
			}
			formatName = formatName.concat(Constants.STRING_POINT).concat(config.getFileSuffix());
			return formatName;
		}
		return StringUtils.EMPTY;
	}
	
	private String createDirectory(BizsysAccountConfig config, BizsysAccountRequest request) {
		boolean flag = false;
		// 该根目录在配置前必须实现创建好
		String localFileRootPath = config.getLocalAccountRootPath();
		File localFile = new File(localFileRootPath);
		if (localFile.exists() && localFile.isDirectory()) {
			boolean a = localFile.setWritable(true);
			if (!a) {
				throw new PlatformException("业务系统对账设置本地目录【" + localFileRootPath + "】可写失败.");
			}
		} else {
			throw new PlatformException("业务系统本地目录【" + localFileRootPath + "】不存在，请事先创建好该目录，并且赋予相应权限。");
		}
		
		String wholePath = StringUtils.EMPTY;
		if (localFile.exists() && localFile.isDirectory()) {
			String bizSysNo = config.getBizSysNo();
			String bizSysNoPath = localFileRootPath.concat(Constants.STRING_SLASH).concat(bizSysNo);
			File bizSysNoFile = new File(bizSysNoPath);
			if (bizSysNoFile.exists() && bizSysNoFile.isDirectory()) {
				boolean a = bizSysNoFile.setWritable(true);
				if (!a) {
					throw new PlatformException("业务系统对账设置本地目录可写【" + bizSysNoPath + "】失败.");
				}
			} else {
				boolean b = bizSysNoFile.mkdir();
				if (!b) {
					throw new PlatformException("业务系统对账创建本地目录【" + bizSysNoPath + "】失败.");
				}
			}
			if (bizSysNoFile.exists() && bizSysNoFile.isDirectory()) {
				String bizTypePath = bizSysNoPath.concat(Constants.STRING_SLASH).concat(config.getBizType());
				File bizTypeFile = new File(bizTypePath);
				if (bizTypeFile.exists() && bizTypeFile.isDirectory()) {
					boolean a = bizTypeFile.setWritable(true);
					if (!a) {
						throw new PlatformException("业务系统对账设置本地目录可写【" + bizTypePath + "】失败.");
					}
				} else {
					boolean b = bizTypeFile.mkdir();
					if (!b) {
						throw new PlatformException("业务系统对账创建本地目录【" + bizTypePath + "】失败.");
					}
				}
				if (bizTypeFile.exists() && bizTypeFile.isDirectory()) {
					String accountDay = request.getAccountDay();
					wholePath = bizTypePath.concat(Constants.STRING_SLASH).concat(accountDay);
					File accountDayFile = new File(wholePath);
					if (accountDayFile.exists() && accountDayFile.isDirectory()) {
					    boolean a = accountDayFile.setWritable(true);
						if (!a) {
							throw new PlatformException("业务系统对账设置本地目录可写【" + wholePath + "】失败.");
						}
						flag =  true;
					} else {
						flag = accountDayFile.mkdir();
						if (!flag) {
							throw new PlatformException("业务系统对账创建本地目录【" + wholePath + "】失败.");
						}
					}
				} else {
					throw new PlatformException("业务系统对账本地目录【" + bizTypePath + "】不存在.");
				}
			} else {
				throw new PlatformException("业务系统对账本地目录【" + bizSysNoPath + "】不存在.");
			}
		} else {
			throw new PlatformException("业务系统本地目录【" + localFileRootPath + "】不存在，请事先创建好该目录，并且赋予相应权限。");
		}
		
		return flag ? wholePath : StringUtils.EMPTY;
	}
}
