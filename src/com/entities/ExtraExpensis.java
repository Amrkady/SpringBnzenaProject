package com.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "extra_expensis")
public class ExtraExpensis {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;

	@Column(name = "total_revenu")
	private double total_revenu;

	@Column(name = "station_id")
	private Integer stationId;

	@Column(name = "cash")
	private double cash;

	@Column(name = "visa")
	private double visa;

	@Column(name = "entry_date")
	private Date entryDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getTotal_revenu() {
		return total_revenu;
	}

	public void setTotal_revenu(double total_revenu) {
		this.total_revenu = total_revenu;
	}

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public double getVisa() {
		return visa;
	}

	public void setVisa(double visa) {
		this.visa = visa;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

}
