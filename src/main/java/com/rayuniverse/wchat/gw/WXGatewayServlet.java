package com.rayuniverse.wchat.gw;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rayuniverse.framework.recharge.Recharge;
import com.rayuniverse.wchat.gw.domain.Message;
import com.rayuniverse.wchat.gw.handers.BindServerURLHander;
import com.rayuniverse.wchat.gw.handers.StaticKeyMessageHander;

public class WXGatewayServlet extends  HttpServlet{
	
	private static final Logger logger = LoggerFactory.getLogger(WXGatewayServlet.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2435284676944124669L;

	public void service(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
		 
		Message message=new Message(request);
		String MsgType=(String)message.getMessageMap().get("MsgType");
		if(MsgType==null)
		{
			BindServerURLHander.hand(message,(HttpServletResponse) response);
		}else
		{
			
			boolean b=StaticKeyMessageHander.hand(message, response);
			if(b==false)
			{
				try{
					String content=message.getMessageKey();
					if(content.toLowerCase().startsWith("tel"))
					{
						String tel=content.substring(3);
						Recharge.shf("1", tel);
					}else
					{
						StaticKeyMessageHander.redirect2Kefu(message, response);
					}
				}catch(Throwable e)
				{
					StaticKeyMessageHander.redirect2Kefu(message, response);
				}
				
			}

		}
	}
	
	
	    

}
