package com.rayuniverse.user.jsb;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rayuniverse.framework.comm.ResultObject;
import com.rayuniverse.framework.json.JsonUtil;
import com.rayuniverse.framework.jsrpc.JsBean;
import com.rayuniverse.user.service.UserService;

@Controller
@JsBean(value="ResourceJsBean")
public class ResourceJsBean {

	@Autowired
	private UserService userService;
	
	
	public String echo(String s)
	{
		return s;
	}
	
	
	/**
	 * ��ȡ�û��б�
	 * @param userName	�û���
	 * @param userType	�û����ͣ�PC-pc���û���
	 * @param flag	�Ƿ���Ч(1-��Ч��0-��Ч)
	 * @return
	 */
	public String queryUserList(String userId, String userName, String userType, String flag)
	{
		ResultObject ro = userService.queryUserList(userId, userName, userType, flag);
		
		return JsonUtil.toJson(ro, true);
	}
	
	public String getUserInfoById(String id)
	{
		ResultObject ro = userService.getUserById(id);
		
		return JsonUtil.toJson(ro, true);
	}
	

	/**
	 * ����û�
	 * @param userId
	 * @param password
	 * @param userName
	 * @param userType
	 * @return
	 */
	public String addUser(String userId, String password, String userName, String userType)
	{
		ResultObject ro = new ResultObject();
		try {
			ro = userService.addNewUser(userId, password, userName, userType);
		} catch (UnsupportedEncodingException e) {
			ro.setState(false);
			ro.setErrorMessage(e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		return JsonUtil.toJson(ro, true);
	}
	
	/**
	 * �޸��û���Ϣ
	 * @param id
	 * @param userId
	 * @param password
	 * @param userName
	 * @param userType
	 * @param flag
	 * @return
	 */
	public String modifyUser(String id, String userId, String password, String userName)
	{
		ResultObject ro = userService.modifyUser(id, userId, password, userName);
		
		return JsonUtil.toJson(ro, true);
	}
	
	/**
	 * ɾ���û���Ϣ
	 * @param id
	 * @return
	 */
	public String deleteUser(String id)
	{
		ResultObject ro = userService.deleteUser(id);
		
		return JsonUtil.toJson(ro, true);
	}
}
