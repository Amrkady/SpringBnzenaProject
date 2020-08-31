package com.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "gas_station_suppliers")
public class GasStationSuppliers {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;

	@Column(name = "gas_id")
	private Integer gasId;

	@Column(name = "station_id")
	private Integer stationId;

	@Column(name = "supplier_id")
	private Integer supplierId;

	@Column(name = "quantity")
	private double quantity;

	@Column(name = "price")
	private double price;

	@Column(name = "total_value")
	private double totalValue;

	@Column(name = "six_price")
	private double sixPrice;

	@Column(name = "total_six_price")
	private double totalSixPrice;

	@Column(name = "trela_price")
	private double trelaPrice;

	@Column(name = "total_trela_price")
	private double totalTrelaPrice;

	@Column(name = "supplier_type")
	private Integer supplierType;

	@Column(name = "bill_no")
	private Integer billNo;

	@Column(name = "sadad")
	private Integer sadad;

	@Column(name = "from_station_id")
	private Integer fromStationId;

	@Column(name = "sup_date")
	private Date supDate;

	@Formula("(select g.name from gas g where g.id = gas_id)")
	private String gasName;

	@Formula("(select s.name from suppliers s where s.id = supplier_id)")
	private String supplierName;

	@Formula("(select s.station_name from stations s where s.id = from_station_id)")
	private String stationName;

	@Transient
	private String supType;

	@Transient
	private String sadType;

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

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getSupDate() {
		return supDate;
	}

	public void setSupDate(Date supDate) {
		this.supDate = supDate;
	}

	public double getSixPrice() {
		return sixPrice;
	}

	public void setSixPrice(double sixPrice) {
		this.sixPrice = sixPrice;
	}

	public double getTotalSixPrice() {
		return totalSixPrice;
	}

	public void setTotalSixPrice(double totalSixPrice) {
		this.totalSixPrice = totalSixPrice;
	}

	public double getTrelaPrice() {
		return trelaPrice;
	}

	public void setTrelaPrice(double trelaPrice) {
		this.trelaPrice = trelaPrice;
	}

	public double getTotalTrelaPrice() {
		return totalTrelaPrice;
	}

	public void setTotalTrelaPrice(double totalTrelaPrice) {
		this.totalTrelaPrice = totalTrelaPrice;
	}

	public Integer getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(Integer supplierType) {
		this.supplierType = supplierType;
	}

	public Integer getBillNo() {
		return billNo;
	}

	public void setBillNo(Integer billNo) {
		this.billNo = billNo;
	}

	public Integer getSadad() {
		return sadad;
	}

	public void setSadad(Integer sadad) {
		this.sadad = sadad;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

	public Integer getFromStationId() {
		return fromStationId;
	}

	public void setFromStationId(Integer fromStationId) {
		this.fromStationId = fromStationId;
	}

	public String getGasName() {
		return gasName;
	}

	public void setGasName(String gasName) {
		this.gasName = gasName;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupType() {
		return supType;
	}

	public void setSupType(String supType) {
		this.supType = supType;
	}

	public String getSadType() {
		return sadType;
	}

	public void setSadType(String sadType) {
		this.sadType = sadType;
	}

}
