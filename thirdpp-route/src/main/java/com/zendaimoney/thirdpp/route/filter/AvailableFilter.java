package com.zendaimoney.thirdpp.route.filter;

import com.zendaimoney.thirdpp.route.Filter;
import com.zendaimoney.thirdpp.route.FilterChain;
import com.zendaimoney.thirdpp.route.entity.SysThirdChannelInfoEntity;
import com.zendaimoney.thirdpp.route.entity.ThirdFieldMapperEntity;
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
 * @ClassName: AvailableFilter
 * @Description: 业务可用逻辑 （第一个路由链）
 * @author: Calvin
 * @date: 2017年5月4日
 */
public class AvailableFilter implements Filter {

    public final ZDLogger logger   = LogUtil.getInstance();
    ZDLogDto       zdLogDto = new ZDLogDto(this.getClass());
    
    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        zdLogDto.setUuid(request.getUuid());
        // 业务处理
        try {
            logger.info(zdLogDto.clear().setHeInfo("开始执行可用业务逻辑"));
            long before = System.currentTimeMillis();

            List<RouteThirdChannelReqVO> channels = filterChain.getChannels();
            TaskReqVO taskReqVO = (TaskReqVO) request;
            List<RouteThirdChannelReqVO> respChannels = this.bankAvailable(
                this.channelAvailable(channels, taskReqVO), taskReqVO);
            filterChain.setChannels(respChannels);

            long end = System.currentTimeMillis();
            
            logger.info(zdLogDto.clear().setTitleInfo("符合的通道").setMsg(respChannels));
            logger.info(zdLogDto.clear().setHeInfo("结束执行可用业务逻辑, 耗时(" + (end - before) + "ms)"));

            if (respChannels != null && respChannels.size() > 0) {//通道列表 >=1 继续路由
                // 继续下一级业务处理
                filterChain.doFilter(request, response, filterChain);
            } else {//没有路由
                throw new PlatformException(PlatformErrorCode.ROUTE_AVAILABLE_ERROR);
            }

        } catch (Exception e) {//异常 中断路由
            logger.error(zdLogDto.clear().setTitleInfo("可用业务逻辑异常").setMsg(e.getMessage()));
            throw new PlatformException(PlatformErrorCode.ROUTE_AVAILABLE_ERROR, e.getCause());
        }
    }

    //产品通道是否可用

    private List<RouteThirdChannelReqVO> channelAvailable(List<RouteThirdChannelReqVO> channels,
                                                          TaskReqVO taskReqVO) {
        List<RouteThirdChannelReqVO> chs = new ArrayList<RouteThirdChannelReqVO>();

        SysThirdChannelInfoEntity thirdChannel = null;
        int let = channels.size();
        for (int i = 0; i < let; i++) {
            thirdChannel = RouteQueryInfoUtil.getSysThirdChannelInfoEntity(channels.get(i)
                .getThirdTypeNo(), taskReqVO.getInfoCategoryCode());
            if (thirdChannel != null) {
                if (1 == thirdChannel.getStatus()) { //状态(0业务通道关闭1业务通道开启)
                    chs.add(channels.get(i));
                }
            }
        }
        return chs;
    }

    //银行是否可用
    private List<RouteThirdChannelReqVO> bankAvailable(List<RouteThirdChannelReqVO> channels,
                                                       TaskReqVO taskReqVO) {
        List<RouteThirdChannelReqVO> chs = new ArrayList<RouteThirdChannelReqVO>();

        ThirdFieldMapperEntity thirdFieldMapper = null;
        int let = channels.size();
        for (int i = 0; i < let; i++) {
            thirdFieldMapper = RouteQueryInfoUtil.getThirdFieldMapperEntity(channels.get(i)
                .getThirdTypeNo(), taskReqVO.getPayerBankCode());
            if (thirdFieldMapper != null) {
                if (1 == thirdFieldMapper.getStatus()) { //状态(0银行通道关闭1银行通道开启)
                    chs.add(channels.get(i));
                }
            }
        }
        return chs;
    }

}
