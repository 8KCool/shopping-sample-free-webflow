package net.hka.onlineshopping.model;

import java.io.Serializable;

import net.hka.shoppingbackend.dto.Address;
import net.hka.shoppingbackend.dto.User;

/**
 * Composite Model for Register web flow
 * */
public class RegisterModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Private fields
	 * */
	private User user;
	private Address billing;
	
	
	/*
	 *  setters and getters	
	 */
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Address getBilling() {
		return billing;
	}
	public void setBilling(Address billing) {
		this.billing = billing;
	}
}
