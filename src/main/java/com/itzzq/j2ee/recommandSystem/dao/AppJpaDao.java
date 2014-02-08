package com.itzzq.j2ee.recommandSystem.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.util.Assert;

import com.itzzq.j2ee.recommandSystem.commons.dao.GenericJpaDao;
import com.itzzq.j2ee.recommandSystem.domain.AppEntity;

/**
 * Data access object JPA impl to work with App entity database operations.
 * 
 * @author itzzq
 */

public class AppJpaDao extends GenericJpaDao<AppEntity, Long> implements
	AppDao {

	public AppJpaDao() {
		super(AppEntity.class);
	}
	
	/**
	 * Queries database for user's loved apps
	 * 
	 * @param uid
	 * @return LovedApp List
	 */
	
	@SuppressWarnings("unchecked")
	public List<AppEntity> loadAppFromUid(Long uid){
		Assert.notNull(uid);

		Query query = getEntityManager().createQuery(
				"select app from " + getPersistentClass().getSimpleName() + " app " + 
				"inner join app.owners user where user.id = :uid" ).setParameter(
				"uid", uid);
		
		return query.getResultList();
	}

	
	/**
	 * Queries database for recent apps;
	 * 
	 * @param recent
	 * @return recent app List
	 */
	@SuppressWarnings("unchecked")
	public List<AppEntity> loadAppFromDate(Date recentDate) {
		Assert.notNull(recentDate);
		
		Query query = getEntityManager().createQuery(
				"select app from "+ getPersistentClass().getSimpleName() + " app " +
				"where app.time > :recentDate").setParameter("recentDate", recentDate);
		
		return query.getResultList();
	}
}
