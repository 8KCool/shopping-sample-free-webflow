<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:url var="css" value="/resources/css" />
<spring:url var="js" value="/resources/js" />
<spring:url var="images" value="/resources/images" />

<c:set var="contextRoot" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
<meta name="description" content="Online Shopping Website Using Spring MVC and Hibernate">
<meta name="author" content="Hany K Abdelsayed">

<!-- for ajax request we need to send csrf token along with the request -->
<meta name="_csrf" content="${_csrf.token}">
<meta name="_csrf_header" content="${_csrf.headerName}">

<title>Online Shopping - ${title}</title>

<!-- Bootstrap core CSS -->
<link href="${css}/bootstrap.min.css" rel="stylesheet">

<!-- Bootstrap Readable Theme -->
<link href="${css}/bootstrap-readable-theme.css" rel="stylesheet">

<!-- Bootstrap DataTables -->
<link href="${css}/dataTables.bootstrap.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="${css}/app.css" rel="stylesheet">

</head>

<body>

	<!-- for adding a loader -->
	<div class="se-pre-con"></div>
	
	
	<div class="wrapper">
		<!-- Navigation -->
		<%@include file="./shared/navbar.jsp"%>
	
		<!-- Page Content -->
		<div class="content">		
			<!-- Load only when user clicks "Home"  -->
			<c:if test="${userClickHome == true}">
				<%@include file="home.jsp"%>
			</c:if>
		
			<!-- Load only when user clicks "About"  -->
			<c:if test="${userClickAbout == true}">
				<%@include file="about.jsp"%>
			</c:if>
		
			<!-- Load only when user clicks "Contact"  -->
			<c:if test="${userClickContact == true}">
				<%@include file="contact.jsp"%>
			</c:if>
			
			<!-- Load all products or category products  -->
			<c:if test="${userClickAllProducts == true or userClickCategoryProducts == true}">
				<%@include file="listProducts.jsp"%>
			</c:if>
			
			<!--  Load only when user clicks "Show Product"  -->
			<c:if test="${userClickShowProduct == true}">
				<%@include file="singleProduct.jsp"%>
			</c:if>
			
			<!-- Load only when user clicks "Manage product"  -->
			<c:if test="${userClickManageProduct == true}">
				<%@include file="manageProduct.jsp"%>
			</c:if>
			
			<!-- Load only when user clicks manage product -->
			<c:if test="${userClickShowCart == true}">
				<%@include file="cart.jsp"%>
			</c:if>
		</div>
		
		<!-- Footer -->
		<div class="footer">	
			<%@include file="./shared/footer.jsp"%>
		</div>
	</div>
	
	<!-- jQuery -->
	<script src="${js}/jquery.js"></script>
	
	<!-- jQuery client-side Validator Plugin  -->
	<script src="${js}/jquery.validate.js"></script>

	<!-- Bootstrap core JavaScript -->
	<script src="${js}/bootstrap.min.js"></script>

	<!-- jQuery DataTable Plugin -->
	<script src="${js}/jquery.dataTables.js"></script>

	<!-- Bootstrap DataTable Script -->
	<script src="${js}/dataTables.bootstrap.js"></script>

	<!-- Bootbox -->
	<script src="${js}/bootbox.min.js"></script>

	<!-- My Script code -->
	<script type="text/javascript">
		window.menu = '${title}';
		window.contextRoot = '${contextRoot}';
	</script>
	<script src="${js}/app.js"></script>

</body>

</html>
