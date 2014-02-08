package com.itzzq.j2ee.recommandSystem.services.impl;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.itzzq.j2ee.recommandSystem.dao.AppDao;
import com.itzzq.j2ee.recommandSystem.domain.AppEntity;
import com.itzzq.j2ee.recommandSystem.services.AppService;

public class AppServiceImpl implements AppService{

	private AppDao appDao;
	private AppEntity selectedApp;
	
	public AppDao getAppDao() {
		return appDao;
	}
	public void setAppDao(AppDao appDao) {
		this.appDao = appDao;
	}
	public AppEntity getSelectedApp() {
		return selectedApp;
	}
	public void setSelectedApp(AppEntity selectedApp) {
		this.selectedApp = selectedApp;
	}
	
	/**
	 * Get all apps from database
	 * 
	 * @return all apps List
	 */
	public List<AppEntity> getAllApps() {
		try {
			return appDao.findAll();
		} catch (Exception e) {
			// something wrong

			return null;
		}
	}

	/**
	 * Get the app which user loves.
	 * 
	 * @param uid
	 * @return loved apps List
	 */
	public List<AppEntity> getLovedApps(Long uid) {
		try {
			return appDao.loadAppFromUid(uid);
		} catch (Exception e) {
			// something wrong

			return null;
		}
	}
	
	/**
	 * Get the new apps from database
	 * 
	 * @return new apps List
	 */
	public List<AppEntity> getNewApps() {
		Date recentDate = new GregorianCalendar(2013,0,1).getTime();
		
		try {
			return appDao.loadAppFromDate(recentDate);
		} catch (Exception e) {
			// something wrong
			return null;
		}
	}
}
