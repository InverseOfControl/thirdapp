package com.zendaimoney.thirdpp.trade.service;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.trade.dao.BankInfoDao;
import com.zendaimoney.thirdpp.trade.entity.BankInfo;

@Transactional
@Service
public class BankInfoService {

	@Resource(name = "bankInfoDao")
	private BankInfoDao bankInfoDao;
	
	public List<BankInfo> getSupportedBanks() throws SQLException{
		return bankInfoDao.queryBankInfos();
	}
}
