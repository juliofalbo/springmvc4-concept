package org.springconcept.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springconcept.daos.UsuarioDAO;
import org.springconcept.models.Role;
import org.springconcept.models.Usuario;
import org.springconcept.validators.UsuarioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//@Controller - Anotação responsável por informar ao Spring que esse Component é uma classe que possue métodos que processam requests numa aplicação web
//@RequestMapping("/usuarios") - Estratégia usada para não repetir /produtos/... no RequestMapping dos métodos
@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	// @Autowired - Anotação responsável por injetar o PerguntaDAO no Controller
	// Quando utilizamos o @AutoWired, pedimos para o Spring uma instância do objeto
	// que foi anotado. Esse recurso é chamado de injeção de dependência e está
	// disponível para qualquer Bean do Spring.
	// É preferível esse tipo de abordagem, justamente para manter o conceito da
	// inversão de controle que basicamente joga toda a responsabilidade de
	// instanciar ou inicializar qualquer tipo de configuração necessária de um
	// objeto para o servidor, nesse caso, o Spring.
	@Autowired
	private UsuarioDAO usuarioDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// Método responsável por iniciar as classes de validação (Validators)
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new UsuarioValidator());
	}

	@RequestMapping("/form")
	public ModelAndView form(Usuario usuario) {
		return new ModelAndView("usuarios/form");
	}

	// @Valid - Anotação usada pra que o Spring valide esse objeto de acordo com a
	// Classe Validation criada.
	// Importante deixar o BindResult logo agós a entidade anotada com o @Valid.
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return form(usuario);
		}
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role("ROLE_ADMIN", usuario));
		usuario.setRoles(roles);
		
		usuarioDAO.save(usuario);

		attributes.addFlashAttribute("sucesso", "Usuário " + usuario.getNome() + " cadastrado com sucesso!");
		return new ModelAndView("redirect:login");
	}

}
