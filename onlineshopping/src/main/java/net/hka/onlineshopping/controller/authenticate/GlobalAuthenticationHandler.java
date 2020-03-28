package net.hka.onlineshopping.controller.authenticate;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import net.hka.onlineshopping.model.UserModel;
import net.hka.shoppingbackend.dao.UserDAO;
import net.hka.shoppingbackend.dto.User;

/**
 * Advice controller that is available for all other controllers and requests. 
 * It returns model attribute for user model and sets a session object by user model 
 * so we don't need to do the all actions in every request.
 * */
@ControllerAdvice
public class GlobalAuthenticationHandler {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private HttpSession session;

	private UserModel userModel = null;
	private User user = null;

	@ModelAttribute("userModel")
	public UserModel getUserModel() {
		if (session.getAttribute("userModel") == null) {
			// get the authentication object
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!authentication.getPrincipal().equals("anonymousUser")) {
				// get the user from the database
				user = userDAO.getByEmail(authentication.getName());

				if (user != null) {
					// create a new user model
					userModel = new UserModel();
					
					// set the name, the id, and the role
					userModel.setId(user.getId());
					userModel.setFullName(user.getFirstName() + " " + user.getLastName());
					userModel.setRole(user.getRole());

					// set the cart for the user if the user is a buyer
					if (user.getRole().equals("USER")) {
						userModel.setCart(user.getCart());
					}
					
					// add the user model to the session
					session.setAttribute("userModel", userModel);
					
					return userModel;
				}
			}
		}

		return (UserModel) session.getAttribute("userModel");
	}

}
