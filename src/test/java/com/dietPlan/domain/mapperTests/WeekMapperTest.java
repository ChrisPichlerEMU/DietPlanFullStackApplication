package com.dietPlan.domain.mapperTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dietPlan.domain.mapper.WeekMapper;
import com.dietPlan.domain.model.Day;
import com.dietPlan.domain.model.Food;
import com.dietPlan.domain.model.Week;
import com.dietPlan.infrastructure.service.CalculateStats;
import com.dietPlan.web.dto.DayDto;
import com.dietPlan.web.dto.FoodDto;
import com.dietPlan.web.dto.WeekDto;

public class WeekMapperTest {
	private WeekMapper weekMapper = WeekMapper.INSTANCE;
	
	private Food food;
	private Food foodTwo;
	private FoodDto foodDto;
	private FoodDto foodDtoTwo;
	private Day day;
	private Day dayTwo;
	private DayDto dayDto;
	private DayDto dayDtoTwo;
	private Week week;
	private Week weekTwo;
	private WeekDto weekDto;
	private WeekDto weekDtoTwo;
	
	@BeforeEach
	public void setUp() {		
		food = new Food();
		food.setName("Banana");
		food.setCalories(80);
		food.setProtein(3);
		food.setCarbs(17);
		food.setSugar(16);
		food.setFat(0);
		food.setSaturatedFat(0);
		food.setSodium(0);
		food.setPotassium(400);
		food.setDeleted(false);
		
		foodTwo = new Food();
		foodTwo.setName("Potato chips");
		foodTwo.setCalories(430);
		foodTwo.setProtein(20);
		foodTwo.setCarbs(50);
		foodTwo.setSugar(20);
		foodTwo.setFat(16);
		foodTwo.setSaturatedFat(9);
		foodTwo.setSodium(400);
		foodTwo.setPotassium(300);
		foodTwo.setDeleted(true);
		
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
		
		day = new Day();
		day.setTotalCalories(700);
		day.setTotalProtein(60);
		day.setTotalCarbs(60);
		day.setTotalSugar(40);
		day.setTotalFat(20);
		day.setTotalSaturatedFat(15);
		day.setTotalSodium(600);
		day.setTotalPotassium(400);
		day.setCarbRatio(40);
		day.setProteinRatio(40);
		day.setFatRatio(20);
		
		day.setFoods(List.of(food, foodTwo));
		
		dayTwo = new Day();
		dayTwo.setTotalCalories(1300);
		dayTwo.setTotalProtein(100);
		dayTwo.setTotalCarbs(150);
		dayTwo.setTotalSugar(70);
		dayTwo.setTotalFat(19);
		dayTwo.setTotalSaturatedFat(13);
		dayTwo.setTotalSodium(1200);
		dayTwo.setTotalPotassium(1700);
		dayTwo.setCarbRatio(48);
		dayTwo.setProteinRatio(40);
		dayTwo.setFatRatio(12);
		
		dayTwo.setFoods(List.of(food, foodTwo, foodTwo));
		
		dayDto = new DayDto();
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
		
		dayDto.setFoods(List.of(foodDto, foodDto));
		
		dayDtoTwo = new DayDto();
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
		
		dayDtoTwo.setFoods(List.of(foodDto, foodDto));
		
		week = new Week();
		week.setTotalCalories(4000);
		week.setTotalProtein(350);
		week.setTotalCarbs(400);
		week.setTotalSugar(140);
		week.setTotalFat(95);
		week.setTotalSaturatedFat(35);
		week.setTotalSodium(4000);
		week.setTotalPotassium(7500);
		week.setCarbRatio(40);
		week.setProteinRatio(33);
		week.setFatRatio(27);
		
		week.setDaysInList(List.of(day, dayTwo));
		
		
		weekDto = new WeekDto();
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
		
		weekDto.setDaysInList(List.of(dayDto, dayDtoTwo));
	}
	
	@Test
	void testToWeekDtoValid() {
		weekDtoTwo = weekMapper.toWeekDto(week);
		
		assertNotNull(weekDtoTwo);
		assertEquals(weekDtoTwo.getDaysInList().size(), week.getDaysInList().size());
		assertEquals(weekDtoTwo.getDaysInList().get(0).getFoods().size(), week.getDaysInList().get(0).getFoods().size());
		assertEquals(weekDtoTwo.getTotalCalories(), week.getTotalCalories());
		assertEquals(weekDtoTwo.getDaysInList().get(0).getFoods().get(1).getName(), week.getDaysInList().get(0).getFoods().get(1).getName());		
	}
	
	@Test
	void testToWeekDtoInvalidNullWeek() {
		Week nullWeek = null;
		
		weekDto = weekMapper.toWeekDto(nullWeek);
		
		assertNull(weekDto);
	}
	
	@Test
	void testToWeekDtoInvalidNullDayInDaysInList() {
		Day nullDay = null;
		week.setDaysInList(Collections.singletonList(nullDay));
		
		weekDto = weekMapper.toWeekDto(week);
		
		assertNotNull(weekDto);
		assertNull(weekDto.getDaysInList().get(0));
	}
	
	@Test
	void testToWeekDtoInvalidNullFoodInDayInDaysInList() {
		Food nullFood = null;
		
		day.setFoods(Collections.singletonList(nullFood));
		
		week.setDaysInList(Collections.singletonList(day));
		
		weekDto = weekMapper.toWeekDto(week);
		
		assertNotNull(weekDto);
		assertNull(weekDto.getDaysInList().get(0).getFoods().get(0));
	}
	
	@Test
	void testToWeekDtoInvalidDay_FoodsNull() {
		day.setFoods(null);
		
		week.setDaysInList(Collections.singletonList(day));
		
		weekDto = weekMapper.toWeekDto(week);
		
		assertNotNull(weekDto);
		assertNull(weekDto.getDaysInList().get(0).getFoods());
	}
	
	@Test
	void testToWeekDtoInvalidDaysInListInDayNull() {
		week.setDaysInList(null);
		
		weekDto = weekMapper.toWeekDto(week);
		
		assertNotNull(weekDto);
		assertNull(weekDto.getDaysInList());
	}
	
	@Test
	void testToWeekValid() {
		weekTwo = weekMapper.toWeek(weekDto);
		
		assertNotNull(weekTwo);
		assertEquals(weekTwo.getDaysInList().size(), weekDto.getDaysInList().size());
		assertEquals(weekTwo.getDaysInList().get(1).getFoods().size(), weekDto.getDaysInList().get(1).getFoods().size());
		assertEquals(weekTwo.getTotalSaturatedFat(), weekDto.getTotalSaturatedFat());
		assertEquals(weekTwo.getDaysInList().get(1).getFoods().get(0).getCalories(), weekDto.getDaysInList().get(1).getFoods().get(0).getCalories());
	}
	
	@Test
	void testToWeekInvalidNullWeekDto() {
		WeekDto nullWeekDto = null;
		
		week = weekMapper.toWeek(nullWeekDto);
		
		assertNull(week);
	}
	
	@Test
	void testToWeekInvalidNullDayInDaysInList() {
		DayDto nullDayDto = null;
		weekDto.setDaysInList(Collections.singletonList(nullDayDto));
		
		week = weekMapper.toWeek(weekDto);
		
		assertNotNull(week);
		assertNull(week.getDaysInList().get(0));
	}
	
	@Test
	void testToWeekInvalidNullFoodInDayInDaysInList() {
		FoodDto nullFoodDto = null;
		
		dayDto.setFoods(Collections.singletonList(nullFoodDto));
		
		weekDto.setDaysInList(Collections.singletonList(dayDto));
		
		week = weekMapper.toWeek(weekDto);
		
		assertNotNull(week);
		assertNull(week.getDaysInList().get(0).getFoods().get(0));
	}
	
	@Test
	void testToWeekInvalidDay_FoodsNull() {
		dayDto.setFoods(null);
		
		weekDto.setDaysInList(Collections.singletonList(dayDto));
		
		week = weekMapper.toWeek(weekDto);
		
		assertNotNull(week);
		assertNull(week.getDaysInList().get(0).getFoods());
	}
	
	@Test
	void testToWeekInvalidDaysInListInDayNull() {
		weekDto.setDaysInList(null);
		
		week = weekMapper.toWeek(weekDto);
		
		assertNotNull(week);
		assertNull(week.getDaysInList());
	}
}
