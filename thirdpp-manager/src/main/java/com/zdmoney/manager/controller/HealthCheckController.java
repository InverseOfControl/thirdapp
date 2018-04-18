package com.zdmoney.manager.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdmoney.manager.service.HealthCheckService;

@Controller
@Scope("singleton")
@RequestMapping("/healthCheck")
public class HealthCheckController {
	@Autowired
	private HealthCheckService checkService;

	@RequestMapping(value = "/verify")
	@ResponseBody
	public String testConnection() throws IOException {
		try {
			int i = checkService.testConnection();
			return "OK";
		} catch (Exception e) {
			return "FAIL";
		}
	}

}
