package com.zendaimoney.thirdpp.channel.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.channel.entity.AccountAgreement;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class AccountAgreementDao extends BaseDao<AccountAgreement> {

    private static final String SQL_PREFIX = "com.zendaimoney.thirdpp.channel.entity.AccountAgreement";

    // 第二个数据源注入
    @Resource(name = "sqlMapClient")
    public SqlMapClient sqlMapClient;

    @SuppressWarnings("deprecation")
    @PostConstruct
    public void initSqlMapClient() {
        super.setSqlMapClient(sqlMapClient);
    }

    /**
     * 查找协议支付账户信息
     * @return
     * @throws SQLException
     */
    public AccountAgreement queryByNameAndIdNum(Map<String,Object> params) throws SQLException {
        return (AccountAgreement) this.sqlMapClient.queryForObject(SQL_PREFIX + ".queryByNameAndIdNum", params);
    }
}
