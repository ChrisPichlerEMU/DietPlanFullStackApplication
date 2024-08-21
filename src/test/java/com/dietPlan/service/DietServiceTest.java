package com.dietPlan.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.dietPlan.domain.mapper.DayMapper;
import com.dietPlan.domain.mapper.FoodMapper;
import com.dietPlan.domain.mapper.WeekMapper;
import com.dietPlan.domain.model.Day;
import com.dietPlan.domain.model.Food;
import com.dietPlan.domain.model.Week;
import com.dietPlan.domain.repository.DayRepository;
import com.dietPlan.domain.repository.FoodRepository;
import com.dietPlan.domain.repository.WeekRepository;
import com.dietPlan.dto.*;
import com.dietPlan.infrastructure.service.CalculateStats;
import com.dietPlan.infrastructure.service.DietService;
import com.dietPlan.mappers.*;
import com.dietPlan.models.*;
import com.dietPlan.web.dto.DayDto;
import com.dietPlan.web.dto.FoodDto;
import com.dietPlan.web.dto.WeekDto;

public class DietServiceTest {
	
	@Mock
	private FoodRepository foodRepository;

	@Mock
	private DayRepository dayRepository;
	
	@Mock
	private WeekRepository weekRepository;
	
	@Mock
	private CalculateStats calculateStats;
	
	@InjectMocks
	private DietService dietService;
	
	private FoodDto foodDto;
	private FoodDto foodDtoTwo;
	private DayDto dayDto;
	private DayDto dayDtoTwo;
	private WeekDto weekDto;
	
	private Food food;
	private Food foodTwo;
	private Day day;
	private Day dayTwo;
	private Week week;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
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
		dayDto.setFoods(List.of(foodDto, foodDto));
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
		dayDtoTwo.setFoods(List.of(foodDto, foodDtoTwo));
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
		
		food = FoodMapper.INSTANCE.toFood(foodDto);
		foodTwo = FoodMapper.INSTANCE.toFood(foodDtoTwo);
		day = DayMapper.INSTANCE.toDay(dayDto);
		dayTwo = DayMapper.INSTANCE.toDay(dayDtoTwo);
		week = WeekMapper.INSTANCE.toWeek(weekDto);
	}
	
	@Test
	void testAddFoodValid() {
		Food savedFood = FoodMapper.INSTANCE.toFood(foodDto);
		
		when(foodRepository.save(any(Food.class))).thenReturn(savedFood);
		
		FoodDto result = dietService.addFood(foodDto);
		
		assertNotNull(result);
		assertEquals("Protein bar", result.getName());
		assertEquals(40, result.getCarbs());
	}
	
	@Test
	void testAddFoodInvalidNullFoodDto() {
		FoodDto result = dietService.addFood(null);
		
		assertNull(result);
	}
	
	@Test
	void testAddDayValid() {
		Day savedDay = DayMapper.INSTANCE.toDay(dayDto);
		
		when(dayRepository.save(any(Day.class))).thenReturn(savedDay);
		
		DayDto result = dietService.addDay(dayDto);
		
		assertNotNull(result);
		assertEquals(500, result.getTotalCalories());
		assertEquals(20, result.getFoods().get(0).getSugar());
	}
	
	@Test
	void testAddDayInvalidNullDayDto() {
		DayDto result = dietService.addDay(null);
		
		assertNull(result);
	}
	
	@Test
	void testAddWeekValid() {
		Week savedWeek = WeekMapper.INSTANCE.toWeek(weekDto);
		
		//when(weekMapper.toWeek(weekDto)).thenReturn(week);
		when(weekRepository.save(any(Week.class))).thenReturn(savedWeek);
		
		WeekDto result = dietService.addWeek(weekDto);
		
		assertNotNull(result);
		assertEquals(1200, result.getDaysInList().get(1).getTotalPotassium());
		assertEquals("Chicken breast", result.getDaysInList().get(1).getFoods().get(1).getName());
	}
	
	@Test
	void testAddDayInvalidNullWeekDto() {
		WeekDto result = dietService.addWeek(null);
		
		assertNull(result);
	}
	
	@Test
	void testAddFoodsToDayValid() {
		Day dayBeingAdded = DayMapper.INSTANCE.toDay(dayDto);
		
		when(dayRepository.findById(1L)).thenReturn(Optional.of(dayBeingAdded));
		when(foodRepository.findById(2L)).thenReturn(Optional.of(foodTwo));
		when(dayRepository.save(any(Day.class))).thenReturn(dayBeingAdded);
		
		DayDto result = dietService.addFoodsToDay(1L, List.of(2L));
		
		assertNotNull(result);
		assertEquals(3, result.getFoods().size());
		assertEquals(30, result.getFoods().get(0).getProtein());
		assertEquals(9, result.getFoods().get(2).getFat());
	}
	
	@Test
	void testAddFoodsToDayInvalidDayNotInDatabase() {
		when(dayRepository.findById(1L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> dietService.addFoodsToDay(1L, List.of(2L, 3L)), "addFoodsToDay() should've thrown an exception, but it didn't");
		
		assertTrue(thrownException.getMessage().equals("Day object not found in database in addFoodsToDay method with id = " + 1L));
	}
	
	@Test
	void testAddFoodsToDayInvalidFoodNotInDatabase() {
		when(dayRepository.findById(1L)).thenReturn(Optional.of(day));
		when(foodRepository.findById(2L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> dietService.addFoodsToDay(1L, List.of(2L)), "addFoodsToDay() should've thrown an exception, but it didn't");

		assertTrue(thrownException.getMessage().equals("Food object not found in database in addFoodsToDay method with id = " + 2L));
	}
	
	@Test
	void testDeleteFoodsInDayValid() {
		Day dayBeingUpdated = DayMapper.INSTANCE.toDay(dayDto);
		dayBeingUpdated.setFoods(new ArrayList<>(List.of(food, food, foodTwo)));
		
		when(dayRepository.findById(1L)).thenReturn(Optional.of(dayBeingUpdated));
		when(foodRepository.findById(2L)).thenReturn(Optional.of(food));
		when(dayRepository.save(any(Day.class))).thenReturn(dayBeingUpdated);
		
		DayDto result = dietService.deleteFoodsInDay(1L, List.of(2L));
		
		assertNotNull(result);
		assertNotNull(result.getFoods());
		assertEquals(2, result.getFoods().size());
	}
	
	@Test
	void testDeleteFoodsInDayInvalidDayNotInDatabase() {
		when(dayRepository.findById(1L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> dietService.deleteFoodsInDay(1L, List.of(2L, 3L)), "deleteFoodsInDay() should've thrown an exception, but it didn't");
	
		assertTrue(thrownException.getMessage().equals("Day object not found in database in deleteFoodsInDay method with id = " + 1L));
	}
	
	@Test
	void testDeleteFoodsInDayInvalidFoodNotInDatabase() {
		when(dayRepository.findById(1L)).thenReturn(Optional.of(day));
		when(foodRepository.findById(2L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> dietService.deleteFoodsInDay(1L, List.of(2L)), "addFoodsToDay() should've thrown an exception, but it didn't");

		assertTrue(thrownException.getMessage().equals("Food object not found in database in deleteFoodsInDay method with id = " + 2L));
	}
	
	@Test
	void testAddDaysToWeekValid() {
		Week weekBeingUpdated = WeekMapper.INSTANCE.toWeek(weekDto);
		List<Day> daysAddedToWeekObject = List.of(day, dayTwo);
		weekBeingUpdated.getDaysInList().addAll(daysAddedToWeekObject);
		
		when(weekRepository.findById(1L)).thenReturn(Optional.of(weekBeingUpdated));
		when(dayRepository.findAllById(List.of(2L))).thenReturn(daysAddedToWeekObject);
		when(weekRepository.save(any(Week.class))).thenReturn(weekBeingUpdated);
		
		WeekDto result = dietService.addDaysToWeek(1L, List.of(2L, 3L));
		
		assertNotNull(result);
		assertEquals(500, result.getDaysInList().get(0).getTotalCalories());
		assertEquals(120, result.getDaysInList().get(1).getTotalCarbs());
	}
	
	@Test
	void testAddDaysToWeekInvalidNotInDatabase() {
		when(weekRepository.findById(1L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> dietService.addDaysToWeek(1L, List.of(2L, 3L)), "addDaysToWeek() should've thrown an exception, but it didn't");
		
		assertTrue(thrownException.getMessage().equals("Week object not found in database in addDaysToWeek method with id = " + 1L));
	}
	
	@Test
	void testAddDaysToWeekInvalidDaysListEmpty() {
		Week weekBeingUpdated = WeekMapper.INSTANCE.toWeek(weekDto);
		
		when(weekRepository.findById(1L)).thenReturn(Optional.of(weekBeingUpdated));
		when(weekRepository.save(any(Week.class))).thenReturn(weekBeingUpdated);
		
		WeekDto result = dietService.addDaysToWeek(1L, List.of());
		
		assertNotNull(result);
	}
	
	@Test
	void testDeleteDaysInWeekValid() {
		Week weekBeingUpdated = WeekMapper.INSTANCE.toWeek(weekDto);
		weekBeingUpdated.setDaysInList(new ArrayList<>(List.of(day, dayTwo)));
		List<Day> daysDeletedFromWeekObject = List.of(day);
		weekBeingUpdated.getDaysInList().removeAll(daysDeletedFromWeekObject);
		
		when(weekRepository.findById(1L)).thenReturn(Optional.of(weekBeingUpdated));
		when(weekRepository.save(any(Week.class))).thenReturn(weekBeingUpdated);
		
		WeekDto result = dietService.deleteDaysInWeek(1L, List.of(2L, 3L));
		
		assertNotNull(result);
		assertEquals(1, result.getDaysInList().size());
	}
	
	@Test
	void testDeleteDaysInWeekInvalidNotInDatabase() {
		when(weekRepository.findById(1L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> dietService.deleteDaysInWeek(1L, List.of(2L, 3L)), "deleteDaysInWeek() should've thrown an exception, but it didn't");
		
		assertTrue(thrownException.getMessage().equals("Week object not found in database in addDaysToWeek method with id = " + 1L));
	}
	
	@Test
	void testGetFoodStatsValid() {
		when(foodRepository.findById(1L)).thenReturn(Optional.of(food));
		
		FoodDto result = dietService.getFoodStats(1L);
		
		assertNotNull(result);
		assertEquals(13, result.getFat());
	}
	
	@Test
	void testGetFoodStatsInvalidNotInDatabase() {
		when(foodRepository.findById(1L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> dietService.getFoodStats(1L), "getFoodStats() should've thrown an exception, but it didn't");
	
		assertTrue(thrownException.getMessage().equals("Food object not found in database in getFoodStats method with id = " + 1L));
	}
	
	@Test
	void testGetDayStatsValid() {
		when(dayRepository.findById(1L)).thenReturn(Optional.of(day));
		when(dayRepository.save(any(Day.class))).thenReturn(day);
		
		DayDto result = dietService.getDayStats(1L);
		
		assertNotNull(result);
		assertEquals(600, result.getTotalPotassium());
	}
	
	@Test
	void testGetDayStatsInvalidNotInDatabase() {
		when(dayRepository.findById(1L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> dietService.getDayStats(1L), "getDayStats() should've thrown an exception, but it didn't");
	
		assertTrue(thrownException.getMessage().equals("Day object not found in database in getDayStats method with id = " + 1L));
	}
	
	@Test
	void testGetWeekStatsValid() {
		when(weekRepository.findById(1L)).thenReturn(Optional.of(week));
		when(weekRepository.save(any(Week.class))).thenReturn(week);
		
		WeekDto result = dietService.getWeekStats(1L);
		
		assertNotNull(result);
		assertEquals(120, result.getTotalSugar());
	}
	
	@Test
	void testGetWeekStatsInvalidNotInDatabase() {
		when(weekRepository.findById(1L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> dietService.getWeekStats(1L), "getWeekStats() should've thrown an exception, but it didn't");
	
		assertTrue(thrownException.getMessage().equals("Week object not found in database in getWeekStats method with id = " + 1L));
	}
	
	@Test
	void testEditFoodValid() {
		when(foodRepository.findById(1L)).thenReturn(Optional.of(food));
		when(foodRepository.save(any(Food.class))).thenReturn(food);
		
		FoodDto result = dietService.editFood(1L, foodDto);
		
		assertNotNull(result);
		assertEquals(150, result.getPotassium());
	}
	
	@Test
	void testEditFoodNotInDatabase() {
		when(foodRepository.findById(1L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> dietService.editFood(1L, foodDto), "editFood() should've thrown an exception, but it didn't");
	
		assertTrue(thrownException.getMessage().equals("Food object not found in database in editFood method with id = " + 1L));
	}
	
	@Test
	void testDeleteFoodValid() {
		when(foodRepository.findById(1L)).thenReturn(Optional.of(food));
		
		FoodDto result = dietService.deleteFood(1L);
		
		assertNotNull(result);
		assertTrue(result.isDeleted());
	}
	
	@Test
	void testDeleteFoodInvalidNotInDatabase() {
		when(foodRepository.findById(1L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> dietService.deleteFood(1L), "deleteFood() should've thrown an exception, but it didn't");
	
		assertTrue(thrownException.getMessage().equals("Food object not found in database in deleteFood method with id = " + 1L));
	}
	
	@Test
	void testDeleteDayValid() {
		when(dayRepository.findById(1L)).thenReturn(Optional.of(day));
		
		DayDto result = dietService.deleteDay(1L);
		
		assertNotNull(result);
		assertTrue(result.isDeleted());
	}
	
	@Test
	void testDeleteDayInvalidNotInDatabase() {
		when(dayRepository.findById(1L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> dietService.deleteDay(1L), "deleteDay() should've thrown an exception, but it didn't");
	
		assertTrue(thrownException.getMessage().equals("Day object not found in database in deleteDay method with id = " + 1L));
	}
	
	@Test
	void testDeleteWeekValid() {
		when(weekRepository.findById(1L)).thenReturn(Optional.of(week));
		
		WeekDto result = dietService.deleteWeek(1L);
		
		assertNotNull(result);
		assertTrue(result.isDeleted());
	}
	
	@Test
	void testDeleteWeekInvalidNotInDatabase() {
		when(weekRepository.findById(1L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> dietService.deleteWeek(1L), "deleteWeek() should've thrown an exception, but it didn't");
	
		assertTrue(thrownException.getMessage().equals("Week object not found in database in deleteWeek method with id = " + 1L));
	}
}
