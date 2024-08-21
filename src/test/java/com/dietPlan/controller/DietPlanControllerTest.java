package com.dietPlan.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.dietPlan.Repositories.FoodRepository;
import com.dietPlan.dto.DayDto;
import com.dietPlan.dto.FoodDto;
import com.dietPlan.dto.WeekDto;
import com.dietPlan.mappers.DayMapper;
import com.dietPlan.mappers.FoodMapper;
import com.dietPlan.mappers.WeekMapper;
import com.dietPlan.models.Day;
import com.dietPlan.models.Food;
import com.dietPlan.models.Week;
import com.dietPlan.service.CalculateStats;
import com.dietPlan.service.DietService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DietPlanController.class)
public class DietPlanControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private static DietService dietService;
	
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
		reset(dietService);
	}
	
	@Test
	public void testAddFoodValidRequest() throws Exception{
		when(dietService.addFood(any(FoodDto.class))).thenReturn(foodDto);
			
		mockMvc.perform(post("/addFood")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(foodDto)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("Protein bar"))
				.andExpect(jsonPath("$.calories").value(400))
				.andExpect(jsonPath("$.protein").value(30))
				.andExpect(jsonPath("$.carbs").value(40))
				.andExpect(jsonPath("$.sugar").value(20))
				.andExpect(jsonPath("$.fat").value(13))
				.andExpect(jsonPath("$.saturatedFat").value(8))
				.andExpect(jsonPath("$.sodium").value(200))
				.andExpect(jsonPath("$.potassium").value(150))
				.andExpect(jsonPath("$.multiplier").value(1.0))
				.andReturn();	
	}
	
	@Test
	public void testAddFoodInvalidRequestNullBody() throws Exception{
		mockMvc.perform(post("/addFood")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isBadRequest())		//Null body should return error code 400
				.andReturn();
	}
	
	@Test
	public void testAddDayValidRequest() throws Exception{
		when(dietService.addDay(any(DayDto.class))).thenReturn(dayDto);
		
		mockMvc.perform(post("/addDay")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dayDto)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalCalories").value(500))
				.andExpect(jsonPath("$.totalProtein").value(50))
				.andExpect(jsonPath("$.totalCarbs").value(50))
				.andExpect(jsonPath("$.totalSugar").value(25))
				.andExpect(jsonPath("$.totalFat").value(9))
				.andExpect(jsonPath("$.totalSaturatedFat").value(5))
				.andExpect(jsonPath("$.totalSodium").value(500))
				.andExpect(jsonPath("$.totalPotassium").value(600))
				.andExpect(jsonPath("$.carbRatio").value(40))
				.andExpect(jsonPath("$.proteinRatio").value(40))
				.andExpect(jsonPath("$.fatRatio").value(20))
				.andReturn();
	}
	
	@Test
	public void testAddDayInvalidRequestNullBody() throws Exception{
		mockMvc.perform(post("/addDay")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isBadRequest())		//Null body should return error code 400
				.andReturn();
	}
	
	@Test
	public void testAddWeekValidRequest() throws Exception{
		when(dietService.addWeek(any(WeekDto.class))).thenReturn(weekDto);
		
		mockMvc.perform(post("/addWeek")
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
		mockMvc.perform(post("/addWeek")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
			.andExpect(status().isBadRequest())		//Null body should return error code 400 
			.andReturn();
	}
	
	@Test
	public void testAddFoodsToDayValidRequest() throws Exception{
		dayDto.setFoods(List.of(foodDto, foodDtoTwo));
		
		Day dayDtotoDay = DayMapper.INSTANCE.toDay(dayDto);
		calculateStats.calculateTotalDayStats(dayDtotoDay);		//Make sure calculateTotalDayStats() is called on dayDto to get correct total stats returned
		dayDto = DayMapper.INSTANCE.toDayDto(dayDtotoDay);
		
		when(dietService.addFoodsToDay(any(Long.class), anyList())).thenReturn(dayDto);		
		
		mockMvc.perform(post("/addFoodsToDay/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("[1]"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.foods[0].name").value("Protein bar"))
				.andExpect(jsonPath("$.foods[0].calories").value(400))
				.andExpect(jsonPath("$.foods[0].protein").value(30))
				.andExpect(jsonPath("$.foods[0].carbs").value(40))
				.andExpect(jsonPath("$.foods[0].sugar").value(20))
				.andExpect(jsonPath("$.foods[0].fat").value(13))
				.andExpect(jsonPath("$.foods[0].saturatedFat").value(8))
				.andExpect(jsonPath("$.foods[0].sodium").value(200))
				.andExpect(jsonPath("$.foods[0].potassium").value(150))
				.andExpect(jsonPath("$.foods[1].name").value("Chicken breast"))
				.andExpect(jsonPath("$.foods[1].calories").value(500))
				.andExpect(jsonPath("$.foods[1].protein").value(100))
				.andExpect(jsonPath("$.foods[1].carbs").value(0))
				.andExpect(jsonPath("$.foods[1].sugar").value(0))
				.andExpect(jsonPath("$.foods[1].fat").value(9))
				.andExpect(jsonPath("$.foods[1].saturatedFat").value(0))
				.andExpect(jsonPath("$.foods[1].sodium").value(0))
				.andExpect(jsonPath("$.foods[1].potassium").value(550))
				.andExpect(jsonPath("$.totalCalories").value(900))
				.andExpect(jsonPath("$.totalProtein").value(130))
				.andExpect(jsonPath("$.totalCarbs").value(40))
				.andExpect(jsonPath("$.totalSugar").value(20))
				.andExpect(jsonPath("$.totalFat").value(22))
				.andExpect(jsonPath("$.totalSaturatedFat").value(8))
				.andExpect(jsonPath("$.totalSodium").value(200))
				.andExpect(jsonPath("$.totalPotassium").value(700))
				.andExpect(jsonPath("$.carbRatio").value(18))
				.andExpect(jsonPath("$.proteinRatio").value(60))
				.andExpect(jsonPath("$.fatRatio").value(22))
				.andReturn();
	}
	
	@Test
	public void testAddFoodsInvalidRequestNullBody() throws Exception{
		mockMvc.perform(post("/addFoodsToDay/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isBadRequest())
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
		
		when(dietService.addDaysToWeek(any(Long.class), anyList())).thenReturn(weekDto);		
		
		mockMvc.perform(post("/addDaysToWeek/1")
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
		mockMvc.perform(post("/addDaysToWeek/1")
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
		
		when(dietService.deleteDaysInWeek(any(Long.class), anyList())).thenReturn(weekDto);		
		
		mockMvc.perform(post("/deleteDaysInWeek/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("[1]"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
	}
	
	@Test
	public void testDeleteDaysInWeekInvalidRequestNullBody() throws Exception{
		mockMvc.perform(post("/deleteDaysInWeek/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void testDeleteFoodsInDayValidRequest() throws Exception{
		Food food = FoodMapper.INSTANCE.toFood(foodDto);
		Food foodTwo = FoodMapper.INSTANCE.toFood(foodDtoTwo);
		Day day = DayMapper.INSTANCE.toDay(dayDto);
		List<Food> foodsInitiallyInDayObject = List.of(food, food, foodTwo, foodTwo);
		day.setFoods(new ArrayList<>(foodsInitiallyInDayObject));
		List<Food> foodsToBeDeletedByServiceMethod = List.of(food);
		day.getFoods().removeAll(new ArrayList<>(foodsToBeDeletedByServiceMethod));
		calculateStats.calculateTotalDayStats(day);		
		dayDto = DayMapper.INSTANCE.toDayDto(day);
		
		when(dietService.deleteFoodsInDay(any(Long.class), anyList())).thenReturn(dayDto);		
		
		mockMvc.perform(post("/deleteFoodsInDay/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("[1]"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.foods[0].name").value("Chicken breast"))
				.andExpect(jsonPath("$.foods[0].calories").value(500))
				.andExpect(jsonPath("$.foods[0].protein").value(100))
				.andExpect(jsonPath("$.foods[0].carbs").value(0))
				.andExpect(jsonPath("$.foods[0].sugar").value(0))
				.andExpect(jsonPath("$.foods[0].fat").value(9))
				.andExpect(jsonPath("$.foods[0].saturatedFat").value(0))
				.andExpect(jsonPath("$.foods[0].sodium").value(0))
				.andExpect(jsonPath("$.foods[0].potassium").value(550))
				.andExpect(jsonPath("$.foods[1].name").value("Chicken breast"))
				.andExpect(jsonPath("$.foods[1].calories").value(500))
				.andExpect(jsonPath("$.foods[1].protein").value(100))
				.andExpect(jsonPath("$.foods[1].carbs").value(0))
				.andExpect(jsonPath("$.foods[1].sugar").value(0))
				.andExpect(jsonPath("$.foods[1].fat").value(9))
				.andExpect(jsonPath("$.foods[1].saturatedFat").value(0))
				.andExpect(jsonPath("$.foods[1].sodium").value(0))
				.andExpect(jsonPath("$.foods[1].potassium").value(550))
				.andExpect(jsonPath("$.totalCalories").value(1000))
				.andExpect(jsonPath("$.totalProtein").value(200))
				.andExpect(jsonPath("$.totalCarbs").value(0))
				.andExpect(jsonPath("$.totalSugar").value(0))
				.andExpect(jsonPath("$.totalFat").value(18))
				.andExpect(jsonPath("$.totalSaturatedFat").value(0))
				.andExpect(jsonPath("$.totalSodium").value(0))
				.andExpect(jsonPath("$.totalPotassium").value(1100))
				.andExpect(jsonPath("$.carbRatio").value(0))
				.andExpect(jsonPath("$.proteinRatio").value(84))
				.andExpect(jsonPath("$.fatRatio").value(16))
				.andReturn();
	}
	
	@Test
	public void testDeleteFoodsInDayInvalidRequestNullBody() throws Exception{
		mockMvc.perform(post("/deleteFoodsInDay/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void testGetFoodValidRequest() throws Exception{
		when(dietService.getFoodStats(any(Long.class))).thenReturn(foodDto);
		
		mockMvc.perform(get("/getFood/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("Protein bar"))
				.andExpect(jsonPath("$.calories").value(400))
				.andExpect(jsonPath("$.protein").value(30))
				.andExpect(jsonPath("$.carbs").value(40))
				.andExpect(jsonPath("$.sugar").value(20))
				.andExpect(jsonPath("$.fat").value(13))
				.andExpect(jsonPath("$.saturatedFat").value(8))
				.andExpect(jsonPath("$.sodium").value(200))
				.andExpect(jsonPath("$.potassium").value(150))
				.andExpect(jsonPath("$.multiplier").value(1.0))
				.andReturn();	
	}
	
	@Test
	public void testGetDayValidRequest() throws Exception{
		when(dietService.getDayStats(any(Long.class))).thenReturn(dayDto);
		
		mockMvc.perform(put("/getDay/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalCalories").value(500))
				.andExpect(jsonPath("$.totalProtein").value(50))
				.andExpect(jsonPath("$.totalCarbs").value(50))
				.andExpect(jsonPath("$.totalSugar").value(25))
				.andExpect(jsonPath("$.totalFat").value(9))
				.andExpect(jsonPath("$.totalSaturatedFat").value(5))
				.andExpect(jsonPath("$.totalSodium").value(500))
				.andExpect(jsonPath("$.totalPotassium").value(600))
				.andExpect(jsonPath("$.carbRatio").value(40))
				.andExpect(jsonPath("$.proteinRatio").value(40))
				.andExpect(jsonPath("$.fatRatio").value(20))
				.andReturn();
	}
	
	@Test
	public void testGetWeekValidRequest() throws Exception{
		when(dietService.getWeekStats(any(Long.class))).thenReturn(weekDto);
		
		mockMvc.perform(put("/getWeek/1")
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
	public void testEditFoodValidRequest() throws Exception{
		when(dietService.editFood(any(Long.class), any(FoodDto.class))).thenReturn(foodDtoTwo);
		
		mockMvc.perform(put("/editFood/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(foodDto)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("Chicken breast"))
				.andExpect(jsonPath("$.calories").value(500))
				.andExpect(jsonPath("$.protein").value(100))
				.andExpect(jsonPath("$.carbs").value(0))
				.andExpect(jsonPath("$.sugar").value(0))
				.andExpect(jsonPath("$.fat").value(9))
				.andExpect(jsonPath("$.saturatedFat").value(0))
				.andExpect(jsonPath("$.sodium").value(0))
				.andExpect(jsonPath("$.potassium").value(550))
				.andExpect(jsonPath("$.multiplier").value(1.0))
				.andReturn();	
	}
	
	@Test
	public void testEditFoodInvalidRequestNullBody() throws Exception{
		mockMvc.perform(put("/editFood/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void testDeleteFood() throws Exception{
		foodDto.setDeleted(true);
		
		when(dietService.deleteFood(any(Long.class))).thenReturn(foodDto);
		
		mockMvc.perform(delete("/deleteFood/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("Protein bar"))
				.andExpect(jsonPath("$.calories").value(400))
				.andExpect(jsonPath("$.protein").value(30))
				.andExpect(jsonPath("$.carbs").value(40))
				.andExpect(jsonPath("$.sugar").value(20))
				.andExpect(jsonPath("$.fat").value(13))
				.andExpect(jsonPath("$.saturatedFat").value(8))
				.andExpect(jsonPath("$.sodium").value(200))
				.andExpect(jsonPath("$.potassium").value(150))
				.andExpect(jsonPath("$.multiplier").value(1.0))
				.andExpect(jsonPath("$.deleted").value(true))
				.andReturn();	
	}
	
	@Test
	public void testDeleteDay() throws Exception{
		dayDto.setDeleted(true);
		
		when(dietService.deleteDay(any(Long.class))).thenReturn(dayDto);
		
		mockMvc.perform(delete("/deleteDay/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalCalories").value(500))
				.andExpect(jsonPath("$.totalProtein").value(50))
				.andExpect(jsonPath("$.totalCarbs").value(50))
				.andExpect(jsonPath("$.totalSugar").value(25))
				.andExpect(jsonPath("$.totalFat").value(9))
				.andExpect(jsonPath("$.totalSaturatedFat").value(5))
				.andExpect(jsonPath("$.totalSodium").value(500))
				.andExpect(jsonPath("$.totalPotassium").value(600))
				.andExpect(jsonPath("$.carbRatio").value(40))
				.andExpect(jsonPath("$.proteinRatio").value(40))
				.andExpect(jsonPath("$.fatRatio").value(20))
				.andReturn();
	}
	
	@Test
	public void testDeleteWeek() throws Exception{
		weekDto.setDeleted(true);
		
		when(dietService.deleteWeek(any(Long.class))).thenReturn(weekDto);
		
		mockMvc.perform(delete("/deleteWeek/1")
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
}
