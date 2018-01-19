<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:pageTemplate showNav="true">

	<main role="main"> 
		<tags:jumbotron subtitle="Lista de Produtos" />
		<div class="container">
			<div class="row">
				<table class="table table-hover">
					<tr>
						<th scope="col">Título</th>
						<th scope="col">Descrição</th>
						<th scope="col">Páginas</th>
						<th scope="col">Preços</th>
					</tr>
					<c:forEach items="${produtos}" var="produto" varStatus="status">
						<tr>
							<td>${produto.titulo}</td>
							<td>${produto.descricao }</td>
							<td>${produto.paginas}</td>
							<td>${produto.precos}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</main>

</tags:pageTemplate>