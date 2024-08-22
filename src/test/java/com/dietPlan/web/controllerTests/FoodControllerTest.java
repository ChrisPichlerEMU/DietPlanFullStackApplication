package com.dietPlan.web.controllerTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dietPlan.infrastructure.service.FoodService;
import com.dietPlan.web.controller.FoodController;
import com.dietPlan.web.dto.FoodDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(FoodController.class)
public class FoodControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private static FoodService foodService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private FoodDto foodDto;
	
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
	}
	
	@AfterAll
	public static void tearDown() {
		reset(foodService);
	}

	@Test
	public void testAddFoodValidRequest() throws Exception{
		when(foodService.addFood(any(FoodDto.class))).thenReturn(foodDto);
			
		mockMvc.perform(post("/food/addFood")
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
		mockMvc.perform(post("/food/addFood")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isBadRequest())		//Null body should return error code 400
				.andReturn();
	}	
	@Test
	public void testGetFoodValidRequest() throws Exception{
		when(foodService.getFoodStats(any(Long.class))).thenReturn(foodDto);
		
		mockMvc.perform(get("/food/getFood/1")
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
	public void testEditFoodValidRequest() throws Exception{
		when(foodService.editFood(any(Long.class), any(FoodDto.class))).thenReturn(foodDto);
		
		mockMvc.perform(put("/food/editFood/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(foodDto)))
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
	public void testEditFoodInvalidRequestNullBody() throws Exception{
		mockMvc.perform(put("/food/editFood/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void testDeleteFood() throws Exception{
		foodDto.setDeleted(true);
		
		when(foodService.deleteFood(any(Long.class))).thenReturn(foodDto);
		
		mockMvc.perform(delete("/food/deleteFood/1")
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
}
