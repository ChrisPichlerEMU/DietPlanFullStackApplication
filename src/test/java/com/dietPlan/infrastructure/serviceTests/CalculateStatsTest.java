package com.dietPlan.infrastructure.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dietPlan.domain.model.Day;
import com.dietPlan.domain.model.Food;
import com.dietPlan.domain.model.Week;
import com.dietPlan.infrastructure.service.CalculateStats;

public class CalculateStatsTest {
	
	private Food food;
	private Food foodTwo;
	private Food foodThree;
	private Food deletedFood;
	private Day day;
	private Day dayTwo;
	private Week week;
	
	private CalculateStats calculateStats;
	
	@BeforeEach
	public void setUp() {
		calculateStats = new CalculateStats();
		
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
		food.setDeleted(false);			//double variable multiplier should be 1.0 by default, so don't call setMultiplier() on food
		
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
		foodTwo.setMultiplier(0.5);		//boolean variable isDeleted should be false by default, so don't call setDeleted() on foodTwo
		
		foodThree = new Food();
		foodThree.setName("Granola bar");
		foodThree.setCalories(250);
		foodThree.setProtein(30);
		foodThree.setCarbs(10);
		foodThree.setSugar(5);
		foodThree.setFat(10);
		foodThree.setSaturatedFat(2);
		foodThree.setSodium(150);
		foodThree.setPotassium(200);
		foodThree.setMultiplier(1.0);
		foodThree.setDeleted(false);
		
		deletedFood = new Food();
		deletedFood.setName("Granola bar");
		deletedFood.setCalories(250);
		deletedFood.setProtein(30);
		deletedFood.setCarbs(10);
		deletedFood.setSugar(5);
		deletedFood.setFat(10);
		deletedFood.setSaturatedFat(2);
		deletedFood.setSodium(150);
		deletedFood.setPotassium(200);
		deletedFood.setMultiplier(1.0);
		deletedFood.setDeleted(true);
		
		day = new Day();
		
		dayTwo = new Day();
		
		week = new Week();
	}
	
	@Test
	public void testCalculateTotalDayStatsValidRatiosAddTo100() {
		day.setFoods(List.of(foodTwo, foodThree));
		
		calculateStats.calculateTotalDayStats(day);
		
		assertEquals(500, day.getTotalCalories());
		assertEquals(80, day.getTotalProtein());
		assertEquals(10, day.getTotalCarbs());
		assertEquals(5, day.getTotalSugar());
		assertEquals(14, day.getTotalFat());
		assertEquals(2, day.getTotalSaturatedFat());
		assertEquals(150, day.getTotalSodium());
		assertEquals(475, day.getTotalPotassium());
		assertEquals(8, day.getCarbRatio());
		assertEquals(67, day.getProteinRatio());
		assertEquals(25, day.getFatRatio());
	}
	
	@Test
	public void testCalculateTotalDayStatsValidRatiosAddTo99() {
		day.setFoods(List.of(food, food, foodTwo));
		
		calculateStats.calculateTotalDayStats(day);
		
		assertEquals(1050, day.getTotalCalories());
		assertEquals(110, day.getTotalProtein());
		assertEquals(80, day.getTotalCarbs());
		assertEquals(40, day.getTotalSugar());
		assertEquals(30, day.getTotalFat());
		assertEquals(16, day.getTotalSaturatedFat());
		assertEquals(400, day.getTotalSodium());
		assertEquals(575, day.getTotalPotassium());
		assertEquals(31, day.getCarbRatio());
		assertEquals(43, day.getProteinRatio());
		assertEquals(26, day.getFatRatio());
	}
	
	@Test
	public void testCalculateTotalDayStatsValidOneFoodIsDeleted() {
		day.setFoods(List.of(food, foodTwo, foodThree, deletedFood));
		
		calculateStats.calculateTotalDayStats(day);
		
		assertEquals(900, day.getTotalCalories());
		assertEquals(110, day.getTotalProtein());
		assertEquals(50, day.getTotalCarbs());
		assertEquals(25, day.getTotalSugar());
		assertEquals(27, day.getTotalFat());
		assertEquals(10, day.getTotalSaturatedFat());
		assertEquals(350, day.getTotalSodium());
		assertEquals(625, day.getTotalPotassium());
		assertEquals(22, day.getCarbRatio());
		assertEquals(51, day.getProteinRatio());
		assertEquals(27, day.getFatRatio());
	}
	
	@Test
	public void testCalculateTotalDayStatsInvalidNullDay() {
		day = null;
				
		assertThrows(IllegalArgumentException.class, () -> calculateStats.calculateTotalDayStats(day));
	}
	
	@Test
	public void testCalculateTotalWeekStatsValidRatiosAddTo100() {
		day.setFoods(List.of(foodThree));
		dayTwo.setFoods(List.of(foodThree, foodThree));
		week.setDaysInList(List.of(day, dayTwo));
		
		calculateStats.calculateTotalWeekStats(week);
		
		assertEquals(750, week.getTotalCalories());
		assertEquals(90, week.getTotalProtein());
		assertEquals(30, week.getTotalCarbs());
		assertEquals(15, week.getTotalSugar());
		assertEquals(30, week.getTotalFat());
		assertEquals(6, week.getTotalSaturatedFat());
		assertEquals(450, week.getTotalSodium());
		assertEquals(600, week.getTotalPotassium());
		assertEquals(16, week.getCarbRatio());
		assertEquals(48, week.getProteinRatio());
		assertEquals(36, week.getFatRatio());
	}
	
	@Test
	public void testCalculateTotalWeekStatsValidRatiosAddTo99() {
		day.setFoods(List.of(foodTwo, foodThree));
		dayTwo.setFoods(List.of(food, food, foodTwo));
		week.setDaysInList(List.of(day, dayTwo));
		
		calculateStats.calculateTotalWeekStats(week);
		
		assertEquals(1550, week.getTotalCalories());
		assertEquals(190, week.getTotalProtein());
		assertEquals(90, week.getTotalCarbs());
		assertEquals(45, week.getTotalSugar());
		assertEquals(44, week.getTotalFat());
		assertEquals(18, week.getTotalSaturatedFat());
		assertEquals(550, week.getTotalSodium());
		assertEquals(1050, week.getTotalPotassium());
		assertEquals(23, week.getCarbRatio());
		assertEquals(51, week.getProteinRatio());
		assertEquals(26, week.getFatRatio());
	}
	
	@Test
	public void testCalculateTotalWeekStatsInvalidNullDay() {
		day.setFoods(List.of(food, foodTwo, foodThree, deletedFood));
		dayTwo.setFoods(null);
		week.setDaysInList(List.of(day, dayTwo));
		
		calculateStats.calculateTotalWeekStats(week);
		
		assertEquals(900, week.getTotalCalories());
		assertEquals(110, week.getTotalProtein());
		assertEquals(50, week.getTotalCarbs());
		assertEquals(25, week.getTotalSugar());
		assertEquals(27, week.getTotalFat());
		assertEquals(10, week.getTotalSaturatedFat());
		assertEquals(350, week.getTotalSodium());
		assertEquals(625, week.getTotalPotassium());
		assertEquals(22, week.getCarbRatio());
		assertEquals(51, week.getProteinRatio());
		assertEquals(27, week.getFatRatio());
	}
	
	@Test
	public void testCalculateTotalWeekStatsInvalidNullWeek() {
		week = null;
		
		assertThrows(IllegalArgumentException.class, () -> calculateStats.calculateTotalWeekStats(week));
	}
}


























