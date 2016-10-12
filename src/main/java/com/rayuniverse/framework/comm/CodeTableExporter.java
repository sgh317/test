package com.rayuniverse.framework.comm;

import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.framework.schedule.CodeTable;
import com.rayuniverse.framework.schedule.CodeTableResource;

public class CodeTableExporter {
   public void export()
   {
	
		Map<String,CodeTableResource> contextBeans=((ApplicationContext)PlatformContext.getGoalbalContext(ApplicationContext.class)).getBeansOfType(CodeTableResource.class);
		if(contextBeans!=null)
		{
			for(CodeTableResource rc:contextBeans.values())
			{
				CodeTable codeTable=(CodeTable)PlatformContext.getServletContext().getAttribute("CodeTable");
				if(codeTable==null)
				{
				    codeTable=new CodeTable();
				    PlatformContext.getServletContext().setAttribute("CodeTable", codeTable);
				}
				codeTable.put(rc.getResourceName(), rc);
			}
		}

   }
}
