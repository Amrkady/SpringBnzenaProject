package com.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.entities.Stations;
import com.services.DepartmentService;

@ManagedBean(name = "homeBean")
@SessionScoped
public class HomeBean {
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private String stationName = "";

	@PostConstruct
	public void init() {

	}

	public void stationName(Integer EntityId) {
		Stations st = (Stations) departmentServiceImpl.findEntityById(Stations.class, EntityId);
		if (st != null) {
			stationName = " „Õÿ… " + st.getName();
		}

	}

	public String stationOne() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("stationId", 1);
		System.out.println("1");
		stationName(1);
		return "success";
	}

	public String stationTwo() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("stationId", 2);
		System.out.println("2");
		stationName(2);
		return "success";
	}

	public String stationThree() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("stationId", 3);
		System.out.println("3");
		stationName(3);
		return "success";
	}

	public String stationFour() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("stationId", 4);
		System.out.println("4");
		stationName(4);
		return "success";
	}

	public String stationFive() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("stationId", 5);
		System.out.println("5");
		stationName(5);
		return "success";
	}

	public String stationSix() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("stationId", 6);
		System.out.println("6");
		stationName(6);
		return "success";
	}

	public String stationSeven() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("stationId", 7);
		System.out.println("7");
		stationName(7);
		return "success";
	}

	public String stationEight() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("stationId", 8);
		System.out.println("8");
		stationName(8);
		return "success";
	}

	public String stationNine() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("stationId", 9);
		System.out.println("9");
		stationName(9);
		return "success";
	}

	public DepartmentService getDepartmentServiceImpl() {
		return departmentServiceImpl;
	}

	public void setDepartmentServiceImpl(DepartmentService departmentServiceImpl) {
		this.departmentServiceImpl = departmentServiceImpl;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
}
