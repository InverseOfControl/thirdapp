package com.zdmoney.manager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TAreaInfoMapper;
import com.zdmoney.manager.models.TAreaInfo;
import com.zdmoney.manager.service.TAreaInfoService;



/**
 * 
 * @author wyj
 *
 */
@Service
public class TAreaInfoServiceImpl implements TAreaInfoService{
	
	@Autowired
	private TAreaInfoMapper areaMapper;

	@Override
	public List<Map> getAreaInfoList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return areaMapper.getAreaInfoList(params);
	}

	@Override
	public int insert(TAreaInfo area) {
		// TODO Auto-generated method stub
		return areaMapper.insert(area);
	}

	@Override
	public int updateArea(TAreaInfo area) {
		// TODO Auto-generated method stub
		return areaMapper.updateArea(area);
	}

	@Override
	public TAreaInfo selectAreaByID(long id) {
		// TODO Auto-generated method stub
		return areaMapper.selectAreaByID(id);
	}

	@Override
	public void batchDeleteArea(List<Integer> listId) {
		// TODO Auto-generated method stub
		areaMapper.batchDeleteArea(listId);
	}

	@Override
	public int getAreaListCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return areaMapper.getAreaListCount(params);
	}

	@Override
	public int getAreaCodeCount(String code) {
		// TODO Auto-generated method stub
		return areaMapper.getAreaCodeCount(code);
	}

	@Override
	public int getAreaCodeObj(TAreaInfo area) {
		// TODO Auto-generated method stub
		return  areaMapper.getAreaCodeObj(area) ;
	}

	@Override
	public int updateSonAreaCode(TAreaInfo area) {
		// TODO Auto-generated method stub
		return areaMapper.updateSonAreaCode(area) ;
	}

	@Override
	public TAreaInfo selectAreaByCode(String code) {
		// TODO Auto-generated method stub
		return  areaMapper.selectAreaByCode(code) ;
		 
	}

	@Override
	public Map<String, String> getAreaListMap() {
		List<TAreaInfo>	listAr=areaMapper.getAreaListMap();
		 Map<String, String> map= new HashMap<String, String>();
		 for(TAreaInfo s : listAr){
			 map.put(s.getAreaCode(), s.getParentId());
		 }
		return  map;
	}

	
}
