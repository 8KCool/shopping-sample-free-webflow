package net.hka.shoppingbackend.dto;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;


@Entity
@Table(name = "user_detail")
/* implementing "Serializable" interface to be able to store it in flow scope */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Private fields
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotBlank(message = "Please enter first name!")
	@Column(name = "first_name")
	private String firstName;
	@NotBlank(message = "Please enter last name!")
	@Column(name = "last_name")
	private String lastName;
	@NotBlank(message = "Please enter email address!")
	private String email;
	@NotBlank(message = "Please enter contact number!")
	@Column(name = "contact_number")
	private String contactNumber;
	
	//private String role;
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	@NotBlank(message = "Please enter password!")
	private String password;
	private boolean enabled = true;

	// confirm password transient field
	@Transient
	private String confirmPassword;

	
	/*
	 * Mapping with the cart, and by adding mappedBy attribute, we are telling
	 * Hibernate to give the ownership of the relation to the User Detail table
	 * as well as the Hibernate will not create a column for cart id 
	 * in the UserDetail table.
	 * 
	 * So we use the mappedBy to solve the conflict between the class representation and the database design.
	 * In our case we need to declare in instance of the cart object inside the User class, while in database design 
	 * we need to add only a column of user_id inside the cart table. 
	 */
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Cart cart;

	
	
	/*
	 * setters and getters
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	/*
	 * toString for debugging (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", contactNumber=" + contactNumber + ", role=" + role + ", password=" + password + ", enabled="
				+ enabled + "]";
	}

}
