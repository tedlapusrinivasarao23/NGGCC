package com.NGC.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/WebSitePaymnetController")
public class PaymentFilesController {
	
	@RequestMapping(value="/ccavResponseHandler.htm", method=RequestMethod.GET)
	public ModelAndView ccavResponseHandler(){
		ModelAndView modelAndView=new ModelAndView("ccavResponseHandler");
		return modelAndView;
	}
	
	@RequestMapping(value="/GetRSA.htm", method=RequestMethod.GET)
	public ModelAndView GetRSA(){
		ModelAndView modelAndView=new ModelAndView("GetRSA");
		return modelAndView;
	}

}
