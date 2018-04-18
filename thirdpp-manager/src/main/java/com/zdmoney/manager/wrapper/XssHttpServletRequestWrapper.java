package com.zdmoney.manager.wrapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {    
    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {  
        super(servletRequest);  
    }  
    public String[] getParameterValues(String parameter) {  
      String[] values = super.getParameterValues(parameter);  
      if (values==null)  {  
                  return null;  
          }  
      int count = values.length;  
      String[] encodedValues = new String[count];  
      for (int i = 0; i < count; i++) {  
                 encodedValues[i] = cleanXSS(values[i]);  
       }  
      return encodedValues;  
    }  
    public String getParameter(String parameter) {  
          String value = super.getParameter(parameter);  
          if (value == null) {  
                 return null;  
                  }  
          return cleanXSS(value);  
    }  
    public String getHeader(String name) {  
        String value = super.getHeader(name);  
        if (value == null)  
            return null;  
        return cleanXSS(value);  
    }  
    private String cleanXSS(String value) {  
        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");  
        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");  
        value = value.replaceAll("%", "");  
       
        value = value.replaceAll("'", "& #39;");  
        value = value.replaceAll("eval\\((.*)\\)", "");  
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");  
        value = value.replaceAll("script", "");  
        String regex = "(^(%|_).*$)|(^.*(%|_)$)";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(value.trim());
        while (match.matches()) {
        	value = value.replaceAll("^%*", "").replaceAll("^_*", "").replaceAll("%*$", "").replaceAll("_*$", "");
            match = pattern.matcher(value.trim());
        }
        return value;  
    }  
    
}
