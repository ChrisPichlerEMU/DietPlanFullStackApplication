package com.dietPlan.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dietPlan.infrastructure.service.DietService;
import com.dietPlan.web.dto.DayDto;

@RestController
@RequestMapping("/day")
public class DayController {

	private DietService dietService;
	
	public DayController(DietService dietService) {
		this.dietService = dietService;
	}
	
	@PostMapping("/addDay")
	public ResponseEntity<DayDto> addDay(@RequestBody DayDto dayDto){
		DayDto savedDayObject = dietService.addDay(dayDto);
		return new ResponseEntity<>(savedDayObject, HttpStatus.CREATED);
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
	
	@PutMapping("/getDay/{dayId}")
	public ResponseEntity<DayDto> getDayStats(@PathVariable Long dayId){
		DayDto returnedDayObject = dietService.getDayStats(dayId);
		return ResponseEntity.ok(returnedDayObject);
	}
	
	@DeleteMapping("/deleteDay/{dayId}")
	public ResponseEntity<DayDto> deleteDay(@PathVariable Long dayId){
		DayDto dayToBeSoftDeleted = dietService.deleteDay(dayId);
		return ResponseEntity.ok(dayToBeSoftDeleted);
	}
}
