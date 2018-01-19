package org.springconcept.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

//@Component - Anotação padrão que informa ao Spring que essa classe será gerenciada por ele e poderá ser injetada através do seu IoC em outros Components
//@Scope - Anotação responsável por controlar o ciclo de vida do Bean
//Por padrão o scope é o SCOPE_APPLICATION, isso significa que o Spring vai criar um único Bean para todo o projeto, independe de sessão.
/**
 * proxyMode.TARGET_CLASS = Responsável por isentar a responsabilidade de ter um
 * scopo definido nas classes que injetam a entidade em evidência. O Spring se
 * torna o responsável por gerenciar as dependencias da entidade.
 * 
 * Conceito de Proxy Por default, quando implementamos alguma interface, o
 * Spring vai criar o Proxy usando o mecanismo padrão do Java. Só para tentar
 * deixar claro. Basicamente a implementação de um Proxy exige que uma classe
 * seja construída em tempo de execução, herdando da classe base ou/e
 * implementando as interfaces dessa mesma classe base. O mecanismo padrão do
 * Java só fornece a segunda opção.
 * 
 * https://domineospring.wordpress.com/2016/02/11/saiba-um-pouco-mais-sobre-os-proxies-do-spring/
 */

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CarrinhoCompras implements Serializable {

	// A interface Serializable possibilita que o estado atual do Bean em evidência
	// seja salva em um arquivo externo e futuramente o mesmo seja recuperado
	// O serialVersionUID é utilizado para que caso o Modelo tenha sua estrutura
	// alterada, o mesmo não se perca na hora de 'deserializar'
	private static final long serialVersionUID = 1L;

	// TreeMap - Os dados no TreeMap são ordenados pela chave e apenas os valores
	// armazenados podem ser nulos, mas a chave não.
	// LinkdHashMap - Ordenação baseado na ordem de inserção dos elementos, apenas
	// os valores armazenados podem ser nulos, mas a chave não.
	// HashMap - Não tem ordenação específica e permite valores nulos tanto para a
	// chave quanto para os valores armazenados.
	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<CarrinhoItem, Integer>();

	public void add(CarrinhoItem item) {
		itens.put(item, getQuantidade(item) + 1);
	}

	public Integer getQuantidade(CarrinhoItem item) {
		if (!itens.containsKey(item)) {
			itens.put(item, 0);
		}
		return itens.get(item);
	}

	// A função reduce aplica reiteradamente uma operação (no nosso caso, somar
	// todos os elementos de uma stream) a cada elemento até gerar um resultado.
	// No âmbito da programação funcional costuma ser chamada de operação fold
	// porque é relacionada a ação de dobrar repetidamente um extenso papel (a
	// stream) até restar um pequeno quadrado, o resultado da operação de dobrado.
	// 0 é nosso valor inicial,
	public int getQuantidade() {
		return itens.values().stream().reduce(0, (next, count) -> next + count);
	}

	public Collection<CarrinhoItem> getItens() {
		return itens.keySet();
	}

	public BigDecimal getTotal(CarrinhoItem item) {
		return item.getTotal(getQuantidade(item));
	}

	public BigDecimal getTotal() {
		BigDecimal total = BigDecimal.ZERO;
		for (CarrinhoItem item : itens.keySet()) {
			total = total.add(getTotal(item));
		}
		return total;
	}

	public void remove(Long produtoId, TipoPreco tipoPreco) {
		Produto produto = new Produto();
		produto.setId(produtoId);
		itens.remove(new CarrinhoItem(produto, tipoPreco));
	}

}
