package org.springconcept.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

//Notação responsável por informar que esse Modelo será controlado pelo Hibernate
@Entity
public class Produto {

	// @Id - Anotação responsável por informar que o atributo abaixo é uma chave
	// primária.
	// @GeneratedValue - Anotação responsável por informar ao JPA que o próprio BD
	// será responsável pela geração da chave primária.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String titulo;
	private String descricao;
	private int paginas;

	// Anotação responsável por criar uma ligação entre uma determinada entidade com
	// um objeto que é apenas uma extensão da mesma (não se trata de outra entidade)
	@ElementCollection
	private List<Preco> precos = new ArrayList<Preco>();

	// Anotação utilizada por formatação de datas
	// @DateTimeFormat(pattern="dd/MM/yyyy") - Não necessário informar o pattern
	// pois as formatações
	// foram configuradas no bean mvcConversionService configurado na classe
	// AppWebConfiguration.
	@DateTimeFormat
	private Calendar dataLancamento;

	private String capaPath;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getPaginas() {
		return paginas;
	}

	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}

	public List<Preco> getPrecos() {
		return precos;
	}

	public void setPrecos(List<Preco> precos) {
		this.precos = precos;
	}

	public Calendar getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Calendar dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public String getCapaPath() {
		return capaPath;
	}

	public void setCapaPath(String capaPath) {
		this.capaPath = capaPath;
	}

	// Método responsável por retornar um código hash de um objeto, facilitando
	// assim a busca em Collections
	// "A função de hashcode deve retornar valores iguais para objetos iguais"
	// O método contains utiliza primeiro o hashCode e depois o equals para Hashs
	// (HashMap, LinkedHashMap)
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	// Por padrão o equals só retorna true se o objeto for exatamente a mesma
	// instância!
	// "Um objeto é igual a outro caso seja a mesma instância na memória."
	// O método contains utiliza o equals para ArrayList
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public BigDecimal precoPara(TipoPreco tipoPreco) {
		return precos.stream().filter(preco -> preco.getTipo().equals(tipoPreco)).findFirst().get().getValor();
	}

}
