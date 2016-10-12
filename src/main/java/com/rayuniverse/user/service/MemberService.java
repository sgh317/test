package com.rayuniverse.user.service;

import java.io.UnsupportedEncodingException;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rayuniverse.framework.comm.ResultObject;
import com.rayuniverse.framework.comm.Util;
import com.rayuniverse.user.dao.MemberDao;
import com.rayuniverse.user.domain.AddressInfo;
import com.rayuniverse.user.domain.MemberInfo;

@Service
public class MemberService {
	@Autowired
	MemberDao memberDao;
	
	
	public ResultObject addNewMember(JSONObject jsonObject) throws UnsupportedEncodingException{
		MemberInfo member=new MemberInfo();
		member.setOpenId(jsonObject.get("openid").toString());
		member.setUserId(Util.getUUID());
		member.setNickName(jsonObject.get("nickname").toString());
		member.setUserPic(jsonObject.get("headimgurl").toString());
		member.setUserMoney("0");
		
		memberDao.addNewMember(member);
		
		ResultObject ro=new ResultObject();
		ro.setState(true);
		ro.setValue(member);
		return ro;
		
	}
	
	
	public ResultObject modifyMember(JSONObject jsonObject)
	{
		ResultObject ro=new ResultObject();
		MemberInfo member = memberDao.getMemberInfo(jsonObject.getString("openId"));
		if(member == null)
		{
			ro.setState(false);
			ro.setErrorMessage("用户不存在，无法修改");
			return ro;
		}
		
		member.setOpenId(jsonObject.get("openid").toString());
		member.setUserId(Util.getUUID());
		member.setNickName(jsonObject.get("nickname").toString());
		member.setUserPic(jsonObject.get("headimgurl").toString());
		member.setUserMoney("0");
		
		memberDao.updateMemberInfo(member);
		ro.setState(true);
		
		return ro;
	}
	
	public ResultObject deleteMember(String userId)
	{
		ResultObject ro=new ResultObject();
		
		memberDao.deleteMemberInfo(userId);
		ro.setState(true);
		
		return ro;
	}
	public ResultObject getMemberByOpenId(String openId)
	{
		ResultObject ro=new ResultObject();
		MemberInfo ui = memberDao.getMemberInfo(openId);
		if(ui == null)
		{
			ro.setState(false);
			ro.setErrorMessage("无此用户");
			return ro;
		}
		ro.setValue(ui);
		ro.setState(true);
		
		return ro;
	}
		/*	新增 用户地址
		 * 
		 * 
		*/	
	public ResultObject addUserAddress(JSONObject jsonObject) throws UnsupportedEncodingException{
		AddressInfo address=new AddressInfo();
		address.setAddressid(jsonObject.get("Addressid").toString());
		address.setUserId(Util.getUUID());
		address.setDename(jsonObject.get("dename").toString());
		address.setProvince(jsonObject.get("province").toString());
		address.setCity(jsonObject.get("city").toString());
		address.setArea(jsonObject.get("area").toString());
		address.setAddressdetail(jsonObject.get("addressdetail").toString());
		address.setMobile(jsonObject.get("mobile").toString());
		address.setDefaultaddr(jsonObject.get("defaultaddr").toString());
		
		memberDao.addUserAddress(address);
		
		ResultObject ro=new ResultObject();
		ro.setState(true);
		ro.setValue(address);
		return ro;
		
	}
	
}
