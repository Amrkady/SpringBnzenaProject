package com.services;

import java.util.List;

import com.entities.Gas;

public interface DepartmentService {

	public List<Gas> loadGass();

	public void addGas(Gas gasAdd);

	public void updateGas(Gas gasAdd);

	public void deleteGas(Gas gasAdd);
		
	
}
