<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<%@include file="../shared/flow-header.jsp"%>

<div class="container">

	<div class="row">

		<div class="col-md-6 col-md-offset-3">

			<div class="panel panel-primary">

				<div class="panel-heading">
					<h4>Sign Up - Personal</h4>
				</div>


				<div class="panel-body">
					<!-- FORM ELEMENTS -->
					<sf:form method="POST" class="form-horizontal"
						modelAttribute="user" id="registerForm">
						<div class="form-group">
							<label class="control-label col-md-4">First Name</label>
							<div class="col-md-8">
								<sf:input type="text" path="firstName" class="form-control"
									placeholder="Enter First Name" />
								<sf:errors path="firstName" cssClass="help-block" element="em"></sf:errors>	
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-4">Last Name</label>
							<div class="col-md-8">
								<sf:input type="text" path="lastName" class="form-control"
									placeholder="Enter Last Name" />
								<sf:errors path="lastName" cssClass="help-block" element="em"></sf:errors>	
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-4">Email</label>
							<div class="col-md-8">
								<sf:input type="text" path="email" class="form-control"
									placeholder="abc@zyx.com" />
								<sf:errors path="email" cssClass="help-block" element="em"></sf:errors>	
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-4">Contact Number</label>
							<div class="col-md-8">
								<sf:input type="text" path="contactNumber" class="form-control"
									placeholder="XXXXXXXXXX" maxlength="10" />
								<sf:errors path="contactNumber" cssClass="help-block" element="em"></sf:errors>	
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-4">Password</label>
							<div class="col-md-8">
								<sf:input type="password" path="password" class="form-control"
									placeholder="Enter Password" />
								<sf:errors path="password" cssClass="help-block" element="em"></sf:errors>	
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-md-4">Confirm Password</label>
							<div class="col-md-8">
								<sf:input type="password" path="confirmPassword" class="form-control"
									placeholder="Re-enter Password" />
								<sf:errors path="confirmPassword" cssClass="help-block" element="em"></sf:errors>	
							</div>
						</div>

						<!-- radio button using bootstrap class of radio-inline -->
						<div class="form-group">
							<label class="control-label col-md-4">Select Role</label>
							<div class="col-md-8">
								<c:forEach var="userRole" items="${userRoles}" varStatus="typeStatus">
									<label class="radio-inline"> 
											<c:if test="${typeStatus.count == 1}">
												<sf:radiobutton path="role" value="${userRole}" checked="checked"/> 
											</c:if>
											<c:if test="${typeStatus.count != 1}">
												<sf:radiobutton path="role" value="${userRole}"/> 
											</c:if>
											
											${userRole.getValueAsLabel()}
									</label>
								</c:forEach>
							</div>
						</div>

						<div class="form-group">
							<div class="col-md-offset-4 col-md-8">
								<!-- submit button for moving forward to the billing -->
								<button type="submit" name="_eventId_billing" id="goToNextBilling" class="btn btn-primary">
									Next - Billing <span class="glyphicon glyphicon-chevron-right"></span>
								</button>	
							</div>
						</div>

					</sf:form>
				</div>
			</div>
		</div>
	</div>

</div>

<%@include file="../shared/flow-footer.jsp"%>
