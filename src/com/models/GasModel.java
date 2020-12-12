package com.models;

import java.math.BigDecimal;

public class GasModel {
	private Integer gasId;

	private Integer stationId;

	private BigDecimal firstAmount;
	
	private BigDecimal litrsAmount;

	private BigDecimal importsAmount;

	private BigDecimal lastAmount;

	private Integer gasTank;

	private String gasName;

	public Integer getGasId() {
		return gasId;
	}

	public void setGasId(Integer gasId) {
		this.gasId = gasId;
	}

	public BigDecimal getFirstAmount() {
		return firstAmount;
	}

	public void setFirstAmount(BigDecimal firstAmount) {
		this.firstAmount = firstAmount;
	}

	public BigDecimal getLitrsAmount() {
		return litrsAmount;
	}

	public void setLitrsAmount(BigDecimal litrsAmount) {
		this.litrsAmount = litrsAmount;
	}

	public BigDecimal getImportsAmount() {
		return importsAmount;
	}

	public void setImportsAmount(BigDecimal importsAmount) {
		this.importsAmount = importsAmount;
	}

	public BigDecimal getLastAmount() {
		return lastAmount;
	}

	public void setLastAmount(BigDecimal lastAmount) {
		this.lastAmount = lastAmount;
	}

	public String getGasName() {
		return gasName;
	}

	public void setGasName(String gasName) {
		this.gasName = gasName;
	}

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public Integer getGasTank() {
		return gasTank;
	}

	public void setGasTank(Integer gasTank) {
		this.gasTank = gasTank;
	}

}
