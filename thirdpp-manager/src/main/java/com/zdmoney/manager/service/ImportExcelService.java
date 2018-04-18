package com.zdmoney.manager.service;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zdmoney.manager.models.ImportExcelErr;
import com.zdmoney.manager.models.TBankOrgInfo;
import com.zdmoney.manager.utils.StringUtil;
import com.zdmoney.manager.utils.WDWUtil;
@Service
public class ImportExcelService {

	//总行数
	private int totalRows = 0;  
	//总条数
	private int totalCells = 0; 
	@Autowired
	private TBankInfoService tBankInfoservice; 
	@Autowired
	private TBankOrgInfoService bankInfoOrgservice; 
	@Autowired
	private TAreaInfoService areaInfoservice; 
	
	private Map<String, String>  bankOrgCodeMap;
	
	private Map<String, String>  bankLineNoMap;
	
	/**
	 * 描述：验证EXCEL文件
	 * @param filePath
	 * @return
	 */
	public boolean validateExcel(String filePath){
		if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))){  
			return false;  
		}  
		return true;
	}
	/**描述 :读EXCEL文件
	 * @param fielName
	 * @return
	 */
	public Map<String, Object>  getExcelInfo(String fileName,MultipartFile Mfile){	
		Map<String, Object>  map=new  HashMap<String, Object>();
		InputStream is = null;  
		try{
			//验证文件名是否合格
			if(!validateExcel(fileName)){
				return null;
			}
			//判断文件时2003版本还是2007版本
			boolean isExcel2003 = true; 
			if(WDWUtil.isExcel2007(fileName)){
				isExcel2003 = false;  
			}
			is = (Mfile.getInputStream());
			map =readExcel(is, isExcel2003); 
			is.close();
		}catch(Exception e){
			e.printStackTrace();
		} finally{
			if(is !=null)
			{
				try{
					is.close();
				}catch(IOException e){
					is = null;    
					e.printStackTrace();  
				}
			}
		}
		return map;
	}

	/**
	 * 此方法两个参数InputStream是字节流。isExcel2003是excel是2003还是2007版本
	 * @param is
	 * @param isExcel2003
	 * @return
	 * @throws IOException
	 */
	public Map<String, Object> readExcel(InputStream is,boolean isExcel2003){
		List<TBankOrgInfo> bankList=new ArrayList<TBankOrgInfo>();
		Map<String, String> areaCodeMap=	areaInfoservice.getAreaListMap();
		Map<String, String> bankCodeMap=	tBankInfoservice.getBnkInfoMap();		
		List<ImportExcelErr> errList=new	ArrayList<ImportExcelErr>();
		try{
			/** 根据版本选择创建Workbook的方式 */
			Workbook wb = null;
			//当excel是2003时
			if(isExcel2003){
				wb = new HSSFWorkbook(is); 
			}
			else{
				wb = new XSSFWorkbook(is); 
			}
			/*int sheetNum=	wb.getNumberOfSheets();
			for(int i=0;i<sheetNum;i++){}*/
			Sheet sheet=wb.getSheetAt(0);
			//得到Excel的行数
			this.totalRows=sheet.getPhysicalNumberOfRows();
			TBankOrgInfo bpam=new TBankOrgInfo();
			//得到Excel的列数(前提是有行数)
			if(totalRows>=1 && sheet.getRow(0) != null){
				this.totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
			}
			if(totalCells!=6){
				ImportExcelErr errImport =new ImportExcelErr();
				errImport.setRowNum(Integer.toString((1)));
				errImport.setNote("列数不为6");
				errList.add(errImport);
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("blist", bankList);
				map.put("errlist", errList);
				return map;
			}
		
			
			for(int r=1;r<totalRows;r++)
			{
			   Row row = sheet.getRow(r);
			   if (row == null) continue;
			   int rowNum=row.getLastCellNum();				
				if (rowNum <6 ){
					ImportExcelErr errImport =new ImportExcelErr();
					errImport.setRowNum(Integer.toString((row.getRowNum()+1)));
					errImport.setNote("列数不为6");
					errList.add(errImport);
					continue;} 
				TBankOrgInfo b=new TBankOrgInfo();
				//循环Excel的列
				for(int c = 0; c <rowNum; c++)
				{ 
					String count=null;
					Cell cell = row.getCell(c); 
				
					if (null != cell)  
					{
						//第一列<银行编码>
						 if(c==0){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							//是否为空 
							if(StringUtil.isEmpty(cell.getStringCellValue())){
								ImportExcelErr errImport =new ImportExcelErr();
								errImport.setRowNum(Integer.toString((row.getRowNum()+1)));
								errImport.setNote("银行编码为空");
								errList.add(errImport);
								break;
								};
							count=bankCodeMap.get(cell.getStringCellValue());
							if(StringUtil.isEmpty(count)){
								ImportExcelErr errImport =new ImportExcelErr();
								errImport.setRowNum(Integer.toString((row.getRowNum()+1)));
								errImport.setNote("银行编码不存在");
								errList.add(errImport);
								break;}; 
							b.setBankCode(cell.getStringCellValue());		
						}
						//第二列
						 else if(c==1){
							//获得第二列<银行机构名称>， 
								cell.setCellType(Cell.CELL_TYPE_STRING);
							 if(StringUtil.isEmpty(cell.getStringCellValue())){								  
								 	ImportExcelErr errImport =new ImportExcelErr();
									errImport.setRowNum(Integer.toString((row.getRowNum()+1)));
									errImport.setNote("银行机构名称为空");
									errList.add(errImport);
								 break;
							};
							 b.setBankOrgName(cell.getStringCellValue());
						}
						//获得第三列<银行机构编码>， 
						else if(c==2){
							/**
							 * 处理：使用POI读excel文件，当遇到特殊格式的字串，比如“13612345678”，等等，
							 * 这样的本来是一个字符串，但是POI在读的时候总是以数值型识别，由此，这样的电话号码读出来后总是1.3XXX+E4
							 */
							/* DecimalFormat df = new DecimalFormat("#");
		                       String cellValue=df.format(cell.getNumericCellValue());*/
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(StringUtil.isEmpty(cell.getStringCellValue())){
								
								ImportExcelErr errImport =new ImportExcelErr();
								errImport.setRowNum(Integer.toString((row.getRowNum()+1)));
								errImport.setNote("银行机构编码为空");
								errList.add(errImport);
								break;}
							bpam.setBankCode(b.getBankCode());
							bpam.setBankOrgNo(cell.getStringCellValue());	
							count=isBankOrgNo(bpam); 
							if(!StringUtil.isEmpty(count)){
								ImportExcelErr errImport =new ImportExcelErr();
								errImport.setRowNum(Integer.toString((row.getRowNum()+1)));
								errImport.setNote("银行机构编码重复");
								errList.add(errImport);
								break;}
							b.setBankOrgNo(cell.getStringCellValue());
						}
						 //第四列 联行号
						else if(c==3){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(StringUtil.isEmpty(cell.getStringCellValue())) {
								b.setBankOrgNo(null);	
								ImportExcelErr errImport =new ImportExcelErr();
								errImport.setRowNum(Integer.toString((row.getRowNum()+1)));
								errImport.setNote("联行号为空");
								errList.add(errImport);
							    break;
							}
							count=isBankLineNo(cell.getStringCellValue()); 
							if(!StringUtil.isEmpty(count)){
								b.setBankOrgNo(null);	
								ImportExcelErr errImport =new ImportExcelErr();
								errImport.setRowNum(Integer.toString((row.getRowNum()+1)));
								errImport.setNote("联行号重复");
								errList.add(errImport);
								break;}
							b.setBankLineNo(cell.getStringCellValue());
						}
						//第五列<省份编码>
						else if(c==4){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(StringUtil.isEmpty(cell.getStringCellValue())){
								b.setBankOrgNo(null);
								ImportExcelErr errImport =new ImportExcelErr();
								errImport.setRowNum(Integer.toString((row.getRowNum()+1)));
								errImport.setNote("机构所在省份编码为空");
								errList.add(errImport);
								break;}
							count=areaCodeMap.get(cell.getStringCellValue());
							if(StringUtil.isEmpty(count)){
								b.setBankOrgNo(null);
								ImportExcelErr errImport =new ImportExcelErr();
								errImport.setRowNum(Integer.toString((row.getRowNum()+1)));
								errImport.setNote("机构所在省份编码不存在");
								errList.add(errImport);
								break;
							}
							b.setBankOrgProvinceNo(cell.getStringCellValue());
						}
						//第六列<城市编码>
						else if(c==5){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(StringUtil.isEmpty(cell.getStringCellValue())){
								b.setBankOrgNo(null);
								ImportExcelErr errImport =new ImportExcelErr();
								errImport.setRowNum(Integer.toString((row.getRowNum()+1)));
								errImport.setNote("机构所在城市编码为空");
								errList.add(errImport);
								break;
							}
								count=areaCodeMap.get(cell.getStringCellValue());
								if(StringUtil.isEmpty(count)){
									b.setBankOrgNo(null);	
									ImportExcelErr errImport =new ImportExcelErr();
									errImport.setRowNum(Integer.toString((row.getRowNum()+1)));
									errImport.setNote("机构所在城市编码不存在");
									errList.add(errImport);
									break;
								}
								if(!count.equals(b.getBankOrgProvinceNo())){
									b.setBankOrgNo(null);	
									ImportExcelErr errImport =new ImportExcelErr();
									errImport.setRowNum(Integer.toString((row.getRowNum()+1)));
									errImport.setNote("机构所在城市编码与机构所在省份编码不匹配");
									errList.add(errImport);
									break;
								}
							 
							b.setBankOrgProvinceCityNo(cell.getStringCellValue());
						}
						
					}else{
						ImportExcelErr errImport =new ImportExcelErr();
						errImport.setRowNum(Integer.toString((row.getRowNum()+1)));
						errImport.setNote("列数不为6");
						errList.add(errImport);
						b.setBankOrgNo(null);
						break;
					}
				}
 				if(!StringUtil.isEmpty(b.getBankOrgNo())){
					bankList.add(b);
				}
				
			}
		}
		catch (IOException e)  {  
			e.printStackTrace();  
		} 
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("blist", bankList);
		map.put("errlist", errList);
		
		return map;
	}
	
	/**
	 * 判断银行机构号是否存在
	 */
	public String isBankOrgNo(TBankOrgInfo bpam){
		String count = null;
		if(bankOrgCodeMap!=null){
			count=bankOrgCodeMap.get("bankCode"+bpam.getBankCode()+"bankOrgNo"+bpam.getBankOrgNo()); 
		}else{
			bankOrgCodeMap=bankInfoOrgservice.getBankOrgInfoMap();
			count=bankOrgCodeMap.get("bankCode"+bpam.getBankCode()+"bankOrgNo"+bpam.getBankOrgNo()); 
		}
		
		return count;
	   /* List<TBankOrgInfo> bankOrgCodeList=	bankInfoOrgservice.getBankOrgInfoMap(); 
		Map<String, String> map=new HashMap<String, String>();	 
		for(TBankOrgInfo boi :bankOrgCodeList){
			map.put("bankCode"+boi.getBankCode()+"bankOrgNo"+boi.getBankOrgNo(), "1");	
		}
		
		for(TBankOrgInfo boi :bankOrgCodeList){
			if(bpam.getBankCode().equals(boi.getBankCode()) && bpam.getBankOrgNo().equals(boi.getBankOrgNo())){
				return true;
			}
		}
		return false;*/
	}
	/**
	 * 判断联行号是否存在
	 * @param bpam
	 * @return
	 */
	public String isBankLineNo(String  bpam){
		String count = null;
		
		if(bankLineNoMap!=null){
			count=bankLineNoMap.get(bpam);
		}else{
			bankLineNoMap=bankInfoOrgservice.getBankLineNoMap();
			count=bankLineNoMap.get(bpam);
		}
		return count;
		
	}
}
