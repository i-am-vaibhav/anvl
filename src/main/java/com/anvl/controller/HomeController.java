/**
 * 
 */
package com.anvl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vaibhav
 *
 */
@RestController
public class HomeController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private MessageSource source;

	@GetMapping(value = "/")
	public String home() {
		log.debug("Home API");
		return getWelcomeMsg();
	}

	@GetMapping(value = "/welcome")
	public String home(@RequestParam(defaultValue = "user") String name) {
		log.debug("Home API with request param :: {}", name);
		return new String(getWelcomeMsg() + " " + name);
	}

	@GetMapping(value = "/{name}")
	public String welcome(@PathVariable String name) {
		log.debug("Home API with path variable :: {}", name);
		return new String(getWelcomeMsg() + " " + name);
	}

	private String getWelcomeMsg() {
		return source.getMessage("welcome.msg", null, LocaleContextHolder.getLocale());
	}

}
