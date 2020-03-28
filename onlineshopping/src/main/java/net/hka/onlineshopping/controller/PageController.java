package net.hka.onlineshopping.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.hka.shoppingbackend.dao.CategoryDAO;
import net.hka.shoppingbackend.dao.ProductDAO;
import net.hka.shoppingbackend.dto.Category;
import net.hka.shoppingbackend.dto.Product;
import net.hka.shoppingbackend.exception.ProductNotFoundException;

@Controller
public class PageController {

	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private ProductDAO productDAO;

	// private static Map<Integer, Category> categoriesMap = null;
	// private static List<Category> categories = null;
	//
	// /*
	// * Helper method to load all Categories from database to local static
	// container/collections
	// */
	// private List<Category> getCategories() {
	// if (categoriesMap == null || categoriesMap.size() == 0) {
	// categoriesMap = new HashMap<>();
	// categories = categoryDAO.list();
	// for (Category category : categories) {
	// categoriesMap.put(category.getId(), category);
	// }
	// }
	//
	// return categories;
	// }
	//
	// /*
	// * Method to return everything to initialize state
	// */
	// @RequestMapping(value = { "/init" })
	// public String init(Model model, HttpServletRequest request) {
	// categoriesMap = null;
	// JsonDataController.init();
	//
	// model.addAttribute("title", "Home");
	//
	// logger.info("Inside PageCtroller init method - INFO");
	// logger.debug("Inside PageCtroller init method - DEBUG");
	//
	// // passing the list of categories
	// model.addAttribute("categories", getCategories());
	//
	// model.addAttribute("userClickHome", true);
	// return "page";
	// }

	/*
	 * Helper method to get all categories from database
	 */
	private List<Category> getCategories() {
		return categoryDAO.list();
	}

	/*
	 * Helper method to get single category from database
	 */
	private Category getCategory(int id) {
		return categoryDAO.get(id);
	}

	/*
	 * Method to load index/home page
	 */
	@RequestMapping(value = { "/", "/home", "/index" })
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "Home");

		//logger.info("Inside PageCtroller index method - INFO");
		//logger.debug("Inside PageCtroller index method - DEBUG");

		// passing the list of categories
		// mv.addObject("categories", categoryDAO.list());
		mv.addObject("categories", getCategories());

		mv.addObject("userClickHome", true);
		return mv;
	}

	/*
	 * Method to load about page
	 */
	@RequestMapping(value = { "/about" })
	public ModelAndView about() {
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "About Us");
		mv.addObject("userClickAbout", true);
		return mv;
	}

	/*
	 * Method to load contact page
	 */
	@RequestMapping(value = { "/contact" })
	public ModelAndView contact() {
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "Contact Us");
		mv.addObject("userClickContact", true);
		return mv;
	}

	/*
	 * Methods to load all products and based on category
	 */
	@RequestMapping(value = { "/show/all/products" })
	public ModelAndView showAllProducts() {
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "All Products");

		// passing the list of categories
		// mv.addObject("categories", categoryDAO.list());
		mv.addObject("categories", getCategories());

		mv.addObject("userClickAllProducts", true);
		return mv;
	}

	@RequestMapping(value = { "/show/category/{id}/products" })
	public ModelAndView showCategoryProducts(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("page");

		// fetch a single category
		// Category category = categoryDAO.get(id);
		Category category = getCategory(id);// categoriesMap.get(id);

		// set the page title
		mv.addObject("title", category.getName());

		// passing the list of categories
		// mv.addObject("categories", categoryDAO.list());
		mv.addObject("categories", getCategories());

		// passing a single category
		mv.addObject("category", category);

		mv.addObject("userClickCategoryProducts", true);
		return mv;
	}

	/*
	 * View a single product
	 */
	@RequestMapping(value = { "/show/product/{id}", "/show/category/product/{id}" })
	public ModelAndView showSingleProduct(@PathVariable("id") int id, HttpServletRequest request)
			throws ProductNotFoundException {
		ModelAndView mv = new ModelAndView("page");

		// fetch a single product
		Product product = productDAO.get(id);

		// throw exception if product not found
		if (product == null)
			throw new ProductNotFoundException();

		// update view count for current product
		product.setViews(product.getViews() + 1);
		productDAO.update(product);

		// set the page title
		mv.addObject("title", product.getName());

		// passing a single product
		mv.addObject("product", product);

		mv.addObject("userClickShowProduct", true);

		// return category id, to be able to return to filtered products by
		// category
		try {
			URL url = new URL(request.getRequestURL().toString());
			String path = url.getPath();
			if (path.contains("category")) {
				mv.addObject("categoryId", product.getCategoryId());
			} else {
				mv.addObject("categoryId", 0);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mv;
	}

	
	/*
	 * Login page
	 * */
	@RequestMapping(value = "/login")
	public ModelAndView login(@RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout) {
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("title", "Login");
		
		// handle invalid credential
		if (error != null) {
			mv.addObject("message", "Invalid Username and Password!");
		}
		
		if (logout != null) {
			mv.addObject("logout", "You have logged out successfully!");
		}
		
		return mv;
	}
	
	/*
	 * Logout handling method
	 * Invalidates HTTP Session, then unbound any objects that are bound to session.
	 * */
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
	    // get the authentication
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		// remove the authentication from security context, and invalidate/clear up session 		
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
		
		return "redirect:/login?logout";
	}
	
	/*
	 * Access denied page  
	 * */
	@RequestMapping(value="/access-denied")
	public ModelAndView accessDenied() {
		ModelAndView mv = new ModelAndView("error");		
		mv.addObject("errorTitle", "Aha! Caught You.");		
		mv.addObject("errorDescription", "You are not authorized to view this page!");		
		mv.addObject("title", "403 Access Denied");		
		return mv;
	}

}
