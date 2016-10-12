package com.rayuniverse.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rayuniverse.framework.comm.ResultObject;
import com.rayuniverse.user.dao.GroupDao;
import com.rayuniverse.user.domain.GroupInfo;

@Service
public class GroupService {
	@Autowired
	private GroupDao groupDao;
	
	
	public ResultObject addNewGroup(String code,String name,String desc)
	{
		if(groupDao.getGroupInfoByCode(code) != null){
			ResultObject ro=new ResultObject();
			ro.setState(false);
			ro.setErrorMessage("�û���[code:"+code+"]�Ѿ�����");
			return ro;
		}
		
		GroupInfo groupInfo = new GroupInfo();
		groupInfo.setId(UUID.randomUUID().toString());
		groupInfo.setCode(code);
		groupInfo.setName(name);
		groupInfo.setDesc(desc);
		
		groupDao.addNewGroup(groupInfo);
		
		ResultObject ro=new ResultObject();
		ro.setState(true);
		ro.setValue(groupInfo);
		return ro;
		
	}
	
	public ResultObject queryGroupList(String code, String name, String desc, String flag) 
	{
		ResultObject ro=new ResultObject();
		List<GroupInfo> list = groupDao.getGroupList(code, name, desc, flag);
		ro.setState(true);
		ro.setValue(list);
		return ro;
	}

	
	public ResultObject modifyGroup(String id, String code, String name, String desc, String flag)
	{
		ResultObject ro=new ResultObject();
		GroupInfo gi = groupDao.getGroupInfoById(id);
		if(gi == null)
		{
			ro.setState(false);
			ro.setErrorMessage("�û��鲻���ڣ��޷��޸�");
			return ro;
		}
		
		gi.setCode(code);
		gi.setName(name);
		gi.setDesc(desc);
		gi.setFlag(flag);
		
		groupDao.updateGroupInfo(gi);
		ro.setState(true);
		ro.setValue(gi);
		
		return ro;
	}
	
	public ResultObject saveGroupAssiResource(String id, String[] res)
	{
		ResultObject ro=new ResultObject();
		ro.setState(true);

		// ɾ��������Դ
		groupDao.deleteGroupResource(id);
		
		// �����Դ��Ϣ
		for (String resId : res) 
		{
			groupDao.insertGroupResource(id, resId);
		}
		
		return ro;
	}
	
	public ResultObject deleteGroup(String id)
	{
		ResultObject ro=new ResultObject();
		
		groupDao.deleteGroupById(id);
		ro.setState(true);
		
		return ro;
	}
	
	public ResultObject getGroupById(String id)
	{
		ResultObject ro=new ResultObject();
		GroupInfo gi = groupDao.getGroupInfoById(id);
		if(gi == null)
		{
			ro.setState(false);
			ro.setErrorMessage("�޴��û���");
			return ro;
		}
		ro.setValue(gi);
		ro.setState(true);
		
		return ro;
	}
	
	public ResultObject getGroupResource(String id)
	{
		ResultObject ro=new ResultObject();
		List list = groupDao.getGroupResource(id);
		if(list == null || list.size() <= 0)
		{
			ro.setState(false);
			ro.setErrorMessage("���û�����δ������Դ");
			return ro;
		}
		ro.setValue(list);
		ro.setState(true);
		
		return ro;
	}
}
