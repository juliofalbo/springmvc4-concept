<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:pageTemplate showNav="true">

	<main role="main"> <tags:jumbotron
		subtitle="Detalhe do Protudo" />
	<div class="container">

		<table class="table">
			<thead class="table-secondary">
				<tr>
					<th scope="col">T�tulo</th>
					<th scope="col">Descri��o</th>
					<th scope="col">P�ginas</th>
					<th scope="col">Lan�amento</th>
				</tr>
			</thead>
			<tr>
				<td>${produto.titulo}</td>
				<td>${produto.descricao}</td>
				<td>${produto.paginas}</td>
				<td><fmt:formatDate pattern="dd/MM/yyyy"
						value="${produto.dataLancamento.time}" /></td>
			</tr>
		</table>

		<hr>

		<!-- 			
				CSRF
				CSRF siginifica Cross-site Request Forgery, � um tipo de ataque inform�tico malicioso a um website no qual comandos n�o autorizados s�o transmitidos atrav�s de um utilizador em quem o website confia
				Em outras palavras � quando um outro site tenta fazer uma requisi��o para seu servidor.
				
				O Spring resolveu esse problema criando uma valida��o de token. 
				Podemos inserir um hidden em nossa aplica��o com o token gerado pelo Spring, por�m essa n�o � a melhor pr�tica.
				
				Ex: <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
				
				Ao utilizarmos a taglib form do Spring, ele nos prov� esse token automaticamente, sem a necessida de cria��o do hidden
			-->

		<form:form servletRelativeAction="/carrinho/add" method="POST">
			<input type="hidden" value="${produto.id}" name="produtoId" />

			<div class="card text-center">
				<div class="card-header"><strong>Pre�os</strong></div>
				<div class="card-body">

					<c:forEach items="${produto.precos}" var="preco" varStatus="status">

						<div>
							<div class="card bg-light mb-3">
								<div class="card-body">
									<label>${preco.tipo}</label> <input type="radio" id="tipo"
										name="tipo" value="${preco.tipo}" /> R$ ${preco.valor}
								</div>
							</div>
						</div>

					</c:forEach>

				</div>
				<div class="card-footer text-muted">
					<button type="submit" class="btn btn-primary">Comprar</button>
				</div>
			</div>

		</form:form>


	</div>
	</main>
</tags:pageTemplate>