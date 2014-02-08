package com.itzzq.j2ee.recommandSystem.dao;

import java.util.Date;
import java.util.List;

import com.itzzq.j2ee.recommandSystem.commons.dao.GenericDao;
import com.itzzq.j2ee.recommandSystem.domain.AppEntity;

/**
 * Data access object interface to work with App entity database operations.
 * 
 * @author itzzq
 */

public interface AppDao extends GenericDao<AppEntity, Long>{
	
	/**
	 * Queries database for user's loved apps
	 * 
	 * @param uid
	 * @return LovedApp List
	 */
	public List<AppEntity> loadAppFromUid(Long uid);
	
	/**
	 * Queries database for recent apps;
	 * 
	 * @param recent
	 * @return recent app List
	 */
	public List<AppEntity> loadAppFromDate(Date recentDate);
}
