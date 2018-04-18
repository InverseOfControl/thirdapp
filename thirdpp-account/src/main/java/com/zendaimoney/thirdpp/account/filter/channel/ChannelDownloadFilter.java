package com.zendaimoney.thirdpp.account.filter.channel;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.account.action.download.DownloadAction;
import com.zendaimoney.thirdpp.account.annotation.ChannelFilterAnnotation;
import com.zendaimoney.thirdpp.account.conf.ServerConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;
import com.zendaimoney.thirdpp.account.exception.PlatformException;
import com.zendaimoney.thirdpp.account.service.ChannelAccountRequestService;
import com.zendaimoney.thirdpp.account.util.ActionMapperUtil;
import com.zendaimoney.thirdpp.account.util.Constants;

@ChannelFilterAnnotation(filterStep = Constants.ChannelFilter.FILTER_ANNOTATION_DOWNLOAD)
@Component(value = "com.zendaimoney.thirdpp.account.filter.channel.ChannelDownloadFilter")
public class ChannelDownloadFilter implements ChannelFilter {

	private static final Log logger = LogFactory.getLog(ChannelDownloadFilter.class);
	@Autowired
	public ChannelAccountRequestService channelAccountRequestService;
	
	@Override
	public void doFilter(ChannelAccountConfig config, ChannelAccountRequest request, ChannelFilterChain chain, boolean isHandle) {
		String currentThreadName = Thread.currentThread().getName();
		if (canDownload(config, request)) {
			try {
				startDownloadOperation(request);
			} catch (Exception e) {
				request.setDownloadStartTime(null);
				request.setDownloadFailedTimes(request.getDownloadFailedTimes() + 1);
				if (isHandle) {
					request.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_FAILED);
				}
				request.setStatus(Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_DOWNLOAD_FILE_FAILED);
				request.setFailedReason(StringUtils.substring(e.getMessage(),0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH));
				channelAccountRequestService.update(request);
				throw new PlatformException("通道对账-下载对账文件-更新下载开始时间异常", e);
			}
			
			if (isHandle) {
				logger.info("通道【手工】对账下载对账文件开始......");
			} else {
				logger.info(currentThreadName + "通道对账下载对账文件开始......");
			}
			long currentStartTime = System.currentTimeMillis();
			
			downloadAccountFile(config, request, isHandle);
			
			long currentEndTime = System.currentTimeMillis();
			if (isHandle) {
				logger.info("通道【手工】对账下载对账文件结束耗时(" + (currentEndTime - currentStartTime) + ")......");
			} else {
				logger.info(currentThreadName + "通道对账下载对账文件结束耗时(" + (currentEndTime - currentStartTime) + ")......");
			}
		}
		chain.doFilter(config, request, Constants.ChannelFilter.FILTER_ANNOTATION_DOWNLOAD, isHandle);
	}

	private void startDownloadOperation(ChannelAccountRequest request){
		if (request.getDownloadStartTime() == null) {
			request.setDownloadStartTime(new Date());
			channelAccountRequestService.update(request);
		}
	}
	
	public boolean canDownload(ChannelAccountConfig config, ChannelAccountRequest request){
		boolean flag = true;
		int status = request.getStatus();
		if (Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_DOWNLOAD_FILE_FAILED != status
				&& Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_INITIAL != status) {
			flag = false;
		}
		return flag;
	}
	
	private void downloadAccountFile(ChannelAccountConfig config, ChannelAccountRequest request, boolean isHandle) {
		Class<? extends DownloadAction> da = ActionMapperUtil.downloadActionMap
				.get(config.getFetchMethod()).getActionClazz();
		DownloadAction downloadAction = (DownloadAction) ServerConfig
				.getBean(da.getName());
		downloadAction.download(config, request, isHandle);
	}
}
