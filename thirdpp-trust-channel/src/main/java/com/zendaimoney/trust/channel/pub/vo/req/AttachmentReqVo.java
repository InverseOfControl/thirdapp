package com.zendaimoney.trust.channel.pub.vo.req;

import java.io.Serializable;

/**
 * 批量处理请求对象
 * @author mencius
 *
 */
public class AttachmentReqVo<E> extends TrustBizReqVo implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 泛型
	 */
	private E attachment;


	public E getAttachment() {
		return attachment;
	}


	public void setAttachment(E attachment) {
		this.attachment = attachment;
	}
}
