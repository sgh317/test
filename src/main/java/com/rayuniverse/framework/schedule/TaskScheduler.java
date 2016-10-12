package com.rayuniverse.framework.schedule;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.framework.pool.ThreadFactory;
import com.rayuniverse.framework.schedule.SchedulePolicy.ThreadMode;



public class TaskScheduler {
	private static final Logger logger = LoggerFactory.getLogger(TaskScheduler.class);
	
    private ConcurrentHashMap<String,ScheduledExecutorService> schedules=new ConcurrentHashMap<String,ScheduledExecutorService>();
	
	public void addSchduleTask(SchduleTask r) {
		
		      
		       
				if(r.getSchedulePolicy().getPeroid()>0)
				{
					if(r.getSchedulePolicy().getThreadMode()==ThreadMode.IndependSingleThread)
					{
						if(r.getSchedulePolicy().getScheduleMode()==SchedulePolicy.ScheduleMode.FixRate)
						{
							logger.info("Active Schdule Task "+r.getTaskName()+" In Independ Single Scheduled Thread As FixRate Mode" +
									" [Delary:"+r.getSchedulePolicy().getDelay()+",Period:"+r.getSchedulePolicy().getPeroid()+",TimeUnit:"+r.getSchedulePolicy().getTimeUnit().toString()+"]");
							
							ScheduledExecutorService schedule=getIndependSingleScheduledExecutorService(r);
							ScheduleCommand scheduleCommand=new ScheduleCommand(r,schedule) ;
							schedule.scheduleAtFixedRate(scheduleCommand,
									r.getSchedulePolicy().getDelay(), r.getSchedulePolicy().getPeroid(), r.getSchedulePolicy().getTimeUnit());
						}
						else
						{
							logger.info("Active Schdule Task "+r.getTaskName()+" In Independ Single Scheduled Thread  As WaitPeriod Mode "+
									" [Delary:"+r.getSchedulePolicy().getDelay()+",Period:"+r.getSchedulePolicy().getPeroid()+",TimeUnit:"+r.getSchedulePolicy().getTimeUnit().toString()+"]");
							ScheduledExecutorService schedule=getIndependSingleScheduledExecutorService(r);
							
							ScheduleCommand scheduleCommand=new ScheduleCommand(r,schedule);
							schedule.schedule(scheduleCommand,
									r.getSchedulePolicy().getDelay(), r.getSchedulePolicy().getTimeUnit());
						}
					}
					else
					{
						
						if(r.getSchedulePolicy().getScheduleMode()==SchedulePolicy.ScheduleMode.FixRate)
						{
							logger.info("Active Schdule Task "+r.getTaskName()+" In ThreadPool["+r.getSchedulePolicy().getThreadPoolName()+"] As FixRate Mode "+
									" [Delary:"+r.getSchedulePolicy().getDelay()+",Period:"+r.getSchedulePolicy().getPeroid()+",TimeUnit:"+r.getSchedulePolicy().getTimeUnit().toString()+"]");
							
							ScheduledExecutorService schedule=getScheduledExecutorService(r.getSchedulePolicy().getThreadPoolName());
							
							ScheduleCommand scheduleCommand=new ScheduleCommand(r,schedule);
							schedule.
							scheduleAtFixedRate(scheduleCommand,
									r.getSchedulePolicy().getDelay(), r.getSchedulePolicy().getPeroid(), r.getSchedulePolicy().getTimeUnit());
						}
						else
						{
							logger.info("Active Schdule Task "+r.getTaskName()+" In ThreadPool["+r.getSchedulePolicy().getThreadPoolName()+"] As WaitPeriod Mode "+
									" [Delary:"+r.getSchedulePolicy().getDelay()+",Period:"+r.getSchedulePolicy().getPeroid()+",TimeUnit:"+r.getSchedulePolicy().getTimeUnit().toString()+"]");
							
							ScheduledExecutorService schedule=getScheduledExecutorService(r.getSchedulePolicy().getThreadPoolName());
							ScheduleCommand scheduleCommand=new ScheduleCommand(r,schedule);
							schedule.schedule(
									scheduleCommand,
									r.getSchedulePolicy().getDelay(), r.getSchedulePolicy().getTimeUnit());
						}
					}
				}
				else
				{
					ScheduledExecutorService schedule=getScheduledExecutorService(SchedulePolicy.KernelTaskPool);
					
					ScheduleCommand scheduleCommand=new ScheduleCommand(r,schedule);
					
					schedule.schedule(
							scheduleCommand,
							r.getSchedulePolicy().getDelay(), r.getSchedulePolicy().getTimeUnit());
					logger.info("Schedule Task["+r.getTaskName()+"] Schedule Period  Config < 0 Peroid. This Task Will  Be Actived Only One Time In ThreadPool[SchduleTask]."+
							" [Delary:"+r.getSchedulePolicy().getDelay()+",Period:"+r.getSchedulePolicy().getPeroid()+",TimeUnit:"+r.getSchedulePolicy().getTimeUnit().toString()+"]");
				}

		
	}
	
    ScheduledExecutorService getScheduledExecutorService(String poolName)
    {
    	ScheduledExecutorService schedule=schedules.get(poolName);
    	if(schedule==null)
    	{
    		schedule=Executors.newScheduledThreadPool(Integer.parseInt(PlatformContext.getConfigItem("platform.schedule."+poolName, "3"))
				,new ThreadFactory("X-KernelTask-"+poolName)); 
    		
    		
    		logger.info("Start KernelTaskThreadPool "+"X-KernelTask-"+poolName+" , poolSize="+PlatformContext.getConfigItem("platform.schedule."+poolName, "3"));
    		
    		
    		final ScheduledExecutorService  finalSchedule=schedule;
    		PlatformContext.addShutdownHook(new Runnable(){
				public void run() {
					 
					finalSchedule.shutdownNow();
					 
				}
    			
    		});
    		schedules.put(poolName, schedule);
    	}
    	return schedule;
    }
    ScheduledExecutorService getIndependSingleScheduledExecutorService(SchduleTask task)
    {
    	if(task.getTaskName()!=null)
    	{
    		if(schedules.get("X-KernelTask-R-"+task.getTaskName()+"-0")!=null)
    		{
    			int i=0;
    			while(true)
    			{
    				i++;
    				if(schedules.get("X-KernelTask-R-"+task.getTaskName()+"-"+i)==null)
    				{
    					ScheduledExecutorService schedule=Executors.newSingleThreadScheduledExecutor(new ThreadFactory("X-ScheduleTask-R-"+task.getTaskName()+"-"+i));
    					schedules.put("X-ScheduleTask-R-"+task.getTaskName()+"-"+i, schedule);
    					logger.info("Start ScheduleTaskThread "+"X-ScheduleTask-R-"+"X-ScheduleTask-R-"+task.getTaskName()+"-"+i+" In Independ Single Scheduled Thread  Mode ");
    					
    					final ScheduledExecutorService  finalSchedule=schedule;
    		    		PlatformContext.addShutdownHook(new Runnable(){
    						public void run() {
    							 
    							finalSchedule.shutdownNow();
    							
    							
    						}
    		    			
    		    		});
    					
    					return schedule;
    				}
    			}
    		}
    		else
    		{
    			ScheduledExecutorService schedule=Executors.newSingleThreadScheduledExecutor(new ThreadFactory("X-ScheduleTask-R-"+task.getTaskName()+"-0"));
				schedules.put("X-ScheduleTask-R-"+task.getTaskName()+"-0", schedule);
				logger.info("Start ScheduleTaskThread "+"X-ScheduleTask-R-"+task.getTaskName()+"-0"+" In Independ Single Scheduled Thread  Mode ");
				
				final ScheduledExecutorService  finalSchedule=schedule;
	    		PlatformContext.addShutdownHook(new Runnable(){
					public void run() {
						 
						finalSchedule.shutdownNow();
						 
					}
	    			
	    		});
	    		
				return schedule;
    		}
    	}
    	else
    	{
    		int i=0;
			while(true)
			{
				i++;
				if(schedules.get("X-ScheduleTask-R-"+i)==null)
				{
					ScheduledExecutorService schedule=Executors.newSingleThreadScheduledExecutor(new ThreadFactory("X-ScheduleTask-R-"+i));
					schedules.put("X-ScheduleTask-R-"+i, schedule);
					logger.info("Start ScheduleTaskThread "+"X-ScheduleTask-R-"+i+" In Independ Single Scheduled Thread  Mode ");
					
					final ScheduledExecutorService  finalSchedule=schedule;
		    		PlatformContext.addShutdownHook(new Runnable(){
						public void run() {
							 
							finalSchedule.shutdownNow();
							
						}
		    			
		    		});
					return schedule;
				}
			}
    	}
    	
    }
    
   
}
