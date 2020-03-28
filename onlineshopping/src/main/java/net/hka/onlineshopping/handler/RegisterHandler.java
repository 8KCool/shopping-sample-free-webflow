package net.hka.onlineshopping.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import net.hka.onlineshopping.controller.PageController;
import net.hka.onlineshopping.model.RegisterModel;
import net.hka.shoppingbackend.dao.UserDAO;
import net.hka.shoppingbackend.dto.Address;
import net.hka.shoppingbackend.dto.Cart;
import net.hka.shoppingbackend.dto.User;
import net.hka.shoppingbackend.dto.UserRole;

/**
 * This is a bean class to provide the RegisterModel at the start of the
 * Register web flow along with both the flow instance variable and to handle
 * all operations inside the flow.
 */
@Component
public class RegisterHandler {

	private static final Logger logger = LoggerFactory.getLogger(PageController.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public RegisterModel init() {
		return new RegisterModel();
	}

	public void addUser(RegisterModel registerModel, User user) {
		registerModel.setUser(user);
	}

	public void addBilling(RegisterModel registerModel, Address billing) {
		registerModel.setBilling(billing);
	}

	public String validateUser(User user, MessageContext error) {
		String transitionValue = "failure";
		boolean isSuccess = true;

		// check the password and confirm password are similar
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			error.addMessage(new MessageBuilder().error().source("confirmPassword")
					.defaultText("Password does not match confirm password!").build());
			// transitionValue = "failure";
			isSuccess = false;
		}

		// check the uniqueness of the user email
		String email = user.getEmail();
		User emailUser = userDAO.getByEmail(email);
		logger.debug("User for eMail: '" + email + "' is " + emailUser);
		if (emailUser != null) {
			error.addMessage(new MessageBuilder().error().source("email").defaultText("Email address is already taken!")
					.build());
			// transitionValue = "failure";
			isSuccess = false;
		}

		// set the transition value to complete the flow
		transitionValue = (isSuccess) ? "success" : "failure";

		return transitionValue;
	}

	public String saveAll(RegisterModel registerModel) {
		boolean isUserSaved = false;
		boolean isAddressSaved = false;
		String transitionValue = "failure";

		// create a new cart for a user with role USER
		User user = registerModel.getUser();
		//if (user.getRole().equals("USER")) {
		if (user.getRole() == UserRole.USER) {
			// create a new cart
			Cart cart = new Cart();
			cart.setUser(user);
			user.setCart(cart);
		}

		// encode the password
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// save the user
		isUserSaved = userDAO.add(user);

		// save the billing address
		Address billing = registerModel.getBilling();
		billing.setUserId(user.getId());
		billing.setBilling(true);
		isAddressSaved = userDAO.addAddress(billing);

		// set the transition value to complete the flow
		transitionValue = (isUserSaved && isAddressSaved) ? "success" : "failure";

		return transitionValue;
	}
	
	// return an array for user roles for only role user and role supplier
	public UserRole[] populateRoles() {
        return new UserRole[] { UserRole.USER, UserRole.SUPPLIER };
    }

}
