package com.zendaimoney.thirdpp.query.action;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.channel.pub.vo.QueryReqVo;
import com.zendaimoney.thirdpp.common.enums.BizSys;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.query.action.annotations.QueryActionAnnotation;
import com.zendaimoney.thirdpp.query.entity.CollectInfo;
import com.zendaimoney.thirdpp.query.mongo.MongoQueryVO;
import com.zendaimoney.thirdpp.query.service.AmqpService;
import com.zendaimoney.thirdpp.query.service.CollectInfoService;
import com.zendaimoney.thirdpp.query.service.CollectionHandler;
import com.zendaimoney.thirdpp.query.service.impl.CollectionHandlerImpl;
import com.zendaimoney.thirdpp.query.util.CalendarUtils;
import com.zendaimoney.thirdpp.query.util.Constants;

/**
 * 代收查询处理Action
 * @author mencius
 *
 */
@QueryActionAnnotation(bizType = BizType.BROKER_COLLECT)
@Component("com.zendaimoney.thirdpp.query.action.CollectQueryAction")
public class CollectQueryAction extends Action<MongoQueryVO, String>{
	
	// 日志工具类
	public static Log logger = LogFactory.getLog(CollectQueryAction.class);

	@Autowired
	private CollectInfoService colInfoService;
	
	@Autowired
	private AmqpService amqpService;
	
	private CollectionHandler handler;
	
	/**
	 * 准备调用channel接口请求参数对象
	 */
	@Override
	protected QueryReqVo preProcess(MongoQueryVO info) {
		// 向channel系统传输的查询对象 queryReqVo
		QueryReqVo queryReqVo = new QueryReqVo();
		queryReqVo.setTradeFlow(info.getTradeFlow()); // 交易流水号
		queryReqVo.setBizSys(BizSys.get(info.getBizSysNo()));
		queryReqVo.setBizType(BizType.get(info.getBizTypeNo())); // 业务类型
		queryReqVo.setThirdType(ThirdType.get(info.getPaySysNo())); // 第三方通道类型
		queryReqVo.setInfoCategoryCode(info.getInfoCategoryCode()); // 信息类别code
		queryReqVo.setOrderDate(info.getCreateTime());
		queryReqVo.setPayerAccountNo(info.getPayerAccountNo()); // 付款方账户
		return queryReqVo;
	}
	
	/**
	 * 获取mongo集合处理类实例
	 */
	@Override
	protected CollectionHandler getCollectionHandler(String collectionName) {
		handler = new CollectionHandlerImpl(collectionName);
		return handler;
	}
	
	/**
	 * 获取当前操作mongo数据对象
	 */
	@Override
	protected MongoQueryVO getMongoQueryVO(MongoQueryVO info){
		return info;
	}
	
	/**
	 * 返回抽象父类
	 */
	@Override
	protected String getCollectionName(String name) {
		return name;
	}

	/**
	 * 获取交易明细
	 * @throws SQLException 
	 */
	@Override
	protected CollectInfo checkIsNeedTpUpdateCollect(String tradeFlow) throws SQLException {
		
		// 获取处理的代收信息
//		try {
			logger.info("开始获取处理的代收信息  ----TradeFlow ："+ tradeFlow);
			CollectInfo collect = colInfoService.queryCollectInfoByReqId(tradeFlow);
			if(null != collect){
				logger.info("获取处理的代收信息"+ "Status:" + collect.getStatus()+ "商户编码"+collect.getSpare2()+"通道编码"+collect.getPaySysNo());
			} else {
				logger.info("collectInfo :" + collect);
			}
			
//		} catch (SQLException e) {
//			logger.error(e.getMessage(), e);
//			return false;
//		}
		return collect;
	}


	/**
	 * 更新交易明细并发送MQ
	 */
	@Override
	protected boolean updateAndSendMQ(MongoQueryVO queryVO, Response response){
		
		try {
			// 交易信息明细更新对象
			CollectInfo collectInfo = new CollectInfo();
			collectInfo.setTradeFlow(queryVO.getTradeFlow()); // 交易流水号
			collectInfo.setStatus(response.getCode()); // 交易结果状态
			collectInfo.setFailReason(response.getMsg()); // 失败描述
			// 第三方响应码
			collectInfo.setTransRepCode(response.getBankRepCode()); // 渠道返回状态
			collectInfo.setThirdReturnTime(CalendarUtils.getFormatNow()); // 回盘时间
			// 通知合并状态(0待通知合并、1不需要通知合并、2已通知合并)
			collectInfo.setNotifyMergeStatus(Constants.INFO_NOTIFY_MERGE_STATUS);
			
			// 更新oracle操作
			int count = colInfoService.updateAndSendMQ(collectInfo, queryVO);
			
			if (count == 1) {
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		
		return false;
	}

	@Override
	protected boolean checkIsNeedTpUpdate(String tradeFlow) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
}
