package com.zendaimoney.thirdpp.account.action.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.account.annotation.FileParseActionAnnotation;
import com.zendaimoney.thirdpp.account.entity.AccountInfo;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;
import com.zendaimoney.thirdpp.account.service.ChannelAccountRequestService;
import com.zendaimoney.thirdpp.account.util.Constants;

/**
 * 解析通道对账文件，将通道对账文件中的记录入对账流水表
 * 
 * @author 00237071
 *
 */
@FileParseActionAnnotation(fileSuffix = Constants.FILE_SUFFIX_CSV)
@Component(value = "com.zendaimoney.thirdpp.account.action.parse.CsvFileParseAction")
public class CsvFileParseAction extends FileParseAction {

	private static final Log logger = LogFactory.getLog(CsvFileParseAction.class);

	@Autowired
	private ChannelAccountRequestService channelAccountRequestService;

	protected void parseFileAndRecord(File file, ChannelAccountConfig config, ChannelAccountRequest request) throws IOException, SQLException, ReflectiveOperationException {
		InputStreamReader fr = null;
		BufferedReader br = null;
		List<String> lineContents = new ArrayList<String>();
		int firstStartRowNumber = -1;
		List<List<String>> listFile = new ArrayList<List<String>>();
		try {
			String rec = null;
			String str;
			List<String> cells = new ArrayList<String>();// 每行记录一个list
			
			fr = new InputStreamReader(new FileInputStream(file), config.getFileEncoding());
			br = new BufferedReader(fr);
			// 读取一行
			int currentRowNumber = 0;
			while ((rec = br.readLine()) != null) {
				currentRowNumber++;
				if (StringUtils.isNotBlank(rec)) {
					lineContents.add(rec);
				}
				Pattern pCells = Pattern.compile("(\"[^\"]*(\"{2})*[^\"]*\")*[^,]*,");
				Matcher mCells = pCells.matcher(rec);
				// 读取每个单元格
				while (mCells.find()) {
					if (firstStartRowNumber == -1) {
						firstStartRowNumber = currentRowNumber;
					}
					str = mCells.group();
					str = str.replaceAll("(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,", "$1");
					str = str.replaceAll("(?sm)(\"(\"))", "$2");
					cells.add(str.trim());
				}
				if (cells.size() > 0) {
					listFile.add(cells);
				}
			}
		} finally {
			releaseResource(fr, br);
		}
		totalCountAmountInfo(lineContents, config, request);
		int rowIndex = getStartRowIndex(request, config, firstStartRowNumber);
		Class<?> tppAccountInfoCla = AccountInfo.class;
		Map<Integer, String> attributeDefinition = getDefinitionAttributes(config);

		List<AccountInfo> aiList = new ArrayList<AccountInfo>();
		AccountInfo accountInfo = null;
		for (int i = rowIndex; i < listFile.size(); i++) {
			List<String> valueList = listFile.get(i);
			accountInfo = new AccountInfo();
			accountInfo.setCurrentIndex(i + 1);
			for (int j = 0; j < valueList.size(); j++) {
				if (StringUtils.isNotBlank(attributeDefinition.get(j))) {
					Field field = tppAccountInfoCla.getDeclaredField(attributeDefinition.get(j));
					field.setAccessible(true);
					field.set(accountInfo, valueList.get(j).trim());
				}
			}
			if (checkUncontrolledFields(accountInfo, config)) {
				initialPublicAttributes(accountInfo, config, request);
				aiList.add(accountInfo);
			}
			accountInfo = null;
		}
		
		batchInsertAccountInfo(aiList);
		aiList = null; listFile = null;
	}

	private void totalCountAmountInfo(List<String> lineContents, ChannelAccountConfig config, ChannelAccountRequest request){
		int size = lineContents.size();
		String[] lines = totalCountAmountLines(config);
		String totalCountAmountDesc = StringUtils.EMPTY;
		if (lines == null || size == 0) {
			return;
		} 
		// 这种情况只适合于统计在明细的前面
		if (lines.length == 1) {
			// 我们认为总金额总记录数在一行
			int line = Integer.parseInt(lines[0]);
			String strs = StringUtils.EMPTY;
			if (line > 0) {
				// if (size <= line -1) return;
				strs = lineContents.get(line - 1);
			} else {
				strs = lineContents.get(size + line);
			}
			if (StringUtils.isNotBlank(strs)) {
				totalCountAmountDesc = totalCountAmountDesc.concat(strs);
			}
		} else {
			int startLine = Integer.parseInt(lines[0]);
			int endLine = Integer.parseInt(lines[1]);
			if (startLine > 0 && endLine > 0 && endLine >= startLine) {
				// if (size <= endLine -1) return;
				for (int i = startLine; i <= endLine; i++) {
					String strs = lineContents.get(i - 1);
					if (StringUtils.isNotBlank(strs)) {
						totalCountAmountDesc = totalCountAmountDesc.concat(strs);
					}
					if (!totalCountAmountDesc.isEmpty() && StringUtils.isNotBlank(strs)) {
						totalCountAmountDesc = totalCountAmountDesc.concat(Constants.STRING_COMMA);
					}
				}
			} 
			
			if (startLine < 0 && endLine < 0 && endLine >= startLine) {
				int start = lineContents.size() + startLine;
				int end = lineContents.size() + endLine;
				// if (size <= end -1) return;
				for (int i = start; i <= end; i++) {
					String strs = lineContents.get(i - 1);
					if (StringUtils.isNotBlank(strs)) {
						totalCountAmountDesc = totalCountAmountDesc.concat(strs);
					}
					if (!totalCountAmountDesc.isEmpty() && StringUtils.isNotBlank(strs)) {
						totalCountAmountDesc = totalCountAmountDesc.concat(Constants.STRING_COMMA);
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
	
	private void releaseResource(InputStreamReader fr, BufferedReader br) {
		if (fr != null) {
			try {
				fr.close();
			} catch (IOException e) {
				logger.error("通道对账 关闭  HSSFWorkbook 资源出错", e);
				return;
			}
		}

		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				logger.error("通道对账 关闭  BufferedInputStream 资源出错", e);
				return;
			}
		}

	}

}
