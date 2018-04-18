package com.zendaimoney.thirdpp.query.thread;

import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.query.action.Action;
import com.zendaimoney.thirdpp.query.mongo.MongoQueryVO;

/**
 * 查询线程
 * 
 */
@SuppressWarnings("rawtypes")
public class QueryActionCallable implements Callable<Response> {

	// 日志工具类
	public static Log logger = LogFactory.getLog(QueryActionCallable.class);

	/**
	 * 查询action
	 */
	private Action action;

	private MongoQueryVO info;
	
	// 当前集合名称
	private String collectionName;

	/**
	 * 
	 * @param action
	 * @param thirdType
	 * @param name
	 */
	public QueryActionCallable(Action action, MongoQueryVO info, String collectionName) {
		this.action = action;
		this.info = info;
		this.collectionName = collectionName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Response call() throws Exception {
		
		Response response = new Response();
		try {
			action.execute(info, collectionName);
		} catch (Exception e) {
			logger.error(info);
			logger.error(e.getMessage(), e);
		}
		
		return response;
	}
}
