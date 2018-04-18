package com.zdmoney.manager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TBankInfoMapper;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.service.TBankInfoService;

/**
 * 
 * @author wyj
 *
 */
@Service
public class TBankInfoServiceImpl implements TBankInfoService{
	
	@Autowired
	private TBankInfoMapper tBankInfoMapper;

	@Override
	public List<Map> getBankInfoList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return tBankInfoMapper.getBankInfoList(params);
	}

	@Override
	public int insert(TBankInfo bankInfo) {
		// TODO Auto-generated method stub
		return tBankInfoMapper.insert(bankInfo);
	}

	@Override
	public int updateBankInfo(TBankInfo bankInfo) {
		// TODO Auto-generated method stub
		return tBankInfoMapper.updateBankInfo(bankInfo);
	}

	@Override
	public TBankInfo selectBankInfoByID(long id) {
		// TODO Auto-generated method stub
		return tBankInfoMapper.selectBankInfoByID(id);
	}

	@Override
	public int getBankInfoListCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return tBankInfoMapper.getBankInfoListCount(params);
	}

	@Override
	public int batchDeleteInfo(List<Integer> list) {
		// TODO Auto-generated method stub
		return tBankInfoMapper.batchDeleteInfo(list);
	}

	@Override
	public List<TBankInfo> selectAllBnkInfo() {
		// TODO Auto-generated method stub
		return tBankInfoMapper.selectAllBnkInfo();
	}

	@Override
	public int getBankCodeCount(String bankCode) {
		// TODO Auto-generated method stub
		return tBankInfoMapper.getBankCodeCount(bankCode);
	}

	@Override
	public Map<String, String> getBnkInfoMap() {
		List<String> listbank=tBankInfoMapper.getBnkInfoMap();
		Map<String, String> map=new HashMap<String, String>();
		for(String code : listbank){
			map.put(code, code);
		}
		return map;
	}

}
