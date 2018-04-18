package com.zendaimoney.thirdpp.trade.service;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.trade.dao.AreaInfoDao;
import com.zendaimoney.thirdpp.trade.entity.AreaInfo;

@Transactional
@Service
public class AreaInfoService {

	@Resource(name = "areaInfoDao")
	private AreaInfoDao areaInfoDao;
	
	/**
	 * 根据指定省份的area code 获得该省份下属的市（县）信息列表
	 * @param provinceAreaCode 要是该值为 null 则返回 省份（直辖市）列表信息
	 * @return 区域列表信息
	 * @throws SQLException
	 */
	public List<AreaInfo> getAreaInfos(String provinceAreaCode) throws SQLException {
		return areaInfoDao.getAreaInfos(provinceAreaCode);
	} 
	
	/**
	 * 根据 市（县）的区域编码 以及 省份的区域编码 获得唯一记录
	 * @param areaCode 市（县）的区域编码
	 * @param parentAreaCode  省份的区域编码
	 * @return
	 * @throws SQLException
	 */
	public AreaInfo getAreaInfoByAreaCodeAndParentCode(String areaCode, String parentAreaCode) throws SQLException {
		return areaInfoDao.getAreaInfoByAreaCodeAndParentCode(areaCode, parentAreaCode);
	}
	
	/**
	 * 根据 区域编码 获得唯一记录
	 * @param areaCode 区域编码
	 * @return
	 * @throws SQLException
	 */
	public AreaInfo getAreaInfoByAreaCode(String areaCode) throws SQLException {
		return areaInfoDao.getAreaInfoByAreaCode(areaCode);
	}
	
}
