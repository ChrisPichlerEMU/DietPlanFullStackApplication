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
import com.dietPlan.web.dto.WeekDto;

@RestController
@RequestMapping("/week")
public class WeekController {

	private DietService dietService;
	
	public WeekController(DietService dietService) {
		this.dietService = dietService;
	}
	
	@PostMapping("/addWeek")
	public ResponseEntity<WeekDto> addWeek(@RequestBody WeekDto weekDto){
		WeekDto savedWeekObject = dietService.addWeek(weekDto);
		return new ResponseEntity<>(savedWeekObject, HttpStatus.CREATED);
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
	
	@PutMapping("/getWeek/{weekId}")
	public ResponseEntity<WeekDto> getWeekStats(@PathVariable Long weekId){
		WeekDto returnedWeekObject = dietService.getWeekStats(weekId);
		return ResponseEntity.ok(returnedWeekObject);
	}
	
	@DeleteMapping("/deleteWeek/{weekId}")
	public ResponseEntity<WeekDto> deleteWeek(@PathVariable Long weekId){
		WeekDto weekToBeSoftDeleted = dietService.deleteWeek(weekId);
		return ResponseEntity.ok(weekToBeSoftDeleted);
	}
}
