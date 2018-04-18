package com.zendaimoney.thirdpp.collect.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by YMSX30009 on 2017/4/11.
 */
public class Excel {

    public enum ExcelType {
        XLS, XLSX
    }

    private Workbook workbook;

    public Excel() throws Exception {
        this(ExcelType.XLS);
    }

    public Excel(ExcelType excelType) throws Exception {
        if (excelType == ExcelType.XLS) {
            workbook = new HSSFWorkbook();
        } else {
            workbook = new XSSFWorkbook();
        }
    }

    public Excel(InputStream in, ExcelType excelType) throws Exception {
        if (excelType == ExcelType.XLS) {
            workbook = new HSSFWorkbook(in);
        } else {
            workbook = new XSSFWorkbook(in);
        }
    }

    /**
     * 将Excel文件转为Bean对象
     *
     * @param headerMapper 列标题的键值对，key:Bean的属性名，key对应Excel的列标题
     * @param tClass
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> List<T> excelToBean(Map<String, String> headerMapper, Class<T> tClass) throws Exception {
        List<T> list = new ArrayList<>();
        List<List<Cell>> rows = getRowsRecords();
        int total = rows.size();
        if (total > 0) {
            int pitch = rows.get(0).size();
            for (int k = 1; k < total; k++) {
                if(rows.get(k).size() == 0){
                    continue;
                }
                T t = tClass.newInstance();
                int temp = 0;
                for (int num = 0; num < pitch; num++) {

                    for (String header : headerMapper.keySet()) {
                        Cell colCell = rows.get(0).get(num);
                        if (colCell.getStringCellValue().equals(headerMapper.get(header))) {
                            PropertyDescriptor propertyDescriptor = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptor(t, header);
                            if (propertyDescriptor != null && num < rows.get(k).size()) {
                                Object val = getCellValue(rows.get(k).get(num), propertyDescriptor);

                                if(val == null || "".equals(val.toString())){
                                    temp ++;
                                }else{
                                    // 四舍五入
                                    if(propertyDescriptor.getPropertyType().isAssignableFrom(BigDecimal.class)){
                                        BigDecimal bd = new BigDecimal(val.toString());
                                        bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                                        val = new BigDecimal(bd.doubleValue()+"");
                                    }
                                    BeanUtils.setProperty(t, header, val);
                                }
                                break;
                            }
                        }
                    }
                }

                if(temp < pitch){
                    list.add(t);
                }
            }
        }
        return list;
    }

    /**
     * 将Bean转为Excel
     *
     * @param headerMapper
     * @param list
     * @param sheetRowNum  每一张表的行数，小于0则不分表
     * @param <T>
     * @throws Exception
     */
    public <T> void beanToExcel(Map<String, String> headerMapper, List<T> list, int sheetRowNum) throws Exception {
        int len = list.size();
        Sheet sheet = null;
        int lastRecRowIndex = 1;
        boolean once = true;
        for (int i = 0; i < len; ++i) {
            T t = list.get(i);
            if (sheetRowNum > 0) {
                if (i % sheetRowNum == 0) {
                    sheet = workbook.createSheet("Sheet" + i / sheetRowNum);
                    lastRecRowIndex = 1;
                    int index = 0;
                    Row headerRow = sheet.createRow(0);
                    for (String key : headerMapper.keySet()) {
                        Cell cell = headerRow.createCell(index++, CellType.STRING);
                        cell.setCellValue(headerMapper.get(key));
                    }
                }
            } else if (once) {
                once = false;
                sheet = workbook.createSheet("Sheet1");
                lastRecRowIndex = 1;
                int index = 0;
                Row headerRow = sheet.createRow(0);
                for (String key : headerMapper.keySet()) {
                    Cell cell = headerRow.createCell(index++, CellType.STRING);
                    cell.setCellValue(headerMapper.get(key));
                }
            }
            assert sheet != null;
            Row recRow = sheet.createRow(lastRecRowIndex++);
            int index = 0;
            for (String key : headerMapper.keySet()) {
                PropertyDescriptor propertyDescriptor = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptor(t, key);
                if (propertyDescriptor != null) {
                    String rs = BeanUtils.getProperty(t, key);
                    Cell cell = recRow.createCell(index++);
                    if (rs != null) {
                        setCellValue(cell, propertyDescriptor, rs);
                    }
                }
            }
        }
    }

    private List<List<Cell>> getRowsRecords() {
        List<List<Cell>> result = new ArrayList<>();
        for (Sheet sheet : workbook) {
            for (Row row : sheet) {
                List<Cell> rowRecs = new ArrayList<>();
                for (int cn = 0; cn < row.getLastCellNum(); cn++) {
                    // If the cell is missing from the file, generate a blank one
                    Cell cell = row.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    rowRecs.add(cell);
                }
                result.add(rowRecs);
            }
        }
        return result;
    }

    private CellType getIntentCelltype(PropertyDescriptor propertyDescriptor) {
        Class t = propertyDescriptor.getPropertyType();
        if (t == String.class) {
            return (CellType.STRING);
        } else if (t == Date.class) {
            return (CellType.STRING);
        } else if (t == Integer.class) {
            return (CellType.NUMERIC);
        } else if (t == Long.class) {
            return (CellType.NUMERIC);
        }
        return (CellType.STRING);
    }

    private void setCellValue(Cell cell, PropertyDescriptor propertyDescriptor, String rs) {
        cell.setCellType(getIntentCelltype(propertyDescriptor));
        cell.setCellValue(rs);
    }

    private Object getCellValue(Cell cell, PropertyDescriptor propertyDescriptor) {
        CellType cellType = propertyDescriptor == null ? cell.getCellTypeEnum() : getIntentCelltype(propertyDescriptor);
        cell.setCellType(cellType);//make sure the type matches intent for retrieving
        switch (cellType) {
            default:
            case _NONE:
            case BLANK:
            case ERROR:
                return null;
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case NUMERIC:
                return cell.getNumericCellValue();
        }
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public static void main(String[] args) {

        String ss = "20000.009999999998";
        BigDecimal b = new BigDecimal(ss);
//        b.setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.println(b.doubleValue());

    }
}
