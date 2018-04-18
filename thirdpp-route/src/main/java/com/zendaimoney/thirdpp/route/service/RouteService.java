package com.zendaimoney.thirdpp.route.service;

import com.ymkj.base.cache.redis.client.ICacheClient;
import com.zendaimoney.thirdpp.route.FilterChain;
import com.zendaimoney.thirdpp.route.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.route.exception.PlatformException;
import com.zendaimoney.thirdpp.route.pub.service.IRouteService;
import com.zendaimoney.thirdpp.route.pub.vo.Response;
import com.zendaimoney.thirdpp.route.pub.vo.TaskReqVO;
import com.zendaimoney.thirdpp.route.utils.LogUtil;
import com.zendaimoney.thirdpp.route.utils.ZDLogDto;
import com.zendaimoney.thirdpp.route.utils.ZDLogger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by YM20051 on 2017/5/25.
 */

@Component
public class RouteService implements IRouteService {

    public final ZDLogger logger   = LogUtil.getInstance();

    @Autowired
    private ICacheClient cacheClient;

    @Override
    public Response route(TaskReqVO taskReqVO) {

        Response response = new Response();
        try {
            if (taskReqVO == null) {
                throw new PlatformException(PlatformErrorCode.VALIDATE_ISNULL, "参数为空!");
            }
            if (StringUtils.isEmpty(taskReqVO.getPayerBankCardNo())) {
                throw new PlatformException(PlatformErrorCode.VALIDATE_ISNULL, "银行卡号为空!");
            }
            if (StringUtils.isEmpty(taskReqVO.getPayerBankCode())) {
                throw new PlatformException(PlatformErrorCode.VALIDATE_ISNULL, "付款方银编码为空!");
            }
            if (StringUtils.isEmpty(taskReqVO.getInfoCategoryCode())) {
                throw new PlatformException(PlatformErrorCode.VALIDATE_ISNULL, "信息类别为空!");
            }
            if (StringUtils.isEmpty(taskReqVO.getAmount())) {
                throw new PlatformException(PlatformErrorCode.VALIDATE_ISNULL, "划扣金额为空!");
            }
            FilterChain filterChain = new FilterChain(cacheClient);
            filterChain.doFilter(taskReqVO, response, filterChain);
            
        } catch (PlatformException e) {
            response.setCode(e.getRealCode());
            response.setMsg(e.getMessage());
        }
        return response;
    }
}
