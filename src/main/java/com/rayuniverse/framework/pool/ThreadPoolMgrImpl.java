package com.rayuniverse.framework.pool;

import java.util.concurrent.ConcurrentHashMap;

import com.rayuniverse.framework.PlatformContext;


public class ThreadPoolMgrImpl implements  ThreadPoolMgr {

	ConcurrentHashMap<String,ThreadPoolImpl> pools=new ConcurrentHashMap<String,ThreadPoolImpl>();
	
	public ThreadPoolMgrImpl()
	{
		PlatformContext.addShutdownHook(new Runnable(){
			public void run() {
				for(ThreadPoolImpl p:pools.values())
				{
					p.shutdownNow();
				}
			}
		});
	}
	 
	public ThreadPool getThreadPool(String name) {
		 if(name==null)
			 throw new RuntimeException("Pool Name Is Null");
		 ThreadPoolImpl pool=pools.get(name);
		 if(pool==null)
		 {
			 synchronized(pools)
			 {
				 pool=pools.get(name);
				 if(pool==null)
				 {
					 pool=new ThreadPoolImpl(name,this);
					 pools.put(name, pool);
				 }
			 }
		 }
		 return pool;
	}
	
	public void remove(ThreadPoolImpl pool)
	{
	    synchronized(pools)
		{
	    	ThreadPoolImpl tp=(ThreadPoolImpl) pools.get(pool.getName());
	    	if(tp==pool)
	    	{
	    		pools.remove(pool.getName());
	    	}
		}
	}
	
}
