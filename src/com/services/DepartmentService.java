package com.services;

import java.util.List;

import com.entities.Attachment;
import com.entities.Gas;
import com.entities.GasGuns;
import com.entities.Rents;
import com.entities.Shops;
import com.entities.Stations;
import com.entities.Suppliers;
import com.entities.Taxs;

public interface DepartmentService {
	public void addGas(Gas gasAdd);

	public void updateGas(Gas gasAdd);

	public void deleteGas(Gas gasAdd);
	
	public void updateStation(Stations st);

	public void save(Object object);

	public void update(Object object);

	public void delete(Object object);


	public Object findEntityById(Class entityClass, Integer EntityId);

	public List<Gas> loadGass(Integer stationId);

	public List<GasGuns> loadGuns(Integer stationId);

	public List<Suppliers> loadSuppliers(Integer stationId);

	public List<Rents> loadRents(Integer stationId);

	public List<Shops> loadShops(Integer stationId);

	public List<Stations> loadStations();

	public List<Attachment> loadAttachments(Integer stId);

	List<Taxs> loadTaxs();
}
