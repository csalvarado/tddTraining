package com.example.tddTraining.controllers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.tddTraining.entities.Item;
import com.example.tddTraining.exceptions.ItemNotFoundException;
import com.example.tddTraining.services.ItemService;
import static org.mockito.BDDMockito.*;


@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
public class ItemControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ItemService itemService;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetItem_ShouldReturnItem() throws Exception {
		given(this.itemService.getItemDetails(anyString())).willReturn(new Item("camisa", "L", 50));
		
		this.mockMvc.perform(get("/items/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value("camisa"))
				.andExpect(jsonPath("size").value("L"));
	}
	
	@Test
	public void testGetItemNotFound() throws Exception {
		given(this.itemService.getItemDetails(anyString())).willThrow(new ItemNotFoundException());
		this.mockMvc.perform(get("/items/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetAll() throws Exception {
		List<Item> items = new ArrayList<Item>();
		Item item1 = new Item((long) 1, "camisa", "L", 50);
		Item item2 = new Item((long) 2, "zapatos", "42", 20);
		items.add(item1);
		items.add(item2);
		given(this.itemService.getAllItems()).willReturn(items);
		this.mockMvc.perform(get("/items/getAll").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id").value(item1.getId()))
        .andExpect(jsonPath("$[0].name").value(item1.getName()))
        .andExpect(jsonPath("$[0].size").value(item1.getSize()))
        .andExpect(jsonPath("$[0].stock").value(item1.getStock()))
        .andExpect(jsonPath("$[1].id").value(item2.getId()))
        .andExpect(jsonPath("$[1].name").value(item2.getName()))
        .andExpect(jsonPath("$[1].size").value(item2.getSize()))
        .andExpect(jsonPath("$[1].stock").value(item2.getStock()));
		
		List<Item> items2 = new ArrayList<Item>();
		given(this.itemService.getAllItems()).willReturn(items2);
		this.mockMvc.perform(get("/items/getAll").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isArray())
		.andExpect(jsonPath("$").isEmpty());
	}

	
}
