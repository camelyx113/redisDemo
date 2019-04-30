package com.yx.controller;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yx.service.RedisService;

@Controller
@RequestMapping(value="/get")
public class RedisController {
	@Autowired
	private RedisService redisService;

	@RequestMapping(value="/amount")
	public String get(HttpServletRequest req) {
		redisService.subtraction();
		req.setAttribute("resut", "成功");
		return "result";
	}
}
