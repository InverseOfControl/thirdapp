package com.zendaimoney.thirdpp.channel.service;

import com.zendaimoney.thirdpp.channel.dao.SignMessageInfoDao;
import com.zendaimoney.thirdpp.channel.entity.SignMessageInfo;
import com.zendaimoney.thirdpp.channel.util.LogPrn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * 协议支付签约短信触发
 *
 * @author wul
 */
@Transactional
@Service
public class SignMessageInfoService {

    // 日誌工具類
    private static final LogPrn logger = new LogPrn(SignMessageInfoService.class);

    @Resource(name = "signMessageInfoDao")
    private SignMessageInfoDao signMessageInfoDao;

    /**
     * 保存或更新签约短信触发信息
     *
     * @param signMessageInfo
     * @return
     */
    public SignMessageInfo saveOrUpdateSignInfo(SignMessageInfo signMessageInfo) {
        if (signMessageInfo.getId() == null) {
            signMessageInfoDao.insert(signMessageInfo);
        } else {
            signMessageInfoDao.update(signMessageInfo);
        }

        return signMessageInfo;
    }

    /**
     * 根据请求ID，查询短信触发信息
     *
     * @param reqId
     * @return
     */
    public SignMessageInfo getByReqId(String reqId) {
        try{
            return signMessageInfoDao.queryByReqId(reqId);
        }catch (SQLException e){
            logger.error("查询协议支付短信触发异常-交易流水", e);
        }

        return null;
    }

    /**
     * 根据tradeFlow，查询短信触发信息
     *
     * @param tradeFlow
     * @return
     */
    public SignMessageInfo getByTradeFlow(String tradeFlow) {
        try{
            return signMessageInfoDao.queryByTradeFlow(tradeFlow);
        }catch (SQLException e){
            logger.error("查询协议支付短信触发异常-通道流水", e);
        }

        return null;
    }

}
