package com.itzzq.j2ee.recommandSystem.services;

import java.util.List;

import com.itzzq.j2ee.recommandSystem.domain.AppEntity;
/**
 * Service providing service methods to work with app data and entity.
 * 
 * @author itzzq
 */

public interface AppService {
	
	/**
	 * Get all apps from database
	 * 
	 * @return all apps List
	 */
	List<AppEntity> getAllApps();
	
	/**
	 * Get the new apps from database
	 * 
	 * @return new apps List
	 */
	List<AppEntity> getNewApps();
	
	/**
	 * Get the app which user loves.
	 * When using Set<AppEntity> myapp, getMyapp() doesn't work.
	 * So I wirte another query for getMyapp(), which is this method.
	 * Howerver, when I use Collection instead of Set, getMyapp() works.
	 * So This method does no use for my project, but leave it for example.
	 * 
	 * @param uid
	 * @return loved apps List
	 */
	public List<AppEntity> getLovedApps(Long uid);
}
