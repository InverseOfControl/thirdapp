package com.zendaimoney.thirdpp.route.transfer.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.route.pub.service.IRouteService;
import com.zendaimoney.thirdpp.route.pub.vo.Response;
import com.zendaimoney.thirdpp.route.pub.vo.TaskReqVO;
import com.zendaimoney.thirdpp.route.transfer.conf.ServerConfig;
import com.zendaimoney.thirdpp.route.transfer.entity.CollectInfo;
import com.zendaimoney.thirdpp.route.transfer.entity.CollectTask;
import com.zendaimoney.thirdpp.route.transfer.exception.PlatformException;
import com.zendaimoney.thirdpp.route.transfer.util.Constants;
import com.zendaimoney.thirdpp.route.transfer.util.IDGenerateUtil;
import com.zendaimoney.thirdpp.route.transfer.util.LogPrn;
import com.zendaimoney.thirdpp.route.transfer.util.StringUtil;

@Service
@Transactional
public class RouteTransferSerivce {

    @Autowired
    private CollectTaskService  collectTaskService;
    @Autowired
    private CollectInfoService  collectInfoService;
    @Autowired
    private IRouteService       iRouteService;

    private static final LogPrn logger = new LogPrn(RouteTransferSerivce.class);
    
    public void  transfer() throws PlatformException {
    	
        try {
            // 组装查询参数.
            HashMap<String, Object> param = new HashMap<String, Object>();
            // 发送通道
            param.put("paySysNo",  ServerConfig.systemConfig.getThirdTypeNo());
            
            CollectTask collectTask = collectTaskService.get(param);
            // 调用支付路由
            TaskReqVO taskReqVO = getTaskVO(collectTask);
            if (taskReqVO != null) {
            	logger.info("调用路由开始"+taskReqVO.toString());
                Response response = iRouteService.route(taskReqVO);
                logger.info("response code:" + response.getCode()
						+ ",response FlowId:" + response.getFlowId()
						+ "response msg:" + response.getMsg());
                CollectTask tempDto = new CollectTask();
                tempDto.setId(collectTask.getId());
                tempDto.setRouteStatus(Integer.valueOf(Constants.TppConstants.ROUTE_STATUS_DONE.getCode()));
                if (filterData(response) != null) {
                	//路由成功
                    tempDto.setPaySysNo(response.getThirdTypeNo());
                }else {
                	//路由失败
                    this.routeFail(collectTask);
                }
                
                //更新routeStatus 避免线程重复扫描
                int i = collectTaskService.update(tempDto);
                if (i == 1) {
                    logger.info("线程:" + Thread.currentThread().getName() + "更新成功-"+ JSON.toJSONString(tempDto));
                } else {
                    logger.info("线程:" + Thread.currentThread().getName() + "更新失败-"+ JSON.toJSONString(tempDto));
                }
            }

        } catch (Exception e) {
            logger.error("路由转发异常", e);
            throw new PlatformException("路由转发异常", e);
        }
    }
    
    public int queryTaskCount() throws SQLException {
    	 
    	int result = 0;
        // 组装查询参数.
        HashMap<String, Object> param = new HashMap<String, Object>();
        // 发送通道
        param.put("paySysNo",  ServerConfig.systemConfig.getThirdTypeNo());
        result = collectTaskService.queryTaskCount(param);
        return result;
    }
    
    /**
     * 路由失败
     * 
     * @param task
     */
    private void routeFail(CollectTask task) {
    	
        // 被拆分交易集
        List<CollectInfo> tradeInfos = new ArrayList<CollectInfo>();
        // 待更新task
        CollectTask updateTask = new CollectTask();
        // 拆分数
        int separateCount = 1;
        CollectInfo tradeInfo = new CollectInfo();
        // 把task数据复制到tradeInfo
        BeanUtils.copyProperties(task, tradeInfo);
        // tradeInfo个别属性定制
        tradeInfo.setTaskId(task.getId());
        // 设置状态-交易失败
        tradeInfo.setStatus(Constants.TppConstants.TRADE_STATE_FAILER.getCode());
        tradeInfo.setFailReason("调用支付路由失败");
        // 生成交易流水号
        tradeInfo.setTradeFlow(IDGenerateUtil.createTradeFlow(BizType.BROKER_COLLECT.getCode()));
        // 设置通知查询状态-不需要通知查询
        tradeInfo.setNotifyQueryStatus(CollectInfo.NOTIFY_QUERY_STATUS_NO);

        tradeInfos.add(tradeInfo);
        // 设置task拆分信息
        // 拆分状态-不需要拆分
        updateTask.setIsSeparate(Integer.valueOf(Constants.TppConstants.TASK_IS_SEPARATE_NO
            .getCode()));
        // 拆分数-1
        updateTask.setSeparateCount(separateCount);
        // 设置待更新task属性
        updateTask.setId(task.getId());
        // 状态-已发送
        updateTask.setStatus(Integer.valueOf(Constants.TppConstants.SEND_STATUS_SENDED.getCode()));
        // 转发
        this.transfer(updateTask, tradeInfos);
        // 写入通知mq,需要自己捕获异常
        try {
            collectInfoService.sendNotifyMergeMsg(tradeInfo);
        } catch (Exception e) {
            logger.error(" 写入通知mq异常：", e);
        }
        
    }

    private TaskReqVO getTaskVO(CollectTask collectTask) {
    	
        TaskReqVO taskReqVO = null;
        if (collectTask != null) {
            taskReqVO = new TaskReqVO();
            taskReqVO.setAmount(collectTask.getAmount());
            taskReqVO.setPayerBankCardNo(collectTask.getPayerBankCardNo());
            taskReqVO.setPayerBankCode(collectTask.getPayerBankCode());
            taskReqVO.setInfoCategoryCode(collectTask.getInfoCategoryCode());
        }
        
        return taskReqVO;

    }

    private Response filterData(Response response) {
    	
        if (response == null) {
            logger.error("调用支付路由失败");
            return null;
        }

        if (StringUtil.isEmpty(response.getCode())) {
            logger.error("调用支付路由失败：返回状态码为空");
            return null;
        }

        if (StringUtil.isEmpty(response.getThirdTypeNo())) {
            logger.error("调用支付路由失败：通道编码为空");
            return null;
        }

        if (!response.getCode().equals("000000")) {//路由失败
            logger.error("调用支付路由失败：" + response.getMsg());
            return null;
        }

        return response;
    }
    
    /**
     * 待收任务转发(转发分成两步：任务更新、交易信息入库)
     * 
     * @param task
     * @param tradeInfos
     *            task拆分交易记录集
     */
    private void transfer(CollectTask task, List<CollectInfo> tradeInfos) {
    	
        if (task != null) {
            collectTaskService.update(task);
        }
        // task拆分交易记录集
        if (tradeInfos != null && tradeInfos.size() > 0) {
            for (int i = 0; i < tradeInfos.size(); i++) {
                System.out.println(JSON.toJSON(tradeInfos.get(i)));
                collectInfoService.insert(tradeInfos.get(i));
            }
        }

    }
}
