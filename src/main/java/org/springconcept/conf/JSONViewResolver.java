package org.springconcept.conf;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

//Classe responsável por possibilitar ao Spring que o mesmo consiga retornar um objeto .json de acordo com a URL solicitada
public class JSONViewResolver implements ViewResolver {

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		MappingJackson2JsonView mappingJackson2JsonView = new MappingJackson2JsonView();
		mappingJackson2JsonView.setPrettyPrint(true);

		return mappingJackson2JsonView;
	}

}
