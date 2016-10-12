package com.rayuniverse.framework;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.CacheManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rayuniverse.framework.comm.Util;

/**
 * 
 * @author liuhaidong
 * 
 * 
 */
public class PlatformFilter implements Filter{

	private Logger logger=LoggerFactory.getLogger(PlatformFilter.class);
	
	
	
    
 
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		try{
			HttpServletResponse resp=(HttpServletResponse) response;
			resp.addHeader("P3P", "CP=CAO PSA OUR");
		
			
			String pathInfo=((HttpServletRequest)request).getServletPath();
			if(pathInfo.endsWith("appcache"))
			{
				response.setContentType("text/cache-manifest manifest");
			}
			
			initRequestContext(  request,   response,filterChain);
			
			filterChain.doFilter(request, response);
			
		}catch(Throwable e)
		{
			logger.error("http request  err", e);
			String err=Util.print(e);
			err.replaceAll("\r\n", "<br/>");
			request.setAttribute("ERROR",err);
			throw new ServletException(e);
		}
		finally
		{
		
			try{
				PlatformContext.releaseHttpRequestThreadContextData();
				
			}catch(Throwable e)
			{
				logger.error("releaseHttpRequestThreadContextData", e);
			}

			
		}
		
	}

	void initRequestContext(ServletRequest request, ServletResponse response,
			FilterChain chain)
	{
		 HttpServletRequest httpRequest=(HttpServletRequest)request;
		 String clientIp=httpRequest.getHeader("X-Forwarded-For");
		 if(clientIp==null)
		 {
			 clientIp=request.getRemoteAddr();
		 }
	     String serverIp=request.getLocalAddr()+":"+request.getServerPort();
	     PlatformContext.putHttpRequestContext(Consts.SERVER_IP,serverIp );
		 PlatformContext.putHttpRequestContext(Consts.CLIENT_IP, clientIp);
			
		 PlatformContext.putHttpRequestContext(FilterChain.class, chain);
		 PlatformContext.putHttpRequestContext(HttpServletRequest.class, request);
		 PlatformContext.putHttpRequestContext(HttpServletResponse.class, response);

		  
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {

		
		
	
		
		PlatformContext.addShutdownHook(new Runnable(){
			public void run() {
				 CacheManager.getInstance().removalAll();
				 CacheManager.getInstance().shutdown();
			}
		});
		
		
		 
		PlatformContext.setServletContext(filterConfig.getServletContext());
		
		String configContext=PlatformContext.getConfigItem("Context");
		if(configContext==null)
		{
			if(filterConfig.getServletContext().getContextPath().equals("/"))
			{ 
				filterConfig.getServletContext().setAttribute("_ContextPath","");
				
			}else if(filterConfig.getServletContext().getContextPath().endsWith("/"))
			{
				filterConfig.getServletContext().setAttribute("_ContextPath",filterConfig.getServletContext().getContextPath().substring(0,filterConfig.getServletContext().getContextPath().length()-1));	   
			}
			else
			{
				filterConfig.getServletContext().setAttribute("_ContextPath",filterConfig.getServletContext().getContextPath());
			}
			
		}
		else
		{
			filterConfig.getServletContext().setAttribute("_ContextPath",configContext);
		}
		
		
	
	}
	
	
	
    public void destroy() {
    	
	}
	
}
