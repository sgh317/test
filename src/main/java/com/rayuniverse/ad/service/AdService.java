package com.rayuniverse.ad.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rayuniverse.ad.dao.AdDao;
import com.rayuniverse.domain.AdDomain;
import com.rayuniverse.framework.comm.Util;

/**
 * 
 * @author DiGua
 *
 */
@Service
@Transactional
public class AdService {
	
	@Autowired
	AdDao adDao;
	
	@Transactional(rollbackFor=Throwable.class,propagation=Propagation.REQUIRED)
	public void saveAdInfo(AdDomain adInfo) {
		adInfo.setAdId(Util.getUUID());
		adDao.saveAdInfo(adInfo);
	}
	
	@Transactional(rollbackFor=Throwable.class,propagation=Propagation.REQUIRED)
	public void updateAdInfo(AdDomain adInfo) {
		adDao.updateAdInfo(adInfo);
	}
	/**
	 * 
	 * @param չʾ��ҳ���
	 */
	@Transactional(rollbackFor=Throwable.class,propagation=Propagation.REQUIRED)
	public List<Map> queryAdhome(Map parameterMap) {
		return adDao.queryAdhome(parameterMap);
	}
	
	/**
	 * 
	 * @param չʾ��ҳ�ȵ��Ƽ�
	 */
	@Transactional(rollbackFor=Throwable.class,propagation=Propagation.REQUIRED)
	public List<Map> queryHotadvise(Map parameterMap) {
		return adDao.queryHotadvise(parameterMap);
	}
	
	/**
	 * 
	 * @param չʾ��ҳ�ؼ���Ʒ
	 */
	@Transactional(rollbackFor=Throwable.class,propagation=Propagation.REQUIRED)
	public List<Map> querydiscountgood(Map parameterMap) {
		return adDao.querydiscountgood(parameterMap);
	}
	
}
