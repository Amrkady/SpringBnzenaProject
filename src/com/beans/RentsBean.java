package com.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.RowEditEvent;

import com.entities.Rents;
import com.entities.Shops;
import com.services.DepartmentService;

import common.util.MsgEntry;
import common.util.Utils;

@ManagedBean(name = "rentsBean")
@ViewScoped
public class RentsBean {
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private List<Rents> rentsList = new ArrayList<Rents>();
	private Rents rentsAdd = new Rents();
	private List<Shops> shopsList = new ArrayList<Shops>();
	private Integer stId;

	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		stId = (Integer) session.getAttribute("stationId");
		if (stId != null) {
			rentsList = departmentServiceImpl.loadRents(stId);
			shopsList = departmentServiceImpl.loadShops(stId);
		}
	}

	public String addGas() {
		try {
			if (rentsAdd != null) {
				rentsAdd.setStationId(stId);
				departmentServiceImpl.save(rentsAdd);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				rentsList = departmentServiceImpl.loadRents(stId);
				rentsAdd = new Rents();
			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "";
	}

//
	public String deleteGas(Rents gs) {
		if (gs != null) {
			try {
				departmentServiceImpl.delete(gs);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.delete"));
				rentsList = departmentServiceImpl.loadRents(stId);
			} catch (Exception e) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.delete"));
				e.printStackTrace();
			}
		}
		return "";
	}

//
	public void onRowEdit(RowEditEvent event) {
		try {
			Rents gs = (Rents) event.getObject();
			departmentServiceImpl.update(gs);
			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.update"));
			rentsList = departmentServiceImpl.loadRents(stId);
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.update"));
			e.printStackTrace();
		}

	}

//
	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("·„ Ì „ «· ⁄œÌ·", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public DepartmentService getDepartmentServiceImpl() {
		return departmentServiceImpl;
	}

	public void setDepartmentServiceImpl(DepartmentService departmentServiceImpl) {
		this.departmentServiceImpl = departmentServiceImpl;
	}

	public List<Rents> getRentsList() {
		return rentsList;
	}

	public void setRentsList(List<Rents> rentsList) {
		this.rentsList = rentsList;
	}

	public Rents getRentsAdd() {
		return rentsAdd;
	}

	public void setRentsAdd(Rents rentsAdd) {
		this.rentsAdd = rentsAdd;
	}

	public List<Shops> getShopsList() {
		return shopsList;
	}

	public void setShopsList(List<Shops> shopsList) {
		this.shopsList = shopsList;
	}

	public Integer getStId() {
		return stId;
	}

	public void setStId(Integer stId) {
		this.stId = stId;
	}

}
