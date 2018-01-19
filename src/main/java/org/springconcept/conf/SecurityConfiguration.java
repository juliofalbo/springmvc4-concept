package org.springconcept.conf;

import org.springconcept.daos.LoginDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//@EnableWebSecurity - Anotação responsável por habilitar as configurações de acesso
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginDAO loginDAO;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Configuração para determinar quais urls serão bloqueadas ou liberadas para
		// acesso sem autenticação
		// Obs: A Ordem em que as configurações são criadas importam, por exemplo, no caso
		// abaixo estamos dizendo que vamos liberar todas as chamadas depois de
		// produtos/ (/produtos/**), porém o Spring vai bloquear o /produtos/form pois nós configuramos isso anteriormente.
		// A boa prática é mapear primeiro as rotas que serão bloqueadas
		http.authorizeRequests()
			.antMatchers("/produtos/form").hasRole("ADMIN")
			.antMatchers("/carrinho/**").permitAll()
			.antMatchers("/produtos").hasRole("ADMIN")
			.antMatchers("/produtos/**").permitAll()
			.antMatchers("/resources/**").permitAll()
			.antMatchers("/usuarios/**").permitAll()
			.antMatchers("/").permitAll()
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login").permitAll()
			.and().formLogin().defaultSuccessUrl("/produtos", true)
			//Configuração feita para o Spring permitir o logout por uma requisição GET
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	//Método responsável por configurar a engine de login
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(loginDAO)
			.passwordEncoder(new BCryptPasswordEncoder());
	}

}
