package com.dietPlan.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WeekDtoTest {

	private FoodDto foodDto;
	private FoodDto foodDtoTwo;
	private DayDto dayDto;
	private DayDto dayDtoTwo;
	private WeekDto weekDto;
	
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
		foodDto.setMultiplier(1.0);
		
		foodDtoTwo = new FoodDto();
		foodDtoTwo.setName("Chicken breast");
		foodDtoTwo.setCalories(500);
		foodDtoTwo.setProtein(100);
		foodDtoTwo.setCarbs(0);
		foodDtoTwo.setSugar(0);
		foodDtoTwo.setFat(9);
		foodDtoTwo.setSaturatedFat(0);
		foodDtoTwo.setSodium(0);
		foodDtoTwo.setPotassium(550);
		foodDtoTwo.setMultiplier(1.0);
		
		dayDto = new DayDto();
		dayDto.setFoods(List.of(foodDto, foodDtoTwo));
		dayDto.setTotalCalories(500);
		dayDto.setTotalProtein(50);
		dayDto.setTotalCarbs(50);
		dayDto.setTotalSugar(25);
		dayDto.setTotalFat(9);
		dayDto.setTotalSaturatedFat(5);
		dayDto.setTotalSodium(500);
		dayDto.setTotalPotassium(600);
		dayDto.setCarbRatio(40);
		dayDto.setProteinRatio(40);
		dayDto.setFatRatio(20);
		
		dayDtoTwo = new DayDto();
		dayDtoTwo.setFoods(List.of(foodDto, foodDto));
		dayDtoTwo.setTotalCalories(1000);
		dayDtoTwo.setTotalProtein(100);
		dayDtoTwo.setTotalCarbs(120);
		dayDtoTwo.setTotalSugar(40);
		dayDtoTwo.setTotalFat(13);
		dayDtoTwo.setTotalSaturatedFat(11);
		dayDtoTwo.setTotalSodium(1100);
		dayDtoTwo.setTotalPotassium(1200);
		dayDtoTwo.setCarbRatio(48);
		dayDtoTwo.setProteinRatio(40);
		dayDtoTwo.setFatRatio(12);
		
		weekDto = new WeekDto();
		weekDto.setDaysInList(List.of(dayDto, dayDtoTwo));
		weekDto.setTotalCalories(3000);
		weekDto.setTotalProtein(250);
		weekDto.setTotalCarbs(300);
		weekDto.setTotalSugar(120);
		weekDto.setTotalFat(89);
		weekDto.setTotalSaturatedFat(20);
		weekDto.setTotalSodium(3000);
		weekDto.setTotalPotassium(3500);
		weekDto.setCarbRatio(40);
		weekDto.setProteinRatio(33);
		weekDto.setFatRatio(27);
		weekDto.setDeleted(false);
	}
	
	@Test
	public void testGetDaysInList() {		
		assertNotNull(weekDto.getDaysInList());
		assertEquals(dayDto, weekDto.getDaysInList().get(0));
		assertEquals("Protein bar", weekDto.getDaysInList().get(1).getFoods().get(1).getName());
	}
	
	@Test
	public void testSetDaysInList() {
		List<DayDto> newListOfDays = List.of(dayDto, dayDto);
		weekDto.setDaysInList(newListOfDays);
		
		assertNotNull(weekDto.getDaysInList());
		assertEquals(dayDto, weekDto.getDaysInList().get(1));
		assertEquals(500, weekDto.getDaysInList().get(1).getFoods().get(1).getCalories());
	}
	
	@Test
	public void testGetTotalCalories() {
		assertEquals(3000, weekDto.getTotalCalories());
	}
	
	@Test
	public void testSetTotalCalories() {
		weekDto.setTotalCalories(4000);
		
		assertEquals(4000, weekDto.getTotalCalories());
	}
	
	@Test
	public void testGetTotalProtein() {
		assertEquals(250, weekDto.getTotalProtein());
	}
	
	@Test
	public void testSetTotalProtein() {
		weekDto.setTotalProtein(300);
		
		assertEquals(300, weekDto.getTotalProtein());
	}
	
	@Test
	public void testGetTotalCarbs() {
		assertEquals(300, weekDto.getTotalCarbs());
	}
	
	@Test
	public void testSetTotalCarbs() {
		weekDto.setTotalCarbs(400);
		
		assertEquals(400, weekDto.getTotalCarbs());
	}
	
	@Test
	public void testGetTotalSugar() {
		assertEquals(120, weekDto.getTotalSugar());
	}
	
	@Test
	public void testSetTotalSugar() {
		weekDto.setTotalSugar(100);
		
		assertEquals(100, weekDto.getTotalSugar());
	}
	
	@Test
	public void testGetTotalFat() {
		assertEquals(89, weekDto.getTotalFat());
	}
	
	@Test
	public void testSetTotalFat() {
		weekDto.setTotalFat(100);
		
		assertEquals(100, weekDto.getTotalFat());
	}
	
	@Test
	public void testGetTotalSaturatedFat() {
		assertEquals(20, weekDto.getTotalSaturatedFat());
	}
	
	@Test
	public void testSetTotalSaturatedFat() {
		weekDto.setTotalSaturatedFat(30);
		
		assertEquals(30, weekDto.getTotalSaturatedFat());
	}
	
	@Test
	public void testGetTotalSodium() {
		assertEquals(3000, weekDto.getTotalSodium());
	}
	
	@Test
	public void testSetTotalSodium() {
		weekDto.setTotalSodium(2000);
		
		assertEquals(2000, weekDto.getTotalSodium());
	}
	
	@Test
	public void testGetTotalPotassium() {
		assertEquals(3500, weekDto.getTotalPotassium());
	}
	
	@Test
	public void testSetTotalPotassium() {
		weekDto.setTotalPotassium(3000);
		
		assertEquals(3000, weekDto.getTotalPotassium());
	}
	
	@Test
	public void testGetCarbRatio() {
		assertEquals(40, weekDto.getCarbRatio());
	}
	
	@Test
	public void testSetCarbRatio() {
		weekDto.setCarbRatio(30);
		
		assertEquals(30, weekDto.getCarbRatio());
	}
	
	@Test
	public void testGetProteinRatio() {
		assertEquals(33, weekDto.getProteinRatio());
	}
	
	@Test
	public void testSetProteinRatio() {
		weekDto.setProteinRatio(25);
		
		assertEquals(25, weekDto.getProteinRatio());
	}
	
	@Test
	public void testGetFatRatio() {
		assertEquals(27, weekDto.getFatRatio());
	}
	
	@Test
	public void testSetFatRatio() {
		weekDto.setFatRatio(15);
		
		assertEquals(15, weekDto.getFatRatio());
	}
	
	@Test
	public void testIsDeleted() {
		assertFalse(weekDto.isDeleted());
	}
	
	@Test
	public void testSetDeleted() {
		weekDto.setDeleted(true);
		
		assertTrue(weekDto.isDeleted());
	}
}
