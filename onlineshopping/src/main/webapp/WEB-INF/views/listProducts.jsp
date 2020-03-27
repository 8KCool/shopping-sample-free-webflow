<div class="container">
	<div class="row">
		<!-- Display sidebar -->
		<div class="col-md-3">
			<%@include file="./shared/sidebar.jsp"%>
		</div>

		<!-- Display actual products -->
		<div class="col-md-9">
			<!-- Added breadcrumb component -->
			<div class="row">
				<div class="col-lg-12">
					<c:if test="${userClickAllProducts == true}">
						<script type="text/javascript">
							window.categoryId = '';
						</script>
						<ol class="breadcrumb">
							<li><a href="${contextRoot}/home">Home</a></li> &nbsp;
							<li class="active">All Products</li>
						</ol>
					</c:if>
					<c:if test="${userClickCategoryProducts == true}">
						<script type="text/javascript">
							window.categoryId = '${category.id}';
						</script>
						<ol class="breadcrumb">
							<li><a href="${contextRoot}/home">Home</a></li> &nbsp;
							<li class="active">Category</li> &nbsp;
							<li class="active">${category.name}</li>
						</ol>
					</c:if>
				</div>
			</div>

			<!-- Added datatable component to display products -->
			<div class="row">
			
				<div class="col-xs-12">
					<div class="container-fluid">
						<div class="table-responsive">
							<table id="productListTable"
								class="table table-striped table-bordered">
								<thead>
		
									<tr>
										<th></th>
										<th>Name</th>
										<th>Brand</th>
										<th>Price</th>
										<th>Qty. Available</th>
										<th></th>
		
									</tr>
		
								</thead>
		
		
								<tfoot>
		
									<tr>
										<th></th>
										<th>Name</th>
										<th>Brand</th>
										<th>Price</th>
										<th>Qty. Available</th>
										<th></th>
		
									</tr>
		
								</tfoot>
		
							</table>
						</div> 
					</div>
					
				</div>
			</div>

		</div>

	</div>
</div>