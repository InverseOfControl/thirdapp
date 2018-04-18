package com.zendaimoney.thirdpp.trade.service;

import com.zendaimoney.thirdpp.trade.dao.RequestDao;
import com.zendaimoney.thirdpp.trade.dao.SignInfoDao;
import com.zendaimoney.thirdpp.trade.dao.SignMessageInfoDao;
import com.zendaimoney.thirdpp.trade.entity.SignInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Transactional
@Service
public class SignInfoService {

    @Resource(name = "requestDao")
    private RequestDao requestDao;

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

}
