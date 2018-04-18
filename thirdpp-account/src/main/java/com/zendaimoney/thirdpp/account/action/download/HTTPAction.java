package com.zendaimoney.thirdpp.account.action.download;

import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.account.annotation.DownloadActionAnnotation;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;
import com.zendaimoney.thirdpp.account.util.Constants;

@DownloadActionAnnotation(fetchMethod = Constants.FETCH_METHOD_HTTP)
@Component("com.zendaimoney.thirdpp.account.action.download.HTTPAction")
public class HTTPAction extends DownloadAction {

	@Override
	public void download(ChannelAccountConfig config, ChannelAccountRequest request, boolean isHandle) {
		
	}

}
