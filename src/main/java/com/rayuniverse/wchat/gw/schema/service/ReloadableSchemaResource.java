package com.rayuniverse.wchat.gw.schema.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rayuniverse.framework.schedule.ReloadableResource;
import com.rayuniverse.framework.schedule.SchedulePolicy;
import com.rayuniverse.framework.schedule.SchedulePolicy.ScheduleMode;
import com.rayuniverse.framework.schedule.SchedulePolicy.ThreadMode;
import com.rayuniverse.wchat.gw.schema.dao.SchemaDao;
import com.rayuniverse.wchat.gw.schema.domain.Schema;
import com.rayuniverse.wchat.gw.schema.domain.SchemaSet;
@Service
public class ReloadableSchemaResource extends ReloadableResource<SchemaSet>{
	private Logger logger=LoggerFactory.getLogger(ReloadableSchemaResource.class);
	@Autowired
	private SchemaDao schemaDao;
	
	protected SchemaSet loadResource() {
		List<Schema> list=schemaDao.getAllSchema();
		SchemaSet set=new SchemaSet();
		if(list!=null)
		{
			for(Schema s:list)
			{
				try{
					 set.addSchema(s);
				}catch(Throwable e)
				{
					logger.error("", e);
				}
			  
			}
		}
		return set;
	}

	 
	protected String getResourceName() {
	 
		return "ReloadableSchemaResource";
	}

	 
	protected void configSchedulePolicy(SchedulePolicy schedulePolicy) {
		 
		schedulePolicy.setThreadMode(ThreadMode.IndependSingleThread);
		schedulePolicy.setPeroid(500);
		schedulePolicy.setTimeUnit(TimeUnit.MICROSECONDS);
		schedulePolicy.setScheduleMode(ScheduleMode.WaitPeriod);
	}

}
