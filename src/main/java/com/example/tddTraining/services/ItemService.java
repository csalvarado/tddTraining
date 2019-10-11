package com.example.tddTraining.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tddTraining.entities.Item;
import com.example.tddTraining.exceptions.ItemNotFoundException;
import com.example.tddTraining.repositories.ItemRepository;


@Service
public class ItemService {
	
	private ItemRepository itemRepository;

	@Autowired
	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	public Item getItemDetails(String name) {
		Item item = itemRepository.findByName(name);
		if (item == null) {
			throw new ItemNotFoundException();
		}
		else {
			return item;
		}
	}

	public List<Item> getAllItems() {
		return (List<Item>) itemRepository.findAll();
	}

	public boolean hasItemsInStock(List<Item> cartItems) {
		//calculate the quantity of items that doesn's have stock
		if (cartItems.stream()
					 .filter(i -> (itemRepository.findById(i.getId()).get().getStock() - i.getStock()) < 0)
					 .count() == 0) {
			return true;
		}
		return false;
	}

	public List<Item> getItemsWithNoStock(List<Item> cartItems) {
		//return items that doesn's have stock
		return cartItems.stream()
						.filter(i -> (itemRepository.findById(i.getId()).get().getStock() - i.getStock()) < 0)
						.collect(Collectors.toList());
	}

	public boolean updateStock(List<Item> cartItems) {
		return cartItems.stream()
				.filter(i -> (itemRepository.save(i) == null))
				.count() == 0;
	}
}
