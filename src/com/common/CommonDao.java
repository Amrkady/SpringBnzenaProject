
package com.common;

import java.util.Date;
import java.util.List;

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
import com.entities.Suppliers;
import com.entities.Users;
import com.models.GasModel;
import com.models.RevuensModel;

public interface CommonDao {

	public boolean saveObject(Object object);

	List<Object> findAll(Class object);

	Object findEntityById(Class entityClass, Integer EntityId);

	boolean deleteObject(Object object);

	boolean updateObject(Object myObject);


	public Users loadUser(final String username, final String password);

	public List<Shops> findShopsByStationId(Integer stationId);

	public List<GasGuns> findGunsByStationId(Integer stationId);

	public List<Gas> findGassByStationId(Integer stationId);

	public List<Suppliers> findSuppliersByStationId(Integer stationId);

	public List<Rents> findRentsByStationId(Integer stationId);

	public List<GasStationSuppliers> findsssByStationId(Integer stationId);

	public List<GunsRevenus> findGunsRevenusByStationId(Integer stationId);

	public List<GasGuns> findGunsByStationIdAndGasId(Integer stationId, Integer gasId);

	public Integer findGunFirstRead(Integer stationId, Integer gunId, Integer gasId);

	public List<Expensis> findExpensisByStationId(Integer stationId);

	public List<Attachment> findAttachmentsByStationId(Integer stationId);

	public List<GasStationSuppliers> findsssByDates(Date dateFrom, Date dateTo, Integer supplierId, Integer suppType,
			Integer stationId, Integer gasId);

	public List<GunsRevenus> findRevsByDates(Date dateFrom, Date dateTo, Integer gasId, Integer stationId);

	public List<Expensis> findExpensisByDates(Date dateFrom, Date dateTo, Integer supType, Integer stId);

	public List<ExpensisTypes> findExpensisTypes(Integer general);

	public List<SndSrfQbd> findSndByType(Integer type, Integer stationId);

	public List<GasModel> getAllLitersBetweenDates(Integer stationId, Date beginDate, Date endDate);

	public List<GeneralPay> getAllGeneralPayBetweenDates(Integer stationId, Date beginDate, Date endDate);

	public List<GeneralPay> findAllGeneralPayByDates(Date dateFrom, Date dateTo);

	public List<Constantsasoul> findAsoulByTypeId(Integer expensisType);

	public List<SndSrfQbd> LoadAllSands(Date dateFrom, Date dateTo, Integer sndType);

	public List<RevuensModel> getFinancialMuneDates(Date beginDate, Date endDate);

	public List<SndSrfQbd> LoadAllAsoul(Date dateFrom, Date dateTo);

	public List<SndSrfQbd> LoadAllSndsWithoutTaxa(Date dateFrom, Date dateTo);

	public List<SndSrfQbd> findGeneralSndByType(Integer type, Integer stationId);

	public List<FirstDayAmount> loadAllfRead(Integer stId);

	public List<FirstDayAmount> findFirstAmountByDates(Date dateFrom, Date dateTo, Integer stationId, Integer gasId);

}
