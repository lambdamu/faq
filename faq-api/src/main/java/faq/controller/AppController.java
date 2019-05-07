package faq.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/", produces = "application/json")
public class AppController {

	@RequestMapping("/login")
	public Principal login(Principal user) {
		return user;
	}

	@RequestMapping("/ping")
	public String ping() {
		return "1";
	}
}
