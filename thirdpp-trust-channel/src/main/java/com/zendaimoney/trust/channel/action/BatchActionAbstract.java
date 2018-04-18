package com.zendaimoney.trust.channel.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.zendaimoney.thirdpp.common.vo.AttachmentResponse;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.trust.channel.conf.CmbConfig;
import com.zendaimoney.trust.channel.conf.FtpConfig;
import com.zendaimoney.trust.channel.entity.BatchOper;
import com.zendaimoney.trust.channel.entity.SysInfoCategory;
import com.zendaimoney.trust.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.trust.channel.entity.batch.FileNameDto;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.service.BatchOperService;
import com.zendaimoney.trust.channel.service.RequestService;
import com.zendaimoney.trust.channel.util.CalendarUtils;
import com.zendaimoney.trust.channel.util.ConfigUtil;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.ConvertUtils;
import com.zendaimoney.trust.channel.util.FTPUtil;
import com.zendaimoney.trust.channel.util.FileUtils;
import com.zendaimoney.trust.channel.util.LogPrn;
import com.zendaimoney.trust.channel.util.ThirdPPCacheContainer;

/**
 * 资金托管批量(生产批量文件、解析返回的结果文件)接口抽象Action
 * 
 * @author mencius
 * 
 */
@SuppressWarnings("rawtypes")
public abstract class BatchActionAbstract {

	private static final LogPrn logger = new LogPrn(BatchActionAbstract.class);

	@Autowired
	private RequestService requestService;

	@Value("${TPP_CMB_DOWNLOAD_FILE_PATH}")
	private String downloadPath;

	@Value("${TPP_CMB_UPLOAD_FILE_PATH}")
	private String forloadPath;

	@Autowired
	private BatchOperService batchOperService;

	/**
	 * 生成批量报文File
	 * 
	 * @param data
	 */
	public Response buildBatch(TrustBizReqVo trustBizReqVo) {
		Response response = null;
		AttachmentResponse<String> attachmentResponse = new AttachmentResponse<String>();
		BatchOper batchOper = null;
		boolean dealFlag = false;
		try {

			// 1.报文批量数据处理 符合第三方资金托管规则
			List data = preProcess(trustBizReqVo);

			// 2.根据规则生产报文文件名称
			String fileName = getFileName(trustBizReqVo);

			// 第三方通道目录文件夹
			String payFolder = trustBizReqVo.getThirdType().getCode()
					+ File.separator;

			// 获取FTP的文件存放地址，并拼接成文件路径信息
			String forloadPath = this.forloadPath + payFolder;

			// 3.创建报文文件并将批量数据写入文件
			writeFile(data, forloadPath, fileName);
			logger.info("生产批量请求文件成功，文件名：" + fileName);
			// 招行ftp配置对象
			FtpConfig cmbFtpConfig = ConfigUtil.getInstance().getCmbFtpConfig();

			// 4.上传至招行的FTP服务器(上传成功之后 删除临时文件)
			dealFlag = uploadFile(trustBizReqVo, forloadPath, fileName, null,
					cmbFtpConfig.getFtpUploadPath(), cmbFtpConfig);

			// 如果上传成功,返回批量生产报文成功
			if (dealFlag) {
				logger.info("上传批量请求文件成功!"+"["+cmbFtpConfig.getFtpServer()+"]");
				response = new Response(
						Constants.CmbConstants.TRADE_STATE_SUCCESS.getCode(),
						Constants.CmbConstants.TRADE_STATE_SUCCESS.getDesc());

				// 将生产的文件名称返回
				attachmentResponse.setAttachment(fileName);

				// 批量更新操作对象
				batchOper = new BatchOper(String.valueOf(trustBizReqVo
						.getTradeFlow()));
				// 记录生产报文FileName
				batchOper.setReqFileName(fileName);
				// 更新批量操作请求记录内请求报文FileName
				finalResultHandler(batchOper);
			} else {
				logger.error("上传批量请求文件失败!"+"["+cmbFtpConfig.getFtpServer()+"]");
				response = new Response(
						Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
						Constants.CmbConstants.TRADE_STATE_FAILER.getDesc());
			}

			// 设置请求流水号号
			BeanUtils.copyProperties(attachmentResponse, response);

			// 删除临时文件
			deleteFile(forloadPath + fileName);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				/*
				 * response = new Response(
				 * PlatformErrorCode.CHANNEL_FILE_MKDIR_ERROR.getErrorCode(),
				 * ExceptionUtil.getExceptionMessage(e));
				 */
				response = new Response(
						Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
						Constants.CmbConstants.TRADE_STATE_FAILER.getDesc());
				// 设置操作流水号
				BeanUtils.copyProperties(attachmentResponse, response);
			} catch (Exception e1) {
				logger.error(e.getMessage(), e);
			}
			return attachmentResponse;
		}
		
		logger.info("生产批量请求文件接口执行完毕!");
		response.setFlowId(trustBizReqVo.getChannelReqId());
		return attachmentResponse;
	}

	/**
	 * 下载报文件
	 * 
	 * @param trustBizReqVo
	 * @return
	 */
	public Response pullBatch(TrustBizReqVo trustBizReqVo) {
		Response response = new Response(
				Constants.CmbConstants.TRADE_STATE_SUCCESS.getCode(),
				Constants.CmbConstants.TRADE_STATE_SUCCESS.getDesc());

		// 处理标识
		boolean dealFlag = false;
		try {
			logger.info("拉取文件请求开始........");
			FtpConfig cmbFtpConfig = ConfigUtil.getInstance().getCmbFtpConfig();

			FtpConfig tppFtpConfig = ConfigUtil.getInstance().getTppFtpConfig();

			// 文件名称
			String fileName = trustBizReqVo.getSpare();
			// 服务器下载地址：
			String ftpDownloadPath = cmbFtpConfig.getFtpDownloadPath();
			logger.info("拉取文件名:"+fileName);
			logger.info("服务器下载地址:"+ftpDownloadPath+",["+cmbFtpConfig.getFtpServer()+"]");
			String payFolder = trustBizReqVo.getThirdType().getCode()
					+ File.separator;
			// 文件拉取存放地址(本地)
			String localFilePath = downloadPath + payFolder;
			logger.info("文件拉取存放地址(本地):"+localFilePath);
			// 从招行FTP拉取报文件
			dealFlag = downloadFile(trustBizReqVo, ftpDownloadPath,
					localFilePath, cmbFtpConfig, fileName);
			if (!dealFlag) {
				logger.info("从招行FTP拉取文件失败!"+"["+cmbFtpConfig.getFtpServer()+"]");
				return new Response(
						Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
						Constants.CmbConstants.TRADE_STATE_FAILER.getDesc());
			}

			// 上传TPP的FTP目标目录
			String targetPath = tppFtpConfig.getFtpUploadPath();

			// 上传TPP的FTP服务器
			dealFlag = uploadFile(trustBizReqVo, localFilePath, fileName,
					payFolder, targetPath, tppFtpConfig);

			if (!dealFlag) {
				logger.info("向TPP系统的FTP上传文件失败!"+"["+tppFtpConfig.getFtpServer()+"]");
				response = new Response(
						Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
						Constants.CmbConstants.TRADE_STATE_FAILER.getDesc());
			}else{
				logger.info("向TPP系统的FTP上传文件成功!"+"["+tppFtpConfig.getFtpServer()+"]");
			}
		} catch (Exception e) {

			logger.error("拉取文件接口，上传下载操作异常:", e);
			/*
			 * response = new Response(
			 * PlatformErrorCode.CHANNEL_FILE_DOWNLOAD_ERROR.getErrorCode(),
			 * ExceptionUtil.getExceptionMessage(e));
			 */
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
					Constants.CmbConstants.TRADE_STATE_FAILER.getDesc());
		}
		logger.info("拉取批量请求文件接口执行完毕!");
		// 设置操作流水号
		response.setFlowId(trustBizReqVo.getChannelReqId());
		return response;
	}

	/**
	 * 解析批量报文
	 * 
	 * @param path
	 * @param trustBizReqVo
	 * @return
	 */
	public Response parseBatch(TrustBizReqVo trustBizReqVo) {
		Response response = null;
		BatchOper batchOper = null;
		try {
			logger.info("解析文件请求开始........");
			// 获取第三方响应文件名
			String fileName = trustBizReqVo.getSpare();
			logger.info("第三方响应文件名:"+fileName);
			// 第三方通道目录文件夹
			String payFolder = trustBizReqVo.getThirdType().getCode()
					+ File.separator;
			
			// 获取FTP的文件存放地址，并拼接成文件路径信息
			String localFilePath = this.downloadPath + payFolder;
			logger.info("本地文件存放地址:"+localFilePath);
			// 如果本地文件夹不存在，则创建
			File file = new File(localFilePath);
			// 判断文件夹是否存在
			if (!file.exists()) {
				// 创建文件夹失败 抛出创建下载文件夹失败
				if (!file.mkdirs()) {
					logger.info("本地文件夹不存在时，创建本地文件夹失败!!!!");
					throw new PlatformException(
							PlatformErrorCode.CHANNEL_FILE_MKDIR_ERROR,
							PlatformErrorCode.CHANNEL_FILE_MKDIR_ERROR
									.getDefaultMessage());
				}
			}

			// ftp配置对象
			FtpConfig tppFtpConfig = ConfigUtil.getInstance().getTppFtpConfig();

			// 1.(判断本地是否已存在此文件，如果没有则至TPP的FTP下载)下载文件
			File localFile = new File(localFilePath + fileName);
			if (!localFile.exists()) {
				logger.info("本地待解析文件不存在，去TPP的FTP上下载....."+"["+tppFtpConfig.getFtpServer()+"]");
				boolean flag = downloadFile(trustBizReqVo,
						tppFtpConfig.getFtpUploadPath() + payFolder,
						localFilePath, tppFtpConfig, fileName);

				// 文件下载失败，解析命令失败 执行结束
				if (!flag) {
					logger.info("(TPP)FTP待解析文件下载失败,文件：" + fileName);

					throw new PlatformException(
							PlatformErrorCode.CHANNEL_FILE_DOWNLOAD_ERROR,
							PlatformErrorCode.CHANNEL_FILE_DOWNLOAD_ERROR
									.getDefaultMessage());
				}
			}

			localFile = new File(localFilePath + fileName);
			if (localFile.length() <= 0) {
				logger.info("报文文件内容为空,文件：" + fileName);
				throw new PlatformException(
						PlatformErrorCode.CHANNEL_FILE_PARSE_ERROR,
						PlatformErrorCode.CHANNEL_FILE_PARSE_ERROR
								.getDefaultMessage());
			}

			// 获取FTP的文件存放地址，并拼接成文件路径信息 ()
			String forloadPath = this.forloadPath + payFolder;
			logger.info("待解析文件地址:"+localFilePath + fileName);
			logger.info("开始解析报文文件，并写入本地待上传目录:"+forloadPath);
			// 2.解析第三方报文文件并写入本地 待upload目录
			writeFile(parseHandle(trustBizReqVo, localFilePath + fileName),
					forloadPath, fileName);
			
			
			String targetPath = tppFtpConfig.getFtpDownloadPath();
			logger.info("上传FTP的目录地址:"+targetPath);
			// 3.上传至TPP支付系统的FTP服务器(由TPP转发系统将文件上传至资金托管第三方系统)
			boolean parseFlag = uploadFile(trustBizReqVo, forloadPath,
					fileName, payFolder, targetPath, tppFtpConfig);
			
			// 如果上传成功,返回批量生产报文成功
			if (parseFlag) {
				logger.info("上传解析后文件成功."+"["+tppFtpConfig.getFtpServer()+"]");
				response = new Response(
						Constants.CmbConstants.TRADE_STATE_SUCCESS.getCode(),
						Constants.CmbConstants.TRADE_STATE_SUCCESS.getDesc());

				/*// 批量更新操作对象
				batchOper = new BatchOper(String.valueOf(trustBizReqVo
						.getBatchNo()));
				// 报文解析后处理FileName
				batchOper.setReqFileName(fileName);
				// 更新批量操作请求记录内 报文解析后处理 FileName
				finalResultHandler(batchOper);
				logger.info("更新批次表成功.");*/

			} else {
				logger.info("上传解析后文件失败!!!"+"["+tppFtpConfig.getFtpServer()+"]");
				response = new Response(
						Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
						Constants.CmbConstants.TRADE_STATE_FAILER.getDesc());
			}

			// 删除临时文件
			deleteFile(forloadPath + fileName);

		} catch (Exception e) {
			logger.error("解析文件接口出现异常:", e);
			/*
			 * response = new Response(
			 * PlatformErrorCode.CHANNEL_FILE_PARSE_ERROR.getErrorCode(),
			 * ExceptionUtil.getExceptionMessage(e));
			 */
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
					Constants.CmbConstants.TRADE_STATE_FAILER.getDesc());
			// 设置操作流水号
		}
		logger.info("解析批量请求文件接口执行完毕!");
		response.setFlowId(trustBizReqVo.getChannelReqId());
		return response;
	}

	/**
	 * 组建批量报文件名称
	 * 
	 * @return fileName
	 */
	public String getFileName(TrustBizReqVo trustBizReqVo) {
		CmbConfig cmbConfig = ConfigUtil.getInstance().getCmbConfig();
		FileNameDto fileNameDto = new FileNameDto();

		// 根据信息类别编码去查询信息类别表
		SysInfoCategory infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap
				.get(trustBizReqVo.getInfoCategoryCode());

		// 根据通道编号 + 商户类型 取得通道信息对象
		SysThirdChannelInfo channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap
				.get(trustBizReqVo.getThirdType().getCode()
						+ infoCategory.getMerchantType());

		fileNameDto.setPacketMark(cmbConfig.getCmbFlag()); // 文件标识
		fileNameDto.setTradeCode(trustBizReqVo.getTrustBizType().getCode()); // 交易码
		fileNameDto.setBatchNo(String.valueOf(trustBizReqVo.getBatchNo())); // 批次号
		fileNameDto.setCmbRequestFlag(cmbConfig.getCmbRequestFlag()); // 请求标识
		fileNameDto.setMerchantDate(CalendarUtils.getShortFormatNow()); // 交易日期
		fileNameDto.setOrganzition(channelInfo.getMerchantNo()); // 请求机构号
		fileNameDto.setStructureFlag(cmbConfig.getStructureFlag()); // 文件结构标识
		fileNameDto.setCmbFileType(cmbConfig.getCmbFileType()); // 扩展名

		// 返回组装的报文文件名
		return ConvertUtils.objToMessage(fileNameDto);
	}

	/** 发送前业务处理 */
	protected abstract List preProcess(TrustBizReqVo trustBizReqVo)
			throws PlatformException;

	/**
	 * 写报文文件
	 * 
	 * @param data
	 * @param fileName
	 * @return File
	 * @throws Exception
	 */
	protected abstract void writeFile(List data, String forloadPath,
			String fileName) throws Exception;

	/**
	 * 上传生产批量报文件
	 * 
	 * @param batchFile
	 * @param fileName
	 * @param targetFtpPath
	 *            ftp目的目录
	 * @return true/false
	 * @throws IOException
	 * @throws SocketException
	 */
	public boolean uploadFile(TrustBizReqVo trustBizReqVo, String forloadPath,
			String fileName, String payFolder, String targetFtpPath,
			FtpConfig ftpConfig) throws SocketException, IOException {

		String uploadPath = targetFtpPath;
		// ftp upload
		FTPUtil ftpUtil = new FTPUtil();
		// 连接FTP服务器
		boolean isConnected = ftpUtil.connectServer(ftpConfig.getFtpServer(), ftpConfig.getFtpPort(),
				ftpConfig.getFtpUsername(), ftpConfig.getFtpPwd(),
				targetFtpPath);
		if(!isConnected) return isConnected;
		
		logger.info("连接FTP服务器成功!"+"["+ftpConfig.getFtpServer()+"]");
		if (payFolder != null) {

			// 创建payFolder文件夹
			if (!ftpUtil.existDirectory(targetFtpPath + payFolder)) {

				boolean createFolderFlag = ftpUtil.createDirectory(payFolder);
				logger.info("创建FTP路径至" + payFolder + " ：" + createFolderFlag);
			}

			uploadPath = targetFtpPath + payFolder;
		}

		boolean changeFlag = ftpUtil.changeDirectory(uploadPath);
		// 更换至第三方通道目录文件夹下
		logger.info("切换FTP路径至" + uploadPath + " ：" + changeFlag);

		// 如果切换路径失败，因为上传路径不存在或创建失败造成，上传文件失败
		if (!changeFlag) {
			logger.error(PlatformErrorCode.CHANNEL_FTP_CHANGE_PATH_ERROR
							.getDefaultMessage());
			throw new PlatformException(
					PlatformErrorCode.CHANNEL_FTP_CHANGE_PATH_ERROR,
					PlatformErrorCode.CHANNEL_FTP_CHANGE_PATH_ERROR
							.getDefaultMessage());
		}

		// 上传本服务器上的文件
		return ftpUtil.uploadFile(new FileInputStream(new File(forloadPath
				+ fileName)), fileName);
	}

	/**
	 * 从FTP服务器上下载资金托管返回的报文文件
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 * @throws SocketException
	 */
	public boolean downloadFile(TrustBizReqVo trustBizReqVo, String savePath,
			String fileName) throws SocketException, IOException {

		FtpConfig ftpConfig = ConfigUtil.getInstance().getTppFtpConfig();

		// 第三方通道目录文件夹
		String payFolder = trustBizReqVo.getThirdType().getCode()
				+ File.separator;

		// ftp download
		FTPUtil ftpUtil = new FTPUtil();
		ftpUtil.connectServer(ftpConfig.getFtpServer(), ftpConfig.getFtpPort(),
				ftpConfig.getFtpUsername(), ftpConfig.getFtpPwd(),
				ftpConfig.getFtpDownloadPath());
		ftpUtil.changeDirectory(ftpConfig.getFtpUploadPath() + payFolder);
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		// 从TPP的FTP下载银行响应报文文件
		return ftpUtil.download(fileName, savePath);
	}

	/**
	 * 从FTP服务器上下载资金托管返回的报文文件
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 * @throws SocketException
	 */
	public boolean downloadFile(TrustBizReqVo trustBizReqVo,
			String downloadPath, String localFilePath, FtpConfig ftpConfig,
			String fileName) throws SocketException, IOException {

		// ftp download
		FTPUtil ftpUtil = new FTPUtil();
		boolean isConnected = ftpUtil.connectServer(ftpConfig.getFtpServer(), ftpConfig.getFtpPort(),
				ftpConfig.getFtpUsername(), ftpConfig.getFtpPwd(),
				ftpConfig.getFtpDownloadPath());
		if(!isConnected) return isConnected;
		ftpUtil.changeDirectory(downloadPath);
		File file = new File(localFilePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		// FTP下载报文文件
		return ftpUtil.download(fileName, localFilePath + fileName);
	}

	/**
	 * 解析报文内容
	 * 
	 * @param trustBizReqVo
	 * @param filePath
	 * @return
	 */
	protected abstract List<String> parseHandle(TrustBizReqVo trustBizReqVo,
			String filePath);

	/**
	 * 更新操作
	 * 
	 * @param batchOper
	 */
	public boolean finalResultHandler(BatchOper batchOper) {

		return batchOperService.update(batchOper);
	}

	/**
	 * 删除临时文件处理
	 * 
	 * @param path
	 *            文件路径
	 */
	public void deleteFile(String path) {
		FileUtils.delete(path);
	}

}
