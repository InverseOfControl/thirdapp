package com.zendaimoney.thirdpp.alarm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ZipFileUtil {

	public static Log logger = LogFactory.getLog(ZipFileUtil.class);

	public static byte[] zipFile(String str) throws Exception {
		if (str == null) {
			return null;
		}
		// 读入缓冲区
		byte[] buf = str.getBytes();
		if (buf.length == 0) {
			return buf;
		}
		byte[] result = null;
		try {
			// 建立压缩字节输出流
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			// 建立zlib压缩输出流
			DeflaterOutputStream zlibOut = new DeflaterOutputStream(outStream);
			zlibOut.write(buf, 0, buf.length);
			zlibOut.close();
			result = outStream.toByteArray();
			outStream.close();
			// 获取压缩后的字节
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return result;
	}

	public static byte[] unzipFile(byte[] bytes) throws Exception {
		if (bytes == null || bytes.length == 0) {
			return bytes;
		}
		byte[] buf = new byte[1024];
		try {
			// 建立解压缩字节输入流
			ByteArrayInputStream inStream = new ByteArrayInputStream(bytes);
			// 建立zlib解压缩输入流
			InflaterInputStream zlib = new InflaterInputStream(inStream);
			// 建立解压字节输出流
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			int num;
			while ((num = zlib.read(buf, 0, buf.length)) != -1) {
				outStream.write(buf, 0, num);
			}
			zlib.close();
			inStream.close();
			buf = outStream.toByteArray();
			outStream.close();
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return buf;
	}

	public static String unzip(byte[] value) {
		String result = null;
		if (value != null && value.length > 0) {
			try {
				result = new String(unzipFile(value), "GBK");
			} catch (Exception e) {
				logger.error(e);
				result = null;
			}
		}
		return result;
	}

	public static String unzipBody(byte[] value) {
		String result = null;
		if (value != null && value.length > 0) {
			try {
				result = new String(unzipFile(value), "GBK");
			} catch (Exception e) {
				logger.error("解压body信息异常",e);
				result = null;
			}
		}
		return result;
	}

	public static String unzipDetail(byte[] value) {
		String result = null;
		if (value != null && value.length > 0) {
			try {
				result = new String(unzipFile(value), "GBK");
				result = result.replaceAll("gType", "infoType")
						.replaceAll("uType", "custType")
						.replaceAll("gid", "info")
						.replaceAll("cType", "custType")
						.replaceAll("cMarkType", "infoType")
						.replaceAll("markInfo", "info");
			} catch (Exception e) {
				logger.error("解压detail信息异常",e);
				result = null;
			}
		}
		return result;
	}
}
