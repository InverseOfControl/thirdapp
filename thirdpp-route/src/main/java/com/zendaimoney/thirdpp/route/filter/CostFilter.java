package com.zendaimoney.thirdpp.route.filter;


import com.alibaba.fastjson.serializer.BigDecimalCodec;
import com.zendaimoney.thirdpp.route.Filter;
import com.zendaimoney.thirdpp.route.FilterChain;
import com.zendaimoney.thirdpp.route.entity.RouteChannelCostEntity;
import com.zendaimoney.thirdpp.route.entity.ThirdFieldMapperEntity;
import com.zendaimoney.thirdpp.route.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.route.pub.vo.Request;
import com.zendaimoney.thirdpp.route.pub.vo.Response;
import com.zendaimoney.thirdpp.route.pub.vo.RouteThirdChannelReqVO;
import com.zendaimoney.thirdpp.route.pub.vo.TaskReqVO;
import com.zendaimoney.thirdpp.route.utils.LogUtil;
import com.zendaimoney.thirdpp.route.utils.RouteQueryInfoUtil;
import com.zendaimoney.thirdpp.route.utils.ZDLogDto;
import com.zendaimoney.thirdpp.route.utils.ZDLogger;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: CostFilter
 * @Description: 业务成本逻辑
 * @author: Calvin
 * @date: 2017年5月4日 
 */
public class CostFilter implements Filter {
    
    private final ZDLogger  logger = LogUtil.getInstance();

	@Override
    public void doFilter(Request request, Response response, FilterChain filterChain)  {
        ZDLogDto  zdLogDto = new ZDLogDto(this.getClass());
        zdLogDto.setUuid(request.getUuid());
        // 业务处理
        try {
            logger.info(zdLogDto.clear().setHeInfo("开始执行业务成本逻辑"));
            long before = System.currentTimeMillis();

            TaskReqVO taskReqVO = (TaskReqVO) request;
            List<RouteThirdChannelReqVO> channels = filterChain.getChannels();
            List<RouteThirdChannelReqVO> respChannels = cost(taskReqVO, channels);//成本算法
            long end = System.currentTimeMillis();
            
            logger.info(zdLogDto.clear().setTitleInfo("符合的通道").setMsg(respChannels));
            logger.info(zdLogDto.clear().setHeInfo("结束执行业务成本逻辑, 耗时(" + (end - before) + "ms)"));
           
            //无可用路由
            if(null == respChannels || respChannels.size() == 0){
            	
            	logger.info(zdLogDto.clear().setTitleInfo("业务成本逻辑：").setMsg("计算成本后无可用通道!"));
            	response.setCode(PlatformErrorCode.ROUTE_AVAILABLE_ERROR.getErrorCode());
            	response.setMsg(PlatformErrorCode.ROUTE_AVAILABLE_ERROR.getDefaultMessage());
            }
            //路由成功
            if(null != respChannels && respChannels.size() == 1){

                response.setThirdTypeNo(respChannels.get(0).getThirdTypeNo());

            }
            //继续下一级业务处理
            if(null != respChannels && respChannels.size() > 1){
            	
                filterChain.setChannels(respChannels);
                filterChain.doFilter(request, response, filterChain);
            }

        } catch (Exception e) {
        	// 异常继续下一级业务处理
            logger.error(zdLogDto.clear().setMsg("业务成本逻辑异常").setMsg(e.getMessage()));
            filterChain.doFilter(request, response, filterChain);
        }
    }
    
    //成本算法
    /**
     * 
     * 
     * @param taskReqVO 入参
     * @param channels 通道列表
     * @return
     */
	private List<RouteThirdChannelReqVO> cost(TaskReqVO taskReqVO, List<RouteThirdChannelReqVO> channels){
        ZDLogDto  zdLogDto = new ZDLogDto(this.getClass());
        int length = channels.size();
        BigDecimal cost = null;//总成本
        Map<RouteThirdChannelReqVO, BigDecimal> map = new HashMap<RouteThirdChannelReqVO, BigDecimal>();
        
        for (int i = 0; i < length; i++) {
            String thirdTypeNo = channels.get(i).getThirdTypeNo();//通道编码
            //
            
            
            List<RouteChannelCostEntity> costEntities = RouteQueryInfoUtil.getCostEntity(thirdTypeNo); //通道对应的费率
            logger.info(zdLogDto.clear().setTitleInfo(channels.get(i).getThirdChannelName()+"通道的费率").setMsg(costEntities));
            
            List<BigDecimal> costs = this.decomposeAmount(taskReqVO.getAmount(),thirdTypeNo, taskReqVO.getPayerBankCode()); //拆单后的金额列表
            int let = costs.size(); // 拆单笔数
            
            logger.info(zdLogDto.clear().setTitleInfo("拆单数").setMsg(let+"笔"));
            logger.debug(zdLogDto.clear().setTitleInfo("拆单结果").setMsg(costs));
            
            BigDecimal firstCost = this.singleCost(costs.get(0), costEntities);//第一笔成本
            if(firstCost != null){
                if(let > 1){ // 单笔成本 * 笔数-1 + 最后一笔成本
                    if(costs.get(0).compareTo(costs.get(let-1)) > 0){ //最后一笔金额 小于 第一笔
                        BigDecimal lastCost = this.singleCost(costs.get(let-1), costEntities);//最后一笔成本
                        cost =  firstCost.multiply(new BigDecimal(let-1)).add(lastCost);
                        
                    }else{ //最后一笔金额 等于 第一笔
                        cost =  firstCost.multiply(new BigDecimal(let)); //单笔成本 * 笔数
                    }
                }else{
                    cost = firstCost;
                }
            }
            
            if(cost !=null ){
                logger.info(zdLogDto.clear().setTitleInfo(channels.get(i).getThirdChannelName()+"通道的成本").setMsg(cost.toPlainString()+"元"));
                map.put(channels.get(i), cost);
                cost = null;
            }
            
        }
        
        //都没有设置费率时
        if(map.isEmpty())
            return channels;
        
        return  this.getMinCostChannel(map);
    }
    
    //拆单
    /**
     * 
     * 
     * @param amount 总金额 (划扣金额)
     * @param thirdTypeNo 通道编码
     * @param bankCode 银行编码
     * @return
     */
    private List<BigDecimal> decomposeAmount(BigDecimal amount,String thirdTypeNo,String bankCode){
        List<BigDecimal> bigDecimals = new ArrayList<BigDecimal>();
        // 第三方通道码 + "_" + 银行编码
        ThirdFieldMapperEntity temp = RouteQueryInfoUtil.getThirdFieldMapperEntity(thirdTypeNo, bankCode);
        //笔数
        int count = 0; 
      //最大限额为Null 或者为0时
        if(temp == null || temp.getCollectMaxMoney() == null ||  temp.getCollectMaxMoney().compareTo(new BigDecimal(0)) <= 0){
            count = 1;
            bigDecimals.add(amount);
        }else {
            count = amount.divide(temp.getCollectMaxMoney(),BigDecimal.ROUND_DOWN).intValue();
            for(int i=0;i<count;i++){
                bigDecimals.add(temp.getCollectMaxMoney());
            }
            
            //已拆单金额
            BigDecimal tempBigDecimal = temp.getCollectMaxMoney().multiply(new BigDecimal(count));
            
            if(amount.compareTo(tempBigDecimal) > 0 ){ // 总金额 > 已拆单金额 (表示有最后一笔)
                bigDecimals.add(amount.subtract(tempBigDecimal));
            }
        }
        return bigDecimals;
    }
    
    //单笔的成本
    /**
     * 
     * 
     * @param amount 金额 （单笔金额）
     * @param costEntities 费率list
     * @return
     */
    private BigDecimal singleCost(BigDecimal amount,List<RouteChannelCostEntity> costEntities){
        BigDecimal cost = null;
        if(costEntities != null){
            int length = costEntities.size();
            for (int i = 0; i < length; i++) {
                BigDecimal min =  costEntities.get(i).getMinAmount();//
                BigDecimal max = costEntities.get(i).getMaxAmount();
                if((amount.compareTo(min)>=0)){ //金额 > 最小值
                    if(max != null && amount.compareTo(max) > 0){ //当金额 > 最大值(最大不为空时)
                        continue;
                    }
                    if("1".equals(costEntities.get(i).getCostType())){//固定
                        cost = costEntities.get(i).getFixedAmount().setScale(4,BigDecimal.ROUND_HALF_UP);;
                    }else if("2".equals(costEntities.get(i).getCostType())){//百分比
                        cost = costEntities.get(i).getPercent().multiply(amount).setScale(4,BigDecimal.ROUND_HALF_UP);;
                        if("1".equals(costEntities.get(i).getHasLimitAmount())){//是 封顶
                            if(cost.compareTo(costEntities.get(i).getLimitAmount()) >=0){
                                cost = costEntities.get(i).getLimitAmount().setScale(4,BigDecimal.ROUND_HALF_UP);;
                            }
                        }
                    }
                }
            }
        }
        
        return cost;
        
    }
    
    //取最小成本
    private List<RouteThirdChannelReqVO> getMinCostChannel(Map<RouteThirdChannelReqVO, BigDecimal> map) {
        List<RouteThirdChannelReqVO>  chs = new ArrayList<RouteThirdChannelReqVO>();
        BigDecimal minCost = new BigDecimal(Long.MAX_VALUE);
        for (Map.Entry<RouteThirdChannelReqVO, BigDecimal> entry : map.entrySet()) {
            if(minCost.compareTo(entry.getValue()) >0){
                chs.clear();
                minCost = entry.getValue();
                chs.add(entry.getKey());
            }else if(minCost.compareTo(entry.getValue()) == 0){
                chs.add(entry.getKey());
            }
            
        }
        return chs;
    }
    
    
}
