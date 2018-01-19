package org.springconcept.conf;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

// @Profile - Anotação responsável por informar ao Spring que quando o profile
// de prod for utilizado, esse será o DataSource.
@Profile("prod")
public class JPAProductionConfiguration {

	@Autowired
	private Environment environment;

	@Bean
	public Properties aditionalProperties() {
		Properties jpaProperties = new Properties();
		// Configuração necessária para configurar o Dialeto que o Hibernate usuário
		// para se comunicar com o MySQL
		jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
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

	@Bean
	private DataSource dataSource() throws URISyntaxException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");

		URI dbUrl = new URI(environment.getProperty("DATABASE_URL"));
		dataSource.setUrl("jdbc:postgresql://"+dbUrl.getHost()+":"+dbUrl.getPort()+dbUrl.getPath());
	    dataSource.setUsername(dbUrl.getUserInfo().split(":")[0]);
	    dataSource.setPassword(dbUrl.getUserInfo().split(":")[1]);
		return dataSource;
	}

}
