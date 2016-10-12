package com.rayuniverse.login;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rayuniverse.framework.Consts;
import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.framework.comm.ResultObject;
import com.rayuniverse.framework.comm.UmUserIdSecurityToken;
import com.rayuniverse.user.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	UserService userService;
	
	
	@RequestMapping(Consts.PUBLIC_PATH+"/logout")
	public String logout() throws IOException {
		UmUserIdSecurityToken.removeUmUserIdSecurity();
		PlatformContext.getHttpServletRequest().getSession(true).invalidate();
		return "login/home";
	}
	
	@RequestMapping(Consts.PUBLIC_PATH+"/login")
	public String index(ModelMap map) throws IOException {
		if(PlatformContext.getUmUserId()!=null)
		{
			return "login/home";
		}
		String userId=PlatformContext.getHttpServletRequest().getParameter("userName");
		String password=PlatformContext.getHttpServletRequest().getParameter("password");
		String validateCode=PlatformContext.getHttpServletRequest().getParameter("captcha");
	
		map.put("style", "style=\"display:none\"");
		if((userId==null||userId.length()==0)&&(password==null||password.length()==0)&&(validateCode==null||validateCode.length()==0)){
			return "login/index";
		}
		
		if((userId==null||userId.length()==0)||(password==null||password.length()==0))
		{
			map.put("style", "");
			map.put("msg", "用户名密码不能为空");
			return "login/index";
		}
		
//		String key=(String)PlatformContext.getHttpServletRequest().getSession(true).getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
//		if(key!=null)
//		{
//			if((validateCode==null||validateCode.length()==0))
//			{
//				map.put("style", "");
//				map.put("msg", "验证码不能为空");
//				return "login/index";
//			}
//			
//			if(validateCode.equalsIgnoreCase(key)==false)
//			{
//				map.put("style", "");
//				map.put("msg", "验证码不正确");
//				return "login/index";
//			}
//		}
		
		ResultObject ro=userService.login(userId, password);
		if(ro.isState())
		{
			PlatformContext.getHttpServletRequest().getSession(true).setAttribute(Consts.UM_USER_ID, userId);
			PlatformContext.putHttpRequestContext(Consts.UM_USER_ID, PlatformContext.getHttpServletRequest().getSession(true).getAttribute(Consts.UM_USER_ID));
			PlatformContext.putHttpRequestContext(HttpSession.class, PlatformContext.getHttpServletRequest().getSession(true));
		   
			UmUserIdSecurityToken.storeUmUserIdSecurity();
			return "login/home";
		}
		else
		{
			map.put("style", "");
			map.put("msg", "用户名密码不正确");
			return "login/index";
		}
		
	}
	
	

}
