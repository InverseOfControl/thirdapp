package com.zendaimoney.thirdpp.alarm.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class FileAccess {

	public static String readAllText(String fileName) throws IOException {
		StringBuffer sb = new StringBuffer();
		char[] buf = new char[1024];
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileName)));
		while (br.read(buf) != -1) {
			sb.append(buf);
		}
		return sb.toString();
	}

	public static String readAllText(String fileName, String encode)
			throws IOException {
		StringBuffer sb = new StringBuffer();
		char[] buf = new char[1024];
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileName), encode));
		while (br.read(buf) != 0) {
			sb.append(buf);
		}
		return sb.toString();
	}

	public static String[] readAllLines(String fileName) throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		String s;
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileName)));
		while ((s = br.readLine()) != null) {
			lines.add(s);
		}
		br.close();
		return lines.toArray(new String[] {});
	}

	public static String[] readAllLines(String fileName, String encode)
			throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		String s;
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileName), encode));
		while ((s = br.readLine()) != null)
			lines.add(s);
		br.close();
		return lines.toArray(new String[] {});
	}

	public static void writeAllText(String fileName, String text)
			throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
		pw.print(text);
		pw.flush();
		pw.close();
	}

	public static void writeAllText(String fileName, String encode, String text)
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(
				new FileOutputStream(fileName), encode));
		pw.print(text);
		pw.flush();
		pw.close();
	}

	public static void writeAllLines(String fileName, String[] lines)
			throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
		for (String s : lines)
			pw.println(s);
		pw.flush();
		pw.close();
	}

	public static void writeAllLines(String fileName, String encode,
			String[] lines) throws FileNotFoundException,
			UnsupportedEncodingException {
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(
				new FileOutputStream(fileName), encode));
		for (String s : lines)
			pw.println(s);
		pw.flush();
		pw.close();
	}

	public static void writeObject(String fileName, Serializable ob)
			throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				fileName));
		oos.writeObject(ob);
		oos.flush();
		oos.close();
	}

	public static Object readObject(String fileName) throws IOException,
			ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				fileName));
		Object ob = ois.readObject();
		ois.close();
		return ob;
	}
}