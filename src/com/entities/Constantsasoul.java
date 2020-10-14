package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "constantsasoul")
public class Constantsasoul {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer Id;

	@Column(name = "name")
	private String name;

	@Column(name = "expensisId")
	private Integer expensisId;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getExpensisId() {
		return expensisId;
	}

	public void setExpensisId(Integer expensisId) {
		this.expensisId = expensisId;
	}


}
