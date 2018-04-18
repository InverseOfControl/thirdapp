package com.zendaimoney.thirdpp.query.entity;

import com.zendaimoney.thirdpp.query.mongo.MongoQueryVO;


public class Response {

	/**
	 * 是否有待发数据
	 */
	private boolean isEmpty;

	/**
	 * 业务对象基类
	 */
	private MongoQueryVO task;

	public Response() {

	}

	public Response(boolean isEmpty, MongoQueryVO task) {
		this.isEmpty = isEmpty;
		this.task = task;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public MongoQueryVO getTask() {
		return task;
	}

	public void setTask(MongoQueryVO task) {
		this.task = task;
	}

}
