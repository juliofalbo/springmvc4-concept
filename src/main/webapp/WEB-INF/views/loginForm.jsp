<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:pageTemplate showNav="true">

	<main role="main"> 
		<tags:jumbotron subtitle="Login" />
		<div class="container">
			<c:if test="${not empty sucesso}">
				<div class="alert alert-success alert-dismissible fade show"
					role="alert">
					<strong>Sucesso!</strong> ${sucesso} .
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
			</c:if>
	
			<c:if test="${not empty erro}">
				<div class="alert alert-danger alert-dismissible fade show"
					role="alert">
					<strong>Erro!</strong> ${erro} .
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
			</c:if>
	
			<security:authorize access="isAuthenticated()">
				<div>
					Você já está logado como
					<security:authentication property="principal.username" />
					!
				</div>
			</security:authorize>
	
			<security:authorize access="!isAuthenticated()">
				<form:form servletRelativeAction="/login" method="POST">
	
					<div class="form-row">
						<div class="form-group col-md-6">
							<label>Email</label> <input type="text" class="form-control"
								name="username" />
						</div>
						<div class="form-group col-md-6">
							<label>Senha</label> <input type="password" class="form-control"
								name="password" />
						</div>
					</div>
	
					<button type="submit" class="btn btn-primary">Logar</button>
				</form:form>
			</security:authorize>
		</div>
	</main>

</tags:pageTemplate>