package net.hka.shoppingbackend.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.hka.shoppingbackend.dao.CartLineDAO;
import net.hka.shoppingbackend.dto.Cart;
import net.hka.shoppingbackend.dto.CartLine;
import net.hka.shoppingbackend.dto.OrderDetail;
//import net.hka.shoppingbackend.dto.OrderDetail;
import net.hka.shoppingbackend.exception.CartLineNotFoundException;

@Repository("cartLineDAO")
@Transactional
public class CartLineDAOImpl implements CartLineDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public CartLine get(int id) throws CartLineNotFoundException {
		// CartLine cartLine =
		// sessionFactory.getCurrentSession().get(CartLine.class,
		// Integer.valueOf(id));
		String selectSingleCartLine = "FROM CartLine WHERE id = :id";

		@SuppressWarnings("unchecked")
		Query<CartLine> query = sessionFactory.getCurrentSession().createQuery(selectSingleCartLine);

		query.setParameter("id", id);

		CartLine cartLine = query.getSingleResult();

		// throw exception if cart line not found
		if (cartLine == null)
			throw new CartLineNotFoundException();

		return cartLine;
	}

	@Override
	public List<CartLine> list(int cartId) {
		String query = "FROM CartLine WHERE cartId = :cartId";
		return sessionFactory.getCurrentSession().createQuery(query, CartLine.class).setParameter("cartId", cartId)
				.getResultList();
	}

	@Override
	public boolean add(CartLine cartLine) {
		try {
			sessionFactory.getCurrentSession().persist(cartLine);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(CartLine cartLine) {
		try {
			sessionFactory.getCurrentSession().update(cartLine);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean remove(CartLine cartLine) {
		try {
			sessionFactory.getCurrentSession().delete(cartLine);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public CartLine getByCartAndProduct(int cartId, int productId) {
		String query = "FROM CartLine WHERE cartId = :cartId AND product.id = :productId";
		try {

			return sessionFactory.getCurrentSession().createQuery(query, CartLine.class).setParameter("cartId", cartId)
					.setParameter("productId", productId).getSingleResult();

		} catch (Exception ex) {
			return null;
		}

	}

	@Override
	public List<CartLine> listAvailable(int cartId) throws CartLineNotFoundException {
		String query = "FROM CartLine WHERE cartId = :cartId AND available = :available";

		List<CartLine> cartLines = sessionFactory.getCurrentSession().createQuery(query, CartLine.class)
				.setParameter("cartId", cartId).setParameter("available", true).getResultList();

		if (cartLines == null || cartLines.size() == 0) {
			throw new CartLineNotFoundException();
		}

		return cartLines;
	}

	@Override
	public boolean updateCart(Cart cart) {
		try {
			sessionFactory.getCurrentSession().update(cart);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean addOrderDetail(OrderDetail orderDetail) {
		try {
			sessionFactory.getCurrentSession().persist(orderDetail);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

}
