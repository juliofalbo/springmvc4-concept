<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:pageTemplate showNav="true">

	<main role="main"> 
		<tags:jumbotron subtitle="Carrinho de Compras" />
		<div class="container">
	
			<div>${sucesso}</div>
	
			<table>
				<tr>
					<td>Título</td>
					<td>Preço</td>
					<td>Quantidade</td>
					<td>Total</td>
					<td>Ação</td>
				</tr>
				<c:forEach items="${carrinhoCompras.itens}" var="item"
					varStatus="status">
					<tr>
						<td>${item.produto.titulo}</td>
						<td>${item.preco}</td>
						<td>${carrinhoCompras.getQuantidade(item)}</td>
						<td>${carrinhoCompras.getTotal(item)}</td>
						<td>
							<form:form action="${spring:mvcUrl('CCC#remove').arg(0, item.produto.id).arg(1, item.tipoPreco).build()}"
								method="POST">
								<input type="submit" value="Excluir">
							</form:form>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="4">${carrinhoCompras.total}</td>
				</tr>
				<tr>
					<td>
						<form:form action="${spring:mvcUrl('PC#finish').build()}" method="POST">
							<input type="submit" value="Finalizar">
						</form:form>
					</td>
				</tr>
			</table>
		</div>
	</main>
</tags:pageTemplate>