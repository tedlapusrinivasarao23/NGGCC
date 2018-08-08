package com.contact.mgmt.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@ComponentScan({"com.contact.mgmt.controller","com.NGC.website.controller"})
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport{
	
	@Override
	 protected void addViewControllers(ViewControllerRegistry registry) {
	  registry.addViewController("/NGCHome.htm").setViewName("NGC");
	 }

	 @Override
	 protected void configureViewResolvers(ViewResolverRegistry registry) {
	  registry.jsp("/WEB-INF/jsp/", ".jsp");
	 }	

}
