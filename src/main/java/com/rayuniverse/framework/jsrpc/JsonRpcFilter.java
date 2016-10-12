package com.rayuniverse.framework.jsrpc;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rayuniverse.framework.Consts;
import com.rayuniverse.framework.PlatformContext;

public class JsonRpcFilter implements Filter {
	
	private Logger logger= LoggerFactory.getLogger(JsonRpcFilter.class);
	
	
	public void destroy() {
		
    }
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		String xrpc=((HttpServletRequest)request).getHeader(Consts.X_RPC);
		String url=((HttpServletRequest)request).getRequestURL().toString();
	    if(Consts.JSON.equals(xrpc))
		{
				PlatformContext.putHttpRequestContext(Consts.REQUEST_TYPE, "JSON_RPC");
				JsonRpcSkeleton.invoke((HttpServletRequest)request, (HttpServletResponse)response);
		}
	    else if(url.indexOf(Consts.JSON_BEAN_STUB)!=-1)
		{
			PlatformContext.putHttpRequestContext(Consts.REQUEST_TYPE, "JSON_GET_STUB");
			
			String[] parts=url.split("/");
			if(Consts.JSON_BEAN_STUB.equals(parts[parts.length-2]))
			{
				JsonRpcSkeleton.getBeanJsonStub(parts[parts.length-1], (HttpServletResponse) response,(HttpServletRequest) request);
			}
			else if(Consts.JSON_FUNCTIOM_STUB.equals(parts[parts.length-2]))
			{
				JsonRpcSkeleton.getFunctionJsonStub(parts[parts.length-1], (HttpServletResponse) response,(HttpServletRequest) request);
			}
			else
			{
				((HttpServletResponse)response).sendError(404);
			}
			
		}
		else
		{
			chain.doFilter(request, response);
		}
	}
	
	public void init(FilterConfig config) throws ServletException {
		
		
	}

}
