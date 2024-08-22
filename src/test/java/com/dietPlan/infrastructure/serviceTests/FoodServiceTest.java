package com.dietPlan.infrastructure.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dietPlan.domain.mapper.FoodMapper;
import com.dietPlan.domain.model.Food;
import com.dietPlan.domain.repository.FoodRepository;
import com.dietPlan.infrastructure.service.FoodService;
import com.dietPlan.web.dto.FoodDto;

public class FoodServiceTest {
	
	@Mock
	private FoodRepository foodRepository;
	
	@InjectMocks
	private FoodService foodService;

	private FoodDto foodDto;
	private Food food;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
		foodDto = new FoodDto();
		foodDto.setName("Protein bar");
		foodDto.setCalories(400);
		foodDto.setProtein(30);
		foodDto.setCarbs(40);
		foodDto.setSugar(20);
		foodDto.setFat(13);
		foodDto.setSaturatedFat(8);
		foodDto.setSodium(200);
		foodDto.setPotassium(150);
		foodDto.setMultiplier(1.0);
		
		food = FoodMapper.INSTANCE.toFood(foodDto);
	}
	
	@Test
	void testAddFoodValid() {
		when(foodRepository.save(any(Food.class))).thenReturn(food);
		
		FoodDto result = foodService.addFood(foodDto);
		
		assertNotNull(result);
		assertEquals("Protein bar", result.getName());
		assertEquals(40, result.getCarbs());
	}
	
	@Test
	void testAddFoodInvalidNullFoodDto() {
		FoodDto result = foodService.addFood(null);
		
		assertNull(result);
	}
	
	@Test
	void testGetFoodStatsValid() {
		when(foodRepository.findById(1L)).thenReturn(Optional.of(food));
		
		FoodDto result = foodService.getFoodStats(1L);
		
		assertNotNull(result);
		assertEquals(13, result.getFat());
	}
	
	@Test
	void testGetFoodStatsInvalidNotInDatabase() {
		when(foodRepository.findById(1L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> foodService.getFoodStats(1L), "getFoodStats() should've thrown an exception, but it didn't");
	
		assertTrue(thrownException.getMessage().equals("Food object not found in database in getFoodStats method with id = " + 1L));
	}
	
	@Test
	void testEditFoodValid() {
		when(foodRepository.findById(1L)).thenReturn(Optional.of(food));
		when(foodRepository.save(any(Food.class))).thenReturn(food);
		
		FoodDto result = foodService.editFood(1L, foodDto);
		
		assertNotNull(result);
		assertEquals(150, result.getPotassium());
	}
	
	@Test
	void testEditFoodNotInDatabase() {
		when(foodRepository.findById(1L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> foodService.editFood(1L, foodDto), "editFood() should've thrown an exception, but it didn't");
	
		assertTrue(thrownException.getMessage().equals("Food object not found in database in editFood method with id = " + 1L));
	}
	
	@Test
	void testDeleteFoodValid() {
		when(foodRepository.findById(1L)).thenReturn(Optional.of(food));
		
		FoodDto result = foodService.deleteFood(1L);
		
		assertNotNull(result);
		assertTrue(result.isDeleted());
	}
	
	@Test
	void testDeleteFoodInvalidNotInDatabase() {
		when(foodRepository.findById(1L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> foodService.deleteFood(1L), "deleteFood() should've thrown an exception, but it didn't");
	
		assertTrue(thrownException.getMessage().equals("Food object not found in database in deleteFood method with id = " + 1L));
	}
}
