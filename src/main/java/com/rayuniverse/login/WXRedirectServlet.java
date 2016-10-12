package com.rayuniverse.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.rayuniverse.customer.dao.CustomerDao;
import com.rayuniverse.domain.CustomerDetailDomain;
import com.rayuniverse.framework.Consts;
import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.framework.comm.UmUserIdSecurityToken;
import com.rayuniverse.framework.comm.Util;
import com.rayuniverse.util.JSDDConfig;

public class WXRedirectServlet extends  HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1328522066725333909L;
	private static final Logger logger = LoggerFactory.getLogger(WXRedirectServlet.class);
	String access_token_url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	public void service(ServletRequest request, ServletResponse response)throws IOException, ServletException
  	{
		String code=request.getParameter("code");
		if(code!=null)
		{
			JSONObject oauth2 = oauth2(code);
			String openId=oauth2.getString("openid");
			String accessToken=oauth2.getString("access_token");
			if(openId!=null)
			{
				 
				 PlatformContext.getHttpServletRequest().getSession(true).setAttribute(Consts.UM_USER_ID, openId);
				 PlatformContext.putHttpRequestContext(Consts.UM_USER_ID, PlatformContext.getHttpServletRequest().getSession(true).getAttribute(Consts.UM_USER_ID));
				 PlatformContext.putHttpRequestContext(HttpSession.class, PlatformContext.getHttpServletRequest().getSession(true));
				 UmUserIdSecurityToken.storeUmUserIdSecurity();
				
				 Cookie  loginCookie=new Cookie("_WXUSTK", UmUserIdSecurityToken.toSecurity(openId));
				 loginCookie.setValue(openId);
				 loginCookie.setPath("/");
				 loginCookie.setMaxAge(315360000);
				 ((HttpServletResponse)response).addCookie(loginCookie);
				 
				 Cookie  loginCookieSign=new Cookie("_ACCESS_TOKEN", UmUserIdSecurityToken.toSecurity(accessToken));
				 loginCookieSign.setPath("/");
				 loginCookieSign.setMaxAge(315360000);
				 ((HttpServletResponse)response).addCookie(loginCookieSign);
				 
				 Cookie[] cookies=((HttpServletRequest)request).getCookies();
				 if(cookies!=null)
				 {
					 for(Cookie c:cookies)
					 {
						 if(c.getName().equals("_WXUSTK"))
						 {
							 String state=request.getParameter("state");
							 
							 ApplicationContext ctx=(ApplicationContext) PlatformContext.getGoalbalContext(ApplicationContext.class);
							 CustomerDao customerDao=ctx.getBean(CustomerDao.class);
							 CustomerDetailDomain customerInfo = customerDao.queryClientInfoByOpenId(openId);
							 if(customerInfo == null){
								 
								if(StringUtils.equals("integralQuery", state)){//积分查询
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/JS/index.html");
								 }else if(StringUtils.equals("policyQuery", state)){//保单查询
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/PersonalPolicy/policyList.html");
								 }else if(StringUtils.equals("jfInd", state)){//保单查询
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/JF/index.html");
								 }else if(StringUtils.equals("jfRule", state)){//保单查询
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/JF/rule.html");
								 }else if(StringUtils.equals("scoreMall", state)){//积分商城
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/score/scoreMall.html");
								 }else if(StringUtils.equals("welcome", state)){//积分商城
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/Welcome/welcome.html");
								 }else if(StringUtils.equals("delbind", state)){//积分商城
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/card/card_del_bind.html");
								 }else{
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/wgqc/html/index.html"); 
								 }
								 
								 return;
							 }
							 else{
								 if(StringUtils.equals("bind", state)){//绑定
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/card/card_package.html");
								 }else if(StringUtils.equals("integralQuery", state)){//积分查询
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/JS/index.html");
								 }else if(StringUtils.equals("apply", state)){//投保
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/card/card_insur.html");
								 }else if(StringUtils.equals("apply1", state)){//投保
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/card/card_package.html");
									 //((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/card/card_info.html");
								 }else if(StringUtils.equals("apply2", state)){//投保
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/card/card_customer.html");
								 }else if(StringUtils.equals("zs", state)){//投保
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/ZS/index.html");
								 }else if(StringUtils.equals("policyQuery", state)){//保单查询
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/PersonalPolicy/policyList.html");
								 }else if(StringUtils.equals("jfInd", state)){//保单查询
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/JF/index.html");
								 }else if(StringUtils.equals("jfRule", state)){//保单查询
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/JF/rule.html");
								 }else if(StringUtils.equals("scoreMall", state)){//积分商城
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/score/scoreMall.html");
								 }else if(StringUtils.equals("hyx_zs", state)){//积分商城
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/card/card_hyx.html");
								 }else if(StringUtils.equals("welcome", state)){//积分商城
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/Welcome/welcome.html");
								 }else if(StringUtils.equals("delbind", state)){//积分商城
									 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/card/card_del_bind.html");
								 }
								 return ; 
							 }
						 }
					 }
				 }
				 
				 
				 String state=request.getParameter("state");
				 if(StringUtils.equals("bind", state)){//绑定
					 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/BIND/index.html");
				 }else if(StringUtils.equals("integralQuery", state)){//积分查询
					 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/JS/index.html");
				 }else if(StringUtils.equals("apply", state)){//投保
					 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/card/card_insur.html");
				 }else if(StringUtils.equals("apply1", state)){//投保
					 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/card/card_package.html");
				 }else if(StringUtils.equals("apply2", state)){//投保
					 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/card/card_customer.html");
				 }else if(StringUtils.equals("zs", state)){//投保
					 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/ZS/index.html");
				 }else if(StringUtils.equals("policyQuery", state)){//保单查询
					 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/PersonalPolicy/policyList.html");
				 }else if(StringUtils.equals("jfInd", state)){//保单查询
					 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/JF/index.html");
				 }else if(StringUtils.equals("jfRule", state)){//保单查询
					 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/JF/rule.html");
				 }else if(StringUtils.equals("scoreMall", state)){//积分商城
					 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/score/scoreMall.html");
				 }else if(StringUtils.equals("hyx_zs", state)){//积分商城
					 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/card/card_hyx.html");
				 }else if(StringUtils.equals("welcome", state)){//积分商城
					 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/Welcome/welcome.html");
				 }else if(StringUtils.equals("delbind", state)){//积分商城
					 ((HttpServletResponse)response).sendRedirect(JSDDConfig.HTML_BASE_URL+"/PUBLIC/card/card_del_bind.html");
				 }
				 return ;
			}
//			
		}
  	}
	
	public   JSONObject oauth2(String code) {
		 
		String requestUrl = access_token_url.replace("CODE", code);
		requestUrl= requestUrl.replace("APPID", PlatformContext.getConfigItem("WEIXIN_APPID"));
		requestUrl= requestUrl.replace("SECRET", PlatformContext.getConfigItem("WEIXIN_APPSECRET"));
		JSONObject jsonObject = Util.httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
			return jsonObject;
			} catch (JSONException e) {
				 
				throw new RuntimeException(e);
			}
		}
		return null;
	}
}
