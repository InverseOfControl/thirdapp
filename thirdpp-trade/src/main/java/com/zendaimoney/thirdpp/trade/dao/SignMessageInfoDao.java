package com.zendaimoney.thirdpp.trade.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.trade.entity.SignMessageInfo;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLException;

@Repository
public class SignMessageInfoDao extends BaseDao<SignMessageInfo> {

    // PayInfo实体类数据库操作前缀.
    private static final String SQL_PREFIX = "com.zendaimoney.thirdpp.trade.entity.SignMessageInfo";

    // 第二个数据源注入
    @Resource(name = "sqlMapClient")
    public SqlMapClient sqlMapClient;

    @PostConstruct
    public void initSqlMapClient() {
        super.setSqlMapClient(sqlMapClient);
    }

    /**
     * 查询短信触发信息
     *
     * @param reqId 交易流水号
     * @return
     * @throws SQLException
     */
    public SignMessageInfo queryByReqId(String reqId) throws SQLException {
        return (SignMessageInfo)this.sqlMapClient.queryForObject(SQL_PREFIX + ".queryByReqId", reqId);
    }
}
