package com.rayuniverse.framework.login;

import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequestWrapper;

import com.rayuniverse.framework.PlatformContext;



public class HttpServletRequest extends HttpServletRequestWrapper {

    static com.rayuniverse.framework.login.Principal principal=new com.rayuniverse.framework.login.Principal();
    
    HashMap extParamters=null;
    
    javax.servlet.http.HttpServletRequest httpRequest;

    public HttpServletRequest(javax.servlet.http.HttpServletRequest req)
	{
	    super(req);
	    this.httpRequest=req; 
	}
    public String getRemoteHost()
    {
    	 if(httpRequest==null)
    	 {
    		 return null;
    	 }
    	 
   	     String clientIp=httpRequest.getHeader("X-Forwarded-For");
		 if(clientIp==null)
		 {
			 clientIp=httpRequest.getRemoteAddr();
		 }
		 return clientIp;
    }
    public String getRemoteAddr()
    {
    	 if(httpRequest==null)
   	     {
   		   return null;
   	     }
    	 String clientIp=httpRequest.getHeader("X-Forwarded-For");
		 if(clientIp==null)
		 {
			 clientIp=httpRequest.getRemoteAddr();
		 }
		 return clientIp;
    }
    
	public String getRemoteUser() {
		
		return  PlatformContext.getUmUserId(); 

	}
	public Principal getUserPrincipal() { 
	    return principal;
	}
	
	public Map getParameterMap()
	{
		if(extParamters==null)
		{
			extParamters=new HashMap();
			extParamters.putAll(super.getParameterMap());
		}
		return extParamters;
	}
	public String getParameter(String name)
	{
		if(extParamters==null)
		{
			return super.getParameter(name);
		}
		else
		{
			Object v=extParamters.get(name);
			if(v!=null)
			{
				if(v instanceof String)
				{
					return (String)v;
				}
				else if(v instanceof String[])
				{
					String[] v1=(String[]) v;
					if(v1.length>0)
						return v1[0];
					return null;
				}
				else
				{
					throw new RuntimeException("framework error");
				}
			}
			else
			{
				return super.getParameter(name); 
			}
			
		}
		 
	}
	public Enumeration getParameterNames()
	{
		if(extParamters==null)
		{
			return super.getParameterNames();
		}
		else
		{
			return  new IteratorEnumeration(extParamters.keySet().iterator());
		}
		
	}
	public String[] getParameterValues(String name)
	{
		if(extParamters==null)
		{
			return super.getParameterValues(name);
		}
		else
		{
			Object obj=extParamters.get(name);
			if(obj==null)
			{
				return super.getParameterValues(name);
			}
			else
			{
				if(obj instanceof String)
				{
					return new String[]{(String) obj};
				}
				else if(obj instanceof String[])
				{
					return (String[])obj;
				}
				else
				{
					throw new RuntimeException("sf framework error.");
				}
			}
		}
	}
	
    
	  

}
class IteratorEnumeration implements Enumeration
{
	Iterator it;
	
	IteratorEnumeration(Iterator it)
	{
		this.it=it;
	}
	public boolean hasMoreElements() {
		 
		return it.hasNext();
	}

	 
	public Object nextElement() {
		 
		return it.next();
	}
	
}


