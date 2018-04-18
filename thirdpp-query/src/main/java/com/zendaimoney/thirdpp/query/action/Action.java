package com.zendaimoney.thirdpp.query.action;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ymkj.base.cache.redis.client.ICacheClient;
import com.zendaimoney.thirdpp.channel.pub.service.IChannelService;
import com.zendaimoney.thirdpp.channel.pub.vo.QueryReqVo;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.query.entity.CollectInfo;
import com.zendaimoney.thirdpp.query.exception.PlatformException;
import com.zendaimoney.thirdpp.query.mongo.MongoQueryVO;
import com.zendaimoney.thirdpp.query.service.CollectionHandler;
import com.zendaimoney.thirdpp.query.util.CacheUtil;
import com.zendaimoney.thirdpp.query.util.Constants;
import com.zendaimoney.thirdpp.query.util.Constants.TppConstants;

/**
 * 
 * @author mencius
 *
 * @param <T1> 任务类
 * @param <T2> 交易明细类
 */
public abstract class Action<K, v> {
	
	// 日志工具类
	public static Log logger = LogFactory.getLog(Action.class);
	
	@Autowired
	private IChannelService channelService;
	
	private CollectionHandler handler;
	
	@Autowired
	private ICacheClient cacheClient;
	
	/**
	 * 获取查询通道请求参数对象
	 * @param info
	 * @return
	 */
	protected abstract QueryReqVo preProcess(K info);
	
	/**
	 * 获取集合操作实例
	 * @param collectionName
	 * @return CollectionHandler
	 */
	protected abstract CollectionHandler getCollectionHandler(v name);
	
	/**
	 * 获取mongo数据对象
	 * @param info
	 * @return MongoQueryVO
	 */
	protected abstract MongoQueryVO getMongoQueryVO(K info);
	
	/**
	 * 获取集合
	 * @param name
	 * @return collectionName
	 */
	protected abstract String getCollectionName(v name);
	
	/**
	 * 判断是否需要更新
	 * @return
	 */
	protected abstract boolean checkIsNeedTpUpdate(String tradeFlow) throws SQLException;
	
	/**
	 * 获取交易明细
	 * @param tradeFlow
	 * @param collectInfo
	 * @return
	 */
	protected abstract CollectInfo checkIsNeedTpUpdateCollect(String tradeFlow) throws SQLException;
	
	/**
	 * 更新交易明细并发送MQ消息
	 * @param queryVO
	 * @param response
	 * @return 
	 */
	protected abstract boolean updateAndSendMQ(MongoQueryVO queryVO, Response response);
	
	/**
	 * 运行方法
	 * 
	 * @param thirdType
	 *            第三方通道编码
	 * @param name
	 *            转发程序名称
	 * @return
	 * @throws SQLException 
	 */
	public void execute(K info, v name)	throws PlatformException, SQLException {
		
		MongoQueryVO queryVO = getMongoQueryVO(info);
		String collectionName = getCollectionName(name);
		
		handler = getCollectionHandler(name);
		// 后处理结果状态  默认为true
		boolean result = true;
		
		try {
			// 查询通道接口
			logger.info("----------------------查询通道开始------------------------------");
			Response response = channelService.queryCommond(preProcess(info));
			
			logger.info("查询通道响应response：flowId=" + response.getFlowId() + " ,code=" + response.getCode() + ",transRepCode=" + response.getBankRepCode() + ", msg=" + response.getMsg());
			
			// 返回响应对象
			if (response != null) {
				
				// 判断通道层返回结果是否为 成功、失败的明确交易状态
				if (TppConstants.TRADE_STATE_SUCCESS.getCode().equals(response.getCode()) || TppConstants.TRADE_STATE_FAILER.getCode().equals(response.getCode())) {
					
					try {
						boolean flag = false;
						
						CollectInfo collectInfo = null;
						
						//获取交易明细
						collectInfo = checkIsNeedTpUpdateCollect(queryVO.getTradeFlow());
						// 交易明细存在
						if (collectInfo != null) {
							
							// 判断交易状态为中间态，则返回true，需要更新处理交易
							if (Constants.TppConstants.TRADE_STATE_MIDDLE.getCode().equals(collectInfo.getStatus())) {
								
								flag = true;
							}
						}
						// 判断交易明细表中交易状态是否为成功或失败，如果不是则进行更新操作
						if (flag) {
							
							// 更新oracle操作
							boolean updateFlag = updateAndSendMQ(queryVO, response);
							// 更新成功
							if (updateFlag) {
								// 如果已经更新成功，则从当前Collection内移除
								handler.delete(collectionName, queryVO);
								
							} else {
								result = false;
							}
						} else {
							
							// 从当前集合里面移除记录
							handler.delete(collectionName, queryVO);
						}
						//通道层查询成功 金额添加到缓存
						if(null != collectInfo){
							logger.info("通道层查询完成   --- collectInfo  第三方通道编码: "+ collectInfo.getPaySysNo()+",  金额: "+ collectInfo.getAmount()+" , 商户编码:"+collectInfo.getSpare2());
						} else {
							logger.info("通道层查询完成   --- collectInfo :" +collectInfo);
						}
						if(TppConstants.TRADE_STATE_SUCCESS.getCode().equals(response.getCode())){
							//TODO
							CacheUtil.addAmount(cacheClient,collectInfo);
						}
						
						//通道层查询失败  失败次数添加到缓存
						if(TppConstants.TRADE_STATE_FAILER.getCode().equals(response.getCode())){
							//TODO
							CacheUtil.addFailTimes(cacheClient,collectInfo);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						result = false;
					}
				} else {
					result = false;
				}
			}
		} catch (Exception e) {
			logger.error("查询通道异常"+e.getMessage(), e);
			result = false;
			
		} finally {
			if (!result) {
				// 从当前collection中移动至下一collection中 
				handler.moveAndDelete(collectionName, queryVO);
			}
		}
	}
	

}
