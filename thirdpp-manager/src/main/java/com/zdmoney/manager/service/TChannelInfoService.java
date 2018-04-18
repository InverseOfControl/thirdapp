package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TAppIps;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TChannelInfo;

/**
 * 
 * @author wyj
 *
 */
public interface TChannelInfoService {
	
	List<Map> getChannelInfoList(Map<String, Object> params);

	
	int insert(TChannelInfo info);
	
	int updateChannelInfo(TChannelInfo info);
	
	TChannelInfo selectChannelInfoByID(long id);
	
	void batchDeleteChannelInfo(List<Integer> listId);
	int getChannelInfoListCount(Map<String, Object> params);
	int	selectMerchantTypeCount(TChannelInfo type);
	 void updateChannelStatus(TChannelInfo into);
}
