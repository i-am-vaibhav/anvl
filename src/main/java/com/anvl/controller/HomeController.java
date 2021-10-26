/**
 * 
 */
package com.anvl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anvl.service.MessageService;

/**
 * @author Vaibhav
 *
 */
@RestController
@RequestMapping("/home")
public class HomeController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private MessageService service;

	private static String WELCOME_MSG = "welcome.msg";

	@GetMapping(value = "/")
	public String home() {
		log.debug("Home API");
		return service.getMsg(WELCOME_MSG);
	}

	@GetMapping(value = "/w")
	public String home(@RequestParam(defaultValue = "user") String name) {
		log.debug("Home API with request param :: {}", name);
		return new String(service.getMsg(WELCOME_MSG) + " " + name);
	}

	@GetMapping(value = "/w/{name}")
	public String welcome(@PathVariable String name) {
		log.debug("Home API with path variable :: {}", name);
		return new String(service.getMsg(WELCOME_MSG) + " " + name);
	}

}
