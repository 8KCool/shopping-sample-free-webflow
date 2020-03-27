package net.hka.onlineshopping.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.hka.onlineshopping.model.CheckoutModel;
import net.hka.onlineshopping.model.UserModel;
import net.hka.shoppingbackend.dao.CartLineDAO;
import net.hka.shoppingbackend.dao.ProductDAO;
import net.hka.shoppingbackend.dao.UserDAO;
import net.hka.shoppingbackend.dto.Address;
import net.hka.shoppingbackend.dto.Cart;
import net.hka.shoppingbackend.dto.CartLine;
import net.hka.shoppingbackend.dto.OrderDetail;
import net.hka.shoppingbackend.dto.OrderItem;
import net.hka.shoppingbackend.dto.Product;
import net.hka.shoppingbackend.dto.User;
import net.hka.shoppingbackend.exception.CartLineNotFoundException;

/**
 * This is a bean class to provide the CheckoutModel at the start of the
 * Checkout web flow along with both the flow instance variable and to handle
 * all operations inside the flow.
 */
@Component
public class CheckoutHandler {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private CartLineDAO cartLineDAO;

	@Autowired
	private HttpSession session;

	public CheckoutModel init(String name) throws CartLineNotFoundException {

		// fetch a single user by email
		User user = userDAO.getByEmail(name);

		CheckoutModel checkoutModel = null;
		if (user != null) {
			// creating new check out model
			checkoutModel = new CheckoutModel();
			checkoutModel.setUser(user);
			checkoutModel.setCart(user.getCart());

			double checkoutTotal = 0.0;
			List<CartLine> cartLines = cartLineDAO.listAvailable(user.getCart().getId());

			if (cartLines == null || cartLines.size() == 0) {
				throw new CartLineNotFoundException("There are no products available for checkout now!");
			}

			for (CartLine cartLine : cartLines) {
				checkoutTotal += cartLine.getTotal();
			}

			checkoutModel.setCartLines(cartLines);
			checkoutModel.setCheckoutTotal(checkoutTotal);
		}

		return checkoutModel;
	}

	public List<Address> getShippingAddresses(CheckoutModel model) {

		List<Address> addresses = userDAO.listShippingAddresses(model.getUser().getId());

		if (addresses.size() == 0) {
			addresses = new ArrayList<>();
		}

		addresses.add(addresses.size(), userDAO.getBillingAddress(model.getUser().getId()));

		return addresses;

	}

	public String saveAddressSelection(CheckoutModel checkoutModel, int shippingId) {

		String transitionValue = "success";

		// logger.info(String.valueOf(shippingId));

		// fetch a single address by address id
		Address shipping = userDAO.getAddress(shippingId);

		// set the shipping address in the check out model (flowScope object)
		checkoutModel.setShipping(shipping);

		return transitionValue;

	}

	public String saveAddress(CheckoutModel checkoutModel, Address shipping) {

		String transitionValue = "success";

		// set the user id
		// set shipping as true
		shipping.setUserId(checkoutModel.getUser().getId());
		shipping.setShipping(true);

		// save the new address for the authenticated user in database
		userDAO.addAddress(shipping);

		// set the shipping address in the check out model (flowScope object)
		checkoutModel.setShipping(shipping);

		return transitionValue;

	}

	public String saveOrder(CheckoutModel checkoutModel) {
		String transitionValue = "success";

		// create a new order object
		OrderDetail orderDetail = new OrderDetail();

		// attach the user
		orderDetail.setUser(checkoutModel.getUser());

		// attach the shipping address
		orderDetail.setShipping(checkoutModel.getShipping());

		// fetch the billing address
		Address billing = userDAO.getBillingAddress(checkoutModel.getUser().getId());
		orderDetail.setBilling(billing);

		List<CartLine> cartLines = checkoutModel.getCartLines();
		OrderItem orderItem = null;

		double orderTotal = 0.0;
		int orderCount = 0;
		Product product = null;

		for (CartLine cartLine : cartLines) {

			orderItem = new OrderItem();

			orderItem.setBuyingPrice(cartLine.getBuyingPrice());
			orderItem.setProduct(cartLine.getProduct());
			orderItem.setProductCount(cartLine.getProductCount());
			orderItem.setTotal(cartLine.getTotal());

			orderItem.setOrderDetail(orderDetail);
			orderDetail.getOrderItems().add(orderItem);

			orderTotal += orderItem.getTotal();
			orderCount++;

			// update the product
			// reduce the quantity of product
			product = cartLine.getProduct();
			product.setQuantity(product.getQuantity() - cartLine.getProductCount());
			product.setPurchases(product.getPurchases() + cartLine.getProductCount());
			productDAO.update(product);

			// delete the cartLine
			cartLineDAO.remove(cartLine);

		}

		orderDetail.setOrderTotal(orderTotal);
		orderDetail.setOrderCount(orderCount);
		orderDetail.setOrderDate(new Date());

		// save the order
		cartLineDAO.addOrderDetail(orderDetail);

		// set it to the orderDetails of checkoutModel
		checkoutModel.setOrderDetail(orderDetail);

		// update the cart
		Cart cart = checkoutModel.getCart();
		cart.setGrandTotal(cart.getGrandTotal() - orderTotal);
		cart.setCartLines(cart.getCartLines() - orderCount);
		cartLineDAO.updateCart(cart);

		// update the cart if its in the session
		UserModel userModel = (UserModel) session.getAttribute("userModel");
		if (userModel != null) {
			userModel.setCart(cart);
		}

		return transitionValue;
	}

	public OrderDetail getOrderDetail(CheckoutModel checkoutModel) {
		return checkoutModel.getOrderDetail();
	}

}
