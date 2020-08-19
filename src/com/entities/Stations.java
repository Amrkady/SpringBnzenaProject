package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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

}
