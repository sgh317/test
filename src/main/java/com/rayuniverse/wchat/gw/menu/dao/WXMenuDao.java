package com.rayuniverse.wchat.gw.menu.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rayuniverse.wchat.gw.menu.domain.WXMenu;

@Repository
public class WXMenuDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public WXMenu getWXMenu()
	{
		List<WXMenu> list=sqlSessionTemplate.selectList("getWXMenu");
		if(list!=null&&list.size()>0)
		{
			list.get(0).initByData();
			return list.get(0);
		}
		return null;
	}

	public void insertWXMenu(WXMenu wxMenu)
	{
		sqlSessionTemplate.insert("insertWXMenu", wxMenu);
	}
	
	public void updateWXMenu(WXMenu wxMenu)
	{
		sqlSessionTemplate.update("updateWXMenu", wxMenu);
	}
}
