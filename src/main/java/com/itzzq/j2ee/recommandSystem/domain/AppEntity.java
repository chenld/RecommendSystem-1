package com.itzzq.j2ee.recommandSystem.domain;


import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.itzzq.j2ee.recommandSystem.commons.domain.BaseEntity;

/**
 * Entity to hold application data - name,time,heat,etc.
 * 
 * @author itzzq
 */
@Entity
@Table(name="app")
public class AppEntity extends BaseEntity{
	private static final long serialVersionUID = -7402283294342062615L;
	
	private String name;
	@Temporal(TemporalType.DATE) 
	private Date time;
	private int heat;
	@ManyToMany(mappedBy="myapp", fetch=FetchType.LAZY)
	private Collection<UserEntity> owners;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getHeat() {
		return heat;
	}
	public void setHeat(int heat) {
		this.heat = heat;
	}
	
	public Collection<UserEntity> getOwners() {
		return owners;
	}
	public void setOwners(Collection<UserEntity> owners) {
		this.owners = owners;
	}
}
