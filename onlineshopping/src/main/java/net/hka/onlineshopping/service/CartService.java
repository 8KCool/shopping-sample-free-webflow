package net.hka.onlineshopping.service;

import java.util.List;

import net.hka.shoppingbackend.dto.CartLine;

/**
 * Apply the business logic for cart handling operations
 * 
 * @author Hany Kamal
 */
public interface CartService {
	
	/*
	 * Set the maximum limit for product quantity in each cart line
	 * */
	public static final int MAX_PRODUCT_COUNT = 3;

	
	/*
	 * Fetch list of cart lines by current cart in the session
	 */
	public List<CartLine> getCartLines();

	
	
	/*
	 * For the cart line updating its count, unit price, and total and for the
	 * cart updating its grand total
	 */	
	public String updateCartLineById(int cartLineId, int count);


	/*
	 * Remove the cart line
	 */
	public String removeCartLine(int cartLineId);

		
	/*
	 * Add new single cart line by product id 
	 */
	public String addCartLine(int productId);

	
	
	/*
	 * Validate cart lines And do the necessary updates to both cart and cart lines
	 */
	public String validateCartLines();

	
	/*
	 * Return a user message based on the result value
	 * */
	public static String getUserMessage(String result){
		String message = null;
		
		switch (result) {			
		case "unavailable":
			message = "Product quantity is not available!";
			break;
		case "productNotFound":
			message = "Product is not available, somthing went wrong please try again!";
			break;
		case "cartLineNotFound":
			message = "Cart line is not available, somthing went wrong please try again!";
			break;
		case "maximum":
			message = "Maximum limit for the item has been reached!";
			break;
		case "outOfRange":
			message = "Product Count should be minimum 1 and maximum " + CartService.MAX_PRODUCT_COUNT +  "!";
			break;	
		case "added":
			message = "Product has been successfully added inside cart!";			
			break;	
		case "updated":
			message = "Cart has been updated successfully!";			
			break;
		case "modified":
			message = "One or more items inside cart has been modified!";
			break;			
		case "deleted":
			message = "CartLine has been successfully removed!";
			break;			
		}
		
		return message;
	}
}
