package org.springconcept.controllers;

import java.util.List;

import org.springconcept.daos.ProdutoDAO;
import org.springconcept.models.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//@Controller - Anotação responsável por informar ao Spring que esse Component é uma classe que possue métodos que processam requests numa aplicação web
@Controller
public class HomeController {

	// @Autowired - Anotação responsável por injetar o PerguntaDAO no Controller
	// Quando utilizamos o @AutoWired, pedimos para o Spring uma instância do objeto
	// que foi anotado. Esse recurso é chamado de injeção de dependência e está
	// disponível para qualquer Bean do Spring.
	// É preferível esse tipo de abordagem, justamente para manter o conceito da
	// inversão de controle que basicamente joga toda a responsabilidade de
	// instanciar ou inicializar qualquer tipo de configuração necessária de um
	// objeto para o servidor, nesse caso, o Spring.
	@Autowired
	private ProdutoDAO produtoDAO;

	// @Cacheable - Anotação responsável por dizer ao Spring que o mesmo deve
	// cachear o retorno do método.
	/**
	 * Cache Área de memória onde é mantida uma cópia temporária de dados
	 * armazenados em um meio de acesso mais lento, com o objetivo de acelerar a
	 * recuperação dos dados.
	 */
	@RequestMapping("/")
	@Cacheable(value = "produtosHome")
	public ModelAndView index() {

		List<Produto> produtos = produtoDAO.list();
		ModelAndView model = new ModelAndView("home");
		model.addObject("produtos", produtos);

		return model;
	}

}
