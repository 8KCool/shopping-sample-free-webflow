$(function() {

	// for adding a loader
	$(window).load(function() {		
		setTimeout(function() {
			$(".se-pre-con").fadeOut("slow");
		}, 500);
	});

	// for handling CSRF token
	var token = $('meta[name="_csrf"]').attr('content');
	var header = $('meta[name="_csrf_header"]').attr('content');

	if ((token != undefined && header != undefined)
			&& (token.length > 0 && header.length > 0)) {
		// set the token header for the ajax request
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});
	}

	// solving the active menu problem
	switch (menu) {

	case 'About Us':
		$('#about').addClass('active');
		break;
	case 'Contact Us':
		$('#contact').addClass('active');
		break;
	case 'All Products':
		$('#listProducts').addClass('active');
		break;
	case 'Manage Product':
		$('#manageProduct').addClass('active');
		break;
	case 'Shopping Cart':
		$('#userModel').addClass('active');
		break;
	default:
		if (menu == "Home")
			break;
		$('#listProducts').addClass('active');
		$('#a_' + menu).addClass('active');
		break;
	}

	// code for jquery dataTable
	var $table = $('#productListTable');

	// execute the below code only where we have this table
	if ($table.length) {
		// console.log('Inside the table!');

		var jsonUrl = '';
		if (window.categoryId == '') {
			jsonUrl = window.contextRoot + '/json/data/all/products';
		} else {
			jsonUrl = window.contextRoot + '/json/data/category/'
					+ window.categoryId + '/products';
		}

		$table
				.DataTable({

					lengthMenu : [ [ 3, 5, 10, -1 ],
							[ '3 Records', '5 Records', '10 Records', 'ALL' ] ],
					pageLength : 5,
					ajax : {
						url : jsonUrl,
						dataSrc : ''
					},
					columns : [
							{
								data : 'code',
								bSortable : false,
								mRender : function(data, type, row) {

									return '<img src="' + window.contextRoot
											+ '/resources/images/' + data
											+ '.jpg" class="dataTableImg"/>';

								}
							},
							{
								data : 'name'
							},
							{
								data : 'brand'
							},
							{
								data : 'unitPrice',
								mRender : function(data, type, row) {
									return '&#8377; ' + data
								}
							},
							{
								data : 'quantity',
								mRender : function(data, type, row) {

									if (data < 1) {
										return '<span style="color:red">Out of Stock!</span>';
									}

									return data;

								}
							},
							{
								data : 'id',
								bSortable : false,
								mRender : function(data, type, row) {

									var categoryPath = (window.categoryId != '') ? '/category'
											: '';
									var href = window.contextRoot + '/show'
											+ categoryPath + '/product/' + data;

									var str = '<a href="'
											+ href
											+ '"'
											+ ' class="btn btn-primary"><span class="glyphicon glyphicon-eye-open"></span></a> &#160;';

									if (typeof (userRole) != 'undefined'
											&& userRole !== 'ADMIN') {
										if (row.quantity < 1) {
											str += '<a href="javascript:void(0)" class="btn btn-success disabled"><span class="glyphicon glyphicon-shopping-cart"></span></a>';
										} else {

											str += '<a href="'
													+ window.contextRoot
													+ '/cart/add/product/'
													+ data
													+ '" class="btn btn-success"><span class="glyphicon glyphicon-shopping-cart"></span></a>';
										}
									} else {
										str += '<a href="'
												+ window.contextRoot
												+ '/manage'
												+ '/product/'
												+ data
												+ '" class="btn btn-warning"><span class="glyphicon glyphicon-pencil"></span></a> &#160;';

									}

									return str;

								}

							} ]
				});
	}

	// list of all products for admin
	var $adminProductsTable = $('#adminProductsTable');

	if ($adminProductsTable.length) {

		var jsonUrl = window.contextRoot + '/json/data/admin/all/products';
		console.log(jsonUrl);

		$adminProductsTable
				.DataTable({
					lengthMenu : [ [ 10, 30, 50, -1 ],
							[ '10 Records', '30 Records', '50 Records', 'ALL' ] ],
					pageLength : 30,
					ajax : {
						url : jsonUrl,
						dataSrc : ''
					},
					columns : [
							{
								data : 'id'
							},

							{
								data : 'code',
								bSortable : false,
								mRender : function(data, type, row) {
									return '<img src="' + window.contextRoot
											+ '/resources/images/' + data
											+ '.jpg" class="dataTableImg"/>';
								}
							},
							{
								data : 'name'
							},
							{
								data : 'brand'
							},
							{
								data : 'unitPrice',
								mRender : function(data, type, row) {
									return '&#8377; ' + data
								}
							},
							{
								data : 'quantity',
								mRender : function(data, type, row) {

									if (data < 1) {
										return '<span style="color:red">Out of Stock!</span>';
									}

									return data;

								}
							},
							{
								data : 'active',
								bSortable : false,
								mRender : function(data, type, row) {
									var str = '';
									if (data) {
										str += '<label class="switch"> <input type="checkbox" value="'
												+ row.id
												+ '" checked="checked">  <div class="slider round"> </div></label>';

									} else {
										str += '<label class="switch"> <input type="checkbox" value="'
												+ row.id
												+ '">  <div class="slider round"> </div></label>';
									}

									return str;
								}
							},
							{
								data : 'id',
								bSortable : false,
								mRender : function(data, type, row) {

									var str = '';
									str += '<a href="'
											+ window.contextRoot
											+ '/manage'
											+ '/product/'
											+ data
											+ '" class="btn btn-warning"><span class="glyphicon glyphicon-pencil"></span></a> &#160;';

									return str;
								}
							} ],

					initComplete : function() {
						var api = this.api();
						api
								.$('.switch input[type="checkbox"]')
								.on(
										'change',
										function() {
											var checkbox = $(this);
											var checked = this.checked;
											var dText = (checked) ? 'You want to activate the Product?'
													: 'You want to de-activate the Product?';

											debugger;
											bootbox
													.confirm({
														size : 'medium',
														title : 'Product Activation/Deactivation',
														message : dText,
														callback : function(
																confirmed) {
															if (confirmed) {
																$
																		.ajax({
																			type : 'GET',
																			url : window.contextRoot
																					+ '/manage/product/'
																					+ checkbox
																							.prop('value')
																					+ '/activation',
																			timeout : 100000,
																			success : function(
																					data) {
																				bootbox
																						.alert(data);
																			},
																			error : function(
																					e) {
																				bootbox
																						.alert('ERROR: '
																								+ e);
																				// display(e);
																			}
																		});
															} else {
																checkbox
																		.prop(
																				'checked',
																				!checked);
															}
														}
													});
										});

					}
				});
	}

	// jQuery Validation Code

	// methods required for validation

	function errorPlacement(error, element) {
		// Add the 'help-block' class to the error element
		error.addClass("help-block");

		// add the error label after the input element
		error.insertAfter(element);

		// add the has-feedback class to the
		// parent div.validate in order to add icons to inputs
		element.parents(".validate").addClass("has-feedback");

	}

	// validating the category form element
	// fetch the form element
	$categoryForm = $('#categoryForm');

	if ($categoryForm.length) {

		$categoryForm
				.validate({
					rules : {
						name : {
							required : true,
							minlength : 3
						},
						description : {
							required : true
						}
					},
					messages : {
						name : {
							required : 'Please enter category name!',
							minlength : 'The category name should not be less than 3 characters!'
						},
						description : {
							required : 'Please enter category description!'
						}
					},
					errorElement : "em",
					errorPlacement : function(error, element) {
						// errorPlacement(error, element);

						// add the class of help-block
						error.addClass("help-block");

						// add the error element after the input element
						error.insertAfter(element);
					}
				}

				);

	}

	/* validating the loginform */
	// fetch the form element
	$loginForm = $('#loginForm');

	if ($loginForm.length) {

		$loginForm.validate({
			rules : {
				username : {
					required : true,
					email : true

				},
				password : {
					required : true
				}
			},
			messages : {
				username : {
					required : 'Please enter your email!',
					email : 'Please enter a valid email address!'
				},
				password : {
					required : 'Please enter your password!'
				}
			},
			errorElement : "em",
			errorPlacement : function(error, element) {
				// Add the 'help-block' class to the error element
				error.addClass("help-block");

				// add the error label after the input element
				error.insertAfter(element);
			}
		}

		);

	}

	/*------*/
	/* for fading out the alert message after 3 seconds */
	$alert = $('.alert');
	if ($alert.length) {
		setTimeout(function() {
			$alert.fadeOut('slow');
		}, 3000);
	}

	/*------*/
	/*
	 * handle refresh cart button 
	 * Note: we used a button instead of "href" link
	 * in this case to make our validation on product quantity limit much easier
	 */
	$('button[name="refreshCart"]')
			.click(
					function() {
						var cartLineId = $(this).attr('value');
						var countField = $('#count_' + cartLineId);

						// get the original Count value
						var originalCount = countField.attr('value');

						// get the current Count value
						var currentCount = countField.val();

						// do the checking only the count has changed
						if (currentCount !== originalCount) {
							// check if the quantity is within the specified
							// range
							if (currentCount < 1 || currentCount > 3) {
								// set the field back to the original field
								countField.val(originalCount);

								// display an error message
								bootbox
										.alert({
											size : 'medium',
											title : 'Error',
											message : 'Product Count should be minimum 1 and maximum 3!'
										});
							} else {
								// use the window.location.href property to send
								// the request to the server.
								// or forward to the controller by another mean
								var updateUrl = window.contextRoot + '/cart/'
										+ cartLineId + '/update?count='
										+ currentCount;
								window.location.href = updateUrl;
							}
						}
					});
});