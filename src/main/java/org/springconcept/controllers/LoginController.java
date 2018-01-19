package org.springconcept.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@Controller - Anotação responsável por informar ao Spring que esse Component é uma classe que possue métodos que processam requests numa aplicação web
@Controller
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "loginForm";
	}

}
