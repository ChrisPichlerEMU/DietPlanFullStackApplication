package com.dietPlan.web.integrationTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.dietPlan.domain.mapper.WeekMapper;
import com.dietPlan.domain.model.Day;
import com.dietPlan.domain.model.Food;
import com.dietPlan.domain.model.Week;
import com.dietPlan.domain.repository.DayRepository;
import com.dietPlan.domain.repository.FoodRepository;
import com.dietPlan.domain.repository.WeekRepository;
import com.dietPlan.web.controller.DayController;
import com.dietPlan.web.controller.WeekController;
import com.dietPlan.web.dto.DayDto;
import com.dietPlan.web.dto.FoodDto;
import com.dietPlan.web.dto.WeekDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class WeekIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WeekRepository weekRepository;
	
	@Autowired
	private DayRepository dayRepository;
	
	@Autowired
	private FoodRepository foodRepository;
	
	private WeekDto weekDto;
	private WeekDto weekDtoTwo;
	private Week week;
	private Week weekTwo;
	private DayDto dayDto;
	private DayDto dayDtoTwo;
	private DayDto dayDtoThree;
	private FoodDto foodDto;
	private FoodDto foodDtoTwo;
	
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
		
		dayDto = new DayDto();
		List<FoodDto> foodList = List.of(foodDto, foodDtoTwo);
		dayDto.setFoods(new ArrayList<>(foodList));
		
		dayDtoTwo = new DayDto();
		List<FoodDto> foodListTwo = List.of(foodDto, foodDtoTwo, foodDtoTwo);
		dayDtoTwo.setFoods(new ArrayList<>(foodListTwo));
		
		dayDtoThree = new DayDto();
		
		weekDto = new WeekDto();
		weekDto.setTotalCalories(1150);	
		weekDto.setTotalSodium(800);
		List<DayDto> dayList = List.of(dayDto, dayDtoTwo);
		weekDto.setDaysInList(new ArrayList<>(dayList));
		
		weekDtoTwo = new WeekDto();
		weekDtoTwo.setDaysInList(new ArrayList<>());
		
		week = WeekMapper.INSTANCE.toWeek(weekDto);
		weekTwo = WeekMapper.INSTANCE.toWeek(weekDtoTwo);
	}
	
	@Test
	public void testAddWeekIntegrationValid() throws Exception{
		MvcResult result = mockMvc.perform(post("/week/addWeek")
								  .contentType(MediaType.APPLICATION_JSON)
								  .content(toJsonString(weekDto)))
								  .andExpect(status().isCreated())
								  .andReturn();
		
		String response = result.getResponse().getContentAsString();
		WeekDto savedWeekDto = objectMapper.readValue(response, WeekDto.class);
		Optional<Week> savedWeek = weekRepository.findById(savedWeekDto.getId());
		Week savedWeekModel = savedWeek.get();
		
		assertThat(savedWeek).isNotNull();
		assertThat(savedWeek).isPresent();
		assertThat(savedWeekModel.getTotalCalories()).isEqualTo(1150);
		assertThat(savedWeekModel.getTotalSodium()).isEqualTo(800);
	}
	
	@Test
	public void testAddWeekIntegrationInvalidEmptyBody() throws Exception{
		mockMvc.perform(post("/week/addWeek")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void testDeleteWeekIntegrationValid() throws Exception{
		week = weekRepository.save(week);

		MvcResult result = mockMvc.perform(delete("/week/deleteWeek/{id}", week.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(week.getId()))
				.andExpect(jsonPath("$.totalCalories").value(1150))
				.andExpect(jsonPath("$.deleted").value(true))
				.andReturn();
		
		assertThat(result).isNotNull();
	}
	
	@Test 
	public void testDeleteWeekIntegrationInvalidNotInDatabase() throws Exception{
		week = weekRepository.save(week);
		
		mockMvc.perform(delete("/week/deleteWeek/{id}", week.getId() + 1L))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("An unexpected error has occured. Message: Week object not found in database in deleteWeek method with id = " + (week.getId() + 1L)));
	}
	
	@Test
	public void testGetWeekIntegrationValid() throws Exception{
		week = weekRepository.save(week);

		MvcResult result = mockMvc.perform(put("/week/getWeek/{id}", week.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(week.getId()))
				.andExpect(jsonPath("$.totalFat").value(20))
				.andExpect(jsonPath("$.totalPotassium").value(1300))
				.andReturn();
		
		assertThat(result).isNotNull();
	}
	
	@Test
	public void testGetWeekIntegrationInvalidNotInDatabase() throws Exception{
		week = weekRepository.save(week);
		
		mockMvc.perform(put("/week/getWeek/{id}", week.getId() + 1L))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("An unexpected error has occured. Message: Week object not found in database in getWeekStats method with id = " + (week.getId() + 1L)));
	}
	
	@Test
	public void testAddDaysToWeekIntegrationValid() throws Exception{
		weekTwo = weekRepository.save(weekTwo);
		Day dayAdded = DayMapper.INSTANCE.toDay(dayDtoThree);
		dayAdded.setFoods(List.of());
		dayAdded = dayRepository.save(dayAdded);
		
		MvcResult result = mockMvc.perform(post("/week/addDaysToWeek/{id}", weekTwo.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content("[" + (dayAdded.getId() + "") + "]"))
				.andExpect(status().isCreated())
				.andReturn();
		
		String response = result.getResponse().getContentAsString();
		WeekDto savedWeekDto = objectMapper.readValue(response, WeekDto.class);
		Optional<Week> savedWeek = weekRepository.findById(savedWeekDto.getId());
		Week savedWeekModel = savedWeek.get();
		
		assertThat(result).isNotNull();
		assertThat(savedWeekModel.getDayIdsInDayList().get(0)).isEqualTo(dayAdded.getId());
		assertThat(savedWeekModel.getDayIdsInDayList().get(0)).isEqualTo(dayAdded.getId());
	}
	
	@Test
	public void testAddDaysToWeekIntegrationInvalidDayAlreadyInDaysList() throws Exception{
		weekTwo = weekRepository.save(weekTwo);
		Day dayAlreadyInDayList = DayMapper.INSTANCE.toDay(dayDtoThree);
		dayAlreadyInDayList.setFoods(List.of());
		dayAlreadyInDayList = dayRepository.save(dayAlreadyInDayList);
		
		mockMvc.perform(post("/week/addDaysToWeek/{id}", weekTwo.getId())		//Add a day with ID = dayAlreadyInDayList.id into weekTwo
				.contentType(MediaType.APPLICATION_JSON)
				.content("[" + (dayAlreadyInDayList.getId() + "") + "]"));
		
		MvcResult result = mockMvc.perform(post("/week/addDaysToWeek/{id}", weekTwo.getId())	//Attempt to add the same Day object into weekTwo
				.contentType(MediaType.APPLICATION_JSON)
				.content("[" + (dayAlreadyInDayList.getId() + "") + "]"))
				.andExpect(status().isCreated())
				.andReturn();
		
		String response = result.getResponse().getContentAsString();
		WeekDto savedWeekDto = objectMapper.readValue(response, WeekDto.class);
		Optional<Week> savedWeek = weekRepository.findById(savedWeekDto.getId());
		Week savedWeekModel = savedWeek.get();
		
		assertThat(result).isNotNull();
		assertThat(savedWeekModel.getDayIdsInDayList().size()).isEqualTo(1);	//The same Day object shouldn't be able to be added into the same Week object more than once
	}
	
	@Test
	public void testAddDaysToWeekInvalidNotInDatabase() throws Exception{
		week = weekRepository.save(week);
		Day dayAdded = DayMapper.INSTANCE.toDay(dayDto);
		dayAdded = dayRepository.save(dayAdded);
		
		mockMvc.perform(post("/week/addDaysToWeek/{id}", week.getId() + 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content("[" + (dayAdded.getId() + "") + "]"))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("An unexpected error has occured. Message: Week object not found in database in addDaysToWeek method with id = " + (week.getId() + 1L)));
				
	}
	
	@Test
	public void testAddDaysToWeekInvalidEmptyBody() throws Exception{
		week = weekRepository.save(week);
		
		mockMvc.perform(post("/week/addDaysToWeek/{id}", week.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("An unexpected error has occured. Message: Required request body is missing: public org.springframework.http.ResponseEntity<com.dietPlan.web.dto.WeekDto> com.dietPlan.web.controller.WeekController.addDaysToWeek(java.lang.Long,java.util.List<java.lang.Long>)"));
	}
		
	@Test
	public void testDeleteDaysInWeekIntegrationValid() throws Exception{
		weekTwo = weekRepository.save(weekTwo);
		Day dayAdded = DayMapper.INSTANCE.toDay(dayDtoThree);
		dayAdded.setFoods(List.of());
		dayAdded = dayRepository.save(dayAdded);
		
		mockMvc.perform(post("/week/addDaysToWeek/{id}", weekTwo.getId())			
				.contentType(MediaType.APPLICATION_JSON)
				.content("[" + (dayAdded.getId() + "") + "]"))
				.andExpect(status().isCreated())
				.andReturn();
		
		MvcResult result = mockMvc.perform(post("/week/deleteDaysInWeek/{id}", weekTwo.getId())			
				.contentType(MediaType.APPLICATION_JSON)
				.content("[" + (dayAdded.getId() + "") + "]"))
				.andExpect(status().isCreated())
				.andReturn();
		
		String response = result.getResponse().getContentAsString();
		WeekDto savedWeekDto = objectMapper.readValue(response, WeekDto.class);
		Optional<Week> savedWeek = weekRepository.findById(savedWeekDto.getId());
		Week savedWeekModel = savedWeek.get();
		
		assertThat(result).isNotNull();
		assertThat(savedWeekModel.getDayIdsInDayList().size()).isEqualTo(0);
	}
	
	@Test
	public void testDeleteDaysInWeekIntegrationInvalidDayNotInDaysList() throws Exception{
		weekTwo = weekRepository.save(weekTwo);
		Day dayAlreadyInDayList = DayMapper.INSTANCE.toDay(dayDtoThree);
		dayAlreadyInDayList.setFoods(List.of());
		dayAlreadyInDayList = dayRepository.save(dayAlreadyInDayList);
		
		MvcResult result = mockMvc.perform(post("/week/deleteDaysInWeek/{id}", weekTwo.getId())	
				.contentType(MediaType.APPLICATION_JSON)
				.content("[" + (dayAlreadyInDayList.getId() + "") + "]"))
				.andExpect(status().isCreated())
				.andReturn();
		
		String response = result.getResponse().getContentAsString();
		WeekDto savedWeekDto = objectMapper.readValue(response, WeekDto.class);
		Optional<Week> savedWeek = weekRepository.findById(savedWeekDto.getId());
		Week savedWeekModel = savedWeek.get();
		
		assertThat(result).isNotNull();
		assertThat(savedWeekModel.getDayIdsInDayList().size()).isEqualTo(0);
	}
	
	@Test
	public void testDeleteDaysInWeekInvalidNotInDatabase() throws Exception{
		week = weekRepository.save(week);
		Day dayAdded = DayMapper.INSTANCE.toDay(dayDto);
		dayAdded = dayRepository.save(dayAdded);
		
		mockMvc.perform(post("/week/deleteDaysInWeek/{id}", week.getId() + 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content("[" + (dayAdded.getId() + "") + "]"))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("An unexpected error has occured. Message: Week object not found in database in deleteDaysInWeek method with id = " + (week.getId() + 1L)));
				
	}
	
	@Test
	public void testDeleteDaysInWeekInvalidEmptyBody() throws Exception{
		week = weekRepository.save(week);
		
		mockMvc.perform(post("/week/deleteDaysInWeek/{id}", week.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("An unexpected error has occured. Message: Required request body is missing: public org.springframework.http.ResponseEntity<com.dietPlan.web.dto.WeekDto> com.dietPlan.web.controller.WeekController.deleteDaysInWeek(java.lang.Long,java.util.List<java.lang.Long>)"));
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
//addDaysToWeek, deleteDaysInWeek