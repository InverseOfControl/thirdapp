package com.zdmoney.manager.common.enums;

public enum BizSys {
	
	/** 001 UserCenter */
	ZENDAI_2001_SYS("001", "UserCenter"),
	/** 002 CRM */
	ZENDAI_2002_SYS("002", "CRM"),
	/** 003 理财业务管理系统 */
	ZENDAI_2003_SYS("003", "理财业务管理系统"),
	/** 004 信贷业务管理系统（个贷） */
	ZENDAI_2004_SYS("004", "信贷业务管理系统（个贷）"),
	/** 005 信用审核系统 */
	ZENDAI_2005_SYS("005", "信用审核系统"),
	/** 006 债权交易系统 */
	ZENDAI_2006_SYS("006", "债权交易系统 "),
	/** 007 账户管理系统 */
	ZENDAI_2007_SYS("007", "账户管理系统"),
	/** 008 合同管理系统 */
	ZENDAI_2008_SYS("008", "合同管理系统"),
	/** 009 绩效考核系统 */
	ZENDAI_2009_SYS("009", "绩效考核系统"),
	/** 010 财务系统 */
	ZENDAI_2010_SYS("010", "财务系统"),
	/** 011 支付平台 */
	ZENDAI_2011_SYS("011", "支付平台 "),
	/** 012 用户WEB系统 */
	ZENDAI_2012_SYS("012", "用户WEB系统"),
	/** 013 CallCenter */
	ZENDAI_2013_SYS("013", "CallCenter"),
	/** 014 OA */
	ZENDAI_2014_SYS("014", "OA"),
	/** 015 系统安全监控 */
	ZENDAI_2015_SYS("015", "系统安全监控"),
	/** 016 文件管理系统 */
	ZENDAI_2016_SYS("016", "文件管理系统"),
	/** 017 Online系统 */
	ZENDAI_ONLINE_SYS("017", "Online系统"),
	/** 018 信贷业务管理系统（车贷） */
	ZENDAI_2018_SYS("018", "信贷业务管理系统（车贷）"),
	/** 019 信贷业务管理系统（中泰信托） */
	ZENDAI_2019_SYS("019", "信贷业务管理系统（中泰信托）"),
	/** 020TPP系统 */
	ZENDAI_2020_SYS("020", "TPP系统"),
	/** 021TPP交易系统 */
	ZENDAI_2021_SYS("021", "TPP交易系统"),
	/** 022TPP转发系统 */
	ZENDAI_2022_SYS("022", "TPP转发系统"),
	/** 023 TPP查询系统 */
	ZENDAI_2023_SYS("023", "TPP查询系统"),
	/** 024 TPP合并系统 */
	ZENDAI_2024_SYS("024", "TPP合并系统"),
	;
    private final String code;
	
	
	private final String desc;

	private BizSys(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static BizSys get(String code) {
		for (BizSys bizSys : BizSys.values()) {
			if (bizSys.getCode().equals(code))
				return bizSys;
		}
		throw new IllegalArgumentException("bizSys is not exist : " + code);
	}


}
