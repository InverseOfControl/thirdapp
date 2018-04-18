package com.zendaimoney.thirdpp.channel.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.channel.entity.AccountAgreementCard;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class AccountAgreementCardDao extends BaseDao<AccountAgreementCard> {
    private static final String SQL_PREFIX = "com.zendaimoney.thirdpp.channel.entity.AccountAgreementCard";

    // 第二个数据源注入
    @Resource(name = "sqlMapClient")
    public SqlMapClient sqlMapClient;

    @SuppressWarnings("deprecation")
    @PostConstruct
    public void initSqlMapClient() {
        super.setSqlMapClient(sqlMapClient);
    }

    /**
     * @return
     * @throws SQLException
     */
    public AccountAgreementCard queryByThirdTypeAndAccountNo(Map<String,Object> params) throws SQLException {
        return (AccountAgreementCard) this.sqlMapClient.queryForObject(SQL_PREFIX + ".queryByThirdTypeAndAccountNo", params);
    }
}
