package com.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "first_day_amount")
public class FirstDayAmount {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;

	@Column(name = "gas_id")
	private Integer gasId;

	@Column(name = "station_id")
	private Integer stationId;

	@Column(name = "amount")
	private double amount;

	@Column(name = "read_Date")
	private Date readDate;

	@Formula("(select s.station_name from stations s where s.id = station_id)")
	private String stationName;

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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}


}
