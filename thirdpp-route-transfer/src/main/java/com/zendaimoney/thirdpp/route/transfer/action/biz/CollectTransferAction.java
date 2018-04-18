package com.zendaimoney.thirdpp.route.transfer.action.biz;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.route.pub.vo.TaskReqVO;
import com.zendaimoney.thirdpp.route.transfer.action.Action;
import com.zendaimoney.thirdpp.route.transfer.entity.CollectTask;
import com.zendaimoney.thirdpp.route.transfer.entity.Response;
import com.zendaimoney.thirdpp.route.transfer.exception.PlatformException;
import com.zendaimoney.thirdpp.route.transfer.service.RouteTransferSerivce;
import com.zendaimoney.thirdpp.route.transfer.util.LogPrn;

/**
 * 路由转发action
 * 
 * @author 00231257
 * 
 */
@Component
public class CollectTransferAction extends Action<CollectTask,TaskReqVO> {

	// 日志工具类
	private static final LogPrn logger = new LogPrn(CollectTransferAction.class);


	@Autowired
	private RouteTransferSerivce routeTransferSerivce;


	/**
	 * 核心处理
	 */
	@Override
	protected Response transfer() throws PlatformException {
	    try {
	        Response response = new Response();
	        routeTransferSerivce.transfer();
            int count = routeTransferSerivce.queryTaskCount();
            if(count>0){
                response.setEmpty(false);
            }else {
                response.setEmpty(true);
            }
            return response;
            
        } catch (Exception e) {
            logger.error("路由转发异常", e);
            throw new PlatformException("路由转发异常", e);
        }
	}


}
