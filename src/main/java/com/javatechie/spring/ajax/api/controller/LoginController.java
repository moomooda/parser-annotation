package com.javatechie.spring.ajax.api.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javatechie.spring.ajax.api.dao.UserDao;
import com.javatechie.spring.ajax.api.dto.User;

@RestController
public class LoginController {

	@Autowired
	UserDao userDao;

	/**
	 * 用户登录
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login")
	public ResponseEntity<Object> login(@RequestParam String userName, @RequestParam String password,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(1000 * 60 * 60);

		if (session.getAttribute("user") != null) {
			if (session.getAttribute("user").equals(userName)) {
				return new ResponseEntity<Object>("该用户已登录", HttpStatus.OK);
			}
		}

		User user = userDao.findByUserName(userName);

		if (user == null || !user.getPassword().equals(password)) {
			return new ResponseEntity<Object>("用户名或密码错误", HttpStatus.NOT_FOUND);
		} else {
			request.getSession().setAttribute("user", user); // 将用户信息放到session中
			return new ResponseEntity<Object>(user, HttpStatus.OK);
		}
	}

	/**
	 * 用户注销
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public ResponseEntity<Object> logout(HttpServletRequest request) {
		Enumeration em = request.getSession().getAttributeNames();
		while (em.hasMoreElements()) {
			request.getSession().removeAttribute(em.nextElement().toString());
		}
		return new ResponseEntity<Object>("退出成功", HttpStatus.OK);
	}

}
