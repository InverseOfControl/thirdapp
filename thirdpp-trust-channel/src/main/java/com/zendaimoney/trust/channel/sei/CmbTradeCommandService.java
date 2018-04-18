package com.zendaimoney.trust.channel.sei;

import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;

/**
 * 对外暴露： 招商银行资金托管单笔交易 接口
 * @author mencius
 *
 */
public interface CmbTradeCommandService {
	
	/**
	 * 3.1.3文件处理指令(FILE)
	 * @param bizReqVo
	 * @return Response
	 */
	public Response fileCommand(TrustBizReqVo bizReqVo);

	/**
	 * 3.2.1用户开户(USRA)
	 * @param bizReqVo
	 * @return Response
	 * @throws Exception 
	 */
	public Response usraCommand(TrustBizReqVo bizReqVo) throws Exception;
	
	/**
	 * 3.2.2用户修改信息(USRE)
	 * @param bizReqVo
	 * @return
	 */
	public Response usreCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.2.3用户绑定银行卡(USRB)
	 * @param bizReqVo
	 * @return
	 */
	public Response usrbCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.2.4银行卡绑定结果查询(UBQY)
	 * @param bizReqVo
	 * @return
	 */
	public Response ubqyCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.2.5用户解绑银行卡(USRJ)
	 * @param bizReqVo
	 * @return
	 */
	public Response usrjCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.2.6用户关户(USRD)
	 * @param bizReqVo
	 * @return
	 */
	public Response usrdCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.3.1三方充值请求(CHRG)
	 * @param bizReqVo
	 * @return
	 * @throws Exception 
	 */
	public Response chrgCommand(TrustBizReqVo bizReqVo) throws Exception;
	
	/**
	 * 3.3.2查询自助转账到账记录(SCQY)
	 * @param bizReqVo
	 * @return
	 */
	public Response scqyCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.3.3自助转账充值请求(SCHG)
	 * @param bizReqVo
	 * @return
	 * @throws Exception 
	 */
	public Response schgCommand(TrustBizReqVo bizReqVo) throws Exception;
	
	/**
	 * 3.3.5提现请求(WTDR)
	 * @param bizReqVo
	 * @return
	 * @throws Exception 
	 */
	public Response wtdrCommand(TrustBizReqVo bizReqVo) throws Exception;
	
	/**
	 * 3.3.6提现结果通知平台(WDRS)
	 * @param bizReqVo
	 * @return
	 */
	public Response wdrsCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.3.7提现结果查询(WDQY)
	 * @param bizReqVo
	 * @return
	 */
	public Response wdqyCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.3.8平台充值(MCHG)
	 * @param bizReqVo
	 * @return
	 */
	public Response mchgCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.3.9平台提现(MDRW)
	 * @param bizReqVo
	 * @return
	 */
	public Response mdrwCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.3.10存管账户计息(MINT)
	 * @param bizReqVo
	 * @return
	 */
	public Response mintCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.4.1登记散标(PROA)
	 * @param bizReqVo
	 * @return
	 */
	public Response proaCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.4.3登记团信息(GROA)
	 * @param bizReqVo
	 * @return
	 */
	public Response groaCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.4.4关闭散标(PROC)
	 * @param bizReqVo
	 * @return
	 */
	public Response procCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.4.5关闭团(GROC)
	 * @param bizReqVo
	 * @return
	 */
	public Response grocCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.5.1冻结(FROZ)
	 * @param bizReqVo
	 * @return
	 */
	public Response frozCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 解冻(UNFR)
	 * @param bizReqVo
	 * @return
	 */
	public Response unfrCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.5.5投资(INVS)
	 * @param bizReqVo
	 * @return
	 */
	public Response invsCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.5.6还款(REPY)
	 * @param bizReqVo
	 * @return
	 */
	public Response repyCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.5.7债权转让(CASM)
	 * @param bizReqVo
	 * @return
	 */
	public Response casmCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.5.8平台收费(MFEE)
	 * @param bizReqVo
	 * @return
	 */
	public Response mfeeCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.5.9平台付款(MPAY)
	 * @param bizReqVo
	 * @return
	 */
	public Response mpayCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.5.10平台垫资(MADV)
	 * @param bizReqVo
	 * @return
	 */
	public Response madvCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.5.11垫资收回(MREG)
	 * @param bizReqVo
	 * @return
	 */
	public Response mregCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.5.12客户入团(GINN)
	 * @param bizReqVo
	 * @return
	 */
	public Response ginnCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.5.13客户出团(GOUT)
	 * @param bizReqVo
	 * @return
	 */
	public Response goutCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.5.14团收取风险金(GCHG)
	 * @param bizReqVo
	 * @return
	 */
	public Response gchgCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.5.15团使用风险金(GDRW)
	 * @param bizReqVo
	 * @return
	 */
	public Response gdrwCommand(TrustBizReqVo bizReqVo);
	/**
	 * 3.5.16受托支付(ETPM)
	 * @param bizReqVo
	 * @return
	 */
	public Response etpmCommand(TrustBizReqVo bizReqVo);
	/**
	 * 3.5.17合作机构垫资(OADV)
	 * @param bizReqVo
	 * @return
	 */
	public Response oadvCommand(TrustBizReqVo bizReqVo);
	/**
	 * 3.5.18合作机构垫资收回(OREG)
	 * @param bizReqVo
	 * @return
	 */
	public Response oregCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.6.2充值资金平台清算(MRSL)
	 * @param bizReqVo
	 * @return
	 */
	public Response mrslCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.7.1每日交易汇总对账(SCCK)
	 * @param bizReqVo
	 * @return
	 */
	public Response scckCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.7.2每日交易明细对账(DCCK)
	 * @param bizReqVo
	 * @return
	 */
	public Response dcckCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.7.3每日余额对账(BCCK)
	 * @param bizReqVo
	 * @return
	 */
	public Response bcckCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.8.1余额查询（BAQY）
	 * @param bizReqVo
	 * @return
	 */
	public Response baqyCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.8.2虚拟户交易查询(TRQY)
	 * @param bizReqVo
	 * @return
	 */
	public Response trqyCommand(TrustBizReqVo bizReqVo);
	
	/**
	 * 3.8.3存管专户入账交易查询(RCQY)
	 * @param bizReqVo
	 * @return
	 */
	public Response rcqyCommand(TrustBizReqVo bizReqVo);
  
}
