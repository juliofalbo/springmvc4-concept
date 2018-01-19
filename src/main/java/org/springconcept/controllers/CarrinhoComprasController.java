package org.springconcept.controllers;

import org.springconcept.daos.ProdutoDAO;
import org.springconcept.models.CarrinhoCompras;
import org.springconcept.models.CarrinhoItem;
import org.springconcept.models.Produto;
import org.springconcept.models.TipoPreco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

// @Controller - Anotação responsável por informar ao Spring que esse Component é uma classe que possue métodos que processam requests numa aplicação web
// @RequestMapping("/carrinho") - Estratégia usada para não repetir /carrinho/... no RequestMapping dos métodos
// @Scope - Anotação responsável por controlar o ciclo de vida do Bean
// SCOPE_REQUEST significa que a cada request novo será criado um novo Bean
@Controller
@RequestMapping("/carrinho")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class CarrinhoComprasController {

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

	@Autowired
	private CarrinhoCompras carrinho;

	@RequestMapping("/add")
	public ModelAndView add(Long produtoId, TipoPreco tipo) {
		ModelAndView modelAndView = new ModelAndView("redirect:/carrinho");

		CarrinhoItem carrinhoItem = criaItem(produtoId, tipo);

		carrinho.add(carrinhoItem);

		return modelAndView;
	}

	private CarrinhoItem criaItem(Long produtoId, TipoPreco tipoPreco) {
		Produto produto = produtoDAO.find(produtoId);
		CarrinhoItem carrinhoItem = new CarrinhoItem(produto, tipoPreco);
		return carrinhoItem;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView itens() {
		return new ModelAndView("carrinho/itens");
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public ModelAndView remove(Long produtoId, TipoPreco tipoPreco) {
		ModelAndView modelAndView = new ModelAndView("redirect:/carrinho");
		
		carrinho.remove(produtoId, tipoPreco);
		return modelAndView;
	}

}
