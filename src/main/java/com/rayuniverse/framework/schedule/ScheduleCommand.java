package com.rayuniverse.framework.schedule;

import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleCommand implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(ScheduleCommand.class);
	SchduleTask task;
	ScheduledExecutorService schedule;
	public ScheduleCommand(SchduleTask task,ScheduledExecutorService schedule)
	{
		this.task=task;
		this.schedule=schedule;
	}
	 
	public void run() {
		
		try{
			task.run();
		}
		catch(Throwable e)
		{
			
			logger.error("Schedule "+task.getTaskName()+" Error", e);
		}finally
		{
			
			if((task.getSchedulePolicy().getScheduleMode()==SchedulePolicy.ScheduleMode.WaitPeriod)&&task.getSchedulePolicy().getPeroid()>0)
			{
			    schedule.schedule(this,task.getSchedulePolicy().getPeroid(), task.getSchedulePolicy().getTimeUnit());
			}
			 
		}
	}
	
}
