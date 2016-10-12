package com.rayuniverse.wchat.gw.handers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.wchat.gw.domain.Message;
import com.rayuniverse.wchat.gw.schema.domain.Schema;
import com.rayuniverse.wchat.gw.schema.service.ReloadableSchemaResource;

public class StaticKeyMessageHander {
	
	public static boolean hand(Message message,HttpServletResponse response) throws IOException{
		if(message.getMessageKey()!=null)
		{
			Schema schema=PlatformContext.getBean(ReloadableSchemaResource.class).getResource().getSchema(message.getMessageKey());
			if(schema==null){
				return false ;
			}
			
			HashMap param=new HashMap();
			param.put("ToUserName", message.getMessageMap().get("FromUserName"));
			param.put("FromUserName", message.getMessageMap().get("ToUserName"));
			param.put("CreateTime", System.currentTimeMillis());
			String replayMessage="";
			String type=(String)schema.getMessageMap().get("type");
			if("text".equals(type)){
				param.put("Content", schema.getMessageMap().get("content"));
			    replayMessage=PlatformContext.getVelocityService().mergeTemplateIntoString("ReplyTextMessage.vm", param);
				
			}else if("news".equals(type)){
				param.put("items", schema.getMessageMap().get("items"));
			    replayMessage=PlatformContext.getVelocityService().mergeTemplateIntoString("ReplyPicMessage.vm", param);
			}
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			try{
				out.write(replayMessage);
				out.flush();
			}finally{
				out.close();
			}
			return true ;
		}
		return false ;
	}
	
	
	public static void redirect2Kefu(Message message,HttpServletResponse response) throws IOException
	{
		
		HashMap param=new HashMap();
		param.put("ToUserName", message.getMessageMap().get("FromUserName"));
		param.put("FromUserName", message.getMessageMap().get("ToUserName"));
		param.put("CreateTime", System.currentTimeMillis());
		String replayMessage="";
		 replayMessage=PlatformContext.getVelocityService().mergeTemplateIntoString("ReplyKefuMessage.vm", param);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try{
			out.write(replayMessage);
			out.flush();
		}finally{
			out.close();
		}
		
	}
	
	

}
