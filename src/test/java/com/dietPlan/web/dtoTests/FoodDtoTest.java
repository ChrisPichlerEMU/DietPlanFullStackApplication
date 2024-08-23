package com.dietPlan.web.dtoTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dietPlan.web.dto.FoodDto;

public class FoodDtoTest {
	private FoodDto foodDto;
	
	@BeforeEach
	public void setUp() {
		foodDto = new FoodDto();
		foodDto.setId(4L);
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
		foodDto.setDeleted(false);
	}
	
	@Test
	public void testGetId() {
		assertEquals(4L, foodDto.getId());
	}
	
	@Test
	public void testSetId() {
		foodDto.setId(3L);
		
		assertEquals(3L, foodDto.getId());
	}
	
	@Test
	public void testGetName() {
		assertEquals("Protein bar", foodDto.getName());
	}
	
	@Test
	public void testSetName() {
		foodDto.setName("Apple");
		
		assertEquals("Apple", foodDto.getName());
	}
	
	@Test
	public void testGetCalories() {
		assertEquals(400, foodDto.getCalories());
	}
	
	@Test
	public void testSetCalories() {
		foodDto.setCalories(500);
		
		assertEquals(500, foodDto.getCalories());
	}
	
	@Test
	public void testGetProtein() {
		assertEquals(30, foodDto.getProtein());
	}
	
	@Test
	public void testSetProtein() {
		foodDto.setProtein(50);
		
		assertEquals(50, foodDto.getProtein());
	}
	
	@Test
	public void testGetCarbs() {
		assertEquals(40, foodDto.getCarbs());
	}
	
	@Test
	public void testSetCarbs() {
		foodDto.setCarbs(50);
		
		assertEquals(50, foodDto.getCarbs());
	}
	
	@Test
	public void testGetSugar() {
		assertEquals(20, foodDto.getSugar());
	}
	
	@Test
	public void testSetSugar() {
		foodDto.setSugar(10);
		
		assertEquals(10, foodDto.getSugar());
	}
	
	@Test
	public void testGetFat() {
		assertEquals(13, foodDto.getFat());
	}
	
	@Test
	public void testSetFat() {
		foodDto.setFat(10);
		
		assertEquals(10, foodDto.getFat());
	}
	
	@Test
	public void testGetSaturatedFat() {
		assertEquals(8, foodDto.getSaturatedFat());
	}
	
	@Test
	public void testSetSaturatedFat() {
		foodDto.setSaturatedFat(5);
		
		assertEquals(5, foodDto.getSaturatedFat());
	}
	
	@Test
	public void testGetSodium() {
		assertEquals(200, foodDto.getSodium());
	}
	
	@Test
	public void testSetSodium() {
		foodDto.setSodium(100);
		
		assertEquals(100, foodDto.getSodium());
	}
	
	@Test
	public void testGetPotassium() {
		assertEquals(150, foodDto.getPotassium());
	}
	
	@Test
	public void testSetPotassium() {
		foodDto.setPotassium(200);
		
		assertEquals(200, foodDto.getPotassium());
	}
	
	@Test
	public void testGetMultiplier() {
		assertEquals(1.0, foodDto.getMultiplier());
	}
	
	@Test
	public void testSetMultiplier() {
		foodDto.setMultiplier(0.5);
		
		assertEquals(0.5, foodDto.getMultiplier());
	}
	
	@Test
	public void testIsDeleted() {
		assertFalse(foodDto.isDeleted());
	}
	
	@Test
	public void testSetDeleted() {
		foodDto.setDeleted(true);
		
		assertTrue(foodDto.isDeleted());
	}
}