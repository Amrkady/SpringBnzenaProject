package com.beans;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

import com.entities.Gas;
import com.entities.GasStationSuppliers;
import com.entities.Stations;
import com.entities.Suppliers;
import com.services.AccountsService;
import com.services.DepartmentService;

import common.util.MsgEntry;
import common.util.Utils;

@ManagedBean(name = "importsBean")
@ViewScoped
public class ImportsBean {
	@ManagedProperty(value = "#{accountsServiceImpl}")
	private AccountsService accountsServiceImpl;
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private List<GasStationSuppliers> sssList = new ArrayList<GasStationSuppliers>();
	private List<Stations> stationsList = new ArrayList<Stations>();
	private GasStationSuppliers sssAdd = new GasStationSuppliers();
	private Integer stId;
	private List<Suppliers> supsList = new ArrayList<Suppliers>();
	private List<Suppliers> supsSadadList = new ArrayList<Suppliers>();
	private boolean show = false;
	private boolean enableShow = false;
	private Date dateFrom;
	private Date dateTo;
	private List<Gas> gasList = new ArrayList<Gas>();
	private Integer supplierId;
	private String supType = null;
	private double listTotalSum;
	private BigDecimal listTotalSumDecimal;
	private double listTotalLitrs;
	private BigDecimal listTotalLitrsDecimal;
	private BigDecimal firstAmountValue;
	private Integer gasId;

	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		stId = (Integer) session.getAttribute("stationId");
		if (stId != null) {
			sssList = accountsServiceImpl.loadsssList(stId);
			gasList = departmentServiceImpl.loadGass(stId);
			if (sssList != null && sssList.size() > 0) {
				supsSadadList = departmentServiceImpl.loadSuppliers(stId);
				listTotalSum = sssList.stream().filter(fdet -> fdet.getPrice() != 0.0d)
						.mapToDouble(fdet -> fdet.getPrice()).sum();

				listTotalSumDecimal = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);

				listTotalLitrs = sssList.stream().filter(fdet -> fdet.getQuantity() != 0.0d)
						.mapToDouble(fdet -> fdet.getQuantity()).sum();

				listTotalLitrsDecimal = new BigDecimal(listTotalLitrs).setScale(3, RoundingMode.HALF_UP);

				System.out.println("" + listTotalSumDecimal);

			}
		}
	}

	public String loadListByDates() {
		sssList = new ArrayList<GasStationSuppliers>();
		if (supType == null || supType.isEmpty()) {
			sssList = accountsServiceImpl.loadsssByDates(dateFrom, dateTo, supplierId, null, stId, gasId);
		} else {

			sssList = accountsServiceImpl.loadsssByDates(dateFrom, dateTo, supplierId, Integer.parseInt(supType), stId,
					gasId);
		}
		listTotalLitrsDecimal = new BigDecimal(0);
		listTotalSumDecimal = new BigDecimal(0);
		if (sssList != null && sssList.size() > 0) {
			listTotalSum = sssList.stream().filter(fdet -> fdet.getPrice() != 0.0d).mapToDouble(fdet -> fdet.getPrice())
					.sum();

			listTotalSumDecimal = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
			listTotalLitrs = sssList.stream().filter(fdet -> fdet.getQuantity() != 0.0d)
					.mapToDouble(fdet -> fdet.getQuantity()).sum();

			listTotalLitrsDecimal = new BigDecimal(listTotalLitrs).setScale(3, RoundingMode.HALF_UP);

			System.out.println("" + listTotalSumDecimal);

		}
		return "";
	}

	public String addGas() {
		try {
			if (sssAdd != null) {
				sssAdd.setSupplierType(Integer.parseInt(sssAdd.getSupType()));
				sssAdd.setSadad(0);
//				Integer.parseInt((sssAdd.getSadType().isEmpty() == true ? "1" : sssAdd.getSadType()))
				sssAdd.setStationId(stId);
				departmentServiceImpl.save(sssAdd);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				sssList = accountsServiceImpl.loadsssList(stId);
				listTotalLitrsDecimal = new BigDecimal(0);
				listTotalSumDecimal = new BigDecimal(0);
				if (sssList != null && sssList.size() > 0) {
					listTotalSum = sssList.stream().filter(fdet -> fdet.getPrice() != 0.0d)
							.mapToDouble(fdet -> fdet.getPrice()).sum();

					listTotalSumDecimal = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
					listTotalLitrs = sssList.stream().filter(fdet -> fdet.getQuantity() != 0.0d)
							.mapToDouble(fdet -> fdet.getQuantity()).sum();

					listTotalLitrsDecimal = new BigDecimal(listTotalLitrs).setScale(3, RoundingMode.HALF_UP);

					System.out.println("" + listTotalSumDecimal);

				}
				sssAdd = new GasStationSuppliers();
			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "";
	}

	public String saveSadad() {
		try {
			if (sssAdd != null) {
				sssAdd.setSadad(1);
				sssAdd.setStationId(stId);
				departmentServiceImpl.save(sssAdd);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				sssAdd = new GasStationSuppliers();
			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "";

	}

	public void loadSuppliersSearch(AjaxBehaviorEvent event) {
		if (supType != null) {
			// supplierId = null;
			if (supType.equalsIgnoreCase("1")) {
				// out load suppliers
				enableShow = false;
				supsList = departmentServiceImpl.loadSuppliers(stId);
			} else {
				// in load stations
				enableShow = true;
				stationsList = departmentServiceImpl.loadStations();
			}
		}

	}

	public void loadSuppliers(AjaxBehaviorEvent event) {
		if (sssAdd != null) {
			if (sssAdd.getSupType() != null) {

				if (sssAdd.getSupType().equalsIgnoreCase("1")) {
					// out load suppliers
					// enableShow = false;
					show = false;
					supsList = departmentServiceImpl.loadSuppliers(stId);
				} else {
					// in load stations
					show = true;
					// enableShow = true;
					stationsList = departmentServiceImpl.loadStations();
				}
			}
		}

	}

//
	public String deleteGas(GasStationSuppliers gs) {
		if (gs != null) {
			try {
				departmentServiceImpl.delete(gs);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.delete"));
				sssList = accountsServiceImpl.loadsssList(stId);
				listTotalLitrsDecimal = new BigDecimal(0);
				listTotalSumDecimal = new BigDecimal(0);
				if (sssList != null && sssList.size() > 0) {
					listTotalSum = sssList.stream().filter(fdet -> fdet.getPrice() != 0.0d)
							.mapToDouble(fdet -> fdet.getPrice()).sum();

					listTotalSumDecimal = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
					listTotalLitrs = sssList.stream().filter(fdet -> fdet.getQuantity() != 0.0d)
							.mapToDouble(fdet -> fdet.getQuantity()).sum();

					listTotalLitrsDecimal = new BigDecimal(listTotalLitrs).setScale(3, RoundingMode.HALF_UP);

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
			GasStationSuppliers gs = (GasStationSuppliers) event.getObject();
			departmentServiceImpl.update(gs);
			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.update"));
			sssList = accountsServiceImpl.loadsssList(stId);
			listTotalLitrsDecimal = new BigDecimal(0);
			listTotalSumDecimal = new BigDecimal(0);
			if (sssList != null && sssList.size() > 0) {
				listTotalSum = sssList.stream().filter(fdet -> fdet.getPrice() != 0.0d)
						.mapToDouble(fdet -> fdet.getPrice()).sum();

				listTotalSumDecimal = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
				listTotalLitrs = sssList.stream().filter(fdet -> fdet.getQuantity() != 0.0d)
						.mapToDouble(fdet -> fdet.getQuantity()).sum();

				listTotalLitrsDecimal = new BigDecimal(listTotalLitrs).setScale(3, RoundingMode.HALF_UP);

				System.out.println("" + listTotalSumDecimal);

			}
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

	public String printAccount() {
		if (supplierId != null && supType != null && !supType.isEmpty() && supType.equalsIgnoreCase("1") == true) {
			try {

				String reportName = "/reports/importsImportant.jasper";
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
				parameters.put("firstAmount", firstAmountValue);

				parameters.put("supId", supplierId);

				String headerPath = FacesContext.getCurrentInstance().getExternalContext()
						.getRealPath("/reports/logoreport.png");
				parameters.put("header", headerPath);
				Utils.printPdfReport(reportName, parameters);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public String printAll() {
		try {
			String reportName = "/reports/imports.jasper";
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
			if (supType == null || supType.isEmpty()) {
				parameters.put("supId", -1);
				parameters.put("stfromId", -1);
			} else {
				if (supType.equalsIgnoreCase("1")) {
					// stationfrom = -1 out suppliers
					parameters.put("supId", supplierId);
					parameters.put("stfromId", -1);
				} else {
					// supId = -1 in suppliers
					parameters.put("supId", -1);
					parameters.put("stfromId", supplierId);
				}
			}
			if (gasId != null && gasId > 0) {
				parameters.put("gasId", gasId);
			} else {
				parameters.put("gasId", -1);
			}
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

	public List<GasStationSuppliers> getSssList() {
		return sssList;
	}

	public void setSssList(List<GasStationSuppliers> sssList) {
		this.sssList = sssList;
	}

	public GasStationSuppliers getSssAdd() {
		return sssAdd;
	}

	public void setSssAdd(GasStationSuppliers sssAdd) {
		this.sssAdd = sssAdd;
	}

	public List<Stations> getStationsList() {
		return stationsList;
	}

	public void setStationsList(List<Stations> stationsList) {
		this.stationsList = stationsList;
	}

	public List<Suppliers> getSupsList() {
		return supsList;
	}

	public void setSupsList(List<Suppliers> supsList) {
		this.supsList = supsList;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public boolean isEnableShow() {
		return enableShow;
	}

	public void setEnableShow(boolean enableShow) {
		this.enableShow = enableShow;
	}

	public List<Gas> getGasList() {
		return gasList;
	}

	public void setGasList(List<Gas> gasList) {
		this.gasList = gasList;
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

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupType() {
		return supType;
	}

	public void setSupType(String supType) {
		this.supType = supType;
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

	public double getListTotalLitrs() {
		return listTotalLitrs;
	}

	public void setListTotalLitrs(double listTotalLitrs) {
		this.listTotalLitrs = listTotalLitrs;
	}

	public BigDecimal getListTotalLitrsDecimal() {
		return listTotalLitrsDecimal;
	}

	public void setListTotalLitrsDecimal(BigDecimal listTotalLitrsDecimal) {
		this.listTotalLitrsDecimal = listTotalLitrsDecimal;
	}

	public BigDecimal getFirstAmountValue() {
		return firstAmountValue;
	}

	public void setFirstAmountValue(BigDecimal firstAmountValue) {
		this.firstAmountValue = firstAmountValue;
	}

	public List<Suppliers> getSupsSadadList() {
		return supsSadadList;
	}

	public void setSupsSadadList(List<Suppliers> supsSadadList) {
		this.supsSadadList = supsSadadList;
	}

	public Integer getGasId() {
		return gasId;
	}

	public void setGasId(Integer gasId) {
		this.gasId = gasId;
	}

}
