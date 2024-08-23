package com.dietPlan.web.integrationTests;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.dietPlan.domain.repository.DayRepository;
import com.dietPlan.domain.repository.FoodRepository;
import com.dietPlan.web.controller.DayController;
import com.dietPlan.web.controller.FoodController;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DayIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private DayRepository dayRepository;
	
	@Autowired
	private DayController dayController;
	
	@BeforeEach
	public void setUp() {
		
	}

}

//addDay, getDay, deleteDay, addFoodsToDay, deleteFoodsInday
