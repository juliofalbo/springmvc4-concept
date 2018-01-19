package org.springconcept.conf;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DataSourceConfigurationTest {

	//@Profile - Anotação responsável por informar ao Spring que quando o profile de teste for utilizado, esse será o DataSource. 
	@Bean
	@Profile("test")
	public DataSource dataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		dataSource.setUrl("jdbc:mysql://localhost:3306/springtest_test");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");

		return dataSource;
	}

}
