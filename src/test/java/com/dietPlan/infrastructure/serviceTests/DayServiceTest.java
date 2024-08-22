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

import com.dietPlan.domain.mapper.DayMapper;
import com.dietPlan.domain.model.Day;
import com.dietPlan.domain.repository.DayRepository;
import com.dietPlan.infrastructure.service.DayService;
import com.dietPlan.web.dto.DayDto;
import com.dietPlan.web.dto.FoodDto;

public class DayServiceTest {
	
	@Mock
	private DayRepository dayRepository;
	
	@InjectMocks
	private DayService dayService;
	
	private DayDto dayDto;
	private Day day;
	private FoodDto foodDto;
	
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
		
		day = DayMapper.INSTANCE.toDay(dayDto);
	}

	@Test
	void testAddDayValid() {
		when(dayRepository.save(any(Day.class))).thenReturn(day);
		
		DayDto result = dayService.addDay(dayDto);
		
		assertNotNull(result);
		assertEquals(500, result.getTotalCalories());
		assertEquals(20, result.getFoods().get(0).getSugar());
	}
	
	@Test
	void testAddDayInvalidNullDayDto() {
		DayDto result = dayService.addDay(null);
		
		assertNull(result);
	}
	
	@Test
	void testDeleteDayValid() {
		when(dayRepository.findById(1L)).thenReturn(Optional.of(day));
		
		DayDto result = dayService.deleteDay(1L);
		
		assertNotNull(result);
		assertTrue(result.isDeleted());
	}
	
	@Test
	void testDeleteDayInvalidNotInDatabase() {
		when(dayRepository.findById(1L)).thenReturn(Optional.empty());
		
		RuntimeException thrownException = assertThrows(RuntimeException.class, () -> dayService.deleteDay(1L), "deleteDay() should've thrown an exception, but it didn't");
	
		assertTrue(thrownException.getMessage().equals("Day object not found in database in deleteDay method with id = " + 1L));
	}
}
