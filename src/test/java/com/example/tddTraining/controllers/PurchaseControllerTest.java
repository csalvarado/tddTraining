package com.example.tddTraining.controllers;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.*;



import com.example.tddTraining.entities.Item;
import com.example.tddTraining.entities.ShoppingCart;
import com.example.tddTraining.entities.User;
import com.example.tddTraining.responses.PurchaseResponse;
import com.example.tddTraining.services.ItemService;
import com.example.tddTraining.services.PaymentService;


@RunWith(SpringRunner.class)
@WebMvcTest(PurchaseController.class)
public class PurchaseControllerTest {

	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ItemService itemService;
	
	@MockBean
	private PaymentService paymentService;
	
	private ShoppingCart shoppingCart;
	
	private User user;
	private List<Item> cartItems;
	private Item item1; 
	private Item item2; 
	private Item item3;
	
	@Before
	public void setUp() throws Exception {
		//initialize fake enter data
		user = new User("csalvarado", "123456", "valid");
		cartItems = new ArrayList<Item>();
		item1 = new Item((long) 1, "camisa", "L", 50);
		item2 = new Item((long) 2, "zapatos", "42", 20);
		item3 = new Item((long) 3, "pantalon", "46", 25);
		cartItems.add(item1);
		cartItems.add(item2);
		cartItems.add(item3);
		shoppingCart = new ShoppingCart(user, cartItems);
	}

	@After
	public void tearDown() throws Exception {
		//clean enter data
		user = null;
		cartItems = null;
		item1 = null;
		item2 = null;
		item3 = null;
		shoppingCart = null;
	}

	@Test
	public void testCorrectPurchase() throws Exception {
		PurchaseResponse response = new PurchaseResponse("PaymentSuccessful", new ArrayList<Item>());
		given(this.itemService.hasItemsInStock(anyList())).willReturn(true);
		given(this.paymentService.makePayment(anyString())).willReturn(response);
		given(this.itemService.updateStock(anyList())).willReturn(true);
		this.mockMvc.perform( post("/purchase")
			      .content(asJsonString(shoppingCart))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("message").value("PaymentSuccessful"))
			      .andExpect(jsonPath("itemsWithNoStock").isEmpty());
	}
	
	@Test
	public void testPurchaseWitHNoStock() throws Exception {
		PurchaseResponse response = new PurchaseResponse("PaymentSuccessful", new ArrayList<Item>());
		List<Item> resItems = new ArrayList<Item>();
		Item item10 = new Item((long) 1, "camisa", "L", 10);
		Item item20 = new Item((long) 2, "zapatos", "42", 1);
		Item item30 = new Item((long) 3, "pantalon", "46", 0);
		resItems.add(item10);
		resItems.add(item20);
		resItems.add(item30);
		given(this.itemService.hasItemsInStock(anyList())).willReturn(false);
		given(this.itemService.getItemsWithNoStock(anyList())).willReturn(resItems);
		given(this.paymentService.makePayment(anyString())).willReturn(response);
		
		this.mockMvc.perform( post("/purchase")
			      .content(asJsonString(shoppingCart))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("message").value("Those items are out of stock, we show the stock we got in warehouse"))
			      .andExpect(jsonPath("itemsWithNoStock[0].stock").value(10))
			      .andExpect(jsonPath("itemsWithNoStock[1].stock").value(1))
			      .andExpect(jsonPath("itemsWithNoStock[2].stock").value(0));
	}
	
	@Test
	public void testUseerFailedCreditCard() throws Exception {
		PurchaseResponse response = new PurchaseResponse("User failed credit check", new ArrayList<Item>());
		given(this.itemService.hasItemsInStock(anyList())).willReturn(true);
		given(this.paymentService.makePayment(anyString())).willReturn(response);
		this.mockMvc.perform( post("/purchase")
			      .content(asJsonString(shoppingCart))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("message").value("User failed credit check"))
			      .andExpect(jsonPath("itemsWithNoStock").isEmpty());
	}
	
	@Test
	public void testCorrectPurchaseWithUpdateItemStockAndRollback() throws Exception {
		PurchaseResponse response = new PurchaseResponse("PaymentSuccessful", new ArrayList<Item>());
		given(this.itemService.hasItemsInStock(anyList())).willReturn(true);
		given(this.itemService.updateStock(anyList())).willReturn(false);
		given(this.paymentService.makePayment(anyString())).willReturn(response);
		
		this.mockMvc.perform( post("/purchase")
			      .content(asJsonString(shoppingCart))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("message").value("The service roollback the process, please try again"))
			      .andExpect(jsonPath("itemsWithNoStock").isEmpty());
	}
	
	@Test
	public void testCorrectPurchaseWithUpdateItemStockAndNotRollback() throws Exception {
		PurchaseResponse response = new PurchaseResponse("PaymentSuccessful", new ArrayList<Item>());
		given(this.itemService.hasItemsInStock(anyList())).willReturn(true);
		given(this.itemService.updateStock(anyList())).willReturn(true);
		given(this.paymentService.makePayment(anyString())).willReturn(response);
		
		this.mockMvc.perform( post("/purchase")
			      .content(asJsonString(shoppingCart))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("message").value("PaymentSuccessful"))
			      .andExpect(jsonPath("itemsWithNoStock").isEmpty());
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
