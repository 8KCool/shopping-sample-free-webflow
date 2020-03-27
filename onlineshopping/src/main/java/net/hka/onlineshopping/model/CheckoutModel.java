package net.hka.onlineshopping.model;

import java.io.Serializable;
import java.util.List;

import net.hka.shoppingbackend.dto.Address;
import net.hka.shoppingbackend.dto.Cart;
import net.hka.shoppingbackend.dto.CartLine;
import net.hka.shoppingbackend.dto.OrderDetail;
import net.hka.shoppingbackend.dto.User;

/**
 * Composite Model for the Checkout web flow
 * */
public class CheckoutModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Private fields
	 */
	private User user;
	private Address shipping;
	private Cart cart;
	private List<CartLine> cartLines;
	private OrderDetail orderDetail;
	private double checkoutTotal;

	
	/*
	 * setters and getters
	 */
	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public double getCheckoutTotal() {
		return checkoutTotal;
	}

	public void setCheckoutTotal(double checkoutTotal) {
		this.checkoutTotal = checkoutTotal;
	}

	public List<CartLine> getCartLines() {
		return cartLines;
	}

	public void setCartLines(List<CartLine> cartLines) {
		this.cartLines = cartLines;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Address getShipping() {
		return shipping;
	}

	public void setShipping(Address shipping) {
		this.shipping = shipping;
	}

}
