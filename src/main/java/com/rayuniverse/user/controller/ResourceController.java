package com.rayuniverse.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ResourceController {
	
	/*@RequestMapping("/users/queryUsers")
	public String queryUsers() {
		
		return "users/queryUsers";
	}

	
	@RequestMapping("/users/toAddUser")
	public String toAddUser()
	{
		return "/users/addUser";
	}

	
	@RequestMapping("/users/toModifyUser")
	public String toModifyUser(ModelMap map, HttpServletRequest request)
	{
		map.put("id", request.getParameter("id"));
		return "/users/modifyUser";
	}
	
	
	@RequestMapping("/users/toShowUserInfo")
	public String toShowUserInfo(ModelMap map, HttpServletRequest request)
	{
		map.put("id", request.getParameter("id"));
		return "/users/userInfo";
	}
	*/
}
