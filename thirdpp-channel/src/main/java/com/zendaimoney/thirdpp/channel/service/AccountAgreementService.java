package com.zendaimoney.thirdpp.channel.service;

import com.zendaimoney.thirdpp.channel.dao.AccountAgreementCardDao;
import com.zendaimoney.thirdpp.channel.dao.AccountAgreementDao;
import com.zendaimoney.thirdpp.channel.entity.AccountAgreement;
import com.zendaimoney.thirdpp.channel.entity.AccountAgreementCard;
import com.zendaimoney.thirdpp.channel.util.LogPrn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AccountAgreementService {

    // 日誌工具類
    private static final LogPrn logger = new LogPrn(AccountAgreementService.class);

    @Autowired
    private AccountAgreementDao accountAgreementDao;

    @Autowired
    private AccountAgreementCardDao accountAgreementCardDao;

    /**
     * 保存
     * @param accountAgreement
     * @return
     */
    public void saveOrUpdateAccountAgreement(AccountAgreement accountAgreement){
        if (accountAgreement.getId() == null) {
            accountAgreementDao.insert(accountAgreement);
        }else {
            accountAgreementDao.update(accountAgreement);
        }
    }

    /**
     * 保存
     * @param accountAgreementCard
     * @return
     */
    public void saveOrUpdateAccountAgreementCard(AccountAgreementCard accountAgreementCard){
        if (accountAgreementCard.getId() == null) {
            accountAgreementCardDao.insert(accountAgreementCard);
        }else {
            accountAgreementCardDao.update(accountAgreementCard);
        }
    }

    /**
     * 查找协议支付签约记录
     * @param name      姓名
     * @param idNum     证件号码
     * @return
     */
    public AccountAgreement getAccountByNameAndIdNum(String name, String idNum){
        AccountAgreement accountAgreement = null;
        try{
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("name", name);
            params.put("idNum", idNum);

            accountAgreement = accountAgreementDao.queryByNameAndIdNum(params);
        }catch (SQLException e){
            logger.error("查找协议支付签约账户记录异常", e);
        }

        return accountAgreement;
    }


    /**
     * 查找协议支付签约记录
     * @param accountId 账户主表ID
     * @param thirdType 第三方支付平台
     * @param cardNo    银行卡号
     * @return
     */
    public AccountAgreementCard getCardByAccountIdAndAccountNoAndThirdType(Long accountId, String cardNo, String thirdType){
        AccountAgreementCard accountAgreementCard = null;
        try{
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("accountId", accountId);
            params.put("thirdType", thirdType);
            params.put("accountNo", cardNo);

            accountAgreementCard = accountAgreementCardDao.queryByThirdTypeAndAccountNo(params);
        }catch (SQLException e){
            logger.error("查找协议支付签约账户银行卡记录异常", e);
        }

        return accountAgreementCard;
    }
}
