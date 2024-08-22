package com.dietPlan.infrastructure.orchestratorTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dietPlan.domain.mapper.DayMapper;
import com.dietPlan.domain.model.Day;
import com.dietPlan.domain.model.Food;
import com.dietPlan.domain.repository.DayRepository;
import com.dietPlan.domain.repository.FoodRepository;
import com.dietPlan.infrastructure.orchestrator.DayOrchestrator;
import com.dietPlan.infrastructure.service.CalculateStats;
import com.dietPlan.web.dto.DayDto;
import com.dietPlan.web.dto.FoodDto;

public class DayOrchestratorTest {
	
	@Mock
	private FoodRepository foodRepository;

	@Mock
	private DayRepository dayRepository;
	
	@Mock
	private CalculateStats calculateStats;
	
	@InjectMocks
	private DayOrchestrator dayOrchestrator;
	
	private Day day;
	private Food food;
	private Food foodTwo;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
		food = new Food();
		food.setName("Protein bar");
		food.setCalories(400);
		food.setProtein(30);
		food.setCarbs(40);
		food.setSugar(20);
		food.setFat(13);
		food.setSaturatedFat(8);
		food.setSodium(200);
		food.setPotassium(150);
		food.setMultiplier(1.0);
		
		foodTwo = new Food();
		foodTwo.setName("Chicken breast");
		foodTwo.setCalories(500);
		foodTwo.setProtein(100);
		foodTwo.setCarbs(0);
		foodTwo.setSugar(0);
		foodTwo.setFat(9);
		foodTwo.setSaturatedFat(0);
		foodTwo.setSodium(0);
		foodTwo.setPotassium(550);
		foodTwo.setMultiplier(1.0);
		
		day = new Day();
		day.setFoods(new ArrayList<>(List.of(food, food)));
		day.setTotalCalories(500);
		day.setTotalProtein(50);
		day.setTotalCarbs(50);
		day.setTotalSugar(25);
		day.setTotalFat(9);
		day.setTotalSaturatedFat(5);
		day.setTotalSodium(500);
		day.setTotalPotassium(600);
		day.setCarbRatio(40);
		day.setProteinRatio(40);
		day.setFatRatio(20);
	}
	
	@Test
	void testGetDayStatsValid() {
		when(dayRepository.findById(1L)).thenReturn(Optional.of(day));
		when(dayRepository.save(any(Day.class))).thenReturn(day);
		
		DayDto result = dayOrchestrator.getDayStats(1L);
		
		assertNotNull(result);
		assertEquals(600, result.getTotalPotassium());
	}
	
	@Test
	void testGetDayStatsInvalidNotInDatabase() {
		when(dayRepository.findById(1L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> dayOrchestrator.getDayStats(1L), "getDayStats() should've thrown an exception, but it didn't");
	
		assertTrue(thrownException.getMessage().equals("Day object not found in database in getDayStats method with id = " + 1L));
	}
	
	@Test
	void testAddFoodsToDayValid() {
		when(dayRepository.findById(1L)).thenReturn(Optional.of(day));
		when(foodRepository.findById(2L)).thenReturn(Optional.of(foodTwo));
		when(dayRepository.save(any(Day.class))).thenReturn(day);
		
		DayDto result = dayOrchestrator.addFoodsToDay(1L, List.of(2L));
		
		assertNotNull(result);
		assertEquals(3, result.getFoods().size());
		assertEquals(30, result.getFoods().get(0).getProtein());
		assertEquals(9, result.getFoods().get(2).getFat());
	}
	
	@Test
	void testAddFoodsToDayInvalidDayNotInDatabase() {
		when(dayRepository.findById(1L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> dayOrchestrator.addFoodsToDay(1L, List.of(2L, 3L)), "addFoodsToDay() should've thrown an exception, but it didn't");
		
		assertTrue(thrownException.getMessage().equals("Day object not found in database in addFoodsToDay method with id = " + 1L));
	}
	
	@Test
	void testAddFoodsToDayInvalidFoodNotInDatabase() {
		when(dayRepository.findById(1L)).thenReturn(Optional.of(day));
		when(foodRepository.findById(2L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> dayOrchestrator.addFoodsToDay(1L, List.of(2L)), "addFoodsToDay() should've thrown an exception, but it didn't");

		assertTrue(thrownException.getMessage().equals("Food object not found in database in addFoodsToDay method with id = " + 2L));
	}
	
	@Test
	void testDeleteFoodsInDayValid() {
		day.setFoods(new ArrayList<>(List.of(food, food, foodTwo)));
		
		when(dayRepository.findById(1L)).thenReturn(Optional.of(day));
		when(foodRepository.findById(2L)).thenReturn(Optional.of(food));
		when(dayRepository.save(any(Day.class))).thenReturn(day);
		
		DayDto result = dayOrchestrator.deleteFoodsInDay(1L, List.of(2L));
		
		assertNotNull(result);
		assertNotNull(result.getFoods());
		assertEquals(2, result.getFoods().size());
	}
	
	@Test
	void testDeleteFoodsInDayInvalidDayNotInDatabase() {
		when(dayRepository.findById(1L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> dayOrchestrator.deleteFoodsInDay(1L, List.of(2L, 3L)), "deleteFoodsInDay() should've thrown an exception, but it didn't");
	
		assertTrue(thrownException.getMessage().equals("Day object not found in database in deleteFoodsInDay method with id = " + 1L));
	}
	
	@Test
	void testDeleteFoodsInDayInvalidFoodNotInDatabase() {
		when(dayRepository.findById(1L)).thenReturn(Optional.of(day));
		when(foodRepository.findById(2L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> dayOrchestrator.deleteFoodsInDay(1L, List.of(2L)), "addFoodsToDay() should've thrown an exception, but it didn't");

		assertTrue(thrownException.getMessage().equals("Food object not found in database in deleteFoodsInDay method with id = " + 2L));
	}
}
