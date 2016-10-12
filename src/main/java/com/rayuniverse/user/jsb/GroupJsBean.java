package com.rayuniverse.user.jsb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rayuniverse.framework.comm.ResultObject;
import com.rayuniverse.framework.json.JsonUtil;
import com.rayuniverse.framework.jsrpc.JsBean;
import com.rayuniverse.user.service.GroupService;

@Controller
@JsBean(value="GroupJsBean")
public class GroupJsBean {

	@Autowired
	private GroupService groupService;
	
	
	/**
	 * 查询用户组列表
	 * @param code
	 * @param name
	 * @param desc
	 * @param flag	是否有效(1-有效；0-无效)
	 * @return
	 */
	public String queryGroupList(String code, String name, String desc, String flag)
	{
		ResultObject ro = groupService.queryGroupList(code, name, desc, flag);
		
		return JsonUtil.toJson(ro, true);
	}
	
	/**
	 * 查询用户组详情
	 * @param id
	 * @return
	 */
	public String getGroupInfoById(String id)
	{
		ResultObject ro = groupService.getGroupById(id);
		
		return JsonUtil.toJson(ro, true);
	}
	
	/**
	 * 获取用户组对应资源
	 * @param id
	 * @return
	 */
	public String getGroupResource(String id)
	{
		ResultObject ro = groupService.getGroupResource(id);
		
		return JsonUtil.toJson(ro, true);
	}
	

	/**
	 * 添加用户组
	 * @param code
	 * @param name
	 * @param desc
	 * @return
	 */
	public String addGroup(String code, String name, String desc)
	{
		ResultObject ro = groupService.addNewGroup(code, name, desc);
		
		return JsonUtil.toJson(ro, true);
	}
	
	/**
	 * 修改用户组信息
	 * @param id
	 * @param code
	 * @param name
	 * @param desc
	 * @param flag
	 * @return
	 */
	public String modifyGroup(String id, String code, String name, String desc, String flag)
	{
		ResultObject ro = groupService.modifyGroup(id, code, name, desc, flag);
		
		return JsonUtil.toJson(ro, true);
	}
	
	/**
	 * 删除用户组信息
	 * @param id
	 * @return
	 */
	public String deleteGroup(String id)
	{
		ResultObject ro = groupService.deleteGroup(id);
		
		return JsonUtil.toJson(ro, true);
	}
	
	/**
	 * 用户组分配资源
	 * @param id
	 * @param resIds
	 * @return
	 */
	public String assignedResource(String id, String resIds)
	{
		ResultObject ro = new ResultObject();

		if(id != null && !"".equals(id))
		{
			String[] res = {};
			if(resIds != null)
			{
				res = resIds.split(",");
			}
			ro = groupService.saveGroupAssiResource(id, res);
		}
		else
		{
			ro.setState(false);
			ro.setErrorMessage("用户组Id为空");
		}
		
		return JsonUtil.toJson(ro, true);
	}
}
