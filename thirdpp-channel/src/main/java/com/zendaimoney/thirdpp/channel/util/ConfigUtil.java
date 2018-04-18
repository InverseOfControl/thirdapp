package com.zendaimoney.thirdpp.channel.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import com.zendaimoney.thirdpp.channel.conf.baofoopay.BaofoopayConfig;
import com.zendaimoney.thirdpp.channel.conf.unspay.UnspayConfig;
import org.apache.commons.lang.StringUtils;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import com.zendaimoney.thirdpp.channel.conf.SystemConfig;
import com.zendaimoney.thirdpp.channel.conf.allinpay.AllinpayConfig;
import com.zendaimoney.thirdpp.channel.conf.fuioupay.FuioupayConfig;
import com.zendaimoney.thirdpp.channel.conf.kjtpay.KjtpayConfig;
import com.zendaimoney.thirdpp.channel.conf.shunionpay.ShunionpayConfig;
import com.zendaimoney.thirdpp.channel.conf.yyoupay.YyoupayConfig;
import com.zendaimoney.thirdpp.channel.conf.zendaipay.ZendaipayConfig;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;

public class ConfigUtil implements Serializable {

	private static final long serialVersionUID = 8433608222120950801L;

	private static final LogPrn log = new LogPrn(ConfigUtil.class);

	private AllinpayConfig allinpayConfig;

	private FuioupayConfig fuioupayConfig;
	
	private SystemConfig systemConfig;

	private ShunionpayConfig shunionpayConfig;
	
	private YyoupayConfig yyoupayConfig;
	
	private ZendaipayConfig zendaipayConfig;
	
	private KjtpayConfig kjtpayConfig;

	private BaofoopayConfig baofoopayConfig;

	private UnspayConfig unspayConfig;

	public ShunionpayConfig getShunionpayConfig() {
		return shunionpayConfig;
	}

	public void setShunionpayConfig(ShunionpayConfig shunionpayConfig) {
		this.shunionpayConfig = shunionpayConfig;
	}

	public FuioupayConfig getFuioupayConfig() {
		return fuioupayConfig;
	}

	public void setFuioupayConfig(FuioupayConfig fuioupayConfig) {
		this.fuioupayConfig = fuioupayConfig;
	}
	
	public YyoupayConfig getYyoupayConfig() {
		return yyoupayConfig;
	}
	
	public void setYyoupayConfig(YyoupayConfig yyoupayConfig) {
		this.yyoupayConfig = yyoupayConfig;
	}

	public ZendaipayConfig getZendaipayConfig() {
		return zendaipayConfig;
	}

	public void setZendaipayConfig(ZendaipayConfig zendaipayConfig) {
		this.zendaipayConfig = zendaipayConfig;
	}

	public KjtpayConfig getKjtpayConfig() {
		return kjtpayConfig;
	}

	public void setKjtpayConfig(KjtpayConfig kjtpayConfig) {
		this.kjtpayConfig = kjtpayConfig;
	}

	public BaofoopayConfig getBaofoopayConfig() {
		return baofoopayConfig;
	}

	public void setBaofoopayConfig(BaofoopayConfig baofoopayConfig) {
		this.baofoopayConfig = baofoopayConfig;
	}

	public UnspayConfig getUnspayConfig() {
		return unspayConfig;
	}

	public void setUnspayConfig(UnspayConfig unspayConfig) {
		this.unspayConfig = unspayConfig;
	}

	private static ConfigUtil single = null;

	private ConfigUtil() {
		// 加载thirdpp.properties配置文件
		loadThirdppFile();
	}

	// 单例.
	public static ConfigUtil getInstance() {
		if (single == null) {
			single = new ConfigUtil();
		}
		return single;
	}

	/**
	 * 加载thirdpp.properties配置文件.
	 */
	public void loadThirdppFile() throws PlatformException {
		
		// 读取配置文件路径
		String path = "";
		try {
			if (StringUtils.isEmpty(System.getenv("TPPGLOBALCONFIGPATH"))) {
				
				path = Thread.currentThread().getContextClassLoader().getResource("conf/thirdpp-channel.properties").getPath();
			} else {
				path = System.getenv("TPPGLOBALCONFIGPATH") + File.separator + "thirdpp-channel.properties";
			}
			File thirdFile = new File(path);
			log.info("thirdpp-channel: " + path);
			Ini conf = new Ini(thirdFile);
			this.populateAttributes(conf);
			log.info("LoadThirdppFile sucessful");
		} catch (IOException e) {
			log.error("Parse thirdpp configuration failed,path=" + path, e);
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		} catch (PlatformException e) {
			throw e;
		} catch (Exception e) {
			log.error("Parse thirdpp configuration failed,path=" + path, e);
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,
					ExceptionUtil.getExceptionMessage(e));

		}

	}

	private void populateAttributes(Ini conf) throws PlatformException {
		this.populateSystemConf(conf);
		this.populateAllinpayConf(conf);
		this.populateFuioupayConf(conf);
		this.populateShunionpayConfig(conf);
		this.populateYyoupayConfig(conf);
		this.populateZendaipayConfig(conf);
		this.populateKjtpayConfig(conf);
		this.populateBaofoopayConfig(conf);
		this.populateUnspayConfig(conf);
	}

	@SuppressWarnings("static-access")
	private void populateSystemConf(Ini conf) throws PlatformException {
		Section section = conf.get("system");
		if (section != null) {
			systemConfig = new SystemConfig();
			systemConfig.setMerge_offline_key(section.get("merge_offline_key"));
			systemConfig.setMerge_online_key(section.get("merge_online_key"));
			
			// 设置全局变量
			log.info("TPPGLOBALCONFIGPATH: " + System.getenv("TPPGLOBALCONFIGPATH"));
			systemConfig.setGlobalConfigPath(System.getenv("TPPGLOBALCONFIGPATH"));
		}
	}

	private void populateAllinpayConf(Ini conf) throws PlatformException {
		Section section = conf.get("allinpay");
		try {
			if (section != null) {
				allinpayConfig = new AllinpayConfig();
				allinpayConfig.setLevel(section.get("level"));
				allinpayConfig.setCertPath(section.get("certPath"));
				allinpayConfig.setMsgInStorage(section.get("msgInStorage"));
				allinpayConfig.setVersion(section.get("version"));
				allinpayConfig.setUrl(section.get("url"));
				allinpayConfig.setUrl2(section.get("url2"));
				
				// 通联业务编码配置-代收
				allinpayConfig.setIncomeBusinessCode(section.get("incomeBusinessCode"));
				// 通联业务编码配置-代付
				allinpayConfig.setPayforBusinessCode(section.get("payforBusinessCode"));
				
				log.info(allinpayConfig.toString());
			}
		} catch (Exception e) {
			log.error("===", e);
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,
					ExceptionUtil.getExceptionMessage(e));

		}
	}

	private void populateFuioupayConf(Ini conf) throws PlatformException {
		Section section = conf.get("fuioupay");

		try {
			if (section != null) {
				fuioupayConfig = new FuioupayConfig();
				fuioupayConfig.setMsgInStorage(section.get("msgInStorage"));
				fuioupayConfig.setVersion(section.get("version"));
				fuioupayConfig.setUrl(section.get("url"));
				fuioupayConfig.setSecretKey(section.get("secret_key"));
				log.info(fuioupayConfig.toString());
			}
		} catch (Exception e) {
			log.error("===", e);
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,
					ExceptionUtil.getExceptionMessage(e));

		}
	}

	/**
	 * 银联配置设置
	 * 
	 * @param conf
	 * @throws PlatformException
	 */
	private void populateShunionpayConfig(Ini conf) throws PlatformException {
		Section section = conf.get("shunionpay");
		try {
			shunionpayConfig = new ShunionpayConfig();
			shunionpayConfig.setVersion(section.get("version"));
			shunionpayConfig.setCollectingUrl(section.get("collecting_url"));
			shunionpayConfig.setCollectingQrytransUrl(section
					.get("collecting_qrytrans_url"));
			shunionpayConfig.setGateId(section.get("gateId"));
			shunionpayConfig.setPublicKey(section.get("publickey"));
			shunionpayConfig.setKeyPath(section.get("keyPath"));
			shunionpayConfig.setMsgInStorage(section.get("msgInStorage"));
			shunionpayConfig.setAppSysId(section.get("appSysId"));
			shunionpayConfig.setSecretKey(section.get("secretKey"));
			
			shunionpayConfig.setAuthUrl(section.get("authUrl")); // 实名认证地址URL
			shunionpayConfig.setCardBinUrl(section.get("cardBinUrl")); // 卡bin查询地址URL
			
			shunionpayConfig.setPayVersion(section.get("payVersion"));
			shunionpayConfig.setPayingUrl(section.get("paying_url"));
			
			log.info(shunionpayConfig.toString());
		} catch (Exception e) {
			log.error("===", e);
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,
					ExceptionUtil.getExceptionMessage(e));

		}
	}
	
	/**
	 * 用友配置设置
	 * 
	 * @param conf
	 * @throws PlatformException
	 */
	private void populateYyoupayConfig(Ini conf) throws PlatformException {
		Section section = conf.get("yyoupay");
		try {
			yyoupayConfig = new YyoupayConfig();
			yyoupayConfig.setyYoupayOpenAct(section.get("YYoupay_openAct"));
			yyoupayConfig.setYyoupaRepayUrl(section.get("YYoupay_REPAY_URL"));
			yyoupayConfig.setMsgInStorage(section.get("msgInStorage"));
			log.info(yyoupayConfig.toString());
		} catch (Exception e) {
			log.error("===", e);
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,
					ExceptionUtil.getExceptionMessage(e));

		}
	}
	
	/**
	 * 证大爱特配置设置
	 * 
	 * @param conf
	 * @throws PlatformException
	 */
	private void populateZendaipayConfig(Ini conf) throws PlatformException {
		Section section = conf.get("zendaipay");
		try {
			zendaipayConfig = new ZendaipayConfig();
			zendaipayConfig.setCollectingUrl(section.get("collecting_url"));
			zendaipayConfig.setCollectingQrytransUrl(section
					.get("collecting_qrytrans_url"));
			zendaipayConfig.setMsgInStorage(section.get("msgInStorage"));
			log.info(zendaipayConfig.toString());
		} catch (Exception e) {
			log.error("===", e);
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,
					ExceptionUtil.getExceptionMessage(e));

		}
	}
	
	/**
	 * 快捷通配置设置
	 * 
	 * @param conf
	 * @throws PlatformException
	 */
	private void populateKjtpayConfig(Ini conf) throws PlatformException {
		Section section = conf.get("kjtpay");
		try {
			kjtpayConfig = new KjtpayConfig();
			kjtpayConfig.setCollectingUrl(section.get("collecting_url"));
			kjtpayConfig.setMsgInStorage(section.get("msgInStorage"));
			kjtpayConfig.setVersion(section.get("version"));
			kjtpayConfig.setKeyPath(section.get("keyPath"));
			kjtpayConfig.setPayUrl(section.get("pay_url"));
			kjtpayConfig.setPayQueryUrl((section.get("pay_query_url")));
			log.info(kjtpayConfig.toString());
		} catch (Exception e) {
			log.error("===", e);
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,
					ExceptionUtil.getExceptionMessage(e));

		}
	}

	/**
	 * 宝付配置设置
	 *
	 * @param conf
	 * @throws PlatformException
	 */
	private void populateBaofoopayConfig(Ini conf) throws PlatformException {
		Section section = conf.get("baofoopay");
		try {
			baofoopayConfig = new BaofoopayConfig();
			baofoopayConfig.setVersion(section.get("version"));
			baofoopayConfig.setCollectingUrl(section.get("collecting_url"));
			baofoopayConfig.setCollectingQrytransUrl(section
					.get("collecting_qrytrans_url"));
			baofoopayConfig.setGateId(section.get("gateId"));
			baofoopayConfig.setPublicKey(section.get("publickey"));
			baofoopayConfig.setKeyPath(section.get("keyPath"));
			baofoopayConfig.setMsgInStorage(section.get("msgInStorage"));

			log.info(baofoopayConfig.toString());
		} catch (Exception e) {
			log.error("===", e);
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,
					ExceptionUtil.getExceptionMessage(e));

		}
	}

	/**
	 * 银生宝配置设置
	 *
	 * @param conf
	 * @throws PlatformException
	 */
	private void populateUnspayConfig(Ini conf) throws PlatformException {
		Section section = conf.get("unspay");
		try {
			unspayConfig = new UnspayConfig();
			unspayConfig.setCollectingUrl(section.get("collecting_url"));
			unspayConfig.setCollectingQrytransUrl(section
					.get("collecting_qrytrans_url"));
			unspayConfig.setMsgInStorage(section.get("msgInStorage"));
			unspayConfig.setVersion(section.get("version"));

			log.info(unspayConfig.toString());
		} catch (Exception e) {
			log.error("===", e);
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,
					ExceptionUtil.getExceptionMessage(e));

		}
	}

	public AllinpayConfig getAllinpayConfig() {
		return allinpayConfig;
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public static void main(String args[]) {
		ConfigUtil configUtil = ConfigUtil.getInstance();

		AllinpayConfig config = configUtil.getAllinpayConfig();

		System.out.println("certPath==" + config.getCertPath());

		System.out.println("version==" + config.getVersion());
	}

}