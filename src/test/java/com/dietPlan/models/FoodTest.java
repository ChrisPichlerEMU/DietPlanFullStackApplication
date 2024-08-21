package com.dietPlan.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dietPlan.domain.model.Food;

public class FoodTest {
	private Food food;
	
	@BeforeEach
	public void setUp() {
		food = new Food();
		food.setId(2L);
		food.setName("Chocolate ice cream");
		food.setCalories(300);
		food.setProtein(20);
		food.setCarbs(30);
		food.setSugar(25);
		food.setFat(9);
		food.setSaturatedFat(6);
		food.setSodium(400);
		food.setPotassium(300);
		food.setMultiplier(1.0);
		food.setDeleted(false);
	}
	
	@Test
	public void testGetId() {
		assertEquals(2L, food.getId());
	}
	
	@Test
	public void testSetId() {
		food.setId(1L);
		
		assertEquals(1L, food.getId());
	}
	
	@Test
	public void testGetName() {
		assertEquals("Chocolate ice cream", food.getName());
	}
	
	@Test
	public void testSetName() {
		food.setName("Pear");
		
		assertEquals("Pear", food.getName());
	}
	
	@Test
	public void testGetCalories() {
		assertEquals(300, food.getCalories());
	}
	
	@Test
	public void testSetCalories() {
		food.setCalories(350);
		
		assertEquals(350, food.getCalories());
	}
	
	@Test
	public void testGetProtein() {
		assertEquals(20, food.getProtein());
	}
	
	@Test
	public void testSetProtein() {
		food.setProtein(10);
		
		assertEquals(10, food.getProtein());
	}
	
	@Test
	public void testGetCarbs() {
		assertEquals(30, food.getCarbs());
	}
	
	@Test
	public void testSetCarbs() {
		food.setCarbs(60);
		
		assertEquals(60, food.getCarbs());
	}
	
	@Test
	public void testGetSugar() {
		assertEquals(25, food.getSugar());
	}
	
	@Test
	public void testSetSugar() {
		food.setSugar(20);
		
		assertEquals(20, food.getSugar());
	}
	
	@Test
	public void testGetFat() {
		assertEquals(9, food.getFat());
	}
	
	@Test
	public void testSetFat() {
		food.setFat(15);
		
		assertEquals(15, food.getFat());
	}
	
	@Test
	public void testGetSaturatedFat() {
		assertEquals(6, food.getSaturatedFat());
	}
	
	@Test
	public void testSetSaturatedFat() {
		food.setSaturatedFat(2);
		
		assertEquals(2, food.getSaturatedFat());
	}
	
	@Test
	public void testGetSodium() {
		assertEquals(400, food.getSodium());
	}
	
	@Test
	public void testSetSodium() {
		food.setSodium(300);
		
		assertEquals(300, food.getSodium());
	}
	
	@Test
	public void testGetPotassium() {
		assertEquals(300, food.getPotassium());
	}
	
	@Test
	public void testSetPotassium() {
		food.setPotassium(250);
		
		assertEquals(250, food.getPotassium());
	}
	
	@Test
	public void testGetMultiplier() {
		assertEquals(1.0, food.getMultiplier());
	}
	
	@Test
	public void testSetMultiplier() {
		food.setMultiplier(0.7);
		
		assertEquals(0.7, food.getMultiplier());
	}
	
	@Test
	public void testIsDeleted() {
		assertFalse(food.isDeleted());
	}
	
	@Test
	public void testSetDeleted() {
		food.setDeleted(true);
		
		assertTrue(food.isDeleted());
	}
}
