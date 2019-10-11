package com.example.tddTraining.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.tddTraining.entities.Item;
import com.example.tddTraining.exceptions.ItemNotFoundException;
import com.example.tddTraining.repositories.ItemRepository;


@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {
	
	@Mock
	private ItemRepository itemRepository;
	
	private ItemService itemService;

	@Before
	public void setUp() throws Exception {
		itemService = new ItemService(itemRepository);
	}

	@After
	public void tearDown() throws Exception {
		itemService = null;
	}

	@Test
	public void testGetItemDetail() {
		given(this.itemRepository.findByName(anyString())).willReturn(new Item("camisa", "L", 50));
		
		Item item = itemService.getItemDetails("camisa");
		assertThat(item.getName(), equalTo("camisa"));
		assertThat(item.getSize(), equalTo("L"));
		assertThat(item.getStock(), equalTo(50));
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void testItemDetailsNotFound() {
		given(this.itemRepository.findByName(anyString())).willThrow(new ItemNotFoundException());
		itemService.getItemDetails("camisa");
	}
	
	@Test 
	public void testGetAlls() {
		List<Item> items = new ArrayList<Item>();
		Item item1 = new Item((long) 1, "camisa", "L", 50);
		Item item2 = new Item((long) 2, "zapatos", "42", 20);
		items.add(item1);
		items.add(item2);
		given(this.itemRepository.findAll()).willReturn(items);
		
		List<Item> res = itemService.getAllItems();
		assertThat(res.get(0).getName(), equalTo("camisa"));
		assertThat(res.get(0).getSize(), equalTo("L"));
		assertThat(res.get(0).getStock(), equalTo(50));
		assertThat(res.get(1).getName(), equalTo("zapatos"));
		assertThat(res.get(1).getSize(), equalTo("42"));
		assertThat(res.get(1).getStock(), equalTo(20));
		
		
		List<Item> items2 = new ArrayList<Item>();
		given(this.itemRepository.findAll()).willReturn(items2);
		List<Item> res2 = itemService.getAllItems(); 
		assertThat(res2.size(), equalTo(0));
	}

	@Test 
	public void testHasItemsInStock() {
		//list that will enter the function
		List<Item> cartItems = new ArrayList<Item>();
		Item item1 = new Item((long) 1, "camisa", "L", 50);
		Item item2 = new Item((long) 2, "zapatos", "42", 20);
		Item item3 = new Item((long) 3, "pantalon", "46", 25);
		cartItems.add(item1);
		cartItems.add(item2);
		cartItems.add(item3);
		//
		Optional<Item> optional1 = Optional.of(item1);
		Item i = new Item();
		i.setId(1);
		given(this.itemRepository.findById(anyLong())).willReturn(optional1);
		assertThat(itemService.hasItemsInStock(cartItems), equalTo(true));
	}
	
	@Test 
	public void testHasntItemsInStock() {
		//list that will enter the function
		List<Item> cartItems = new ArrayList<Item>();
		Item item1 = new Item((long) 1, "camisa", "L", 50);
		Item item2 = new Item((long) 2, "zapatos", "42", 20);
		Item item3 = new Item((long) 3, "pantalon", "46", 25);
		cartItems.add(item1);
		cartItems.add(item2);
		cartItems.add(item3);
		item1.setStock(0);
		//
		Optional<Item> optional1 = Optional.of(item1);
		given(this.itemRepository.findById(anyLong())).willReturn(optional1);
		assertThat(itemService.hasItemsInStock(cartItems), equalTo(false));
	}
	
	@Test 
	public void testGetItemsWithNoStock() {
		//list that will enter the function
				List<Item> cartItems = new ArrayList<Item>();
				Item item1 = new Item((long) 1, "camisa", "L", 50);
				Item item2 = new Item((long) 2, "zapatos", "42", 20);
				Item item3 = new Item((long) 3, "pantalon", "46", 25);
				cartItems.add(item1);
				cartItems.add(item2);
				cartItems.add(item3);
				//
				Optional<Item> optional1 = Optional.of(item1);
				given(this.itemRepository.findById(anyLong())).willReturn(optional1);
				assertThat(itemService.getItemsWithNoStock(cartItems).size(), equalTo(0));
	}
	
	@Test 
	public void testGetItemsWithNoStock2() {
		//list that will enter the function
				List<Item> cartItems = new ArrayList<Item>();
				Item item1 = new Item((long) 1, "camisa", "L", 50);
				Item item2 = new Item((long) 2, "zapatos", "42", 20);
				Item item3 = new Item((long) 3, "pantalon", "46", 25);
				cartItems.add(item1);
				cartItems.add(item2);
				cartItems.add(item3);
				Item optional = new Item((long) 1, "camisa", "L", 0);
				//
				Optional<Item> optional1 = Optional.of(optional);
				given(this.itemRepository.findById(anyLong())).willReturn(optional1);
				assertThat(itemService.getItemsWithNoStock(cartItems).size(), equalTo(3));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testUpdateStockOk() {
		
		//list that will enter the function
		List<Item> cartItems = new ArrayList<Item>();
		Item item1 = new Item((long) 1, "camisa", "L", 50);
		Item item2 = new Item((long) 2, "zapatos", "42", 20);
		Item item3 = new Item((long) 3, "pantalon", "46", 25);
		cartItems.add(item1);
		cartItems.add(item2);
		cartItems.add(item3);
		Item i = new Item((long) 1, "camisa", "L", 0);
		given(this.itemRepository.save(anyObject())).willReturn(i);
		assertThat(itemService.updateStock(cartItems), equalTo(true));
	}
	@SuppressWarnings("deprecation")
	@Test
	public void testUpdateStockKO() {
		
		//list that will enter the function
		List<Item> cartItems = new ArrayList<Item>();
		Item item1 = new Item((long) 1, "camisa", "L", 50);
		Item item2 = new Item((long) 2, "zapatos", "42", 20);
		Item item3 = new Item((long) 3, "pantalon", "46", 25);
		cartItems.add(item1);
		cartItems.add(item2);
		cartItems.add(item3);
		Item i = null;
		given(this.itemRepository.save(anyObject())).willReturn(i);
		assertThat(itemService.updateStock(cartItems), equalTo(false));
	}

}
