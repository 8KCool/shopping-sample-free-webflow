package net.hka.shoppingbackend.exception;

import java.io.Serializable;

public class CartLineNotFoundException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public CartLineNotFoundException() {
		this("Cart Line is not available!");
	}
	
	public CartLineNotFoundException(String message) {
		this.message = System.currentTimeMillis() + ": " + message;
	}

	public String getMessage() {
		return message;
	}

}
