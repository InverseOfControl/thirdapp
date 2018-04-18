package com.zendaimoney.thirdpp.alarm.common;

import java.sql.CallableStatement;
import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zendaimoney.thirdpp.alarm.util.CsswebConfig;

public class DeliverService {
	
	public static Log logger = LogFactory.getLog(DeliverService.class);

	public void insertUcSmsReceive(UcSmsReceive ucSmsReceive) throws Exception{
		String sql = "{call p_smsreceive_insert(?,?,?,?)}";
		DataSource dataSource = null;
		Connection connOracle = null;
		CallableStatement cs = null;
		try {
			dataSource = (DataSource) CsswebConfig.getBean("dataSource");
			connOracle = dataSource.getConnection();
			cs = connOracle.prepareCall(sql);
			cs.setString(1, ucSmsReceive.getMobileFrom());
			cs.setString(2, ucSmsReceive.getBody());
			cs.setString(3, ucSmsReceive.getCp());
			cs.setString(4, ucSmsReceive.getMobileTo());
			cs.execute();
		} catch (Exception e) {
			logger.error("上行短信--插入异常", e);
			throw e;
		} finally {
			try {
				if (cs != null) {
					cs.close();
				}
				if (connOracle != null) {
					connOracle.close();
				}
			} catch (Exception e) {
				logger.error("上行短信插入--关闭异常", e);
			}

		}
	}

}
