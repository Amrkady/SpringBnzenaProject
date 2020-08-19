package com.common;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;

import com.entities.Users;

public class CommonDaoImpl extends HibernateTemplate implements CommonDao {
	private SessionFactory sessionFactory;

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public boolean saveObject(Object object) {
		try {
			sessionFactory.getCurrentSession().save(object);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Object> findAll(Class typeClass) {
		try {
			return loadAll(typeClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Object findEntityById(Class entityClass, Integer EntityId) {
		return get(entityClass, EntityId);
	}

	@Override
	@Transactional
	public boolean deleteObject(Object object) {
		try {
			sessionFactory.getCurrentSession().delete(object);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	@Transactional
	public boolean updateObject(Object myObject) {
		try {
			sessionFactory.getCurrentSession().update(myObject);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Users loadUser(final String username, final String password) throws AuthenticationException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Users.class);
		criteria.add(Restrictions.eq("loginName", username));
		if (password != null) {

			criteria.add(Restrictions.eq("password", password));

		}
		Users result = (Users) criteria.uniqueResult();
		if (result == null) {
			throw new BadCredentialsException("bad credentials");
		}
		Hibernate.initialize(result.getRole());
		return result;
	}


}
