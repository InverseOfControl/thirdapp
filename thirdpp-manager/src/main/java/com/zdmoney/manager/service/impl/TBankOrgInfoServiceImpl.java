package com.zdmoney.manager.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TBankOrgInfoMapper;
import com.zdmoney.manager.models.TBankOrgInfo;
import com.zdmoney.manager.service.TBankOrgInfoService;

/**
 * 
 * @author wyj
 *
 */
@Service
public class TBankOrgInfoServiceImpl implements TBankOrgInfoService{
	
	@Autowired
	private TBankOrgInfoMapper tBankInfoMapper;
	/*@Autowired
	private SqlSessionFactory sessionFactory; 
	SqlSession session = sessionFactory.openSession(ExecutorType.BATCH, false); */
	// 第二个数据源注入
		/*@Resource(name = "sqlMapClient")
		public SqlMapClient sqlMapClient*/;

	private List<TBankOrgInfo> bankOrgCodeList;
		
	@Override
	public List<Map> getBankOrgInfoList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return tBankInfoMapper.getBankOrgInfoList(params);
	}

	@Override
	public int insert(TBankOrgInfo bankInfo) {
		// TODO Auto-generated method stub
		return  tBankInfoMapper.insert(bankInfo);
	}

	@Override
	public int updateBankOrgInfo(TBankOrgInfo bankInfo) {
		// TODO Auto-generated method stub
		return tBankInfoMapper.updateBankOrgInfo(bankInfo);
	}

	@Override
	public TBankOrgInfo selectBankOrgInfoByID(long id) {
		// TODO Auto-generated method stub
		return tBankInfoMapper.selectBankOrgInfoByID(id);
	}

	@Override
	public int getBankOrgInfoListCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return tBankInfoMapper.getBankOrgInfoListCount(params);
	}

	@Override
	public int getBankCodeCount(String bankCode) {
		// TODO Auto-generated method stub
		return tBankInfoMapper.getBankCodeCount(bankCode);
	}

	@Override
	public int batchDeleteInfo(List<Integer> list) {
		// TODO Auto-generated method stub
		return tBankInfoMapper.batchDeleteInfo(list);
	}

	@Override
	public String getBankName(String code) {
		// TODO Auto-generated method stub
		return tBankInfoMapper.getBankName(code);
	}

	@Override
	public int getBankLineNo(TBankOrgInfo orgInfo) {
		// TODO Auto-generated method stub
		return tBankInfoMapper.getBankLineNo(orgInfo);
	}

	@Override
	public int getBankOrgCount(TBankOrgInfo orgInfo) {
		// TODO Auto-generated method stub
		return tBankInfoMapper.getBankOrgCount(orgInfo);
	}
	
	
	@Override
	public int batchInsert(List<TBankOrgInfo> orgInfo) {
	  int count=0;
	//	tBankInfoMapper= session.getMapper(TBankOrgInfoMapper.class);		 
		if (orgInfo != null && !orgInfo.isEmpty()) {
			for (TBankOrgInfo bank: orgInfo) {
				 
				count+= this.insert(bank);
				 
			}
			 
		}
		return count; 
	}

	@Override
	public Map<String, String> getBankOrgInfoMap() {
		   bankOrgCodeList=	tBankInfoMapper.getBankOrgInfoMap();
			Map<String, String> map=new HashMap<String, String>();	 
			for(TBankOrgInfo boi :bankOrgCodeList){
				map.put("bankCode"+boi.getBankCode()+"bankOrgNo"+boi.getBankOrgNo(), "1");	
			}
			
		return map;
	}
	
	public Map<String, String> getBankLineNoMap() {
		Map<String, String> map=new HashMap<String, String>();	 
		  if(bankOrgCodeList==null){
			  bankOrgCodeList=	tBankInfoMapper.getBankOrgInfoMap();
		  } 
		  for(TBankOrgInfo boi :bankOrgCodeList){
				map.put( boi.getBankLineNo() ,  "1");	
			}
		return map;
	}
	@Override
	public String getBankOrgInID(String bankLineNo) {
		// TODO Auto-generated method stub
		return tBankInfoMapper.getBankOrgInID(bankLineNo);
	}
 

	/*@Override
	public void batchInsert(List<TBankOrgInfo> orgInfo) {
		int count = 0;
		  try {
	//	tBankInfoMapper= session.getMapper(TBankOrgInfoMapper.class);		 
		if (orgInfo != null && !orgInfo.isEmpty()) {
			for (TBankOrgInfo bank: orgInfo) {
				count++;
				this.insert(bank);
				if (count % 10000 == 0) {
					//手动每10000个一提交，提交后无法回滚  
					session.commit();                 
					//清理缓存，防止溢出                             
					session.clearCache();
				}
			}
			session.commit(); 
		}
		  } catch (Exception e) {
			  //回滚
			  session.rollback();
		  }	finally{
			  //关闭
			  session.close();
		  }
	}*/
 
	 

}
