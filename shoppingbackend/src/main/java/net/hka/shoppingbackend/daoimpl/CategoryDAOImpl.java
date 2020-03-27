package net.hka.shoppingbackend.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.hka.shoppingbackend.dao.CategoryDAO;
import net.hka.shoppingbackend.dto.Category;

@Repository("categoryDAO")
@Transactional
public class CategoryDAOImpl implements CategoryDAO {

	@Autowired
	private /* static */ SessionFactory sessionFactory;

	// @Autowired
	// private ProductDAO productDAO;

	/*
	 * fetch a list of categories
	 */
	@Override
	public List<Category> list() {
		String selectActiveCategory = "FROM Category WHERE active = :active";

		@SuppressWarnings("unchecked")
		Query<Category> query = sessionFactory.getCurrentSession().createQuery(selectActiveCategory);

		query.setParameter("active", true);

		return query.getResultList();
	}

	/*
	 * fetch a single category by id
	 */
	@Override
	public Category get(int id) {

		// return sessionFactory.getCurrentSession().get(Category.class,
		// Integer.valueOf(id));
		String selectSingleCategory = "FROM Category WHERE id = :id";

		@SuppressWarnings("unchecked")
		Query<Category> query = sessionFactory.getCurrentSession().createQuery(selectSingleCategory);

		query.setParameter("id", id);

		return query.getSingleResult();

	}

	/*
	 * INSERT
	 */
	@Override
	public boolean add(Category category) {
		try {
			// add the category to database table
			sessionFactory.getCurrentSession().persist(category);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/*
	 * UPDATE
	 */
	@Override
	public boolean update(Category category) {
		try {
			// update the category to database table
			sessionFactory.getCurrentSession().update(category);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/*
	 * DELETE
	 */
	@Override
	public boolean delete(Category category) {
		// mark the category to be deactivate
		category.setActive(false);

		try {
			// update the category in database table to inactive
			sessionFactory.getCurrentSession().update(category);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	// @Override
	// public Map<Integer, List<Product>> listCategoriesActiveProducts() {
	// Map<Integer, List<Product>> categoriesProducts = new HashMap<>();
	// List<Category> categories = list();
	// for(Category category : categories) {
	// List<Product> products =
	// productDAO.listActiveProductsByCategory(category.getId());
	// categoriesProducts.put(category.getId(), products);
	// }
	//
	// return categoriesProducts;
	// }

}
