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

import com.entities.Gas;
import com.services.DepartmentService;

import common.util.MsgEntry;
import common.util.Utils;

@ManagedBean(name = "gasBean")
@ViewScoped
public class GasBean {
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private List<Gas> gasList = new ArrayList<Gas>();
	private Gas gasAdd = new Gas();
	private Integer stId;

	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		stId = (Integer) session.getAttribute("stationId");
		if (stId != null) {
			gasList = departmentServiceImpl.loadGass(stId);
		}

	}

	public String addGas() {
		try {
			if (gasAdd != null) {
				gasAdd.setStationId(stId);
				departmentServiceImpl.addGas(gasAdd);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				gasList = departmentServiceImpl.loadGass(stId);
				gasAdd = new Gas();
			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "";
	}

//
	public String deleteGas(Gas gs) {
		if (gs != null) {
			try {
				departmentServiceImpl.deleteGas(gs);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.delete"));
				gasList = departmentServiceImpl.loadGass(stId);
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
			Gas gs = (Gas) event.getObject();
			departmentServiceImpl.updateGas(gs);
			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.update"));
			gasList = departmentServiceImpl.loadGass(stId);
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.update"));
			e.printStackTrace();
		}

	}

//
	public void onRowCancel(RowEditEvent event) {
//		
		FacesMessage msg = new FacesMessage("�� ��� �������", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public DepartmentService getDepartmentServiceImpl() {
		return departmentServiceImpl;
	}

	public void setDepartmentServiceImpl(DepartmentService departmentServiceImpl) {
		this.departmentServiceImpl = departmentServiceImpl;
	}

	public List<Gas> getGasList() {
		return gasList;
	}

	public void setGasList(List<Gas> gasList) {
		this.gasList = gasList;
	}

	public Gas getGasAdd() {
		return gasAdd;
	}

	public void setGasAdd(Gas gasAdd) {
		this.gasAdd = gasAdd;
	}

	public Integer getStId() {
		return stId;
	}

	public void setStId(Integer stId) {
		this.stId = stId;
	}

}
