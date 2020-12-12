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

import com.entities.FirstDayAmount;
import com.entities.Gas;
import com.entities.GasGuns;
import com.entities.GunsRevenus;
import com.entities.Taxs;
import com.models.GasModel;
import com.services.AccountsService;
import com.services.DepartmentService;

import common.util.MsgEntry;
import common.util.Utils;

@ManagedBean(name = "revenuesBean")
@ViewScoped
public class RevenuesBean {
	@ManagedProperty(value = "#{accountsServiceImpl}")
	private AccountsService accountsServiceImpl;
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private GunsRevenus sssAdd = new GunsRevenus();
	private Integer stId;
	private List<GasGuns> gunsList = new ArrayList<GasGuns>();
	private List<Gas> gasList = new ArrayList<Gas>();
	private List<GunsRevenus> gunsRevenuList = new ArrayList<GunsRevenus>();
	private List<GunsRevenus> gunsRevenus = new ArrayList<GunsRevenus>();
	private Date dateFrom;
	private Date dateTo;
	private Integer gasId;
	private double listTotalSum;
	private double litersTotalSum;
	private BigDecimal listTotalSumDecimal;
	private BigDecimal litersTotalSumDecimal;
	private List<GasModel> gmList = new ArrayList<GasModel>();
	private List<Taxs> taxsList = new ArrayList<Taxs>();
	private FirstDayAmount addObj = new FirstDayAmount();
	private List<FirstDayAmount> readList = new ArrayList<FirstDayAmount>();

	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		stId = (Integer) session.getAttribute("stationId");
		if (stId != null) {
			gunsRevenuList = accountsServiceImpl.loadGunsRevenusList(stId);
			// gunsList = departmentServiceImpl.loadGuns(stId);
			readList = accountsServiceImpl.loadAllfRead(stId);
			gasList = departmentServiceImpl.loadGass(stId);
			taxsList = departmentServiceImpl.loadTaxs();
			if (gunsRevenuList != null && gunsRevenuList.size() > 0) {
				listTotalSum = gunsRevenuList.stream().filter(fdet -> fdet.getTotalPrice() != 0.0d)
						.mapToDouble(fdet -> fdet.getTotalPrice()).sum();

				litersTotalSum = gunsRevenuList.stream().filter(fdet -> fdet.getLitersNum() != 0.0d)
						.mapToDouble(fdet -> fdet.getLitersNum()).sum();

				BigDecimal sum = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
				listTotalSumDecimal = sum;
				sum = new BigDecimal(litersTotalSum).setScale(3, RoundingMode.HALF_UP);
				litersTotalSumDecimal = sum;
				System.out.println("" + listTotalSumDecimal + ">>>>" + litersTotalSumDecimal);

			}
		}
	}

	public String loadListByDates() {
		gunsRevenuList = new ArrayList<GunsRevenus>();
		gunsRevenuList = accountsServiceImpl.loadRevsByDates(dateFrom, dateTo, gasId, stId);
		if (gunsRevenuList != null && gunsRevenuList.size() > 0) {
			listTotalSum = gunsRevenuList.stream().filter(fdet -> fdet.getTotalPrice() != 0.0d)
					.mapToDouble(fdet -> fdet.getTotalPrice()).sum();

			litersTotalSum = gunsRevenuList.stream().filter(fdet -> fdet.getLitersNum() != 0.0d)
					.mapToDouble(fdet -> fdet.getLitersNum()).sum();

			BigDecimal sum = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
			listTotalSumDecimal = sum;
			sum = new BigDecimal(litersTotalSum).setScale(3, RoundingMode.HALF_UP);
			litersTotalSumDecimal = sum;
			System.out.println("" + listTotalSumDecimal + ">>>>" + litersTotalSumDecimal);

		}
		if (stId != null && dateFrom != null && dateTo != null) {
			gmList = accountsServiceImpl.getAllLitersBetweenDates(stId, dateFrom, dateTo);
		}
		return "";
	}

	public String addGas() {
		try {
			if (sssAdd != null) {
				sssAdd.setStationId(stId);
				sssAdd.setLitersNum(sssAdd.getLastRead() - sssAdd.getFirstRead());
				sssAdd.setTotalPrice(sssAdd.getLiterPrice() * sssAdd.getLitersNum());
				sssAdd.setTotalPrice(Math.round(sssAdd.getTotalPrice() * 100) / 100.00d);
				sssAdd.setLitersNum(Math.round(sssAdd.getLitersNum() * 100) / 100.00d);
				departmentServiceImpl.save(sssAdd);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				gunsRevenuList = accountsServiceImpl.loadGunsRevenusList(stId);
				if (gunsRevenuList != null && gunsRevenuList.size() > 0) {
					listTotalSum = gunsRevenuList.stream().filter(fdet -> fdet.getTotalPrice() != 0.0d)
							.mapToDouble(fdet -> fdet.getTotalPrice()).sum();

					litersTotalSum = gunsRevenuList.stream().filter(fdet -> fdet.getLitersNum() != 0.0d)
							.mapToDouble(fdet -> fdet.getLitersNum()).sum();

					BigDecimal sum = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
					listTotalSum = sum.doubleValue();
					sum = new BigDecimal(litersTotalSum).setScale(3, RoundingMode.HALF_UP);
					litersTotalSum = sum.doubleValue();

				}
				sssAdd = new GunsRevenus();
			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "";
	}

	public String addFirst() {
		try {
			if (addObj != null) {
				addObj.setStationId(stId);
				departmentServiceImpl.save(addObj);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				readList = accountsServiceImpl.loadAllfRead(stId);
				addObj = new FirstDayAmount();
			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "";
	}

	public void loadGuns(AjaxBehaviorEvent event) {
		if (sssAdd != null) {
			if (sssAdd.getGasId() != null) {
				Gas gs = (Gas) departmentServiceImpl.findEntityById(Gas.class, sssAdd.getGasId());
				sssAdd.setLiterPrice(gs.getPrice());
				// load guns by stationId and gas id
				gunsList = accountsServiceImpl.loadGunsByStationIdAndGasId(stId, sssAdd.getGasId());
			}
		}

	}

	public void loadFirstRead(AjaxBehaviorEvent event) {
		if (sssAdd != null) {
			if (sssAdd.getGunId() != null) {
				// load first read by stationId and gas id and gun id
				Integer id = accountsServiceImpl.loadGunFirstRead(stId, sssAdd.getGunId(), sssAdd.getGasId());
				if (id != null && id > 0.0) {
					GunsRevenus gs = (GunsRevenus) departmentServiceImpl.findEntityById(GunsRevenus.class, id);
					sssAdd.setFirstRead(gs.getLastRead());
				} else {
					sssAdd.setFirstRead(0);
				}
			}
		}

	}

	public String deletefread(FirstDayAmount gs) {
		if (gs != null) {
			try {
				departmentServiceImpl.delete(gs);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.delete"));
				readList = accountsServiceImpl.loadAllfRead(stId);
			} catch (Exception e) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.delete"));
				e.printStackTrace();
			}
		}
		return "";
	}

//
	public String deleteGas(GunsRevenus gs) {
		if (gs != null) {
			try {
				departmentServiceImpl.delete(gs);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.delete"));
				gunsRevenuList = accountsServiceImpl.loadGunsRevenusList(stId);
				if (gunsRevenuList != null && gunsRevenuList.size() > 0) {
					listTotalSum = gunsRevenuList.stream().filter(fdet -> fdet.getTotalPrice() != 0.0d)
							.mapToDouble(fdet -> fdet.getTotalPrice()).sum();

					litersTotalSum = gunsRevenuList.stream().filter(fdet -> fdet.getLitersNum() != 0.0d)
							.mapToDouble(fdet -> fdet.getLitersNum()).sum();

					BigDecimal sum = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
					listTotalSum = sum.doubleValue();
					sum = new BigDecimal(litersTotalSum).setScale(3, RoundingMode.HALF_UP);
					litersTotalSum = sum.doubleValue();

					Utils.updateUIComponent("form:myTable");

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
			GunsRevenus gs = (GunsRevenus) event.getObject();
			gs.setLitersNum(gs.getLastRead() - gs.getFirstRead());
			gs.setTotalPrice(gs.getLiterPrice() * gs.getLitersNum());
			gs.setTotalPrice(Math.round(gs.getTotalPrice() * 100) / 100.00d);
			gs.setLitersNum(Math.round(gs.getLitersNum() * 100) / 100.00d);
			departmentServiceImpl.update(gs);
			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.update"));
			gunsRevenuList = accountsServiceImpl.loadGunsRevenusList(stId);
			if (gunsRevenuList != null && gunsRevenuList.size() > 0) {
				listTotalSum = gunsRevenuList.stream().filter(fdet -> fdet.getTotalPrice() != 0.0d)
						.mapToDouble(fdet -> fdet.getTotalPrice()).sum();

				litersTotalSum = gunsRevenuList.stream().filter(fdet -> fdet.getLitersNum() != 0.0d)
						.mapToDouble(fdet -> fdet.getLitersNum()).sum();

				BigDecimal sum = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
				listTotalSum = sum.doubleValue();
				sum = new BigDecimal(litersTotalSum).setScale(3, RoundingMode.HALF_UP);
				litersTotalSum = sum.doubleValue();
				Utils.updateUIComponent("form:myTable");

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

	public String printAll() {
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

	public List<Taxs> getTaxsList() {
		return taxsList;
	}

	public void setTaxsList(List<Taxs> taxsList) {
		this.taxsList = taxsList;
	}

	public FirstDayAmount getAddObj() {
		return addObj;
	}

	public void setAddObj(FirstDayAmount addObj) {
		this.addObj = addObj;
	}

	public double getListTotalSum() {
		return listTotalSum;
	}

	public void setListTotalSum(double listTotalSum) {
		this.listTotalSum = listTotalSum;
	}

	public double getLitersTotalSum() {
		return litersTotalSum;
	}

	public void setLitersTotalSum(double litersTotalSum) {
		this.litersTotalSum = litersTotalSum;
	}

	public BigDecimal getListTotalSumDecimal() {
		return listTotalSumDecimal;
	}

	public void setListTotalSumDecimal(BigDecimal listTotalSumDecimal) {
		this.listTotalSumDecimal = listTotalSumDecimal;
	}

	public BigDecimal getLitersTotalSumDecimal() {
		return litersTotalSumDecimal;
	}

	public void setLitersTotalSumDecimal(BigDecimal litersTotalSumDecimal) {
		this.litersTotalSumDecimal = litersTotalSumDecimal;
	}

	public List<GunsRevenus> getGunsRevenus() {
		return gunsRevenus;
	}

	public void setGunsRevenus(List<GunsRevenus> gunsRevenus) {
		this.gunsRevenus = gunsRevenus;
	}

	public List<GasModel> getGmList() {
		return gmList;
	}

	public void setGmList(List<GasModel> gmList) {
		this.gmList = gmList;
	}

	public List<FirstDayAmount> getReadList() {
		return readList;
	}

	public void setReadList(List<FirstDayAmount> readList) {
		this.readList = readList;
	}

}
