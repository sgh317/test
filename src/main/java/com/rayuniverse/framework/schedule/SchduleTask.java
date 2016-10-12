package com.rayuniverse.framework.schedule;



public interface SchduleTask  extends Runnable{
	    SchedulePolicy getSchedulePolicy();
	    String getTaskName();
 
}
