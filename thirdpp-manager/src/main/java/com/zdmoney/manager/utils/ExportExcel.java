package com.zdmoney.manager.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/** 
 *
 * @author 渡心
 * @version 2015年3月20日 上午9:28:15 
 */
public class ExportExcel<T> extends AbstractExcelView {
	
	private final Logger logger = Logger.getLogger(ExportExcel.class);

	public void exportExcel(String title, Collection<T> dataset, String[] fieldNames, OutputStream out)  
    {  
        exportExcel(title, null, dataset, fieldNames, out, "yyyy-MM-dd", null);  
    }  
  
    public void exportExcel(String title, String[] headers, Collection<T> dataset, String[] fieldNames,
            OutputStream out)  
    {  
        exportExcel(title, headers, dataset, fieldNames, out, "yyyy-MM-dd", null);  
    }  
    public void exportExcel(HSSFWorkbook workbook, String title, String[] headers, List<Map> dataset, String[] fieldNames,
            OutputStream out)  
    {  
        exportExcel(workbook, title, headers, dataset, fieldNames, out, "yyyy-MM-dd", null);  
    }
  
    public void exportExcel(String title, String[] headers,  
            Collection<T> dataset, String[] fieldNames, OutputStream out, String pattern, String footInfo)  
    {  
        // 声明一个工作薄  
        HSSFWorkbook workbook = new HSSFWorkbook();  
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title);  
        // 设置表格默认列宽度为20
        sheet.setDefaultColumnWidth(20);
        // 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();  
        // 设置这些样式  
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        // 生成一个字体  
        HSSFFont font = workbook.createFont();  
        font.setColor(HSSFColor.VIOLET.index);  
        font.setFontHeightInPoints((short) 12);  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        // 把字体应用到当前的样式  
        style.setFont(font);  
        // 生成并设置另一个样式  
        HSSFCellStyle style2 = workbook.createCellStyle();  
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);  
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        // 生成另一个字体  
        HSSFFont font2 = workbook.createFont();  
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
        // 把字体应用到当前的样式  
        style2.setFont(font2);  
        
        HSSFFont font3 = workbook.createFont();  
        font3.setColor(HSSFColor.BLUE.index);
  
        // 声明一个画图的顶级管理器  
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();  
        // 定义注释的大小和位置,详见文档  
        //HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));  
        // 设置注释内容  
        //comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));  
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.  
        //comment.setAuthor("duxin");  
  
        // 产生表格标题行  
        HSSFRow row = sheet.createRow(0);  
        for (int i = 0; i < headers.length; i++)  
        {  
            HSSFCell cell = row.createCell(i);  
            cell.setCellStyle(style);  
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
            cell.setCellValue(text);  
        }  
  
        // 遍历集合数据，产生数据行  
        Iterator<T> it = dataset.iterator();  
        int index = 0;  
        while (it.hasNext())  
        {  
            index++;  
            row = sheet.createRow(index);  
            T t = (T) it.next();  
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
            Class tCls = t.getClass();
            for (int i = 0; i < fieldNames.length; i++)  
            {  
            	try  
            	{  
	                HSSFCell cell = row.createCell(i);  
	                cell.setCellStyle(style2);  
	                String fieldName = fieldNames[i];  
	                String getMethodName = "get"  
                        + fieldName.substring(0, 1).toUpperCase()  
                        + fieldName.substring(1);  
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});  
                    Object value = getMethod.invoke(t, new Object[]{});  
                    // 判断值的类型后进行强制类型转换  
                    String textValue = null;  
                    if (value instanceof Date)  
                    {  
                        Date date = (Date) value;  
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
                        textValue = sdf.format(date);  
                    }  
                    else if (value instanceof byte[])  
                    {  
                        // 有图片时，设置行高为60px;  
                        row.setHeightInPoints(60);  
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算  
                        sheet.setColumnWidth(i, (short) (35.7 * 80));  
                        // sheet.autoSizeColumn(i);  
                        byte[] bsValue = (byte[]) value;  
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,  
                                1023, 255, (short) 6, index, (short) 6, index);  
                        anchor.setAnchorType(2);  
                        patriarch.createPicture(anchor, workbook.addPicture(  
                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));  
                    }  
                    else  
                    {  
                        // 其它数据类型都当作字符串简单处理  
                        textValue = StringUtil.parseString(value);  
                    }  
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成  
                    if (textValue != null)  
                    {  
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");  
                        Matcher matcher = p.matcher(textValue);  
                        if (matcher.matches())  
                        {  
                            // 是数字当作double处理  
                            cell.setCellValue(Double.parseDouble(textValue));  
                        }  
                        else  
                        {  
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);  
                            richString.applyFont(font3);
                            cell.setCellValue(richString);  
                        }  
                    }  
                }  
                catch (SecurityException e)  
                {  
                    e.printStackTrace();  
                }  
            	catch (NoSuchMethodException e)  
                {  
                    e.printStackTrace();  
                }  
                catch (IllegalArgumentException e)  
                {  
                    e.printStackTrace();  
                }  
                catch (IllegalAccessException e)  
                {  
                    e.printStackTrace();  
                }  
                catch (InvocationTargetException e)  
                {  
                    e.printStackTrace();  
                }  
                finally  
                {  
                    // 清理资源  
                }  
            }  
        }
        if(!StringUtil.isEmpty(footInfo)){
        	 // 生成一个样式  
            HSSFCellStyle style3 = workbook.createCellStyle();  
            // 设置这些样式  
            style3.setFillForegroundColor(HSSFColor.TAN.index);  
            style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style3.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style3.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            // 把字体应用到当前的样式  
            HSSFFont font1 = workbook.createFont();  
            font1.setColor(HSSFColor.RED.index);  
            font1.setFontHeightInPoints((short) 12);  
            font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            style3.setFont(font1);  
        	
        	int rowNum = dataset.size();
        	HSSFRow footRow = sheet.createRow(rowNum+1);
        	HSSFCell cell0 = footRow.createCell(0); 
        	HSSFRichTextString text = new HSSFRichTextString(footInfo);  
        	cell0.setCellStyle(style3);  
            cell0.setCellValue(text);  
        	for (int i = 1; i < headers.length; i++)  
            {  
                HSSFCell cell = footRow.createCell(i);  
                cell.setCellStyle(style3);  
            } 
        	sheet.addMergedRegion(new CellRangeAddress(rowNum+1, rowNum+1, 0, headers.length-1));
        }
        try  
        {  
            workbook.write(out);  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
    }  
    
    public void exportExcel(HSSFWorkbook workbook, String title, String[] headers,  
            List<Map> dataset, String[] fieldNames, OutputStream out, String pattern, String footInfo)  
    {  
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title);  
        // 设置表格默认列宽度为20
        sheet.setDefaultColumnWidth(20);
        // 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();  
        // 设置这些样式  
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        // 生成一个字体  
        HSSFFont font = workbook.createFont();  
        font.setColor(HSSFColor.VIOLET.index);  
        font.setFontHeightInPoints((short) 12);  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        // 把字体应用到当前的样式  
        style.setFont(font);  
        // 生成并设置另一个样式  
        HSSFCellStyle style2 = workbook.createCellStyle();  
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);  
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        // 生成另一个字体  
        HSSFFont font2 = workbook.createFont();  
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
        // 把字体应用到当前的样式  
        style2.setFont(font2);  
        
        HSSFFont font3 = workbook.createFont();  
        font3.setColor(HSSFColor.BLUE.index);
  
        // 声明一个画图的顶级管理器  
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();  
        // 定义注释的大小和位置,详见文档  
        //HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));  
        // 设置注释内容  
        //comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));  
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.  
        //comment.setAuthor("duxin");  
  
        // 产生表格标题行  
        HSSFRow row = sheet.createRow(0);  
        for (int i = 0; i < headers.length; i++)  
        {  
            HSSFCell cell = row.createCell(i);  
            cell.setCellStyle(style);  
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
            cell.setCellValue(text);  
        }  
  
        // 遍历集合数据，产生数据行  
//        Iterator<T> it = dataset.iterator();  
        int index = 0;  
        for (Map map : dataset) 
        {  
            index++;  
            row = sheet.createRow(index);  
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
            for (int i = 0; i < fieldNames.length; i++)  
            {  
            	try  
            	{  
	                HSSFCell cell = row.createCell(i);  
	                cell.setCellStyle(style2);  
	                String fieldName = fieldNames[i];  
	                /*String getMethodName = "get"  
                        + fieldName.substring(0, 1).toUpperCase()  
                        + fieldName.substring(1);  
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});  
                    Object value = getMethod.invoke(t, new Object[]{});  */
	                Object value = map.get(fieldName);
                    // 判断值的类型后进行强制类型转换  
                    String textValue = null;  
                    if (value instanceof Date)  
                    {  
                        Date date = (Date) value;  
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
                        textValue = sdf.format(date);  
                    }  
                    else if (value instanceof byte[])  
                    {  
                        // 有图片时，设置行高为60px;  
                        row.setHeightInPoints(60);  
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算  
                        sheet.setColumnWidth(i, (short) (35.7 * 80));  
                        // sheet.autoSizeColumn(i);  
                        byte[] bsValue = (byte[]) value;  
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,  
                                1023, 255, (short) 6, index, (short) 6, index);  
                        anchor.setAnchorType(2);  
                        patriarch.createPicture(anchor, workbook.addPicture(  
                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));  
                    }  
                    else  
                    {  
                        // 其它数据类型都当作字符串简单处理  
                        textValue = StringUtil.parseString(value);  
                    }  
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成  
                    if (textValue != null)  
                    {  
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");  
                        Matcher matcher = p.matcher(textValue);  
                        if (matcher.matches())  
                        {  
                            // 是数字当作double处理  
                            cell.setCellValue(Double.parseDouble(textValue));  
                        }  
                        else  
                        {  
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);  
                            richString.applyFont(font3);
                            cell.setCellValue(richString);  
                        }  
                    }  
                }  
                catch (SecurityException e)  
                {  
                    e.printStackTrace();  
                }  
                catch (IllegalArgumentException e)  
                {  
                    e.printStackTrace();  
                }  
                finally  
                {  
                    // 清理资源  
                }  
            }  
        }
        if(!StringUtil.isEmpty(footInfo)){
        	 // 生成一个样式  
            HSSFCellStyle style3 = workbook.createCellStyle();  
            // 设置这些样式  
            style3.setFillForegroundColor(HSSFColor.TAN.index);  
            style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
            style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
            style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
            style3.setBorderRight(HSSFCellStyle.BORDER_THIN);  
            style3.setBorderTop(HSSFCellStyle.BORDER_THIN);  
            style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            // 把字体应用到当前的样式  
            HSSFFont font1 = workbook.createFont();  
            font1.setColor(HSSFColor.RED.index);  
            font1.setFontHeightInPoints((short) 12);  
            font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            style3.setFont(font1);  
        	
        	int rowNum = dataset.size();
        	HSSFRow footRow = sheet.createRow(rowNum+1);
        	HSSFCell cell0 = footRow.createCell(0); 
        	HSSFRichTextString text = new HSSFRichTextString(footInfo);  
        	cell0.setCellStyle(style3);  
            cell0.setCellValue(text);  
        	for (int i = 1; i < headers.length; i++)  
            {  
                HSSFCell cell = footRow.createCell(i);  
                cell.setCellStyle(style3);  
            } 
        	sheet.addMergedRegion(new CellRangeAddress(rowNum+1, rowNum+1, 0, headers.length-1));
        }
        try  
        {  
        	
            workbook.write(out);  
            out.flush();
        	out.close();
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        } 
    }  
    
    /**
     * 将数据导出到excel2007中
     * @param title
     * @param headers
     * @param dataset
     * @param fieldNames
     * @param out
     * @param pattern
     */
 	public void exportExcel2007(String title, String[] headers,
 			Collection<T> dataset, String[] fieldNames, OutputStream out,
 			String pattern) {
 		// 创建工作簿
 		XSSFWorkbook workBook = new XSSFWorkbook(); // 创建工作薄
 		// 创建sheet
 		XSSFSheet sheet = workBook.createSheet();
 		// 设置表格默认列宽度为20
 		sheet.setDefaultColumnWidth(20);
 		// 创建标题样式
 		XSSFCellStyle titleCellStyle = workBook.createCellStyle();
 		// 设置标题样式
 		titleCellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
 		titleCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
 		titleCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
 		titleCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
 		titleCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
 		titleCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
 		titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
 		// 生成标题字体
 		XSSFFont titleFont = workBook.createFont();
 		titleFont.setColor(HSSFColor.VIOLET.index);
 		titleFont.setFontHeightInPoints((short) 12);
 		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
 		// 把标题字体应用到当前的标题样式
 		titleCellStyle.setFont(titleFont);

 		// 生成并设置内容样式
 		XSSFCellStyle contentCellStyle = workBook.createCellStyle();
 		contentCellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
 		contentCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
 		contentCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
 		contentCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
 		contentCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
 		contentCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
 		contentCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
 		contentCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
 		// 生成内容字体
 		XSSFFont contentFont = workBook.createFont();
 		contentFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
 		// 把内容字体应用到当前的内容样式
 		contentCellStyle.setFont(contentFont);

 		// 若内容为数字则使用蓝色字体
 		XSSFFont numFont = workBook.createFont();
 		numFont.setColor(HSSFColor.BLUE.index);

 		// 产生并写入标题栏
 		XSSFRow titleRow = sheet.createRow((short) 0);
 		for (int i = 0; i < headers.length; i++) {
 			XSSFCell cell = titleRow.createCell(i);
 			cell.setCellStyle(titleCellStyle);
 			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
 			cell.setCellValue(headers[i]);
 		}

 		// 遍历集合数据，产生数据行
 		Iterator<T> it = dataset.iterator();
 		int index = 0;
 		XSSFRow conentRow = null;
 		while (it.hasNext()) {
 			index++;
 			conentRow = sheet.createRow(index);
 			T t = (T) it.next();
 			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
 			Class tCls = t.getClass();
 			for (int i = 0; i < fieldNames.length; i++) {
 				try {
 					XSSFCell cell = conentRow.createCell(i);
 					cell.setCellStyle(contentCellStyle);
 					String fieldName = fieldNames[i];
 					String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
 					Method getMethod = tCls.getMethod(getMethodName,new Class[] {});
 					Object value = getMethod.invoke(t, new Object[] {});
 					// 判断值的类型后进行强制类型转换
 					String textValue = null;
 					if (value instanceof Date) {
 						Date date = (Date) value;
 						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
 						textValue = sdf.format(date);
 					}else {
 						// 其它数据类型都当作字符串简单处理
 						textValue = StringUtil.parseString(value);
 					}
 					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
 					if (textValue != null) {
 						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
 						Matcher matcher = p.matcher(textValue);
 						if (matcher.matches()) {
 							// 是数字当作double处理
 							cell.setCellValue(Double.parseDouble(textValue));
 						} else {
 							XSSFRichTextString richString = new XSSFRichTextString(textValue);
 							richString.applyFont(numFont);
 							cell.setCellValue(richString);
 						}
 					}
 				} catch (SecurityException e) {
 					logger.error(e);
 				} catch (NoSuchMethodException e) {
 					logger.error(e);
 				} catch (IllegalArgumentException e) {
 					logger.error(e);
 				} catch (IllegalAccessException e) {
 					logger.error(e);
 				} catch (InvocationTargetException e) {
 					logger.error(e);
 				} finally {
 					// 清理资源
 				}
 			}
 		}
 		try {
 			workBook.write(out);
 			out.flush();
 		} catch (IOException e) {
 			logger.error(e);
 		}
 	}
 	
 	/**  
     * 设置下载文件中文件的名称  
     *   
     * @param filename  
     * @param request  
     * @return  
     */    
	public String encodeFilename(String filename, HttpServletRequest request) {
		/**
		 * 获取客户端浏览器和操作系统信息 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE
		 * 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)
		 * 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1;
		 * zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6
		 */
		String agent = request.getHeader("USER-AGENT");
		try {
			//IE 6-10
			if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {
				String newFileName = URLEncoder.encode(filename, "UTF-8");
				newFileName = StringUtils.replace(newFileName, "+", "%20");
				if (newFileName.length() > 150) {
					newFileName = new String(filename.getBytes("GB2312"),"ISO8859-1");
					newFileName = StringUtils.replace(newFileName, " ", "%20");
				}
				return newFileName;
			}
			//IE 11
			if ((agent != null) && (-1 != agent.indexOf("rv:11.0") && -1 != agent.indexOf("Trident/7.0"))) {
				
				return URLEncoder.encode(filename, "UTF-8");
			}
			//FIRE FOX
			if ((agent != null) && (-1 != agent.indexOf("Mozilla")))
				return MimeUtility.encodeText(filename, "UTF-8", "B");
			return filename;
		} catch (Exception ex) {
			logger.error(ex);
			return filename;
		}
	}
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String title = (String) model.get("title");
		String[] headers = (String[]) model.get("headers");
		List<Map> dataset = (List<Map>) model.get("dataset");
		String[] fieldNames = (String[]) model.get("fieldNames");
		String filename = (String) model.get("fileName");//设置下载时客户端Excel的名称     
        filename = this.encodeFilename(filename, request);//处理中文文件名  
        response.setContentType("application/vnd.ms-excel");     
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
		this.exportExcel(workbook, title, headers, dataset, fieldNames, response.getOutputStream());
	}

}
