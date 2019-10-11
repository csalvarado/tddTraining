package com.example.tddTraining.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.tddTraining.entities.Item;


@Repository
public interface ItemRepository extends CrudRepository<Item, Long>{

	Item findByName(String name);
}
