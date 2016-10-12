package com.rayuniverse.framework;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rayuniverse.framework.schedule.SchedulePolicy;
import com.rayuniverse.framework.schedule.SchedulePolicy.ScheduleMode;
import com.rayuniverse.framework.schedule.SchedulePolicy.ThreadMode;



public class ReloadableConfigResource  extends com.rayuniverse.framework.schedule.ReloadableResource<Properties>{
    private Logger logger=LoggerFactory.getLogger(ReloadableConfigResource.class);
 
    public ReloadableConfigResource()
    {
    	{
    		URL uri=ReloadableConfigResource.class.getClassLoader().getResource("OsEnvCommCfg.properties");
        	if(uri!=null)
        	{
        		logger.info("OsEnvCommCfg.properties  Path:"+uri.toString());
        	}
    	}
    	
    	{
    		URL uri=ReloadableConfigResource.class.getClassLoader().getResource("cfg.properties");
        	if(uri!=null)
        	{
        		logger.info("cfg.properties  Path:"+uri.toString());
        	}
    	}
    	
    	
    }
	protected Properties loadResource() {
		
		Properties prop=new Properties();
	 

		 
		
		{

			InputStream in=ReloadableConfigResource.class.getClassLoader().getResourceAsStream("OsEnvCommCfg.properties");
			try{
				if(in!=null)
				{
					try {
						
					
						
						prop.load(in);
						
					
					} catch (IOException e) {
						
						logger.error("load OsEnvCommCfg.properties error", e);
						
					}
				}
				else
				{
					
				}
			}finally
			{
				if(in!=null)
				{
					try {
						in.close();
					} catch (IOException e) {
						logger.error("close OsEnvCommCfg.properties error", e);
					}
				}
			}
		
		}
		
		{
			InputStream in=ReloadableConfigResource.class.getClassLoader().getResourceAsStream("cfg.properties");
			try{
				if(in!=null)
				{
					try {
						
						
						
						prop.load(in);
						
						
					} catch (IOException e) {
						
						logger.error("load cfg.properties error", e);
						
					}
				}
				else
				{
					
				}
			}finally
			{
				if(in!=null)
				{
					try {
						in.close();
					} catch (IOException e) {
						logger.error("close cfg.properties error", e);
					}
				}
			}
		}
		
	   
	   
	   return prop;
	}

	 
	protected String getResourceName() {
		 
		return "ReloadableConfigResource";
	}

	 
	protected void configSchedulePolicy(SchedulePolicy schedulePolicy) {
		schedulePolicy.setScheduleMode(ScheduleMode.WaitPeriod);
		schedulePolicy.setThreadPoolName(getResourceName());
		schedulePolicy.setThreadMode(ThreadMode.IndependSingleThread);
	}

}
