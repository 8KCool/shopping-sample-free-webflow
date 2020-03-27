<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<div class="container">
	<!-- Product Form -->
	<div class="row">
		<c:if test="${not empty message}">
			<div class="col-xs-12">
				<div class="alert alert-success alert-dismissible">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					${message}
				</div>
			</div>
		</c:if>
			
		<div class="col-md-offset-2 col-md-8">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4>Product Management</h4>
				</div>
				
				<div class="panel-body">
					<!-- FORM ELEMENTS -->
					<sf:form class="form-horizontal" modelAttribute="product"
						action="${contextRoot}/manage/product" method="POST"
						enctype="multipart/form-data">
						<div class="form-group">
							<label class="control-label col-md-4" for="name">Product Name: </label>
							<div class="col-md-8">
								<sf:input type="text" path="name" id="name" placeholder="Enter Product Name" class="form-control" />
								<sf:errors path="name" cssClass="help-block" element="em"></sf:errors>
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-md-4" for="brand">Brand Name: </label>
							<div class="col-md-8">
								<sf:input type="text" path="brand" id="brand" placeholder="Enter Brand Name" class="form-control" />
								<sf:errors path="brand" cssClass="help-block" element="em"></sf:errors>
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-md-4" for="description">Product Description: </label>
							<div class="col-md-8">
								<sf:textarea path="description" id="description" rows="4" class="form-control" />
								<sf:errors path="description" cssClass="help-block" element="em"></sf:errors>
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-md-4" for="unitPrice">Unit Price: </label>
							<div class="col-md-8">
								<sf:input type="number" path="unitPrice" id="unitPrice" placeholder="Enter Unit Price In &#8377;" class="form-control" />
								<sf:errors path="unitPrice" cssClass="help-block" element="em"></sf:errors>
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-md-4" for="quantity">Quantity Available: </label>
							<div class="col-md-8">
								<sf:input type="number" path="quantity" id="quantity" placeholder="Enter Quantity Available" class="form-control" />
							</div>
						</div>
						
						<!-- File element to upload product image -->
						<div class="form-group">
							<label class="control-label col-md-4" for="file">Select an Image: </label>
							<div class="col-md-8">
								<sf:input type="file" path="file" id="file" class="form-control" />
								<sf:errors path="file" cssClass="help-block" element="em"></sf:errors>
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-md-4" for="categoryId">Select Category: </label>
							<div class="col-md-8">
								<sf:select path="categoryId" id="categoryId"  class="form-control" 
									items="${categories}"
									itemLabel="name"
									itemValue="id"
								/>
								<div class="text-right">
									<br/>			
									<button type="button" class="btn btn-warning btn-xs" data-toggle="modal" data-target="#categoryModal">Add New Category</button>
								</div>									
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-md-offset-4 col-md-8">
								<input type="submit" name="submit" id="submit" value="Submit" class="btn btn-primary" />
							</div>
						</div>
						
						<!-- hidden fields for editing purposes, using the same form to edit the product -->
						<div>
							<sf:hidden path="id"/>
							<sf:hidden path="code"/>
							<sf:hidden path="active"/>
							<sf:hidden path="supplierId"/>							
						</div>
					</sf:form>
				</div>
				
			</div>
		</div>
		
	</div>
	
	<!-- Category Modal -->
	<div class="modal fade" id="categoryModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="modalLabel">New Category</h4>
	      </div>
	      <div class="modal-body">
	        
	        <sf:form id="categoryForm" class="form-horizontal" modelAttribute="category" action="${contextRoot}/manage/category" method="POST">
	        	
       			<div class="form-group">
					<label for="category_name" class="control-label col-md-4">Name</label>
					<div class="col-md-8 validate">
						<sf:input type="text" path="name" id="category_name" class="form-control"
							placeholder="Category Name" /> 
					</div>
				</div>
       			
       			<div class="form-group">				
					<label for="category_description" class="control-label col-md-4">Description</label>
					<div class="col-md-8 validate">
						<sf:textarea path="description" id="category_description" class="form-control"
							placeholder="Enter category description here!" /> 
					</div>
				</div>	        	        
	        
	        
				<div class="form-group">				
					<div class="col-md-offset-4 col-md-4">					
						<input type="submit" name="submit" id="saveCategory" value="Save" class="btn btn-primary"/>						
					</div>
				</div>	        
	        </sf:form>
	      </div>
	    </div>
	  </div>
	</div>
		
	<!-- Products table -->
	<div class="row">
		<div class="col-xs-12">
			<h3>Available Products</h3>
		</div>
		
		<div class="col-xs-12">
			<div class="container-fluid">
				<div class="table-responsive">
					<table id="adminProductsTable" class="table table-condensed table-bordered">
								
						<thead>					
							<tr>					
								<th>Id</th>
								<th>&#160;</th>
								<th>Name</th>
								<th>Brand</th>
								<th>Unit Price</th>
								<th>Qty. Avail</th>
								<th>Activate</th>				
								<th>Edit</th>
							</tr>					
						</thead>
						<tbody>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tbody>
						<tfoot>
							<tr>					
								<th>Id</th>
								<th>&#160;</th>
								<th>Name</th>
								<th>Brand</th>
								<th>Unit Price</th>
								<th>Qty. Avail</th>
								<th>Activate</th>				
								<th>Edit</th>
							</tr>									
						</tfoot>
													
					</table>
				</div> 
			</div>			
		</div>
	</div>
	
</div>