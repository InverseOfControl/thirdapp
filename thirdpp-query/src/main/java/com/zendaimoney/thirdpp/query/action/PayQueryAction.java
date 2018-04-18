package com.zendaimoney.thirdpp.query.action;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.channel.pub.service.IChannelService;
import com.zendaimoney.thirdpp.channel.pub.vo.QueryReqVo;
import com.zendaimoney.thirdpp.common.enums.BizSys;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.query.action.annotations.QueryActionAnnotation;
import com.zendaimoney.thirdpp.query.entity.CollectInfo;
import com.zendaimoney.thirdpp.query.entity.PayInfo;
import com.zendaimoney.thirdpp.query.mongo.MongoQueryVO;
import com.zendaimoney.thirdpp.query.service.CollectionHandler;
import com.zendaimoney.thirdpp.query.service.PayInfoService;
import com.zendaimoney.thirdpp.query.service.impl.CollectionHandlerImpl;
import com.zendaimoney.thirdpp.query.util.Constants;

@QueryActionAnnotation(bizType = BizType.BROKER_PAY)
@Component("com.zendaimoney.thirdpp.query.action.PayQueryAction")
public class PayQueryAction extends Action<MongoQueryVO, String>{
	
	// 日志工具类
	public static Log logger = LogFactory.getLog(PayQueryAction.class);

	@Autowired
	private IChannelService channelService;
	
	@Autowired
	private PayInfoService payInfoService;
	
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
	 * 判断交易明细是否需要更新结果
	 */
	@Override
	protected boolean checkIsNeedTpUpdate(String tradeFlow) {
		
		// 获取处理的代收信息
		try {
			PayInfo payInfo = payInfoService.queryPayInfoByReqId(tradeFlow);
			
			// 交易明细存在
			if (payInfo != null) {
				
				// 判断交易状态为中间态，则返回true，需要更新处理交易
				if (!Constants.TppConstants.TRADE_STATE_MIDDLE.getCode().equals(payInfo.getStatus())) {
					
					return true;
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return false;
	}


	/**
	 * 更新交易明细并发送MQ
	 */
	@Override
	protected boolean updateAndSendMQ(MongoQueryVO queryVO, Response response){
		
		try {
			PayInfo payInfo = new PayInfo();
			payInfo.setTradeFlow(queryVO.getTradeFlow()); // 交易流水号
			payInfo.setStatus(response.getCode()); // 交易结果状态
			payInfo.setFailReason(response.getMsg() != null ? StringUtils.substring(response.getMsg(), 0, 150) : "");
			payInfo.setTransRepCode(response.getBankRepCode()); // 渠道返回状态
//			payInfo.setThirdReturnTime(CalendarUtils.getFormatNow()); // 回盘时间
			
			// 更新oracle操作
			int count = payInfoService.updateAndSendMQ(payInfo, queryVO);
			
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
	protected CollectInfo checkIsNeedTpUpdateCollect(String tradeFlow)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


}
