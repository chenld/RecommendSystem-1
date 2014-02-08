package com.itzzq.j2ee.recommandSystem.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.itzzq.j2ee.recommandSystem.dao.AppDao;
import com.itzzq.j2ee.recommandSystem.dao.UserDao;
import com.itzzq.j2ee.recommandSystem.domain.AppEntity;
import com.itzzq.j2ee.recommandSystem.domain.UserEntity;
import com.itzzq.j2ee.recommandSystem.services.RecommandService;
import com.itzzq.j2ee.recommandSystem.services.utils.Matrix;

public class RecommandServiceImpl implements RecommandService {

	private AppDao appDao;
	private UserDao userDao;
	private Long[] allAppID;
	private Long[] lovedAppID;
	private Map<Matrix, Integer> cooccurrence;
	private Map<Matrix, Integer> userRate;
	private Map<Matrix, Integer> recommand;
	private List<AppEntity> recommandResult;

	public List<AppEntity> itemBasedCF(String userName) {

		List<AppEntity> allApps = appDao.findAll();
		List<UserEntity> allUsers = userDao.findAll();
		UserEntity currentUser = userDao.loadUserByUserName(userName);
		int i = 0;
		int size = allApps.size();
		allAppID = new Long[size];
		cooccurrence = new HashMap<Matrix, Integer>();
		userRate = new HashMap<Matrix, Integer>();
		recommand = new HashMap<Matrix, Integer>();
		recommandResult = new ArrayList<AppEntity>();
		
		for (AppEntity app : allApps) {
			allAppID[i] = app.getId();
			i++;
		}

		// 同现矩阵和评分矩阵初始化
		for (int j = 0; j < allAppID.length; j++) {
			for (int k = 0; k < allAppID.length; k++) {
				cooccurrence.put(new Matrix(allAppID[j], allAppID[k]), 0);
				userRate.put(new Matrix(allAppID[j], allAppID[j]), 1);
			}
		}
		i = 0;

		// 得到所有用户喜欢的应用
		for (UserEntity user : allUsers) {
			Collection<AppEntity> lovedApp = user.getMyapp();
			lovedAppID = new Long[lovedApp.size()];
			for (AppEntity app : lovedApp) {
				lovedAppID[i] = app.getId();
				i++;
			}

			// 每个用户喜欢的应用的同现次数记录入同现矩阵中
			for (int j = 0; j < lovedAppID.length; j++) {
				for (int k = j; k < lovedAppID.length; k++) {
					Matrix tempkeyX = new Matrix(lovedAppID[j], lovedAppID[k]);
					Matrix tempkeyY = new Matrix(lovedAppID[k], lovedAppID[j]);
					
					Integer tempvalue = cooccurrence.get(tempkeyX) + 1;
					cooccurrence.put(tempkeyX, tempvalue);
					cooccurrence.put(tempkeyY, tempvalue);
				}
				
			}
			
			// 根据用户喜欢的应用为当前用户构建评分矩阵
			if (user.equals(currentUser)) {
				for (int j = 0; j < lovedAppID.length; j++) {
					// 若有喜欢的应用则评9分，没有则为初始的1分
					userRate.put(new Matrix(lovedAppID[j], lovedAppID[j]), 9);
				}
			}

			i = 0;
		}
		
		// 同现矩阵*评分矩阵=推荐矩阵
		for (int j = 0; j < allAppID.length; j++) {
			int recommandItem = 0;
			// 如果为用户已经喜欢的应用则不需要推荐
			if (userRate.get(new Matrix(allAppID[j], allAppID[j])) == 9)
				continue;
			for (int k = 0; k < allAppID.length; k++) {
				recommandItem += cooccurrence.get(new Matrix(allAppID[j], allAppID[k])) * userRate.get(new Matrix(allAppID[k], allAppID[k]));
			}
			recommand.put(new Matrix(allAppID[j], allAppID[j]), recommandItem);
		}
		
		// 排序得出权值最高的应用
		List<Map.Entry<Matrix, Integer>> recommandList = new ArrayList<>();
		recommandList.addAll(recommand.entrySet());
		Collections.sort(recommandList, new Comparator<Map.Entry<Matrix, Integer>>() {
			@Override
			public int compare(Entry<Matrix, Integer> m,
					Entry<Matrix, Integer> n) {
				return n.getValue() - m.getValue();
			}
		});
		
		// 输出推荐前三个应用
		Iterator<Map.Entry<Matrix, Integer>> it = recommandList.iterator();
		for(int j=0; j<3; j++) {
			Long tempID = it.next().getKey().getRow();
			AppEntity tempApp = appDao.findById(tempID);
			
			recommandResult.add(tempApp);
		}
		
		return recommandResult;
	}

	public AppDao getAppDao() {
		return appDao;
	}

	public void setAppDao(AppDao appDao) {
		this.appDao = appDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
