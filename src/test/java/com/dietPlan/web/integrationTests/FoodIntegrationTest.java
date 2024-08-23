package com.dietPlan.web.integrationTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.fasterxml.jackson.databind.ObjectMapper;

import com.dietPlan.domain.mapper.FoodMapper;
import com.dietPlan.domain.model.Food;
import com.dietPlan.domain.repository.FoodRepository;
import com.dietPlan.web.controller.FoodController;
import com.dietPlan.web.dto.FoodDto;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FoodIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private FoodRepository foodRepository;
	
	private FoodDto foodDto;
	private Food food;
	
	@BeforeEach
	public void setUp() {
		foodDto = new FoodDto();
		foodDto.setName("Pork loin - TESTOBJECT");
		foodDto.setCalories(400);
		foodDto.setProtein(80);
		foodDto.setCarbs(0);
		foodDto.setSugar(0);
		foodDto.setFat(9);
		foodDto.setSaturatedFat(2);
		foodDto.setSodium(50);
		foodDto.setPotassium(400);
		foodDto.setMultiplier(0.6);
		foodDto.setDeleted(false);
		
		food = FoodMapper.INSTANCE.toFood(foodDto);
	}
	
	@Test
	public void testAddFoodIntegrationValid() throws Exception{
		MvcResult result = mockMvc.perform(post("/food/addFood")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJsonString(foodDto)))
				.andExpect(status().isCreated())
				.andReturn();
		
		Optional<Food> savedFood = foodRepository.findByName("Pork loin - TESTOBJECT");
		Food savedFoodModel = savedFood.get();
		
		assertThat(result).isNotNull();
		assertThat(savedFood).isNotNull();
		assertThat(savedFood).isPresent();
		assertThat(savedFoodModel.getProtein()).isEqualTo(80);
		assertThat(savedFoodModel.getMultiplier()).isEqualTo(0.6);
	}
	
	@Test
	public void testAddFoodIntegrationInvalidEmptyBody() throws Exception{
		mockMvc.perform(post("/food/addFood")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void testGetFoodIntegrationValid() throws Exception{
		food = foodRepository.save(food);

		MvcResult result = mockMvc.perform(get("/food/getFood/{id}", food.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(food.getId()))
				.andExpect(jsonPath("$.calories").value(400))
				.andExpect(jsonPath("$.saturatedFat").value(2))
				.andReturn();
		
		assertThat(result).isNotNull();
	}
	
	@Test
	public void testGetFoodIntegrationInvalidNotInDatabase() throws Exception{
		food = foodRepository.save(food);
		
		mockMvc.perform(get("/food/getFood/{id}", food.getId() + 1L))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("An unexpected error has occured. Message: Food object not found in database in getFoodStats method with id = " + (food.getId() + 1L)));
	}
	
	@Test
	public void testEditFoodIntegrationValid() throws Exception{
		food = foodRepository.save(food);
		
		foodDto.setCalories(600);
		foodDto.setMultiplier(0.9);
		
		MvcResult result = mockMvc.perform(put("/food/editFood/{id}", food.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJsonString(foodDto)))
				.andExpect(status().isOk())
				.andReturn();
		
		Optional<Food> editedFood = foodRepository.findByName("Pork loin - TESTOBJECT");
		Food editedFoodModel = editedFood.get();
		
		assertThat(result).isNotNull();
		assertThat(editedFood).isNotNull();
		assertThat(editedFood).isPresent();
		assertThat(editedFoodModel.getProtein()).isEqualTo(80);
		assertThat(editedFoodModel.getCalories()).isEqualTo(600);
		assertThat(editedFoodModel.getMultiplier()).isEqualTo(0.9);
	}
	
	@Test
	public void testEditFoodIntegrationInvalidNotInDatabase() throws Exception{
		food = foodRepository.save(food);
		
		mockMvc.perform(put("/food/editFood/{id}", food.getId() + 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJsonString(foodDto)))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("An unexpected error has occured. Message: Food object not found in database in editFood method with id = " + (food.getId() + 1L)));
	}
	
	@Test
	public void testEditFoodIntegrationInvalidEmptyBody() throws Exception{
		food = foodRepository.save(food);
		
		mockMvc.perform(put("/food/editFood/{id}", food.getId() + 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("An unexpected error has occured. Message: Required request body is missing: public org.springframework.http.ResponseEntity<com.dietPlan.web.dto.FoodDto> com.dietPlan.web.controller.FoodController.editFood(java.lang.Long,com.dietPlan.web.dto.FoodDto)"));
	}
	
	@Test
	public void testDeleteFoodIntegrationValid() throws Exception{
		food = foodRepository.save(food);

		mockMvc.perform(delete("/food/deleteFood/{id}", food.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(food.getId()))
				.andExpect(jsonPath("$.calories").value(400))
				.andExpect(jsonPath("$.deleted").value(true));
	}
	
	@Test 
	public void testDeleteFoodIntegrationInvalidNotInDatabase() throws Exception{
		food = foodRepository.save(food);
		
		mockMvc.perform(delete("/food/deleteFood/{id}", food.getId() + 1L))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("An unexpected error has occured. Message: Food object not found in database in deleteFood method with id = " + (food.getId() + 1L)));
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
