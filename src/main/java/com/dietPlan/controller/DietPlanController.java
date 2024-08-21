package com.dietPlan.controller;

import com.dietPlan.dto.*;
import com.dietPlan.service.DietService;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//Define HTTP endpoints for adding Foods to database, logging a meal, getting daily
//intake, etc.
@Controller
@RequestMapping("/")
public class DietPlanController {
	
	private DietService dietService;
	
	public DietPlanController(DietService dietService) {
		this.dietService = dietService;
	}
	
	//Add a Food object to the database
	@PostMapping("/addFood")
	public ResponseEntity<FoodDto> addFood(@RequestBody FoodDto foodDto){
		FoodDto savedFoodObject = dietService.addFood(foodDto);
		return new ResponseEntity<>(savedFoodObject, HttpStatus.CREATED);
	}
	
	@PostMapping("/addDay")
	public ResponseEntity<DayDto> addDay(@RequestBody DayDto dayDto){
		DayDto savedDayObject = dietService.addDay(dayDto);
		return new ResponseEntity<>(savedDayObject, HttpStatus.CREATED);
	}
	
	@PostMapping("/addWeek")
	public ResponseEntity<WeekDto> addWeek(@RequestBody WeekDto weekDto){
		WeekDto savedWeekObject = dietService.addWeek(weekDto);
		return new ResponseEntity<>(savedWeekObject, HttpStatus.CREATED);
	}
	
	@PostMapping("/addFoodsToDay/{dayId}")
	public ResponseEntity<DayDto> addFoodsToDay(@PathVariable Long dayId, @RequestBody List<Long> foodIds){
		DayDto updatedDayObject = dietService.addFoodsToDay(dayId, foodIds);
		return new ResponseEntity<>(updatedDayObject, HttpStatus.CREATED);
	}
	
	@PostMapping("/deleteFoodsInDay/{dayId}")
	public ResponseEntity<DayDto> deleteFoodsInDay(@PathVariable Long dayId, @RequestBody List<Long> foodIds){
		DayDto updatedDayObject = dietService.deleteFoodsInDay(dayId, foodIds);
		return new ResponseEntity<>(updatedDayObject, HttpStatus.CREATED);
	}

	@PostMapping("/addDaysToWeek/{weekId}")
	public ResponseEntity<WeekDto> addDaysToWeek(@PathVariable Long weekId, @RequestBody List<Long> dayIds){
		WeekDto updatedWeekObject = dietService.addDaysToWeek(weekId, dayIds);
		return new ResponseEntity<>(updatedWeekObject, HttpStatus.CREATED);
	}
	
	@PostMapping("/deleteDaysInWeek/{weekId}")
	public ResponseEntity<WeekDto> deleteDaysInWeek(@PathVariable Long weekId, @RequestBody List<Long> dayIds){
		WeekDto updatedWeekObject = dietService.deleteDaysInWeek(weekId, dayIds);
		return new ResponseEntity<>(updatedWeekObject, HttpStatus.CREATED);
	}
	
	@GetMapping("/getFood/{foodId}")
	public ResponseEntity<FoodDto> getFoodStats(@PathVariable Long foodId){
		FoodDto returnedFoodObject = dietService.getFoodStats(foodId);
		return ResponseEntity.ok(returnedFoodObject);
	}
	
	@PutMapping("/getDay/{dayId}")
	public ResponseEntity<DayDto> getDayStats(@PathVariable Long dayId){
		DayDto returnedDayObject = dietService.getDayStats(dayId);
		return ResponseEntity.ok(returnedDayObject);
	}
	
	@PutMapping("/getWeek/{weekId}")
	public ResponseEntity<WeekDto> getWeekStats(@PathVariable Long weekId){
		WeekDto returnedWeekObject = dietService.getWeekStats(weekId);
		return ResponseEntity.ok(returnedWeekObject);
	}
	
	@PutMapping("/editFood/{foodId}")
	public ResponseEntity<FoodDto> editFood(@PathVariable Long foodId, @RequestBody FoodDto foodDto){
		FoodDto foodToBeEdited = dietService.editFood(foodId, foodDto);
		return ResponseEntity.ok(foodToBeEdited);
	}
	
	@DeleteMapping("/deleteFood/{foodId}")
	public ResponseEntity<FoodDto> deleteFood(@PathVariable Long foodId){
		FoodDto foodToBeSoftDeleted = dietService.deleteFood(foodId);
		return ResponseEntity.ok(foodToBeSoftDeleted);
	}
	
	@DeleteMapping("/deleteDay/{dayId}")
	public ResponseEntity<DayDto> deleteDay(@PathVariable Long dayId){
		DayDto dayToBeSoftDeleted = dietService.deleteDay(dayId);
		return ResponseEntity.ok(dayToBeSoftDeleted);
	}
	
	@DeleteMapping("/deleteWeek/{weekId}")
	public ResponseEntity<WeekDto> deleteWeek(@PathVariable Long weekId){
		WeekDto weekToBeSoftDeleted = dietService.deleteWeek(weekId);
		return ResponseEntity.ok(weekToBeSoftDeleted);
	}
}
