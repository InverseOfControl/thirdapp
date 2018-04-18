package com.zdmoney.manager.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.groups.Default;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.zdmoney.manager.Validate.Validate;
import com.zdmoney.manager.models.ImportExcelErr;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TBankOrgInfo;
import com.zdmoney.manager.service.ImportExcelService;
import com.zdmoney.manager.service.TBankInfoService;
import com.zdmoney.manager.service.TBankOrgInfoService;
import com.zdmoney.manager.utils.ConverNullString;
import com.zdmoney.manager.utils.ExportExcel;
import com.zdmoney.manager.utils.JsonDateFormatUtil;
import com.zdmoney.manager.utils.Page;
import com.zdmoney.manager.utils.PermissionUtil;
import com.zdmoney.manager.utils.StringUtil;
 
 


/** 
 *
 * @author wyj
 * @version 2015年7月3日 下午1:59:18 
 */
@Controller
@RequestMapping(value = "/bim/bankOrgInfo")
public class TBankOrgInfoController {
	private final Logger log = Logger.getLogger(TSymPermissionController.class);
	@Autowired
	private TBankOrgInfoService tBankInfoservice; 
	@Autowired
	private TBankInfoService bankInfoservice; 
	@Autowired
	private	ImportExcelService im; 
	@Autowired
	private Page<TBankOrgInfo> page;
	@RequestMapping(value = "/bankOrgInfoListData/{bankCode}")
	@ResponseBody
	public String listData(@PathVariable("bankCode") String bankCode,HttpServletRequest request, ModelMap modelMap){		
		try {
			// 1、设置查询参数
			Map<String,Object> params = new HashMap<String,Object>();
			String beginTime =request.getParameter("beginTime");
			String endTime =request.getParameter("endTime");
			this.datagridParam(request, params);
			if(!StringUtil.isEmpty(beginTime)){
				beginTime=beginTime.replaceAll("-", "");
				params.put("beginTime", beginTime);
			}
			if(!StringUtil.isEmpty(endTime)){
				endTime=endTime.replaceAll("-", "");
				params.put("endTime", endTime);
			}
			params.put("bankCode", bankCode);
			params.put("bankOrgName", request.getParameter("bankOrgName"));
			 
			params.put("bankOrgProvinceNo", request.getParameter("bankOrgProvinceNo"));
			params.put("bankOrgProvinceCityNo", request.getParameter("bankOrgProvinceCityNo"));

			// 2.遍历增加及转换自定义内容
			List<Map> rows =tBankInfoservice.getBankOrgInfoList(params);
	 
			int count = tBankInfoservice.getBankOrgInfoListCount(params);
			for (Map d : rows) {
				// 2.自定义按钮设置在此处

				/*/d.put("PAYER_BANK_CARD_TYPE", d.get("PAYER_BANK_CARD_TYPE") == null ? "" : BankCardTypeEnum.getEnumDesc(d.get("PAYER_BANK_CARD_TYPE").toString()));
				d.put("PAYER_ID_TYPE", d.get("PAYER_ID_TYPE") == null ? "" : IdTypeEnum.getEnumDesc(d.get("PAYER_ID_TYPE").toString()));
				d.put("CURRENCY", d.get("CURRENCY") == null ? "" : CurrencyEnum.getEnumDesc(d.get("CURRENCY").toString()));
				d.put("PRIORITY", d.get("PRIORITY") == null ? "" : PriorityEnum.getEnumDesc(d.get("PRIORITY").toString()));
				d.put("STATUS", d.get("STATUS") == null ? "" : SendStatusEnum.getEnumDesc(d.get("STATUS").toString()));
				d.put("IS_SEPARATE", d.get("IS_SEPARATE") == null ? "" : IsSeparateEnum.getEnumDesc(d.get("IS_SEPARATE").toString()));
				d.put("BIZ_TYPE", d.get("BIZ_TYPE") == null ? "" : BizTypeEnum.getEnumDesc(d.get("BIZ_TYPE").toString()));
				d.put("IS_NEED_PUSH", d.get("IS_NEED_PUSH") == null ? "" : NeedPushEnum.getEnumDesc(d.get("IS_NEED_PUSH").toString()));
				d.put("TRADE_STATUS", d.get("TRADE_STATUS") == null ? "" : TradeStatusEnum.getEnumDesc(d.get("TRADE_STATUS").toString()));
				d.put("IS_NEED_SPILT", d.get("IS_NEED_SPILT") == null ? "" : IsNeedSpiltEnum.getEnumDesc(d.get("IS_NEED_SPILT").toString()));
				d.put("AMOUNT" , d.get( "AMOUNT" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get( "AMOUNT" )) );
				d.put("FEE" , d.get( "FEE" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get( "FEE" )) );
				d.put("TRADE_SUCCESS_AMOUNT" , d.get( "TRADE_SUCCESS_AMOUNT" )==null ? "0.00" : BigDecimalFormatUtil.switchMoneyFormat((BigDecimal)d.get( "TRADE_SUCCESS_AMOUNT" )) );*/

				d.put("update","<a href='javascript:return void(0);' onClick=\"updateBankOrgInfo('"+ d.get("ID")+ "');\"><i class='icon-edit'></i></a>");
			}

			// 3.组合输出列表查询所需数据
			// JsonConfig 用于解析转换的设置
			JsonConfig config = new JsonConfig();
			JsonDateFormatUtil.formatDateForJsonConfig_type2(config);

			JSONArray json_rows = JSONArray.fromObject(rows, config);
			String json = "{\"total\":\""
					+ count
					+ "\",\"rows\":" + json_rows.toString() + "}";
			return json;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private void datagridParam(HttpServletRequest request, Map<String,Object> params) throws Exception {
		int pageNumber = StringUtils.isBlank(request.getParameter("page")) ? 1: Integer.parseInt(request.getParameter("page"));
		int pageSize = StringUtils.isBlank(request.getParameter("rows")) ? 10: Integer.parseInt(request.getParameter("rows"));
		int rowBegin = (pageNumber-1) * pageSize + 1;
		int rowEnd = (pageNumber-1) * pageSize + pageSize;
		String sortName = request.getParameter("sort");
		String sortOrder = StringUtils.isBlank(request.getParameter("order")) ? "asc" : request.getParameter("order");
		if (StringUtils.isNotBlank(sortName) && StringUtils.isNotBlank(sortOrder)) {
			params.put("orderStr", " order by " + sortName + " " + sortOrder + " ");
		}
		params.put("rowBegin", rowBegin);
		params.put("rowEnd", rowEnd);
	}
	@RequestMapping(value = "/tobankOrgInfoList/{bankCode}")
	public String toListData(@PathVariable("bankCode") String bankCode,HttpServletRequest request, ModelMap modelMap){		 
		modelMap.put("bankCode", bankCode);
		
		return "/bim/bankOrgInfo/bankOrgInfoList";
	}
	@RequestMapping(value = "/bankOrgInfoList")
	public String list(HttpServletRequest request, ModelMap modelMap){
		try {
			HttpSession session = request.getSession();
			System.out.println( session.getAttribute("permMap"));
			if(!PermissionUtil.isHavePermission(session, "/bim/bankOrgInfo/bankOrgInfoList")){
				return "/errorpage/permissionError";
			}
			/*Map<String,Object> params = new HashMap<String,Object>();
			//分页
			int pageNo = StringUtil.parseInteger(request.getParameter("pageNo"), 1);
			int pageSize = StringUtil.parseInteger(request.getParameter("pageSize"), 15);
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			//查询条件
			//封装查询条件
			String bankOrgName = request.getParameter("bankOrgName");   
			String bankOrgNo = request.getParameter("bankOrgNo");
			String bankOrgProvinceNo = request.getParameter("bankOrgProvinceNo");
			String bankCode = request.getParameter("bankCode"); 
			String beginTime = request.getParameter("beginTime");
			String endTime = request.getParameter("endTime");
			if(!StringUtil.isEmpty(bankOrgName)){
				params.put("bankOrgName", bankOrgName);
				modelMap.put("bankOrgName", bankOrgName);
			}
			if(!StringUtil.isEmpty(bankCode)){
				params.put("bankCode", bankCode);
				modelMap.put("bankCode", bankCode);
			}
			if(!StringUtil.isEmpty(bankOrgProvinceNo)){
				params.put("bankOrgProvinceNo", bankOrgProvinceNo);
				modelMap.put("bankOrgProvinceNo", bankOrgProvinceNo);
			}
			if(!StringUtil.isEmpty(bankOrgNo)){
				params.put("bankOrgNo", bankOrgNo);
				modelMap.put("bankOrgNo", bankOrgNo);
			}
			if(!StringUtil.isEmpty(bankCode)){
				params.put("bankCode", bankCode);
				modelMap.put("bankCode", bankCode);
			}
			if(!StringUtil.isEmpty(beginTime)){
				modelMap.put("beginTime", beginTime);
				beginTime=beginTime.replaceAll("-", "");
				params.put("beginTime", beginTime);

			}
			if(!StringUtil.isEmpty(endTime)){
				modelMap.put("endTime", endTime);
				endTime=endTime.replaceAll("-", "");
				params.put("endTime", endTime);

			}
			params.put("page", page);

			//查询银行信息列表
			List<TBankOrgInfo> tBankList=tBankInfoservice.getBankOrgInfoList(params);
			page.setResults(tBankList);		
			modelMap.put("tBankOrgList", tBankList);
			modelMap.put("page", page);*/
			/*	return "/bim/bankOrgInfo/bankOrgInfoList"; */
			List<TBankInfo> bankInfoList=bankInfoservice.selectAllBnkInfo();
			modelMap.put("bankInfoList", bankInfoList);
			return "/bim/bankOrgInfo/toBankInfoOrgMeanu";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
	@RequestMapping(value = "/ExcelTemplate")
	public String templateUI(HttpServletRequest request, ModelMap modelMap){	
		return "bim/bankOrgInfo/excelTemplate";
	}
	/**
	 * 银行信息编辑页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/bankOrgInfoEditUI/{id}/{bankCode}")
	public String editUI(@PathVariable("id") String id,@PathVariable("bankCode") String bankCode,HttpServletRequest request, ModelMap modelMap) {
		TBankOrgInfo tb = null;
		if(!StringUtil.isEmpty(id)){
			//权限验证
			HttpSession session = request.getSession();
			System.out.println( session.getAttribute("permMap"));
			 

			tb = tBankInfoservice.selectBankOrgInfoByID(StringUtil.parseLong(id));
			tb.setNoteOrgNo(tb.getBankOrgNo());
			tb.setNoteOrgNo2(tb.getBankLineNo());
		}else{
			//权限验证
			HttpSession session = request.getSession();
			System.out.println(session.getAttribute("permMap"));
			 

			tb = new TBankOrgInfo();
		}
		if(null != tb){
			 
			tb.setBankCode(bankCode);
			tb.setBankName(tBankInfoservice.getBankName(bankCode));
			modelMap.put("bankOrgInfo", tb);
		}
		return "bim/bankOrgInfo/bankOrgInfoEdit";
	}
	@RequestMapping(value = "/ExcelImport")
	@ResponseBody
	public String excelImport(@RequestParam(value = "filename", required = false) MultipartFile file,HttpServletRequest request, ModelMap modelMap) {
		//权限验证
		JSONObject jb=new JSONObject();		 
		try {
		  String name=file.getOriginalFilename();
		  String path=  request.getSession().getServletContext().getRealPath("/upload");
		  boolean flag=im.validateExcel(name);
		  if(!flag){
			  jb.put("valmsg", "err");
			  return  jb.toString();
		  } 
		  Map<String, Object> maplist=im.getExcelInfo(name, file) ;
		  List<TBankOrgInfo> bankList=  (List<TBankOrgInfo>) maplist.get("blist");
		  List<ImportExcelErr> errlist=  (List<ImportExcelErr>) maplist.get("errlist");
		 
		  List<TBankOrgInfo> abl=new ArrayList<TBankOrgInfo>();
		  int insertCount=0;
		  for(int i=0;i<bankList.size();i++){
			  TBankOrgInfo  ti=	 bankList.get(i);
			  abl.add(ti);
			  if(i % 10000==0){
				  insertCount+=	tBankInfoservice.batchInsert(abl);
				  abl.clear();
			  }
		  }
		  insertCount+= tBankInfoservice.batchInsert(abl);
		    String msg="--------------------导入详情(提示前100条错误信息)---------------------<br>";
		    
		   if(errlist.size()>0){
			  int i=0;
		    for(ImportExcelErr err:errlist){
		    	i++;
		    	if(i>100)break;
		    	msg+= "第"+ err.getRowNum()+"行"+ err.getNote()+"<br>";
		    
		    }
		} 
		   //成功条数 ：insertCount
		   //失败条数 ：
		   	int errMum=  bankList.size()-insertCount;
			jb.put("valmsg","导入成功"+(insertCount)+"条！导入失败"+(errlist.size()+errMum)+"条！<br>"+msg);
			jb.put("msgFalg", "true");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			jb.put("valmsg","导入失败！");
		} 
		 return  jb.toString(); 
	}
	@RequestMapping(value = "/ExcelImportUI")
	public String editUI(HttpServletRequest request, ModelMap modelMap) {
		//权限验证
		HttpSession session = request.getSession();
		System.out.println( session.getAttribute("permMap"));		 
		return "bim/bankOrgInfo/bankOrgInfoExcelImport";
	}
	@RequestMapping(value = "/excelMsg" )
	public String excelMsgUI(HttpServletRequest request, ModelMap modelMap) {
		//权限验证
		String msg = request.getParameter("msg");
		modelMap.put("msg",msg);
		return "bim/bankOrgInfo/bankOrgInfoMsg";
		
	}
	@RequestMapping(value = "/bankInfoOrgDelete/{ids}")
	@ResponseBody
	public String delete(@PathVariable("ids") String[] ids,HttpServletRequest request, ModelMap modelMap) {
			if(null != ids && ids.length>0){
				List<Integer> list = new ArrayList<Integer>();
				for(String infoIdStr : ids){
					Integer infoId = Integer.valueOf(infoIdStr);
					list.add(infoId);
				}
				tBankInfoservice.batchDeleteInfo(list);
			}
		 
		return null;
		
	}
	/**
	 * 判断bankCode是否重复
	 * @param request
	 * @param modelMap
	 * @return
	 */
/*	@RequestMapping(value = "/bankCodeValide/{bankOrgNo}",produces = "text/html;charset=UTF-8")
	@ResponseBody*/
	/*public String bankCodeValide(@ModelAttribute("bankOrgNo")  String bankOrgNo,HttpServletRequest request,HttpServletResponse responsem, ModelMap modelMap) {
		JSONObject jb=new JSONObject();		
		try {
			//int count=tBankInfoservice.getBankCodeCount(bankOrgNo);
			System.out.println(count);
			if(count>0){

				jb.put("valmsg","false");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}

		return jb.toString();
	}*/
	/**
	 * 保存银行数据信息
	 * @param request
	 * @param modelMap
	 * @return
	 */
 
	@RequestMapping(value = "/bankOrgInfoSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String bankOrgInfoSave(@ModelAttribute("bankOrgInfoSave")  TBankOrgInfo bankInfo,HttpServletRequest request, ModelMap modelMap) {
		try{
			//权限验证
			HttpSession session = request.getSession();
			/* if(!PermissionUtil.isHavePermission(session, "/bim/bankOrgInfo/saveBankOrgInfo")){
				return "/errorpage/permissionError";
				 
			} */
			ConverNullString sr=new  ConverNullString();
			sr.nullConverNullString(bankInfo);
			String response =  Validate.getInstance().validate(bankInfo,
					com.zdmoney.manager.Validate.InsertCheck.class, Default.class);
			JSONObject jb=new JSONObject();
			
			int count=tBankInfoservice.getBankOrgCount(bankInfo);
			int num=tBankInfoservice.getBankLineNo(bankInfo);
			String id=tBankInfoservice.getBankOrgInID(bankInfo.getBankLineNo());
			if(!StringUtils.isBlank(response)){
				System.out.println("返回信息："+response);				 
				jb.put("valmsg", response);
				return jb.toString();
			}
			if(count>0){
				if(StringUtil.isEmpty(bankInfo.getId())){
					response="银行机构行号已存在";
					jb.put("valmsg",response);
					return jb.toString();
				}else{
					if(!bankInfo.getBankOrgNo().equals(bankInfo.getNoteOrgNo())){
						response="银行机构行号已存在";
						jb.put("valmsg",response);
						return jb.toString();
					}
				}	
				
				}
			if(num>0){
				if(StringUtil.isEmpty(bankInfo.getId())){
					response="支付联行号已存在";
					jb.put("valmsg",response);
					return jb.toString();
				}else{
					if(!bankInfo.getId().toString().equals(id)){
						response="支付联行号已存在";
						jb.put("valmsg",response);
						return jb.toString();
					}
				}	
				
				}
		 
			if(StringUtil.isEmpty(bankInfo.getId())){
				//新增	
				log.info("新增+："+bankInfo.getId());
				tBankInfoservice.insert(bankInfo);
			}else{
				//修改		
				log.info("修改+："+bankInfo.getId());
			    tBankInfoservice.updateBankOrgInfo(bankInfo);
				
			} 
			log.info("保存成功");		
			return  jb.toString();
		}catch(Exception e){
			log.error(e.getMessage(), e);
			modelMap.put("errorMsg", e.getMessage());
			return "/errorpage/error";
		}
	}
 
}
