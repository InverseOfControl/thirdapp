package com.zendaimoney.thirdpp.channel.util.allinpay;

import java.math.BigDecimal;

import com.allinpay.XmlTools;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;

public class AllinpayUtil {

	private static final int UNIT = 100;

	/**
	 * xml报文签名
	 * 
	 * @param xml
	 * @param bizSys
	 * @return
	 * @throws Exception
	 */
	public static String signMsg(String xml, BizReqVo bizReqVo)
			throws Exception {

		String certPath = null;

		String certPwd = null;

		String merchantType = ThirdPPCacheContainer.sysInfoCategoryMap.get(
				bizReqVo.getInfoCategoryCode()).getMerchantType();

		// 获取证书路径
		certPath = ConfigUtil.getInstance().getAllinpayConfig().getCertPath();
		// 获取证书密码
		certPwd = ThirdPPCacheContainer.sysThirdChannelInfoMap.get(
				bizReqVo.getThirdType().getCode() + merchantType).getCertPwd();
		// 生成证书绝对路径
		certPath = Thread.currentThread().getContextClassLoader()
				.getResource(certPath).getPath() + ThirdPPCacheContainer.sysThirdChannelInfoMap.get(
						bizReqVo.getThirdType().getCode() + merchantType).getCertName();

		xml = XmlTools.signMsg(xml, certPath, certPwd, false);

		return xml;

	}
	
	/**
	 * xml报文签名-310011接口
	 * 
	 * autor:ym10159
	 * date:2018年4月9日 上午10:33:11
	 * @param xml 签名信息
	 * @param bizReqVo 通道信息
	 */
	public static String signMsg_310011(String xml, BizReqVo bizReqVo) throws Exception {

		String certPath = null;
		String certPwd = null;

		String merchantType = ThirdPPCacheContainer.sysInfoCategoryMap.get(
				bizReqVo.getInfoCategoryCode()).getMerchantType();

		// 获取证书路径
		certPath = ConfigUtil.getInstance().getAllinpayConfig().getCertPath();
		// 获取证书密码
		certPwd = ThirdPPCacheContainer.sysThirdChannelInfoMap.get(bizReqVo.getThirdType().getCode() + merchantType).getCertPwd();
		// 生成证书绝对路径
		certPath = Thread.currentThread().getContextClassLoader()
				.getResource(certPath).getPath() + ThirdPPCacheContainer.sysThirdChannelInfoMap
				.get(bizReqVo.getThirdType().getCode() + merchantType).getCertName();

		return XmlTools.signMsg(xml, certPath, certPwd, false);
	}

	public static BigDecimal yuanConvertFen(BigDecimal amount) {
		BigDecimal decUnit = BigDecimal.valueOf(UNIT);
		return amount.multiply(decUnit).setScale(0);
	}

	public static void main(String args[]) throws Exception {
		//System.out.println(signMsg("123456", BizSys.ZENDAI_2003_SYS));

	}

}
