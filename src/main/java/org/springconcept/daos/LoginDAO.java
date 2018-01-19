package org.springconcept.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springconcept.models.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

// @Repository - Mapear essa classe para que o Spring a gerencie
//Foi implementada a interface UserDetailsService para que esse DAO seja utilizado pelas configurações do Spring Security no momento da autenticação
@Repository
public class LoginDAO implements UserDetailsService {

	// @PersistenceContext - Anotação resposnável por dizer para o Spring que ele
	// vai injetar o EntityManager.
	// O EntityManager é o serviço central responsável por todas as ações de
	// persistência das Entidades da aplicação.
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Usuario loadUserByUsername(String email) {
		Usuario usuario = manager.createQuery("select u from Usuario u where u.email = :email", Usuario.class)
				.setParameter("email", email).getSingleResult();

		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário com o email " + email + " não foi encontrado");
		}

		return usuario;
	}


}
