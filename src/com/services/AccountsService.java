package com.services;

import java.util.Date;
import java.util.List;

import com.entities.Constantsasoul;
import com.entities.Expensis;
import com.entities.ExpensisTypes;
import com.entities.FirstDayAmount;
import com.entities.GasGuns;
import com.entities.GasStationSuppliers;
import com.entities.GeneralPay;
import com.entities.GunsRevenus;
import com.entities.SndSrfQbd;
import com.models.GasModel;
import com.models.RevuensModel;

public interface AccountsService {

	public List<GasStationSuppliers> loadsssList(Integer stId);

	public List<GunsRevenus> loadGunsRevenusList(Integer stId);

	public List<GasGuns> loadGunsByStationIdAndGasId(Integer stationId, Integer gasId);

	public Integer loadGunFirstRead(Integer stationId, Integer gunId, Integer gasId);

	public List<Expensis> loadExpensisByStationId(Integer stationId);

	public List<ExpensisTypes> loadAllExpensisTypes(Integer general);

	public List<GasStationSuppliers> loadsssByDates(Date dateFrom, Date dateTo, Integer supplierId, Integer suppType,
			Integer stationId);

	public List<GunsRevenus> loadRevsByDates(Date dateFrom, Date dateTo, Integer gasId, Integer stationId);

	public List<Expensis> loadExpensisByDates(Date dateFrom, Date dateTo, Integer supType, Integer stId);

	public List<Expensis> loadAllExpensisList();

	public List<ExpensisTypes> loadAllExpensisTypesList();

	public List<GunsRevenus> loadAllGunsRevenusList();

	public List<SndSrfQbd> loadSndByType(Integer type, Integer stationId);

	public List<GasModel> getAllLitersBetweenDates(Integer stationId, Date beginDate, Date endDate);

	public List<GeneralPay> loadAllGeneralPayBetweenDates(Integer stationId, Date beginDate, Date endDate);

	public List<GeneralPay> loadAllGeneralPayByDates(Date dateFrom, Date dateTo);

	public List<Constantsasoul> loadAsoulByTypeId(Integer expensisType);

	public List<RevuensModel> getFinancialMuneDates(Date beginDate, Date endDate);

	public List<SndSrfQbd> LoadAllAsoul(Date beginDate, Date endDate);

	public List<SndSrfQbd> LoadAllSndsWithoutTaxa(Date beginDate, Date endDate);

	public List<SndSrfQbd> findGeneralSndByType(Integer type, Integer stationId);

	public List<FirstDayAmount> loadAllfRead(Integer stId);
	
}
