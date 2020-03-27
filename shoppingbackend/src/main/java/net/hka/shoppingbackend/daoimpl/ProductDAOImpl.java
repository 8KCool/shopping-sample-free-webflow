package net.hka.shoppingbackend.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.hka.shoppingbackend.dao.ProductDAO;
import net.hka.shoppingbackend.dto.Product;
import net.hka.shoppingbackend.exception.ProductNotFoundException;

@Repository("productDAO")
@Transactional
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	private SessionFactory sessionFactory;

	/*
	 * fetch a single product by id
	 */
	@Override
	public Product get(int productId) throws ProductNotFoundException {
		try {
			// Product product =
			// sessionFactory.getCurrentSession().get(Product.class,
			// Integer.valueOf(productId));
			String selectSingleProduct = "FROM Product WHERE id = :id";

			@SuppressWarnings("unchecked")
			Query<Product> query = sessionFactory.getCurrentSession().createQuery(selectSingleProduct);

			query.setParameter("id", productId);

			Product product = query.getSingleResult();

			// throw exception if product not found
			if (product == null)
				throw new ProductNotFoundException();

			return product;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/*
	 * fetch a list of products
	 */
	@Override
	public List<Product> list() {

		return sessionFactory.getCurrentSession().createQuery("FROM Product", Product.class).getResultList();
	}

	/*
	 * INSERT
	 */
	@Override
	public boolean add(Product product) {
		try {
			sessionFactory.getCurrentSession().persist(product);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/*
	 * UPDATE
	 */
	@Override
	public boolean update(Product product) {
		try {
			sessionFactory.getCurrentSession().update(product);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/*
	 * DELETE
	 */
	@Override
	public boolean delete(Product product) {
		try {

			product.setActive(false);
			// call the update method
			return this.update(product);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/*
	 * fetch a list of active products
	 */
	@Override
	public List<Product> listActiveProducts() {
		String selectActiveProducts = "FROM Product WHERE active = :active";
		return sessionFactory.getCurrentSession().createQuery(selectActiveProducts, Product.class)
				.setParameter("active", true).getResultList();
	}

	/*
	 * fetch a list of active products by category
	 */
	@Override
	public List<Product> listActiveProductsByCategory(int categoryId) {
		String selectActiveProductsByCategory = "FROM Product WHERE active = :active AND categoryId = :categoryId";
		return sessionFactory.getCurrentSession().createQuery(selectActiveProductsByCategory, Product.class)
				.setParameter("active", true).setParameter("categoryId", categoryId).getResultList();
	}

	/*
	 * fetch a sorted list of latest inserted active products by count
	 */
	@Override
	public List<Product> getLatestActiveProducts(int count) {
		return sessionFactory.getCurrentSession()
				.createQuery("FROM Product WHERE active = :active ORDER BY id", Product.class)
				.setParameter("active", true).setFirstResult(0).setMaxResults(count).getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.hka.shoppingbackend.dao.ProductDAO#getActiveProductsSortedBy(java.
	 * lang.String, int)
	 * 
	 * Method to return the first five products sorted by parameter count
	 */
	@Override
	public List<Product> getActiveProductsSortedBy(String param, int count) {

		String query = "FROM Product WHERE active = true ORDER BY " + param + " DESC";

		return sessionFactory.getCurrentSession().createQuery(query, Product.class).setFirstResult(0)
				.setMaxResults(count).getResultList();

	}

}
