package com.dietPlan.infrastructure.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dietPlan.domain.mapper.WeekMapper;
import com.dietPlan.domain.model.Week;
import com.dietPlan.domain.repository.WeekRepository;
import com.dietPlan.infrastructure.service.WeekService;
import com.dietPlan.web.dto.DayDto;
import com.dietPlan.web.dto.FoodDto;
import com.dietPlan.web.dto.WeekDto;

public class WeekServiceTest {
	
	@Mock
	private WeekRepository weekRepository;
	
	@InjectMocks
	private WeekService weekService;
	
	private WeekDto weekDto;
	private Week week;
	private DayDto dayDto;
	private DayDto dayDtoTwo;
	private FoodDto foodDto;
	private FoodDto foodDtoTwo;
	
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
		
		week = WeekMapper.INSTANCE.toWeek(weekDto);
	}
	
	@Test
	void testAddWeekValid() {
		Week savedWeek = WeekMapper.INSTANCE.toWeek(weekDto);
		
		//when(weekMapper.toWeek(weekDto)).thenReturn(week);
		when(weekRepository.save(any(Week.class))).thenReturn(savedWeek);
		
		WeekDto result = weekService.addWeek(weekDto);
		
		assertNotNull(result);
		assertEquals(1200, result.getDaysInList().get(1).getTotalPotassium());
		assertEquals("Chicken breast", result.getDaysInList().get(1).getFoods().get(1).getName());
	}
	
	@Test
	void testAddWeekInvalidNullWeekDto() {
		WeekDto result = weekService.addWeek(null);
		
		assertNull(result);
	}
	
	@Test
	void testDeleteWeekValid() {
		when(weekRepository.findById(1L)).thenReturn(Optional.of(week));
		
		WeekDto result = weekService.deleteWeek(1L);
		
		assertNotNull(result);
		assertTrue(result.isDeleted());
	}
	
	@Test
	void testDeleteWeekInvalidNotInDatabase() {
		when(weekRepository.findById(1L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> weekService.deleteWeek(1L), "deleteWeek() should've thrown an exception, but it didn't");
	
		assertTrue(thrownException.getMessage().equals("Week object not found in database in deleteWeek method with id = " + 1L));
	}
}
