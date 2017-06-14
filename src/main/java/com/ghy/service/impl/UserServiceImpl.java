package com.ghy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghy.dao.UserDao;
import com.ghy.model.User;
import com.ghy.service.UserService;

/**  
*  
*
* @author ghy  
* @date 2017年5月13日
* 类说明  :
*/
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		User user = userDao.findByUsername(username);
		
		return user;
	}

}

