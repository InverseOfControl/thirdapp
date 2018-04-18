package com.zendaimoney.thirdpp.alarm.util;

import java.io.File;

public class FileFilter implements java.io.FilenameFilter {

	// 是否是待读文件.
	public boolean isReadFile(String fname) {
		if (fname.toLowerCase().endsWith(".read")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean accept(File dir, String fname) {
		return (isReadFile(fname));

	}
}
