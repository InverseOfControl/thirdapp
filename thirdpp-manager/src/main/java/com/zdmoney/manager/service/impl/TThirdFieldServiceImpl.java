package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TBankInfoMapper;
import com.zdmoney.manager.mapper.TSysPermissionMapper;
import com.zdmoney.manager.mapper.TThirdFieldMapperMapper;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TThirdFieldMapper;
import com.zdmoney.manager.service.TBankInfoService;
import com.zdmoney.manager.service.TThirdFieldMapperService;

/**
 * 
 * @author wyj
 *
 */
@Service
public class TThirdFieldServiceImpl implements TThirdFieldMapperService{
	
	@Autowired
	private TThirdFieldMapperMapper thirdFieldMapper;

	@Override
	public List<Map> getThirdFieldList(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return thirdFieldMapper.getThirdFieldList(params);
	}

	@Override
	public int insert(TThirdFieldMapper third) {
		// TODO Auto-generated method stub
		return thirdFieldMapper.insert(third);
	}

	@Override
	public int updateThirdField(TThirdFieldMapper third) {
		// TODO Auto-generated method stub
		return thirdFieldMapper.updateThirdField(third);
	}

	@Override
	public TThirdFieldMapper selectThirdFieldByID(long id) {
		// TODO Auto-generated method stub
		return thirdFieldMapper.selectThirdFieldByID(id);
	}

	@Override
	public int getThirdFieldListCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return thirdFieldMapper.getThirdFieldListCount(params);
	}

	@Override
	public int batchDeleteInfo(List<Integer> list) {
		// TODO Auto-generated method stub
		return thirdFieldMapper.batchDeleteInfo(list);
	}

	@Override
	public int updateStatus(TThirdFieldMapper third) {
		// TODO Auto-generated method stub
		return thirdFieldMapper.updateStatus(third);
	}

	@Override
	public List<TThirdFieldMapper> getThirdField(TThirdFieldMapper third) {
		return thirdFieldMapper.getThirdField(third);
	}

}
