package com.rayuniverse.framework.schedule;

import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rayuniverse.framework.ds.DataSourceContext;


public abstract class ReloadableResource<T> implements SchduleTask {
	private static  final Logger logger = LoggerFactory.getLogger(ReloadableResource.class);
	protected AtomicReference reference=null;
	SchedulePolicy schedulePolicy=new SchedulePolicy();
	public ReloadableResource()
	{
		schedulePolicy.setThreadPoolName(SchedulePolicy.ReloadableResourcePool);
		configSchedulePolicy(schedulePolicy);
	}
	public AtomicReference getAtomicReference()
	{
		return reference;
	}
	public  T getResource()
	{
		if(reference==null)
		{
			synchronized(this)
			{
				if(reference==null)
				{
					AtomicReference temp=new AtomicReference();
					temp.set(loadResource());
					reference=temp;
				}
			}
		}
		return (T) reference.get();
	}
	void _loadResource()
	{
		try{
			DataSourceContext.setUseAsyncDataSource();
			if(reference==null)
			{
				synchronized(this)
				{
					if(reference==null)
					{
						AtomicReference temp=new AtomicReference();
						temp.set(loadResource());
						reference=temp;
						return ;
					}
				}
			}
			reference.set(loadResource());
			
		}finally
		{
			DataSourceContext.clear();
		}
		
	}
	public void run() {
		 
		try{
			_loadResource();
		}
		catch(Throwable e)
		{
			logger.error("Reload "+getResourceName()+" Error", e);
		}
		
	}
	protected abstract  T loadResource();
	protected abstract  String getResourceName();
	protected abstract  void  configSchedulePolicy(SchedulePolicy schedulePolicy );
	public  String getTaskName()
	{
		return "Reload"+getResourceName()+"Task";
	}
	public  SchedulePolicy getSchedulePolicy()
	{
		 return  schedulePolicy;
	}
	 
	
}
