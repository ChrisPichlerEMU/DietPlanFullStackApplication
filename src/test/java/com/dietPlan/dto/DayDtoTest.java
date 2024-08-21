package com.dietPlan.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DayDtoTest {
	
	private FoodDto foodDto;
	private FoodDto foodDtoTwo;
	private DayDto dayDto;
	private WeekDto weekDto;
	
	@BeforeEach
	public void setUp() {
		foodDto = new FoodDto();
		foodDto.setName("Apple");
		foodDto.setCalories(50);
		foodDto.setProtein(2);
		foodDto.setCarbs(11);
		foodDto.setSugar(10);
		foodDto.setFat(1);
		foodDto.setSaturatedFat(0);
		foodDto.setSodium(0);
		foodDto.setPotassium(350);
		foodDto.setMultiplier(1.0);
		
		foodDtoTwo = new FoodDto();
		foodDtoTwo.setName("Pork loin");
		foodDtoTwo.setCalories(550);
		foodDtoTwo.setProtein(120);
		foodDtoTwo.setCarbs(0);
		foodDtoTwo.setSugar(1);
		foodDtoTwo.setFat(8);
		foodDtoTwo.setSaturatedFat(2);
		foodDtoTwo.setSodium(0);
		foodDtoTwo.setPotassium(450);
		foodDtoTwo.setMultiplier(1.0);
		
		weekDto = new WeekDto();
		
		dayDto = new DayDto();
		weekDto.setDaysInList(List.of(dayDto));
		dayDto.setFoods(List.of(foodDto, foodDtoTwo));
		dayDto.setWeek(weekDto);
		dayDto.setTotalCalories(550);
		dayDto.setTotalProtein(122);
		dayDto.setTotalCarbs(11);
		dayDto.setTotalSugar(11);
		dayDto.setTotalFat(9);
		dayDto.setTotalSaturatedFat(2);
		dayDto.setTotalSodium(0);
		dayDto.setTotalPotassium(800);
		dayDto.setCarbRatio(10);
		dayDto.setProteinRatio(70);
		dayDto.setFatRatio(20);
		dayDto.setDeleted(false);
	}
	
	@Test
	public void testGetFoods() {
		assertNotNull(dayDto.getFoods());
		assertEquals(foodDto, dayDto.getFoods().get(0));
		assertEquals("Pork loin", dayDto.getFoods().get(1).getName());
	}
	
	@Test
	public void testSetFoods() {
		List<FoodDto> newListOfFoods = List.of(foodDto, foodDto);
		dayDto.setFoods(newListOfFoods);
		
		assertNotNull(dayDto.getFoods());
		assertEquals(foodDto, dayDto.getFoods().get(1));
		assertEquals(2, dayDto.getFoods().get(1).getProtein());
	}
	
	@Test
	public void testGetWeek() {
		assertNotNull(dayDto.getWeek());
		assertEquals(dayDto, dayDto.getWeek().getDaysInList().get(0));
		assertEquals(550, dayDto.getWeek().getDaysInList().get(0).getTotalCalories());
		assertEquals(foodDto, dayDto.getWeek().getDaysInList().get(0).getFoods().get(0));
		assertEquals(11, dayDto.getWeek().getDaysInList().get(0).getFoods().get(0).getCarbs());
	}
	
	@Test
	public void testSetWeek() {
		WeekDto weekDtoTwo = new WeekDto();
		DayDto dayDtoTwo = new DayDto();
		dayDtoTwo.setFoods(List.of(foodDto, foodDto));
		weekDtoTwo.setDaysInList(List.of(dayDtoTwo));
		dayDto.setWeek(weekDtoTwo);
		
		assertNotNull(dayDto.getWeek());
		assertEquals(dayDtoTwo, dayDto.getWeek().getDaysInList().get(0));
		assertEquals(foodDto, dayDto.getWeek().getDaysInList().get(0).getFoods().get(1));
	}
	
	@Test
	public void testGetTotalCalories() {
		assertEquals(550, dayDto.getTotalCalories());
	}
	
	@Test
	public void testSetTotalCalories() {
		dayDto.setTotalCalories(400);
		
		assertEquals(400, dayDto.getTotalCalories());
	}
	
	@Test
	public void testGetTotalProtein() {
		assertEquals(122, dayDto.getTotalProtein());
	}
	
	@Test
	public void testSetTotalProtein() {
		dayDto.setTotalProtein(100);
		
		assertEquals(100, dayDto.getTotalProtein());
	}
	
	@Test
	public void testGetTotalCarbs() {
		assertEquals(11, dayDto.getTotalCarbs());
	}
	
	@Test
	public void testSetTotalCarbs() {
		dayDto.setTotalCarbs(30);
		
		assertEquals(30, dayDto.getTotalCarbs());
	}
	
	@Test
	public void testGetTotalSugar() {
		assertEquals(11, dayDto.getTotalSugar());
	}
	
	@Test
	public void testSetTotalSugar() {
		dayDto.setTotalSugar(5);
		
		assertEquals(5, dayDto.getTotalSugar());
	}
	
	@Test
	public void testGetTotalFat() {
		assertEquals(9, dayDto.getTotalFat());
	}
	
	@Test
	public void testSetTotalFat() {
		dayDto.setTotalFat(15);
		
		assertEquals(15, dayDto.getTotalFat());
	}
	
	@Test
	public void testGetTotalSaturatedFat() {
		assertEquals(2, dayDto.getTotalSaturatedFat());
	}
	
	@Test
	public void testSetTotalSaturatedFat() {
		dayDto.setTotalSaturatedFat(8);
		
		assertEquals(8, dayDto.getTotalSaturatedFat());
	}
	
	@Test
	public void testGetSodium() {
		assertEquals(0, dayDto.getTotalSodium());
	}
	
	@Test
	public void testSetSodium() {
		dayDto.setTotalSodium(50);
		
		assertEquals(50, dayDto.getTotalSodium());
	}
	
	@Test
	public void testGetTotalPotassium() {
		assertEquals(800, dayDto.getTotalPotassium());
	}
	
	@Test
	public void testSetTotalPotassium() {
		dayDto.setTotalPotassium(600);
		
		assertEquals(600, dayDto.getTotalPotassium());
	}
	
	@Test
	public void testGetCarbRatio() {
		assertEquals(10, dayDto.getCarbRatio());
	}
	
	@Test
	public void testSetCarbRatio() {
		dayDto.setCarbRatio(20);
		
		assertEquals(20, dayDto.getCarbRatio());
	}
	
	@Test
	public void testGetProteinRatio() {
		assertEquals(70, dayDto.getProteinRatio());
	}
	
	@Test
	public void testSetProteinRatio() {
		dayDto.setProteinRatio(50);
		
		assertEquals(50, dayDto.getProteinRatio());
	}
	
	@Test
	public void testGetFatRatio() {
		assertEquals(20, dayDto.getFatRatio());
	}
	
	@Test
	public void testSetFatRatio() {
		dayDto.setFatRatio(10);
		
		assertEquals(10, dayDto.getFatRatio());
	}
	
	@Test
	public void testIsDeleted() {
		assertFalse(dayDto.isDeleted());
	}
	
	@Test
	public void testSetDeleted() {
		dayDto.setDeleted(true);
		
		assertTrue(dayDto.isDeleted());
	}
}