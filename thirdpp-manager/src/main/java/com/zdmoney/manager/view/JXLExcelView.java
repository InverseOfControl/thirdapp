package com.zdmoney.manager.view;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.document.AbstractJExcelView;

/** 
 *
 * @author 00225641
 * @version 2014年11月19日 下午4:05:10 
 */
public class JXLExcelView extends AbstractJExcelView {
	Logger log = Logger.getLogger(JXLExcelView.class);
	
	private String fileName = "";
	
	private String[] columnNames = null;  

    private String[] dbColumnNames = null;  

//    private Integer[] columnWidths = null;
    
	@Override
	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map<String, Object> map,
			WritableWorkbook workBook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		fileName = (String) map.get("fileName");
		
		columnNames = (String[])map.get("columnNames");  
      
        dbColumnNames = (String[])map.get("dbColumnNames");  
          
        OutputStream os = null;  
        try {  
  
            String excelName = fileName + ".xls";  
            // 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开  
            response.setContentType("APPLICATION/OCTET-STREAM");  
            response.setHeader("Content-Disposition", "attachment; filename="  
                    + URLEncoder.encode(excelName, "UTF-8"));  
            os = response.getOutputStream();  
            // sheet名称  
            String sheetName = fileName;  
  
            // 全局设置  
            WorkbookSettings setting = new WorkbookSettings();  
            Locale locale = new java.util.Locale("zh", "CN");  
            setting.setLocale(locale);  
            setting.setEncoding("ISO-8859-1");  
            // 创建工作薄  
            workBook = Workbook.createWorkbook(os); // 建立excel文件  
            // 创建第一个工作表  
            WritableSheet ws = workBook.createSheet(sheetName, 1); // sheet名称  
            // 添加标题  
            addColumNameToWsheet(ws);  
  
            List<Map<String,Object>> rowList = (List<Map<String,Object>>) map.get("rowList");  
            writeContext(ws, rowList);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(),e);
        } finally {  
            // 写入文件  
            try {  
            	workBook.write();  
            	workBook.close();  
                os.flush();  
                os.close();  
            } catch (WriteException e) {  
            	log.error(e.getMessage(),e);  
            } catch (IOException e) {  
            	log.error(e.getMessage(),e); 
            }  
  
        }  
		
	}
	
	private void writeContext(WritableSheet wsheet, List<Map<String,Object>> rowList) {  
        int rows = rowList.size();  
        Label wlabel = null;  
        WritableCellFormat wcf = getFormat();  
        Object value = null;  
        try {  
            for (int i = 0; i < rows; i++) {  
                Map<String,Object> row = rowList.get(i);   
                int cols = columnNames.length;
                for (int j = 0; j < cols; j++) {  
                    value = row.get(dbColumnNames[j])==null?"":row.get(dbColumnNames[j]);
                    wlabel = new Label(j, (i + 1), value + "", wcf);  
                    wsheet.addCell(wlabel);  
                }  
            }  
        } catch (Exception e) {  
        	log.error(e.getMessage(),e);
        }  
  
    }  
  
    // 添加标题样式  
    private void addColumNameToWsheet(jxl.write.WritableSheet wsheet)  
            throws RowsExceededException, WriteException {  
  
        // 设置excel标题  
        WritableFont wfont = new WritableFont(WritableFont.ARIAL,  
                WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD); 
        WritableCellFormat wcfFC = new WritableCellFormat(wfont);
        wcfFC.setAlignment(Alignment.CENTRE);  
        wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);  
        Label wlabel1 = null;  
        String[] columNames = columnNames;  
        if (null == columNames)  
            return;  
        int colSize = columNames.length;  
  
//        Integer[] colsWidth = columnWidths;  
//        if (null == colsWidth) {  
//            colsWidth = new Integer[colSize];  
//            for (int i = 0; i < colSize; i++) {  
//                colsWidth[i] = 20;  
//            }  
//        }  
  
        int width = 0;  
        String colName = null;  
        for (int i = 0; i < colSize; i++) {  
            colName = columNames[i];  
            if (null == colName || "".equals(colName))  
                colName = "";  
            wlabel1 = new Label(i, 0, colName, wcfFC);  
            wsheet.addCell(wlabel1);  
            width = 30;  //列宽
            // 默认设置列宽  
            wsheet.setColumnView(i, width);  
        }  
  
    }  
  
    // 设置格式  
    private WritableCellFormat getFormat() {  
  
        WritableFont wfont = getFont();  
        WritableCellFormat wcfFC = new WritableCellFormat(wfont);  
        try {  
            wcfFC.setWrap(true);  
            wcfFC.setAlignment(Alignment.CENTRE);  
            wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);  
        } catch (WriteException e) {  
        	log.error(e.getMessage(),e);  
        }  
        return wcfFC;  
    }  
  
    // 设置字体  
    private WritableFont getFont() {  
        return new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD);  
    }  

}
