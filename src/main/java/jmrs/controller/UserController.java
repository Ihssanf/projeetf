package jmrs.controller;

import jmrs.entity.User;
import jmrs.enums.Role;
import jmrs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@Controller
@Scope("prototype")
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private jmrs.service.UserService userService;

	private jmrs.entity.User user;

	private List<jmrs.entity.User> userList = new ArrayList<jmrs.entity.User>();

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView modelAndView = new ModelAndView("user-list");

		userList = userService.findAll(jmrs.entity.User.class);
		modelAndView.addObject("userList", userList);

		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView("user-addEdit");
		modelAndView.addObject(ACTION, ACTION_ADD);
		modelAndView.addObject("roles", jmrs.enums.Role.values());
		return modelAndView;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(jmrs.entity.User user) {
		ModelAndView modelAndView = new ModelAndView("user-addEdit");
		modelAndView.addObject(ACTION, ACTION_EDIT);
		modelAndView.addObject("roles", jmrs.enums.Role.values());

		user = userService.findOne(jmrs.entity.User.class, user.getUserId());
		modelAndView.addObject("user", user);

		return modelAndView;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(jmrs.entity.User user) {
		ModelAndView modelAndView = new ModelAndView("redirect:/user/list");

		if (user != null) {
			userService.save(user);
		}

		return modelAndView;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(jmrs.entity.User user) {
		ModelAndView modelAndView = new ModelAndView("redirect:/user/list");

		if (user != null) {
			userService.update(user);
		}

		return modelAndView;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView delete(jmrs.entity.User user) {
		ModelAndView modelAndView = new ModelAndView("redirect:/user/list");

		if (user != null) {
			userService.delete(jmrs.entity.User.class, user.getUserId());
		}

		return modelAndView;
	}

	public jmrs.entity.User getUser() {
		return user;
	}

	public void setUser(jmrs.entity.User user) {
		this.user = user;
	}

	public List<jmrs.entity.User> getUserList() {
		return userList;
	}

	public void setUserList(List<jmrs.entity.User> userList) {
		this.userList = userList;
	}
}
