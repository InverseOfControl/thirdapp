package com.zendaimoney.util;

import java.io.File;

import java.util.List;


import com.zendaimoney.util.ReadExcelUtil;


public class ReadExcleHelper {

	public static String COLLECT_CASE_DATA = "/PayRouteData.xls";
	public static Integer RequestVo_Data_sheet = 0;
	public static Integer ArrayList_sheet_ALL = 1;
	public static Integer ArrayList_sheet_One = 2;
	

	private String[] menthodAdd = { "set", "1" };//

	// 读取excel页面信息
	// caseFileName 文件名 ; caseSheet sheet页面的index
	public List<String[]> getDataList(String caseFileName, Integer caseSheet) {
		File file = ReadExcelUtil.getFile(caseFileName);
//		System.out.println(caseFileName);
		List<String[]> list = null;
		// 读取测试数据放到string 列表中
		try {
			list = ReadExcelUtil.poireadExcel(file, caseSheet);// poi
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 读取某行信息
	public String[] getCaseData(List<String[]> list, Integer sheetrow) {
		String[] testdatastr = new String[] {};
		testdatastr = list.get(sheetrow);
		return testdatastr;
	}

	// 读取数据信息 代扣




}
