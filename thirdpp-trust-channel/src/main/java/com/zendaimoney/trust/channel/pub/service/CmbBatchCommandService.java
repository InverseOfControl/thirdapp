package com.zendaimoney.trust.channel.pub.service;

import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;

/**
 * 对外暴露：批量(生成/解析)文件处理命令接口
 * @author mencius
 *
 */
public interface CmbBatchCommandService {

	/**
	 * 构建批量报文文件
	 * @param data 报文批数据
	 * @param TrustBizReqVo 请求对象
	 * @return Response
	 */
	public Response build(TrustBizReqVo trustBizReqVo);
	
	/**
	 * 从第三方拉取结果文件
	 * @param trustBizReqVo
	 * @return
	 */
	public Response pull(TrustBizReqVo trustBizReqVo);
	
	/**
	 * 解析批量报文文件
	 * @param path 解析文件路径信息
	 * @param cmbBizType 招商银行业务类型
	 * @return Response
	 */
	public Response parse(TrustBizReqVo trustBizReqVo);
}
