package org.springconcept.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springconcept.models.Usuario;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// @Repository - Mapear essa classe para que o Spring a gerencie
//Foi implementada a interface UserDetailsService para que esse DAO seja utilizado pelas configurações do Spring Security no momento da autenticação
@Repository
@Transactional
public class UsuarioDAO {

	// @PersistenceContext - Anotação resposnável por dizer para o Spring que ele
	// vai injetar o EntityManager.
	// O EntityManager é o serviço central responsável por todas as ações de
	// persistência das Entidades da aplicação.
	@PersistenceContext
	private EntityManager manager;

	public void save(Usuario usuario) {
		manager.persist(usuario);
	}

}
