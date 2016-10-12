package com.rayuniverse.wchat.gw.schema.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageController {
	
	@RequestMapping("/message/queryTextMsg")
	public String queryTextMsg() {
		
		return "message/queryTextMsg";
	}

	@RequestMapping("/message/queryNewsMsg")
	public String queryNewsMsg() {
		
		return "message/queryNewsMsg";
	}

	@RequestMapping("/message/toAddTextMsg")
	public String toAddTextMsg()
	{
		return "message/addTextMsg";
	}

	@RequestMapping("/message/toAddNewsMsg")
	public String toAddNewsMsg()
	{
		return "message/addNewsMsg";
	}
	
	@RequestMapping("/message/toModifyTextMsg")
	public String toModifyTextMsg(ModelMap map, HttpServletRequest request)
	{
		map.put("id", request.getParameter("id"));
		return "message/modifyTextMsg";
	}
	
	@RequestMapping("/message/toModifyNewsMsg")
	public String toModifyNewsMsg(ModelMap map, HttpServletRequest request)
	{
		map.put("id", request.getParameter("id"));
		return "message/modifyNewsMsg";
	}
	
}
