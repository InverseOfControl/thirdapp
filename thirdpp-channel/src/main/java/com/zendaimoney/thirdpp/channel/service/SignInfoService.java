package com.zendaimoney.thirdpp.channel.service;

import com.zendaimoney.thirdpp.channel.dao.SignInfoDao;
import com.zendaimoney.thirdpp.channel.entity.SignInfo;
import com.zendaimoney.thirdpp.channel.util.LogPrn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;

@Transactional
@Service
public class SignInfoService {

    // 日誌工具類
    private static final LogPrn logger = new LogPrn(SignInfoService.class);

    @Resource(name = "signInfoDao")
    private SignInfoDao signInfoDao;

    /**
     * 保存或更新签约短信触发信息
     *
     * @param signInfo
     * @return
     */
    public SignInfo saveOrUpdateSignInfo(SignInfo signInfo) {
        if (signInfo.getId() == null) {
            signInfoDao.insert(signInfo);
        } else {
            signInfoDao.update(signInfo);
        }

        return signInfo;
    }

    /**
     * 根据tradeFlow，查询短信触发信息
     *
     * @param reqId
     * @return
     */
    public SignInfo getByReqId(String reqId) {
        try{
            return signInfoDao.queryByReqId(reqId);
        }catch (SQLException e){
            logger.error("查询协议支付异常-通道流水", e);
        }

        return null;
    }
}
