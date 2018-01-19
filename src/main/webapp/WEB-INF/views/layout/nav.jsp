<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#">
	  <img src="/assets/brand/bootstrap-solid.svg" width="30" height="30" class="d-inline-block align-top" alt="">
	    Spring
  </a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNavDropdown">
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" href="${spring:mvcUrl('HC#index').build()}">Home</a>
      </li>
      <security:authorize access="hasRole('ROLE_ADMIN')">
	      <li class="nav-item">
	        <a class="nav-link" href="${spring:mvcUrl('PC#list').build()}"><fmt:message key="nav.lista_de_produtos" /></a>
	      </li>
	      <li class="nav-item">
	        <a class="nav-link" href="${spring:mvcUrl('PC#form').build()}"><fmt:message key="nav.cadastro_de_produtos" /></a>
	      </li>
      </security:authorize>
    </ul>
    <ul class="navbar-nav ml-auto">
    	<li>
    		<a class="nav-link" href="${spring:mvcUrl('CCC#itens').build()}">
	    		<!-- Opção de passar parâmetro para o arquivo messages.properties
		    		<fmt:message key="nav.cadastro_de_produtos">
		    			<fmt:param value="${carrinhoCompras.quantidade}" />
		    		</fmt:message>
	    		-->
	    		<!-- Para passar mais de um argumento basta separá-los por vírgulas -->
	    		<spring:message code="nav.seu_carrinho" arguments="${carrinhoCompras.quantidade}" />
    		</a>
    	</li>
   		<security:authorize access="!isAuthenticated()">
    		<li>
				<a class="nav-link" href="${spring:mvcUrl('UC#form').build()}"><fmt:message key="nav.registrar" /></a>
			</li>
		</security:authorize>
		<security:authorize access="isAuthenticated()">
			<li>
    			<span class="nav-link"> Olá, <security:authentication property="principal.username"/></span>
	    	</li>
	    	<li>
				<a class="nav-link" href="<c:url value="/logout" />">Sair</a>
			</li>
		</security:authorize>
		<li>
			<a class="nav-link" href="?locale=pt_BR"><fmt:message key="nav.pt" /></a>
		</li>
		<li>
			<a class="nav-link" href="?locale=en_US"><fmt:message key="nav.en" /></a>
		</li>
    </ul>
  </div>
</nav>
