package com.common;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;

import com.entities.Attachment;
import com.entities.Expensis;
import com.entities.ExpensisTypes;
import com.entities.Gas;
import com.entities.GasGuns;
import com.entities.GasStationSuppliers;
import com.entities.GunsRevenus;
import com.entities.Rents;
import com.entities.Shops;
import com.entities.Suppliers;
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

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Shops> findShopsByStationId(Integer stationId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Shops.class);
		criteria.add(Restrictions.eq("stationId", stationId));
		List<Shops> shops = criteria.list();
		return shops;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<GasGuns> findGunsByStationId(Integer stationId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GasGuns.class);
		criteria.add(Restrictions.eq("stationId", stationId));
		List<GasGuns> guns = criteria.list();
		return guns;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Gas> findGassByStationId(Integer stationId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Gas.class);
		criteria.add(Restrictions.eq("stationId", stationId));
		List<Gas> shops = criteria.list();
		return shops;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Suppliers> findSuppliersByStationId(Integer stationId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Suppliers.class);
		criteria.add(Restrictions.eq("stationId", stationId));
		List<Suppliers> shops = criteria.list();
		return shops;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Rents> findRentsByStationId(Integer stationId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Rents.class);
		criteria.add(Restrictions.eq("stationId", stationId));
		List<Rents> shops = criteria.list();
		return shops;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<GasStationSuppliers> findsssByStationId(Integer stationId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GasStationSuppliers.class);
		if (stationId != -1) {
			criteria.add(Restrictions.eq("stationId", stationId));
		}
		List<GasStationSuppliers> sss = criteria.list();
		return sss;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<GunsRevenus> findGunsRevenusByStationId(Integer stationId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GunsRevenus.class);
		criteria.add(Restrictions.eq("stationId", stationId));
		List<GunsRevenus> sss = criteria.list();
		return sss;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<GasGuns> findGunsByStationIdAndGasId(Integer stationId, Integer gasId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GasGuns.class);
		criteria.add(Restrictions.eq("stationId", stationId));
		criteria.add(Restrictions.eq("gasId", gasId));
		List<GasGuns> guns = criteria.list();
		return guns;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Expensis> findExpensisByStationId(Integer stationId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Expensis.class);
		criteria.add(Restrictions.eq("stationId", stationId));
		List<Expensis> expensisList = criteria.list();
		return expensisList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Integer findGunFirstRead(Integer stationId, Integer gunId, Integer gasId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GunsRevenus.class);
		criteria.add(Restrictions.eq("stationId", stationId));
		criteria.add(Restrictions.eq("gasId", gasId));
		criteria.add(Restrictions.eq("gunId", gunId));
		criteria.setProjection(Projections.max("id"));
		// criteria.setProjection(Projections.max("date"));
		Integer read = (Integer) criteria.uniqueResult();
		return (read == null) ? 0 : read;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Attachment> findAttachmentsByStationId(Integer stationId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Attachment.class);
		criteria.add(Restrictions.eq("stationId", stationId));
		List<Attachment> List = criteria.list();
		return List;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<GasStationSuppliers> findsssByDates(Date dateFrom, Date dateTo, Integer supplierId, Integer suppType,
			Integer stationId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GasStationSuppliers.class);
		if (stationId != -1) {
			criteria.add(Restrictions.eq("stationId", stationId));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("supDate", dateFrom));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("supDate", dateTo));
		}

		if (supplierId != null) {
			if (suppType == 1) {
				criteria.add(Restrictions.eq("supplierId", supplierId));
			} else {
				criteria.add(Restrictions.eq("fromStationId", supplierId));
			}
		}
		List<GasStationSuppliers> ssslist = criteria.list();
		return ssslist;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<GunsRevenus> findRevsByDates(Date dateFrom, Date dateTo, Integer gasId, Integer stationId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GunsRevenus.class);
		if (stationId != -1) {
			criteria.add(Restrictions.eq("stationId", stationId));
		}

		if (dateFrom != null) {
			criteria.add(Restrictions.ge("revDate", dateFrom));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("revDate", dateTo));
		}

		if (gasId != null) {
			criteria.add(Restrictions.eq("gasId", gasId));

		}
		List<GunsRevenus> list = criteria.list();
		return list;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Expensis> findExpensisByDates(Date dateFrom, Date dateTo, Integer supType, Integer stId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Expensis.class);
		if (stId != -1) {
			criteria.add(Restrictions.eq("stationId", stId));
		}

		if (dateFrom != null) {
			criteria.add(Restrictions.ge("monthDate", dateFrom));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("monthDate", dateTo));
		}

		if (supType != null) {
			criteria.add(Restrictions.eq("expensisType", supType));
		}
		List<Expensis> list = criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ExpensisTypes> findExpensisTypes(Integer general) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ExpensisTypes.class);
		criteria.add(Restrictions.eq("general", general));
		List<ExpensisTypes> expensisList = criteria.list();
		return expensisList;
	}

}
