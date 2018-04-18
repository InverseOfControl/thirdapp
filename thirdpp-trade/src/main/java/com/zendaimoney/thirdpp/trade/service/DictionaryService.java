package com.zendaimoney.thirdpp.trade.service;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zendaimoney.thirdpp.trade.dao.DictionaryDao;
import com.zendaimoney.thirdpp.trade.entity.Dictionary;

@Service
public class DictionaryService {

	@Resource(name = "dictionaryDao")
	private DictionaryDao dictionaryDao;
	
	public List<Dictionary> getSupportedPayPlatforms(String dicType) throws SQLException{
		return dictionaryDao.getSupportedPayPlatforms(dicType);
	}
	
	public List<Dictionary> getDictionaryByDicCode(String dicCode) throws SQLException {
		return dictionaryDao.getDictionaryByDicCode(dicCode);
	}
	
}
