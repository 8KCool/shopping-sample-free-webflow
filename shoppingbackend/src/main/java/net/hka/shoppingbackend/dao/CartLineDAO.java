package net.hka.shoppingbackend.dao;

import java.util.List;

import net.hka.shoppingbackend.dto.Cart;
import net.hka.shoppingbackend.dto.CartLine;
import net.hka.shoppingbackend.dto.OrderDetail;
import net.hka.shoppingbackend.exception.CartLineNotFoundException;

public interface CartLineDAO {

	public CartLine get(int id) throws CartLineNotFoundException;
	public List<CartLine> list(int cartId);
		
	public boolean add(CartLine cartLine);
	public boolean update(CartLine cartLine);
	public boolean remove(CartLine cartLine);
	
	// fetch the CartLine based on cartId and productId
	public CartLine getByCartAndProduct(int cartId, int productId);		
				
	// list of available cartLine
	public List<CartLine> listAvailable(int cartId) throws CartLineNotFoundException;
	
	
	// updating the cart
	boolean updateCart(Cart cart);
	
	// adding order details
	boolean addOrderDetail(OrderDetail orderDetail);

	
}
