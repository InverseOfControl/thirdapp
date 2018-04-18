package com.zendaimoney.thirdpp.route.filter;


import com.ymkj.base.cache.redis.client.ICacheClient;
import com.zendaimoney.thirdpp.route.Filter;
import com.zendaimoney.thirdpp.route.FilterChain;
import com.zendaimoney.thirdpp.route.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.route.exception.PlatformException;
import com.zendaimoney.thirdpp.route.pub.vo.Request;
import com.zendaimoney.thirdpp.route.pub.vo.Response;
import com.zendaimoney.thirdpp.route.pub.vo.RouteThirdChannelReqVO;
import com.zendaimoney.thirdpp.route.pub.vo.TaskReqVO;
import com.zendaimoney.thirdpp.route.utils.LogUtil;
import com.zendaimoney.thirdpp.route.utils.RouteQueryInfoUtil;
import com.zendaimoney.thirdpp.route.utils.ZDLogDto;
import com.zendaimoney.thirdpp.route.utils.ZDLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ControlFilter
 * @Description: 业务控制逻辑
 * @author: Calvin
 * @date: 2017年5月4日 
 */
public class ControlFilter implements Filter {

    private final ZDLogger        logger = LogUtil.getInstance();
   
    
    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain)  {
        ZDLogDto  zdLogDto = new ZDLogDto(this.getClass());
        zdLogDto.setUuid(request.getUuid());
        // 业务处理
        try {

            logger.info(zdLogDto.clear().setHeInfo("开始执行业务控制逻辑"));
            long before = System.currentTimeMillis();

            List<RouteThirdChannelReqVO> channels = filterChain.getChannels();
            ICacheClient iCacheClient = filterChain.getCacheClient();
            TaskReqVO taskReqVO = (TaskReqVO) request;
            List<RouteThirdChannelReqVO> respChannels = this.getChannel(channels,iCacheClient,taskReqVO.getPayerBankCardNo());
            
            long end = System.currentTimeMillis();
            
            logger.info(zdLogDto.clear().setTitleInfo("符合的通道").setMsg(respChannels));
            logger.info(zdLogDto.clear().setHeInfo("结束执行业务控制逻辑, 耗时(" + (end - before) + "ms)"));

            if(respChannels!= null && respChannels.size() >1){// 继续下一级业务处理
                filterChain.setChannels(respChannels);
                filterChain.doFilter(request, response, filterChain);
            }else if(respChannels!= null &&  respChannels.size() == 1){//路由成功
                response.setThirdTypeNo(respChannels.get(0).getThirdTypeNo());

            }else{ 
            	// 没有路由
            	response.setCode(PlatformErrorCode.ROUTE_AVAILABLE_ERROR.getErrorCode());
            	response.setMsg(PlatformErrorCode.ROUTE_AVAILABLE_ERROR.getDefaultMessage());
//              throw new PlatformException(PlatformErrorCode.ROUTE_CONTROL_ERROR);
            }
            
        } catch (Exception e) {
            logger.error(zdLogDto.clear().setTitleInfo("业务控制逻辑异常").setMsg(e.getMessage()));
            filterChain.doFilter(request, response, filterChain);
        }
    }
    
    //判断失败次数
    private List<RouteThirdChannelReqVO> getChannel(List<RouteThirdChannelReqVO> channels,ICacheClient iCacheClient,String cardNo) throws Exception{
        ZDLogDto  zdLogDto = new ZDLogDto(this.getClass());
        List<RouteThirdChannelReqVO> chs = new ArrayList<RouteThirdChannelReqVO>();
        Integer fTimes = null;
        int let = channels.size();
        for (int i = 0; i < let; i++) {
            fTimes = RouteQueryInfoUtil.getFailTimesCache(iCacheClient, channels.get(i).getThirdTypeNo(), cardNo);
            if(fTimes < channels.get(i).getFailTimes()){
                chs.add(channels.get(i));
            }

            logger.info(zdLogDto.clear().setTitleInfo("通道:"+channels.get(i).getThirdChannelName()+"-"+"银行卡号:"+cardNo+" 总失败次数").setMsg(channels.get(i).getFailTimes()+"次"));
            logger.info(zdLogDto.clear().setTitleInfo("通道:"+channels.get(i).getThirdChannelName()+"-"+"银行卡号:"+cardNo+" 当前失败次数").setMsg(fTimes+"次"));
        }
        return chs;
    }

}
