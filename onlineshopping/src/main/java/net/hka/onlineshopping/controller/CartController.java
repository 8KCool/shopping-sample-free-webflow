package net.hka.onlineshopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.hka.onlineshopping.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	/*
	 * Method to load cart page with available cart lines 
	 * and handle the user messages
	 */
	@RequestMapping("/show")
	public ModelAndView showCart(@RequestParam(name = "result", required = false) String result) {

		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "Shopping Cart");
		mv.addObject("userClickShowCart", true);

		if (result != null) {
			String message = CartService.getUserMessage(result);
			mv.addObject("message", message);
			
			if(result.equals("added") || result.equals("updated")) 
				cartService.validateCartLines();
			
		} else {
			String response = cartService.validateCartLines();
			if (response.equals("result=modified")) {
				mv.addObject("message", "One or more items inside cart has been modified!");
			}
		}

		// add cart lines to the view
		mv.addObject("cartLines", cartService.getCartLines());
		
		return mv;

	}

	/*
	 *  Method to update the cart line in the cart and redirect back to the cart page
	 */
	@RequestMapping("/{cartLineId}/update")
	public String udpateCartLine(@PathVariable int cartLineId, @RequestParam int count) {
		String response = cartService.updateCartLineById(cartLineId, count);
		return "redirect:/cart/show?" + response;
	}

	/* 
	 *  Method to add cart line to the cart and redirect back to the cart page
	 */
	@RequestMapping("/add/product/{productId}")
	public String addCartLine(@PathVariable int productId) {
		String response = cartService.addCartLine(productId);
		return "redirect:/cart/show?" + response;
	}

	/* 
	 *  Method to remove cart line from the cart and redirect back to the cart page
	 */
	@RequestMapping("/{cartLineId}/remove")
	public String removeCartLine(@PathVariable int cartLineId) {
		String response = cartService.removeCartLine(cartLineId);
		return "redirect:/cart/show?" + response;
	}

	/*
	 * Method to do the validation to the cart lines for the current cart and then  
	 * it redirects to checkout if result received is success
	 * or it displays a message in cart page to the user about the changes 
	 */
	@RequestMapping("/validate")
	public String validateCart() {
		String response = cartService.validateCartLines();
		if (!response.equals("result=success")) {
			return "redirect:/cart/show?" + response;
		} else {
			return "redirect:/cart/checkout";
		}
	}
}
