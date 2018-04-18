package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TBankInfoMapper;
import com.zdmoney.manager.mapper.TCategoryAppsMapper;
import com.zdmoney.manager.mapper.TSysPermissionMapper;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TCategoryApps;
import com.zdmoney.manager.models.TCategoryBanks;
import com.zdmoney.manager.models.TDictionary;
import com.zdmoney.manager.service.TBankInfoService;
import com.zdmoney.manager.service.TCategoryAppService;

/**
 * 
 * @author wyj
 *
 */
@Service
public class TCategoryAppServiceImpl implements TCategoryAppService{
	
	@Autowired
	private TCategoryAppsMapper infoCateMapper;

	@Override
	public List<Map> getInfoCatefoList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return infoCateMapper.getInfoCatefoList(params);
	}

	@Override
	public int insertInfoCategory(TCategoryApps info) {
		// TODO Auto-generated method stub
		return infoCateMapper.insertInfoCategory(info);
	}

	@Override
	public int insertInfoCategoryApps(TCategoryApps  ta) {
		// TODO Auto-generated method stub
		return infoCateMapper.insertInfoCategoryApps(ta);
	}

	@Override
	public void batchDeleteInfo(List<Integer> list) {
		infoCateMapper.batchDeleteInfo(list);
		
	}

	@Override
	public void batchDeleteInfoApps(String code) {
		infoCateMapper.batchDeleteInfoApps(code);
		
	}

	@Override
	public List<TCategoryApps> infoAppsIDList(List<Integer> list) {
		// TODO Auto-generated method stub
		return infoCateMapper.infoAppsIDList(list);
	}

	@Override
	public int getInfoCatefoListCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return infoCateMapper.getInfoCatefoListCount(params);
	}

	@Override
	public List<Map> getMerchantTypeList() {
		// TODO Auto-generated method stub
		return infoCateMapper.getMerchantTypeList();
	}

	@Override
	public TCategoryApps selectCategoryByID(long id) {
		// TODO Auto-generated method stub
		return infoCateMapper.selectCategoryByID(id);
	}

	@Override
	public List<TCategoryApps> selectCategoryCodeStr(String code) {
		// TODO Auto-generated method stub
		return infoCateMapper.selectCategoryCodeStr(code);
	}

	@Override
	public void updateCategory(TCategoryApps info) {
		// TODO Auto-generated method stub
		infoCateMapper.updateCategory(info);
	}

	@Override
	public List<TCategoryApps> selectAppNameStr(String code) {
		// TODO Auto-generated method stub
		return infoCateMapper.selectAppNameStr(code);
	}

	@Override
	public int getCategoryCodeCount(String code){	 
		// TODO Auto-generated method stub
		return infoCateMapper.getCategoryCodeCount(code);
	}

	@Override
	public void batchDeleteCode(List<String> list) {
		 infoCateMapper.batchDeleteCode(list);
		
	}

	@Override
	public List<TCategoryBanks> selectCategoryBanksByCategoryCode(
			String categoryCode) {
		return infoCateMapper.selectCategoryBanksByCategoryCode(categoryCode);
	}

	@Override
	public void commonConfigure(TCategoryBanks tCategoryBanks) {
		infoCateMapper.commonConfigure(tCategoryBanks);
	}

}
