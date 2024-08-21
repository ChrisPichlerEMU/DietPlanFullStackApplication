package com.dietPlan.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dietPlan.models.Food;

public interface FoodRepository extends JpaRepository <Food, Long>{
	//If isDeleted boolean variable = true, don't return when findById is called
	@Override
	@Query("SELECT f FROM Food f WHERE f.id = :id AND f.isDeleted = false")
	Optional<Food> findById(@Param("id") Long id);
}
