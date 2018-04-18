package com.zendaimoney.thirdpp.trade.service;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.trade.dao.BankOrgInfoDao;
import com.zendaimoney.thirdpp.trade.entity.BankOrgInfo;

@Transactional
@Service
public class BankOrgInfoService {

	@Resource(name = "bankOrgInfoDao")
	private BankOrgInfoDao bankOrgInfoDao;

	public List<BankOrgInfo> getBankOrgInfoByBankCodeAndBankArea(
			String bankCode, String bankOrgProvinceNo,
			String bankOrgProvinceCityNo) throws SQLException {
		
		return bankOrgInfoDao.getBankOrgInfoByBankCodeAndBankArea(bankCode,
				bankOrgProvinceNo, bankOrgProvinceCityNo);
		
	}
}
