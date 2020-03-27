<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${isSuccessEndState != true}">
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->		
			<div class="navbar-header">		
				<a class="navbar-brand" href="${flowExecutionUrl}&_eventId_home">Home</a>
			</div>		
		</div>
	</nav>
</c:if>