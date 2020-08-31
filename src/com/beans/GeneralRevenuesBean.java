package com.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import com.entities.Expensis;
import com.entities.Gas;
import com.entities.GasGuns;
import com.entities.GasStationSuppliers;
import com.entities.GunsRevenus;
import com.entities.Stations;
import com.entities.Taxs;
import com.models.SandModel;
import com.services.AccountsService;
import com.services.DepartmentService;

import common.util.MsgEntry;
import common.util.Utils;

@ManagedBean(name = "generalRevenuesBean")
@ViewScoped
public class GeneralRevenuesBean {
	@ManagedProperty(value = "#{accountsServiceImpl}")
	private AccountsService accountsServiceImpl;
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private GunsRevenus sssAdd = new GunsRevenus();
	private Integer stId;
	private List<GasGuns> gunsList = new ArrayList<GasGuns>();
	private List<Gas> gasList = new ArrayList<Gas>();
	private List<GunsRevenus> gunsRevenuList = new ArrayList<GunsRevenus>();
	private Date dateFrom;
	private Date dateTo;
	private Integer gasId;
	private List<Stations> stationsList = new ArrayList<Stations>();
	private List<Taxs> taxsList = new ArrayList<Taxs>();
	private double taxVal;
	private List<Expensis> expensisList = new ArrayList<Expensis>();
	private List<GasStationSuppliers> sssList = new ArrayList<GasStationSuppliers>();
	private double supTaxVal;
	private double supTotalQuantity;

	private SandModel sm = new SandModel();

	@PostConstruct
	public void init() {
		gunsRevenuList = accountsServiceImpl.loadAllGunsRevenusList();
		stationsList = departmentServiceImpl.loadStations();
		taxsList = departmentServiceImpl.loadTaxs();
		expensisList = accountsServiceImpl.loadAllExpensisList();
		sssList = accountsServiceImpl.loadsssList(-1);
		calTaxForOutSuppliers();
	}

	public String loadListByDates() {
		gunsRevenuList = new ArrayList<GunsRevenus>();
		if (stId == null) {
			stId = -1;
		}
		expensisList = accountsServiceImpl.loadExpensisByDates(dateFrom, dateTo, null, stId);
		gunsRevenuList = accountsServiceImpl.loadRevsByDates(dateFrom, dateTo, null, stId);
		sssList = accountsServiceImpl.loadsssByDates(dateFrom, dateTo, null, null, stId);
		calTaxForOutSuppliers();
		return "";
	}

	public void loadGass(AjaxBehaviorEvent event) {
		if (stId != null) {
			gasList = departmentServiceImpl.loadGass(stId);
		}

	}

	public void calTaxForOutSuppliers() {
		supTotalQuantity = 0;
		supTaxVal = 0;
		if (sssList != null) {
			for (GasStationSuppliers sup : sssList) {
				if (sup.getSupplierType() == 1) {
					supTotalQuantity += sup.getPrice();
				}

			}
			supTotalQuantity = Math.round(supTotalQuantity * 100) / 100.00d;
			supTaxVal = supTotalQuantity * (taxsList.get(0).getTaxValue() / 100);
			supTaxVal = Math.round(supTaxVal * 100) / 100.00d;
			for (GasStationSuppliers sup : sssList) {
				if (sup.getSupplierType() == 2) {
					supTotalQuantity += sup.getPrice();
				}

			}
			supTotalQuantity = supTotalQuantity - supTaxVal;
			supTotalQuantity = Math.round(supTotalQuantity * 100) / 100.00d;
		}

	}

	public void updateTaxsValue() {
		if (taxVal > 0) {
			try {
				Taxs tx = new Taxs();
				tx.setId(1);
				tx.setTaxValue(taxVal);
				departmentServiceImpl.update(tx);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.update"));
			} catch (Exception e) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.update"));
				e.printStackTrace();
			}
		}

	}

	public double sumPrice() {
		double sum = gunsRevenuList.stream().mapToDouble(fdet -> fdet.getTotalPrice()).sum();
		return sum;
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

				String reportName = "/reports/Bills_snad.jasper";
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

	public String printAll() {
		if (stId == null) {
			stId = -1;
		}
		try {
			String reportName = "/reports/revenues.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("stationId", stId);
			String fromDate = "1";
			String toDate = "1";
			String fromD = "1";
			String toD = "1";
			if (dateTo == null || dateFrom == null) {
				dateTo = null;
				dateFrom = null;
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				fromDate = sdf.format(dateFrom);
				toDate = sdf.format(dateTo);
				sdf = new SimpleDateFormat("dd/MM/yyyy");
				fromD = sdf.format(dateFrom);
				toD = sdf.format(dateTo);
			}
			parameters.put("dateFrom", fromDate);
			parameters.put("dateTo", toDate);
			parameters.put("dateF", fromD);
			parameters.put("dateT", toD);
			String headerPath = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/reports/logoreport.png");
			parameters.put("header", headerPath);
			Utils.printPdfReport(reportName, parameters);

		} catch (Exception e) {
			e.printStackTrace();
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

	public List<Gas> getGasList() {
		return gasList;
	}

	public void setGasList(List<Gas> gasList) {
		this.gasList = gasList;
	}

	public List<GasGuns> getGunsList() {
		return gunsList;
	}

	public void setGunsList(List<GasGuns> gunsList) {
		this.gunsList = gunsList;
	}

	public List<GunsRevenus> getGunsRevenuList() {
		return gunsRevenuList;
	}

	public void setGunsRevenuList(List<GunsRevenus> gunsRevenuList) {
		this.gunsRevenuList = gunsRevenuList;
	}

	public GunsRevenus getSssAdd() {
		return sssAdd;
	}

	public void setSssAdd(GunsRevenus sssAdd) {
		this.sssAdd = sssAdd;
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

	public Integer getGasId() {
		return gasId;
	}

	public void setGasId(Integer gasId) {
		this.gasId = gasId;
	}

	public List<Stations> getStationsList() {
		return stationsList;
	}

	public void setStationsList(List<Stations> stationsList) {
		this.stationsList = stationsList;
	}

	public List<Taxs> getTaxsList() {
		return taxsList;
	}

	public void setTaxsList(List<Taxs> taxsList) {
		this.taxsList = taxsList;
	}

	public double getTaxVal() {
		return taxVal;
	}

	public void setTaxVal(double taxVal) {
		this.taxVal = taxVal;
	}

	public List<Expensis> getExpensisList() {
		return expensisList;
	}

	public void setExpensisList(List<Expensis> expensisList) {
		this.expensisList = expensisList;
	}

	public List<GasStationSuppliers> getSssList() {
		return sssList;
	}

	public void setSssList(List<GasStationSuppliers> sssList) {
		this.sssList = sssList;
	}

	public double getSupTaxVal() {
		return supTaxVal;
	}

	public void setSupTaxVal(double supTaxVal) {
		this.supTaxVal = supTaxVal;
	}

	public double getSupTotalQuantity() {
		return supTotalQuantity;
	}

	public void setSupTotalQuantity(double supTotalQuantity) {
		this.supTotalQuantity = supTotalQuantity;
	}

	public SandModel getSm() {
		return sm;
	}

	public void setSm(SandModel sm) {
		this.sm = sm;
	}

}
