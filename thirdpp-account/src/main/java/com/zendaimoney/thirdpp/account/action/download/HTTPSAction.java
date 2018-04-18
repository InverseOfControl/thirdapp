package com.zendaimoney.thirdpp.account.action.download;

import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.account.annotation.DownloadActionAnnotation;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;
import com.zendaimoney.thirdpp.account.util.Constants;

@DownloadActionAnnotation(fetchMethod = Constants.FETCH_METHOD_HTTPS)
@Component("com.zendaimoney.thirdpp.account.action.download.HTTPSAction")
public class HTTPSAction extends DownloadAction {

	@Override
	public void download(ChannelAccountConfig config, ChannelAccountRequest request, boolean isHandle) {
	}

}
