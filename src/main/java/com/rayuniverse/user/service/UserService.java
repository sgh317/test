package com.rayuniverse.user.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rayuniverse.framework.comm.MD5;
import com.rayuniverse.framework.comm.ResultObject;
import com.rayuniverse.user.dao.UserDao;
import com.rayuniverse.user.domain.UserInfo;

@Service
public class UserService {
	@Autowired
	UserDao userDao;
	
	public ResultObject login(String userId,String password) throws UnsupportedEncodingException
	{
		ResultObject ro=new ResultObject();
		UserInfo userInfo=userDao.getUserInfo(userId);
		if(userInfo==null)
		{
			ro.setState(false);
			ro.setErrorCode(1);
			ro.setErrorMessage("用户不存在");
			return ro;
		}
		String pwd=MD5.getMD5((password+"rayuniverse_password_key").getBytes("utf-8"));
		if(pwd.equals(userInfo.getPassword())){
			ro.setState(true);
			return ro;
		}
		else
		{
			ro.setState(false);
			ro.setErrorCode(2);
			ro.setErrorMessage("密码不正确");
			return ro;
		}
		
	}
	
	public ResultObject addNewUser(String userId,String password,String name, String type) throws UnsupportedEncodingException{
		
		if(userDao.existsUser(userId)){
			ResultObject ro=new ResultObject();
			ro.setState(false);
			ro.setErrorMessage("用户已经存在");
			return ro;
		}
		String pwd=MD5.getMD5((password+"rayuniverse_password_key").getBytes("utf-8"));
		UserInfo userInfo=new UserInfo();
		userInfo.setId(UUID.randomUUID().toString());
		userInfo.setName(name);
		userInfo.setPassword(pwd);
		userInfo.setUserId(userId);
		userInfo.setType(type);
		
		userDao.addNewUser(userInfo);
		
		ResultObject ro=new ResultObject();
		ro.setState(true);
		ro.setValue(userInfo);
		return ro;
		
	}
	
	public ResultObject queryUserList(String userId, String userName, String userType, String flag) 
	{
		ResultObject ro=new ResultObject();
		List<UserInfo> list = userDao.getUserList(userId, userName, userType, flag);
		ro.setState(true);
		ro.setValue(list);
		return ro;
	}

	
	public ResultObject modifyUser(String id, String userId, String password, String userName)
	{
		ResultObject ro=new ResultObject();
		UserInfo ui = userDao.getUserInfoById(id);
		if(ui == null)
		{
			ro.setState(false);
			ro.setErrorMessage("用户不存在，无法修改");
			return ro;
		}
		
		try {
			String pwd = MD5.getMD5((password+"rayuniverse_password_key").getBytes("utf-8"));
			ui.setName(userName);
			ui.setPassword(pwd);
			ui.setUserId(userId);
			
			userDao.updateUserInfo(ui);
			ro.setState(true);
		} catch (UnsupportedEncodingException e) {
			ro.setState(false);
			ro.setErrorMessage(e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		return ro;
	}
	
	public ResultObject deleteUser(String id)
	{
		ResultObject ro=new ResultObject();
		
		userDao.deleteUserById(id);
		ro.setState(true);
		
		return ro;
	}

	public ResultObject getUserById(String id)
	{
		ResultObject ro=new ResultObject();
		UserInfo ui = userDao.getUserInfoById(id);
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
	
	public ResultObject getUserByUserId(String userId)
	{
		ResultObject ro=new ResultObject();
		UserInfo ui = userDao.getUserInfo(userId);
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
	
	public ResultObject saveUserAssiGroup(String id, String[] res)
	{
		ResultObject ro=new ResultObject();
		ro.setState(true);

		// 删除所有用户组关联
		userDao.deleteUserToGroup(id);
		
		// 添加用户组关联
		for (String resId : res) 
		{
			userDao.insertUserToGroup(id, resId);
		}
		
		return ro;
	}
	
	public ResultObject queryResourceListByUserId(String userId)
	{
		ResultObject ro=new ResultObject();
		List list = userDao.queryResourceListByUserId(userId);
		if(list == null || list.size() <= 0)
		{
			ro.setState(false);
//			ro.setErrorMessage("此用户尚未分配用户组资源");
			return ro;
		}
		ro.setValue(list);
		ro.setState(true);
		
		return ro;
	}
	
	public ResultObject queryGroupListByUserId(String userId)
	{
		ResultObject ro=new ResultObject();
		List list = userDao.queryGroupListByUserId(userId);
		if(list == null || list.size() <= 0)
		{
			ro.setState(false);
//			ro.setErrorMessage("此用户尚未分配用户组");
			return ro;
		}
		ro.setValue(list);
		ro.setState(true);
		
		return ro;
	}
	
}
