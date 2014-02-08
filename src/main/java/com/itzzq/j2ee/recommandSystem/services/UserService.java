package com.itzzq.j2ee.recommandSystem.services;

import java.util.Collection;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;

import com.itzzq.j2ee.recommandSystem.domain.AppEntity;
import com.itzzq.j2ee.recommandSystem.domain.UserEntity;

/**
 * Service providing service methods to work with user data and entity.
 * 
 * @author itzzq
 */

public interface UserService {

	/**
	 * Show User's buddy list
	 * 
	 * @param userName
	 * @return buddyList
	 */
	public Collection<UserEntity> showBuddyList(String userName);
	
	/**
	 * User include other user in his buddy list
	 * 
	 * @param userName
	 * @param buddy
	 * @return true if add success
	 */
	public boolean addBuddy(String userName, UserEntity buddy);
	
	/**
	 * User remove a buudy from his buddy list
	 * 
	 * @param userName
	 * @param buddy
	 * @return true if add success
	 */
	public boolean removeBuddy(String userName, UserEntity buddy);
	
	/**
	 * Show User added apps which he loves
	 * 
	 * @return Set<AppEntity>
	 */
	public Collection<AppEntity> showLovedApp(String userName);
	
	/**
	 * User add app which he loves - persist to database
	 * 
	 * @param userName, AppEntity
	 * @return true if add success
	 */
	public boolean addLovedApp(String userName, AppEntity app);
	
	/**
	 * User delete app which he loves - persist to database
	 * 
	 * @param AppEntity
	 * @return true if add success
	 */
	public boolean deleteLovedApp(String userName, AppEntity app);
	
	/**
	 * Create user - persist to database
	 * 
	 * @param userEntity
	 * @return true if success
	 */
	boolean createUser(UserEntity userEntity);
    
    /**
     * Check user name availability. UI ajax use.
     * 
     * @param ajax event
     * @return
     */
    boolean checkAvailable(AjaxBehaviorEvent event);
    
    /**
     * Retrieves full User record from database by user name
     * 
     * @param userName
     * @return UserEntity
     */
    UserEntity loadUserEntityByUsername(String userName);
    
    /**
	 * Get all users from database
	 * 
	 * @return all users List
	 */
	List<UserEntity> getAllUsers();
}