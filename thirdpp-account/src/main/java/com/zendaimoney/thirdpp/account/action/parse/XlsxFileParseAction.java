package com.zendaimoney.thirdpp.account.action.parse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.account.annotation.FileParseActionAnnotation;
import com.zendaimoney.thirdpp.account.entity.AccountInfo;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;
import com.zendaimoney.thirdpp.account.exception.PlatformException;
import com.zendaimoney.thirdpp.account.util.CalendarUtils;
import com.zendaimoney.thirdpp.account.util.Constants;

@FileParseActionAnnotation(fileSuffix = Constants.FILE_SUFFIX_XLSX)
@Component(value = "com.zendaimoney.thirdpp.account.action.parse.XlsxFileParseAction")
public class XlsxFileParseAction extends FileParseAction {

	private static final Log logger = LogFactory.getLog(XlsxFileParseAction.class);

	protected void parseFileAndRecord(File file, ChannelAccountConfig config, ChannelAccountRequest request) throws IOException, SQLException, ReflectiveOperationException{
		Workbook xssfWorkbook = null;
		Map<Integer, String> attributeDefinition = getDefinitionAttributes(config);
		try {
			OPCPackage pkg = null;
			try {
				pkg = OPCPackage.open(file);
			} catch (InvalidFormatException e) {
				throw new PlatformException("通道对账解析对账文件失败，不能识别的文件格式");
			}
			xssfWorkbook = new XSSFWorkbook(pkg);
			Class<?> tppAccountInfoCla = AccountInfo.class;
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				Sheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				totalCountAmountInfo(xssfSheet, config, request);
				List<AccountInfo> aiList = new ArrayList<AccountInfo>();
				AccountInfo accountInfo = null;
				int rowIndex = getStartRowIndex(request, config, 0);
				for (int rowNum = rowIndex; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					Row xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						accountInfo = new AccountInfo();
						accountInfo.setCurrentIndex(rowNum + 1);
						for (Entry<Integer, String> entry :attributeDefinition.entrySet()) {
							Field field = tppAccountInfoCla.getDeclaredField(entry.getValue());
							field.setAccessible(true);
							field.set(accountInfo, getValue(xssfRow.getCell(entry.getKey())));
						}
						
						if (checkUncontrolledFields(accountInfo, config)) {
							initialPublicAttributes(accountInfo, config, request);
							aiList.add(accountInfo);
						}
						accountInfo = null;
					}
				}
				batchInsertAccountInfo(aiList);
				aiList = null;
			}
		} finally {
			releaseResource(xssfWorkbook);
		}
	}

	private void totalCountAmountInfo(Sheet hssfSheet, ChannelAccountConfig config, ChannelAccountRequest request) {
		int size = hssfSheet.getLastRowNum();
		String[] lines = totalCountAmountLines(config);
		String totalCountAmountDesc = StringUtils.EMPTY;
		if (lines == null || size <= 0) {
			return;
		} else {
			if (lines.length == 1) {
				// 我们认为总金额总记录在一行
				int line = Integer.parseInt(lines[0]);
				Row hssfRow = null;
				if (line > 0) {
					//if (size <= line -1) return;
					hssfRow = hssfSheet.getRow(line - 1);
				} else {
					hssfRow = hssfSheet.getRow(hssfSheet.getLastRowNum() + line);
				}
				if (hssfRow != null) {
					for (int i = hssfRow.getFirstCellNum(); i < hssfRow.getLastCellNum(); i++ ) {
						String str = getValue(hssfRow.getCell(i));
						if (StringUtils.isNotBlank(str)) {
							totalCountAmountDesc = totalCountAmountDesc.concat(str);
						}
					}
				}
			} else {
				int startLine = Integer.parseInt(lines[0]);
				int endLine = Integer.parseInt(lines[1]);
				if (startLine > 0 && endLine > 0 && endLine >= startLine) {
					//if (size <= endLine -1) return;
					for (int i = startLine; i <= endLine; i++) {
						Row hssfRow =  hssfSheet.getRow(i - 1);
						if (hssfRow != null) {
							for (int j = hssfRow.getFirstCellNum(); j < hssfRow.getLastCellNum(); j++) {
								String str = getValue(hssfRow.getCell(j));
								if (StringUtils.isNotBlank(str)) {
									totalCountAmountDesc = totalCountAmountDesc.concat(str);
								}
							}
							if (!totalCountAmountDesc.isEmpty()) {
								totalCountAmountDesc = totalCountAmountDesc.concat(Constants.STRING_COMMA);
							}
						}
					}
				} 
				
				if (startLine < 0 && endLine < 0 && endLine >= startLine) {
					int start = hssfSheet.getLastRowNum() + startLine;
					int end = hssfSheet.getLastRowNum() + endLine;
					//if (size <= end -1) return;
					for (int i = start; i <= end; i++) {
						Row hssfRow2 = hssfSheet.getRow(i - 1);
						if (hssfRow2 != null) {
							for (int j = hssfRow2.getFirstCellNum(); j < hssfRow2.getLastCellNum(); j++) {
								String str = getValue(hssfRow2.getCell(j));
								if (StringUtils.isNotBlank(str)) {
									totalCountAmountDesc = totalCountAmountDesc.concat(str);
								}
							}
							if (!totalCountAmountDesc.isEmpty()) {
								totalCountAmountDesc = totalCountAmountDesc.concat(Constants.STRING_COMMA);
							}
						}
					}
				}
			}
			
			if (!totalCountAmountDesc.isEmpty()) {
				totalCountAmountDesc = totalCountAmountDesc.replaceAll(Constants.STRING_JIN, StringUtils.EMPTY);
				request.setTotalCountAmountDesc(totalCountAmountDesc);
				channelAccountRequestService.update(request);
			}
		}
	}
	
	private void releaseResource(Workbook xssfWorkbook) {
		if (xssfWorkbook != null) {
			try {
				xssfWorkbook.close();
			} catch (IOException e) {
				logger.error("通道对账 关闭  XSSFWorkbook 资源出错", e);
				return;
			}
		}
	}
	
	private String getValue(Cell hssfCell) {
		if (hssfCell != null) {
			if (HSSFCell.CELL_TYPE_BOOLEAN == hssfCell.getCellType()) {
				return String.valueOf(hssfCell.getBooleanCellValue()).trim();
			} else if (HSSFCell.CELL_TYPE_NUMERIC == hssfCell.getCellType()) {
				if (DateUtil.isCellDateFormatted(hssfCell)) {
					Date date = hssfCell.getDateCellValue();
					return DateFormatUtils.format(date, CalendarUtils.LONG_FORMAT_BLAS).trim();
				} else {
					DecimalFormat df = new DecimalFormat(Constants.MONEY_FORMAT_POINT_TWO);
					return df.format(hssfCell.getNumericCellValue()).trim();
				}
			} else if (HSSFCell.CELL_TYPE_STRING == hssfCell.getCellType()){
				return hssfCell.getRichStringCellValue().toString().trim();
			}
		}
		return null;
	}

}
