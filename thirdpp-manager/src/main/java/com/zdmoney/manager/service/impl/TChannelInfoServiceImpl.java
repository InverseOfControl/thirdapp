package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TAppIpsMapper;
import com.zdmoney.manager.mapper.TBankInfoMapper;
import com.zdmoney.manager.mapper.TChannelInfoMapper;
import com.zdmoney.manager.mapper.TSysPermissionMapper;
import com.zdmoney.manager.models.TAppIps;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TChannelInfo;
import com.zdmoney.manager.service.TAppIpsService;
import com.zdmoney.manager.service.TBankInfoService;
import com.zdmoney.manager.service.TChannelInfoService;

/**
 * 
 * @author wyj
 *
 */
@Service
public class TChannelInfoServiceImpl implements TChannelInfoService{
	
	@Autowired
	private TChannelInfoMapper channelMapper;

	@Override
	public List<Map> getChannelInfoList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return channelMapper.getChannelInfoList(params);
	}

	@Override
	public int insert(TChannelInfo info) {
		// TODO Auto-generated method stub
		return channelMapper.insert(info);
	}

	@Override
	public int updateChannelInfo(TChannelInfo info) {
		// TODO Auto-generated method stub
		return channelMapper.updateChannelInfo(info);
	}

	@Override
	public TChannelInfo selectChannelInfoByID(long id) {
		// TODO Auto-generated method stub
		return channelMapper.selectChannelInfoByID(id);
	}

	@Override
	public void batchDeleteChannelInfo(List<Integer> listId) {
		channelMapper.batchDeleteChannelInfo(listId);
		
	}

	@Override
	public int getChannelInfoListCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return channelMapper.getChannelInfoListCount(params);
	}

	@Override
	public int selectMerchantTypeCount(TChannelInfo into) {
		// TODO Auto-generated method stub
		return channelMapper.selectMerchantTypeCount(into);
	}

	@Override
	public void updateChannelStatus(TChannelInfo into) {
		// TODO Auto-generated method stub
		 channelMapper.updateChannelStatus(into);
	}


	
}
