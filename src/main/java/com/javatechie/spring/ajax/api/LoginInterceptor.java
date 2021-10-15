package com.javatechie.spring.ajax.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.javatechie.spring.ajax.api.dto.User;

/**
 * 登录验证拦截
 *
 */
@Controller
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

	Logger log = Logger.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String basePath = request.getContextPath();
		String path = request.getRequestURI();
		log.info("basePath:"+basePath);
		log.info("path:"+path);
		
		// 如果登录了，会把用户信息存进session
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if(user==null){
			log.info("尚未登录，跳转到登录界面");
			response.sendRedirect(request.getContextPath()+"/login");
			return false;
		}
		log.info("用户已登录,userName:"+user);
		return true;
	}
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler,ModelAndView model) throws Exception{
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                            Object handler,Exception ex) throws Exception{
    }
}
