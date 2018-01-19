package org.springconcept.controllers;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springconcept.conf.AppWebConfiguration;
import org.springconcept.conf.DataSourceConfigurationTest;
import org.springconcept.conf.JPAConfiguration;
import org.springconcept.conf.SecurityConfiguration;
import org.springconcept.daos.ProdutoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//@RunWith - Anotação do JUnit que indical qual suite será responsável pelos testes
//SpringJUnit4ClassRunner classe do Spring resposável por executar os testes
//@ContextConfiguration - Anotação responsável por informar ao Spring onde estão os arquivos de configuração 
//@ActiveProfiles - Anotação responsável por informar ao Spring em qual profile a classe em evidência vai utilizar
//@WebAppConfiguration - Anotação responsável por fazer o Runner do Spring Test carregar os objetos necessários para uma aplicação Web
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JPAConfiguration.class, ProdutoDAO.class, DataSourceConfigurationTest.class,
		AppWebConfiguration.class, SecurityConfiguration.class })
@ActiveProfiles("test")
@WebAppConfiguration
public class ProdutoControllerTest {

	@Autowired
	private WebApplicationContext context;

	// Classe do Spring para simular requisições
	private MockMvc mockMvc;

	@Autowired
	private Filter springSecurityFilterChain;

	// Anotação responsável por informar ao JUnit códigos que devem ser executados
	// antes dos testes
	@Before
	public void setup() {
		// Criando o MockMvc de acordo com o contexto da aplicação
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).addFilter(springSecurityFilterChain).build();
	}

	// Anotação do JUnit responsável por informar ao mesmo que o método é um método
	// de Teste
	@Test
	public void deveRetornarParaHomeComOsLivros() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("produtos"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/views/home.jsp"));
	}

	// Anotação do JUnit responsável por informar ao mesmo que o método é um método
	// de Teste
	@Test
	public void somenteOAdminDeveAcessarOCadastroDeProdutos() throws Exception {
		UserRequestPostProcessor user = SecurityMockMvcRequestPostProcessors.user("user@gmail.com").password("123").roles("USER");
		mockMvc.perform(MockMvcRequestBuilders.get("/produtos/form").with(user))
				.andExpect(MockMvcResultMatchers.status().is(403));
	}

}
