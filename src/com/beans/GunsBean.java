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
import com.entities.GasGuns;
import com.services.DepartmentService;

import common.util.MsgEntry;
import common.util.Utils;

@ManagedBean(name = "gunsBean")
@ViewScoped
public class GunsBean {
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private List<GasGuns> gunsList = new ArrayList<GasGuns>();
	private GasGuns gunsAdd = new GasGuns();
	private List<Gas> gasList = new ArrayList<Gas>();
	private Integer stId;
	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		stId = (Integer) session.getAttribute("stationId");
		if (stId != null) {
			gunsList = departmentServiceImpl.loadGuns(stId);
			gasList = departmentServiceImpl.loadGass(stId);
		}

	}

	public String addGas() {
		try {
			if (gunsAdd != null) {
				gunsAdd.setStationId(stId);
				departmentServiceImpl.save(gunsAdd);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				gunsList = departmentServiceImpl.loadGuns(stId);
				gunsAdd = new GasGuns();
			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "";
	}

//
	public String deleteGas(GasGuns gs) {
		if (gs != null) {
			try {
				departmentServiceImpl.delete(gs);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.delete"));
				gunsList = departmentServiceImpl.loadGuns(stId);
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
			GasGuns gs = (GasGuns) event.getObject();
			departmentServiceImpl.update(gs);
			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.update"));
			gunsList = departmentServiceImpl.loadGuns(stId);
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

	public List<GasGuns> getGunsList() {
		return gunsList;
	}

	public void setGunsList(List<GasGuns> gunsList) {
		this.gunsList = gunsList;
	}

	public GasGuns getGunsAdd() {
		return gunsAdd;
	}

	public void setGunsAdd(GasGuns gunsAdd) {
		this.gunsAdd = gunsAdd;
	}

	public List<Gas> getGasList() {
		return gasList;
	}

	public void setGasList(List<Gas> gasList) {
		this.gasList = gasList;
	}

	public Integer getStId() {
		return stId;
	}

	public void setStId(Integer stId) {
		this.stId = stId;
	}

}
