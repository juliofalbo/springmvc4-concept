package org.springconcept.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.springconcept.controllers.HomeController;
import org.springconcept.daos.ProdutoDAO;
import org.springconcept.infra.FileSaver;
import org.springconcept.models.CarrinhoCompras;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.common.cache.CacheBuilder;

// @EnableWebMvc - Habilitando do Spring MVC
// @ComponentScan - Informando o pacote das Classes que são gerenciadas pelo Spring
// @EnableCaching - Habilitar o gerenciamento de Cache para a Aplicação
@EnableWebMvc
@ComponentScan(basePackageClasses = { HomeController.class, ProdutoDAO.class, FileSaver.class, CarrinhoCompras.class })
@EnableCaching
public class AppWebConfiguration extends WebMvcConfigurerAdapter {

	// @Bean - Todo método que é gerenciado pelo Spring

	// Método responsável por identificar o caminha das views
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();

		// A pasta de views está dentro de WEB-INF pois por padrão, as jsps que se
		// encontram dentro dessa pasta não podem ser acessadas diretamente pela URL
		internalResourceViewResolver.setPrefix("/WEB-INF/views/");
		// Remove a necessidade de colocar a extensão .jsp no retorno dos métodos dos
		// Controllers
		internalResourceViewResolver.setSuffix(".jsp");

		// Permite que todos os Beans fiquem disponíveis como Atributos nas JSP's
		// Não é uma boa prática pois isso pode confundir o desenvolvedor na hora de
		// nomear seus Beans e além de ocupar memoria com elementos desnecessários
		// internalResourceViewResolver.setExposeContextBeansAsAttributes(true);

		// A boa prática é utilizar o método setExposedContextBeanNames, informando qual
		// Bean ficará disponível nas JSP's, informando seu nome com a primeira letra
		// minúscula
		internalResourceViewResolver.setExposedContextBeanNames("carrinhoCompras");

		return internalResourceViewResolver;
	}

	// Método responsável por configurar a engine de de mensagens do Spring
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource bundleMessageSource = new ReloadableResourceBundleMessageSource();
		bundleMessageSource.setBasename("/WEB-INF/messages");
		// bundleMessageSource.setDefaultEncoding("UTF-8");
		bundleMessageSource.setCacheSeconds(1);

		return bundleMessageSource;
	}

	// Método utilizado para que o Spring gerencie as conversões necessárias pelo
	// sistema.
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService defaultFormattingConversionService = new DefaultFormattingConversionService();

		DateFormatterRegistrar dateFormatterRegistrar = new DateFormatterRegistrar();
		dateFormatterRegistrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		dateFormatterRegistrar.registerFormatters(defaultFormattingConversionService);

		return defaultFormattingConversionService;
	}

	// Método utilizado para configurar o upload de arquivos.
	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

	// Método responsável por informar ao Spring que ele pode injetar um
	// RestTemplate em qualquer Component
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	// Método responsável por informar ao Spring como ele irá gerenciar o cache da
	// aplicação
	@Bean
	public CacheManager cacheManager() {
		// ConcurrentMapCacheManager - Gerenciador básico para testes em DEV

		// Criação das configurações do cache
		// Cache configurado para ficar ativo por 5 minutos e suportar até 100 elementos
		CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(5,
				TimeUnit.MINUTES);

		// Utilizando o cache do Google - Guava
		GuavaCacheManager guavaCacheManager = new GuavaCacheManager();
		guavaCacheManager.setCacheBuilder(builder);

		return new ConcurrentMapCacheManager();
	}

	// Padrão criado para verificar qual retorno dos métodos do Controller de acordo
	// com a URL chamada
	@Bean
	public ViewResolver contentNegotiationViewResolver(ContentNegotiationManager manager) {

		List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
		viewResolvers.add(internalResourceViewResolver());
		viewResolvers.add(new JSONViewResolver());

		ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
		contentNegotiatingViewResolver.setViewResolvers(viewResolvers);
		contentNegotiatingViewResolver.setContentNegotiationManager(manager);

		return contentNegotiatingViewResolver;
	}

	// Método criado para informar ao Spring que ele não gerenciará arquivos .css,
	// .js e etc...
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	// Método responsável por manipular os encoders das senhas
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Método responsável por gerenciar os Interceptadores do Spring
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}

	/**
	 * 
	 * Entre outras coisas, os cookies são utilizados pelos sites principalmente
	 * para identificar e armazenar informações sobre os visitantes. Eles são
	 * pequenos arquivos de texto que ficam gravados no computador do internauta e
	 * podem ser recuperados pelo site que o enviou durante a navegação.
	 * 
	 */
	// Método responsável por salvar o locale selecionado pelo usuário nos cookies
	@Bean
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver();
	}

	// Método responsável por manipular os encoders das senhas
	@Bean
	public MailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setHost("smtp.gmail.com");
		mailSender.setUsername("springmvcconceptteste@gmail.com");
		mailSender.setPassword("spring123");
		mailSender.setPort(587);
		mailSender.setProtocol("smtp");
		
		Properties mailProps = new Properties();
		mailProps.put("mail.smtp.auth", true);
		mailProps.put("mail.smtp.starttls.enable", true);
		mailProps.put("mail.smtp.quitwait", false);
		
		mailSender.setJavaMailProperties(mailProps);
		
		return mailSender;
	}

}
