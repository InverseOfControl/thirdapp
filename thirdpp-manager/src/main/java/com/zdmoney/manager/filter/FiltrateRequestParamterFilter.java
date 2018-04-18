package com.zdmoney.manager.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FiltrateRequestParamterFilter implements Filter {
	private static Pattern SCRIPT_PATTERN = Pattern.compile("<script.*>.*<\\/script\\s*>");
	
	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		Map<String,String[]> paramMap=request.getParameterMap();
		
		String[] paras=null;

		int flag=0;
		String lowStr=null;
		

		Iterator   it=paramMap.keySet().iterator();   
        while   (it.hasNext())   
        {   
        	String key=it.next().toString();
			paras=paramMap.get(key);
			if(paras!=null && paras.length>0) {
				for(int i=0;i<paras.length;i++) {
					lowStr=paras[i].toLowerCase();
					Matcher m = SCRIPT_PATTERN.matcher(lowStr);
					if(m.find()) {
						flag=1;
					}
					
					if(flag == 1 || lowStr.contains(" alert ")) {
						flag=1;
					}
					
					if(flag == 1 || lowStr.contains(" javascript ") || lowStr.contains(" script ") || lowStr.contains(" expression ")) {
						flag=1;
					}
					
					//过滤sql转换函数
					if(flag == 1 || lowStr.contains(" ascii( ") || lowStr.contains(" ascii ( ") || lowStr.contains(" chr( ") || lowStr.contains(" chr ( ") || lowStr.contains(" %0d ") || lowStr.contains(" %0a ") || lowStr.contains(" */ ")) {
						flag=1;
					}
					
					//过滤sql关键字
					if(flag == 1 || lowStr.contains(" alter ") || lowStr.contains(" create ") || lowStr.contains(" truncate ") || lowStr.contains(" drop ") || lowStr.contains(" lock table") || lowStr.contains("insert ") || lowStr.contains(" update ") || lowStr.contains(" delete ") || lowStr.contains(" select ") || lowStr.contains("grant ") || lowStr.contains(" where ") || lowStr.contains(" or ") || lowStr.contains(" document.write ") || lowStr.contains("count ") || lowStr.contains("exec ") || lowStr.contains(" union ") || lowStr.contains("' || '' || '")) {
						flag=1;
					}
					
					//过滤sql语法关键字
					//if(flag==1 || lowStr.contains(" where ") || lowStr.contains(" and ") || lowStr.contains(" or ")) {
					if(flag==1 || lowStr.contains(" where ") || lowStr.contains(" or ")) {
						flag=1;
					}
					
					if(flag==1){
						ServletOutputStream out = null;
						try {
							out = response.getOutputStream();
							out.print("{\"ucCode\":920023,\"ucMessage\":\"isFilter\"}");
							out.flush();
							out.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						return ;
					}
				}
			}
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}


}
