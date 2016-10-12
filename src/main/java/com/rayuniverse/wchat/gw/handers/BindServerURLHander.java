package com.rayuniverse.wchat.gw.handers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.wchat.gw.domain.Message;

public class BindServerURLHander {
	 private static final Logger logger = LoggerFactory.getLogger(BindServerURLHander.class);
	public static void hand(Message upMessage,HttpServletResponse response)
	{

        try{
        	if(isWeixinReq(upMessage)){
        		response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				out.write((String)upMessage.getMessageMap().get("echostr"));
				out.close();
				logger.info("微信公共号绑定服务器成功");
				 
        	}
        	
        }catch (Throwable e){
        	logger.error("错误请求",e);
        }
	
	}
	
	  private static  boolean isWeixinReq(Message upMessage) throws IOException{
			String token = PlatformContext.getConfigItem("WEIXIN_TOKEN");
			
	    	String[] params = {(String)upMessage.getMessageMap().get("nonce"),(String)upMessage.getMessageMap().get("timestamp"),token};
	    	Arrays.sort(params);
	    	String shaStr = EncoderHandler.encode("SHA1", params[0]+params[1]+params[2]);
	    	if(shaStr.equals((String)upMessage.getMessageMap().get("signature"))){
	    		return true;
	    	}
	    	return false;
	    }

}
