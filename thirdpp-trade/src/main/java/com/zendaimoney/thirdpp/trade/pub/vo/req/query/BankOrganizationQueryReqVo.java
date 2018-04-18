package com.zendaimoney.thirdpp.trade.pub.vo.req.query;

/**
 * 查询区域所属支行查询请求类
 * 即 ：根据总行编码，省份（直辖市）的 区域编码，省份（直辖市）下属的市（县）区域编码，获得该市（县）区域下，总行的所有支行信息列表
 * 查询条件：{
 * 		a，bankCode - 支行所属的总行的编码。
 *      b，bankOrganizationProvinceNo - 省份（直辖市）的 区域编码。
 * 		c，bankOrganizationCityNo - 省份（直辖市）下属的市（县）区域编码
 * } 三者缺一不可
 * 
 * @author 00237071
 *
 */
public class BankOrganizationQueryReqVo extends BaseQueryRequestVo {


	private static final long serialVersionUID = -5599939237226300956L;
	

	// 总行编码
	private String bankCode;
	
	// 省份（直辖市）的 区域编码
	private String bankOrganizationProvinceNo;
	
	// 省份（直辖市）下属的市（县）区域编码
	private String bankOrganizationCityNo;

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankOrganizationProvinceNo() {
		return bankOrganizationProvinceNo;
	}

	public void setBankOrganizationProvinceNo(String bankOrganizationProvinceNo) {
		this.bankOrganizationProvinceNo = bankOrganizationProvinceNo;
	}

	public String getBankOrganizationCityNo() {
		return bankOrganizationCityNo;
	}

	public void setBankOrganizationCityNo(String bankOrganizationCityNo) {
		this.bankOrganizationCityNo = bankOrganizationCityNo;
	}
	
}
