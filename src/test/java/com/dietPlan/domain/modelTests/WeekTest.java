package com.dietPlan.domain.modelTests;

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

public class WeekTest {

	private Food food;
	private Food foodTwo;
	private Day day;
	private Day dayTwo;
	private Week week;
	
	@BeforeEach
	public void setUp() {
		food = new Food();
		food.setName("Pasta");
		food.setCalories(300);
		food.setProtein(20);
		food.setCarbs(50);
		food.setSugar(5);
		food.setFat(4);
		food.setSaturatedFat(2);
		food.setSodium(300);
		food.setPotassium(450);
		food.setMultiplier(1.0);
		
		foodTwo = new Food();
		foodTwo.setName("Soup");
		foodTwo.setCalories(450);
		foodTwo.setProtein(30);
		foodTwo.setCarbs(50);
		foodTwo.setSugar(5);
		foodTwo.setFat(14);
		foodTwo.setSaturatedFat(6);
		foodTwo.setSodium(150);
		foodTwo.setPotassium(350);
		foodTwo.setMultiplier(1.0);
		
		day = new Day();
		day.setFoods(List.of(food, foodTwo));
		day.setTotalCalories(750);
		day.setTotalProtein(50);
		day.setTotalCarbs(100);
		day.setTotalSugar(10);
		day.setTotalFat(18);
		day.setTotalSaturatedFat(8);
		day.setTotalSodium(450);
		day.setTotalPotassium(800);
		day.setCarbRatio(40);
		day.setProteinRatio(40);
		day.setFatRatio(20);
		
		dayTwo = new Day();
		dayTwo.setFoods(List.of(food, food));
		dayTwo.setTotalCalories(600);
		dayTwo.setTotalProtein(40);
		dayTwo.setTotalCarbs(100);
		dayTwo.setTotalSugar(10);
		dayTwo.setTotalFat(8);
		dayTwo.setTotalSaturatedFat(4);
		dayTwo.setTotalSodium(600);
		dayTwo.setTotalPotassium(900);
		dayTwo.setCarbRatio(48);
		dayTwo.setProteinRatio(40);
		dayTwo.setFatRatio(12);
		
		week = new Week();
		week.setId(5L);
		week.setDaysInList(List.of(day, dayTwo));
		week.setDayIdsInDayList(List.of(4L, 9L));
		week.setTotalCalories(1350);
		week.setTotalProtein(90);
		week.setTotalCarbs(200);
		week.setTotalSugar(20);
		week.setTotalFat(26);
		week.setTotalSaturatedFat(12);
		week.setTotalSodium(1400);
		week.setTotalPotassium(1700);
		week.setCarbRatio(40);
		week.setProteinRatio(33);
		week.setFatRatio(27);
		week.setDeleted(false);
	}
	
	@Test
	public void testGetId() {
		assertEquals(5L, week.getId());
	}
	
	@Test
	public void testSetId() {
		week.setId(3L);
		
		assertEquals(3L, week.getId());
	}
	
	@Test
	public void testGetDaysInList() {		
		assertNotNull(week.getDaysInList());
		assertEquals(dayTwo, week.getDaysInList().get(1));
		assertEquals(20, week.getDaysInList().get(0).getFoods().get(0).getProtein());
	}
	
	@Test
	public void testSetDaysInList() {
		List<Day> newListOfDays = List.of(day, day);
		week.setDaysInList(newListOfDays);
		
		assertNotNull(week.getDaysInList());
		assertEquals(day, week.getDaysInList().get(1));
		assertEquals(4, week.getDaysInList().get(1).getFoods().get(0).getFat());
	}
	
	@Test
	public void testGetDayIdsInDayList() {
		assertNotNull(week.getDayIdsInDayList());
		assertEquals(2, week.getDayIdsInDayList().size());
		assertEquals(4L, week.getDayIdsInDayList().get(0));
		assertEquals(9L, week.getDayIdsInDayList().get(1));
	}
	
	@Test
	public void testSetDayIdsInDayList() {
		week.setDayIdsInDayList(List.of(4L));
		
		assertNotNull(week.getDayIdsInDayList());
		assertEquals(1, week.getDayIdsInDayList().size());
		assertEquals(4L, week.getDayIdsInDayList().get(0));
	}
	
	@Test
	public void testGetTotalCalories() {
		assertEquals(1350, week.getTotalCalories());
	}
	
	@Test
	public void testSetTotalCalories() {
		week.setTotalCalories(1600);
		
		assertEquals(1600, week.getTotalCalories());
	}
	
	@Test
	public void testGetTotalProtein() {
		assertEquals(90, week.getTotalProtein());
	}
	
	@Test
	public void testSetTotalProtein() {
		week.setTotalProtein(80);
		
		assertEquals(80, week.getTotalProtein());
	}
	
	@Test
	public void testGetTotalCarbs() {
		assertEquals(200, week.getTotalCarbs());
	}
	
	@Test
	public void testSetTotalCarbs() {
		week.setTotalCarbs(600);
		
		assertEquals(600, week.getTotalCarbs());
	}
	
	@Test
	public void testGetTotalSugar() {
		assertEquals(20, week.getTotalSugar());
	}
	
	@Test
	public void testSetTotalSugar() {
		week.setTotalSugar(50);
		
		assertEquals(50, week.getTotalSugar());
	}
	
	@Test
	public void testGetTotalFat() {
		assertEquals(26, week.getTotalFat());
	}
	
	@Test
	public void testSetTotalFat() {
		week.setTotalFat(36);
		
		assertEquals(36, week.getTotalFat());
	}
	
	@Test
	public void testGetTotalSaturatedFat() {
		assertEquals(12, week.getTotalSaturatedFat());
	}
	
	@Test
	public void testSetTotalSaturatedFat() {
		week.setTotalSaturatedFat(15);
		
		assertEquals(15, week.getTotalSaturatedFat());
	}
	
	@Test
	public void testGetTotalSodium() {
		assertEquals(1400, week.getTotalSodium());
	}
	
	@Test
	public void testSetTotalSodium() {
		week.setTotalSodium(1700);
		
		assertEquals(1700, week.getTotalSodium());
	}
	
	@Test
	public void testGetTotalPotassium() {
		assertEquals(1700, week.getTotalPotassium());
	}
	
	@Test
	public void testSetTotalPotassium() {
		week.setTotalPotassium(2000);
		
		assertEquals(2000, week.getTotalPotassium());
	}
	
	@Test
	public void testGetCarbRatio() {
		assertEquals(40, week.getCarbRatio());
	}
	
	@Test
	public void testSetCarbRatio() {
		week.setCarbRatio(25);
		
		assertEquals(25, week.getCarbRatio());
	}
	
	@Test
	public void testGetProteinRatio() {
		assertEquals(33, week.getProteinRatio());
	}
	
	@Test
	public void testSetProteinRatio() {
		week.setProteinRatio(30);
		
		assertEquals(30, week.getProteinRatio());
	}
	
	@Test
	public void testGetFatRatio() {
		assertEquals(27, week.getFatRatio());
	}
	
	@Test
	public void testSetFatRatio() {
		week.setFatRatio(35);
		
		assertEquals(35, week.getFatRatio());
	}
	
	@Test
	public void testIsDeleted() {
		assertFalse(week.isDeleted());
	}
	
	@Test
	public void testSetDeleted() {
		week.setDeleted(true);
		
		assertTrue(week.isDeleted());
	}
}
