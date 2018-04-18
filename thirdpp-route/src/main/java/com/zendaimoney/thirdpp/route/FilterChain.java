package com.zendaimoney.thirdpp.route;


import com.ymkj.base.cache.redis.client.ICacheClient;
import com.zendaimoney.thirdpp.route.pub.vo.Request;
import com.zendaimoney.thirdpp.route.pub.vo.Response;
import com.zendaimoney.thirdpp.route.pub.vo.RouteThirdChannelReqVO;

import java.util.ArrayList;
import java.util.List;

public class FilterChain implements Filter {

    private int index = 0;

    private ICacheClient cacheClient;
    /**
     * 通道列表
     */
    private List<RouteThirdChannelReqVO> channels;

    public FilterChain(ICacheClient cacheClient) {
        this.channels = new ArrayList<>();
        this.channels.addAll(RouteCacheContainer.routeThirdChannels.values());
        this.cacheClient = cacheClient;
    }

    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        if(index == RouteCacheContainer.filters.size()){
            return;
        }
        Filter filter = RouteCacheContainer.filters.get(index);
        index++;
        filter.doFilter(request, response, filterChain);
    }

    public List<RouteThirdChannelReqVO> getChannels() {
        return channels;
    }

    public void setChannels(List<RouteThirdChannelReqVO> channels) {
        this.channels = channels;
    }

    public ICacheClient getCacheClient() {
        return cacheClient;
    }

}
