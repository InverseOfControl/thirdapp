package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;




import com.zdmoney.manager.models.TThreadPool;

public interface TThreadPoolService {
	
List<Map> getThreadPoolList(Map<String, Object> params);

	
	int insert(TThreadPool ips);
	
	int updateThreadPool(TThreadPool ips);
	int updateThreadPoolActive(TThreadPool ips);
	TThreadPool selectThreadPoolByID(long id);

	int getThreadPoolListCount(Map<String, Object> params);
	int batchDeleteInfo(List<Integer> list);
	String getThreadPoolCount(TThreadPool tp);
	
}
