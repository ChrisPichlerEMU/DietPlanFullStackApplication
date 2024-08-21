package com.dietPlan.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dietPlan.domain.model.Week;

public interface WeekRepository extends JpaRepository<Week, Long>{
	//If isDeleted boolean variable = true, don't return when findById is called
	@Override
	@Query("SELECT w FROM Week w WHERE w.id = :id AND w.isDeleted = false")
	Optional<Week> findById(@Param("id") Long id);
}
