package com.zendaimoney.thirdpp.channel.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;
import com.zendaimoney.thirdpp.common.vo.Response;

/**
 * 第三方银行请求过滤器
 * 
 * @author liuyi
 *
 */
@Service
public class BankRequestFilter implements RequestFilter {

	@Override
	public Response doFilter(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 第三方支持银行过滤
	 */
	@Override
	public Response bankFilter(Object object, String bankCode) {
		Response response = new Response(Response.SUCCESS_RESPONSE_CODE);
		if (object == null) {
			response = new Response(
					PlatformErrorCode.CHANNEL_NOT_FOUND_ERROR.getErrorCode(),
					PlatformErrorCode.CHANNEL_NOT_FOUND_ERROR.getDefaultMessage());
			
		} else if (!StringUtils.isBlank(bankCode)){
			BizReqVo bizReqVo = (BizReqVo) object;
			// 第三方支付银行渠道当前有效状态 0：银行通道关闭  1：银行通道开启
			Object statusObj = ThirdPPCacheContainer.thirdBankStatusMap.get(bizReqVo.getThirdType().getCode() + bankCode);
			if (statusObj != null && !StringUtils.isBlank(statusObj.toString())) {
				int status =  Integer.valueOf(statusObj.toString());
				if (status != 1) {
					response = new Response(
							PlatformErrorCode.CHANNEL_PAY_BANK_CLOSE.getErrorCode(),
							PlatformErrorCode.CHANNEL_PAY_BANK_CLOSE.getDefaultMessage());
				}
			}
		}

		return response;
	}

}
