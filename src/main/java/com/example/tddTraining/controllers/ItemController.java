package com.example.tddTraining.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.tddTraining.entities.Item;
import com.example.tddTraining.exceptions.ItemNotFoundException;
import com.example.tddTraining.services.ItemService;

@RestController
public class ItemController {

	
	private ItemService itemService;

	@Autowired
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping("/items/{name}")
	private Item getItem(@PathVariable String name) {
		return itemService.getItemDetails(name);
	}
	
	@GetMapping("/items/getAll")
	private List<Item> getAll() {
		return itemService.getAllItems();
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private void itemNotFoundHandler(ItemNotFoundException ex) {
		
	}
	
}
