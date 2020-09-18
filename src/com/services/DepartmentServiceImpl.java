package com.services;

import java.util.List;

import com.common.CommonDao;
import com.entities.Attachment;
import com.entities.ExpensisTypes;
import com.entities.Gas;
import com.entities.GasGuns;
import com.entities.Rents;
import com.entities.Shops;
import com.entities.Stations;
import com.entities.Suppliers;
import com.entities.Taxs;

public class DepartmentServiceImpl implements DepartmentService {

	CommonDao commonDao;

	public CommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}
	@Override
	public Object findEntityById(Class entityClass, Integer EntityId) {
		return commonDao.findEntityById(entityClass, EntityId);
	}

	@Override
	public List<Gas> loadGass(Integer stationId) {
		List gass = commonDao.findGassByStationId(stationId);
		return gass;
	}

	@Override
	public void addGas(Gas gasAdd) {
		commonDao.saveObject(gasAdd);

	}

	@Override
	public void updateGas(Gas gasAdd) {
		commonDao.updateObject(gasAdd);

	}

	@Override
	public void deleteGas(Gas gasAdd) {
		commonDao.deleteObject(gasAdd);

	}

	@Override
	public void updateStation(Stations st) {
		commonDao.updateObject(st);

	}

	@Override
	public void save(Object object) {
		commonDao.saveObject(object);

	}

	@Override
	public void update(Object object) {
		commonDao.updateObject(object);

	}

	@Override
	public void delete(Object object) {
		commonDao.deleteObject(object);

	}

	@Override
	public List<GasGuns> loadGuns(Integer stationId) {
		List guns = commonDao.findGunsByStationId(stationId);
		return guns;
	}

	@Override
	public List<Suppliers> loadSuppliers(Integer stationId) {
		List sup = commonDao.findSuppliersByStationId(stationId);
		return sup;
	}

	@Override
	public List<Rents> loadRents(Integer stationId) {
		List rents = commonDao.findRentsByStationId(stationId);
		return rents;
	}

	@Override
	public List<Shops> loadShops(Integer stationId) {
		List sup = commonDao.findShopsByStationId(stationId);
		return sup;
	}

	@Override
	public List<Stations> loadStations() {
		List stations = commonDao.findAll(Stations.class);
		return stations;
	}

	@Override
	public List<Attachment> loadAttachments(Integer stId) {
		List atts = commonDao.findAttachmentsByStationId(stId);
		return atts;
	}

	@Override
	public List<Taxs> loadTaxs() {
		List stations = commonDao.findAll(Taxs.class);
		return stations;
	}

	@Override
	public List<ExpensisTypes> loadExpTypes() {
		List stations = commonDao.findAll(ExpensisTypes.class);
		return stations;
	}

}
