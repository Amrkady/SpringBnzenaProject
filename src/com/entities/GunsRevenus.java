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
@Table(name = "guns_revenus")
public class GunsRevenus {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;

	@Column(name = "gun_id")
	private Integer gunId;

	@Column(name = "station_id")
	private Integer stationId;

	@Column(name = "first_read")
	private double firstRead;

	@Column(name = "last_read")
	private double lastRead;

	@Column(name = "liter_price")
	private double literPrice;

	@Column(name = "total_price")
	private double totalPrice;

	@Column(name = "rev_date")
	private Date revDate;

	@Column(name = "gas_id")
	private Integer gasId;

	@Formula("(select g.name from gas g where g.id = gas_id)")
	private String gasName;

	@Formula("(select g.number from gas_guns g where g.id = gun_id)")
	private Integer gunNum;

	@Column(name = "liters_num")
	private double litersNum;

	@Formula("(select s.station_name from stations s where s.id = station_id)")
	private String stationName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGunId() {
		return gunId;
	}

	public void setGunId(Integer gunId) {
		this.gunId = gunId;
	}

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public double getFirstRead() {
		return firstRead;
	}

	public void setFirstRead(double firstRead) {
		this.firstRead = firstRead;
	}

	public double getLastRead() {
		return lastRead;
	}

	public void setLastRead(double lastRead) {
		this.lastRead = lastRead;
	}

	public double getLiterPrice() {
		return literPrice;
	}

	public void setLiterPrice(double literPrice) {
		this.literPrice = literPrice;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getRevDate() {
		return revDate;
	}

	public void setRevDate(Date revDate) {
		this.revDate = revDate;
	}

	public Integer getGasId() {
		return gasId;
	}

	public void setGasId(Integer gasId) {
		this.gasId = gasId;
	}

	public String getGasName() {
		return gasName;
	}

	public void setGasName(String gasName) {
		this.gasName = gasName;
	}

	public Integer getGunNum() {
		return gunNum;
	}

	public void setGunNum(Integer gunNum) {
		this.gunNum = gunNum;
	}

	public double getLitersNum() {
		return litersNum;
	}

	public void setLitersNum(double litersNum) {
		this.litersNum = litersNum;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

}
