package com.dietPlan.web.integrationTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.dietPlan.domain.mapper.DayMapper;
import com.dietPlan.domain.mapper.FoodMapper;
import com.dietPlan.domain.model.Day;
import com.dietPlan.domain.model.Food;
import com.dietPlan.domain.repository.DayRepository;
import com.dietPlan.domain.repository.FoodRepository;
import com.dietPlan.web.dto.DayDto;
import com.dietPlan.web.dto.FoodDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DayIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private DayRepository dayRepository;
	
	@Autowired
	private FoodRepository foodRepository;
	
	private DayDto dayDto;
	private DayDto dayDtoTwo;
	private Day day;
	private Day dayTwo;
	private FoodDto foodDto;
	private FoodDto foodDtoTwo;
	private Food food;
	private Food foodTwo;
	
	@BeforeEach
	public void setUp() {
		foodDto = new FoodDto();
		foodDto.setName("Pork loin");
		foodDto.setCalories(400);
		foodDto.setProtein(80);
		foodDto.setCarbs(0);
		foodDto.setSugar(0);
		foodDto.setFat(9);
		foodDto.setSaturatedFat(2);
		foodDto.setSodium(50);
		foodDto.setPotassium(400);
		foodDto.setMultiplier(0.5);
		foodDto.setDeleted(false);
		
		food = FoodMapper.INSTANCE.toFood(foodDto);
				
		foodDtoTwo = new FoodDto();
		foodDtoTwo.setName("Granola bar");
		foodDtoTwo.setCalories(250);
		foodDtoTwo.setProtein(25);
		foodDtoTwo.setCarbs(25);
		foodDtoTwo.setSugar(15);
		foodDtoTwo.setFat(4);
		foodDtoTwo.setSaturatedFat(2);
		foodDtoTwo.setSodium(250);
		foodDtoTwo.setPotassium(300);
		foodDtoTwo.setMultiplier(1.0);
		foodDtoTwo.setDeleted(false); 
		
		foodTwo = FoodMapper.INSTANCE.toFood(foodDtoTwo);
		
		dayDto = new DayDto();
		
		day = new Day();
		
		dayDtoTwo = new DayDto();
		
		dayTwo = new Day();
	}
	
	@Test
	public void testAddDayIntegrationValid() throws Exception{
		MvcResult result = mockMvc.perform(post("/day/addDay")
								  .contentType(MediaType.APPLICATION_JSON)
								  .content(toJsonString(dayDto)))
								  .andExpect(status().isCreated())
								  .andReturn();
		
		String response = result.getResponse().getContentAsString();
		DayDto savedDayDto = objectMapper.readValue(response, DayDto.class);
		Optional<Day> savedDay = dayRepository.findById(savedDayDto.getId());
		Day savedDayModel = savedDay.get();
		
		assertThat(savedDay).isNotNull();
		assertThat(savedDay).isPresent();
		assertThat(savedDayModel.getTotalCalories()).isEqualTo(0);
	}
	
	@Test
	public void testAddDayIntegrationInvalidEmptyBody() throws Exception{
		mockMvc.perform(post("/day/addDay")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void testDeleteDayIntegrationValid() throws Exception{
		day = dayRepository.save(day);

		MvcResult result = mockMvc.perform(delete("/day/deleteDay/{id}", day.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(day.getId()))
				.andExpect(jsonPath("$.deleted").value(true))
				.andReturn();
		
		assertThat(result).isNotNull();
	}
	
	@Test 
	public void testDeleteDayIntegrationInvalidNotInDatabase() throws Exception{
		day = dayRepository.save(day);
		
		mockMvc.perform(delete("/day/deleteDay/{id}", day.getId() + 1L))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("An unexpected error has occured. Message: Day object not found in database in deleteDay method with id = " + (day.getId() + 1L)));
	}
	
	@Test
	public void testGetDayIntegrationValid() throws Exception{
		day = dayRepository.save(day);
		Food foodAdded = foodRepository.save(food);
		
		mockMvc.perform(post("/day/addFoodsToDay/{id}", day.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content("[" + (foodAdded.getId() + "") + ", " + (foodAdded.getId() + "") + "]"));				

		MvcResult result = mockMvc.perform(put("/day/getDay/{id}", day.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(day.getId()))
				.andExpect(jsonPath("$.totalCalories").value(400))
				.andExpect(jsonPath("$.totalProtein").value(80))
				.andExpect(jsonPath("$.totalCarbs").value(0))
				.andExpect(jsonPath("$.totalSugar").value(0))
				.andExpect(jsonPath("$.totalFat").value(8))
				.andExpect(jsonPath("$.totalSaturatedFat").value(2))
				.andExpect(jsonPath("$.totalSodium").value(50))
				.andExpect(jsonPath("$.totalPotassium").value(400))
				.andExpect(jsonPath("$.deleted").value(false))
				.andReturn();
		
		assertThat(result).isNotNull();
	}
	
	@Test
	public void testGetDayIntegrationInvalidNotInDatabase() throws Exception{
		day = dayRepository.save(day);
		
		mockMvc.perform(put("/day/getDay/{id}", day.getId() + 1L))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("An unexpected error has occured. Message: Day object not found in database in getDayStats method with id = " + (day.getId() + 1L)));
	}
	
	@Test
	public void testAddFoodsToDayIntegrationValid() throws Exception{
		dayTwo = dayRepository.save(dayTwo);
		Food foodAdded = foodRepository.save(food);
		
		MvcResult result = mockMvc.perform(post("/day/addFoodsToDay/{id}", dayTwo.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content("[" + (foodAdded.getId() + "") + "]"))
				.andExpect(status().isCreated())
				.andReturn();
		
		String response = result.getResponse().getContentAsString();
		DayDto savedDayDto = objectMapper.readValue(response, DayDto.class);
		Optional<Day> savedDay = dayRepository.findById(savedDayDto.getId());
		Day savedDayModel = savedDay.get();
		
		assertThat(result).isNotNull();
		assertThat(savedDayModel.getFoods().size()).isEqualTo(1);
		assertThat(savedDayModel.getFoods().get(0).getId()).isEqualTo(foodAdded.getId());
	}
	
	@Test
	public void testAddFoodsToDayInvalidDayNotInDatabase() throws Exception{
		day = dayRepository.save(day);
		food = foodRepository.save(food);
		
		mockMvc.perform(post("/day/addFoodsToDay/{id}", day.getId() + 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content("[" + (food.getId() + "") + "]"))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("An unexpected error has occured. Message: Day object not found in database in addFoodsToDay method with id = " + (day.getId() + 1L)));
				
	}
	
	@Test
	public void testAddFoodsToDayInvalidFoodNotInDatabase() throws Exception{
		day = dayRepository.save(day);
		food = foodRepository.save(food);
		
		mockMvc.perform(post("/day/addFoodsToDay/{id}", day.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content("[" + ((food.getId() + 1L) + "") + "]"))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("An unexpected error has occured. Message: Food object not found in database in addFoodsToDay method with id = " + (food.getId() + 1L)));
	}
	
	@Test
	public void testAddFoodsToDayInvalidEmptyBody() throws Exception{
		day = dayRepository.save(day);
		
		mockMvc.perform(post("/day/addFoodsToDay/{id}", day.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("An unexpected error has occured. Message: Required request body is missing: public org.springframework.http.ResponseEntity<com.dietPlan.web.dto.DayDto> com.dietPlan.web.controller.DayController.addFoodsToDay(java.lang.Long,java.util.List<java.lang.Long>)"));
	}
		
	@Test
	public void testDeleteFoodsInDayIntegrationValid() throws Exception{
		dayTwo = dayRepository.save(dayTwo);
		Food foodAdded = foodRepository.save(food);
		
		mockMvc.perform(post("/day/addFoodsToDay/{id}", dayTwo.getId())			
				.contentType(MediaType.APPLICATION_JSON)
				.content("[" + (foodAdded.getId() + "") + "]"))
				.andExpect(status().isCreated())
				.andReturn();
		
		MvcResult result = mockMvc.perform(post("/day/deleteFoodsInDay/{id}", dayTwo.getId())			
				.contentType(MediaType.APPLICATION_JSON)
				.content("[" + (foodAdded.getId() + "") + "]"))
				.andExpect(status().isCreated())
				.andReturn();
		
		String response = result.getResponse().getContentAsString();
		DayDto savedDayDto = objectMapper.readValue(response, DayDto.class);
		Optional<Day> savedDay = dayRepository.findById(savedDayDto.getId());
		Day savedDayModel = savedDay.get();
		
		assertThat(result).isNotNull();
		assertThat(savedDayModel.getFoods().size()).isEqualTo(0);
	}
	
	@Test
	public void testDeleteFoodsInDayIntegrationInvalidFoodNotInDaysList() throws Exception{
		dayTwo = dayRepository.save(dayTwo);
		food = foodRepository.save(food);
		
		MvcResult result = mockMvc.perform(post("/day/deleteFoodsInDay/{id}", dayTwo.getId())	
				.contentType(MediaType.APPLICATION_JSON)
				.content("[" + (food.getId() + "") + "]"))
				.andExpect(status().isCreated())
				.andReturn();
		
		String response = result.getResponse().getContentAsString();
		DayDto savedDayDto = objectMapper.readValue(response, DayDto.class);
		Optional<Day> savedDay = dayRepository.findById(savedDayDto.getId());
		Day savedDayModel = savedDay.get();
		
		assertThat(result).isNotNull();
		assertThat(savedDayModel.getFoods().size()).isEqualTo(0);
	}
	
	@Test
	public void testDeleteFoodsInDayInvalidDayNotInDatabase() throws Exception{
		day = dayRepository.save(day);
		Food foodAdded = foodRepository.save(food);
		
		mockMvc.perform(post("/day/deleteFoodsInDay/{id}", day.getId() + 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content("[" + (foodAdded.getId() + "") + "]"))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("An unexpected error has occured. Message: Day object not found in database in deleteFoodsInDay method with id = " + (day.getId() + 1L)));
				
	}
	
	@Test
	public void testDeleteFoodsInDayInvalidFoodNotInDatabase() throws Exception{
		day = dayRepository.save(day);
		Food foodAdded = foodRepository.save(food);
		
		mockMvc.perform(post("/day/deleteFoodsInDay/{id}", day.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content("[" + ((foodAdded.getId() + 1L) + "") + "]"))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("An unexpected error has occured. Message: Food object not found in database in deleteFoodsInDay method with id = " + (food.getId() + 1L)));
	}
	
	@Test
	public void testDeleteFoodsInDayInvalidEmptyBody() throws Exception{
		day = dayRepository.save(day);
		
		mockMvc.perform(post("/day/deleteFoodsInDay/{id}", day.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("An unexpected error has occured. Message: Required request body is missing: public org.springframework.http.ResponseEntity<com.dietPlan.web.dto.DayDto> com.dietPlan.web.controller.DayController.deleteFoodsInDay(java.lang.Long,java.util.List<java.lang.Long>)"));
	}
	
	private static String toJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		}
		catch(Exception e) {
			System.out.println("An exception was caught in the toJsonString() method in the FoodIntegrationTest class. Message: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

}

//addDay, getDay, deleteDay, addFoodsToDay, deleteFoodsInday
