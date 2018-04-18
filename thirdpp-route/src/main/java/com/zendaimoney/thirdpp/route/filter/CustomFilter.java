package com.zendaimoney.thirdpp.route.filter;


import com.zendaimoney.thirdpp.route.Filter;
import com.zendaimoney.thirdpp.route.FilterChain;
import com.zendaimoney.thirdpp.route.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.route.exception.PlatformException;
import com.zendaimoney.thirdpp.route.pub.vo.Request;
import com.zendaimoney.thirdpp.route.pub.vo.Response;
import com.zendaimoney.thirdpp.route.pub.vo.RouteThirdChannelReqVO;
import com.zendaimoney.thirdpp.route.utils.LogUtil;
import com.zendaimoney.thirdpp.route.utils.SortUil;
import com.zendaimoney.thirdpp.route.utils.ZDLogDto;
import com.zendaimoney.thirdpp.route.utils.ZDLogger;

import java.util.List;

/**
 * @ClassName: CustomFilter
 * @Description: 业务手工配置
 * @author: Calvin
 * @date: 2017年5月4日 
 */
public class CustomFilter implements Filter {

    private final ZDLogger        logger = LogUtil.getInstance();
    
    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        ZDLogDto  zdLogDto = new ZDLogDto(this.getClass());
        zdLogDto.setUuid(request.getUuid());
        // 业务处理
        try {

            logger.info(zdLogDto.clear().setHeInfo("开始执行业务手工配置"));
            long before = System.currentTimeMillis();

            List<RouteThirdChannelReqVO> channels = filterChain.getChannels();
            RouteThirdChannelReqVO thirdChannelReqVO = this.getChannel(channels);

            long end = System.currentTimeMillis();
            
            logger.info(zdLogDto.clear().setTitleInfo("符合的通道").setMsg(thirdChannelReqVO));
            logger.info(zdLogDto.clear().setHeInfo("结束执行业务手工配置, 耗时(" + (end - before) + "ms)"));
            
            // 继续下一级业务处理
            if(thirdChannelReqVO !=null){//路由成功
                response.setThirdTypeNo(thirdChannelReqVO.getThirdTypeNo());
            }
            
        } catch (Exception e) {
            logger.error(zdLogDto.clear().setTitleInfo("业务手工配置异常").setMsg(e.getMessage()));
            throw new PlatformException(PlatformErrorCode.UNKNOW_ERROR);
        }
    }
    
    //取优先级最高的
    private RouteThirdChannelReqVO getChannel(List<RouteThirdChannelReqVO> channels){
        SortUil<RouteThirdChannelReqVO> sortList = new SortUil<RouteThirdChannelReqVO>();  
        //按priority排序  
         sortList.Sort_U(channels, "priority", SortUil.SORT_DESC);
        return channels.get(0);
    }
    
}
