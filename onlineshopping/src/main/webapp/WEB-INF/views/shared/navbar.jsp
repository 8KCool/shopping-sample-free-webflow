<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${contextRoot}/home">Online
				Shopping</a>
		</div>
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<!-- <li class="nav-item active"><a class="nav-link" href="#">Home
						<span class="sr-only">(current)</span>
				</a></li>  -->
				<li class="nav-item" id="about"><a class="nav-link" href="${contextRoot}/about">About</a></li>
				<li class="nav-item" id="contact"><a class="nav-link" href="${contextRoot}/contact">Contact</a></li>
				<li class="nav-item" id="listProducts"><a class="nav-link" href="${contextRoot}/show/all/products">View Products</a>
				</li>
				
				<!-- Manage products menu part. it is available only for administrators -->
				<security:authorize access="hasAuthority('ADMIN')">
					<li class="nav-item" id="manageProduct"><a class="nav-link" href="${contextRoot}/manage/product">Manage Product</a>
					</li>
				</security:authorize>	
			</ul>
			
			<!-- Right rendered navigation menus -->
			<ul class="nav navbar-nav navbar-right">
				<!-- login & logout menu part. it is available only for anonymous visitors -->
				<security:authorize access="isAnonymous()">
					<li class="nav-item-right" id="register"><a class="nav-link" href="${contextRoot}/register">Sign Up</a></li>
					<li class="nav-item-right" id="login"><a class="nav-link" href="${contextRoot}/login">Login</a></li>
				</security:authorize>

				<!-- logged user menu info, cart, and logout part. it is available only for authenticated visitors -->
				<security:authorize access="isAuthenticated()">
					<li class="dropdown" id="userModel">
					
						<!-- user menu info part -->
						<a class="btn btn-default dropdown-toggle" href="javascript:void(0)"
							id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="true"> ${userModel.fullName}&#160;<span class="caret"></span>
						</a>
						
						<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
							<!-- cart menu part. it is available only for authorized users -->
							<security:authorize access="hasAuthority('USER')">
								<li id="cart">
									<a href="${contextRoot}/cart/show"> 
										<span class="glyphicon glyphicon-shopping-cart"></span>&#160;
										<span class="badge">${userModel.cart.cartLines}</span> - &#8377;
										${userModel.cart.grandTotal}
									</a>
								</li>
								<li role="separator" class="divider"></li>
							</security:authorize>
							
							<!-- logout menu part -->
							<li id="logout"><a href="${contextRoot}/logout">Logout</a></li>
						</ul>
					</li>
				</security:authorize>

			</ul>
		</div>
	</div>
</nav>
<script>
	window.userRole = '${userModel.role}';
</script>