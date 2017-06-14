package com.ghy.service;

import com.ghy.model.User;

/**  
*  
*
* @author ghy  
* @date 2017年5月13日
* 类说明  :
*/
public interface UserService {

	User findByUsername(String username);
}

