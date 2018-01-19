package org.springconcept.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;

//@Entity - Notação responsável por informar que esse Modelo será controlado pelo Hibernate
// GrantedAuthority - Interface implementada para que a mesma seja utilizada
// como Role do Spring Security
@Entity
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	public Role(String nome, Usuario usuario) {
		this.nome = nome;
		this.usuario = usuario;
	}

	public Role() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "UsuarioRole", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "USUARIO_ID"))
	private Usuario usuario;

	@Override
	public String getAuthority() {
		return this.nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
