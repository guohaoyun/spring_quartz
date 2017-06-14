package com.ghy.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**  
*  
*
* @author ghy  
* @date 2017年5月13日
* 类说明  :
*/
@Entity
@Table(name = "t_user")
public class User implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6934630401601581044L;

	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "username",length = 18)
	private String username;
	
	@Column(name = "password",length = 32)
	private String password;
	
	public User(){}
	
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}

