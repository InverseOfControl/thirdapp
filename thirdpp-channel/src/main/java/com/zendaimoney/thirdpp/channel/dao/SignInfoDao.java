package com.zendaimoney.thirdpp.channel.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.channel.entity.SignInfo;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLException;

@Repository
public class SignInfoDao extends BaseDao<SignInfo> {

    // PayInfo实体类数据库操作前缀.
    private static final String SQL_PREFIX_PAYINFO = "com.zendaimoney.thirdpp.channel.entity.SignInfo";

    // 第二个数据源注入
    @Resource(name = "sqlMapClient")
    public SqlMapClient sqlMapClient;

    @PostConstruct
    public void initSqlMapClient() {
        super.setSqlMapClient(sqlMapClient);
    }

    /**
     * @param reqId
     * @return
     * @throws SQLException
     */
    public SignInfo queryByReqId(String reqId) throws SQLException {
        return (SignInfo) this.sqlMapClient.queryForObject(SQL_PREFIX_PAYINFO + ".queryByReqId", reqId);
    }

}
