package com.rayuniverse.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GroupController {
	
	@RequestMapping("/group/queryGroups")
	public String queryGroups() {
		
		return "group/queryGroups";
	}
	
	@RequestMapping("/group/toAddGroup")
	public String toAddGroup()
	{
		return "group/addGroup";
	}
	
	@RequestMapping("/group/toModifyGroup")
	public String toModifyGroup(ModelMap map, HttpServletRequest request)
	{
		map.put("id", request.getParameter("id"));
		return "group/modifyGroup";
	}
	
	@RequestMapping("/group/toShowGroupInfo")
	public String toShowGroupInfo(ModelMap map, HttpServletRequest request)
	{
		map.put("id", request.getParameter("id"));
		return "group/groupInfo";
	}
	
	@RequestMapping("/group/toAssignedResource")
	public String toAssignedResource(ModelMap map, HttpServletRequest request)
	{
		map.put("id", request.getParameter("id"));
		return "group/assignedResource";
	}
}
