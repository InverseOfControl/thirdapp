package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TThirdFieldMapper;


public interface TThirdFieldMapperMapper {
	
	List<Map> getThirdFieldList(Map<String, Object> params);

	
	int insert(TThirdFieldMapper third);
	
	int updateThirdField(TThirdFieldMapper third);
	
	TThirdFieldMapper selectThirdFieldByID(long id);

	int getThirdFieldListCount(Map<String, Object> params);
	int batchDeleteInfo(List<Integer> list) ;
	int updateStatus(TThirdFieldMapper third) ;
	List<TThirdFieldMapper> getThirdField(TThirdFieldMapper third);
}
