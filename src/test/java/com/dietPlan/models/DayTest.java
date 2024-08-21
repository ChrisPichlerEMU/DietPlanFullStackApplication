package com.dietPlan.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dietPlan.domain.model.Day;
import com.dietPlan.domain.model.Food;
import com.dietPlan.domain.model.Week;

public class DayTest {
	private Food food;
	private Food foodTwo;
	private Day day;
	private Week week;
	
	@BeforeEach
	public void setUp() {
		food = new Food();
		food.setId(3L);
		food.setName("Oatmeal");
		food.setCalories(150);
		food.setProtein(10);
		food.setCarbs(20);
		food.setSugar(0);
		food.setFat(3);
		food.setSaturatedFat(1);
		food.setSodium(50);
		food.setPotassium(450);
		food.setMultiplier(1.0);
		food.setDeleted(false);
		
		foodTwo = new Food();
		foodTwo.setId(2L);
		foodTwo.setName("Salmon");
		foodTwo.setCalories(350);
		foodTwo.setProtein(70);
		foodTwo.setCarbs(0);
		foodTwo.setSugar(2);
		foodTwo.setFat(7);
		foodTwo.setSaturatedFat(3);
		foodTwo.setSodium(100);
		foodTwo.setPotassium(400);
		foodTwo.setMultiplier(1.0);
		foodTwo.setDeleted(false);
		
		week = new Week();
		
		day = new Day();
		day.setId(3L);
		week.setDaysInList(List.of(day));
		day.setFoods(List.of(food, foodTwo));
		day.setWeek(week);
		day.setTotalCalories(650);
		day.setTotalProtein(152);
		day.setTotalCarbs(10);
		day.setTotalSugar(10);
		day.setTotalFat(9);
		day.setTotalSaturatedFat(2);
		day.setTotalSodium(0);
		day.setTotalPotassium(800);
		day.setCarbRatio(10);
		day.setProteinRatio(70);
		day.setFatRatio(20);
		day.setDeleted(false);
	}
	
	@Test
	public void getId() {
		assertEquals(3L, day.getId());
	}
	
	@Test
	public void setId() {
		day.setId(4L);
		assertEquals(4L, day.getId());
	}
	
	@Test
	public void testGetFoods() {
		assertNotNull(day.getFoods());
		assertEquals(food, day.getFoods().get(0));
		assertEquals("Salmon", day.getFoods().get(1).getName());
	}
	
	@Test
	public void testSetFoods() {
		List<Food> newListOfFoods = List.of(food, food);
		day.setFoods(newListOfFoods);
		
		assertNotNull(day.getFoods());
		assertEquals(food, day.getFoods().get(1));
		assertEquals(10, day.getFoods().get(1).getProtein());
	}
	
	@Test
	public void testGetWeek() {
		assertNotNull(day.getWeek());
		assertEquals(day, day.getWeek().getDaysInList().get(0));
		assertEquals(650, day.getWeek().getDaysInList().get(0).getTotalCalories());
		assertEquals(food, day.getWeek().getDaysInList().get(0).getFoods().get(0));
		assertEquals(0, day.getWeek().getDaysInList().get(0).getFoods().get(1).getCarbs());
	}
	
	@Test
	public void testSetWeek() {
		Week weekTwo = new Week();
		Day dayTwo = new Day();
		dayTwo.setFoods(List.of(food, food));
		weekTwo.setDaysInList(List.of(dayTwo));
		day.setWeek(weekTwo);
		
		assertNotNull(day.getWeek());
		assertEquals(dayTwo, day.getWeek().getDaysInList().get(0));
		assertEquals(food, day.getWeek().getDaysInList().get(0).getFoods().get(1));
	}
	
	@Test
	public void testGetTotalCalories() {
		assertEquals(650, day.getTotalCalories());
	}
	
	@Test
	public void testSetTotalCalories() {
		day.setTotalCalories(300);
		
		assertEquals(300, day.getTotalCalories());
	}
	
	@Test
	public void testGetTotalProtein() {
		assertEquals(152, day.getTotalProtein());
	}
	
	@Test
	public void testSetTotalProtein() {
		day.setTotalProtein(70);
		
		assertEquals(70, day.getTotalProtein());
	}
	
	@Test
	public void testGetTotalCarbs() {
		assertEquals(10, day.getTotalCarbs());
	}
	
	@Test
	public void testSetTotalCarbs() {
		day.setTotalCarbs(20);
		
		assertEquals(20, day.getTotalCarbs());
	}
	
	@Test
	public void testGetTotalSugar() {
		assertEquals(10, day.getTotalSugar());
	}
	
	@Test
	public void testSetTotalSugar() {
		day.setTotalSugar(6);
		
		assertEquals(6, day.getTotalSugar());
	}
	
	@Test
	public void testGetTotalFat() {
		assertEquals(9, day.getTotalFat());
	}
	
	@Test
	public void testSetTotalFat() {
		day.setTotalFat(12);
		
		assertEquals(12, day.getTotalFat());
	}
	
	@Test
	public void testGetTotalSaturatedFat() {
		assertEquals(2, day.getTotalSaturatedFat());
	}
	
	@Test
	public void testSetTotalSaturatedFat() {
		day.setTotalSaturatedFat(9);
		
		assertEquals(9, day.getTotalSaturatedFat());
	}
	
	@Test
	public void testGetSodium() {
		assertEquals(0, day.getTotalSodium());
	}
	
	@Test
	public void testSetSodium() {
		day.setTotalSodium(550);
		
		assertEquals(550, day.getTotalSodium());
	}
	
	@Test
	public void testGetTotalPotassium() {
		assertEquals(800, day.getTotalPotassium());
	}
	
	@Test
	public void testSetTotalPotassium() {
		day.setTotalPotassium(400);
		
		assertEquals(400, day.getTotalPotassium());
	}
	
	@Test
	public void testGetCarbRatio() {
		assertEquals(10, day.getCarbRatio());
	}
	
	@Test
	public void testSetCarbRatio() {
		day.setCarbRatio(30);
		
		assertEquals(30, day.getCarbRatio());
	}
	
	@Test
	public void testGetProteinRatio() {
		assertEquals(70, day.getProteinRatio());
	}
	
	@Test
	public void testSetProteinRatio() {
		day.setProteinRatio(40);
		
		assertEquals(40, day.getProteinRatio());
	}
	
	@Test
	public void testGetFatRatio() {
		assertEquals(20, day.getFatRatio());
	}
	
	@Test
	public void testSetFatRatio() {
		day.setFatRatio(15);
		
		assertEquals(15, day.getFatRatio());
	}
	
	@Test
	public void testIsDeleted() {
		assertFalse(day.isDeleted());
	}
	
	@Test
	public void testSetDeleted() {
		day.setDeleted(true);
		
		assertTrue(day.isDeleted());
	}
}
