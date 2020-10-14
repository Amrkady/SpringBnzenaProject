package com.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author Alkady
 *
 */
@Entity
@Table(name = "general_pay")
public class GeneralPay {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;

	@Column(name = "gas_id")
	private Integer gasId;

	@Column(name = "station_id")
	private Integer stationId;


	@Column(name = "firstAmount")
	private BigDecimal firstAmount;
	
	@Column(name = "litrPayAmount")
	private BigDecimal litrPayAmount;

	@Column(name = "importsAmount")
	private BigDecimal importsAmount;

	@Column(name = "deffiernceAmount")
	private BigDecimal deffiernceAmount;

	@Column(name = "monitorsAmount")
	private BigDecimal monitorsAmount;

	@Column(name = "deficitExcess")
	private BigDecimal deficitExcess;

	@Column(name = "dateInsert")
	private Date dateInsert;

	@Formula("(select s.station_name from stations s where s.id = station_id)")
	private String stationName;

	@Formula("(select g.name from gas g where g.id = gas_id)")
	private String gasName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGasId() {
		return gasId;
	}

	public void setGasId(Integer gasId) {
		this.gasId = gasId;
	}

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public BigDecimal getFirstAmount() {
		return firstAmount;
	}

	public void setFirstAmount(BigDecimal firstAmount) {
		this.firstAmount = firstAmount;
	}

	public BigDecimal getLitrPayAmount() {
		return litrPayAmount;
	}

	public void setLitrPayAmount(BigDecimal litrPayAmount) {
		this.litrPayAmount = litrPayAmount;
	}

	public BigDecimal getImportsAmount() {
		return importsAmount;
	}

	public void setImportsAmount(BigDecimal importsAmount) {
		this.importsAmount = importsAmount;
	}

	public BigDecimal getDeffiernceAmount() {
		return deffiernceAmount;
	}

	public void setDeffiernceAmount(BigDecimal deffiernceAmount) {
		this.deffiernceAmount = deffiernceAmount;
	}

	public BigDecimal getMonitorsAmount() {
		return monitorsAmount;
	}

	public void setMonitorsAmount(BigDecimal monitorsAmount) {
		this.monitorsAmount = monitorsAmount;
	}

	public Date getDateInsert() {
		return dateInsert;
	}

	public void setDateInsert(Date dateInsert) {
		this.dateInsert = dateInsert;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getGasName() {
		return gasName;
	}

	public void setGasName(String gasName) {
		this.gasName = gasName;
	}

	public BigDecimal getDeficitExcess() {
		return deficitExcess;
	}

	public void setDeficitExcess(BigDecimal deficitExcess) {
		this.deficitExcess = deficitExcess;
	}

}
