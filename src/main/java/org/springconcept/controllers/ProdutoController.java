package org.springconcept.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springconcept.daos.ProdutoDAO;
import org.springconcept.infra.FileSaver;
import org.springconcept.models.Produto;
import org.springconcept.models.TipoPreco;
import org.springconcept.validators.ProdutoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//@Controller - Anotação responsável por informar ao Spring que esse Component é uma classe que possue métodos que processam requests numa aplicação web
//@RequestMapping("/produtos") - Estratégia usada para não repetir /produtos/... no RequestMapping dos métodos
@Controller
@RequestMapping("/produtos")
public class ProdutoController {

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
	private FileSaver fileSaver;

	// Método responsável por iniciar as classes de validação (Validators)
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidator());
	}

	@RequestMapping("/form")
	public ModelAndView form(Produto produto) {
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tipos", TipoPreco.values());
		return modelAndView;
	}

	// @Valid - Anotação usada pra que o Spring valide esse objeto de acordo com a
	// Classe Validation criada.
	// Importante deixar o BindResult logo agós a entidade anotada com o @Valid.
	// @CacheEvict - Anotação responsável por limpar determinado cache ao final da
	// execução do método
	@RequestMapping(method = RequestMethod.POST)
	@CacheEvict(value = "produtosHome", allEntries = true)
	public ModelAndView save(MultipartFile capa, @Valid Produto produto, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return form(produto);
		}

		String anexoPath = fileSaver.write("imagens", capa);
		produto.setCapaPath(anexoPath);

		produtoDAO.save(produto);
		attributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
		return new ModelAndView("redirect:produtos");
	}
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {
		List<Produto> produtos = produtoDAO.list();
		// Envia os parametros adicionados para determinada view
		ModelAndView modelAndView = new ModelAndView("produtos/list");
		modelAndView.addObject("produtos", produtos);
		return modelAndView;
	}

	@RequestMapping("/detail/{id}")
	public ModelAndView detail(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("produtos/detail");
		Produto produto = produtoDAO.find(id);
		modelAndView.addObject("produto", produto);
		return modelAndView;
	}

	// @ResponseBody - Informar para o Spring que o retorno será o objeto convertido para JSON
	// A lib responsável por realizar essa transformação é o Jackson, caso a mesma não esteja no projeto, deria erro
	//Codigo comentado pois foi implantando o padrão contentNegotiationViewResolver (AppWebConfiguration)
//	@RequestMapping("/{id}")
//	@ResponseBody
//	public Produto detailJSON(@PathVariable("id") Long id) {
//		Produto produto = produtoDAO.find(id);
//		return produto;
//	}

	//Não necessário pois as Exception estão sendo tratados pelo ExceptionHandlerController 
//	@ExceptionHandler(Exception.class)
//	public String exceptionHandler() {
//		return "error";
//	}
	
}
