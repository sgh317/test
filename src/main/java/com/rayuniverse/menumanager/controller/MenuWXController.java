package com.rayuniverse.menumanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MenuWXController {
	
	@RequestMapping("/menu/defineWXMenus")
	public String toDefineWXMenus(ModelMap map) {

		return "menus/defineWXMenus";
	}
	

	@RequestMapping("/menu/deployWXMenus")
	public String toDeployWXMenus(ModelMap map) {

		return "menus/deployWXMenus";
	}
}
