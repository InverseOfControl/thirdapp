package com.zendaimoney.trust.channel.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.trust.channel.dao.CashTransferDao;
import com.zendaimoney.trust.channel.entity.CashTransfer;
import com.zendaimoney.trust.channel.mongo.UnKnowTradeService;

/**
 * 虚拟交易 转账
 * @author 00233197
 *
 */
@Service
@Transactional
public class CashTransferService {

	@Autowired
	private CashTransferDao cashTransferDao;

	@Autowired
	private UnKnowTradeService unKnowTradeService;
	
	/**
	 * 入库操作
	 * 
	 * @param collectInfo
	 */
	public void insert(CashTransfer transfer) {
		cashTransferDao.insert(transfer);
	}
	


}
