package net.hka.onlineshopping.model;

import java.io.Serializable;

import net.hka.shoppingbackend.dto.Cart;

/**
 * Composite Model to store authenticated user required fields, once the user logs in
 * */
public class UserModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String fullName;
	private String role;
	private Cart cart;
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
		
}
