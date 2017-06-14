package com.ghy.dao.impl;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.ghy.dao.UserDao;
import com.ghy.model.User;

/**  
*  
*
* @author ghy  
* @date 2017年5月13日
* 类说明  :
*/
@Component
public class UserDaoImpl extends BaseDaoImpl<User, Integer> implements UserDao{

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		
		User user = this.uniqueResult
				(createCriteria().add(Restrictions.eq("username", username)));
				
		return user;
	}

}

