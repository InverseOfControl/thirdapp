package com.zendaimoney.thirdpp.route.transfer.dao;

import java.sql.SQLException;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.route.transfer.entity.CollectTask;

@Repository
public class CollectTaskDao extends BaseDao<CollectTask> {
	// CollectTask实体类数据库操作前缀.
	private static final String SQL_PREFIX_COLLECTTASK = "com.zendaimoney.thirdpp.route.transfer.entity.CollectTask";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	/**
	 * 获取待发送任务
	 * 
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public CollectTask queryRouteTask(HashMap<String, Object> param)
			throws SQLException {
		CollectTask collectTask = (CollectTask) this.sqlMapClient
				.queryForObject(SQL_PREFIX_COLLECTTASK
						+ ".queryRouteTask",param);

		return collectTask;
	}
	
	public int queryTaskCount(HashMap<String, Object> param) throws SQLException {
	    int count = 0;
	    Object object = this.sqlMapClient.queryForObject(SQL_PREFIX_COLLECTTASK+ ".queryTaskCount",param);
	    if(object !=null){
	        count = (Integer)object;
	    }
	    return count;
	}
}
