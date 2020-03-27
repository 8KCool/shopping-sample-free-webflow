package net.hka.onlineshopping.serviceimpl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.hka.onlineshopping.model.UserModel;
import net.hka.onlineshopping.service.CartService;
import net.hka.shoppingbackend.dao.CartLineDAO;
import net.hka.shoppingbackend.dao.ProductDAO;
import net.hka.shoppingbackend.dto.Cart;
import net.hka.shoppingbackend.dto.CartLine;
import net.hka.shoppingbackend.dto.Product;
import net.hka.shoppingbackend.exception.CartLineNotFoundException;
import net.hka.shoppingbackend.exception.ProductNotFoundException;

@Service("cartService")
public class CartServiceImpl implements CartService {

	@Autowired
	private CartLineDAO cartLineDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private HttpSession session;
	
	
	/*
	 * Get current cart for the authenticated user from the session
	 */
	private Cart getCart() {
		return ((UserModel) session.getAttribute("userModel")).getCart();
	}

	
	
	/*
	 * Fetch list of cart lines by current cart in the session
	 */
	public List<CartLine> getCartLines() {
		return cartLineDAO.list(this.getCart().getId());
	}

	
	
	/*
	 * For the cart line updating its count, unit price, and total and for the
	 * cart updating its grand total
	 */
	private String updateCartLine(CartLine cartLine, int count) {
		String response = null;

		// check if the cartLine product quantity is within the specified range
		if (cartLine.getProductCount() < 1 || cartLine.getProductCount() > MAX_PRODUCT_COUNT) {
			// set response to outOfRange
			response = "result=outOfRange";
		} else {
			// keep cartLine's total
			double oldTotal = cartLine.getTotal();

			// get cart line's product
			Product product = cartLine.getProduct();

			// check if that much quantity is available or not
			if (product.getQuantity() < count) {
				// set response to unavailable
				response = "result=unavailable";

			} else {
				// update the cart line
				cartLine.setProductCount(count);
				cartLine.setBuyingPrice(product.getUnitPrice());
				cartLine.setTotal(product.getUnitPrice() * count);
				cartLineDAO.update(cartLine);

				// update the cart
				Cart cart = this.getCart();
				cart.setGrandTotal(cart.getGrandTotal() - oldTotal + cartLine.getTotal());
				cartLineDAO.updateCart(cart);

				// set response to updated
				response = "result=updated";
			}
		}
		
		return response;
	}

	public String updateCartLineById(int cartLineId, int count) {
		String response = null;
		
		try {
			// fetch a single cart line by id
			CartLine cartLine = cartLineDAO.get(cartLineId);
			
			// delegate the business logic to the local method updateCartLine 
			// and set the response
			response = this.updateCartLine(cartLine, count);
			
		} catch (CartLineNotFoundException e) {
			// set response to cartLineNotFound
			response = "result=cartLineNotFound";
		}

		return response;
	}


	/*
	 * Remove the cart line
	 */
	public String removeCartLine(int cartLineId) {
		String response = null;

		// get the current cart from the session
		Cart cart = this.getCart();

		try {
			// fetch a single cart line by id
			CartLine cartLine = cartLineDAO.get(cartLineId);

			// deduct the grand total of the cart
			cart.setGrandTotal(cart.getGrandTotal() - cartLine.getTotal());
			// subtract the cart lines of the cart by one
			cart.setCartLines(cart.getCartLines() - 1);

			// update the cart
			cartLineDAO.updateCart(cart);

			// remove the cartLine
			cartLineDAO.remove(cartLine);

			// set response to deleted
			response = "result=deleted";

		} catch (CartLineNotFoundException e) {
			// set response to cartLineNotFound
			response = "result=cartLineNotFound";
		}

		return response;
	}

		
	/*
	 * Add new single cart line by product id 
	 */
	public String addCartLine(int productId) {
		String response = null;

		// get current cart
		Cart cart = this.getCart();

		// fetch single cart line by cart and product
		CartLine cartLine = cartLineDAO.getByCartAndProduct(cart.getId(), productId);
		
		if (cartLine == null) {
			try {
				// add a new cartLine if a new product is getting added to the current cart
				cartLine = new CartLine();
				
				// fetch single product by product id
				Product product = productDAO.get(productId);			
				
				// double check on product availability in case of the client script is disabled
				if(product.getQuantity() > 0) {
					
					// transfer the product details to cartLine
					cartLine.setCartId(cart.getId());
					cartLine.setProduct(product);
					cartLine.setProductCount(1);
					cartLine.setBuyingPrice(product.getUnitPrice());
					cartLine.setTotal(product.getUnitPrice());

					// insert a new cartLine
					cartLineDAO.add(cartLine);

					// update the cart
					cart.setGrandTotal(cart.getGrandTotal() + cartLine.getTotal());
					cart.setCartLines(cart.getCartLines() + 1);
					cartLineDAO.updateCart(cart);

					// set response to added
					response = "result=added";
					
				} else {
					// set response to unavailable
					response = "result=unavailable";
				}
				
				
			} catch (ProductNotFoundException e) {
				// set response to productNotFound
				response = "result=productNotFound";
			}

		} else {
			// if the product is already exist with equivalent cart line presented in the cart 
			// , we need to check if the cartLine has been already reached to maximum count
			if (cartLine.getProductCount() < MAX_PRODUCT_COUNT) {
				// delegate the business logic to the local method updateCartLine 
				// to increase the count and set the response
				response = this.updateCartLine(cartLine, cartLine.getProductCount() + 1);
				
			} else {
				// set response to maximum
				response = "result=maximum";
			}
		}

		return response;
	}

	
	
	/*
	 * Validate cart lines And do the necessary updates to both cart and cart lines
	 */
	public String validateCartLines() {
		double grandTotal = 0.0;
		int lineCount = 0;
		String response = "result=success";
		boolean changed = false;
		Product product = null;

		// get the current cart from the session
		Cart cart = this.getCart();

		// fetch all cart lines by cart id
		List<CartLine> cartLines = cartLineDAO.list(cart.getId());

		// iterate over the cart lines
		for (CartLine cartLine : cartLines) {
			// get cart line's product
			product = cartLine.getProduct();

			// set changed to false
			changed = false;

			// check if the product is active or not
			// if it is not active make the availability of cartLine as false
			if ((!product.isActive() && product.getQuantity() == 0) && cartLine.isAvailable()) {
				cartLine.setAvailable(false);
				changed = true;
			}

			// check if the cartLine is not available
			// check whether the product is active and has at least one quantity
			// available
			if ((product.isActive() && product.getQuantity() > 0) && !(cartLine.isAvailable())) {
				cartLine.setAvailable(true);
				changed = true;
			}

			// check if the buying price of product has been changed
			if (cartLine.getBuyingPrice() != product.getUnitPrice()) {
				// set the buying price to the new price
				cartLine.setBuyingPrice(product.getUnitPrice());
				// calculate and set the new total
				cartLine.setTotal(cartLine.getProductCount() * product.getUnitPrice());
				changed = true;
			}

			// check if that much quantity of product is available or not
			if (cartLine.getProductCount() > product.getQuantity()) {
				cartLine.setProductCount(product.getQuantity());
				cartLine.setTotal(cartLine.getProductCount() * product.getUnitPrice());
				changed = true;

			}

			// change has been taken place
			if (changed) {
				// update the cartLine
				cartLineDAO.update(cartLine);
				// set response to modified
				response = "result=modified";
			}

			grandTotal += cartLine.getTotal();
			lineCount++;
		}

		// update the cart
		cart.setCartLines(lineCount++);
		cart.setGrandTotal(grandTotal);
		cartLineDAO.updateCart(cart);

		return response;
	}

}
