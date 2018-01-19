package org.springconcept.models;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

//Notação responsável por informar que essa classe faz parte de uma outra entidade e não é uma entidade em si
@Embeddable
public class Preco {

	private BigDecimal valor;
	private TipoPreco tipo;

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public TipoPreco getTipo() {
		return tipo;
	}

	public void setTipo(TipoPreco tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return this.tipo.name() + " - " + this.valor;
	}

}