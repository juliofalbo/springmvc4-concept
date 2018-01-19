<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:pageTemplate showNav="true">

	<jsp:attribute name="modal">
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">Spring MVC4</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        Essa aplica��o foi criada com o objetivo de explorar esse t�o poderoso framework que � o Spring MVC e suas melhores pr�ticas!
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-success" data-dismiss="modal">Fechar</button>
		      </div>
		    </div>
		  </div>
		</div>
		
		<script type="text/javascript">
		    $(window).on('load',function(){
		        $('#myModal').modal('show');
		    });
		</script>
	</jsp:attribute>

	<jsp:body>
		<main role="main"> 
			<tags:jumbotron subtitle="Home" />
			<div class="container">
				<div class="row">
					<c:forEach items="${produtos}" var="produto" varStatus="status">
		
						<div class="col-md-4">
							<h2>${produto.titulo}</h2>
							<p>${produto.descricao }</p>
							<p>
								P�ginas: <strong>${produto.paginas}</strong>
							</p>
							<p>
								<a class="btn btn-secondary" role="button"
									href="${spring:mvcUrl('PC#detail').arg(0, produto.id).build()}">Detalhes
									&raquo;</a>
							</p>
						</div>
		
					</c:forEach>
				</div>
			</div>
		</main>
	</jsp:body>
	
</tags:pageTemplate>

