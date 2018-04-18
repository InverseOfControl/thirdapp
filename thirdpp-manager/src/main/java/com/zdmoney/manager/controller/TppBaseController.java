package com.zdmoney.manager.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class TppBaseController {
    
    
    protected void datagridParam(HttpServletRequest request, Map<String,Object> params) throws Exception {
        int pageNumber = StringUtils.isBlank(request.getParameter("page")) ? 1: Integer.parseInt(request.getParameter("page"));
        int pageSize = StringUtils.isBlank(request.getParameter("rows")) ? 10: Integer.parseInt(request.getParameter("rows"));
        int rowBegin = (pageNumber-1) * pageSize + 1;
        int rowEnd = (pageNumber-1) * pageSize + pageSize;
        String sortName = request.getParameter("sort");
        String sortOrder = StringUtils.isBlank(request.getParameter("order")) ? "asc" : request.getParameter("order");
        if (StringUtils.isNotBlank(sortName) && StringUtils.isNotBlank(sortOrder)) {
            params.put("orderStr", " order by " + sortName + " " + sortOrder + " ");
        }
        params.put("rowBegin", rowBegin);
        params.put("rowEnd", rowEnd);
    }

}
