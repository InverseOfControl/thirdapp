package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TAppIps;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TChannelInfo;
import com.zdmoney.manager.models.TSysPermission;
import com.zdmoney.manager.models.TThirdFieldMapper;

public interface TChannelInfoMapper {
	
	List<Map> getChannelInfoList(Map<String, Object> params);

	
	int insert(TChannelInfo info);
	
	int updateChannelInfo(TChannelInfo info);
	
	TChannelInfo selectChannelInfoByID(long id);
	
	void batchDeleteChannelInfo(List<Integer> listId);
	int getChannelInfoListCount(Map<String, Object> params);
	
	int	selectMerchantTypeCount(TChannelInfo into);
	
	 void updateChannelStatus(TChannelInfo into);
}
