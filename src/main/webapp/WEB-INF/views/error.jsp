<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:pageTemplate showNav="true">

	<main role="main"> <tags:jumbotron subtitle="Erro" />
	<div class="container">
		<div class="row">
			<div class="alert alert-danger alert-dismissible fade show"
				role="alert">
				<strong>Erro!</strong> ${exception.message}
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>


			<!-- 
		        Mensagem: ${exception.message}
		        <c:forEach items="${exception.stackTrace}" var="stk">
		            ${stk}
		        </c:forEach>    
		    -->

		</div>
	</div>
	</main>

</tags:pageTemplate>

