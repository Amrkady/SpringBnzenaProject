package com.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
import com.entities.Constantsasoul;
import com.entities.Expensis;
import com.entities.ExpensisTypes;
import com.entities.FirstDayAmount;
import com.entities.Gas;
import com.entities.GasGuns;
import com.entities.GasStationSuppliers;
import com.entities.GeneralPay;
import com.entities.GunsRevenus;
import com.entities.Rents;
import com.entities.Shops;
import com.entities.SndSrfQbd;
import com.entities.Stations;
import com.entities.Suppliers;
import com.entities.Users;
import com.models.GasModel;
import com.models.RevuensModel;

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
		criteria.add(Restrictions.eq("sadad", 0));
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

		if (suppType != null) {
			criteria.add(Restrictions.eq("supplierType", suppType));
		}
		if (supplierId != null) {
			if (suppType == 1) {
				criteria.add(Restrictions.eq("supplierId", supplierId));
			} else {
				criteria.add(Restrictions.eq("fromStationId", supplierId));
			}
		}
		criteria.add(Restrictions.eq("sadad", 0));
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

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SndSrfQbd> findSndByType(Integer type, Integer stationId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SndSrfQbd.class);
		if (stationId != -1) {
			criteria.add(Restrictions.eq("stationId", stationId));
		}
		criteria.add(Restrictions.eq("sndType", type));
		criteria.add(Restrictions.ne("expensisTypesId", -1));
		List<SndSrfQbd> list = criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SndSrfQbd> findGeneralSndByType(Integer type, Integer stationId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SndSrfQbd.class);
		if (stationId != -1) {
			criteria.add(Restrictions.eq("stationId", stationId));
		}
		criteria.add(Restrictions.eq("sndType", type));
		criteria.add(Restrictions.eq("expensisTypesId", -1));
		List<SndSrfQbd> list = criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<GasModel> getAllLitersBetweenDates(Integer stationId, Date beginDate, Date endDate) {
		BigDecimal litrsSum = new BigDecimal(0);
		Double litSum = 0d;
		BigDecimal importsSum = new BigDecimal(0);
		BigDecimal firstAmountSum = new BigDecimal(0);
		BigDecimal lastAmountSum = new BigDecimal(0);
		Double impsSum = 0d;
		Double firstAmount = 0d;
		Double totalAmount = 0d;
		List<GasModel> values = new ArrayList<GasModel>();

		List<Gas> gass = new ArrayList<Gas>();
		gass = findGassByStationId(stationId);
		if (gass != null && gass.size() > 0) {

			for (Gas gas : gass) {
				GasModel gm = new GasModel();
				List<GasStationSuppliers> ssslist = findsssDates(beginDate, endDate, stationId, gas.getId());
				if (ssslist != null && ssslist.size() > 0) {
					impsSum = ssslist.stream().filter(fdet -> fdet.getQuantity() != 0.0d)
							.mapToDouble(fdet -> fdet.getQuantity()).sum();
					importsSum = new BigDecimal(impsSum).setScale(2, RoundingMode.HALF_UP);
				}

				List<GunsRevenus> litrsPay = findRevsByDates(beginDate, endDate, gas.getId(), stationId);
				if (litrsPay != null && litrsPay.size() > 0) {
					litSum = litrsPay.stream().filter(fdet -> fdet.getLitersNum() != 0.0d)
							.mapToDouble(fdet -> fdet.getLitersNum()).sum();
					litrsSum = new BigDecimal(litSum).setScale(2, RoundingMode.HALF_UP);
				}
				List<FirstDayAmount> fDAmount = findFirstAmountByDates(beginDate, endDate, stationId, gas.getId());
				if (fDAmount != null && fDAmount.size() > 0) {
					firstAmountSum = new BigDecimal(fDAmount.get(0).getAmount()).setScale(3, RoundingMode.HALF_UP);
					gm.setFirstAmount(firstAmountSum);
					firstAmount = fDAmount.get(0).getAmount();
				}

//				lastAmountSum = (firstAmountSum.add(importsSum)).subtract(litrsSum);
				totalAmount = (firstAmount + impsSum) - litSum;
				lastAmountSum = new BigDecimal(totalAmount).setScale(2, RoundingMode.HALF_UP);
				gm.setLastAmount(lastAmountSum);
				gm.setGasId(gas.getId());
				gm.setImportsAmount(importsSum);
				gm.setLitrsAmount(litrsSum);
				gm.setGasTank(gas.getTankWidth());
				gm.setGasName(gas.getName());
				gm.setStationId(gas.getStationId());

				values.add(gm);
				litrsSum = new BigDecimal(0);
				litSum = 0d;
				importsSum = new BigDecimal(0);
				firstAmountSum = new BigDecimal(0);
				lastAmountSum = new BigDecimal(0);
				impsSum = 0d;
				// litrsSum = ssslist.stream().map(fdet -> fdet.quantity).sum();
			}
			// gass.stream().map(fdet->fdet.totalPrice).sum();
		}
		return values;

	}

	/**
	 * @param dateFrom
	 * @param dateTo
	 * @param stationId
	 * @param gasId     if sadad = 0 it is mean bill sadad = 1 it is mean sadad
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<GasStationSuppliers> findsssDates(Date dateFrom, Date dateTo, Integer stationId, Integer gasId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GasStationSuppliers.class);
		criteria.add(Restrictions.eq("stationId", stationId));
		criteria.add(Restrictions.eq("gasId", gasId));
		criteria.add(Restrictions.ge("supDate", dateFrom));
		criteria.add(Restrictions.le("supDate", dateTo));
		criteria.add(Restrictions.eq("sadad", 0));
		List<GasStationSuppliers> ssslist = criteria.list();
		return ssslist;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<FirstDayAmount> findFirstAmountByDates(Date dateFrom, Date dateTo, Integer stationId, Integer gasId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FirstDayAmount.class);
		criteria.add(Restrictions.eq("stationId", stationId));
		criteria.add(Restrictions.eq("gasId", gasId));
		criteria.add(Restrictions.ge("readDate", dateFrom));
		criteria.add(Restrictions.le("readDate", dateTo));
		List<FirstDayAmount> ssslist = criteria.list();
		return ssslist;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<GeneralPay> getAllGeneralPayBetweenDates(Integer stationId, Date beginDate, Date endDate) {
		BigDecimal litrsSum = new BigDecimal(0);
		Double litSum = 0d;
		BigDecimal importsSum = new BigDecimal(0);
		BigDecimal firstAmountSum = new BigDecimal(0);
		BigDecimal lastAmountSum = new BigDecimal(0);
		Double impsSum = 0d;
		Double firstAmount = 0d;
		Double totalAmount = 0d;
		List<GeneralPay> values = new ArrayList<GeneralPay>();

		List<Gas> gass = new ArrayList<Gas>();
		gass = findGassByStationId(stationId);
		if (gass != null && gass.size() > 0) {

			for (Gas gas : gass) {
				GeneralPay gm = new GeneralPay();
				List<GasStationSuppliers> ssslist = findsssDates(beginDate, endDate, stationId, gas.getId());
				if (ssslist != null && ssslist.size() > 0) {
					impsSum = ssslist.stream().filter(fdet -> fdet.getQuantity() != 0.0d)
							.mapToDouble(fdet -> fdet.getQuantity()).sum();
					importsSum = new BigDecimal(impsSum).setScale(2, RoundingMode.HALF_UP);
				}

				List<GunsRevenus> litrsPay = findRevsByDates(beginDate, endDate, gas.getId(), stationId);
				if (litrsPay != null && litrsPay.size() > 0) {
					litSum = litrsPay.stream().filter(fdet -> fdet.getLitersNum() != 0.0d)
							.mapToDouble(fdet -> fdet.getLitersNum()).sum();
					litrsSum = new BigDecimal(litSum).setScale(2, RoundingMode.HALF_UP);
				}
				List<FirstDayAmount> fDAmount = findFirstAmountByDates(beginDate, endDate, stationId, gas.getId());
				if (fDAmount != null && fDAmount.size() > 0) {
					firstAmountSum = new BigDecimal(fDAmount.get(0).getAmount()).setScale(3, RoundingMode.HALF_UP);
					gm.setFirstAmount(firstAmountSum);
					firstAmount = fDAmount.get(0).getAmount();
				}

//				lastAmountSum = (firstAmountSum.add(importsSum)).subtract(litrsSum);
				totalAmount = (firstAmount + impsSum) - litSum;
				lastAmountSum = new BigDecimal(totalAmount).setScale(2, RoundingMode.HALF_UP);
				gm.setDeffiernceAmount(lastAmountSum);
				gm.setFirstAmount(firstAmountSum);
				gm.setGasId(gas.getId());
				gm.setImportsAmount(importsSum);
				gm.setLitrPayAmount(litrsSum);
				gm.setStationId(gas.getStationId());
				gm.setDateInsert(beginDate);
				Stations st = (Stations) findEntityById(Stations.class, stationId);
				Gas gss = (Gas) findEntityById(Gas.class, gas.getId());
				gm.setStationName(st.getName());
				gm.setGasName(gss.getName());
				values.add(gm);
				litrsSum = new BigDecimal(0);
				litSum = 0d;
				importsSum = new BigDecimal(0);
				firstAmountSum = new BigDecimal(0);
				lastAmountSum = new BigDecimal(0);
				impsSum = 0d;
				// litrsSum = ssslist.stream().map(fdet -> fdet.quantity).sum();
			}
			// gass.stream().map(fdet->fdet.totalPrice).sum();
		}
		return values;

	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<GeneralPay> findAllGeneralPayByDates(Date dateFrom, Date dateTo) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GeneralPay.class);
		criteria.add(Restrictions.ge("dateInsert", dateFrom));
		criteria.add(Restrictions.le("dateInsert", dateTo));
		List<GeneralPay> ssslist = criteria.list();
		return ssslist;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Constantsasoul> findAsoulByTypeId(Integer expensisType) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Constantsasoul.class);

		criteria.add(Restrictions.eq("expensisId", expensisType));
		List<Constantsasoul> list = criteria.list();
		return list;
	}

	@Transactional
	@Override
	public List<SndSrfQbd> LoadAllSands(Date dateFrom, Date dateTo, Integer sndType) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SndSrfQbd.class);
		criteria.add(Restrictions.ge("sndDate", dateFrom));
		criteria.add(Restrictions.le("sndDate", dateTo));
		// criteria.add(Restrictions.ne("expensisTypesId", 5)); // tax
		// criteria.add(Restrictions.ne("expensisTypesId", 9)); // asoul
		criteria.add(Restrictions.eq("sndType", sndType));
		List<SndSrfQbd> list = criteria.list();
//		list.stream().mapToInt(snd -> snd.getExpensisTypesId()).filter(snd -> snd != 9);
		return list;
	}

	@Transactional
	@Override
	public List<RevuensModel> getFinancialMuneDates(Date beginDate, Date endDate) {
		List<RevuensModel> list = new ArrayList<RevuensModel>();
		List<Stations> sts = loadAll(Stations.class);
		BigDecimal amountSumDecimal = new BigDecimal(0);
		Double amountSum = 0d;
		for (Stations stns : sts) {
			RevuensModel rm = new RevuensModel();
			List<GunsRevenus> revenus = findRevsByDates(beginDate, endDate, null, stns.getId());
			amountSum = revenus.stream().filter(fdet -> fdet.getTotalPrice() != 0.0d)
					.mapToDouble(fdet -> fdet.getTotalPrice()).sum();
			amountSumDecimal = new BigDecimal(amountSum).setScale(3, RoundingMode.HALF_UP);
			List<Expensis> expensisList = findExpensisByDates(beginDate, endDate, null, stns.getId());
			Double listTotalSum = expensisList.stream().filter(fdet -> fdet.getExpensisQuantity() != 0.0d)
					.mapToDouble(fdet -> fdet.getExpensisQuantity()).sum();
			BigDecimal sum = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
			List<Expensis> expensisTax = findExpensisByDates(beginDate, endDate, 5, stns.getId());
			Double taxSum = expensisTax.stream().filter(fdet -> fdet.getExpensisQuantity() != 0.0d)
					.mapToDouble(fdet -> fdet.getExpensisQuantity()).sum();
			BigDecimal tax = new BigDecimal(taxSum).setScale(3, RoundingMode.HALF_UP);

			rm.setStationId(1);
			rm.setStationName(stns.getName());
			rm.setExpAmount(sum);
			rm.setTaxs(tax);
			rm.setRevAmount(amountSumDecimal);
			double dif = amountSumDecimal.doubleValue() - sum.doubleValue();
			rm.setProfit(new BigDecimal(dif).setScale(2, RoundingMode.HALF_UP));
			list.add(rm);
			amountSumDecimal = new BigDecimal(0);
			amountSum = 0d;
			sum = new BigDecimal(0);
		}
		return list;
	}

	@Transactional
	@Override
	public List<SndSrfQbd> LoadAllAsoul(Date dateFrom, Date dateTo) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SndSrfQbd.class);
		criteria.add(Restrictions.ge("sndDate", dateFrom));
		criteria.add(Restrictions.le("sndDate", dateTo));
		// criteria.add(Restrictions.ne("expensisTypesId", 5)); // tax
		criteria.add(Restrictions.eq("expensisTypesId", 9)); // asoul
		List<SndSrfQbd> list = criteria.list();
//		list.stream().mapToInt(snd -> snd.getExpensisTypesId()).filter(snd -> snd != 9);
		return list;
	}

	@Transactional
	@Override
	public List<SndSrfQbd> LoadAllSndsWithoutTaxa(Date dateFrom, Date dateTo) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SndSrfQbd.class);
		criteria.add(Restrictions.ge("sndDate", dateFrom));
		criteria.add(Restrictions.le("sndDate", dateTo));
		criteria.add(Restrictions.ne("expensisTypesId", 5)); // tax
		// criteria.add(Restrictions.eq("expensisTypesId", 9)); // asoul
		List<SndSrfQbd> list = criteria.list();
//		list.stream().mapToInt(snd -> snd.getExpensisTypesId()).filter(snd -> snd != 9);
		return list;
	}

	@Transactional
	@Override
	public List<FirstDayAmount> loadAllfRead(Integer stId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FirstDayAmount.class);
		criteria.add(Restrictions.eq("stationId", stId));
		List<FirstDayAmount> list = criteria.list();
		return list;
	}

}
