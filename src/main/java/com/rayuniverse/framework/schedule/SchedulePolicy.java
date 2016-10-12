package com.rayuniverse.framework.schedule;

import java.util.concurrent.TimeUnit;

public class SchedulePolicy {
	public static final String KernelTaskPool="KernelTask";
	public static final String ReloadableResourcePool="ReloadableResource";
	
	public enum ScheduleMode{FixRate,WaitPeriod};
	public enum ThreadMode{IndependSingleThread,ThreadPool};
	private TimeUnit timeUnit=TimeUnit.SECONDS;
	private long peroid=15;
	private long delay=0;
	private ScheduleMode scheduleMode=ScheduleMode.WaitPeriod;
	private ThreadMode threadMode=ThreadMode.ThreadPool;
	private String threadPoolName=KernelTaskPool;
	private int monitorStatusEventCacheSize=25;
	
 
	
	
 
	public int getMonitorStatusEventCacheSize() {
		return monitorStatusEventCacheSize;
	}
	public void setMonitorStatusEventCacheSize(int monitorStatusEventCacheSize) {
		this.monitorStatusEventCacheSize = monitorStatusEventCacheSize;
	}
	public TimeUnit getTimeUnit() {
		return timeUnit;
	}
	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}
	public long getPeroid() {
		return peroid;
	}
	public void setPeroid(long peroid) {
		this.peroid = peroid;
	}
	
	public long getDelay() {
		return delay;
	}
	public void setDelay(long delay) {
		this.delay = delay;
	}
	public ScheduleMode getScheduleMode() {
		return scheduleMode;
	}
	public void setScheduleMode(ScheduleMode scheduleMode) {
		this.scheduleMode = scheduleMode;
	}
	public ThreadMode getThreadMode() {
		return threadMode;
	}
	public void setThreadMode(ThreadMode threadMode) {
		this.threadMode = threadMode;
	}
	public String getThreadPoolName() {
		return threadPoolName;
	}
	public void setThreadPoolName(String threadPoolName) {
		this.threadPoolName = threadPoolName;
	}
	
}
