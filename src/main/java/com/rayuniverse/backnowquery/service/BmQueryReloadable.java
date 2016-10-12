package com.rayuniverse.backnowquery.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rayuniverse.backnowquery.dao.BmQueryDao;
import com.rayuniverse.framework.schedule.ReloadableResource;
import com.rayuniverse.framework.schedule.SchedulePolicy;
import com.rayuniverse.framework.schedule.SchedulePolicy.ThreadMode;

@Service
public class BmQueryReloadable extends
		ReloadableResource<Map<String, List<Map<String, Object>>>> {
	@Autowired
	BmQueryDao BmQueryDao;

	@Override
	protected Map<String, List<Map<String, Object>>> loadResource() {
		Map map = new HashMap();
		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
		/*try {
			resultMap = BmQueryDao.queryBmList1();
			map.put("sum", resultMap);
		} catch (Throwable e) {
			e.printStackTrace();
		}*/
		try {
			resultMap = BmQueryDao.querySumsaleBymonth();
			map.put("salebymonth", resultMap);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			resultMap = BmQueryDao.querySumorderBymonth();
			map.put("orderbymonth", resultMap);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		/*try {
			resultMap = BmQueryDao.queryBmList4();
			map.put("recharge", resultMap);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			resultMap = BmQueryDao.queryBmList5();
			map.put("consume", resultMap);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			resultMap = BmQueryDao.queryBmList6();
			map.put("rechargeList", resultMap);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			resultMap = BmQueryDao.queryBmList7();
			map.put("consumeList", resultMap);
		} catch (Throwable e) {
			e.printStackTrace();
		}*/
		try {
			int NewOrder = BmQueryDao.queryBmList8();
			map.put("NewOrder", NewOrder);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return map;
	}

	protected String getResourceName() {

		return "BmQueryReloadable";
	}

	protected void configSchedulePolicy(SchedulePolicy schedulePolicy) {

		schedulePolicy.setPeroid(1);
		schedulePolicy.setThreadMode(ThreadMode.IndependSingleThread);
		schedulePolicy.setTimeUnit(TimeUnit.MINUTES);

	}

}
