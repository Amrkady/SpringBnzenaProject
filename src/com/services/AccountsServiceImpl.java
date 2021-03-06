package com.services;

import java.util.Date;
import java.util.List;

import com.common.CommonDao;
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

public class AccountsServiceImpl implements AccountsService {

	CommonDao commonDao;

	public CommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public List<GasStationSuppliers> loadsssList(Integer stId) {
		return commonDao.findsssByStationId(stId);
	}

	@Override
	public List<GunsRevenus> loadGunsRevenusList(Integer stId) {
		return commonDao.findGunsRevenusByStationId(stId);
	}

	@Override
	public List<GasGuns> loadGunsByStationIdAndGasId(Integer stationId, Integer gasId) {
		return commonDao.findGunsByStationIdAndGasId(stationId, gasId);
	}

	@Override
	public Integer loadGunFirstRead(Integer stationId, Integer gunId, Integer gasId) {
		return commonDao.findGunFirstRead(stationId, gunId, gasId);
	}

	@Override
	public List<Expensis> loadExpensisByStationId(Integer stationId) {
		return commonDao.findExpensisByStationId(stationId);
	}

	@Override
	public List<ExpensisTypes> loadAllExpensisTypes(Integer general) {
		List et = commonDao.findExpensisTypes(general);
		return et;
	}

	@Override
	public List<Expensis> loadAllExpensisList() {
		List et = commonDao.findAll(Expensis.class);
		return et;
	}

	@Override
	public List<GasStationSuppliers> loadsssByDates(Date dateFrom, Date dateTo, Integer supplierId, Integer suppType,
			Integer stationId, Integer gasId) {
		List ls = commonDao.findsssByDates(dateFrom, dateTo, supplierId, suppType, stationId, gasId);
		return ls;
	}

	@Override
	public List<GunsRevenus> loadRevsByDates(Date dateFrom, Date dateTo, Integer gasId, Integer stationId) {
		List ls = commonDao.findRevsByDates(dateFrom, dateTo, gasId, stationId);
		return ls;
	}

	@Override
	public List<Expensis> loadExpensisByDates(Date dateFrom, Date dateTo, Integer supType, Integer stId) {
		List ls = commonDao.findExpensisByDates(dateFrom, dateTo, supType, stId);
		return ls;
	}

	@Override
	public List<ExpensisTypes> loadAllExpensisTypesList() {
		List ls = commonDao.findAll(ExpensisTypes.class);
		return ls;
	}

	@Override
	public List<GunsRevenus> loadAllGunsRevenusList() {
		List ls = commonDao.findAll(GunsRevenus.class);
		return ls;
	}

	@Override
	public List<SndSrfQbd> loadSndByType(Integer type, Integer stationId) {
		List ls = commonDao.findSndByType(type, stationId);
		return ls;
	}

	@Override
	public List<SndSrfQbd> findGeneralSndByType(Integer type, Integer stationId) {
		List ls = commonDao.findGeneralSndByType(type, stationId);
		return ls;
	}

	@Override
	public List<GasModel> getAllLitersBetweenDates(Integer stationId, Date beginDate, Date endDate) {
		List ls = commonDao.getAllLitersBetweenDates(stationId, beginDate, endDate);
		return ls;
	}


	@Override
	public List<GeneralPay> loadAllGeneralPayBetweenDates(Integer stationId, Date beginDate, Date endDate) {
		List ls = commonDao.getAllGeneralPayBetweenDates(stationId, beginDate, endDate);
		return ls;
	}

	@Override
	public List<GeneralPay> loadAllGeneralPayByDates(Date dateFrom, Date dateTo) {
		List ls = commonDao.findAllGeneralPayByDates(dateFrom, dateTo);
		return ls;
	}

	@Override
	public List<Constantsasoul> loadAsoulByTypeId(Integer expensisType) {
		List ls = commonDao.findAsoulByTypeId(expensisType);
		return ls;
	}

	@Override
	public List<RevuensModel> getFinancialMuneDates(Date beginDate, Date endDate) {
		List ls = commonDao.getFinancialMuneDates(beginDate, endDate);
		return ls;

	}

	@Override
	public List<SndSrfQbd> LoadAllAsoul(Date beginDate, Date endDate) {
		List ls = commonDao.LoadAllAsoul(beginDate, endDate);
		return ls;
	}

	@Override
	public List<SndSrfQbd> LoadAllSndsWithoutTaxa(Date beginDate, Date endDate) {
		List ls = commonDao.LoadAllSndsWithoutTaxa(beginDate, endDate);
		return ls;
	}

	@Override
	public List<FirstDayAmount> loadAllfRead(Integer stId) {
		List ls = commonDao.loadAllfRead(stId);
		return ls;
	}

	@Override
	public List<FirstDayAmount> findFirstAmountByDates(Date dateFrom, Date dateTo, Integer stationId, Integer gasId) {
		List ls = commonDao.findFirstAmountByDates(dateFrom, dateTo, stationId, gasId);
		return ls;
	}
}
