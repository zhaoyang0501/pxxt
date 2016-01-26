package com.pzy.controller.front;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pzy.entity.Attence;
import com.pzy.entity.Grade;
import com.pzy.entity.News;
import com.pzy.entity.Report;
import com.pzy.entity.User;
import com.pzy.service.AttenceService;
import com.pzy.service.GradeService;
import com.pzy.service.NewsService;
import com.pzy.service.ReportService;
import com.pzy.service.ScoreService;
import com.pzy.service.TeacherService;
import com.pzy.service.TimetableService;
import com.pzy.service.UserService;
import com.pzy.service.WorkService;
/***
 * 前台，首页各种连接登陆等
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
	@Autowired
	private AttenceService attenceService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private NewsService newsService;
	/***
	 * 跳转到首页
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model) {
		List<News> news= newsService.findAll();
		model.addAttribute("news", news.size()==0?null:news.get(0));
		return "index";
	}
	/***
	 * 点击个人中心
	 * @return
	 */
	@RequestMapping("center")
	public String center() {
		return "center";
	}
	/***
	 * 我参与的班级
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("mygrade")
	public String mygrade(Model model,HttpSession httpSession) {
		model.addAttribute("grades", gradeService.findAll());
		model.addAttribute("reports", reportService.findByUser((User)httpSession.getAttribute("user")));
		return "mygrade";
	}
	
	
	/***
	 * 我的就业情况
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("mywork")
	public String mywork(Model model,HttpSession httpSession) {
		model.addAttribute("work",workService.findByUser((User)httpSession.getAttribute("user")));
		return "mywork";
	}
	
	/***
	 * 我的成绩
	 * @param model
	 * @param key
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("myscore")
	public String myscore(Model model,String key,HttpSession httpSession) {
		if(StringUtils.isNotBlank(key)){
			model.addAttribute("scores",scoreService.findByCategoryName(key,(User)httpSession.getAttribute("user")));
		}
		return "myscore";
	}
	
	/***
	 * 我的考勤
	 * @param model
	 * @param key
	 * @param httpSession
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("myattence")
	public String myattence(Model model,String key,HttpSession httpSession) throws ParseException {
		if(StringUtils.isNotBlank(key)){
			User user=	(User)httpSession.getAttribute("user");
			List<Attence> lists=attenceService.findAll(user, DateUtils.parseDate("2015-01-01", "yyyy-MM-dd"), DateUtils.parseDate("2019-01-01", "yyyy-MM-dd"));
			Integer count=0;
			for(Attence bean:lists){
				if("缺勤".equals(bean.getState()))
					count++;
			}
			model.addAttribute("attences",lists);
			model.addAttribute("count",count);
		}
		return "myattence";
	}
	
	
	/***
	 * 注册连接
	 * @return
	 */
	@RequestMapping("register")
	public String register() {
		return "register";
	}
	/***
	 * 点击注册
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("doregister")
	public String register(User user,Model model) {
		user.setCreateDate(new Date());
		model.addAttribute("tip","注册成功请登录！");
		userService.save(user);
		return "login";
	}
	/***
	 * 登陆连接
	 * @return
	 */
	@RequestMapping("login")
	public String login() {
		return "login";
	}
	/***
	 * 退出操作清空缓存等操作
	 * @param httpSession
	 * @param model
	 * @return
	 */
	@RequestMapping("loginout")
	public String loginout(HttpSession httpSession,Model model) {
		httpSession.removeAttribute("user");
		List<News> news= newsService.findAll();
		model.addAttribute("news", news.size()==0?null:news.get(0));
		return "index";
	}
	
	/***
	 * 报名连接
	 * @param model
	 * @return
	 */
	@RequestMapping("report")
	public String report(Model model) {
		model.addAttribute("grades", gradeService.findAll());
		return "report";
	}
	/***
	 * 点击报名
	 * @param model
	 * @param report
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("doreport")
	public String doreport(Model model,Report report,HttpSession httpSession) {
		report.setCreateDate(new Date());
		report.setUser((User)httpSession.getAttribute("user"));
		reportService.save(report);
		model.addAttribute("tip","报名成功!");
		model.addAttribute("grades", gradeService.findAll());
		return "report";
	}
	/***
	 * 教师风采
	 * @param model
	 * @return
	 */
	@RequestMapping("teacher")
	public String teacher(Model model) {
		model.addAttribute("teachers",teacherService.findAll());
		return "teacher";
	}
	/***
	 * 就业情况
	 * @param model
	 * @return
	 */
	@RequestMapping("work")
	public String work(Model model) {
		model.addAttribute("works",workService.findAll());
		return "work";
	}
	/***
	 * 公司简介
	 * @return
	 */
	@RequestMapping("about")
	public String about() {
		return "about";
	}
	/***
	 * 报班情况
	 * @param model
	 * @return
	 */
	@RequestMapping("grade")
	public String grade(Model model) {
		model.addAttribute("grades", gradeService.findAll());
		return "grade";
	}
	/***
	 * 查看班级详情 课表啥的
	 * @param id
	 * @param model
	 * @return
	 */
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
	/***
	 * 执行登陆动作
	 * @param user
	 * @param httpSession
	 * @param model
	 * @return
	 */
	@RequestMapping("dologin")
	public String dologin(User user,HttpSession httpSession,Model model) {
		User loginuser=userService.login(user.getUsername(), user.getPassword());
    	if(loginuser!=null){
    		List<News> news= newsService.findAll();
    		model.addAttribute("news", news.size()==0?null:news.get(0));
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

