package com.itzzq.j2ee.recommandSystem.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.component.inputtext.InputText;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.itzzq.j2ee.recommandSystem.dao.UserDao;
import com.itzzq.j2ee.recommandSystem.domain.AppEntity;
import com.itzzq.j2ee.recommandSystem.domain.UserEntity;
import com.itzzq.j2ee.recommandSystem.services.UserService;

/**
 * Service providing service methods to work with user data and entity.
 * 
 * @author itzzq
 */

public class UserServiceImpl implements UserService, UserDetailsService {

	private UserDao userDao;
	private UserEntity selectedBuddy;

	/**
	 * User include other user in his buddy list
	 * 
	 * @param userName
	 * @param buddy
	 * @return true if add success
	 */
	public boolean addBuddy(String userName, UserEntity buddy) {
		UserEntity user = userDao.loadUserByUserName(userName);	
		FacesMessage message;
		try {
			user.addFriend(buddy);
			userDao.update(user);
		} catch (Exception e) {
			message = constructErrorMessage(String.format(getMessageBundle()
					.getString("addBuddyFail"), buddy.getUserName()), null);
			getFacesContext().addMessage(null, message);
			return false;
		}
		message = constructInfoMessage(String.format(getMessageBundle()
				.getString("addBuddySuccess"), buddy.getUserName()), null);
		getFacesContext().addMessage(null, message);
		return true;
	}
	
	/**
	 * User remove a buudy from his buddy list
	 * 
	 * @param userName
	 * @param buddy
	 * @return true if add success
	 */
	public boolean removeBuddy(String userName, UserEntity buddy) {
		UserEntity user = userDao.loadUserByUserName(userName);	
		FacesMessage message;
		try {
			user.deleteFriend(buddy);
			userDao.update(user);
		} catch (Exception e) {
			message = constructErrorMessage(String.format(getMessageBundle()
					.getString("deleteBuddyFail"), buddy.getUserName()), null);
			getFacesContext().addMessage(null, message);
			return false;
		}
		message = constructInfoMessage(String.format(getMessageBundle()
				.getString("deleteBuddySuccess"), buddy.getUserName()), null);
		getFacesContext().addMessage(null, message);
		return true;
	}
	
	/**
	 * Show User's buddy list
	 * 
	 * @param userName
	 * @return buddyList
	 */
	public Collection<UserEntity> showBuddyList(String userName) {
		UserEntity user = userDao.loadUserByUserName(userName);	
		Collection<UserEntity> buddyList = null;
		try {
			buddyList = user.getMyfriend();
		} catch (Exception e) {
			FacesMessage message = constructErrorMessage(String.format(getMessageBundle()
					.getString("getMyfriendFail")), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
		return buddyList;
	}
	
	/**
	 * User add app which he loves - persist to database
	 * 
	 * @param userName
	 * @param app
	 * @return true if add success
	 */
	public boolean addLovedApp(String userName, AppEntity app) {
		UserEntity user = userDao.loadUserByUserName(userName);	
		FacesMessage message;
		try {
			user.addApp(app);
			userDao.update(user);
		} catch (Exception e) {
			message = constructErrorMessage(String.format(getMessageBundle()
					.getString("addAppFail"), app.getName()), null);
			getFacesContext().addMessage(null, message);
			return false;
		}
		message = constructInfoMessage(String.format(getMessageBundle()
				.getString("addAppSuccess"), app.getName()), null);
		getFacesContext().addMessage(null, message);
		return true;
	}
	
	/**
	 * User delete app which he loves - persist to database
	 * 
	 * @param userName
	 * @param app
	 * @return true if add success
	 */
	public boolean deleteLovedApp(String userName, AppEntity app) {
		UserEntity user = userDao.loadUserByUserName(userName);	
		FacesMessage message;
		try {
			user.deleteApp(app);
			userDao.update(user);
		} catch (Exception e) {
			message = constructErrorMessage(String.format(getMessageBundle()
					.getString("deleteAppFail"), app.getName()), null);
			getFacesContext().addMessage(null, message);
			return false;
		}
		message = constructInfoMessage(String.format(getMessageBundle()
				.getString("deleteAppSuccess"), app.getName()), null);
		getFacesContext().addMessage(null, message);
		return true;
	}
	
	/**
	 * Show User added apps which he loves
	 * 
	 * @param userName
	 * @return lovedApp
	 */
	public Collection<AppEntity> showLovedApp(String userName) {
		UserEntity user = userDao.loadUserByUserName(userName);	
		Collection<AppEntity> lovedApp = null;
		try {
			lovedApp = user.getMyapp();
		} catch (Exception e) {
			FacesMessage message = constructErrorMessage(String.format(getMessageBundle()
					.getString("getAppFail")), null);
			getFacesContext().addMessage(null, message);
			return null;
		}
		return lovedApp;
	}

	/**
	 * Create user - persist to database
	 * 
	 * @param userEntity
	 * @return true if success
	 */
	public boolean createUser(UserEntity userEntity) {
		if (!userDao.checkAvailable(userEntity.getUserName())) {
			FacesMessage message = constructErrorMessage(String.format(
					getMessageBundle().getString("userExistsMsg"),
					userEntity.getUserName()), null);
			getFacesContext().addMessage(null, message);
			return false;
		}

		try {
			userDao.save(userEntity);
		} catch (Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
			return false;
		}
		return true;
	}

	/**
	 * Get all users from database
	 * 
	 * @return all users list
	 */
	public List<UserEntity> getAllUsers() {
		try {
			return userDao.findAll();
		} catch (Exception e) {
			// something wrong
			return null;
		}
	}
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserEntity getSelectedBuddy() {
		return selectedBuddy;
	}

	public void setSelectedBuddy(UserEntity selectedBuddy) {
		this.selectedBuddy = selectedBuddy;
	}

	/**
	 * Check user name availability. UI ajax use.
	 * 
	 * @param ajax
	 *            event
	 * @return
	 */
	public boolean checkAvailable(AjaxBehaviorEvent event) {

		InputText inputText = (InputText) event.getSource();
		String value = (String) inputText.getValue();

		boolean available = userDao.checkAvailable(value);

		if (!available) {
			FacesMessage message = constructErrorMessage(null, String.format(
					getMessageBundle().getString("userExistsMsg"), value));
			getFacesContext().addMessage(event.getComponent().getClientId(),
					message);
		} else {
			FacesMessage message = constructInfoMessage(null, String.format(
					getMessageBundle().getString("userAvailableMsg"), value));
			getFacesContext().addMessage(event.getComponent().getClientId(),
					message);
		}

		return available;
	}

	/**
	 * Construct UserDetails instance required by spring security
	 */
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {

		UserEntity user = userDao.loadUserByUserName(userName);

		if (user == null) {
			throw new UsernameNotFoundException(String.format(
					getMessageBundle().getString("badCredentials"), userName));
		}

		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		User userDetails = new User(user.getUserName(), user.getPassword(),
				authorities);

		return userDetails;
	}

	/**
	 * Retrieves full User record from database by user name
	 * 
	 * @param userName
	 * @return UserEntity
	 */
	public UserEntity loadUserEntityByUsername(String userName) {
		return userDao.loadUserByUserName(userName);
	}

	protected FacesMessage constructErrorMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_ERROR, message, detail);
	}

	protected FacesMessage constructInfoMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_INFO, message, detail);
	}

	protected FacesMessage constructFatalMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_FATAL, message, detail);
	}

	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	protected ResourceBundle getMessageBundle() {
		return ResourceBundle.getBundle("message-labels");
	}

}