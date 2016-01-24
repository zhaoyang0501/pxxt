package com.pzy.controller.front;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pzy.entity.User;
import com.pzy.service.UserService;
import com.pzy.service.GradeService;
/***
 * 后台首页，处理后台登录验证权限等操作
 * @author qq:263608237
 *
 */
@Controller
@RequestMapping("/")
public class FrontController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private GradeService gradeService;
	@RequestMapping("index")
	public String index() {
		return "index";
	}
	@RequestMapping("register")
	public String register() {
		return "register";
	}
	@RequestMapping("doregister")
	public String register(User user,Model model) {
		user.setCreateDate(new Date());
		model.addAttribute("tip","注册成功请登录！");
		userService.save(user);
		return "login";
	}
	@RequestMapping("login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("teacher")
	public String teacher() {
		return "teacher";
	}
	@RequestMapping("work")
	public String work() {
		return "work";
	}
	@RequestMapping("about")
	public String about() {
		return "about";
	}
	@RequestMapping("grade")
	public String grade(Model model) {
		model.addAttribute("grades", gradeService.findAll());
		return "grade";
	}
	@RequestMapping("dologin")
	public String dologin(User user,HttpSession httpSession,Model model) {
		User loginuser=userService.login(user.getUsername(), user.getPassword());
    	if(loginuser!=null){
    		
    		httpSession.setAttribute("user", loginuser);
            return "index"; 
    	}
    	else{
    		httpSession.removeAttribute("user");
    		model.addAttribute("tip","登陆失败 不存在此用户名或密码!");
    		return "login";
    	}
	}
}

