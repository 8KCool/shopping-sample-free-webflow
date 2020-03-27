package net.hka.shoppingbackend.dao;

import java.util.List;

import net.hka.shoppingbackend.dto.Product;
import net.hka.shoppingbackend.exception.ProductNotFoundException;

public interface ProductDAO {

	Product get(int productId) throws ProductNotFoundException;
	List<Product> list();	
	boolean add(Product product);
	boolean update(Product product);
	boolean delete(Product product);

	List<Product> getActiveProductsSortedBy(String param, int count);	
	
	
	// business methods
	List<Product> listActiveProducts();	
	List<Product> listActiveProductsByCategory(int categoryId);
	List<Product> getLatestActiveProducts(int count);
	
}
