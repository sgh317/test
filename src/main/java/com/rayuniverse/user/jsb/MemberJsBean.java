package com.rayuniverse.user.jsb;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rayuniverse.framework.comm.ResultObject;
import com.rayuniverse.framework.comm.UmUserIdSecurityToken;
import com.rayuniverse.framework.comm.Util;
import com.rayuniverse.framework.json.JsonUtil;
import com.rayuniverse.framework.jsrpc.JsBean;
import com.rayuniverse.user.service.MemberService;

import net.sf.json.JSONObject;

@Controller
@JsBean(value="MemberJsBean")
public class MemberJsBean {
	
	String snsapi_userinfo_url=" https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	@Autowired
	private MemberService memberService;
	
	
	public String getUserInfoById(String openId)
	{
		ResultObject ro = memberService.getMemberByOpenId(openId);
		
		return JsonUtil.toJson(ro, true);
	}
	

	/**
	 * 添加用户
	 * @param userId
	 * @param password
	 * @param userName
	 * @param userType
	 * @return
	 */
	public String addMember(String openId,String accessToken)
	{
		ResultObject ro = new ResultObject();
		try {
			String requestUrl = snsapi_userinfo_url.replace("OPENID", UmUserIdSecurityToken.fromSecurity(openId));
			requestUrl = snsapi_userinfo_url.replace("ACCESS_TOKEN", UmUserIdSecurityToken.fromSecurity(accessToken));
			JSONObject jsonObject = Util.httpRequest(requestUrl, "GET", null);
			ResultObject ro1 = memberService.getMemberByOpenId(jsonObject.getString("openId"));
			if(!ro1.isState()) {
				ro = memberService.addNewMember(jsonObject);
			}
		} catch (UnsupportedEncodingException e) {
			ro.setState(false);
			ro.setErrorMessage(e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		return JsonUtil.toJson(ro, true);
	}
	
	/**
	 * 修改用户信息
	 * @return
	 */
	public String modifyMember(JSONObject jsonObject)
	{
		ResultObject ro = memberService.modifyMember(jsonObject);
		
		return JsonUtil.toJson(ro, true);
	}
	
	/**
	 * 删除用户信息
	 * @param id
	 * @return
	 */
	public String deleteMember(String userId)
	{
		ResultObject ro = memberService.deleteMember(userId);
		
		return JsonUtil.toJson(ro, true);
	}
	
}
