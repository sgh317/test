package com.rayuniverse.wchat.gw.asyn;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rayuniverse.domain.TaskData;

@Repository
public class AsyncTaskDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	
	public void addNewTask(TaskData taskData) {
		
		sqlSessionTemplate.insert("addNewTask", taskData);
	}

	
}
