package net.hka.shoppingbackend.dao;

import java.util.List;

import net.hka.shoppingbackend.dto.Address;
import net.hka.shoppingbackend.dto.User;

public interface UserDAO {

	/*
	 *  user related operations
	 */
	User getByEmail(String email);
	User get(int id);

	boolean add(User user);
	
	
	/*
	 *  adding and updating a new address
	 */
	boolean addAddress(Address address);
	boolean updateAddress(Address address);
	
	
	/*
	 *  address related operations
	 */
	Address getAddress(int addressId);
	Address getBillingAddress(int userId);
	List<Address> listShippingAddresses(int userId);
		
}
