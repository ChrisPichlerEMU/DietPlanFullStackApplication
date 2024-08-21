package com.dietPlan.domain.mapperTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dietPlan.domain.mapper.DayMapper;
import com.dietPlan.domain.model.Day;
import com.dietPlan.domain.model.Food;
import com.dietPlan.domain.model.Week;
import com.dietPlan.web.dto.DayDto;
import com.dietPlan.web.dto.FoodDto;
import com.dietPlan.web.dto.WeekDto;

public class DayMapperTest {
	private final DayMapper dayMapper = DayMapper.INSTANCE;
	
	private FoodDto foodDto;
	private FoodDto foodDtoTwo;
	private Food food;
	private Food foodTwo;
	private Day day;
	private Day dayNull;
	private DayDto dayDto;
	private DayDto dayDtoNull;
	private Week week;
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
		food.setDeleted(false);
		
		foodTwo = new Food();
		foodTwo.setName("Chocolate ice cream");
		foodTwo.setCalories(250);
		foodTwo.setProtein(10);
		foodTwo.setCarbs(40);
		foodTwo.setSugar(25);
		foodTwo.setFat(6);
		foodTwo.setSaturatedFat(4);
		foodTwo.setSodium(250);
		foodTwo.setPotassium(200);
		foodTwo.setDeleted(false);
		
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
		
		day = new Day();
		List<Day> daysInWeek = List.of(day);
		
		week.setDaysInList(new ArrayList<>(daysInWeek));
		
		
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
		
		dayDto = new DayDto();
		List<DayDto> dayDtosInWeek = List.of(dayDto);
		
		weekDto.setDaysInList(new ArrayList<>(dayDtosInWeek));
	}
	
	@Test
	void testToDayDtoValid() {
		day = new Day();
		day.setFoods(List.of(food, food, foodTwo));
		day.setWeek(week);
		
		dayDto = dayMapper.toDayDto(day);
		
		assertNotNull(dayDto);
		assertEquals(dayDto.getFoods().size(), day.getFoods().size());
		assertEquals(dayDto.getFoods().get(0).getName(), day.getFoods().get(0).getName());
		assertEquals(dayDto.getFoods().get(1).getProtein(), day.getFoods().get(1).getProtein());
		assertEquals(dayDto.getFoods().get(2).getSaturatedFat(), day.getFoods().get(2).getSaturatedFat());
		assertEquals(dayDto.getWeek().getTotalFat(), day.getWeek().getTotalFat());
	}
	
	@Test
	void testToDayDtoInvalidNullDay() {
		dayDto = dayMapper.toDayDto(dayNull);
		
		assertNull(dayDto);
	}
	
	@Test
	void testToDayDtoInvalidDay_FoodsNull() {
		day.setFoods(null);
		
		dayDto = dayMapper.toDayDto(day);
		
		assertNull(dayDto.getFoods());
	}
	
	@Test
	void testToDayDtoInvalidFoodInDay_FoodsNull() {
		Food nullFood = null;
		
		day.setFoods(Collections.singletonList(nullFood));
		
		dayDto = dayMapper.toDayDto(day);
		
		assertNull(dayDto.getFoods().get(0));
	}
	
	@Test
	void testToDayDtoInvalidDayListInWeekNull() {
		day.setWeek(week);
		week.setDaysInList(null);
		
		dayDto = dayMapper.toDayDto(day);
		
		assertNull(dayDto.getWeek().getDaysInList());
	}
	
	@Test
	void testToDayValid() {
		dayDto = new DayDto();
		dayDto.setFoods(List.of(foodDto, foodDtoTwo, foodDtoTwo));
		dayDto.setWeek(weekDto);
		
		day = dayMapper.toDay(dayDto);
		
		assertNotNull(day);
		assertEquals(day.getFoods().size(), dayDto.getFoods().size());
		assertEquals(day.getFoods().get(0).getCarbs(), dayDto.getFoods().get(0).getCarbs());
		assertEquals(day.getFoods().get(1).getFat(), dayDto.getFoods().get(1).getFat());
		assertEquals(day.getFoods().get(2).isDeleted(), dayDto.getFoods().get(2).isDeleted());
		assertEquals(day.getWeek().getTotalCarbs(), dayDto.getWeek().getTotalCarbs());
	}
	
	@Test
	void testToDayInvalidNullDayDto() {
		day = dayMapper.toDay(dayDtoNull);
		
		assertNull(day);
	}
	
	@Test
	void testToDayInvalidDayListInWeekNull() {
		dayDto.setWeek(weekDto);
		weekDto.setDaysInList(null);
		
		day = dayMapper.toDay(dayDto);
		
		assertNull(day.getWeek().getDaysInList());
	}
	
	@Test
	void testUpdatedFoodRowFromDtoValid() {
		day.setId(3L);
		day.setWeek(week);
		
		List<Food> foodList = Collections.singletonList(food);
		day.setFoods(new ArrayList<>(foodList));
		
		dayDto = new DayDto();
		List<FoodDto> foodListForDto = Collections.singletonList(foodDto);
		dayDto.setFoods(new ArrayList<FoodDto>(foodListForDto));
		dayDto.setWeek(weekDto);
		
		dayMapper.updateFoodRowFromDto(dayDto, day);
		
		assertNotNull(day);
		assertEquals(3L, day.getId());
		assertEquals(1, day.getFoods().size());
		assertEquals("Protein bar", day.getFoods().get(0).getName());
		assertEquals(400, day.getFoods().get(0).getCalories());
		assertEquals(dayDto.getWeek().getTotalCalories(), day.getWeek().getTotalCalories());
	}
	
	@Test
	void testUpdatedFoodRowFromDtoInvalidNullDayDto() {
		day.setId(3L);
		List<Food> foodList = List.of(food);
		day.setFoods(new ArrayList<>(foodList));
		
		dayMapper.updateFoodRowFromDto(dayDtoNull, day);
		
		assertNotNull(day);
		assertEquals(3L, day.getId());
		assertEquals(1, day.getFoods().size());
		assertEquals(2, day.getFoods().get(0).getProtein());
		assertEquals(250, day.getFoods().get(0).getPotassium());
	}
	
	@Test
	void testUpdatedFoodRowFromDtoInvalidDay_FoodsNull(){
		day.setId(3L);
		day.setFoods(null);
		
		dayDto = new DayDto();
		List<FoodDto> foodListForDto = List.of(foodDto);
		dayDto.setFoods(new ArrayList<FoodDto>(foodListForDto));
		
		dayMapper.updateFoodRowFromDto(dayDto, day);
		
		assertNotNull(day);
		assertEquals(3L, day.getId());
		assertEquals(1, day.getFoods().size());
		assertEquals("Protein bar", day.getFoods().get(0).getName());
		assertEquals(200, day.getFoods().get(0).getSodium());
	}
	
	@Test
	void testUpdatedFoodRowFromDtoInvalidDay_FoodsNullDayDto_FoodsNull() {
		day.setId(3L);
		day.setFoods(null);
		
		dayDto = new DayDto();
		dayDto.setFoods(null);
		
		dayMapper.updateFoodRowFromDto(dayDto, day);
		
		assertNotNull(day);
		assertNull(day.getFoods());
	}
	
	@Test
	void testUpdatedFoodRowFromDtoInvalidDayDto_FoodNull() {
		day.setId(3L);
		List<Food> foodList = List.of(food);
		day.setFoods(new ArrayList<>(foodList));
		
		dayDto = new DayDto();
		dayDto.setFoods(null);
		
		dayMapper.updateFoodRowFromDto(dayDto, day);
		
		assertNotNull(day);
		assertNull(day.getFoods());
	}
	
	@Test
	void testUpdatedFoodRowFromDtoInvalidDayDtoHasNullFood() {
		day.setId(3L);
		List<Food> foodList = List.of(food);
		day.setFoods(new ArrayList<>(foodList));
		
		FoodDto nullFoodDto = null;
		
		dayDto = new DayDto();
		dayDto.setFoods(Collections.singletonList(nullFoodDto));
		
		dayMapper.updateFoodRowFromDto(dayDto, day);
		
		assertNotNull(day);
		assertNull(day.getFoods().get(0));
	}
	
	@Test
	void testUpdatedFoodRowFromDtoInvalidDay_WeekNull() {
		day.setId(3L);
		day.setWeek(null);
		
		WeekDto weekDto = new WeekDto();
		dayDto = new DayDto();
		dayDto.setWeek(weekDto);
		
		dayMapper.updateFoodRowFromDto(dayDto, day);
		
		assertNotNull(day);
		assertNotNull(day.getWeek());
	}	
}
