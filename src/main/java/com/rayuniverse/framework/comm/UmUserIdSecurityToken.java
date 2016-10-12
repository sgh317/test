package com.rayuniverse.framework.comm;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rayuniverse.framework.Consts;
import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.framework.des.DESCode;
import com.rayuniverse.framework.hex.Hex;

public class UmUserIdSecurityToken {
	static private Logger logger=LoggerFactory.getLogger(UmUserIdSecurityToken.class);
	private  final  static String DES_KEY="ilIcNENt9CY=";
	public final static String SIGN="rayuniverse2015";
	
	
	public static String toSecurity(String s)
	{
		byte[] data;
		try {
			data = DESCode.encrypt(s.getBytes("utf-8"), DES_KEY);
			return Hex.byte2hex(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static String fromSecurity(String s)
	{
		try {
		byte[] data=Hex.hex2byte( s);
		byte[] painData= DESCode.decrypt(data, DES_KEY);
		return new String(painData,"utf-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	public static String parseUser(HttpServletRequest hreq)
	{
		String token=null;
		Cookie[] cks=hreq.getCookies();
		if(cks!=null)
		{
			for(Cookie c:cks)
			{
				if(c.getName().equals(Consts.UM_USERID_TOKEN))
				{
					token=c.getValue();
					break;
				}
				
			}
		}
		if(token==null)
		{
			return null;
		}
		
		try{
			String[] seg=token.split("-");
			if(seg.length!=2)
			{
				return null;
			}
			String userId=fromSecurity(seg[0]);
			String sign=MD5.getMD5((userId+SIGN).getBytes("utf-8"));
			if(sign.equals(seg[1])){
				return userId;
			}
			else
			{
				return null;
			}
		}catch(Throwable e)
		{
			return null;
		}

	}
	public static void storeUmUserIdSecurity()
	{
		String seg0=toSecurity(PlatformContext.getUmUserId());
		String seg1;
		try {
			seg1 = MD5.getMD5((PlatformContext.getUmUserId()+SIGN).getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		Cookie  localSession=new Cookie(Consts.UM_USERID_TOKEN,seg0+"-"+seg1);
		localSession.setPath("/");
		PlatformContext.getHttpServletResponse().addCookie(localSession);
	}
	
	public static void removeUmUserIdSecurity()
	{
		Cookie  localSession=new Cookie(Consts.UM_USERID_TOKEN, "");
		localSession.setPath("/");
		localSession.setMaxAge(0);
		PlatformContext.getHttpServletResponse().addCookie(localSession);
	}

}
