<%@include file="../shared/flow-header.jsp"%>

<div class="container">
	<div class="row">

		<!-- column to display the personal details -->
		<div class="col-sm-6">

			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4>Personal Details</h4>
				</div>
				<div class="panel-body">
					<!-- code to display the personal details -->
					<div class="text-center">
						<h3>Name : <strong>${registerModel.user.firstName} ${registerModel.user.lastName}</strong></h3>
						<h4>Email : <strong>${registerModel.user.email}</strong></h4>
						<h4>Contact : <strong>${registerModel.user.contactNumber}</strong></h4>
						<h4>Role : <strong>${registerModel.user.role}</strong></h4>
					</div>	
				</div>
				<div class="panel-footer">
					<!-- anchor to move to the edit of personal details -->
					<a href="${flowExecutionUrl}&_eventId_personal" class="btn btn-primary">Edit</a>
				</div>
			</div>

		</div>

		<!-- column to display the address  -->
		<div class="col-sm-6">

			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4>Billing Address</h4>
				</div>
				<div class="panel-body">
					<!-- code to display the communication address -->
					<div class="text-center">
						<p>${registerModel.billing.addressLineOne}, </p>
						<p>${registerModel.billing.addressLineTwo} </p>
						<p>${registerModel.billing.city} -  ${registerModel.billing.postalCode}, </p>
						<p>${registerModel.billing.state}</p>
						<p>${registerModel.billing.country}</p>
					</div>
				</div>
				<div class="panel-footer">
					<!-- anchor to move to the edit of address -->
					<a href="${flowExecutionUrl}&_eventId_billing" class="btn btn-primary">Edit</a>
				</div>
			</div>

		</div>

	</div>

	<!-- to provide the confirm button after displaying the details -->
	<div class="row">
		<div class="col-sm-4 col-sm-offset-4">

			<div class="text-center">

				<!-- anchor to move to the success page -->
				<a href="${flowExecutionUrl}&_eventId_submit" class="btn btn-primary">Confirm</a>
			</div>

		</div>
	</div>
</div>

<%@include file="../shared/flow-footer.jsp"%>
