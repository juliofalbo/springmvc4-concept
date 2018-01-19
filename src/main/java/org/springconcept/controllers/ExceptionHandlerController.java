package org.springconcept.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//@ControllerAdvice - Anotação responsável que esse Controller 'observa' todos os outros Controllers
@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(Exception.class)
	public ModelAndView exceptionHandler(Exception exception) {
		exception.printStackTrace();

		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("exception", exception);

		return modelAndView;
	}

}
