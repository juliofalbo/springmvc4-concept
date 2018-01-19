package org.springconcept.daos;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springconcept.builders.ProdutoBuilder;
import org.springconcept.conf.DataSourceConfigurationTest;
import org.springconcept.conf.JPAConfiguration;
import org.springconcept.models.Produto;
import org.springconcept.models.TipoPreco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

//@RunWith - Anotação do JUnit que indical qual suite será responsável pelos testes
//SpringJUnit4ClassRunner classe do Spring resposável por executar os testes
//@ContextConfiguration - Anotação responsável por informar ao Spring onde estão os arquivos de configuração 
//@ActiveProfiles - Anotação responsável por informar ao Spring em qual profile a classe em evidência vai utilizar
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JPAConfiguration.class, ProdutoDAO.class, DataSourceConfigurationTest.class })
@ActiveProfiles("test")
public class ProdutoDAOTest {

	@Autowired
	private ProdutoDAO produtoDAO;

	// Anotação do JUnit responsável por informar ao mesmo que o método é um método
	// de Teste
	@Test
	// @Transactional - Anotação responsável por criar as transações que serão
	// usadas pelo produtoDAO
	@Transactional
	public void deveSomarTodosOsPrecoPorTipoDeLivro() {

		// Builder criado para facilitar os testes
		List<Produto> livrosImpressos = ProdutoBuilder.newProduto(TipoPreco.IMPRESSO, BigDecimal.TEN).more(3)
				.buildAll();

		List<Produto> livrosEbook = ProdutoBuilder.newProduto(TipoPreco.EBOOK, BigDecimal.TEN).more(3).buildAll();

		// Salvando os livros criados pelo builder
		livrosImpressos.forEach(produtoDAO::save);
		livrosEbook.forEach(produtoDAO::save);

		// Chamando o método a ser testado
		BigDecimal sumEbooks = produtoDAO.sumPricesForTypePrice(TipoPreco.EBOOK);

		// Realizando o teste pelo JUnit
		Assert.assertEquals(new BigDecimal(40).setScale(2), sumEbooks);

	}

}
