package com.zendaimoney.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

/**读取excel中内容
 * @author zhengxiao
 * @date 2015-10-16 10:40
 */
public class ReadExcelUtil {
	
	
	
	/**支持Excel 95-2000的所有版本。读取excel中内容，每行数据放到一个string数组，所有数组放到list。
	 * @param excelFile 读取文件对象
	 * @param sheetNum 读取第几个工作区的内容，从0开始读值，0代表第一个工作区
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 * @return List<String[]> list
	 */
	public static List<String[]> readExcel(File excelFile,int sheetNum) throws BiffException,
			IOException {
		// 创建一个list 用来存储读取的内容
		List<String[]> list = new ArrayList<String[]>();
		WorkbookSettings workbookSettings = new WorkbookSettings();
	    //workbookSettings.setEncoding("UTF-8"); //解决中文乱码
	    Workbook  rwb = Workbook.getWorkbook(excelFile, workbookSettings);
		Cell cell = null;
		// 创建输入流
		InputStream stream = new FileInputStream(excelFile);
		// 获取Excel文件对象
		rwb = Workbook.getWorkbook(stream);
		// 获取文件的指定工作表 默认的第一个
		Sheet sheet = rwb.getSheet(sheetNum);
		// 行数(表头的目录不需要，从1开始)
		for (int i = 1; i < sheet.getRows(); i++) {
			// 创建一个数组 用来存储每一列的值
			String[] str = new String[sheet.getColumns()];
			// 列数
			for (int j = 0; j < sheet.getColumns(); j++) {
				// 获取第i行，第j列的值
				cell = sheet.getCell(j, i);
				str[j] = cell.getContents();
			}
			// 把刚获取的列存入list
			list.add(str);
		}
		rwb.close();
		stream.close();
		// 返回值集合
		return list;
	}
	
	
	public static List<String[]> poireadExcel(File excelFile,int sheetNum) throws IOException
	{
		// 创建一个list 用来存储读取的内容
		List<String[]> list = new ArrayList<String[]>();
		//创建数据流 xls
		InputStream is = new FileInputStream(excelFile);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		//xls文件中某个sheet页面
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(sheetNum);
         //逐行处理数据
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++){
			HSSFRow hssfRow = hssfSheet.getRow(rowNum);
			// 创建一个数组 用来存储每一列的值
			String[] str = new String[hssfRow.getLastCellNum()];
			//逐列读取数据，数据处理为字符串
			for(int colNum = 0; colNum <= hssfRow.getLastCellNum()-1; colNum++){
				HSSFCell hssfcell= hssfRow.getCell(colNum);
				//列值空“”处理报错，需要单独解决
				if (hssfcell==null){
					str[colNum]="";
				}
				else{
				  //读取列值	
				  str[colNum]=hssfRow.getCell(colNum).getStringCellValue(); 	
				}
			}
			// 把刚获取的列存入list
			list.add(str);
		}
	
//		hssfWorkbook.close();
		is.close();
		return list;
	}
	/**
	 * 读取测试用例文件
	 *  *  在Java文件中getResource或getResourceAsStream均可
		例：getClass().getResourceAsStream(filePath);
		filePath可以是"/filename",这里的/代表web发布根路径下WEB-INF/classes
	 * @param file
	 * @return
	 */
	public static File getFile(String file){
		File f =  new File(ReadExcelUtil.class.getResource(file).getFile());
		return f;
	}
}
