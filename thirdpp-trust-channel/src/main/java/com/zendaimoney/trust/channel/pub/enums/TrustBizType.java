package com.zendaimoney.trust.channel.pub.enums;

/**
 * 招商银行业务类型
 * @author mencius
 *
 */
public enum TrustBizType {

	// 招商银行业务类型
	FILE("FILE", "文件处理"),
	USRA("USRA", "用户开户"),
	USRE("USRE", "用户修改信息"),
	USRB("USRB", "用户绑定银行卡"),
	UBQY("UBQY", "银行卡绑定结果查询"),
	USRJ("USRJ", "用户解绑银行卡"),
	USRD("USRD", "用户关户"),
	CHRG("CHRG", "三方充值请求"),
	SCQY("SCQY", "查询自助转账到账记录"),
	SCHG("SCHG", "自助转账充值请求"),
	DCHG("DCHG", "代扣充值请求"),
	WTDR("WTDR", "提现请求"),
	WDRS("WDRS", "提现结果通知平台WDRS"),
	WDQY("WDQY", "提现结果查询WDQY"),
	MCHG("MCHG", "平台充值MCHG"),
	MDRW("MDRW", "平台提现MDRW"),
	MINT("MINT", "存管账户计息MINT"),
	PROA("PROA", "登记散标PROA"),
	GROA("GROA", "登记团信息GROA"),
	PROC("PROC", "关闭散标PROC"),
	GROC("GROC", "关闭团GROC"),
	FROZ("FROZ", "冻结FROZ"),
	UNFR("UNFR", "解冻UNFR"),
	INVS("INVS", "投资INVS"),
	REPY("REPY", "还款REPY"),
	CASM("CASM", "债权转让CASM"),
	MFEE("MFEE", "平台收费MFEE"),
	MPAY("MPAY", "平台付款MPAY"),
	MADV("MADV", "平台垫资MADV"),
	MREG("MREG", "垫资收回MREG"),
	GINN("GINN", "客户入团GINN"),
	GOUT("GOUT", "客户出团GOUT"),
	GCHG("GCHG", "团收取风险金GCHG"),
	GDRW("GDRW", "团使用风险金GDRW"),
	ETPM("ETPM", "受托支付"),
	OADV("OADV", "合作机构垫资"),
	OREG("OREG", "合作机构垫资收回"),
	
	MRSL("MRSL", "充值资金平台清算MRSL"),
	DRSL("DRSL", "充值资金直接清分DSRL"),
	
	SCCK("SCCK", "每日交易汇总对账SCCK"),
	DCCK("DCCK", "每日交易明细对账DCCK"),
	BCCK("BCCK", "每日余额对账BCCK"),
	
	BAQY("BAQY", "余额查询BAQY"),
	TRQY("TRQY", "虚拟户交易查询TRQY"),
	RCQY("RCQY", "存管专户入账交易查询RCQY"),
	
	//TPP2.0自定义业务类型(非第三方)
	BATCH_BUILD("BATCH_BUILD", "批量生产报文(TPP自定义)"),
	BATCH_DOWNLOAD("BATCH_DOWNLOAD", "下载第三方文件(TPP自定义)"),
	BATCH_PARSE("BATCH_PARSE", "批量转译报文(TPP自定义)"),
	;
	private final String code;
	
	
	private final String desc;

	private TrustBizType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static TrustBizType get(String code) {
		for (TrustBizType cmbBizType : TrustBizType.values()) {
			if (cmbBizType.getCode().equals(code))
				return cmbBizType;
		}
		throw new IllegalArgumentException("cmbBizType is not exist : " + code);
	}
}
