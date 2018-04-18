package com.zendaimoney.thirdpp.common.vo;

public class AttachmentResponse<E> extends Response {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4425832926558967457L;
    
	
	private E attachment;


	public E getAttachment() {
		return attachment;
	}


	public void setAttachment(E attachment) {
		this.attachment = attachment;
	}
	
	

}
