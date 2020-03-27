package net.hka.shoppingbackend.dao;

import java.util.List;

import net.hka.shoppingbackend.dto.Category;

public interface CategoryDAO {
	
	List<Category> list();
	
	Category get(int id);
	
	boolean add(Category categoety);
	
	boolean update(Category category);
	
	boolean delete(Category category);
	
	/*
	 *  business methods
	 */
	//Map<Integer, List<Product>> listCategoriesActiveProducts();	
	
}
