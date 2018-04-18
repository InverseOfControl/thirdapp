package com.zendaimoney.thirdpp.account.action.parse;

import java.io.BufferedInputStream;
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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.account.annotation.FileParseActionAnnotation;
import com.zendaimoney.thirdpp.account.entity.AccountInfo;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;
import com.zendaimoney.thirdpp.account.util.Constants;

@FileParseActionAnnotation(fileSuffix = Constants.FILE_SUFFIX_TXT)
@Component(value = "com.zendaimoney.thirdpp.account.action.parse.TxtFileParseAction")
public class TxtFileParseAction extends FileParseAction {

	private static final Log logger = LogFactory.getLog(TxtFileParseAction.class);

	protected void parseFileAndRecord(File file, ChannelAccountConfig config, ChannelAccountRequest request) throws IOException, SQLException, ReflectiveOperationException{
		String rec = null;
		InputStreamReader fr = null;
		BufferedReader br = null;
		List<List<String>> listFile = new ArrayList<List<String>>();
		String code = getFilecharset(file);
		List<String> lineContents = new ArrayList<String>();
		try {
			fr = new InputStreamReader(new FileInputStream(file), code);
			br = new BufferedReader(fr);
			// 读取一行
			while ((rec = br.readLine()) != null) {
				if (StringUtils.isNotBlank(rec)) {
					lineContents.add(rec);
				}
				// 每行记录一个list
				List<String> cells = new ArrayList<String>();
				String[] attrs = rec.split(transfered(config.getAttrSplit()));
				for (String str : attrs) {
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
		int rowIndex = getStartRowIndex(request, config, 0);
		Class<?> tppAccountInfoCla = AccountInfo.class;
		Map<Integer, String> attributeDefinition = getDefinitionAttributes(config);

		List<AccountInfo> aiList = new ArrayList<AccountInfo>();
		AccountInfo accountInfo = null;
		for (int i = rowIndex; i < listFile.size(); i++) {
			List<String> valueList = listFile.get(i);
			accountInfo = new AccountInfo();
			accountInfo.setCurrentIndex(i + 1);
			Field field = null;
			for (int j = 0; j < valueList.size(); j++) {
				if (StringUtils.isNotBlank(attributeDefinition.get(j))) {
					field = tppAccountInfoCla.getDeclaredField(attributeDefinition.get(j));
					field.setAccessible(true);
					field.set(accountInfo, valueList.get(j).trim());
					field = null;
				}
			}
			
			if (checkUncontrolledFields(accountInfo, config)) {
				initialPublicAttributes(accountInfo, config, request);
				aiList.add(accountInfo);
			}
			accountInfo = null;
		}
		batchInsertAccountInfo(aiList);
		listFile = null; aiList = null;
	}
	
	/** 
	 * 判断文件的编码格式 
	 * @param fileName :file 
	 * @return 文件编码格式 
	 * @throws IOException 
	 */  
	private static String getFilecharset(File sourceFile) {
		String charset = "GBK";
		BufferedInputStream bis = null;
		byte[] first3Bytes = new byte[3];
		try {
			boolean checked = false;
			bis = new BufferedInputStream(new FileInputStream(sourceFile));
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1) {
				return charset; // 文件编码为 ANSI
			} else if (first3Bytes[0] == (byte) 0xFF
					&& first3Bytes[1] == (byte) 0xFE) {
				charset = "UTF-16LE"; // 文件编码为 Unicode
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE
					&& first3Bytes[1] == (byte) 0xFF) {
				charset = "UTF-16BE"; // 文件编码为 Unicode big endian
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF
					&& first3Bytes[1] == (byte) 0xBB
					&& first3Bytes[2] == (byte) 0xBF) {
				charset = "UTF-8"; // 文件编码为 UTF-8
				checked = true;
			}
			bis.reset();
			if (!checked) {
				while ((read = bis.read()) != -1) {
					if (read >= 0xF0)
						break;
					if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
						break;
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
							// (0x80
							// - 0xBF),也可能在GB编码内
							continue;
						else
							break;
					} else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = "UTF-8";
								break;
							} else
								break;
						} else
							break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return charset;
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
				//if (size <= line -1) return;
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
				//if (size <= endLine -1) return;
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
				//if (size <= end -1) return;
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
	
	private String transfered(String split){
		if (split.equals(Constants.STRING_CA)) {
			return Constants.STRING_CA_TR;
		}
		return split;
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
