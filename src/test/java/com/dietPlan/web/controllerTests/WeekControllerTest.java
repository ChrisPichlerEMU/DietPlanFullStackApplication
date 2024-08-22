package com.dietPlan.web.controllerTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dietPlan.domain.mapper.DayMapper;
import com.dietPlan.domain.mapper.WeekMapper;
import com.dietPlan.domain.model.Day;
import com.dietPlan.domain.model.Week;
import com.dietPlan.infrastructure.orchestrator.WeekOrchestrator;
import com.dietPlan.infrastructure.service.CalculateStats;
import com.dietPlan.infrastructure.service.WeekService;
import com.dietPlan.web.controller.WeekController;
import com.dietPlan.web.dto.DayDto;
import com.dietPlan.web.dto.FoodDto;
import com.dietPlan.web.dto.WeekDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(WeekController.class)
public class WeekControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private static WeekService weekService;
	
	@MockBean
	private static WeekOrchestrator weekOrchestrator;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private FoodDto foodDto;
	private FoodDto foodDtoTwo;
	private DayDto dayDto;
	private DayDto dayDtoTwo;
	private WeekDto weekDto;
	private CalculateStats calculateStats;
	
	@BeforeEach
	public void setUp() {
		calculateStats = new CalculateStats();
		
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
	}
	
	@AfterAll
	public static void tearDown() {
		reset(weekService);
		reset(weekOrchestrator);
	}
	
	@Test
	public void testAddWeekValidRequest() throws Exception{
		when(weekService.addWeek(any(WeekDto.class))).thenReturn(weekDto);
		
		mockMvc.perform(post("/week/addWeek")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(weekDto)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalCalories").value(3000))
				.andExpect(jsonPath("$.totalProtein").value(250))
				.andExpect(jsonPath("$.totalCarbs").value(300))
				.andExpect(jsonPath("$.totalSugar").value(120))
				.andExpect(jsonPath("$.totalFat").value(89))
				.andExpect(jsonPath("$.totalSaturatedFat").value(20))
				.andExpect(jsonPath("$.totalSodium").value(3000))
				.andExpect(jsonPath("$.totalPotassium").value(3500))
				.andExpect(jsonPath("$.carbRatio").value(40))
				.andExpect(jsonPath("$.proteinRatio").value(33))
				.andExpect(jsonPath("$.fatRatio").value(27))
				.andReturn();
	}
	
	@Test
	public void testAddWeekInvalidRequestNullBody() throws Exception{
		mockMvc.perform(post("/week/addWeek")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
			.andExpect(status().isBadRequest())		//Null body should return error code 400 
			.andReturn();
	}
	
	@Test
	public void testGetWeekValidRequest() throws Exception{
		when(weekOrchestrator.getWeekStats(any(Long.class))).thenReturn(weekDto);
		
		mockMvc.perform(put("/week/getWeek/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalCalories").value(3000))
				.andExpect(jsonPath("$.totalProtein").value(250))
				.andExpect(jsonPath("$.totalCarbs").value(300))
				.andExpect(jsonPath("$.totalSugar").value(120))
				.andExpect(jsonPath("$.totalFat").value(89))
				.andExpect(jsonPath("$.totalSaturatedFat").value(20))
				.andExpect(jsonPath("$.totalSodium").value(3000))
				.andExpect(jsonPath("$.totalPotassium").value(3500))
				.andExpect(jsonPath("$.carbRatio").value(40))
				.andExpect(jsonPath("$.proteinRatio").value(33))
				.andExpect(jsonPath("$.fatRatio").value(27))
				.andReturn();
	}
	
	@Test
	public void testDeleteWeekValidRequest() throws Exception{
		weekDto.setDeleted(true);
		
		when(weekService.deleteWeek(any(Long.class))).thenReturn(weekDto);
		
		mockMvc.perform(delete("/week/deleteWeek/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalCalories").value(3000))
				.andExpect(jsonPath("$.totalProtein").value(250))
				.andExpect(jsonPath("$.totalCarbs").value(300))
				.andExpect(jsonPath("$.totalSugar").value(120))
				.andExpect(jsonPath("$.totalFat").value(89))
				.andExpect(jsonPath("$.totalSaturatedFat").value(20))
				.andExpect(jsonPath("$.totalSodium").value(3000))
				.andExpect(jsonPath("$.totalPotassium").value(3500))
				.andExpect(jsonPath("$.carbRatio").value(40))
				.andExpect(jsonPath("$.proteinRatio").value(33))
				.andExpect(jsonPath("$.fatRatio").value(27))
				.andReturn();
	}
	
	@Test
	public void testAddDaysToWeekValidRequest() throws Exception {
		dayDto.setFoods(List.of(foodDto, foodDtoTwo));
		dayDtoTwo.setFoods(List.of(foodDto, foodDto));
		
		Day dayDtotoDay = DayMapper.INSTANCE.toDay(dayDto);
		calculateStats.calculateTotalDayStats(dayDtotoDay);		//Make sure calculateTotalDayStats() is called on dayDto to get correct total stats returned
		dayDto = DayMapper.INSTANCE.toDayDto(dayDtotoDay);
		
		Day dayDtotoDayTwo = DayMapper.INSTANCE.toDay(dayDtoTwo);
		calculateStats.calculateTotalDayStats(dayDtotoDayTwo);		//Make sure calculateTotalDayStats() is called on dayDto to get correct total stats returned
		dayDtoTwo = DayMapper.INSTANCE.toDayDto(dayDtotoDayTwo);
		
		weekDto.setDaysInList(List.of(dayDto, dayDtoTwo));
		
		Week weekDtoToWeek = WeekMapper.INSTANCE.toWeek(weekDto);
		calculateStats.calculateTotalWeekStats(weekDtoToWeek);		//Make sure calculateTotalWeekStats() is called on weekDto to get correct total stats returned
		weekDto = WeekMapper.INSTANCE.toWeekDto(weekDtoToWeek);
		
		when(weekOrchestrator.addDaysToWeek(any(Long.class), anyList())).thenReturn(weekDto);		
		
		mockMvc.perform(post("/week/addDaysToWeek/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("[1]"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalCalories").value(1700))
				.andExpect(jsonPath("$.totalProtein").value(190))
				.andExpect(jsonPath("$.totalCarbs").value(120))
				.andExpect(jsonPath("$.totalSugar").value(60))
				.andExpect(jsonPath("$.totalFat").value(48))
				.andExpect(jsonPath("$.totalSaturatedFat").value(24))
				.andExpect(jsonPath("$.totalSodium").value(600))
				.andExpect(jsonPath("$.totalPotassium").value(1000))
				.andExpect(jsonPath("$.carbRatio").value(28))
				.andExpect(jsonPath("$.proteinRatio").value(47))
				.andExpect(jsonPath("$.fatRatio").value(25))
				.andReturn();
	}
	
	@Test
	public void testAddDaysToWeekInvalidRequestNullBody() throws Exception{
		mockMvc.perform(post("/week/addDaysToWeek/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void testDeleteDaysInWeekValidRequest() throws Exception{
		Week week = new Week();
		Day day = DayMapper.INSTANCE.toDay(dayDto);
		Day dayTwo = DayMapper.INSTANCE.toDay(dayDtoTwo);
		List<Day> daysInitiallyInWeekObject = List.of(day, dayTwo);
		week.setDaysInList(new ArrayList<>(daysInitiallyInWeekObject));
		List<Day> dayToBeRemoved = List.of(day);
		week.getDaysInList().removeAll(new ArrayList<>(dayToBeRemoved));
		calculateStats.calculateTotalWeekStats(week);
		weekDto = WeekMapper.INSTANCE.toWeekDto(week);
		
		when(weekOrchestrator.deleteDaysInWeek(any(Long.class), anyList())).thenReturn(weekDto);		
		
		mockMvc.perform(post("/week/deleteDaysInWeek/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("[1]"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
	}
	
	@Test
	public void testDeleteDaysInWeekInvalidRequestNullBody() throws Exception{
		mockMvc.perform(post("/week/deleteDaysInWeek/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
}
