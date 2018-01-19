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
	
			<table class="table">
				<thead class="table-secondary">
					<tr>
						<th scope="col">Título</th>
						<th scope="col">Preço</th>
						<th scope="col">Quantidade</th>
						<th scope="col">Total</th>
						<th scope="col">Ação</th>
					</tr>
				</thead>
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
								<input type="submit" class="btn btn-danger btn-sm" value="Excluir">
							</form:form>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="table-dark" colspan="3">Total</td>
					<td colspan="2" class="table-dark"><strong>${carrinhoCompras.total}</strong></td>
				</tr>
				<tr>
					<td>
						<form:form action="${spring:mvcUrl('PC#finish').build()}" method="POST">
							<input type="submit" class="btn btn-success btn-lg" value="Finalizar">
						</form:form>
					</td>
				</tr>
			</table>
		</div>
	</main>
</tags:pageTemplate>