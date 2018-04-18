package com.zendaimoney.thirdpp.query.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.zendaimoney.thirdpp.query.conf.MongoConfig;
import com.zendaimoney.thirdpp.query.conf.ServerConfig;
import com.zendaimoney.thirdpp.query.dao.TppTradeWaitingQueryDao;
import com.zendaimoney.thirdpp.query.entity.TradeWaitingQuery;
import com.zendaimoney.thirdpp.query.entity.UnknowTradeVO;
import com.zendaimoney.thirdpp.query.util.Constants;
import com.zendaimoney.thirdpp.query.util.MongoDBUtils;

/**
 * 从交易待查询表获取记录并入mongoDB库
 * @author mencius
 *
 */
@Service
@Transactional
public class TradeWaitingQueryService {

	// 日志工具类
	private static Log logger = LogFactory.getLog(TradeWaitingQueryService.class);
	
	@Autowired
	private TppTradeWaitingQueryDao queryDao;
		
	/**
	 * 从待处理的临时表内拉入mongo集合 UNKNOW_TRADE_COLLECTION
	 */
	public void deal() {
		
 		int limit = ServerConfig.systemConfig.getWaitingSize();
		try {
			List<TradeWaitingQuery> tradeWaitingQueries = queryDao.queryTppTradeWaitingQueryDaos(ServerConfig.systemConfig.getAppName(), Constants.TPP_QUERY_DEALFLAG_INIT, limit);
			
			if (tradeWaitingQueries != null) {
				
				// 遍历记录集
				for (TradeWaitingQuery vo : tradeWaitingQueries) {
					// 入库并更新临时记录表状态为1
					insertRecordAndUpdate(vo);
				}
				logger.info("tradeWaitingQueries:" + tradeWaitingQueries.size());
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 入库并更新状态
	 * @param vo
	 * @throws SQLException
	 */
	public void insertRecordAndUpdate(TradeWaitingQuery vo) throws SQLException {
		
		UnknowTradeVO unknowTradeVO = new UnknowTradeVO(vo.getTradeFlow(), vo.getBizTypeNo(), vo.getBizSysNo(), vo.getPaySysNo(), "-", 
				vo.getInfoCategoryCode(), vo.getOpMode(), vo.getPayerAccountNo(), vo.getQueryModuleName(), vo.getCreateTime());
		
		// 将记录插入 UNKNOW_TRADE_COLLECTION 集合内
		MongoDBUtils.addWaitingQuery(MongoConfig.mongoConfig.getCollectionName(), unknowTradeVO.toDbObject());
		
		// 将拉入库后的记录修改状态为 1.(处理成功)
		queryDao.updateStatus(vo, Constants.TPP_QUERY_DEALFLAG_DEALED);
	}
}
