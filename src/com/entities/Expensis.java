package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "expensis")
public class Expensis {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;

	@Column(name = "salaries")
	private Integer salaries;

	@Column(name = "gas_id")
	private Integer gasId;

	@Column(name = "expensis_quantity")
	private Integer expensisQuantity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSalaries() {
		return salaries;
	}

	public void setSalaries(Integer salaries) {
		this.salaries = salaries;
	}

	public Integer getGasId() {
		return gasId;
	}

	public void setGasId(Integer gasId) {
		this.gasId = gasId;
	}

	public Integer getExpensisQuantity() {
		return expensisQuantity;
	}

	public void setExpensisQuantity(Integer expensisQuantity) {
		this.expensisQuantity = expensisQuantity;
	}


}
