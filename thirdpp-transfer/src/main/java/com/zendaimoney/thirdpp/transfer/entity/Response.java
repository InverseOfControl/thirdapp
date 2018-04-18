package com.zendaimoney.thirdpp.transfer.entity;

import com.zendaimoney.thirdpp.transfer.entity.collect.CollectTask;

public class Response {

	/**
	 * 是否有待发数据
	 */
	private boolean isEmpty;

	/**
	 * 业务对象基类
	 */
	private CollectTask task;

	public Response() {

	}

	public Response(boolean isEmpty, CollectTask task) {
		this.isEmpty = isEmpty;
		this.task = task;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public CollectTask getTask() {
		return task;
	}

	public void setTask(CollectTask task) {
		this.task = task;
	}

}
