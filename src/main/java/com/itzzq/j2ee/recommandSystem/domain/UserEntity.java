package com.itzzq.j2ee.recommandSystem.domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.itzzq.j2ee.recommandSystem.commons.domain.BaseEntity;

/**
 * Entity to hold application user data - username,password,etc.
 * 
 * @author itzzq
 */
@Entity
@Table(name = "appuser")
public class UserEntity extends BaseEntity {
	private static final long serialVersionUID = 8847316867503858086L;

	private String userName;
	private String password;
	@ManyToMany
	@JoinTable(name = "lovedapp", joinColumns = @JoinColumn(name = "userid", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "appid", referencedColumnName = "id"))
	private Collection<AppEntity> myapp;

	@ManyToMany
	@JoinTable(name = "buddylist", joinColumns = @JoinColumn(name = "uid", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "fid", referencedColumnName = "id"))
	private Collection<UserEntity> myfriend;
	
	@ManyToMany(mappedBy = "myfriend", fetch=FetchType.LAZY)
	private Collection<UserEntity> friendofuser;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		PasswordEncoder crypto = new Md5PasswordEncoder();
		this.password = crypto.encodePassword(password, null);
	}

	public Collection<AppEntity> getMyapp() {
		return myapp;
	}

	public void setMyapp(Collection<AppEntity> myapp) {
		this.myapp = myapp;
	}

	public void addApp(AppEntity app) {
		if(!getMyapp().contains(app)){
			getMyapp().add(app);
		}
	}

	public void deleteApp(AppEntity app) {
		if(getMyapp().contains(app)){
			getMyapp().remove(app);
		}
	}
	
	public Collection<UserEntity> getMyfriend() {
		return myfriend;
	}

	public void setMyfriend(Collection<UserEntity> myfriend) {
		this.myfriend = myfriend;
	}

	public void addFriend(UserEntity friend) {
		if(!getMyfriend().contains(friend)) {
			getMyfriend().add(friend);
		}
	}
	
	public void deleteFriend(UserEntity friend) {
		if(getMyfriend().contains(friend)) {
			getMyfriend().remove(friend);
		}
	}
	
	public Collection<UserEntity> getFriendofuser() {
		return friendofuser;
	}

	public void setFriendofuser(Collection<UserEntity> friendofuser) {
		this.friendofuser = friendofuser;
	}

}
