package org.springconcept.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//Informar ao Spring que ele será responsável por controlar as transações
@EnableTransactionManagement
public class JPAConfiguration {

	//Importante: Ao receber o DataSource por parâmetro, o Spring está gerenciando qual DataSource ele deve injetar de acordo com o Profile.
	//Se o método dataSource() fosse chamado diretamente (factoryBean.setDataSource(dataSource());) a configuração de Profile seria ignorada.
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Properties aditionalProperties) {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

		// JpaVendorAdapter é a interface implementada pelo HibernateJpaVendorAdapter
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		factoryBean.setJpaVendorAdapter(vendorAdapter);

		// Configuração necessária para realizar a conexão com o Banco de Dados MySQL
		factoryBean.setDataSource(dataSource);

		// Configurações Adicionais
		factoryBean.setJpaProperties(aditionalProperties);

		// Informar para o JPA onde estão as entidades do projeto
		factoryBean.setPackagesToScan("org.springconcept.models");

		return factoryBean;
	}

	@Bean
	@Profile("dev")
	public Properties aditionalProperties() {
		Properties jpaProperties = new Properties();
		// Configuração necessária para configurar o Dialeto que o Hibernate usuário
		// para se comunicar com o MySQL
		jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		// Configuração necessária para visualizar o sql gerado pelo Hibernate
		jpaProperties.setProperty("hibernate.show_sql", "true");

		// Configuração necessária para mapear como o Hibernate se comportará perante ao
		// banco
		// hbm = Hibernate Mapping
		// ddl = Data Definition Language
		// Opções:
		// validate: Valida o schema, não faz mudanças no banco de dados.
		// update: Faz update do schema.
		// create: Cria o schema, destruindo dados anteriores.
		// create-drop: Dropa o schema ao terminar a sessão.
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update");

		return jpaProperties;
	}

	// @Profile - Anotação responsável por informar ao Spring que quando o profile
	// de teste for utilizado, esse será o DataSource.
	@Bean
	@Profile("dev")
	private DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		dataSource.setUrl("jdbc:mysql://localhost:3306/springtest");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		return dataSource;
	}

	// Criando um Bean habilitado para gerenciar as transações
	// A partir desse bean o Spring fornecerá as transações para o EntityManager
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

}
