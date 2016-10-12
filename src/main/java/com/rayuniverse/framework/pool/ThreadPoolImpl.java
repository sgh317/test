package com.rayuniverse.framework.pool;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rayuniverse.framework.Consts;
import com.rayuniverse.framework.PlatformContext;



public class ThreadPoolImpl implements ThreadPool{

	private static final Logger logger = LoggerFactory.getLogger(ThreadPoolImpl.class);
	LinkedBlockingQueue<Runnable> commandQueue;
	ThreadPoolExecutor executor;
	ThreadPoolMgrImpl threadPoolMgrImpl;
	public String name;
	public ThreadPoolImpl(String name,ThreadPoolMgrImpl threadPoolMgrImpl)
	{
		this.name=name;
		this.threadPoolMgrImpl=threadPoolMgrImpl;
		commandQueue=new LinkedBlockingQueue<Runnable>(Integer.parseInt(PlatformContext.getConfigItem("platform.pool."+name+".queue", "10000")));
		executor=new ThreadPoolExecutor(
				Integer.parseInt(PlatformContext.getConfigItem("platform.pool."+name+".corePoolSize", "2")),
				Integer.parseInt(PlatformContext.getConfigItem("platform.pool."+name+".maxNumPoolSize", "100")),
				Integer.parseInt(PlatformContext.getConfigItem("platform.pool."+name+".keepLiveTime", "5")),
				TimeUnit.SECONDS,commandQueue,new ThreadFactory("POOL["+name+"]"));
		
		logger.info("Create Thread Pool "+name+" queue="+PlatformContext.getConfigItem("platform.pool."+name+".queue", "10000")+" corePoolSize="
				+PlatformContext.getConfigItem("platform.pool."+name+".corePoolSize", "2")+" maxNumPoolSize="
				+PlatformContext.getConfigItem("platform.pool."+name+".maxNumPoolSize", "100")+" keepLiveTime="
				+PlatformContext.getConfigItem("platform.pool."+name+".keepLiveTime", "5")+"SECONDS");
	}
	 
	public Future submit(final Runnable run) {
		
		final HttpServletRequest request=PlatformContext.getHttpServletRequest();
		final HttpServletResponse response=PlatformContext.getHttpServletResponse();
		final String umid=PlatformContext.getUmUserId();
		final HttpSession session=PlatformContext.getHttpSession();
		ContextAwareRunnable r=new ContextAwareRunnable();
		r.setRequest(request);
		r.setResponse(response);
		r.setSession(session);
		r.setUmid(umid);
		r.setTask(run);
		
		return executor.submit(r);
		
		 
	}

	public <T> Future<T> submit(Runnable run, T result) {
	 
		final HttpServletRequest request=PlatformContext.getHttpServletRequest();
		final HttpServletResponse response=PlatformContext.getHttpServletResponse();
		final String umid=PlatformContext.getUmUserId();
		final HttpSession session=PlatformContext.getHttpSession();
		ContextAwareRunnable r=new ContextAwareRunnable();
		r.setRequest(request);
		r.setResponse(response);
		r.setSession(session);
		r.setUmid(umid);
		r.setTask(run);
		
		return executor.submit(r, result);
	}

	 
	public <T> Future<T> submit(Callable<T> run) {
		 
		
		final HttpServletRequest request=PlatformContext.getHttpServletRequest();
		final HttpServletResponse response=PlatformContext.getHttpServletResponse();
		final String umid=PlatformContext.getUmUserId();
		final HttpSession session=PlatformContext.getHttpSession();
		ContextAwareCallable r=new ContextAwareCallable();
		r.setRequest(request);
		r.setResponse(response);
		r.setSession(session);
		r.setUmid(umid);
		r.setTask(run);
		
		return executor.submit(r);
	}

 
//	public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
//		 
//		return executor.invokeAny(tasks);
//	}
//
//	 
//	public <T> T invokeAny(Collection<? extends Callable<T>> tasks,
//			long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
//	 
//		return executor.invokeAny(tasks, timeout, unit);
//	}
// 
//	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
//			throws InterruptedException {
//		 
//		return executor.invokeAll(tasks);
//	}
//
//	 
//	public <T> List<Future<T>> invokeAll(
//			Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
//			throws InterruptedException {
//		
//		return executor.invokeAll(tasks, timeout, unit);
//	}

	 
	public void shutdown() {
		executor.shutdown();
		threadPoolMgrImpl.remove(this);
		
	}

	 
	public List<Runnable> shutdownNow() {
		threadPoolMgrImpl.remove(this);
		return executor.shutdownNow();
		
	}

	public String getName() {
		return name;
		 
	}
	
	

}


class ContextAwareRunnable implements Runnable{
	  final static private Logger logger=LoggerFactory.getLogger(ContextAwareRunnable.class);
	  HttpServletRequest request;
	  HttpServletResponse response;
	  String umid;
	  Runnable task;
	  HttpSession session;
	  
	public HttpServletRequest getRequest() {
		return request;
	}



	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}



	public HttpServletResponse getResponse() {
		return response;
	}



	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}



	public String getUmid() {
		return umid;
	}



	public void setUmid(String umid) {
		this.umid = umid;
	}



	public Runnable getTask() {
		return task;
	}



	public void setTask(Runnable task) {
		this.task = task;
	}



	public HttpSession getSession() {
		return session;
	}



	public void setSession(HttpSession session) {
		this.session = session;
	}



	public void run() {
		

		try{
		
			PlatformContext.putHttpRequestContext(Consts.UM_USER_ID, umid);
			PlatformContext.putHttpRequestContext(HttpSession.class, session);
			PlatformContext.putHttpRequestContext(HttpServletRequest.class, request);
			PlatformContext.putHttpRequestContext(HttpServletResponse.class, response);
			task.run();
		}catch(Throwable e)
		{
			logger.error(e.getMessage(),e);
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
	
}



class ContextAwareCallable implements Callable{
	  final static private Logger logger=LoggerFactory.getLogger(ContextAwareRunnable.class);
	  HttpServletRequest request;
	  HttpServletResponse response;
	  String umid;
	  Callable task;
	  HttpSession session;
	  
	public HttpServletRequest getRequest() {
		return request;
	}



	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}



	public HttpServletResponse getResponse() {
		return response;
	}



	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}



	public String getUmid() {
		return umid;
	}



	public void setUmid(String umid) {
		this.umid = umid;
	}


 


	public Callable getTask() {
		return task;
	}



	public void setTask(Callable task) {
		this.task = task;
	}



	public HttpSession getSession() {
		return session;
	}



	public void setSession(HttpSession session) {
		this.session = session;
	}

	public Object call() throws Exception {
		 
		try{
			 
			PlatformContext.putHttpRequestContext(Consts.UM_USER_ID, umid);
			PlatformContext.putHttpRequestContext(HttpSession.class, session);
			PlatformContext.putHttpRequestContext(HttpServletRequest.class, request);
			PlatformContext.putHttpRequestContext(HttpServletResponse.class, response);
			return task.call();
		}catch(Throwable e)
		{
			logger.error(e.getMessage(),e);
			throw new Exception(e);
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
	
}
