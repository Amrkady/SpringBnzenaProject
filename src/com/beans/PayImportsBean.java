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

import org.primefaces.event.RowEditEvent;

import com.entities.Expensis;
import com.entities.GeneralPay;
import com.entities.GunsRevenus;
import com.entities.Stations;
import com.models.MouznaModel;
import com.services.AccountsService;
import com.services.DepartmentService;

import common.util.MsgEntry;
import common.util.Utils;

@ManagedBean(name = "payImportsBean")
@ViewScoped

public class PayImportsBean {
	@ManagedProperty(value = "#{accountsServiceImpl}")
	private AccountsService accountsServiceImpl;
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private Integer stId;
	private Date dateFrom;
	private Date dateTo;
	List<GeneralPay> gmList = new ArrayList<GeneralPay>();
	List<Stations> stationsList = new ArrayList<Stations>();
	private List<Expensis> expensisList = new ArrayList<Expensis>();
	private List<GunsRevenus> gunsRevenuList = new ArrayList<GunsRevenus>();
	private List<MouznaModel> mouznaModelList = new ArrayList<MouznaModel>();
	private double first95;
	private double first91;
	private double firstD;
	private double last95;
	private double last91;
	private double lastD;
	private double eqamaPrice;

	@PostConstruct
	public void init() {

	}

	public String loadListByDates() {
		stationsList = departmentServiceImpl.loadStations();
		gmList = new ArrayList<GeneralPay>();
		if (dateFrom != null && dateTo != null) {
			gmList = accountsServiceImpl.loadAllGeneralPayByDates(dateFrom, dateTo);
			if (gmList == null || gmList.size() == 0) {
				stationsList = departmentServiceImpl.loadStations();

				for (Stations stat : stationsList) {
					List<GeneralPay> list = new ArrayList<GeneralPay>();
					list = accountsServiceImpl.loadAllGeneralPayBetweenDates(stat.getId(), dateFrom, dateTo);
					if (list != null && list.size() > 0) {
						gmList.addAll(list);
					}
				}

			}
		}
		return "";
	}

	public String save() {
		try {
			for (GeneralPay generalPay : gmList) {
				if (generalPay.getId() == null) {
					departmentServiceImpl.save(generalPay);
				} else {
					departmentServiceImpl.update(generalPay);
				}
			}
			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "";
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("·„ Ì „ «· ⁄œÌ·", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public void calcFineValSum(GeneralPay generalPay) {
		if (generalPay != null) {
			BigDecimal monValue = generalPay.getMonitorsAmount();
			BigDecimal accountValue = generalPay.getDeffiernceAmount();
			if (monValue != null && accountValue != null) {
				double dif = monValue.doubleValue() - accountValue.doubleValue();
				generalPay.setDeficitExcess(new BigDecimal(dif).setScale(2, RoundingMode.HALF_UP));
//				try {
//					departmentServiceImpl.update(generalPay);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}

			}
		}
	}

	public String printAll() {
		try {
			String reportName = "/reports/revenuesAndPays.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			String headerPath = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/reports/logoreport.png");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String fromDate = sdf.format(dateFrom);
			String toDate = sdf.format(dateTo);
			parameters.put("dateFrom", fromDate);
			parameters.put("dateTo", toDate);
			parameters.put("header", headerPath);
			Utils.printPdfReportFromListDataSource(reportName, parameters, gmList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public void loadData() {
		MouznaModel mm = new MouznaModel();
		mouznaModelList = new ArrayList<MouznaModel>();
		for (Stations stat : stationsList) {
			expensisList = accountsServiceImpl.loadExpensisByDates(dateFrom, dateTo, null, stat.getId());
			gunsRevenuList = accountsServiceImpl.loadRevsByDates(dateFrom, dateTo, null, stat.getId());
			mm = new MouznaModel();
			mm.setStationName(stat.getName());
			mm.setStationId(stat.getId());
			mm.setRevAmount(gunsRevenuList.stream().filter(fdet -> fdet.getTotalPrice() != 0.0d)
					.mapToDouble(fdet -> fdet.getTotalPrice()).sum());
			mm.setExpAmount(expensisList.stream().filter(fdet -> fdet.getExpensisQuantity() != 0.0d)
					.mapToDouble(fdet -> fdet.getExpensisQuantity()).sum());
			mm.setProfit(mm.getRevAmount() - mm.getExpAmount());
			mm.setLostGas(gmList.stream().filter(fdet -> fdet.getStationId() == stat.getId())
					.mapToDouble(fdet -> fdet.getDeficitExcess().doubleValue()).sum());

			double oil95 = gmList.stream()
					.filter(fdet -> fdet.getDeficitExcess() != null && fdet.getGasName() != null
							&& fdet.getStationId() == stat.getId() && "95".equalsIgnoreCase(fdet.getGasName().trim()))
					.mapToDouble(fdet -> fdet.getDeficitExcess().doubleValue()).sum();
			double oil91 = gmList.stream()
					.filter(fdet -> fdet.getDeficitExcess() != null && fdet.getGasName() != null
							&& fdet.getStationId() == stat.getId()
							&& ("91".equalsIgnoreCase(fdet.getGasName().trim())
									|| Utils.loadMessagesFromFile("5zan1").equalsIgnoreCase(fdet.getGasName().trim())
									|| Utils.loadMessagesFromFile("khazan2").equalsIgnoreCase(fdet.getGasName().trim())
									|| Utils.loadMessagesFromFile("khazan3").equalsIgnoreCase(fdet.getGasName().trim()))

					).mapToDouble(fdet -> fdet.getDeficitExcess().doubleValue()).sum();
			double dezl = gmList.stream()
					.filter(fdet -> fdet.getDeficitExcess() != null && fdet.getGasName() != null
							&& fdet.getStationId() == stat.getId()
							&& Utils.loadMessagesFromFile("dezl").equalsIgnoreCase(fdet.getGasName().trim()))
					.mapToDouble(fdet -> fdet.getDeficitExcess().doubleValue()).sum();

			mm.setLostGas(oil95 * first95 + oil91 * first91 + dezl * firstD); // «·⁄Ã“ ›Ï «·”⁄—
			double oil951 = gmList.stream()
					.filter(fdet -> fdet.getFirstAmount() != null && fdet.getGasName() != null
							&& fdet.getStationId() == stat.getId() && "95".equalsIgnoreCase(fdet.getGasName().trim()))
					.mapToDouble(fdet -> fdet.getFirstAmount().doubleValue()).sum();
			double oil911 = gmList.stream()
					.filter(fdet -> fdet.getFirstAmount() != null && fdet.getGasName() != null
							&& fdet.getStationId() == stat.getId()
							&& ("91".equalsIgnoreCase(fdet.getGasName().trim())
									|| Utils.loadMessagesFromFile("5zan1").equalsIgnoreCase(fdet.getGasName().trim())
									|| Utils.loadMessagesFromFile("khazan2").equalsIgnoreCase(fdet.getGasName().trim())
									|| Utils.loadMessagesFromFile("khazan3").equalsIgnoreCase(fdet.getGasName().trim()))

					).mapToDouble(fdet -> fdet.getFirstAmount().doubleValue()).sum();
			double dezl1 = gmList.stream()
					.filter(fdet -> fdet.getFirstAmount() != null && fdet.getGasName() != null
							&& fdet.getStationId() == stat.getId()
							&& Utils.loadMessagesFromFile("dezl").equalsIgnoreCase(fdet.getGasName().trim()))
					.mapToDouble(fdet -> fdet.getFirstAmount().doubleValue()).sum();

			mm.setFirstAmount(oil951 * first95 + oil911 * first91 + dezl1 * firstD); // —’Ìœ »œ«Ì… «·› —… ›Ï «·”⁄—

			double oil95end = gmList.stream()
					.filter(fdet -> fdet.getDeffiernceAmount() != null && fdet.getGasName() != null
							&& fdet.getStationId() == stat.getId() && "95".equalsIgnoreCase(fdet.getGasName().trim()))
					.mapToDouble(fdet -> fdet.getDeffiernceAmount().doubleValue()).sum();
			double oil91end = gmList.stream().filter(fdet -> fdet.getDeffiernceAmount() != null
					&& fdet.getGasName() != null && fdet.getStationId() == stat.getId()
					&& ("91".equalsIgnoreCase(fdet.getGasName().trim())
							|| Utils.loadMessagesFromFile("5zan1").equalsIgnoreCase(fdet.getGasName().trim())
							|| Utils.loadMessagesFromFile("khazan2").equalsIgnoreCase(fdet.getGasName().trim())
							|| Utils.loadMessagesFromFile("khazan3").equalsIgnoreCase(fdet.getGasName().trim())))
					.mapToDouble(fdet -> fdet.getDeffiernceAmount().doubleValue()).sum();
			double dezlend = gmList.stream()
					.filter(fdet -> fdet.getDeffiernceAmount() != null && fdet.getGasName() != null
							&& fdet.getStationId() == stat.getId()
							&& Utils.loadMessagesFromFile("dezl").equalsIgnoreCase(fdet.getGasName().trim()))
					.mapToDouble(fdet -> fdet.getDeffiernceAmount().doubleValue()).sum();
			mm.setSecondAmount(oil95end * last95 + oil91end * last91 + dezlend * lastD); // —’Ìœ ‰Â«Ì… «·› —… ›Ï «·”⁄—
			mm.setProfitProp(
					(mm.getRevAmount() + mm.getSecondAmount() - mm.getFirstAmount() - mm.getExpAmount()));

			mm.setProfitAfterLost(mm.getProfitProp() + mm.getLostGas());

			mouznaModelList.add(mm);

		}

	}

	public String printMouzna() {
		try {
			loadData();
			String reportName = "/reports/mouznaReport.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			String headerPath = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/reports/logoreport.png");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String fromDate = sdf.format(dateFrom);
			String toDate = sdf.format(dateTo);
			parameters.put("dateFrom", fromDate);
			parameters.put("dateTo", toDate);
			parameters.put("eqamaPrice", eqamaPrice);
			parameters.put("header", headerPath);
			Utils.printPdfReportFromListDataSource(reportName, parameters, mouznaModelList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public AccountsService getAccountsServiceImpl() {
		return accountsServiceImpl;
	}

	public void setAccountsServiceImpl(AccountsService accountsServiceImpl) {
		this.accountsServiceImpl = accountsServiceImpl;
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

	public List<GeneralPay> getGmList() {
		return gmList;
	}

	public void setGmList(List<GeneralPay> gmList) {
		this.gmList = gmList;
	}

	public List<Stations> getStationsList() {
		return stationsList;
	}

	public void setStationsList(List<Stations> stationsList) {
		this.stationsList = stationsList;
	}

	public double getFirst95() {
		return first95;
	}

	public void setFirst95(double first95) {
		this.first95 = first95;
	}

	public double getFirst91() {
		return first91;
	}

	public void setFirst91(double first91) {
		this.first91 = first91;
	}

	public double getFirstD() {
		return firstD;
	}

	public void setFirstD(double firstD) {
		this.firstD = firstD;
	}

	public double getLast95() {
		return last95;
	}

	public void setLast95(double last95) {
		this.last95 = last95;
	}

	public double getLast91() {
		return last91;
	}

	public void setLast91(double last91) {
		this.last91 = last91;
	}

	public double getLastD() {
		return lastD;
	}

	public void setLastD(double lastD) {
		this.lastD = lastD;
	}

	public List<Expensis> getExpensisList() {
		return expensisList;
	}

	public void setExpensisList(List<Expensis> expensisList) {
		this.expensisList = expensisList;
	}

	public List<GunsRevenus> getGunsRevenuList() {
		return gunsRevenuList;
	}

	public void setGunsRevenuList(List<GunsRevenus> gunsRevenuList) {
		this.gunsRevenuList = gunsRevenuList;
	}

	public double getEqamaPrice() {
		return eqamaPrice;
	}

	public void setEqamaPrice(double eqamaPrice) {
		this.eqamaPrice = eqamaPrice;
	}

	public List<MouznaModel> getMouznaModelList() {
		return mouznaModelList;
	}

	public void setMouznaModelList(List<MouznaModel> mouznaModelList) {
		this.mouznaModelList = mouznaModelList;
	}

}
