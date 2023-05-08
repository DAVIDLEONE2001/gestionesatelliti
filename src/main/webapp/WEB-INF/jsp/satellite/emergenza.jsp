<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%><!doctype html>
<html lang="it" class="h-100">
<head>

<!-- Common imports in pages -->
<jsp:include page="../header.jsp" />

<title>Visualizza Elemento</title>

</head>
<body class="d-flex flex-column h-100">

	<!-- Fixed navbar -->
	<jsp:include page="../navbar.jsp"></jsp:include>


	<!-- Begin page content -->
	<main class="flex-shrink-0">
		<div class="container">

			<div class='card'>
				<div class='card-header'>
					<h5>Visualizza dettaglio</h5>
				</div>


				 <div class='card-body'>
				 
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Numero voci totali: </dt>
							  <dd class="col-sm-9">${emergenza_list_all_attribute_size}</dd>
					    	</dl>
					    	
					    	
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Numero Voci che verranno modificate: </dt>
							  <dd class="col-sm-9">${emergenza_list_attribute_size}</dd>
					    	</dl>
					    	
					    	
					    </div>
				<!-- end card body -->


				<div class='card-footer'>
					<form:form method="post"
						action="${pageContext.request.contextPath}/satellite/emergenza"
						 novalidate="novalidate">
						<input type="hidden" value="${delete_satellite_attr.id}"
							name="id"></input>
						<a href="${pageContext.request.contextPath}/home"
							class='btn btn-outline-secondary' style='width: 80px'> <i
							class='fa fa-chevron-left'></i> Back
						</a>

						<button type="submit" name="submit" id="submit"
							class="btn btn-danger">Conferma</button>

					</form:form>
				</div>
				<!-- end card -->
			</div>


			<!-- end container -->
		</div>

	</main>

	<!-- Footer -->
	<jsp:include page="../footer.jsp" />
</body>
</html>