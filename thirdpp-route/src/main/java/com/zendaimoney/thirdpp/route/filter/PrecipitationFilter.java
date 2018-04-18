package com.zendaimoney.thirdpp.route.filter;


import com.ymkj.base.cache.redis.client.ICacheClient;
import com.zendaimoney.thirdpp.route.Filter;
import com.zendaimoney.thirdpp.route.FilterChain;
import com.zendaimoney.thirdpp.route.pub.vo.Request;
import com.zendaimoney.thirdpp.route.pub.vo.Response;
import com.zendaimoney.thirdpp.route.pub.vo.RouteThirdChannelReqVO;
import com.zendaimoney.thirdpp.route.pub.vo.TaskReqVO;
import com.zendaimoney.thirdpp.route.utils.LogUtil;
import com.zendaimoney.thirdpp.route.utils.RouteQueryInfoUtil;
import com.zendaimoney.thirdpp.route.utils.ZDLogDto;
import com.zendaimoney.thirdpp.route.utils.ZDLogger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: PrecipitationFilter
 * @Description: 业务沉淀逻辑
 * @author: Calvin
 * @date: 2017年5月4日 
 */
public class PrecipitationFilter implements Filter {

    private final ZDLogger        logger = LogUtil.getInstance();
    
    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        ZDLogDto  zdLogDto = new ZDLogDto(this.getClass());
        zdLogDto.setUuid(request.getUuid());
        // 业务处理
        try {
            logger.info(zdLogDto.clear().setHeInfo("开始执行业务沉淀逻辑"));
            long before = System.currentTimeMillis();

            List<RouteThirdChannelReqVO> channels = filterChain.getChannels();
            TaskReqVO taskReqVO = (TaskReqVO) request;
            List<RouteThirdChannelReqVO> respChannels = this.getChannel(channels,filterChain.getCacheClient(),taskReqVO.getInfoCategoryCode());

            
            long end = System.currentTimeMillis();
            
            logger.info(zdLogDto.clear().setTitleInfo("符合的通道").setMsg(respChannels));
            logger.info(zdLogDto.clear().setHeInfo("结束执行业务沉淀逻辑, 耗时(" + (end - before) + "ms)"));
            
            if(respChannels!= null &&  respChannels.size() == 1){//路由成功

                response.setThirdTypeNo(respChannels.get(0).getThirdTypeNo());
            }else{//继续下个路由

                filterChain.setChannels(respChannels);
                filterChain.doFilter(request, response, filterChain);
            }

        } catch (Exception e) {
            logger.error(zdLogDto.clear().setTitleInfo("业务沉淀逻辑异常").setMsg(e.getMessage()));
            filterChain.doFilter(request, response, filterChain);
        }
    }
    
    //判断沉淀量
    private List<RouteThirdChannelReqVO> getChannel(List<RouteThirdChannelReqVO> channels,ICacheClient iCacheClient,String infoCategoryCode) throws Exception{
        ZDLogDto  zdLogDto = new ZDLogDto(this.getClass());
        List<RouteThirdChannelReqVO> chs = new ArrayList<RouteThirdChannelReqVO>();
        BigDecimal pCache = null;
        BigDecimal pDb = null;
        int let = channels.size();
        for (int i = 0; i < let; i++) {
            pCache = RouteQueryInfoUtil.getPrecipitationCache(iCacheClient, channels.get(i).getThirdTypeNo(), infoCategoryCode);
            pDb = RouteQueryInfoUtil.getPrecipitationDB(channels.get(i).getThirdTypeNo(),infoCategoryCode);
            if(pCache.compareTo(pDb) < 0){
                chs.add(channels.get(i));
            }
            
            logger.info(zdLogDto.clear().setTitleInfo("通道:"+channels.get(i).getThirdChannelName()+" 商户总沉淀量").setMsg(pDb+"元"));
            logger.info(zdLogDto.clear().setTitleInfo("通道:"+channels.get(i).getThirdChannelName()+" 当前沉淀量").setMsg(pCache+"元"));
        }
        
        if(chs.size() == 0){ //当商户没有沉淀量 或者 已到达沉淀量   返回所有列表
            chs.addAll(channels);
        }
        return chs;
    }

}
