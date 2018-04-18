package com.zendaimoney.thirdpp.route;

import com.zendaimoney.thirdpp.route.pub.vo.Request;
import com.zendaimoney.thirdpp.route.pub.vo.Response;


public interface Filter {
    
    public void doFilter(Request request, Response response, FilterChain filterChain);

}
