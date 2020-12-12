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

import com.entities.GeneralPay;
import com.entities.Stations;
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

	@PostConstruct
	public void init() {

	}

	public String loadListByDates() {
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
				double dif = accountValue.doubleValue() - monValue.doubleValue();
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

}
