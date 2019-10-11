package com.example.tddTraining.entities;

import java.util.List;

public class ShoppingCart {

	private User user;
	private List<Item> cartItems;
	
	
	
	public ShoppingCart() {
		super();
	}

	public ShoppingCart(User user, List<Item> cartItems) {
		this.user = user;
		this.cartItems = cartItems;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Item> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<Item> cartItems) {
		this.cartItems = cartItems;
	}

	@Override
	public String toString() {
		return "ShoppingCart [user=" + user + ", cartItems=" + cartItems + "]";
	}
	
}
