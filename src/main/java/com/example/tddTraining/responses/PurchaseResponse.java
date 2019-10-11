package com.example.tddTraining.responses;


import java.util.List;

import com.example.tddTraining.entities.Item;

public class PurchaseResponse {

	private String message;
	private List<Item> itemsWithNoStock;
	
	
	
	public PurchaseResponse() {
		super();
	}

	public PurchaseResponse(String message, List<Item> items) {
		this.message = message;
		this.itemsWithNoStock = items;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Item> getItemsWithNoStock() {
		return itemsWithNoStock;
	}

	public void setItemsWithNoStock(List<Item> itemsWithNoStock) {
		this.itemsWithNoStock = itemsWithNoStock;
	}

	@Override
	public String toString() {
		return "PurchaseResponse [message=" + message + ", itemsWithNoStock=" + itemsWithNoStock + "]";
	}
	
	

}
