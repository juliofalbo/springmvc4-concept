package org.springconcept.conf;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// Configuração responsável para que o Spring reconheça os Controllers do
		// SpringWeb
		return new Class[] { SecurityConfiguration.class, AppWebConfiguration.class, JPAConfiguration.class,
				JPAProductionConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {};
	}

	@Override
	protected String[] getServletMappings() {
		// Configuração para que o servidor reconheça que a partir do caminho
		// determinado abaixo, o Spring irá controlar as requisições.
		return new String[] { "/" };
	}

	// // Método responsável por criar os filtros para o Spring
	// @Override
	// protected Filter[] getServletFilters() {
	// // Configurando o Encode para UTF-8
	// // CharacterEncodingFilter characterEncodingFilter = new
	// // CharacterEncodingFilter();
	// // characterEncodingFilter.setEncoding("UTF-8");
	//
	// // OpenEntityManagerInViewFilter - Filtro responsável por informar ao Spring
	// que
	// // a Session do EntityManager ficará aberta até a execução da JSP - Solução
	// para LAZY INI. Excep.
	// //Problema: O Hibernate vai realizar uma nova consulta para cada filho da
	// entidade, ou seja, vai abrir varias transações!
	// return new Filter[] { new OpenEntityManagerInViewFilter() };
	// }

	// Configuração necessário para que o Controlle reconheça os arquivos Multipart
	// enviados pela JSP
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}

	// Configuração responsável por informar ao Spring algo que será executado ao
	// inciar a aplicação
//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		super.onStartup(servletContext);
//		// Informando ao Spring que o profile a ser utilizado quando a aplicação subir é
//		// o dev
//		servletContext.addListener(RequestContextListener.class);
//		servletContext.setInitParameter("spring.profiles.active", "dev");
//	}

}
