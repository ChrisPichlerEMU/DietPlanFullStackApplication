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
import com.dietPlan.domain.mapper.FoodMapper;
import com.dietPlan.domain.model.Day;
import com.dietPlan.domain.model.Food;
import com.dietPlan.infrastructure.orchestrator.DayOrchestrator;
import com.dietPlan.infrastructure.service.CalculateStats;
import com.dietPlan.infrastructure.service.DayService;
import com.dietPlan.web.controller.DayController;
import com.dietPlan.web.dto.DayDto;
import com.dietPlan.web.dto.FoodDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DayController.class)
public class DayControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private static DayService dayService;
	
	@MockBean
	private static DayOrchestrator dayOrchestrator;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private FoodDto foodDto;
	private FoodDto foodDtoTwo;
	private DayDto dayDto;
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
	}
	
	@AfterAll
	public static void tearDown() {
		reset(dayService);
		reset(dayOrchestrator);
	}
	
	@Test
	public void testAddDayValidRequest() throws Exception{
		when(dayService.addDay(any(DayDto.class))).thenReturn(dayDto);
		
		mockMvc.perform(post("/day/addDay")
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
	public void testAddDayInvalidRequestEmptyBody() throws Exception{
		mockMvc.perform(post("/day/addDay")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isInternalServerError())
				.andReturn();
	}
	
	@Test
	public void testGetDayValidRequest() throws Exception{
		when(dayOrchestrator.getDayStats(any(Long.class))).thenReturn(dayDto);
		
		mockMvc.perform(put("/day/getDay/1")
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
	public void testDeleteDay() throws Exception{
		dayDto.setDeleted(true);
		
		when(dayService.deleteDay(any(Long.class))).thenReturn(dayDto);
		
		mockMvc.perform(delete("/day/deleteDay/1")
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
	public void testAddFoodsToDayValidRequest() throws Exception{
		dayDto.setFoods(List.of(foodDto, foodDtoTwo));
		
		Day dayDtotoDay = DayMapper.INSTANCE.toDay(dayDto);
		calculateStats.calculateTotalDayStats(dayDtotoDay);		//Make sure calculateTotalDayStats() is called on dayDto to get correct total stats returned
		dayDto = DayMapper.INSTANCE.toDayDto(dayDtotoDay);
		
		when(dayOrchestrator.addFoodsToDay(any(Long.class), anyList())).thenReturn(dayDto);		
		
		mockMvc.perform(post("/day/addFoodsToDay/1")
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
	public void testAddFoodsToDayInvalidRequestEmptyBody() throws Exception{
		mockMvc.perform(post("/day/addFoodsToDay/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isInternalServerError())
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
		
		when(dayOrchestrator.deleteFoodsInDay(any(Long.class), anyList())).thenReturn(dayDto);		
		
		mockMvc.perform(post("/day/deleteFoodsInDay/1")
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
	public void testDeleteFoodsInDayInvalidRequestEmptyBody() throws Exception{
		mockMvc.perform(post("/day/deleteFoodsInDay/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isInternalServerError())
				.andReturn();
	}
}
