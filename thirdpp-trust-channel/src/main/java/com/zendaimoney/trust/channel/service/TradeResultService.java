package com.zendaimoney.trust.channel.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.trust.channel.dao.AcctCommandDao;
import com.zendaimoney.trust.channel.dao.BatchOperDao;
import com.zendaimoney.trust.channel.dao.CashDrawDao;
import com.zendaimoney.trust.channel.dao.CashTransferDao;
import com.zendaimoney.trust.channel.dao.GeneralOperDao;
import com.zendaimoney.trust.channel.dao.OpenBoundDao;
import com.zendaimoney.trust.channel.dao.RechargeDao;
import com.zendaimoney.trust.channel.dao.TradeResultDao;
import com.zendaimoney.trust.channel.entity.AcctCommand;
import com.zendaimoney.trust.channel.entity.BatchOper;
import com.zendaimoney.trust.channel.entity.CashDraw;
import com.zendaimoney.trust.channel.entity.CashTransfer;
import com.zendaimoney.trust.channel.entity.GeneralOper;
import com.zendaimoney.trust.channel.entity.OpenBound;
import com.zendaimoney.trust.channel.entity.Recharge;
import com.zendaimoney.trust.channel.entity.TradeResult;

@Service
@Transactional
public class TradeResultService {

	@Resource(name = "tradeResultDao")
	private TradeResultDao tradeResultDao;
	
//	@Autowired
//	private MqProducter mqProducter;
	
	/**
	 * 开户绑卡Dao
	 */
	@Resource(name = "openBoundDao")
	private OpenBoundDao openBoundDao;

	/**
	 * 普通操作Dao
	 */
	@Resource(name = "generalOperDao")
	private GeneralOperDao generalOperDao;
	
	/**
	 * 批量文件操作Dao
	 */
	@Resource(name = "batchOperDao")
	private BatchOperDao batchOperDao;
	
	/**
	 * 三方充值交易
	 */
	@Resource(name = "rechargeDao")
	private RechargeDao rechargeDao;
	
	/**
	 * 提现交易
	 */
	@Resource(name = "cashDrawDao")
	private CashDrawDao cashDrawDao;
	
	/**
	 * 账户资金
	 */
	@Resource(name = "acctCommandDao")
	private AcctCommandDao acctCommandDao;
	
	/**
	 * 转账
	 */
	@Resource(name = "cashTransferDao")
	private CashTransferDao cashTransferDao;
	
	/**
	 * 记录请求操作
	 * 
	 * @param request
	 */
	public void insert(TradeResult tradeResult) {
		tradeResultDao.insert(tradeResult);
	}

	public void finalResultHandler(TradeResult tradeResult) {
		// 记录返回结果
		if (tradeResult != null) {
			tradeResultDao.insert(tradeResult);
		}
		
	}
	
	/**
	 * 开户绑卡更新交易
	 * @param tradeResult
	 * @param openBound
	 */
	public void finalResultHandler(TradeResult tradeResult, OpenBound openBound) {
		// 记录返回结果
		if (tradeResult != null) {
			tradeResultDao.insert(tradeResult);
		}
		
		// 开户绑卡更新状态
		if (openBound != null) {
			openBoundDao.update(openBound);
		}
	}
	
	/**
	 * 普通操作更新交易
	 * @param tradeResult
	 * @param openBound
	 */
	public void finalResultHandler(TradeResult tradeResult, GeneralOper generalOper) {
		// 记录返回结果
		if (tradeResult != null) {
			tradeResultDao.insert(tradeResult);
		}
		
		// 普通操作更新状态
		if (generalOper != null) {
			generalOperDao.update(generalOper);
		}
	}
	
	/**
	 * 批量文件操作更新交易
	 * @param tradeResult
	 * @param openBound
	 */
	public void finalResultHandler(TradeResult tradeResult, BatchOper batchOper) {
		// 记录返回结果
		if (tradeResult != null) {
			tradeResultDao.insert(tradeResult);
		}
		
		// 批量文件操作更新状态
		if (batchOper != null) {
			batchOperDao.update(batchOper);
		}
	}
	
	/**
	 * 三方充值操作更新交易
	 * @param tradeResult
	 * @param openBound
	 */
	public void finalResultHandler(TradeResult tradeResult, Recharge recharge) {
		// 记录返回结果
		if (tradeResult != null) {
			tradeResultDao.insert(tradeResult);
		}
		
		// 三方充值交易更新状态
		if (recharge != null) {
			rechargeDao.update(recharge);
		}
	}
	
	/**
	 * 提现操作更新交易
	 * @param tradeResult
	 * @param openBound
	 */
	public void finalResultHandler(TradeResult tradeResult, CashDraw cashDraw) {
		// 记录返回结果
		if (tradeResult != null) {
			tradeResultDao.insert(tradeResult);
		}
		
		// 提现交易更新状态
		if (cashDraw != null) {
			cashDrawDao.update(cashDraw);
		}
	}

	
	/**
	 * 账户资金操作更新
	 * @param tradeResult
	 * @param acctCommand
	 */
	public void finalResultHandler(TradeResult tradeResult, AcctCommand acctCommand) {
		// 记录返回结果
		if (tradeResult != null) {
			tradeResultDao.insert(tradeResult);
		}
		
		// 更新账户资金结果
		if (acctCommand != null) {
			acctCommandDao.update(acctCommand);
		}
	}
	
	public void finalResultHandler(TradeResult tradeResult, CashTransfer cashTransfer) {
		// 记录返回结果
		if (tradeResult != null) {
			tradeResultDao.insert(tradeResult);
		}
		
		// 更新账户资金结果
		if (cashTransfer != null) {
			cashTransferDao.update(cashTransfer);
		}
	}
}
