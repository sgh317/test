package com.rayuniverse.wchat.gw.cfg;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.framework.schedule.ReloadableResource;
import com.rayuniverse.framework.schedule.SchedulePolicy;
import com.rayuniverse.framework.schedule.SchedulePolicy.ScheduleMode;
import com.rayuniverse.framework.schedule.SchedulePolicy.ThreadMode;
import com.rayuniverse.wchat.gw.cfg.dao.ConfigItemDao;
import com.rayuniverse.wchat.gw.util.AccessToken;
import com.rayuniverse.wchat.gw.util.JsApiTicket;
import com.rayuniverse.wchat.gw.util.WeixinUtil;
@Service
public class ReloadableWXConfigResource  extends ReloadableResource<WXConfig>{

	 private static final Logger logger = LoggerFactory.getLogger(ReloadableWXConfigResource.class);
	@Autowired
	protected ConfigItemDao configItemDao;
	
	protected WXConfig loadResource() {
		 

		try{
			return  ((ApplicationContext)PlatformContext.getGoalbalContext(ApplicationContext.class)).getBean(ReloadableWXConfigResource.class).doGetWXConfig();
		}catch(Throwable e)
		{
			logger.error("", e);
			try{
				return  ((ApplicationContext)PlatformContext.getGoalbalContext(ApplicationContext.class)).getBean(ReloadableWXConfigResource.class).doGetWXConfig();
			}catch(Throwable e1)
			{
				logger.error("", e1);
				try{
					return  ((ApplicationContext)PlatformContext.getGoalbalContext(ApplicationContext.class)).getBean(ReloadableWXConfigResource.class).doGetWXConfig();
				}catch(Throwable e2)
				{
					logger.error("", e2);
					return _getLastWXConfig();
				}
			}
		}
		
	
	}

	
	protected WXConfig _getLastWXConfig()
	{
		if(reference!=null)
		{
			WXConfig wxc=(WXConfig) reference.get();
			if(wxc!=null)
			{
				return wxc;
			}else
			{
				return null;
			}
		}
		return null;
	}
	
	 
	protected String getResourceName() {
		 
		return "ReloadableWXConfigResource";
	}

	 
	protected void configSchedulePolicy(SchedulePolicy schedulePolicy) {
		 
		schedulePolicy.setPeroid(1);
		schedulePolicy.setTimeUnit(TimeUnit.SECONDS);
		schedulePolicy.setScheduleMode(ScheduleMode.WaitPeriod);
		schedulePolicy.setThreadMode(ThreadMode.IndependSingleThread);
	}
	

	
	@Transactional
	public WXConfig doGetWXConfig()
	{
		WXConfig wxconfig=configItemDao.selectTokenTicket();
		ConfigItem item=wxconfig.getAccesstoken();
		if(item.isValidate()==false)
		{
			String accesstoken=getAccessToken();
			String jsapiticket=getJsApiTicket(accesstoken);
			configItemDao.updateTokenTicket(accesstoken, jsapiticket);
		    
			wxconfig=configItemDao.selectTokenTicket();
			return wxconfig;
			
		}else
		{
			 return wxconfig;
		}
		
		
	}
	
	private String getAccessToken()
	{
		try{
			return doGetAccessToken();
		}catch(Throwable e)
		{
			
			try{
				Thread.sleep(1000);
				return doGetAccessToken();
			}catch(Throwable e1)
			{
				try{
					Thread.sleep(2000);
					return doGetAccessToken();
				}catch(Throwable e2)
				{
					try{
						Thread.sleep(4000);
						return doGetAccessToken();
					}catch(Throwable e3)
					{
						try{
							Thread.sleep(8000);
							return doGetAccessToken();
						}catch(Throwable e4)
						{
							throw new RuntimeException(e4);
						}
					}
					
				}
			}
		}
	}
	
	private String getJsApiTicket(String token)
	{
		try{
			return doGetJsApiTicket(token);
		}catch(Throwable e)
		{
			
			try{
				Thread.sleep(1000);
				return doGetJsApiTicket(token);
			}catch(Throwable e1)
			{
				try{
					Thread.sleep(2000);
					return doGetJsApiTicket(token);
				}catch(Throwable e2)
				{
					try{
						Thread.sleep(4000);
						return doGetJsApiTicket(token);
					}catch(Throwable e3)
					{
						try{
							Thread.sleep(8000);
							return doGetJsApiTicket(token);
						}catch(Throwable e4)
						{
							throw new RuntimeException(e4);
						}
					}
					
				}
			}
		}
	}
	
	
	private String doGetAccessToken()
	{
		String appId = PlatformContext.getConfigItem("WEIXIN_APPID");
		String appSecret =PlatformContext.getConfigItem("WEIXIN_APPSECRET");
	    AccessToken accessToken = WeixinUtil.getAccessToken2(appId, appSecret);
	    if(accessToken.getToken()!=null)
	    {
	    	return accessToken.getToken();
	    }
	    throw new RuntimeException("Cant not get access token");
	    
	}
	
	private String doGetJsApiTicket(String token)
	{
		JsApiTicket ticket = WeixinUtil.getJsApiTicket2(token);
	    if(ticket.getTicket()!=null){
	    	return ticket.getTicket();
	    }
	    throw new RuntimeException("Cant not get jsapi ticket ");
	}
	
	
	
	



}
