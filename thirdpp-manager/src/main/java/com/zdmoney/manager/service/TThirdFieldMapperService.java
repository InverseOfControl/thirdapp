package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.mapper.TThirdFieldMapperMapper;
import com.zdmoney.manager.models.TThirdFieldMapper;
 

/**
 * 
 * @author wyj
 *
 */
public interface TThirdFieldMapperService {
	
List<Map> getThirdFieldList(Map<String, Object> params);

	
	int insert(TThirdFieldMapper third);
	
	int updateThirdField(TThirdFieldMapper third);
	
	TThirdFieldMapper selectThirdFieldByID(long id);

	int getThirdFieldListCount(Map<String, Object> params);
	
	int batchDeleteInfo(List<Integer> list);
	int updateStatus(TThirdFieldMapper third);
	List<TThirdFieldMapper> getThirdField(TThirdFieldMapper third);
}
