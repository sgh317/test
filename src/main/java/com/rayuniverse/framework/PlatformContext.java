package com.rayuniverse.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;

import com.rayuniverse.framework.comm.ProtectDecisionSpi;
import com.rayuniverse.framework.jsrpc.RpcContext;
import com.rayuniverse.framework.pool.ThreadPoolMgr;
import com.rayuniverse.framework.pool.ThreadPoolMgrImpl;
import com.rayuniverse.framework.schedule.SchduleTask;
import com.rayuniverse.framework.schedule.TaskScheduler;
import com.rayuniverse.framework.volicity.VelocityService;
import com.rayuniverse.framework.volicity.VelocityServiceImpl;

public class PlatformContext {
	
	protected  static ThreadLocal<ConcurrentHashMap> httpThreadContextData=new ThreadLocal();
	protected static ConcurrentHashMap  goalbelContext=new ConcurrentHashMap();
	protected static RpcContext rpcContext=new RpcContext();
	protected static List<Runnable> shutdownHook=new ArrayList();
	protected static TaskScheduler taskScheduler=new TaskScheduler();
	protected static ThreadPoolMgrImpl threadPoolMgr=new ThreadPoolMgrImpl(); 
	protected static ServletContext servletContext;
	protected static ProtectDecisionSpi protectDecision=null;
	protected static VelocityService velocityService=new  VelocityServiceImpl();
	
	public static VelocityService getVelocityService()
	{
		return velocityService;
	}
	public static <T> T getBean(Class<T> t )
	{
		 ApplicationContext ctx=(ApplicationContext) PlatformContext.getGoalbalContext(ApplicationContext.class);
		 return ctx.getBean(t);
		
	}
	
	public static void setServletContext(ServletContext ctx)
	{
		servletContext=ctx;
	}
	public static void setProtectDecision(ProtectDecisionSpi spi)
	{
		protectDecision=spi;
	}
	public static ServletContext getServletContext()
	{
		return servletContext;
	}
	
	public static ThreadPoolMgr getThreadPoolMgr()
	{
		return threadPoolMgr;
	}
	public static TaskScheduler getTaskScheduler()
	{
		return taskScheduler;
	}
	public static  void  releaseHttpRequestThreadContextData()
	{
		httpThreadContextData.remove();
	}
	public static void putGoalbel(Object key,Object v)
	{
		goalbelContext.put(key, v);
	}
	
	public static HttpSession getHttpSession()
	{
		ConcurrentHashMap data=httpThreadContextData.get();
		if(data==null)
		{
			return null;
		}
		
		
		{
			 HttpSession session=  (HttpSession) data.get(HttpSession.class);
			 if(session!=null)
			 {
				 String umid=getUmUserId();
				 if(umid!=null)
				 {
					 if(umid.equals(session.getAttribute(Consts.UM_USER_ID)))
					 {
						 return session;
					 }
				 }
			 }
		}
		
		 
		{
			 HttpServletRequest  req=getHttpServletRequest();
			 if(req!=null)
			 {
				 HttpSession session= req.getSession(false);
				 if(session!=null)
				 {
					 String umid=getUmUserId();
					 if(umid!=null)
					 {
						 if(umid.equals(session.getAttribute(Consts.UM_USER_ID)))
						 {
							 return session;
						 }
					 }
				 }
			 }
		}

		 return null;
	}
	public static HttpServletResponse getHttpServletResponse()
	{
		ConcurrentHashMap data=httpThreadContextData.get();
		if(data==null)
		{
			return null;
		}
		return (HttpServletResponse) data.get(HttpServletResponse.class);
	}
	public static void putGoalbalContext(Object key,Object value)
	{
		goalbelContext.put(key, value);
	}
	public static String getUmUserId()
	{
		ConcurrentHashMap map=httpThreadContextData.get();
		if(map==null)
			return null;
		return (String) map.get(Consts.UM_USER_ID);
	}
	public static Object getGoalbalContext(Object key)
	{
		return goalbelContext.get(key);
	}
	public static HttpServletRequest getHttpServletRequest()
	{
		ConcurrentHashMap data=httpThreadContextData.get();
		if(data==null)
		{
			return null;
		}
		return (HttpServletRequest) data.get(HttpServletRequest.class);
	}
	public  static ReloadableConfigResource getReloadableConfigResource()
	{
		ReloadableConfigResource ds=(ReloadableConfigResource) PlatformContext.getGoalbalContext(ReloadableConfigResource.class);
	    if(ds==null)
	    {
	    	synchronized(ReloadableConfigResource.class)
	    	{
	    		 ds=(ReloadableConfigResource) PlatformContext.getGoalbalContext(ReloadableConfigResource.class);
	    		 if(ds==null)
	    		 {
	    			 ds=new ReloadableConfigResource();
	    			 PlatformContext.putGoalbalContext(ReloadableConfigResource.class, ds);
	    			 ds.getResource();
	    			 PlatformContext.addSchduleTask(ds);
	    		 }
	    	}
	    }
	    return ds;
	}
	public static String getConfigItem(String item)
	{
		Properties prop=getReloadableConfigResource().getResource();
		if(prop!=null)
		{
			return  (String) prop.get(item);
		}
		return null;
	}
	public static String getConfigItem(String item,String defaultValue)
	{
		String v=getConfigItem(item);
		if(v==null)
		{
			return defaultValue;
		}
		return v;
	}
	public static void addShutdownHook(Runnable hook)
	{
		shutdownHook.add(hook);
	}
	
	public static void addSchduleTask(SchduleTask t)
	{
		taskScheduler.addSchduleTask(t);
	}
	public static RpcContext getRpcContext()
	{
		return rpcContext;
	}
	
	public static ConcurrentHashMap getHttpRequestContextMap()
	{
		return httpThreadContextData.get();
	}
	

	public static Object getHttpRequestContext(Object key )
	{
		ConcurrentHashMap map=httpThreadContextData.get();
		if(map==null)
			return null;
		return  map.get(key);
		
	}
	
	public static void putHttpRequestContext(Object key,Object v)
	{
		ConcurrentHashMap map=httpThreadContextData.get();
		if(map==null)
		{
			map=new ConcurrentHashMap();
			httpThreadContextData.set(map);
		}
		map.put(key, v);
	}
	
	public static <T> T getHttpRequestContext(Object key,Class<T> t )
	{
		ConcurrentHashMap map=httpThreadContextData.get();
		if(map==null)
			return null;
		return (T) map.get(key);
		
	}

}
