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

import com.entities.Suppliers;
import com.services.DepartmentService;

import common.util.MsgEntry;
import common.util.Utils;

@ManagedBean(name = "suppliersBean")
@ViewScoped
public class SuppliersBean {
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private List<Suppliers> suppliersList = new ArrayList<Suppliers>();
	private Suppliers supAdd = new Suppliers();
	private Integer stId;

	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		stId = (Integer) session.getAttribute("stationId");
		if (stId != null) {
			suppliersList = departmentServiceImpl.loadSuppliers(stId);
		}
	}

	public String addGas() {
		try {
			if (supAdd != null) {
				supAdd.setStationId(stId);
				departmentServiceImpl.save(supAdd);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				suppliersList = departmentServiceImpl.loadSuppliers(stId);
				supAdd = new Suppliers();
			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "";
	}

//
	public String deleteGas(Suppliers gs) {
		if (gs != null) {
			try {
				departmentServiceImpl.delete(gs);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.delete"));
				suppliersList = departmentServiceImpl.loadSuppliers(stId);
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
			Suppliers gs = (Suppliers) event.getObject();
			departmentServiceImpl.update(gs);
			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.update"));
			suppliersList = departmentServiceImpl.loadSuppliers(stId);
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

	public List<Suppliers> getSuppliersList() {
		return suppliersList;
	}

	public void setSuppliersList(List<Suppliers> suppliersList) {
		this.suppliersList = suppliersList;
	}

	public Suppliers getSupAdd() {
		return supAdd;
	}

	public void setSupAdd(Suppliers supAdd) {
		this.supAdd = supAdd;
	}

	public Integer getStId() {
		return stId;
	}

	public void setStId(Integer stId) {
		this.stId = stId;
	}

}
