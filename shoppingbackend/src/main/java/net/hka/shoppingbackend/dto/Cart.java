package net.hka.shoppingbackend.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Cart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * Private fields
	 * */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	@Column(name = "grand_total")
	private double grandTotal;
	@Column(name = "cart_lines")
	private int cartLines;

	/*
	 *  linking the cart with a user with one to one relation mapping
	 *  and without mappedBy that mean the user_id column will be created inside the cart table
	 */
	@OneToOne
	//@JoinColumn(name = "uid") // to override the hibernate generated column name which in our case will by user_id(table_identityColumnName)
	private User user;
	
	/*
	 * setters and getters
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public int getCartLines() {
		return cartLines;
	}

	public void setCartLines(int cartLines) {
		this.cartLines = cartLines;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	/* toString for debugging
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cart [id=" + id + ", grandTotal=" + grandTotal + ", cartLines=" + cartLines + "]";
	}
	
	
	
	
}
