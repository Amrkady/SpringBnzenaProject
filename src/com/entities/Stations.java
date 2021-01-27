package com.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "stations")
public class Stations {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;

	@Column(name = "station_name")
	private String name;

	@Column(name = "station_number")
	private Integer stationNumber;

	@Column(name = "manager")
	private String manager;

	@Column(name = "loction")
	private String loction;

	@Column(name = "num_guns")
	private Integer numGuns;

	@Column(name = "num_shops")
	private Integer numShops;

	@Column(name = "license_num")
	private Integer licenseNum;

	@Column(name = "license_owner")
	private String licenseOwner;

	@Column(name = "commercial_num")
	private Integer commercialNum;

	@Column(name = "license_expired_date")
	private Date licenseExpiredDate;

	@Column(name = "rent_start_date")
	private Date rentStartDate;

	@Column(name = "rent_end_date")
	private Date rentEndDate;

	@Transient
	private double eqamaCost;

	public double getEqamaCost() {
		return eqamaCost;
	}

	public void setEqamaCost(double eqamaCost) {
		this.eqamaCost = eqamaCost;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(Integer stationNumber) {
		this.stationNumber = stationNumber;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getLoction() {
		return loction;
	}

	public void setLoction(String loction) {
		this.loction = loction;
	}

	public Integer getNumGuns() {
		return numGuns;
	}

	public void setNumGuns(Integer numGuns) {
		this.numGuns = numGuns;
	}

	public Integer getNumShops() {
		return numShops;
	}

	public void setNumShops(Integer numShops) {
		this.numShops = numShops;
	}

	public Integer getLicenseNum() {
		return licenseNum;
	}

	public void setLicenseNum(Integer licenseNum) {
		this.licenseNum = licenseNum;
	}

	public String getLicenseOwner() {
		return licenseOwner;
	}

	public void setLicenseOwner(String licenseOwner) {
		this.licenseOwner = licenseOwner;
	}

	public Integer getCommercialNum() {
		return commercialNum;
	}

	public void setCommercialNum(Integer commercialNum) {
		this.commercialNum = commercialNum;
	}

	public Date getLicenseExpiredDate() {
		return licenseExpiredDate;
	}

	public void setLicenseExpiredDate(Date licenseExpiredDate) {
		this.licenseExpiredDate = licenseExpiredDate;
	}

	public Date getRentStartDate() {
		return rentStartDate;
	}

	public void setRentStartDate(Date rentStartDate) {
		this.rentStartDate = rentStartDate;
	}

	public Date getRentEndDate() {
		return rentEndDate;
	}

	public void setRentEndDate(Date rentEndDate) {
		this.rentEndDate = rentEndDate;
	}

}
