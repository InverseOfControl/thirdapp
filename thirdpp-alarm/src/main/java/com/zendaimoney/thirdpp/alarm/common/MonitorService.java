package com.zendaimoney.thirdpp.alarm.common;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zendaimoney.thirdpp.alarm.common.exception.CsswebException;
import com.zendaimoney.thirdpp.alarm.common.exception.DataAccessException;
import com.zendaimoney.thirdpp.alarm.util.CsswebConfig;

public class MonitorService {

	public static Log logger = LogFactory.getLog(MonitorService.class);

	public List<Monitor> queryMonitor() throws CsswebException {
		String sql = "SELECT id,mobiles,note,CREATE_TIME FROM (SELECT ROW_NUMBER()"
				+ " OVER (PARTITION BY mobiles ORDER BY id DESC) AS ROW_NO,A.*  FROM TPP_MONITOR A"
				+ " WHERE A.mark='0' ) x WHERE x.ROW_NO = 1 AND ROWNUM<=1000 ORDER BY id";
		DataSource dataSource = null;
		Connection connOracle = null;
		Statement stOracle = null;
		ResultSet rsOracle = null;
		Monitor monitor = null;
		List<Monitor> list = new ArrayList<Monitor>();
		try {
			dataSource = (DataSource) CsswebConfig.getBean("dataSource");
			connOracle = dataSource.getConnection();
			stOracle = connOracle.createStatement();
			rsOracle = stOracle.executeQuery(sql);
			while (rsOracle != null && rsOracle.next()) {
				monitor = new Monitor();
				monitor.setId(rsOracle.getLong(1));
				monitor.setMobiles(rsOracle.getString(2));
				monitor.setNote(rsOracle.getString(3));
				monitor.setCreateTime(rsOracle.getDate(4));
				list.add(monitor);
			}
		} catch (Exception e) {
			logger.error("告警数据查询--查询异常", e);
			return list;
		} finally {
			try {
				if (rsOracle != null) {
					rsOracle.close();
				}
				if (stOracle != null) {
					stOracle.close();
				}
				if (connOracle != null) {
					connOracle.close();
				}
			} catch (Exception e) {
				logger.error("告警数据查询--关闭异常", e);
			}

		}
		return list;
	}

	public void updateMonitor(List<Monitor> list) throws CsswebException {
		String sql = "update TPP_MONITOR t set t.mark = '1' where t.mobiles = ? and t.note = ? and t.mark = '0'";
		DataSource dataSource = null;
		Connection connOracle = null;
		PreparedStatement ps = null;
		if (list != null && list.size() > 0) {
			try {
				dataSource = (DataSource) CsswebConfig.getBean("dataSource");
				connOracle = dataSource.getConnection();
				ps = connOracle.prepareStatement(sql);
				for (Monitor m : list) {
					ps.setString(1, m.getMobiles());
					ps.setString(2, m.getNote());
					ps.addBatch();
				}
				ps.executeBatch();
			} catch (Exception e) {
				logger.error("告警数据更新异常", e);
				throw new CsswebException(e);
			} finally {
				try {
					if (ps != null) {
						ps.close();
					}
					if (connOracle != null) {
						connOracle.close();
					}
				} catch (Exception e) {
					logger.error("告警数据更新--关闭异常", e);
				}
			}
		}
	}

	public void insertMonitor(String mobiles, String note)
			throws DataAccessException {
		String sql = "{call ps_alarm_log_monitor(?,?)}";
		DataSource dataSource = null;
		Connection connOracle = null;
		CallableStatement  cs = null;
		try {
			dataSource = (DataSource) CsswebConfig.getBean("dataSource");
			connOracle = dataSource.getConnection();
			cs = connOracle.prepareCall(sql);
			cs.setString(1,mobiles);
			cs.setString(2,note);
			cs.execute();
		} catch (Exception e) {
			logger.error("告警数据插入异常", e);
			throw new CsswebException(e);
		} finally {
			try {
				if (cs != null) {
					cs.close();
				}
				if (connOracle != null) {
					connOracle.close();
				}
			} catch (Exception e) {
				logger.error("告警数据插入--关闭异常", e);
			}
		}
	}
	
	
	public void updateMonitor(Monitor monitor) throws CsswebException {
		String sql = "update TPP_MONITOR t set t.mark = '1' where t.mobiles = ? and t.note = ? and t.mark = '0'";
		DataSource dataSource = null;
		Connection connOracle = null;
		PreparedStatement ps = null;
		try {
			dataSource = (DataSource) CsswebConfig.getBean("dataSource");
			connOracle = dataSource.getConnection();
			ps = connOracle.prepareStatement(sql);
			ps.setString(1, monitor.getMobiles());
			ps.setString(2, monitor.getNote());
			
			ps.execute();
		} catch (Exception e) {
			logger.error("告警数据更新异常", e);
			throw new CsswebException(e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connOracle != null) {
					connOracle.close();
				}
			} catch (Exception e) {
				logger.error("告警数据更新--关闭异常", e);
			}
		}
	}

}