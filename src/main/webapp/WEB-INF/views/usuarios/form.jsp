<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:pageTemplate showNav="true">

	<main role="main"> 
		<tags:jumbotron subtitle="Login" />
		<div class="container">
	
			<!-- spring:mvcUrl é usado para reconhercer o Controller -->
			<!-- o Spring reconhece automaticamente as iniciais do seu Controller, por isso foi colocado apenas o IC (ItensController) -->
			<!-- o # funciona como se fosse um ., indicando qual o método daquele controller deverá ser chamado -->
			<form:form action="${spring:mvcUrl('UC#save').build()}" method="POST"
				commandName="usuario">
	
				<div class="form-row">
					<div class="form-group col-md-6">
						<label>Nome</label>
						<form:input class="form-control" path="nome" />
						<form:errors path="nome" />
					</div>
					<div class="form-group col-md-6">
						<label>Email</label>
						<form:input class="form-control" type="text" path="email" />
						<form:errors path="email" />
					</div>
					<div class="form-group col-md-6">
						<label>Senha</label>
						<form:input class="form-control" type="password" path="senha" />
						<form:errors path="senha" />
					</div>
				</div>
	
				<button type="submit" class="btn btn-primary">Cadastrar</button>
			</form:form>
		</div>
	</main>
</tags:pageTemplate>