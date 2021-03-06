package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "gas")
public class Gas {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "station_id")
	private Integer stationId;

	@Column(name = "price")
	private double price;

	@Column(name = "first_read")
	private double firstRead;

	@Column(name = "tank_width")
	private Integer tankWidth;

	@Formula("(select s.station_name from stations s where s.id = station_id)")
	private String stationName;

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

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public double getFirstRead() {
		return firstRead;
	}

	public void setFirstRead(double firstRead) {
		this.firstRead = firstRead;
	}

	public Integer getTankWidth() {
		return tankWidth;
	}

	public void setTankWidth(Integer tankWidth) {
		this.tankWidth = tankWidth;
	}

}
