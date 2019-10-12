package com.example.tddTraining.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ItemTest {

	Item item;

	@Test
	public void testItem() {
		item = new Item();
		assertThat(item.getName(), equalTo(null));
	}

	@Test
	public void testItemStringStringInt() {
		item = new Item("camisa", "M", 10);
		assertThat(item.getName(), equalTo("camisa"));
		assertThat(item.getSize(), equalTo("M"));
		assertThat(item.getStock(), equalTo(10));
	}

	@Test
	public void testItemLongStringStringInt() {
		item = new Item((long) 1,"camisa", "M", 10);
		assertThat(item.getId(), equalTo((long) 1));
		assertThat(item.getName(), equalTo("camisa"));
		assertThat(item.getSize(), equalTo("M"));
		assertThat(item.getStock(), equalTo(10));
	}

	@Test
	public void testSetId() {
		item = new Item((long) 1,"camisa", "M", 10);
		item.setId((long) 2);
		assertThat(item.getId(), equalTo((long) 2));
	}

	@Test
	public void testSetStock() {
		item = new Item((long) 1,"camisa", "M", 10);
		item.setStock(1);
		assertThat(item.getStock(), equalTo(1));
	}

	@Test
	public void testSetName() {
		item = new Item((long) 1,"camisa", "M", 10);
		item.setName("pantalon");
		assertThat(item.getName(), equalTo("pantalon"));
	}
		

	@Test
	public void testSetSize() {
		item = new Item((long) 1,"camisa", "M", 10);
		item.setSize("L");
		assertThat(item.getSize(), equalTo("L"));
	}

}
