package com.itzzq.j2ee.recommandSystem.services;

import java.util.List;

import com.itzzq.j2ee.recommandSystem.domain.AppEntity;

public interface RecommandService {
	
	/**
	 * Item-Based Collaborative Filtering Recommendation
	 * 
	 * @return Recommend list
	 */
	public List<AppEntity> itemBasedCF(String userName); 
}
