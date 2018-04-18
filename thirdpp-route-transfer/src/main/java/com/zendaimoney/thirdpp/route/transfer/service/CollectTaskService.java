package com.zendaimoney.thirdpp.route.transfer.service;

import java.sql.SQLException;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.route.transfer.dao.CollectTaskDao;
import com.zendaimoney.thirdpp.route.transfer.entity.CollectTask;
import com.zendaimoney.thirdpp.route.transfer.util.Constants;

@Service
@Transactional
public class CollectTaskService {

	@Resource(name = "collectTaskDao")
	private CollectTaskDao collectTaskDao;
	
	/**
	 * collectTask修改操作
	 * 
	 * @param collectTask
	 */
	public int update(CollectTask collectTask) {
	  return collectTaskDao.update(collectTask);
	}
	
    public CollectTask get(HashMap<String, Object> param) throws Exception {
    	
        CollectTask task = null;
        CollectTask updateTask = null;
        // 锁定数
        int lockNum = 0;
        // 获取待发送任务
        task = collectTaskDao.queryRouteTask(param);
        // 如果没有待发送数据
        if (task == null) {
            return null;
        }
        // 锁定该任务
        updateTask = new CollectTask();
        updateTask.setId(task.getId());
        // 设置状态-发送中
        updateTask.setRouteStatus(Integer.valueOf(Constants.TppConstants.ROUTE_STATUS_DOING.getCode()));
        // 发送数量可以是任意值，只要不为空就行
        updateTask.setOldStatus(task.getRouteStatus());
        lockNum = this.update(updateTask);

        // 如果没有锁定任务，则返回选中对象
        if (lockNum <= 0) {
            return null;
        }

        return task;
    }
    
    public int queryTaskCount(HashMap<String, Object> param) throws SQLException {
       return collectTaskDao.queryTaskCount(param);
    }
    

}
