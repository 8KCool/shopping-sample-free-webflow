package net.hka.shoppingbackend.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.hka.shoppingbackend.dao.UserDAO;
import net.hka.shoppingbackend.dto.Address;
import net.hka.shoppingbackend.dto.User;

@Repository("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User getByEmail(String email) {
		String selectQuery = "FROM User WHERE email = :email";
		try {
			return sessionFactory.getCurrentSession().createQuery(selectQuery, User.class).setParameter("email", email)
					.getSingleResult();
		} catch (Exception ex) {
			return null;
		}

	}

	@Override
	public User get(int id) {
		try {
			// return sessionFactory.getCurrentSession().get(User.class, id);
			String selectSingleUser = "FROM User WHERE id = :id";

			@SuppressWarnings("unchecked")
			Query<User> query = sessionFactory.getCurrentSession().createQuery(selectSingleUser);

			query.setParameter("id", id);

			return query.getSingleResult();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	@Override
	public boolean add(User user) {
		try {
			sessionFactory.getCurrentSession().persist(user);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean addAddress(Address address) {
		try {
			// will look for this code later and why we need to change it
			sessionFactory.getCurrentSession().persist(address);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean updateAddress(Address address) {
		try {
			sessionFactory.getCurrentSession().update(address);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public List<Address> listShippingAddresses(int userId) {
		String selectQuery = "FROM Address WHERE userId = :userId AND shipping = :isShipping ORDER BY id DESC";
		return sessionFactory.getCurrentSession().createQuery(selectQuery, Address.class).setParameter("userId", userId)
				.setParameter("isShipping", true).getResultList();

	}

	@Override
	public Address getBillingAddress(int userId) {
		String selectQuery = "FROM Address WHERE userId = :userId AND billing = :isBilling";
		try {
			return sessionFactory.getCurrentSession().createQuery(selectQuery, Address.class)
					.setParameter("userId", userId).setParameter("isBilling", true).getSingleResult();
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public Address getAddress(int addressId) {
		try {
			// return sessionFactory.getCurrentSession().get(Address.class,
			// addressId);
			String selectSingleAddress = "FROM Address WHERE id = :id";

			@SuppressWarnings("unchecked")
			Query<Address> query = sessionFactory.getCurrentSession().createQuery(selectSingleAddress);

			query.setParameter("id", addressId);

			return query.getSingleResult();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

}
