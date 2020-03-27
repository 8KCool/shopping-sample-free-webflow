package net.hka.shoppingbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.hka.shoppingbackend.dao.CartLineDAO;
import net.hka.shoppingbackend.dao.ProductDAO;
import net.hka.shoppingbackend.dao.UserDAO;
import net.hka.shoppingbackend.dto.Cart;
import net.hka.shoppingbackend.dto.CartLine;
import net.hka.shoppingbackend.dto.Product;
import net.hka.shoppingbackend.dto.User;
import net.hka.shoppingbackend.exception.ProductNotFoundException;

public class CartLineTestCase {

	private static AnnotationConfigApplicationContext context;

	private static CartLineDAO cartLineDAO;
	private static ProductDAO productDAO;
	private static UserDAO userDAO;

	private CartLine cartLine = null;

	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("net.hka.shoppingbackend");
		context.refresh();
		cartLineDAO = (CartLineDAO) context.getBean("cartLineDAO");
		productDAO = (ProductDAO) context.getBean("productDAO");
		userDAO = (UserDAO) context.getBean("userDAO");
	}

	@Test
	public void testAddCartLine() {

		// fetch the user and then cart of that user
		User user = userDAO.getByEmail("absr@gmail.com");
		Cart cart = user.getCart();

		// fetch the product
		Product product = null;
		try {
			product = productDAO.get(2);

			// Create a new CartLine
			cartLine = new CartLine();
			cartLine.setCartId(cart.getId());
			cartLine.setProduct(product);
			cartLine.setProductCount(1);

			double oldTotal = cartLine.getTotal();

			cartLine.setTotal(product.getUnitPrice() * cartLine.getProductCount());

			assertEquals("Failed to add the CartLine!", true, cartLineDAO.add(cartLine));

			// update cart
			cart.setCartLines(cart.getCartLines() + 1);
			cart.setGrandTotal(cart.getGrandTotal() + (cartLine.getTotal() - oldTotal));
			assertEquals("Failed to update the cart!", true, cartLineDAO.updateCart(cart));

		} catch (ProductNotFoundException e) {
			assertEquals("Failed to get the Product!", true, false);
		}

	}

	@Test
	public void testUpdateCartLine() {

		// fetch the user and then cart of that user
		User user = userDAO.getByEmail("absr@gmail.com");
		Cart cart = user.getCart();

		cartLine = cartLineDAO.getByCartAndProduct(cart.getId(), 2);

		cartLine.setProductCount(cartLine.getProductCount() + 1);

		double oldTotal = cartLine.getTotal();

		cartLine.setTotal(cartLine.getProduct().getUnitPrice() * cartLine.getProductCount());

		cart.setGrandTotal(cart.getGrandTotal() + (cartLine.getTotal() - oldTotal));

		assertEquals("Failed to update the CartLine!", true, cartLineDAO.update(cartLine));

	}

}
