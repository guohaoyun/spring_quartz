package com.ghy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ghy.model.User;
import com.ghy.service.UserService;

/**  
*  
*
* @author ghy  
* @date 2017年4月22日
* 类说明  :
*/
@Controller
public class LoginController extends BaseController{

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "toLogin")
	public ModelAndView toLogin(HttpServletRequest request,HttpServletResponse response){
		
		User user = (User) request.getSession().getAttribute("userinfo");
		ModelAndView mav = new ModelAndView();
		
		if(user != null){
			mav.setViewName("index");
			mav.addObject("userinfo", user);
			return mav;
		}else{
			mav.setViewName("login");
			return mav;
		}
	}
	
	@RequestMapping(value = "login")
	public String login(HttpServletRequest request,HttpServletResponse response){
		
		String username = getPara(request, "username");
		String password = getPara(request, "password");
		
		User user = userService.findByUsername(username);
		
		if(user != null){
			if(password.equals(user.getPassword())){
				request.getSession().setAttribute("userinfo", user);
				return "index";
			}
		}else{
			return "redirect:/toLogin";
		}
		
		return "redirect:/toLogin";
	}
	
	
	@RequestMapping(value = "logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		
		request.getSession().removeAttribute("userinfo");
		
		return "redirect:/toLogin";
	}
}

