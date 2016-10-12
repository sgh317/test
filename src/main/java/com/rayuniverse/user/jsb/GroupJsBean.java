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
	 * ��ѯ�û����б�
	 * @param code
	 * @param name
	 * @param desc
	 * @param flag	�Ƿ���Ч(1-��Ч��0-��Ч)
	 * @return
	 */
	public String queryGroupList(String code, String name, String desc, String flag)
	{
		ResultObject ro = groupService.queryGroupList(code, name, desc, flag);
		
		return JsonUtil.toJson(ro, true);
	}
	
	/**
	 * ��ѯ�û�������
	 * @param id
	 * @return
	 */
	public String getGroupInfoById(String id)
	{
		ResultObject ro = groupService.getGroupById(id);
		
		return JsonUtil.toJson(ro, true);
	}
	
	/**
	 * ��ȡ�û����Ӧ��Դ
	 * @param id
	 * @return
	 */
	public String getGroupResource(String id)
	{
		ResultObject ro = groupService.getGroupResource(id);
		
		return JsonUtil.toJson(ro, true);
	}
	

	/**
	 * ����û���
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
	 * �޸��û�����Ϣ
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
	 * ɾ���û�����Ϣ
	 * @param id
	 * @return
	 */
	public String deleteGroup(String id)
	{
		ResultObject ro = groupService.deleteGroup(id);
		
		return JsonUtil.toJson(ro, true);
	}
	
	/**
	 * �û��������Դ
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
			ro.setErrorMessage("�û���IdΪ��");
		}
		
		return JsonUtil.toJson(ro, true);
	}
}
