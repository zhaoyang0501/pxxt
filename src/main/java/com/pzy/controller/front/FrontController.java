package com.pzy.controller.front;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pzy.entity.Grade;
import com.pzy.entity.Report;
import com.pzy.entity.User;
import com.pzy.service.GradeService;
import com.pzy.service.ReportService;
import com.pzy.service.ScoreService;
import com.pzy.service.TimetableService;
import com.pzy.service.UserService;
import com.pzy.service.WorkService;
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
	@Autowired
	private TimetableService timetableService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private WorkService workService;
	@Autowired
	private ScoreService scoreService;
	@RequestMapping("index")
	public String index() {
		return "index";
	}
	
	@RequestMapping("center")
	public String center() {
		return "center";
	}
	@RequestMapping("mygrade")
	public String mygrade(Model model,HttpSession httpSession) {
		model.addAttribute("grades", gradeService.findAll());
		model.addAttribute("reports", reportService.findByUser((User)httpSession.getAttribute("user")));
		return "mygrade";
	}
	@RequestMapping("mywork")
	public String mywork(Model model,HttpSession httpSession) {
		model.addAttribute("work",workService.findByUser((User)httpSession.getAttribute("user")));
		return "mywork";
	}
	@RequestMapping("myscore")
	public String myscore(Model model,String key,HttpSession httpSession) {
		if(StringUtils.isNotBlank(key)){
			model.addAttribute("scores",scoreService.findByCategoryName(key,(User)httpSession.getAttribute("user")));
		}
		return "myscore";
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
	@RequestMapping("report")
	public String report(Model model) {
		model.addAttribute("grades", gradeService.findAll());
		return "report";
	}
	@RequestMapping("doreport")
	public String doreport(Model model,Report report,HttpSession httpSession) {
		report.setCreateDate(new Date());
		report.setUser((User)httpSession.getAttribute("user"));
		reportService.save(report);
		model.addAttribute("tip","报名成功!");
		model.addAttribute("grades", gradeService.findAll());
		return "report";
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
	@RequestMapping("viewgrade")
	public String viewgrade( Long id,Model model) {
		Grade grade=gradeService.find(id);
		model.addAttribute("grade", grade);
		model.addAttribute("week1", timetableService.findByGradeAndWeek(grade,1));
		model.addAttribute("week2", timetableService.findByGradeAndWeek(grade,2));
		model.addAttribute("week3", timetableService.findByGradeAndWeek(grade,3));
		model.addAttribute("week4", timetableService.findByGradeAndWeek(grade,4));
		model.addAttribute("week5", timetableService.findByGradeAndWeek(grade,5));
		model.addAttribute("week6", timetableService.findByGradeAndWeek(grade,6));
		model.addAttribute("week7", timetableService.findByGradeAndWeek(grade,7));
		return "viewgrade";
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

