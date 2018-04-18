package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 




import com.zdmoney.manager.mapper.TThreadPoolMapper;
import com.zdmoney.manager.models.TThreadPool;
import com.zdmoney.manager.service.TThreadPoolService;
 
/**
 * 
 * @author wyj
 *
 */
@Service
public class TThreadPoolServiceImpl implements TThreadPoolService{
	
	@Autowired
	private TThreadPoolMapper threadMapper;

	@Override
	public List<Map> getThreadPoolList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return threadMapper.getThreadPoolList(params);
	}

	@Override
	public int insert(TThreadPool thread) {
		// TODO Auto-generated method stub
		return threadMapper.insert(thread);
	}

	@Override
	public int updateThreadPool(TThreadPool thread) {
		// TODO Auto-generated method stub
		return threadMapper.updateThreadPool(thread);
	}

	@Override
	public TThreadPool selectThreadPoolByID(long id) {
		// TODO Auto-generated method stub
		return threadMapper.selectThreadPoolByID(id);
	}

	@Override
	public int getThreadPoolListCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return threadMapper.getThreadPoolListCount(params);
	}

	@Override
	public int batchDeleteInfo(List<Integer> list) {
		// TODO Auto-generated method stub
		return threadMapper.batchDeleteInfo(list);
	}

	@Override
	public String getThreadPoolCount(TThreadPool tp) {
		// TODO Auto-generated method stub
		return threadMapper.getThreadPoolCount(tp);
	}

	@Override
	public int updateThreadPoolActive(TThreadPool ips) {
		return threadMapper.updateThreadPoolActive(ips);
	}

	
}
