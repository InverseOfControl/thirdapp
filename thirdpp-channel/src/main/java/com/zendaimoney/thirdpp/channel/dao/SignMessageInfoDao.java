package com.zendaimoney.thirdpp.channel.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.channel.entity.SignMessageInfo;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * 签约短信触发
 *
 * @author wulj
 */
@Repository
public class SignMessageInfoDao extends BaseDao<SignMessageInfo> {

    // PayInfo实体类数据库操作前缀.
    private static final String SQL_PREFIX = "com.zendaimoney.thirdpp.channel.entity.SignMessageInfo";

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
        return (SignMessageInfo) this.sqlMapClient.queryForObject(SQL_PREFIX + ".queryByReqId", reqId);
    }

    /**
     * 查询短信触发信息
     *
     * @param tradeFlow 通道流水号
     * @return
     * @throws SQLException
     */
    public SignMessageInfo queryByTradeFlow(String tradeFlow) throws SQLException {
        return (SignMessageInfo) this.sqlMapClient.queryForObject(SQL_PREFIX + ".queryByTradeFlow", tradeFlow);
    }
}
