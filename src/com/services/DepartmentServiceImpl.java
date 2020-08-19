package com.services;

import java.util.List;

import com.common.CommonDao;
import com.entities.Gas;

public class DepartmentServiceImpl implements DepartmentService{
	
   CommonDao commonDao;


	public CommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public List<Gas> loadGass() {
		List gass = commonDao.findAll(Gas.class);
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
}
