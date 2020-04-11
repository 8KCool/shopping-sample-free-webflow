package net.hka.onlineshopping.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.hka.onlineshopping.util.FileUtil;
import net.hka.onlineshopping.validator.ProductValidator;
import net.hka.shoppingbackend.dao.CategoryDAO;
import net.hka.shoppingbackend.dao.ProductDAO;
import net.hka.shoppingbackend.dto.Category;
import net.hka.shoppingbackend.dto.Product;
import net.hka.shoppingbackend.exception.ProductNotFoundException;

@Controller
@RequestMapping("/manage")
public class ManagementController {

	//private static final Logger logger = LoggerFactory.getLogger(ManagementController.class);

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private ProductValidator productValidator;
	
	
	/*
	 *  Method to load the manage product form page
	 */
	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public ModelAndView manageProduct(@RequestParam(name = "success", required = false) String success) {

		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "Manage Product");
		mv.addObject("userClickManageProduct", true);

		Product nProduct = new Product();

		// assuming that the user is ADMIN
		// later we will fixed it based on user is SUPPLIER or ADMIN
		nProduct.setSupplierId(1);
		nProduct.setActive(true);

		mv.addObject("product", nProduct);

		if (success != null) {
			if (success.equals("product")) {
				mv.addObject("message", "Product submitted successfully!");
			} else if (success.equals("category")) {
				mv.addObject("message", "Category submitted successfully!");
			}
		}

		return mv;

	}

	/*
	 *  Method to add or update the product and redirect back to the manage product from page
	 */
	@RequestMapping(value = "/product", method = RequestMethod.POST)
	public String managePostProduct(@Valid @ModelAttribute("product") Product mProduct, BindingResult results,
			Model model, HttpServletRequest request) {

		//logger.info(mProduct.toString());

		// mandatory file upload check in add mode
		if (mProduct.getId() == 0) {
			productValidator.validate(mProduct, results);
		} else {
			// do check only in edit mode when a file has been selected
			if (!mProduct.getFile().getOriginalFilename().equals("")) {
				productValidator.validate(mProduct, results);
			}
		}

		// check if there are any errors
		if (results.hasErrors()) {
			model.addAttribute("title", "Manage Product");
			model.addAttribute("message", "Validation fails for adding the product!");
			model.addAttribute("userClickManageProduct", true);

			return "page";
		}

		// add or update the product 	
		if (mProduct.getId() == 0) {
			productDAO.add(mProduct);
		} else {
			productDAO.update(mProduct);
		}

		// upload the file
		if (!mProduct.getFile().getOriginalFilename().equals("")) {
			FileUtil.uploadFile(request, mProduct.getFile(), mProduct.getCode());
		}

		// redirect to manage product page
		return "redirect:/manage/product?success=product";
	}

	/*
	 *  Method to load the manage product form page and load it by product details for editing
	 */
	@RequestMapping("/product/{id}")
	public ModelAndView manageProductEdit(@PathVariable int id) throws ProductNotFoundException {

		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "Manage Product");
		mv.addObject("userClickManageProduct", true);

		// fetch a single product
		Product product = productDAO.get(id);
		
		// throw exception if product not found
		if (product == null)
			throw new ProductNotFoundException();
		
		mv.addObject("product", product);

		return mv;

	}
	
	/*
	 *  Method to activate and deactivate the product status and called throw AJAX
	 */
	@RequestMapping(value = "/product/{id}/activation", method = RequestMethod.GET)
	@ResponseBody // AJAX
	public String managePostProductActivation(@PathVariable int id) throws ProductNotFoundException {
		// fetch a single product
		Product product = productDAO.get(id);
		
		// throw exception if product not found
		if (product == null)
			throw new ProductNotFoundException();
		
		// update active status for current product 
		boolean isActive = product.isActive();
		product.setActive(!isActive);
		productDAO.update(product);
		
		return (isActive) ? "Product Dectivated Successfully!" : "Product Activated Successfully";
	}

	
	/*
	 *  Method to add new category and redirect back to the manage product page
	 */
	@RequestMapping(value = "/category", method = RequestMethod.POST)
	public String managePostCategory(@ModelAttribute("category") Category mCategory, HttpServletRequest request) {
		// add the new category
		categoryDAO.add(mCategory);
		
		// redirect to manage product page
		return "redirect:" + request.getHeader("Referer") + "?success=category";
	}

	/*
	 *  Method to return all categories from database and make it available for all the request mapping, we can use it to
	 *  populate the combo boxes or data tables
	 */
	@ModelAttribute("categories")
	public List<Category> modelCategories() {
		return categoryDAO.list();
	}

	/*
	 *  Method to return a new category object and make it available for all the request mapping
	 */
	@ModelAttribute("category")
	public Category modelCategory() {
		return new Category();
	}

}
