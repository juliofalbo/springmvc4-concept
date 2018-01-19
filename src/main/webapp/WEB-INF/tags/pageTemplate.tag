<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<!-- Cria um atributo na nova tag -->
<%@ attribute name="showNav" required="true" %>

<!-- Cria a posibilidade desse template receber um fragmento, que nada mais é que uma parte de código que será injetada aqui -->
<%@ attribute name="modal" fragment="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<%@include file="/WEB-INF/views/layout/header.jsp"%>

	<body>
		<c:if test="${showNav}">
			<%@include file="/WEB-INF/views/layout/nav.jsp"%>
		</c:if>
	
		<jsp:doBody />
	
		<%@include file="/WEB-INF/views/layout/footer.jsp"%>
	</body>
	
	<!-- Tag responsável por injetar o um código externo -->
	<jsp:invoke fragment="modal"></jsp:invoke>

</html>