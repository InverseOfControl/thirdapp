package com.zendaimoney.trust.channel.entity;

import com.zendaimoney.trust.channel.entity.cmb.Header;
import com.zendaimoney.trust.channel.pub.vo.resp.CommonResp;

/**
 * 报文信息传输对象Dto
 * @author mencius
 *
 */
public class MessageDto {

	// 报文头
	private Header header;
	
	// 报文体
	private CommonResp respVO;
	
	/**
	 * 构造器
	 * @param header
	 * @param respVO
	 */
	public MessageDto(Header header, CommonResp respVO) {
		this.header = header;
		this.respVO = respVO;
	}
	
	public Header getHeader() {
		return header;
	}
	
	public void setHeader(Header header) {
		this.header = header;
	}
	
	public CommonResp getRespVO() {
		return respVO;
	}
	
	public void setRespVO(CommonResp respVO) {
		this.respVO = respVO;
	}
}
