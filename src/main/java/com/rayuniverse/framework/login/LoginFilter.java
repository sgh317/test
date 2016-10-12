package com.rayuniverse.framework.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rayuniverse.framework.Consts;
import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.framework.comm.UmUserIdSecurityToken;

public class LoginFilter implements Filter {
	

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		 String pathInfo=((HttpServletRequest)request).getServletPath();
		 initUserContext(  request,   response);
		 if(pathInfo!=null&&((pathInfo.startsWith(Consts.PUBLIC_PATH)||pathInfo.startsWith("/CacheableResource"))))
		 {
			   chain.doFilter(request, response);
			   return;
		 }
		 else{
			 if(PlatformContext.getUmUserId()==null){
				 HttpServletResponse rs=(HttpServletResponse)response;
				 String xrpc=((HttpServletRequest)request).getHeader(Consts.X_RPC);
				 if(Consts.JSON.equals(xrpc)){
					    response.setCharacterEncoding("utf-8");
						response.setContentType("text/javascript");
						response.getWriter().write(Consts.SESSION_INVALIDATE);
					 
				 }else
				 {
					    String configContext=PlatformContext.getConfigItem("Context");
						if(configContext==null)
						{
							 rs.sendRedirect(PlatformContext.getServletContext().getContextPath()+Consts.LOGIN_URL);
						}else
						{
							 rs.sendRedirect(configContext+Consts.LOGIN_URL);
						}
					
				 }
			
				 
			 }else
			 {
				 chain.doFilter(request, response);
			 }
			 
		 }
		 

		 
	}
	
	private void initUserContext(ServletRequest request, ServletResponse response){
		HttpServletRequest hreq=(HttpServletRequest) request;
		HttpSession session = ((HttpServletRequest) request)
				.getSession(true);
	
		if (session!=null&&session.getAttribute(Consts.UM_USER_ID) != null) {
			PlatformContext.putHttpRequestContext(Consts.UM_USER_ID, session.getAttribute(Consts.UM_USER_ID));
			PlatformContext.putHttpRequestContext(HttpSession.class, session);
			
			HttpServletRequest wapperedRequest = new com.rayuniverse.framework.login.HttpServletRequest(
					(HttpServletRequest) request);
		   request=wapperedRequest;
		   PlatformContext.putHttpRequestContext(HttpServletRequest.class, request);
		   
		}
		else {
			
			String userId=UmUserIdSecurityToken.parseUser(hreq);
			if(userId!=null)
			{
				session.setAttribute(Consts.UM_USER_ID, userId);

				PlatformContext.putHttpRequestContext(Consts.UM_USER_ID, session.getAttribute(Consts.UM_USER_ID));
				PlatformContext.putHttpRequestContext(HttpSession.class, session);
				
				HttpServletRequest wapperedRequest = new com.rayuniverse.framework.login.HttpServletRequest(
						(HttpServletRequest) request);
			   request=wapperedRequest;
			   PlatformContext.putHttpRequestContext(HttpServletRequest.class, request);
			   
			
			}
			
		}
		
		 
		
	}

	 
	public void destroy() {
		 
		
	}

	 
	public void init(FilterConfig arg0) throws ServletException {
		 
		
	}

}
