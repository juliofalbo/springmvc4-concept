<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:pageTemplate showNav="true">

	<main role="main"> 
		<tags:jumbotron subtitle="Cadastro de Produtos" />
		<div class="container">
	
			<!-- spring:mvcUrl é usado para reconhercer o Controller -->
			<!-- o Spring reconhece automaticamente as iniciais do seu Controller, por isso foi colocado apenas o IC (ItensController) -->
			<!-- o # funciona como se fosse um ., indicando qual o método daquele controller deverá ser chamado -->
			<form:form action="${spring:mvcUrl('PC#save').build()}" method="POST"
				commandName="produto" enctype="multipart/form-data">
	
				<div class="form-row">
					<div class="form-group col-md-6">
						<label>Título</label>
						<form:input class="form-control" path="titulo" />
						<form:errors path="titulo" />
					</div>
					<div class="form-group col-md-6">
						<label>Páginas</label>
						<form:input class="form-control" type="text" path="paginas" />
						<form:errors path="paginas" />
					</div>
				</div>
	
				<div class="form-group">
					<label>Data de lançamento</label>
					<form:input class="form-control" path="dataLancamento" />
					<form:errors path="dataLancamento" />
				</div>
	
				<div class="form-group">
					<div class="custom-file">
						<input type="file" class="custom-file-input" name="capa">
						<label class="custom-file-label">Escolha um Arquivo...</label>
					</div>
				</div>
	
				<div class="form-group">
					<label>Descrição</label>
					<form:textarea class="form-control" path="descricao" />
					<form:errors path="descricao" />
				</div>
	
				<c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
					<div class="form-group">
						<form:input type="hidden" path="precos[${status.index}].tipo"
							value="${tipoPreco}" />
	
						<label>${tipoPreco}</label>
						<form:input class="form-control"
							path="precos[${status.index}].valor" placeholder="R$" />
					</div>
				</c:forEach>
	
				<button type="submit" class="btn btn-primary">Cadastrar</button>
			</form:form>
		</div>
	</main>
</tags:pageTemplate>