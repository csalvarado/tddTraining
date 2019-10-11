package com.example.tddTraining;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class TddTrainingApplication implements CommandLineRunner {

//	@Autowired
//	ItemRepository temporalDataBase;
	
	public static void main(String[] args) {
		SpringApplication.run(TddTrainingApplication.class, args);
		System.out.print("Hello TDD");
	}
	
	@Override
	public void run(String... args) throws Exception {
//		Item item1 = new Item("camisa", "L", 50);
//		temporalDataBase.save(item1);
	}
	
}
