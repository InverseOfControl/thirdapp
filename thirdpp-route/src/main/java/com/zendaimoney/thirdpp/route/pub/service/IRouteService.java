package com.zendaimoney.thirdpp.route.pub.service;

import com.zendaimoney.thirdpp.route.pub.vo.Response;
import com.zendaimoney.thirdpp.route.pub.vo.TaskReqVO;

/**
 * Created by YM20051 on 2017/5/24.
 */
public interface IRouteService {

    public Response route(TaskReqVO taskReqVO);
}
