package org.springconcept.controllers;

import java.util.concurrent.Callable;

import org.springconcept.models.CarrinhoCompras;
import org.springconcept.models.DadosPagamento;
import org.springconcept.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//@Controller - Anotação responsável por informar ao Spring que esse Component é uma classe que possue métodos que processam requests numa aplicação web
//@RequestMapping("/pagamento") - Estratégia usada para não repetir /pagamento/... no RequestMapping dos métodos
@Controller
@RequestMapping("/pagamento")
public class PagamentoController {

	// @Autowired - Anotação responsável por injetar o PerguntaDAO no Controller
	// Quando utilizamos o @AutoWired, pedimos para o Spring uma instância do objeto
	// que foi anotado. Esse recurso é chamado de injeção de dependência e está
	// disponível para qualquer Bean do Spring.
	// É preferível esse tipo de abordagem, justamente para manter o conceito da
	// inversão de controle que basicamente joga toda a responsabilidade de
	// instanciar ou inicializar qualquer tipo de configuração necessária de um
	// objeto para o servidor, nesse caso, o Spring.
	@Autowired
	private CarrinhoCompras carrinho;

	// RestTemplate - Classe responsável por realizar uma requisição via REST
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MailSender sender;

	// @AuthenticationPrincipal - Anotação responsável por informar que o Spring
	// Security vai injetar o usuário logado como parâmetro
	// Callable - Permite a execução do método assíncrono gerenciado pelo Spring
	@RequestMapping(value = "/finish", method = RequestMethod.POST)
	public Callable<ModelAndView> finish(@AuthenticationPrincipal Usuario usuario, RedirectAttributes model) {

		// Criação de classe anônima Java 8
		return () -> {
			String uri = "http://book-payment.herokuapp.com/payment";

			try {
				// Necessário criar um objeto com chave e valor* para que a integração seja
				// realizada com sucesso.
				// * - A API RestTemplate converte automaticamente o objeto para JSON.
				// Necessário a API do Jackson para que o mesmo converta automaticamente a
				// resposta da requisição (HttpMessageConverter)
				String response = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotal()),
						String.class);

				enviaEmailCompraProduto(usuario);

				model.addFlashAttribute("sucesso", response);
				return new ModelAndView("redirect:/produtos");
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				model.addFlashAttribute("falha", "Valor maior que o permitido!");
				return new ModelAndView("redirect:/produtos");
			}
		};

	}

	private void enviaEmailCompraProduto(Usuario usuario) {

		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject("Compra Finalizada com Sucesso");
		email.setTo(usuario.getEmail());
//		email.setTo("julio.falbo.rj@gmail.com");
		email.setText("Compra aprovada com sucesso no valor de " + carrinho.getTotal());
		email.setFrom("compras@juliofalbo.tech");

		sender.send(email);
	}

}
