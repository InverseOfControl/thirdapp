package com.zendaimoney.thirdpp.trade.pub.vo.req.query;

/**
 * 省份（直辖市）下属的市（县）查询请求类
 * 即 ：根据指定省份（直辖市）的 区域编码， 获得该省份（直辖市）下属的市（县）列表。
 * 
 * 查询条件 ：{
 * 		bankOrganizationProvinceNo - 省份（直辖市）的 区域编码
 * } 该属性不可或缺
 * 
 * @author 00237071
 *
 */
public class BankAreaCityQueryReqVo extends BaseQueryRequestVo {


	private static final long serialVersionUID = -230183876847259031L;
	
	// 省份（直辖市）的 区域编码
	private String bankOrganizationProvinceNo;

	public String getBankOrganizationProvinceNo() {
		return bankOrganizationProvinceNo;
	}

	public void setBankOrganizationProvinceNo(String bankOrganizationProvinceNo) {
		this.bankOrganizationProvinceNo = bankOrganizationProvinceNo;
	}
	
}
