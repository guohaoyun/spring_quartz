package com.ghy.dao;

import com.ghy.model.User;

/**  
*  
*
* @author ghy  
* @date 2017年5月13日
* 类说明  :
*/
public interface UserDao {

	User findByUsername(String username);
}

