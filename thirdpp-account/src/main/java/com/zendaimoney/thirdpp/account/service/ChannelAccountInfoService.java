package com.zendaimoney.thirdpp.account.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.account.dao.ChannelAccountInfoDao;
import com.zendaimoney.thirdpp.account.entity.AccountInfo;
import com.zendaimoney.thirdpp.account.entity.AccountInfoTemple;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountInfo;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountRequest;
import com.zendaimoney.thirdpp.account.exception.PlatformException;
import com.zendaimoney.thirdpp.account.util.Constants;
import com.zendaimoney.thirdpp.account.util.TxtUtil;

@Service
public class ChannelAccountInfoService {
	
	private static final Log logger = LogFactory.getLog(ChannelAccountInfoService.class);
	
	@Autowired
	private ChannelAccountInfoDao channelAccountInfoDao;
	
	@Autowired
	private BizsysAccountInfoService bizsysAccountInfoService;
	
	@Autowired
	private ChannelAccountRequestService channelAccountRequestService;
	
	@Autowired
	private CollectInfoService collectInfoService;
	@Autowired
	private PayInfoService payInfoService;
	
	public void updateAccountInfoByTaskId(List<Long> taskIdList, String bizReqId) throws SQLException {
		List<AccountInfo> total = new ArrayList<AccountInfo>();
		for (Long taskId : taskIdList) {
			List<AccountInfo> accountInfoList = channelAccountInfoDao.getAccountInfoByTaskId(String.valueOf(taskId));
			for (AccountInfo ac : accountInfoList) {
				ac.setBizsysAccountStatus(Constants.AccountInfo.BIZSYS_ACCOUNT_INFO_ACCOUNT_STATUS_DONE);
				ac.setBizsysAccountReqId(bizReqId);
				total.add(ac);
			}
		}
		channelAccountInfoDao.batchUpdate(total);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
	public void batchUpdateCollect(List<AccountInfo> accountInfoList, List<AccountInfoTemple> collectInfoList) {
		updateAccountInfo(accountInfoList);
		updateCollectInfo(collectInfoList);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
	public void batchUpdatePay(List<AccountInfo> accountInfoList, List<AccountInfoTemple> collectInfoList) {
		updateAccountInfo(accountInfoList);
		updatePayInfo(collectInfoList);
	}
	
	private void updateAccountInfo(List<AccountInfo> info) throws PlatformException {
		try {
			channelAccountInfoDao.batchUpdate(info);
		} catch (Exception e) {
			logger.error("通道对账批量更新对账明细表异常", e);
			String failedReason = StringUtils.substring(e.getMessage(), 0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH);
			throw new PlatformException(failedReason, e);
		}
	}
	
	private void updateCollectInfo(List<AccountInfoTemple> info) throws PlatformException {
		try {
			collectInfoService.update(info);
		} catch (Exception e) {
			logger.error("通道对账批量更新代扣业务明细表异常", e);
			String failedReason = StringUtils.substring(e.getMessage(), 0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH);
			throw new PlatformException(failedReason, e);
		}
	}
	
	private void updatePayInfo(List<AccountInfoTemple> info) throws PlatformException {
		try {
			payInfoService.update(info);
		} catch (Exception e) {
			logger.error("通道对账批量更新代付业务明细表异常", e);
			String failedReason = StringUtils.substring(e.getMessage(), 0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH);
			throw new PlatformException(failedReason, e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
	public void seriesUpdate(List<Long> taskIdList, List<BizsysAccountInfo> statisticList, List<String> data, BizsysAccountRequest request, String fileEncoding) throws SQLException, IOException {
		updateAccountInfoByTaskId(taskIdList, request.getReqId());
		bizsysAccountInfoService.batchInsert(statisticList);
		String temp = request.getLocalizePath().concat(Constants.STRING_SLASH).concat(request.getLocalizeFileName());
		File file = new File(temp);
		if (file == null || !file.exists()) {
			throw new SQLException("业务系统对账文件不存在");
		}
		TxtUtil.writerTxt(file, data, fileEncoding);
	}

	public AccountInfo getLastAccountInfoByBizsysRequest(String bizsysRequestId) throws SQLException {
		return channelAccountInfoDao.getLastAccountInfoByBizsysRequest(bizsysRequestId);
	}
	
	public void insert(AccountInfo accountInfo) {
		channelAccountInfoDao.insert(accountInfo);
	}
	
	public AccountInfo getLastByReqId(String reqId) throws SQLException {
		return channelAccountInfoDao.getLastByChannelRequest(reqId);
	}
	
	public List<String> getRelatedBizsysNoByReqId(String reqId) throws SQLException {
		return channelAccountInfoDao.getRelatedBizsysNoByReqId(reqId);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
	public void batchInsert(List<AccountInfo> accountInfos) throws SQLException {
		channelAccountInfoDao.batchInsert(accountInfos);
	}
	
	public AccountInfo getLastSuccessByReqId(String reqId) throws SQLException {
		return channelAccountInfoDao.getLastSuccessByReqId(reqId);
	}
	
	public List<AccountInfo> getNotAccountByReqIdAndBizType(String reqId, String bizType) throws SQLException {
		return channelAccountInfoDao.getNotAccountByReqIdAndBizType(reqId, bizType);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
	public void update(List<AccountInfo> accountInfoList) throws SQLException {
		channelAccountInfoDao.batchUpdate(accountInfoList);
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");
		list.add("8");
		list.add("9");
		list.add("10");
		
		int size = list.size();
		int batchSize = size / 3;
		for (int i = 0; i < batchSize; i++) {
			List<String> subBatchAccountInfo = list.subList(3 * i, 3 * (i + 1));
			for (int j = 0; j < subBatchAccountInfo.size(); j++) {
				System.out.println(subBatchAccountInfo.get(j));
			}
		}
		
		// List<String> subList = list.subList(0, 2); 去除的是 0 和 1 两个数据
		
	}
}
