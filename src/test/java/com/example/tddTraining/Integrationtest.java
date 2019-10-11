package com.example.tddTraining;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.tddTraining.entities.Item;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class Integrationtest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		
		
	}

	@Test
	public void testGetItem() {
		//arrange
		
		//act
		ResponseEntity<Item> response = restTemplate.getForEntity("/items/camisa", Item.class);
		
		//assert
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(response.getBody().getName(), equalTo("camisa"));
		assertThat(response.getBody().getSize(), equalTo("L"));
	}
	
	@Test
	public void testAddItem() {
		
	}
	
	@Test
	public void testDelete() {
		
	}

}
