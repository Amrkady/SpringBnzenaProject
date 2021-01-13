package com.models;

public class MouznaModel {

	private String stationName;

	private Integer stationId;

	private double revAmount;

	private double expAmount;

	private double profit;

	private double lostGas;

	private double profitProp;

	private double profitAfterLost;

	private double firstAmount;

	private double secondAmount;

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public double getRevAmount() {
		return revAmount;
	}

	public void setRevAmount(double revAmount) {
		this.revAmount = revAmount;
	}

	public double getExpAmount() {
		return expAmount;
	}

	public void setExpAmount(double expAmount) {
		this.expAmount = expAmount;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public double getLostGas() {
		return lostGas;
	}

	public void setLostGas(double lostGas) {
		this.lostGas = lostGas;
	}

	public double getProfitProp() {
		return profitProp;
	}

	public void setProfitProp(double profitProp) {
		this.profitProp = profitProp;
	}

	public double getProfitAfterLost() {
		return profitAfterLost;
	}

	public void setProfitAfterLost(double profitAfterLost) {
		this.profitAfterLost = profitAfterLost;
	}

	public double getFirstAmount() {
		return firstAmount;
	}

	public void setFirstAmount(double firstAmount) {
		this.firstAmount = firstAmount;
	}

	public double getSecondAmount() {
		return secondAmount;
	}

	public void setSecondAmount(double secondAmount) {
		this.secondAmount = secondAmount;
	}

}
