package com.beans;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.entities.Expensis;
import com.entities.ExpensisTypes;
import com.entities.SndSrfQbd;
import com.entities.Stations;
import com.models.SandModel;
import com.services.AccountsService;
import com.services.DepartmentService;

import common.util.MsgEntry;
import common.util.Utils;

@ManagedBean(name = "generalExpensisBean")
@ViewScoped
public class GeneralExpensisBean {
	@ManagedProperty(value = "#{accountsServiceImpl}")
	private AccountsService accountsServiceImpl;
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private Expensis sssAdd = new Expensis();
	private Integer stId;
	private List<Expensis> expensisList = new ArrayList<Expensis>();
	private List<ExpensisTypes> expensisTypesList = new ArrayList<ExpensisTypes>();
	private List<ExpensisTypes> expensisTypesSearch = new ArrayList<ExpensisTypes>();
	private List<Stations> stationsList = new ArrayList<Stations>();
	private Date dateFrom;
	private Date dateTo;
	private Integer supType;
	private SandModel sm = new SandModel();
	private SndSrfQbd snd = new SndSrfQbd();
	private List<SndSrfQbd> sndList = new ArrayList<SndSrfQbd>();
	@PostConstruct
	public void init() {
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
//		stId = (Integer) session.getAttribute("stationId");
//		if (stId != null) {
		expensisList = accountsServiceImpl.loadAllExpensisList();
		expensisTypesList = accountsServiceImpl.loadAllExpensisTypes(1);
		expensisTypesSearch = accountsServiceImpl.loadAllExpensisTypesList();
		stationsList = departmentServiceImpl.loadStations();
		sndList = accountsServiceImpl.loadSndByType(3, -1);
		// }
	}

	public String loadListByDates() {
		expensisList = new ArrayList<Expensis>();
		if (stId == null) {
			stId = -1;
		}
		expensisList = accountsServiceImpl.loadExpensisByDates(dateFrom, dateTo, supType, stId);
		return "";
	}

	public String addGas() {
		try {
			if (sssAdd != null) {
				// sssAdd.setStationId(stId);
				departmentServiceImpl.save(sssAdd);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				expensisList = accountsServiceImpl.loadAllExpensisList();
				sssAdd = new Expensis();
			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "";
	}


	public String deleteGas(Expensis gs) {
		if (gs != null) {
			try {
				departmentServiceImpl.delete(gs);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.delete"));
				expensisList = accountsServiceImpl.loadAllExpensisList();
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
			Expensis gs = (Expensis) event.getObject();
			departmentServiceImpl.update(gs);
			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.update"));
			expensisList = accountsServiceImpl.loadAllExpensisList();
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.update"));
			e.printStackTrace();
		}

	}

	public String print() {

		if (sm != null) {
			String hDate = null;
			try {
				hDate = Utils.grigDatesConvert(sm.getEntryDate());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			try {

				String reportName = "/reports/Bills_snad-srf.jasper";
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("custName", sm.getName());
				String reyal = String.valueOf(sm.getAmount());
				if (reyal.contains(".")) {
					reyal = reyal.substring(0, reyal.indexOf("."));
					parameters.put("reyal", Integer.parseInt(reyal));
					String hall = String.valueOf(sm.getAmount());
					hall = hall.substring(hall.indexOf(".") + 1);
					parameters.put("halaa", Integer.parseInt(hall));
				} else {
					parameters.put("reyal", (int) sm.getAmount());
					parameters.put("halaa", 00);
				}
				parameters.put("for", sm.getForWhat());
				parameters.put("payType", sm.getPayType());
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String grigDate = sdf.format(sm.getEntryDate());
				parameters.put("date", grigDate);
				parameters.put("dateH", hDate);
				parameters.put("costByLet", new BigDecimal(sm.getAmount()));
				String headerPath = FacesContext.getCurrentInstance().getExternalContext()
						.getRealPath("/reports/logoreport.png");
				parameters.put("header", headerPath);
//		//parameters.put("userId", employerId);
				Utils.printPdfReport(reportName, parameters);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return "";
	}
//
	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("·„ Ì „ «· ⁄œÌ·", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public String addSnad() {
		try {
			if (snd != null) {
				snd.setAmount(sm.getAmount());
				snd.setSndDate(sm.getEntryDate());
				snd.setName(sm.getName());
				snd.setForReason(sm.getForWhat());
				snd.setPayType(sm.getPayType());
				snd.setSndType(3); // 1 snd qabd
				departmentServiceImpl.save(snd);
				// canPrint = false;
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				sndList = accountsServiceImpl.loadSndByType(3, -1);
				snd = new SndSrfQbd();
				sm = new SandModel();
			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "";
	}

	public String print(SndSrfQbd sm) {

		if (sm != null) {
			String hDate = null;
			try {
				hDate = Utils.grigDatesConvert(sm.getSndDate());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			try {
				String reportName = "/reports/Bills_snad-srf.jasper";
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("custName", sm.getName());
				String reyal = String.valueOf(sm.getAmount());
				if (reyal.contains(".")) {
					reyal = reyal.substring(0, reyal.indexOf("."));
					parameters.put("reyal", Integer.parseInt(reyal));
					String hall = String.valueOf(sm.getAmount());
					hall = hall.substring(hall.indexOf(".") + 1);
					parameters.put("halaa", Integer.parseInt(hall));
				} else {
					parameters.put("reyal", (int) sm.getAmount());
					parameters.put("halaa", 00);
				}
				parameters.put("for", sm.getForReason() == null ? " " : sm.getForReason());
				parameters.put("payType", sm.getPayType() == null ? "" : sm.getPayType());
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String grigDate = sdf.format(sm.getSndDate());
				parameters.put("date", grigDate);
				parameters.put("dateH", hDate);
				parameters.put("costByLet", new BigDecimal(sm.getAmount()));
				String headerPath = FacesContext.getCurrentInstance().getExternalContext()
						.getRealPath("/reports/logoreport.png");
				parameters.put("header", headerPath);
//		//parameters.put("userId", employerId);
				Utils.printPdfReport(reportName, parameters);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return "";
	}

	public DepartmentService getDepartmentServiceImpl() {
		return departmentServiceImpl;
	}

	public void setDepartmentServiceImpl(DepartmentService departmentServiceImpl) {
		this.departmentServiceImpl = departmentServiceImpl;
	}

	public Integer getStId() {
		return stId;
	}

	public void setStId(Integer stId) {
		this.stId = stId;
	}

	public AccountsService getAccountsServiceImpl() {
		return accountsServiceImpl;
	}

	public void setAccountsServiceImpl(AccountsService accountsServiceImpl) {
		this.accountsServiceImpl = accountsServiceImpl;
	}

	public List<Expensis> getExpensisList() {
		return expensisList;
	}

	public void setExpensisList(List<Expensis> expensisList) {
		this.expensisList = expensisList;
	}

	public List<ExpensisTypes> getExpensisTypesList() {
		return expensisTypesList;
	}

	public void setExpensisTypesList(List<ExpensisTypes> expensisTypesList) {
		this.expensisTypesList = expensisTypesList;
	}

	public void setSssAdd(Expensis sssAdd) {
		this.sssAdd = sssAdd;
	}

	public Expensis getSssAdd() {
		return sssAdd;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Integer getSupType() {
		return supType;
	}

	public void setSupType(Integer supType) {
		this.supType = supType;
	}

	public List<Stations> getStationsList() {
		return stationsList;
	}

	public void setStationsList(List<Stations> stationsList) {
		this.stationsList = stationsList;
	}

	public List<ExpensisTypes> getExpensisTypesSearch() {
		return expensisTypesSearch;
	}

	public void setExpensisTypesSearch(List<ExpensisTypes> expensisTypesSearch) {
		this.expensisTypesSearch = expensisTypesSearch;
	}

	public SandModel getSm() {
		return sm;
	}

	public void setSm(SandModel sm) {
		this.sm = sm;
	}

	public SndSrfQbd getSnd() {
		return snd;
	}

	public void setSnd(SndSrfQbd snd) {
		this.snd = snd;
	}

	public List<SndSrfQbd> getSndList() {
		return sndList;
	}

	public void setSndList(List<SndSrfQbd> sndList) {
		this.sndList = sndList;
	}

}
