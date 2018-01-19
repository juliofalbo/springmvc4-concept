package org.springconcept.daos;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springconcept.models.Produto;
import org.springconcept.models.TipoPreco;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//@Repository - Mapear essa classe para que o Spring a gerencie
//@Transactional - Informar para o Spring que esse DAO precisa de uma transação gerenciada por ele
@Repository
@Transactional
public class ProdutoDAO {

	// @PersistenceContext - Anotação resposnável por dizer para o Spring que ele
	// vai injetar o EntityManager.
	// O EntityManager é o serviço central responsável por todas as ações de
	// persistência das Entidades da aplicação.
	@PersistenceContext
	private EntityManager manager;

	public void save(Produto produto) {
		manager.persist(produto);
	}

	public List<Produto> list() {
		return manager.createQuery("select distinct(p) from Produto p join fetch p.precos", Produto.class).getResultList();
	}

	// join fetch é utilizado para que o Hibernate entenda que os objetos
	// referenciados devem ser retornados também - Solução para o problema de Lazy
	// Load (Estilo de carregamento que não traz os filhos da Entidade populados)
	public Produto find(Long id) {
		return manager.createQuery("select distinct(p) from Produto p " + "join fetch p.precos preco where p.id = :id",
				Produto.class).setParameter("id", id).getSingleResult();
	}

	public BigDecimal sumPricesForTypePrice(TipoPreco tipoPreco) {
		TypedQuery<BigDecimal> query = manager.createQuery(
				"select sum(preco.valor) from Produto p " + "join p.precos preco where preco.tipo = :tipoPreco",
				BigDecimal.class);
		query.setParameter("tipoPreco", tipoPreco);

		return query.getSingleResult();
	}

}
