package com.javatechie.spring.ajax.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	LoginInterceptor loginInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 登录拦截器
		registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/login", "/js/**",
				"/html/**", "/images/**", "/css/**");
	}
	
    @Override  
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {  
        configurer.enable();  
    } 
    
    @Bean  
    public InternalResourceViewResolver viewResolver() {  
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();  
        resolver.setPrefix("/templates/");  
        resolver.setSuffix(".html");  
        resolver.setViewClass(JstlView.class);  
        return resolver;  
    } 
    
    @Override  
    public void addViewControllers(ViewControllerRegistry registry) {}  
}
