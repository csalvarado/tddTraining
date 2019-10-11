package com.example.tddTraining.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Item {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	
	private String name;
	private String size;
	private Integer stock;
	
	
	
	public Item() {
	}

	public Item(String name, String talla, int stock) {
		this.name = name;
		this.size = talla;
		this.stock = stock;
	}

	public Item(Long id, String name, String talla, int stock) {
		this.Id = id;
		this.name = name;
		this.size = talla;
		this.stock = stock;
	}
	
	public Long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSize(String size) {
		this.size = size;
	}



	public String getName() {
		return this.name;
	}

	public String getSize() {
		return this.size;
	}
	
}
