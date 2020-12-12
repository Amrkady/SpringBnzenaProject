package com.models;

import java.math.BigDecimal;

public class RevuensModel {

	private Integer stationId;

	private BigDecimal revAmount;

	private BigDecimal expAmount;
	
	private BigDecimal profit;

	private BigDecimal zakah;

	private BigDecimal taxs;

	private String stationName;

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public BigDecimal getRevAmount() {
		return revAmount;
	}

	public void setRevAmount(BigDecimal revAmount) {
		this.revAmount = revAmount;
	}

	public BigDecimal getExpAmount() {
		return expAmount;
	}

	public void setExpAmount(BigDecimal expAmount) {
		this.expAmount = expAmount;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public BigDecimal getZakah() {
		return zakah;
	}

	public void setZakah(BigDecimal zakah) {
		this.zakah = zakah;
	}

	public BigDecimal getTaxs() {
		return taxs;
	}

	public void setTaxs(BigDecimal taxs) {
		this.taxs = taxs;
	}

}
