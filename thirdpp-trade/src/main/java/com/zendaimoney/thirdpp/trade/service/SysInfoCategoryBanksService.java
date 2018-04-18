package com.zendaimoney.thirdpp.trade.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.trade.dao.SysInfoCategoryBanksDao;
import com.zendaimoney.thirdpp.trade.entity.SysInfoCategoryBanks;

@Transactional
@Service
public class SysInfoCategoryBanksService {

	@Resource(name = "sysInfoCategoryBanksDao")
	private SysInfoCategoryBanksDao sysInfoCategoryBanksDao;


	/**
	 * 查询该信息类别的银行通道配置
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> queryInfoCategoryBanks(String infoCategoryCode) throws SQLException{
		Map<String,String> map = new HashMap<String,String>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("infoCategoryCode", infoCategoryCode);
		List<SysInfoCategoryBanks> list = sysInfoCategoryBanksDao.querySysInfoCategoryBanks(paramMap);
		if(list != null && !list.isEmpty()){
			for(SysInfoCategoryBanks s:list){
				map.put(s.getBankCode(), s.getPaySysNo());
			}
		}
		return map;
	}

}
