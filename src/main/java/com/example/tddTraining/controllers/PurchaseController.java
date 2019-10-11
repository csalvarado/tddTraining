package com.example.tddTraining.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.example.tddTraining.entities.ShoppingCart;
import com.example.tddTraining.responses.PurchaseResponse;
import com.example.tddTraining.services.ItemService;
import com.example.tddTraining.services.PaymentService;



@RestController
public class PurchaseController {

	
	private ItemService itemService;
	
	private PaymentService paymentService;
	
	
	@Autowired
	public PurchaseController(ItemService itemService, PaymentService paymentService) {
		this.itemService = itemService;
		this.paymentService = paymentService;
	}



	@RequestMapping(path = "/purchase", produces = "application/json", method = RequestMethod.POST)
	private PurchaseResponse getItem(@RequestBody ShoppingCart shoppingCart) {
		PurchaseResponse response = new PurchaseResponse();
		if (itemService.hasItemsInStock(shoppingCart.getCartItems())) {
			response = paymentService.makePayment(shoppingCart.getUser().getCreditCard());
			if (response.getMessage().equals("PaymentSuccessful")) {
				boolean done = itemService.updateStock(shoppingCart.getCartItems());
				if (!done) {
					response.setMessage("The service roollback the process, please try again");
				}
			}
		}
		else {
			response.setMessage("Those items are out of stock, we show the stock we got in warehouse");
			response.setItemsWithNoStock(itemService.getItemsWithNoStock(shoppingCart.getCartItems()));
 		}
		return response;
	}
}
