package com.zendaimoney.thirdpp.account.action.download;

import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;

public abstract class DownloadAction {

	public abstract void download(ChannelAccountConfig config, ChannelAccountRequest request, boolean isHandle);
	
}
