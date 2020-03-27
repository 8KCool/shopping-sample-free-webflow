package net.hka.shoppingbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.hka.shoppingbackend.dao.CategoryDAO;
import net.hka.shoppingbackend.dto.Category;

public class CategoryTestCase {
	private static AnnotationConfigApplicationContext context;

	private static CategoryDAO categoryDAO;

	private Category category;

	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("net.hka.shoppingbackend");
		context.refresh();
		categoryDAO = (CategoryDAO) context.getBean("categoryDAO");
	}

	/*	@Test
	public void testAddCategory() {
		
		category = new Category();
		
		category.setName("Laptop");
		category.setDescription("This is some description for laptop!");
		category.setImageURL("CAT_105.png");
		
		assertEquals("Something went wrong while adding a category inside the table!",true,categoryDAO.add(category));
		
		
	}
	*/
	
/*	@Test
	public void testGetCategory() {
		
		category = categoryDAO.get(3);
		
		
		assertEquals("Something went wrong while fetching a single category from the table!","Television",category.getName());
		
		
	}
	*/
	
/*	@Test
	public void testUpdateCategory() {
		
		category = categoryDAO.get(3);
		
		category.setName("TV");
		
		assertEquals("Something went wrong while updating a single category in the table!",true,categoryDAO.update(category));
		
		
	}
	*/

/*	@Test
	public void testDeleteCategory() {
		
		category = categoryDAO.get(3);		
		assertEquals("Something went wrong while deleting a single category in the table!",true,categoryDAO.delete(category));
		
		
	}
*/	
	
	@Test
	public void testListCategory() {
					
		assertEquals("Something went wrong while fetching the list of categories from the table!",3,categoryDAO.list().size());
		
		
	}
	

/*	
	@Test
	public void testCRUDCategory() {
		
		// add operation
		category = new Category();
		
		category.setName("Laptop");
		category.setDescription("This is some description for laptop!");
		category.setImageURL("CAT_1.png");
		
		assertEquals("Something went wrong while adding a category inside the table!",true,categoryDAO.add(category));
		
		
		category = new Category();
		
		category.setName("Television");
		category.setDescription("This is some description for television!");
		category.setImageURL("CAT_2.png");
		
		assertEquals("Something went wrong while adding a category inside the table!",true,categoryDAO.add(category));

		
		// fetching and updating the category
		category = categoryDAO.get(2);
		
		category.setName("TV");
		
		assertEquals("Something went wrong while updating a single category in the table!",true,categoryDAO.update(category));
		
		
		// delete the category
		assertEquals("Something went wrong while deleting a single category in the table!",true,categoryDAO.delete(category));
		
		
		//fetching the list
		assertEquals("Something went wrong while fetching the list of categories from the table!",1,categoryDAO.list().size());		
				
		
	}
*/
	

}
