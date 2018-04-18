package com.zendaimoney.thirdpp.trade.sei;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zendaimoney.thirdpp.common.vo.AttachmentResponse;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.trade.exception.PlatformErrorCode;
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
import com.zendaimoney.thirdpp.trade.service.AreaInfoService;
import com.zendaimoney.thirdpp.trade.service.BankInfoService;
import com.zendaimoney.thirdpp.trade.service.BankOrgInfoService;
import com.zendaimoney.thirdpp.trade.service.DictionaryService;
import com.zendaimoney.thirdpp.trade.service.SysAppIPSService;
import com.zendaimoney.thirdpp.trade.service.SysAppService;
import com.zendaimoney.thirdpp.trade.service.SysInfoCategoryAPPSService;
import com.zendaimoney.thirdpp.trade.service.SysInfoCategoryService;
import com.zendaimoney.thirdpp.trade.service.ThirdFieldMapperService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml","classpath:spring-dubbo.xml","classpath:applicationContext-task.xml","classpath:spring-rabbitmq-producter.xml" })

public class BaseServiceTest {


	@Autowired
	public SysAppIPSService sysAppIPSService;
	@Autowired
	public AreaInfoService areaInfoService;
	@Autowired
	public BankInfoService bankInfoService;
	@Autowired
	public BankOrgInfoService bankOrgInfoService;
	@Autowired
	public SysAppService sysAppService;
	@Autowired
	private SysInfoCategoryAPPSService sysInfoCategoryAPPSService;
	@Autowired
	private SysInfoCategoryService sysInfoCategoryService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private ThirdFieldMapperService thirdFieldMapperService;
	
	private static BaseServiceImpl baseService = new BaseServiceImpl();
	
	@Test
	public void getBankAreaProvincesWhenRequestIsNull() {
		
		AttachmentResponse<List<BankAreaResponseVo>> response = baseService.getBankAreaProvinces(null);
		
		Assert.assertEquals(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), response.getCode());
	}
	
	@Test
	public void getBankAreaProvincesWhenBizSysNoIsNull() {
		
		// 此处没有设置bizSysNo
		BaseQueryRequestVo requestVo = new BaseQueryRequestVo();
		
		AttachmentResponse<List<BankAreaResponseVo>> response = baseService.getBankAreaProvinces(requestVo);
		
		Assert.assertEquals(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), response.getCode());
	}
	
	@Test
	public void getBankAreaProvincesWhenNoAccessPermission() {
		
		BaseQueryRequestVo requestVo = new BaseQueryRequestVo();
		requestVo.setBizSysNo("004");
		
		AttachmentResponse<List<BankAreaResponseVo>> response = baseService.getBankAreaProvinces(requestVo);
		
		Assert.assertEquals(PlatformErrorCode.IP_ACCESS_APP_PERMISSION_ERROR.getErrorCode(), response.getCode());
	}
	
	@Test
	public void getBankAreaProvinces() {
		
		BaseQueryRequestVo requestVo = new BaseQueryRequestVo();
		requestVo.setBizSysNo("004");
		
		AttachmentResponse<List<BankAreaResponseVo>> response = baseService.getBankAreaProvinces(requestVo);
		List<BankAreaResponseVo> list = response.getAttachment();
		Assert.assertNotNull(list);
	}
	
	@Test
	public void getBankAreaCitysByProvince() {
		
		BankAreaCityQueryReqVo requestVo = new BankAreaCityQueryReqVo();
		requestVo.setBizSysNo("004");
		requestVo.setBankOrganizationProvinceNo("009");
		
		AttachmentResponse<List<BankAreaResponseVo>> response = baseService.getBankAreaCitysByProvince(requestVo);
		List<BankAreaResponseVo> list = response.getAttachment();
		Assert.assertNotNull(list);
	}
	
	@Test
	public void getSupportedBanks() {
		
		BaseQueryRequestVo requestVo = new BaseQueryRequestVo();
		requestVo.setBizSysNo("004");
		
		AttachmentResponse<List<BankResponseVo>> response = baseService.getSupportedBanks(requestVo);
		List<BankResponseVo> list = response.getAttachment();
		for (BankResponseVo vo : list) {
			System.out.println(vo.getBankCode() + ":" + vo.getBankName());
		}
		Assert.assertNotNull(list);
	}
	
	@Test
	public void getSysAppByBizSysNo() {
		
		BaseQueryRequestVo requestVo = new BaseQueryRequestVo();
		// biz
		requestVo.setBizSysNo("001");
		
		AttachmentResponse<List<BankResponseVo>> response = baseService.getSupportedBanks(requestVo);
		Assert.assertEquals(PlatformErrorCode.NOT_EXISTS_SYSTEM.getErrorCode(), response.getCode());
	}
	
	@Test
	public void getBankOrganizationsByBankCodeAndBankAreaWhenNoParameter() {
		
		BankOrganizationQueryReqVo requestVo = new BankOrganizationQueryReqVo();
		requestVo.setBizSysNo("004");
		
		AttachmentResponse<List<BankOrgResponseVo>> response = baseService.getBankOrganizationsByBankCodeAndBankArea(requestVo);
		List<BankOrgResponseVo> list = response.getAttachment();
		Assert.assertEquals(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), response.getCode());
		Assert.assertNull(list);
	}
	
	@Test
	public void getBankOrganizationsByBankCodeAndBankAreaWhenOneParameter() {
		
		BankOrganizationQueryReqVo requestVo = new BankOrganizationQueryReqVo();
		requestVo.setBizSysNo("004");
		requestVo.setBankCode("001");
		AttachmentResponse<List<BankOrgResponseVo>> response = baseService.getBankOrganizationsByBankCodeAndBankArea(requestVo);
		List<BankOrgResponseVo> list = response.getAttachment();
		Assert.assertEquals(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), response.getCode());
		Assert.assertNull(list);
	}
	
	@Test
	public void getBankOrganizationsByBankCodeAndBankAreaWhenTwoParameters() {
		
		BankOrganizationQueryReqVo requestVo = new BankOrganizationQueryReqVo();
		requestVo.setBizSysNo("004");
		requestVo.setBankCode("001");
		requestVo.setBankOrganizationProvinceNo("001");
		AttachmentResponse<List<BankOrgResponseVo>> response = baseService.getBankOrganizationsByBankCodeAndBankArea(requestVo);
		List<BankOrgResponseVo> list = response.getAttachment();
		Assert.assertEquals(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(), response.getCode());
		Assert.assertNull(list);
	}
	
	@Test
	public void getBankOrganizationsByBankCodeAndBankArea() {
		
		BankOrganizationQueryReqVo requestVo = new BankOrganizationQueryReqVo();
		requestVo.setBizSysNo("004");
		requestVo.setBankCode("001");
		requestVo.setBankOrganizationProvinceNo("002");
		requestVo.setBankOrganizationCityNo("003");
		AttachmentResponse<List<BankOrgResponseVo>> response = baseService.getBankOrganizationsByBankCodeAndBankArea(requestVo);
		List<BankOrgResponseVo> list = response.getAttachment();
		Assert.assertNotNull(list);
	}
	
	@Test
	public void getSupportedPayPlatformsHappyPass(){
		BaseQueryRequestVo vo = new BaseQueryRequestVo();
		vo.setBizSysNo("004");
		AttachmentResponse<List<ThirdPartyPayPlatformResponseVo>> response = baseService.getSupportThirdPartyPayPlatforms(vo);
		Assert.assertEquals(Response.SUCCESS_RESPONSE_CODE, response.getCode());
		for (ThirdPartyPayPlatformResponseVo temp : response.getAttachment()) {
			System.out.println(temp.getThirdPartyPayPlatformCode() + ":" + temp.getThirdPartyPayPlatformName());
		}
	}
	
	@Test
	public void getSupportedPayPlatformsBizSysNoNull(){
		BaseQueryRequestVo vo = new BaseQueryRequestVo();
		AttachmentResponse<List<ThirdPartyPayPlatformResponseVo>> response = baseService.getSupportThirdPartyPayPlatforms(vo);
		Assert.assertNull(response.getAttachment());
		Assert.assertEquals("000001", response.getCode());
		System.out.println(response.getCode() + ":" + response.getMsg());
	}
	
	@Test
	public void getSupportedPayPlatformsBizSysNotExists(){
		BaseQueryRequestVo vo = new BaseQueryRequestVo();
		vo.setBizSysNo("0090");
		AttachmentResponse<List<ThirdPartyPayPlatformResponseVo>> response = baseService.getSupportThirdPartyPayPlatforms(vo);
		Assert.assertNull(response.getAttachment());
		Assert.assertEquals("200012", response.getCode());
		System.out.println(response.getCode() + ":" + response.getMsg());
	}
	
	@Test
	public void getSupportedPayPlatformsBizSysNoPermission(){
		BaseQueryRequestVo vo = new BaseQueryRequestVo();
		vo.setBizSysNo("004");
		AttachmentResponse<List<ThirdPartyPayPlatformResponseVo>> response = baseService.getSupportThirdPartyPayPlatforms(vo);
		Assert.assertNull(response.getAttachment());
		Assert.assertEquals("200009", response.getCode());
		System.out.println(response.getCode() + ":" + response.getMsg());
	}
	
	
	@Test
	public void queryThirdPartyPayPlatformSupportBanksHappyPass(){
		ThirdPartyPayPlatformSupportBankReqVo vo = new ThirdPartyPayPlatformSupportBankReqVo();
		vo.setBizSysNo("004");
		vo.setThirdPartyPayPlatformCode("4");
		AttachmentResponse<List<ThirdPartyPayPlatformSupportBankRespVo>> response = baseService.getSupportBanksByThirdPartyPayPlatform(vo);
		Assert.assertEquals(Response.SUCCESS_RESPONSE_CODE, response.getCode());
		Assert.assertNotNull(response.getAttachment());
		for (ThirdPartyPayPlatformSupportBankRespVo temp : response.getAttachment()) {
			System.out.println(temp.getBankCode() + ":" + temp.getBankName());
		}
	}
	
	@Test
	public void queryThirdPartyPayPlatformSupportBanksThirdPartyPayPlatformCodeNotExists(){
		ThirdPartyPayPlatformSupportBankReqVo vo = new ThirdPartyPayPlatformSupportBankReqVo();
		vo.setBizSysNo("004");
		vo.setThirdPartyPayPlatformCode("9");
		AttachmentResponse<List<ThirdPartyPayPlatformSupportBankRespVo>> response = baseService.getSupportBanksByThirdPartyPayPlatform(vo);
		Assert.assertEquals("000007", response.getCode());
		Assert.assertNull(response.getAttachment());
		System.out.println("msg : " + response.getMsg());
	}
	
	@Test
	public void queryThirdPartyPayPlatformSupportBanksThirdPartyPayPlatformCodeBlank(){
		ThirdPartyPayPlatformSupportBankReqVo vo = new ThirdPartyPayPlatformSupportBankReqVo();
		vo.setBizSysNo("004");
		AttachmentResponse<List<ThirdPartyPayPlatformSupportBankRespVo>> response = baseService.getSupportBanksByThirdPartyPayPlatform(vo);
		Assert.assertEquals("000001", response.getCode());
		Assert.assertNull(response.getAttachment());
		System.out.println(response.getCode() + ":" + response.getMsg());
	}
	
	@Test
	public void getSupportBankInfoByThirdPartyPayPlatformCodeAndBankCodeHappyPass(){
		ThirdPartyPayPlatformSupportBankInfoReqVo vo = new ThirdPartyPayPlatformSupportBankInfoReqVo();
		vo.setBizSysNo("004");
		vo.setThirdPartyPayPlatformCode("0");
		vo.setBankCode("00080014");
		AttachmentResponse<ThirdPartyPayPlatformSupportBankInfoRespVo> response = baseService.getSupportBankInfoByThirdPartyPayPlatformCodeAndBankCode(vo);
		Assert.assertEquals(Response.SUCCESS_RESPONSE_CODE, response.getCode());
		Assert.assertNotNull(response.getAttachment());
		ThirdPartyPayPlatformSupportBankInfoRespVo attach = response.getAttachment();
		System.out.println(attach.getStatus() + ":" +attach.getCollectMaxMoney() + ":" + attach.getPayMaxMoney() + ":" + attach.getQuickPayMaxMoney());
	}
	
	@Test
	public void getSupportBankInfoByThirdPartyPayPlatformCodeAndBankCodeThirdPartyPayPlatformCodeBlank(){
		ThirdPartyPayPlatformSupportBankInfoReqVo vo = new ThirdPartyPayPlatformSupportBankInfoReqVo();
		vo.setBizSysNo("004");
		vo.setBankCode("00080014");
		AttachmentResponse<ThirdPartyPayPlatformSupportBankInfoRespVo> response = baseService.getSupportBankInfoByThirdPartyPayPlatformCodeAndBankCode(vo);
		Assert.assertEquals("000001", response.getCode());
		Assert.assertNull(response.getAttachment());
		System.out.println(response.getCode() + ":" + response.getMsg());
	}
	
	@Test
	public void getSupportBankInfoByThirdPartyPayPlatformCodeAndBankCodeBankCodeBlank(){
		ThirdPartyPayPlatformSupportBankInfoReqVo vo = new ThirdPartyPayPlatformSupportBankInfoReqVo();
		vo.setBizSysNo("004");
		vo.setThirdPartyPayPlatformCode("0");
		AttachmentResponse<ThirdPartyPayPlatformSupportBankInfoRespVo> response = baseService.getSupportBankInfoByThirdPartyPayPlatformCodeAndBankCode(vo);
		Assert.assertEquals("000001", response.getCode());
		Assert.assertNull(response.getAttachment());
		System.out.println(response.getCode() + ":" + response.getMsg());
	}
	
	@Test
	public void getSupportBankInfoByThirdPartyPayPlatformCodeAndBankCodeBothBlank(){
		ThirdPartyPayPlatformSupportBankInfoReqVo vo = new ThirdPartyPayPlatformSupportBankInfoReqVo();
		vo.setBizSysNo("004");
		AttachmentResponse<ThirdPartyPayPlatformSupportBankInfoRespVo> response = baseService.getSupportBankInfoByThirdPartyPayPlatformCodeAndBankCode(vo);
		Assert.assertEquals("000001", response.getCode());
		Assert.assertNull(response.getAttachment());
		System.out.println(response.getCode() + ":" + response.getMsg());
	}
	
	@Test
	public void getSupportBankInfoByThirdPartyPayPlatformCodeAndBankCodeNoRecord(){
		// 测试数据库无记录
		ThirdPartyPayPlatformSupportBankInfoReqVo vo = new ThirdPartyPayPlatformSupportBankInfoReqVo();
		vo.setBizSysNo("004");
		vo.setThirdPartyPayPlatformCode("0");
		vo.setBankCode("00080099");
		AttachmentResponse<ThirdPartyPayPlatformSupportBankInfoRespVo> response = baseService.getSupportBankInfoByThirdPartyPayPlatformCodeAndBankCode(vo);
		Assert.assertEquals(Response.SUCCESS_RESPONSE_CODE, response.getCode());
		Assert.assertNull(response.getAttachment());
	}
	
	@Test
	public void getSupportBankInfoByThirdPartyPayPlatformCodeAndBankCodeHaveRecord(){
		ThirdPartyPayPlatformSupportBankInfoReqVo vo = new ThirdPartyPayPlatformSupportBankInfoReqVo();
		vo.setBizSysNo("004");
		vo.setThirdPartyPayPlatformCode("0");
		vo.setBankCode("00080010");
		AttachmentResponse<ThirdPartyPayPlatformSupportBankInfoRespVo> response = baseService.getSupportBankInfoByThirdPartyPayPlatformCodeAndBankCode(vo);
		Assert.assertEquals(Response.SUCCESS_RESPONSE_CODE, response.getCode());
		Assert.assertNotNull(response.getAttachment());
		ThirdPartyPayPlatformSupportBankInfoRespVo respVo = response.getAttachment();
		System.out.println(respVo.getStatus() + ":" + respVo.getCollectMaxMoney() + ":" + respVo.getPayMaxMoney() + ":" + respVo.getQuickPayMaxMoney());
	}
	
	@Before
	public void init(){
		baseService.setSysAppIPSService(sysAppIPSService);
		baseService.setAreaInfoService(areaInfoService);
		baseService.setBankInfoService(bankInfoService);
		baseService.setBankOrgInfoService(bankOrgInfoService);
		baseService.setSysAppService(sysAppService);
		baseService.setSysInfoCategoryAPPSService(sysInfoCategoryAPPSService);
		baseService.setSysInfoCategoryService(sysInfoCategoryService);
		baseService.setDictionaryService(dictionaryService);
		baseService.setThirdFieldMapperService(thirdFieldMapperService);
	}
}
