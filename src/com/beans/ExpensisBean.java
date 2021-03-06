package com.beans;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.event.RowEditEvent;

import com.entities.Constantsasoul;
import com.entities.Expensis;
import com.entities.ExpensisTypes;
import com.entities.SndSrfQbd;
import com.services.AccountsService;
import com.services.DepartmentService;

import common.util.MsgEntry;
import common.util.Utils;

@ManagedBean(name = "expensisBean")
@ViewScoped
public class ExpensisBean {
	@ManagedProperty(value = "#{accountsServiceImpl}")
	private AccountsService accountsServiceImpl;
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private Expensis sssAdd = new Expensis();
	private Integer stId;
	private List<Expensis> expensisList = new ArrayList<Expensis>();
	private List<ExpensisTypes> expensisTypesList = new ArrayList<ExpensisTypes>();
	private Date dateFrom;
	private Date dateTo;
	private Integer supType;
	private List<SndSrfQbd> sndList = new ArrayList<SndSrfQbd>();
	private SndSrfQbd snd = new SndSrfQbd();
	private double listTotalSum;
	private BigDecimal listTotalSumDecimal;
	private List<Constantsasoul> asoulList = new ArrayList<>();
	private boolean show = false;
	private boolean vat;
	private double taxValue;

	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		stId = (Integer) session.getAttribute("stationId");
		if (stId != null) {
			expensisList = accountsServiceImpl.loadExpensisByStationId(stId);
			expensisTypesList = accountsServiceImpl.loadAllExpensisTypes(0);
			sndList = accountsServiceImpl.loadSndByType(2, stId);
			if (expensisList != null && expensisList.size() > 0) {
				listTotalSum = expensisList.stream().filter(fdet -> fdet.getExpensisQuantity() != 0.0d)
						.mapToDouble(fdet -> fdet.getExpensisQuantity()).sum();
				BigDecimal sum = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
				listTotalSumDecimal = sum;
				System.out.println("" + listTotalSumDecimal);

			}
		}
	}

	public String loadListByDates() {
		expensisList = new ArrayList<Expensis>();
		expensisList = accountsServiceImpl.loadExpensisByDates(dateFrom, dateTo, supType, stId);
		if (expensisList != null && expensisList.size() > 0) {
			listTotalSum = expensisList.stream().filter(fdet -> fdet.getExpensisQuantity() != 0.0d)
					.mapToDouble(fdet -> fdet.getExpensisQuantity()).sum();
			BigDecimal sum = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
			listTotalSumDecimal = sum;
			System.out.println("" + listTotalSumDecimal);

		}
		return "";
	}

	public String addGas() {
		try {
			if (sssAdd != null) {
				sssAdd.setStationId(stId);
				departmentServiceImpl.save(sssAdd);
				if (snd != null) {
					snd.setSndDate(sssAdd.getMonthDate());
					snd.setAmount(sssAdd.getExpensisQuantity());
					snd.setStationId(stId);
					snd.setSndType(2);
					snd.setExpensisTypesId(sssAdd.getExpensisType());
					snd.setAsoulId(sssAdd.getAsoulId());
					departmentServiceImpl.save(snd);
					sssAdd = new Expensis();
					snd = new SndSrfQbd();
				}
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				expensisList = accountsServiceImpl.loadExpensisByStationId(stId);
				if (expensisList != null && expensisList.size() > 0) {
					listTotalSum = expensisList.stream().filter(fdet -> fdet.getExpensisQuantity() != 0.0d)
							.mapToDouble(fdet -> fdet.getExpensisQuantity()).sum();
					BigDecimal sum = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
					listTotalSumDecimal = sum;
					System.out.println("" + listTotalSumDecimal);

				}
				sndList = accountsServiceImpl.loadSndByType(2, stId);
				sssAdd = new Expensis();
			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "";
	}

	public void updateCom() {
		if (sssAdd.getExpensisQuantity() > 0 && vat == true) {
			taxValue = (sssAdd.getExpensisQuantity() / 1.15) * 0.15;
			taxValue = Math.round(taxValue * 100) / 100.00d;

		} else {
			taxValue = 0.0;
		}
	}

	public String deletesnd(SndSrfQbd gs) {
		if (gs != null) {
			try {
				departmentServiceImpl.delete(gs);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.delete"));
				sndList = accountsServiceImpl.loadSndByType(2, stId);
			} catch (Exception e) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.delete"));
				e.printStackTrace();
			}
		}
		return "";
	}

	public void loadAsoulList(AjaxBehaviorEvent event) {
		show = false;
		asoulList = accountsServiceImpl.loadAsoulByTypeId(sssAdd.getExpensisType());
		if (asoulList != null && asoulList.size() > 0) {
			show = true;
		}
	}

	public void loadAsList(Expensis expensis) {

		asoulList = accountsServiceImpl.loadAsoulByTypeId(expensis.getExpensisType());

	}

//	public void loadGuns(AjaxBehaviorEvent event) {
//		if (sssAdd != null) {
//			if (sssAdd.getGasId() != null) {
//				Gas gs = (Gas) departmentServiceImpl.findEntityById(Gas.class, sssAdd.getGasId());
//				sssAdd.setLiterPrice(gs.getPrice());
//				// load guns by stationId and gas id
//				gunsList = accountsServiceImpl.loadGunsByStationIdAndGasId(stId, sssAdd.getGasId());
//			}
//		}
//
//	}

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
				parameters.put("costByLet", sm.getAmount());
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

	public String deleteGas(Expensis gs) {
		if (gs != null) {
			try {
				departmentServiceImpl.delete(gs);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.delete"));
				expensisList = accountsServiceImpl.loadExpensisByStationId(stId);
				if (expensisList != null && expensisList.size() > 0) {
					listTotalSum = expensisList.stream().filter(fdet -> fdet.getExpensisQuantity() != 0.0d)
							.mapToDouble(fdet -> fdet.getExpensisQuantity()).sum();
					BigDecimal sum = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
					listTotalSumDecimal = sum;
					System.out.println("" + listTotalSumDecimal);

				}
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
			expensisList = accountsServiceImpl.loadExpensisByStationId(stId);
			if (expensisList != null && expensisList.size() > 0) {
				listTotalSum = expensisList.stream().filter(fdet -> fdet.getExpensisQuantity() != 0.0d)
						.mapToDouble(fdet -> fdet.getExpensisQuantity()).sum();
				BigDecimal sum = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
				listTotalSumDecimal = sum;
				System.out.println("" + listTotalSumDecimal);

			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.update"));
			e.printStackTrace();
		}

	}

//
	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("�� ��� �������", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);

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

	public List<SndSrfQbd> getSndList() {
		return sndList;
	}

	public void setSndList(List<SndSrfQbd> sndList) {
		this.sndList = sndList;
	}

	public SndSrfQbd getSnd() {
		return snd;
	}

	public void setSnd(SndSrfQbd snd) {
		this.snd = snd;
	}

	public double getListTotalSum() {
		return listTotalSum;
	}

	public void setListTotalSum(double listTotalSum) {
		this.listTotalSum = listTotalSum;
	}

	public BigDecimal getListTotalSumDecimal() {
		return listTotalSumDecimal;
	}

	public void setListTotalSumDecimal(BigDecimal listTotalSumDecimal) {
		this.listTotalSumDecimal = listTotalSumDecimal;
	}

	public List<Constantsasoul> getAsoulList() {
		return asoulList;
	}

	public void setAsoulList(List<Constantsasoul> asoulList) {
		this.asoulList = asoulList;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public boolean isVat() {
		return vat;
	}

	public void setVat(boolean vat) {
		this.vat = vat;
	}

	public double getTaxValue() {
		return taxValue;
	}

	public void setTaxValue(double taxValue) {
		this.taxValue = taxValue;
	}

}
