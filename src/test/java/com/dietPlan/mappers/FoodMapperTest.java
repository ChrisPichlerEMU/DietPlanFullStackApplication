package com.dietPlan.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dietPlan.domain.mapper.FoodMapper;
import com.dietPlan.domain.model.Day;
import com.dietPlan.domain.model.Food;
import com.dietPlan.web.dto.DayDto;
import com.dietPlan.web.dto.FoodDto;

public class FoodMapperTest {
	private final FoodMapper foodMapper = FoodMapper.INSTANCE;
	
	private Food food;
	private Food foodTwo;
	private Food foodNull;
	private FoodDto foodDto;
	private FoodDto foodDtoTwo;
	private FoodDto foodDtoNull;
	
	@BeforeEach
	public void setUp() {
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
		foodDto.setDeleted(false);
		
		
		food = new Food();
		food.setName("Apple");
		food.setCalories(50);
		food.setProtein(2);
		food.setCarbs(10);
		food.setSugar(10);
		food.setFat(0);
		food.setSaturatedFat(0);
		food.setSodium(0);
		food.setPotassium(250);
		food.setDeleted(true);
	}
	
	@Test
	void testToFoodDtoValid() {
		foodDtoTwo = foodMapper.toFoodDto(food);
		
		assertNotNull(foodDtoTwo);
		assertEquals(foodDtoTwo.getName(), food.getName());
		assertEquals(foodDtoTwo.getPotassium(), food.getPotassium());
		assertEquals(foodDtoTwo.isDeleted(), food.isDeleted());
	}
	
	@Test
	void testToFoodDtoInvalidNullFood() {
		foodDtoTwo = foodMapper.toFoodDto(foodNull);
		
		assertNull(foodDtoTwo);
	}
	
	@Test
	void testToFoodValid() {
		foodTwo = foodMapper.toFood(foodDto);
		
		assertNotNull(foodTwo);
		assertEquals(foodTwo.getCarbs(), foodDto.getCarbs());
		assertEquals(foodTwo.getSugar(), foodDto.getSugar());
		assertEquals(foodTwo.getSodium(), foodDto.getSodium());
	}
	
	@Test
	void testToFoodInvalidNullFoodDto() {
		foodTwo = foodMapper.toFood(foodDtoNull);
		
		assertNull(foodTwo);
	}
	
	@Test
	void testUpdatedFoodRowFromDtoValid() {
		food.setId(2L);
		
		foodMapper.updateFoodRowFromDto(foodDto, food);
		
		assertNotNull(food);
		assertEquals(2L, food.getId());
		assertEquals("Protein bar", food.getName());
		assertEquals(13, food.getFat());
	}
	
	@Test
	void testUpdatedFoodRowFromDtoInvalidNullFoodDto() {
		food.setId(2L);
		
		foodMapper.updateFoodRowFromDto(foodDtoNull, food);
		
		assertNotNull(food);
		assertEquals(2L, food.getId());
		assertEquals("Apple", food.getName());
		assertEquals(50, food.getCalories());
	}
}
