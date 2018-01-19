<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:pageTemplate showNav="true">

	<main role="main"> 
		<tags:jumbotron subtitle="Detalhe do Protudo ${produto.titulo}" />
		<div class="container">
	
	<div class="row">
			<img src="${produto.capaPath}">

			<table class="table">
  				<thead class="table-secondary">
					<tr>
						<th scope="col">Título</th>
						<th scope="col">Descrição</th>
						<th scope="col">Páginas</th>
						<th scope="col">Lançamento</th>
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

			<form:form servletRelativeAction="/carrinho/add" method="POST">
				<input type="hidden" value="${produto.id}" name="produtoId" />
				<c:forEach items="${produto.precos}" var="preco" varStatus="status">
					<label>${preco.tipo}</label>
					<input type="radio" id="tipo" name="tipo" value="${preco.tipo}" /> - R$ ${preco.valor} 
				</c:forEach>
				
				<br />
				
				<!-- 			
					CSRF
					CSRF siginifica Cross-site Request Forgery, é um tipo de ataque informático malicioso a um website no qual comandos não autorizados são transmitidos através de um utilizador em quem o website confia
					Em outras palavras é quando um outro site tenta fazer uma requisição para seu servidor.
					
					O Spring resolveu esse problema criando uma validação de token. 
					Podemos inserir um hidden em nossa aplicação com o token gerado pelo Spring, porém essa não é a melhor prática.
					
					Ex: <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
					
					Ao utilizarmos a taglib form do Spring, ele nos provê esse token automaticamente, sem a necessida de criação do hidden
				-->

				<button type="submit">Comprar</button>
			</form:form>
	</div>
		</div>
	</main>
</tags:pageTemplate>