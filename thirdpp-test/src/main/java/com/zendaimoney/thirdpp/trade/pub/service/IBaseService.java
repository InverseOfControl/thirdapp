package com.zendaimoney.thirdpp.trade.pub.service;

import java.util.List;

import com.zendaimoney.thirdpp.common.vo.AttachmentResponse;
import com.zendaimoney.thirdpp.trade.pub.vo.req.query.BankAreaCityQueryReqVo;
import com.zendaimoney.thirdpp.trade.pub.vo.req.query.BankOrganizationQueryReqVo;
import com.zendaimoney.thirdpp.trade.pub.vo.req.query.BaseQueryRequestVo;
import com.zendaimoney.thirdpp.trade.pub.vo.req.query.ThirdPartyPayPlatformSupportBankInfoReqVo;
import com.zendaimoney.thirdpp.trade.pub.vo.req.query.ThirdPartyPayPlatformSupportBankReqVo;
import com.zendaimoney.thirdpp.trade.pub.vo.resp.query.BankAreaResponseVo;
import com.zendaimoney.thirdpp.trade.pub.vo.resp.query.BankOrgResponseVo;
import com.zendaimoney.thirdpp.trade.pub.vo.resp.query.BankResponseVo;
import com.zendaimoney.thirdpp.trade.pub.vo.resp.query.ThirdPartyPayPlatformResponseVo;
import com.zendaimoney.thirdpp.trade.pub.vo.resp.query.ThirdPartyPayPlatformSupportBankInfoRespVo;
import com.zendaimoney.thirdpp.trade.pub.vo.resp.query.ThirdPartyPayPlatformSupportBankRespVo;

/**
 * 基础服务
 * @author 00231257
 * @version 1.0 00237071
 *
 */
public interface IBaseService {

	/**
	 * 获得所有支持的银行总行列表
	 * @param vo
	 * @return
	 */
	public AttachmentResponse<List<BankResponseVo>> getSupportedBanks(BaseQueryRequestVo vo);
	
	/**
	 * 获得省份（直辖市）列表
	 * @param vo
	 * @return
	 */
	public AttachmentResponse<List<BankAreaResponseVo>> getBankAreaProvinces(BaseQueryRequestVo vo);
	
	
	/**
	 * 根据指定省份（直辖市）的 区域编码， 获得该省份（直辖市）下属的市（县）列表
	 * @param vo
	 * @return
	 */
	public AttachmentResponse<List<BankAreaResponseVo>> getBankAreaCitysByProvince(BankAreaCityQueryReqVo vo);
	
	/**
	 * 根据总行编码，省份（直辖市）的 区域编码，省份（直辖市）下属的市（县）区域编码，获得该市（县）区域下，总行的所有支行信息列表
	 * @param vo
	 * @return
	 */
	public AttachmentResponse<List<BankOrgResponseVo>> getBankOrganizationsByBankCodeAndBankArea(BankOrganizationQueryReqVo vo);
	
	/**
	 * 获得支持的第三方支付平台
	 * @param vo
	 * @return
	 */
	public AttachmentResponse<List<ThirdPartyPayPlatformResponseVo>> getSupportThirdPartyPayPlatforms(BaseQueryRequestVo vo);
	

	/**
	 * 获得第三方支付平台支持的银行列表
	 * @param vo
	 * @return
	 */
	public AttachmentResponse<List<ThirdPartyPayPlatformSupportBankRespVo>> getSupportBanksByThirdPartyPayPlatform(ThirdPartyPayPlatformSupportBankReqVo vo);
	
	/**
	 * 获得第三方支付平台支持银行的详细信息
	 * @param vo
	 * @return
	 */
	public AttachmentResponse<ThirdPartyPayPlatformSupportBankInfoRespVo> getSupportBankInfoByThirdPartyPayPlatformCodeAndBankCode(
			ThirdPartyPayPlatformSupportBankInfoReqVo vo);
	
}
