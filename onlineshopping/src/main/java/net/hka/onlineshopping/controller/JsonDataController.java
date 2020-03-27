package net.hka.onlineshopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.hka.shoppingbackend.dao.ProductDAO;
import net.hka.shoppingbackend.dto.Product;

@Controller
@RequestMapping("/json/data")
public class JsonDataController {
	//@Autowired
	//private CategoryDAO categoryDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
//	private static List<Product> allProductsList = null;
//	private static List<Product> allActiveProductsList = null;
//	private static Map<Integer, List<Product>> categoriesProductsMap = null;
//	
//	public static void init() { 
//		allProductsList = null;
//		allActiveProductsList = null;
//		categoriesProductsMap = null;
//	}
//	
//	/*
//	 *  Helper methods to load and caching/storing the data in local static container/collections
//	 */
//	private List<Product> getProductsList() { 
//		if(allProductsList == null || allProductsList.size() == 0) {
//			allProductsList = productDAO.list();
//		}
//		
//		return allProductsList;
//	}
//	private List<Product> getActiveProductsList() { 
//		if(allActiveProductsList == null || allActiveProductsList.size() == 0) {
//			allActiveProductsList = productDAO.listActiveProducts();
//		}
//		
//		return allActiveProductsList;
//	}
//	private List<Product> getProductsByCategoryIdList(int id) { 
//		if(categoriesProductsMap == null || categoriesProductsMap.size() == 0) {
//			categoriesProductsMap = categoryDAO.listCategoriesActiveProducts();
//		}
//		
//		return categoriesProductsMap.get(id);
//	}
	
	/*
	 *  Method to return all products in JSON format
	 */
	@RequestMapping("/admin/all/products")
	@ResponseBody
	public List<Product> getAllProducts() {
		return productDAO.list();//getProductsList();//
	}	
	
	/* Method to return all active products in JSON format
	 * 
	 */
	@RequestMapping("/all/products")
	@ResponseBody
	public List<Product> getActiveProducts() {
		return productDAO.listActiveProducts();//getActiveProductsList();//
	}
	
	/* Method to return all active products by category in JSON format
	 * 
	 */
	@RequestMapping("/category/{id}/products")
	@ResponseBody
	public List<Product> getProductsByCategory(@PathVariable int id) {
		return productDAO.listActiveProductsByCategory(id);//getProductsByCategoryIdList(id);//
	}
	
	/*
	 *  Method to return the first five products sorted by views count in JSON format
	 */
	@RequestMapping("/mv/products")
	@ResponseBody
	public List<Product> getMostViewedProducts() {
		return productDAO.getActiveProductsSortedBy("views", 5);				
	}
	
	/*
	 *  Method to return the first five products sorted by purchases count in JSON format
	 */
	@RequestMapping("/mp/products")
	@ResponseBody
	public List<Product> getMostPurchasedProducts() {
		return productDAO.getActiveProductsSortedBy("purchases", 5);				
	}
	
	
}
