package com.dietPlan.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dietPlan.domain.model.Day;

public interface DayRepository extends JpaRepository<Day, Long>{
	//If isDeleted boolean variable = true, don't return when findById is called
	@Override
	@Query("SELECT d FROM Day d WHERE d.id = :id AND d.isDeleted = false")
	Optional<Day> findById(@Param("id") Long id);
}
