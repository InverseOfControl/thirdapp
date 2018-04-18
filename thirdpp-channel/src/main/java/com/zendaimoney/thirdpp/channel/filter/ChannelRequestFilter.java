package com.zendaimoney.thirdpp.channel.filter;

import org.springframework.stereotype.Service;

import com.zendaimoney.thirdpp.channel.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;
import com.zendaimoney.thirdpp.common.vo.Response;

/**
 * 通道层请求过滤器
 * 
 * @author mencius
 *
 */
@Service
public class ChannelRequestFilter implements RequestFilter {

	@Override
	public Response doFilter(Object object) {
        
		Response response = new Response(Response.SUCCESS_RESPONSE_CODE);
		if (object == null) {
			response = new Response(
					PlatformErrorCode.CHANNEL_NOT_FOUND_ERROR.getErrorCode(),
					PlatformErrorCode.CHANNEL_NOT_FOUND_ERROR
							.getDefaultMessage());
		} else {

			BizReqVo bizReqVo = (BizReqVo) object;
			// 根据信息类别编码去查询信息类别表
			SysInfoCategory infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap
					.get(bizReqVo.getInfoCategoryCode());

			// 如果缓存Map中不存在信息类型对象，则抛出异常 - 请求通道无法获取
			if (infoCategory == null) {
				response = new Response(
						PlatformErrorCode.CHANNEL_NOT_FOUND_ERROR
								.getErrorCode(),
						PlatformErrorCode.CHANNEL_NOT_FOUND_ERROR
								.getDefaultMessage());
			} else {
				// key= 第三方通道编码 + 商户号类型
				SysThirdChannelInfo channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap
						.get(bizReqVo.getThirdType().getCode()
								+ infoCategory.getMerchantType());

				// 如果通道信息为空，则抛出异常
				if (channelInfo == null) {
					response = new Response(
							PlatformErrorCode.CHANNEL_NOT_FOUND_ERROR
									.getErrorCode(),
							PlatformErrorCode.CHANNEL_NOT_FOUND_ERROR
									.getDefaultMessage());
				}
			}
		}

		return response;
	}

	@Override
	public Response bankFilter(Object object, String bankCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
